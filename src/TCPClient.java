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
			System.out.println("Start TCP client");
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String recv() {
		try {
			in = new BufferedReader(new InputStreamReader(Socket.getInputStream()));
			System.out.println("TCPClient recv");
			String str = in.readLine();
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
			System.out.println("TCPClient Sending " + str);
			out = new PrintWriter(Socket.getOutputStream(), true);
			out.println(str);
			System.out.println("TCPClient send");
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		try {
			Socket.close();
			out.close();
			in.close();
		} 
		catch (IOException e) {
		}
	}

	@Override
	public boolean isConnected() {
		return Socket.isConnected();
	}
}