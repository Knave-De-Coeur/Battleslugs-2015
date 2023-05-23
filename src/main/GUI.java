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

public class GUI extends JFrame implements ActionListener {
	String pl1 = JOptionPane.showInputDialog(null,"Player 1, please enter name ",null,JOptionPane.INFORMATION_MESSAGE);
	String pl2 = JOptionPane.showInputDialog(null,"Player 2, please enter name ",null,JOptionPane.INFORMATION_MESSAGE);
	//Asks the users for their names

	Players player1 = new Players(pl1);//Player one's name as an argument for Player's object
	Players player2 = new Players(pl2);//Player two's name as an argument for Player's object
	//Both will use the constructor that generates the name the enter as their player name

	Slugs pl1_slugs = new Slugs();
	Slugs pl2_slugs = new Slugs();
	//Two objects of slugs one for each player

	boolean viewSlugs = false;
	boolean addingSlugs = true;
	boolean plyr1Turn = true;
	boolean fiveSlugs = false;
	//Used for functionality and validation purposes

	int grid = 12;//Required size of game board
	int pixelSpace = 5;
	int plyrStat = 8;//Stats and other functions of each player
	int slugsOnBoard = 0;

	BButton[][] btn_plyr = new BButton[grid][grid];
	BButton[][] btn_mini = new BButton[grid][grid];

	//Regular JButton to perform a simple task, to activate a function
	JButton btnGenSlugs = new JButton("Generate Slug");

	GridLayout glButtons = new GridLayout(grid,grid);// 12 x 12
	GridLayout glPlyrInfo = new GridLayout(plyrStat,0, pixelSpace, pixelSpace);//1x0 so the stats appear one after the other
	GridLayout glMiniGrid = new GridLayout(grid,grid,0,0);//12 x 12
	BorderLayout blGame = new BorderLayout();

	JToggleButton togView = new JToggleButton("View Your Slugs");

	JLabel lblPlyrName = new JLabel();
	JLabel lblPlyrShots = new JLabel();
	JLabel lblPlyrMisses = new JLabel();
	JLabel lblPlyrHits = new JLabel();
	JLabel lblPlyrSlugLives = new JLabel();
	JLabel lblPlyrHitRatio = new JLabel();

	JPanel pnlStat = new JPanel(glPlyrInfo);
	JPanel pnlMini = new JPanel(glMiniGrid);
	JPanel pnlMain = new JPanel(glButtons);
	JPanel pnlTog = new JPanel();
	JPanel pnlOuter = new JPanel(blGame);

	public GUI() {
		super("Battleslugs");//Title of window

		JOptionPane.showMessageDialog(null,"Player 1, please enter your slugs ",null,JOptionPane.INFORMATION_MESSAGE);
		//Initializing Player names

		lblPlyrName.setText("Player: " + player1.getName());
		lblPlyrShots.setText("Shots: " + player1.getExecutedShots());
		lblPlyrMisses.setText("Misses: " + player1.getMisses());
		lblPlyrHits.setText("Slugs Hit: " + player1.getSlugHit());
		lblPlyrSlugLives.setText("Slugs Lives Left: " + player1.getSlugLives());
		lblPlyrHitRatio.setText("Hit Ratio: " + player1.Hitratio());
		//Creates Labels with info of the players from their repective classes

		pnlStat.add(lblPlyrName);
		pnlStat.add(lblPlyrShots);
		pnlStat.add(lblPlyrMisses);
		pnlStat.add(lblPlyrHits);
		pnlStat.add(lblPlyrSlugLives);
		pnlStat.add(lblPlyrHitRatio);
		//Adds all the players stats to a panel

		for(int row = 0; row < grid; row++) {
			for(int column = 0; column < grid; column++) {
				btn_mini[row][column] = new BButton(row,column);
				pnlMini.add(btn_mini[row][column]);// adding to a panel
				btn_plyr[row][column] = new BButton(row,column);
				btn_plyr[row][column].addActionListener(this);// Functionality is given to the buttons
				pnlMain.add(btn_plyr[row][column]);// adding to a panel
			}
		}// No functionality was given to the mini buttons because they are there to show the slug

		pnlStat.add(pnlMini); //Adds the grid of buttons to the status panel
		pnlStat.add(btnGenSlugs);
		btnGenSlugs.addActionListener(this); //Adds functionality when the button is pressed


		pnlTog.add(togView);
		togView.addActionListener(this);
		//Adds toggle button to its own panel, will be used to show the player their own slugs

		pnlOuter.add("Center", pnlMain);//Middle of the panel
		pnlStat.setPreferredSize(new Dimension(250,1000));
		// Will give the stat a custom dimension to display the slugs abit better
		pnlOuter.add("East", pnlStat);// Right of the panel
		pnlOuter.add("South", pnlTog);//Bottom of the panel
		// Places each panel in their own respective side on the frame

		setContentPane(pnlOuter);
	}

