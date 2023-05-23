package main;

import javax.swing.JFrame;

public class Main {
	public static void main(String [] args) {
		GUI game = new GUI();
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setSize(1400,725);
		game.setVisible(true);
	}
}