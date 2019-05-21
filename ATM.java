import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.InputMismatchException;

public class ATM {
    private Map<String, Account> accounts;;
    private Scanner in;
    private String currency = "kr";
    
    public ATM() {
	accounts = new HashMap<>();
	in = new Scanner(System.in);
	/*
	Account account1 = new Account("Robert",  "4321"); 
	Account account2 = new Account("Kalle", "1234");
	Account account3 = new Account("Peter",  "5678");	
	accounts.put(account1.getPin(), account1);
	accounts.put(account2.getPin(), account2);
	accounts.put(account3.getPin(), account3);
	ObjectIO.writeObject(accounts);
	*/
	accounts = ObjectIO.readObject();
    }

    public void start() {
	Account account = login();
	if(account!= null) {
	    menu(account);
	}
    }

    public Account checkLogin(String loginPin){
	if(accounts.containsKey(loginPin)){
	    return accounts.get(loginPin); 
	}else {
	    System.out.println("Cannot find Account or wrong pin");
	}
	return null;
    }
    
    public Account login() {
	for(int i = 0; i < 3; i++){
	    System.out.println("Please enter pin");  
	    String loginPin = in.nextLine();
	    Account account = checkLogin(loginPin);
	    if(account != null){
		return account;
	    }
	}
	System.out.println("too many loginAttempts");
	return null;
	
    }
    
    public void printMenu() {
	String menuString = "";
	menuString += "1: Withdraw\n";
	menuString += "2: Deposit\n";
	menuString += "3: Show Balance\n";
	menuString += "4: Calculate Interest\n";
	menuString += "0: To Exit";
	System.out.println(menuString);
    }

    public void menu(Account account) {
	int choice = -1;
	while(choice != 0) {
	    printMenu();
	    choice = getNumberInputInt();
	    switch(choice) {
		case 1: {
		    withdrawUI(account);
		    break;
		}
		
		case 2 : {
		    depositUI(account);
		    break;
		}
		    
		case 3:{
		    showBalance(account);
		    break;
	       	}
		    
		case 4: {
		    calculateInterestUI(account);
		    break;
		}

		case 0: {
		    ObjectIO.writeObject(accounts);
		    in.close();
		    break;
		}

		default:{
		    System.out.println("incorrect input");
		    break;
		}
	    }
	}
    }

    public void withdraw(Account fromAccount, int amount)throws InsufficientFundsException, IllegalAmountException {
	int balance = fromAccount.getBalance();
	if(amount > 2000){
	    throw new IllegalAmountException(2000);
	   	    
	}else if(balance >= amount) {
	    balance -= amount;
	    fromAccount.setBalance(balance);
	    System.out.println("Your balance is now: " + fromAccount.getBalance() + currency);
	} else {
	    throw new InsufficientFundsException();
	}
    }

    public void withdrawUI(Account fromAccount) {
	System.out.println("How much do you want to withdraw");
	int amount = getNumberInputInt();
	try{
       	withdraw(fromAccount, amount);
	} catch(InsufficientFundsException|IllegalAmountException ex) {
	    System.err.println(ex.getMessage());
	}
	
    }

    public int getNumberInputInt(){
	boolean isCorrectInput = false;
	while(!isCorrectInput){
	    try {
		Integer number  = in.nextInt();
		isCorrectInput = true;
		//EmptyBuffer
		in.nextLine();
		return number;
		
	    }catch (InputMismatchException ex) {
		//EmptyBuffer
		in.nextLine();
		System.out.println("Incorrect value please enter a valid number.");
	    }
	}
	return -1;
    }

    public double getNumberInputDouble() {
        boolean isCorrectInput = false;
	while(!isCorrectInput){
	    try {
		double number  = in.nextDouble();
		isCorrectInput = true;
		//EmptyBuffer
		in.nextLine();
		return number;
		
	    }catch (InputMismatchException ex) {
		//EmptyBuffer
		in.nextLine();
		System.out.println("Incorrect value please enter a valid number.");
	    }
	}
	return -1;
    }
    
    public void showBalance(Account fromAccount) {
	System.out.println("Hi " + fromAccount.getName());
	System.out.println("Your current balance is " + fromAccount.getBalance() + currency);

    }

    public void deposit(Account toAccount,int amount) {
	int balance = toAccount.getBalance();
	balance +=  amount;
	toAccount.setBalance(balance);
    }

    public void depositUI(Account toAccount) {
       System.out.println("How much do you want to deposit");
       int deposit = getNumberInputInt();
       deposit(toAccount, deposit);
       System.out.println("Your current balance is now " + toAccount.getBalance() + currency);
       
    }

    public double calculateInterest(Account account, int years, double interest) {
	double powerOf = interest;
	for(int i = 0; i < years-1; i++) {
	    powerOf = powerOf * interest;
	}
	double amountInBank = account.getBalance() * powerOf;
	return amountInBank;
    }

    public void calculateInterestUI(Account account) {
	System.out.println("Put interest per year");
	double interest = getNumberInputDouble();
	System.out.println("Put amount of years");
	int years = getNumberInputInt();
	double amountInBank = calculateInterest(account, years, interest);
	String amountInBankFormat = String.format("%.2f", amountInBank);
	System.out.println("With " + interest + "% in " + years + " year/s you will have " +  amountInBankFormat + currency + " in bank");
    }

    public Map<String, Account> getAccounts() {
	return accounts;
    }

    public void save() {
	ObjectIO.writeObject(accounts);
	
    }
    
}
