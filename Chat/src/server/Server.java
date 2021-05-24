package server;

import socket.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.SocketHandler;

public class Server implements IServer {

    private int port = 0;
    private ServerSocket server = null;
    public boolean running = false;
    private ArrayList<ISocketConnection> clients = new ArrayList<>();
    private ArrayList<String> channels = new ArrayList<>();


    public Server(int port) {
        this.port = port;
    }

    private void systemMessage(ISocketMessage msg) {
        if (msg.getMessage().contains("{NEWUSER}")) {
            //new User joined!

            String username = msg.getMessage().split(" ")[1];
            msg.getSource().setUsername(username);
        }
        String allUsers = listUsers(msg.getSource().getChannel());

        SocketMessage newUser = new SocketMessage(msg.getSource(), "{USERS} " + allUsers);
        broadcastMessage(newUser);


    }


    private void changeChannel(ISocketMessage msg) {
        String oldChannel = msg.getSource().getChannel();
        String newChannel = msg.getMessage().replace("@channel ", "");

        if (!channels.contains(newChannel)) {
            channels.add(newChannel);
        }else if(!channels.contains("Main")){
            channels.add("Main");
        }

        msg.getSource().setChannel(newChannel);
        System.out.println("Channel wurde geaendert für " + msg.getSource().getUsername() + " von " + oldChannel + " auf " + msg.getSource().getChannel());

        String users = listUsers(oldChannel);
        String channels = listChannels();

        SocketMessage channelMessage = new SocketMessage(msg.getSource(), "{CHANNELS} " + channels);
        broadcastMessage(channelMessage);

        SocketMessage userMessage = new SocketMessage(msg.getSource(), "{USERS} " + users);
        broadcastMessage(userMessage, oldChannel);

        users = listUsers(newChannel);
        userMessage = new SocketMessage(msg.getSource(), "{USERS} " + users);
        broadcastMessage(userMessage, newChannel);
    }


    private String listUsers(String channel) {
        String allUsers = "";

        for (ISocketConnection c : clients) {
            if (c.getChannel().equals(channel)) {
                if (allUsers.length() > 0) {
                    allUsers += ";";
                }
                allUsers += c.getUsername();
            }
        }
        return allUsers;
    }

    private String listChannels() {
        String allChannels = "";
        int counter=0;
        while (channels.size() != counter) {
            allChannels = allChannels + ";" + channels.get(counter);
            counter++;
        }

        System.out.println(allChannels);
        return allChannels;
    }


    private void broadcastMessage(ISocketMessage msg) {
        broadcastMessage(msg, msg.getSource().getChannel());
    }

    private void broadcastMessage(ISocketMessage msg, String channel) {
        for (ISocketConnection c : clients) {
            if (c.getChannel().equals(channel)) {
                c.sendMessage(msg);
            }
        }
    }

    private void privateMessage(ISocketMessage msg) {
        // Schneide alles zwischen dem "@" und dem ersten leerzeichen(" ") aus!
        // Aus @peter testnachricht wird das @ und die testnachricht entfernt, sodass nurnoch der Username stehenbleibt!
        String receiver = msg.getMessage().substring(1, msg.getMessage().indexOf(" "));
        if (receiver.length() > 0) {
            for (ISocketConnection c : clients) {
                if (c.getUsername().equals(receiver)) {
                    //c ist der Empfänger -> schicke die Nachricht an c!
                    String message = msg.getMessage().substring(msg.getMessage().indexOf(" ") + 1);
                    message = "Private Nachricht von " + msg.getSource().getUsername() + ": " + message;
                    c.sendMessage(new SocketMessage(msg.getSource(), message));
                } else if (c == msg.getSource()) {
                    // c ist der Sender -> schicke die Nachricht an c!
                    String message = msg.getMessage().substring(msg.getMessage().indexOf(" ") + 1);
                    message = "Private Nachricht an " + receiver + ": " + message;
                    c.sendMessage(new SocketMessage(msg.getSource(), message));
                }
            }
        }
    }


    @Override
    public void handleConnections() {
        System.out.println("Listening for incoming connections...");

        ISocketSubscriber broadcastSubscriber = new ISocketSubscriber() {
            @Override
            public void messageReceived(ISocketMessage msg) {
                System.out.println("Nachricht erhalten: " + msg.getMessage());

                if (msg.getMessage().contains("{") && msg.getMessage().contains("}")) {
                    systemMessage(msg);
                } else if (msg.getMessage().charAt(0) == '@') { // die nachricht startet mit einem @
                    if (msg.getMessage().startsWith("@channel")) {
                        changeChannel(msg);
                    } else {
                        // private nachricht
                        privateMessage(msg);
                    }
                } else {
                    SocketMessage broadCastMessage =
                            new SocketMessage(
                                    msg.getSource(),
                                    msg.getSource().getUsername() +
                                            ": " +
                                            msg.getMessage()
                            );

                    broadcastMessage(broadCastMessage);
                }
            }
        };

        new Thread() {
            @Override
            public void run() {
                while (running) {
                    try {
                        Socket incoming = server.accept(); // new client connects to the server

                        System.out.println("New incoming connection: " + incoming.getInetAddress().getHostAddress());

                        SocketConnection client = new SocketConnection(incoming); // wrap client into SocketConnection class
                        clients.add(client); // add client to the list of all clients

                        client.addSubscriber(broadcastSubscriber); // add broadcastSubscriber (which is called, when a messages was received) to the connection

                        client.start(); // start receiving messages
                    } catch (IOException e) {
                    }
                }
            }
        }.start();
    }

    @Override
    public void start() throws IOException {
        System.out.println("Starting server...");
        running = true;
        server = new ServerSocket(port);

        handleConnections();
    }

    @Override
    public void stop() {
        running = false;
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server chatServer = new Server(5432);

        try {
            chatServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

