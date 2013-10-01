package brorlandi.server;

import java.net.Socket;

public interface ClientSessionCallbackInterface {

	void onServerOn(ServerInterface server);
	void onClientSessionConnect(Socket client);
	void onClientSessionDisconnect(Socket client);
	void onMessageReceive(String message);
	void onException(Exception e);
	
}
