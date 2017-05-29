package battleslugs;

import javax.swing.*;

public class BButton extends JButton //Inherits attributes of the JButton class
{
	private int x_axis; //This will store the position of the button in the x-axis
	private int y_axis; //This will store the position of the button in the y-axis


	public BButton()
	{
		x_axis = 0;
		y_axis = 0;
	}
	//Constructor that accepts no values in it

	public BButton(int x, int y)
	{
		x_axis = x;
		y_axis = y;
		//System.out.println("x_axis = "+x_axis);
		//System.out.println("y_axis = "+y_axis);

	}
	//Constructor that accepts all the variables the class has


	public void setXaxis(int x)
	{
		x_axis = x;
		//System.out.println("x_axis set to "+x_axis);
	}
	public int getXaxis()
	{
		return x_axis;
	}

	public void setYaxis(int y)
	{
		y_axis = y;
	}
	public int getYaxis()
	{
		return y_axis;
	}
	//Getters and Setters of the class's variables

}