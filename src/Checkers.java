import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Checkers {

	private static Checkers checkers;
	private GUI gui;
	private String username;
	private NetworkCreator network;
	private Map<String,String> foundPlayers = new HashMap<String,String>();

	private Checkers(){
		username = JOptionPane.showInputDialog(null, "Please enter a unique username!");
		gui = new GUI();
		network = new NetworkCreator(); 
		network.StartNetworking();
		setButtonActions();
	}
	private void setButtonActions(){
		PlayerSelectionScreen pScreen = (PlayerSelectionScreen) gui.getPlayerSelectionScreen();

		pScreen.setpBtnConnectAction(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Manual connect to host.");
			}
		});

		pScreen.setpBtnPlayAction(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Play selected player.");
				String playerIp = foundPlayers.get( pScreen.getSelectedPlayerName() );
				
			}
		});

		pScreen.setpBtnRefreshAction(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Refresh players found list.");
			}
		});
	}

	public static void main(String[] args) {
		checkers = new Checkers();
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
