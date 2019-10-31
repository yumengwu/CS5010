package tradestock.view.comboboxview;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class represents the SaveStrategyChooseStrategy, it enables user to choose a strategy to
 * save.
 */
public class SaveStrategyChooseStrategy extends JFrame implements ComboBoxView {
  private JComboBox strategyText;
  private JButton choose;
  private JButton home;
  private JLabel hint;

  /**
   * Constructor of SaveStrategyChooseStrategy, it initializes the SaveStrategyChooseStrategy view.
   * @param caption caption.
   * @param strategyList all strategy names.
   */
  public SaveStrategyChooseStrategy(String caption, List<String> strategyList) {
    super(caption);
    JLabel strategyName;
    strategyName = new JLabel("Strategy Name: ");

    Vector<String> allName = new Vector<>(strategyList);
    strategyText = new JComboBox(allName);
    JPanel portfolioPanel = new JPanel();
    portfolioPanel.add(strategyName);
    portfolioPanel.add(strategyText);
    choose = new JButton("Choose");
    home = new JButton("Home");
    choose.setActionCommand("save strategy choose strategy");
    home.setActionCommand("save strategy choose strategy home");
    if (strategyList.size() == 0) {
      choose.hide();
    }
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(choose);
    buttonPanel.add(home);
    hint = new JLabel();
    JPanel hintPanel = new JPanel();
    hintPanel.add(hint);
    JPanel composition = new JPanel();
    composition.setLayout(new GridLayout(2,1));
    composition.add(portfolioPanel);
    composition.add(hintPanel);
    this.add(composition, BorderLayout.PAGE_START);
    this.add(buttonPanel, BorderLayout.PAGE_END);
    this.setPreferredSize(new Dimension(450, 500));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    this.pack();
  }

  /**
   * Add provided listener to buttons.
   * @param listener the provided listener.
   */
  @Override
  public void addActionListener(ActionListener listener) {
    choose.addActionListener(listener);
    home.addActionListener(listener);
  }

  /**
   * Get the content of combobox that user select.
   * @return the content of combobox that user select.
   */
  @Override
  public ArrayList<String> getInput() {
    ArrayList<String> allInput = new ArrayList<>();
    allInput.add(strategyText.getSelectedItem().toString());
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
   * Reset focus.
   */
  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }
}
