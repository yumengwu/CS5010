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
 * This class represents a view when user buy stock.
 */
public class ChooseAWayToBuyStock extends JFrame implements ButtonOnly {
  private JButton buyWithNumber;
  private JButton buyWithMoney;

  /**
   * Constructor of ChooseAWayToBuyStock, it initializes the view of ChooseAWayToBuyStock.
   * @param caption caption.
   */
  public ChooseAWayToBuyStock(String caption) {
    super(caption);
    buyWithNumber = new JButton("Buy Given Number of Shares");
    buyWithNumber.setActionCommand("buy by number");
    buyWithMoney = new JButton("Buy Given Money");
    buyWithMoney.setActionCommand("buy by money");
    JPanel panel = new JPanel();
    panel.add(buyWithNumber);
    panel.add(buyWithMoney);
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
   * Add provided listener.
   * @param listener provided listener.
   */
  @Override
  public void addActionListener (ActionListener listener) {
    buyWithNumber.addActionListener(listener);
    buyWithMoney.addActionListener(listener);
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
