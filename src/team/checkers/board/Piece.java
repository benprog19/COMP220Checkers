package team.checkers.board;

import javax.swing.JButton;

public class Piece {

	private char color;
	private boolean king;
	private JButton button; // SHOULD NEVER CHANGE 
	
	private boolean selected;
	private boolean highlighted;
	
	private int x;
	private int y;

	public Piece() {
		this.color=' ';
		this.king = false;
		this.selected = false;
		this.highlighted = false;
	}
	
	public Piece(char color) {
		this.color= color;
		this.king = false;
		this.selected = false;
		this.highlighted = false;
	}
	
	public Piece(char color, boolean king) {
		this.color= color;
		this.king = king;
		this.selected = false;
		this.highlighted = false;
	}
	
	public Piece(Piece other) {
		this.color = other.color;
		this.king = other.king;
		this.selected = false;
		this.highlighted = false;
	}
	
	public void setColor(char color) {
		this.color = color;
	}
	
	public char getColor() {
		return this.color;
	}
	
	public void setKing(boolean b) {
		this.king= b;
	}
	
	public boolean isKing() {
		return king;
	}
	
	public void setButton(JButton button) {
		 this.button = button;
	}
	
	public JButton getButton() {
		return button;
	}
	
	public void select() {
		if (selected) {
			selected = false;
		} else {
			selected = true;
		}
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void highlight() {
		if (highlighted) {
			highlighted = false;
		} else {
			highlighted = true;
		}
	}
	
	public boolean isHighlighted() {
		return highlighted;
	}
	
	public char oppositeColor() throws Exception {
		if(this.color=='B')
		{
			return 'R';
		}
		else if(this.color=='R')
		{
			return 'B';
		}
		else
		{
			throw new IllegalArgumentException("Illegal Input please Select a Piece");
		}
	}
	
	public void copy(Piece other) {
		this.color = other.color;
		this.highlighted = false;
		this.selected = false;
		this.king = other.king;
	}
	
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int[] getLocation() {
		int[] num = new int[2];
		num[0] = this.x;
		num[1] = this.y;
		return num;
	}
}
