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
					UDPclient.send("");
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
				boolean open = UDPserver.recv();
				if(open){
					StartTCPServer();
				}
			}
		}
	};
	
	public void terminate() {
        running = false;
    }
	
	protected List<String> getAvailablePlayers(){
		return UDPserver.getAddresses();
	}
	
	private void StartTCPServer(){
		/*terminate();
		UDPclient.close();
		UDPserver.close();*/
		
		TCPserver.socket("");
		boolean connect = TCPserver.accept();
		if(connect)
			System.out.println("Server connected");
	}
	
	private boolean StartTCPClient(String ipAddress){
		/*terminate();
		UDPclient.close();
		UDPserver.close();*/
		
		boolean connect = TCPclient.socket(ipAddress);
		if(connect){
			System.out.println("Client connected");
			return true;
		}
		return false;
	}
	
	protected boolean Connect(String ipAddress){
		
		System.out.println("Sending Listen to connect");
		
		UDPclient.send("Listen");
		
		System.out.println("Attempting to connect to " + ipAddress);
		
		//Sleep for 1 Second to make sure server is listening
		try {
		    Thread.sleep(1000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		//Start TCP client
		boolean connected = StartTCPClient(ipAddress);
		
		return connected;
		
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
