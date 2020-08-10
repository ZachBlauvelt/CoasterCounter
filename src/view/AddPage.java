package view;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilCalendarModel;

import java.awt.*;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Properties;

import javax.swing.*;

import controller.Features;
import model.ReadOnlyModel;

/**
 * A page where the user can add a new coaster that they have not yet ridden.  This page only displays
 * if the searched coaster has a ride count of 0.  It allows the user to add a rating and a date for
 * the ride.
 */
public class AddPage extends JPanel {
  private String name;
  private String park;
  private ReadOnlyModel model;
  private JButton home;
  private JButton accept;
  private JDatePanelImpl datePanel;
  private JSlider ratingSlider;
  private boolean useDate;

  public AddPage(String name, String park, ReadOnlyModel model) {
    super();
    this.name = name;
    this.park = park;
    this.model = model;
    this.useDate = false;

    this.setBackground(Color.BLACK);
    this.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

    this.home = new JButton("Home");
    c.gridx = 0;
    c.gridy = 0;
    this.add(home, c);

    JPanel nameAndPark = new JPanel();
    nameAndPark.setBackground(Color.BLACK);
    nameAndPark.setLayout(new BorderLayout());
    Font titleFont = new Font("TimesRoman", Font.PLAIN, 40);
    JLabel titleLabel = new JLabel(name);
    titleLabel.setFont(titleFont);
    titleLabel.setForeground(Color.WHITE);
    nameAndPark.add(titleLabel, BorderLayout.NORTH);
    Font parkFont = new Font("TimesRoman", Font.PLAIN, 25);
    JLabel parkLabel = new JLabel(park);
    parkLabel.setFont(parkFont);
    parkLabel.setForeground(Color.WHITE);
    nameAndPark.add(parkLabel, BorderLayout.SOUTH);
    c.gridx = 1;
    c.gridy = 0;
    this.add(nameAndPark, c);

    JPanel slidePanel = new JPanel();
    slidePanel.setLayout(new BorderLayout());
    ratingSlider = new JSlider(0, 100, 0);
    Dictionary<Integer, JLabel> ratings = this.getRatings();
    ratingSlider.setMajorTickSpacing(10);
    ratingSlider.setMinorTickSpacing(1);
    ratingSlider.setLabelTable(ratings);
    ratingSlider.setSnapToTicks(true);
    ratingSlider.setPaintTicks(true);
    ratingSlider.setPaintLabels(true);
    JLabel rating = new JLabel("Rating: 0");
    ratingSlider.addChangeListener(evt -> {
      rating.setText("Rating: " + ((double)ratingSlider.getValue()/(double)10));
    });
    slidePanel.add(rating, BorderLayout.CENTER);
    slidePanel.add(ratingSlider, BorderLayout.SOUTH);
    c.gridx = 0;
    c.gridy = 1;
    this.add(slidePanel, c);

    JPanel dateSelectPanel = new JPanel();
    dateSelectPanel.setLayout(new BorderLayout());
    JLabel prompt = new JLabel("Would you like to add a date?");
    dateSelectPanel.add(prompt, BorderLayout.NORTH);
    JButton yesDate = new JButton("Yes");
    dateSelectPanel.add(yesDate, BorderLayout.WEST);
    JButton noDate = new JButton("No");
    dateSelectPanel.add(noDate, BorderLayout.EAST);
    UtilCalendarModel dateModel = new UtilCalendarModel();
    Properties p = new Properties();
    p.put("text.today", "Today");
    p.put("text.month", "Month");
    p.put("text.year", "Year");
    datePanel = new JDatePanelImpl(dateModel, p);
    datePanel.setVisible(false);
    dateSelectPanel.add(datePanel, BorderLayout.SOUTH);
    yesDate.addActionListener(evt -> {
      datePanel.setVisible(true);
      this.useDate = true;
    });
    noDate.addActionListener(evt -> {
      datePanel.setVisible(false);
      this.useDate = false;
    });
    c.gridx = 1;
    c.gridy = 1;
    this.add(dateSelectPanel, c);

    this.accept = new JButton("Accept");
    c.gridx = 0;
    c.gridy = 2;
    this.add(accept, c);
  }

  public void addFeatures(Features f) {
    this.home.addActionListener(evt -> {
      f.goHome();
    });
    this.accept.addActionListener(evt -> {
      UtilCalendarModel dateModel = (UtilCalendarModel) AddPage.this.datePanel.getModel();
      Date date = new Date(dateModel.getYear() - 1900, dateModel.getMonth(), dateModel.getDay() + 1);
      double rating = (double)AddPage.this.ratingSlider.getValue() / 10.0;
      if (useDate) {
        f.addCoaster(AddPage.this.name, AddPage.this.park, date, rating);
        f.goHome();
      }
      else {
        f.addCoaster(AddPage.this.name, AddPage.this.park, null, rating);
        f.goHome();
      }
    });
  }

  private Dictionary<Integer, JLabel> getRatings() {
    Dictionary<Integer, JLabel> ratings = new Hashtable<>();
    for (int i = 0; i <= 100; i += 10) {
      ratings.put(i, new JLabel(String.valueOf(i/10)));
    }
    return ratings;
  }
}
