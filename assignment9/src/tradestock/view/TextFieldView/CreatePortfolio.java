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
 * This class represents the view when users do create portfolio operation.
 */
public class CreatePortfolio extends JFrame implements WithTextField {
  private JTextField name;
  private JLabel hint;
  private JLabel label;
  private JButton create;
  private JButton main;

  /**
   * Constructor of CreatPortfolio, it initialize the view including label, text fields and
   * buttons.
   * @param caption caption.
   */
  public CreatePortfolio(String caption) {
    super(caption);
    this.setPreferredSize(new Dimension(450, 500));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    JPanel panel = new JPanel();
    JPanel hintPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    label = new JLabel("Portfolio Name: ");
    name = new JTextField(20);
    create = new JButton("create");
    main = new JButton("home");
    create.setActionCommand("create");
    main.setActionCommand("create portfolio home");
    hint = new JLabel("");
    panel.add(label);
    panel.add(name);
    hintPanel.add(hint);
    buttonPanel.add(create);
    buttonPanel.add(main);

    this.add(panel, BorderLayout.PAGE_START);
    this.add(hintPanel, BorderLayout.CENTER);
    this.add(buttonPanel, BorderLayout.PAGE_END);
    this.setVisible(true);
    this.pack();
  }

  /**
   * Add provided listener.
   * @param listener the provided listener.
   */
  @Override
  public void addActionListener(ActionListener listener) {
    create.addActionListener(listener);
    main.addActionListener(listener);
  }

  /**
   * Get the content of text field that user typed.
   * @return the content of text field that user typed.
   */
  @Override
  public String getInput() {
    return name.getText();
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
    name.setText("");
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
