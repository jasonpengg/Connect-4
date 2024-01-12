import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;

public class Connect4 implements ActionListener, MouseListener, MouseMotionListener{
	//Properties 
	calculations calcs = new calculations();
	JFrame theFrame = new JFrame("Connect 4");
	ConnectPanel thePanel = new ConnectPanel(calcs.getTheme("Standard Theme"));
	themesPanel themePanel = new themesPanel();
	
	JTextArea chatArea = new JTextArea();
	JScrollPane theScroll = new JScrollPane(chatArea);

	JMenuBar theBar = new JMenuBar();
	
	JButton mainMenu = new JButton("Home");
	JButton themeMenu = new JButton("Theme");
	JButton theme1 = new JButton("Original");
	JButton theme2 = new JButton("Space");
	JButton theme3 = new JButton("Christmas");

	Timer theTimer = new Timer(1000/30, this);
	SuperSocketMaster ssm = null;

	String[] strTheme = new String[5];
	
	int intReleasedX = 0;
	int intReleasedY = 0;
	


	/*
	HOW THE CODE WILL WORK: 
	Mouse listener and MouseMotionLsitener will use booleans to dictate where the piece will be dropped/final place 
	The mouseReleased will be the final coords of where the piece can go and that will start an action of calculations 
	Calcuations include: placing a piece into an array, changing player turns, checking for connect 4s, and SSM related stuff 
	 */
	// Methods
	public void mouseMoved(MouseEvent evt){
		
	}
	public void mouseDragged(MouseEvent evt){
		thePanel.intDraggedX = evt.getX();
		thePanel.intDraggedY = evt.getY();

	}
	public void mouseExited(MouseEvent evt){
	
	}
	public void mouseEntered(MouseEvent evt){
	
	}
	public void mouseReleased(MouseEvent evt){
		//Add a check to see if the player was clicking on a piece beforehand, or else they will place a piece if they click on top 
		
		if(thePanel.blnPlaced == true){
			intReleasedX = evt.getX();
			intReleasedY = evt.getY();
			
			calcs.position(intReleasedX, intReleasedY);
			//Checks to see if the piece placed is in bounds 
			if(calcs.position(intReleasedX, intReleasedY) == true){
				thePanel.intBoard = calcs.place();
				System.out.println("winner is H " +calcs.HorizontalCheckWin());
				System.out.println("winner is V " +calcs.VerticalCheckWin());
				System.out.println("winner is D " +calcs.DiagonalCheckWin());
			}
			thePanel.intTurn = calcs.getPlayerTurn();
			System.out.println(calcs.getPlayerTurn());
			
			thePanel.blnPlaced = false;
			thePanel.blnPressed = false;
			calcs.printBoard();
		}
		//BUG fix???
		thePanel.intDraggedX = -100;
		thePanel.intDraggedY = -100;
	}
	public void mousePressed(MouseEvent evt){
		thePanel.intPressedX = evt.getX();
		thePanel.intPressedY = evt.getY();
		System.out.println(calcs.getPlayer());
		if(calcs.getPlayer().equals("RED")){
			System.out.println("RED");
			thePanel.blnRed = true;
			thePanel.blnYellow = false;
		}else if(calcs.getPlayer().equals("YELLOW")){
			System.out.println("YELLOW");
			thePanel.blnYellow = true;
			thePanel.blnRed = false;
		}
		thePanel.blnPressed = true;
		System.out.println("Pressed = " +thePanel.intPressedX +" "+ thePanel.intPressedY);
	}
	public void mouseClicked(MouseEvent evt){
		
	}
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == mainMenu){
			theFrame.setContentPane(thePanel);
			theFrame.pack();
			theFrame.repaint();
		}
		//-----------------------Theme Menu-----------------------------//
		if(evt.getSource() == themeMenu){
			theFrame.setContentPane(themePanel);
			theFrame.pack();
			theFrame.repaint();
		}
		//Original ("Standard Theme")
		if (evt.getSource() == theme1){
			thePanel.strTheme = calcs.getTheme("Standard Theme");
			thePanel.loadTheme(calcs.getTheme("Standard Theme"));
		}
		//Space ("Space Theme")
		if (evt.getSource() == theme2){
			thePanel.strTheme = calcs.getTheme("Space Theme");
			thePanel.loadTheme(calcs.getTheme("Space Theme"));
		}
		//Christmas ("Christmas Theme")
		if (evt.getSource() == theme3){
			thePanel.strTheme = calcs.getTheme("Christmas Theme");
			thePanel.loadTheme(calcs.getTheme("Christmas Theme"));
		
		}
		
		//--------------ETC-------------------//
		if(evt.getSource() == theTimer){
			thePanel.repaint();
		}
		
		
	}
	// Constructor
	//add timer to the panel to repaint 
	public Connect4(){
		//Initiallizing the Game Setup 
		thePanel.intBoard = calcs.getBoard();
		thePanel.strTheme = calcs.getTheme("Standard Theme");
		thePanel.setPreferredSize(new Dimension(1280, 720));
		thePanel.setLayout(null);
		theFrame.setContentPane(thePanel);
		
		//----------------------------------------- GAME PANEL -----------------------------------------------//
		
		theScroll.setSize(480,400);
		theScroll.setLocation(10,310);
		thePanel.add(theScroll);


		thePanel.addMouseListener(this);
		thePanel.addMouseMotionListener(this);
		// ------------------------------------------------ THEME PANEL --------------------------------------------//
		themePanel.setLayout(null);
		themePanel.setPreferredSize(new Dimension(1280, 720));
		themePanel.setBackground(Color.WHITE);
		
		theme1.setSize(300,100);
		theme1.setLocation(490, 100);
		theme1.addActionListener(this);
		themePanel.add(theme1);
		
		theme2.setSize(300,100);
		theme2.setLocation(60, 100);
		theme2.addActionListener(this);
		themePanel.add(theme2);
		
		theme3.setSize(300,100);
		theme3.setLocation(930, 100);
		theme3.addActionListener(this);
		themePanel.add(theme3);
		
		//----------------------------------------------- GENERAL FRAME ----------------------------------------------//
		
		//Frame and Menu Bar
		theFrame.setJMenuBar(theBar);
		theBar.add(mainMenu);
		theBar.add(themeMenu);
		
		//ActionListener
		mainMenu.addActionListener(this);
		themeMenu.addActionListener(this);
		
		thePanel.repaint();
		theFrame.pack();
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theFrame.setResizable(false);
		theFrame.setVisible(true);
		theTimer.start();
	}

	// Main Method
	public static void main(String[] args){
		new Connect4();
	}
}
