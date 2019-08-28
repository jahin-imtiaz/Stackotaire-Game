/**
 * This <code>Card</code> class contains information of a card such as
 * its value , suit and its color
 * @author Jahin Imtiaz
 * 		<dt><b>email:</b><dd>  jahin.imtiaz@stonybrook.edu
 * 		<dt><b>Stony Brook ID:</b><dd>  111214457
 *		<dt><b>Rec Section:</b><dd> 01
 */
public class Card {
	private int suit;
	private int value;
	boolean isFaceUp;
	
	String values[]={" ","A","2","3","4","5","6","7","8","9","10","J","Q","K"};
	char suits[]   = {' ','\u2666','\u2663','\u2665','\u2660'};
	/**
	 * Constructor for the card class
	 * @param value
	 * 		value of the Card
	 * @param suit
	 * 		suit of a card
	 * @param isFaceUp
	 * 		the face value of a card.
	 */
	public Card( int value, int suit, boolean isFaceUp){
		this.suit=suit;
		this.value = value;
		this.isFaceUp =isFaceUp;
	}
	/**
	 * return the suit number of the card
	 * @return
	 * 		return the suit number
	 */
	public int getSuit() {
		return suit;
	}
	/**
	 * Set the suit number of a card
	 * @param suit
	 * 		suit of the card
	 */
	public void setSuit(int suit) {
		this.suit = suit;
	}
	/**
	 * returns the Value of the card
	 * @return
	 * 		value of the card
	 */
	public int getValue() {
		return value;
	}
	/**
	 * sets the value of the card
	 * @param value
	 * 		value of the card
	 */
	public void setValue(int value) {
		this.value = value;
	}
	/**
	 * return if the card is facedUP or not
	 * @return
	 * 		the face value of a card
	 */
	public boolean isFaceUp(){
		return isFaceUp;
	}
	/**
	 * sets the faceUp value
	 * @param faceUp
	 * 		boolean value for face Up
	 */
	public void setFaceUp(boolean faceUp){
		isFaceUp= faceUp;
	}
	/**
	 *  returns if the card is red or not
	 * @return
	 * 		return if red or not
	 */
	public boolean isRed(){
		return (this.suit%2 != 0); //if odd/red, returns true
	}
	/**
	 * returns a string format of the card
	 */
	public String toString(){
		if(isFaceUp == true){
			return (values[this.value]+suits[this.suit]);
		}
		else return ("XX");
	}
}
