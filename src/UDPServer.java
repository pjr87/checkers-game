/**
 * UDP Server, implements networking class used for Checkers game
 * 
 * @author phillipryan
 */

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class UDPServer implements UDPNetwork{
	
	public static final int DEFAULT_PORT = 7777;
	private DatagramSocket socket = null;
	private DatagramPacket packet;
	List<String> ipAddresses = new ArrayList<String>();

	@Override
	public void socket() {
		try{
            // creating a socket
			socket = new DatagramSocket(DEFAULT_PORT);
        }
		catch(IOException e){
            System.err.println("IOException " + e);
        }
	}
	
	@Override
	public void recv() {
		packet = new DatagramPacket (new byte[1], 1);
        try{
            socket.receive (packet);
            String address = packet.getAddress().toString();
            System.out.println("Received from: " + address + ":" +
                               packet.getPort());
            addAddress(address);
            byte[] outBuffer = new java.util.Date ().toString ().getBytes ();
		    packet.setData (outBuffer);
            packet.setLength (outBuffer.length);
        }
        catch (IOException ie){
            ie.printStackTrace();
        }
	}
	
	public boolean addAddress(String address){
		String addr = address.replace("/", "");
		for(int i =0; i < ipAddresses.size(); i++)
	    {
	        if(addr.contains(ipAddresses.get(i)))
	        {
	            return true;
	        }
	    }
		ipAddresses.add(addr);
		return false;
	}

	@Override
	public void send() {
		try{
            socket.send (packet);
		} 
		catch (IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		socket.close ();
	}

	@Override
	public List<String> getAddresses() {
		return ipAddresses;
	}
	
}
