package brorlandi.application.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import brorlandi.client.ClientAbstractInput;
import brorlandi.client.ClientCallbackInterface;

public class ClientInputKeyboard extends ClientAbstractInput{
	public ClientInputKeyboard(ClientCallbackInterface client) {
		super(client);
	}

	public void run(){
		String line = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); // entrada de dados
	
		while(super.isRunning()){ // le enquanto o cliente esta conectado com o servidor
			try {
				line = br.readLine(); // lê teclado do cliente
			} catch (IOException e) {
				super.onException(e);
			}
			super.onClientInput(line); // input do cliente na aplicação.
		}
		try {
			br.close();
		} catch (IOException e) {}
	}
}
