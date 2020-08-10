package controller;

/**
 * A representation of the controller for the program.
 */
public interface IController {
  /**
   * Sets the program in motion, including ensuring that all needed databases and tables are created
   * and accessible.
   */
  public void go();
}
