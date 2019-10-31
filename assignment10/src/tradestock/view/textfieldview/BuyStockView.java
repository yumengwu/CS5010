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
 * This class represents a view that user will see when user buy stocks.
 */
public class BuyStockView extends JFrame implements WithTextField {

  private JLabel hint;
  private JButton buy;
  private JButton home;

  private JTextField stockSymbolText;
  private JTextField volumeText;
  private JComboBox year;
  private JComboBox month;
  private JComboBox day;
  private JTextField portfolioNameText;

  /**
   * Constructor of BuyStockView, it initialize the view including label, text fields and buttons.
   * @param caption caption.
   */
  public BuyStockView(String caption) {
    super(caption);
    JLabel stockSymbol;
    JLabel volume;
    JLabel date;
    JLabel portfolioName;
    JPanel stockSymbolPanel = new JPanel();
    stockSymbol = new JLabel("Stock Symbol  ");
    stockSymbolText = new JTextField(20);
    stockSymbolPanel.add(stockSymbol);
    stockSymbolPanel.add(stockSymbolText);

    JPanel volumePanel = new JPanel();
    volume = new JLabel("Volume        ");
    volumeText = new JTextField(20);
    volumePanel.add(volume);
    volumePanel.add(volumeText);

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

    JPanel portfolioPanel = new JPanel();
    portfolioName = new JLabel("Portfolio Name");
    portfolioNameText = new JTextField(20);
    portfolioPanel.add(portfolioName);
    portfolioPanel.add(portfolioNameText);

    JPanel hintPanel = new JPanel();
    hint = new JLabel("");
    hintPanel.add(hint);

    JPanel buttonPanel = new JPanel();
    buy = new JButton("buy");
    buy.setActionCommand("buy");
    home = new JButton("home");
    home.setActionCommand("buy stock home");
    buttonPanel.add(buy);
    buttonPanel.add(home);

    JPanel composition = new JPanel();
    composition.setLayout(new GridLayout(4, 1));
    composition.add(stockSymbolPanel);
    composition.add(volumePanel);
    composition.add(datePanel);
    composition.add(portfolioPanel);

    this.add(composition, BorderLayout.PAGE_START);
    this.add(hintPanel, BorderLayout.CENTER);
    this.add(buttonPanel, BorderLayout.PAGE_END);

    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setPreferredSize(new Dimension(450, 500));
    this.setVisible(true);
    this.pack();
  }

  /**
   * Add a provided listener.
   * @param listener the provided listener.
   */
  @Override
  public void addActionListener(ActionListener listener) {
    buy.addActionListener(listener);
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
    allInput.add(stockSymbolText.getText());
    allInput.add(volumeText.getText());
    allInput.add(date);
    allInput.add(portfolioNameText.getText());
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
    stockSymbolText.setText("");
    volumeText.setText("");
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
