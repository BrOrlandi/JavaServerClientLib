package brorlandi.application.server;

import java.io.IOException;

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
		 System.out.println("Cliente Conectado: "+client.getSocket().getInetAddress().getHostAddress() + ":"+client.getSocket().getPort());
		 mServerInterface.sendMessageToAll("Cliente Conectado: "+client.getSocket().getInetAddress().getHostAddress() + ":"+client.getSocket().getPort());
	}

	@Override
	public void onMessageReceive(ClientSessionInterface client, String message){
		if(message.equals("fechar server")){
			mServerInterface.sendMessageToAll("Voce solicitou que o servidor feche sua conexao!");
			try {
				client.getSocket().close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			System.out.println("Recebi: "+message);		
			String toupper = message.toUpperCase();
			System.out.println("Enviando de volta: "+toupper);
			mServerInterface.sendMessageToAll(toupper);
		}
	}

	@Override
	public void onException(ClientSessionInterface client, Exception e) {
		System.out.println("ERRO CLIENT SESSION: "+e.getMessage());
		e.printStackTrace();
	}

	@Override
	public void onClientSessionDisconnect(ClientSessionInterface client) {
		 System.out.println("Desconectou Cliente: "+client.getSocket().getInetAddress().getHostAddress() + ":"+client.getSocket().getPort());
		 mServerInterface.sendMessageToAll("Desconectou Cliente: "+client.getSocket().getInetAddress().getHostAddress() + ":"+client.getSocket().getPort());
	}

	@Override
	public void onServerOff(ServerInterface server) {
		 System.out.println("Servidor fechou (ClienteSession)");
	}
}
