package lekro.rogueoid.entity;

import java.awt.Point;

import lekro.rogueoid.map.Level;


public class Monster extends Entity {
	
	private int direction;
	private int stale = 0;
	
	public Monster(int x, int y, Level level) {
		super(x, y, level, 5);
		setRepresentation(Level.MOB);
		setName("Scary Monster");
		direction = getRand().nextInt(4);
		// TODO Auto-generated constructor stub
	}
	
	public Monster(Point location, Level level) {
		this(location.x, location.y, level);
	}

	@Override
	public void tick() {
		boolean possible;
		do {
			possible = move(direction);
			stale++;
			if (!possible || stale > 3) {
				direction = getRand().nextInt(4);
				stale = 0;
			}
		} while (!possible);
	}

	@Override
	public void receiveAttack(Entity other) {
		if (getHealth() <= 0) getLevel().getEntities().remove(this);
	}
	
	@Override
	public void attack(Entity other) {
		if (other instanceof Monster) return;
		super.attack(other);
	}

}
