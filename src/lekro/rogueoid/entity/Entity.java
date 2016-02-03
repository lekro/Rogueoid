package lekro.rogueoid.entity;

import java.awt.Point;
import java.util.Random;

import lekro.rogueoid.map.Level;

public abstract class Entity {

	private int x, y;
	private Random rand;
	private Level level;
	
	
	public Entity(int x, int y, Level level) {
		setX(x);
		setY(y);
		this.level = level;
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
