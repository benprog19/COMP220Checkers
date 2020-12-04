package team.checkers.game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.ImageIcon;

import team.checkers.board.Board;
import team.checkers.player.Stats;

public class Main {
	
	private static Game game;

	/**
	 * 
	 * @param args
	 * This is the main method where the game is played.
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
		
		Board board = new Board(false);
		game.setBoard(board);
		
		if (c == 'c') {
			consoleGame(board, scanner);
		} else if (c == 'g') {
			board.getMenu().display(true);
		} else if (c == 's') {
			game.readStats(true);
		}
		
	}
	/**
	 * This is the method to create the game
	 * @return the called game
	 */
	public static Game getGame() {
		return game;
	}
	/**
	 * This is how you play the game through the console
	 * @param board - Creates a board
	 * @param scanner - Creates a scanner to read moves
	 */
	private static void consoleGame(Board board, Scanner scanner) {
		String redTeam = game.getRedPlayer();
		String blackTeam = game.getBlackPlayer();
		Stats redStats = game.getStatsFromName(redTeam);
		Stats blackStats = game.getStatsFromName(blackTeam);
		
		while (board.getWin() == 'P') {
			int turn = game.getTurn();
			System.out.println("\n\n\n\n" + redTeam + "[" + redStats.getWLRatio() + "] vs " 
			+ blackTeam + "[" + blackStats.getWLRatio() + "]");
			System.out.println("  Turn: " + (game.getTurnCharRef() == 'R' ? "red" : "black") + "'s turn");
			System.out.println(board.toString());
			System.out.print("[" + game.getTurnCharRef() + "] Select a piece (form: A:#): ");
			
			String sel = scanner.next();
			while(!validSelect(sel, board)) {
				System.out.print("[" + game.getTurnCharRef() + "] Select a piece (form: A:#): ");
				sel = scanner.next();
			}
			String[] vals = sel.split(":");
			int x = vals[0].charAt(0) - 65;
			int y = Integer.parseInt(vals[1]);
			try {
				ArrayList<int[]> jumps = board.canJump(x, y);
				System.out.print("[" + game.getTurnCharRef() + "] Possible moves are: ");
				for (int i = 0; i < jumps.size(); i++) {
					System.out.print("[" + (char) (jumps.get(i)[0] + 65) + ", " + jumps.get(i)[1] + "] ");
				}
				System.out.print("\nWhich location would you like to jump to: ");
				String move = scanner.next();
				while (!validMove(move, jumps)) {
					System.out.print("\nWhich location would you like to jump to: ");
					move = scanner.next();
				}
				String[] vals1 = move.split(":");
				int moveX = vals1[0].charAt(0) - 65;
				int moveY = Integer.parseInt(vals1[1]);
				board.jump(x, y, moveX, moveY);
				game.nextTurn();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		for (int i = 0; i < board.pieces().length; i++) {
			for (int j = 0; j < board.pieces().length; j++) {
				board.pieces()[i][j].setColor(' ');
				System.out.println(board.toString());
			}
		}
		System.out.println("\n\n\n\n" + redTeam + "[" + redStats.getWLRatio() + "] vs " 
				+ blackTeam + "[" + blackStats.getWLRatio() + "]");
		char winner = board.getWin();
		String message = "";
		if (winner == 'S') {
			message = "STALEMATE";
		} else if (winner == 'R') {
			message = "RED WINS";
		} else if (winner == 'B') {
			message = "BLACK WINS";
		}
		System.out.println("  " + message + "\n");
		game.printStats();
		scanner.close();
	}
	/**
	 * 
	 * @param input - The selected piece
	 * @param jumps - The places the piece could move if there was nothing in it's way
	 * @return whether the piece selected is a piece that can be moved
	 */
	private static boolean validMove(String input, ArrayList<int[]> jumps) {
		if (input.contains(":") && input.length() == 3) {
			String[] vals = input.split(":");
			try {
				char c = vals[0].charAt(0);
				int num = Integer.parseInt(vals[1]);
				if (c >= 65 && c <= 72) {
					if (num >= 0 && num <= 7) {
						int[] nums = new int[2];
						nums[0] = c-65;
						nums[1] = num;
						boolean match = false;
						for (int i = 0; i < jumps.size(); i++) {
							if (Arrays.equals(nums, jumps.get(i))) {
								match = true;
								break;
							}
						}
						if (match) {
							return true;
						}
					}
				}
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}
	/**
	 * 
	 * @param input - takes in the player's selected move
	 * @param board - takes in the current board
	 * @return if the move is valid
	 */
	private static boolean validSelect(String input, Board board) {
		if (input.contains(":") && input.length() == 3) {
			String[] vals = input.split(":");
			try {
				char c = vals[0].charAt(0);
				int num = Integer.parseInt(vals[1]);
				if (c >= 65 && c <= 72) {
					if (num >= 0 && num <= 7) {
						if (board.pieceAt(c-65, num).getColor() == game.getTurnCharRef()) {
							if (board.canJump(c-65, num).size() > 0) {
								return true;
							}
						}
					}
				}
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

}
