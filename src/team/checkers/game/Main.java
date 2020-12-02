package team.checkers.game;

import java.util.Scanner;

import team.checkers.board.Board;
import team.checkers.player.Stats;

public class Main {
	
	private static Game game;
	
	
	/* TODO 
	 *  - option for GUI or console
	 *  - player stats (using HashMap)
	 *
	 */
	
	public static void main(String[] args) {
		game = new Game();
		System.out.print("Welcome to Checkers! \n\n"
				+ "    This project has been developed by\n"
				+ "       Ben Craig, Dean O'Toole, and Morgan Smith\n\n"
				+ "Please enter red name: ");
		
		Scanner scanner = new Scanner(System.in);
		String redName = scanner.nextLine();
		System.out.print("Please enter black name: ");
		String blackName = scanner.nextLine();
		
		game.setRedPlayer(redName.stripTrailing().stripLeading().toUpperCase());
		game.setBlackPlayer(blackName.stripTrailing().stripLeading().toUpperCase());
		
		System.out.print("Play with console [c], GUI [g] or view stats [s]: ");
		char c = scanner.next().charAt(0);
		scanner.close();
		
		Board board = new Board(false);
		game.setBoard(board);
		
		if (c == 'c') {
			
		} else if (c == 'g') {
			board.getMenu().display(true);
		} else if (c == 's') {
			game.readStats(true);
		}
		
	}
	
	public static Game getGame() {
		return game;
	}

}
