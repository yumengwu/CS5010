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
 * This class represents the AddStock view when user do add stock operation, it enables user to add
 * a stock to portfolio.
 */
public class AddStock extends JFrame implements WithTextField {
  private JButton add;
  private JButton home;
  private JTextField portfolioName;
  private JTextField[] stockText;
  private int num;
  private JLabel hint;

  /**
   * Constructor of AddStock, it initializes the AddStock view.
   * @param caption caption.
   */
  public AddStock(String caption) {
    super(caption);
    JLabel[] stockLabel;
    JPanel[] stockPanel;
    JLabel portfolio = new JLabel("Portfolio Name ");
    portfolioName = new JTextField(20);
    JPanel portfolioPanel = new JPanel();
    portfolioPanel.add(portfolio);
    portfolioPanel.add(portfolioName);
    num = 5;
    stockLabel = new JLabel[num];
    stockText = new JTextField[num];
    stockPanel = new JPanel[num];
    for (int i = 0; i < num; i++) {
      int j = i + 1;
      stockLabel[i] = new JLabel("Stock" + j);
      stockText[i] = new JTextField(20);
      stockPanel[i] = new JPanel();
      stockPanel[i].add(stockLabel[i]);
      stockPanel[i].add(stockText[i]);
    }

    hint = new JLabel();
    JPanel hintPanel = new JPanel();
    hintPanel.add(hint);
    add = new JButton("Add");
    add.setActionCommand("add stock add");
    home = new JButton("Home");
    home.setActionCommand("add stock home");
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(add);
    buttonPanel.add(home);
    JPanel bottomPanel = new JPanel();
    bottomPanel.setLayout(new GridLayout(2,1));
    bottomPanel.add(hintPanel);
    bottomPanel.add(buttonPanel);

    JPanel allStock = new JPanel();
    allStock.setLayout(new GridLayout(num + 1, 1));
    allStock.add(portfolioPanel);
    for (int i = 0; i < num; i++) {
      allStock.add(stockPanel[i]);
    }
    int vsb = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
    int hsb = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
    JScrollPane scrollPane = new JScrollPane(allStock,vsb,hsb);
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
    add.addActionListener(listener);
    home.addActionListener(listener);
  }

  /**
   * Get the content of text field that user typed.
   * @return the content of text field that user typed.
   */
  @Override
  public List<String> getInput() {
    List<String> allInput = new ArrayList<>();
    allInput.add(portfolioName.getText());

    for (int i = 0; i < num; i++) {
      allInput.add(stockText[i].getText().toUpperCase());
    }
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
   * Clear text fields.
   */
  @Override
  public void clearField() {
    portfolioName.setText("");
    for (int i = 0; i < num; i++) {
      stockText[i].setText("");
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
