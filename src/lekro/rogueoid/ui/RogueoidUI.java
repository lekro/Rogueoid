package lekro.rogueoid.ui;

import lekro.rogueoid.RogueLoop;

public abstract class RogueoidUI {

	public static RogueoidUI getRecommendedUI(RogueLoop loop) {
		// TODO make this more interesting
		return new SwingUI(loop);
	}
	
	public abstract void setText(String text);
	
}
