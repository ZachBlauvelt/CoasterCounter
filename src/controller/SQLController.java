package controller;

import java.util.Date;

import model.IModel;
import view.IView;

public class SQLController implements IController, Features {
  private IModel model;
  private IView view;

  public SQLController(IModel model, IView view) {
    this.model = model;
    this.view = view;
  }

  @Override
  public void go() {
    StartTasks.locateOrCreateTable();
  }

  @Override
  public void goHome() {
    view.goHome();
  }

  @Override
  public void addPage(String name, String park) {
    view.goToAddPage(name, park);
  }

  @Override
  public void addCoaster(String name, String park, Date date, double rating) {
    model.addCoaster(name, park, date, rating);
  }

  @Override
  public void updatePage(String name, String park) {
    view.goToUpdatePage(name, park);
  }

  @Override
  public void updateRating(String name, String park, double rating) {
    this.model.changeRating(name, park, rating);
  }

  @Override
  public void addRide(String name, String park, Date ridden) {
    this.model.addRide(name, park, ridden);
  }

  @Override
  public void removeRide(String name, String park) {
    this.model.removeRide(name, park);
  }
}
