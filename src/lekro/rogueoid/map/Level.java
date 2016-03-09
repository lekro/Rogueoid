package lekro.rogueoid.map;

import java.awt.Point;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import lekro.rogueoid.RogueLoop;
import lekro.rogueoid.RogueMath;
import lekro.rogueoid.entity.Entity;
import lekro.rogueoid.entity.Monster;
import lekro.rogueoid.entity.Player;
import lekro.rogueoid.entity.attributes.VisionLevel;
import lekro.rogueoid.entity.effects.HealthPotionEffect;
import lekro.rogueoid.entity.tile.Potion;
import lekro.rogueoid.entity.tile.Staircase;

public class Level {

	public static final int DEFAULT_WIDTH = 63;
	public static final int DEFAULT_HEIGHT = 30;
	public static final int SECTOR_COUNT_X = 3;
	public static final int SECTOR_COUNT_Y = 3;
	
	public static final char EMPTY_SPACE = ' ';
	public static final char WALL_X = '-';
	public static final char WALL_Y = '|';
	public static final char CORNER_1 = '0';
	public static final char CORNER_2 = '0';
	public static final char FLOOR = '.';
	public static final char PATH_FLOOR = '#';
	public static final char DOOR = '+';
	public static final char MOB = 'M';
	public static final char PLAYER = 'O';
	
	public static final Set<Character> PASSABLE = new HashSet<Character>();
	
	static {
		PASSABLE.add(FLOOR);
		PASSABLE.add(PATH_FLOOR);
		PASSABLE.add(DOOR);
	}
	
	public static final Set<Character> TRANSPARENT = new HashSet<Character>();
	
	static {
		TRANSPARENT.add(FLOOR);
		TRANSPARENT.add(PATH_FLOOR);
		TRANSPARENT.add(DOOR);
	}
	
	public static final int ROOM_SKIP_CHANCE = 10;
	public static final int ROOM_SKIP_MAX = 2;
	
	private int height;
	private int width;
	private Room[][] rooms;
	private Set<Path> paths;
	private char[][] charMap;
	
	private Set<Entity> entities;
	private Player player;
	private RogueLoop loop;
	
	public Level() {
		this(DEFAULT_HEIGHT, DEFAULT_WIDTH);
	}
	
	public Level(int height, int width) {
		this.height = height;
		this.width = width;
		
		Random random = new Random();
		rooms = new Room[SECTOR_COUNT_X][SECTOR_COUNT_Y];
		paths = new HashSet<Path>();
		
		int deleteQuota = ROOM_SKIP_MAX;
		
		// Generate (or not) the rooms
		
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
					if (!(roomHeight < 5 || roomHeight >= sectorHeight - 2
							|| roomWidth < 5 || roomWidth >= sectorWidth - 2)) {
						break;
					}
				}
				int roomY = random.nextInt(sectorHeight - roomHeight) + j*sectorHeight;
				int roomX = random.nextInt(sectorWidth - roomWidth) + i*sectorWidth;
				rooms[i][j] = new Room(roomX, roomY, roomWidth, roomHeight);
			}
		}

		// Generate paths:
		// Paths in Y-direction:
		
		for (int i = 0; i < rooms.length; i++) {
			for (int j = 0; j < rooms[i].length - 1; j++) {
				if (rooms[i][j] != null && rooms[i][j+1] != null)
					paths.add(new Path(rooms[i][j], rooms[i][j+1]));
			}
		}
		
		// Paths in X-direction:
		
		for (int i = 0; i < rooms.length - 1; i++) {
			for (int j = 0; j < rooms[i].length; j++) {
				if (rooms[i][j] != null && rooms[i+1][j] != null)
					paths.add(new Path(rooms[i][j], rooms[i+1][j]));
			}
		}
		
		entities = new HashSet<Entity>();
		
		// We are placing exactly one monster in a room (for now) :
		
		for (Room[] rms : rooms) {
			for (Room r : rms) {
				if (r == null) continue;
				int x = random.nextInt(r.width-2) + r.x + 1;
				int y = random.nextInt(r.height-2) + r.y + 1;
				Monster m = new Monster(x, y, this);
				entities.add(m);
			}
		}
		
		Staircase st = new Staircase(getRandomLocation(), this);
		entities.add(st);
		Potion po = new Potion(getRandomLocation(), this, new HealthPotionEffect(0, 1));
		entities.add(po);
		
		charMap = new char[width][height];
		
		for (char[] boo : charMap) {
			Arrays.fill(boo, new Character(EMPTY_SPACE));
		}
		
		for (int i = 0; i < rooms.length; i++) {
			for (int j = 0; j < rooms[i].length; j++) {
				Room r = rooms[i][j];
				
				if (r == null) continue;
				
				for (int x = r.x; x < r.x+r.width; x++) {
					charMap[x][r.y] = WALL_X;
					charMap[x][r.y+r.height-1] = WALL_X;
				}
				for (int y = r.y; y < r.y+r.height; y++) {
					charMap[r.x][y] = WALL_Y;
					charMap[r.x+r.width-1][y] = WALL_Y;
				}
				
				charMap[r.x][r.y] = charMap[r.x+r.width-1][r.y+r.height-1] = CORNER_1;
				charMap[r.x][r.y+r.height-1] = charMap[r.x+r.width-1][r.y] = CORNER_2;
				
				for (int x = r.x + 1; x < r.x+r.width - 1; x++) {
					for (int y = r.y + 1; y < r.y+r.height - 1; y++) {
						charMap[x][y] = FLOOR;
					}
				}
			}	
		}
		
		for (Path p : paths) p.displayCharMap(charMap);		
		
	}
	
	public Point getRandomLocation() {
		Random rand = new Random();
		Point p = null;
		while (true) {
			
			Room r = rooms[rand.nextInt(SECTOR_COUNT_X)][rand.nextInt(SECTOR_COUNT_Y)];
			
			if (r == null) continue;
			
			int x = rand.nextInt(r.width-2) + r.x + 1;
			int y = rand.nextInt(r.height-2) + r.y + 1;
			p = new Point(x, y);
			for (Entity e : getEntities()) {
				if (p.equals(e.getLocation())) continue;
			}
			break;
		}
		return p;
	}
	
	public Room getRoom(int x, int y) {
		for (Room[] rms : rooms) {
			for (Room r : rms) {
				if (r == null) continue;
				if (r.contains(x, y)) return r;
			}
		}
		return null;
	}
	
	public boolean isValidLocation(int x, int y) {
		char[][] map = toCharArray();
		if (PASSABLE.contains(map[x][y])) return true;
		return false;
	}
	
	public char[][] getCharMap() {
		return charMap;
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
	
	public char[][] getMaskedMap() {
		char[][] map = toCharArray();
		if (getPlayer().getVisionLevel().equals(VisionLevel.OMNISCIENT)) return map;
		return getPlayer().getMapMask().apply(map);
	}
	
	public String toString() {
		char[][] map = getMaskedMap();
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
	
	public Set<Entity> getEntitiesAtLocation(int x, int y) {
		Set<Entity> foundEntities = new HashSet<Entity>();
		for (Entity e : entities) {
			if (e.getX() == x && e.getY() == y) {
				foundEntities.add(e);
			}
		}
		return foundEntities;
	}
	
	public Player getPlayer() {
		for (Entity e : getEntities()) {
			if (e instanceof Player) {
				player = (Player) e;
			}
		}
		return player;
	}
	
	public RogueLoop getLoop() {
		return loop;
	}
	
	public void setLoop(RogueLoop loop) {
		this.loop = loop;
	}
	
}
