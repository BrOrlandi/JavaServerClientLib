package brorlandi.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable, ClientInterface{

	private String mServerAddress; // 'localhost'
	private int mPorta;
	
	private Socket mServer;
	private BufferedReader mServerInput;
	private BufferedWriter mServerOutput;
	
	private ClientAbstractInput mInputThread;
	private ClientCallbackInterface mClientCallback;
	
	
	public Client(String server, int porta, ClientAbstractInput input){
		mServerAddress = server;
		mPorta = porta;

		new Thread(this).start();
		mInputThread = input;
		if(mInputThread != null){
			mInputThread.start(); // inicia a Thread
			mInputThread.setName("Client Input Thread"); //nome da Thread para debug
		}
	}
	
	public static void main(String args[]){
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String serveraddr=null;
		int porta = 6666;
		try {
			System.out.println("Digite o endereço ou IP do servidor:");
			serveraddr = br.readLine();
			System.out.println("Digite a Porta do servidor: ");
			porta = Integer.parseInt(br.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}

		new Client(serveraddr, porta);
	}
	
	public void run(){
		String mensagemFeedback;
		
		mServer = null;
		try {
			mServer = new Socket(mServerAddress, mPorta);
			System.out.println("Conectado ao Servidor em: "+ mServer.getInetAddress().getHostAddress() +":"+mServer.getPort());
		} catch (UnknownHostException e1) {
			System.out.println("Servidor desconhecido. "+ e1.getMessage());
			System.exit(1);
		} catch (Exception e1) {
			System.out.println("ERRO: "+e1.getMessage());
			System.exit(1);
		}
		
		
		try{
			mServerInput = new BufferedReader(new InputStreamReader(mServer.getInputStream()));
			mServerOutput = new BufferedWriter(new OutputStreamWriter(mServer.getOutputStream()));

			mensagemFeedback = "";
			do{
				System.out.println(mensagemFeedback);
				mensagemFeedback = mServerInput.readLine();
			}while(mensagemFeedback != null);
			
			mServerInput.close();
			mServerOutput.close();
			mServer.close();
			if(mServer.isClosed() == true){
				mInputThread.setConnected(false);
				System.out.println("A conexao com o servidor foi fechada :(");
			}
		}catch(IOException e){
			System.out.println("IOException ERRO: "+e.getMessage());
			e.printStackTrace();
		}catch(Exception e){
			System.out.println("ERRO: "+e.getMessage());
			e.printStackTrace();
		} finally {

			try {
				mServerInput.close();
				mServerOutput.close();
				mServer.close();

			} catch (IOException e) {
			}

		}
	}

	@Override
	public void clientInput(String input) {
		try {
			mServerOutput.write(input);
			mServerOutput.newLine();
			mServerOutput.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
