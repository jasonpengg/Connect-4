import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class winScreenPanel extends JPanel{
	// Properties 
	BufferedImage imgBackground = null;
	BufferedImage imgBackground1 = null;
	//Checks if someone has won
	boolean blnWin = false; 

	//Methods
	public void paintComponent(Graphics g){
		if(blnWin == true){
			g.drawImage(imgBackground, 0,0, null);
		}
		if(blnWin == false){
			g.drawImage(imgBackground1, 0,0, null);
		}
	}
	//Constructor
	public winScreenPanel(){
		//Loading from jar file
		InputStream imageclass = null;
		imageclass = this.getClass().getResourceAsStream("resources/winscreen.png");
		if(imageclass != null){
			try{
				imgBackground = ImageIO.read(imageclass);
			}catch(IOException e){
				System.out.println("Unable to load image file from jar");
			}
		}
		//Trying to load image from the folder 
		if(imgBackground == null){
			try{
				imgBackground = ImageIO.read(new File("resources/winscreen.png"));
			}catch(IOException e){
				System.out.println("Unable to load image");
			}
		}
		//Loading from Jar file
		imageclass = this.getClass().getResourceAsStream("resources/tie.png");
		if(imageclass != null){
			try{
				imgBackground1 = ImageIO.read(imageclass);
			}catch(IOException e){
				System.out.println("Unable to load image file from jar");
			}
		}
		//Trying to load image from the folder 
		if(imgBackground1 == null){
			try{
				imgBackground1 = ImageIO.read(new File("resources/tie.png"));
			}catch(IOException e){
				System.out.println("Unable to load image");
			}
		}
	}
}
