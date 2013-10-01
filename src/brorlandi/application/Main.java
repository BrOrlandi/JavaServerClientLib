package brorlandi.application;

import brorlandi.server.Server;

public class Main {

	public static void main(String args[]){
		ServerApp server = new ServerApp();
		ServerInputKeyboard input = new ServerInputKeyboard(server);
		ClientSessionApp clientsession = new ClientSessionApp();
		new Server(server, clientsession,input);
	}
}
