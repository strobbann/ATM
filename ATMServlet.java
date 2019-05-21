import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ATMServlet extends HttpServlet {
    
    private String message;
    private String name;
    private String docType;
    private ATM atm;
    private Account account;
    private boolean isLoggedIn;
    
    public void init()throws ServletException {
	message = "Hello World";
	name = getClass().getName();
	docType =  "<!doctype html public \"-//w3c//dtd html 4.0 " +  "transitional//en\">\n";
	atm = new ATM();
	
    }
    
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
	String loginPin = req.getParameter("pin");
	
	String act = req.getParameter("act");
	
	resp.setContentType("text/html");
	
	PrintWriter out = resp.getWriter();
	if(!isLoggedIn){
	    if(loginPin != null) {
		account = atm.checkLogin(loginPin);
		if(account != null){
		    isLoggedIn = true;
		}
	    } else {
		out.println(loginScr());
	    }
	}
	
	
	if(account != null ) {
	    if(act == null){
		out.println(loggedInScreen());
	    } else {
		choiceMenu(act, out, account);
	    }
	}
    }
    
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	resp.setContentType("text/html");
	String depositParam = req.getParameter("DepositAmount");
	String withdrawParam = req.getParameter("WithdrawAmount");
	try {
	    if(depositParam != null) { 
		depositToATM(account, depositParam);
	    }
	    
	    else if(withdrawParam != null) {
		withdrawFromATM(account, withdrawParam);
	    }
	}catch(NumberFormatException ex) {
	    
	}
	doGet(req, resp);
    }

    private void depositToATM(Account account, String paramValue) {
	try {
	    int deposit = Integer.parseInt(paramValue);
	    atm.deposit(account, deposit);
	    atm.save();
	}catch (NumberFormatException ex) {
	    ex.printStackTrace();
	} 
    }
    
    private void withdrawFromATM(Account account, String paramValue) {
	try {
	    int withdraw = Integer.parseInt(paramValue);
	    atm.withdraw(account, withdraw);
	    atm.save();
	}catch (NumberFormatException ex) {
	    ex.printStackTrace();
	} catch (IllegalAmountException|InsufficientFundsException ex) {
	    ex.printStackTrace();
	}
    }
           
    public void choiceMenu(String act, PrintWriter out, Account account) {
	switch(act) {
	    case "Deposit" : {
		out.println(depositMenu(act));
		break;
	    }
	    case "Show balance" : {
		out.println(showBalanceMenu(act, account));
		break;
	    }
	    case "Withdraw" : {
		out.println(showWithdrawMenu(act));
	    }
	    case "Calculate Interest" : {
		out.println(showCalculateInterestMenu(act));
	    }
	}
    }

    private String showCalculateInterestMenu(String act) {
	String returnStr = docType +
	    "<html>\n" +
	    "<head><title>" + act  + "</title></head>\n" +
	    "<body bgcolor = \"#f0f0f0\" >\n" +
	    "<h1>" + act  + "</h1>\n" +
	    "<form action = \"ATMServlet\" method = \"GET\" >\n" +
	    "<input type=\"digit\" name  = \"Interest\"/>\n"+ 
	    "<input type=\"digit\" name = \"Years\">\n"+ 
            "<input type=\"submit\" value = \"Submit\">\n" +
	    "</form>\n" +
	    "</body>\n"+
	    "</html>";
	return returnStr;
    }
    
    private String showWithdrawMenu(String act){
	String returnStr = docType +
	    "<html>\n" +
	    "<head><title>" + act  + "</title></head>\n" +
	    "<body bgcolor = \"#f0f0f0\" >\n" +
	    "<h1>"+ act  + "</h1>\n" + 
	    "<form action = \"ATMServlet\" method = \"POST\" >\n" +
	    "<input type=\"text\" name  = \"WithdrawAmount\"/>\n" +
	    "<input type=\"submit\" value = \"Submit\"/>\n"+ 
	    "</form>\n" +
	    "</body>\n" +
	    "</html>\n";
	return returnStr;
    }
    private String showBalanceMenu(String act, Account account) {
	String returnStr = docType +
	    "<html>\n" +
	    "<head><title>" + act + "</title></head>\n" +
	    "<body bgcolor = \"#f0f0f0\">\n" +
	    "<h1>" + act + "</h1>\n" +
	    "<h1>" + account.getBalance() + "</h1>\n" + 
	    "<form action = \"ATMServlet\" method = \"GET\">\n" +
	    "<input type=\"submit\" value = \"back\">\n" +
	    "</body>\n" +
	    "</html>";
	
	return returnStr;
    }
    
    private String depositMenu(String act) {
	String returnStr = docType +
	    "<html>\n" +
	    "<head><title>" + act  + "</title></head>\n" +
	    "<body bgcolor = \"#f0f0f0\" >\n" +
	    "<h1>"+ act  + "</h1>\n" + 
	    "<form action = \"ATMServlet\" method = \"POST\" >\n" +
	    "<input type=\"text\" name  = \"DepositAmount\"/>\n" +
	    "<input type=\"submit\" value = \"Submit\"/>\n" + 
	    "</form>\n" +
	    "</body>\n" +
	    "</html>\n";
	return returnStr;
	
    }
    
    public String loggedInScreen() {
	String returnStr = docType +
	    "<html>\n" +
	    "<head><title>" + name  + "</title></head>\n" +
	    "<body bgcolor = \"#f0f0f0\" >\n" +
	    "<h1>" + account.getName() + "</h1>\n" +
	    "<form action = \"ATMServlet\" method = \"GET\" >\n" +
	    "<input type=\"submit\" name =\"act\" value = \"Show balance\"/>\n"+
	    "<input type=\"submit\" name =\"act\" value = \"Deposit\"/>\n"+
	    "<input type=\"submit\" name =\"act\" value = \"Withdraw\"/>\n"+
	    "<input type=\"submit\" name =\"act\" value = \"Calculate Interest\"/>\n"+ 
	    "</form>\n" + 
	    "</body>\n"+
	    "</html>";
	return returnStr;
    }
    
    public String loginScr() {
	String returnStr = docType +
	    "<html>\n" +
	    "<head><title>" + name  + "</title></head>\n" +
	    "<body bgcolor = \"#f0f0f0\" >\n" +
	    "<h1>" + message  + "</h1>\n" +
	    "<form action = \"ATMServlet\" method = \"GET\" >\n" +
	    "<input type=\"text\" name  = \"pin\"/>\n" +
	    "<input type=\"submit\" value = \"Submit\"/>\n"+ 
	    "</form>\n" +
	    "</body>\n"+
	    "</html>";
	return returnStr;
	
    }
    
}


