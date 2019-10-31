package tradestock.view.advancedview;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The class represent the view when user save a strategy to a file.
 */
public class SaveStrategy extends JFrame implements IAdvancedView {
  private JLabel hint;
  private JButton ok;
  private JFileChooser fileChooser;
  private int userSelection;

  /**
   * Constructor of SaveStrategy, it initializes the SaveStrategy class.
   * @param caption caption.
   */
  public SaveStrategy(String caption) {
    super(caption);
    fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Specify a file to save");
    userSelection = fileChooser.showSaveDialog(this);
    ok = new JButton("OK");
    ok.setActionCommand("save strategy ok");
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(ok);

    JPanel panel = new JPanel();
    hint = new JLabel();
    panel.add(hint);

    this.setPreferredSize(new Dimension(450, 500));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.add(panel, BorderLayout.PAGE_START);
    this.add(buttonPanel, BorderLayout.PAGE_END);
    this.setVisible(true);
    this.pack();

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
   * Add provided listener.
   * @param listener the provided listener.
   */
  @Override
  public void addActionListener(ActionListener listener) {
    ok.addActionListener(listener);
    fileChooser.addActionListener(listener);
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
