import static org.junit.Assert.*;

import org.junit.Test;

public class KingTest extends PieceTest {

	@Test
	public void testGetMoveDirections() {
		Piece k = new King(true);
		Direction[] directions =k.getMoveDirections();
		Direction[] expdirecs  = {Direction.UpRight, Direction.UpLeft, Direction.DownLeft, Direction.DownRight};
		assertArrayEquals(expdirecs, directions);
	}
}
