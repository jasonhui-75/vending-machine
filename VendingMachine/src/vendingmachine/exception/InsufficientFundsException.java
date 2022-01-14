package vendingmachine.exception;

public class InsufficientFundsException extends RuntimeException {
	public InsufficientFundsException(String m)
	{
		super(m);
	}
	public InsufficientFundsException() {
		super("Insufficient amount of coins entered");
	}
}
