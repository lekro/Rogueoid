package lekro.rogueoid.entity;

import lekro.rogueoid.map.Level;


public class Monster extends Entity {
	
	private int direction;
	private int stale = 0;
	
	public Monster(int x, int y, Level level) {
		super(x, y, level);
		direction = getRand().nextInt(4);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick() {
		int x = getX();
		int y = getY();
		do {
			x = getX();
			y = getY();
			switch (direction) {
			case 0:
				x++;
				break;
			case 1:
				x--;
				break;
			case 2:
				y++;
				break;
			case 3:
				y--;
				break;
			}
			stale++;
			if (!getLevel().isValidLocation(x, y) || stale > 3) {
				direction = getRand().nextInt(4);
				stale = 0;
			}
		} while(!getLevel().isValidLocation(x, y));
		setX(x);
		setY(y);
	}

}
