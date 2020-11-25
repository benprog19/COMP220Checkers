package team.checkers;

public class Main {
	
	private static Game game;
	
	public static void main(String[] args) {
		Board board = new Board(true);
		game = new Game(board);
	}
	
	public static Game getGame() {
		return game;
	}

}
