package team.checkers.game;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import org.junit.jupiter.params.shadow.com.univocity.parsers.common.input.EOFException;

import team.checkers.board.Board;
import team.checkers.player.Stats;

public class Game {

	private String redPlayer;
	private String blackPlayer;

	private int turn;
	private Board board;
	private HashMap<String, Stats> playerStats;

	/**
	 * This is a constuctor 
	 * @param board - Takes in an already created board
	 */
	public Game(Board board) {
		this.turn = 0;
		this.board = board;
		playerStats = new HashMap<>();
		readStats(false);
	}
	/**
	 * This is the constructor that is called at the beginning of the game to start the game
	 */
	public Game() {
		this.turn = 0;
		this.playerStats = new HashMap<>();
		readStats(false);
	}
/**
 * This creates a new board based on the last board
 * @param board - The former board
 */
	public void setBoard(Board board) {
		this.board = board;
	}
/**
 * This sets the red player's name
 * @param name - The player's name
 */
	public void setRedPlayer(String name) {
		this.redPlayer = name;
		registerPlayer(name);
	}
/**
 * This sets the black player's name
 * @param name - The player's name
 */
	public void setBlackPlayer(String name) {
		this.blackPlayer = name;
		registerPlayer(name);
	}
/**
 * This creates a player's statistics if they did not already have them
 * @param name - the player's name
 */
	public void registerPlayer(String name) {
		if (!playerStats.containsKey(name)) {
			playerStats.put(name, new Stats(name, 0, 0));
		}
	}
/**
 * 
 * @return if it is the red player's turn
 */
	public String getRedPlayer() {
		return redPlayer != null ? redPlayer : "N/A";
	}
/**
 * 
 * @return if it is the black player's turn
 */
	public String getBlackPlayer() {
		return blackPlayer != null ? blackPlayer : "N/A";
	}
/**
 * 
 * @return the turn
 */
	public int getTurn() {
		return turn;
	}
/**
 * 
 * @return The letter of who's turn it is
 */
	public char getTurnCharRef() {
		if (this.turn == 0) {
			return 'R';
		} else if (this.turn == 1) {
			return 'B';
		}
		return 'X';
	}
/**
 * This is who has the next turn
 */
	public void nextTurn() {
		this.turn = (turn == 0 ? 1 : 0);
		this.board.getMenu().setIndicator((turn == 0 ? Color.RED : Color.BLACK));
	}
/**
 * 
 * @param c - a player 
 * @return who's turn it is
 */
	public boolean hasTurn(char c) {
		if (c == 'R') {
			return getTurn() == 0;
		} else if (c == 'B') {
			return getTurn() == 1;
		}
		return false;
	}
/**
 * 
 * @return a board
 */
	public Board getBoard() {
		return board;
	}
/**
 * This reads the file that contains the statistics and calls a persons former statistics
 *  to get their overall ratio
 * @param output - This checks if the player already had statistics
 */
	@SuppressWarnings("unchecked")
	public void readStats(boolean output) {
		File file = new File("stor/playerstats.dat");
		if (!file.exists()) {
			return;
		}

		// reads all information from file and puts it into HashMap
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			playerStats = (HashMap<String, Stats>) ois.readObject();

			ois.close();
			fis.close();

			// prints out information loaded
			if (output) {
				Set<String> set = playerStats.keySet();
				Iterator<String> iter = set.iterator();
				while (iter.hasNext()) {
					String userName = iter.next();
					Stats stats = playerStats.get(userName);
					if (stats != null) {
						System.out.println(stats.getName() + " : [" + stats.getWins() + ", " + stats.getLosses() + ", "
								+ stats.getWLRatio() + "]");
					}
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (EOFException e) {
			e.printStackTrace();
		}
	}
/**
 * This adds the statistics to a file
 */
	public void updateStats() {
		File dir = new File("stor");
		File file = new File("stor/playerstats.dat");
		if (!dir.exists()) {
			dir.mkdir();
		}

//		System.out.println("Saving " + playerStats.size() + " number of stats from HashMap");
//		Set<String> set = playerStats.keySet();
//		Iterator<String> iter = set.iterator();
//		while (iter.hasNext()) {
//			String userName = iter.next();
//			Stats stats = playerStats.get(userName);
//			if (stats != null) {
//				System.out.println(stats.getName() 
//						+ " : [" + stats.getWins() + ", " + stats.getLosses() + ", " + stats.getWLRatio() + "]");
//			}
//		}

		// write information to file
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(playerStats);
			oos.close();
			fos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
/**
 * Creates a HashMap of a Player's Stats
 * @return the player's stats 
 */
	public HashMap<String, Stats> getPlayerStats() {
		return playerStats;
	}
/**
 * This creates a Stat Object for the person playing
 * @param name - The name of the player
 * @return the statistics of who's playing
 */
	public Stats getStatsFromName(String name) {
		return playerStats.get(name);
	}
	/**
	 * This adds a win to the statistics of the person playing who lost
	 * @param name - The name of who won
	 */
	public void addWin(String name) {
		Stats stats = playerStats.get(name);
		if (stats != null) {
			stats.addWin();
			playerStats.put(name, stats);
			updateStats();
		}

	}
/**
 * This adds a loss to the statistics of the person playing who lost
 * @param name - The name of who lost
 */
	public void addLoss(String name) {
		Stats stats = playerStats.get(name);
		if (stats != null) {
			stats.addLoss();
			playerStats.put(name, stats);
			updateStats();
		}
	}

	/**
	 * This is the method to print each player's statistics
	 */
	public void printStats() {
		Stats stats = getStatsFromName(redPlayer);
		System.out.println(stats.toString());
		
		stats = getStatsFromName(blackPlayer);
		System.out.println(stats.toString());
	}
}
