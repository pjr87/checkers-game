
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
	
	@Override
	public void apply(){
		end.setPiece(start.getPiece());
		start.removePiece();
		captured.removePiece();
	}
	
	@Override
	public void sendMove(NetworkCreator send, boolean b){
		send.SendMove(make_send_string(b));
	}
	
	@Override
	public String make_send_string(boolean b){
		String s = name;
		s += delim + String.valueOf(get_start_pos());
		s += delim + String.valueOf(get_end_pos());
		s +=  delim + String.valueOf(get_captured());
		if(b)
			s +=  delim + "doubleJump\n";
		else
			s +=  "\n";
		
		return s;
	}
}
