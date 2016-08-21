import java.awt.Color;
import java.awt.Container;
import java.awt.DisplayMode;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class GUI extends JFrame{
	
	private static final long serialVersionUID = 3346418586960045067L;
	
	final static Color clrDisabledGreen = new Color(160, 255, 170);
	final static Color clrEnabledGreen = new Color(0, 255, 127);
	final static Color clrOffTiles = new Color(238, 238, 224);
	final static Color clrDisabledBorders = new Color(205, 205, 193);
	final static Color clrEnabledBorders = new Color(139, 136, 120);
	
	GameScreen gameScreen;
	PlayerSelectionScreen playerSelectionScreen;
	
	public GUI(JLabel[] j){
		gameScreen = new GameScreen(j);
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
			setSize(800,800);
		}
		//change to the player selection screen
		else if(screen == Screen.PLAYER_SELECTION_SCREEN){
	        contain.add(playerSelectionScreen);
			setSize(630, 257);
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
	
	public void setSquareClickedAction(MouseAdapter mouseA) {
		gameScreen.setAllSquaresMouseListener(mouseA);	
	}

	public String getSelectedIp() {
		return playerSelectionScreen.getSelectedIp();	
	}

	public String getInputtedIp() {
		return playerSelectionScreen.getInputtedIp();
	}
	public void deselectAllsquares(){
		gameScreen.deselectAllSquares();
		refreshScreen();
	}
	
	public static void unhighlightSquare(JLabel s){
		s.setBackground(GUI.clrDisabledGreen);
		s.setBorder(BorderFactory.createLineBorder(GUI.clrDisabledBorders));
	}
	public static void highlightSquare(JLabel s){
		s.setBackground(GUI.clrEnabledGreen);
		s.setBorder(BorderFactory.createLineBorder(GUI.clrEnabledBorders));
	}
	public void enableDrawAndResign(boolean b){
		gameScreen.setBtnDrawAndResignEnabled(b);
	}
	
	public void displayWinner(int winner){
		//winner values: 0=white won, 1=red won, 2=Tie 	
		String strWinner = "Something bad happened... winner could not be found.";
		switch(winner){
			case 0:
				strWinner="Black Won!";
				break;
			case 1:
				strWinner="Red Won!";
				break;
			case 2:
				strWinner="It's a Tie!";
				break;
			default:
				break;
		}
		JOptionPane.showMessageDialog(null, strWinner);
	}
	
}
