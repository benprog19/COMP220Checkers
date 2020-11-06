package ben.checkers.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import ben.checkers.team.Team;

public class Board {

	private JFrame frame;
	private JPanel gui;
	private Button[][] buttons;
	private JPanel checkersBoard;
	private JLabel message;
	private String colsLabel;
	private String rowLabel;

	int rows;
	int cols;

	final int width = 400;
	final int height = 400;

	Team redTeam;
	Team blackTeam;

	public Board(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;

		frame = new JFrame();
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("Checkers");

		gui = new JPanel(new BorderLayout(3, 3));

		buttons = new Button[rows][cols];

		message = new JLabel("Testing checkers");
		
		colsLabel = "ABCDEFGH";
		
		redTeam = new Team("red");
		blackTeam = new Team("black");

		initializeGUI();
	}

	public void initializeGUI() {
		gui.setBorder(new EmptyBorder(3, 3, 3, 3));
		JToolBar tools = new JToolBar();
		tools.setFloatable(false);
		gui.add(tools, BorderLayout.PAGE_START);
		tools.add(new JButton("Reset"));
		tools.addSeparator();
		tools.add(message);

		gui.add(new JLabel(""), BorderLayout.LINE_START);

		checkersBoard = new JPanel(new GridLayout(0, 9));
		checkersBoard.setBorder(new LineBorder(Color.BLACK));

		Insets insets = new Insets(0, 0, 0, 0);
		ImageIcon red = new ImageIcon("red.png");
		ImageIcon black = new ImageIcon("black.png");
		System.out.println("rows: " + buttons.length);
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons.length; j++) {
				JButton b = new JButton();
				b.setMargin(insets);
				Button button = new Button(i, j, b, null, this);
				b.setBackground(new Color(originalBackgroundColor(i, j)[0], originalBackgroundColor(i, j)[1],
						originalBackgroundColor(i, j)[2]));
				if (!((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0))) {
					if (j >= 0 && j < 3) {
						if (i % 2 == 0 || i % 2 == 1) {
							button.setTeam(redTeam);
							button.getButtonReference().setIcon(red);
						}
					}
					if (j >= 5 && j <= 7) {
						if (i % 2 == 0 || i % 2 == 1) {
							button.setTeam(blackTeam);
							button.getButtonReference().setIcon(black);
						}
					}
					button.listen();
				}
				buttons[i][j] = button;
			}
		}
		checkersBoard.add(new JLabel(""));
		for (int i = 0; i < buttons.length; i++) {
			checkersBoard.add(new JLabel(colsLabel.substring(i, i + 1), SwingConstants.CENTER));
		}
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons.length; j++) {
				switch (j) {
				case 0:
					checkersBoard.add(new JLabel("" + (i + 1), SwingConstants.CENTER));
				default:
					checkersBoard.add(buttons[j][i].getButtonReference());
				}
			}
		}

		gui.add(checkersBoard);

		frame.add(gui);
	}

	public Team getRedTeam() {
		return redTeam;
	}

	public Team getBlackTeam() {
		return blackTeam;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void display(boolean display) {

		// Create and set up the window.

		// Display the window.

		frame.pack();

		frame.setVisible(true);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public Button[][] getButtons() {
		return buttons;
	}

	public int[] originalBackgroundColor(int i, int j) {
		int[] colors = new int[3];
		if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) {
			colors[0] = 176;
			colors[1] = 245;
			colors[2] = 185;
		} else {
			colors[0] = 154;
			colors[1] = 237;
			colors[2] = 165;
		}
		return colors;
	}

	public void resetColoring() {
		for (int i = 0; i < getButtons().length; i++) {
			for (int j = 0; j < getButtons()[i].length; j++) {
				getButtons()[i][j].getButtonReference().setBackground(new Color(originalBackgroundColor(i, j)[0],
						originalBackgroundColor(i, j)[1], originalBackgroundColor(i, j)[2]));
				getButtons()[i][j].setHighlighted(false);
				getButtons()[i][j].setSuggested(null);
			}
		}
	}
	
	public boolean canJump() {
		return true;
	}
	
	public void jump() {
		
	}
}
