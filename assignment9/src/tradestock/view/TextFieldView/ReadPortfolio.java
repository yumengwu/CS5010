package tradestock.view.TextFieldView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The class represents the view when user read portfolio from a file.
 */
public class ReadPortfolio extends JFrame implements WithTextField {
  private JLabel fileName;
  private JTextField fileNameText;
  private JButton read;
  private JButton home;
  private JLabel hint;

  /**
   * Constructor of ReadPortfolio, it initializes the ReadPortfolio view.
   * @param caption caption.
   */
  public ReadPortfolio(String caption) {
    super(caption);
    fileName = new JLabel("File Name: ");
    fileNameText = new JTextField(20);
    JPanel filePanel = new JPanel();
    filePanel.add(fileName);
    filePanel.add(fileNameText);
    read = new JButton("Read");
    home = new JButton("Home");
    read.setActionCommand("read portfolio read");
    home.setActionCommand("read portfolio home");
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(read);
    buttonPanel.add(home);
    hint = new JLabel();
    hint.setText("If you want to specify the file path, add it to file name. eg: C:/filename");
    JPanel hintPanel = new JPanel();
    hintPanel.add(hint);
    JPanel composition = new JPanel();
    composition.setLayout(new GridLayout(2,1));
    composition.add(filePanel);
    composition.add(hintPanel);
    this.add(composition, BorderLayout.PAGE_START);
    this.add(buttonPanel, BorderLayout.PAGE_END);
    this.setPreferredSize(new Dimension(450, 500));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    this.pack();
  }

  /**
   * Add provided listener.
   * @param listener the provided listener.
   */
  @Override
  public void addActionListener (ActionListener listener) {
    read.addActionListener(listener);
    home.addActionListener(listener);
  }

  /**
   * Get the content of text field that user typed.
   * @return the content of text field that user typed.
   */
  @Override
  public String getInput () {
    return fileNameText.getText();
  }

  /**
   * Take a given a message, and show it on the view.
   * @param message A given string message.
   */
  @Override
  public void setHintMess (String message) {
    hint.setText(message);
  }

  /**
   * Clear the text field.
   */
  @Override
  public void clearField () {
    fileNameText.setText("");
  }

  /**
   * Reset focus.
   */
  @Override
  public void resetFocus () {
    this.setFocusable(true);
    this.requestFocus();
  }
}
