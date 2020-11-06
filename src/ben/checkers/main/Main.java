package ben.checkers.main;

import javax.swing.SwingUtilities;

import ben.checkers.gui.Board;

public class Main {

	public static void main(String[] args) {
		Board checkers = new Board(8, 8);
		System.out.println("Bye Ben!");
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				checkers.display(true);
			}

		});

	}

}
