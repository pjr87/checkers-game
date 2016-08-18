public class King extends Piece
{
	private static final long serialVersionUID = 5808630875414058730L;

	public King(boolean team)
    {
    	super(team);
    	Direction[] directions = {Direction.UpRight, Direction.UpLeft, Direction.DownLeft, Direction.DownRight};
    	this.directions = directions;
    }
}

