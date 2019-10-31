package tradestock.view.helperclass;

import java.awt.event.ActionListener;
import java.time.LocalDate;

import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

import tradestock.view.advancedview.IAdvancedView;
import tradestock.view.buttononlyview.ButtonOnly;
import tradestock.view.buttononlyview.MainView;
import tradestock.view.comboboxview.ComboBoxView;
import tradestock.view.textfieldview.WithTextField;

/**
 * This is a helper class.
 */
public class HelperClass {

  /**
   * This class helps to show user valid days when user choose a date from combobox.
   * @param year the year user selected.
   * @param month the month user selected.
   * @param day the day will be showed.
   */
  static public void setDay( JComboBox year,JComboBox month, JComboBox day) {
    String numStringOfMonth = month.getSelectedItem().toString();
    int numOfMonth = Integer.parseInt(numStringOfMonth);
    DefaultComboBoxModel model = (DefaultComboBoxModel) day.getModel();
    if (numOfMonth == 1 || numOfMonth == 3 || numOfMonth == 5 || numOfMonth == 7 || numOfMonth == 8
        || numOfMonth == 10 || numOfMonth == 12) {
      String[] days = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13"
          , "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27"
          , "28", "29", "30", "31"};
      model.removeAllElements();
      for (int i = 0; i < 31; i++) {
        model.addElement(days[i]);
      }
    }
    else if (numOfMonth == 2) {
      String numStringOfYear = year.getSelectedItem().toString();
      int numOfYear = Integer.parseInt(numStringOfYear);
      if ((numOfYear % 100 == 0 && numOfYear % 400 == 0) || (numOfYear % 100 != 0 && numOfYear
          % 4 == 0)) {
        String[] days1 = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12",
            "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27"
            , "28", "29"};
        model.removeAllElements();
        for (int i = 0; i < 29; i++) {
          model.addElement(days1[i]);
        }
      }
      else {
        String[] days2 = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12",
            "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27"
            , "28"};
        model.removeAllElements();
        for (int i = 0; i < 28; i++) {
          model.addElement(days2[i]);
        }
      }
    }
    else {
      String[] days3 = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13"
          , "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27"
          , "28", "29", "30"};
      model.removeAllElements();
      for (int i = 0; i < 30; i++) {
        model.addElement(days3[i]);
      }
    }
  }

  /**
   * Append the information user typed with a uniform format.
   * @param stockSymbolText stock symbol user typed.
   * @param moneyText money user typed.
   * @param year the year user selected.
   * @param month the month user selected.
   * @param day the day user selected.
   * @param portfolioNameText portfolioName user typed.
   * @return all information user typed separate by new line.
   */
  static public String getInput(JTextField stockSymbolText, JTextField moneyText, JComboBox year
      , JComboBox month, JComboBox day, JTextField portfolioNameText) {
    StringBuilder sb = new StringBuilder();
    sb.append(stockSymbolText.getText());
    sb.append("\n");
    sb.append(moneyText.getText());
    sb.append("\n");
    sb.append(year.getSelectedItem().toString());
    sb.append("-");
    sb.append(month.getSelectedItem().toString());
    sb.append("-");
    sb.append(day.getSelectedItem().toString());
    sb.append("\n");
    sb.append(portfolioNameText.getText());
    sb.append("\n");
    return sb.toString();
  }

  /**
   * Append the information user typed with a uniform format.
   * @param year the year user selected.
   * @param month the month user selected.
   * @param day the day user selected.
   * @param portfolioNameText portfolioName user typed.
   * @return all information user typed separate by new line.
   */
  static public String getInput(JTextField portfolioNameText, JComboBox year, JComboBox month,
      JComboBox day) {
    StringBuilder sb = new StringBuilder();
    sb.append(portfolioNameText.getText());
    sb.append("\n");
    sb.append(year.getSelectedItem().toString());
    sb.append("-");
    sb.append(month.getSelectedItem().toString());
    sb.append("-");
    sb.append(day.getSelectedItem().toString());
    return sb.toString();
  }

  /**
   * Get the day with 31 days.
   * @return a string array with 31 days.
   */
  static public String[] getDefaultDay() {
    String[] days = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13"
        , "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27"
        , "28", "29", "30", "31"};
    return days;
  }

  /**
   * Helper method to check if user input is valid, if not set hint message.
   * @param s a WithTextfield object.
   * @return true if the user input is valid, false otherwise.
   */
  static public boolean checkInputWithTextField(WithTextField s) {
    String portfolioName = s.getInput().get(0);
    if (portfolioName.length() == 0) {
      s.setHintMess("empty portfolio name");
      return false;
    }
    String dateString = s.getInput().get(1);
    LocalDate date;
    try {
      date = LocalDate.parse(dateString);
    }
    catch (Exception e) {
      s.setHintMess("Invalid date");
      return false;
    }
    if (date.isAfter(LocalDate.now())) {
      s.setHintMess("Data cannot be after now");
      return false;
    }
    return true;
  }

  /**
   * Helper method to help to set view location and dispose view.
   * @param main the button only view.
   * @param other another button only view.
   * @param listener the provided listener.
   */
  static public void setViewLocation(ButtonOnly main, ButtonOnly other
      , ActionListener listener) {
    main = new MainView("Home");
    main.addActionListener(listener);
    ((JFrame) main).setLocation(((JFrame) other).getLocation());
    ((JFrame) other).dispose();
  }

  /**
   * Helper method to help to set view location and dispose view.
   * @param main the button only view.
   * @param other the IAdvanvedView.
   * @param listener the provided listener.
   */
  static public void setViewLocationHelper(ButtonOnly main, IAdvancedView other
      , ActionListener listener) {
    main = new MainView("Home");
    main.addActionListener(listener);
    ((JFrame) main).setLocation(((JFrame) other).getLocation());
    ((JFrame) other).dispose();
  }


  /**
   * Check if user input of strategy basic with an end date is valid. Set hint if not valid.
   * @param s a view user use to type strategy basic information.
   * @return true if what user typed is valid, false other wise.
   */
  static public boolean checkStrategyBasicWithEnd(WithTextField s) {
    List<String> all = s.getInput();
    if (all.get(0).length() == 0) {
      s.setHintMess("Empty Strategy Name");
      return false;
    }
    try {
      if (all.get(1).length() == 0 || Integer.parseInt(all.get(1)) < 0) {
        s.setHintMess("Invalid stock number");
        return false;
      }
    }
    catch (NumberFormatException e) {
      s.setHintMess("Invalid stock number");
      return false;
    }
    LocalDate start = LocalDate.parse(s.getInput().get(2));
    LocalDate end = LocalDate.parse(s.getInput().get(3));
    if (start.isAfter(end)) {
      s.setHintMess("Start date is after end date");
      return false;
    }
    if (start.isAfter(LocalDate.now())) {
      s.setHintMess("Start date cannot after now");
      return false;
    }
    try {
      if (all.get(4).length() == 0 || Integer.parseInt(all.get(4)) < 0) {
        s.setHintMess("Invalid interval days");
        return false;
      }
    }
    catch (NumberFormatException e) {
      s.setHintMess("Invalid interval days");
      return false;
    }
    try {
      if (all.get(5).length() == 0 || Double.parseDouble(all.get(5)) < 0) {
        s.setHintMess("Invalid money");
        return false;
      }
    }
    catch (NumberFormatException e) {
      s.setHintMess("Invalid money");
      return false;
    }
    return true;
  }

  /**
   * Check if user input of strategy basic without an end date is valid. Set hint if not valid.
   * @param s a view user use to type strategy basic information.
   * @return true if what user typed is valid, false other wise.
   */
  static public boolean checkStrategyBasicNoEnd(WithTextField s) {
    List<String> all = s.getInput();
    if (all.get(0).length() == 0) {
      s.setHintMess("Empty Strategy Name");
      return false;
    }
    try {
      if (all.get(1).length() == 0 || Integer.parseInt(all.get(1)) < 0) {
        s.setHintMess("Invalid stock number");
        return false;
      }
    }
    catch (NumberFormatException e) {
      s.setHintMess("Invalid stock number");
      return false;
    }
    LocalDate start = LocalDate.parse(s.getInput().get(2));
    if (start.isAfter(LocalDate.now())) {
      s.setHintMess("Start date cannot be after now");
      return false;
    }
    try {
      if (all.get(3).length() == 0 || Integer.parseInt(all.get(3)) < 0) {
        s.setHintMess("Invalid interval days");
        return false;
      }
    }
    catch (NumberFormatException e) {
      s.setHintMess("Invalid interval days");
      return false;
    }
    try {
      if (all.get(4).length() == 0 || Double.parseDouble(all.get(4)) < 0) {
        s.setHintMess("Invalid money");
        return false;
      }
    }
    catch (NumberFormatException e) {
      s.setHintMess("Invalid money");
      return false;
    }
    return true;
  }

  /**
   * Add stock and percentage to their lists, and sent hint message if input is not valid.
   * @param s A WithTextField view user use to type stocks and percentages.
   * @param stock stock list.
   * @param percentage percentage list.
   * @return true if every user input is valid, false otherwise.
   */
  static public boolean addStockAndPercentage(WithTextField s, List<String> stock
      , List<Double> percentage) {
    List<String> all = s.getInput();
    for (int i = 0; i < all.size(); i += 2) {
      if (all.get(i).length() == 0) {
        s.setHintMess("Empty stock symbol exists");
        return false;
      }
      stock.add(all.get(i));
      if (all.get(i + 1).length() == 0) {
        percentage.add(0.0);
        continue;
      }
      try {
        double percent = Double.parseDouble(all.get(i + 1));
        percentage.add(percent);
      }
      catch (NumberFormatException e) {
        s.setHintMess(e.getMessage());
        return false;
      }
    }
    return true;
  }

  /**
   * Set View location.
   * @param main the mainView.
   * @param other A commobox view.
   * @param listener A provided listener.
   */
  static public void setLocation(ButtonOnly main, ComboBoxView other, ActionListener listener) {
    other.addActionListener(listener);
    ((JFrame) other).setLocation(((JFrame) main).getLocation());
    ((JFrame) main).dispose();
  }

  /**
   * Check if user input is valid. If user input is not valid, set hint message.
   * @param s the WithTextField view that allow user to type information.
   * @return true if user input is valid, false otherwise.
   */
  static public boolean checkStockMoneyDate(WithTextField s) {
    String stockSymbol = s.getInput().get(0);
    if (stockSymbol.length() == 0) {
      s.setHintMess("empty stock symbol");
      return false;
    }

    String money = s.getInput().get(1);
    if (money.length() == 0 || !money.matches("(([1-9][\\d]*|[0])\\.[\\d]*)|([1-9][\\d]*)"
        + "")) {
      s.setHintMess("Invalid money");
      return false;
    }
    String dateString = s.getInput().get(2);
    LocalDate date;
    try {
      date = LocalDate.parse(dateString);
    }
    catch (Exception e) {
      s.setHintMess("Invalid date");
      return false;
    }
    if (date.isAfter(LocalDate.now())) {
      s.setHintMess("Date cannot be after now");
      return false;
    }
    String portfolioName = s.getInput().get(3);
    if (portfolioName.length() == 0) {
      s.setHintMess("Portfolio Name Empty");
      return false;
    }
    return true;
  }

  /**
   * Check if user input is valid.
   * @param s the with text field view.
   * @return true is valid, false otherwise.
   */
  static public boolean checkStockShareDate(WithTextField s) {
    String stockSymbol = s.getInput().get(0);
    if (stockSymbol.length() == 0) {
      s.setHintMess("empty stock symbol");
      return false;
    }
    String volume = s.getInput().get(1);
    if (volume.length() == 0 || !volume.matches("[1-9][\\d]*")) {
      s.setHintMess("invalid volume");
      return false;
    }
    String dateString = s.getInput().get(2);
    LocalDate date;
    try {
      date = LocalDate.parse(dateString);
    }
    catch (Exception e) {
      s.setHintMess("Invalid date");
      return false;
    }
    if (date.isAfter(LocalDate.now())) {
      s.setHintMess("Date cannot be after now");
      return false;
    }
    return true;
  }

  /**
   * Set view.
   * @param first first view.
   * @param main main view.
   * @param listener listener.
   */
  static public void disposeTheSecondSetFirstView(WithTextField first, ButtonOnly main,
      ActionListener listener) {
    first.addActionListener(listener);
    ((JFrame) first).setLocation(((JFrame) main).getLocation());
    ((JFrame) main).dispose();
  }

  /**
   * Manage View.
   * @param main main view.
   * @param second second view.
   * @param listener listener.
   */
  static public void disposeTheFirstSetSecond(ButtonOnly main, WithTextField second,
      ActionListener listener) {
    main.addActionListener(listener);
    ((JFrame) main).setLocation(((JFrame) second).getLocation());
    ((JFrame) second).dispose();
  }
}
