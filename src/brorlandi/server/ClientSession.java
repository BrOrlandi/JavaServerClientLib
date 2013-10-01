package brorlandi.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Representa uma sessão do Cliente no Servidor.
 * @author Bruno
 *
 */
public class ClientSession extends Thread implements ClientSessionInterface{
	private Socket mClient; ///< Socket que conversa com o cliente
	private BufferedReader mClienteInput = null; ///< Buffer de leitura de dados do cliente.
	private BufferedWriter mClienteOutput = null; ///< Buffer de escrita de dados para o cliente.
	
	private ClientSessionCallbackInterface mClientSessionCallback; ///< Callback para tratar sessão do cliente.

	public ClientSession(ClientSessionCallbackInterface csbi, Socket cliente){
		mClientSessionCallback = csbi;
		mClient = cliente;
	}
	
	public void run(){

		mClientSessionCallback.onClientSessionConnect(mClient);
        
        
		// comunicação com o cliente
		try{
			mClienteInput = new BufferedReader(new InputStreamReader(mClient.getInputStream()));
			mClienteOutput = new BufferedWriter(new OutputStreamWriter(mClient.getOutputStream()));
			String read;
			
			do{
				read = mClienteInput.readLine();
				if(read != null){
					mClientSessionCallback.onMessageReceive(read);
				}
			}while(read != null);
			if(read == null){
				mClientSessionCallback.onClientSessionDisconnect(mClient);
			}
		}catch(Exception e){
			mClientSessionCallback.onException(e);
		} finally {

            try {
        	mClienteOutput.close();
            mClienteInput.close();
            mClient.close();
            } catch (IOException e) {
            }
            //mServer.removeClientSession(this);
        }
	}

	@Override
	public synchronized void sendMessage(String message) {
		try {
			mClienteOutput.write(message);
			mClienteOutput.newLine();
			mClienteOutput.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}