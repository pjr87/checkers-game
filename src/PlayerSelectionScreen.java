import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.net.SocketException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PlayerSelectionScreen extends JPanel{

	private static final long serialVersionUID = 7893337942915288121L;
	
	//Manual connect to other player panel 
	private JPanel pPnlManualConnect;
	private JScrollPane pSclPnlPlayers;
	private JLabel pLblOpponentIp;
	private JButton pBtnConnect;
	private JLabel pLblYourIp;
	private JLabel pLblVersion;
	private JTextField pTxtHostAddress;

	//Auto find other players panel
	private JPanel pPnlPlayerList;
	private JList<Object[]> pLstPlayers;
	private JButton pBtnPlay;
	private JButton pBtnRefresh;
	
	//helper vars
	private DefaultListModel<String> players;
	private static final String versionText="Version: ";
	private static final String version="2.0";
	
	//Constructor - GUI Setup
	public PlayerSelectionScreen() {
		
		this.setLayout(null);
		players = new DefaultListModel<String>();

		pLblVersion = new JLabel(versionText+version);
		
		//player Screen sub-panels
		createManualConnectPanel();
		createPlayerListPanel();
		
		updatePlayBtn();
		updateConnectBtn();
		
		//add panels to screen
		Dimension size;
		Insets insets = this.getInsets();
		
		size = pPnlManualConnect.getPreferredSize();
		pPnlManualConnect.setBounds(15+insets.left, 10+insets.top, size.width+30, size.height+30);
		pPnlManualConnect.setBorder(new TitledBorder("Manually Connect"));
		this.add(pPnlManualConnect);
		
		size = pPnlPlayerList.getPreferredSize();
		pPnlPlayerList.setBounds(86+insets.left, 80+insets.top, size.width+30, size.height+30);
		pPnlPlayerList.setBorder(new TitledBorder("Select Opponent From List"));
		this.add(pPnlPlayerList);
		
		size = pLblVersion.getPreferredSize();
		pLblVersion.setBounds(5+insets.left, 208+insets.top, size.width, size.height);
		this.add(pLblVersion);
	
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void createPlayerListPanel(){
		
		pPnlPlayerList = new JPanel();
		pPnlPlayerList.setToolTipText("sample text");
		 
		pPnlPlayerList.setLayout(new GridBagLayout());
		GridBagConstraints layout = new GridBagConstraints();
		
		//instantiate gui objects
		pBtnPlay = new JButton("Challenge Selected Player");
		pBtnRefresh = new JButton("     Refresh Players List      ");
		pLstPlayers = new JList(players);
		pSclPnlPlayers = new JScrollPane(pLstPlayers);
		
		//jlist options
		pLstPlayers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		pLstPlayers.setLayoutOrientation(JList.VERTICAL);
		
		pLstPlayers.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg) {
				updatePlayBtn();
			}
		});
		
	//position all elements on panel
		layout.gridx=2;
		layout.gridy=0;
		pPnlPlayerList.add(new JLabel("   "),layout);
		
		layout.gridx=2;
		layout.gridy=1;
		layout.gridheight=1;
		pPnlPlayerList.add(pBtnPlay,layout);
		
		layout.gridx=1;
		layout.gridy=0;
		pPnlPlayerList.add(new JLabel("   "),layout);
		
		layout.gridx=2;
		layout.gridy=3;
		JLabel padding = new JLabel();
		padding.setPreferredSize(new Dimension(10,0));
		padding.setOpaque(true);
		pPnlPlayerList.add(padding,layout);
		
		layout.gridx=2;
		layout.gridy=4;
		pPnlPlayerList.add(pBtnRefresh,layout);
		
		layout.gridx=0;
		layout.gridy=0;
		layout.gridheight=5;
		pSclPnlPlayers.setPreferredSize(new Dimension(200, 102));
		pPnlPlayerList.add(pSclPnlPlayers,layout);
	}
	
	private void createManualConnectPanel(){
		
		//gridbag setup
		GridBagConstraints layout = new GridBagConstraints();
		layout.fill=GridBagConstraints.HORIZONTAL;
		layout.anchor=GridBagConstraints.WEST;
		
		//instantiate Gui objects
		pPnlManualConnect = new JPanel();
		pTxtHostAddress = new JTextField();
		pBtnConnect = new JButton("Challenge");
		pLblOpponentIp = new JLabel("Opponents IP address: ");
		
		//add listener for when text in pTxtHostAddress changes.
		pTxtHostAddress.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) { updateConnectBtn(); }
			
			@Override
			public void insertUpdate(DocumentEvent e) { updateConnectBtn(); }
			
			@Override
			public void changedUpdate(DocumentEvent e) { updateConnectBtn(); }
		});

		pPnlManualConnect.setLayout(new GridBagLayout());

		//get IP address to display
		String address = "";
		try {  address = Checkers.getIps().get(0);  } catch (SocketException e){}

		pLblYourIp = new JLabel("Your IP address is: "+address);

	//position all elements on panel
		layout.gridx=0;
		layout.gridy=0;
		pPnlManualConnect.add(pLblOpponentIp,layout);

		layout.gridx=1;
		layout.gridy=0;
		pTxtHostAddress.setPreferredSize(new Dimension(100, 28));
		pPnlManualConnect.add(pTxtHostAddress,layout);

		layout.gridx=2;
		layout.gridy=0;
		pPnlManualConnect.add(new JLabel("   "),layout);

		layout.gridx=3;
		layout.gridy=0;
		pPnlManualConnect.add(pBtnConnect,layout);

		layout.gridx=4;
		layout.gridy=0;
		pPnlManualConnect.add(new JLabel("   "),layout);

		layout.gridx=5;
		layout.gridy=0;
		pPnlManualConnect.add(pLblYourIp,layout);
	}
	
	//enables and disables the Challenge button
	public void updateConnectBtn(){
		String ipText = pTxtHostAddress.getText();
		if(ipText.matches("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$"))
			pBtnConnect.setEnabled(true);
		else
			pBtnConnect.setEnabled(false);
	}
	
	//enables and disables the "found players" play button 
	public void updatePlayBtn(){
		if (pLstPlayers.getSelectedIndex() == -1)
			pBtnPlay.setEnabled(false);
		else 
			pBtnPlay.setEnabled(true);
	}

	//set action listerners
	public void setpBtnPlayAction(ActionListener a) { pBtnPlay.addActionListener(a); }
	public void setpBtnRefreshAction(ActionListener a) { pBtnRefresh.addActionListener(a); }
	public void setpBtnConnectAction(ActionListener a) { pBtnConnect.addActionListener(a); }

	//returns opponents ip address
	public String getSelectedIp(){ return (String) players.getElementAt(pLstPlayers.getSelectedIndex()); }
	public String getInputtedIp(){ return pTxtHostAddress.getText(); }
	
	//update the found players list
	public void updatePlayersList(List<String> newPlayers){
		int ind = pLstPlayers.getSelectedIndex();
		players.removeAllElements();
		for(int i = 0;i<newPlayers.size();i++){
			players.addElement(newPlayers.get(i));
			pLstPlayers.ensureIndexIsVisible(i);
			pLstPlayers.setSelectedIndex(ind);
		}
		updatePlayBtn();
	}








}
