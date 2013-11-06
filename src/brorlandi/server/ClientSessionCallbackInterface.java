package brorlandi.server;

/**
 * Interface deve ser implementada para tratar todos os clientes conectados no servidor. Sugestão: criar um HashMap para agregar os clientes.
 * @author Bruno
 *
 */
public interface ClientSessionCallbackInterface {

	void onServerOn(ServerInterface server); ///< Quando o servidor iniciou.
	void onServerOff(ServerInterface server); ///< Quando o servidor iniciou.
	void onClientSessionConnect(ClientSessionInterface client); ///< Quando um novo cliente se conecta ao servidor.
	void onClientSessionDisconnect(ClientSessionInterface client); ///< Quando um cliente se desconecta do servidor.
	void onMessageReceive(ClientSessionInterface client, String message); ///< Quando o servidor recebe uma mensagem de um cliente.
	void onException(ClientSessionInterface client, Exception e); ///< Quando acontece uma exceção na comunicação com o cliente.
	
}
