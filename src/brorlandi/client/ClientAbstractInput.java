package brorlandi.client;


public abstract class ClientAbstractInput extends Thread{

	private ClientCallbackInterface mClient; // cliente que possui interface para tratar mensagens de entrada.
	private boolean isRunning;
	
	public ClientAbstractInput(ClientCallbackInterface client){
		mClient = client;
		isRunning = true;
	}
	
	public void onException(Exception e){
		mClient.onException(e);
	}
	
	public void onClientInput(String input){
		if(isRunning)
			mClient.onClientInput(input);
	}
	
	public void closeClientInputThread(){
		isRunning = false;
	}
	
	public boolean isRunning(){
		return isRunning;
	}

	public abstract void run();
}
