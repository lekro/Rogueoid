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
		l.getEntities().add(new Player(p.x, p.y, l));
		RogueoidUI ui = RogueoidUI.getRecommendedUI();
		RogueLoop loop = new RogueLoop(l, ui);
		ui.setText(l.toString());
		while (true) {
			loop.loop();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
