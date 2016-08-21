import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;

public class Board {

	Square[] squares;

	//initialization
	public Board(){
		squares = new Square[32];

		for(int i=0; i<32; i++)
			squares[i] = new Square(i);
		
		int topLNum=4;
		int topRNum=3;
		int bottomLNum=4;
		int bottomRNum = 5;
		boolean isEvenRow=false;
		int counter=0;

		for(int i=0; i<32; i++){

			Map<Direction, Square> tempMap = new HashMap<Direction, Square>();

			//topLeft link
			if((i-5)>=0 && (i%4!=0|| !isEvenRow))
				tempMap.put(Direction.UpLeft, squares[i-topLNum]);
			else
				tempMap.put(Direction.UpLeft, null);

			//topRight link
			if((i-4)>=0  && ((i+1)%4!=0 || isEvenRow))
				tempMap.put(Direction.UpRight, squares[i-topRNum]);
			else
				tempMap.put(Direction.UpRight, null);

			//bottomLeft link
			if((i+4)<32 && (i%4!=0 || !isEvenRow))
				tempMap.put(Direction.DownLeft, squares[i+bottomLNum]);
			else
				tempMap.put(Direction.DownLeft, null);

			//bottomRight link
			if((i+5)<32  && ((i+1)%4!=0 || isEvenRow))
				tempMap.put(Direction.DownRight, squares[i+bottomRNum]);
			else
				tempMap.put(Direction.DownRight, null);

			squares[i].setNeighbours(tempMap);
			
			if(counter==3){
				counter=-1;
				if(topLNum==4){
					topLNum=5;
					topRNum=4;
					bottomLNum=3;
					bottomRNum = 4;
					isEvenRow=true;
				}
				else{
					topLNum=4;
					topRNum=3;
					bottomLNum=4;
					bottomRNum = 5;
					isEvenRow=false;
				}
			}
			counter++;
		}
	}
	
	//returns all the squares (used for linking with the GUI)
	public Square[] getSquares(){
		return squares;
	}
	
	//highlights all the pieces (Squares) that can currently move
	public void showAllValidMoves(boolean i){
		ArrayList<Move> list = new ArrayList<Move>();

		//true if a mandatory jump move exists.
		boolean mandatoryMoveExists = false;
		for (Square square : squares) {
			if(square.getPiece()!=null && square.getPiece().getTeam()==i){
				ArrayList<Move> moves = square.getMoves();
				
				for (Move move : moves) {
					if(move instanceof C_Move){
						//if there is a jump move break out start over only getting jump moves
						mandatoryMoveExists=true;
						break;
					}
					list.add(move);
				}
				if(mandatoryMoveExists)
					break;
			}
		}

		//if a jump move exists: remove all current moves and only get the jump moves
		if(mandatoryMoveExists){
			list.removeAll(list);
			for (Square square : squares) {
				if(square.getPiece()!=null && square.getPiece().getTeam()==i){
					ArrayList<Move> moves = square.getMoves();
					for (Move move : moves) {
						if(move instanceof C_Move){
							list.add(move);
						}
					}
				}
			}
		}
		for (Move move : list) //highlight all valid pieces that can move.
			GUI.highlightSquare(squares[move.get_start_pos()]);
	}
	
	
	public void movePiece(Move m){
		Piece piece = squares[m.get_start_pos()].getPiece();
		squares[m.get_end_pos()].setPiece(piece);
		squares[m.get_start_pos()].setPiece(null);

		if(m.get_captured()>=0)
			squares[m.get_captured()].setPiece(null);
	}
	
	//returns and highlights all the spaces that a piece can move to 
	public ArrayList<Move> getAvailableMoves(Square s){
		boolean mandatoryMoveExists=false;
		ArrayList<Move> list = s.getMoves();
		for (Move move : list) {
			if(move instanceof C_Move){
				mandatoryMoveExists=true;
			}
			GUI.highlightSquare(squares[move.get_end_pos()]);
		}
		if(mandatoryMoveExists){
			for (int x = list.size()-1; x > -1; x--){
				if(!(list.get(x) instanceof C_Move)){
					GUI.unhighlightSquare(squares[list.get(x).get_end_pos()]);
					list.remove(x);
				}
			}
		}
		return list;
	}
	
	//sets up the board for a new game  
	public void setBoard(boolean isRed) {
		for(int i = 10; i<11;i++)
			squares[i].setPiece( new Piece(!isRed) );

		for(int i=21; i<22;i++)
			squares[i].setPiece( new Piece(isRed) );
	}
	
	//returns 1 for red won, 0 for white won, and -1 for no winner yet
	public int isGameOver(boolean turn){ // input is whoever's turn it is to move (red = true, white = false)
	
		boolean redHasPieces=false, whiteHasPieces=false, redPiecesCanMove=false, whitePiecesCanMove=false;
		for (Square square : squares) {
			if(square.getPiece()!=null){
				if(square.getPiece().getTeam()==true){
					redHasPieces=true;
					if(square.getMoves().size()>0)
						redPiecesCanMove=true;
				}
				else{
					whiteHasPieces=true;
					if(square.getMoves().size()>0)
						whitePiecesCanMove=true;
				}
			}
		}
		
		if((!redHasPieces) || (!redPiecesCanMove && turn) )//red has no pieces or can't move - WHITE WON
			return 0;
		else if(!whiteHasPieces || (!whitePiecesCanMove && !turn))//white has no pieces or can't move - RED WON
			return 1;
		
		return -1;
	}
}
