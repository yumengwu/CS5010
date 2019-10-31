package tradestock.view.textfieldview;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import tradestock.view.helperclass.HelperClass;

/**
 * This class represent the view when user choose to create a strategy with an end date , it
 * enables user type the basis information about the strategy, include the number of stocks in the
 * strategy, the strategy name, start date, end date, interval, and how much money the user invest
 * every period.
 */
public class CreateStrategyInputStrategyBasic extends JFrame implements WithTextField {
  private JTextField number;
  private JTextField strategyName;
  private JComboBox startYear;
  private JComboBox startMonth;
  private JComboBox startDay;
  private JComboBox endYear;
  private JComboBox endMonth;
  private JComboBox endDay;
  private JLabel hint;
  private JButton next;
  private JButton home;
  private JTextField interval;
  private JTextField moneyText;

  /**
   * Constructor of CreateStrategyInputStrategyBasic, it initialize the view including label,
   * combobox, text fields and buttons.
   * @param caption caption.
   */
  public CreateStrategyInputStrategyBasic(String caption) {
    super(caption);
    JLabel label;
    this.setPreferredSize(new Dimension(450, 500));

    this.setLayout(new BorderLayout());

    JPanel panel = new JPanel();
    JPanel hintPanel = new JPanel();
    JPanel buttonPanel = new JPanel();


    JLabel strategyNameLabel = new JLabel("Strategy Name");
    strategyName = new JTextField(20);
    JPanel strategyNamePanel = new JPanel();
    strategyNamePanel.add(strategyNameLabel);
    strategyNamePanel.add(strategyName);

    label = new JLabel("Stock Number: ");
    number = new JTextField(20);
    next = new JButton("Next");
    home = new JButton("Home");
    next.setActionCommand("create strategy input stock number next");
    home.setActionCommand("create strategy input stock number home");
    hint = new JLabel("");
    panel.add(label);
    panel.add(number);
    hintPanel.add(hint);
    buttonPanel.add(next);
    buttonPanel.add(home);

    JLabel startDate;
    JPanel datePanel = new JPanel();
    startDate = new JLabel("Start Date");
    JLabel yearLabel = new JLabel("Year");
    JLabel monthLabel = new JLabel("Month");
    JLabel dayLabel = new JLabel("Day");
    int currentYear = LocalDateTime.now().getYear();
    String[] years = new String[currentYear + 45 - 1994];
    int j = 0;
    for (int i = 1994; i < currentYear + 45; i++) {
      years[j++] = Integer.toString(i);
    }
    ActionListener listener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        HelperClass.setDay(startYear, startMonth, startDay);
      }
    };
    startYear = new JComboBox(years);
    startYear.addActionListener(listener);

    endYear = new JComboBox(years);
    endYear.addActionListener(listener);

    String[] monthNumber = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    startMonth = new JComboBox(monthNumber);
    startMonth.addActionListener(listener);
    endMonth = new JComboBox(monthNumber);
    endMonth.addActionListener(listener);
    String[] days = HelperClass.getDefaultDay();
    startDay = new JComboBox(days);
    endDay = new JComboBox(days);

    datePanel.add(startDate);
    datePanel.add(yearLabel);
    datePanel.add(startYear);
    datePanel.add(monthLabel);
    datePanel.add(startMonth);
    datePanel.add(dayLabel);
    datePanel.add(startDay);

    JLabel endDate = new JLabel("End Date");
    JLabel yearLabel1 = new JLabel("Year");
    JLabel monthLabel1 = new JLabel("Month");
    JLabel dayLabel1 = new JLabel("Day");
    JPanel endPanel = new JPanel();
    endPanel.add(endDate);
    endPanel.add(yearLabel1);
    endPanel.add(endYear);
    endPanel.add(monthLabel1);
    endPanel.add(endMonth);
    endPanel.add(dayLabel1);
    endPanel.add(endDay);

    JLabel intervalLable = new JLabel("Interval(days)");
    interval = new JTextField(20);
    JPanel intervalPanel = new JPanel();
    intervalPanel.add(intervalLable);
    intervalPanel.add(interval);

    JLabel moneyLabel = new JLabel("Money");
    moneyText = new JTextField(20);
    JPanel moneyPanel = new JPanel();
    moneyPanel.add(moneyLabel);
    moneyPanel.add(moneyText);


    JPanel composition = new JPanel();
    composition.setLayout(new GridLayout(6,1));
    composition.add(strategyNamePanel);
    composition.add(panel);
    composition.add(datePanel);
    composition.add(endPanel);
    composition.add(intervalPanel);
    composition.add(moneyPanel);
    JPanel finalPanel = new JPanel();
    finalPanel.add(composition);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.add(composition, BorderLayout.PAGE_START);
    this.add(hintPanel, BorderLayout.CENTER);
    this.add(buttonPanel, BorderLayout.PAGE_END);
    this.setVisible(true);
    this.pack();
  }

  /**
   * Add provided listener.
   * @param listener the provided listener.
   */
  public void addActionListener(ActionListener listener) {
    next.addActionListener(listener);
    home.addActionListener(listener);
  }

  /**
   * Get the content of text field that user typed.
   * @return the content of text field that user typed.
   */
  public List<String> getInput() {
    String startDate = startYear.getSelectedItem().toString() + "-"
        + startMonth.getSelectedItem().toString() + "-" + startDay.getSelectedItem().toString();
    String endDate = endYear.getSelectedItem().toString() + "-"
        + endMonth.getSelectedItem().toString() + "-" + endDay.getSelectedItem().toString();
    List<String> allInput = new ArrayList<>();
    allInput.add(strategyName.getText());
    allInput.add(number.getText());
    allInput.add(startDate);
    allInput.add(endDate);
    allInput.add(interval.getText());
    allInput.add(moneyText.getText());
    return allInput;
  }

  /**
   * Take a given a message, and show it on the view.
   * @param message A given string message.
   */
  public void setHintMess(String message) {
    hint.setText(message);
  }

  /**
   * Clear the text field.
   */
  public void clearField() {
    strategyName.setText("");
    number.setText("");
    interval.setText("");
    moneyText.setText("");
  }

  /**
   * Reset focus.
   */
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }
}

