package ben.checkers;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

class Panel extends JFrame {

	Color color;
	
	int value = 0;
	
	Panel() {
		if (value == 0) {
			color = Color.GRAY;
			value++;
		} else if (value == 1) {
			color = Color.WHITE;
			value--;
		}
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		g.setColor(color);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
