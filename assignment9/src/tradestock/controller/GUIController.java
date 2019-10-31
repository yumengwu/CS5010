package tradestock.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.time.LocalDate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import tradestock.model.getprice.GetPrice;
import tradestock.model.getprice.MockGetPrice;
import tradestock.model.stock.IStock;
import tradestock.model.stock.Stock;
import tradestock.model.tradeoperation.TradeOperation;

import tradestock.view.ButtonOnlyView.ButtonOnly;
import tradestock.view.ButtonOnlyView.CheckCost;
import tradestock.view.ButtonOnlyView.CheckValue;
import tradestock.view.ButtonOnlyView.ChooseAWayToBuyStock;
import tradestock.view.ButtonOnlyView.GetPortfolio;
import tradestock.view.ButtonOnlyView.MainView;
import tradestock.view.ButtonOnlyView.SetAPI;
import tradestock.view.ButtonOnlyView.ShowAllPortfolios;
import tradestock.view.ButtonOnlyView.ShowGetPortfolioDetail;
import tradestock.view.ButtonOnlyView.ShowGetPortfolioDetailByDate;
import tradestock.view.TextFieldView.BuyStockMoney;
import tradestock.view.TextFieldView.BuyStockView;
import tradestock.view.TextFieldView.CheckCostByDateView;
import tradestock.view.TextFieldView.CheckCostView;
import tradestock.view.TextFieldView.CheckValueByDateView;
import tradestock.view.TextFieldView.CheckValueView;
import tradestock.view.TextFieldView.CreatePortfolio;
import tradestock.view.TextFieldView.GetPortfolioDetailByDateView;
import tradestock.view.TextFieldView.GetPortfolioDetailView;
import tradestock.view.TextFieldView.ReadPortfolio;
import tradestock.view.TextFieldView.SavePortfolio;
import tradestock.view.TextFieldView.SetCommissionFee;
import tradestock.view.TextFieldView.WithTextField;

/**
 * This is a Trade controller control a trade program with a GUI. It contains a execute method to
 * run the program.
 */
public class GUIController implements IController<Stock>, ActionListener {
  private TradeOperation<Stock> model;
  private ButtonOnly mainView;
  private ButtonOnly showPortfolioView;
  private ButtonOnly showGetPortfolioDetail;
  private ButtonOnly showGetPortfolioDetailByDate;
  private ButtonOnly checkCostBoth;
  private ButtonOnly checkValueBoth;
  private ButtonOnly getPortfolio;
  private ButtonOnly setAPI;
  private ButtonOnly buyStockChooseAWay;
  private WithTextField createView;
  private WithTextField getPortfolioDetailView;
  private WithTextField getPortfolioDetailByDateView;
  private WithTextField buyStock;
  private WithTextField buyStockMoney;
  private WithTextField checkCost;
  private WithTextField checkCostByDate;
  private WithTextField checkValue;
  private WithTextField checkValueByDate;
  private WithTextField setCommissionFee;
  private WithTextField savePortfolio;
  private WithTextField readPortfolio;
  private String str;
  private Map<String, Runnable> actionMap;

  /**
   * Constructor of GUIController, it initializes the controller include model and view that
   * use to run the program.
   * @param m model that uses to run the program.
   * @param v view that uses to run the program.
   */
  public GUIController(TradeOperation m, ButtonOnly v) {
    this.model = m;
    this.mainView = v;
    mainView.addActionListener(this);
    str = "";
  }

