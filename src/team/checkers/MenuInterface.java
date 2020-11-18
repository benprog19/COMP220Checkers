package team.checkers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class MenuInterface {
	
	private Board board;
	
	private JFrame frame;
	private JPanel gui;
	private JPanel checkersBoard;
	private JLabel message;
	private String colsLabel;
	private String rowLabel;

	final private int width = 600;
	final private int height = 600;
	
	public MenuInterface(Board board, boolean display) {
		this.board = board;
		
		frame = new JFrame();
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("Checkers");
		gui = new JPanel(new BorderLayout(4, 4));
		message = new JLabel("Testing checkers");
		colsLabel = "ABCDEFGH";
		
		gui.setBorder(new EmptyBorder(4, 4, 4, 4));
		JToolBar tools = new JToolBar();
		tools.setFloatable(false);
		gui.add(tools, BorderLayout.PAGE_START);
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
	
	private int[] originalBackgroundColor(int i, int j) {
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
			if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) {
				colors[0] = 176;
				colors[1] = 245;
				colors[2] = 185;
			} else {
				colors[0] = 154;
				colors[1] = 237;
				colors[2] = 165;
			}
		}
		return colors;
	}
	
	public void display(boolean display) {

		// Create and set up the window.

		// Display the window.
		//frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void update(boolean firstSetup) {
		Insets insets = new Insets(0, 0, 0, 0);
		ImageIcon red = new ImageIcon("content/red.png");
		ImageIcon black = new ImageIcon("content/black.png");
		ImageIcon redKing = new ImageIcon("content/red_king.png");
		ImageIcon blackKing = new ImageIcon("content/black_king.png");
		//System.out.println("rows: " + board.getPieces().length);
		Piece[][] pieces = board.pieces();
		for (int i = 0; i < pieces.length; i++) {
			for (int j = 0; j < pieces.length; j++) {
				
				JButton b;
				
				if (firstSetup) {
					b = new JButton();
					b.setMargin(insets);
					final int i1 = i;
					final int j1 = j;
					b.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							System.out.println("Action[" + i1 + "," + j1 + "]: clicked");
							Piece piece = pieces[i1][j1];
							if (piece.getColor() != ' ') {
								piece.select();
								System.out.println("b[" + i1 + "," + j1 + "]: " + pieces[i1][j1].getColor() + " sel " + pieces[i1][j1].isSelected());
								try {
									ArrayList<int[]> moves = board.canJump(i1, j1);
									for (int i = 0; i < moves.size(); i++) {
										Piece newPiece = pieces[moves.get(i)[0]][moves.get(i)[1]];
										newPiece.highlight();
										System.out.println("b[" + moves.get(i)[0] + "," + moves.get(i)[1] + "]: " + pieces[i1][j1].getColor() + " high " + newPiece.isHighlighted());
									}
								} catch (Exception e1) {
									
								}
							} else {
								if (piece.isHighlighted()) {
									System.out.println("b[" + i1 + "," + j1 + "]: " + pieces[i1][j1].toString() + " was high");
									ArrayList<Piece> selected = board.getSelectedPieces();
									System.out.println(selected.size() + " size");
									if (selected.size() == 1) {
										Piece selPiece = selected.get(0);
										System.out.println("b[" + i1 + "," + j1 + "]: " + pieces[i1][j1].toString() + " was sel");
										piece.copy(selPiece);
										selPiece.select();
										selPiece.setColor(' ');
										selPiece.setKing(false);
										System.out.println("b[" + i1 + "," + j1 + "]: " 
										+ pieces[i1][j1].toString() + " is now " + pieces[i1][j1].getColor());
										piece.highlight();
									}
									clearSelecting(pieces);
								}
							}
							
							update(false);
						}
						
					});
					pieces[i][j].setButton(b);
				} else {
					b = pieces[i][j].getButton();
				}
				
				b.setBackground(new Color(originalBackgroundColor(i, j)[0], originalBackgroundColor(i, j)[1],
						originalBackgroundColor(i, j)[2]));
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
	}
	
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
	
}
