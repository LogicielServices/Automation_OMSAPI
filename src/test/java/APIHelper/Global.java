package APIHelper;

import io.restassured.response.Response;

public class Global {

    //public static String tfaCode="123456";
    //public static String BaseURL="http://oms-qa.vcttech.co:8002";
    public static String BaseURL = "https://qa.webtrade-api.com:443";
    public static String ApiBaseURL = "http://10.0.10.57:4040";
    public static String getUserName;
    public static String getUserEmail;
    public static String getCodeID;
    public static String getAccToken;
    public static String getPassword;
    public static String getorderType;
    public static Double getAvgPrice = 0.00;
    public static Double getOptionAvgPrice = 0.00;
    public static Boolean ValidationFlag = false;
    public static Response getTopicResponse;
    public static String getTopicValue;
    public static Double getLONGrealizedPnL = 0.00;
    public static Double getSHORTrealizedPnL = 0.00;
    public static Double getOptionLONGrealizedPnL = 0.00;
    public static Double getOptionSHORTrealizedPnL = 0.00;
    public static String getResponseArray;


//===================================================Locate Subscribe Response Fields===============================================================

    public static String getLocateOrdType;
    public static String getLocateQuoteReqID;
    public static String getLocateOrdStatus;
    public static Integer getLocateOrderQty = 0;
    public static Double getLocateOfferPx = 0.00;
    public static Integer getLocateOfferSize = 0;
    public static Integer getLocateCumQty = 0;
    public static Double getLocateAvgPx = 0.00;
    public static String getLocateStatusDesc;
    public static String getLocateStatus;
    public static String getLocateOrdRejReason;
    public static String getLocateTransactionStatusString;
    public static String getLocateTransactionStatus;
    public static String getLocateTimeInForce;
    public static String getLocateText;
    public static String getLocateId;
    public static String getLocateSymbol;
    public static String getLocateSymbolSfx;
    public static String getLocateTransactTimeUtc;
    public static String getLocateTransactTime;
    public static String getLocateClientID;
    public static String getLocateLocateType;
    public static String getLocateBoothID;
    public static String getLocateAccount;
    public static String getLocateOriginatingUserDesc;
    public static Integer getLocateEtbQty = 0;


//===================================================Orders Subscription Response Fields===============================================================

    public static String getOrderValue = "";
    public static String getOrderID;
    public static String getSideDesc;
    public static String getBuyFilledOrderID;
    public static String getSellFilledOrderID;
    public static String getShortSellFilledOrderID;
    public static Integer getBuyFilled_qOrderID = 0;
    public static Integer getSellFilled_qOrderID = 0;
    public static Integer getShortSellFilled_qOrderID = 0;
    public static String getStatus;
    public static String getID;
    public static Integer qOrderID = 0;
    public static String gettime;
    public static String getaccount;
    public static String getclOrdID;
    public static String getOrigClOrdID;
    public static Double getprice = 0.00;
    public static String getsymbol;
    public static String gettext;
    public static String getside;
    public static String getcomplianceID;
    public static String getoriginatingUserDesc;
    public static String getordType;
    public static Double getstopPx = 0.00;
    public static String gettimeInForce;
    public static String gettransactTime;
    public static String getsymbolSfx;
    public static String getsymbolWithoutSfx;
    public static String gettifDesc;
    public static String getorderTypeDesc;
    public static String getstatusDesc;
    public static Double getavgPx = 0.00;
    public static Double getcumQty = 0.00;
    public static Double getworkableQty = 0.00;
    public static Double getleavesQty = 0.00;
    public static Double getorderQty = 0.00;
    public static String getlocateID;
    public static String getcontactName;
    public static String getlocateRequired;
    public static Double getlocateRate;
    public static String getdestination;
    public static String getboothID;


    //===================================================Option Orders Subscription Response Fields===============================================================

