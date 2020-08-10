package view;

import java.awt.*;

import javax.swing.*;

import controller.Features;
import model.ReadOnlyModel;

/**
 * A paged GUI view that allows the user to view desired info on the home page, add coasters on an
 * add page, and update already ridden coasters on an update page.
 */
public class GraphicalView extends JFrame implements IView {
  private ReadOnlyModel model;
  private JPanel currentPage;
  private Features features;

  public GraphicalView(ReadOnlyModel model) {
    super();
    this.setTitle("Coaster Counter");
    Toolkit tk = Toolkit.getDefaultToolkit();
    int xSize = ((int) tk.getScreenSize().getWidth());
    int ySize = ((int) tk.getScreenSize().getHeight());
    this.setPreferredSize(new Dimension(xSize,ySize));
    this.setResizable(false);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.model = model;

    this.pack();
  }

  @Override
  public void goHome() {
    this.currentPage = new HomePage(this.model);
    HomePage home = (HomePage)this.currentPage;
    home.addFeatures(this.features);
    this.getContentPane().removeAll();
    this.getContentPane().add(this.currentPage);
    this.revalidate();
    this.repaint();
  }

  @Override
  public void goToUpdatePage(String name, String park) {
    this.currentPage = new UpdatePage(name, park, this.model);
    UpdatePage update = (UpdatePage) this.currentPage;
    update.addFeatures(this.features);
    this.getContentPane().removeAll();
    this.getContentPane().add(this.currentPage);
    this.revalidate();
    this.repaint();
  }

  @Override
  public void goToAddPage(String name, String park) {
    this.currentPage = new AddPage(name, park, this.model);
    AddPage add = (AddPage)this.currentPage;
    add.addFeatures(this.features);
    this.getContentPane().removeAll();
    this.getContentPane().add(this.currentPage);
    this.revalidate();
    this.repaint();
  }

  public void addFeatures(Features f) {
    this.features = f;
    this.currentPage = new HomePage(this.model);
    HomePage home = (HomePage)this.currentPage;
    home.addFeatures(f);
    this.goHome();
  }
}
