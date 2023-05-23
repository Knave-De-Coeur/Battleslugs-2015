package main;

import javax.swing.JOptionPane;

public class Players
{
	private int Board_size = 12; //Size decliared by the requirements
	private final int challenger_screen = 0; // Opponent board: the board where the opponent placed their slugs, hidden from player
	private final int plyr_screen = 1; //Slug Borad: the board where the player chose to enter their slugs
	private int Board_views = 2; // Total number of boards each player has
	private String name;
	private int [][][] board = new int[Board_size][Board_size][Board_views];//Three dimensional to keep both 12x12 grides behind each other
	private int executed_shots;//Total valid shots of the player
	private int misses;//Total shots that didn't hit a slug
	private int hitslug;//Total shots that did hit a slug
	private int sluglives;//Total lives
	private double hitratio;// Accuracy ratio

	/* Values on Board:
		   0 = no executed shot and nothing there
		   1 = executed shot and miss
		   2 = no executed shot and slug piece is there
		   3 = executed shot and hit slug
	 */

	public Players()
	{
		//System.out.println("Object declared with no arguments");
		name = "";
		for(int row = 0; row < board.length; row++)
		{
			for(int column =0; column < board[plyr_screen].length; column++)//plyr_board is the second i.e. players own board
			{
				board[row][column][challenger_screen] = 0;
				board[row][column][plyr_screen] = 0;
			}
		}//Loop sets everywhere on both boards to the value of 0, meaning nothing is placed in that cell yet
		executed_shots = 0;
		misses = 0;
		hitslug = 0;
		sluglives = 0;
	}
	//Constructor takes no arguments

	public Players(String input)
	{
		name = input;// value entered in the arguments set the name
		for(int row = 0; row < board.length; row++)
		{
			for(int column =0; column < board[plyr_screen].length; column++)//plyr_board is the second i.e. players own board
			{
				board[row][column][challenger_screen] = 0;
				board[row][column][plyr_screen] = 0;
			}
		}//Loop sets everywhere on both boards to the value of 0, meaning nothing is placed in that cell yet
		executed_shots = 0;
		misses = 0;
		hitslug = 0;
		sluglives = 0;
	}
	//Constructor takes only the players name as arguments

	public Players(String n, int b, int e, int m, int s, int sf)
	{
		name = n;
		for(int row = 0; row < board.length; row++)
		{
			for(int column =0; column < board[plyr_screen].length; row++)
			{
				board[row][column][challenger_screen] = b;
				board[row][column][plyr_screen] = b;
			}
		}//Loop sets everywhere on both boards to the value of 0, meaning nothing is placed in that cell yet
		executed_shots = e;
		misses = m;
		hitslug = s;
		sluglives = sf;
	}
	//Constructor takes arguments for all statistics of the player except the hitratio

	public void setName(String n)
	{
		name = n;
	}
	public String getName()
	{
		return name;
	}

