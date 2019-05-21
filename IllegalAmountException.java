public class IllegalAmountException extends Exception{

    public IllegalAmountException(int amount) {
	super("Not Allowed To withdraw more than " + amount);
    }
}
