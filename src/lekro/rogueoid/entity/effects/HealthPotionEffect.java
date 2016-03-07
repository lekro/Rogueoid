package lekro.rogueoid.entity.effects;

import lekro.rogueoid.entity.Entity;

public class HealthPotionEffect extends PotionEffect {

	public HealthPotionEffect(int magnitude, int time) {
		super(magnitude, time, "Health");
	}

	@Override
	public void affect(Entity e) {
		e.setHealth(e.getMaxHealth());
	}
	

}
