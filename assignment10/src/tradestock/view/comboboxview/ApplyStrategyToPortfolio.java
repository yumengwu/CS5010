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
 * This class represents the view when user apply a strategy to a portfolio.
 */
public class ApplyStrategyToPortfolio extends JFrame implements ComboBoxView {
  private JButton apply;
  private JButton home;
  private JLabel hint;
  private JComboBox portfolio;
  private JComboBox strategy;

  /**
   * Constructor of ApplyStrategyToPortfolio, it initializes the ApplyStrategyToPortfolio view.
   * @param caption caption.
   * @param portfolioName all portfolio name.
   * @param strategyName all strategy name.
   */
  public ApplyStrategyToPortfolio(String caption, List<String> portfolioName,
      List<String> strategyName) {
    super(caption);
    Vector<String> portfolio = new Vector<>(portfolioName);
    Vector<String> strategy = new Vector<>(strategyName);
    this.portfolio = new JComboBox(portfolio);
    this.strategy = new JComboBox(strategy);
    JPanel comboPanel = new JPanel();

    JLabel portfolioLabel = new JLabel("Portfolio");
    JLabel strategyLabel = new JLabel("Strategy");

    JPanel portfolioPanel = new JPanel();
    portfolioPanel.add(portfolioLabel);
    portfolioPanel.add(this.portfolio);

    JPanel strategyPanel = new JPanel();
    strategyPanel.add(strategyLabel);
    strategyPanel.add(this.strategy);

    comboPanel.setLayout(new GridLayout(2,1));
    comboPanel.add(portfolioPanel);
    comboPanel.add(strategyPanel);

    apply = new JButton("Apply");
    apply.setActionCommand("apply strategy apply");
    home = new JButton("Home");
    home.setActionCommand("apply strategy home");
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(apply);
    buttonPanel.add(home);
    if (portfolioName.size() == 0 || strategyName.size() == 0) {
      apply.hide();
    }
    hint = new JLabel();
    JPanel hintPanel = new JPanel();
    hintPanel.add(hint);
    JPanel bottomPanel = new JPanel();
    bottomPanel.setLayout(new GridLayout(2,1));
    bottomPanel.add(hintPanel);
    bottomPanel.add(buttonPanel);
    this.add(comboPanel, BorderLayout.PAGE_START);
    this.add(bottomPanel, BorderLayout.PAGE_END);
    this.setPreferredSize(new Dimension(450, 500));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    this.pack();

  }

  /**
   * Add a listener to button.
   * @param listener the provided listener.
   */
  @Override
  public void addActionListener(ActionListener listener) {
    home.addActionListener(listener);
    apply.addActionListener(listener);
  }

  /**
   * Get the content of combobox that user select.
   * @return the content of combobox that user select.
   */
  @Override
  public ArrayList<String> getInput() {
    ArrayList<String> all = new ArrayList<>();
    all.add(portfolio.getSelectedItem().toString());
    all.add(strategy.getSelectedItem().toString());
    return all;
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
