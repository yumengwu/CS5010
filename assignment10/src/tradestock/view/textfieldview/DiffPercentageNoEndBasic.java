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
 * This class represents the view when user choose to create a strategy without an end date and use
 * different weight on every stock in a strategy. It enables use to input the number of stocks in
 * the strategy, the name of the strategy, start date, interval of the strategy and how much money
 * the user invest every period.
 */
public class DiffPercentageNoEndBasic extends JFrame implements WithTextField {

  private JTextField number;
  private JTextField strategyName;
  private JComboBox startYear;
  private JComboBox startMonth;
  private JComboBox startDay;
  private JLabel hint;
  private JButton next;
  private JButton home;
  private JTextField interval;
  private JTextField moneyText;

  /**
   * Constructor of DiffPercentageNoEndBasic, it initializes the view including label, text fields
   * and buttons.
   * @param caption caption.
   */
  public DiffPercentageNoEndBasic(String caption) {
    super(caption);
    JLabel label;
    this.setPreferredSize(new Dimension(450 , 500));

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
    next.setActionCommand("diff percentage no end basic next");
    home.setActionCommand("diff percentage no end basic home");
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
        HelperClass.setDay(startYear , startMonth , startDay);
      }
    };
    startYear = new JComboBox(years);
    startYear.addActionListener(listener);

    String[] monthNumber = { "01" , "02" , "03" , "04" , "05" , "06" , "07" , "08" , "09" , "10" ,
        "11" , "12" };
    startMonth = new JComboBox(monthNumber);
    startMonth.addActionListener(listener);
    String[] days = HelperClass.getDefaultDay();
    startDay = new JComboBox(days);

    datePanel.add(startDate);
    datePanel.add(yearLabel);
    datePanel.add(startYear);
    datePanel.add(monthLabel);
    datePanel.add(startMonth);
    datePanel.add(dayLabel);
    datePanel.add(startDay);

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
    composition.setLayout(new GridLayout(5 , 1));
    composition.add(strategyNamePanel);
    composition.add(panel);
    composition.add(datePanel);
    composition.add(intervalPanel);
    composition.add(moneyPanel);
    JPanel finalPanel = new JPanel();
    finalPanel.add(composition);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.add(composition , BorderLayout.PAGE_START);
    this.add(hintPanel , BorderLayout.CENTER);
    this.add(buttonPanel , BorderLayout.PAGE_END);
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
    List<String> allInput = new ArrayList<>();
    allInput.add(strategyName.getText());
    allInput.add(number.getText());
    allInput.add(startDate);
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
