package dean.checkers;
import java.util.ArrayList;

public class Checkers {

	public static void main(String[] args) {
		try {
			Board board = new Board();
			System.out.println(board.toString());
			ArrayList<int[]> test = new ArrayList<int[]>(board.canJump(2, 1));
			board.jump(2, 1, 3, 2,test);
			System.out.println(board.toString());
			test = board.canJump(5, 0);
	 		board.jump(5,0,4,1,test);
	 		System.out.println(board.toString());
	 		test = board.canJump(3, 2);
	 		board.jump(3, 2, 5, 0,test);
	 		System.out.println(board.toString());
		}
		catch(Exception e)
		{
			
		}
	}

}
