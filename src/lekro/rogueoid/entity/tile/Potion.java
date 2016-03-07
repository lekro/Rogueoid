package lekro.rogueoid.entity.tile;

import java.awt.Point;

import lekro.rogueoid.entity.Entity;
import lekro.rogueoid.entity.effects.PotionEffect;
import lekro.rogueoid.map.Level;

public class Potion extends TileEntity {

	private PotionEffect effect;
	
	public Potion(Point location, Level level, PotionEffect effect) {
		this(location.x, location.y, level, effect);
	}

	public Potion(int x, int y, Level level, PotionEffect effect) {
		super(x, y, level);
		setName(effect+" Potion");
		setRepresentation('P');
		this.effect = effect;
	}

	@Override
	public void activate(Entity activator) {
		effect.affect(activator);
		getLevel().getEntities().remove(this);
	}

}
