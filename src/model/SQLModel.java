package model;
import java.sql.*;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A model implementation that uses a MySQL database to store and access the user's ride counts.
 */
public class SQLModel implements IModel {

  @Override
  public int getCreditCount() {
    try {
      int count = -1;
      Statement stmt = this.connect();
      String query = "select count(*) as total from coasters;";
      ResultSet rs = stmt.executeQuery(query);
      rs.next();
        count = rs.getInt(1);
      return count;
    } catch(SQLException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public Object[][] alphabetical(String order, int num) {
    String orderBy = "DESC";
    if (order.equals("Increasing")) {
      orderBy = "ASC";
    }
    try {
      int i = 0;
      Object[][] rides = new Object[num][2];
      Statement stmt = this.connect();
      String query = "select name, park from coasters " +
              "ORDER BY name " + orderBy + ";";
      ResultSet rs = stmt.executeQuery(query);
      while (rs.next() && i < num) {
        rides[i] = new Object[]{rs.getString("name"),
                rs.getString("park")};
        i++;
      }
      return rides;
    } catch (SQLException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public Object[][] parkOrder(String order, int num) {
    String orderBy = "DESC";
    if (order.equals("Increasing")) {
      orderBy = "ASC";
    }
    try {
      int i = 0;
      Object[][] rides = new Object[num][2];
      Statement stmt = this.connect();
      String query = "select name, park from coasters " +
              "ORDER BY park " + orderBy + ";";
      ResultSet rs = stmt.executeQuery(query);
      while (rs.next() && i < num) {
        rides[i] = new Object[]{rs.getString("name"),
                rs.getString("park")};
        i++;
      }
      return rides;
    } catch (SQLException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public Object[][] topRated(String order, int num) {
    String orderBy = "DESC";
    if (order.equals("Increasing")) {
      orderBy = "ASC";
    }
    try {
      int i = 0;
      Object[][] ratings = new Object[num][2];
      Statement stmt = this.connect();
      String query = "select name, park, personal_rating from coasters " +
              "ORDER BY personal_rating " + orderBy + ";";
      ResultSet rs = stmt.executeQuery(query);
      while (rs.next() && i < num) {
        ratings[i] = new Object[]{rs.getString("name") + "  -  " +
                rs.getString("park"), rs.getFloat("personal_rating")};
        i++;
      }
      return ratings;
    } catch (SQLException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public Object[][] mostRidden(String order, int num) {
    String orderBy = "DESC";
    if (order.equals("Increasing")) {
      orderBy = "ASC";
    }
    try {
      int i = 0;
      Object[][] rides = new Object[num][2];
      Statement stmt = this.connect();
      String query = "select name, park, times_ridden from coasters " +
              "ORDER BY times_ridden " + orderBy + ";";
      ResultSet rs = stmt.executeQuery(query);
      while (rs.next() && i < num) {
        rides[i] = new Object[]{rs.getString("name") + "  -  " +
                rs.getString("park"), rs.getInt("times_ridden")};
        i++;
      }
      return rides;
    } catch (SQLException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public Object[][] firstRide(String order, int num) {
    String orderBy = "DESC";
    if (order.equals("Increasing")) {
      orderBy = "ASC";
    }
    try {
      int i = 0;
      Object[][] rides = new Object[num][2];
      Statement stmt = this.connect();
      String query = "select name, park, first_ride from coasters " +
              "ORDER BY first_ride " + orderBy + ";";
      ResultSet rs = stmt.executeQuery(query);
      while (rs.next() && i < num) {
        rides[i] = new Object[]{rs.getString("name") + "  -  " +
                rs.getString("park"),
                rs.getDate("first_ride") == null ? "No date added" : rs.getDate("first_ride").toString()};
        i++;
      }
      return rides;
    } catch (SQLException ex) {
        throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public Object[][] latestRide(String order, int num) {
    String orderBy = "DESC";
    if (order.equals("Increasing")) {
      orderBy = "ASC";
    }
    try {
      int i = 0;
      Object[][] rides = new Object[num][2];
      Statement stmt = this.connect();
      String query = "select name, park, latest_ride from coasters " +
              "ORDER BY latest_ride " + orderBy + ";";
      ResultSet rs = stmt.executeQuery(query);
      while (rs.next() && i < num) {
        rides[i] = new Object[]{rs.getString("name") + "  -  " +
                rs.getString("park"),
                rs.getDate("latest_ride") == null ? "No date added" : rs.getDate("latest_ride").toString()};
        i++;
      }
      return rides;
    } catch (SQLException ex) {
        throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public boolean isValidCoaster(String name, String park, String status) {
    try {
      Statement stmt = this.connect();
      String query;
      String table = "validCoasters";
      if (status.equals("Ridden")) {
        table = "coasters";
      }
      if (park == null) {
        query = "select name, park from " + table +
                " WHERE name = '" + this.formatName(name) + "';";
      }
      else {
        query = "select name, park from " + table +
                " WHERE name = '" + this.formatName(name) + "' AND park = '" + this.formatName(park) + "';";
      }
      ResultSet rs = stmt.executeQuery(query);
      int rowCount = 0;
      while ( rs.next() )
      {
        // Process the row.
        rowCount++;
      }
      return rowCount == 1;
    } catch(SQLException ex){
        throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public String getPark(String coaster) {
    try {
      Statement stmt = this.connect();
      String query = "select park from validCoasters " +
              "WHERE name = '" + this.formatName(coaster) + "';";
      ResultSet rs = stmt.executeQuery(query);
      rs.next();
      return rs.getString("park");
    } catch(SQLException ex){
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public List<String> namesContaining(String s) {
    try {
      Statement stmt = this.connect();
      String query;
      ArrayList<String> result = new ArrayList<>();
        query = "select name, park from validCoasters;";
        ResultSet rs = stmt.executeQuery(query);
        while(rs.next()) {
          result.add(rs.getString("name") + "  -  " + rs.getString("park"));
        }
      return result;
    } catch(SQLException ex){
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public String getTimesRidden(String name, String park) {
    try {
      Statement stmt = this.connect();
      String query = "select times_ridden from coasters " +
              "WHERE name = '" + this.formatName(name) + "' AND " +
              "park = '" + this.formatName(park) + "';";
      ResultSet rs = stmt.executeQuery(query);
      rs.next();
      return rs.getString("times_ridden");
    } catch(SQLException ex){
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public double getRating(String name, String park) {
    try {
      Statement stmt = this.connect();
      String query = "select personal_rating from coasters " +
              "WHERE name = '" + this.formatName(name) + "' AND " +
              "park = '" + this.formatName(park) + "';";
      ResultSet rs = stmt.executeQuery(query);
      rs.next();
      return rs.getDouble("personal_rating");
    } catch(SQLException ex){
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public String[] getDates(String name, String park) {
    try {
      String[] dates = new String[2];
      Statement stmt = this.connect();
      String query = "select first_ride, latest_ride from coasters " +
              "WHERE name = '" + this.formatName(name) + "' AND " +
              "park = '" + this.formatName(park) + "';";
      ResultSet rs = stmt.executeQuery(query);
      rs.next();
      Date first = rs.getDate("first_ride");
      Date latest = rs.getDate("latest_ride");
      if (first == null) {
        dates[0] = "?";
      } else {
        dates[0] = first.toString();
      }
      if (latest == null) {
        dates[1] = "?";
      } else {
        dates[1] = latest.toString();
      }
      return dates;
    } catch(SQLException ex){
      throw new IllegalArgumentException(ex.getMessage());
    }
  }


  private Statement connect() throws SQLException {
    Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/coastercounter?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
            "myuser", "bruins99");
    Statement stmt = conn.createStatement();
    return stmt;
  }

  @Override
  public void addCoaster(String name, String park, Date ridden, double rating) {
    try {
      Statement stmt = this.connect();
      Format formatter = new SimpleDateFormat("yyyy-MM-dd");
      String query;
      if (ridden == null) {
        query = "INSERT INTO coasters (name, park, times_ridden, first_ride, latest_ride, personal_rating) " +
                "VALUES ('" + this.formatName(name) + "', '" + this.formatName(park) + "', " + 1 + ", NULL, NULL, " + rating + ");";
      } else {
        String s = formatter.format(ridden);
        query = "INSERT INTO coasters (name, park, times_ridden, first_ride, latest_ride, personal_rating) " +
                "VALUES ('" + this.formatName(name) + "', '" + this.formatName(park) + "', " + 1 + ", '" + s + "', '" +
                s + "', " + rating + ");";
      }
      stmt.execute(query);
    } catch(SQLException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public void addRide(String name, String park, Date ridden) {
    try {
      String date;
      Format formatter = new SimpleDateFormat("yyyy-MM-dd");
      if (this.getDates(name, park)[1].equals("?")) {
        if (ridden == null) {
          date = "?";
        } else {
          date = formatter.format(ridden);
        }
      }
      else {
        SimpleDateFormat simpFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = simpFormatter.parse(this.getDates(name, park)[1]);
        if (ridden == null) {
          date = this.getDates(name, park)[1];
        } else if (date1.compareTo(ridden) < 0) {
          date = formatter.format(ridden);
        } else {
          date = this.getDates(name, park)[1];
        }
      }
      Statement stmt = this.connect();
      String query = "UPDATE coasters SET latest_ride = '" + date +
             "', times_ridden = " + (Double.parseDouble(this.getTimesRidden(name, park)) + 1) +
              " WHERE name = '" + this.formatName(name) + "' AND park = '" + this.formatName(park) + "';";
       stmt.execute(query);
    } catch(SQLException | ParseException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public void changeRating(String name, String park, double newRating) {
    try {
      Statement stmt = this.connect();
      String query = "UPDATE coasters SET personal_rating = " + newRating +
                " WHERE name = '" + this.formatName(name) + "' AND park = '" + this.formatName(park) + "';";
      stmt.execute(query);
    } catch(SQLException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  @Override
  public void removeRide(String name, String park) {
    try {
      Statement stmt = this.connect();
      String query = "DELETE FROM coasters" +
              " WHERE name = '" + this.formatName(name) + "' AND park = '" + this.formatName(park) + "';";
      stmt.execute(query);
    } catch(SQLException ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  private String formatName(String name) {
    StringBuilder ret = new StringBuilder();
    for (int i = 0; i < name.length(); i++) {
      if (name.charAt(i) == '\'') {
        ret.append('\'');
      }
      ret.append(name.charAt(i));
    }
    return ret.toString();
  }

}
