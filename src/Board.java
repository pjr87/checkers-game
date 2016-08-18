import java.util.ArrayList;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.sun.jmx.snmp.SnmpPduPacket;
import com.sun.xml.internal.ws.api.config.management.policy.ManagedServiceAssertion.NestedParameters;

public class Board {

	Square[] squares;
	
	public Board(){
		squares = new Square[32];
		
		for(int i=0; i<32; i++)
			squares[i] = new Square(i);
		
		for(int i=0; i<32; i++){
			//topleft link
			if(i-5>=0 && i%4!=0)
				squares[i].setNeighbor(Direction.UpLeft,squares[i-5]);
			else
				squares[i].setNeighbor(Direction.UpLeft,null);
			
			//topright link
			if(i-4>=0  && i+1%4!=0)
				squares[i].setNeighbor(Direction.UpRight, squares[i-4]);
			else
				squares[i].setNeighbor(Direction.UpRight, null);
			
			//bottom left link
			if(i+4<32  && i%4!=0)
				squares[i].setNeighbor(Direction.DownLeft, squares[i+4]);
			else
				squares[i].setNeighbor(Direction.DownLeft, null);
			
			//bottomright link
			if(i+5<32  && i+1%4!=0)
				squares[i].setNeighbor(Direction.DownRight, squares[i+5]);
			else
				squares[i].setNeighbor(Direction.DownRight, null);
		}
	}
	
	public Squares[] getSquares(){
		return squares;
	}
	
	public ArrayList<Move> getValidMoves(boolean i){
		ArrayList<Move> list = new ArrayList<Move>();
		boolean mandatoryMoveExists = false;
		for (Square square : squares) {
			if(square.getPiece()!=null && square.getPiece().getTeam()==i){
				Move[] moves = square.getMoves();
				for (Move move : moves) {
					if(move instanceof C_Move || !mandatoryMoveExists)
						mandatoryMoveExists=true;
					list.add(move);
				}
			}
		}
		if(mandatoryMoveExists){
			for (int x = list.size()-1; x > -1; x--){
				if(!(list.get(x) instanceof C_Move))
					list.remove(x);
			}
		}
	}
	
	public void movePiece(Move m){
		Piece piece = squares[m.get_start_pos()].getPiece();
		squares[m.get_end_pos()].setPiece(piece);
		squares[m.get_start_pos()].setPiece(null);
		
		if(m.get_captured()>=0)
			squares[m.get_captured()].setPiece(null);
	}

	public void setBoard(boolean isRed) {
		for(int i = 0; i<12;i++)
			squares[i].setPiece( new Piece(!isRed) );
		
		for(int i=20; i<32;i++)
			squares[i].setPiece( new Piece(isRed) );
	}
}
