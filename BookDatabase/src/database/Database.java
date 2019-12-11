package database;
import java.util.Scanner;
import java.sql.*;
/**
 *
 * @author seantocci
 */
public class Database {

	final static String driverClass = "org.sqlite.JDBC";
	
	// User must change the url for the project to run!!!
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
				//Press 1 for: All titles of books written by a male author
				try {
					//gets connection to the database
					Class.forName(driverClass);
					connection = DriverManager.getConnection(url);
					Statement stmnt = connection.createStatement();

					// create SQLite statement to send to database
					sql ="select Title " +
							"from Authors join Wrote join books" +
							" where Gender = 'Male' and Authors.SSN=Wrote.SSN and Wrote.ISBN == books.ISBN";
					
					//retrieves all the Titles for male authors
					ResultSet results = stmnt.executeQuery(sql);
					while (results.next()) {
						String names = results.getString("Title");
						System.out.println(names);
					}
				} 
				catch (Exception e) {
					//Prints out sql statement (usually the problem if something goes wrong)
					System.out.println(sql);
					e.printStackTrace();
				} finally {
					try {
						// close the connection to the database
						// in a try block since sometimes it can fail.
						connection.close();
					} 
					catch (Exception e) {}
				}
				break;

			case 2:
				//Press 2 for: All book titles by the publisher Books R US"
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
				
			case 3:
				//Press 3 for: All publishers by books written by Sean Tocci
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

			case 4:
				// Press 4 for: The number of Books each author wrote
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
				//Press 5 for: All SSN and the cost of all their books
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
				//Press 6 for: All Authors who have published with more than 2 publishers
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
				// stops the while loop and ends program
				sql="stop";
				break;
			}


		}
	}

}
