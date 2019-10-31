package tradestock.view.ButtonOnlyView;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * This class represents the page when user do check cost operation.
 */
public class CheckCost extends JFrame implements ButtonOnly {
  private JButton checkCostBasis;
  private JButton checkCostBasisByDate;

  /**
   * Constructor of CheckCost, it initializes the page when user do check cost operation.
   * @param caption caption.
   */
  public CheckCost(String caption) {
    super(caption);
    checkCostBasis = new JButton("Check Cost Basis");
    checkCostBasis.setActionCommand("check cost check cost basis");

    checkCostBasisByDate = new JButton("Check Cost Basis By Date");
    checkCostBasisByDate.setActionCommand("check cost check cost basis by date");
    JPanel panel = new JPanel();
    panel.add(checkCostBasis);
    panel.add(checkCostBasisByDate);
    GridLayout gridLayout = new GridLayout(2, 1);
    gridLayout.setHgap(100);
    gridLayout.setVgap(100);
    panel.setLayout(gridLayout);
    panel.setBorder(new EmptyBorder(new Insets(100, 85, 100, 85)));
    this.add(panel);

    this.setPreferredSize(new Dimension(450, 500));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    this.pack();
  }

  /**
   * Add the provided listener.
   * @param listener provided listener.
   */
  @Override
  public void addActionListener (ActionListener listener) {
    checkCostBasis.addActionListener(listener);
    checkCostBasisByDate.addActionListener(listener);
  }

  /**
   * Reset the focus on the appropriate part of the view.
   */
  @Override
  public void resetFocus () {
    this.setFocusable(true);
    this.requestFocus();
  }
}
