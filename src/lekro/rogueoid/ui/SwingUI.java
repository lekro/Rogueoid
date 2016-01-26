package lekro.rogueoid.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import lekro.rogueoid.RogueLoop;
import lekro.rogueoid.Rogueoid;

public class SwingUI extends RogueoidUI {

	private JFrame frame;
	private JTextArea text;
	
	public SwingUI(RogueLoop loop) {
		
		frame = new JFrame("Rogueoid "+Rogueoid.VERSION);
		text = new JTextArea(30, 63);
		text.setFont(new Font("monospaced", Font.PLAIN, 18));
		text.setBackground(Color.BLACK);
		text.setForeground(Color.GRAY);
		text.setSelectionColor(Color.BLACK);
		text.setSelectedTextColor(Color.GRAY);
		text.setCaretColor(Color.BLACK);
		text.setEditable(false);
		text.addKeyListener(new RogueListener(loop));
		frame.add(text);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	public void setText(String text) {
		this.text.setText(text);
	}
	
}
