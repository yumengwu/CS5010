package tradestock.view.ButtonOnlyView;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This class is the main view (home view) of the program. It contains operation buttons that
 * guide user to operate.
 */
public class MainView extends JFrame implements ButtonOnly {
  private JButton createPortfolio;

  private JButton checkCost;
  private JButton checkValue;
  private JButton getPortfolio;
  private JButton showAllPortfolio;
  private JButton buyStock;
  private JButton setCommissionFee;
  private JButton setAPI;
  private JButton savePortfolioToFile;
  private JButton readPortfolioFromFile;
  private JButton quit;

  /**
   * Constructor of MainView, it initialize the main view.
   * @param str caption.
   */
  public MainView (String str) {
    super(str);
    this.setPreferredSize(new Dimension(450, 500));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);

    GridLayout layout = new GridLayout(6, 2);
    JPanel panel;
    panel = new JPanel();
    panel.setLayout(layout);

    createPortfolio = new JButton("Create Portfolio");
    showAllPortfolio = new JButton("Show All Portfolio");
    getPortfolio = new JButton("Get Portfolio");
    buyStock = new JButton("Buy Stock");
    quit = new JButton("Quit");
    checkCost = new JButton("Check Cost");
    checkValue = new JButton("Check Value");
    setCommissionFee = new JButton("Set Commission Fee");
    setAPI = new JButton("Set API");
    savePortfolioToFile = new JButton("Save Portfolio To File");
    readPortfolioFromFile = new JButton("Read Portfolio From File");

    createPortfolio.setActionCommand("createPortfolio");
    showAllPortfolio.setActionCommand("showAllPortfolio");
    buyStock.setActionCommand("buyStockChooseAWay");
    getPortfolio.setActionCommand("getPortfolio");
    checkCost.setActionCommand("checkCost");
    checkValue.setActionCommand("checkValue");
    setCommissionFee.setActionCommand("setCommissionFee");
    setAPI.setActionCommand("setAPI");
    savePortfolioToFile.setActionCommand("savePortfolioToFile");
    readPortfolioFromFile.setActionCommand("readPortfolioFromFile");
    quit.setActionCommand("quit");

    panel.add(createPortfolio);
    panel.add(showAllPortfolio);
    panel.add(getPortfolio);
    panel.add(buyStock);
    panel.add(checkCost);
    panel.add(checkValue);
    panel.add(setCommissionFee);
    panel.add(setAPI);
    panel.add(savePortfolioToFile);
    panel.add(readPortfolioFromFile);
    panel.add(quit);

    this.getContentPane().add(panel);
    this.setVisible(true);
    this.pack();
  }

  /**
   * Add the provided listener.
   * @param listener provided listener.
   */
  @Override
  public void addActionListener(ActionListener listener) {
    createPortfolio.addActionListener(listener);
    showAllPortfolio.addActionListener(listener);
    buyStock.addActionListener(listener);
    quit.addActionListener(listener);
    checkCost.addActionListener(listener);
    checkValue.addActionListener(listener);
    getPortfolio.addActionListener(listener);
    setCommissionFee.addActionListener(listener);
    savePortfolioToFile.addActionListener(listener);
    readPortfolioFromFile.addActionListener(listener);
    setAPI.addActionListener(listener);
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
