package brorlandi.client;

import java.net.Socket;


public interface ClientInterface {

	public boolean isClientConnected(); ///< retorna se o cliente está conectado
	public Socket getSocket(); ///> retorna o socket de comunicação com o servidor.
	public void sendMessage(String message); ///< envia uma mensagem para o servidor.
	public void downClient(); ///< solicita que o cliente se desconecte.
	
}
