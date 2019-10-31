package calculator;

import java.util.Scanner;

/**
 * Demonstrates a simple command-line-based calculator
 */
public class SimpleCalc1 {
  public static void main(String[] args) {
    int num1, num2;
    CalculatorModel model = new CalculatorModel();
    Scanner scan = new Scanner(System.in);
    num1 = scan.nextInt();
    num2 = scan.nextInt();
    System.out.printf("%d", model.add(num1, num2));
  }
}
