package dean.checkers;

public class Piece {

private char color;
private boolean King;

	
	Piece() {
		this.color=' ';
		this.King = false;
	}
	Piece(char color)
	{
		this.color= color;
		this.King = false;
	}
	Piece(char color,boolean king)
	{
		this.color= color;
		this.King = king;
	}
	Piece(Piece other)
	{
		this.color = other.color;
		this.King = other.King;
	}
	
	void setColor(char color)
	{
		this.color = color;
	}
	char getColor()
	{
		return this.color;
	}
	
	void setKing(boolean b)
	{
		this.King= b;
	}
	boolean isKing()
	{
		return King;
	}
	char oppositeColor() throws Exception
	{
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
	

}
