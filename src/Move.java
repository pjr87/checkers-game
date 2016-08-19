
public class Move {
	private Square start;
	private Square end;
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
	
	public void apply(Network send){
		end.setPiece(start.getPiece());
		start.removePiece();
		
		send.SendMove(make_send_string());
	}
	
	public String make_send_string(){
		String s = name + lineDelim;
		s += startRep + delim + String.valueOf(get_start_pos()) + lineDelim;
		s += endRep + delim + String.valueOf(get_end_pos()) + lineDelim;
		s += capturedRep + delim + "null" + lineDelim;
		
		return s;
	}
	
	public static Move makeMove(String moveStr){
		String lines[] = moveStr.split(Move.lineDelim);
		if (lines.length < 4){
			//TODO report error
		}
		
		int startID = 0;
		int endID = 0;
		
		{ // parse the second line for the id of the start square
			String fields[] = lines[1].split(Move.delim);	
			if (fields.length < 2){
				//TODO report error
				return null;
			}
		
			if (fields[0] == Move.startRep){
				startID = Integer.parseInt(fields[1]);
			}
			else {
				//TODO report error
				return null;
			}
		}
		
		{ // parse the third line for the id of the end square
			String fields[] = lines[2].split(Move.delim);
			if (fields.length < 2){
				//TODO report error
				return null;
			}
			
			if (fields[0] == Move.endRep){
				endID = Integer.parseInt(fields[1]);
			}
			else {
				//TODO report error
				return null;
			}
		}
		
		if (lines[0] == Move.name){ // check if the move was a regular move...		
			return new Move(get_square(startID), get_square(endID)); // TODO Make a Move and return it
		}
		else if (lines[0] == C_Move.name){ // ...or a capturing move
			int captureID = 0;
			
			{ // parse the fourth line for the id of the captured square
				String fields[] = lines[3].split(Move.delim);
				if (fields.length < 2){
					//TODO report error
					return null;
				}
				
				if (fields[0] == Move.capturedRep){
					endID = Integer.parseInt(fields[1]);
				}
				else {
					//TODO report error
					return null;
				}
			}
			
			return new C_Move(get_square(startID), get_square(endID), get_square(captureID)); // TODO Make a C_Move and return it
		}
		else {
			//TODO report error
			return null;
		}
	}
}
