package lekro.rogueoid.entity.attributes;

public enum VisionLevel {

	/**
	 * Cannot see anywhere but own tile
	 */
	BLIND(0),
	/**
	 * Can only see adjacent tiles
	 */
	LANTERN(10),
	/**
	 * Can see the entire room and one tile in paths
	 * (just like Rogue)
	 */
	LIT(20),
	/**
	 * All-seeing
	 */
	OMNISCIENT(30);
	
	private int level;
	
	private VisionLevel(int level) {
		this.level = level;
	}
	
	public int getLevel() {
		return level;
	}
	
	public boolean seeRooms() {
		return getLevel() >= VisionLevel.LIT.getLevel();
	}
	
	public boolean seeAdjacent() {
		return getLevel() >= VisionLevel.LANTERN.getLevel();
	}
}
