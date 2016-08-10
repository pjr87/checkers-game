import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JFrame{
	private static final long serialVersionUID = 3346418586960045067L;
	
	GameScreen gameScreen;
	PlayerSelectionScreen playerSelectionScreen;
	
	public GUI(){
		gameScreen = new GameScreen();
		playerSelectionScreen = new PlayerSelectionScreen();
		this.add(playerSelectionScreen);
		setTitle("Chekrz!");
		setSize(732, 305); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(true);
	}
	public void refreshScreen(){
		this.revalidate();
	}
	
	public void linkSpaces(Space[][] spaces){
		//add spaces to gameScreen
	}
	public JPanel getPlayerSelectionScreen(){
		return playerSelectionScreen;
	}
	public void updatePlayersList(){
		playerSelectionScreen.updatePlayersList();
	}
}
