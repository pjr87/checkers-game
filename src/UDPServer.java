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
	
	public static final int DEFAULT_PORT = 5001;
	private DatagramSocket socket = null;
	private DatagramPacket packet;
	List<String> ipAddresses = new ArrayList<String>();

	@Override
	public void socket() {
		try{
            // creating a socket
			socket = new DatagramSocket(DEFAULT_PORT);
			socket.setBroadcast(true);
        }
		catch(IOException e){
            //System.err.println("IOException " + e);
        }
	}
	
	@Override
	public boolean recv() {
		byte[] receiveData = new byte[255];

		packet = new DatagramPacket(receiveData, receiveData.length);
        try{
        	socket.receive(packet);
            String sentence = new String( packet.getData(), 0,
            		packet.getLength() );
            System.out.println("RECEIVED: " + sentence);
            
            String address = packet.getAddress().toString();
            addAddress(address);
            
            System.out.println("UDPServer Received from: " + address + ":" +
                    packet.getPort() + " " + sentence);
            
            if(sentence.equals("Listen")){
            	return true;
            }
        }
        catch (IOException ie){
            //ie.printStackTrace();
        }
        return false;
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
	public void send(String message) {
		try{
            socket.send (packet);
		} 
		catch (IOException e){
			//e.printStackTrace();
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
