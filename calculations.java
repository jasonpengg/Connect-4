import java.io.*;
class calculations{
	private int intCurrentPlayer = 0;
	// [Columns] [Rows] 
	// [x][y]
	private int intBoard[][] = new int[7][6];
	public int intRow = 0;
	private int intPiece = 1;
	private String strPlayer = "1";
	
	public void initializeboard(){
		for(int intCount = 0; intCount <7; intCount++){
			for(int intCount2 = 0; intCount2 <6; intCount2++){
				intBoard[intCount][intCount2] = 0;
			}
		}
	}
	//FileIO reading from textfile to put theme into an array
	public String[] getTheme(String strTheme){
		InputStreamReader irs = new InputStreamReader(System.in); 
		BufferedReader kb = new BufferedReader(irs);
		String[] themeArray = new String[5];
		try{
			FileReader fr = new FileReader ("Themes.txt");
			BufferedReader themeReader = new BufferedReader(fr);
			String strInput ="";
			
			while (!strInput.equals(strTheme)){
				strInput = themeReader.readLine();
			}
			for (int intCount =0; intCount < 5; intCount++){
				themeArray[intCount] = strInput;
				strInput = themeReader.readLine();
				//StrInput will contain an extraline or null if its at the end 
			}
				themeReader.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		return themeArray;
	}
	public void printThemeArray(String[] themeArray){
		for (int intCount = 0; intCount < 5; intCount++){
			System.out.println(themeArray[intCount]);
		}
	}
	
	
	//---------------------printing board----------------------------------//
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
			strPlayer = "1";
		}else if(intCurrentPlayer % 2 == 1){
			//Player 2 (Yellow)
			intPiece = 2;
			strPlayer = "2";
		}
	}
	//Checks array for 4 in a row Horizontal
	public String HorizontalCheckWin(){
		// intCount2 = horizontal array 
		String strRow ="    ";
		int intLength = 0;
		String strCheck ="";
		for(int intRow = 0; intRow <6; intRow++){
			strCheck ="";
			strRow = "    ";
			for(int intColumn = 0; intColumn <7; intColumn++){
				strRow = strRow + intBoard[intColumn][intRow];
				intLength = strRow.length();
				for(int intCount = 0; intCount <= intLength-4; intCount++){
					strCheck = strRow.substring(intCount, intCount + 4);
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
	public boolean CheckTie(){
		String strCheck = "";
		for(int intRow = 0; intRow <6; intRow++){
			strCheck ="";
			for(int intColumn = 0; intColumn <7; intColumn++){
				if(intBoard[intColumn][intRow] == 0){
					return false;
				}
			}
		}
		return true;
	}
	
	//Checks array for 4 in a row Vertical
	public String VerticalCheckWin(){
		// intCount2 = horizontal array 
		String strRow ="    ";
		int intLength = 0;
		String strCheck ="";
		for(int intColumn = 0; intColumn <7; intColumn++){
			strCheck ="";
			strRow = "    ";
			for(int intRow = 0; intRow <6; intRow++){
				strRow = strRow + intBoard[intColumn][intRow];
				intLength = strRow.length();
				//System.out.println(strRow);
				for(int intCount = 0; intCount <= intLength-4; intCount++){
					strCheck = strRow.substring(intCount, intCount+ 4);
				//	System.out.println(strCheck);
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
	public String DiagonalCheckWin() {
		// Check for diagonal win from bottom-left to top-right
		String strInRow = "    ";
		for (int intColumn = 0; intColumn < 4; intColumn++) {
			strInRow = "";
		for (int intRow = 0; intRow < 3; intRow++) {
				strInRow = "" + intBoard[intColumn][intRow]+intBoard[intColumn+1][intRow+1]+intBoard[intColumn+2][intRow+2]+intBoard[intColumn+3][intRow+3];
				if (strInRow.equals("1111")){
					return "1";
				}else if (strInRow.equals("2222")){
					return "2";
				}
			}
		}

		//Check for diagonal win from bottom-right to top-left
		for (int intColumn = 3; intColumn < 7; intColumn++) {
			strInRow = "";
			for (int intRow = 0; intRow < 3; intRow++) {
				strInRow = "";
				strInRow = "" + intBoard[intColumn][intRow]+intBoard[intColumn-1][intRow+1]+intBoard[intColumn-2][intRow+2]+intBoard[intColumn-3][intRow+3];
			if (strInRow.equals("1111")){
					return "1";
				}else if (strInRow.equals("2222")){
					return "2";
				}
			}
		}
		return "0"; // No diagonal win found
	}
	public String checkResult(){
		if(DiagonalCheckWin().equals("1")){
			return "Player 1";
		}else if(DiagonalCheckWin().equals("2")){
			return "Player 2";
		}else if(HorizontalCheckWin().equals("1")){
			return "Player 1";
		}else if(HorizontalCheckWin().equals("2")){
			return "Player 2";
		}else if(VerticalCheckWin().equals("1")){
			return "Player 1";
		}else if(VerticalCheckWin().equals("2")){
			return "Player 2";
		}else if(CheckTie() == true){
			return "tie";
		}
		return "0";
	}
	
	//Contructor
	public calculations(){
	}
}
