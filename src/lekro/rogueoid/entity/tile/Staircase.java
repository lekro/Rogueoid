package lekro.rogueoid.entity.tile;

import lekro.rogueoid.entity.Entity;
import lekro.rogueoid.map.Level;

public class Staircase extends TileEntity {

	public Staircase(int x, int y, Level level) {
		super(x, y, level);
		setName("Staircase");
		setRepresentation('S');
	}

	@Override
	public void activate(Entity activator) {
		getLevel().getLoop().reset();
	}

}
