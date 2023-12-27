import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;


public class ConnectPanel extends JPanel{
	// Properties 
	boolean blnPrintGrid = true;
	int intPressedX = 0;
	int intPressedY = 0; 
	int intDraggedX = 0;
	int intDraggedY = 0;
	int intRedX = 390; 
	int intRedY = 210;
	int intYellowX = 390;
	int intYellowY = 110;
	int intDiffX = 0;
	int intDiffY = 0;
	boolean blnDiff = true;
	boolean blnPressed = false;
	boolean blnRed = true;
	boolean blnYellow = true;
	boolean blnPlaced = false;
	BufferedImage imgRed = null;
	BufferedImage imgYellow = null;
	int intBoard[][];
	
	// Methods
	// override how JComponent is painted 
	// Set the Red and Yellow pieces to coordinates 
	
	
	public void paintComponent(Graphics g){
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0,0,1280,720);
		g.setColor(Color.BLUE);
		g.fillRect(500,100,770,610);
		
		g.setColor(Color.RED);
		g.fillOval(intRedX,intRedY, 90, 90);
		g.setColor(Color.YELLOW);
		g.fillOval(intYellowX,intYellowY, 90, 90);
		
		//prints Grid
		g.setColor(Color.WHITE);
		int intX = 400;
		int intY = 10;
		for (int intCount = 0; intCount < 7; intCount++){
			intX = intX +110;
			for(int intCount2 = 0; intCount2 < 6; intCount2++){
				intY = intY + 100;
				if(intBoard[intCount][intCount2] == 0){
					g.setColor(Color.WHITE);
					g.fillOval(intX,intY, 90, 90);
				}else if(intBoard[intCount][intCount2] == 1){
					g.setColor(Color.RED);
					g.fillOval(intX,intY, 90, 90);
				}else if(intBoard[intCount][intCount2] == 2){
					g.setColor(Color.YELLOW);
					g.fillOval(intX,intY, 90, 90);
				}
				
			}
			intY = 10;
		}

		//RED
		if(intPressedX >= (intRedX) && intPressedX <= (intRedX +90)&&intPressedY >= intRedY&& intPressedY <= (intRedY + 90) && blnPressed == true && blnRed == true){
			System.out.println("RED placed");
			intDiffX = intPressedX - intRedX;
			intDiffY = intPressedY - intRedY;
			g.drawImage(imgRed, intDraggedX - intDiffX, intDraggedY - intDiffY, null);
			blnPlaced = true;
		}
		if(intPressedX >= (intYellowX) && intPressedX <= (intYellowX +90)&&intPressedY >= intYellowY&& intPressedY <= (intYellowY + 90) && blnPressed == true && blnYellow == true){
			System.out.println("Yellow placed");
			intDiffX = intPressedX - intYellowX;
			intDiffY = intPressedY - intYellowY;
			g.drawImage(imgYellow, intDraggedX - intDiffX, intDraggedY - intDiffY, null);
			blnPlaced = true;
		}
		//YELLOW
		
		
		
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
		try{
			imgRed = ImageIO.read(new File("red.png"));
		}catch (IOException e){
			System.out.println("cannot load image");
		}
		try{
			imgYellow = ImageIO.read(new File("yellow.png"));
		}catch (IOException e){
			System.out.println("cannot load image");
		}
	}
}
/* IDEAS FOR HOW THE GRID IS ORGANIZED
 * //Horizontal 
	g.fillOval(510,610, 90, 90);
	g.fillOval(620,610, 90, 90);
	g.fillOval(730,610, 90, 90);
	g.fillOval(840,610, 90, 90);
	g.fillOval(950,610, 90, 90);
	g.fillOval(1060,610, 90, 90);
	g.fillOval(1170,610, 90, 90);
	//Vertical 
	g.fillOval(510,510, 90, 90);
	g.fillOval(510,410, 90, 90);
	g.fillOval(510,310, 90, 90);
	g.fillOval(510,210, 90, 90);
	g.fillOval(510,110, 90, 90);

	g.setColor(Color.GRAY);
	g.fillRect(500,0,1,800);
	g.fillRect(610,0,1,800);
	g.fillRect(720,0,1,800);
	g.fillRect(830,0,1,800);
	g.fillRect(940,0,1,800);
	g.fillRect(1050,0,1,800);
	g.fillRect(1160,0,1,800);
	g.fillRect(1270,0,1,800);
	*/
