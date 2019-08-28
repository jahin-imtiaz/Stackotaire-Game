/**
 * The <code>Stackotaire</code> class provides a menu that allows the user interact
 * with the game UI. This will be a simple command line like UI where the player 
 * specifies the moves to play.
 * @author Jahin Imtiaz
 * 		<dt><b>email:</b><dd>  jahin.imtiaz@stonybrook.edu
 * 		<dt><b>Stony Brook ID:</b><dd>  111214457
 *		<dt><b>Rec Section:</b><dd> 01
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Stackotaire {
	//create An array of stacks for tableau piles and foundation piles
	//create individual stacks for stock or deck, waste and main stack
	public static CardStack[] tableauArray;
	public static CardStack[] foundationArray;
	public static CardStack stockStack;
	public static CardStack wasteStack;
	public static CardStack mainStack;		//holds all 52 cards at the start
	public static ArrayList<Card> deckList; //a list to hold all 52 cards
	public static int helpCount = 0;		//count how many times "help" was used
	
	public static void main(String[] args) {
		//declare variables to get user input
		String input, argOne, argTwo,argThree;
		Scanner userInput = new Scanner(System.in);
		
		//initialize the game and display the board
		initializeGame();
		displayGameBoard();
		System.out.println(" ");
		
		//do this until we get 'Quit' command
		do{ 
			
			System.out.println(" ");
			System.out.println("Need Help? Write 'Help'. :)");
			System.out.println("Wants to know the Commands to play this game? Write 'Commands'. :)");
			System.out.print("Enter a command: ");
			input = userInput.next();
			System.out.println(" ");
		try{
			//draw a card if the command is draw
			if(input.equalsIgnoreCase("draw")){
				//check if the stock is empty or not
				//if empty, move all the cards from the waste stack
				//to the stock stack and then draw the card
				if(stockStack.isEmpty()){
					while(!wasteStack.isEmpty()){
						stockStack.push(wasteStack.pop());
					}
				}
				//move the top card faced up to the waste stack 
				if(!stockStack.isEmpty()){
					stockStack.peek().setFaceUp(true);
					wasteStack.push(stockStack.pop());
				}
				
				//display the game board
				System.out.println(" ");
				displayGameBoard();
			}
			// move a card if the command is move
			else if (input.equalsIgnoreCase("move")){
				//get the two arguments
				argOne = userInput.next();
				argTwo = userInput.next();
				
				//if Argument one is W1, do this
				if(argOne.equalsIgnoreCase("W1")){
					//if the waste stack is not empty, move a card from stack
					if (!wasteStack.isEmpty()){
						//if the argument two is T, do this
						if(Character.toUpperCase(argTwo.charAt(0)) == 'T'){
							
							if(Character.getNumericValue(argOne.charAt(1)) != 1
							  ||Character.getNumericValue(argTwo.charAt(1)) <1 
							  || Character.getNumericValue(argTwo.charAt(1)) >7){
								
								throw new IllegalPositionException("The position"
										+ " of the tables is not valid.");
							}
							
							//get the given tableau number  
						    int number=Character.getNumericValue(argTwo.charAt(1));
							//if the tableau pile is empty, do this
							if(tableauArray[number].isEmpty()){
								//if the top card in wasteStack is King, move it 
								if(wasteStack.peek().getValue() == 13){
									tableauArray[number].push(wasteStack.pop());
								}
								//otherwise print error message
								else System.out.println("Move is not allowed");
							}
							//move the card if their color is different and the
							//waste stacks cards value is less than the tableau
							else if(wasteStack.peek().getValue() == tableauArray
							  [number].peek().getValue()-1 && wasteStack.peek()
							  .isRed() != tableauArray[number].peek().isRed() ){
								
								tableauArray[number].push(wasteStack.pop());
								
							}
							//otherwise print an error message
							else System.out.println("Move is not allowed");
						}
						
						//if the argument two is F, do this
						else if(Character.toUpperCase(argTwo.charAt(0)) == 'F'){
							
							if(Character.getNumericValue(argOne.charAt(1)) != 0 
							  || Character.getNumericValue(argTwo.charAt(1)) <1
							  || Character.getNumericValue(argTwo.charAt(1)) >4){
								
								throw new IllegalPositionException("The position of the tables is not valid.");
							
							}
							
							//get the given tableau number  
							int number=Character.getNumericValue(argTwo.charAt(1));
							//if the foundation pile is empty, do this
							if(foundationArray[number].isEmpty()){
								//if the top card in wasteStack is Ace, move it 
								if(wasteStack.peek().getValue() == 1){
									foundationArray[number].push(wasteStack.pop());
								}
								//otherwise print error message
								else System.out.println("Move is not allowed");
							}
							//move if they have the same suit value and wastes
							//top cards value is 1 greater than the foundations  
							else if(wasteStack.peek().getValue() == foundationArray
							  [number].peek().getValue()+1 && wasteStack.peek()
							  .getSuit() == foundationArray[number].peek()
							  .getSuit() ){
								
								foundationArray[number].push(wasteStack.pop());
							
							}
							//otherwise print an error message
							else System.out.println("Move is not allowed");
						}
						//otherwise print error message
						else System.out.println("Move is not allowed");
					}
					else System.out.println("Move is not allowed");
					//if the waste stack is empty , print error message

				}
				
				//if argument one is F(1-4), do this
				else if(Character.toUpperCase(argOne.charAt(0)) == 'F'){
					
					//get the given foundation number and tableau number  
					int numberF = Character.getNumericValue(argOne.charAt(1));
					int numberT = Character.getNumericValue(argTwo.charAt(1));
					//check the input values
					if(numberF < 1 || numberF >4 || numberT <1 || numberT > 7){
						throw new IllegalPositionException("The position of the"
								+ " tables is not valid.");
					}
					
					if(Character.toUpperCase(argTwo.charAt(0)) == 'T'){
						//check if the foundation pile is empty or not
						if(foundationArray[numberF].isEmpty()){
							System.out.println("Move is not allowed");
						}
						else {
							//if the tableau pile is empty, do this
							if(tableauArray[numberT].isEmpty()){
								//if the top card in wasteStack is King, move it 
								if(foundationArray[numberF].peek().getValue() 
								  == 13){
									
									tableauArray[numberT].push(foundationArray
									  [numberF].pop());
								}
								//otherwise print error message
								else System.out.println("Move is not allowed");
							}
							//move the card if their color is different and the
							//foundation cards value is less than the tableau
							else if(foundationArray[numberF].peek().getValue() 
							  == tableauArray[numberT].peek().getValue()-1 && 
							  foundationArray[numberF].peek().isRed() != tableauArray
							  [numberT].peek().isRed() ){
								
								tableauArray[numberT].push(foundationArray
								  [numberF].pop());
							}
							//otherwise print an error message
							else System.out.println("Move is not allowed");
						}
					}
					else System.out.println("Move is not allowed");

				}
				
				//if argument one is T(1-7), do this
				else if(Character.toUpperCase(argOne.charAt(0)) == 'T'){
					//get the given foundation number and tableau number  
					int number1 = Character.getNumericValue(argOne.charAt(1));
					int number2 = Character.getNumericValue(argTwo.charAt(1));
					
					//if argument two is T(1-7), do this
					if(Character.toUpperCase(argTwo.charAt(0)) == 'T'){
						//check the input values
						if(number1 < 1 || number1 >7 || number2 <1 || number2 > 7){
							throw new IllegalPositionException("The position of "
							   + "the tables is not valid.");
						}
						
						//check if the tableau(FROM) piles is empty or not
						if(tableauArray[number1].isEmpty()){
							System.out.println("Move is not allowed");
						}
						else {
							//if the tableau(TO) pile is empty, do this
							if(tableauArray[number2].isEmpty()){
								//if the top card in foundationstack is King, move it 
								if(tableauArray[number1].peek().getValue() == 13){
									tableauArray[number2].push(tableauArray
									  [number1].pop());
								}
								//otherwise print error message
								else System.out.println("Move is not allowed");
							}
							//move the card if their color is different and the
							// From cards value is less than the To cards value
							else if(tableauArray[number1].peek().getValue() == 
							  tableauArray[number2].peek().getValue()-1 && 
							  tableauArray[number1].peek().isRed() != tableauArray
							  [number2].peek().isRed() ){
								
								tableauArray[number2].push(tableauArray[number1].pop());
							}
							//otherwise print an error message
							else System.out.println("Move is not allowed");
						}
					}
					//if the argument two is F(1-4), do this
					else if (Character.toUpperCase(argTwo.charAt(0)) == 'F'){
						
						if(number1 < 1 || number1 >7 || number2 <1 || number2 > 4){
							throw new IllegalPositionException("The position of "
									+ "the tables is not valid.");
						}
						
						//check if the tableau(FROM) piles is empty or not
						if(tableauArray[number1].isEmpty()){
							System.out.println("Move is not allowed");
						}
						//if the foundation pile is empty, do this
						else if(foundationArray[number2].isEmpty()){
							//if the top card in tableau is Ace, move it 
							if(tableauArray[number1].peek().getValue() == 1){
								foundationArray[number2].push(tableauArray[number1]
								 .pop());
							}
							//otherwise print error message
							else System.out.println("Move is not allowed");
						}
						//move if they have the same suit value and tableaus
						//top cards value is 1 greater than the foundations  
						else if(tableauArray[number1].peek().getValue() == 
						  foundationArray[number2].peek().getValue()+1 && 
						  tableauArray[number1].peek().getSuit() == 
						  foundationArray[number2].peek().getSuit() ){
							
							foundationArray[number2].push(tableauArray[number1].pop());
						}
						//otherwise print an error message
						else System.out.println("Move is not allowed");
					}
					else System.out.println("Move is not allowed");

				}
				//the command was in wrong format
				else System.out.println("Move is not allowed");
				//check the board if its a winning board or not
				if(checkWinningBoard()){
					//if its a winning board, print message to start again
					System.out.println("Congratulations !! You Win !");
					System.out.println(" ");
					System.out.println("You took "+helpCount+" helps to Win this game.");
					System.out.println(" ");
					System.out.println("Let's Play Again.");
					initializeGame();
					System.out.println(" ");
					displayGameBoard();
				}
				else {
					//if its not a winning board, display the current board
					System.out.println(" ");
					displayGameBoard();
				}
			}
			//if the command is MOVEN do this.
			else if(input.equalsIgnoreCase("MoveN")){
				
				//get the three arguments
				argOne = userInput.next();
				argTwo = userInput.next();
				argThree = userInput.next();
				
				//get the given tableau numbers and N 
				int numberFrom = Character.getNumericValue(argOne.charAt(1));
				int numberTo = Character.getNumericValue(argTwo.charAt(1));
				int N = Character.getNumericValue(argThree.charAt(0));
				
				if(numberFrom < 1 || numberFrom >7 || numberTo <1 || numberTo > 7){
					throw new IllegalPositionException("The position of the "
						+ "tables is not valid.");
				}
				
				//check if the argument one is tableau pile or not
				if (Character.toUpperCase(argOne.charAt(0)) == 'T'){
					//if argument two is T(1-7), do this
					if(Character.toUpperCase(argTwo.charAt(0)) == 'T'){
						//check if the tableau(FROM) piles is empty or not
						//check if the (FROM) pile have enough face up cards
						if(tableauArray[numberFrom].isEmpty() || !(tableauArray
							[numberFrom].checkNopenCards(N))){
							
							System.out.println("Move is not allowed");
						}
						else{
							//open a temp stack to hold the n cards
							CardStack tempStack = new CardStack();
							//push n cards in the temp stack from the tableau
							int i = 0;
							while(i<N){ 
								tempStack.push(tableauArray[numberFrom].pop());
								i++;
							}
							//if the tableau(TO) pile is empty, do this
							if(tableauArray[numberTo].isEmpty()){
								//if the top card in tempStack is King, move it 
								if(tempStack.peek().getValue() == 13){
									while(!tempStack.isEmpty()){
										tableauArray[numberTo].push(tempStack.pop());
									}
								}
								//otherwise print error message
								else {
									while(!tempStack.isEmpty()){
										tableauArray[numberFrom].push(tempStack.pop());
									}
									System.out.println("Move is not allowed");
								}
							}
							//move the card if temp stacks top card and from
							//stacks top cards color is different and the
							//tempstacks value is less than the To cards value
							else if(tempStack.peek().getValue() == tableauArray
							  [numberTo].peek().getValue()-1 && tempStack.peek()
							  .isRed() != tableauArray[numberTo].peek().isRed() ){
								
								while(!tempStack.isEmpty()){
									tableauArray[numberTo].push(tempStack.pop());
								}
							}
							//otherwise print an error message
							else{
								while(!tempStack.isEmpty()){
									tableauArray[numberFrom].push(tempStack.pop());
								}
								System.out.println("Move is not allowed");
							}
						}
					}
					else System.out.println("Move is not allowed");
				}
				else System.out.println("Move is not allowed");
				//check the board if its a winning board or not
				if(checkWinningBoard()){
					//if its a winning board, print message to start again
					System.out.println("Congratulations !! You Win !");
					System.out.println(" ");
					System.out.println("You took "+helpCount+" helps to Win "
							+ "this game.");
					System.out.println(" ");
					System.out.println("Let's Play Again.");
					initializeGame();
					System.out.println(" ");
					displayGameBoard();
				}
				else {
					//if its not a winning board, display the current board
					System.out.println(" ");
					displayGameBoard();
				}
			}
			//restart the game if the input is 'restart'
			else if(input.equalsIgnoreCase("restart")){
				//ask if he wants to quit or no
				System.out.print("Do you want to start a new game? (Y/N): ");
				input = userInput.next();
				//if yes, initialize a new game
				if(input.equalsIgnoreCase("y")){
					System.out.println(" ");
					System.out.println("You Lost. Starting a new game. GOOD LUCK!");
					initializeGame();
					System.out.println(" ");
				}
				//otherwise display the current board
				displayGameBoard();
			}
			//if the user enter 'quit', exit the game
			else if(input.equalsIgnoreCase("quit")){
				System.out.print("Do you want to quit? (Y/N): ");
				input = userInput.next();
				if(input.equalsIgnoreCase("y")){
					System.out.println("You Lost. Better Luck Next Time !");
					System.exit(0);
				}
				else displayGameBoard();
			}
			//if User needs help, perform this code
			//This command search through all stacks and and moves a card if
			//possible. this gives priority to the foundation piles, then waste 
			//stacks and then tableaus. which means it will always look to bring
			//a card to the foundation piles first.if no move is possible, it 
			//draws a card for the user if available.
			else if(input.equalsIgnoreCase("HELP")){
				//increase the helpCounter
				helpCount++;
				//if any cards were possible to move to foundation print this
				if(helpFoundation()){
					System.out.print(" ");
				}
				//otherwise if any cards were possible to move to tableau
				//from waste print this
				else if(helpWasteToTableau()){
					System.out.print(" ");
				}
				//otherwise if any cards were possible to move from another
				//tableau, print this
				else if(helpTableauToTableau()){
					System.out.print(" ");
				}
				//otherwise draw a card 
				else{
					//check if the stock is empty or not
					//if empty, move all the cards from the waste stack
					//to the stock stack and then draw the card
					if(stockStack.isEmpty()){
						while(!wasteStack.isEmpty()){
							stockStack.push(wasteStack.pop());
						}
					}
					//move the top card faced up to the waste stack 
					stockStack.peek().setFaceUp(true);
					wasteStack.push(stockStack.pop());
					
					//display the game board
					System.out.println(" ");
					displayGameBoard();
					System.out.println(" ");
					System.out.println("A Card Has been drawn from the stock. ");
				}
				//check the board if its a winning board or not
				if(checkWinningBoard()){
					//if its a winning board, print message to start again
					System.out.println("Congratulations !! You Win !");
					System.out.println(" ");
					System.out.println("You took "+helpCount+" helps to Win this game.");
					System.out.println(" ");
					System.out.println("Let's Play Again.");
					initializeGame();
					System.out.println(" ");
					displayGameBoard();
				}

			}
			else if(input.equalsIgnoreCase("Commands")){
				System.out.println("'Draw' : This command removes the top card from the stock"
						+ " pile and places it face up in the waste pile.\n" );
				System.out.println("'move (T/F/W)# (T/F/W)#': This command move one"
						+ " card from one pile to another pile. First argument\n "
						+ "		after the word 'move' is for the pile where you want "
						+ "to move the card from. and the 2nd argument is for the\n"
						+ "		pile where you want to move the card. i.e 'move W1 T2'"
						+ " - this command move a card from the waste pile #1 to\n"
						+ "		tableau pile #2.\n");
				System.out.println("'moveN (T/F/W)# (T/F/W)# #': This command move N"
						+ " cards from one pile to another pile. First argument\n "
						+ "		after the word 'moveN' is for the pile where you want "
						+ "to move the cards from. and the 2nd argument is for the\n"
						+ "		pile where you want to move the cards. and the 3rd "
						+ "argument is the nnumber of cards you want to move. \n"
						+ "		i.e 'move T6 T2 5'- this command move 5 cards from the tableau pile #6 to\n"
						+ "		tableau pile #2.\n");
				System.out.println("'Restart': This command restards the game and open a new board \n");
				System.out.println("'Quit' : This command terminates the program.\n");
				System.out.println("'Help' : this command looks for a possible move and executes it on behalf of the user.\n");
				
			}
		}
		catch(IllegalArgumentException e){
			System.out.println(e);
		}
		catch(Exception e){
			System.out.println(e);
		}
		}
		while(!input.equalsIgnoreCase("Quit"));
		
	}
	/**
	 * this method initializes the board, creates cards, shuffles them and
	 * distributes the cards into proper stacks
	 * <dt>Post Condition:<dd>
	 * 		all cards must be shuffled and distributed properly
	 */
	public static void initializeGame(){
		//create an array of CardStack to hold the tableau stack cards
				tableauArray = new CardStack[8];
				for (int i=1; i<tableauArray.length;i++){
					tableauArray[i] = new CardStack('t');
				}
				
				//create an array of CardStack to hold the foundation stack cards
				foundationArray = new CardStack[5];
				for (int i=1; i<foundationArray.length;i++){
					foundationArray[i] = new CardStack('f');
				}
				
				//create stacks for stock and waste piles
				stockStack = new CardStack('s');
				wasteStack = new CardStack('w');
				
				//create a main stack to hold all 52 cards
				mainStack = new CardStack();
				
				for (int i=1; i<=13; i++){
					for(int j=1; j<=4; j++){
						mainStack.push(new Card(i,j,false));
						
					}
				}
				
				//creeate an array list of cards to hold the shuffled cards.
				deckList = new ArrayList<Card>();
				while(!mainStack.isEmpty()){
					deckList.add(mainStack.pop());
				}
				
				//shuffle the cards
				Collections.shuffle(deckList);
				
				//insert  first 28 cards in the tableauArray stacks
				int k=0;
				for (int i=1; i<8; i++){
					for(int j=1;j<=i;j++){
						tableauArray[i].push(deckList.get(k++));
					}
				}
				
				//insert the next 24 cards in the stock stacks
				while(k < deckList.size()){
					stockStack.push(deckList.get(k));
					k++;
				}
				
	}
	/**
	 * This method produces an image of the game board. 
	 */
	public static void displayGameBoard(){
		//print the foundation stack
		int i=1;
		while(i<5){
			if(foundationArray[i].isEmpty()){
				System.out.printf("%-5s","[F"+i+"]");
			}
			else foundationArray[i].printStack('f');
			i++;
		}
		//print the waste stack
		System.out.printf("%-3s%4s "," ","W1");
		wasteStack.printStack('w');
		
		System.out.printf("%-3s"," ");
		
		//pint the stock stack
		stockStack.printStack('s');
		System.out.printf("%-5d",stockStack.size());
		
		System.out.println(" ");
		System.out.println(" ");
		
		//print the tableau stack
		int j =7;
		while(j>=1){
			System.out.print("T"+j+" ");
			tableauArray[j].printStack('t');
			System.out.println(" ");
			j--;
		}
	}
	/**
	 * Checks if all the cards in the deck is faced up or not. if all cards are
	 * faced up, its a winning board
	 * @return
	 * 		returns true, if all cards are faced up
	 */
	public static boolean checkWinningBoard(){
		int i=0;
		while(i<52){
			if(deckList.get(i).isFaceUp == false)
				return false;
			i++;
		}
		return true;
	}
	/**
	* this method search through all the tableau piles and waste stack to find
	* a suitable card to move to the foundation array.
	* * @return
	 * 		returns true if any card was possible to move
	*/
	public static boolean helpFoundation(){
		//go through all FoundationStack
		//check if any card can be brought or not from tableau or waste
		for(int i=1; i<5; i++){
			//first priority is my waste stack
			//if waste stack is not empty, check if any card can be
			//brought or not. otherwise look into tableau piles
			if(!wasteStack.isEmpty()){
				//if foundation stack is empty, only an ACE card can 
				//be brought from the stack
				if(foundationArray[i].isEmpty() && wasteStack.peek().getValue() == 1){
					foundationArray[i].push(wasteStack.pop());
		
					displayGameBoard();
					System.out.println(" ");
					System.out.println("Moved from W1"+" to F"+i+" .");
					System.out.println(" ");
					return true;
				}
				//if foundation card is not empty, any card that is same
				//suit and one value higher than the top, can be braught
				else if(!foundationArray[i].isEmpty() && wasteStack.peek()
				  .getValue() == foundationArray[i].peek().getValue()+1 && 
				  wasteStack.peek().getSuit() == foundationArray[i].peek()
				  .getSuit() ){
					
					foundationArray[i].push(wasteStack.pop());
					
					displayGameBoard();
					System.out.println(" ");
					System.out.println("Moved from W1"+" to F"+i+" .");
					System.out.println(" ");
					return true;
				}
				//if no cards could be brought from waste stacks,
				//search into tableau piles
				else{
					//go through all tableau stacks
					for(int j=1; j<8;j++){
						//if foundation array is empty, only an ace can
						//be brought from the tableaus
						if(foundationArray[i].isEmpty()){
							//search only when the tableau isn't empty
							if(!tableauArray[j].isEmpty()){
								//look for Ace cards
								if(tableauArray[j].peek().getValue()== 1){
									foundationArray[i].push(tableauArray[j].pop());
									
									displayGameBoard();
									System.out.println(" ");
									System.out.println("Moved from T"+j+" to"
											+ " F"+i+" .");
									System.out.println(" ");
									return true;
								}
							}
						}
						//if foundation array is not empty and if tableau array
						//is not empty, bring any card that is one higher and same suit
						else if(!tableauArray[j].isEmpty()){
							
							if(tableauArray[j].peek().getValue() == foundationArray
							  [i].peek().getValue()+1 && tableauArray[j].peek()
							  .getSuit() == foundationArray[i].peek().getSuit() ){
								
								foundationArray[i].push(tableauArray[j].pop());
								
								displayGameBoard();
								System.out.println(" ");
								System.out.println("Moved from T"+j+" to F"+i+" .");
								System.out.println(" ");
								return true;
							}
						}
					}
				}
			}
			//only if waste stack was empty, loop into tableau piles first.
			else{
				//go through all tableau stacks
				for(int j=1; j<8;j++){
					//if foundation array is empty, only an ace can
					//be brought
					if(foundationArray[i].isEmpty()){
						if(!tableauArray[j].isEmpty()){
							if(tableauArray[j].peek().getValue()== 1){
								foundationArray[i].push(tableauArray[j].pop());
								
								displayGameBoard();
								System.out.println(" ");
								System.out.println("Moved from T"+j+" to F"+i+" .");
								System.out.println(" ");
								return true;
							}
						}
					}
					//if foundation and tableau array are both not empty
					//bring any card that is one higher and same suit
					else if(!tableauArray[j].isEmpty()){
					
						if(tableauArray[j].peek().getValue() == foundationArray[i]
						  .peek().getValue()+1 && tableauArray[j].peek().getSuit()
						  == foundationArray[i].peek().getSuit() ){
							
							foundationArray[i].push(tableauArray[j].pop());
							
							displayGameBoard();
							System.out.println(" ");
							System.out.println("Moved from T"+j+" to F"+i+" .");
							System.out.println(" ");
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * this method tries to bring any valid card from waste pile to tableau 
	 * piles.
	 * @return
	 * 		returns true if any card was possible to move
	 */
	public static boolean helpWasteToTableau(){
		//goes through all tableau piles to see if any pile available to receive
		//any card from the stack
		for(int i=1;i<8;i++){
			//if the current tableau pile is empty, only bring King
			if(tableauArray[i].isEmpty() && !wasteStack.isEmpty() ){
				if(wasteStack.peek().getValue()== 13){
					tableauArray[i].push(wasteStack.pop());

					displayGameBoard();
					System.out.println(" ");
					System.out.println("Moved from W1 to T"+i+" .");
					System.out.println(" ");
					return true;
				}
			}
			//if its not empty, bring any valid card
			else if(!tableauArray[i].isEmpty() && !wasteStack.isEmpty()){
				if(tableauArray[i].peek().getValue() == wasteStack.peek()
				  .getValue()+1 && tableauArray[i].peek().isRed() != wasteStack
				  .peek().isRed()){
					
					tableauArray[i].push(wasteStack.pop());

					displayGameBoard();
					System.out.println(" ");
					System.out.println("Moved from W1 to T"+i+" .");
					System.out.println(" ");
					return true;
				}
			}
		}
		return false;	
	}
	/**
	 * this method tries to bring any valid card from other tableau piles to 
	 * the current tableau. this method first looks for a top card and after
	 * being unsuccessful, it looks if N cards can be brought or not
	 * @return
	 * 		returns true if any card was possible to move
	 */
	public static boolean helpTableauToTableau(){
		//go through all tableaus
		for(int i=1; i<8; i++){
			//if current tableau is empty, look for a King in other tableau's
			if (tableauArray[i].isEmpty()){
				for(int j=1; j<8;j++ ){
					//check only when the tableau is not empty
					if(!tableauArray[j].isEmpty()){
						//check if any King can be brought from the top of a pile
						if(tableauArray[j].peek().getValue() == 13){
							tableauArray[i].push(tableauArray[j].pop());
							
							//if theres is no card left after removing the king
							//this means, this king was already in a valid place
							// so the move is not good. so put back the king
							if(tableauArray[j].isEmpty()){
								tableauArray[j].push(tableauArray[i].pop());
							}
							//otherwise print the move
							else{
								displayGameBoard();
								System.out.println(" ");
								System.out.println("Moved from T"+j+" to T"+i+" .");
								System.out.println(" ");
								return true;
							}
						}
						//if no king was available on the top, check if any 
						//faced up king is available inside other cards of 
						//that tableau  or not
						else{
							//create a new stack and put all the facedup cards
							CardStack tempStack1 = new CardStack();
							//tempstack2 only gets the faced down cards
							CardStack tempStack2 = new CardStack();
							while(!tableauArray[j].isEmpty()){
								if(tableauArray[j].peek().isFaceUp){
									tempStack1.push(tableauArray[j].pop());
								}
								else tempStack2.push(tableauArray[j].pop());
							}
							//keep a copy of the top card of tempStack1
							Card tempCard;
							tempCard = tempStack1.peek();
							//get the size of tempStack 2 to see if it is empty
							int tempSize= tempStack2.size();
							//put all the faced down cards back
							while(!tempStack2.isEmpty()){
								tableauArray[j].push(tempStack2.pop());
							}
							//put all the faced up cards back
							while(!tempStack1.isEmpty()){
								tableauArray[j].push(tempStack1.pop());
							}
							//check if the top card of the new stack is king
							//or not.if king, move all the cards if tempSize!=0
							if(tempCard.getValue() == 13){
								//tempSize = 0 indicates that the King was in the
								//first position of a pile. which was a valid place
								if(tempSize!=0){
									//put the faced up cards in tempStack1
									//and faced down cards in tempStack2
									while(!tableauArray[j].isEmpty()){
										if(tableauArray[j].peek().isFaceUp){
											tempStack1.push(tableauArray[j].pop());
										}
										else tempStack2.push(tableauArray[j].pop());
									}
									//put back the faced down cards
									while(!tempStack2.isEmpty()){
										tableauArray[j].push(tempStack2.pop());
									}
									//put all the faced up cards in the new pile
									while(!tempStack1.isEmpty()){
										tableauArray[i].push(tempStack1.pop());
										
									}
									displayGameBoard();
									System.out.println(" ");
									System.out.println("Moved from T"+j+" to T"+i+" .");
									System.out.println(" ");
									return true;
								}	
							}
							//if the top card of tempStack1, which carries the
							//faced up cards wasn't a King, put all the cards back
							else{
								while(!tempStack1.isEmpty()){
									tableauArray[j].push(tempStack1.pop());
								}
							}
						}
					}
				}
			}
			//if current tableau is not empty, look for any valid card that 
			//can be brought from other tableau piles
			else if(!tableauArray[i].isEmpty()){
				for(int j=1; j<8;j++ ){
					//look for other places only if that place is not empty
					if(!tableauArray[j].isEmpty()){
						//check for validity
						if(tableauArray[j].peek().getValue() == tableauArray[i].
						  peek().getValue()-1 && tableauArray[i].peek().isRed()
						  != tableauArray[j].peek().isRed()){		
							
							tableauArray[i].push(tableauArray[j].pop());
							
							//now check the table that i just removed the card
							//from. if the top card is faced up, this means 
							//the card i removed was in a good place, and the 
							//move is not necessary.
							//if the tableau becomes empty, this means its a
							//valid  move
							if(tableauArray[j].isEmpty()){
								displayGameBoard();
								System.out.println(" ");
								System.out.println("Moved from T"+j+" to T"+i+" .");
								System.out.println(" ");
								return true;
							}
							//if the tableau doesn't become empty and the top card
							//is faced down, then its also a valid move
							else if(!tableauArray[j].isEmpty() && !tableauArray[j]
							  .peek().isFaceUp()){
								
								displayGameBoard();
								System.out.println(" ");
								System.out.println("Moved from T"+j+" to T"+i+" .");
								System.out.println(" ");
								return true;
							}
							//otherwise it was invalid move
							else{
								tableauArray[j].push(tableauArray[i].pop());
							}
						}
						// check if any N number of faced up cards can be
						//braught from other tableau's or not
						else{
							//create a new stack and put all the facedup cards
							CardStack tempStack1 = new CardStack();
							CardStack tempStack2 = new CardStack();
							while(!tableauArray[j].isEmpty()){
								if(tableauArray[j].peek().isFaceUp){
									tempStack1.push(tableauArray[j].pop());
								}
								else tempStack2.push(tableauArray[j].pop());
							}
							//keep a copy of the top card of tempStack
							Card tempCard;
							tempCard = tempStack1.peek();

							//put all the faced down cards back
							while(!tempStack2.isEmpty()){
								tableauArray[j].push(tempStack2.pop());
							}
							while(!tempStack1.isEmpty()){
								tableauArray[j].push(tempStack1.pop());
							}
							//check if the top card of the new stack is king
							//or not.if king, move all the cards
							if(tempCard.getValue() == tableauArray[i].peek().
							  getValue()-1 && tempCard.isRed()!=tableauArray[i]
							  .peek().isRed()){ 
								
								while(!tableauArray[j].isEmpty()){
									if(tableauArray[j].peek().isFaceUp){
										tempStack1.push(tableauArray[j].pop());
									}
									else tempStack2.push(tableauArray[j].pop());
								}
								
								while(!tempStack2.isEmpty()){
									tableauArray[j].push(tempStack2.pop());
								}
								
								while(!tempStack1.isEmpty()){
									tableauArray[i].push(tempStack1.pop());
									
								}
								displayGameBoard();
								System.out.println(" ");
								System.out.println("Moved from T"+j+" to T"+i+" .");
								System.out.println(" ");
								return true;
							}
							
						}
						
					}
				}
			}
			//if current tableau is empty look for other tableaus where K is n cards down
			//also if current tableau is not empty look for n cards that can be moved.
		}
		return false;
	}
}
