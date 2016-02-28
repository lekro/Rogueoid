package lekro.rogueoid.map.mask;

import lekro.rogueoid.map.Level;

public abstract class MapMask {
	
	private int width, height;
	public static final char MASK = Level.EMPTY_SPACE;
	
	public MapMask(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public char[][] apply(char[][] map) {
		if (map.length != getWidth() || map[0].length != getHeight())
			throw new IllegalArgumentException("Map to mask off must be of the same size!");
		boolean[][] fow = toBooleanArray();
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = (fow[i][j]) ? map[i][j] : MASK;
			}
		}
		return map;
	}
	
	public abstract boolean[][] toBooleanArray();
	public abstract void clear();
	public abstract void append(boolean[][] map);
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
}
