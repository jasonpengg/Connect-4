import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;


public class helpPanel extends JPanel{
	// Properties 
	BufferedImage imgBackground = null;
	
	boolean blnWin = false; 

	
	public void paintComponent(Graphics g){
			g.drawImage(imgBackground, 0,0, null);
	}
	//Constructor
	public helpPanel(){
		InputStream imageclass = null;
		imageclass = this.getClass().getResourceAsStream("resources/helpscreen.png");
		if(imageclass != null){
			try{
				imgBackground = ImageIO.read(imageclass);
			}catch(IOException e){
				System.out.println("Unable to load image file from jar");
			}
		}
		if(imgBackground == null){
			try{
				imgBackground = ImageIO.read(new File("helppanelpic.png"));
			}catch(IOException e){
				System.out.println("Unable to load image");
			}
		}
	}
}
