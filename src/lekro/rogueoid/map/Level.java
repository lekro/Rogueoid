package lekro.rogueoid.map;

import java.util.Arrays;
import java.util.Random;

import lekro.rogueoid.RogueMath;

public class Level {

	public static final int DEFAULT_WIDTH = 63;
	public static final int DEFAULT_HEIGHT = 30;
	public static final int SECTOR_COUNT_X = 3;
	public static final int SECTOR_COUNT_Y = 3;
	private int height;
	private int width;
	private Room[][] rooms;
	
	public static final char EMPTY_SPACE = ' ';
	public static final char WALL = '#';
	public static final char FLOOR = '.';
	
	public static final int ROOM_SKIP_CHANCE = 10;
	public static final int ROOM_SKIP_MAX = 2;
	
	public Level() {
		this(DEFAULT_HEIGHT, DEFAULT_WIDTH);
	}
	
	public Level(int height, int width) {
		this.height = height;
		this.width = width;
		
		Random random = new Random();
		rooms = new Room[SECTOR_COUNT_X][SECTOR_COUNT_Y];
		
		int deleteQuota = ROOM_SKIP_MAX;
		
		for (int i = 0; i < rooms.length; i++) {
			for (int j = 0; j < rooms[i].length; j++) {
				if (deleteQuota > 0) {
					int deleteChance = random.nextInt(100);
					if (deleteChance < ROOM_SKIP_CHANCE) {
						deleteQuota--;
						rooms[i][j] = null;
						// We will skip some rooms
						continue;
					}
				}
				
				int sectorHeight = height / SECTOR_COUNT_Y;
				int sectorWidth = width / SECTOR_COUNT_X;
				int roomHeight = 0;
				int roomWidth = 0;
				while (true) {
					roomHeight = RogueMath.roll(sectorHeight / 2, sectorHeight);
					roomWidth = RogueMath.roll(sectorWidth / 2, sectorWidth);
					if (!(roomHeight < 5 || roomHeight >= sectorHeight - 1
							|| roomWidth < 5 || roomWidth >= sectorWidth - 1)) {
						break;
					}
				}
				int roomY = random.nextInt(sectorHeight - roomHeight) + j*sectorHeight;
				int roomX = random.nextInt(sectorWidth - roomWidth) + i*sectorWidth;
				rooms[i][j] = new Room(roomX, roomY, roomHeight, roomWidth);
			}
		}
		
	}
	
	public char[][] toCharArray() {

		char[][] map = new char[width][height];
		for (char[] boo : map) {
			Arrays.fill(boo, new Character(EMPTY_SPACE));
		}
		
		for (int i = 0; i < rooms.length; i++) {
			for (int j = 0; j < rooms[i].length; j++) {
				Room r = rooms[i][j];
				
				if (r == null) continue;
				
				for (int x = r.x; x < r.x+r.width; x++) {
					map[x][r.y] = WALL;
					map[x][r.y+r.height-1] = WALL;
				}
				for (int y = r.y; y < r.y+r.height; y++) {
					map[r.x][y] = WALL;
					map[r.x+r.width-1][y] = WALL;
				}
				
				for (int x = r.x + 1; x < r.x+r.width - 1; x++) {
					for (int y = r.y + 1; y < r.y+r.height - 1; y++) {
						map[x][y] = FLOOR;
					}
				}
			}	
		}
		
		return map;
	}
	
	public String toString() {
		char[][] map = toCharArray();
		StringBuilder sb = new StringBuilder((height+1)*width);
		for (int i = 0; i < map[0].length; i++) {
			for (int j = 0; j < map.length; j++) {
				sb.append(map[j][i]);
			}
			sb.append('\n');
		}
		return sb.toString();
	}
	
	public Room[][] getRooms() {
		return rooms;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
}