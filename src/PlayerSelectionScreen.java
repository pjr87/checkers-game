import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

public class PlayerSelectionScreen extends JPanel{

	private static final long serialVersionUID = 7893337942915288121L;
	
	//Manual connect to other player panel 
	private JPanel pPnlManualConnect;
	private JScrollPane pSclPnlPlayers;
	private JLabel pLblOpponentIp;
	private JButton pBtnConnect;
	private JLabel pLblYourIp;
	private JTextField pTxtHostAddress;

	//Auto find other players panel
	private JList<Vector<String>> pLstPlayers;
	private JPanel pPnlPlayerList;
	private JButton pBtnPlay;
	private JButton pBtnRefresh;
	
	//helper variables 
	

	private Vector<String> getPlayers(Map<String, String> players){
		Vector<String> vector = null;
		for (String playerName : players.keySet()) {
			vector.add(playerName);
		}
		return vector;
	}
	
	
	//Constructor - GUI Setup
	public PlayerSelectionScreen() {
		
		this.setLayout(null);
		
		//player Screen sub-panels
		createManualConnectPanel();
		createPlayerListPanel();
		
		
		//add panels to screen
		Dimension size;
		Insets insets = this.getInsets();
		
		size = pPnlManualConnect.getPreferredSize();
		pPnlManualConnect.setBounds(5+insets.left, 5+insets.top, size.width+16, size.height+16);
		pPnlManualConnect.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(pPnlManualConnect);
		
		size = pPnlPlayerList.getPreferredSize();
		pPnlPlayerList.setBounds(5+insets.left, 55+insets.top, size.width+16, size.height+16);
		pPnlPlayerList.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(pPnlPlayerList);
	
	}
	private void createPlayerListPanel(){
		
		pPnlPlayerList = new JPanel();
		
		pPnlPlayerList.setLayout(new GridBagLayout());
		GridBagConstraints layout = new GridBagConstraints();
		Vector<String> players = new Vector<String>();
		players.add("hey");
		players.add("te");
		players.add("hey");
		players.add("te");
		players.add("hey");
		players.add("te");
		players.add("hey");
		players.add("te");
		players.add("hey");
		players.add("te");
		players.add("hey");
		
		//instantiate gui objects
		pBtnPlay = new JButton("Play Selected Player");
		pBtnRefresh = new JButton("Refresh Players List");
		pLstPlayers = new JList(players);
		pSclPnlPlayers = new JScrollPane(pLstPlayers);
		
		//jlist options
		pLstPlayers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		pLstPlayers.setLayoutOrientation(JList.VERTICAL);
		
		pLstPlayers.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg) {
				if (pLstPlayers.getSelectedIndex() == -1) {
					//No selection, disable play button.
					pBtnPlay.setEnabled(false);

				} else {
					//Selection, enable the play button.
					pBtnPlay.setEnabled(true);
				}
			}
		});
		
		layout.gridx=1;
		layout.gridy=0;
		pPnlPlayerList.add(pBtnPlay,layout);
		
		layout.gridx=0;
		layout.gridy=0;
		pSclPnlPlayers.setPreferredSize(new Dimension(200, 80));
		pPnlPlayerList.add(pSclPnlPlayers,layout);
		
		layout.gridx=0;
		layout.gridy=1;
		pPnlPlayerList.add(pBtnRefresh,layout);
		
	}
	

	private void createManualConnectPanel(){
		
		//instantiate Gui objects
		GridBagConstraints layout = new GridBagConstraints();
		JLabel padding = new JLabel("  ");
		pPnlManualConnect = new JPanel();
		pTxtHostAddress = new JTextField();
		pBtnConnect = new JButton("Connect");
		pLblOpponentIp = new JLabel("Enter opponents IP address to start a game: ");

		pPnlManualConnect.setLayout(new GridBagLayout());

		//get IP address to display
		String address = "";
		try {  address = Checkers.getIps().get(0);  } catch (SocketException e){}

		pLblYourIp = new JLabel("Your IP address is: "+address);

		//layout settings 
		//layout.weightx=1.0;
		//layout.weighty=1.0;
		layout.fill=GridBagConstraints.HORIZONTAL;
		layout.anchor=GridBagConstraints.WEST;
		
		layout.gridx=0;
		layout.gridy=0;
		pPnlManualConnect.add(pLblOpponentIp,layout);

		layout.gridx=1;
		layout.gridy=0;
		pTxtHostAddress.setPreferredSize(new Dimension(100, 28));
		pPnlManualConnect.add(pTxtHostAddress,layout);

		layout.gridx=2;
		layout.gridy=0;
		pPnlManualConnect.add(padding,layout);

		layout.gridx=3;
		layout.gridy=0;
		pPnlManualConnect.add(pBtnConnect,layout);

		layout.gridx=4;
		layout.gridy=0;
		padding.setText("       ");
		pPnlManualConnect.add(padding,layout);

		layout.gridx=5;
		layout.gridy=0;
		pPnlManualConnect.add(pLblYourIp,layout);

	}

	//
	public void setpBtnPlayAction(ActionListener a) {
		pBtnPlay.addActionListener(a);
	}

	public void setpBtnRefreshAction(ActionListener a) {
		pBtnRefresh.addActionListener(a);
	}


	public void setpBtnConnectAction(ActionListener a) {
		pBtnConnect.addActionListener(a);
	}
	
	public String getSelectedPlayerName(){
		return pLstPlayers.getSelectedValue().toString();
	}
	
	public void updatePlayersList(Map<String,String> newPlayers){
		//players = newPlayers
	}








}
