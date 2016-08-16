import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
	//private String username;
	//private Map<String,String> foundPlayers = new HashMap<String,String>();

	private Checkers(){
		
		network = new NetworkCreator(); 
		network.StartNetworking();
		//username = JOptionPane.showInputDialog(null, "Please enter a unique username!");
		gui = new GUI(new JLabel[8][8]);
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
				System.out.println(chooseWhoGoesFirst());
				network.Connect(  gui.getInputtedIp()  );
				gui.setScreen(Screen.GAME_SCREEN);
			}
		});
		
		//play button
		gui.setpBtnPlayAction(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Play selected player.");
				System.out.println( chooseWhoGoesFirst() );
				network.Connect(  gui.getSelectedIp()  );
				gui.setScreen(Screen.GAME_SCREEN);
			}
		});

		//refresh players found list
		gui.setpBtnRefreshAction(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Refresh players found list.");
				gui.updatePlayersList( network.getAvailablePlayers() );
			}
		});
		
		gui.setSpaceClickedAction(new MouseAdapter() {
			@Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Yay you clicked me");
                //if space is highlighted showing that it can move 
                	//call function to highlight all the spaces that the piece can move to 
                //else if space is empty but highlighted 
                	//call function to move the previously selected piece to the space
                //else if space doesn't have a piece on it or isn't highlighted 
                	//do nothing. 
            }
		});
	}
	
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
