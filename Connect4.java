import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class Connect4 implements ActionListener{
	//Properties 
	JFrame theframe = new JFrame("graphics!!!");
	ConnectPanel thepanel = new ConnectPanel();
	Timer thetimer = new Timer(1000/30, this);

	// Methods
	public void actionPerformed(ActionEvent evt){

	}
	// Constructor
	public Connect4(){
		thepanel.setPreferredSize(new Dimension(1280, 720));
		theframe.setContentPane(thepanel);
		thepanel.repaint();
		theframe.pack();
		theframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theframe.setVisible(true);
		thetimer.start();
	}

	// Main Method
	public static void main(String[] args){
		new Connect4();
	}
}
