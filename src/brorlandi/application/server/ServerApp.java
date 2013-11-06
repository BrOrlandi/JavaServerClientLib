package brorlandi.application.server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import brorlandi.server.ClientSessionInterface;
import brorlandi.server.ServerCallbackInterface;
import brorlandi.server.ServerInterface;

public class ServerApp implements ServerCallbackInterface {

	ServerInterface mServer;
	
	@Override
	public void onServerOn(ServerInterface server) {
		mServer = server;
		try {
			System.out.println("Servidor aberto: "+InetAddress.getLocalHost().getHostAddress() +":"+server.getLocalPort());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getPort() {
		return 6666;
	}

	@Override
	public void onException(Exception e) {
		System.out.println("EXCEPTION: "+e.getMessage());
		e.printStackTrace();
	}

	@Override
	public void onServerClosesByException(Exception e) {
		System.out.println("CLOSED BY EXCEPTION: "+e.getMessage());
		e.printStackTrace();
		
	}

	@Override
	public void onServerInput(String input) {
		if(input.equals(".")){
			mServer.sendMessageToAll("..");
			mServer.downServer();
			return;
		}
		System.out.println("ServerInput: "+input);
		ArrayList<ClientSessionInterface> sessions = mServer.getClientSessions();
		for(ClientSessionInterface sc : sessions){
			sc.sendMessage("Server: " +input);
		}
		System.out.println(sessions.size()+" clientes receberam a mensagem.");
		
	}

	@Override
	public void onServerOff(ServerInterface server) {
		System.out.println("Fechando Servidor.");
		System.exit(1);	
	}
}
