package tradestock.view.ButtonOnlyView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import tradestock.model.stock.IStock;

/**
 * This class represents a ShowGetPortfolioDetailByDate View that show given portfolio detail at
 * a given date.
 */
public class ShowGetPortfolioDetailByDate extends JFrame implements ButtonOnly {
  private JLabel label;
  private JButton home;

  /**
   * Constructor of ShowGetPortfolioDetailByDate. It initialize the show portfolio at a certain
   * date page.
   * @param caption caption.
   * @param list the list of all stocks information includes its symbol and number of shares
   *     that contains in a given portfolio and at a certain date.
   * @param portfolioName A given portfolio name.
   */
  public ShowGetPortfolioDetailByDate(String caption, List<IStock> list, String portfolioName ) {
    super(caption);
    this.setPreferredSize(new Dimension(450, 500));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel panel = new JPanel();
    JPanel buttonPanel = new JPanel();
    StringBuilder sb = new StringBuilder();
    if (list != null) {

      sb.append("<html> Following is detail of " + portfolioName + "<br/>");
      for (IStock stock : list) {
        sb.append(stock.getStockSymbol());
        sb.append(": ");
        sb.append(stock.getNumber());
        sb.append("<br/>");
      }
      sb.append("</html>");
    }
    else {
      sb.append("Portfolio " + portfolioName + " doesn't exist ");
    }
    label = new JLabel(sb.toString());
    home = new JButton("Home");
    home.setActionCommand("show get portfolio detail by date home");

    panel.add(label);
    buttonPanel.add(home);

    this.add(panel, BorderLayout.PAGE_START);
    this.add(buttonPanel, BorderLayout.PAGE_END);
    this.setVisible(true);
    this.pack();
  }

  /**
   * Add the provided listener.
   * @param listener provided listener.
   */
  @Override
  public void addActionListener(ActionListener listener) {
    home.addActionListener(listener);
  }

  /**
   * Reset the focus on the appropriate part of the view.
   */
  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }
}
