import javax.swing.ImageIcon;

public class Piece extends ImageIcon{

	private static final long serialVersionUID = 6206359718427232597L;
	private boolean team;
	protected Direction[] directions;
	
	public Piece(boolean team)
	{
		Direction[] directions = {Direction.UpRight, Direction.UpLeft};
		this.directions = directions;
		this.team = team;
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
	}	
}
