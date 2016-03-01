package lekro.rogueoid.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import lekro.rogueoid.RogueLoop;

public class RogueListener implements KeyListener {

	RogueLoop loop;
	int keyAlreadyPressed = -1;
	
	public RogueListener(RogueLoop rl) {
		loop = rl;
	}
	
	@Override
	public void keyPressed(KeyEvent ke) {
		int input = ke.getKeyCode();
		if (input == KeyEvent.VK_ESCAPE) {
			loop.newPlayer();
			loop.reset();
		}
		if (keyAlreadyPressed == input) return;
		keyAlreadyPressed = input;
		int direction = getDirection(input);
		if (direction != -1) loop.playerInput(direction);
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		int input = ke.getKeyCode();
		keyAlreadyPressed = -1;
		int direction = getDirection(input);
		if (direction != -1) loop.playerInput(-1);
	}
	
	public int getDirection(int input) {
		return (input==KeyEvent.VK_D)?0:(input==KeyEvent.VK_W)?1:(input==KeyEvent.VK_A)?2:(input==KeyEvent.VK_S)?3:-1;
	}

	@Override
	public void keyTyped(KeyEvent ke) {
	}

}
