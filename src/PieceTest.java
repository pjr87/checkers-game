import static org.junit.Assert.*;

import org.junit.Test;

public class PieceTest {

	@Test
	public void testGetTeam() {
		Piece ppos = new Piece(true);
		Piece pfal = new Piece(false);
		assertEquals(true, ppos.getTeam());
		assertEquals(false, pfal.getTeam());
	}

	@Test
	public void testSetTeam() {
		Piece ppos = new Piece(true);
		ppos.setTeam(false);
		assertEquals(false, ppos.getTeam());
	}
	
	@Test
	public void testGetMoveDirections() {
		Piece p = new Piece(true);
		Direction[] directions = p.getMoveDirections();
		Direction[] expdirecs  = {Direction.UpRight, Direction.UpLeft};
		assertArrayEquals(expdirecs, directions);
	}
}
