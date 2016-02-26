package lekro.rogueoid.map;

import java.awt.Rectangle;

public class Room extends Rectangle {
	
	private boolean found = false;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//public int height, width, x, y;

	public Room(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	public void find() {
		found = true;
	}
	
	public boolean isFound() {
		return found;
	}
	
	public String toString() {
		return width+"x"+height+" at ("+x+", "+y+")";
	}
	
}
