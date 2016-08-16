
public class Move {
	private Space start;
	private Space end;
	
	public int get_start_pos(){
		return start.get_pos();
	}
	
	public int get_end_pos(){
		return end.get_pos();
	}
	
	public int get_captured(){
		return -1;
	}
	
	public void apply(){
		end.set_piece(start.get_piece);
		start.remove_piece();
		
		Network.sendMove(this);
	}
}
