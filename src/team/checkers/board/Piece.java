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
/**
 * This is a constructor to create the pieces
 */
	public Piece() {
		this.color=' ';
		this.king = false;
		this.selected = false;
		this.highlighted = false;
	}
	/**
	 * This is a constructor to create the pieces based on taking in a piece 
	 * @param color
	 */
	public Piece(char color) {
		this.color= color;
		this.king = false;
		this.selected = false;
		this.highlighted = false;
	}
	/**
	 * This is a constructor to create the pieces if they are a king
	 * @param color
	 * @param king
	 */
	public Piece(char color, boolean king) {
		this.color= color;
		this.king = king;
		this.selected = false;
		this.highlighted = false;
	}
	/**
	 * This is a constructor to create a copy of a piece
	 * @param other - the piece to be copied
	 */
	public Piece(Piece other) {
		this.color = other.color;
		this.king = other.king;
		this.selected = false;
		this.highlighted = false;
	}
	/**
	 * Sets the color of the player
	 * @param color - the player's color
	 */
	public void setColor(char color) {
		this.color = color;
	}
	/**
	 * 
	 * @return the player's color
	 */
	public char getColor() {
		return this.color;
	}
	/**
	 * Tells if a piece is a king
	 * @param b - true or false based on a piece's king status
	 */
	public void setKing(boolean b) {
		this.king= b;
	}
	/**
	 * 
	 * @return the piece's king status
	 */
	public boolean isKing() {
		return king;
	}
	/**
	 * This sets the button
	 * @param button - This is part of creating the GUI
	 */
	public void setButton(JButton button) {
		 this.button = button;
	}
	/**
	 * 
	 * @return the set button
	 */
	public JButton getButton() {
		return button;
	}
	/**
	 * This takes in the piece that has been selected
	 */
	public void select() {
		if (selected) {
			selected = false;
		} else {
			selected = true;
		}
	}
	/**
	 * 
	 * @return what piece was selected
	 */
	public boolean isSelected() {
		return selected;
	}
	/**
	 * Highlight a piece or position based on what was selected
	 */
	public void highlight() {
		if (highlighted) {
			highlighted = false;
		} else {
			highlighted = true;
		}
	}
	/**
	 * 
	 * @return if the piece is highlighted
	 */
	public boolean isHighlighted() {
		return highlighted;
	}
	/**
	 * 
	 * @return The oppposite color of a piece
	 * @throws Exception if the selected place is not allowed
	 */
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
	/**
	 * This creates a copy of the piece
	 * @param other - the piece we want copied
	 */
	public void copy(Piece other) {
		this.color = other.color;
		this.highlighted = false;
		this.selected = false;
		this.king = other.king;
	}
	/**
	 * This sets the piece's location
	 * @param x - the piece's row
	 * @param y - the piece's column
	 */
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * 
	 * @return the location of the piece
	 */
	public int[] getLocation() {
		int[] num = new int[2];
		num[0] = this.x;
		num[1] = this.y;
		return num;
	}
}
