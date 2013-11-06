package brorlandi.application.server;

import brorlandi.server.ClientSessionCallbackInterface;
import brorlandi.server.ClientSessionInterface;
import brorlandi.server.ServerInterface;

public class ClientSessionApp implements ClientSessionCallbackInterface {

	private ServerInterface mServerInterface;

	@Override
	public void onServerOn(ServerInterface server) {
		mServerInterface = server;
	}

	@Override
	public synchronized void onClientSessionConnect(ClientSessionInterface client){
		 System.out.println("Cliente: "+client.getSocket().getInetAddress().getHostAddress() + ":"+client.getSocket().getPort());
	}

	@Override
	public void onMessageReceive(ClientSessionInterface client, String message){
		System.out.println("Recebi: "+message);		
		String toupper = message.toUpperCase();
		System.out.println("Enviando de volta: "+toupper);
		mServerInterface.sendMessageToAll(toupper);
	}

	@Override
	public void onException(ClientSessionInterface client, Exception e) {
		System.out.println("ERRO CLIENT SESSION: "+e.getMessage());
		e.printStackTrace();
	}

	@Override
	public void onClientSessionDisconnect(ClientSessionInterface client) {
		 System.out.println("Desconectou Cliente: "+client.getSocket().getInetAddress().getHostAddress() + ":"+client.getSocket().getPort());
		
	}

	@Override
	public void onServerOff(ServerInterface server) {
		 System.out.println("Servidor fechou (ClienteSession)");
		
	}
}
