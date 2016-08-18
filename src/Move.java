
public class Move {
	private Square start;
	private Square end;
	
	public Move(Square start, Square end){
		this.start = start;
		this.end = end;
	}
	
	public int get_start_pos(){
		return start.getLabel();
	}
	
	public int get_end_pos(){
		return end.getLabel();
	}
	
	public int get_captured(){
		return -1;
	}
	
	public void apply(){
		end.setPiece(start.getPiece());
		start.removePiece();
		
		Network.sendMove(this);
	}
}
