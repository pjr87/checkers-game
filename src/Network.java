/**
 * Networking class used for Checkers game
 * 
 * @author phillipryan
 */

abstract class Network {
	
	/**
	 * The constructor.
	 */
	protected Network() {
		super();
	}
	
	abstract protected void StartNetworking();
	abstract protected void Connect(String ipAddress);
	abstract protected void SendMove();
	abstract protected void RecvMove();
	abstract protected void CloseNetworking();
}