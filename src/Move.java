
public class Move {
	protected Square start;
	protected Square end;
	public static final String name = "MOVE";
	public static final String startRep = "Start:";
	public static final String endRep = "End:";
	public static final String capturedRep = "Captured:";
	public static final String delim = " ";
	public static final String lineDelim = "\n";
	
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
	
	public void apply(NetworkCreator send){
		end.setPiece(start.getPiece());
		start.removePiece();
		
		send.SendMove(make_send_string());
	}
	
	public String make_send_string(){
		String s = name +delim;
		s +=  String.valueOf(get_start_pos());
		s += delim + String.valueOf(get_end_pos());
		s += delim + "null\n";
		
		return s;
	}
}
