import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Piece extends ImageIcon{

	private static final long serialVersionUID = 6206359718427232597L;
	protected boolean team;
	protected Direction[] directions;
	
	public Piece(boolean team)
	{
		Direction[] directions = {Direction.UpRight, Direction.UpLeft};
		this.directions = directions;
		this.team = team;
		URL url;
		if(team){
//			Image redImage = null;
//			try {
//			    File pathToFile = new File("/images/red-piece.png");
//			    redImage = ImageIO.read(pathToFile);
//			} catch (IOException ex) {
//			    ex.printStackTrace();
//			}
//			this.setImage(redImage);
			this.setImage(Toolkit.getDefaultToolkit().getImage(Piece.class.getResource("/resources/red-piece.png")));
		}
		else{
//			Image whiteImage = null;
//			try {
//			
//			    url = Piece.class.getResource("/resource/black-piece.png");
//                        
//			    whiteImage = ImageIO.read(pathToFile);
//			} catch (IOException ex) {
//			    ex.printStackTrace();
//			}
			this.setImage(Toolkit.getDefaultToolkit().getImage(Piece.class.getResource("/resources/black-piece.png")));
		}
	}

    Direction[] getMoveDirections()
    {
        return this.directions; 
    }

    /*
     * Returns true of the piece is the red piece and false if it's not
     */
	public boolean getTeam() {
		return this.team;
	}

	public void setTeam(boolean team) {
		this.team = team;
		if(team){
			Image redImage = null;
			try {
			    File pathToFile = new File("/images/red-piece.png");
			    redImage = ImageIO.read(pathToFile);
			} catch (IOException ex) {
			    ex.printStackTrace();
			}
			this.setImage(redImage);
		}
		else{
			Image whiteImage = null;
			try {
			    File pathToFile = new File("/images/white-piece.png");
			    whiteImage = ImageIO.read(pathToFile);
			} catch (IOException ex) {
			    ex.printStackTrace();
			}
			this.setImage(whiteImage);
		}
	}	
}
