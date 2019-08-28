/**
 * The llegalPositionException throws IllegalArgumentException .
 * @author Jahin Imtiaz
 * 		<dt><b>email:</b><dd>  jahin.imtiaz@stonybrook.edu
 * 		<dt><b>Stony Brook ID:</b><dd>  111214457
 *		<dt><b>Rec Section:</b><dd> 01
 */
public class IllegalPositionException extends IllegalArgumentException{

	/**
	 * return a message when the exception happens.
	 * @param message
	 * Exception message to be displayed to the user.
	 */
	public IllegalPositionException(String message){
		super(message);
	}
}
