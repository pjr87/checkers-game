import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class King extends Piece
{
	private static final long serialVersionUID = 5808630875414058730L;

	public King(boolean team)
    {
    	super(team);
    	Direction[] directions = {Direction.UpRight, Direction.UpLeft, Direction.DownLeft, Direction.DownRight};
    	this.directions = directions;
    	setTeam(team);
    }
	
	
	public void setTeam(boolean team) {
		this.team = team;
		if(team){
//			Image redImage = null;
//			try {
//			    File pathToFile = new File("/images/red-piece-king.png");
//			    redImage = ImageIO.read(pathToFile);
//			} catch (IOException ex) {
//			    ex.printStackTrace();
//			}
//			this.setImage(redImage);
			this.setImage(Toolkit.getDefaultToolkit().getImage(Piece.class.getResource("/resources/red-piece-king.png")));

		}
		else{
//			Image whiteImage = null;
//			try {
//			    File pathToFile = new File("/images/black-piece-king.png");
//			    whiteImage = ImageIO.read(pathToFile);
//			} catch (IOException ex) {
//			    ex.printStackTrace();
//			}
//			this.setImage(whiteImage);
			this.setImage(Toolkit.getDefaultToolkit().getImage(Piece.class.getResource("/resources/blak-piece-king.png")));

		}
	}	
}

