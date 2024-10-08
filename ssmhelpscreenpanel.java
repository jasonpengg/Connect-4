import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class ssmhelpscreenpanel extends JPanel{
	// Properties 
	BufferedImage imgBackground = null;

	// Methods
	// override how JComponent is painted 
	public void paintComponent(Graphics g){
		g.drawImage(imgBackground, 0,0, null);
	}
	//Constructor
	public ssmhelpscreenpanel (){
		//Trying to load from jar file
		InputStream imageclass = null;
		imageclass = this.getClass().getResourceAsStream("resources/ssmhelp.png");
		if(imageclass != null){
			try{
				imgBackground = ImageIO.read(imageclass);
			}catch(IOException e){
				System.out.println("Unable to load image file from jar");
			}
		}
		//Trying to load image from folder
		if(imgBackground == null){
			try{
				imgBackground = ImageIO.read(new File("resources/ssmhelp.png"));
			}catch(IOException e){
				System.out.println("Unable to load image");
			}
		}
	}
}
