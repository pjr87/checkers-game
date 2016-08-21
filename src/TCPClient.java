/**
 * TCP Client, implements networking class used for Checkers game
 * 
 * @author phillipryan
 */
import java.io.*;
import java.net.*;

public class TCPClient implements TCPNetwork {
	
    Socket Socket = null;
    PrintWriter out = null;
    BufferedReader in = null;

	@Override
	public boolean socket(String ipAddress) {
		try{
			Socket = new Socket(ipAddress, 10007);

			return true;
		}
		catch (IOException e) {
			System.err.println("Couldn't get I/O for "
                            + "the connection to: " + ipAddress);
		}
		return false;
	}

	@Override
	public boolean accept() {
		return false;
	}

	@Override
	public String recv() {
		try {
			in = new BufferedReader(new InputStreamReader(Socket.getInputStream()));
			String str = in.readLine();
			System.out.println("NETWORK TCPClient recveived: " + str);
			return str;
		} 
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void send(String str) {
		try {
			out = new PrintWriter(Socket.getOutputStream(), true);
			out.println(str);
			System.out.println("NETWORK TCPClient Sent: " + str);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		try {
			Socket.close();
		} 
		catch (IOException e) {
		}
	}

	@Override
	public boolean isConnected() {
		return Socket.isConnected();
	}
}