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
	
	PrintWriter out;
	BufferedReader in;

	@Override
	public boolean socket(String ipAddress) {
		try { 
			serverSocket = new ServerSocket(10007);
			/*serverSocket.setReuseAddress(true);
			serverSocket.bind(new InetSocketAddress(10007));*/
		} 
		catch (IOException e) 
		{ 
			e.printStackTrace();
			System.err.println("Could not listen on port: 10007: " + e.getMessage()); 
			System.exit(1); 
		}
		return false;
	}

	@Override
	public boolean accept() {
		try { 
			clientSocket = serverSocket.accept(); 
			
			return true;
		} 
		catch (IOException e) { 
			System.err.println("Accept failed."); 
			return false;
		} 
	}

	@Override
	public String recv() {
		try {
			in = new BufferedReader(new InputStreamReader( clientSocket.getInputStream())); 
			String str = in.readLine();
			System.out.println("NETWORK TCPServer recveived: " + str);
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
			out = new PrintWriter(clientSocket.getOutputStream(), true); 
			out.println(str);
			System.out.println("NETWORK TCPServer Sent: " + str);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		try {
			serverSocket.close();
			clientSocket.close();
			out.close();
			in.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isConnected() {
		try {
			return serverSocket.getInetAddress().isReachable(0);
		} catch (IOException e) {
			return false;
		}
	}

}
