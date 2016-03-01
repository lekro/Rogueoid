package lekro.rogueoid.entity.tile;

import lekro.rogueoid.entity.Entity;
import lekro.rogueoid.entity.Player;
import lekro.rogueoid.map.Level;

public abstract class TileEntity extends Entity {

	public TileEntity(int x, int y, Level level) {
		super(x, y, level, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick() {
		// No computation necessary during tick
	}

	public abstract void activate(Entity activator);
	
	@Override
	public void receiveAttack(Entity other) {
		if (getHealth() < 1) {
			setHealth(1);
			if (other instanceof Player) {
				activate(other);
			}
		}
	}

}
