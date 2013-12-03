package brorlandi.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;

/**
 * Representa uma sessão do Cliente no Servidor.
 * @author Bruno
 *
 */
public class ClientSession extends Thread implements ClientSessionInterface{
	private Socket mClient; ///< Socket que conversa com o cliente
	private BufferedReader mFromClientInput = null; ///< Buffer de leitura de dados do cliente.
	private BufferedWriter mToClientOutput = null; ///< Buffer de escrita de dados para o cliente.
	
	private ClientSessionCallbackInterface mClientSessionCallback; ///< Callback para tratar sessão do cliente.
	private Server mServer;
	
	private Object lockSendMessage;

	public ClientSession(ClientSessionCallbackInterface csbi, Socket cliente){
		mClientSessionCallback = csbi;
		mClient = cliente;
		lockSendMessage = new Object();
	}
	
	public void run(){
		// comunicação com o cliente
		try{
			mFromClientInput = new BufferedReader(new InputStreamReader(mClient.getInputStream()));
			mToClientOutput = new BufferedWriter(new OutputStreamWriter(mClient.getOutputStream()));
			
			mClientSessionCallback.onClientSessionConnect(this);
			
			String read;
			
			do{
				read = mFromClientInput.readLine();
				if(read != null){
					mClientSessionCallback.onMessageReceive(this, read);
				}
			}while(read != null);
			if(read == null){
	            mServer.removeClientSession(this);
				mClientSessionCallback.onClientSessionDisconnect(this);
			}
		}catch(SocketException se){
            mServer.removeClientSession(this);
			mClientSessionCallback.onClientSessionDisconnect(this);
		} catch(Exception e){
			mClientSessionCallback.onException(this, e);
		} finally {

            try {
            	mToClientOutput.close();
	        	mFromClientInput.close();
	            mClient.close();
            } catch (IOException e) {
            }
        }
	}

	@Override
	public void sendMessage(String message) {
		synchronized (lockSendMessage) {
			try {
				mToClientOutput.write(message);
				mToClientOutput.newLine();
				mToClientOutput.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public Socket getSocket(){
		return mClient;
	}
	
	public void setServer(Server server){
		mServer = server;
	}
}