package lekro.rogueoid;

import lekro.rogueoid.entity.Entity;
import lekro.rogueoid.map.Level;
import lekro.rogueoid.ui.RogueoidUI;

public class RogueLoop {

	Level level;
	RogueoidUI ui;
	
	public RogueLoop(Level l, RogueoidUI ui) {
		level = l;
		this.ui = ui;
	}
	
	public void loop() {
		for (Entity e : level.getEntities()) {
			e.tick();
		}
		ui.setText(level.toString());
	}
	
}
