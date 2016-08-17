import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class GUI extends JFrame{
	
	private static final long serialVersionUID = 3346418586960045067L;
	
	GameScreen gameScreen;
	PlayerSelectionScreen playerSelectionScreen;
	
	public GUI(JLabel[][] spaces){
		gameScreen = new GameScreen(spaces);
		playerSelectionScreen = new PlayerSelectionScreen();
		
		setScreen(Screen.PLAYER_SELECTION_SCREEN);
		setTitle("DraughtZz!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
	}
	
	//refresh the frame
	public void refreshScreen(){
		this.revalidate();
		this.repaint();
	}

	//set the current screen
	public void setScreen(Screen screen){
		Container contain = getContentPane();
        contain.removeAll();
        
        //change to the game screen
		if(screen == Screen.GAME_SCREEN){
	        contain.add(gameScreen);
			setSize(650,650);
		}
		//change to the player selection screen
		else if(screen == Screen.PLAYER_SELECTION_SCREEN){
	        contain.add(playerSelectionScreen);
			setSize(600, 257);
		}
		
		//refreshScreen();
	}
	
	public void updatePlayersList(List<String> players){
		playerSelectionScreen.updatePlayersList(players);
		//refreshScreen();
	}
	
//functions to pass to the Screens
	public void setpBtnDrawAction(ActionListener actionListener) {
		gameScreen.setpBtnDrawAction(actionListener);
	}

	public void setpBtnResignAction(ActionListener actionListener) {
		gameScreen.setpBtnResignAction(actionListener);
	}

	public void setpBtnConnectAction(ActionListener actionListener) {
		playerSelectionScreen.setpBtnConnectAction(actionListener);	
	}

	public void setpBtnNotationAction(ActionListener actionListener) {
		gameScreen.setpBtnNotationAction(actionListener);
	}

	public void setpBtnRefreshAction(ActionListener actionListener) {
		playerSelectionScreen.setpBtnRefreshAction(actionListener);	
	}

	public void setpBtnPlayAction(ActionListener actionListener) {
		playerSelectionScreen.setpBtnPlayAction(actionListener);	
	}
	
	public void setSpaceClickedAction(MouseAdapter mouseA) {
		gameScreen.setAllSpacesMouseListener(mouseA);	
	}

	public String getSelectedIp() {
		return playerSelectionScreen.getSelectedIp();	
	}

	public String getInputtedIp() {
		return playerSelectionScreen.getInputtedIp();
	}
}
