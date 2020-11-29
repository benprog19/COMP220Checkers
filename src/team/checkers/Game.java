package team.checkers;

import java.awt.Color;

public class Game {

	private int turn;
	private Board board;
	
	public Game(Board board) {
		this.turn = 0;
		this.board = board;
	}
	
	public int getTurn() {
		return turn;
	}
	
	public char getTurnCharRef() {
		if (this.turn == 0) {
			return 'R';
		} else if (this.turn == 1) {
			return 'B';
		} 
		return 'X';
	}
	
	public void nextTurn() {
		this.turn = (turn == 0 ? 1 : 0);
		this.board.getMenu().setIndicator((turn == 0 ? Color.RED : Color.BLACK));
	}
	
	public boolean hasTurn(char c) {
		if (c == 'R') {
			return getTurn() == 0;
		} else if (c == 'B') {
			return getTurn() == 1;
		}
		return false;
	}
	
	public Board getBoard() {
		return board;
	}
	
}

