import java.util.List;

/**
 * UDP Networking class used for Checkers game
 * 
 * @author phillipryan
 */

public interface UDPNetwork {
	void socket();
	boolean recv();
	void send(String message);
	void close();
	List<String> getAddresses();
}