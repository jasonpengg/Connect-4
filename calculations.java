import java.io.*;
/** Class calculates logic calculations behind the game as well as holds data of the game
 */
public class calculations{
	/**Turn Count for ongoing game
	 */
	private int intCurrentPlayerTurn = 0;
	/**Array that represents the board data
	*/
	public int intBoard[][] = new int[7][6];
	/**The Row that the piece is being placed in; dictated by the player 
	 */
	public int intRow = 0;
	/**Represents what piece needs to be put down; changes according to player turn
	 */
	private int intPiece = 1;
	/**Represents which players turn it is
	 */
	private String strPlayer = "1";

	/**Method initializes the board/resets it
	 */
	public void initializeboard(){
		for(int intCount = 0; intCount <7; intCount++){
			for(int intCount2 = 0; intCount2 <6; intCount2++){
				intBoard[intCount][intCount2] = 0;
			}
		}
	}
	//FileIO reading from textfile to put theme into an array
	/**Using file input and output to read from themes text file to load theme data into an array
	 * @return strTheme which holds the names of files used to draw the interface for each theme
	 * @param strTheme The theme array that holds the name of files used to draw the themes
	 */
	public String[] getTheme(String strTheme){
		String[] themeArray = new String[5];
		InputStream txtClass = null;
		txtClass = this.getClass().getClassLoader().getResourceAsStream("resources/themes.txt");
		if(txtClass != null){
			try{
				BufferedReader themeReader = new BufferedReader(new InputStreamReader(txtClass));
				String strInput ="";
				//Reads the file lines until it finds the line that has the theme equal to strTheme input
				while (!strInput.equals(strTheme)){
					strInput = themeReader.readLine();
				}
				//For loop that loads themeArray with desired theme from txt file
				for (int intCount =0; intCount < 5; intCount++){
					themeArray[intCount] = strInput;
					strInput = themeReader.readLine();
					//StrInput will contain an extra line or null if its at the end 
				}
				//closing reader
				themeReader.close();
			//Catching exception 
			}catch(IOException e){
				e.printStackTrace();
			}
		}if(txtClass == null){
			try{
				BufferedReader themeReader = new BufferedReader(new FileReader("resources/themes.txt"));
				String strInput ="";
				
				//Reads the file lines until it fines the line that has the theme equal to strTheme input
				while (!strInput.equals(strTheme)){
					strInput = themeReader.readLine();
				}
				//For loop that loads themeArray with desired theme from txt file
				for (int intCount =0; intCount < 5; intCount++){
					themeArray[intCount] = strInput;
					strInput = themeReader.readLine();
					//StrInput will contain an extra line or null if its at the end 
				}
				//closing filereader
				themeReader.close();
			//catching exception 
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		//returns the theme array so that display can be drawn accordingly
		return themeArray;
	}

	/**Prints theme array for testing
	 * @param themeArray Array that holds names of the files used to draw a theme
	 */
		public void printThemeArray(String[] themeArray){
		for (int intCount = 0; intCount < 5; intCount++){
			System.out.println(themeArray[intCount]);
		}
	}
	
	/**Prints game board array for testing
	 * @param intBoard[][] Game board array that holds information regarding how the board is filled up
	 */
	public void printBoard(int intBoard[][]){
		for(int intCount = 0; intCount <6; intCount++){
			for(int intCount2 = 0; intCount2 <7; intCount2++){
				System.out.print(intBoard[intCount2][intCount]);
			}
			System.out.print("\n");
		}
	}

	//Method checks if position is within range of game board 
	/**Checks if the position the player dropped their piece is within the boards coordinates
	 * @return A boolean that indicates if thier piece was within board coordinate bound or not
	 * @param intY Y-coordinate of piece
	 * @param intX X-coordinate of piece
	 */
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
	//Method places piece on board by adjusting board array values accordingly
	/**Method adjusts game board array according to where the piece was placed by user
	 * @return Newly updated board array 
	 */
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
	/**Method reinitializes the board and sets the player turn to 0 to reset the game 
	 */
	public void resetGame(){
		initializeboard();
		intCurrentPlayerTurn = 0;
		getPlayer();
	}
	//Method returns the current player turn
	/**Method access the current player turn
	 * @return Current player turn
	 */
	public int getPlayerTurn(){
		return intCurrentPlayerTurn;
	}
	
	/**Method access the board array 
	 * @return Board array
	 */
	public int[][] getBoard(){
		return intBoard;
	}
	
	//Method returns back value for what player it is based on if the current player turn is divisible by 2 
	//Method also sets the intPiece value in accordance to player so that when it fills array it corresponds to the right player 
	/**Method determines which piece and whos turn it is based on if the current player turn is divisible by 2
	 * @return Username of the players who's turn it is
	 */
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

	//Accessible within only in the program, this method increases the player turn and then calculates whos turn it is by using modulus 
	//Method also sets the intPiece variable to correspond with "piece" value for each player
	/**Method determines which player just complleted their turn and adjusts pieces and data for next players turn
	 */
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
	/**Method undergoes calculations on board array to see if their are four of one players pieces horizontally in a row
	 * @return A string that indicates the number of the player that won, or if no winner, 0.
	 */
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
	/**Method checks if there is a tie by seeing if every index of board array has a non-zero value
	 * @return A boolean that describes if there is a tie or not
	 */
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
	/**Method undergoes calculations on board array to see if their are four of one players pieces vertically in a row
	 * @return A string that indicates the number of the player that won, or if no winner, 0.
	 */
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
	/**Method udnergoes calculations on board array to see if their are four of one players pieces vertically in a row
	 * @return A string that indicates the number of the player that won, or if no winner, 0.
	 */
	public String DiagonalCheckWin() {
		//Check for diagonal win from bottom-left to top-right
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
		for(int intColumn = 3; intColumn < 7; intColumn++) {
			strInRow = "";
			for(int intRow = 0; intRow < 3; intRow++) {
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
	/**Method returns either "player 1" or "player 2" dependent on who won 
	 * @return A string that describes which player won or if there was a tie
	 */
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
	/**Method loads the interactive help demonstration 
	 * @return The demonstration board array
	 */
	public int[][] loadDemoBoard(){
		int[][] Demoboard = new int[7][6];
		InputStream txtClass = null;
		txtClass = this.getClass().getClassLoader().getResourceAsStream("resources/demoboard.txt");
		if(txtClass != null){
			try{
				BufferedReader boardReader = new BufferedReader(new InputStreamReader(txtClass));
				String strLine ="";
				String[] strSplit;
				for(int intColumn =0; intColumn < 6; intColumn++){
					strLine = boardReader.readLine();
					strSplit = strLine.split(",");
					for (int introw = 0; introw <7; introw++){
						Demoboard[introw][intColumn] = Integer.parseInt(strSplit[introw]);
					}
				}
				boardReader.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		if(txtClass == null){
			try{
				BufferedReader boardReader = new BufferedReader(new FileReader("resources/demoboard.txt"));
				String strLine ="";
				String[] strSplit;
				for (int intColumn =0; intColumn < 6; intColumn++){
					strLine = boardReader.readLine();
					strSplit = strLine.split(",");
					for (int introw = 0; introw <7; introw++){
						Demoboard[introw][intColumn] = Integer.parseInt(strSplit[introw]);
					}
				}
				boardReader.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		return Demoboard;
	}
	
	//Constructor
	/**Constructs the calculations class object 
	 */
	public calculations(){
	}
}
