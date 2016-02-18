package lekro.rogueoid.map;

import java.awt.Point;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import lekro.rogueoid.RogueMath;
import lekro.rogueoid.entity.Entity;
import lekro.rogueoid.entity.Monster;
import lekro.rogueoid.entity.Player;

public class Level {

	public static final int DEFAULT_WIDTH = 63;
	public static final int DEFAULT_HEIGHT = 30;
	public static final int SECTOR_COUNT_X = 3;
	public static final int SECTOR_COUNT_Y = 3;
	
	public static final char EMPTY_SPACE = ' ';
	public static final char EMPTY_SPACE_FULLWIDTH = 0x3000;
	public static final char WALL = '#';
	public static final char WALL_FULLWIDTH = 0xFF03;
	public static final char FLOOR = '.';
	public static final char FLOOR_FULLWIDTH = 0xFF0A;
	public static final char MOB = 'M';
	public static final char MOB_FULLWIDTH = 0xFF2D;
	public static final char PLAYER = 'O';
	public static final char PLAYER_FULLWIDTH = 0xFF2F;
	
	public static final int ROOM_SKIP_CHANCE = 10;
	public static final int ROOM_SKIP_MAX = 2;
	
	private int height;
	private int width;
	private Room[][] rooms;
	private char[][] charMap;
	
	private Set<Entity> entities;
	
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
		
		entities = new HashSet<Entity>();
		
		for (Room[] rms : rooms) {
			for (Room r : rms) {
				if (r == null) continue;
				int x = random.nextInt(r.width-2) + r.x + 1;
				int y = random.nextInt(r.height-2) + r.y + 1;
				Monster m = new Monster(x, y, this);
				entities.add(m);
			}
		}
		
		charMap = new char[width][height];
		for (char[] boo : charMap) {
			Arrays.fill(boo, new Character(EMPTY_SPACE));
		}
		
		for (int i = 0; i < rooms.length; i++) {
			for (int j = 0; j < rooms[i].length; j++) {
				Room r = rooms[i][j];
				
				if (r == null) continue;
				
				for (int x = r.x; x < r.x+r.width; x++) {
					charMap[x][r.y] = WALL;
					charMap[x][r.y+r.height-1] = WALL;
				}
				for (int y = r.y; y < r.y+r.height; y++) {
					charMap[r.x][y] = WALL;
					charMap[r.x+r.width-1][y] = WALL;
				}
				
				for (int x = r.x + 1; x < r.x+r.width - 1; x++) {
					for (int y = r.y + 1; y < r.y+r.height - 1; y++) {
						charMap[x][y] = FLOOR;
					}
				}
			}	
		}
		
	}
	
	public Point getValidLocation() {
		Random rand = new Random();
		Point p = null;
		while (true) {
			
			Room r = rooms[rand.nextInt(SECTOR_COUNT_X)][rand.nextInt(SECTOR_COUNT_Y)];
			
			if (r == null) continue;
			
			int x = rand.nextInt(r.width-2) + r.x + 1;
			int y = rand.nextInt(r.height-2) + r.y + 1;
			p = new Point(x, y);
			break;
		}
		return p;
	}
	
	public boolean isValidLocation(int x, int y) {
		/*
		for (Room[] rm : rooms) {
			for (Room r : rm) {
				if (r == null) continue;
				if (y > r.y && y < r.y+r.height - 1 && x > r.x && x < r.x+width - 1) {
					return true;
				}
			}
		}
		*/
		char[][] map = toCharArray();
		if (map[x][y] == '.') return true;
		return false;
	}
	
	public char[][] toCharArray() {

		char[][] map = new char[charMap.length][charMap[0].length];
		
		for (int i = 0; i < charMap.length; i++) {
			System.arraycopy(charMap[i], 0, map[i], 0, charMap[i].length);
		}
		
		for (Entity e : entities) {
			map[e.getX()][e.getY()] = e.getRepresentation();
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
	
	public Set<Entity> getEntities() {
		return entities;
	}
	
	public Player getPlayer() {
		for (Entity e : getEntities()) {
			if (e instanceof Player) return (Player) e;
		}
		return null;
	}
	
}