	public void actionPerformed(ActionEvent press) {
		Object theButton = press.getSource();//Object implements theActionListener Interface

		if(theButton == togView) { //If statement to see which button is equal to the event
			if(!addingSlugs) {
				if(plyr1Turn) {
					ShowYourSlugs(player1); //Will flip third dim array of board to show player1 their slugs
				} else {
					ShowYourSlugs(player2);
				}
				// If the toggle button is pressed, depending on whether it is player
				// one's turn or not will display the slugs of the active player
			} else {
				JOptionPane.showMessageDialog(null,"Cannot perform action at this time",null,JOptionPane.ERROR_MESSAGE);
			}
		} else if(theButton == btnGenSlugs) {
			if(slugsOnBoard < 5) {
				if (plyr1Turn) { //Will add slugs for player 1
					GenerateSlug(pl1_slugs);
				} else { // Will add slugs for player 2
					GenerateSlug(pl2_slugs);
				}
			} else {
				JOptionPane.showMessageDialog(null,"Cannot generate more slugs now",null,JOptionPane.ERROR_MESSAGE);
			}
		} else { // by default the only other button type btn_plyr
			if((addingSlugs)&&(plyr1Turn)) { //Player 1 will place their slugs
				plyr1Turn = PlaceSlugs(theButton, true, player1, pl1_slugs, player2);
			} else if(addingSlugs) { //Player 2 will place their slugs
				plyr1Turn = PlaceSlugs(theButton, true, player2, pl2_slugs, player1);
			} else { //adding_slugs needs to be true to actually place a slug on the board
				plyr1Turn = ThrowSalt(theButton, plyr1Turn, viewSlugs);
			}// needs adding_slugs to be false
		}
	}

	public void GenerateSlug(Slugs sl) {
		for(int row = 0; row < 12; row++) {
			for(int column = 0; column < 12; column++) {
				btn_mini[row][column].setBackground(null);
			}
		} // Makes sure the mini map is clear before making a new slug shape
		sl.Generate_SlugShape();// generates new coordinates the slug will take place
		int[] x = new int [5];
		int[] y = new int [5];
		// Declaration of two arrays of 5 int each these will be the placeholders for the coordinates

		for(int i = 0; i < 5; i++) {
			x[i] = 0;
			y[i] = 0;
		} //Declares them all as 0 for readability purposes

		for(int a = 0; a < 5;a++) {
			x[a] = 5 + sl.getSlug(0,a);// Each x coordinate will start from 5
			y[a] = 5 + sl.getSlug(1,a);// Each y coordinate will start from 5
		}

		// 5 is added to the values in the generate method so the slug will be in the middle of the mini map when generated

		for(int a = 0; a < 5;a++) {
			if(x[a] == 5 && y[a] == 5) {
				btn_mini[x[a]][y[a]].setBackground(Color.YELLOW);//Will mark the place holder of the slug
			} else {
				btn_mini[x[a]][y[a]].setBackground(Color.GREEN);//Slug pieces
			}
		}//simply shows the shape of the slug on the mini map
	}


