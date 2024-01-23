import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;


public class winScreenPanel extends JPanel{
	// Properties 
	BufferedImage imgBackground = null;
	BufferedImage imgBackground1 = null;
	
	boolean blnWin = false; 

	
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
		try{
			imgBackground = ImageIO.read(new File("resources/winscreen.png"));
		}catch (IOException e){
			System.out.println("cannot load image");
		}
		try{
			imgBackground1 = ImageIO.read(new File("resources/tie.png"));
		}catch (IOException e){
			System.out.println("cannot load image");
		}
	}
}
