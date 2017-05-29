package battleslugs;

import javax.swing.*;

public class Start_Battleslugs
{
	public static void main(String [] args)
	{
		GUI game = new GUI();//Instantaites GUI class
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Helps the system not crash
		game.setSize(1400,725);//Set the pixels
		game.setVisible(true);//Shows the actual window
	}
}