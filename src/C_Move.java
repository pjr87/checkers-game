
public class C_Move extends Move {
	private Square captured;
	public static final String name = "MOVE";
	
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
		String s = name;
		s += delim + String.valueOf(get_start_pos());
		s += delim + String.valueOf(get_end_pos());
		s +=  delim + String.valueOf(get_captured()) +"\n";
		
		return s;
	}
}
