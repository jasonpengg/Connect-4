import java.io.*;
public class calculations{
	//Method checks for max height
	public static int position(int intX, int intY){
		int intXMin = 390; 
		int intXMax = 500; 
		for(int intCount = 0; intCount < 7; intCount++){
			intXMin = intXMin + 110;
			intXMax = intXMax + 110;
			if(intX >= intXMin && intX <= intXMax && intY < 100){
				return intCount;
			}
		}
		return -10;
	}
}
