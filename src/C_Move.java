
public class C_Move extends Move {
	private Space captured;
	
	public C_Move(Space start, Space end, Space captured){
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
