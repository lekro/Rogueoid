package lekro.rogueoid.ui;

import lekro.rogueoid.RogueLoop;


public abstract class RogueoidUI {

	protected RogueLoop loop;
	
	public static final char BLOCK = 0x2588;
	public static final char LIGHT = 0x2591;
	public static final char MEDIUM = 0x2592;
	public static final char DARK = 0x2593;
	
	public static RogueoidUI getRecommendedUI() {
		// TODO make this more interesting
		return new SwingUI();
	}
	
	public abstract void setText(String text);
	
	public void setLoop(RogueLoop loop) {
		this.loop = loop;
	}
	
}
