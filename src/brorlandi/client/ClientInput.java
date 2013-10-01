package brorlandi.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientInput extends Thread{
	
	private ClientInputInterface mClient; // cliente que possui interface para tratar mensagens de entrada.
	private boolean mConnected;
	
	public ClientInput(ClientInputInterface client){
		mClient = client; // recebe a interface no construtor
		setConnected(true);
	}
	
	public void run(){
		String line = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); // entrada de dados
		try{
			while(true){ 
				try {
					line = br.readLine(); // lê teclado do cliente
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(getConnected()){ // le enquanto o cliente esta ligado a um servidor
					mClient.clientInput(line); // avisa o cliente que há uma mensagem para ser enviada
				}
				else{
					break;
				}
			}
		}catch(Exception e){
			//e.printStackTrace();
		}finally{
			System.out.println("ClientInput is closed");
			try {
				br.close();
			} catch (IOException e) {}
		}
	}

	public synchronized boolean getConnected(){
		return mConnected;
	}
	
	public synchronized void setConnected(boolean conn){
		mConnected = conn;
	}
}