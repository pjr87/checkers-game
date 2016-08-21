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

import javax.swing.JOptionPane;

public class Checkers implements ConnectionStatus{

	private GUI gui;
	private NetworkCreator network;
	private Board board;
	private boolean isRed;
	boolean turn;

	private ArrayList<Move> currentMoves;
	//private String username;

	@Override
	public void connectionMade(int status) {
		//Do something here, this only happens when TCP connection is made
		System.out.println("Server connection recevied from player " + status);

		switch(status){
		case 1:
			System.out.println("Player2: Player 2's turn");
			isRed=true;
			startGame();
			break;
		case 2:
			System.out.println("Player2: Player 1's turn");
			isRed=false;
			startGame();
			startRecv();
			break;
		default:
			System.out.println("Player2: Failed to connect");
			break;
		}
	
	}

	private Checkers(){

		network = new NetworkCreator(); 
		//Player player = new Player(network);
		network.addListener(this);
		network.StartNetworking();

		board = new Board();

		gui = new GUI(board.getSquares());
		updatePlayersList.start();

		setButtonActions();
	}

	public static void main(String[] args) {
		new Checkers();
	}

	@Deprecated
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
				network.SendMove("DRAW");
				startRecv();
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
					gui.deselectAllsquares();
					currentMoves = board.getAvailableMoves(square);
				}

				//if we selected a space to move a piece to
				else if(square.getBackground() == GUI.clrEnabledGreen && square.getPiece()==null){
					ArrayList<Move> nextMoves = null;
					for (Move move : currentMoves) {
						if(move.get_end_pos()==square.getLabel()){
							turn=false;
							gui.deselectAllsquares();

							//if its a jump move
							if(move instanceof C_Move){
								((C_Move) move).apply();//just Applies move to local board
								checkForKing(move.end);
								nextMoves = board.getAvailableMoves(move.end);

								boolean doubleJump=false;

								for (Move nextMove : nextMoves) {
									if(nextMove instanceof C_Move)
										doubleJump=true;
								}

								//send the move to opponent
								((C_Move) move).sendMove(network,doubleJump);


								//if its the last jump move
								if(!doubleJump){
									gui.enableDraw(false);
									gui.deselectAllsquares();
									startRecv();
								}
							}
							else{//if a normal move
								move.apply(network);//apply and send move
								gui.enableDraw(false);
								checkForKing(move.end);
								startRecv();
							}

							gui.refreshScreen();
							break;
						}
					}
					currentMoves = nextMoves;
				}
				else if(turn){
					gui.deselectAllsquares();
					board.showAllValidMoves(isRed);
				}

			}

		});
	}
	private void startRecv(){
		Thread recvThread = new Thread () {
			public void run () {
				receivedFromNetwork(network.RecvMove());
			}
		};
		recvThread.start();
	}

	Thread updatePlayersList = new Thread () {
		public void run () {
			while(true){
				gui.updatePlayersList( network.getAvailablePlayers() );
				try { Thread.sleep(2000); } catch (InterruptedException e) { }
			}
		}
	};

	public void checkForKing(Square s){
		if(s.getLabel()< 4)
			s.setPiece(new King(isRed));
	}

	public void receivedFromNetwork(String data){
		int gameOver=-1;
		if(data == null){
			exitToPlayerSelectionScreen();
			JOptionPane.showMessageDialog(null, "Opponent Disconnected!");
		}
		else if(data.split(" ")[0].equals("MOVE")){
			Move move= makeMove(data);
			board.movePiece(move);
			
			//check if there is winner
			gameOver = board.isGameOver(isRed);
			board.showAllValidMoves(isRed);
			gui.enableDraw(true);
			turn=true;
		}
		else if(data.split(" ")[0].equals("C_MOVE")){
			Move move= makeMove(data);
			board.movePiece(move);
			if(data.split(" ").length==5){//it is a double jump
				startRecv();
			}
			else{	
				//check if there is winner			
				gameOver = board.isGameOver(isRed);
				board.showAllValidMoves(isRed);
				gui.enableDraw(true);
				turn=true;
			}
		}
		else if(data.split(" ")[0].equals("IS_DRAW")){
			gui.setScreen(Screen.GAME_SCREEN);
			exitToPlayerSelectionScreen();
		}
		else if(data.split(" ")[0].equals("DRAW")){
			int dialogResult = JOptionPane.showConfirmDialog(null, "Your opponent is offering a draw.");
			if(dialogResult == JOptionPane.YES_OPTION){
				network.SendMove("IS_DRAW");
				exitToPlayerSelectionScreen();
			}
			else
				startRecv();
		}
		else if(data.split(" ")[0].equals("GAMEOVER")){
			//if(data.split(" ")[1].equals("1") )
			gui.displayWinner(Integer.parseInt(data.split(" ")[1]));
			exitToPlayerSelectionScreen();
		}
		else
			startRecv();
		
		if(gameOver>-1){
			network.SendMove("GAMEOVER "+gameOver);
			gui.displayWinner(gameOver);
			exitToPlayerSelectionScreen();
		}
	}
	public void exitToPlayerSelectionScreen(){
		gui.setScreen(Screen.PLAYER_SELECTION_SCREEN);
		network.CloseNetworking();
		//network.StartNetworking();
		gui.refreshScreen();
	}

	//connects to opponent and if connected successfully it will begin a game
	public void challengePlayer(String player){
		//System.out.println( chooseWhoGoesFirst() );

		int t;

		if((t = network.Connect( player ))>0){
			if(t==1)
				isRed=true;
			else
				isRed=false;

			startGame();
		}
	}

	public void startGame(){
		//sets the board
		board.setBoard(isRed);

		//changes the screen
		gui.setScreen(Screen.GAME_SCREEN);

		//refreshes the GUI to display the changes

		if(isRed){
			turn=true;
			gui.enableDraw(true);
			board.showAllValidMoves(isRed);
		}
		else{
			startRecv();
			gui.enableDraw(false);
			turn=false;
		}

		gui.refreshScreen();
	}

	public Move makeMove(String moveStr){
		String values[] = moveStr.split(Move.delim);
		if(values.length>3){
			int startID = 31-Integer.parseInt(values[1]);
			int endID = 31-Integer.parseInt(values[2]);

			if(values[3].equals("null"))
				return new Move(board.getSquares()[startID], board.getSquares()[endID]);
			else 
				return new C_Move(board.getSquares()[startID], board.getSquares()[endID], board.getSquares()[31-Integer.parseInt(values[3])]);
		}
		return null;
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
