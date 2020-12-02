package team.checkers.player;

import java.io.Serializable;

public class Stats implements Serializable {

	private String name;
	private int wins;
	private int losses;
	
	public Stats(String name) {
		this.name = name;
	}
	
	public Stats(String name, int wins, int losses) {
		this.name = name;
		this.wins = wins;
		this.losses = losses;
	}
	
	public void addWin() {
		this.wins++;
		System.out.println(wins + " wins");
	}
	
	public void addLoss() {
		this.losses++;
		System.out.println(losses + " losses");
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getWins() {
		return wins;
	}
	
	public int getLosses() {
		return losses;
	}
	
	public double getWLRatio() {
		return (double) wins / (wins + losses);
	}
	
}
