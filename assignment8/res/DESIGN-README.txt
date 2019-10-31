Design: The program is designed in MVC pattern. Basic operation is handled by model.
Model includes: 
    Transaction history: It records the transactions includes the transaction date, is the
    transaction is sell or buy, how many shares(numbers) of trade item involved in this transaction,
     and the price of each item.

    Stock: It includes a stock symbol, and a list of transaction history of this stock.

    Portfolio: It includes a portfolio name, a map of stock, and a GetPrice object given to
    Constructor when construct portfolio object because API limits the number to get stock data and
    this design could make API just connect to the server once when user buy the same stock in
    different portfolio.

Controller includes:
   Trade controller: Control the whole program, it ask view to provide user input, then pass the
   input to model to get the result then print the result, the process continues until the program
   end.

View include:
   CommandView: This is a text base interface, it reads user input and output message from
   command line. CommandView includes a readable to read user input to the program, and an
   appendable to output the result to the user.