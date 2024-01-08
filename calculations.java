import java.io.*;
class calculations{
	private int intCurrentPlayer = 0;
	// [Columns] [Rows] 
	// [x][y]
	private int intBoard[][] = new int[7][6];
	private int intRow = 0;
	private int intPiece = 1;
	private String strPlayer = "RED";
	
	public void initializeboard(){
		for(int intCount = 0; intCount <7; intCount++){
			for(int intCount2 = 0; intCount2 <6; intCount2++){
				intBoard[intCount][intCount2] = 0;
			}
		}
	}
	public void printBoard(){
		for(int intCount = 0; intCount <6; intCount++){
			for(int intCount2 = 0; intCount2 <7; intCount2++){
				System.out.print(intBoard[intCount2][intCount]);
			}
			System.out.print("\n");
		}
	}
	//Method checks for position
	public boolean position(int intX, int intY){
		int intXMin = 390; 
		int intXMax = 500; 
		for(int intCount = 0; intCount < 7; intCount++){
			intXMin = intXMin + 110;
			intXMax = intXMax + 110;
			if(intX >= intXMin && intX <= intXMax && intY < 100){
				intRow = intCount;
				return true;
			}
		}
		return false;
	}
	//places piece on board 
	public int[][] place(){
		for(int intCount =5; intCount >= 0; intCount--){
			if(intBoard[intRow][intCount] == 0){
				intBoard[intRow][intCount] = intPiece;
				switchPlayer();
				break;
			}
		}

		return intBoard;
	}
	
	public int getPlayerTurn(){
		return intCurrentPlayer;
	}
	public String getPlayer(){
		return strPlayer;
	}
	public int[][] getBoard(){
		return intBoard;
	}
	
	private void switchPlayer(){
		intCurrentPlayer++;
		if(intCurrentPlayer % 2 == 0){
			//Player 1 (RED)
			intPiece = 1;
			strPlayer = "RED";
		}else if(intCurrentPlayer % 2 == 1){
			//Player 2 (Yellow)
			intPiece = 2;
			strPlayer = "YELLOW";
		}
	}
	//Checks array for 4 in a row Horizontal
	public String HorizontalCheckWin(){
		// intCount2 = horizontal array 
		String strRow ="    ";
		int intLength = 0;
		String strCheck ="";
		for(int intCount = 0; intCount <6; intCount++){
			strCheck ="";
			strRow = "    ";
			for(int intCount2 = 0; intCount2 <7; intCount2++){
				strRow = strRow + intBoard[intCount2][intCount];
				intLength = strRow.length();
				for(int intCount3 = 0; intCount3 < intLength-4; intCount3++){
					strCheck = strRow.substring(intCount3, intCount3 + 4);
					if (strCheck.equals("1111")){
						return "1";
					}else if(strCheck.equals("2222")){
						return "2";
					}
				}
			}
			System.out.print("\n");
		}
		
		return "0";
	}
	//Checks array for 4 in a row Vertical
	public String VerticalCheckWin(){
		// intCount2 = horizontal array 
		String strRow ="    ";
		int intLength = 0;
		String strCheck ="";
		for(int intCount = 0; intCount <7; intCount++){
			strCheck ="";
			strRow = "    ";
			for(int intCount2 = 0; intCount2 <6; intCount2++){
				strRow = strRow + intBoard[intCount][intCount2];
				intLength = strRow.length();
				for(int intCount3 = 0; intCount3 < intLength-4; intCount3++){
					strCheck = strRow.substring(intCount3, intCount3 + 4);
					if (strCheck.equals("1111")){
						return "1";
					}else if(strCheck.equals("2222")){
						return "2";
					}
				}
			}
			
		}
		return "0";
	}
	//Checks for Diagonal 
	
	//Contructor
	public calculations(){
	}
}
