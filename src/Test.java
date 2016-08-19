import java.util.Arrays;
import java.util.List;

public class Test {
	
	public static void main(String[] args){
		//Start Player1
		Player1thread();
		
		//Start Player2
		Player2thread();
	}
	
	public static void Player1thread(){
		NetworkCreator network = new NetworkCreator();
		network.StartNetworking();
		
		try {
			//Runs for 3 seconds
			Thread.sleep(3000);
			//This is used to represent the action of a player picking a game
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		List<String> players = network.getAvailablePlayers();
		System.out.println(Arrays.toString(players.toArray()));
		
		network.Connect(players.get(0)); //TODO
		
		network.CloseNetworking();
	}
	
	public static void Player2thread(){
		NetworkCreator network = new NetworkCreator();
		network.StartNetworking();
		
		try {
			//Runs for 3 seconds
			Thread.sleep(3000);
			//This is used to represent the action of a player picking a game
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//List<String> players = network.getAvailablePlayers();
		//System.out.println(Arrays.toString(players.toArray()));
		
		//network.CloseNetworking();
	}

}
