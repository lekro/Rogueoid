package lekro.rogueoid.entity;

import java.awt.Point;
import java.util.Random;

import lekro.rogueoid.map.Level;

public abstract class Entity {

	private int x, y;
	private Random rand;
	private Level level;
	private int health;
	private int maxHealth;
	private char representation;
	
	public Entity(int x, int y, Level level, int maxHealth) {
		setX(x);
		setY(y);
		this.level = level;
		this.maxHealth = maxHealth;
		this.health = maxHealth;
		this.representation = 'E';
		rand = new Random();
	}
	
	public abstract void tick();
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getHealth() {
		return health;
	}
	
	public char getRepresentation() {
		return representation;
	}
	
	public void setRepresentation(char representation) {
		this.representation = representation;
	}
	
	public double getPercentHealth() {
		return (double) health / (double) maxHealth;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public void move(int direction) {
		int x = getX();
		int y = getY();
		switch (direction) {
		case 0: // 0 radians
			x++;
			break;
		case 1: // pi/2 radians, remember y is 0 at top
			y--;
			break;
		case 2: // pi radians
			x--;
			break;
		case 3: // 3*pi/2 radians, remember y is 0 at top
			y++;
			break;
		}
		if (getLevel().isValidLocation(x, y)) {
			setX(x);
			setY(y);
		}
	}
	
	public Point getCoordinates() {
		return new Point(x, y);
	}
	
	public Random getRand() {
		return rand;
	}
	
	public Level getLevel() {
		return level;
	}
	
}
