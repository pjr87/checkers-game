import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;

public class Square extends JLabel {

	private static final long serialVersionUID = -7358340280413951025L;	
	private Piece here = null;

	private Map<Direction, Square> neighbours;

	private int label;
	public Square(int label, Piece here, Map<Direction, Square> neighbours )
	{
		this.setPiece(here);
		this.setLabel(label); 
		this.setNeighbours(neighbours);
		this.setIcon(here);

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
		this.setIcon(here);
	}

	/*
	 * Removes piece from the square by setting it to null
	 */
	public void removePiece()
	{
		this.here = null;
		this.setIcon(null);
	}

	private ArrayList<Square> emptyNeighbours()
	{
		ArrayList<Square> emptyneighbours = new ArrayList<Square>();
		for (Square neighbour : neighbours.values()){
			if(neighbour == null)
			{
				continue;
			}
			if(neighbour.getPiece() == null)
			{
				emptyneighbours.add(neighbour);
			}
		}
		return emptyneighbours;
	}

	/*
	 * Returns all possible moves that can be done 
	 */
	public ArrayList<Move> getMoves()
	{
		if(this.here == null){
			return null;
		}
		ArrayList<Move> moves = new ArrayList<Move>();
		for (Square neighbour : neighbours.values()) {
			if(neighbour == null)
			{
				continue;
			}
			if(neighbour.getPiece() == null )
			{
				if((this.neighbours.get(Direction.UpLeft)!=null) && this.neighbours.get(Direction.UpLeft)==neighbour){
					moves.add(new Move(this, neighbour));
				}
				else if((this.neighbours.get(Direction.UpRight)!=null) && this.neighbours.get(Direction.UpRight)==neighbour){
					moves.add(new Move(this, neighbour));
				}
				else if((this.neighbours.get(Direction.DownLeft)!=null) && this.neighbours.get(Direction.DownLeft)==neighbour){
					if(this.getPiece() instanceof King){
						moves.add(new Move(this, neighbour));
					}
				}
				else if((this.neighbours.get(Direction.DownRight)!=null) && this.neighbours.get(Direction.DownRight)==neighbour){
					if(this.getPiece() instanceof King){
						moves.add(new Move(this, neighbour));
					}
				}
			}
			else if(neighbour.getPiece().getTeam() != this.getPiece().getTeam())
			{
				if((this.neighbours.get(Direction.UpLeft)!=null) && this.neighbours.get(Direction.UpLeft)==neighbour){
					if(neighbour.neighbours.get(Direction.UpLeft)!=null && neighbour.neighbours.get(Direction.UpLeft).getPiece()==null){
						moves.add(new C_Move(this, neighbour.neighbours.get(Direction.UpLeft), neighbour));
					}
				}
				else if((this.neighbours.get(Direction.UpRight)!=null) && this.neighbours.get(Direction.UpRight)==neighbour){
					if(neighbour.neighbours.get(Direction.UpRight)!=null && neighbour.neighbours.get(Direction.UpRight).getPiece()==null){
						moves.add(new C_Move(this, neighbour.neighbours.get(Direction.UpRight), neighbour));
					}
				}
				else if((this.neighbours.get(Direction.DownLeft)!=null) && this.neighbours.get(Direction.DownLeft)==neighbour && this.getPiece() instanceof King){
					if(neighbour.neighbours.get(Direction.DownLeft)!=null && neighbour.neighbours.get(Direction.DownLeft).getPiece()==null ){
						moves.add(new C_Move(this, neighbour.neighbours.get(Direction.DownLeft), neighbour));
					}
				}
				else if((this.neighbours.get(Direction.DownRight)!=null) && this.neighbours.get(Direction.DownRight)==neighbour && this.getPiece() instanceof King){
					if(neighbour.neighbours.get(Direction.DownRight)!=null && neighbour.neighbours.get(Direction.DownRight).getPiece()==null){
						moves.add(new C_Move(this, neighbour.neighbours.get(Direction.DownRight), neighbour));
					}
				}
			}
		}
		return moves;
	}
}
