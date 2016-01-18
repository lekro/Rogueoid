package lekro.rogueoid;

import lekro.rogueoid.map.Level;
import lekro.rogueoid.ui.RogueoidUI;

public class Rogueoid {

	public static final String VERSION = "0.0.1";
	
	public static void main(String[] args) {
		
		RogueoidUI ui = RogueoidUI.getRecommendedUI();
		ui.setText(new Level().toString());
		
	}
	
}
