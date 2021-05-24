package sample;

import server.Server;
import socket.SocketConnection;

public class Launcher {
    public static void main(String[] args) {
        Main.main(args);
        Server.main(args);
        //Controller.main(args);
    }
}
