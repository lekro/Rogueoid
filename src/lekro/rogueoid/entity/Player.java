package lekro.rogueoid.entity;

import lekro.rogueoid.map.Level;


public class Player extends Entity {

	int direction = -1;
	
	public Player(int x, int y, Level level) {
		super(x, y, level);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick() {
		move(direction);
	}
	
	public void moveLater(int direction) {
		this.direction = direction;
	}

}
