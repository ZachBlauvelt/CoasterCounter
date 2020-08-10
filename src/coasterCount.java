import java.sql.Date;

import controller.Features;
import controller.SQLController;
import controller.StartTasks;
import model.SQLModel;
import view.GraphicalView;

public class coasterCount {
  public static void main(String[] args) {
    StartTasks.locateOrCreateTable();
    SQLModel model = new SQLModel();
    GraphicalView view = new GraphicalView(model);
    Features f = new SQLController(model, view);
    view.addFeatures(f);
    view.setVisible(true);
  }
}
