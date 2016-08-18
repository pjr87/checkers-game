
public class C_Move extends Move {
	private Square captured;
	
	public C_Move(Square start, Square end, Square captured){
		super(start, end);
		this.captured = captured;
	}
	
	public int get_captured(){
		return captured.get_pos();
	}
	
	public void apply(){
		captured.remove_piece();
		super.apply();
	}
}
