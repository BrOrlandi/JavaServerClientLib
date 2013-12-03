package brorlandi.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;


/**
 * Contem o ServerSocket e trata as requisi��es dos clientes.
 * Implementa Runnable para ser executado em uma Thread
 * @author Bruno
 *
 */
public class Server implements Runnable,ServerInterface
{

	private ServerSocket mServer; ///< O servidor possui um socket de servidor
	private ArrayList<ClientSessionInterface> mSessions; ///< lista com os clientes conectados
	private ServerAbstractInput mInputThread; ///< Thread responsavel pela entrada de dados, exemplo: Teclado
	private boolean mServerOn; ///< Variavel para indicar se o servidor está ligado.
	
	private ServerCallbackInterface mServerCallback; ///< interface callback quando o servidor executa operações.
	private ClientSessionCallbackInterface mClientSessionCallback; ///< interface callback para atender cada cliente.
	
	private Object lockSessions;
	
	public Server(ServerCallbackInterface server, ClientSessionCallbackInterface clientsession, ServerAbstractInput input){
		mServerCallback = server;
		mClientSessionCallback = clientsession;
		try {
			mServer = new ServerSocket(mServerCallback.getPort());
		} catch (IOException e) {
			mServerCallback.onServerClosesByException(e);
			System.exit(1);
		} // Criar ServerSocket
		mSessions = new ArrayList<ClientSessionInterface>(); // Lista de Sessões dos Clientes
		mServerOn = true; // Servidor inicia ligado
		lockSessions = new Object();
		Thread serverThread = new Thread(this); // Roda o servidor em uma Thread nova;
		serverThread.start(); // inicia a Thread.
		serverThread.setName("Server Thread"); // nome da Thread para debug
		mInputThread = input; // thread de input do servidor
		if(mInputThread != null){
			mInputThread.start(); // inicia a Thread
			mInputThread.setName("Server Input Thread"); //nome da Thread para debug
		}
	}
	
	@Override
	public void run() {
		mServerCallback.onServerOn(this);
		mClientSessionCallback.onServerOn(this);
		
		while(isServerOn()){
			//aguarda cliente
			Socket cliente = null;
			ClientSession sc;
			try {
				cliente = mServer.accept();
				// cria uma nova Thread para receber o cliente
				sc = new ClientSession(mClientSessionCallback,cliente);
				sc.setServer(this);
				synchronized (lockSessions) {
					mSessions.add(sc);	// adiciona a lista de clientes conectados
				}
				sc.start(); // inicia a Thread para tratar o cliente
			}catch(SocketException e){
			}catch(IOException e){
				//System.out.println("Falha de IO ao conectar cliente.");
				//e.printStackTrace();
				onException(e);
			}
		}
		mServerCallback.onServerOff(this);
		mClientSessionCallback.onServerOff(this);
		//Server Close callback
	}
	
	public void onException(Exception e){
		mServerCallback.onException(e);
	}
	
	@Override
	public boolean isServerOn(){
		return mServerOn;
	}

	@Override
	public void downServer(){
		mServerOn = false;
		if(mInputThread != null)
			mInputThread.closeServerInputThread();
		try {
			mServer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean removeClientSession(ClientSession sc){ // synchronized para evitar problemas de vários clientes se desconectando ao mesmo tempo.
		boolean b = false;
		synchronized (lockSessions) {
			b = mSessions.remove(sc);
		}
		return b;
	}

	@Override
	public ArrayList<ClientSessionInterface> getClientSessions(){
		return mSessions;
	}

	@Override
	public int getLocalPort() {
		return mServer.getLocalPort();
	}

	@Override
	public int sendMessageToAll(String message) { // synchronized para não ter mais de uma thread mandando mensagem para todos os clientes
		synchronized (lockSessions) {
			for(ClientSessionInterface sci : mSessions){
				sci.sendMessage(message);
			}
		}
		return mSessions.size();
	}
}
