package main;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;

public class GUI extends JFrame implements ActionListener
{
	String pl1 = JOptionPane.showInputDialog(null,"Player 1, please enter name ",null,JOptionPane.INFORMATION_MESSAGE);
	String pl2 = JOptionPane.showInputDialog(null,"Player 2, please enter name ",null,JOptionPane.INFORMATION_MESSAGE);
	//Asks the users for their names

	Players player1 = new Players(pl1);//Player one's name as an argument for Player's object
	Players player2 = new Players(pl2);//Player two's name as an argument for Player's object
	//Both will use the constructor that generates the name the enter as their player name

	Slugs pl1_slugs = new Slugs();
	Slugs pl2_slugs = new Slugs();
	//Two objects of slugs one for each player

	boolean viewslugs = false;
	boolean adding_slugs = true;
	boolean plyr1_turn = true;
	boolean fiveslugs = false;
	//Used for functionality and validation purposes

	int grid = 12;//Required size of game board
	int pixelspace = 5;
	int plyr_stat = 8;//Stats and other functions of each player
	int slugs_on_board = 0;

	BButton[][] btn_plyr = new BButton[grid][grid];
	BButton[][] btn_mini = new BButton[grid][grid];
	//Array of instantiated buttons
	JButton btn_genslugs = new JButton("Generate Slug");
	//Regular Jbutton to perform a simple task, to activate a function

	GridLayout GL_buttons = new GridLayout(grid,grid);// 12 x 12
	GridLayout GL_plyr_info = new GridLayout(plyr_stat,0,pixelspace,pixelspace);//1x0 so the stats appear one after the other
	GridLayout GL_mini_grid = new GridLayout(grid,grid,0,0);//12 x 12
	BorderLayout BL_game = new BorderLayout();

	JToggleButton tog_view = new JToggleButton("View Your Slugs");

	JLabel Lbl_plyr_name = new JLabel();
	JLabel Lbl_plyr_shots = new JLabel();
	JLabel Lbl_plyr_misses = new JLabel();
	JLabel Lbl_plyr_hits = new JLabel();
	JLabel Lbl_plyr_sluglives = new JLabel();
	JLabel Lbl_plyr_hitratio = new JLabel();

	JPanel pnl_stat = new JPanel(GL_plyr_info);
	JPanel pnl_mini = new JPanel(GL_mini_grid);
	JPanel pnl_main = new JPanel(GL_buttons);
	JPanel pnl_tog = new JPanel();
	JPanel pnl_outer = new JPanel(BL_game);

	public GUI()
	{
		super("Battleslugs");//Title of window

		JOptionPane.showMessageDialog(null,"Player 1, please enter your slugs ",null,JOptionPane.INFORMATION_MESSAGE);
		//Initializing Player names

		Lbl_plyr_name.setText("Player: " + player1.getName());
		Lbl_plyr_shots.setText("Shots: " + player1.getExecutedShots());
		Lbl_plyr_misses.setText("Misses: " + player1.getMisses());
		Lbl_plyr_hits.setText("Slugs Hit: " + player1.getSlugHit());
		Lbl_plyr_sluglives.setText("Slugs Lives Left: " + player1.getSlugLives());
		Lbl_plyr_hitratio.setText("Hit Ratio: " + player1.Hitratio());
		//Creates Labels with info of the players from their repective classes

		pnl_stat.add(Lbl_plyr_name);
		pnl_stat.add(Lbl_plyr_shots);
		pnl_stat.add(Lbl_plyr_misses);
		pnl_stat.add(Lbl_plyr_hits);
		pnl_stat.add(Lbl_plyr_sluglives);
		pnl_stat.add(Lbl_plyr_hitratio);
		//Adds all the players stats to a panel

		for(int row = 0; row < grid; row++)
		{
			for(int column = 0; column < grid; column++)
			{
				btn_mini[row][column] = new BButton(row,column);
				pnl_mini.add(btn_mini[row][column]);// adding to a panel
				btn_plyr[row][column] = new BButton(row,column);
				btn_plyr[row][column].addActionListener(this);// Functionality is given to the buttons
				pnl_main.add(btn_plyr[row][column]);// adding to a panel
			}
		}// No functionality was given to the mini buttons because they are there to show the slug

		pnl_stat.add(pnl_mini); //Adds the grid of buttons to the status panel
		pnl_stat.add(btn_genslugs);
		btn_genslugs.addActionListener(this); //Adds functionality when the button is pressed


		pnl_tog.add(tog_view);
		tog_view.addActionListener(this);
		//Adds toggle button to its own panel, will be used to show the player their own slugs

		pnl_outer.add("Center",pnl_main);//Middle of the panel
		pnl_stat.setPreferredSize(new Dimension(250,1000));
		// Will give the stat a custom dimension to display the slugs abit better
		pnl_outer.add("East",pnl_stat);// Right of the panel
		pnl_outer.add("South",pnl_tog);//Bottom of the panel
		// Places each panel in their own respective side on the frame

		setContentPane(pnl_outer);
	}

