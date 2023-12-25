import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;

public class Connect4 implements ActionListener, MouseListener, MouseMotionListener{
	//Properties 
	JFrame theFrame = new JFrame("graphics!!!");
	ConnectPanel thePanel = new ConnectPanel();
	JTextArea chatArea = new JTextArea();
	JScrollPane theScroll = new JScrollPane(chatArea);

	Timer theTimer = new Timer(1000/30, this);
	SuperSocketMaster ssm = null;


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
		//System.out.println("Drag = " + thePanel.intDraggedX +" "+thePanel.intDraggedY);
	}
	public void mouseExited(MouseEvent evt){
	
	}
	public void mouseEntered(MouseEvent evt){
	
	}
	public void mouseReleased(MouseEvent evt){

	}
	public void mousePressed(MouseEvent evt){
		thePanel.intPressedX = evt.getX();
		thePanel.intPressedY = evt.getX();
		System.out.println("Pressed = " +thePanel.intPressedX +" "+ thePanel.intPressedY);

	}
	public void mouseClicked(MouseEvent evt){
		
	}
	public void actionPerformed(ActionEvent evt){

	}
	// Constructor
	//add timer to the panel to repaint 
	public Connect4(){
		thePanel.setPreferredSize(new Dimension(1280, 720));
		thePanel.setLayout(null);
		theFrame.setContentPane(thePanel);
		
		theScroll.setSize(480,400);
		theScroll.setLocation(10,310);
		thePanel.add(theScroll);


		theFrame.addMouseListener(this);
		theFrame.addMouseMotionListener(this);
		
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
