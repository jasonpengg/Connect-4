import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class demoPanel extends JPanel{
	//Properties
	//Moving the piece
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
	boolean blnPressed = false;
	boolean blnPlaced = false;
	int intArrowX = 150;
	int intDefArrowX = 0;
	
	//Image variables 
	BufferedImage imgbackground = null;
	BufferedImage imgPlayer1 = null;
	BufferedImage imgPlayer2 = null;
	BufferedImage imgBackground = null;
	BufferedImage imgBoard = null;
	BufferedImage imgArrow = null;
	BufferedImage imgHighlight = null;
	BufferedImage img1 = null;
	BufferedImage img2 = null;
	BufferedImage img3 = null;
	BufferedImage img4 = null;
	String[] strTheme = {"","","","",""};
	
	//Game variables
	int intBoard[][];
	int intSeconds = 0;
	boolean blnWin = false;
	
	public void paintComponent(Graphics g){
		//Sets Background 
		g.drawImage(imgbackground, 0, 0, null);
		g.drawImage(imgBoard, 500, 100, null);
	
		//Draw red and yellow piece at resting place
		g.drawImage(imgPlayer1, intRedX, intRedY, null);
		g.drawImage(imgPlayer2, intYellowX, intYellowY, null);
		
		//Prints gameboard
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
		if(blnWin == true){
			g.drawImage(imgPlayer1, 840, 110, null);
		}
		//Player1
		//If mouse is pressed within specific coordinates draw the checker where the player dragged the mouse
		if(intPressedX >= (intRedX) && intPressedX <= (intRedX +90)&&intPressedY >= intRedY&& intPressedY <= (intRedY + 90) && blnPressed == true){
			intDiffX = intPressedX - intRedX;
			intDiffY = intPressedY - intRedY;
			g.drawImage(imgPlayer1, intDraggedX - intDiffX, intDraggedY - intDiffY, null);
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
		//Draws moving arrow
		if(blnWin == false){
			if (intArrowX >200){
				intDefArrowX = -2;
			}else if(intArrowX <= 150){
				intDefArrowX = 2;
			}
			intArrowX = intArrowX + intDefArrowX;
			g.drawImage(imgArrow, intArrowX, 220, null);
		}
		//Counts the pieces in a row
		if (blnWin == true){
			if(intSeconds >= 1){
				g.drawImage(img1, 500, 0, null);
			}if(intSeconds >= 2){
				g.drawImage(img2, 610, 0, null);
			}if(intSeconds >= 3){
				g.drawImage(img3, 720, 0, null);
			}if(intSeconds >= 4){
				g.drawImage(img4, 830, 0, null);
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
		//Loading from Jar File
		InputStream imageclass = null;
		imageclass = this.getClass().getResourceAsStream("resources/"+strTheme[1]);
		if(imageclass != null){
			try{
				imgPlayer1 = ImageIO.read(imageclass);
			}catch(IOException e){
				System.out.println("Unable to load image file from jar");
			}
		}
		//Trying to load image using file name from theme array into a variable while catching IOexception 
		if(imageclass==null){
			try{
				imgPlayer1 = ImageIO.read(new File("resources/"+strTheme[1]));
			}catch (IOException e){
				System.out.println("cannot load image1");
			}	
		}
		
		//Loading from Jar File
		imageclass = this.getClass().getResourceAsStream("resources/"+strTheme[2]);
		if(imageclass != null){
			try{
				imgPlayer2 = ImageIO.read(imageclass);
			}catch(IOException e){
				System.out.println("Unable to load image file from jar");
			}
		}
		//Trying to load image using file name from theme array into a variable while catching IOexception 
		if(imageclass==null){
			try{
				imgPlayer2 = ImageIO.read(new File("resources/"+strTheme[2]));
			}catch (IOException e){
				System.out.println("cannot load image2");
			}
		}
		
		//Loading from Jar File
		imageclass = this.getClass().getResourceAsStream("resources/"+strTheme[3]);
		if(imageclass != null){
			try{
				imgBoard = ImageIO.read(imageclass);
			}catch(IOException e){
				System.out.println("Unable to load image file from jar");
			}
		}
		//Trying to load image using file name from theme array into a variable while catching IOexception 
		if(imageclass==null){
			try{
				imgBoard= ImageIO.read(new File("resources/"+strTheme[3]));
			}catch (IOException e){
				System.out.println("cannot load image3");
			}
		}
		
		//Loading from Jar File
		imageclass = this.getClass().getResourceAsStream("resources/"+strTheme[4]);
		if(imageclass != null){
			try{
				imgBackground = ImageIO.read(imageclass);
			}catch(IOException e){
				System.out.println("Unable to load image file from jar");
			}
		}
		//Trying to load image using file name from theme array into a variable while catching IOexception 
		if(imageclass==null){
			try{
				imgBackground = ImageIO.read(new File("resources/"+strTheme[4]));
			}catch (IOException e){
				System.out.println("cannot load image4");
			}
		}
		
		//Loading from Jar File
		imageclass = this.getClass().getResourceAsStream("resources/arrow.png");
		if(imageclass != null){
			try{
				imgArrow = ImageIO.read(imageclass);
			}catch(IOException e){
				System.out.println("Unable to load image file from jar");
			}
		}
		//Trying to load image while catching IOException 
		if(imageclass==null){
			try{
				imgArrow = ImageIO.read(new File("resources/arrow.png"));
			}catch (IOException e){
				System.out.println("cannot load image5");
			}
		}
		
		//Loading from Jar File
		imageclass = this.getClass().getResourceAsStream("resources/highlight.png");
		if(imageclass != null){
			try{
				imgHighlight = ImageIO.read(imageclass);
			}catch(IOException e){
				System.out.println("Unable to load image file from jar");
			}
		}
		//Trying to load image while catching IOexception
		if(imageclass==null){ 
			try{
				imgHighlight = ImageIO.read(new File("resources/highlight.png"));
			}catch (IOException e){
				System.out.println("cannot load image6");
			}
		} 
		
		//Loading from Jar File
		imageclass = this.getClass().getResourceAsStream("resources/one.png");
		if(imageclass != null){
			try{
				img1 = ImageIO.read(imageclass);
			}catch(IOException e){
				System.out.println("Unable to load image file from jar");
			}
		}
		//Trying to load image while catching IOexception 
		if(imageclass==null){
			try{
				img1 = ImageIO.read(new File("resources/one.png"));
			}catch (IOException e){
				System.out.println("cannot load image6");
			}
		}
		
		//Loading from Jar File
		imageclass = this.getClass().getResourceAsStream("resources/two.png");
		if(imageclass != null){
			try{
				img2 = ImageIO.read(imageclass);
			}catch(IOException e){
				System.out.println("Unable to load image file from jar");
			}
		}
		//Trying to load image while catching IOexception 
		if(imageclass==null){
			try{
				img2 = ImageIO.read(new File("resources/two.png"));
			}catch (IOException e){
				System.out.println("cannot load image7");
			}
		}
		
		//Loading from Jar File
		imageclass = this.getClass().getResourceAsStream("resources/three.png");
		if(imageclass != null){
			try{
				img3 = ImageIO.read(imageclass);
			}catch(IOException e){
				System.out.println("Unable to load image file from jar");
			}
		}
		//Trying to load image while catching IOexception 
		if(imageclass==null){
			try{
				img3 = ImageIO.read(new File("resources/three.png"));
			}catch (IOException e){
				System.out.println("cannot load image8");
			}
		} 
		
		//Loading from Jar File
		imageclass = this.getClass().getResourceAsStream("resources/four.png");
		if(imageclass != null){
			try{
				img4 = ImageIO.read(imageclass);
			}catch(IOException e){
				System.out.println("Unable to load image file from jar");
			}
		}
		//Trying to load image while catching IOexception 
		if(imageclass==null){
			try{
				img4 = ImageIO.read(new File("resources/four.png"));
			}catch (IOException e){
				System.out.println("cannot load image9");
			}
		}
		
		//Loading from Jar File
		imageclass = this.getClass().getResourceAsStream("resources/demohelpscreen.png");
		if(imageclass != null){
			try{
				imgbackground = ImageIO.read(imageclass);
			}catch(IOException e){
				System.out.println("Unable to load image file from jar");
			}
		}
		if(imageclass==null){
			try{
				imgbackground = ImageIO.read(new File("resources/demohelpscreen.png"));
			}catch (IOException e){
				System.out.println("cannot load image10");
			}
		}
	}
	//Constructor
	public demoPanel (String[] strTheme, int[][] intBoard){
		this.strTheme = strTheme;
		this.intBoard = intBoard;
		loadTheme(strTheme);
	}
}