    public static String getOptionOrderID;
    public static Integer getOptionqOrderID = 0;
    public static Double getOptionStrikePrice;
    public static String getOptionMaturityDay;
    public static String getOptionMaturityMonthYear;
    public static String getOptionMaturityMonthYearDesc;
    public static String getOptionSideDesc;
    public static String getOptionMaturityDate;
    public static String getOptionDesc;
    public static String getOptionCmta;
    public static String getOptionExecBroker;
    public static Integer getOptionPutOrCallInt = 0;
    public static String getOptionPutOrCall;
    public static Integer getOptionCoveredOrUncoveredInt = 0;
    public static String getOptionCoveredOrUncovered;
    public static Integer getOptionCustomerOrFirmInt = 0;
    public static String getOptionCustomerOrFirm;
    public static Integer getOptionOpenCloseBoxed = 0;
    public static String getOptionOpenClose;
    public static String getOptionBuyFilledOrderID;
    public static String getOptionSellFilledOrderID;
    public static Integer getOptionBuyFilled_qOrderID = 0;
    public static Integer getOptionSellFilled_qOrderID = 0;

    public static String getOptionStatus;
    public static String getOptionID;
    public static String getOptionTime;
    public static String getOptionAccount;
    public static String getOptionclOrdID;
    public static String getOptionOrigClOrdID;
    public static Double getOptionPrice = 0.00;
    public static String getOptionSymbol;
    public static String getOptSymbol;
    public static String getOptionText;
    public static String getOptionSide;
    public static String getOptionComplianceID;
    public static String getOptionOriginatingUserDesc;
    public static String getOptionOrdType;
    public static Double getOptionStopPx = 0.00;
    public static String getOptionTimeInForce;
    public static String getOptionTransactTime;
    public static String getOptionSymbolSfx;
    public static String getOptionSymbolWithoutSfx;
    public static String getOptionTifDesc;
    public static String getOptionOrderTypeDesc;
    public static String getOptionStatusDesc;
    public static Double getOptionAvgPx;
    public static Double getOptionCumQty = 0.00;
    public static Double getOptionWorkableQty = 0.00;
    public static Double getOptionLeavesQty = 0.00;
    public static Double getOptionOrderQty = 0.00;
    public static String getOptionLocateID;
    public static String getOptionContactName;
    public static String getOptionLocateRequired;
    public static Double getOptionLocateRate = 0.00;
    public static String getOptionDestination;
    public static String getOptionBoothID;


    //===================================================Executions Subscription Response Fields===============================================================

    public static String getExecution_destination;
    public static String getExecution_account;
    public static String getExecution_sideDesc;
    public static String getExecution_side;
    public static String getExecution_price;
    public static String getExecution_symbol;
    public static String getExecution_symbolSfx;
    public static String getExecution_status;
    public static String getExecution_text;
    public static String getExecution_transactTime;
    public static String getExecution_tradeDate;
    public static String getExecution_ordStatus;
    public static String getExecution_originatingUserDesc;
    public static String getExecution_execBroker;
    public static String getExecution_timeInForce;
    public static String getExecution_ordType;
    public static String getExecution_orderId;
    public static Integer getExecution_qOrderID = 0;
    public static String getExecution_clOrdID;
    public static String getExecution_origClOrdID;
    public static String getExecution_execRefID;
    public static String getExecution_execID;
    public static String getExecution_execType;
    public static String getExecution_execTransType;
    public static String getExecution_execTransTypeDesc;
    public static Double getExecution_lastPx = 0.00;
    public static Double getExecution_orderQty = 0.00;
    public static Double getExecution_leavesQty = 0.00;
    public static Double getExecution_lastShares = 0.00;
    public static Double getExecution_cumQty = 0.00;
    public static Double getExecution_avgPx = 0.00;


    //===================================================Equity Position Subscription Response Fields===============================================================

    public static String getPosition_id;
    public static Double getPosition_completeDayBuyOrderQty = 0.00;
    public static Double getPosition_completeDaySellLongOrderQty = 0.00;
    public static Double getPosition_completeDaySellShortOrderQty = 0.00;
    public static String getPosition_symbol;
    public static String getPosition_positionString;
    public static Double getPosition_avgPrice = 0.00;
    public static Double getPosition_totDollarOfTrade;
    public static Double getPosition_execQty = 0.00;
    public static Double getPosition_realizedPnL = 0.00;
    public static String getPosition_symbolSfx;
    public static String getPosition_originatingUserDesc;
    public static String getPosition_account;
    public static Double totalTrade = 0.00;

    public static Double getcompleteDayBuyOrderQty = 0.00;
    public static Double getcompleteDaySellLongOrderQty = 0.00;
    public static Double getcompleteDaySellShortOrderQty = 0.00;


