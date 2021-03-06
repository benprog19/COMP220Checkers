package team.checkers.board;

import java.util.ArrayList;

import team.checkers.game.Main;


public class Board {
	
	private Piece[][] board;
	private MenuInterface menu;
	
	
	/**
	 * 
	 * @param display -This is the window the game is played in when using a GUI 
	 * This is the board constructor.
	 */
	public Board(boolean display) {
		this.board = new Piece[8][8];

		/** lines 21 - 29 constructs a board of empty Pieces */
		for(int i = 0; i < 8; i++ )
		{
			for(int j = 0; j < 8; j++ )
			{
				this.board[i][j]=new Piece();
				this.board[i][j].setColor(' ');
			}
		}
		/**Set the color of the pieces for the playing both R in the
		 *  North B in the South lines 33 - 49
		 */
		for(int i = 0; i < 8; i++ ) {
			for(int j = 0; j < 8; j++ )
			{

				if(i%2 == 0 && j%2 ==1)
				{

					board[i][j].setColor(colorCheck(i));
				}
				else if(i%2==1 && j%2==0)
				{
					board[i][j].setColor(colorCheck(i));
				}

			}
		}
		menu = new MenuInterface(this, display);
	}
	
	/**
	 * This is the call method to get the menu
	 */
	public MenuInterface getMenu() {
		return menu;
	}
	
	/**
	 * @author Dean O'Toole
	 * @param i - row number
	 * @return correct character for that row for the start of the game
	 * 			Black is Rows 0-2
	 * 			Red is Rows 5-7
	 * 			Rows 3-4 are empty so they are filled with ' '
	 */
	public char colorCheck(int i) {

		if(i < 3)
		{
			return 'R';
		}
		else if(i > 4)
		{
			return 'B';
		}
		else
		{
			return ' ';
		}

	}
	/**
	 *  Prints the current State of the Board
	 */
	@Override
	public String toString() {
		String ans = " ";
		for (int i = 0; i < 8; i++) {
			ans = ans + "   " + (char) (i + 65) + "";
		}
		ans = ans + "\n   ------------------------------\n";
			
		for(int i = 0; i < 8; i++)
		{
			ans = ans + i + " | ";
			for(int j = 0; j < 8; j++)
			{
				ans = ans + (this.board[j][i].getColor());
				if(j != 7)
				{
					ans = ans +(" | ");
				}
			}
			ans = ans +("\n");
			if(i !=7)
			{
				ans = ans +("  |--------------------------------\n");
			}
		}
		return ans;
	}

	/**
	 * 
	 * @param i row of selected piece
	 * @param j column of selected piece
	 * 
	 * finds out where the player wants the selected piece to jump to and takes pieces jump over out
	 */
	public void jump(int i, int j, int k, int l) {

		board[k][l].setColor(board[i][j].getColor());
		board[k][l].setKing(board[i][j].isKing());
		board[i][j].setColor(' ');
		board[i][j].setKing(false);
		
		//If it is jumping over a piece
		if (Math.abs(i-k)==2&& Math.abs(j-l)==2)
		{
			if(k > i && l > j)
			{
				board[i+1][j+1].setColor(' ');
				board[i+1][j+1].setKing(false);
			}
			if(i > k && j > l)
			{
				board[i-1][j-1].setColor(' ');
				board[i-1][j-1].setKing(false);
			}
			if(i > k && l > j)
			{
				board[i-1][j+1].setColor(' ');
				board[i-1][j+1].setKing(false);
			}
			if(k > i && j > l)
			{
				board[i+1][j-1].setColor(' ');
				board[i+1][j-1].setKing(false);
			}
		}
		kingCheck(i,j);

	}
	/**
	 * 
	 * @param toX - the row where the piece is moving to
	 * @param toY - the column where the piece is moving to
	 * @param fromX - the row where the piece is moving from
	 * @param fromY - the column where the piece is moving from
	 * @return - The board with the new move executed
	 */
	public ArrayList<Piece> getJumpedPieces(int toX, int toY, int fromX, int fromY) {
		ArrayList<Piece> pieces = new ArrayList<>();
		if (Math.abs(toX - fromX) >= 2 && Math.abs(toY - fromY) >= 2) {
			int x = (toX + fromX) / 2;
			int y = (toY + fromY) / 2;
			pieces.add(board[x][y]);
		}
		return pieces;
	}
	
