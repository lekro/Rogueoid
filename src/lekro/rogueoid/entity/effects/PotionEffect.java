package lekro.rogueoid.entity.effects;

import lekro.rogueoid.entity.Entity;

public abstract class PotionEffect {
	
	int magnitude, time;
	String name;
	
	public PotionEffect(int magnitude, int time, String name) {
		this.magnitude = magnitude;
		this.time = time;
		this.name = name;
	}
	
	public abstract void affect(Entity e);
	
	public int getMagnitude() {
		return magnitude;
	}
	
	public int getTimeLeft() {
		return time;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name+"("+magnitude+")";
	}
	
}
