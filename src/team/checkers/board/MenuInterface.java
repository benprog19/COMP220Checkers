package team.checkers.board;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import team.checkers.game.Game;
import team.checkers.game.Main;
import team.checkers.player.Stats;

public class MenuInterface {

	private Board board;

	private JFrame frame;
	private JPanel gui;
	private JPanel checkersBoard;
	private JLabel message;
	private String colsLabel;
	private String rowLabel;
	private JButton indicator;

	final private int width = 650;
	final private int height = 650;
/**
 * 
 * @param board - This is the current board
 * @param display - This is the window the game is played in when using a GUI
 */
	public MenuInterface(Board board, boolean display) {
		Game game = Main.getGame();
	
		this.board = board;

		frame = new JFrame();
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.setResizable(false);
		frame.setTitle("Checkers");
		gui = new JPanel(new BorderLayout(4, 4));
		
		Stats redStats = game.getStatsFromName(game.getRedPlayer());
		Stats blackStats = game.getStatsFromName(game.getBlackPlayer());
		
		message = new JLabel("Checkers |  " + redStats.getName() 
		+ " [" + redStats.getWLRatio() + "] vs " + blackStats.getName() 
		+ " [" + blackStats.getWLRatio() + "]");
		colsLabel = "ABCDEFGH";

		gui.setBorder(new EmptyBorder(4, 4, 4, 4));
		JToolBar tools = new JToolBar();
		tools.setFloatable(false);
		gui.add(tools, BorderLayout.PAGE_START);

		indicator = new JButton();
		indicator.setBackground(Color.red);
		tools.add(indicator);
		tools.add(new JLabel(" "));
		tools.add(message);

		gui.add(new JLabel(""), BorderLayout.LINE_START);
		update(true);
		checkersBoard = new JPanel(new GridLayout(0, 9));
		checkersBoard.setBorder(new LineBorder(Color.BLACK));

		checkersBoard.add(new JLabel(""));
		for (int i = 0; i < board.pieces().length; i++) {
			checkersBoard.add(new JLabel(colsLabel.substring(i, i + 1), SwingConstants.CENTER));
		}
		for (int i = 0; i < board.pieces().length; i++) {
			for (int j = 0; j < board.pieces().length; j++) {
				switch (j) {
				case 0:
					checkersBoard.add(new JLabel("" + (i + 1), SwingConstants.CENTER));
				default:
					checkersBoard.add(board.pieces()[j][i].getButton());
				}
			}
		}
		gui.add(checkersBoard);
		frame.add(gui);

		if (display) {
			display(true);
		}
	}
/**
 * This sets the board background
 * @param i - the row
 * @param j - the column
 * @param allColor - this is the winning player
 * @return what the background of the board should look like
 */
	private int[] originalBackgroundColor(int i, int j, char allColor) {
		int[] colors = new int[3];
		if (board.pieces()[i][j].isSelected()) {
			colors[0] = 0;
			colors[1] = 234;
			colors[2] = 255;
		} else if (board.pieces()[i][j].isHighlighted()) {
			colors[0] = 255;
			colors[1] = 255;
			colors[2] = 255;
		} else {
			if (allColor == ' ') {
				if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) {
					if (i >= 4) { // black
						colors[0] = 176;
						colors[1] = 176;
						colors[2] = 176;
					} else if (i <= 3) { // red
						colors[0] = 255;
						colors[1] = 140;
						colors[2] = 140;
					}
				} else {
					if (i <= 3) { // red
						colors[0] = 255;
						colors[1] = 112;
						colors[2] = 112;
					} else if (i >= 4) { // black
						colors[0] = 155;
						colors[1] = 155;
						colors[2] = 155;
					}
				}
			} else {
				if (allColor == 'R') {
					if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) {
						colors[0] = 255;
						colors[1] = 140;
						colors[2] = 140;
					} else {
						colors[0] = 255;
						colors[1] = 112;
						colors[2] = 112;
					}
				} else if (allColor == 'B') {
					if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) {
						colors[0] = 176;
						colors[1] = 176;
						colors[2] = 176;
					} else {
						colors[0] = 155;
						colors[1] = 155;
						colors[2] = 155;
					}
				} else if (allColor == 'S') {
					if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) {
						colors[0] = 255;
						colors[1] = 171;
						colors[2] = 249;
					} else {
						colors[0] = 255;
						colors[1] = 184;
						colors[2] = 250;
					}
				}
			}
		}
		return colors;
	}