	public void actionPerformed(ActionEvent press)
	{
		Object thebutton = press.getSource();//Object implements theActionListener Interface
		//System.out.println("actionperformed() Started");

		if(thebutton == tog_view)//If statement to see which button is equal to the event
		{
			//System.out.println("Toggle Pressed");
			//System.out.println("adding_slugs = " + adding_slugs);
			if(adding_slugs == false)
			/*Validation so the player won't be able to use the method until all slugs from
			both players have been placed on their board */
			{
				if(plyr1_turn == true)
				{
					//System.out.println("Player 1 is seeing their slugs");
					//System.out.println("Calling Showyourslugs()");
					ShowYourSlugs(player1); //Will flip third dim array of board to show player1 their slugs
					//System.out.println("Showyourslugs() called");
				}
				else if (plyr1_turn == false)
				{
					//System.out.println("Player 2 is seeing their slugs");
					ShowYourSlugs(player2);
				}
				/*If the toggle button is pressed, depending on wheater it is player
				one's turn or not will display the slugs of the active player */
			}
			else
			{
				//System.out.println("Cannot perform action at this time");
				JOptionPane.showMessageDialog(null,"Cannot perform action at this time",null,JOptionPane.ERROR_MESSAGE);
				//Outputs error to user
			}
		}

		else if(thebutton == btn_genslugs)
		{
			//System.out.println("Button pressed is btn_genslugs");
			//System.out.println("No of slugs = " + slugs_on_board);
			//System.out.println("adding_slugs = " + adding_slugs);
			if(slugs_on_board < 5)
			{

				if(plyr1_turn == true)//Will add slugs for player 1
				{
					//System.out.println("Generating a slug for " + player1.getName());
					//System.out.println("Calling GenerateSlug()");
					GenerateSlug(player1, pl1_slugs);
					//System.out.println("Finished Calling GenerateSlug()");
				}
				else if (plyr1_turn == false)// Will add slugs for player 2
				{
					//System.out.println("Generating a slug for " + player2.getName());
					GenerateSlug(player2, pl2_slugs);
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null,"Cannot generate more slugs now",null,JOptionPane.ERROR_MESSAGE);
				//System.out.println("Cannot perform generate more slugs now");
			}
		}
		else // by default the only other button type btn_plyr
		{
			System.out.println("Button pressed was btn_plyr");
			//System.out.println("adding_slugs = " + adding_slugs);
			if((adding_slugs == true)&&(plyr1_turn == true))//Player 1 will place their slugs
			{
				//System.out.println(adding_slugs + " and " + plyr1_turn + "and" + player1.getName() + "Placed a slug on the board");
				System.out.println("Callin PlaceSlugs()");
				plyr1_turn = PlaceSlugs(thebutton, plyr1_turn, player1, pl1_slugs, player2);
				System.out.println("Finish Calling Placeslugs()");
			}
			else if(adding_slugs == true)//Player 2 will place thier slugs
			{
				//System.out.println(adding_slugs + "and" + plyr1_turn + "and" + player1.getName() + "Placed a slug on the board");
				plyr1_turn = PlaceSlugs(thebutton, plyr1_turn, player2, pl2_slugs, player1);
			}
			//adding_slugs needs to be true to actually place a slug on the board
			else
			{
				//System.out.println("Salt is thrown");
				plyr1_turn = ThrowSalt(thebutton,plyr1_turn,viewslugs);
				//plyr_1 = true, player 1 throws salt, otherwise player 2 throws salt
			}// needs adding_slugs to be false
		}
		//System.out.println("actionperformed() finished");
	}

