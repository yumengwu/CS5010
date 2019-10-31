Design Change:


   CommandView, Command Controller and GUI controller design almost remain the same, except add
   some classes or methods into them.
   Last time, when user do save/read on file, user need to type the file path, this time we use
   filechooser, a gui for use to choose the file path, it is easier for user to use the application.
   Also we use combobox for user enter date or portfolio/strategy name, this can prevent user from
   entering wrong information.

   For model, we add IStrategy interface and strategy class because of the requirements. IStrategy
   is used to store variables to describe strategy. Also, in order to read strategy file, we add
   another class implementing IXMLParser interface to transfer raw text file to Strategy object.
   So return type of parse method in IXMLParser is Object now.
   In TradeModel and TradeOperation, we add several public methods to create strategy and apply
   strategy to portfolio, and save and read strategy files. We also add several private helper
   method in process of strategy.