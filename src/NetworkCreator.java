import java.lang.*;
import java.util.ArrayList;
import java.util.List;

interface ConnectionStatus {
	void connectionMade(int status);
}

public class NetworkCreator{
	private UDPNetwork UDPserver;
	private UDPNetwork UDPclient;
	private TCPNetwork TCPserver;
	private TCPNetwork TCPclient;

	private boolean isClient = false;
	private boolean isServer = false;

	public int clientTurn = 0;
	public int serverTurn = 0;

	private List<ConnectionStatus> listeners = new ArrayList<ConnectionStatus>();

	private volatile boolean running = true;

	public void addListener(ConnectionStatus toAdd){
		listeners.add(toAdd);
	}

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
					Thread.sleep(1000);
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
					break;
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
		terminate();
		UDPclient.close();
		UDPserver.close();

		TCPserver.socket("");
		boolean connect = TCPserver.accept();
		if(connect){
			isServer = true;
			//Determine who goes first randomly
			int tmp = (int) ( Math.random() * 2 + 1);
			//int tmp = 2;
			if(tmp == 1){
				clientTurn = 1;
				serverTurn = 2;
			}
			else if(tmp == 2){
				clientTurn = 2;
				serverTurn = 1;
			}

			String turn = Integer.toString(serverTurn);
			TCPserver.send(turn);

			System.out.println("NETWORK TCP Server connected");
			//alert all the listeners that the tcp server has been connected
			//for each listener thats registered: call listener function
			for(ConnectionStatus cs : listeners)
				cs.connectionMade(serverTurn);
		}
	}

	private int StartTCPClient(String ipAddress){
		terminate();
		UDPclient.close();
		UDPserver.close();

		boolean connect = TCPclient.socket(ipAddress);
		if(connect){
			System.out.println("NETWORK TCP Client connected");
			String Serverturn = TCPclient.recv();
			if(Serverturn.equals("1")){
				serverTurn = 1;
				clientTurn = 2;
			}
			else if(Serverturn.equals("2")){
				serverTurn = 2;
				clientTurn = 1;
			}

			isClient = true;
		}

		return clientTurn;
	}

	protected int Connect(String ipAddress){
		UDPclient.send("Listen");

		//Sleep for 1 Second to make sure server is listening
		try {
			Thread.sleep(1000);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

		//Start TCP client
		int turn = StartTCPClient(ipAddress);

		return turn;
	}

	protected void SendMove(String str){
		if(isServer)
			TCPserver.send(str);
		else if(isClient)
			TCPclient.send(str);
	}

	protected String RecvMove(){
		String str = null;
		if(isServer)
			str = TCPserver.recv();
		else if(isClient)
			str = TCPclient.recv();

		return str;
	}

	protected void CloseNetworking(){
		terminate();

		UDPserver.close();
		UDPclient.close();
	}
}