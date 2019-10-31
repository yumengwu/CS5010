package tradestock;

import java.io.InputStreamReader;
import java.util.Scanner;
import tradestock.controller.GUIController;
import tradestock.controller.IController;
import tradestock.controller.TradeController;
import tradestock.model.stock.Stock;
import tradestock.model.tradeoperation.TradeModel;
import tradestock.model.tradeoperation.TradeOperation;
import tradestock.view.buttononlyview.ButtonOnly;
import tradestock.view.CommandView;
import tradestock.view.IView;
import tradestock.view.buttononlyview.MainView;

/**
 * Runner of the program.
 */
public class TradeRunner {

  /**
   * A main method to run this program.
   * @param args arguments to run the method.
   */
  public static void main(String[] args) {
    TradeOperation<Stock> model = TradeModel.getBuilder().build();
    IView view;
    ButtonOnly guiView;
    IController<Stock> controller;
    while (true) {
      System.out.println("Please follow the command and make choice\n");
      System.out.println("1 - User Command Line View");
      System.out.println("2 - User GUI View");
      Scanner sc = new Scanner(System.in);
      String choice = sc.next();
      if (choice.equals("1")) {
        view = new CommandView(new InputStreamReader(System.in), System.out);
        controller = new TradeController(model, view);
        break;
      }
      else if (choice.equals("2")) {
        guiView = new MainView("Main");
        controller = new GUIController(model, guiView);
        break;
      }
      else {
        System.out.println("Invalid choice, try again\n");
      }
    }
    controller.execute();
  }
}
