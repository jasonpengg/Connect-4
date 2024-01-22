import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;


public class ConnectPanel extends JPanel{
	/**properties 
	 * @return hi
	 */
	boolean blnPrintGrid = true;
	int intPressedX = -0;
	int intPressedY = -0; 
	int intDraggedX = -1000;
	int intDraggedY = -1000;
	int intRedX = 390; 
	int intRedY = 210;
	int intYellowX = 390;
	int intYellowY = 110;
	int intDiffX = -5000;
	int intDiffY = -5000;
	boolean blnDiff = true;
	boolean blnPressed = false;
	boolean blnRed = true;
	boolean blnYellow = true;
	boolean blnPlaced = false;
	
	//SSM variables
	int intSSMX = 0;
	int intSSMY = 0;
	int intPlayer = 0;
	boolean blnDraw = false;
	
	//Theme variables 
	BufferedImage imgPlayer1 = null;
	BufferedImage imgPlayer2 = null;
	BufferedImage imgBackground = null;
	BufferedImage imgBoard = null;
	BufferedImage imgArrow = null;
	BufferedImage imgHighlight = null;
	String[] strTheme = {"","","","",""};
	
	//Game variables
	int intTurn = 0;
	int intBoard[][];
	
	
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
	
		//Draw red and yellow piece at resting place
		g.drawImage(imgPlayer1,intRedX, intRedY, null);
		g.drawImage(imgPlayer2, intYellowX, intYellowY, null);
		
		//prints gameboard
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
		//If mouse is pressed within specific coordinates draw the checker where the player dragged the mouse
		if(intPressedX >= (intRedX) && intPressedX <= (intRedX +90)&&intPressedY >= intRedY&& intPressedY <= (intRedY + 90) && blnPressed == true && blnRed == true){
			//System.out.println("RED placed");
			//To constantly draw the checker as if the mouse is "holding" it, we must calcualte the distance the mouse is from the top right of the image to accurately draw the image
			intDiffX = intPressedX - intRedX;
			intDiffY = intPressedY - intRedY;
			//Drawing checker
			g.drawImage(imgPlayer1, intDraggedX - intDiffX, intDraggedY - intDiffY, null);
			//System.out.println(intDraggedX +","+ intDiffX +","+ intDraggedY +","+ intDiffY);
			blnPlaced = true;
		}
		//Player2
		//If mouse is pressed within specific coordinates draw the checker where the player dragged the mouse
		if(intPressedX >= (intYellowX) && intPressedX <= (intYellowX +90)&&intPressedY >= intYellowY&& intPressedY <= (intYellowY + 90) && blnPressed == true && blnYellow == true){
			//System.out.println("Yellow placed");
			//To constantly draw the checker as if the mouse is "holding" it, we must calcualte the distance the mouse is from the top right of the image to accurately draw the image
			intDiffX = intPressedX - intYellowX;
			intDiffY = intPressedY - intYellowY;
			g.drawImage(imgPlayer2, intDraggedX - intDiffX, intDraggedY - intDiffY, null);
			//System.out.println(intDraggedX +","+ intDiffX +","+ intDraggedY +","+ intDiffY);
			blnPlaced = true;
		}

		int intXMin = 390; 
		int intXMax = 500; 
		
		//For loop to draw highlight depending on coordinates of where the player has dragged their piece
		for(int intCount = 0; intCount < 7; intCount++){
			intXMin = intXMin + 110;
			intXMax = intXMax + 110;
			//Highlights column depending if it fits within coordinate range
			if(intDraggedX >= intXMin && intDraggedX <= intXMax && intDraggedY < 100){
				g.drawImage(imgHighlight, intXMin, 0, null);
			}
		}
		//Determine turn order by using if the turn value is an even number (intTurn%2 = 0) and intPlayer == 1, then it is player 1's turn and therefore draw arrow next to their checker to indicate turn
		if(this.intTurn % 2 == 0 && intPlayer == 1){
			g.drawImage(imgArrow, 200 ,220, null);
		//Determine turn order by using if the turn value is an odd number (intTurn%2 = 1) and intPlayer == 2, then it is player 2's turn and therefore draw arrow next to their checker to indicate turn
		}else if(this.intTurn % 2 ==1 && intPlayer == 2){
			g.drawImage(imgArrow, 200 ,120, null);
		}
		
		//Drawing Opponents piece when they move using ssm coordinates
		if(blnDraw == true){
			if(intPlayer == 2){
				g.drawImage(imgPlayer1, intSSMX, intSSMY, null);
			}	
			else if(intPlayer == 1){
				g.drawImage(imgPlayer2, intSSMX, intSSMY, null);
			}
		}
		
		//Drawing lines to split up columns
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
		
	//Method to load in all images based on theme array loaded in on calculations.java
	public void loadTheme(String[] strTheme){
		this.strTheme = strTheme;
		System.out.println("loading new images");
		//Trying to load image using file name from theme array into a variable while catching IOexception 
		try{
			imgPlayer1 = ImageIO.read(new File(strTheme[1]));
		}catch (IOException e){
			System.out.println("cannot load image");
		}
		//Trying to load image using file name from theme array into a variable while catching IOexception 
		try{
			imgPlayer2 = ImageIO.read(new File(strTheme[2]));
		}catch (IOException e){
			System.out.println("cannot load image");
		}
		//Trying to load image using file name from theme array into a variable while catching IOexception 
		try{
			imgBoard= ImageIO.read(new File(strTheme[3]));
		}catch (IOException e){
			System.out.println("cannot load image");
		}
		//Trying to load image using file name from theme array into a variable while catching IOexception 
		try{
			imgBackground = ImageIO.read(new File(strTheme[4]));
		}catch (IOException e){
			System.out.println("cannot load image");
		}
		//Trying to load image while catching IOException 
		try{
			imgArrow = ImageIO.read(new File("arrow.png"));
		}catch (IOException e){
			System.out.println("cannot load image");
		}
		//Trying to load image while catching IOexception 
		try{
			imgHighlight = ImageIO.read(new File("highlight.png"));
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
