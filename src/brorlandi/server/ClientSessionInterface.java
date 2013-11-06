package brorlandi.server;

import java.net.Socket;

/*
 * Interface com os objetos dos clientes contém metodos que pode ser chamados.
 */
public interface ClientSessionInterface {

	public void sendMessage(String message); ///< Envia uma mensagem para o cliente.
	public Socket getSocket(); ///< Pega o socket de comunicação com o cliente.
}
