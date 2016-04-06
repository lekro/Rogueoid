package lekro.rogueoid;

import lekro.rogueoid.entity.Entity;
import lekro.rogueoid.entity.Player;
import lekro.rogueoid.map.Level;
import lekro.rogueoid.ui.RogueoidUI;

public class RogueLoop {

	Level level;
	Player player;
	RogueoidUI ui;
	
	boolean progress = false;
	
	public RogueLoop(Level l, RogueoidUI ui) {
		level = l;
		player = l.getPlayer();
		this.ui = ui;
		l.setLoop(this);
		ui.setLoop(this);
	}
	
	public void loop() {
		progress = true;
		player.tick();
		if (player.isDesiredMovePossible()) {
			for (Entity e : level.getEntities()) {
				if (!(e instanceof Player)) e.tick();
			}
		}
		player.updateDisplay();
		String display = level.toString() + "\n" + constructPlayerBar();
		ui.setText(display);
		progress = false;
	}
	
	public void playerInput(int input) {
		if (player.getHealth() <= 0) return;
		player.moveLater(input);
		if (input != -1) {
			while (progress) {
				
			}
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
		//int numberOfStats = 2;
		//int statWidth = width / numberOfStats;
		String bar = player.getName() + " - ";
		bar += player.getHealth() + " / ";
		bar += player.getMaxHealth() + " HP - ";
		//bar += generateBar(statWidth, bar.length());
		bar += "Score";
		return bar;
	}
	
	public String generateBar(int statWidth, int currentLength) {
		String bar = "[";
		int healthWidth = statWidth - currentLength;
		int healthSpace = (healthWidth - 2)*2/3;
		int healthBlock = (int) (healthSpace * player.getPercentHealth());
		for (int i = 0; i < healthSpace; i++) {
			if (i < healthBlock) bar += RogueoidUI.BLOCK;
			else bar += RogueoidUI.LIGHT;
		}
		bar += "] ";
		return bar;
	}
	
	public void newPlayer() {
		player = new Player(level.getRandomLocation(), level);
	}
	
	public void resetPlayer() {
		player.setLocation(level.getRandomLocation());
		player.getMapMask().clear();
		player.setLevel(level);
		player.discoverLand();
	}
	
	public void reset() {
		level = new Level();
		level.setLoop(this);
		resetPlayer();
		level.getEntities().add(player);
		ui.setText(level.toString() + "\n" + constructPlayerBar());
	}
	
}
