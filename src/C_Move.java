
public class C_Move extends Move {
	private Square captured;
	
	public void apply(){
		captured.remove_piece();
		super.apply();
	}
}
