import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Random;

import javax.swing.JLabel;

public class Checkers{

	private GUI gui;
	private NetworkCreator network;
	private Board board;
	private boolean isRed;
	private ArrayList<Move> currentMoves;
	//private String username;
	//private Map<String,String> foundPlayers = new HashMap<String,String>();

	private Checkers(){

		network = new NetworkCreator(); 
		network.StartNetworking();
		//username = JOptionPane.showInputDialog(null, "Please enter a unique username!");
		board = new Board();

		gui = new GUI(board.getSquares());
		gui.updatePlayersList( network.getAvailablePlayers() );

		setButtonActions();
	}

	public static void main(String[] args) {
		new Checkers();
	}
	
	
	private int chooseWhoGoesFirst(){
		Random random = new Random();
		return random.nextInt(2);
	}
	
	//implements all the GUI buttons and the actions for when squares are clicked
	private void setButtonActions(){

		//Offer Draw Button
		gui.setpBtnDrawAction(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Offer Draw.");
			}
		});

		//Resign Button
		gui.setpBtnResignAction(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Resign.");
				gui.setScreen(Screen.PLAYER_SELECTION_SCREEN);
			}
		});

		//Notation Button
		gui.setpBtnNotationAction(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("View Notation.");
			}
		});

		//Manual Connect button
		gui.setpBtnConnectAction(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Manual connect to host.");
				challengePlayer(gui.getInputtedIp());
			}
		});

		//play button
		gui.setpBtnPlayAction(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Play selected player.");
				challengePlayer(gui.getSelectedIp());
			}
		});

		//refresh players found list button
		gui.setpBtnRefreshAction(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Refresh players found list.");
				gui.updatePlayersList( network.getAvailablePlayers() );
			}
		});

		//is called when a square is clicked
		gui.setSquareClickedAction(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Square square = null;
				//find out which square was clicked
				for (int i = 0; i < 32; i++){
					if (e.getSource() == board.getSquares()[i]) {
						System.out.println("Square: " + i + " was clicked");
						square= board.getSquares()[i];
					}
				}
				
				//if we are selected a piece we can move
				if(square.getBackground() == GUI.clrEnabledGreen && square.getPiece()!=null){
					currentMoves = board.getAvailableMoves(square);
				}

				//if we selected a space to move a piece to
				else if(square.getBackground() == GUI.clrEnabledGreen && square.getPiece()==null){
					for (Move move : currentMoves) {
						if(move.get_end_pos()==square.getLabel()){
							move.apply(network);
							//board.movePiece(move);
							break;
						}
					}
				}
				
			}
		});
	}
	
	//connects to opponent and if connected successfully it will begin a game
	public void challengePlayer(String player){
		//System.out.println( chooseWhoGoesFirst() );
		//if(network.Connect( player )){

			isRed=true;
			startGame();
		//}
	}

	public void startGame(){
		//sets the board
		board.setBoard(isRed);
		
		//changes the screen
		gui.setScreen(Screen.GAME_SCREEN);
		
		//refreshes the GUI to display the changes
		gui.refreshScreen();
		
		board.showAllValidMoves(isRed);
	}

	//will be replaced by method from NetworkCreator when implemented (it returns our ip address)
	@Deprecated
	public static ArrayList<String> getIps() throws SocketException{
		ArrayList<String> arrayList = new ArrayList<String>();
		@SuppressWarnings("rawtypes")
		Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
		while (interfaces.hasMoreElements()) {
			NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();
			if(networkInterface.isLoopback() || !networkInterface.isUp())
				continue;

			for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
				String a = interfaceAddress.getAddress().getHostAddress();
				if(!a.contains(":"))
					arrayList.add(a);
				System.out.println(a);
			}
		}
		return arrayList;
	}
}
