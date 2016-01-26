package lekro.rogueoid.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import lekro.rogueoid.RogueLoop;

public class RogueListener implements KeyListener {

	RogueLoop loop;
	
	public RogueListener(RogueLoop rl) {
		loop = rl;
	}
	
	@Override
	public void keyPressed(KeyEvent ke) {
		switch(ke.getKeyCode()) {
		case KeyEvent.VK_W:
			break;
		case KeyEvent.VK_A:
			break;
		case KeyEvent.VK_S:
			break;
		case KeyEvent.VK_D:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
