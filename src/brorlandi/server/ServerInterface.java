package brorlandi.server;

import java.util.ArrayList;


/*
 * Métodos que podem ser chamados pela sua aplicação da interface com o servidor.
 */
public interface ServerInterface {

	public boolean isServerOn(); ///< retorna se o servidor está ligado
	public int getLocalPort(); ///< retorna a porta que o servidor está ouvindo
	public void downServer(); ///< requisição para o servidor fechar 
	public ArrayList<ClientSessionInterface> getClientSessions(); ///< pega todos as sessões de clientes conectados ao servidor.
	public boolean removeClientSession(ClientSession sc); ///< desconecta o cliente do servidor
	public int sendMessageToAll(String message); ///< envia uma mensagem para todos os clientes conectados
}
