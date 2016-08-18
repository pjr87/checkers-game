import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;

public class Square extends JLabel {
	
	private static final long serialVersionUID = -7358340280413951025L;	
	private Piece here = null;
	
	@SuppressWarnings("unused")
	private Map<Direction, Square> neighbours;
	
	private int label;
	public Square(int label, Piece here, Map<Direction, Square> neighbours )
	{
		this.setPiece(here);
		this.setLabel(label); 
		this.setNeighbours(neighbours);
		
	}
	public Square(int label)
	{
		this.setPiece(null);
		this.setLabel(label);
		Map<Direction, Square> neighbours = new HashMap<Direction, Square>();
		neighbours.put(Direction.UpLeft, null);
		neighbours.put(Direction.UpRight, null);
		neighbours.put(Direction.DownLeft, null);
		neighbours.put(Direction.DownRight, null);
		this.setNeighbours(neighbours);
	}
	
	public void setNeighbours(Map<Direction, Square> neighbours)
	{
		this.neighbours = neighbours;
	}
	public int getLabel() {
		return label;
	}
	public void setLabel(int label) {
		this.label = label;
	}
	
	public Piece getPiece() {
		return here;
	}
	/**
	 * Returns the piece that is currently occupying the block
	 * @param here
	 */
	public void setPiece(Piece here) {
		this.here = here;
	}
	
	/*
	 * Removes piece from the square by setting it to null
	 */
	public void removePiece()
	{
		this.here = null;
	}
}
