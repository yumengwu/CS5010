package tradestock.view.ButtonOnlyView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class represents the view that show all portfolios.
 */
public class ShowAllPortfolios extends JFrame implements ButtonOnly {

  private JLabel label;
  private JButton home;
  private List<String> list;

  /**
   * Constructor of ShowAllPortfolios. It initialize the ShowAllPortfolios View.
   * @param caption caption.
   * @param list the portfolio list that contains all portfolios.
   */
  public ShowAllPortfolios(String caption, List<String> list) {
    super(caption);
    this.list = list;
    this.setPreferredSize(new Dimension(450, 500));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    JPanel panel = new JPanel();
    JPanel buttonPanel = new JPanel();

    String str = "Following are all portfolios: " + "<br/>";
    for (String s : list) {
      str = str + s;
      str = str + "<br/>";
    }
    str = "<html>"+ str + "</html>";
    label = new JLabel(str);
    panel.add(label, BorderLayout.PAGE_START);

    home = new JButton("home");
    home.setActionCommand("show all portfolios home");
    buttonPanel.add(home, BorderLayout.SOUTH);
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
