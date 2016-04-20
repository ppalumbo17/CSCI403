import java.sql.*;
import java.io.*;
import java.util.*;

public class Project5 {

	private static Connection db;
	private boolean requestCompleted = false;
	private String username = "ppalumbo";

	public static void main(String[] args){
		try{
			Project5 proj5 = new Project5();
			proj5.run(args);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		try{
			db.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void run(String[] args) throws ClassNotFoundException{
		Class.forName("org.postgresql.Driver");
		String connectString = "jdbc:postgresql://flowers.mines.edu/csci403";
		System.out.print("Username: ");
		// String username = "ppalumbo"; //System.console().readLine();
		System.out.print("Password: ");
		String password = "pp1234pp"; //new String(System.console().readPassword());
		// Connection db;
		// boolean requestCompleted = false;

		//Connect to Database
		try {
			db = DriverManager.getConnection(connectString, username, password);
		}
		catch (SQLException e) {
			System.out.println("Error connecting to database: " + e);
			return;
		}

		// uncomment next block to turn off autoCommit - it will then be necessary to 
		// add in db.commit() calls to apply changes to the database

		try {
			db.setAutoCommit(false);
		}
		catch (SQLException e) {
			System.out.println("Database error: " + e);
			return;
		}

		//Ask what users would like to do
		while(!requestCompleted){
			System.out.print("Would you like to:\n1) Search\n2) Insert\n3) Modify\n4) Delete\n5) Quit\n");
			String request = System.console().readLine();
			
			if(request.equals("1")){
				System.out.println("\nEnter a keyword:\n");
				String input = System.console().readLine();
				searchDB(input);
				keepGoing();
			}
			else if(request.equals("2")){
				insertDB();
				keepGoing();
			}
			else if(request.equals("3")){
				modifyDB();
				keepGoing();
			}
			else if(request.equals("4")){
				deleteDB();
				keepGoing();
			}
			else if(request.equals("5")){
				requestCompleted = true;
			}
			else{
				System.out.println("Please enter a number from 1-4");
			}
		}
		
		
		

		// immediate SELECT
		// String query1 = 
		// 		"SELECT course_id, section, title, max_credits AS hours"
		// 		+ " FROM mines_courses"
		// 		+ " WHERE instructor = 'Painter-Wakefield, Christopher'";
		// try {
		// 	Statement stmt = db.createStatement();
		// 	ResultSet results = stmt.executeQuery(query1);
		// 	while (results.next()) {
		// 		String courseId = results.getString("course_id");
		// 		String section = results.getString("section");
		// 		String title = results.getString("title");
		// 		int hours = results.getInt("hours");
			
		// 		System.out.println(courseId + " " + section + "\t" + hours + " " + title);
		// 	}
		// 	System.out.println();
		// }
		// catch (SQLException e) {
		// 	System.out.println("Database error: " + e);
		// }

		
		// // prepared SELECT
		// String query2 =
		// 		"SELECT course_id, section, title, max_credits AS hours"
		// 		+ " FROM mines_courses"
		// 		+ " WHERE instructor = ?";
		
		// try {
		// 	PreparedStatement ps = db.prepareStatement(query2);
		
		// 	System.out.print("Please enter an instructor (lastname, firstname): ");
		// 	String instr = System.console().readLine();
		// 	ps.setString(1, instr);
		// 	ResultSet results = ps.executeQuery();
		// 	while (results.next()) {
		// 		String courseId = results.getString("course_id");
		// 		String section = results.getString("section");
		// 		String title = results.getString("title");
		// 		int hours = results.getInt("hours");
				
		// 		System.out.println(courseId + " " + section + "\t" + hours + " " + title);
		// 	}
		// 	System.out.println();
		// }
		// catch (SQLException e) {
		// 	System.out.println("Database error: " + e);
		// }

		// // DDL query - note we use .execute() instead of .executeQuery()
  //               String ddlQuery = "CREATE TABLE foo (x text PRIMARY KEY)";
		// try {
		// 	Statement stmt = db.createStatement();
		// 	stmt.execute(ddlQuery);
		// }
		// catch (SQLException e) {
		// 	System.out.println("Database error: " + e);
		// }
			

		// // some modification queries - note the different method used, "executeUpdate()",
		// // which returns an int (# of rows affected) rather than a ResultSet
		// String query3 = "INSERT INTO foo VALUES (?)";
		
		// try {
		// 	PreparedStatement ps = db.prepareStatement(query3);
		// 	ps.setString(1, "testing 1 2 3");
		// 	int rows = ps.executeUpdate();
		// 	System.out.println("Rows inserted: " + rows);
		// 	//db.commit();	// needed when autoCommit is set to false
		// }
		// catch (SQLException e) {
		// 	System.out.println("Database error: " + e);
		// }

		// // this time should cause an integrity constraint violation
		// try {
		// 	PreparedStatement ps = db.prepareStatement(query3);
		// 	ps.setString(1, "testing 1 2 3");
		// 	int rows = ps.executeUpdate();
		// 	System.out.println("Rows inserted: " + rows);
		// 	//db.commit();	// needed when autoCommit is set to false
		// }
		// catch (SQLException e) {
		// 	System.out.println("Database error: " + e);
		// }

		// // bad SELECT query with exception handling
		// String query4 = "SELECT FROM foo WHERE blah = arg";
		// try {
		// 	Statement stmt = db.createStatement();
		// 	ResultSet results = stmt.executeQuery(query4);
		// }
		// catch (SQLException e) {
		// 	System.out.println("Database error: " + e);
		// }
	}

	//Search Function
	public void searchDB(String criteria){
		//Create Query
			String query1 = 
				"SELECT new.title, new.name, new.year, new.genre"
				+ " FROM ("+username+".album AS al JOIN "+username+".artist AS ar ON al.artist_id = ar.id"
				+ " JOIN "+username+".album_genre AS ge ON ge.album_id = al.id) AS new"
				+ " WHERE lower(new.title) LIKE lower('%"+criteria+"%')"
				+ " OR CAST(new.year as TEXT) LIKE '%"+criteria+"%'"
				+ " OR lower(new.name) LIKE lower('%"+criteria+"%')"
				+ " OR lower(new.genre) LIKE lower('%"+criteria+"%')";
		try {
			Statement stmt = db.createStatement();
			ResultSet results = stmt.executeQuery(query1);
			System.out.format("%37s | %20s | %4s | %s\n", "Title", "Name", "Year", "Genre");
			while (results.next()) {
				String name = results.getString("name"); //Name of Artist
				String title = results.getString("title"); //Name of Album
				String genre = results.getString("genre"); //Name of Genre
				int year = results.getInt("year"); //Year of Albume
			
				// Try to format ouptu a little
				System.out.format("%37s|%20s|%4s|%s\n", title, name, year, genre);
			}
			System.out.println();
		}
		catch (SQLException e) {
			System.out.println("Search error: " + e);
		}
	}
	//Insert Function
	public void insertDB(){
		String artist = "";
		String artist_id ="";
		String album = "";
		String album_year = "";
		String album_id="";
		List<String>album_genres;


		System.out.println("Please enter an Artist");
		artist = System.console().readLine();
		String query1 = 
			"SELECT COUNT(*) AS total"
			+ " FROM "+username+".artist AS ar"
			+ " WHERE ar.name = '"+artist+"'";
		Statement stmt = null;
		ResultSet results = null;
		try{ 
			stmt = db.createStatement();
			results = stmt.executeQuery(query1);
			}
			catch(Exception e){
				System.out.println("Error in Insert " + e);
			}
			finally{
			try{
				//while(results != null){
					if(results.next()){
						int returned = results.getInt("total");
						if(returned > 0){ //First check if artist exists
							//Get artist_id
							String query2 = 
								"SELECT id FROM "
								+username+".artist AS arid"
								+ " WHERE arid.name = '"+artist+"'";

							//Try to get artist ID
							try{
								ResultSet id_result = stmt.executeQuery(query2);
								while(id_result.next()){
									artist_id = Integer.toString(id_result.getInt("id"));
								}
							}
							catch(SQLException e){
								System.out.println("Database Error: " + e);
							}	

							//If yes get album info
							System.out.println("Please Enter the Album Name:\n");
							album = System.console().readLine();
							boolean album_year_good = false;
							
							while(!album_year_good){
								System.out.println("Please Enter the Album Year:\n");
								album_year = System.console().readLine();
								if(album_year.length()!=4){
									System.out.println("Please enter a 4 digit year!\n");
								}
								else
									album_year_good = true;
							}
							//boolean album_genre_good = false;
							
							//while(!album_genre_good){
								System.out.println("Please input album genre(s) in comma seperated list e.g(rock,roll)");
								String album_genres_in = System.console().readLine();
								album_genres = Arrays.asList(album_genres_in.split("\\s*,\\s*"));
							//}
								//TODO: TEST IF GENRES ARE REAL

							for(String genre : album_genres){

								String values = artist_id + ", '" + album + "' , " + album_year;
								String query3 =
									"INSERT INTO "+username+".album"
									+ " (artist_id, title, year)"
									+ " VALUES ("+values+") RETURNING id";
								try{

									ResultSet album_result = stmt.executeQuery(query3);
									while(album_result.next()){
										album_id = Integer.toString(album_result.getInt("id"));
									}
									db.commit();
									System.out.println("Insert into album SUCCESSFUL");
								}
								catch(SQLException e){
									System.out.println("Insert into album UNSUCCESSFUL exception: "+ e);
								}
								String gvalues = album_id + ", '"+genre+"'";
								String query4 =
									"INSERT INTO "+username+".album_genre"
									+ " VALUES ("+gvalues+")";
								try{
									boolean genre_result = stmt.execute(query4);
									db.commit();
									System.out.println("Insert into album_genre SUCCESSFUL");
								}
								catch(SQLException e){
									System.out.println("Insert into album_genre UNSUCCESSFUL exception: "+ e);
								}
							}

							
						}
						else{
							System.out.println("No Artist by that name");
							
						}
						
					//}
				}
			}
			catch(SQLException e){
			System.out.println("End Insert Error: "+ e);
			}
		}
			
	}

	public void modifyDB(){
		String album = "";

		//Input the Album
		System.out.println("Please enter an Album you would like to modify:\n");
		album = System.console().readLine();

		String query = "SELECT j.title, j.name, j.year, j.genre"
			+ " FROM ("+username+".album AS al JOIN "+username+".artist AS ar"
			+ " ON al.artist_id = ar.id JOIN "+username+".album_genre AS ag ON ag.album_id = al.id"
			+ ") AS j WHERE j.title = '"+album+"'";
		
		Statement stmt = null;
		ResultSet results = null;
		try{
			stmt = db.createStatement();
			results = stmt.executeQuery(query);
		}
		catch(Exception e){
			System.out.println("Error: "+e);
		}
		finally{
			try{
				if(results.next()){
					// int num_returned = results.getInt("total");
					// if(num_returned > 0){
						String album_title = results.getString("title");
						String album_name = results.getString("name");
						String album_year = results.getString("year");
						String album_genre = results.getString("genre");

						System.out.printf("Album Info:\n Title: %s, Band Name: %s, Year: %s, Genre: %s", album_title, album_name, album_year, album_genre);
						//MENU OPTIONS
						System.out.println("\nWhat would you like to edit? (Enter a number 1-3)\n1) Title\n 2) Year\n 3) Genre");
						String input = System.console().readLine();

						if(input.equals("1")){
							//Perform Title Update
							System.out.println("What would you like the new album title to be?");
							String new_title = System.console().readLine();
							//Create Query
							String title_query = //"UPDATE ?.album AS al SET title = '?' WHERE al.title = '?'";
							"UPDATE "+username+".album AS al"
								+" SET title = '"+new_title+"'"
								+" WHERE al.title = '"+album_title+"'";
							try{
								// PreparedStatement ps = db.prepareStatement(title_query);
								// ps.setString(1, username);//, new_title, album_title);
								// ps.setString(1, new_title);
								// ps.setString(1, album_title);
								boolean title_results = stmt.execute(title_query);
								System.out.println("Title Update SUCCESSFUL");
							}
							catch(Exception e){
								System.out.println("Title Update UNSUCCESSFUL error "+ e);
							}
						}
						else if(input.equals("2")){
							//Perform Year Update
							System.out.println("What would you like the new album year to be?");
							String new_year = System.console().readLine();
							//Create Query
							String year_query = "UPDATE "+username+".album AS al"
								+" SET year = '"+new_year+"'"
								+" WHERE al.title = '"+album_title+"'";
							try{
								boolean year_results = stmt.execute(year_query);
								System.out.println("Year Update SUCCESSFUL");
							}
							catch(Exception e){
								System.out.println("Year Update UNSUCCESSFUL error "+ e);
							}
						}
						else if(input.equals("3")){
							//Perform Genre Update
							System.out.println("What would you like the new album genre to be?");
							String new_genre = System.console().readLine();
							//Create Query
							String genre_query = "UPDATE "+username+".album AS al"
								+" SET title = '"+new_genre+"'"
								+" WHERE al.title = '"+album_title+"'";
							try{
								boolean genre_results = stmt.execute(genre_query);
								System.out.println("Genre Update SUCCESSFUL");
							}
							catch(Exception e){
								System.out.println("Genre Update UNSUCCESSFUL error "+ e);
							}
						}
						else{
							System.out.println("Please Enter a Number From 1-3");
						}
					}
					else{
						System.out.println("No album by that name.");
					}
					db.commit();
				}
			// }
			catch(Exception e){
				System.out.println("Error: "+e);
			}

		}
		
	}
	public void deleteDB(){
		System.out.println("Which Album would you like to delete?");
		String albumTitle = System.console().readLine();

		Statement stmt = null;
		ResultSet results = null;

		String query = "SELECT id FROM "+username+".album"
			+" AS al WHERE al.title = '"+albumTitle+"'";

		try{
			stmt = db.createStatement();
			results = stmt.executeQuery(query);
		}
		catch(Exception e){
			System.out.println("Delete Error: "+e);
		}finally{
			try{
				if(results.next()){
					int album_id = results.getInt("id");
					//Delete from album genre
					String delete_genre = "DELETE FROM "+username+".album_genre AS ag"
						+" WHERE ag.album_id = '"+album_id+"'";

					try{
						boolean genre_result = stmt.execute(delete_genre);
						System.out.println("Delete genre query SUCCESSFUL");
					}
					catch(Exception e){
						System.out.println("Delete genre query UNSUCCESSFUL error: "+e);
					}
					//Delete from album
					String delete_query = "DELETE FROM "+username+".album AS al"
						+" WHERE al.id = '"+album_id+"'";

					try{
						boolean delete_result = stmt.execute(delete_query);
						System.out.println("Delete Query SUCCESSFUL");

					}
					catch(Exception e){
						System.out.println("Delete Quesry UNSUCCESSFUL error: "+ e);
					}
				}
				else{
					System.out.println("Album does not exist");
				}
				db.commit();
				}
			catch(Exception e){
				System.out.println("Database error: "+e);
			}
		}
	}
	public void keepGoing(){
		System.out.println("\nDo you want to perform another query?\n1) Yes\n2) No\n");
		String result = System.console().readLine();
		if(result.equals("1") || result.toLowerCase().equals("yes") || result.toLowerCase().equals("y")){
			requestCompleted = false;
		}
		else if(result.equals("2") || result.toLowerCase().equals("no") || result.toLowerCase().equals("n")){
			requestCompleted = true;
		}
		else{
			System.out.println("Not an answer!");
			requestCompleted = false;
		}
	}
}

