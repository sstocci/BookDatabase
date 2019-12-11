package database;
import java.util.Scanner;
import java.sql.*;
/**
 *
 * @author seantocci
 */
public class Database {

final static String driverClass = "org.sqlite.JDBC";
final static String url = "jdbc:sqlite:/Users/seantocci/Documents/workspace/LibraryDatabase/src/database/books.db";

    public static void main(String[] args) {
        // TODO code application logic here
        String sql = "select * from Books";
        Scanner input = new Scanner(System.in);
	Connection connection = null;
        
        while (sql != "stop"){
            System.out.println("Here are some executables for the database:");
            System.out.println("Press 1 for: All titles of books written by a male author");
            System.out.println("Press 2 for: All book titles by the publisher Books R US");
            System.out.println("Press 3 for: All publishers by books written by Sean Tocci");
            System.out.println("Press 4 for: The number of Books each author wrote");
            System.out.println("Press 5 for: All SSN and the cost of all their books");
            System.out.println("Press 6 for: All Authors who have published with more than 2 publishers");
            System.out.println("Press 7 to quit");
            int UsrIn;
            UsrIn = input.nextInt();
            int statement = UsrIn;
            switch(statement){
        case 1:
                   	try {
            Class.forName(driverClass);
            connection = DriverManager.getConnection(url);
            Statement stmnt = connection.createStatement();
    
            sql ="select Title " +
"from Authors join Wrote join books" +
" where Gender = 'Male' and Authors.SSN=Wrote.SSN and Wrote.ISBN == books.ISBN";
            ResultSet results = stmnt.executeQuery(sql);
            while (results.next()) {
            String names = results.getString("Title");
            System.out.println(names);
	}
	} 
        catch (Exception e) {
	System.out.println(sql);
	e.printStackTrace();
	} finally {
	try {
	connection.close();
	} 
        catch (Exception e) {}
	}
                   break;
                        
        case 2:
            try {
            Class.forName(driverClass);
            connection = DriverManager.getConnection(url);
            Statement stmnt = connection.createStatement();
            sql = "select title, Publisher " +
"from Books join Publisher " +
"where Books.Publisher = Publisher.companyName and Books.Publisher ='Books R US'";
            ResultSet results = stmnt.executeQuery(sql);
            while (results.next()) {
            String names = results.getString("title");
            String publisher = results.getString("Publisher");
            System.out.println("");
            System.out.printf("Title: %s Publisher: %s",names, publisher);
	}
	} 
        catch (Exception e) {
	System.out.println(sql);
	e.printStackTrace();
	} finally {
	try {
	connection.close();
	} 
        catch (Exception e) {}
	}
                   break;
           /////////////////////////
            case 3:
            try {
            Class.forName(driverClass);
            connection = DriverManager.getConnection(url);
            Statement stmnt = connection.createStatement();
       
            sql ="select Publisher from Authors join Wrote join Books where Authors.fname ='Sean' and Authors.lastName='Tocci' and Authors.SSN = Wrote.SSN and wrote.ISBN = books.ISBN";
            ResultSet results = stmnt.executeQuery(sql);
            while (results.next()) {
            String names = results.getString("Publisher");
            System.out.println(names);
	}
	} 
        catch (Exception e) {
	System.out.println(sql);
	e.printStackTrace();
	} finally {
	try {
	connection.close();
	} 
        catch (Exception e) {}
	}
            break;
            //////////////////////
        case 4:
            try {
            Class.forName(driverClass);
            connection = DriverManager.getConnection(url);
            Statement stmnt = connection.createStatement();
       
            sql ="select fname,lastName,count(*) from Wrote inner join Authors on Wrote.ssn = Authors.ssn group by Wrote.SSN";
            ResultSet results = stmnt.executeQuery(sql);
            while (results.next()) {
            String fname = results.getString("fname");
            String lname = results.getString("lastName");
            String count = results.getString("count(*)");
            System.out.println("");
            System.out.printf("First Name: %s Last Name: %s count: %s",fname, lname,count);
            System.out.println("");
	}
	} 
        catch (Exception e) {
	System.out.println(sql);
	e.printStackTrace();
	} finally {
	try {
	connection.close();
	} 
        catch (Exception e) {}
	}
                   break;
        case 5:
                   	try {
            Class.forName(driverClass);
            connection = DriverManager.getConnection(url);
            Statement stmnt = connection.createStatement();
       
            sql ="select sum(price),ssn from Wrote inner join Books on Wrote.ISBN = Books.ISBN group by Wrote.SSN";
            ResultSet results = stmnt.executeQuery(sql);
            while (results.next()) {
            String sum = results.getString("sum(price)");
            String ssn = results.getString("ssn");
            System.out.printf("SSN: %s Total Cost: %s",ssn,sum);
            System.out.println("");
	}
	} 
        catch (Exception e) {
	System.out.println(sql);
	e.printStackTrace();
	} finally {
	try {
	connection.close();
	} 
        catch (Exception e) {}
	}
                   break;
        case 6:
            try {
            Class.forName(driverClass);
            connection = DriverManager.getConnection(url);
            Statement stmnt = connection.createStatement();
       
            sql ="select count(Publisher),fname,lastName from Wrote inner join Books on Wrote.ISBN = Books.ISBN join Authors on Wrote.SSN=Authors.SSN group by Wrote.SSN Having count(*) > 2";
            ResultSet results = stmnt.executeQuery(sql);
            while (results.next()) {
            String count = results.getString("count(Publisher)");
            String fname = results.getString("fname");
            String lname = results.getString("lastName");
            System.out.printf("First Name: %s Last Name: %s Number of Publishers: %s",fname, lname,count);
            System.out.println("");
	}
	} 
        catch (Exception e) {
	System.out.println(sql);
	e.printStackTrace();
	} finally {
	try {
	connection.close();
	} 
        catch (Exception e) {}
	}
                   break;
                case 7:
                    sql="stop";
                    break;
            }

        
        }
    }
    
}
