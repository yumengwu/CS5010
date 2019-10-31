package tradestock.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import tradestock.model.getprice.GetPrice;
import tradestock.model.getprice.MockGetPrice;
import tradestock.model.stock.IStock;
import tradestock.model.stock.Stock;
import tradestock.model.strategy.IStrategy;
import tradestock.model.tradeoperation.TradeOperation;

import tradestock.view.advancedview.IAdvancedView;
import tradestock.view.advancedview.ReadStrategy;
import tradestock.view.advancedview.SaveStrategy;
import tradestock.view.buttononlyview.ButtonOnly;
import tradestock.view.buttononlyview.CheckCost;
import tradestock.view.buttononlyview.CheckValue;
import tradestock.view.buttononlyview.ChooseAWayToBuyStock;
import tradestock.view.buttononlyview.ChooseStrategyWithEndDateOrNot;
import tradestock.view.buttononlyview.GetPortfolio;
import tradestock.view.buttononlyview.MainView;
import tradestock.view.buttononlyview.SetAPI;
import tradestock.view.buttononlyview.ShowAllPortfolios;
import tradestock.view.buttononlyview.ShowGetPortfolioDetail;
import tradestock.view.buttononlyview.ShowGetPortfolioDetailByDate;
import tradestock.view.comboboxview.ApplyStrategyToPortfolio;
import tradestock.view.comboboxview.ComboBoxView;
import tradestock.view.comboboxview.SaveStrategyChooseStrategy;
import tradestock.view.helperclass.HelperClass;
import tradestock.view.textfieldview.AddStock;
import tradestock.view.textfieldview.BuyStockMoney;
import tradestock.view.textfieldview.BuyStockView;
import tradestock.view.textfieldview.CheckCostByDateView;
import tradestock.view.textfieldview.CheckCostView;
import tradestock.view.textfieldview.CheckValueByDateView;
import tradestock.view.textfieldview.CheckValueView;
import tradestock.view.textfieldview.CreatePortfolio;
import tradestock.view.textfieldview.CreateStrategyInputStrategyBasic;
import tradestock.view.textfieldview.DiffPercentageEndBasic;
import tradestock.view.textfieldview.DiffPercentageNoEndBasic;
import tradestock.view.textfieldview.EqualPercentageNoEndBasic;
import tradestock.view.textfieldview.GetPortfolioDetailByDateView;
import tradestock.view.textfieldview.GetPortfolioDetailView;
import tradestock.view.advancedview.ReadPortfolio;
import tradestock.view.advancedview.SavePortfolio;
import tradestock.view.comboboxview.SavePortfolioChoosePortfolio;
import tradestock.view.textfieldview.SetCommissionFee;
import tradestock.view.textfieldview.UseDiffPercentageEnd;
import tradestock.view.textfieldview.UseDiffPercentageNoEnd;
import tradestock.view.textfieldview.UseEqualPercentageEnd;
import tradestock.view.textfieldview.UseEqualsPercentage;
import tradestock.view.textfieldview.WithTextField;

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
  private ButtonOnly chooseWithEndDateOrNot;
  private WithTextField addStock;
  private IAdvancedView savePortfolio;
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
  private ComboBoxView saveChoosePortfolio;
  private ComboBoxView saveChooseStrategy;
  private WithTextField equalPercentageNoEndBasic;
  private WithTextField diffPercentageNoEndBasic;
  private WithTextField useDiffPercentageNoEnd;
  private WithTextField createStrategyInputStrategyBasic;
  private WithTextField useEqualsPercentage;
  private WithTextField useEqualPercentageEnd;
  private WithTextField diffPercentageEndBasic;
  private WithTextField useDifferentPercentageEnd;
  private ComboBoxView applyStrategy;
  private IAdvancedView readPortfolio;
  private IAdvancedView readStrategy;
  private IAdvancedView saveStrategy;
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
   * Initialize the home page action map.
   * @param actionMap the action map of this controller.
   */
  private void initializeCreatePortfolio(Map<String, Runnable> actionMap) {
    actionMap.put("createPortfolio", () -> {
      createView = new CreatePortfolio("create portfolio");
      HelperClass.disposeTheSecondSetFirstView(createView, mainView, this);
    });
    actionMap.put("create", () -> {
      String portfolioName = createView.getInput().get(0);
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
    actionMap.put("create portfolio home", () -> {
      mainView = new MainView("Home");
      HelperClass.disposeTheFirstSetSecond(mainView, createView, this);
    });
  }

  /**
   * Private method that initialize the action map.
   * @return the action map.
   */
  private Map<String, Runnable> initializeMap() {
    Map<String, Runnable> actionMap = new HashMap<>();
    initializeCreatePortfolio(actionMap);

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
      List<String> allPortfolio = model.getPortfolioList();
      saveChoosePortfolio = new SavePortfolioChoosePortfolio("Choose Portfolio", allPortfolio);
      saveChoosePortfolio.addActionListener(this);
      ((JFrame) saveChoosePortfolio).setLocation(((JFrame) this.mainView).getLocation());
      ((JFrame) this.mainView).dispose();
    });

    actionMap.put("readPortfolioFromFile", () -> {
      ((JFrame) this.mainView).hide();
      readPortfolio = new ReadPortfolio("Read Portfolio");
      readPortfolio.addActionListener(this);
      ((JFrame) readPortfolio).setLocation(((JFrame) this.mainView).getLocation());
      String fileName = "";
      if (readPortfolio.getFileChooser() == JFileChooser.APPROVE_OPTION) {
        fileName = readPortfolio.getInput();
        try {
          model.readPortfolio(fileName);
          readPortfolio.setHint("Read " + fileName);
        }
        catch (IllegalArgumentException e) {
          readPortfolio.setHint(e.getMessage());
        }
      }
      else {
        mainView = new MainView("Home");
        mainView.addActionListener(this);
        ((JFrame) mainView).setLocation(((JFrame) this.readPortfolio).getLocation());
      }
    });

    actionMap.put("quit", () -> {
      System.exit(0);
    });

    actionMap.put("get portfolio detail check", () -> {
      String portfolioName = getPortfolioDetailView.getInput().get(0);
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
      ((JFrame) showGetPortfolioDetail).setLocation((
          (JFrame) this.getPortfolioDetailView).getLocation());
      ((JFrame) this.getPortfolioDetailView).dispose();
    });

    actionMap.put("get portfolio detail by date check", () -> {
      if (!HelperClass.checkInputWithTextField(getPortfolioDetailByDateView)) {
        return;
      }
      String portfolioName = getPortfolioDetailByDateView.getInput().get(0);
      String dateString = getPortfolioDetailByDateView.getInput().get(1);
      LocalDate date;
      date = LocalDate.parse(dateString);
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
      HelperClass.checkStockShareDate(buyStock);
      String stockSymbol = buyStock.getInput().get(0);
      String volume = buyStock.getInput().get(1);
      String dateString = buyStock.getInput().get(2);
      LocalDate date = LocalDate.parse(dateString);
      String portfolioName = buyStock.getInput().get(3);
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
      String portfolioName = checkCost.getInput().get(0);
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
      if (!HelperClass.checkInputWithTextField(checkCostByDate)) {
        return;
      }
      String portfolioName = checkCostByDate.getInput().get(0);
      String dateString = checkCostByDate.getInput().get(1);
      LocalDate date = LocalDate.parse(dateString);
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
      String portfolioName = checkValue.getInput().get(0);
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
      if (!HelperClass.checkInputWithTextField(checkValueByDate)) {
        return;
      }
      String portfolioName = checkValueByDate.getInput().get(0);
      String dateString = checkValueByDate.getInput().get(1);
      LocalDate date = LocalDate.parse(dateString);
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
      String fee = setCommissionFee.getInput().get(0);
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
      HelperClass.setViewLocation(mainView, setAPI, this);
    });

    actionMap.put("mock api", () -> {
      model.setAPI(new MockGetPrice());
      HelperClass.setViewLocation(mainView, setAPI, this);
    });

    actionMap.put("save portfolio choose portfolio", () -> {
      ((JFrame) saveChoosePortfolio).hide();
      savePortfolio = new SavePortfolio("Save");
      savePortfolio.addActionListener(this);
      ((JFrame) savePortfolio).setLocation(((JFrame) this.saveChoosePortfolio).getLocation());
      String portfolioName = saveChoosePortfolio.getInput().get(0);

      String message;
      if (savePortfolio.getFileChooser() == JFileChooser.APPROVE_OPTION) {
        String fileName = savePortfolio.getInput();
        model.savePortfolio(portfolioName, fileName);
        message = "save " + portfolioName + " to " + fileName;
        savePortfolio.setHint(message);
      }
      else {
        HelperClass.setViewLocationHelper(mainView, savePortfolio, this);
        ((JFrame) saveChoosePortfolio).dispose();
      }
    });

    actionMap.put("save portfolio ok", () -> {
      HelperClass.setViewLocationHelper(mainView, savePortfolio, this);
      ((JFrame) saveChoosePortfolio).dispose();
    });

    actionMap.put("buy stock money buy", () -> {
      if (!HelperClass.checkStockMoneyDate(buyStockMoney)) {
        return;
      }
      String stockSymbol = buyStockMoney.getInput().get(0);
      String money = buyStockMoney.getInput().get(1);
      String dateString = buyStockMoney.getInput().get(2);
      LocalDate date = LocalDate.parse(dateString);
      String portfolioName = buyStockMoney.getInput().get(3);
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

    actionMap.put("save portfolio choose portfolio home", () -> {
      mainView = new MainView("Home");
      mainView.addActionListener(this);
      ((JFrame) mainView).setLocation(((JFrame) this.saveChoosePortfolio).getLocation());
      ((JFrame) this.saveChoosePortfolio).dispose();
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

    /**
     * HW10 add
     */
     actionMap.put("add portfolio", () -> {
       addStock = new AddStock("Add Stock To Portfolio");
       addStock.addActionListener(this);
       ((JFrame) addStock).setLocation(((JFrame) this.mainView).getLocation());
       ((JFrame) this.mainView).dispose();
     });

     actionMap.put("add stock add", () -> {
       List<String> inputFromAdd = addStock.getInput();
       String portfolioName = inputFromAdd.get(0);
       if (portfolioName.length() == 0) {
         addStock.setHintMess("Empty Portfolio Name");
         addStock.clearField();
         return;
       }
       ArrayList<String> validStockName = new ArrayList<>();
       for (int i = 1; i < inputFromAdd.size(); i++) {
         if (inputFromAdd.get(i).length() != 0) {
           validStockName.add(inputFromAdd.get(i));
         }
       }
       for (int i = 0; i < validStockName.size(); i++) {
         model.addStock(portfolioName, validStockName.get(i));
       }
       addStock.setHintMess(validStockName.size() + " stock(s) is added to " + portfolioName);
       addStock.clearField();
     });

     actionMap.put("add stock home", () -> {
       mainView = new MainView("Home");
       mainView.addActionListener(this);
       ((JFrame) mainView).setLocation(((JFrame) this.addStock).getLocation());
       ((JFrame) this.addStock).dispose();
     });

     actionMap.put("create strategy", () -> {
       chooseWithEndDateOrNot = new ChooseStrategyWithEndDateOrNot("With An End Date?");
       chooseWithEndDateOrNot.addActionListener(this);
       ((JFrame) chooseWithEndDateOrNot).setLocation(((JFrame) this.mainView).getLocation());
       ((JFrame) this.mainView).dispose();
     });

     actionMap.put("choose self define percentage", () -> {
       diffPercentageEndBasic = new DiffPercentageEndBasic("Strategy Basic");
       diffPercentageEndBasic.addActionListener(this);
       ((JFrame) diffPercentageEndBasic).setLocation(((JFrame)
           this.chooseWithEndDateOrNot).getLocation());
       ((JFrame) this.chooseWithEndDateOrNot).dispose();
     });

     actionMap.put("different percentage end basic next", () -> {
       if (!HelperClass.checkStrategyBasicWithEnd(diffPercentageEndBasic)) {
         return;
       }
       List<String> all = diffPercentageEndBasic.getInput();
       useDifferentPercentageEnd = new UseDiffPercentageEnd("Stock and Percentage"
           ,Integer.parseInt(all.get(1)));
       useDifferentPercentageEnd.addActionListener(this);
       ((JFrame) useDifferentPercentageEnd).setLocation(((JFrame) this.diffPercentageEndBasic)
           .getLocation());
       ((JFrame) this.diffPercentageEndBasic).hide();
     });

     actionMap.put("different percentage end basic home", () -> {
       mainView = new MainView("Home");
       mainView.addActionListener(this);
       ((JFrame) mainView).setLocation(((JFrame) this.diffPercentageEndBasic).getLocation());
       ((JFrame) this.diffPercentageEndBasic).dispose();
     });

     actionMap.put("use different percentage end create", () -> {
       List<String> basicInfo = diffPercentageEndBasic.getInput();
       List<String> stock = new ArrayList<>();
       List<Double> percentage = new ArrayList<>();
       if (!HelperClass.addStockAndPercentage(useDifferentPercentageEnd, stock, percentage)) {
         return;
       }
       String strategyName = basicInfo.get(0);
       LocalDate startDate = LocalDate.parse(basicInfo.get(2));
       LocalDate endDate = LocalDate.parse(basicInfo.get(3));
       int interval = Integer.parseInt(basicInfo.get(4));
       double money = Double.parseDouble(basicInfo.get(5));
       try {
         model.createStrategyDiffPercent(strategyName,stock, percentage, money , startDate, endDate,
             interval);
         useDifferentPercentageEnd.setHintMess("Strategy " + strategyName + " created.");
         ((JFrame) this.diffPercentageEndBasic).dispose();
       }
       catch (Exception e) {
         useDifferentPercentageEnd.setHintMess(e.getMessage());
       }
     });

     actionMap.put("use different percentage end home", () -> {
       mainView = new MainView("Home");
       mainView.addActionListener(this);
       ((JFrame) mainView).setLocation(((JFrame) this.useDifferentPercentageEnd).getLocation());
       ((JFrame) this.useDifferentPercentageEnd).dispose();
     });

     actionMap.put("choose all equals percentage", () -> {
       createStrategyInputStrategyBasic = new CreateStrategyInputStrategyBasic
           ("Strategy Basic");
       createStrategyInputStrategyBasic.addActionListener(this);
       ((JFrame) createStrategyInputStrategyBasic).setLocation(((JFrame)
           this.chooseWithEndDateOrNot).getLocation());
       ((JFrame) this.chooseWithEndDateOrNot).dispose();
     });

     actionMap.put("create strategy input stock number next", () -> {
      List<String> all = createStrategyInputStrategyBasic.getInput();
      if (!HelperClass.checkStrategyBasicWithEnd(createStrategyInputStrategyBasic)) {
        return;
      }
      useEqualPercentageEnd = new UseEqualPercentageEnd("Stock and Percentage"
          ,Integer.parseInt(all.get(1)) );
      useEqualPercentageEnd.addActionListener(this);
      ((JFrame) useEqualPercentageEnd).setLocation(((JFrame) this.createStrategyInputStrategyBasic)
          .getLocation());
      ((JFrame) this.createStrategyInputStrategyBasic).hide();
    });

     actionMap.put("create strategy input stock number home", () -> {
       mainView = new MainView("Home");
       mainView.addActionListener(this);
       ((JFrame) mainView).setLocation(((JFrame) this.createStrategyInputStrategyBasic).getLocation());
       ((JFrame) this.createStrategyInputStrategyBasic).dispose();
     });

     actionMap.put("use equal percentage end create", () -> {
       List<String> basicInfo = createStrategyInputStrategyBasic.getInput();
       List<String> stockSymbol = useEqualPercentageEnd.getInput();
       for (int i = 0; i < stockSymbol.size(); i++) {
         if (stockSymbol.get(i).length() == 0) {
           useEqualPercentageEnd.setHintMess("At least one stock symbol is empty");
           return;
         }
       }
       String strategyName = basicInfo.get(0);
       LocalDate startDate = LocalDate.parse(basicInfo.get(2));
       LocalDate endDate = LocalDate.parse(basicInfo.get(3));
       int interval = Integer.parseInt(basicInfo.get(4));
       double money = Double.parseDouble(basicInfo.get(5));
       try {
         model.createStrategyEqualPercent(strategyName , stockSymbol, money , startDate, endDate,
             interval);
         useEqualPercentageEnd.setHintMess("Strategy " + strategyName + " created.");
         ((JFrame) this.createStrategyInputStrategyBasic).dispose();
       }
       catch (Exception e) {
         useEqualPercentageEnd.setHintMess(e.getMessage());
       }
     });

     actionMap.put("use equal percentage end home", () -> {
       mainView = new MainView("Home");
       mainView.addActionListener(this);
       ((JFrame) mainView).setLocation(((JFrame) this.useEqualPercentageEnd).getLocation());
       ((JFrame) this.useEqualPercentageEnd).dispose();
     });


     actionMap.put("choose strategy different no end", () -> {
       diffPercentageNoEndBasic = new DiffPercentageNoEndBasic("Strategy Basic");
       diffPercentageNoEndBasic.addActionListener(this);
       ((JFrame) diffPercentageNoEndBasic).setLocation(((JFrame)
           this.chooseWithEndDateOrNot).getLocation());
       ((JFrame) this.chooseWithEndDateOrNot).dispose();
     });

     actionMap.put("diff percentage no end basic next", () -> {
       List<String> all = diffPercentageNoEndBasic.getInput();
       if (!HelperClass.checkStrategyBasicNoEnd(diffPercentageNoEndBasic)) {
         return;
       }
       useDiffPercentageNoEnd = new UseDiffPercentageNoEnd("Stock and Percentage"
           , Integer.parseInt(all.get(1)));
       useDiffPercentageNoEnd.addActionListener(this);
       ((JFrame) useDiffPercentageNoEnd).setLocation(((JFrame) this.diffPercentageNoEndBasic)
           .getLocation());
       ((JFrame) this.diffPercentageNoEndBasic).hide();
     });

     actionMap.put("diff percentage no end basic home", () -> {
       mainView = new MainView("Home");
       mainView.addActionListener(this);
       ((JFrame) mainView).setLocation(((JFrame) this.diffPercentageNoEndBasic).getLocation());
       ((JFrame) this.diffPercentageNoEndBasic).dispose();
     });

     actionMap.put("use different percentage no end create", () -> {
       ArrayList<String> stock = new ArrayList<>();
       ArrayList<Double> percentage = new ArrayList<>();
       HelperClass.addStockAndPercentage(useDiffPercentageNoEnd, stock, percentage);
       List<String> basicInfo = diffPercentageNoEndBasic.getInput();
       String strategyName = basicInfo.get(0);
       LocalDate startDate = LocalDate.parse(basicInfo.get(2));
       int interval = Integer.parseInt(basicInfo.get(3));
       double money = Double.parseDouble(basicInfo.get(4));
       try {
         model.createStrategyNoEndDiffPercent(strategyName , stock, percentage, money , startDate,
             interval);
         useDiffPercentageNoEnd.setHintMess("Strategy " + strategyName + " created.");
         ((JFrame) this.diffPercentageNoEndBasic).dispose();
       }
       catch (Exception e) {
         useDiffPercentageNoEnd.setHintMess(e.getMessage());
       }
     });

     actionMap.put("use different percentage no end home", () -> {
       mainView = new MainView("Home");
       mainView.addActionListener(this);
       ((JFrame) mainView).setLocation(((JFrame) this.useDiffPercentageNoEnd).getLocation());
       ((JFrame) this.useDiffPercentageNoEnd).dispose();
     });

     actionMap.put("choose strategy equal no end", () ->{
       equalPercentageNoEndBasic = new EqualPercentageNoEndBasic("Strategy Basic Info");
       equalPercentageNoEndBasic.addActionListener(this);
       ((JFrame) equalPercentageNoEndBasic).setLocation(((JFrame) this.chooseWithEndDateOrNot)
           .getLocation());
       ((JFrame) this.chooseWithEndDateOrNot).dispose();
     });

     actionMap.put("equal percentage no end basic next", () -> {
       List<String> all = equalPercentageNoEndBasic.getInput();
       if (!HelperClass.checkStrategyBasicNoEnd(equalPercentageNoEndBasic)) {
         return;
       }
       useEqualsPercentage = new UseEqualsPercentage("Stock Symbols"
           , Integer.parseInt(all.get(1)));
       useEqualsPercentage.addActionListener(this);
       ((JFrame) useEqualsPercentage).setLocation(((JFrame) this.equalPercentageNoEndBasic)
           .getLocation());
       ((JFrame) this.equalPercentageNoEndBasic).hide();
     });

     actionMap.put("equal percentage no end basic home", () -> {
       mainView = new MainView("Home");
       mainView.addActionListener(this);
       ((JFrame) mainView).setLocation(((JFrame) this.equalPercentageNoEndBasic).getLocation());
       ((JFrame) this.equalPercentageNoEndBasic).dispose();
     });

     actionMap.put("create equals strategy create", () -> {
       List<String> basicInfo = equalPercentageNoEndBasic.getInput();
       List<String> stockSymbol = useEqualsPercentage.getInput();
       for (int i = 0; i < stockSymbol.size(); i++) {
         if (stockSymbol.get(i).length() == 0) {
           equalPercentageNoEndBasic.setHintMess("At least one stock symbol is empty");
           return;
         }
       }
       String strategyName = basicInfo.get(0);
       LocalDate startDate = LocalDate.parse(basicInfo.get(2));
       int interval = Integer.parseInt(basicInfo.get(3));
       double money = Double.parseDouble(basicInfo.get(4));
       try {
         model.createStrategyNoEndEqualPercent(strategyName , stockSymbol , money , startDate ,
             interval);
         useEqualsPercentage.setHintMess("Strategy " + strategyName + " created.");
         ((JFrame) this.equalPercentageNoEndBasic).dispose();
       }
       catch (Exception e) {
         useEqualsPercentage.setHintMess(e.getMessage());
       }

     });

     actionMap.put("create equals strategy home", () -> {
       mainView = new MainView("Home");
       mainView.addActionListener(this);
       ((JFrame) mainView).setLocation(((JFrame) this.useEqualsPercentage).getLocation());
       ((JFrame) this.useEqualsPercentage).dispose();
     });

     actionMap.put("apply strategy", () -> {
       List<String> portfolio = model.getPortfolioList();
       List<IStrategy> strategy = model.getStrategy();
       List<String> strategyList = new ArrayList<>();
       for (IStrategy s : strategy) {
         strategyList.add(s.getStrategyName());
       }
       applyStrategy = new ApplyStrategyToPortfolio("Apply", portfolio, strategyList);
       HelperClass.setLocation(mainView, applyStrategy, this);
     });

     actionMap.put("apply strategy apply", () -> {
       String portfolio = applyStrategy.getInput().get(0);
       String strategy = applyStrategy.getInput().get(1);
       try {
         model.invest(portfolio, strategy);
         applyStrategy.setHintMess("Apply " + strategy + " to " + portfolio);
       }
       catch (Exception e) {
         applyStrategy.setHintMess(e.getMessage());
       }
     });

     actionMap.put("apply strategy home", () -> {
       mainView = new MainView("Home");
       mainView.addActionListener(this);
       ((JFrame) mainView).setLocation(((JFrame) this.applyStrategy).getLocation());
       ((JFrame) this.applyStrategy).dispose();
     });

     actionMap.put("read strategy", () -> {
       ((JFrame) this.mainView).hide();
       readStrategy = new ReadStrategy("Read Strategy");
       readStrategy.addActionListener(this);
       ((JFrame) readStrategy).setLocation(((JFrame) this.mainView).getLocation());
       String fileName = "";
       if (readStrategy.getFileChooser() == JFileChooser.APPROVE_OPTION) {
         fileName = readStrategy.getInput();
         try {
           model.readStrategy(fileName);
           readStrategy.setHint("Read " + fileName);
         }
         catch (IllegalArgumentException e) {
           readStrategy.setHint(e.getMessage());
         }
       }
       else {
         mainView = new MainView("Home");
         mainView.addActionListener(this);
         ((JFrame) mainView).setLocation(((JFrame) this.readStrategy).getLocation());
       }
     });

     actionMap.put("read strategy home", () -> {
       mainView = new MainView("Home");
       mainView.addActionListener(this);
       ((JFrame) mainView).setLocation(((JFrame) this.readStrategy).getLocation());
       ((JFrame) this.readStrategy).dispose();
     });

     actionMap.put("save strategy", () -> {
       List<IStrategy> allStrategy = model.getStrategy();
       List<String> strategy = new ArrayList<>();
       for(IStrategy s : allStrategy) {
         strategy.add(s.getStrategyName());
       }
       saveChooseStrategy = new SaveStrategyChooseStrategy("Choose Portfolio", strategy);
       saveChooseStrategy.addActionListener(this);
       ((JFrame) saveChooseStrategy).setLocation(((JFrame) this.mainView).getLocation());
       ((JFrame) this.mainView).dispose();
     });

     actionMap.put("save strategy choose strategy", () -> {
       ((JFrame) saveChooseStrategy).hide();
       saveStrategy = new SaveStrategy("Save");
       saveStrategy.addActionListener(this);
       ((JFrame) saveStrategy).setLocation(((JFrame) this.saveChooseStrategy).getLocation());
       String strategyName = saveChooseStrategy.getInput().get(0);

       String message;
       if (saveStrategy.getFileChooser() == JFileChooser.APPROVE_OPTION) {
         String fileName = saveStrategy.getInput();
         model.saveStrategy(strategyName, fileName);
         message = "save " + strategyName + " to " + fileName;
         saveStrategy.setHint(message);
       }
       else {
         mainView = new MainView("Home");
         mainView.addActionListener(this);
         ((JFrame) mainView).setLocation(((JFrame) this.saveStrategy).getLocation());
         ((JFrame) saveChooseStrategy).dispose();
         ((JFrame) saveStrategy).dispose();
       }
     });

     actionMap.put("save strategy choose strategy home", () -> {
       mainView = new MainView("Home");
       mainView.addActionListener(this);
       ((JFrame) mainView).setLocation(((JFrame) this.saveChooseStrategy).getLocation());
       ((JFrame) saveChooseStrategy).dispose();
     });

     actionMap.put("save strategy ok", () -> {
       mainView = new MainView("Home");
       mainView.addActionListener(this);
       ((JFrame) mainView).setLocation(((JFrame) this.saveStrategy).getLocation());
       ((JFrame) this.saveStrategy).dispose();
     });

     actionMap.put("choose strategy home", () -> {
       mainView = new MainView("Home");
       mainView.addActionListener(this);
       ((JFrame) mainView).setLocation(((JFrame) this.chooseWithEndDateOrNot).getLocation());
       ((JFrame) this.chooseWithEndDateOrNot).dispose();
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
  public void actionPerformed(ActionEvent e) {
    this.str = e.getActionCommand();
    actionMap.get(str).run();
  }
}
