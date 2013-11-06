package brorlandi.server;

/*
 * Deve herdar esta classe para implementar a thread de input do servidor, se houver, 
 * podendo chamar os métodos onServerInput quando há uma mensagem no servidor e onException para avisar uma exception no servidor.
 */
public abstract class ServerAbstractInput extends Thread{

	private ServerCallbackInterface mServer; // servidor que possui interface para tratar mensagens de entrada.
	private boolean isRunning;
	
	public ServerAbstractInput(ServerCallbackInterface server){
		mServer = server; // recebe a interface no construtor
		isRunning = true;
	}
	
	public void onException(Exception e){
		mServer.onException(e);
	}
	
	public void onServerInput(String input){
		if(isRunning)
			mServer.onServerInput(input);
	}
	
	public void closeServerInputThread(){
		isRunning = false;
	}
	
	public boolean isRunning(){
		return isRunning;
	}

	public abstract void run();
}
