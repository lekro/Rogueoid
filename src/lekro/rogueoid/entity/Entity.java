package lekro.rogueoid.entity;

import java.awt.Point;
import java.util.Random;

public abstract class Entity {

	private int x, y;
	private Random rand;
	
	
	
	protected Entity(int x, int y) {
		setX(x);
		setY(y);
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
	
	public Point getCoordinates() {
		return new Point(x, y);
	}
	
	public Random getRand() {
		return rand;
	}
	
}
