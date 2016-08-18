/**
 * TCP Server, implements networking class used for Checkers game
 * 
 * @author phillipryan
 */

import java.net.*; 
import java.io.*;

public class TCPServer implements TCPNetwork {
	
	ServerSocket serverSocket = null; 
	Socket clientSocket = null; 

	@Override
	public boolean socket(String ipAddress) {
		try { 
			serverSocket = new ServerSocket(10007); 
		} 
		catch (IOException e) 
		{ 
			System.err.println("Could not listen on port: 10007."); 
			System.exit(1); 
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
		try { 
			System.out.println ("Waiting for Client");
			clientSocket = serverSocket.accept(); 
			return true;
		} 
		catch (IOException e) { 
			System.err.println("Accept failed."); 
			return false;
		} 
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
