/**
 * This <code>CardStack</code> class will represent a stack of cards in the game
 * @author Jahin Imtiaz
 * 		<dt><b>email:</b><dd>  jahin.imtiaz@stonybrook.edu
 * 		<dt><b>Stony Brook ID:</b><dd>  111214457
 *		<dt><b>Rec Section:</b><dd> 01
 */
import java.util.Stack;

public class CardStack {
	
	char type;	//type of the CardStack
	Stack <Card> stack;
	/**
	 * Constructor for the CardStack class
	 * creates a stack of type Card
	 */
	public CardStack(){
		stack = new Stack<Card>();
	}
	/**
	 * Constructor for the CardStack class with a parameter
	 * creates a stack of type Card
	 * @param type
	 * 		the type of the card
	 */
	public CardStack(char type){
		type = type;
		stack = new Stack<Card>();
	}
	/**
	 * Pushes a card into the stack
	 * @param newCard
	 * 		the card to be added
	 */
	public void push(Card newCard){
		stack.push(newCard);
	}
	/**
	 * Pops a card from the stack
	 * @return
	 * 		returns top card of the stack
	 */
	public Card pop(){
		return stack.pop();
	}
	/**
	 * checks if the stack is empty or not
	 * @return
	 * 		returns true if the card is empty
	 */
	public boolean isEmpty(){
		return (stack.isEmpty());
	}
	/**
	 * returns the size of the stack
	 * @return
	 * 		returns the size of the stack
	 */
	public int size(){
		return stack.size();
	}
	/**
	 * Returns the top card of a stack without removing
	 * @return
	 * 		returns a Card
	 */
	public Card peek(){
		return stack.peek();
	}
	/**
	 * prints the stack depending on the type of the stack
	 * @param type
	 * 		type that determines the stack
	 */
	public void printStack(char type){ 
		switch(type){
		//if its a stock Stack, print only the top card
		case 's' : 
			//prints empty cards when the stack is empty
			if (stack.isEmpty()){
				System.out.printf("%-5s","[  ]");
				break;
			}
			//otherwise prints the top card
			else{
				//sets the face value to false so is faced down after printing
				stack.peek().setFaceUp(false);
				System.out.printf("%-5s","["+stack.peek()+"]");
				break;
			}
		//if its a waste stack, prints the top card
		case 'w' :
			if (stack.isEmpty()){
				System.out.printf("%-5s","[  ]");
				break;
			}
			else{
				System.out.printf("%-5s","["+stack.peek()+"]");
				break;
			}
		//if its a foundation stack, prints the top card
		case 'f' :
			System.out.printf("%-5s","["+stack.peek()+"]");
			break;
		//if its a tableau stack, prints the whole stack on a line
		case 't' :
			if (stack.isEmpty()){
				System.out.printf("%-5s","[  ]");
				break;
			}
			else{
				//creates a temporary stack to print the stack
				Stack <Card> tempStack = new Stack<Card>();
				//set the top cards face value to true to show only the first card
				stack.peek().setFaceUp(true);
				while(!stack.isEmpty()){
					tempStack.push(stack.pop());
				}
				//put the cards back and print
				while(!tempStack.isEmpty()){
					System.out.printf("%-5s","["+tempStack.peek()+"]");
					stack.push(tempStack.pop());
				}
			}
			break;
			
		}
	}
	/**
	 * count the number of faced up card in a stack
	 * @param n
	 * 		number of faceup cards needed
	 * @return
	 * 		return true if n number of faced up cards is available
	 */
	public boolean checkNopenCards(int n){
		int count=0;
		Stack<Card> tempStack = new Stack<Card>();
		//increase the counter when the card is faced up
		while(!stack.isEmpty()){
			if(stack.peek().isFaceUp){
				count++;
			}
			tempStack.push(stack.pop());
		}
		//push the cards back in to the main stack
		while(!tempStack.isEmpty()){
			stack.push(tempStack.pop());
		}
		//if counter is less than the asked number, return false
		if (count<n) return false;
		//else return true
		else return true;
	}
}
