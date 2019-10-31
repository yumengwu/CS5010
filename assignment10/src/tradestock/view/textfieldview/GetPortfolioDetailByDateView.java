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
 * This class represents the view when users do examine a portfolio at a given date.
 */
public class GetPortfolioDetailByDateView extends JFrame implements WithTextField {

  private JTextField portfolioNameText;
  private JComboBox year;
  private JComboBox month;
  private JComboBox day;
  private JLabel hint;
  private JButton check;
  private JButton home;

  /**
   * Constructor of GetPortfolioDetailByDateView, it initialize the view including label, text
   * fields and buttons.
   * @param caption caption.
   */
  public GetPortfolioDetailByDateView(String caption) {
    super(caption);
    JLabel portfolioName;
    JLabel date;
    this.setPreferredSize(new Dimension(450, 500));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel first = new JPanel();
    portfolioName = new JLabel("Portfolio Name ");
    portfolioNameText = new JTextField(20);
    first.add(portfolioName);
    first.add(portfolioNameText);

    JPanel datePanel = new JPanel();
    date = new JLabel("Date     ");
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
        HelperClass.setDay(year, month, day);
      }
    };
    year = new JComboBox(years);
    year.addActionListener(listener);

    String[] monthNumber = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    month = new JComboBox(monthNumber);
    month.addActionListener(listener);
    String[] days = HelperClass.getDefaultDay();
    day = new JComboBox(days);
    datePanel.add(date);
    datePanel.add(yearLabel);
    datePanel.add(year);
    datePanel.add(monthLabel);
    datePanel.add(month);
    datePanel.add(dayLabel);
    datePanel.add(day);

    hint = new JLabel();
    JPanel third = new JPanel();
    third.add(hint);

    JPanel buttonPanel = new JPanel();
    check = new JButton("check");
    check.setActionCommand("get portfolio detail by date check");
    home = new JButton("home");
    home.setActionCommand("get portfolio detail by date home");
    buttonPanel.add(check);
    buttonPanel.add(home);

    JPanel composition = new JPanel();
    composition.setLayout(new GridLayout(2,1));
    composition.add(first);
    composition.add(datePanel);
    this.add(composition, BorderLayout.PAGE_START);
    this.add(third, BorderLayout.CENTER);
    this.add(buttonPanel, BorderLayout.AFTER_LAST_LINE);
    this.setVisible(true);
    this.pack();
  }

  /**
   * Add provided listener.
   * @param listener the provided listener.
   */
  @Override
  public void addActionListener(ActionListener listener) {
    check.addActionListener(listener);
    home.addActionListener(listener);
  }

  /**
   * Get the content of text field that user typed.
   * @return the content of text field that user typed.
   */
  @Override
  public List<String> getInput() {
    List<String> allInput = new ArrayList<>();
    String date = year.getSelectedItem().toString() + "-" + month.getSelectedItem().toString() + "-"
        + day.getSelectedItem().toString();
    allInput.add(portfolioNameText.getText());
    allInput.add(date);
    return allInput;
  }

  /**
   * Take a given a message, and show it on the view.
   * @param message A given string message.
   */
  @Override
  public void setHintMess(String message) {
    hint.setText(message);
  }

  /**
   * Clear the text field.
   */
  @Override
  public void clearField() {
    portfolioNameText.setText("");
  }

  /**
   * Reset focus.
   */
  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }
}
