
public class Move {
	private Square start;
	private Square end;
	
	public void get_start_pos(){
		//what should this function return?
	}
	
	public void get_end_pos(){
		//see above
	}
	
	public void apply(){
		end.set_piece(start.get_piece);
		stare.remove_piece();
		
		//Networking has to happen. How are we doing that?
	}
}
