package brorlandi.application.client;

import brorlandi.client.ClientCallbackInterface;
import brorlandi.client.ClientInterface;

public class ClientApp implements ClientCallbackInterface{

	ClientInterface mClient;
	
	@Override
	public void onClientConnected(ClientInterface client) {
		mClient = client;
		System.out.println("Conectado com o servidor na porta " + client.getSocket().getPort());
	}

	@Override
	public void onServerDisconnected() {
		System.out.println("Conexão perdida com o Servidor.");
		
	}

	@Override
	public void onException(Exception e) {
		System.out.println(e.getMessage());
		e.printStackTrace();
		
	}

	@Override
	public void onClientInput(String input) {
		if(input.equals("fechar client")){
			System.out.println("Voce solicitou fechar a conexao no cliente");	
			mClient.downClient();
		}
		else
			mClient.sendMessage(input);
	}

	@Override
	public void onMessageReceive(String message) {
		System.out.println("Server: "+ message);		
	}


}
