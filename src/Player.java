import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Player implements ConnectionStatus{
	
	NetworkCreator network;
	
	public Player(NetworkCreator network) {
		this.network = network;
	}

	@Override
	public void connectionMade(int status) {
		//Do something here, this only happens when TCP connection is made
		System.out.println("Server connection recevied from player " + status);
		
		switch(status){
		case 0:
			System.out.println("Player2: Failed to connect");
			break;
		case 1:
			System.out.println("Player2: Player 2's turn");
			try {
				//Runs for 1 seconds
				Thread.sleep(1000);
				//This is used to represent the action of a player picking a game
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.network.SendMove("MOVE 7 10 null");
			break;
		case 2:
			System.out.println("Player2: Player 1's turn");
			String str = this.network.RecvMove();
			System.out.println("Player2: Receieved " + str);
			break;
		}
	}
	
	public static void main(String[] args){
		//Start Player1
		Player1thread();
		
		//Start Player2
		//Player2thread();
		
		//Start Player1
		Player1thread();
				
		//Start Player2
		//Player2thread();
	}
	
	public static void Player1thread(){
		System.out.println("Player1: Open");
		NetworkCreator network = new NetworkCreator();
		Player player1 = new Player(network);
		
		network.addListener(player1);
		
		System.out.println("Player1: StartNetworking");
		network.StartNetworking();
		
		/*try {
			//Runs for 3 seconds
			Thread.sleep(1000);
			//This is used to represent the action of a player picking a game
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List<String> players = network.getAvailablePlayers();
		System.out.println(Arrays.toString(players.toArray()));*/
		
		int n = 3;
		List<String> players = null;
		
		while(n == 3){
		
			players = network.getAvailablePlayers();
			System.out.println(Arrays.toString(players.toArray()));
			
			Scanner reader = new Scanner(System.in);  // Reading from System.in
			System.out.println("Enter a number: ");
			n = reader.nextInt();
		}
		
		System.out.println("Connect " + n);
		
		int turn = network.Connect(players.get(n));
		
		System.out.println("turn " + turn);
		
		switch(turn){
		case 0:
			System.out.println("Player1: Failed to connect");
			break;
		case 1:
			System.out.println("Player1: Player 1's turn");
			try {
				//Runs for 1 seconds
				Thread.sleep(1000);
				//This is used to represent the action of a player picking a game
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			network.SendMove("MOVE 30 25 null");
			System.out.println("Player1 sent: MOVE 30 25 null");
			break;
		case 2:
			System.out.println("Player1: Player 2's turn");
			String str = network.RecvMove();
			System.out.println("Player1: Receieved " + str);
			break;
		}
		
		network.CloseNetworking();
		
		try {
			//Runs for 1 seconds
			Thread.sleep(2000);
			//This is used to represent the action of a player picking a game
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void Player2thread(){
		NetworkCreator network = new NetworkCreator();
		Player player2 = new Player(network);
		
		network.addListener(player2);
		
		network.StartNetworking();
		
		try {
			//Runs for 3 seconds
			Thread.sleep(10000);
			//This is used to represent the action of a player picking a game
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		/*
		int n = 3;
		List<String> players = null;
		
		while(n == 3){
		
			players = network.getAvailablePlayers();
			System.out.println(Arrays.toString(players.toArray()));
			
			Scanner reader = new Scanner(System.in);  // Reading from System.in
			System.out.println("Enter a number: ");
			n = reader.nextInt();
		}
		
		int turn = network.Connect(players.get(n));
		//int turn = network.Connect(players.get(0));
		
		System.out.println("turn " + turn);*/
		
		List<String> players = network.getAvailablePlayers();
		System.out.println(Arrays.toString(players.toArray()));
		
		network.CloseNetworking();
	}
}