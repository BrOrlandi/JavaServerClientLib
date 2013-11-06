package brorlandi.client;



/**
 * Deve ser implementado para tratar os eventos do lado do cliente.
 * @author Bruno H. Orlandi
 *
 */
public interface ClientCallbackInterface {

	public void onClientConnected(ClientInterface client); ///< Cliente conectado com o servidor.
	public void onServerDisconnected(); ///< Quando a conexão com o servidor foi perdida.
	public void onException(Exception e); ///< Quando ocorreu uma exceção no
	public void onClientInput(String input); ///< Quando o cliente envia uma mensagem
	public void onMessageReceive(String message); ///< Quando o cliente recebe uma mensagem do servidor
}
