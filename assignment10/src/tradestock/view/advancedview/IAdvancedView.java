package tradestock.view.advancedview;

import java.awt.event.ActionListener;

/**
 * This Interface represent a view with File Chooser. User can get file path and operation
 * on the file chooser, and set hint message at a IAdvancedView.
 */
public interface IAdvancedView {


  /**
   * Get the file path.
   * @return the file path as a String.
   */
  String getInput();

  /**
   * Get the operation user did.
   * @return An Integer represents the user operation.
   */
  int getFileChooser();

  /**
   * Set Hint message.
   * @param message message that need to be set.
   */
  void setHint(String message);

  /**
   * Add the provided listener.
   * @param listener provided listener.
   */
  void addActionListener(ActionListener listener);

  /**
   * Reset the focus on the appropriate part of the view.
   */
  void resetFocus();
}