	public void GenerateSlug(Players p, Slugs sl)
	{
		//System.out.println("GenerateSlug() Started");
		//System.out.println("Being called from actionperformed()");
		for(int row = 0; row < 12; row++)
		{
			for(int column = 0; column < 12; column++)
			{
				btn_mini[row][column].setBackground(null);
			}
		}// Makes sure the mini map is clear before making a new slug shape
		//System.out.println("Mini grid set back to default colour");
		//System.out.println("Calling Generate_Slug()");
		sl.Generate_SlugShape();// generates new coordinates the slug will take place
		//System.out.println("Calling Generate_Slug()");
		int[] x = new int [5];
		int[] y = new int [5];
		/*Declaration of two arrays of 5 int each these will be
		the placeholders for the coordinates */

		//System.out.println("Initialization: ");
		for(int i = 0; i < 5; i++)
		{
			x[i] = 0;
			y[i] = 0;
			//System.out.println( x[i] + "," + y[i]);
		}//Declares them all as 0 for readability purposes
		//System.out.println("Slug on mini grid: ");
		for(int a = 0; a < 5;a++)
		{
			x[a] = 5 + sl.getSlug(0,a);// Each x coordinate will start from 5
			y[a] = 5 + sl.getSlug(1,a);// Each y coordinate will start from 5
			//System.out.println(x[a] + "," + y[a]);
		}/* 5 is added to the values in the generate method so the slug
		will be in the middle of the mini map when generated */

		for(int a = 0; a < 5;a++)
		{
			if(x[a] == 5 && y[a] == 5)
			{
				btn_mini[x[a]][y[a]].setBackground(Color.YELLOW);//Will mark the place holder of the slug
				//System.out.println(x[a]+","+y[a] + " is Yellow");
			}
			else{
				btn_mini[x[a]][y[a]].setBackground(Color.GREEN);//Slug pieces
				//System.out.println(x[a]+","+y[a] + " is Green");
			}
		}//simply shows the shape of the slug on the mini map
		//System.out.println("GenerateSlug() Finished");
	}


	public boolean PlaceSlugs(Object thebutton, boolean plyr1_turn, Players p, Slugs sl, Players challenger)
	{
		System.out.println("PlaceSlugs() Started");
		System.out.println("Being called from actionperformed()");
		BButton btn_slug = new BButton();//New object of BButton
		btn_slug = (BButton)thebutton;//Parses Object into BButton

		int x = 0;
		int y = 0;
		int sllife = 0;
		boolean clear = true;
		boolean inbounds = true;
		// Variable declaration
		/*System.out.println("Variables at the start of the function are: " + "\n" +
							"x: " + x + "\n" +
							"y: " + y + "\n" +
							"sllife: " + sllife + "\n" +
							"clear: " + clear + "\n" +
							"inbounds: " + inbounds + "\n");*/

		clear = IsClear(btn_slug, p, sl, x, y);//Validation to check that there aren't any other slugs
		//System.out.println("clear = " + clear);

		inbounds = InBounds(btn_slug,p, sl, x, y);//Validation to check that the slug won't fall off the board
		//System.out.println("inbounds = " + inbounds);

		if((clear == true) &&(inbounds == true))// Needs both validations to be true
		{
			//System.out.println("Slug is placed at: ");
			for(int a = 0 ; a < 5; a++)
			{
				x = btn_slug.getXaxis() + sl.getSlug(0,a);
				y = btn_slug.getYaxis() + sl.getSlug(1,a);
				//System.out.println(x + "," + y);
				/*Will add the values of the coordinates to the button coordinates,
				so the shape of the slug will generate from the button chosen*/
				p.setBoard(x,y,1,2);//Actually places the slug in the players board in the order of the shape
			}
			slugs_on_board++;
			//System.out.println("Slugs on board has been incremented");
			sllife = (p.getSlugLives())+5;
			//System.out.println("Slug lives have increased by 5");
			//Increases the slug lives of each player by 5 for every successful slug
			p.setSlugLives(sllife);//Sets the lives in the players stats
			Lbl_plyr_sluglives.setText("Slug Lives: " + p.getSlugLives());
			//System.out.println("Sets and updates slug lives");
			//Sets the label in the GUI
		}

		fiveslugs = IsReady(p, slugs_on_board);//Validates wheather 5 slugs are on the board
		//System.out.println("fiveslugs = " + fiveslugs);
		//System.out.println("plyr1_turn" + plyr1_turn);

		if((plyr1_turn == true)&&(fiveslugs == true))// Needs to be Player 1s turn needs to have 5 slus
		{
			JOptionPane.showMessageDialog(null,"Player 2, enter your slugs");
			fiveslugs = false;//reset
			plyr1_turn = false;
			slugs_on_board = 0;//reset
			//System.out.println("slugs on board = " + slugs_on_board);
			//System.out.println("all slugs are placed on the board");
			p.PlaceOnBoard(challenger);//adds the slugs on the other players main board, to throw salt
			//System.out.println("Calls Switch players");
			SwitchPlayers(challenger);
			//System.out.println("Switch players successfully called");
			System.out.println("PlaceSlugs() calling Clear()");
			ClearGrid();//Clears mini map for next player
			System.out.println("Clear() returned");
		}//Switches player and returns false indicating that player 1 has finished adding slugs on their board
		else if ((plyr1_turn == false)&&(fiveslugs == true))//Needs to be player 2s turn and have 5 slugs on the board
		{
			JOptionPane.showMessageDialog(null,"Start throwing salt! ");
			fiveslugs = false;//reset
			plyr1_turn = true;
			adding_slugs = false;//This will switch the functionality of the plyr_btn
			p.PlaceOnBoard(challenger);//Places player 2s slugs in player 1s main board, to throw salt
			SwitchPlayers(challenger);
			ClearGrid();//Clears mini map for assurance
			ShowSlugsMiniMap(challenger);// Displays the slugs of player 1
		}//Switches players, returns true, starts game by setting adding_slugs as false
		//System.out.println(plyr1_turn);
		//System.out.println("Returning to actionperformed()");
		//System.out.println("PlaceSlugs() Finished");
		return plyr1_turn;

	}

