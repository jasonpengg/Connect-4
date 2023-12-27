import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;

public class Connect4 implements ActionListener, MouseListener, MouseMotionListener{
	//Properties 
	calculations calcs = new calculations();
	
	JFrame theFrame = new JFrame("graphics!!!");
	ConnectPanel thePanel = new ConnectPanel();
	JTextArea chatArea = new JTextArea();
	JScrollPane theScroll = new JScrollPane(chatArea);

	Timer theTimer = new Timer(1000/30, this);
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
			}
			int intCount = calcs.getPlayerTurn();
			System.out.println(intCount);
			
			thePanel.blnPlaced = false;
			thePanel.blnPressed = false;
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
		if(evt.getSource() == theTimer){
			thePanel.repaint();
		}
		
	}
	// Constructor
	//add timer to the panel to repaint 
	public Connect4(){
		//Initiallizing the Game Setup 
		thePanel.intBoard = calcs.getBoard();
		
		
		thePanel.setPreferredSize(new Dimension(1280, 720));
		thePanel.setLayout(null);
		theFrame.setContentPane(thePanel);
		
		theScroll.setSize(480,400);
		theScroll.setLocation(10,310);
		thePanel.add(theScroll);


		thePanel.addMouseListener(this);
		thePanel.addMouseMotionListener(this);
		
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
