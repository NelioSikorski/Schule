package socket;

public interface ISocketSubscriber {

    /**
     * method is called if something happened
     * (message was received)
     * @param msg Message
     */
    void messageReceived(ISocketMessage msg);

}
