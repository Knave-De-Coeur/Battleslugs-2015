package main;

import javax.swing.JFrame;

public class Main
{
	public static void main(String [] args)
	{
		GUI game = new GUI();
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Helps the system not crash
		game.setSize(1400,725);//Set the pixels
		game.setVisible(true);//Shows the actual window
	}
}