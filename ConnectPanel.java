import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;


public class ConnectPanel extends JPanel{
	/** Checks to see if grid needs to be printed 
	 * @return Set as true 
	 */
	boolean blnPrintGrid = true;
	/**Describes the x-coordinate of the mouse when user presses their mouse
	 * @return Value is inputted by the MouseListner
	 */
	int intPressedX = -0;
	/**Describes the y-coordinate of the mouse when user presses their mouse
	 * @return Value is inputted by the MouseListner
	 */
	int intPressedY = -0; 
	/**Describes the x-coordinate of the mouse when user presses their mouse and drags it 
	 * @return Value is initalized at -1000 but is set as different values inputted by MouseMotionListener
	 */
	int intDraggedX = -1000;
	/**Describes the y-coordinate of the mouse when user presses their mouse and drags it 
	 * @return Value is initalized at -1000 but is set as different values inputted by MouseMotionListener
	 */
	int intDraggedY = -1000;
	/**Describes the x-coordinate of red game piece origin
	 * @return value is constant throughout gameplay but can be manually changed in code to change starting place of piece
	 */
	int intRedX = 390; 
	/**Describes the y-coordinate of red game piece origin
	 * @return value is constant throughout gameplay but can be manually changed in code to change starting place of piece
	 */
	int intRedY = 210;
	/**Describes the x-coordinate of yellow game piece origin
	 * @return value is constant throughout gameplay but can be manually changed in code to change starting place of piece
	 */
	int intYellowX = 390;
	/**Describes the y-coordinate of yellow game piece origin
	 * @return value is constant throughout gameplay but can be manually changed in code to change starting place of piece
	 */
	int intYellowY = 110;
	/**Describes the x-coordinate difference from the mouse and where the image is drawn
	 * @return value is used to draw piece when player is moving it 
	 */
	int intDiffX = -5000;
	/**Describes the y-coordinate difference from the mouse and where the image is drawn
	 * @return value is used to draw piece when player is moving it 
	 */
	int intDiffY = -5000;
	/**Describes if the player pressed on their piece
	 * @return value is used in animation of drawing clicked piece
	 */
	boolean blnPressed = false;
	/**Describes if the red player is allowed to move their piece
	 * @return Value is based on turn calculations
	 */
	boolean blnRed = true;
	/**Describes if the yellow player is allowed to move their piece
	 * @return Value is based on turn calculations
	 */
	boolean blnYellow = true;
	/**Describes if piece was placed successfully on the board
	 * @return Value is based on mouse drag
	 */
	boolean blnPlaced = false;
	
	//SSM variables
	/**Describes the x-coordinate of the opponents piece when being dragged around
	 * @return value is used to draw piece when player is moving it 
	 */
	int intSSMX = 0;
	/**Describes the x-coordinate of the opponents piece when being dragged around
	 * @return value is used to draw piece when player is moving it 
	 */
	int intSSMY = 0;
	/**Describes what player's turn it is
	 * @return value is used to differientiate gameplay calculations
	 */
	int intPlayer = 0;
	/**Describes if panel needs to be drawn
	 */
	boolean blnDraw = false;
	
	//Theme variables 
	/**Holds the image of player ones piece
	 */
	BufferedImage imgPlayer1 = null;
	/**Holds the image of player twos piece
	 */
	BufferedImage imgPlayer2 = null;
	/**Holds the image of background
	 */
	BufferedImage imgBackground = null;
	/**Holds the image of the board
	 */
	BufferedImage imgBoard = null;
	/**Holds the image of arrow
	 */
	BufferedImage imgArrow = null;
	/**Holds the image of highlight
	 */
	BufferedImage imgHighlight = null;
	/**Array to hold theme components
	 */
	String[] strTheme = {"","","","",""};
	
	//Game variables
	
	/**Variable decides the turn count
	 */
	int intTurn = 0;
	/**Board array; updated as the game goes on
	 */
	int intBoard[][];
	
	
	// Methods
	// override how JComponent is painted 
	// Set the Red and Yellow pieces to coordinates 
	
	//"Space Theme"
	//"Standard Theme"
	//"Christmas Theme"
	
	/**Method paints the display of the board, "holes" in the board and all components on the gameplay panel
	 * @return No return value; just draws
	 */
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
		
	
	/**Loads in all images based on theme array loaded in on calculations.java
	 * @return No return values; just draws display
	 */
	public void loadTheme(String[] strTheme){
		this.strTheme = strTheme;
		System.out.println("loading new images");
		//Trying to load image using file name from theme array into a variable while catching IOexception 
		try{
			imgPlayer1 = ImageIO.read(new File("resources/"+strTheme[1]));
		}catch (IOException e){
			System.out.println("cannot load image1");
		}
		//Trying to load image using file name from theme array into a variable while catching IOexception 
		try{
			imgPlayer2 = ImageIO.read(new File("resources/"+strTheme[2]));
		}catch (IOException e){
			System.out.println("cannot load image2");
		}
		//Trying to load image using file name from theme array into a variable while catching IOexception 
		try{
			imgBoard= ImageIO.read(new File("resources/"+strTheme[3]));
		}catch (IOException e){
			System.out.println("cannot load image3");
		}
		//Trying to load image using file name from theme array into a variable while catching IOexception 
		try{
			imgBackground = ImageIO.read(new File("resources/"+strTheme[4]));
		}catch (IOException e){
			System.out.println("cannot load image4");
		}
		//Trying to load image while catching IOException 
		try{
			imgArrow = ImageIO.read(new File("resources/arrow.png"));
		}catch (IOException e){
			System.out.println("cannot load image5");
		}
		//Trying to load image while catching IOexception 
		try{
			imgHighlight = ImageIO.read(new File("resources/highlight.png"));
		}catch (IOException e){
			System.out.println("cannot load image6");
		}
	}
	//Constructor
	/**Constructs the pannel 
	 * @param strTheme - theme array which holds the name of all files needed to draw images on the panel
	 */
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
