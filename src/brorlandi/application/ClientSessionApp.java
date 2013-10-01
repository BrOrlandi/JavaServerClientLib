package brorlandi.application;

import java.net.Socket;

import brorlandi.server.ClientSessionCallbackInterface;
import brorlandi.server.ServerInterface;

public class ClientSessionApp implements ClientSessionCallbackInterface {

	private ServerInterface mServerInterface;

	@Override
	public void onServerOn(ServerInterface server) {
		mServerInterface = server;
	}

	@Override
	public synchronized void onClientSessionConnect(Socket client) {
		 System.out.println("Cliente: "+client.getInetAddress().getHostAddress() + ":"+client.getPort());
	}

	@Override
	public void onMessageReceive(String message) {
		System.out.println("Recebi: "+message);		
		String toupper = message.toUpperCase();
		System.out.println("Enviando de volta: "+toupper);
		mServerInterface.sendMessageToAll(toupper);
	}

	@Override
	public void onException(Exception e) {
		System.out.println("ERRO CLIENT SESSION: "+e.getMessage());
		e.printStackTrace();
	}

	@Override
	public void onClientSessionDisconnect(Socket client) {
		// TODO Auto-generated method stub
		
	}
}
