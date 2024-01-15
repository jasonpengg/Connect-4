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
	JPanel SSMPanel = new JPanel();
	
	
	
	JTextArea chatArea = new JTextArea();
	JScrollPane theScroll = new JScrollPane(chatArea);
	JButton sendButton = new JButton("send");
	JButton hostButton = new JButton("Host");
	JButton joinButton = new JButton("Join");
	JLabel nameLabel = new JLabel ("Enter Name: ");
	JLabel ipLabel = new JLabel ("Enter IP Adress:");
	JLabel portLabel = new JLabel ("Enter Port Number:");
	JTextField sendField = new JTextField();
	 
	JTextField ipField = new JTextField();
	JTextField portField = new JTextField();
	JTextField userField = new JTextField(); 
	

	JMenuBar theBar = new JMenuBar();
	
	JButton mainMenu = new JButton("Home");
	JButton themeMenu = new JButton("Theme");
	JButton theme1 = new JButton("Original");
	JButton theme2 = new JButton("Space");
	JButton theme3 = new JButton("Christmas");
	
	

	Timer theTimer = new Timer(1000/30, this);
	

	String[] strTheme = new String[5];
	
	
	//SSM 
	SuperSocketMaster ssm = null;
	
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
		//------------------------SSM --------------------------------//
		if(evt.getSource() == sendButton){
			System.out.println("send: "+sendField.getText());
			ssm.sendText(sendField.getText());
			chatArea.append(sendField.getText() +"\n");
		}
		
		//--------------ETC-------------------//
		
		if(evt.getSource() == theTimer){
			thePanel.repaint();
		}
		
		
	}
	public void initializePanel(){
		theFrame.setContentPane(SSMPanel);
		theFrame.pack();
		theFrame.repaint();
		theBar.setVisible(false);
	}
	// Constructor
	//add timer to the panel to repaint 
	public Connect4(){
		//Initiallizing the Game Setup 
		//---------------------------SSM PANEL ---------------------------------//
		
		
		SSMPanel.setPreferredSize(new Dimension(1280, 720));
		SSMPanel.setLayout(null);
		
		ipField.setSize(400, 50);
		ipField.setLocation(500,150);
		SSMPanel.add(ipField);
		ipField.addActionListener(this);
		
		ipLabel.setSize(200, 50);
		ipLabel.setLocation(300,150);
		SSMPanel.add(ipLabel);
		
		portField.setSize(400, 50);
		portField.setLocation(500,250);
		SSMPanel.add(portField);
		portLabel.setSize(200, 50);
		portLabel.setLocation(300,250);
		SSMPanel.add(portLabel);
		
		userField.setSize(400, 50);
		userField.setLocation(500,350);
		SSMPanel.add(userField);
		nameLabel.setSize(200, 50);
		nameLabel.setLocation(300,350);
		SSMPanel.add(nameLabel);
		
		
		

		theFrame.setContentPane(thePanel);
		
		//----------------------------------------- GAME PANEL -----------------------------------------------//
		
		thePanel.intBoard = calcs.getBoard();
		thePanel.strTheme = calcs.getTheme("Standard Theme");
		thePanel.setPreferredSize(new Dimension(1280, 720));
		thePanel.setLayout(null);
		theScroll.setSize(480,350);
		theScroll.setLocation(10,310);
		thePanel.add(theScroll);
		
		
		sendField.setSize(400,30);
		sendField.setLocation(10,680);
		thePanel.add(sendField);
		sendField.addActionListener(this);
		
		sendButton.setSize(80, 30);
		sendButton.setLocation(410, 680);
		thePanel.add(sendButton);
		sendButton.addActionListener(this);
		


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
		initializePanel();
	}

	// Main Method
	public static void main(String[] args){
		new Connect4();
	}
}
