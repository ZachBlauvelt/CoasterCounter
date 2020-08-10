package model;


import java.util.List;

public interface ReadOnlyModel {

  /**
   * Returns the number of unique coasters ridden.
   */
   int getCreditCount();

  /**
   * Returns an array of coaster-park pairs sorted alphabetically by coaster name.
   * @param order whether the coasters should be sorted in ascending or descending order
   * @param num the number of coasters to be included in the array
   */
   Object[][] alphabetical(String order, int num);

  /**
   * Returns an array of coaster-park pairs sorted alphabetically by park name.
   * @param order whether the coasters should be sorted in ascending or descending order
   * @param num the number of coasters to be included in the array
   */
   Object[][] parkOrder(String order, int num);

  /**
   * Returns an array of coaster-rating pairs sorted numerically by rating.
   * @param order whether the coasters should be sorted in ascending or descending order
   * @param num the number of coasters to be included in the array
   */
   Object[][] topRated(String order, int num);

  /**
   * Returns an array of coaster-ride count pairs sorted numerically by ride count.
   * @param order whether the coasters should be sorted in ascending or descending order
   * @param num the number of coasters to be included in the array
   */
  Object[][] mostRidden(String order, int num);

  /**
   * Returns an array of coaster-first ride pairs sorted numerically by first ride date.
   * @param order whether the coasters should be sorted in ascending or descending order
   * @param num the number of coasters to be included in the array
   */
  public Object[][] firstRide(String order, int num);

  /**
   * Returns an array of coaster-latest ride pairs sorted numerically by latest ride date.
   * @param order whether the coasters should be sorted in ascending or descending order
   * @param num the number of coasters to be included in the array
   */
   Object[][] latestRide(String order, int num);

  /**
   * Returns whether the given coaster name/park pair exists in the database of known coasters.
   * @param name the coaster name
   * @param park the coaster park
   * @param status the status of the coaster
   */
  boolean isValidCoaster(String name, String park, String status);

  /**
   * Returns the park for the given coaster name. **Must be called after isValidCoaster in order to
   * ensure the name is legitimate and has no duplicate names.**
   * @param coaster the name of the coaster
   */
  String getPark(String coaster);

  /**
   * Returns a list of coaster - park pairs starting with the given string.
   * @param s the inputted prefix
   */
  List<String> namesContaining(String s);

  /**
   * Returns the number of recorded rides for the given name/park combo.
   * @param name the coaster name
   * @param park the coaster park
   */
  String getTimesRidden(String name, String park);

  /**
   * Returns the rating for the given name/park combo.
   * @param name the coaster name
   * @param park the coaster park
   */
  double getRating(String name, String park);

  /**
   * Returns the first and latest ride dates for the given name/park combo. (Non-existent dates
   * return as "?")
   * @param name the coaster name
   * @param park the coaster park
   */
  String[] getDates(String name, String park);
}
