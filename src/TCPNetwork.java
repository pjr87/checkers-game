/**
 * TCP Networking class used for Checkers game
 * 
 * @author phillipryan
 */

public interface TCPNetwork {
	void socket();
	void bind();
	void listen();
	void accept();
	void connect();
	void recv();
	void send();
	void close();
}