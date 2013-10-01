package brorlandi.server;


/**
 * ServerCallbackInterface have all methods that server can call to running an application
 * @author Bruno H. Orlandi
 *
 */
public interface ServerCallbackInterface {

	/**
	 * Called when server is on
	 * @param server ServerInterface of the server
	 */
	public void onServerOn(ServerInterface server);

	/**
	 * Called when server is turned off
	 * @param server ServerInterface of the server
	 */
	public void onServerOff(ServerInterface server);
	
	/**
	 * is called by server when it starts, will try to use the number of port returned by this method
	 * @return the port that server will try to listen
	 */
	public int getPort();
	
	/**
	 * Called when occurs a Exception in the server.
	 * @param e exception
	 */
	public void onException(Exception e);
	
	/**
	 * Will be called when server is closed by an exception
	 * @param e exception
	 */
	public void onServerClosesByException(Exception e);
	
	/**
	 * Will be called by the Server Input Thread when server has an input
	 * @param input
	 */
	public void onServerInput(String input);
	
	//public void onServerClosing()
}
