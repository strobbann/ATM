import java.io.Serializable;
public class Account implements Serializable {
    private String name;
    private String number;
    private String pin;
    private int balance = 0;

    public Account(String name, String pin) {
	this.name = name;
	this.number = java.util.UUID.randomUUID().toString();
	this.pin = pin;
    }

    public void setBalance(int balance) {
	this.balance = balance;
    }
    
    public String getNumber(){
	return number;
    }

    public String getPin() {
	return pin;
    }

    public int getBalance() {
	return balance;
    }

    public String getName() {
	return name;
    }

}


