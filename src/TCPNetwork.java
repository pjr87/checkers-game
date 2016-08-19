/**
 * TCP Networking class used for Checkers game
 * 
 * @author phillipryan
 */

public interface TCPNetwork {
	boolean socket(String ipAddress);
	boolean accept();
	boolean isConnected();
	String recv();
	void send(String str);
	void close();
}