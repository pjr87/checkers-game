
public class Move {
	private Square start;
	private Square end;
	
	public Move(Square start, Square end){
		this.start = start;
		this.end = end;
	}
	
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
