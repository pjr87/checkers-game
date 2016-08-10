public class Piece {
	private boolean team;

    Direction[] get_move_directions()
    {
        Direction[] directions = {Direction.UpRight, Direction.UpLeft};
        return directions; 
    }

	public boolean isTeam() {
		return team;
	}

	public void setTeam(boolean team) {
		this.team = team;
	}	
}
