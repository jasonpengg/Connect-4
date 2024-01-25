import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class helpPanel extends JPanel{
	// Properties 
	BufferedImage imgBackground = null;

	//Methods
	public void paintComponent(Graphics g){
		g.drawImage(imgBackground, 0,0, null);
	}
	//Constructor
	public helpPanel(){
		//Loading from Jar File
		InputStream imageclass = null;
		imageclass = this.getClass().getResourceAsStream("resources/helpscreen.png");
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
				imgBackground = ImageIO.read(new File("resources/helpscreen.png"));
			}catch(IOException e){
				System.out.println("Unable to load image");
			}
		}
	}
}
