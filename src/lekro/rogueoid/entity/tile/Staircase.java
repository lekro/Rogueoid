package lekro.rogueoid.entity.tile;

import java.awt.Point;

import lekro.rogueoid.entity.Entity;
import lekro.rogueoid.map.Level;

public class Staircase extends TileEntity {

	public Staircase(int x, int y, Level level) {
		super(x, y, level);
		setName("Staircase");
		setRepresentation('S');
	}
	
	public Staircase(Point location, Level level) {
		this(location.x, location.y, level);
	}

	@Override
	public void activate(Entity activator) {
		getLevel().getLoop().reset();
	}

}
