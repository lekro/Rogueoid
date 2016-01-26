package lekro.rogueoid;

import java.awt.Point;

import lekro.rogueoid.entity.Player;
import lekro.rogueoid.map.Level;
import lekro.rogueoid.ui.RogueoidUI;

public class Rogueoid {

	public static final String VERSION = "0.0.1";
	
	public static void main(String[] args) {

		Level l = new Level();
		Point p = l.getValidLocation();
		l.getEntities().add(new Player(p.x, p.y));
		RogueLoop loop = new RogueLoop();
		RogueoidUI ui = RogueoidUI.getRecommendedUI(loop);
		ui.setText(l.toString());
		
	}
	
}
