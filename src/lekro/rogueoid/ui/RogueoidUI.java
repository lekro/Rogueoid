package lekro.rogueoid.ui;

import lekro.rogueoid.RogueLoop;


public abstract class RogueoidUI {

	protected RogueLoop loop;
	
	public static RogueoidUI getRecommendedUI() {
		// TODO make this more interesting
		return new SwingUI();
	}
	
	public abstract void setText(String text);
	
	public void setLoop(RogueLoop loop) {
		this.loop = loop;
	}
	
}
