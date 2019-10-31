Design: The program is designed in MVC pattern. Basic operation is handled by model.
Model includes: 
    Transaction history: It records the transactions includes the transaction date, is the
    transaction is sell or buy, how many shares(numbers) of trade item involved in this transaction,
     and the price of each item.

    Stock: It includes a stock symbol, and a list of transaction history of this stock. The main
    change of Stock object is parameter of method to get cost because of commission fee. Our design
    is that commission fee depends on number of transactions, and this fee is from model, so methods
    in Stock will add this parameter to calculate cost.

    Portfolio: It includes a portfolio name, a map of stock, and a GetPrice object given to
    Constructor when construct portfolio object because API limits the number to get stock data and
    this design could make API just connect to the server once when user buy the same stock in
    different portfolio. The change of portfolio is also adding commission fee parameter when get
    cost. Another change is that get state method will return a List object, rather than a single
    string, because what to display is view's work, so model should return raw data, and view should
    transfer raw data to formatted data.

    TradeModel: Change of model is adding methods to set or get commission fee, and to set API, and
    also, get state method will return List object.

    XMLParser object is to parse text data from file to Portfolio object.

Controller includes:
   Trade controller: Control the whole program, it ask view to provide user input, then pass the
   input to model to get the result then print the result, the process continues until the program
   end.

View include:
   CommandView: This is a text base interface, it reads user input and output message from
   command line. CommandView includes a readable to read user input to the program, and an
   appendable to output the result to the user.