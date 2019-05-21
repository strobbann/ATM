public class InsufficientFundsException extends Exception {

    public InsufficientFundsException() {
	super("You don't have enough funds for this transaction");
    }
    
}
