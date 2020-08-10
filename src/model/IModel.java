package model;

import java.util.Date;

/**
 * A representation of a model that allows for the data stored within to be updated and removed.
 */
public interface IModel extends ReadOnlyModel {

  /**
   * Adds the specified coaster to the user's ridden table.
   * @param name the coaster name
   * @param park the park that the coaster is at
   * @param ridden the date that the ride occurred on
   * @param rating the rating that the user gave the ride
   */
  void addCoaster(String name, String park, Date ridden, double rating);

  /**
   * Adds a new ride to an already ridden coaster (this is to allow the user to change their most
   * recent ride as well as increment the number of times they have ridden a certain ride).
   * @param name the coaster name
   * @param park the park that the coaster is at
   * @param ridden the date that the ride occurred on
   */
  void addRide(String name, String park, Date ridden);

  /**
   * Tells the model to change the rating of the given coaster to the given rating.
   * @param name the coaster name
   * @param park the park that the coaster is at
   * @param newRating the new rating for the coaster
   */
  void changeRating(String name, String park, double newRating);

  /**
   * Tells the model to remove the given coaster from the user's ridden table.
   * @param name the coaster name
   * @param park the park that the coaster is at
   */
  void removeRide(String name, String park);
}
