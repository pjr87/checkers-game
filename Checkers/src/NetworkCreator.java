import java.lang.*;
import java.util.List;

public class NetworkCreator{
	private UDPNetwork UDPserver;
	private UDPNetwork UDPclient;
	private TCPNetwork TCPserver;
	private TCPNetwork TCPclient;
	
	private volatile boolean running = true;
	
	public NetworkCreator(){
		UDPserver = new UDPServer();
		UDPclient = new UDPClient();
		
		TCPserver = new TCPServer();
		TCPclient = new TCPClient();
	}
	
	protected void StartNetworking(){
		//Start Server
		UDPserver.socket();
		
		//Start client
		UDPclient.socket();
		
		//Client send thread
		sendThread.start();
			
		//Server recv
		recvThread.start();
	}
	
	Thread sendThread = new Thread (){
		public void run () {
			while(running){
				try {
					UDPclient.send();
					Thread.sleep(10000);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};
	
	Thread recvThread = new Thread () {
		public void run () {
			while(running){
				UDPserver.recv();
			}
		}
	};
	
	public void terminate() {
        running = false;
    }
	
	protected List<String> getAvailablePlayers(){
		return UDPserver.getAddresses();
	}
	
	protected void Connect(String ipAddress){
		//Player 1 chooses a game, sends IP info to P2
			//UDP message sent P1 -> P2
			//P1 starts TCP server
				//Timeout after 2 min
			//P2 takes IP info from UDP and starts TCP client
			//P2 connects to P1
		//Interrupts StartNetworking Thread to try and connect
		//Throw error if failure, Client should handle error and restart networking
		
		//Players enter state based on sending and receiving moves
	}
	
	protected void SendMove(){
		//When player is ready to send
			//Sends message, waits for response, 5 second delay
			//Sends again until response is received
				//If fail 5 times then throw error
	}
	
	protected void RecvMove(){
		//When player is ready to receive
			//Player enters loop till message is received
			//Sends back size of message received
	}
	
	protected void CloseNetworking(){
		terminate();
		
		UDPserver.close();
		UDPclient.close();
	}
}