	public void setBoard(int row, int column, int screen, int status)
	{
		try
		{
			board[row][column][screen] = status;
		}catch(ArrayIndexOutOfBoundsException ex)//Catches if the method sets a value to a location that doesn't exist
		{
			JOptionPane.showMessageDialog(null,ex);
		}   //will return the out of bounds error to the user
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null,ex);
		}// will return the error to user
	}
	public int getBoard(int row, int column, int screen)
	{
		try//Trycatch initiated
		{
			return board[row][column][screen];
		}catch(ArrayIndexOutOfBoundsException ex)//Catches if the function returns a value that doesn't exist
		{
			//System.out.println(ex + " cought, returning -1");
			JOptionPane.showMessageDialog(null,ex);
		}// will return the out of bounds error to the user
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null,"Not valid",null,JOptionPane.WARNING_MESSAGE);
		}// will return awarning error to user
		return -1;//This value will be used in a different function to validate the positions
	}
	/*This getter has a try catch so everytime its called with arguments that go out of the array it will
	output the error and return a -1 instead of the actuall value */

	public void setExecutedShots(int e)
	{
		executed_shots = e;
	}
	public int getExecutedShots()
	{
		return executed_shots;
	}

	public void setMisses(int m)
	{
		misses = m;
	}
	public int getMisses()
	{
		return misses;
	}

	public void setSlugHit(int s)
	{
		hitslug = s;
	}
	public int getSlugHit()
	{
		return hitslug;
	}

	public void setSlugLives(int sv)
	{
		sluglives = sv;
	}
	public int getSlugLives()
	{
		return sluglives;
	}
	//Getters and Setters for Players

	public double Hitratio()
	{
		int ans =0;//temp variable
		try
		{
			ans = (getSlugHit()*100/getExecutedShots());
			/* Will multiply all the slug hits by 100 then divide that
			byt the toatl shots the player, invoked */
			return ans;
		}catch(ArithmeticException ex)
		/*Since when initialized the values above read zero, the program,
		in the beginning, trys to divide by 0 causing this type of error */
		{
			return 0.0;
			//Program goes on returning the value 0.0
		}
		catch(Exception ex)
		{
			return 0.0;
			//Program goes on returning the value 0.0
		}
	}

	//In the start of the program executed_shots and hitslugs are 0 the try catches catches the /0

	public void PlaceOnBoard(Players challenger)
	{
		for(int row = 0; row < 12; row++)
		{
			for(int column = 0; column < 12; column++)
			{
				challenger.setBoard(row,column,0,getBoard(row,column,1));
			}
		}
	}
	//Method that sets enemys slugs on players board without actually revealing them until salt is thrown i.e value = 3

	public boolean CheckHit(Players challenger, int row, int column)
	{
		boolean validshot = true;// Unless conditions say otherwise the shot is declaired valid i.e. true
		int sllife = 0;//This is a temperary storage to set the new lives after one has been taken
		if(board[row][column][challenger_screen] == 0)//No slug and hasn't been hit
		{
			board[row][column][challenger_screen]++;//Increments player's view of the enemy's board showing the affect
			executed_shots++;
			misses++;// Miss increases since the previous value indicated there was nothing in that cell
			hitratio = Hitratio();
			challenger.setBoard(row,column,plyr_screen,1);// Sets the enemy player's board the miss
		}
		else if (board[row][column][challenger_screen] == 2)//There is a slug and hasn't been hit
		{
			JOptionPane.showMessageDialog(null,"Slug Hit!!",null,JOptionPane.ERROR_MESSAGE);
			//Notifys player they hit the challenger's slug
			board[row][column][challenger_screen]++;//Shows the player where the piece of the slug he hit is
			executed_shots++;//In either of the cases (0 or 2) a shot has been executed therefore its incremented
			hitslug++;//Shows the slug was hit
			hitratio = Hitratio();
			sllife = challenger.getSlugLives()-1;//Decreaes lives
			challenger.setSlugLives(sllife);//sets new total lives to the challenging player
			challenger.setBoard(row,column,plyr_screen,3);//Indicates on the challengers board their slug has been hit
		}
		else if((board[row][column][challenger_screen] == 1)||(board[row][column][challenger_screen] == 3))
		{
			validshot = false;//It is not a valid shot simce another shot has already taken place
			JOptionPane.showMessageDialog(null,"Not a vaid place to shoot",null,JOptionPane.WARNING_MESSAGE);
		}
		//Validates so that is the cell was hit no action will be taken
		return validshot;
	}

	public void CheckIfWin(Players challenger)
	{
		String accoldates = "";//Will be used to store the statistics of the winning player
		if(challenger.getSlugLives() == 0)//Game deciding value
		{
			accoldates = "Player: " + getName() + "\n" +
						 "Shots executed: "  + getExecutedShots() + "\n" +
						 "Misses " + getMisses() + "\n" +
						 "Slugs Hits: " + getSlugHit() + "\n" +
						 "Slug Lives: " + getSlugLives() + "\n" +
						 "Hit Ratio: " + Hitratio(); // Stores all the statistics in a variable
			JOptionPane.showMessageDialog(null,"The Winner is : " + accoldates,null,JOptionPane.INFORMATION_MESSAGE);
			// Outputs winner statistics in a messagebox
			System.out.println("The winner is: " + accoldates);
		}
		else if(challenger.getSlugLives() < 0)
		{
			JOptionPane.showMessageDialog(null,"ERROR",null,JOptionPane.ERROR_MESSAGE);
		}
	}
	//Will check at each turn if the players lives drop to 0 thus declairing the challenger the	winner
}