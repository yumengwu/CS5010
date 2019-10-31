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
 * This class represents the view when user do get portfolio operation.
 */
public class GetPortfolio extends JFrame implements ButtonOnly {
  private JButton getPortfolioDetail;
  private JButton getPortfolioDetailByDate;

  /**
   * Constructor of GetPortfolio, it initializes the GerPortfolio page.
   * @param caption caption.
   */
  public GetPortfolio(String caption) {
    super(caption);
    getPortfolioDetail = new JButton("Get Portfolio Detail");
    getPortfolioDetail.setActionCommand("get portfolio detail");
    getPortfolioDetailByDate = new JButton("Get Portfolio Detail By Date");
    getPortfolioDetailByDate.setActionCommand("get portfolio detail by date");
    JPanel panel = new JPanel();
    panel.add(getPortfolioDetail);
    panel.add(getPortfolioDetailByDate);
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
    getPortfolioDetail.addActionListener(listener);
    getPortfolioDetailByDate.addActionListener(listener);
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
