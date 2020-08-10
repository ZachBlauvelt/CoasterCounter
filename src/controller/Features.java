package controller;

import java.util.Date;

/**
 * An interface that is meant to be used by the View in order to allow for Listener pattern behavior
 * between the controller and the GUI.
 */
public interface Features {

  /**
   * Notifies the View that a request has been made for it to return to its home page.
   */
  public void goHome();

  /**
   * Tells the view to display a page where the user can add a coaster that they have not yet ridden.
   * @param name the coaster name
   * @param park the park that the coaster is at
   */
  public void addPage(String name, String park);

  /**
   * Tells the model to add the coaster with the given information to the user's ridden table.
   * @param name the coaster name
   * @param park the park that the coaster is at
   * @param date the date that the ride occurred on
   * @param rating the rating that the user gave the ride
   */
  void addCoaster(String name, String park, Date date, double rating);

  /**
   * Tells the View to display a page where the user can update the information for the given
   * coaster that they have already ridden.
   * @param name the coaster name
   * @param park the park that the coaster is at
   */
  void updatePage(String name, String park);

  /**
   * Tells the model to change the rating of the given coaster to the given rating.
   * @param name the coaster name
   * @param park the park that the coaster is at
   * @param rating the new rating for the coaster
   */
  void updateRating(String name, String park, double rating);

  /**
   * Adds a new ride to an already ridden coaster (this is to allow the user to change their most
   * recent ride as well as increment the number of times they have ridden a certain ride).
   * @param name the coaster name
   * @param park the park that the coaster is at
   * @param ridden the date that the ride occurred on
   */
  void addRide(String name, String park, Date ridden);

  /**
   * Tells the model to remove the given coaster from the user's ridden table.
   * @param name the coaster name
   * @param park the park that the coaster is at
   */
  void removeRide(String name, String park);
}
