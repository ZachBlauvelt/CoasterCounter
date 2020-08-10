package view;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import controller.Features;
import model.ReadOnlyModel;

/**
 * A home page that contains a search bar to allow the user to lookup coasters.  It also contains
 * a JTable that allows the user to view coasters sorted by various attributes that can be selected
 * in a drop-down menu.
 */
public class HomePage extends JPanel {
  private ReadOnlyModel model;
  private JButton enter;
  private JComboBox comboBox;
  private JComboBox orderBy;
  private JComboBox order;
  private JTable onDisplay;

  public HomePage(ReadOnlyModel model) {
    super();
    this.setBackground(Color.BLACK);
    this.model = model;

    comboBox = new JComboBox();
    Object[] elements = model.namesContaining("").toArray();
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        AutoCompleteSupport.install(comboBox, GlazedLists.eventListOf(elements));
      }
    });

    this.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 0;
    this.add(comboBox, c);

    this.enter = new JButton("Search");
    c.gridx = 1;
    c.gridy = 0;
    this.add(enter, c);

    Font titleFont = new Font("TimesRoman", Font.PLAIN, 60);
    c.gridx = 0;
    c.gridy = 1;
    JLabel titleLabel = new JLabel("Coaster Counter");
    titleLabel.setFont(titleFont);
    titleLabel.setForeground(Color.WHITE);
    this.add(titleLabel, c);

    String[] orders = {"Name (Alphabetical)", "Park (Alphabetical)", "Times Ridden", "Rating", "First Ride", "Latest Ride"};
    this.orderBy = new JComboBox(orders);
    orderBy.addActionListener(evt -> {
      HomePage.this.changeTableTitles();
      HomePage.this.updateTableContents();
      HomePage.this.repaint();
    });
    String[] orderings = {"Increasing", "Decreasing"};
    this.order = new JComboBox(orderings);
    order.addActionListener(evt -> {
      HomePage.this.changeTableTitles();
      HomePage.this.updateTableContents();
      HomePage.this.repaint();
    });
    this.setUpOnDisplay();
    JScrollPane scrollPane = new JScrollPane(onDisplay);
    JPanel orderPanel = new JPanel();
    orderPanel.setBackground(Color.BLACK);
    orderPanel.setLayout(new BorderLayout());
    orderPanel.add(orderBy, BorderLayout.WEST);
    orderPanel.add(order, BorderLayout.EAST);
    orderPanel.add(scrollPane, BorderLayout.SOUTH);
    c.gridx = 0;
    c.gridy = 2;
    this.add(orderPanel, c);
  }

  public void addFeatures(Features f) {
    this.enter.addActionListener(evt -> {
      String searchEntry = HomePage.this.comboBox.getSelectedItem().toString();
      if (!searchEntry.contains("  -  ")) {
        if(model.isValidCoaster(searchEntry, null, "Ridden")) {
          f.updatePage(searchEntry, model.getPark(searchEntry));
        }
        else if (model.isValidCoaster(searchEntry, null, "Unridden")) {
          f.addPage(searchEntry, model.getPark(searchEntry));
        }
        else {
          JOptionPane.showMessageDialog(HomePage.this,
                  "The coaster you searched cannot be found.  Please check your spelling " +
                  "or utilize autocomplete.",
                  "Search error",
                  JOptionPane.ERROR_MESSAGE);
        }
      } else {
        String coaster = searchEntry.substring(0, searchEntry.indexOf("  -  "));
        String park = searchEntry.substring(searchEntry.indexOf("  -  ") + 5);
        if (model.isValidCoaster(coaster, park, "Ridden")) {
          f.updatePage(coaster, park);
        }
        else if (model.isValidCoaster(coaster, park, "Unridden")) {
            f.addPage(coaster, park);
        }
        else {
          JOptionPane.showMessageDialog(HomePage.this,
                  "The coaster you searched cannot be found.  Please check your spelling " +
                          "or utilize autocomplete.",
                  "Search error",
                  JOptionPane.ERROR_MESSAGE);
        }
      }
    });
  }

  private void setUpOnDisplay() {
    String[] colNames = {"Name", "Park"};
    onDisplay = new JTable(model.alphabetical("Increasing", model.getCreditCount()), colNames);
    onDisplay.setPreferredScrollableViewportSize(new Dimension(600, 500));
    onDisplay.setFillsViewportHeight(true);
    DefaultTableModel tableModel = new DefaultTableModel(model.alphabetical("Increasing", model.getCreditCount()), colNames) {
      @Override
      public boolean isCellEditable(int row, int column) {
        //all cells false
        return false;
      }
    };
    onDisplay.setModel(tableModel);
    this.repaint();
  }

  private void changeTableTitles() {
    String selected = (String)this.orderBy.getSelectedItem();
    switch(selected) {
      case "Name (Alphabetical)":
      case "Park (Alphabetical)":
        onDisplay.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("Name");
        onDisplay.getTableHeader().getColumnModel().getColumn(1).setHeaderValue("Park");
        break;
      case "Times Ridden":
        onDisplay.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("Name");
        onDisplay.getTableHeader().getColumnModel().getColumn(1).setHeaderValue("Times Ridden");
        break;
      case "Rating":
        onDisplay.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("Name");
        onDisplay.getTableHeader().getColumnModel().getColumn(1).setHeaderValue("Rating");
        break;
      default:
        onDisplay.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("Name");
        onDisplay.getTableHeader().getColumnModel().getColumn(1).setHeaderValue("Date");
    }
  }

  private void updateTableContents() {
    String selected = (String)this.orderBy.getSelectedItem();
    Object[][] data;
    switch(selected) {
      case "Name (Alphabetical)":
        data = model.alphabetical((String)this.order.getSelectedItem(),model.getCreditCount());
        break;
      case "Park (Alphabetical)":
        data = model.parkOrder((String)this.order.getSelectedItem(),model.getCreditCount());
        break;
      case "Times Ridden":
        data = model.mostRidden((String)this.order.getSelectedItem(),model.getCreditCount());
        break;
      case "Rating":
        data = model.topRated((String)this.order.getSelectedItem(),model.getCreditCount());
        break;
      case "First Ride":
        data = model.firstRide((String)this.order.getSelectedItem(),model.getCreditCount());
        if (data == null) {
          JOptionPane.showMessageDialog(onDisplay,
                  "No dates are present on your entries.  Please add some before viewing this tab.",
                  "Action Failed",
                  JOptionPane.WARNING_MESSAGE);
        }
        break;
      default:
        data = model.latestRide((String)this.order.getSelectedItem(),model.getCreditCount());
        if (data == null) {
          JOptionPane.showMessageDialog(onDisplay,
                  "No dates are present on your entries.  Please add some before viewing this tab.",
                  "Action Failed",
                  JOptionPane.WARNING_MESSAGE);
        }
    }

    if (data != null) {
      for (int row = 0; row < data.length; row++) {
        onDisplay.setValueAt(data[row][0], row, 0);
        onDisplay.setValueAt(data[row][1], row, 1);
      }
    }
  }
}
