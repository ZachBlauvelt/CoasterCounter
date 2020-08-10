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
 * A page that allows the user to update a previously ridden ride.  This include the functionality to
 * change the rating, add a new ride with a new date, and even remove the ride completely from the
 * ridden rides (the user will be prompted to confirm that they want to remove to avoid unwanted
 * deletions.
 */
public class UpdatePage extends JPanel {
  private String name;
  private String park;
  private ReadOnlyModel model;
  private JButton home;
  private JButton delete;
  private JDatePanelImpl datePanel;
  private JSlider ratingSlider;
  private JButton add;
  private JButton change;
  private boolean useDate;
  private int numRides;

  public UpdatePage(String name, String park, ReadOnlyModel model) {
    this.name = name;
    this.park = park;
    this.model = model;
    this.useDate = false;

    this.setBackground(Color.BLACK);
    this.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(Color.BLACK);
    buttonPanel.setLayout(new BorderLayout());
    this.home = new JButton("Home");
    buttonPanel.add(home, BorderLayout.NORTH);
    this.delete = new JButton("Delete");
    delete.setForeground(Color.RED);
    buttonPanel.add(delete, BorderLayout.SOUTH);
    c.gridx = 0;
    c.gridy = 0;
    this.add(buttonPanel, c);

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

    Font bodyFont = new Font("TimesRoman", Font.PLAIN, 20);
    numRides = Integer.parseInt(model.getTimesRidden(name, park));
    JLabel timesRidden = new JLabel("Times Ridden: " + numRides);
    timesRidden.setFont(bodyFont);
    timesRidden.setForeground(Color.WHITE);
    c.gridx = 0;
    c.gridy = 1;
    this.add(timesRidden, c);

    JPanel addRidePanel = new JPanel();
    addRidePanel.setBackground(Color.BLACK);
    addRidePanel.setLayout(new BorderLayout());
    JButton yes = new JButton("Yes");
    JLabel prompt = new JLabel("  Add a ride?  ");
    prompt.setFont(bodyFont);
    prompt.setForeground(Color.WHITE);
    JButton no = new JButton("No");
    addRidePanel.add(yes, BorderLayout.WEST);
    addRidePanel.add(prompt, BorderLayout.CENTER);
    addRidePanel.add(no, BorderLayout.EAST);
    c.gridx = 1;
    c.gridy = 1;
    this.add(addRidePanel, c);

    JPanel rideAdder = new JPanel();
    rideAdder.setBackground(Color.BLACK);
    rideAdder.setLayout(new BorderLayout());
    JPanel dateSelectPanel = new JPanel();
    dateSelectPanel.setLayout(new BorderLayout());
    JLabel datePrompt = new JLabel("Would you like to add a date?");
    dateSelectPanel.add(datePrompt, BorderLayout.NORTH);
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
    rideAdder.add(dateSelectPanel, BorderLayout.WEST);
    yesDate.addActionListener(evt -> {
      datePanel.setVisible(true);
      this.useDate = true;
    });
    noDate.addActionListener(evt -> {
      datePanel.setVisible(false);
      this.useDate = false;
    });
    c.gridx = 0;
    c.gridy = 2;
    rideAdder.setVisible(false);
    this.add(rideAdder, c);

    JPanel adderButtons = new JPanel();
    adderButtons.setBackground(Color.BLACK);
    adderButtons.setLayout(new BorderLayout());
    add = new JButton("Add");
    add.addActionListener(evt -> {
      numRides++;
      timesRidden.setText("Times Ridden: " + numRides);
    });
    JButton cancel = new JButton("Cancel");
    adderButtons.add(add, BorderLayout.WEST);
    adderButtons.add(cancel, BorderLayout.EAST);
    c.gridx = 1;
    c.gridy = 2;
    adderButtons.setVisible(false);
    this.add(adderButtons, c);

    yes.addActionListener(evt -> {
      rideAdder.setVisible(true);
      adderButtons.setVisible(true);
    });
    no.addActionListener(evt -> {
      rideAdder.setVisible(false);
      adderButtons.setVisible(false);
    });
    cancel.addActionListener(evt -> {
      rideAdder.setVisible(false);
      adderButtons.setVisible(false);
    });

    JLabel rating = new JLabel("Personal Rating: " + model.getRating(name, park));
    rating.setFont(bodyFont);
    rating.setForeground(Color.WHITE);
    c.gridx = 0;
    c.gridy = 3;
    this.add(rating, c);

    JPanel ratingPanel = new JPanel();
    ratingPanel.setBackground(Color.BLACK);
    ratingPanel.setLayout(new BorderLayout());
    JButton rYes = new JButton("Yes");
    JLabel rPrompt = new JLabel("  Change rating?  ");
    rPrompt.setFont(bodyFont);
    rPrompt.setForeground(Color.WHITE);
    JButton rNo = new JButton("No");
    ratingPanel.add(rYes, BorderLayout.WEST);
    ratingPanel.add(rPrompt, BorderLayout.CENTER);
    ratingPanel.add(rNo, BorderLayout.EAST);
    c.gridx = 1;
    c.gridy = 3;
    this.add(ratingPanel, c);

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
    JLabel rating2 = new JLabel("Rating: 0");
    ratingSlider.addChangeListener(evt -> {
      rating2.setText("Rating: " + ((double)ratingSlider.getValue()/(double)10));
    });
    slidePanel.add(rating2, BorderLayout.CENTER);
    slidePanel.add(ratingSlider, BorderLayout.SOUTH);
    c.gridx = 0;
    c.gridy = 4;
    slidePanel.setVisible(false);
    this.add(slidePanel, c);

    JPanel rateButtons = new JPanel();
    rateButtons.setBackground(Color.BLACK);
    rateButtons.setLayout(new BorderLayout());
    change = new JButton("Change");
    change.addActionListener(evt -> {
      rating.setText("Personal Rating: " + (double)this.ratingSlider.getValue() / 10.0);
      this.repaint();
    });
    JButton cancel2 = new JButton("Cancel");
    rateButtons.add(change, BorderLayout.WEST);
    rateButtons.add(cancel2, BorderLayout.EAST);
    c.gridx = 1;
    c.gridy = 4;
    rateButtons.setVisible(false);
    this.add(rateButtons, c);

    rYes.addActionListener(evt -> {
      rateButtons.setVisible(true);
      slidePanel.setVisible(true);
    });
    rNo.addActionListener(evt -> {
      slidePanel.setVisible(false);
      rateButtons.setVisible(false);
    });
    cancel2.addActionListener(evt -> {
      ratingSlider.setValue(0);
      slidePanel.setVisible(false);
      rateButtons.setVisible(false);
    });

    String[] dates = model.getDates(name, park);
    JLabel firstRide = new JLabel("First Ride: " + dates[0]);
    firstRide.setFont(bodyFont);
    firstRide.setForeground(Color.WHITE);
    c.gridx = 0;
    c.gridy = 5;
    this.add(firstRide, c);
    JLabel latestRide = new JLabel("  Latest Ride: " + dates[1]);
    latestRide.setFont(bodyFont);
    latestRide.setForeground(Color.WHITE);
    c.gridx = 1;
    c.gridy = 5;
    this.add(latestRide, c);
  }

  public void addFeatures(Features f) {
    this.home.addActionListener(evt -> {
      f.goHome();
    });

    this.add.addActionListener(evt -> {
      UtilCalendarModel dateModel = (UtilCalendarModel) UpdatePage.this.datePanel.getModel();
      Date date = new Date(dateModel.getYear() - 1900, dateModel.getMonth(), dateModel.getDay() + 1);
      if (useDate) {
        f.addRide(UpdatePage.this.name, UpdatePage.this.park, date);
      } else {
        f.addRide(UpdatePage.this.name, UpdatePage.this.park, null);
      }
    });

    this.change.addActionListener(evt -> {
      double rating = (double)UpdatePage.this.ratingSlider.getValue() / 10.0;
      f.updateRating(UpdatePage.this.name, UpdatePage.this.park, rating);
      UpdatePage.this.repaint();
    });

    this.delete.addActionListener(evt -> {
      int choice = JOptionPane.showOptionDialog(this, "Are you sure you"
                      + " want to delete this entry? All information will be lost.", "Delete", JOptionPane.YES_NO_OPTION,
              JOptionPane.QUESTION_MESSAGE,
              null, null, JOptionPane.YES_OPTION);
      if (choice == JOptionPane.YES_OPTION) {
        f.removeRide(UpdatePage.this.name, UpdatePage.this.park);
        f.addPage(UpdatePage.this.name, UpdatePage.this.park);
      } else {
        // do noting as the user canceled the delete action
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
