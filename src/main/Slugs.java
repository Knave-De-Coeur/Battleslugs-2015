package main;

import java.util.Random;
import javax.swing.JOptionPane;

public class Slugs
{
	private int slug_size = 5;//Required size of slug
	private int slug_coordinates = 2;//X and Y
	private int[][] slug = new int[slug_coordinates][slug_size];
	private int x_co;
	private int y_co;// variables

	public Slugs(int x, int y)
	{
		for(int size = 0; size < slug[1].length; size++)//Will iterate 5 times
		{
			for(int coordinate = 0; coordinate < slug.length; coordinate++)//Will iterate twice for 5 times
			{
				slug[coordinate][size] = 0;// Nested loop to set all variables to 0 in multi dim array
			}
		}//Coordinates are generated from left to right each time
		x_co = x;
		y_co = y;
	}//Constructor takes no arguments

	public Slugs()
	{
		for(int size = 0; size < slug[1].length; size++)//Will iterate 5 times
		{
			for(int coordinate = 0; coordinate < slug.length; coordinate++)//Will iterate twice for five times
			{
				slug[coordinate][size] = 0;// Nested loop to set all variables to 0 in multi dim array
			}
		}//Coordinates are generated from left to right each time
		x_co = 0;
		y_co = 0;
	}//Constructor takes no arguments


	public int getXco()
	{
		return x_co;
	}

	public int getYco()
	{
		return y_co;
	}

	public int getSlug(int x, int y)
	{
		try
		{
			return slug[x][y];//returns value of location
		}catch(ArrayIndexOutOfBoundsException ex)//returns a value in a location that doesn't exist
		{
			JOptionPane.showMessageDialog(null,ex + "Caught, returning -1");
			return -1;
		}
		catch(Exception ex)// catches any other error
		{
			JOptionPane.showMessageDialog(null,ex + "Caught, returning -1");
			return -1;
		}//Always return a -1

	}
	//Accessors

	public void Generate_SlugShape()// This will generate the random coordinates and store them in slug[][]
	{
		Random randomslug = new Random();

		int xpos = 0;
		int ypos = 0;
		//Declaration of temporary holder

		xpos = getXco();
		ypos = getYco();
		//Initailizes the temporary holder as the values of the x and y coordinates

		slug[0][0] = xpos;
		slug[1][0] = ypos;
		//Centre of the slug which will be used as a place marker
		boolean unique = false;// This boolean will determine when to stop the loop generating the coordinates
		for(int i = 1; i < slug_size; i++)
		{
			do{
				int numb = 0;
				numb = randomslug.nextInt(8)+1;// This gives the random generater the range of 1-8
				switch(numb)
				{
					case 1:
					{
						xpos++;
						ypos += 0;		/*Each case is dependent on the random number generated,
										from that number the x and y coordinates will be altered
										in one of 8 ways, that will set the coordinates around 0,0 and then
										from the newly generated coordinates, new ones will be generated until,
										the loop finishes all the while, constantly storing them in the multi dim
										array */
					}break;
					case 2:
					{
						ypos--;
						xpos++;
					}break;
					case 3:
					{
						xpos += 0;
						ypos++;
					}break;
					case 4:
					{
						xpos--;
						ypos--;
					}break;
					case 5:
					{
						xpos--;
						ypos += 0;
					}break;
					case 6:
					{
						ypos++;
						xpos--;
					}break;
					case 7:
					{
						xpos += 0;
						ypos++;
					}break;
					case 8:
					{
						xpos++;
						ypos++;
					}
				}/*After this line, unique is set true, a forloop is used to check each space in the array to see
				if there is the same pair of uniquely generated numbers, if so setting unique back true requiring the
				do while to loop again until a unique set of numbers is generated*/
				unique = true;
				for(int a = 0; a < slug_size; a++)
				{
					if(xpos == slug[0][a] && ypos == slug[1][a])
					{
						unique = false;
						xpos = 0;
						ypos = 0;
					}
				}// Validation of newly generated position

			}while(unique == false);

			slug[0][i] = xpos;
			slug[1][i] = ypos; // once the uniquely generated numbers are generated and validated they are set in the array
		}
	}

}