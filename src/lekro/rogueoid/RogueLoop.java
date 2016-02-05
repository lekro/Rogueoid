package lekro.rogueoid;

import java.awt.Point;

import lekro.rogueoid.entity.Entity;
import lekro.rogueoid.entity.Player;
import lekro.rogueoid.map.Level;
import lekro.rogueoid.ui.RogueoidUI;

public class RogueLoop {

	Level level;
	Player player;
	RogueoidUI ui;
	
	
	
	public RogueLoop(Level l, RogueoidUI ui) {
		level = l;
		player = l.getPlayer();
		this.ui = ui;
		ui.setLoop(this);
	}
	
	public void loop() {
		level.getPlayer().tick();
		for (Entity e : level.getEntities()) {
			if (!(e instanceof Player)) e.tick();
		}
		level.getPlayer().updateDisplay();
		String display = level.toString() + "\n" + constructPlayerBar();
		ui.setText(display);
	}
	
	public void playerInput(int input) {
		if (player.getHealth() <= 0) return;
		player.moveLater(input);
		if (input != -1) {
			loop();
		}
	}
	
	public String constructPlayerBar() {
		int width = level.getWidth();
		if (player.getHealth() <= 0) {
			String dead =  "You died :O Press [ESC] to restart.";
			// Center the dead message on the screen:
			int bufferSpace = (width - dead.length()) / 2;
			for (int i = 0; i < bufferSpace; i++) {
				dead = " " + dead + " ";
			}
			return dead;
		}
		int numberOfStats = 2;
		int statWidth = width / numberOfStats;
		String bar = "";
		bar += player.getHealth() + " / ";
		bar += player.getMaxHealth() + " [";
		int healthWidth = statWidth - bar.length();
		int healthSpace = (healthWidth - 2)*2/3;
		int healthBlock = (int) (healthSpace * player.getPercentHealth());
		for (int i = 0; i < healthSpace; i++) {
			if (i < healthBlock) bar += RogueoidUI.DARK;
			else bar += RogueoidUI.LIGHT;
		}
		bar += "] ";
		bar += "Score";
		return bar;
	}
	
	public void reset() {
		level = new Level();
		Point p = level.getValidLocation();
		player = new Player(p.x, p.y, level);
		level.getEntities().add(player);
		ui.setText(level.toString() + "\n" + constructPlayerBar());
	}
	
}
