package lekro.rogueoid.map.mask;

public class UniformMapMask extends MapMask {

	boolean[][] mask;
	
	public UniformMapMask(int width, int height) {
		super(width, height);
		mask = new boolean[width][height];
	}

	@Override
	public boolean[][] toBooleanArray() {
		return mask;
	}

	@Override
	public void clear() {
		mask = new boolean[getWidth()][getHeight()];
	}

	@Override
	public void append(boolean[][] map) {
		if (map.length != getWidth() || map[0].length != getHeight())
			throw new IllegalArgumentException("Map to append must be of the same size!");
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				mask[i][j] = mask[i][j] || map[i][j];
			}
		}
	}

}