	public boolean IsClear(BButton btn_slug, Players p,Slugs sl, int x, int y)
	{
		boolean clear = true;
		//System.out.println("Clear at start = " + clear);
	//	System.out.println("Checking coordinates");
		for(int a = 0 ; a < 5; a++)
		{
			x = btn_slug.getXaxis() + sl.getSlug(0,a);
			y = btn_slug.getYaxis() + sl.getSlug(1,a);
			//System.out.println(x + "," + y);
			if(p.getBoard(x,y,1) == 2)//Checks if there is already a slug piece
			{
			//	System.out.println(x + "," + y + " Has a slug piece");
				clear = false;//returns false
				//System.out.println("clear exists with a "+clear);
				JOptionPane.showMessageDialog(null,"A slug's in the way!!",null, JOptionPane.WARNING_MESSAGE);
				a = 4;//ends loop
			}
		}
		//System.out.println("No slug pieces anywhere");
		//System.out.println("clear exists with a "+clear);
		return clear;
	}//Checks if there is a slug in the cell already

	public boolean InBounds(BButton btn_slug, Players p,Slugs sl, int x, int y)
	{
		boolean inbounds = true;
		//System.out.println("inbounds at start = " + inbounds);
		//System.out.println("Checking coordinates");
		for(int a = 0 ; a < 5 && inbounds; a++)
		{
			x = btn_slug.getXaxis() + sl.getSlug(0,a);
			y = btn_slug.getYaxis() + sl.getSlug(1,a);
			int temp = p.getBoard(x,y,1);// gets the value of those particular coordinates
			if(temp == -1)//If there is an error the function catches the error to return this value
			{
				a = 4;
				//System.out.println(x + "," + y + " Doen't exist");
				inbounds = false;
				//System.out.println("inbounds exists with a "+inbounds);
				JOptionPane.showMessageDialog(null,"Can't place it here it will fall off",null,JOptionPane.WARNING_MESSAGE);
			}
		}
		//System.out.println("All positions are valid");
		//System.out.println("inbounds exists with a "+inbounds);
		return inbounds;
	}//Checks if the slug entered is within the bounds of the board

