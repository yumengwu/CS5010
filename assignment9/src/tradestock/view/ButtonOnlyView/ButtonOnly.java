package tradestock.view.ButtonOnlyView;

import java.awt.event.ActionListener;

/**
 * This interface of Views that don't have textfield in the view. It contains
 * addActionLister(ActionListener listener) and resetFocus() methods.
 */
public interface ButtonOnly {

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
