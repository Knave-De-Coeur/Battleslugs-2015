package main;

import javax.swing.JButton;

public class BButton extends JButton { //Inherits attributes of the JButton class
	private int xAxis; //This will store the position of the button in the x-axis
	private int yAxis; //This will store the position of the button in the y-axis


	public BButton() {
		xAxis = 0;
		yAxis = 0;
	}
	//Constructor that accepts no values in it

	public BButton(int x, int y) {
		xAxis = x;
		yAxis = y;

	}
	//Constructor that accepts all the variables the class has


	public void setXaxis(int x) {
		xAxis = x;
	}
	public int getXaxis() {
		return xAxis;
	}

	public void setYaxis(int y) {
		yAxis = y;
	}
	public int getYaxis() {
		return yAxis;
	}
	//Getters and Setters of the class's variables

}