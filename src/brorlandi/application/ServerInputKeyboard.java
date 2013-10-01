package brorlandi.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import brorlandi.server.ServerAbstractInput;
import brorlandi.server.ServerCallbackInterface;


public class ServerInputKeyboard extends ServerAbstractInput{
	
	public ServerInputKeyboard(ServerCallbackInterface server) {
		super(server);
	}

	public void run(){
		String line = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); // entrada de dados
	
		while(super.isRunning()){ // le enquanto o servidor esta ligado
			try {
				line = br.readLine(); // lê teclado do servidor
			} catch (IOException e) {
				super.onException(e);
			}
			super.onServerInput(line); // avisa o servidor que há uma mensagem para ser enviada
		}
		try {
			br.close();
		} catch (IOException e) {}
	}
}
