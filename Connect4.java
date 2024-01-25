import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;

public class Connect4 implements ActionListener, MouseListener, MouseMotionListener{
	//Properties of panels and frames
	calculations calcs = new calculations();
	JFrame theFrame = new JFrame("Connect 4");
	ConnectPanel thePanel = new ConnectPanel(calcs.getTheme("Standard Theme"));
	demoPanel dPanel = new demoPanel(calcs.getTheme("Standard Theme"),calcs.loadDemoBoard());
	themesPanel themePanel = new themesPanel();
	JPanel SSMPanel = new JPanel();
	ssmhelpscreenpanel helpSSMPanel = new ssmhelpscreenpanel();
	helpPanel helpGamePanel = new helpPanel();
	winScreenPanel winPanel = new winScreenPanel();

	//Properties for SSM Panel
	JButton sendButton = new JButton("send");
	JButton hostButton = new JButton("Host");
	JButton joinButton = new JButton("Join");
	JButton getIPButton = new JButton("Get my IP");
	JButton demoButton = new JButton("Demonstration");
	
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
	
	
	//Properties for game panel 
	JTextArea chatArea = new JTextArea();
	JScrollPane theScroll = new JScrollPane(chatArea);
	
	JLabel winLabel = new JLabel("winner");
	JLabel P1Score = new JLabel ("Player 1: 0W");
	JLabel P2Score = new JLabel ("Player 2: 0W");
	JMenuBar theBar = new JMenuBar();
	
	//Properties for themes panel 
	JButton mainMenu = new JButton("Home");
	JButton themeMenu = new JButton("Theme");
	JButton helpMenu = new JButton("Help");
	JButton helpSSMMenu = new JButton("Help");
	JButton returnSSMMenu = new JButton("Return to Screen");
	JButton returnHomeMenu = new JButton("Return to Screen");
	JButton theme1 = new JButton("Original");
	JButton theme2 = new JButton("Space");
	JButton theme3 = new JButton("Christmas");
	
	//Properties for play again screen 
	JButton playAgain = new JButton("Play Again");
	
	//Timer for frame refresh rate
	Timer theTimer = new Timer(1000/60, this);
	Timer demoTimer = new Timer(1000, this);
	int intSeconds = 0;
	
	//Theme array
	String[] strTheme = new String[5];
	
	//SSM 
	String[] strSSMArray = new String[3];
	SuperSocketMaster ssm = null;
	
	//SSM Personal 
	String strUsername ="";
	String strOpponent = "";
	
	//SSM values
	String strPlayer1 ="";
	String strPlayer2 ="";
	int intPlayer2Score = 0;
	int intPlayer1Score =0;
	int intPlayer;
	
	//SSM SentOut
	int intPlayerTurn = 0;
	int intReleasedX = 0;
	int intReleasedY = 0;
	
	//Setting up the split value (value used to split SSM messages into array)
	String strSplit = ";-;";
	//Setting the boolean game value. Is true when someone joins the lobby
	boolean blnGame = false;
	
	//Variables for help screen
	boolean blnHelpDemo = false;

