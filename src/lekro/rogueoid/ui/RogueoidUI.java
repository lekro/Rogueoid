package lekro.rogueoid.ui;

public abstract class RogueoidUI {

	public static RogueoidUI getRecommendedUI() {
		// TODO make this more interesting
		return new SwingUI();
	}
	
	public abstract void setText(String text);
	
}