	public boolean PlaceSlugs(Object thebutton, boolean plyr1_turn, Players p, Slugs sl, Players challenger) {
		BButton btn_slug = new BButton();//New object of BButton
		btn_slug = (BButton)thebutton;//Parses Object into BButton

		int x = 0;
		int y = 0;
		int sllife = 0;
		boolean clear = true;
		boolean inbounds = true;
		// Variable declaration

		clear = IsClear(btn_slug, p, sl, x, y);//Validation to check that there aren't any other slugs

		inbounds = InBounds(btn_slug,p, sl, x, y);//Validation to check that the slug won't fall off the board

		if((clear) && (inbounds)) {
			//System.out.println("Slug is placed at: ");
			for(int a = 0 ; a < 5; a++) {
				x = btn_slug.getXaxis() + sl.getSlug(0,a);
				y = btn_slug.getYaxis() + sl.getSlug(1,a);
				// Will add the values of the coordinates to the button coordinates,so the shape of the slug will generate from the button chosen
				p.setBoard(x,y,1,2);//Actually places the slug in the players board in the order of the shape
			}
			slugsOnBoard++;
			sllife = (p.getSlugLives())+5;
			//Increases the slug lives of each player by 5 for every successful slug
			p.setSlugLives(sllife);//Sets the lives in the players stats
			lblPlyrSlugLives.setText("Slug Lives: " + p.getSlugLives());
			//Sets the label in the GUI
		}

		fiveSlugs = IsReady(p, slugsOnBoard);//Validates wheather 5 slugs are on the board

		if((plyr1_turn) && (fiveSlugs)) {
			JOptionPane.showMessageDialog(null,"Player 2, enter your slugs");
			fiveSlugs = false;//reset
			plyr1_turn = false;
			slugsOnBoard = 0;//reset
			p.PlaceOnBoard(challenger);//adds the slugs on the other players main board, to throw salt
			SwitchPlayers(challenger);
			System.out.println("PlaceSlugs() calling Clear()");
			ClearGrid();//Clears mini map for next player
			System.out.println("Clear() returned");
		} else if ((!plyr1_turn) && (fiveSlugs)) {
			JOptionPane.showMessageDialog(null,"Start throwing salt! ");
			fiveSlugs = false;//reset
			plyr1_turn = true;
			addingSlugs = false;//This will switch the functionality of the plyr_btn
			p.PlaceOnBoard(challenger);//Places player 2s slugs in player 1s main board, to throw salt
			SwitchPlayers(challenger);
			ClearGrid();//Clears mini map for assurance
			ShowSlugsMiniMap(challenger);// Displays the slugs of player 1
		}//Switches players, returns true, starts game by setting adding_slugs as false
		return plyr1_turn;
	}

	public boolean IsClear(BButton btn_slug, Players p,Slugs sl, int x, int y) {
		boolean clear = true;
		for(int a = 0 ; a < 5; a++) {
			x = btn_slug.getXaxis() + sl.getSlug(0,a);
			y = btn_slug.getYaxis() + sl.getSlug(1,a);
			if(p.getBoard(x,y,1) == 2) { //Checks if there is already a slug piece
				clear = false; //returns false
				JOptionPane.showMessageDialog(null,"A slug's in the way!!",null, JOptionPane.WARNING_MESSAGE);
				a = 4;//ends loop
			}
		}
		return clear;
	}//Checks if there is a slug in the cell already

	public boolean InBounds(BButton btn_slug, Players p,Slugs sl, int x, int y) {
		boolean inbounds = true;
		for(int a = 0 ; a < 5 && inbounds; a++) {
			x = btn_slug.getXaxis() + sl.getSlug(0,a);
			y = btn_slug.getYaxis() + sl.getSlug(1,a);
			int temp = p.getBoard(x,y,1);// gets the value of those particular coordinates
			if(temp == -1) { //If there is an error the function catches the error to return this value
				a = 4;
				inbounds = false;
				JOptionPane.showMessageDialog(null,"Can't place it here it will fall off",null,JOptionPane.WARNING_MESSAGE);
			}
		}
		return inbounds;
	}//Checks if the slug entered is within the bounds of the board

	public boolean IsReady(Players p, int slugs_on_board) {
		boolean isready = false;
		if(slugs_on_board == 5) {
			Showslugs(p);
			isready = true;
		} else if((slugs_on_board > 0)&&(slugs_on_board < 5)) {
			Showslugs(p);
		} else if(slugs_on_board > 5) { //Will call this method to display in either case to show the slug the player just placed
			JOptionPane.showMessageDialog(null,"Too may slugs",null,JOptionPane.ERROR_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null,"No slugs to display",null,JOptionPane.INFORMATION_MESSAGE);
		}
		return isready;
	}
	//This method returns true if all of the players slugs are on their board, it will also assist in switching turns when adding slugs

	public boolean ThrowSalt(Object thebutton, boolean plyr1_turn, boolean viewslugs) {
		BButton btn_salt = new BButton();//Creating a new object, of BButton
		btn_salt = (BButton)thebutton;//Parsing the event into the button

		int x = 0;
		int y = 0;//Variable declaration

		x = btn_salt.getXaxis();
		y = btn_salt.getYaxis();// setting variables the coordinates of the button

		//Makes sure player one has their toggle off
		if((plyr1_turn) && (!viewslugs)) {
			if(player1.CheckHit(player2, x, y)) {
				player1.CheckIfWin(player2);//Checks if player 1 wins
				JOptionPane.showMessageDialog(null,"Turn ended");
				SwitchPlayers(player2);//Switches player
				plyr1_turn = false;//Player 1's turn ends
			}
		} else if((!plyr1_turn) && (!viewslugs)) {
			//Makes sure player2's toggle is off
			if(player2.CheckHit(player1, x, y)) {
				player2.CheckIfWin(player1);//Check is player 2 wins
				JOptionPane.showMessageDialog(null,"Turn ended",null,JOptionPane.INFORMATION_MESSAGE);
				SwitchPlayers(player1);//Switches player
				plyr1_turn = true;//Its player ones turn again
			}
		}
		return plyr1_turn;//Return wheather or not its player 1's turn
	}

