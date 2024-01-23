import java.io.*;
class calculations{
	private int intCurrentPlayerTurn = 0;
	// [Columns] [Rows] 
	// [x][y]
	public int intBoard[][] = new int[7][6];
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
			FileReader fr = new FileReader ("resources/Themes.txt");
			BufferedReader themeReader = new BufferedReader(fr);
			String strInput ="";
			
			//Reads the file lines until it fines the line that has the theme equal to strTheme input
			while (!strInput.equals(strTheme)){
				strInput = themeReader.readLine();
			}
			//For loop that loads themeArray with desired theme from txt file
			for (int intCount =0; intCount < 5; intCount++){
				themeArray[intCount] = strInput;
				strInput = themeReader.readLine();
				//StrInput will contain an extraline or null if its at the end 
			}
				//closing filereader
				themeReader.close();
		//catching exception 
		}catch(IOException e){
			e.printStackTrace();
		}
		//returns the theme array so that display can be drawn accordingly
		return themeArray;
	}
	
	public void printThemeArray(String[] themeArray){
		for (int intCount = 0; intCount < 5; intCount++){
			System.out.println(themeArray[intCount]);
		}
	}
	
	
	//---------------------printing board----------------------------------//
	
	//Printing board array for testing 
	
	public void printBoard(int intBoard[][]){
		for(int intCount = 0; intCount <6; intCount++){
			for(int intCount2 = 0; intCount2 <7; intCount2++){
				System.out.print(intBoard[intCount2][intCount]);
			}
			System.out.print("\n");
		}
	}
	
	//Method checks if position is within range of game board 
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
	//method places piece on board by adjusting board array values accordingly
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
	
	//Method resets game by reinitializing the board and setting player turn to 0
	public void resetGame(){
		initializeboard();
		intCurrentPlayerTurn = 0;
		getPlayer();
	}
	//Method returns the current player turn
	public int getPlayerTurn(){
		return intCurrentPlayerTurn;
	}
	
	//Method returns back value for what player it is based on if the current player turn is divisible by 2 
	//Method also sets the intPiece value in accordance to player so that when it fills array it corresponds to the right player 
	public String getPlayer(){
		if(intCurrentPlayerTurn % 2 == 0){
			//Player 1 (RED)
			intPiece = 1;
			strPlayer = "1";
		}else if(intCurrentPlayerTurn % 2 == 1){
			//Player 2 (Yellow)
			intPiece = 2;
			strPlayer = "2";
		}
		return strPlayer;
	}
	//Returns board array 
	public int[][] getBoard(){
		return intBoard;
	}
	
	//Accessible within only in the program, this method increases the player turn and then calculates whos turn it is by using modulus 
	//Method also sets the intPiece variable to correspond with "piece" value for each player
	private void switchPlayer(){
		intCurrentPlayerTurn++;
		if(intCurrentPlayerTurn % 2 == 0){
			//Player 1 (RED)
			intPiece = 1;
			strPlayer = "1";
		}else if(intCurrentPlayerTurn % 2 == 1){
			//Player 2 (Yellow)
			intPiece = 2;
			strPlayer = "2";
		}
	}
	//Checks array for 4 in a row Horizontal
	//Returns the player number for the player that does have four in a row or returns 0 if there is no 4 horizontally in a row
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
	//Checks if there is a tie by seeing if every part of the array has a non-zero value
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
	//Returns the player number if they have 4 in a row or returns 0 if there is no 4 vertically in a row
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
		//If a player has four in a row return their player number but if no player has 4 in a row return 0 
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
	//Method returns the string of player number dependent on the check values 
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
	//Load Interactive Help Demo 

	public int[][] loadDemoBoard(){
		InputStreamReader irs = new InputStreamReader(System.in); 
		BufferedReader kb = new BufferedReader(irs);
		int[][] Demoboard = new int[7][6];
		try{
			FileReader fr = new FileReader ("resources/Demoboard.txt");
			BufferedReader boardReader = new BufferedReader(fr);
			String strLine ="";
			String[] strSplit;
			for (int intColumn =0; intColumn < 6; intColumn++){
				strLine = boardReader.readLine();
				System.out.println(strLine);
				strSplit = strLine.split(",");
				for (int introw = 0; introw <7; introw++){
					Demoboard[introw][intColumn] = Integer.parseInt(strSplit[introw]);
				}
			}
			boardReader.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		printBoard(Demoboard);
		return Demoboard;
	}
	
	//Contructor
	public calculations(){
	}
}
