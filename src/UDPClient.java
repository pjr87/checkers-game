/**
 * Client, implements networking class used for Checkers game
 * 
 * @author phillipryan
 */

import java.io.*;
import java.net.*;
import java.util.List;

public class UDPClient implements UDPNetwork{
	
	public static final int DEFAULT_PORT = 7777;
	private DatagramSocket socket = null;
	private DatagramPacket packet;
	//private String hostname= "localhost";
    private InetAddress host;

	@Override
	public void socket() {
		try 
		{
			//host = InetAddress.getByName(hostname);
			host = InetAddress.getLocalHost();
			socket = new DatagramSocket (null);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void recv() {
		try 
		{
			 packet.setLength(100);
			 socket.receive (packet);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void send() {
		try 
		{
			packet = new DatagramPacket (new byte[100], 0,host, DEFAULT_PORT);
			 System.out.println("Sent from: " + packet.getAddress () + ":" +
                     packet.getPort ());
			socket.send (packet);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		socket.close ();
	}

	@Override
	public List<String> getAddresses() {
		return null;
	}

}