	/**
	 * 
	 * @param i row position of selected piece
	 * @param j column position of selected piece
	 * @param k row position of the desired space
	 * @param l column position of the desired space
	 * 
	 * @return returns a boolean if the selected piece can move to the selected point
	 * @throws Exception if the selected piece is any color other than 'R' or 'B'
	 * */
	public ArrayList<int[]> canJump(int i,int j) throws Exception {
		ArrayList<int[]> points = new ArrayList<>();

		if(board[i][j].isKing())
		{
			return kingJump(i,j);
		}
		else if(board[i][j].getColor()=='R')
		{
			if(!board[i][j].isKing())
			{

				return redJump(i,j);
			}
		}

		else if(board[i][j].getColor()=='B')
		{
			if(!board[i][j].isKing())
			{
				return blackJump(i,j);
			}
		}
		throw new Exception("Please select a piece");
	}
/**
 * 
 * @param i - the row the piece is in
 * @param j - the column the piece is in
 * @return - whether a king piece can complete the selected jump
 * @throws Exception - if the selected place is not allowed
 */
	//return whether a King piece can complete the desired jump
	private ArrayList<int[]> kingJump(int i,int j) throws Exception
	{

		ArrayList<int[]> points = new ArrayList<>();
		for(int k = i-2; k < i+3; k++)
		{
			for(int l = j-2; l < j+3; l++)
			{
				if( k < 0 || l < 0)
				{
					continue;
				}
				if(k > 7 || l > 7)
				{
					continue;
				}
				if(!(i%2 == 0 && j%2 ==1))
				{
					if(!(i%2 == 1 && j%2 ==0))
					{
						continue;
					}
				}
				if(board[k][l].getColor()==' ')
				{
					//This is true for every move a King can make
					if(Math.abs(i-k)==1&& Math.abs(j-l)==1)
					{
						int[] point = new int[2];
						point[0] = k;
						point[1]=l;
						points.add(point);
					}

					//This is True for every Jump a king can make
					else if(Math.abs(i-k)==2&& Math.abs(j-l)==2)
					{
						/**
						 * This is the case in which the target space is empty and the space between the target and the
						 * current space is defined by board[i+1][j+1]	 
						 */
						if(board[k][l].getColor()==' ' && k > i && l > j)
						{
							if(board[i+1][j+1].getColor()==board[i][j].oppositeColor())
							{
								int[] point = new int[2];
								point[0] = k;
								point[1]=l;
								points.add(point);
							}
						}
						/**
						 * This is the case in which the target space is empty and the space between the target and the
						 * current space is defined by board[i-1][j+1]	 
						 */
						else if(board[k][l].getColor()==' ' && i > k && l > j)
						{
							if(board[i-1][j+1].getColor()==board[i][j].oppositeColor())
							{
								int[] point = new int[2];
								point[0] = k;
								point[1]=l;
								points.add(point);
							}
						}
						/**
						 * This is the case in which the target space is empty and the space between the target and the
						 * current space is defined by board[i-1][j-1]	 
						 */
						else if(board[k][l].getColor()==' ' && i > k && j > l)
						{
							if(board[i-1][j-1].getColor()==board[i][j].oppositeColor())
							{
								int[] point = new int[2];
								point[0] = k;
								point[1]=l;
								points.add(point);
							}
						}
						/**
						 * This is the case in which the target space is empty and the space between the target and the
						 * current space is defined by board[i+1][j-1]	 
						 */
						else if(board[k][l].getColor()==' ' && k > i && j > l)
						{
							if(board[i+1][j-1].getColor()==board[i][j].oppositeColor())
							{
								int[] point = new int[2];
								point[0] = k;
								point[1]=l;
								points.add(point);
							}
						}
					}
				}
			}
		}
		return points;
	}
/**
 * 
 * @param i - The desired jump's row location
 * @param j - The desired jump's column location
 * @return - whether a nonKing red piece can complete the selected jump
 * @throws Exception if the selected place is not allowed
 */
	
	private ArrayList<int[]> redJump(int i,int j) throws Exception
	{
		ArrayList<int[]> points = new ArrayList<>();
		for(int k = i+1; k < i+3; k++)
		{
			for(int l = j-2; l < j+3; l++)
			{

				if( k < 0 || l < 0)
				{
					continue;
				}
				if(k > 7 || l > 7)
				{
					continue;
				}
				if(!(i%2 == 0 && j%2 ==1))
				{
					if(!(i%2 == 1 && j%2 ==0))
					{
						continue;
					}
				}
				if(board[k][l].getColor()==' ')
				{
					//This is true for every move a red Piece can make
					if((i-k)==-1 && Math.abs(j-l)==1)
					{
						if(board[k][l].getColor()==' ')
						{
							int[] point = new int[2];
							point[0] = k;
							point[1] = l;
							points.add(point);
						}
					}
					//This is true for every jump a red Piece can make
					else if((i-k)==-2 && Math.abs(j-l)==2)
					{
						/**case for a Red piece jumping to an empty space over a piece defined with the opposite color
						 * and position given by board[i+1][j+1]
						 */
						if(board[k][l].getColor()==' ' && k > i && l > j)
						{
							if(board[i+1][j+1].getColor()==board[i][j].oppositeColor())
							{
								int[] point = new int[2];
								point[0] = k;
								point[1]=l;
								points.add(point);
							}
						}
						/**case for a Red piece jumping to an empty space over a piece defined with the opposite color
						 * and position given by board[i+1][j-1]
						 */
						else if(board[k][l].getColor()==' ' && k > i && j > l)
						{
							if(board[i+1][j-1].getColor()==board[i][j].oppositeColor())
							{
								int[] point = new int[2];
								point[0] = k;
								point[1]=l;
								points.add(point);
							}
						}
					}
				}
			}
		}
		return points;
	}

/**
 * 
 * @param i - the desired jump's row location
 * @param j - the desired jump's column location
 * @return whether a nonKing black piece can legally perform the selected jump
 * @throws Exception if the selected place is not allowed
 */
	