	public boolean IsReady(Players p, int slugs_on_board)
	{
		//System.out.println("IsReady()Started");
		//System.out.println("Slugs on board " + slugs_on_board);
		boolean isready = false;
		//System.out.println("isready start with a " + isready);
		if(slugs_on_board == 5)//When its 5 will return true
		{
			//System.out.println(p.getName() + "has placed all of his slugs");
			Showslugs(p);
			isready = true;
		}
		else if((slugs_on_board > 0)&&(slugs_on_board < 5))
		{
			Showslugs(p);
		}//Will call this method to display in either case to show the slug the player just placed
		else if(slugs_on_board > 5)
		{
			JOptionPane.showMessageDialog(null,"Too may slugs",null,JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			JOptionPane.showMessageDialog(null,"No slugs to display",null,JOptionPane.INFORMATION_MESSAGE);
		}
		//System.out.println("isready exits with a "+ isready);
		//System.out.println("IsReady()Finished ");
		return isready;
	}
	//This method returns true if all of the players slugs are on their board, it will also assist in switching turns when adding slugs

	public boolean ThrowSalt(Object thebutton, boolean plyr1_turn, boolean viewslugs)
	{
		//System.out.println("ThrowSalt() Started");
		BButton btn_salt = new BButton();//Creating a new object, of BButton
		btn_salt = (BButton)thebutton;//Parsing the event into the button

		System.out.println("plyr1_turn at start = " + plyr1_turn);
		System.out.println("viewslugs at start = " + viewslugs);

		int x = 0;
		int y = 0;//Variable declaration

		x = btn_salt.getXaxis();
		y = btn_salt.getYaxis();// setting variables the coordinates of the button
		//System.out.println(x + "," + y);

		if((plyr1_turn == true)&&(viewslugs == false))//Makes sure player one has their toggle off
		{
			if(player1.CheckHit(player2,x,y) == true)//Validates shot
			{
				//System.out.println("Player one successfully threw salt at player 2�s board ");
				player1.CheckIfWin(player2);//Checks if player 1 wins
				JOptionPane.showMessageDialog(null,"Turn ended");
				SwitchPlayers(player2);//Switches player
				plyr1_turn = false;//Player 1's turn ends
				//System.out.println("plyr1_turn: " + plyr1_turn);
			}
		}
		else if((plyr1_turn == false)&&(viewslugs == false))//Makes sure player2's toggle is off
		{
			if(player2.CheckHit(player1,x,y) == true)//validates shot
			{
				//System.out.println("Player 2 successfully threw salt at player 1�s board ");
				player2.CheckIfWin(player1);//Check is player 2 wins
				JOptionPane.showMessageDialog(null,"Turn ended",null,JOptionPane.INFORMATION_MESSAGE);
				SwitchPlayers(player1);//Switches player
				plyr1_turn = true;//Its player ones turn again
				//System.out.println("plyr1_turn: " + plyr1_turn);
			}
		}
		//System.out.println("Exit: " +plyr1_turn);
		//System.out.println("ThrowSalt() Finished");
		return plyr1_turn;//Return wheather or not its player 1's turn

	}

	public void ShowYourSlugs(Players p)
	{
		//System.out.println("ShowYourSlugs() Started");
		//System.out.println("Being called from actionperformed");
		if(viewslugs == false)
		{
			//System.out.println("Start Showing = " + viewslugs);
			Showslugs(p);//Puts challenger board in the mini grid
			Showminigrid(p);//Puts slugboard in the game grid
			viewslugs = true;
			//System.out.println("End Showing = " + viewslugs);
		}
		else
		{
			//System.out.println("Start Showing back = " + viewslugs);
			ShowGrid(p);//Puts slugboard in the game grid
			ShowSlugsMiniMap(p);//Puts the challenger baord in the mini grid
			viewslugs = false;
			//System.out.println("End Showing = back" + viewslugs);
		}
		//System.out.println("Viewslugs out: " + viewslugs);
		//System.out.println("ShowYourSlugs() Finished");
	}

	public void SwitchPlayers(Players p)
	{
		//System.out.println("SwitchPlayers()Started");
		//System.out.println("CALLED BY PLACESLUGS");
		//System.out.println("The following are set to the labels on the game frame:");
		Lbl_plyr_name.setText("Player: " + p.getName());
		//System.out.println(p.getName());
		Lbl_plyr_shots.setText("Shots: " + p.getExecutedShots());
		//System.out.println(p.getExecutedShots());
		Lbl_plyr_misses.setText("Misses: " + p.getMisses());
		//System.out.println(p.getMisses());
		Lbl_plyr_hits.setText("Slugs Hit: " + p.getSlugHit());
		//System.out.println(p.getSlugHit());
		Lbl_plyr_sluglives.setText("Slug Lives: " + p.getSlugLives());
		//System.out.println(p.getSlugLives());
		Lbl_plyr_hitratio.setText("Hit Ratio: " + p.Hitratio());

		//Sets all of the stats to the player in the parameters
		//System.out.println("Calling Showslugsminimap from SwitchPlayers");
		ShowSlugsMiniMap(p);// Sets the mini map to the players own board showing their slugs
		ShowGrid(p);// Sets the game board to show the player's view of the enemy's board
		//System.out.println("Returning to PlaceSlugs");
		//System.out.println("SwitchPlayers()Finished");
	}

	public void ShowGrid(Players p)// This method is used to display the main board
	{
		for(int row = 0; row < 12; row++ )
		{
			for(int column = 0; column < 12; column++)
			{
				if(p.getBoard(row,column,0) == 1)//Checks the third dim of player for the main board
				{
					btn_plyr[row][column].setBackground(Color.WHITE);// Salt missed
					//System.out.println("Main Grid button "+row+","+column+" is White");
				}
				else if(p.getBoard(row,column,0) == 3)
				{
					btn_plyr[row][column].setBackground(Color.RED);// Salt hit
					//System.out.println("Main Grid button "+row+","+column+" is Red");
				}
				else
				{
					btn_plyr[row][column].setBackground(null);// Salt hasn't been thrown
					//System.out.println("Main Grid button "+row+","+column+" is default");
				}
			}
		}
	}

	public void Showminigrid(Players p)
	{
		//System.out.println("Showminigrid() Started");
		for(int row = 0; row < 12; row++ )
		{
			for(int column = 0; column < 12; column++)
			{
				if(p.getBoard(row,column,0) == 1)//Checks the third dim of player for the main board
				{
					btn_mini[row][column].setBackground(Color.WHITE);// Salt missed
					//System.out.println("Mini Grid button "+row+","+column+" is White");
				}
				else if(p.getBoard(row,column,0) == 3)
				{
					btn_mini[row][column].setBackground(Color.RED);// Salt hit
					//System.out.println("Mini Grid button "+row+","+column+" is Red");
				}
				else
				{
					btn_mini[row][column].setBackground(null);// Salt hasn't been thrown
					//System.out.println("Mini Grid button "+row+","+column+" is default");
				}
			}
		}
		//System.out.println("Showminigrid() finished");
	}

	public void ShowSlugsMiniMap(Players p)
	{
		//System.out.println("Showminimap() Started");
		//System.out.println("Called from SwitchPlayers");
		for(int row = 0; row < 12; row++)
		{
			for(int column = 0; column < 12; column++)//Goes through the whole gird
			{
				if(p.getBoard(row,column,1) == 1)
				{
					btn_mini[row][column].setBackground(Color.WHITE);//Salt on the board
					//System.out.println("Mini Grid button "+row+","+column+" is White");
				}
				else if(p.getBoard(row,column,1) == 2)
				{
					btn_mini[row][column].setBackground(Color.GREEN);//Slug on the board
					//System.out.println("Mini Grid button "+row+","+column+" is Green");
				}
				else if(p.getBoard(row,column,1) == 3)
				{
					btn_mini[row][column].setBackground(Color.RED);//Slugs has been hit
					//System.out.println("Mini Grid button "+row+","+column+" is Red");
				}
				else if(p.getBoard(row,column,1) == 0)
				{
					btn_mini[row][column].setBackground(null);//Nothing
					//System.out.println("Mini Grid button "+row+","+column+" is default");
				}
			}
		}
		//System.out.println("Returning to SwitchPlayers");
		//System.out.println("Showminimap() finished");
	}

	public void Showslugs(Players p)//Used to display slugboard on main grid
	{
		for(int row = 0; row < 12; row++)
		{
			for(int column = 0; column < 12; column++)//iterates 12 x 12 times
			{
				if(p.getBoard(row,column,1) == 1)
				{
					btn_plyr[row][column].setBackground(Color.WHITE);//Displays a miss (salt)
					//System.out.println("Main Grid button "+row+","+column+" is White");
				}
				else if(p.getBoard(row,column,1) == 2)
				{
					btn_plyr[row][column].setBackground(Color.GREEN);//Displays a slug
					//System.out.println("Main Grid button "+row+","+column+" is Green");
				}
				else if(p.getBoard(row,column,1) == 3)
				{
					btn_plyr[row][column].setBackground(Color.RED);//Displays a slug piece hit
					//System.out.println("Main Grid button "+row+","+column+" is Red");
				}
				else if(p.getBoard(row,column,1) == 0)
				{
					btn_plyr[row][column].setBackground(null);//Nothing there
					//System.out.println("Main Grid button "+row+","+column+" is default");
				}
			}
		}
	}

	public void ClearGrid()
	{
		//System.out.println("Called from PlaceSlugs()");
		for(int row = 0; row < 12; row++)
		{
			for(int column = 0; column < 12; column++)
			{
				btn_mini[row][column].setBackground(null);
				//System.out.println("The button: " + row+","+column + "is default");
			}//Simply sets the buttons in the mini map to default
		}
		//System.out.println("Returning to PlaceSlugs()");
	}
}