import java.util.List;

/**
 * UDP Networking class used for Checkers game
 * 
 * @author phillipryan
 */

public interface UDPNetwork {
	void socket();
	void recv();
	void send();
	void close();
	List<String> getAddresses();
}