package team.checkers.player;

import java.io.Serializable;

public class Stats implements Serializable {

	private String name;
	private int wins;
	private int losses;
	
	/**
	 * This is a constructor that assigns names
	 * @param name - The name of who is playing the game
	 */
	public Stats(String name) {
		this.name = name;
	}
	/**
	 *  This is a constructor that assigns names and stats
	 * @param name - The name of who is playing
	 * @param wins - How many times someone has won
	 * @param losses - How many times someone has lost
	 */
	public Stats(String name, int wins, int losses) {
		this.name = name;
		this.wins = wins;
		this.losses = losses;
	}
	
	/**
	 * This adds a win to someone's stats
	 */
	public void addWin() {
		this.wins++;
		System.out.println(wins + " wins");
	}
	/**
	 * This adds a loss to someone's stats
	 */
	public void addLoss() {
		this.losses++;
		System.out.println(losses + " losses");
	}
	/**
	 * 
	 * @return - a person's name
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * 
	 * @return - How many times a person has won
	 */
	public int getWins() {
		return wins;
	}
	/**
	 * 
	 * @return - How many times a person has lost
	 */
	public int getLosses() {
		return losses;
	}
	/**
	 * Creates the Win to loss ratio
	 * @return - The ratio of wins to total
	 */
	public double getWLRatio() {
		return (double) wins / (wins + losses);
	}
	
}
