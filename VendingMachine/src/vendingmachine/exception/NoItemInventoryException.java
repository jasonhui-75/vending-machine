package vendingmachine.exception;

public class NoItemInventoryException extends RuntimeException {
	public NoItemInventoryException()
	{
		super("This item is currently out of stock");
	}
}
