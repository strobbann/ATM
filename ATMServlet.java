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
	System.out.println("connected");
	
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
	int deposit = Integer.parseInt(req.getParameter("Amount"));
	System.out.println("deposit: " + deposit);
	if(deposit >0) {
	    atm.deposit(account, deposit);
	}
	System.out.println("Account balance: " + account.getBalance());
	doGet(req, resp);
    }

    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	resp.setContentType("text/html");
	
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
	}
    }
    private String showBalanceMenu(String act, Account account) {
	String returnStr = docType +
	    "<html>\n" +
	    "<head><title>" + act + "</title></head>\n" +
	    "<body>\n" +
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
	    "<input type=\"text\" name  = \"Amount\"/>\n" +
	    "<input type=\"submit\" value = \"Submit\"/>\n"+ 
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
	    "<input type=\"submit\" name =\"act\" value = \"Deposit\"/>\n"+ 
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

			   
