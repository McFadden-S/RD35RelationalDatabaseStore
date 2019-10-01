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
    
    /*
    Purpose: reset saved values from log in
    In: none
    Out: none
    */
    public void logout(){
        currentUser = "";
        loginVerified = false;
    }//end of logout
    
    /*
    Purpose: get the number of products offered by database
    In: true for offered, false for in cart
    Out: (int) number of products 
    */
    public int getNumberofProducts(boolean control) throws SQLException{
        int num = 0;
        Statement stmt = connection.createStatement();
        String query;
        ResultSet rs;
        
        if(control){
            query = "select idProducts from Products";
            rs = stmt.executeQuery(query);
            rs.last();
            num=rs.getRow();
        }//end of if offered
        else{
            query = "select idCart from Cart";
            rs = stmt.executeQuery(query);
            rs.last();
            num=rs.getRow();
        }//end of else in cart
        
        return num;
    }//end of number of products
    
    /*
    Purpose: Sends a string array with the information for each product
    In: none
    Out: String[] product info
    */
    public String[] getProductInfo(int numOfProd) throws SQLException{
        String[] info = new String[numOfProd]; 
        
        Statement stmt = connection.createStatement();
        String query = "select idProducts, name from Products";
        ResultSet rs = stmt.executeQuery(query);
        
        for(int i = 0; i < info.length; i++){ 
           rs.absolute(i+1);
           info[i]=rs.getInt(1) + ": " +rs.getString(2);
        }//end of for loop
            
        return info;
    }//end of getProductInfo
    
 }  // end class