package lekro.rogueoid.entity.attributes;

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
	
	private int level;
	
	private MapLevel(int level) {
		this.level = level;
	}
	
	public int getLevel() {
		return level;
	}
	
}