  /**
   * Private method that initialize the action map.
   * @return the action map.
   */
  private Map<String, Runnable> initializeMap() {
    Map<String, Runnable> actionMap = new HashMap<>();
    actionMap.put("createPortfolio", () -> {
      createView = new CreatePortfolio("create portfolio");
      createView.addActionListener(this);
      ((JFrame) createView).setLocation(((JFrame) this.mainView).getLocation());
      ((JFrame) this.mainView).dispose();
    });

    actionMap.put("showAllPortfolio", () -> {
      List<String> list = model.getPortfolioList();
      showPortfolioView = new ShowAllPortfolios("show portfolios", list);
      showPortfolioView.addActionListener(this);
      ((JFrame) showPortfolioView).setLocation(((JFrame) this.mainView).getLocation());
      ((JFrame) this.mainView).dispose();
    });

    actionMap.put("getPortfolio", () -> {
      getPortfolio = new GetPortfolio("Get Portfolio");
      getPortfolio.addActionListener(this);
      ((JFrame) getPortfolio).setLocation(((JFrame) this.mainView).getLocation());
      ((JFrame) this.mainView).dispose();
    });

    actionMap.put("get portfolio detail", () -> {
      getPortfolioDetailView = new GetPortfolioDetailView("Get Portfolio Detail");
      getPortfolioDetailView.addActionListener(this);
      ((JFrame) getPortfolioDetailView).setLocation(((JFrame) this.mainView).getLocation());
      ((JFrame) this.getPortfolio).dispose();
    });

    actionMap.put("get portfolio detail by date", () -> {
      getPortfolioDetailByDateView = new GetPortfolioDetailByDateView("Get Portfolio Detail "
          + "By Date");
      getPortfolioDetailByDateView.addActionListener(this);
      ((JFrame) getPortfolioDetailByDateView).setLocation(((JFrame) this.mainView).getLocation());
      ((JFrame) this.getPortfolio).dispose();
    });

    actionMap.put("buyStockChooseAWay", () -> {
      buyStockChooseAWay = new ChooseAWayToBuyStock("Choose One Way To Buy");
      buyStockChooseAWay.addActionListener(this);
      ((JFrame) buyStockChooseAWay).setLocation(((JFrame) this.mainView).getLocation());
      ((JFrame) this.mainView).dispose();
    });

    actionMap.put("buy by number", () -> {
      buyStock = new BuyStockView("Buy");
      buyStock.addActionListener(this);
      ((JFrame) buyStock).setLocation(((JFrame) this.buyStockChooseAWay).getLocation());
      ((JFrame) this.buyStockChooseAWay).dispose();
    });

    actionMap.put("buy by money", () -> {
      buyStockMoney = new BuyStockMoney("Buy");
      buyStockMoney.addActionListener(this);
      ((JFrame) buyStockMoney).setLocation(((JFrame) this.buyStockChooseAWay).getLocation());
      ((JFrame) this.buyStockChooseAWay).dispose();
    });


    actionMap.put("checkCost", () -> {
      checkCostBoth = new CheckCost("Check Cost");
      checkCostBoth.addActionListener(this);
      ((JFrame) checkCostBoth).setLocation(((JFrame) this.mainView).getLocation());
      ((JFrame) this.mainView).dispose();
    });

    actionMap.put("check cost check cost basis", () -> {
      checkCost = new CheckCostView("Check Cost Basis");
      checkCost.addActionListener(this);
      ((JFrame) checkCost).setLocation(((JFrame) this.mainView).getLocation());
      ((JFrame) this.checkCostBoth).dispose();
    });

    actionMap.put("check cost check cost basis by date", () -> {
      checkCostByDate = new CheckCostByDateView("Check Cost Basis");
      checkCostByDate.addActionListener(this);
      ((JFrame) checkCostByDate).setLocation(((JFrame) this.mainView).getLocation());
      ((JFrame) this.checkCostBoth).dispose();
    });

    actionMap.put("checkValue", () -> {
      checkValueBoth = new CheckValue("Check Value");
      checkValueBoth.addActionListener(this);
      ((JFrame) checkValueBoth).setLocation(((JFrame) this.mainView).getLocation());
      ((JFrame) this.mainView).dispose();
    });

    actionMap.put("check value check total value", () -> {
      checkValue = new CheckValueView("Check Total Value");
      checkValue.addActionListener(this);
      ((JFrame) checkValue).setLocation(((JFrame) this.mainView).getLocation());
      ((JFrame) this.checkValueBoth).dispose();
    });

    actionMap.put("check value check total value by date", () -> {
      checkValueByDate = new CheckValueByDateView("Check Total Value");
      checkValueByDate.addActionListener(this);
      ((JFrame) checkValueByDate).setLocation(((JFrame) this.mainView).getLocation());
      ((JFrame) this.checkValueBoth).dispose();
    });

    actionMap.put("setCommissionFee", () -> {
      setCommissionFee = new SetCommissionFee("Set Commission Fee");
      setCommissionFee.addActionListener(this);
      ((JFrame) setCommissionFee).setLocation(((JFrame) this.mainView).getLocation());
      ((JFrame) this.mainView).dispose();
    });

    actionMap.put("setAPI", () -> {
      setAPI = new SetAPI("Set API");
      setAPI.addActionListener(this);
      ((JFrame) setAPI).setLocation(((JFrame) this.mainView).getLocation());
      ((JFrame) this.mainView).dispose();
    });

    actionMap.put("savePortfolioToFile", () -> {
      savePortfolio = new SavePortfolio("Save Portfolio");
      savePortfolio.addActionListener(this);
      ((JFrame) savePortfolio).setLocation(((JFrame) this.mainView).getLocation());
      ((JFrame) this.mainView).dispose();
    });

    actionMap.put("readPortfolioFromFile", () -> {
      readPortfolio = new ReadPortfolio("Read Portfolio");
      readPortfolio.addActionListener(this);
      ((JFrame) readPortfolio).setLocation(((JFrame) this.mainView).getLocation());
      ((JFrame) this.mainView).dispose();
    });

    actionMap.put("quit", () -> {
      System.exit(0);
    });

    actionMap.put("create", () -> {
      String portfolioName = createView.getInput();
      if (portfolioName.length() == 0) {
        createView.setHintMess("empty portfolio name");
        return;
      }
      try {
        model.createPortfolio(portfolioName);
        createView.setHintMess("Portfolio " + portfolioName + " created.");
        createView.clearField();
      }
      catch (IllegalArgumentException e) {
        createView.setHintMess(e.getMessage());
      }
    });

    actionMap.put("get portfolio detail check", () -> {
      String portfolioName = getPortfolioDetailView.getInput();
      if (portfolioName.length() == 0) {
        getPortfolioDetailView.setHintMess("Empty portfolio name");
        return;
      }
      try {
        List<IStock> list = model.getPortfolioState(portfolioName);
        showGetPortfolioDetail = new ShowGetPortfolioDetail("Show Detail of Portfolio",
            list, portfolioName);
      }
      catch (IllegalArgumentException e) {
        showGetPortfolioDetail = new ShowGetPortfolioDetail("Show Detail of Portfolio",
            null, portfolioName);
      }
      showGetPortfolioDetail.addActionListener(this);
      ((JFrame) showGetPortfolioDetail).setLocation(((JFrame) this.getPortfolioDetailView).getLocation());
      ((JFrame) this.getPortfolioDetailView).dispose();
    });

    actionMap.put("get portfolio detail by date check", () -> {
      String string = getPortfolioDetailByDateView.getInput();
      int index = string.indexOf("\n");
      String portfolioName = string.substring(0, index);
      if (portfolioName.length() == 0) {
        getPortfolioDetailByDateView.setHintMess("empty portfolio name");
        return;
      }

      String dateString = string.substring(index + 1);
      LocalDate date;
      try {
        date = LocalDate.parse(dateString);
      }
      catch (Exception e) {
        getPortfolioDetailByDateView.setHintMess("Invalid date");
        return;
      }
      try {
        List<IStock> list = model.getPortfolioStateByDate(portfolioName, date);
        showGetPortfolioDetailByDate = new ShowGetPortfolioDetailByDate("Show Detail "
            + "of Portfolio", list, portfolioName);
      }
      catch (IllegalArgumentException e) {
        showGetPortfolioDetailByDate = new ShowGetPortfolioDetailByDate("Show Detail "
            + "of Portfolio", null, portfolioName);
      }
      showGetPortfolioDetailByDate.addActionListener(this);
      ((JFrame) showGetPortfolioDetailByDate).setLocation(((JFrame)
          this.getPortfolioDetailByDateView).getLocation());
      ((JFrame) this.getPortfolioDetailByDateView).dispose();
    });

    actionMap.put("buy", () -> {
      String string = buyStock.getInput();
      int index = string.indexOf("\n");
      String stockSymbol = string.substring(0, index);
      if (stockSymbol.length() == 0) {
        buyStock.setHintMess("empty stock symbol");
        return;
      }
      string = string.substring(index + 1);
      index = string.indexOf("\n");
      String volume = string.substring(0, index);
      if (volume.length() == 0 || !volume.matches("[1-9][\\d]*")) {
        buyStock.setHintMess("invalid volume");
        return;
      }
      string = string.substring(index + 1);
      index = string.indexOf("\n");
      String dateString = string.substring(0, index);
      LocalDate date;
      try {
        date = LocalDate.parse(dateString);
      }
      catch (Exception e) {
        buyStock.setHintMess("Invalid date");
        return;
      }
      String portfolioName = string.substring(index + 1, string.length() - 1);
      String message = "";
      try {
        model.buy(portfolioName, stockSymbol, date, Double.parseDouble(volume));
        message = "Buy " + volume + " shares of " + stockSymbol + " to portfolio "
            + portfolioName + " at " + date;

      }
      catch (IllegalArgumentException e) {
        message = e.getMessage();
      }
      buyStock.clearField();
      buyStock.setHintMess(message);
    });

    actionMap.put("check cost view check", () -> {
      String portfolioName = checkCost.getInput();
      if (portfolioName.length() == 0) {
        checkCost.setHintMess("empty portfolio name");
        return;
      }
      String message;
      try {
        double cost = model.getTotalCostBasis(portfolioName);
        message = "Total cost basis of " + portfolioName + " by now is $" + cost;
      }
      catch (IllegalArgumentException e) {
        message = e.getMessage();
      }
      checkCost.clearField();
      checkCost.setHintMess(message);
    });

    actionMap.put("check cost by date view check", () -> {
      String string = checkCostByDate.getInput();
      int index = string.indexOf("\n");
      String portfolioName = string.substring(0, index);
      if (portfolioName.length() == 0) {
        checkCostByDate.setHintMess("empty portfolio name");
        return;
      }
      String dateString = string.substring(index + 1);
      LocalDate date;
      try {
        date = LocalDate.parse(dateString);
      }
      catch (Exception e) {
        checkCostByDate.setHintMess("Invalid date");
        return;
      }
      String message;
      try {
        double cost = model.getTotalCostBasisByDate(portfolioName, date);
        message = "Total cost of portfolio " + portfolioName + " by " + date  + " is $" + cost;
      }
      catch (IllegalArgumentException e) {
        message = e.getMessage();
      }
      checkCostByDate.clearField();
      checkCostByDate.setHintMess(message);
    });

    actionMap.put("check value view check", () -> {
      String portfolioName = checkValue.getInput();
      if (portfolioName.length() == 0) {
        checkValue.setHintMess("empty portfolio name");
        return;
      }
      String message;
      try {
        double value = model.getTotalValue(portfolioName);
        message = "Total value of portfolio " + portfolioName + " by now is $" + value;
      }
      catch (IllegalArgumentException e) {
        message = e.getMessage();
      }
      checkValue.clearField();
      checkValue.setHintMess(message);
    });

    actionMap.put("check value by date view check", () -> {
      String str = checkValueByDate.getInput();
      int index = str.indexOf("\n");
      String portfolioName = str.substring(0, index);
      if (portfolioName.length() == 0) {
        checkValueByDate.setHintMess("empty portfolio name");
        return;
      }
      String dateString = str.substring(index + 1);
      LocalDate date;
      try {
        date = LocalDate.parse(dateString);
      }
      catch (Exception e) {
        checkValueByDate.setHintMess("Invalid date");
        return;
      }
      String message;
      try {
        double value = model.getTotalValueByDate(portfolioName, date);
        message = "Total value of portfolio " + portfolioName + " by " + date  + " is $" + value;
      }
      catch (IllegalArgumentException e) {
        message = e.getMessage();
      }
      checkValueByDate.clearField();
      checkValueByDate.setHintMess(message);
    });

    actionMap.put("set commission fee set", () -> {
      String fee = setCommissionFee.getInput();
      if (fee.length() == 0) {
        setCommissionFee.setHintMess("Empty Commission Fee");
        return;
      }
      if (!fee.matches("(([1-9][\\d]*|[0])\\.[\\d]*)|([1-9][\\d]*)")) {
        setCommissionFee.setHintMess("Invalid Commission Fee");
        return;
      }
      double price = Double.parseDouble(fee);
      model.setCommissionFee(price);
      String message = "Commission fee set as $" + price;
      setCommissionFee.setHintMess(message);
    });

    actionMap.put("alpha vantage", () -> {
      model.setAPI(new GetPrice());
      mainView = new MainView("Home");
      mainView.addActionListener(this);
      ((JFrame) mainView).setLocation(((JFrame) this.setAPI).getLocation());
      ((JFrame) this.setAPI).dispose();
    });

    actionMap.put("mock api", () -> {
      model.setAPI(new MockGetPrice());
      mainView = new MainView("Home");
      mainView.addActionListener(this);
      ((JFrame) mainView).setLocation(((JFrame) this.setAPI).getLocation());
      ((JFrame) this.setAPI).dispose();
    });

    actionMap.put("save portfolio save", () -> {
      String input = savePortfolio.getInput();
      String portfolioName;
      String fileName;
      int index = input.indexOf("\n");
      portfolioName = input.substring(0, index);
      fileName = input.substring(index + 1);
      String message;
      if (portfolioName.length() == 0) {
        message = "Empty portfolio name";
        savePortfolio.setHintMess(message);
        return;
      }
      if (fileName.length() == 0) {
        message = "Empty file name";
        savePortfolio.setHintMess(message);
        return;
      }
      model.savePortfolio(portfolioName, fileName);
      message = "Save portfolio " + portfolioName + " to file " + fileName;
      savePortfolio.setHintMess(message);
    });

    actionMap.put("read portfolio read", () -> {
      String fileName = readPortfolio.getInput();
      String message;
      if (fileName.length() == 0) {
        message = "Empty file name";
        readPortfolio.setHintMess(message);
        return;
      }
      try {
        model.readPortfolio(fileName);
        message = "Read " + fileName;
      }
      catch (Exception e) {
        message = e.getMessage();
      }
      readPortfolio.setHintMess(message);
    });

    actionMap.put("buy stock money buy", () -> {
      String string = buyStockMoney.getInput();
      int index = string.indexOf("\n");
      String stockSymbol = string.substring(0, index);
      if (stockSymbol.length() == 0) {
        buyStockMoney.setHintMess("empty stock symbol");
        return;
      }
      string = string.substring(index + 1);
      index = string.indexOf("\n");
      String money = string.substring(0, index);
      if (money.length() == 0 || !money.matches("(([1-9][\\d]*|[0])\\.[\\d]*)|([1-9][\\d]*)"
          + "")) {
        buyStockMoney.setHintMess("Invalid money");
        return;
      }
      string = string.substring(index + 1);
      index = string.indexOf("\n");
      String dateString = string.substring(0, index);
      LocalDate date;
      try {
        date = LocalDate.parse(dateString);
      }
      catch (Exception e) {
        buyStockMoney.setHintMess("Invalid date");
        return;
      }
      String portfolioName = string.substring(index + 1, string.length() - 1);
      String message = "";
      try {
        model.buyAmount(portfolioName, stockSymbol, date, Double.parseDouble(money));
        message = "Buy $" + Integer.parseInt(money) + " of " + stockSymbol + " to portfolio "
            + portfolioName + " on " + date.toString() + "\n";

      }
      catch (IllegalArgumentException e) {
        message = e.getMessage();
      }
      buyStockMoney.clearField();
      buyStockMoney.setHintMess(message);
    });

    actionMap.put("show get portfolio detail home", () -> {
      mainView = new MainView("Home");
      mainView.addActionListener(this);
      ((JFrame) mainView).setLocation(((JFrame) this.showGetPortfolioDetail).getLocation());
      ((JFrame) this.showGetPortfolioDetail).dispose();
    });

    actionMap.put("create portfolio home", () -> {
      mainView = new MainView("Home");
      mainView.addActionListener(this);
      ((JFrame) mainView).setLocation(((JFrame) this.createView).getLocation());
      ((JFrame) this.createView).dispose();
    });

    actionMap.put("show all portfolios home", () -> {
      mainView = new MainView("Home");
      mainView.addActionListener(this);
      ((JFrame) mainView).setLocation(((JFrame) this.showPortfolioView).getLocation());
      ((JFrame) this.showPortfolioView).dispose();
    });

    actionMap.put("show get portfolio detail by date home", () -> {
      mainView = new MainView("Home");
      mainView.addActionListener(this);
      ((JFrame) mainView).setLocation(((JFrame) this.showGetPortfolioDetailByDate).getLocation());
      ((JFrame) this.showGetPortfolioDetailByDate).dispose();
    });

    actionMap.put("get portfolio detail by date home", () -> {
      mainView = new MainView("Home");
      mainView.addActionListener(this);
      ((JFrame) mainView).setLocation(((JFrame) this.getPortfolioDetailByDateView).getLocation());
      ((JFrame) this.getPortfolioDetailByDateView).dispose();
    });

    actionMap.put("get portfolio detail home", () -> {
      mainView = new MainView("Home");
      mainView.addActionListener(this);
      ((JFrame) mainView).setLocation(((JFrame) this.getPortfolioDetailView).getLocation());
      ((JFrame) this.getPortfolioDetailView).dispose();
    });


    actionMap.put("buy stock home", () -> {
      mainView = new MainView("Home");
      mainView.addActionListener(this);
      ((JFrame) mainView).setLocation(((JFrame) this.buyStock).getLocation());
      ((JFrame) this.buyStock).dispose();
    });

    actionMap.put("check cost view home", () -> {
      mainView = new MainView("Home");
      mainView.addActionListener(this);
      ((JFrame) mainView).setLocation(((JFrame) this.checkCost).getLocation());
      ((JFrame) this.checkCost).dispose();
    });

    actionMap.put("check cost by date view home", () -> {
      mainView = new MainView("Home");
      mainView.addActionListener(this);
      ((JFrame) mainView).setLocation(((JFrame) this.checkCostByDate).getLocation());
      ((JFrame) this.checkCostByDate).dispose();
    });

    actionMap.put("check value view home", () -> {
      mainView = new MainView("Home");
      mainView.addActionListener(this);
      ((JFrame) mainView).setLocation(((JFrame) this.checkValue).getLocation());
      ((JFrame) this.checkValue).dispose();
    });

    actionMap.put("check value by date view home", () -> {
      mainView = new MainView("Home");
      mainView.addActionListener(this);
      ((JFrame) mainView).setLocation(((JFrame) this.checkValueByDate).getLocation());
      ((JFrame) this.checkValueByDate).dispose();
    });

    actionMap.put("set commission fee home", () -> {
      mainView = new MainView("Home");
      mainView.addActionListener(this);
      ((JFrame) mainView).setLocation(((JFrame) this.setCommissionFee).getLocation());
      ((JFrame) this.setCommissionFee).dispose();
    });

    actionMap.put("save portfolio home", () -> {
      mainView = new MainView("Home");
      mainView.addActionListener(this);
      ((JFrame) mainView).setLocation(((JFrame) this.savePortfolio).getLocation());
      ((JFrame) this.savePortfolio).dispose();
    });

    actionMap.put("read portfolio home", () -> {
      mainView = new MainView("Home");
      mainView.addActionListener(this);
      ((JFrame) mainView).setLocation(((JFrame) this.readPortfolio).getLocation());
      ((JFrame) this.readPortfolio).dispose();
    });

    actionMap.put("buy stock money home", () -> {
      mainView = new MainView("Home");
      mainView.addActionListener(this);
      ((JFrame) mainView).setLocation(((JFrame) this.buyStockMoney).getLocation());
      ((JFrame) this.buyStockMoney).dispose();
    });

    return actionMap;
  }

  /**
   * Method that use to run the program.
   */
  @Override
  public void execute() {
    actionMap = initializeMap();
  }

  /**
   * Detect when user click a button, then conduct the user to appropriate operation.
   * @param e An action event.
   */
  @Override
  public void actionPerformed (ActionEvent e) {
    this.str = e.getActionCommand();
    actionMap.get(str).run();
  }
}
