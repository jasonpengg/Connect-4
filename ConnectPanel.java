import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;


public class ConnectPanel extends JPanel{
	// Properties 


	// Methods
	// override how JComponent is painted 
	public void paintComponent(Graphics g){
	g.setColor(Color.BLUE);
	g.fillRect(500,90,770,610);
	
	g.setColor(Color.RED);
	//Horizontal 
	g.fillOval(510,600, 90, 90);
	g.fillOval(620,600, 90, 90);
	g.fillOval(730,600, 90, 90);
	g.fillOval(840,600, 90, 90);
	g.fillOval(950,600, 90, 90);
	g.fillOval(1060,600, 90, 90);
	g.fillOval(1170,600, 90, 90);
	//Vertical 
	g.fillOval(510,500, 90, 90);
	g.fillOval(510,400, 90, 90);
	g.fillOval(510,300, 90, 90);
	g.fillOval(510,200, 90, 90);
	g.fillOval(510,100, 90, 90);

	g.setColor(Color.GRAY);
	g.fillRect(500,0,1,800);
	g.fillRect(610,0,1,800);
	g.fillRect(720,0,1,800);
	g.fillRect(830,0,1,800);
	g.fillRect(940,0,1,800);
	g.fillRect(1050,0,1,800);
	g.fillRect(1160,0,1,800);
	g.fillRect(1270,0,1,800);
	
	}
	//Constructor
	public ConnectPanel (){

		
	}
}
