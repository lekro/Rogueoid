package lekro.rogueoid.map;

import java.util.Random;

public class Level {

	public static final int DEFAULT_HEIGHT = 63;
	public static final int DEFAULT_WIDTH = 63;
	public static final int SECTOR_COUNT_X = 3;
	public static final int SECTOR_COUNT_Y = 3;
	private int height;
	private int width;
	
	public Level() {
		this(DEFAULT_HEIGHT, DEFAULT_WIDTH);
	}
	
	public Level(int height, int width) {
		this.height = height;
		this.width = width;
		
		Random random = new Random();
		Room[][] rooms = new Room[SECTOR_COUNT_X][SECTOR_COUNT_Y];
		
		for (int i = 0; i < rooms.length; i++) {
			for (int j = 0; j < rooms[i].length; j++) {
				int sectorHeight = height / SECTOR_COUNT_Y;
				int sectorWidth = width / SECTOR_COUNT_X;
				int roomHeight = random.nextInt(sectorHeight - 5) + 3;
				int roomWidth = random.nextInt(sectorWidth - 5) + 3;
				int roomY = random.nextInt(sectorHeight - roomHeight);
				int roomX = random.nextInt(sectorWidth - roomWidth);
				rooms[i][j] = new Room(roomX, roomY, roomHeight, roomWidth);
			}
		}
		
	}
	
}
