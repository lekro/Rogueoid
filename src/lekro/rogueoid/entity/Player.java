package lekro.rogueoid.entity;

import lekro.rogueoid.entity.attributes.MapLevel;
import lekro.rogueoid.entity.attributes.VisionLevel;
import lekro.rogueoid.map.Level;


public class Player extends Entity {

	private int direction = -1;
	private boolean couldMove;
	
	// TODO select following based on some parameters:
	private MapLevel mapLevel = MapLevel.MARAUDERS;
	private VisionLevel visionLevel = VisionLevel.LIT;
	
	public Player(int x, int y, Level level) {
		super(x, y, level, 10);
		level.getEntities().add(this);
		getLevel().discoverLand(getX(), getY());
		setRepresentation(Level.PLAYER);
		setName("Player");
	}

	@Override
	public void tick() {
		updateDisplay();
		//setHealth(getHealth()-1);
		couldMove = move(direction);
		getLevel().discoverLand(getX(), getY());
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

}