    //===================================================Option Position Subscription Response Fields===============================================================

    public static String getOptionPosition_id;
    public static String getOptionPosition_comment;
    public static String getOptionPosition_currency;
    public static String getOptionPosition_exchangeName;
    public static String getOptionPosition_exchangeID;
    public static String getOptionPosition_rule80A;
    public static Integer getOptionPosition_positionOperation = 0;
    public static Integer getOptionPosition_positionSide = 0;
    public static Double getOptionPosition_parentOrdPrice = 0.00;
    public static Double getOptionPosition_parentOrdQty = 0.00;
    public static String getOptionPosition_clientID;
    public static Double getOptionPosition_execQtyFrac = 0.00;
    public static Double getOptionPosition_unRealizedPnL = 0.00;
    public static Double getOptionPosition_openQty = 0.00;
    public static Double getOptionPosition_totalBuyOrderQty = 0.00;
    public static Double getOptionPosition_totalSellOrderQty = 0.00;
    public static Double getOptionPosition_buyAvgPx = 0.00;
    public static Double getOptionPosition_sellAvgPx = 0.00;
    public static String getOptionPosition_boothID;
    public static String getOptionPosition_symbolWithoutSfx;
    public static Double getOptionPosition_completeDayBuyOrderQty = 0.00;
    public static Double getOptionPosition_completeDaySellLongOrderQty = 0.00;
    public static Double getOptionPosition_completeDaySellShortOrderQty = 0.00;
    public static String getOptionPosition_optionSymbol;
    public static String getOptionPosition_optionsFields;
    public static String getOptionPosition_optionDesc;
    public static Integer getOptionPosition_maturityDay = 0;
    public static String getOptionPosition_maturityMonthYear;
    public static String getOptionPosition_maturityMonthYearDesc;
    public static String getOptionPosition_maturityDate;
    public static Double getOptionPosition_strikePrice = 0.00;
    public static Integer getOptionPosition_putOrCallInt = 0;
    public static String getOptionPosition_putOrCall;
    public static Integer getOptionPosition_coveredOrUncoveredInt = 0;
    public static String getOptionPosition_coveredOrUncovered;
    public static Integer getOptionPosition_customerOrFirmInt = 0;
    public static String getOptionPosition_customerOrFirm;
    public static String getOptionPosition_openCloseBoxed;
    public static String getOptionPosition_openClose;
    public static String getOptionPosition_cmta;
    public static String getOptionPosition_expiryDate;
    public static String getOptionPosition_symbol;
    public static String getOptionPosition_positionString;
    public static Double getOptionPosition_avgPrice = 0.00;
    public static Double getOptionPosition_totDollarOfTrade = 0.00;
    public static Double getOptionPosition_execQty = 0.00;
    public static Double getOptionPosition_realizedPnL = 0.00;
    public static String getOptionPosition_symbolSfx;
    public static String getOptionPosition_originatingUserDesc;
    public static String getOptionPosition_account;
	
	
	/*
	public static Double   getOptionPosition_completeDayBuyOrderQty=0.00;        
	public static Double   getOptionPosition_completeDaySellLongOrderQty=0.00;       
	public static Double   getOptionPosition_completeDaySellShortOrderQty=0.00;               
	public static String   getOptionPosition_symbol;            
	public static String   getOptionPosition_positionString;              
	public static Double   getOptionPosition_avgPrice=0.00;           
	public static String   getOptionPosition_totDollarOfTrade;           
	public static Double   getOptionPosition_execQty=0.00;             
	public static Double   getOptionPosition_realizedPnL=0.00;     
	public static String   getOptionPosition_symbolSfx;          
	public static String   getOptionPosition_originatingUserDesc;       
	public static String   getOptionPosition_account; 
	public static Double   totalOptionTrade=0.00;
	
	*/

    public static Double getOptioncompleteDayBuyOrderQty = 0.00;
    public static Double getOptioncompleteDaySellLongOrderQty = 0.00;
    public static Double getOptioncompleteDaySellShortOrderQty = 0.00;


    //===================================================Accounts Response Fields===============================================================

    public static int totalCount;
    public static int accountId;
    public static int pageNumber = 1;
    public static int pageSize = 1;
}
