package ben.checkers;

import javax.swing.SwingUtilities;

class Main {

	static void main(String[] args) {
		Board checkers = new Board(8, 8);
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				checkers.display(true);
			}

		});

	}

}
