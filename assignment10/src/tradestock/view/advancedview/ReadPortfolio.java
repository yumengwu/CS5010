package tradestock.view.advancedview;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * The class represents the view when user read portfolio from a file.
 */
public class ReadPortfolio extends JFrame implements IAdvancedView {
  private JButton ok;
  private JLabel hint;
  private JFileChooser fileChooser;
  private int userSelection;

  /**
   * Constructor of ReadPortfolio, it initializes the ReadPortfolio view.
   * @param caption caption.
   */
  public ReadPortfolio(String caption) {
    super(caption);
    fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Specify a file to open");
    userSelection = fileChooser.showOpenDialog(this);
    ok = new JButton("OK");
    ok.setActionCommand("read portfolio home");
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(ok);
    hint = new JLabel();
    hint.setText("");
    JPanel hintPanel = new JPanel();
    hintPanel.add(hint);
    JPanel composition = new JPanel();
    composition.setLayout(new GridLayout(1,1));
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
  public void addActionListener(ActionListener listener) {
    fileChooser.addActionListener(listener);
    ok.addActionListener(listener);
  }

  /**
   * Get the file path.
   * @return the file path as a String.
   */
  @Override
  public String getInput() {
    return fileChooser.getSelectedFile().getAbsolutePath();
  }

  /**
   * Get the operation user did.
   * @return An Integer represents the user operation.
   */
  @Override
  public int getFileChooser() {
    return userSelection;
  }

  /**
   * Set Hint message.
   * @param message message that need to be set.
   */
  @Override
  public void setHint(String message) {
    hint.setText(message);
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
