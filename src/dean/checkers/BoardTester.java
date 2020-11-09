package dean.checkers;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class BoardTester {


	 
	@Test public void PieceTest()
	{	
	final Piece  checker = new Piece();
		assertEquals(' ',checker.getColor());
	}
	@Test public void BoardTest()
	{
		Board board = new Board();
		assertEquals(board.pieceAt(2, 1).getColor(),'R');
		assertEquals(board.pieceAt(2, 1).isKing(),false);
		assertEquals(board.pieceAt(6, 0).getColor(),' ');
		assertEquals(board.pieceAt(6, 0).isKing(),false);
		assertEquals(board.pieceAt(5, 0).getColor(),'B');
		assertEquals(board.pieceAt(5, 0).isKing(),false);
	}
	@Test public void PieceWithColorTest()
	{
		final Piece  checker = new Piece('G');
		assertEquals('G',checker.getColor());
	}
	@Test public void PieceWithColorAndKing()
	{
		final Piece  checker = new Piece('G',true);
		assertEquals('G',checker.getColor());
		assertEquals(true,checker.isKing());
	}
	@Test public void jumpTest()
	{
		try {
		Board board = new Board();
		ArrayList<int[]> test = new ArrayList<int[]>(board.canJump(2, 1));
		board.jump(2, 1, 3, 2,test);
		test = board.canJump(5, 0);
 		board.jump(5,0,4,1,test);
 		test = board.canJump(3, 2);
 		board.jump(3, 2, 5, 0,test);
 		
 		assertEquals(board.pieceAt(2, 1).getColor(),' ');
		assertEquals(board.pieceAt(2, 1).isKing(),false);
		assertEquals(board.pieceAt(4, 1).getColor(),' ');
		assertEquals(board.pieceAt(4, 1).isKing(),false);
		assertEquals(board.pieceAt(5, 0).getColor(),'R');
		assertEquals(board.pieceAt(5, 0).isKing(),false);
		}
		catch(Exception e)
		{
			fail();
		}
	}
}
