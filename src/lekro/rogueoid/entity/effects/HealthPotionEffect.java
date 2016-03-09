package lekro.rogueoid.entity.effects;

import lekro.rogueoid.entity.Entity;

public class HealthPotionEffect extends PotionEffect {

	public HealthPotionEffect(int magnitude, int time) {
		super(magnitude, time, "Health");
	}

	@Override
	public void affect(Entity e) {
		e.setHealth(e.getHealth() + getMagnitude());
		if (getMagnitude() == 0 || e.getHealth() > e.getMaxHealth()) {
			e.setHealth(e.getMaxHealth());
		}
	}
	
	@Override
	public String toString() {
		return getName() + "(" + 
				((getMagnitude() == 0) ? "full" : getMagnitude()) + ")";
	}

}
