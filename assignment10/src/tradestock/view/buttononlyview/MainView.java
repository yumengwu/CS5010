package tradestock.view.buttononlyview;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This class represents the main view.
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
  private JButton add;
  private JButton createStrategy;
  private JButton applyStrategy;
  private JButton quit;
  private JButton readStrategy;
  private JButton saveStrategy;

  /**
   * Constructor of MainPlusView, it initialize the mainPlus view.
   *
   * @param str caption.
   */
  public MainView(String str) {
    super(str);
    this.setPreferredSize(new Dimension(450, 500));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);

    GridLayout layout = new GridLayout(8, 2);
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
    add = new JButton("Add Stock To Portfolio");
    createStrategy = new JButton("Create Strategy");
    applyStrategy = new JButton("Apply Strategy");
    readStrategy = new JButton("Read Strategy from file");
    saveStrategy = new JButton("Save Strategy to file");

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
    add.setActionCommand("add portfolio");
    createStrategy.setActionCommand("create strategy");
    applyStrategy.setActionCommand("apply strategy");
    readStrategy.setActionCommand("read strategy");
    saveStrategy.setActionCommand("save strategy");

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
    panel.add(saveStrategy);
    panel.add(readStrategy);
    panel.add(add);
    panel.add(createStrategy);
    panel.add(applyStrategy);
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
    add.addActionListener(listener);
    createStrategy.addActionListener(listener);
    applyStrategy.addActionListener(listener);
    readStrategy.addActionListener(listener);
    saveStrategy.addActionListener(listener);
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
