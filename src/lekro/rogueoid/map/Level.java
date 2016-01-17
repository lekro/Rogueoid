package lekro.rogueoid.map;

import java.util.Arrays;
import java.util.Random;

public class Level {

	public static final int DEFAULT_WIDTH = 63;
	public static final int DEFAULT_HEIGHT = 63;
	public static final int SECTOR_COUNT_X = 3;
	public static final int SECTOR_COUNT_Y = 3;
	private int height;
	private int width;
	private Room[][] rooms;
	
	public static final char EMPTY_SPACE = ' ';
	public static final char WALL = '#';
	public static final char FLOOR = '.';
	
	public Level() {
		this(DEFAULT_HEIGHT, DEFAULT_WIDTH);
	}
	
	public Level(int height, int width) {
		this.height = height;
		this.width = width;
		
		Random random = new Random();
		rooms = new Room[SECTOR_COUNT_X][SECTOR_COUNT_Y];
		
		for (int i = 0; i < rooms.length; i++) {
			for (int j = 0; j < rooms[i].length; j++) {
				int sectorHeight = height / SECTOR_COUNT_Y;
				int sectorWidth = width / SECTOR_COUNT_X;
				int roomHeight = random.nextInt(sectorHeight - 5) + 3;
				int roomWidth = random.nextInt(sectorWidth - 5) + 3;
				int roomY = random.nextInt(sectorHeight - roomHeight) + j*sectorHeight;
				int roomX = random.nextInt(sectorWidth - roomWidth) + i*sectorWidth;
				rooms[i][j] = new Room(roomX, roomY, roomHeight, roomWidth);
			}
		}
		
	}
	
	public char[][] grabMap() {

		char[][] map = new char[width][height];
		Arrays.fill(map, EMPTY_SPACE);
		
		for (int i = 0; i < rooms.length; i++) {
			for (int j = 0; j < rooms[i].length; j++) {
				Room r = rooms[i][j];
				System.out.println("Room ("+i+","+j+") - "+r.toString());
				
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
	
	public static void printMap(char[][] map) {

		for (int i = 0; i < map[0].length; i++) {
			for (int j = 0; j < map.length; j++) {
				System.out.print(map[j][i]);
			}
			System.out.println();
		}
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
}
