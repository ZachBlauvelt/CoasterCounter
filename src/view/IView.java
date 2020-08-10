package view;

import controller.Features;

/**
 * A view for the user to see the data stored within the table.
 */
public interface IView {
  /**
   * Returns the view to a home page (where no editing can occur)
   */
  void goHome();

  /**
   * Goes to a page allowing the user to add rides on a given coaster.
   * @param name the coaster name
   * @param park the coaster park
   */
  void goToAddPage(String name, String park);

  /**
   * Adds a Features listener object to this View.
   * @param f the features object
   */
  void addFeatures(Features f);

  /**
   * Tells the view to go to the update page for the given ride and park.
   * @param name the coaster name
   * @param park the coaster park
   */
  void goToUpdatePage(String name, String park);
}
