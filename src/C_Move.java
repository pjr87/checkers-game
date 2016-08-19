
public class C_Move extends Move {
	private Square captured;
	public static final String name = "C_MOVE";
	
	public C_Move(Square start, Square end, Square captured){
		super(start, end);
		this.captured = captured;
	}
	
	public int get_captured(){
		return captured.getLabel();
	}
	
	public void apply(NetworkCreator send){
		captured.removePiece();
		super.apply(send);
	}
	
	public String make_send_string(){
		String s = name + "\n";
		s += startRep + delim + String.valueOf(get_start_pos()) + "\n";
		s += endRep + delim + String.valueOf(get_end_pos()) + "\n";
		s += capturedRep + delim + String.valueOf(get_captured()) + "\n";
		
		return s;
	}
}
