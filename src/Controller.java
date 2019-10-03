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
            num=rs.getInt(1);
        }//end of if offered
        else{
            query = "select count(*) from Cart where username = \"" + currentUser + "\"";
            rs = stmt.executeQuery(query);
            rs.absolute(1);
            num=rs.getInt(1);
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
    
    /*
    Purpose: Sends a string array with the information for each product
    In: none
    Out: String[] product info
    */
    public String[] getCartInfo(int numOfProd) throws SQLException{
        String[] info = new String[numOfProd]; 
        
        Statement stmt = connection.createStatement();
        Statement stmt2 = connection.createStatement();
        String query = "select idProduct, quantity from Cart where username = \"" + currentUser +  "\"";
        ResultSet rs = stmt.executeQuery(query);
        ResultSet name;
        
        double price = 0;
        
        for(int i = 0; i < info.length; i++){ 
           rs.absolute(i+1);
           
           query = "select name, price from Products where idProducts = " + rs.getInt(1);
           name = stmt2.executeQuery(query);
           name.absolute(1);
           
           price = rs.getInt(2) * name.getDouble(2);
           
           info[i]=rs.getInt(1) + ": ";
           info[i]+=name.getString(1) + " x";
           info[i]+=rs.getInt(2) + " = $";
           info[i]+=price + "";
        }//end of for loop
            
        return info;
    }//end of getCartInfo
    
    /*
    Purpose: get the number of columns offered by table in database
    In: table title
    Out: (int) number of columns
    */
    public int getNumberofColumns(String table) throws SQLException{
        int num = 0;
        
        Statement stmt = connection.createStatement();
        String query = "select count(*) from " + table;
        ResultSet rs = stmt.executeQuery(query);
        
        rs.absolute(1);
        num=rs.getInt(1);
        
        return num;
    }//end of number of columns 
    
    /*
    Purpose: Sends a string array with the information for a item
    In: none
    Out: String[] product info
    */
    public String[] getItemInfo(int numOfCol, int itemID) throws SQLException{
        String[] info = new String[numOfCol]; 
        
        Statement stmt = connection.createStatement();
        String query = "select * from Products where idProducts = \"" + itemID + "\"";
        ResultSet rs = stmt.executeQuery(query);
        rs.absolute(1);
        
        for(int i = 0; i < info.length; i++){ 
           info[i]=rs.getMetaData().getColumnLabel(i+1) + ": " +rs.getString(i+1);
        }//end of for loop
            
        return info;
    }//end of getItemInfo
    
    /*
    Purpose: add item to cart 
    In: selected item id, quantity to be added
    Out: None
    */
    public void addToCart(int id, int addedQuantity) throws SQLException{
        Statement stmtQuantityFind = connection.createStatement();
        Statement stmtAdd = connection.createStatement();
        
        String queryQuantityFind = "select idCart, quantity from Cart where idProduct = " + id + 
                " and username = \"" + currentUser + "\"";
        String queryAdd = "";
        
        ResultSet rsQuantity = stmtQuantityFind.executeQuery(queryQuantityFind);
        boolean repeatedAdd = rsQuantity.absolute(1);
        
        if(!repeatedAdd){
            queryAdd = "insert into Cart (username, idProduct, quantity) values "
                + "(\"" + currentUser + "\", " + id + ", " + addedQuantity + ")";
            stmtAdd.execute(queryAdd);
        }//end of if not added
        else{
            int quantity = rsQuantity.getInt(2) + addedQuantity;
            int idCart = rsQuantity.getInt(1);
            
            queryAdd = "update Cart set quantity = " + quantity + " where idCart = "
                    + idCart;
            stmtAdd.execute(queryAdd);
        }//end of else quantity already exist
    }//end of addToCart
    
 }  // end class