package tradestock.view.buttononlyview;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * This class represent the view when user choose what type of strategy they are going to create.
 */
public class ChooseStrategyWithEndDateOrNot extends JFrame implements ButtonOnly {
  private JButton allEqualNoEnd;
  private JButton specificNoEnd;
  private JButton equalsPercentage;
  private JButton specifiedPercentage;
  private JButton home;

  /**
   * Constructor of ChooseStrategyWithEndDateOrNot, it initialize the ChooseStrategyWithEndDateOrNot
   *  view.
   * @param caption caption.
   */
  public ChooseStrategyWithEndDateOrNot(String caption) {
    super(caption);
    allEqualNoEnd = new JButton("Create Equal Percentage No End Date");
    allEqualNoEnd.setActionCommand("choose strategy equal no end");
    specificNoEnd = new JButton("Create Different Percentage No End Date");
    specificNoEnd.setActionCommand("choose strategy different no end");

    equalsPercentage = new JButton("Create All Equals Percentage With End Date");
    specifiedPercentage = new JButton("Create Different Percentage With End Date");
    equalsPercentage.setActionCommand("choose all equals percentage");
    specifiedPercentage.setActionCommand("choose self define percentage");

    home = new JButton("Home");
    home.setActionCommand("choose strategy home");
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(home);

    JPanel panel = new JPanel();
    panel.add(allEqualNoEnd);
    panel.add(specificNoEnd);
    panel.add(equalsPercentage);
    panel.add(specifiedPercentage);
    GridLayout gridLayout = new GridLayout(4, 1);
    gridLayout.setHgap(50);
    gridLayout.setVgap(50);
    panel.setLayout(gridLayout);
    panel.setBorder(new EmptyBorder(new Insets(50, 35, 50, 35)));
    this.add(panel, BorderLayout.CENTER);
    this.add(buttonPanel, BorderLayout.PAGE_END);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setPreferredSize(new Dimension(450, 500));
    this.setVisible(true);
    this.pack();
  }

  /**
   * dd the provided listener.
   * @param listener provided listener.
   */
  @Override
  public void addActionListener(ActionListener listener) {
    allEqualNoEnd.addActionListener(listener);
    specificNoEnd.addActionListener(listener);
    equalsPercentage.addActionListener(listener);
    specifiedPercentage.addActionListener(listener);
    home.addActionListener(listener);
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
