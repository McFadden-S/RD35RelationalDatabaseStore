// **********************************************************************
// Programmer:      Shae McFadden
// Class:           CS35S
//
// Assignment:      Relational Database Store
//
// Description:     This is the main class that will establish the server
//                  connection and call the GUI for the user to interface
//
// Input:           The user will log in, then be moved to the the store
//                  page where they can make purchases
//
// Output:          The GUI will show them the "store" and their previous
//                  transactions.
// ***********************************************************************

import java.io.IOException;
import java.sql.*;
import javax.swing.*;
import java.text.DecimalFormat;

public class Main {  // begin class
	public static void main(String args[]) throws SQLException {  // begin main
	
	// ***** declaration of variables and constants *****
	
        String url = "jdbc:mysql://localhost:3306/mydb?zeroDateTimeBehavior=convertToNull";
        
	// ***** create objects *****
        
        Connection connection = DriverManager.getConnection(url, "root", "Tiwaz16CF");
        
	// ***** Print Banner *****
	
		System.out.println("**************************************");
		System.out.println("NAME:        Shae McFadden");
		System.out.println("Class:       CS35S");
		System.out.println("Assignment:  Relational Database Store");
		System.out.println("**************************************");
	
	// ***** processing *****
	
		

	// ***** closing message *****
	
		System.out.println("\nend of processing");
	
	}  // end main	
}  // end class