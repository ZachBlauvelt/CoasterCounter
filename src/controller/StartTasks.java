package controller;

import java.sql.*;

/**
 * A class to perform the necessary tasks for the program to create a table if needed and establish
 * a connection to the table.
 */
public class StartTasks {
  /**
   * Establishes a connection to the database, then attempts to create the necessary table if it does not
   * yet exist.
   */
  public static void locateOrCreateTable() {
    try (
            // Step 1: Allocate a database 'Connection' object
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/coastercounter?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                    "myuser", "bruins99");    // for MySQL only

            // Step 2: Allocate a 'Statement' object in the Connection
            Statement stmt = conn.createStatement();
    ) {
      String tableCreate = "CREATE TABLE IF NOT EXISTS coasters (\n"
              + "   name text NOT NULL, \n"
              + "   park text NOT NULL, \n"
              + "   times_ridden integer NOT NULL, \n"
              + "   first_ride date, \n"
              + "   latest_ride date, \n"
              + "   personal_rating float\n"
              + ");";
      //System.out.println("The SQL statement is: " + tableCreate + "\n");   //Echo for debugging
      stmt.execute(tableCreate);

    } catch(SQLException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }
}
