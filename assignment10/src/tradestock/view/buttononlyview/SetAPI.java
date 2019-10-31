package tradestock.view.buttononlyview;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * The class represents the view when user do set API operation.
 */
public class SetAPI extends JFrame implements ButtonOnly {
  private JButton alphaVantage;
  private JButton defaultApi;

  /**
   * Constructor of SetAPI, it initializes the set API view.
   * @param caption caption.
   */
  public SetAPI(String caption) {
    super(caption);
    alphaVantage = new JButton("Alpha Vantage API");
    alphaVantage.setActionCommand("alpha vantage");
    defaultApi = new JButton("Mock API");
    defaultApi.setActionCommand("mock api");
    JPanel panel = new JPanel();
    panel.add(alphaVantage);
    panel.add(defaultApi);
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
  public void addActionListener(ActionListener listener) {
    alphaVantage.addActionListener(listener);
    defaultApi.addActionListener(listener);
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
