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
		try{
			imgBackground = ImageIO.read(new File("resources/helpscreen.png"));
		}catch (IOException e){
			System.out.println("cannot load image");
		}

	}
}