/**
 * 
 * @param display - This is the window the GUI version sis played in 
 */
	public void display(boolean display) {

		// Create and set up the window.

		// Display the window.
		// frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
/**
 * This sets up the board at the beginning of the game
 * @param firstSetup - this is whether or not the game has been started
 */
	public void update(boolean firstSetup) {
		Insets insets = new Insets(0, 0, 0, 0);
		ImageIcon red = new ImageIcon("content/red.png");
		ImageIcon black = new ImageIcon("content/black.png");
		ImageIcon redKing = new ImageIcon("content/red_king.png");
		ImageIcon blackKing = new ImageIcon("content/black_king.png");
		// System.out.println("rows: " + board.getPieces().length);
		Piece[][] pieces = board.pieces();
		ActionListener actionListener;
		for (int i = 0; i < pieces.length; i++) {
			for (int j = 0; j < pieces.length; j++) {

				JButton b;

				if (firstSetup) {
					b = new JButton();
					b.setMargin(insets);
					final int i1 = i;
					final int j1 = j;
					actionListener = new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							pieceAction(pieces, i1, j1);
						}

					};
					b.addActionListener(actionListener);
					pieces[i][j].setButton(b);
					pieces[i][j].setLocation(i, j);
				} else {
					b = pieces[i][j].getButton();
				}

				b.setBackground(new Color(originalBackgroundColor(i, j, ' ')[0], originalBackgroundColor(i, j, ' ')[1],
						originalBackgroundColor(i, j, ' ')[2]));
				if (pieces[i][j].getColor() == 'R') {
					if (pieces[i][j].isKing()) {
						b.setIcon(redKing);
					} else {
						b.setIcon(red);
					}
				} else if (pieces[i][j].getColor() == 'B') {
					if (pieces[i][j].isKing()) {
						b.setIcon(blackKing);
					} else {
						b.setIcon(black);
					}
				} else {
					b.setIcon(new ImageIcon(""));
				}
			}
		}
		if (Main.getGame() != null) {
			char winner = board.getWin();
			if (winner != 'P') {
				for (int i = 0; i < pieces.length; i++) {
					for (int j = 0; j < pieces.length; j++) {
						pieces[i][j].setColor(' ');
						pieces[i][j].getButton().setBackground(new Color(originalBackgroundColor(i, j, winner)[0],
								originalBackgroundColor(i, j, winner)[1], originalBackgroundColor(i, j, winner)[2]));
						pieces[i][j].getButton().setIcon(new ImageIcon(""));
						pieces[i][j].getButton().setEnabled(false);
					}
				}
			}
		}
	}
