package tradestock.view.TextFieldView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class represents a view, when users do check value operation.
 */
public class CheckValueView extends JFrame implements WithTextField {
  private JLabel portfolioNameLabel;
  private JTextField portfolioNameText;

  private JButton check;
  private JButton home;

  private JLabel hint;

  /**
   * Constructor of CheckValueView, it initialize the view including label, text fields and
   * buttons.
   * @param caption caption.
   */
  public CheckValueView(String caption) {
    super(caption);

    JPanel portfolioPanel = new JPanel();
    portfolioNameLabel = new JLabel("Portfolio Name");
    portfolioNameText = new JTextField(20);
    portfolioPanel.add(portfolioNameLabel);
    portfolioPanel.add(portfolioNameText);

    JPanel buttonPanel = new JPanel();
    check = new JButton("check");
    check.setActionCommand("check value view check");
    home = new JButton("home");
    home.setActionCommand("check value view home");
    buttonPanel.add(check);
    buttonPanel.add(home);

    hint = new JLabel("");
    JPanel hintPanel = new JPanel();
    hintPanel.add(hint);

    this.add(portfolioPanel, BorderLayout.PAGE_START);
    this.add(hintPanel, BorderLayout.CENTER);
    this.add(buttonPanel, BorderLayout.PAGE_END);

    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setPreferredSize(new Dimension(450, 500));
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
  public String getInput() {
    return portfolioNameText.getText();
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
