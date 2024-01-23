import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;


public class winScreenPanel extends JPanel{
	// Properties 
	BufferedImage imgBackground = null;

	// Methods
	// override how JComponent is painted 
	public void paintComponent(Graphics g){
		g.drawImage(imgBackground, 0,0, null);
	}
	//Constructor
	public winScreenPanel (){
		try{
			imgBackground = ImageIO.read(new File("resources/winscreen.png"));
		}catch (IOException e){
			System.out.println("cannot load image");
		}
	}
}