	// Methods
	//Overriding mouseMoved method
	public void mouseMoved(MouseEvent evt){
		
	}
	//If player presses on piece and it's their turn get the coordinates of their piece to draw and send over on SSM to opponent so it is drawn on their screen
	public void mouseDragged(MouseEvent evt){
		if(calcs.getPlayer().equals(""+intPlayer)&& thePanel.blnPlaced == true){
			thePanel.intDraggedX = evt.getX();
			thePanel.intDraggedY = evt.getY();
			ssm.sendText("Location"+ strSplit+ (thePanel.intDraggedX - thePanel.intDiffX)+ strSplit + (thePanel.intDraggedY - thePanel.intDiffY));
		}
		//If they are pressing piece when in the demo get the coordinates of their mouse when they are dragging
		if(blnHelpDemo == true){
			dPanel.intDraggedX = evt.getX();
			dPanel.intDraggedY = evt.getY();
		}
	}
	//overriding mouseExited method
	public void mouseExited(MouseEvent evt){
	
	}
	//overriding mouseEntered method
	public void mouseEntered(MouseEvent evt){
	
	}
	//Method: if the piece that is being dragged is released over the board (blnPlaced) then undergo calculations of filling up the board and sending data to opponent
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
			}
			//Calculations adjusting turn, and adjusting variables to keep accurate turn respective calculations
			turnLabel.setText("Turn Count: "+calcs.getPlayerTurn());
			thePanel.intTurn = calcs.getPlayerTurn();
			System.out.println(calcs.getPlayerTurn());
			
			thePanel.blnPlaced = false;
			thePanel.blnPressed = false;
			calcs.printBoard(calcs.intBoard);
		}
		thePanel.intDraggedX = -1000;
		thePanel.intDraggedY = -1000;
		
		//If user is on the help demonstration undergo these calculations instead 
		//If they release pieced within specific range of coordinates they "win" and demonstration timer will begin 
		if(blnHelpDemo == true && dPanel.blnPlaced == true){
			dPanel.blnPressed = false;
			dPanel.intDraggedX = -1000;
			dPanel.intDraggedY = -1000;
			intReleasedX = evt.getX();
			intReleasedY = evt.getY();
			if(intReleasedX >= 830 && intReleasedX <= 940 && intReleasedY < 100){
				System.out.println("win");
				dPanel.blnWin = true;
				demoTimer.start();
			}
		}
	}
	//When the mouse is pressed do a bunch of checks of if gameplay is enabled (have both players joined), which players turn it is then set booleans regarding panel variables accordingly 
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
		//if player is on help demonstration run this code. Code gets coordinates of where mouse was clicked
		if(blnHelpDemo == true){
			dPanel.intPressedX = evt.getX();
			dPanel.intPressedY = evt.getY();
			dPanel.blnPressed = true;
		}
	}
	//overriding mouseClicked method
	public void mouseClicked(MouseEvent evt){
		
	}
	//overriding actionPerformed method
	public void actionPerformed(ActionEvent evt){
		//Setting content pane in accordance to which menu button is pressed
		if(evt.getSource() == mainMenu){
			theFrame.setContentPane(thePanel);
			theFrame.pack();
			theFrame.repaint();
		}
		//-----------------------Theme Menu-----------------------------//
		//Setting content pane in accordance to which menu button is pressed
		if(evt.getSource() == themeMenu){
			theFrame.setContentPane(themePanel);
			theFrame.pack();
			theFrame.repaint();
		}
		//Setting visuals to Original theme when player presses button - "Standard Theme"
		if (evt.getSource() == theme1){
			turnLabel.setForeground(Color.BLACK);
			P1Score.setForeground(Color.BLACK);
			P2Score.setForeground(Color.BLACK);
			thePanel.strTheme = calcs.getTheme("Standard Theme");
			thePanel.loadTheme(calcs.getTheme("Standard Theme"));
			changeToHomePanel();
		}
		//Setting visuals to Space theme when player presses button - "Space Theme"
		if (evt.getSource() == theme2){
			turnLabel.setForeground(Color.WHITE);
			P1Score.setForeground(Color.WHITE);
			P2Score.setForeground(Color.WHITE);
			thePanel.strTheme = calcs.getTheme("Space Theme");
			thePanel.loadTheme(calcs.getTheme("Space Theme"));
			changeToHomePanel();
		}
		//Setting visuals to Christmas theme when player presses button - "Christmas Theme"
		if (evt.getSource() == theme3){
			turnLabel.setForeground(Color.BLACK);
			P1Score.setForeground(Color.BLACK);
			P2Score.setForeground(Color.BLACK);
			thePanel.strTheme = calcs.getTheme("Christmas Theme");
			thePanel.loadTheme(calcs.getTheme("Christmas Theme"));
			changeToHomePanel();
		
		}
		//------------------------SSM --------------------------------//
		//Send Types: 
		//1. Message + Username 
		//eg: Message, Jason, Hey, 
		//2. Real time location of the block
		//eg Location, 10, 20, 
		//3. Gameplay data: where they placed the piece 
		//eg. Game, column placed, [0]
		
		//if person presses getIPButton, append their ip address to the textbox
		if(evt.getSource() == getIPButton){
			chatArea.append(ssm.getMyAddress() + "\n");
		}
		//When users click the send button append the sendField text to the text area while in demonstration mode as (You: 'text')
		//When user clicks send button and is in game, send the message in specific format (message;-;Username;-;message)
		if(evt.getSource() == sendButton){
			if(blnHelpDemo == true){
				System.out.println("send");
				chatArea.append("You: "+sendField.getText() +"\n");
				chatArea.setCaretPosition(chatArea.getDocument().getLength());
			}else if(blnHelpDemo == false){
				System.out.println("send: "+sendField.getText());
				chatArea.append(strUsername +": "+sendField.getText() +"\n");
				chatArea.setCaretPosition(chatArea.getDocument().getLength());
				ssm.sendText("Message"+strSplit+strUsername+strSplit+sendField.getText());
				sendField.setText("");
				//[Message][Username][TextMessage]
			}
		}
		//If user presses enter into the send field, send all text data within the sendField if in demonstratin mode as (You: 'text')
		//When user clicks send button and is in game, send the message in specific format (message;-;Username;-;Textmessage)
		if(evt.getSource() == sendField){
			if(blnHelpDemo == true){
				chatArea.append("You: "+sendField.getText() +"\n");
				chatArea.setCaretPosition(chatArea.getDocument().getLength());
			}else if(blnHelpDemo == false){
				System.out.println("send: "+sendField.getText());
				chatArea.append(strUsername +": "+sendField.getText() +"\n");
				chatArea.setCaretPosition(chatArea.getDocument().getLength());
				ssm.sendText("Message"+strSplit+strUsername+strSplit+sendField.getText());
				sendField.setText("");
				//[Message][Username][TextMessage]
			}
		}
		//If the user presses host button and there are no empty sections start up super socket master in host mode and set player number to 1
		if (evt.getSource() == hostButton){
			if(checkEmpty(false) == false){
				initializeHomePanel();
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
		//If user presses join button and does not have any empty sections, start up super socket master and join as a client
		//Also send an initial message to other player to let them know you have joined successfully
		if (evt.getSource() == joinButton){
			if(checkEmpty(true) == false){
				initializeHomePanel();
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
			}
		//Setting content pane in accordance to button pressed 
		}if (evt.getSource() == helpSSMMenu){
			theFrame.setContentPane(helpSSMPanel);
			theFrame.pack();
			theFrame.repaint();
		}if(evt.getSource() == demoButton){
			theFrame.setContentPane(dPanel);
			theFrame.pack();
			theFrame.repaint();
			blnHelpDemo = true;
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
			//split up incoming ssm message by ;-; as the 'splitting value'
			try{
				String[] strSSMArray = ssm.readText().split(strSplit);
				//If the message of 'has joined the lobby' is recieved the game is initalized and players can now play
				if(strSSMArray[2].equals("has joined the lobby")){
					ssm.sendText("Message"+strSplit+strUsername+strSplit+"is the host");
					blnGame = true;
				}
				//If the first split of the SSM message is 'message' append it to the chat 
				if(strSSMArray[0].equals("Message")){
					chatArea.append(strSSMArray[1]+": "+strSSMArray[2]+"\n");
					chatArea.setCaretPosition(chatArea.getDocument().getLength());
					strOpponent = strSSMArray[1];
					checkPlayer();
				//if the first split of the array is 'Location', set values of intSSMX and intSSMY to draw in calculations/gui panel
				}else if(strSSMArray[0].equals("Location")){
					//thePanel.drawPiece(strSSMArray);
					thePanel.intSSMX = Integer.parseInt(strSSMArray[1]); 
					thePanel.intSSMY = Integer.parseInt(strSSMArray[2]); 
					thePanel.blnDraw = true;
				//If SSM message first split is let go, stop drawing the opponents piece
				}else if(strSSMArray[0].equals("Let Go")){
					thePanel.blnDraw = false;
				//if the first split of the SSM message is game, complete calculations based on the game such as turn count
				}else if(strSSMArray[0].equals("Game")){
					thePanel.blnDraw = false;
					calcs.intRow = Integer.parseInt(strSSMArray[1]);
					thePanel.intBoard = calcs.place();
					thePanel.intTurn = calcs.getPlayerTurn();
					turnLabel.setText("Turn Count: "+calcs.getPlayerTurn());
					results();
				}
			}catch(ArrayIndexOutOfBoundsException e){
				System.out.println("Badly Formatted Data");
			}
		}
		//If the players press play again button enable the play again method to start the game over
		if(evt.getSource() == playAgain){
			calcs.resetGame();
			turnLabel.setText("Turn Count: "+calcs.getPlayerTurn());
			intPlayerTurn = 0;
			thePanel.intTurn = 0;
			changeToHomePanel();
		}
		//when the timer goes off, check conditions to decide if drawing is needed
		if(evt.getSource() == theTimer){
			if(blnHelpDemo == false){
				thePanel.repaint();
			}else if(blnHelpDemo == true){
				dPanel.repaint();
			}
		}
		//when demonstration timer goes off, draw neccessary components
		if(evt.getSource() == demoTimer){
			intSeconds = intSeconds + 1;
			dPanel.intSeconds = intSeconds;
			if(intSeconds >= 6){
				winPanel.blnWin = true;
				theFrame.setContentPane(winPanel);
				theFrame.pack();
				theFrame.repaint();
				playAgain.setVisible(false);
				if(intSeconds >= 8){
					demoTimer.stop();
					intSeconds = 0;
					dPanel.intSeconds = 0;
					dPanel.blnWin = false;
					blnHelpDemo = false;
					playAgain.setVisible(true);
					theFrame.setContentPane(SSMPanel);
					theFrame.pack();
					theFrame.repaint();
				}
			}
		}
	}
	//Initialize the content pane to the SSM panel and set up frame 
	public void initializePanel(){
		theBar.setVisible(false);
		theFrame.setContentPane(SSMPanel);
		theFrame.pack();
		theFrame.repaint();
		theBar.setVisible(false);
	}
	//Setting up chat Area and scroll as well as buttons 
	public void initializeHomePanel(){
		theScroll.setSize(480,350);
		theScroll.setLocation(10,310);
		chatArea.setText("");
		thePanel.add(theScroll);
		
		sendField.setSize(400,30);
		sendField.setLocation(10,680);
		sendField.setText("");
		thePanel.add(sendField);
		
		sendButton.setSize(80, 30);
		sendButton.setLocation(410, 680);
		thePanel.add(sendButton);
		
		theScroll.setSize(480,350);
		theScroll.setLocation(10,310);
		thePanel.add(theScroll);
	} 
	//Method is setting content pane the panel 
	public void changeToHomePanel(){
		theBar.setVisible(true);
		theFrame.setContentPane(thePanel);
		theFrame.pack();
		theFrame.repaint();
	} 
	//Method sets the username for both players based on if they're player 1 or two and sets up 'score board'
	public void checkPlayer(){
		if(intPlayer == 1){
			strPlayer1 = strUsername;
			strPlayer2 = strOpponent; 
		}else if(intPlayer == 2){
			strPlayer2 = strUsername; 
			strPlayer1 = strOpponent;
		}
		P1Score.setText(strPlayer1 + ": " +intPlayer1Score+"W");
		P2Score.setText(strPlayer2 + ": " +intPlayer2Score+"W");
	}
	//Method to check which player won or if there is a tie and adjusting score board 
	public void results(){
		if(calcs.checkResult().equals("Player 1")){
			winLabel.setText("The Winner is: " +strPlayer1);
			intPlayer1Score++;
			P1Score.setText(strPlayer1 + ": " +intPlayer1Score+"W");
			winPanel.blnWin = true;
			theBar.setVisible(false);
			theFrame.setContentPane(winPanel);
			theFrame.pack();
			theFrame.repaint();
		}else if(calcs.checkResult().equals ("Player 2")){
			winLabel.setText("The Winner is: " +strPlayer2);
			intPlayer2Score++;
			P2Score.setText(strPlayer2 + ": " +intPlayer2Score+"W");
			theBar.setVisible(false);
			winPanel.blnWin = true;
			theFrame.setContentPane(winPanel);
			theFrame.pack();
			theFrame.repaint();
		}else if(calcs.checkResult().equals("tie")){
			winLabel.setText("Tie! nobody wins");
			theBar.setVisible(false);
			winPanel.blnWin = false;
			theFrame.setContentPane(winPanel);
			theFrame.pack();
			theFrame.repaint();
		}
	}
	//Checks for validity of the text entered in the text fields
	public boolean checkEmpty(boolean blnCheckIP){
		boolean blnIP = false; 
		boolean blnPort = false;
		boolean blnUser = false;
		int intTimes = 0;
		//Checks if the textfield is empty 
		if(ipField.getText().isEmpty() == true&&blnCheckIP == true){
			invalidLabel1.setVisible(true);
			invalidLabel1.setText("invalid: Cannot be empty");
			blnIP = true;
		}if(!ipField.getText().isEmpty() == true||blnCheckIP == false){
			invalidLabel1.setVisible(false);
			invalidLabel1.setText("invalid: Cannot be empty");
		}if(portField.getText().isEmpty() == true){
			invalidLabel2.setVisible(true);
			invalidLabel2.setText("invalid: Cannot be empty");
			blnPort = true;
		}if(!portField.getText().isEmpty() == true){
			invalidLabel2.setVisible(false);
			invalidLabel2.setText("invalid: Cannot be empty");
		}if(userField.getText().isEmpty() == true){
			invalidLabel3.setVisible(true);
			invalidLabel3.setText("invalid: Cannot be empty");
			blnUser = true;
		}if(!userField.getText().isEmpty() == true){
			invalidLabel3.setVisible(false);
			invalidLabel3.setText("invalid: Cannot be empty");
		}
		//Checks if player put in string data into the ip address text field
		if(blnIP == false && blnCheckIP == true){
			try{
				String strInput = ipField.getText();
				int intLength = strInput.length();
				System.out.println(intLength);
				String strCheck = "";
				String strIP = "";
				for (int intCount = 0; intCount < intLength; intCount++){
					strCheck = strInput.substring(intCount, intCount+1);
					if(strCheck.equals(".")){
						strCheck = "";
					}
					strIP = strIP + strCheck;
				}
				Integer.parseInt(strIP);
			}catch(NumberFormatException e){
				System.out.println("caught");
				blnIP = true; 
				invalidLabel1.setVisible(true);
				invalidLabel1.setText("Must input a valid IP");
			}
		}
		//Checks if the user put in string data into the port text area
		if(blnPort == false){
			try{
				String strInput = portField.getText();
				int intLength = strInput.length();
				String strCheck = "";
				String strIP = "";
				for (int intCount = 0; intCount < intLength; intCount++){
					strCheck = strInput.substring( intCount, intCount+1);
					if(strCheck.equals(".")){
						strCheck = "";
					}
					strIP = strIP + strCheck;
				}
				Integer.parseInt(strIP);
			}catch(NumberFormatException e){
				System.out.println("caught");
				blnPort = true; 
				invalidLabel2.setVisible(true);
				invalidLabel2.setText("Must input a valid Port Number");
			}
		}
		//Checks length of name
		if(blnUser == false){
			String strInput = userField.getText();
			int intLength = strInput.length();
			System.out.println(intLength);
			if(intLength >12){
				blnUser = true;
				invalidLabel3.setVisible(true);
				invalidLabel3.setText("Must be less than 12 characters");
			}
		}
		//checks for Port length
		if(blnPort == false){
			String strInput = portField.getText();
			int intLength = strInput.length();
			System.out.println(intLength);
			if(intLength >5){
				blnUser = true;
				invalidLabel2.setVisible(true);
				invalidLabel2.setText("Must be less than 5 numbersh");
			}
		}
		
		System.out.println("Port: "+blnPort+" IP: "+blnIP+" USER: "+blnUser);
		//if statements to check conditions and finally return boolean value 
		if(blnPort == true||blnUser == true){
			return true;
		}else if(blnIP == true && blnCheckIP == true){
			return true;
		}else{
			return false;
		}
	}
	// Constructor
	public Connect4(){
		//Initializing the Game Setup 
		//---------------------------SSM PANEL ---------------------------------//
		//Setting up panel and additional Jcomponents of the panel
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
		
		demoButton.setSize(300,50);
		demoButton.setLocation(750,550);
		SSMPanel.add(demoButton);
		demoButton.addActionListener(this);
	
		theFrame.setContentPane(thePanel);
		
		//----------------------------------------- GAME PANEL -----------------------------------------------//
		//Setting up panel and additional Jcomponents of the panel
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
		//----------------------------------------------------DEMONSTRATION PANEL----------------------------------//
		//Setting up panel and additional Jcomponents of the panel
		dPanel.strTheme = calcs.getTheme("Standard Theme");
		dPanel.setPreferredSize(new Dimension(1280, 720));
		dPanel.setLayout(null);
		chatArea.setEditable(false);
		theScroll.setSize(480,350);
		theScroll.setLocation(10,310);
		dPanel.add(theScroll);
		
		sendField.setSize(400,30);
		sendField.setLocation(10,680);
		dPanel.add(sendField);

		
		sendButton.setSize(80, 30);
		sendButton.setLocation(410, 680);
		dPanel.add(sendButton);

		dPanel.addMouseListener(this);
		dPanel.addMouseMotionListener(this);
		// ------------------------------------------------ THEME PANEL --------------------------------------------//
		//Setting up panel and additional Jcomponents of the panel
		themePanel.setLayout(null);
		themePanel.setPreferredSize(new Dimension(1280, 720));
		themePanel.setBackground(Color.WHITE);
		
		//setting up theme one 
		theme1.setSize(300,100);
		theme1.setLocation(490, 100);
		theme1.addActionListener(this);
		themePanel.add(theme1);
		
		//setting up theme 2
		theme2.setSize(300,100);
		theme2.setLocation(60, 100);
		theme2.addActionListener(this);
		themePanel.add(theme2);
		
		//setting up theme 3
		theme3.setSize(300,100);
		theme3.setLocation(930, 100);
		theme3.addActionListener(this);
		themePanel.add(theme3);
		//----------------------------------------------- WIN PANEL ----------------------------------------------//
		//Setting up panel and additional Jcomponents of the panel
		winPanel.setPreferredSize(new Dimension(1280, 720));
		winPanel.setLayout(null);
		winLabel.setFont(new Font(winLabel.getFont().getName(), winLabel.getFont().getStyle(), 30));
		winLabel.setSize(400,100);
		winLabel.setLocation(490,300);
		winPanel.add(winLabel);
		
		//Setting up panel and additional Jcomponents of the panel
		playAgain.setSize(300,100);
		playAgain.setLocation(490, 400);
		playAgain.addActionListener(this);
		
		winPanel.add(playAgain);
		
		//------------------------------------------------HELP PANEL-------------------------------------------------//
		//Setting up panel and additional Jcomponents of the panel
		helpGamePanel.setPreferredSize(new Dimension(1280, 720));
		helpGamePanel.setLayout(null);

		returnHomeMenu.setSize(300,50);
		returnHomeMenu.setLocation(100,450);
		returnHomeMenu.setForeground(Color.BLUE);
		helpGamePanel.add(returnHomeMenu);
		returnHomeMenu.addActionListener(this);
		
		//Setting up panel and additional Jcomponents of the panel
		helpSSMPanel.setPreferredSize(new Dimension(1280, 720));
		helpSSMPanel.setLayout(null);
		returnSSMMenu.setSize(300,50);
		returnSSMMenu.setLocation(550, 650);
		returnSSMMenu.setForeground(Color.BLUE);
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
		
		//Setting up frame
		thePanel.repaint();
		theFrame.pack();
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theFrame.setResizable(false);
		theFrame.setVisible(true);
		theTimer.start();
		initializePanel();
	}
	//Main Method
	public static void main(String[] args){
		new Connect4();
	}
}
