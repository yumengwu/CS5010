package tradestock.view.comboboxview;

import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * This interface represent views with Combobox but not textfield.
 */
public interface ComboBoxView {

  /**
   * Add a listener to button.
   * @param listener the provided listener.
   */
  void addActionListener(ActionListener listener);

  /**
   * Get the content of combobox that user select.
   * @return the content of combobox that user select.
   */
  ArrayList<String> getInput();

  /**
   * Take a given a message, and show it on the view.
   * @param message A given string message.
   */
  void setHintMess(String message);

  /**
   * Reset focus.
   */
  void resetFocus();
}