	private ArrayList<int[]> blackJump(int i,int j) throws Exception
	{
		ArrayList<int[]> points = new ArrayList<>();
		for(int k = i-1; k > i-3; k--)
		{
			for(int l = j-2; l < j+3; l++)
			{
				if( k < 0 || l < 0)
				{
					continue;
				}
				if(k > 7 || l > 7)
				{
					continue;
				}
				if(!(i%2 == 0 && j%2 ==1))
				{
					if(!(i%2 == 1 && j%2 ==0))
					{
						continue;
					}
				}
				if(board[k][l].getColor()==' ')
				{

					if((i-k)==1 && Math.abs(j-l)==1)
					{
						if(board[k][l].getColor()==' ')
						{
							int[] point = new int[2];
							point[0] = k;
							point[1]=l;
							points.add(point);
						}
					}
					else if((i-k)==2 && Math.abs(j-l)==2)
					{
						/**case for a black piece jumping to an empty space over a piece defined with the opposite color
						 * and position given by board[i-1][j+1]
						 */
						if(board[k][l].getColor()==' ' && i > k && l > j)
						{
							if(board[i-1][j+1].getColor()==board[i][j].oppositeColor())
							{
								int[] point = new int[2];
								point[0] = k;
								point[1]=l;
								points.add(point);
							}
						}
						/**case for a black piece jumping to an empty space over a piece defined with the opposite color
						 * and position given by board[i-1][j-1]
						 */
						else if(board[k][l].getColor()==' ' && i > k && j > l)
						{
							if(board[i-1][j-1].getColor()==board[i][j].oppositeColor())
							{
								int[] point = new int[2];
								point[0] = k;
								point[1]=l;
								points.add(point);
							}

						}
					}
				}
			}
		}
		return points;
	}
	/**
	 * 
	 * @param i - the row location
	 * @param j - the column location
	 * @return the board with the piece at [i,j] selected
	 */
	public Piece pieceAt(int i, int j) {
		return board[i][j];
	}
	/**
	 * Checks if a piece is a king
	 * @param i - the row location
	 * @param j - the column location
	 */
	public void kingCheck(int i, int j) {
		if(pieceAt(i,j).getColor() == 'R'&& i == 7)
		{
			pieceAt(i,j).setKing(true);
		}
		else if(pieceAt(i,j).getColor() == 'B' && i == 0)
		{
			pieceAt(i,j).setKing(true);
		}
	}
	/**
	 * 
	 * @return the board
	 */
	public Piece[][] pieces() {
		return board;
	}
/**
 * 	
 * @return The pieces that are legal to pick
 */
	public ArrayList<Piece> getSelectedPieces() {
		ArrayList<Piece> pieces = new ArrayList<>();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				Piece piece = pieceAt(i, j);
				//System.out.print("[" + i + "," + j + "]: " + (piece.isHighlighted() ? "high" : "") + (piece.isSelected() ? "sel" : ""));
				if (piece.isSelected()) {
					pieces.add(piece);
				}
			}
		}
		return pieces;
	}
/**
 * 
 * @param c - the player
 * @return how many pieces they still have and their positions
 */
	public ArrayList<int[]> getTeamCheckers(char c) {
		ArrayList<int[]> list = new ArrayList<>();
		
		for (int i = 0; i < pieces().length; i++) {
			for (int j = 0; j < pieces().length; j++) {
				if (pieceAt(i,j).getColor() == c) {
					int[] loc = new int[2];
					loc[0] = i;
					loc[1] = j;
					list.add(loc);
				}
			}
		}
		return list;
	}
/**
 * 	
 * @return the player who wins, or a stalemate
 */
	public char getWin() {
		int red = getTeamCheckers('R').size();
		int black = getTeamCheckers('B').size();
		if(red == 0) {
			Main.getGame().addWin(Main.getGame().getBlackPlayer());
			Main.getGame().addLoss(Main.getGame().getRedPlayer());
			Main.getGame().printStats();
			return 'B'; // red
		} else if(black == 0) {
			Main.getGame().addWin(Main.getGame().getRedPlayer());
			Main.getGame().addLoss(Main.getGame().getBlackPlayer());
			Main.getGame().printStats();
			return 'R'; // black
		} else {
			char turn = Main
					.getGame()
					.getTurnCharRef();
			int num = turn == 'R' ? red : black;
			boolean stalemate = true;
			for (int i = 0; i < num; i++) {
				int[] loc = getTeamCheckers(turn).get(i);
				try {
					if (canJump(loc[0], loc[1]).size() > 0) {
						stalemate = false;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (stalemate) {
				return 'S'; // stalemate
			}
		}
		return 'P'; // pass 
	}
}
		
	
	
