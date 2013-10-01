package brorlandi.client;

import java.util.ArrayList;

import brorlandi.server.ClientSession;
import brorlandi.server.ClientSessionInterface;

public interface ClientInterface {

	public boolean isClientConnected(); ///< retorna se o cliente está conectado
	public int getLocalPort(); ///< retorna a porta que o servidor está ouvindo
	public void downClient(); ///< requisição para o cliente fechar a conexão 
	
}
