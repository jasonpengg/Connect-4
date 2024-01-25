import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class themesPanel extends JPanel{
	// Properties 
	BufferedImage imgBackground = null;

	// Methods
	public void paintComponent(Graphics g){
		g.drawImage(imgBackground, 0,0, null);
	}
	//Constructor
	public themesPanel (){
		//Loading from jar file
		InputStream imageclass = null;
		imageclass = this.getClass().getResourceAsStream("resources/themes.png");
		if(imageclass != null){
			try{
				imgBackground = ImageIO.read(imageclass);
			}catch(IOException e){
				System.out.println("Unable to load image file from jar");
			}
		}
		//Loading image from folder
		if(imgBackground == null){
			try{
				imgBackground = ImageIO.read(new File("resources/themes.png"));
			}catch(IOException e){
				System.out.println("Unable to load image");
			}
		}
	}
}
