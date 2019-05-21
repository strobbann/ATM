import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.HashMap;
public class ObjectIO {
    private static String target = "C:\\Users\\Robert\\javacode\\git\\ATM\\accounts.ser";
    // private static InputStream inputStream = Main.class.getResourceAsStream("accounts.ser");
    public ObjectIO() {
	
    }

    public static void writeObject(Map<String ,Account> accounts) {
	try(FileOutputStream fileOut = new FileOutputStream(target);
	    ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
	    out.writeObject(accounts);
	}catch(Exception ex) {
	    ex.printStackTrace();
	}
	
    }
    @SuppressWarnings("unchecked")
    public static Map<String, Account> readObject() {
	try(FileInputStream fileIn = new FileInputStream(target);
	    ObjectInputStream in = new ObjectInputStream(fileIn)) {
	    return (Map<String, Account>)in.readObject();
	} catch(Exception ex) {
	    ex.printStackTrace();
	}
	return new HashMap<>();
    }
}
