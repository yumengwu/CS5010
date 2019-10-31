package tradestock.view.textfieldview;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

/**
 * This class represents the view when user create a strategy with end date and choose use different
 * weight on every stock, it enables user to type the stock symbols and its percentage.
 */
public class UseDiffPercentageEnd extends JFrame implements WithTextField {
  private JButton create;
  private JButton home;
  private JTextField[] stockSymbol;
  private JTextField[] percentage;
  private JLabel hint;
  private int num;

  /**
   * Constructor of UseDiffPercentageEnd, it initializes the UseDiffPercentageEnd vies.
   * @param caption caption.
   * @param numberOfStock the number of stocks user will add to the portfolio.
   */
  public UseDiffPercentageEnd(String caption, int numberOfStock) {
    super(caption);
    List<String> stockNames = new ArrayList<>();
    JLabel[] stockLabel;
    JLabel[] percentageLabel;
    JPanel[] stockPanel;
    hint = new JLabel();

    num = numberOfStock;
    stockLabel = new JLabel[num];
    stockSymbol = new JTextField[num];
    percentageLabel = new JLabel[num];
    percentage = new JTextField[num];

    stockPanel = new JPanel[num];
    for (int i = 0; i < num; i++) {
      stockLabel[i] = new JLabel("Stock Symbol");
      stockSymbol[i] = new JTextField(20);
      percentageLabel[i] = new JLabel("Percentage(Decimal eg:0.2)");
      percentage[i] = new JTextField(3);
      stockPanel[i] = new JPanel();
      stockPanel[i].add(stockLabel[i]);
      stockPanel[i].add(stockSymbol[i]);
      stockPanel[i].add(percentageLabel[i]);
      stockPanel[i].add(percentage[i]);
    }

    JPanel hintPanel = new JPanel();
    hintPanel.add(hint);

    create = new JButton("Create");
    create.setActionCommand("use different percentage end create");
    home = new JButton("Home");
    home.setActionCommand("use different percentage end home");
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(create);
    buttonPanel.add(home);

    JPanel bottomPanel = new JPanel();
    bottomPanel.setLayout(new GridLayout(2, 1));
    bottomPanel.add(hintPanel);
    bottomPanel.add(buttonPanel);

    JPanel allStock = new JPanel();
    allStock.setLayout(new GridLayout(stockNames.size(), 1));
    for (int i = 0; i < num; i++) {
      allStock.add(stockPanel[i]);
    }
    int vsb = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
    int hsb = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
    JScrollPane scrollPane = new JScrollPane(allStock,vsb,hsb);
    this.add(hintPanel, BorderLayout.PAGE_START);
    this.add(scrollPane, BorderLayout.CENTER);
    this.add(bottomPanel, BorderLayout.PAGE_END);

    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setPreferredSize(new Dimension(450, 500));
    this.setVisible(true);
    this.pack();
  }

  /**
   * Add listener to buttons.
   * @param listener the provided listener.
   */
  @Override
  public void addActionListener(ActionListener listener) {
    create.addActionListener(listener);
    home.addActionListener(listener);
  }

  /**
   * Get the content of text field that user typed.
   * @return the content of text field that user typed.
   */
  @Override
  public List<String> getInput() {
    List<String> allInfo = new ArrayList<>();
    for (int i = 0; i < num; i++) {
      allInfo.add(stockSymbol[i].getText().toUpperCase());
      allInfo.add(percentage[i].getText());
    }
    return allInfo;
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
   * Clear text fields.
   */
  @Override
  public void clearField() {
    for (int i = 0; i < num; i++) {
      percentage[i].setText("");
    }
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
