package socket;

import java.io.IOException;

public interface IServer{

    public void handleConnections();

    public void start() throws IOException;

    public void stop();
}
