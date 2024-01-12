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
	
	//Themes
	BufferedImage imgPlayer1 = null;
	BufferedImage imgPlayer2 = null;
	BufferedImage imgBackground = null;
	BufferedImage imgBoard = null;
	BufferedImage imgArrow = null;
	
	int intTurn = 0;
	int intBoard[][];
	
	String[] strTheme = {"","","","",""};
	
	
	// Methods
	// override how JComponent is painted 
	// Set the Red and Yellow pieces to coordinates 
	
	//"Space Theme"
	//"Standard Theme"
	//"Christmas Theme"
	
	public void paintComponent(Graphics g){
		//Sets Background 
		g.drawImage(imgBackground, 0, 0, null);
		g.drawImage(imgBoard, 500, 100, null);
	
		g.drawImage(imgPlayer1,intRedX, intRedY, null);
		 
		g.drawImage(imgPlayer2, intYellowX, intYellowY, null);
		
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
					g.drawImage(imgPlayer1, intX, intY, null);
					
				}else if(intBoard[intCount][intCount2] == 2){
					g.drawImage(imgPlayer2, intX, intY, null);
					
				}
				
			}
			intY = 10;
		}

		//Player1
		if(intPressedX >= (intRedX) && intPressedX <= (intRedX +90)&&intPressedY >= intRedY&& intPressedY <= (intRedY + 90) && blnPressed == true && blnRed == true){
			//System.out.println("RED placed");
			intDiffX = intPressedX - intRedX;
			intDiffY = intPressedY - intRedY;
			g.drawImage(imgPlayer1, intDraggedX - intDiffX, intDraggedY - intDiffY, null);
			blnPlaced = true;
		}
		//Player2
		if(intPressedX >= (intYellowX) && intPressedX <= (intYellowX +90)&&intPressedY >= intYellowY&& intPressedY <= (intYellowY + 90) && blnPressed == true && blnYellow == true){
			//System.out.println("Yellow placed");
			intDiffX = intPressedX - intYellowX;
			intDiffY = intPressedY - intYellowY;
			g.drawImage(imgPlayer2, intDraggedX - intDiffX, intDraggedY - intDiffY, null);
			blnPlaced = true;
		}
		//Determine turn order 
		//% 2 = Player 2
		if(this.intTurn % 2 ==0){
			g.drawImage(imgArrow, 200 ,220, null);
		}else if(this.intTurn % 2 ==1){
			g.drawImage(imgArrow, 200 ,120, null);
		}
		
		
		
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
		
	public void loadTheme(String[] strTheme){
		this.strTheme = strTheme;
		System.out.println("loading new images");
		try{
			imgPlayer1 = ImageIO.read(new File(strTheme[1]));
		}catch (IOException e){
			System.out.println("cannot load image");
		}
		try{
			imgPlayer2 = ImageIO.read(new File(strTheme[2]));
		}catch (IOException e){
			System.out.println("cannot load image");
		}
		try{
			imgBoard= ImageIO.read(new File(strTheme[3]));
		}catch (IOException e){
			System.out.println("cannot load image");
		}
		try{
			imgBackground = ImageIO.read(new File(strTheme[4]));
		}catch (IOException e){
			System.out.println("cannot load image");
		}
		try{
			imgArrow = ImageIO.read(new File("arrow.png"));
		}catch (IOException e){
			System.out.println("cannot load image");
		}
	}
	//Constructor
	public ConnectPanel (String[] strTheme){
		this.strTheme = strTheme;
		System.out.println("loading new images");
		loadTheme(strTheme);
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
