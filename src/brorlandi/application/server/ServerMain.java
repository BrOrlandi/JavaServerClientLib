package brorlandi.application.server;

import brorlandi.server.Server;

public class ServerMain {

	public static void main(String args[]){
		ServerApp server = new ServerApp();
		ServerInputKeyboard input = new ServerInputKeyboard(server);
		ClientSessionApp clientsession = new ClientSessionApp();
		new Server(server, clientsession,input);
	}
}
