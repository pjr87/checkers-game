/**
 * Client, implements networking class used for Checkers game
 * 
 * @author phillipryan
 */

import java.io.*;
import java.net.*;
import java.util.List;

public class UDPClient implements UDPNetwork{
	
	public static final int DEFAULT_PORT = 5001;
	private DatagramSocket socket = null;
	private DatagramPacket packet;
    private InetAddress host;

	@Override
	public void socket() {
		try 
		{
			host = InetAddress.getByName("255.255.255.255");
			socket = new DatagramSocket ();
		} 
		catch (Exception e) 
		{
			//e.printStackTrace();
		}
	}

	@Override
	public boolean recv() {
		try 
		{
			 packet.setLength(100);
			 socket.receive (packet);
		} 
		catch (IOException e) 
		{
			//e.printStackTrace();
		}
		return false;
	}

	@Override
	public void send(String message) {
		try 
		{
            byte[] sendData = message.getBytes("UTF-8");
			packet = new DatagramPacket (sendData, sendData.length, host, DEFAULT_PORT);
			socket.send (packet);
		} 
		catch (IOException e) 
		{
			//e.printStackTrace();
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