/* **********************************************************
 * Programmer:  Shae McFadden
 * Class:	CS35S
 * 
 * Assignment:	Relational Database Store
 *
 * Description:	This class will handle the interactions between
 *              the store GUI and the database
 * 
 * **************************************************************/
 
 // import files here as needed
 import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
 
 public class Controller
 {  // begin class
 	
    // *********** class constants **********
    // ********** instance variable **********

     private Connection connection; 
     private boolean loginVerified = false;
     private String currentUser = "";
     
    // ********** constructors ***********

    /*
     Purpose: Establish a default constructor
     In: None
     Out: None
    */
     public Controller(){
         
     }//end of default constructor
     
    // ********** functions **********
     
     /*
     Purpose: to establish the connection between the controller and the database
     In: Database URL, Username, Password in the string format
     Out: None
     */
    public void establishConnection(String url, String user, String pass){
         
        try {
            connection = DriverManager.getConnection(url, user, pass);
        }//end of try 
        catch (SQLException ex) {
            System.out.println("Connection Failed");
            while(true);
        }//end of catch
        
    }//end of establishConnection
 
    /*
    Purpose: Checks to see if store login information is valid
    In: username and password as a string
    Out: Boolean for if its valid 
    */
    public boolean validateLogin(String user, String pass) throws SQLException{
        Statement stmt = connection.createStatement();
        
        String query = "select username password from user where username = \"" 
                + user + "\" and password = \"" + pass + "\"";
        
        ResultSet rs =  stmt.executeQuery(query); //executes SQL
        
        if (rs.absolute(1)){ //true if object has a row ie. query found matching values
            loginVerified = true;
            currentUser = user;
        }//end of if
        
        return loginVerified;
    }//end of validateLogin
    
    
 }  // end class