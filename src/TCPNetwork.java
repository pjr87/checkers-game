/**
 * TCP Networking class used for Checkers game
 * 
 * @author phillipryan
 */

public interface TCPNetwork {
	boolean socket(String ipAddress);
	void bind();
	void listen();
	boolean accept();
	void connect();
	void recv();
	void send();
	void close();
}