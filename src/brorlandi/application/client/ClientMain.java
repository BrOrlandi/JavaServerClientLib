package brorlandi.application.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import brorlandi.client.Client;

public class ClientMain {

	
	public static void main(String args[]){
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String serveraddr=null;
		int porta = 6666;
		try {
			System.out.println("Digite o endereço ou IP do servidor:");
			serveraddr = br.readLine();
			//System.out.println("Digite a Porta do servidor: ");
			//porta = Integer.parseInt(br.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ClientApp client = new ClientApp();
		ClientInputKeyboard input = new ClientInputKeyboard(client);
		new Client(serveraddr,porta,client,input);
	}

}
