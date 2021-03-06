// **********************************************************************
// Programmer:      Shae McFadden
// Class:           CS35S
//
// Assignment:      Relational Database Store
//
// Description:     This is the main class that will call the GUI and
//                  Controller
//
// Input:           The user will log in, then be moved to the the store
//                  page where they can make purchases through the GUI
//
// Output:          The GUI will show them the "store" and their previous
//                  transactions.
// ***********************************************************************

import java.io.IOException;
import javax.swing.*;
import java.text.DecimalFormat;

public class Main {  // begin class
	public static void main(String args[]) {  // begin main
	
	// ***** declaration of variables and constants *****
	
        String url = "jdbc:mysql://localhost:3306/mydb?zeroDateTimeBehavior=convertToNull";
        String user = "root";
        String pass = "Tiwaz16CF";
        
	// ***** create objects *****
        
        Controller control = new Controller();
        LogIn loginUI = new LogIn();
        Store storeUI = new Store();
        
	// ***** Print Banner *****
	
		System.out.println("**************************************");
		System.out.println("NAME:        Shae McFadden");
		System.out.println("Class:       CS35S");
		System.out.println("Assignment:  Relational Database Store");
		System.out.println("**************************************");
	
	// ***** processing *****
	
		control.establishConnection(url, user, pass);
                
                loginUI.setController(control);
                loginUI.setStoreUI(storeUI);
                loginUI.setVisible(true);
                
                storeUI.setController(control);
                storeUI.setloginUI(loginUI);
                
	// ***** closing message *****
	
		System.out.println("END OF SET UP ROUTINE");
	
	}  // end main	
}  // end class