	public void ShowYourSlugs(Players p) {
		if(!viewSlugs) {
			Showslugs(p);//Puts challenger board in the mini grid
			ShowMiniGrid(p);//Puts slugboard in the game grid
			viewSlugs = true;
		} else {
			ShowGrid(p);//Puts slugboard in the game grid
			ShowSlugsMiniMap(p);//Puts the challenger baord in the mini grid
			viewSlugs = false;
		}
	}

	public void SwitchPlayers(Players p) {
		lblPlyrName.setText("Player: " + p.getName());
		lblPlyrShots.setText("Shots: " + p.getExecutedShots());
		lblPlyrMisses.setText("Misses: " + p.getMisses());
		lblPlyrHits.setText("Slugs Hit: " + p.getSlugHit());
		lblPlyrSlugLives.setText("Slug Lives: " + p.getSlugLives());
		lblPlyrHitRatio.setText("Hit Ratio: " + p.Hitratio());

		// Sets all the stats to the player in the parameters
		ShowSlugsMiniMap(p);// Sets the mini map to the players own board showing their slugs
		ShowGrid(p);// Sets the game board to show the player's view of the enemy's board
	}

	public void ShowGrid(Players p) { // This method is used to display the main board
		for (int row = 0; row < 12; row++ ) {
			for(int column = 0; column < 12; column++) {
				if(p.getBoard(row,column,0) == 1) { //Checks the third dim of player for the main board
					btn_plyr[row][column].setBackground(Color.WHITE);// Salt missed
				} else if(p.getBoard(row,column,0) == 3) {
					btn_plyr[row][column].setBackground(Color.RED);// Salt hit
				} else {
					btn_plyr[row][column].setBackground(null);// Salt hasn't been thrown
				}
			}
		}
	}

	public void ShowMiniGrid(Players p) {
		for (int row = 0; row < 12; row++ ) {
			for(int column = 0; column < 12; column++) {
				if(p.getBoard(row,column,0) == 1) { //Checks the third dim of player for the main board
					btn_mini[row][column].setBackground(Color.WHITE);// Salt missed
				} else if(p.getBoard(row,column,0) == 3) {
					btn_mini[row][column].setBackground(Color.RED);// Salt hit
				} else {
					btn_mini[row][column].setBackground(null);// Salt hasn't been thrown
				}
			}
		}
	}

	public void ShowSlugsMiniMap(Players p) {
		for (int row = 0; row < 12; row++) {
			for(int column = 0; column < 12; column++) { //Goes through the whole gird
				if(p.getBoard(row,column,1) == 1) {
					btn_mini[row][column].setBackground(Color.WHITE);//Salt on the board
				} else if(p.getBoard(row,column,1) == 2) {
					btn_mini[row][column].setBackground(Color.GREEN);//Slug on the board
				} else if(p.getBoard(row,column,1) == 3) {
					btn_mini[row][column].setBackground(Color.RED);//Slugs has been hit
				} else if(p.getBoard(row,column,1) == 0) {
					btn_mini[row][column].setBackground(null);//Nothing
				}
			}
		}
	}

	public void Showslugs(Players p) {
		for (int row = 0; row < 12; row++) {
			for(int column = 0; column < 12; column++) {
				if(p.getBoard(row,column,1) == 1) {
					btn_plyr[row][column].setBackground(Color.WHITE);//Displays a miss (salt)
				} else if(p.getBoard(row,column,1) == 2) {
					btn_plyr[row][column].setBackground(Color.GREEN);//Displays a slug
				} else if(p.getBoard(row,column,1) == 3) {
					btn_plyr[row][column].setBackground(Color.RED);//Displays a slug piece hit
				} else if(p.getBoard(row,column,1) == 0) {
					btn_plyr[row][column].setBackground(null);//Nothing there
				}
			}
		}
	}

	public void ClearGrid() {
		for(int row = 0; row < 12; row++) {
			for(int column = 0; column < 12; column++) {
				btn_mini[row][column].setBackground(null);
			}//Simply sets the buttons in the mini map to default
		}
	}
}