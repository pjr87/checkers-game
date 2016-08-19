/**
 * TCP Client, implements networking class used for Checkers game
 * 
 * @author phillipryan
 */
import java.io.*;
import java.net.*;

public class TCPClient implements TCPNetwork {
	
    Socket Socket = null;
    ObjectOutputStream out = null;
    ObjectInputStream in = null;

	@Override
	public boolean socket(String ipAddress) {
		try{
			System.out.println("Start TCP client");
			Socket = new Socket(ipAddress, 10007);
         
			out = new ObjectOutputStream(Socket.getOutputStream());
			in = new ObjectInputStream(Socket.getInputStream());
			
			return true;
		} 
		catch (IOException e) {
			System.err.println("Couldn't get I/O for "
                            + "the connection to: " + ipAddress);
		}
		return false;
	}

	@Override
	public void bind() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void listen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean accept() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void connect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void recv() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void send() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}
}