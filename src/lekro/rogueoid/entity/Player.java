package lekro.rogueoid.entity;

import lekro.rogueoid.map.Level;


public class Player extends Entity {

	int direction = -1;
	boolean couldMove;
	
	public Player(int x, int y, Level level) {
		super(x, y, level, 10);
		level.getFogOfWar()[x][y] = true;
		setRepresentation(Level.PLAYER);
		setName("Player");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick() {
		updateDisplay();
		//setHealth(getHealth()-1);
		couldMove = move(direction);
		getLevel().getFogOfWar()[getX()][getY()] = true;
	}
	
	public void updateDisplay() {
		if (getHealth() <= 0) {
			setRepresentation( (char) 'X');
		}
	}
	
	public void moveLater(int direction) {
		this.direction = direction;
	}
	
	public boolean isDesiredMovePossible() {
		return couldMove;
	}

}
