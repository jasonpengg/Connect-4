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
	JPanel helpSSMPanel = new JPanel();
	JPanel helpGamePanel = new JPanel();
	JPanel winPanel = new JPanel();
	
	JTextArea chatArea = new JTextArea();
	JScrollPane theScroll = new JScrollPane(chatArea);
	JButton sendButton = new JButton("send");
	JButton hostButton = new JButton("Host");
	JButton joinButton = new JButton("Join");
	JButton getIPButton = new JButton("Get my IP");
	JLabel nameLabel = new JLabel ("Enter Name: ");
	JLabel invalidLabel1 = new JLabel ("invalid: Cannot be empty");
	JLabel ipLabel = new JLabel ("Enter IP Address:");
	JLabel invalidLabel2 = new JLabel ("invalid: Cannot be empty");
	JLabel portLabel = new JLabel ("Enter Port Number:");
	JLabel invalidLabel3 = new JLabel ("invalid: Cannot be empty");
	JTextField sendField = new JTextField();
	
	JLabel turnLabel = new JLabel ("Turn Count: 0");
	JTextField ipField = new JTextField();
	JTextField portField = new JTextField();
	JTextField userField = new JTextField(); 
	
	JLabel winLabel = new JLabel("winner");
	JLabel P1Score = new JLabel ("Player 1: 0W");
	JLabel P2Score = new JLabel ("Player 2: 0W");

	JMenuBar theBar = new JMenuBar();
	
	JButton mainMenu = new JButton("Home");
	JButton themeMenu = new JButton("Theme");
	JButton helpMenu = new JButton("Help");
	JButton helpSSMMenu = new JButton("Help");
	JButton returnSSMMenu = new JButton("Return to Screen");
	JButton returnHomeMenu = new JButton("Return to Screen");
	JButton theme1 = new JButton("Original");
	JButton theme2 = new JButton("Space");
	JButton theme3 = new JButton("Christmas");
	
	JButton playAgain = new JButton("Play Again");
	
	

	Timer theTimer = new Timer(1000/60, this);
	

	String[] strTheme = new String[5];
	
	
	
	//SSM 
	String[] strSSMArray = new String[3];
	SuperSocketMaster ssm = null;
	
	
	//SSM Personal 
	String strUsername ="";
	String strOpponent = "";
	
	
	String strPlayer1 ="";
	String strPlayer2 ="";
	int intPlayer2Score = 0;
	int intPlayer1Score =0;
	
	int intPlayer;
	
	//SSM SentOut
	int intPlayerTurn = 0;
	int intReleasedX = 0;
	int intReleasedY = 0;
	
	String strSplit = ";-;";
	boolean blnGame = false;
	


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
		if(calcs.getPlayer().equals(""+intPlayer)&& thePanel.blnPlaced == true){
			thePanel.intDraggedX = evt.getX();
			thePanel.intDraggedY = evt.getY();
			ssm.sendText("Location"+ strSplit+ (thePanel.intDraggedX - thePanel.intDiffX)+ strSplit + (thePanel.intDraggedY - thePanel.intDiffY));
		}
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
			ssm.sendText("Let Go"+strSplit+"0"+strSplit+"0");
			//Checks to see if the piece placed is in bounds 
			if(calcs.position(intReleasedX, intReleasedY) == true){
				thePanel.intBoard = calcs.place();
				ssm.sendText("Game"+strSplit+calcs.intRow+strSplit+"0");
				results();
				System.out.println(calcs.checkResult());
			}
			turnLabel.setText("Turn Count: "+calcs.getPlayerTurn());
			thePanel.intTurn = calcs.getPlayerTurn();
			System.out.println(calcs.getPlayerTurn());
			
			thePanel.blnPlaced = false;
			thePanel.blnPressed = false;
			calcs.printBoard();
		}
		thePanel.intDraggedX = -1000;
		thePanel.intDraggedY = -1000;
	}
	public void mousePressed(MouseEvent evt){
		thePanel.intPressedX = evt.getX();
		thePanel.intPressedY = evt.getY();
		System.out.println(calcs.getPlayer());
		if(blnGame == true){
			if(calcs.getPlayer().equals(""+intPlayer)){
				if(calcs.getPlayer().equals("1")&&intPlayer == 1){
					System.out.println("RED");
					thePanel.blnRed = true;
					thePanel.blnYellow = false;
				}else if(calcs.getPlayer().equals("2") && intPlayer ==2){
					System.out.println("YELLOW");
					thePanel.blnYellow = true;
					thePanel.blnRed = false;
				}
				thePanel.blnPressed = true;
				System.out.println("Pressed = " +thePanel.intPressedX +" "+ thePanel.intPressedY);
			}
		}
		
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
			turnLabel.setForeground(Color.BLACK);
			P1Score.setForeground(Color.BLACK);
			P2Score.setForeground(Color.BLACK);
			thePanel.strTheme = calcs.getTheme("Standard Theme");
			thePanel.loadTheme(calcs.getTheme("Standard Theme"));
			changeToHomePanel();
		}
		//Space ("Space Theme")
		if (evt.getSource() == theme2){
			turnLabel.setForeground(Color.WHITE);
			P1Score.setForeground(Color.WHITE);
			P2Score.setForeground(Color.WHITE);
			thePanel.strTheme = calcs.getTheme("Space Theme");
			thePanel.loadTheme(calcs.getTheme("Space Theme"));
			changeToHomePanel();
		}
		//Christmas ("Christmas Theme")
		if (evt.getSource() == theme3){
			turnLabel.setForeground(Color.BLACK);
			P1Score.setForeground(Color.BLACK);
			P2Score.setForeground(Color.BLACK);
			thePanel.strTheme = calcs.getTheme("Christmas Theme");
			thePanel.loadTheme(calcs.getTheme("Christmas Theme"));
			changeToHomePanel();
		
		}
		//------------------------SSM --------------------------------//
		//Array Format: 1 = Name 2 = Message 3 = tile choice 4 = X-Cord 5 = Y-Cord 6 = turn
		
		//Send Types: 
		
		//1. Message + Username 
		//eg: Message, Jason, Hey, 
		
		//2. Real time location of the block
		//eg Location, 10, 20, 
		 
		//3. Gameplay data: where they placed the piece 
		//eg. Game, column placed, [0]
		
		
		
		if(evt.getSource() == getIPButton){
			chatArea.append(ssm.getMyAddress() + "\n");
			
		}
		
		
		
		if(evt.getSource() == sendButton){
			System.out.println("send: "+sendField.getText());
			chatArea.append(strUsername +": "+sendField.getText() +"\n");
			ssm.sendText("Message"+strSplit+strUsername+strSplit+sendField.getText());
			sendField.setText("");
			//[message][Username][MMessage]
		}
		if(evt.getSource() == sendField){
			System.out.println("send: "+sendField.getText());
			chatArea.append(strUsername +": "+sendField.getText() +"\n");
			ssm.sendText("Message"+strSplit+strUsername+strSplit+sendField.getText());
			sendField.setText("");
		}
		if (evt.getSource() == hostButton){
			if(checkEmpty(false) == false){
				System.out.println("Start socket in server mode");
				ssm = new SuperSocketMaster(Integer.parseInt(portField.getText()), this);
				ssm.connect();
				strUsername = userField.getText();
				changeToHomePanel();
				this.intPlayer = 1;
				thePanel.intPlayer = this.intPlayer;
				strUsername = userField.getText();
			}
		}
		
		if (evt.getSource() == joinButton){
			if(checkEmpty(true) == false){
				System.out.println("Start socket in client mode");
				ssm = new SuperSocketMaster(ipField.getText(), Integer.parseInt(portField.getText()),this);
				ssm.connect();
				strUsername = userField.getText();
				changeToHomePanel();
				blnGame = true;
				this.intPlayer = 2;
				thePanel.intPlayer = this.intPlayer;
				strUsername = userField.getText();
				ssm.sendText("Message"+strSplit+strUsername+strSplit+"has joined the lobby");
				//chatArea.append(strUsername +": "+sendField.getText() +"\n");
			}
			
		}if (evt.getSource() == helpSSMMenu){
			theFrame.setContentPane(helpSSMPanel);
			theFrame.pack();
			theFrame.repaint();
		}if (evt.getSource() == helpMenu){
			theFrame.setContentPane(helpGamePanel);
			theFrame.pack();
			theFrame.repaint();
		}if (evt.getSource() == returnHomeMenu){
			changeToHomePanel();
		}if (evt.getSource() == returnSSMMenu){
			initializePanel();
		}
		
		if(evt.getSource() == ssm){
			String[] strSSMArray = ssm.readText().split(strSplit);
			if(strSSMArray[2].equals("has joined the lobby")){
				ssm.sendText("Message"+strSplit+strUsername+strSplit+"is the host");
				blnGame = true;
			}
			if(strSSMArray[0].equals("Message")){
				chatArea.append(strSSMArray[1]+": "+strSSMArray[2]+"\n");
				strOpponent = strSSMArray[1];
				checkPlayer();
				
			}else if(strSSMArray[0].equals("Location")){
				//thePanel.drawPiece(strSSMArray);
				thePanel.intSSMX = Integer.parseInt(strSSMArray[1]); 
				thePanel.intSSMY = Integer.parseInt(strSSMArray[2]); 
				thePanel.blnDraw = true;
				//BLN draw needs to be true when ARRAY [0] = game
			}else if(strSSMArray[0].equals("Let Go")){
				thePanel.blnDraw = false;
				
			}else if(strSSMArray[0].equals("Game")){
				thePanel.blnDraw = false;
				calcs.intRow = Integer.parseInt(strSSMArray[1]);
				thePanel.intBoard = calcs.place();
				thePanel.intTurn = calcs.getPlayerTurn();
				turnLabel.setText("Turn Count: "+calcs.getPlayerTurn());
				results();
			}
		}
		if(evt.getSource() == playAgain){
			calcs.resetGame();
			turnLabel.setText("Turn Count: "+calcs.getPlayerTurn());
			intPlayerTurn = 0;
			thePanel.intTurn = 0;
			changeToHomePanel();
		}
		
		
		//--------------ETC-------------------//
		
		if(evt.getSource() == theTimer){
			thePanel.repaint();
			//[Game][x][y]
		}
		
		
	}
	public void initializePanel(){
		theBar.setVisible(false);
		theFrame.setContentPane(SSMPanel);
		theFrame.pack();
		theFrame.repaint();
		theBar.setVisible(false);
	}
	public void changeToHomePanel(){
		theBar.setVisible(true);
		theFrame.setContentPane(thePanel);
		theFrame.pack();
		theFrame.repaint();
			
	} 
	public void checkPlayer(){
		if(intPlayer == 1){
			strPlayer1 = strUsername;
			strPlayer2 = strOpponent; 
		}else if(intPlayer == 2){
			strPlayer2 = strUsername; 
			strPlayer1 = strOpponent;
		}
		P1Score.setText(strPlayer1 + ": " +intPlayer1Score);
		P2Score.setText(strPlayer2 + ": " +intPlayer2Score);
	}
	public void results(){
		if(calcs.checkResult().equals("Player 1")){
			winLabel.setText("The Winner is: " +strPlayer1);
			intPlayer1Score++;
			P1Score.setText(strPlayer1 + ": " +intPlayer1Score+"W");
			theFrame.setContentPane(winPanel);
			theFrame.pack();
			theFrame.repaint();
		}else if(calcs.checkResult().equals ("Player 2")){
			winLabel.setText("The Winner is: " +strPlayer2);
			intPlayer2Score++;
			P2Score.setText(strPlayer2 + ": " +intPlayer2Score+"W");
			theFrame.setContentPane(winPanel);
			theFrame.pack();
			theFrame.repaint();
		}else if(calcs.checkResult().equals("tie")){
			winLabel.setText("Tie! nobody wins");
			theFrame.setContentPane(winPanel);
			theFrame.pack();
			theFrame.repaint();
		}
	}
	public boolean checkEmpty(boolean blnCheckIP){
		boolean blnIP = false; 
		boolean blnPort = false;
		boolean blnUser = false;
		if(ipField.getText().isEmpty() == true&&blnCheckIP == true){
			invalidLabel1.setVisible(true);
			blnIP = true;
		}if(!ipField.getText().isEmpty() == true||blnCheckIP == false){
			invalidLabel1.setVisible(false);
		}if(portField.getText().isEmpty() == true){
			invalidLabel2.setVisible(true);
			blnPort = true;
		}if(!portField.getText().isEmpty() == true){
			invalidLabel2.setVisible(false);
		}if(userField.getText().isEmpty() == true){
			invalidLabel3.setVisible(true);
			blnUser = true;
		}if(!userField.getText().isEmpty() == true){
			invalidLabel3.setVisible(false);
		}
		System.out.println("Port: "+blnPort+" IP: "+blnIP+" USER: "+blnUser);
		if(blnPort == true||blnUser == true){
			return true;
		}else if(blnIP == true && blnCheckIP == true){
			return true;
		}else{
			return false;
		}
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
		
		invalidLabel1.setSize(200, 50); 
		invalidLabel1.setLocation(1000,150);
		SSMPanel.add(invalidLabel1);
		invalidLabel1.setForeground(Color.RED);
		invalidLabel1.setVisible(false);
		
		ipLabel.setSize(200, 50);
		ipLabel.setLocation(300,150);
		SSMPanel.add(ipLabel);
		
		portField.setSize(400, 50);
		portField.setLocation(500,250);
		SSMPanel.add(portField);
		
		portLabel.setSize(200, 50);
		portLabel.setLocation(300,250);
		SSMPanel.add(portLabel);
		
		invalidLabel2.setSize(200, 50); 
		invalidLabel2.setLocation(1000,250);
		SSMPanel.add(invalidLabel2);
		invalidLabel2.setForeground(Color.RED);
		invalidLabel2.setVisible(false);
		
		userField.setSize(400, 50);
		userField.setLocation(500,350);
		SSMPanel.add(userField);
		nameLabel.setSize(200, 50);
		nameLabel.setLocation(300,350);
		SSMPanel.add(nameLabel);
		
		invalidLabel3.setSize(200, 50); 
		invalidLabel3.setLocation(1000,350);
		SSMPanel.add(invalidLabel3);
		invalidLabel3.setForeground(Color.RED);
		invalidLabel3.setVisible(false);
		
		hostButton.setSize(300,50);
		hostButton.setLocation(750, 450);
		SSMPanel.add(hostButton);
		hostButton.addActionListener(this);
		
		joinButton.setSize(300,50);
		joinButton.setLocation(350,450);
		SSMPanel.add(joinButton);
		joinButton.addActionListener(this);
		
		helpSSMMenu.setSize(300,50);
		helpSSMMenu.setLocation(350,550);
		SSMPanel.add(helpSSMMenu);
		helpSSMMenu.addActionListener(this);
		
	
		theFrame.setContentPane(thePanel);
		
		//----------------------------------------- GAME PANEL -----------------------------------------------//
		
		thePanel.intBoard = calcs.getBoard();
		thePanel.strTheme = calcs.getTheme("Standard Theme");
		thePanel.setPreferredSize(new Dimension(1280, 720));
		thePanel.setLayout(null);
		chatArea.setEditable(false);
		theScroll.setSize(480,350);
		theScroll.setLocation(10,310);
		thePanel.add(theScroll);
		
		
		sendField.setSize(400,30);
		sendField.setLocation(10,680);
		thePanel.add(sendField);
		sendField.addActionListener(this);
		
		
		getIPButton.setSize(150,30);
		getIPButton.setLocation(10,280);
		thePanel.add(getIPButton);
		getIPButton.addActionListener(this);
		
		sendButton.setSize(80, 30);
		sendButton.setLocation(410, 680);
		thePanel.add(sendButton);
		sendButton.addActionListener(this);
		
		turnLabel.setSize(100, 30);
		turnLabel.setLocation(0,0);
		thePanel.add(turnLabel);
		
		P1Score.setFont(new Font(P1Score.getFont().getName(), P1Score.getFont().getStyle(), 30));
		P1Score.setSize(300, 40);
		P1Score. setLocation(10,40);
		thePanel.add(P1Score);
		
		P2Score.setFont(new Font(P2Score.getFont().getName(), P2Score.getFont().getStyle(), 30));
		P2Score.setSize(300, 40);
		P2Score. setLocation(10,120);
		thePanel.add(P2Score);


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
		//----------------------------------------------- WIN PANEL ----------------------------------------------//
		winPanel.setPreferredSize(new Dimension(1280, 720));
		winPanel.setLayout(null);
		winLabel.setFont(new Font(winLabel.getFont().getName(), winLabel.getFont().getStyle(), 30));
		winLabel.setSize(400,100);
		winLabel.setLocation(490,300);
		winPanel.add(winLabel);
		
		playAgain.setSize(300,100);
		playAgain.setLocation(490, 400);
		playAgain.addActionListener(this);
		
		winPanel.add(playAgain);
		
		//------------------------------------------------HELP PANELS-------------------------------------------------//
		helpGamePanel.setPreferredSize(new Dimension(1280, 720));
		helpGamePanel.setLayout(null);
		returnHomeMenu.setSize(300,100);
		returnHomeMenu.setLocation(60, 100);
		returnHomeMenu.addActionListener(this);
		helpGamePanel.add(returnHomeMenu);
		
		helpSSMPanel.setPreferredSize(new Dimension(1280, 720));
		helpSSMPanel.setLayout(null);
		returnSSMMenu.setSize(300,100);
		returnSSMMenu.setLocation(60, 100);
		returnSSMMenu.addActionListener(this);
		helpSSMPanel.add(returnSSMMenu);
		
		//----------------------------------------------- GENERAL FRAME ----------------------------------------------//
		
		//Frame and Menu Bar
		theFrame.setJMenuBar(theBar);
		theBar.add(mainMenu);
		theBar.add(themeMenu);
		theBar.add(helpMenu);
		
		//ActionListener
		helpMenu.addActionListener(this);
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
