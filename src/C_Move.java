
public class C_Move extends Move {
	private Space captured;
	
	public int get_captured(){
		return captured.get_pos();
	}
	
	public void apply(){
		captured.remove_piece();
		super.apply();
	}
}
