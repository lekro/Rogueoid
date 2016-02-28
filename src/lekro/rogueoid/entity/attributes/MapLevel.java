package lekro.rogueoid.entity.attributes;

import lekro.rogueoid.map.mask.LayeredMapMask;
import lekro.rogueoid.map.mask.MapMask;
import lekro.rogueoid.map.mask.UniformMapMask;

public enum MapLevel {

	/**
	 * Has no map, only what is visible
	 */
	MAPLESS(0), 
	/**
	 * Forgets what was seen 1-2 moves ago
	 */
	AMNESIAC(10), 
	/**
	 * Could forget what was seen a couple of moves ago
	 */
	FORGETFUL(20), 
	/**
	 * Remembers everything, but can't track entities
	 */
	NORMAL(30), 
	/**
	 * Remembers everything and can see all entities everywhere.
	 */
	MARAUDERS(40);
	
	// TODO implement NORMAL map / make entities not visible anywhere in the map
	
	private int level;
	
	private MapLevel(int level) {
		this.level = level;
	}
	
	public int getLevel() {
		return level;
	}
	
	public boolean isForgetful() {
		return getLevel() < NORMAL.getLevel();
	}
	
	public int getForgetfulness() {
		switch(this) {
		case MAPLESS: return 1;
		case AMNESIAC: return 3;
		case FORGETFUL: return 5;
		default: return -1;
		}
	}
	
	public MapMask toMapMask(int width, int height) {
		switch(this) {
		case AMNESIAC:
		case FORGETFUL:
		case MAPLESS:
			return new LayeredMapMask(getForgetfulness(), width, height);
		case NORMAL:
		case MARAUDERS:
		default:
			return new UniformMapMask(width, height);
		}
	}
	
}
