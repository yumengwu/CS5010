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
 * This class represents the view when user create a strategy without end date and choose use
 * same weight on every stock, it enables user to type the stock symbols.
 */
public class UseEqualsPercentage extends JFrame implements WithTextField {
  private JButton create;
  private JButton home;
  private JTextField[] stockSymbol;
  private JLabel hint;
  private int num;

  /**
   * Constructor of UseEqualsPercentage, it initializes the UseEqualsPercentage object.
   * @param caption caption.
   * @param numberOfStock the number of stocks the user have in the strategy.
   */
  public UseEqualsPercentage(String caption, int numberOfStock) {
    super(caption);
    List<String> stockNames = new ArrayList<>();
    this.num = numberOfStock;
    JLabel[] stockLabel;
    JPanel[] stockPanel;
    hint = new JLabel();

    int num = numberOfStock;
    stockLabel = new JLabel[num];
    stockSymbol = new JTextField[num];

    stockPanel = new JPanel[num];
    for (int i = 0; i < num; i++) {
      stockLabel[i] = new JLabel("Stock Symbol");
      stockSymbol[i] = new JTextField(20);
      stockPanel[i] = new JPanel();
      stockPanel[i].add(stockLabel[i]);
      stockPanel[i].add(stockSymbol[i]);
    }

    JPanel hintPanel = new JPanel();
    hintPanel.add(hint);

    create = new JButton("Create");
    create.setActionCommand("create equals strategy create");
    home = new JButton("Home");
    home.setActionCommand("create equals strategy home");
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
   * Add provided listener to buttons.
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
    for (int i = 0; i < this.num; i++) {
      stockSymbol[i].setText("");
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
