package vendingmachine.exception;

public class NoSuchItemException extends RuntimeException {
	public NoSuchItemException() {
		super("Please select a valid item");
	}
}
