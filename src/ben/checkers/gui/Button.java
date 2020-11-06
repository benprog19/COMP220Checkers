package ben.checkers.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import ben.checkers.team.Team;

public class Button {

	int x;
	int y;
	JButton button;
	Team team;
	Board checkersMenu;

	boolean highlighted;
	Button suggested;
	Button prevButton;

	public Button(int x, int y, JButton button, Team team, Board checkersMenu) {
		this.x = x;
		this.y = y;
		this.button = button;
		this.team = team;
		this.checkersMenu = checkersMenu;

		this.highlighted = false;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public JButton getButtonReference() {
		return button;
	}

	public Team getTeam() {
		return team;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setButtonReference(JButton button) {
		this.button = button;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public boolean hasTeam() {
		if (this.team != null) {
			return true;
		}
		return false;
	}

	public void setHighlighted(boolean highlight) {
		this.highlighted = highlight;
		if (highlight) {
			button.setBackground(new Color(255, 255, 255));
		} else {
			button.setBackground(new Color(getCheckerBoard().originalBackgroundColor(x, y)[0],
					getCheckerBoard().originalBackgroundColor(x, y)[1],
					getCheckerBoard().originalBackgroundColor(x, y)[2]));
		}
	}
	
	public boolean isHighlighted() {
		return highlighted;
	}
	
	public void setSuggested(Button suggested) {
		this.suggested = suggested;
	}
	
	public boolean isSuggested() {
		if (this.suggested != null) {
			return false;
		} 
		return true;
	}
	
	public Button getSuggested() {
		return suggested;
	}
	
	public void setPreviousButton(Button button) {
		this.prevButton = button;
	}
	
	public boolean hasPrevious() {
		if (prevButton != null) {
			return true;
		}
		return false;
	}
	
	public Button getPrevious() {
		return prevButton;
	}

	public void listen() {
		Button b = this;
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getCheckerBoard().resetColoring();
				System.out.print("[" + x + "," + y + "] ");
				if (team != null) {
					if (team.getName().equals("red")) {
						if (y < 7) {
							if (x < 7) {
								Button ref = getCheckerBoard().getButtons()[x+1][y+1];
								if (ref.getTeam() == null) {
									ref.setHighlighted(true);
									ref.setSuggested(b); // suggested not working?
									ref.setPreviousButton(b);
									System.out.print("[" + ref.getSuggested().toString() + ":" + ref.getPrevious().toString() + "] ");
								}
							}
							if (x > 0) {
								Button ref = getCheckerBoard().getButtons()[x-1][y+1];
								if (ref.getTeam() == null) {
									ref.setHighlighted(true);
									ref.setSuggested(b);
									ref.setPreviousButton(b);
								}
							}
						}
					} else if (team.getName().equals("black")) {
						if (y > 0) {
							if (x < 7) {
								Button ref = getCheckerBoard().getButtons()[x+1][y-1];
								if (ref.getTeam() == null) {
									ref.setHighlighted(true);
									ref.setSuggested(b);
									ref.setPreviousButton(b);
								}
							}
							if (x > 0) {
								Button ref = getCheckerBoard().getButtons()[x-1][y-1];
								if (ref.getTeam() == null) {
									ref.setHighlighted(true);
									ref.setSuggested(b);
									ref.setPreviousButton(b);
								}
							}
						}
					}
				} else {
					System.out.print("no team");
					if (isSuggested()) {
						System.out.print("suggested\n");
						setTeam(getPrevious().getTeam());
						setHighlighted(false);
						setSuggested(null);
						getButtonReference().setIcon(new ImageIcon(getTeam().getName() + ".png"));
						getPrevious().getButtonReference().setIcon(null);
						getPrevious().setTeam(null);
						getPrevious().setHighlighted(false);
						getPrevious().setSuggested(null);
						
					}
				}

				
				if (highlighted) {
					setHighlighted(false);
				} else {
					setHighlighted(true);
				}
				System.out.println();
			}

		});
	}

	public Board getCheckerBoard() {
		return checkersMenu;
	}

}