/**
 * This is called when a piece is selected and where it can move.
 * @param pieces - This is the piece that is clicked
 * @param i1 - This is the row location of the piece being clicked
 * @param j1 - This is the column location of the piece being clicked
 */
	public void pieceAction(Piece[][] pieces, final int i1, final int j1) {
		//System.out.println("Action[" + i1 + "," + j1 + "]: clicked : " + Main.getGame().getTurn());
		Piece piece = pieces[i1][j1]; // piece being clicked

		if (piece.getColor() != ' ') { // if piece does have a checker

			if (board.getSelectedPieces().size() > 0) {
				clearSelecting(pieces);
			}

			if (Main.getGame().hasTurn(piece.getColor())) { // if current turn == piece color reference
				piece.select(); // select piece clicked

				//System.out.println("b[" + i1 + "," + j1 + "]: " + pieces[i1][j1].getColor() + " sel " + pieces[i1][j1].isSelected());

				try {
					ArrayList<int[]> moves = board.canJump(i1, j1); // find locations to jump

					for (int i = 0; i < moves.size(); i++) { // highlight locations to jump
						Piece newPiece = pieces[moves.get(i)[0]][moves.get(i)[1]];
						newPiece.highlight();
						//System.out.println("b[" + moves.get(i)[0] + "," + moves.get(i)[1] + "]: " + pieces[i1][j1].getColor() + " high " + newPiece.isHighlighted());
					}
				} catch (Exception e1) {

				}
			}
		} else { // if piece does not have a checker

			if (piece.isHighlighted()) { // if its a highlighted piece
				//System.out.println("b[" + i1 + "," + j1 + "]: " + pieces[i1][j1].toString() + " was high");

				ArrayList<Piece> selected = board.getSelectedPieces(); // find selected pieces on board
																		// (should only be one piece that was selected)
				//System.out.println(selected.size() + " size");
				if (selected.size() == 1) { // if equal to 1, which should always be true

					Piece selPiece = selected.get(0); // get selected piece
					//System.out.println("b[" + i1 + "," + j1 + "]: " + pieces[i1][j1].toString() + " was sel");
					piece.copy(selPiece); // get the clicked piece and copy information from the selected piece
					if (piece.getColor() == 'R') {
						if (piece.getLocation()[0] == 7) {
							piece.setKing(true);
						}
					} else if (piece.getColor() == 'B') {
						if (piece.getLocation()[0] == 0) {
							piece.setKing(true);
						}
					}

					selPiece.select(); // deselect the selected piece
					selPiece.setColor(' '); // remove the checker from the selected piece
					selPiece.setKing(false); // remove king if there was a king of the selected piece

					//System.out.println("b[" + i1 + "," + j1 + "]: " + pieces[i1][j1].toString() + " is now " + pieces[i1][j1].getColor());

					piece.highlight(); // unhighlight the clicked piece

					ArrayList<Piece> jumped = board.getJumpedPieces(piece.getLocation()[0], piece.getLocation()[1],
							selPiece.getLocation()[0], selPiece.getLocation()[1]);
					//System.out.println("There were " + jumped.size() + " pieces jumped");
					if (jumped.size() > 0) { // if the jump had jumped over other pieces
						for (int i = 0; i < jumped.size(); i++) {
							Piece jumpedPiece = jumped.get(i);
							//System.out.println("Piece [" + jumpedPiece.getLocation()[0] + "," + jumpedPiece.getLocation()[1] + "] was jumped.");
							jumpedPiece.setColor(' ');
							jumpedPiece.setKing(false); // delete those checkers
						}
					}

					Main.getGame().nextTurn(); // proceed to next turn
				}
				clearSelecting(pieces); // clear any marks on board
			}
		}

		update(false);
	}
/**
 * This clears marks off the board
 * @param pieces - This is what piece/pieces have been selected 
 */
	public void clearSelecting(Piece[][] pieces) {
		for (int i = 0; i < pieces.length; i++) {
			for (int j = 0; j < pieces.length; j++) {
				Piece piece = pieces[i][j];
				if (piece.isHighlighted()) {
					piece.highlight();
				}
				if (piece.isSelected()) {
					piece.select();
				}
			}
		}
	}
	/**
	 * This sets the message
	 * @param message - This is the message that is being set; this is not used
	 */
	@Deprecated
	public void setMessage(String message) {
		this.message = null;
	}
	/**
	 * This sets the indicator color of the board
	 */
	public void setIndicator(Color color) {
		indicator.setBackground(color);
	}
	/**
	 * This gets the indicator color of the board
	 */
	public JButton getIndicator() {
		return indicator;
	}
}
