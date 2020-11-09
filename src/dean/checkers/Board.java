package dean.checkers;
import java.util.ArrayList;

/**
 * This class is used for the checker board
 */
public class Board {

	//the board
	private Piece[][] board;



	/**
	 * @author Dean O'Toole
	 * @summary Board() constructs a new board
	 */
	Board()
	{
		//we use the Piece type because we need to store both color and if the Piece is a King
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
		for(int i = 0; i < 8; i++ )
		{
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
	}


	/**
	 * @author Dean O'Toole
	 * @param i - row number
	 * @return correct character for that row for the start of the game
	 * 			Black is Rows 0-2
	 * 			Red is Rows 5-7
	 * 			Rows 3-4 are empty so they are filled with ' '
	 */
	public char colorCheck(int i)
	{

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
	 *  Prints the Current State of the Board
	 */
	@Override
	public String toString()
	{
		String ans = "";
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				ans = ans + (this.board[i][j].getColor());
				if(j != 7)
				{
					ans = ans +(" | ");
				}
			}
			ans = ans +("\n");
			if(i !=7)
			{
				ans = ans +("-----------------------------\n");
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
	public void jump(int i, int j, int k, int l, ArrayList<int[]> points)
	{

		for(int groupNum = 0; groupNum < points.size();groupNum++)
		{
			int [] point = points.get(groupNum);
			if(point[0] == k && point[1] == l)
			{
				//moving a piece
				board[k][l].setColor(board[i][j].getColor());
				board[k][l].setKing(board[i][j].isKing());
				board[i][j].setColor(' ');
				board[i][j].setKing(false);
			}
		}
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
	 * @param i row position of selected piece
	 * @param j column position of selected piece
	 * @param k row position of the desired space
	 * @param l column position of the desired space
	 * 
	 * @return returns a boolean if the selected piece can move to the selected point
	 * @throws Exception if the selected piece is any color other than 'R' or 'B'
	 * */
	public ArrayList<int[]> canJump(int i,int j) throws Exception
	{
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

	//return whether a nonKing red piece can complete the desired jump
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
							point[1]=l;
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


	//return whether a nonKing black piece can complete the desired jump
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
	// returning the piece at the board spot
	public Piece pieceAt(int i, int j)
	{
		return board[i][j];
	}

	public void kingCheck(int i, int j)
	{
		if(pieceAt(i,j).getColor() == 'R'&& i == 7)
		{
			pieceAt(i,j).setKing(true);
		}
		else if(pieceAt(i,j).getColor() == 'B' && i == 0)
		{
			pieceAt(i,j).setKing(true);
		}
	}


}
