File format of portfolio is as following (cost is the price of each share):
<portfolio>
<portfolio-name>p1</portfolio-name>
<stock>
<stock-symbol>MSFT</stock-symbol>
<transaction-history>
<date>2019-03-11</date>
<sell>false</sell>
<volume>36.0</volume>
<cost>100.0</cost>
</transaction-history>
...
</stock>
...
</portfolio>
All the tags must be lower case. All content must in root tag portfolio. User cannot change order
of tags. A portfolio can have one or more stocks, and a stock can have one or more
transaction-history. Format of date must be yyyy-mm-dd. Sell must be true or false. Other tags are
invalid. If portfolio name already exists, the method will overwrite portfolio in map.

File format of strategy is as following (day means intervals):
<strategy>
	<strategy-name>s1</strategy-name>
	<stock-list>
		<stock>
			<stock-symbol>GOOG</stock-symbol>
			<stock-weight>0.4</stock-weight>
		</stock>
		<stock>
			<stock-symbol>AAPL</stock-symbol>
			<stock-weight>0.6</stock-weight>
		</stock>
	</stock-list>
	<begin-date>2018-04-01</begin-date>
	<end-date>2019-04-01</end-date>
	<amount>20000.0</amount>
	<day>30</day>
</strategy>
All the tags must be lower case. All content must in root tag strategy. User cannot change order
of tags. A stock-list can have one or more stocks. Format of date must be yyyy-mm-dd. Other tags
are invalid. If strategy name already exists, the method will overwrite strategy in map. end-date
cannot be empty. For weight, user can add weights to each stock, or make each weight empty.
