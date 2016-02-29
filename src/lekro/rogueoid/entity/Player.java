package lekro.rogueoid.entity;

import lekro.rogueoid.entity.attributes.MapLevel;
import lekro.rogueoid.entity.attributes.VisionLevel;
import lekro.rogueoid.map.Level;
import lekro.rogueoid.map.Room;
import lekro.rogueoid.map.mask.MapMask;


public class Player extends Entity {

	private int direction = -1;
	private boolean couldMove;
	private int mapAge;
	
	// TODO select following based on some parameters:
	private MapLevel mapLevel = MapLevel.MARAUDERS;
	private VisionLevel visionLevel = VisionLevel.LIT;
	
	private MapMask mask;
	
	public Player(int x, int y, Level level) {
		super(x, y, level, 10);
		level.getEntities().add(this);
		mask = getMapLevel().toMapMask(level.getWidth(), level.getHeight());
		discoverLand(getX(), getY());
		setRepresentation(Level.PLAYER);
		setName("Player");
	}

	@Override
	public void tick() {
		updateDisplay();
		couldMove = move(direction);
		if (couldMove) discoverLand(getX(), getY());
	}
	
	public void discoverLand(int x, int y) {
		VisionLevel v = getVisionLevel();
		MapLevel m = getMapLevel();
		
		boolean[][] discovery = new boolean[getLevel().getWidth()][getLevel().getHeight()];
		
		Room room = getLevel().getRoom(x, y);
		
		if (room != null && (m.isForgetful() || !room.isFound()) && v.seeRooms()) {
			room.find();
			for (int i = room.x; i < room.x + room.width; i++) {
				for (int j = room.y; j < room.y + room.height; j++) {
					discoverTile(discovery, i, j);
				}
			}
		}
		if (v.seeAdjacent()) {
			// This code is for seeing only one tile away, except in rooms:
			char[][] map = getLevel().getCharMap();
			
			for (int i = -1; i <= 1; i++) {
				if (x+i >= 0 && x+i <= discovery.length && Level.PASSABLE.contains(map[x+1][y])) {
					discoverTile(discovery, x+i, y);
				}
				if (y+i >= 0 && y+i <= discovery[x].length && Level.PASSABLE.contains(map[x][y+1])) {
					discoverTile(discovery, x, y+i);
				}
			}
		} else discoverTile(discovery, x, y);
		
		getMapMask().append(discovery);
	}
	
	/**
	 * 
	 * "Discover" one tile, i.e., allow the player to see it.
	 * 
	 * @param x - the X coordinate of the tile to be discovered
	 * @param y - the Y coordinate of the tile to be discovered
	 * @return if the tile was existent & changed
	 */
	public boolean discoverTile(boolean[][] fow, int x, int y) {
		if (x > fow.length || x < 0 || y > fow[x].length || y < 0) return false;
		else {
			boolean old = fow[x][y];
			fow[x][y] = true;
			return old != fow[x][y];
		}
	}
	
	public MapMask getMapMask() {
		return mask;
	}
	
	public void updateDisplay() {
		if (getHealth() <= 0) {
			setRepresentation( (char) 'X');
		}
	}
	
	public void moveLater(int direction) {
		this.direction = direction;
	}
	
	public boolean isDesiredMovePossible() {
		return couldMove;
	}

	@Override
	public void handleHealthChange() {
		updateDisplay();
	}

	public MapLevel getMapLevel() {
		return mapLevel;
	}

	public VisionLevel getVisionLevel() {
		return visionLevel;
	}

	public int getMapAge() {
		return mapAge;
	}

	public void resetMapAge() {
		this.mapAge = 0;
	}

	public void incrementMapAge() {
		this.mapAge++;
	}

}
