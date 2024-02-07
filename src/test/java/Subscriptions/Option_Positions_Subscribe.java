package Subscriptions;

import static io.restassured.RestAssured.given;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import APIHelper.APIHelperClass;
import APIHelper.Global;
import APIHelper.LoggingManager;
import XLDataProvider.ExcelDataProvider;
import groovy.util.logging.Log4j;
import io.restassured.RestAssured;
import io.restassured.response.Response;

//Test(groups = {"Create"},dependsOnGroups={"UserLoginAuthentications"})

public class Option_Positions_Subscribe {

	
	@BeforeMethod
	public void beforeMethod() 
	{
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}
	
	@AfterMethod
	public void afterMethod(ITestResult result) 
	{
		if (result.getStatus()==ITestResult.FAILURE) 
		{
			Throwable throwable=result.getThrowable();
			StringWriter errorWriter=new StringWriter();
			throwable.printStackTrace(new PrintWriter(errorWriter));
			LoggingManager.logger.error(errorWriter.toString());
		}
		
	}
	 
	 @Test (dataProvider="SubscribeBUYOption_Positions", dataProviderClass=ExcelDataProvider.class , groups={"SubscribeBUYOption_Positions"}, dependsOnGroups={"UserLoginAuthentications"})//UserLoginAuthentications
	 public void Verify_Subscribe_BUY_Option_Positions(	String Option_Position_TestCases,
			 											String Endpoint_Version,
														String Subscribe_Option_Positions_BasePath,
														String Content_Type,
														String Subscribe_Option_Positions_StatusCode,
														String Position_ID,
													    String Validate_Position_FLAT,
													    String Order_Creation_BasePath,
													    String Order_OrdType,
													    String Order_TimeInForce,
													    String Order_Destination,
													    String Order_Price,
													    String Order_StopPx,
													    String Order_PutOrCall,
													    String Order_StrikePrice,
													    String Order_CoveredOrUncovered,
													    String Order_CustomerOrFirm,
													    String Order_Cmta,
													    String Order_OpenClose,
													    String Order_ExpiryDate,
														String Order_Creation_StatusCode,
														String OptionOrder_Creation_Body,
														String OptionOrder_Creation_StatusCode,
														String OptionOrder_Creation_Response,
														String Subscribe_OptionOrder_BasePath,
														String StatusCode,
														String Subscribe_OptionOrder_UserID,
														String Subscribe_OptionOrder_OrderType,
														String Subscribe_OptionOrder_Side,
														String Subscribe_OptionOrder_Symbol,
														String Subscribe_OptionOrder_Account,
														String Subscribe_OptionOrder_Destination,
														String Subscribe_OptionOrder_Price,
														String Subscribe_OptionOrder_OrderQty,
													    String Subscribe_OptionOrder_ExpectedStatus,
										    			String Subscribe_Executions_BasePath,
														String Subscribe_Executions_StatusCode,
														String Executions_Case,
														String Executions_SideDesc,
														String Executions_Side,
														String Fetch_Executions_Symbol,
														String Fetch_Executions_Account,
														String Fetch_Executions_OrderQty,
														String Fetch_Executions_Price,
														String Fetch_Executions_OrderType,
														String Fetch_Executions_Destination,
														String Fetch_Executions_SymbolSfx,
														String Fetch_Executions_Status,
														String Fetch_Executions_Text,
														String Fetch_Executions_OrdStatus,
														String Fetch_Last_Executions_OrdStatus,
														String Fetch_Executions_OriginatingUserDesc,
														String Fetch_Executions_ExecBroker,
														String Fetch_Executions_TimeInForce,
														String Fetch_Executions_ExecRefID,
														String Fetch_Executions_ExecTransType,
														String Fetch_Executions_ExecTransTypeDesc,
														String Validate_Position_comment_Value,
														String Validate_Position_currency_Value,
														String Validate_Position_exchangeName_Value,
														String Validate_Position_exchangeID_Value,
														String Validate_Position_rule80A_Value,
														String Validate_Position_positionOperation_Value,
														String Validate_Position_positionSide_Value,
														String Validate_Position_parentOrdPrice_Value,
														String Validate_Position_parentOrdQty_Value,
														String Validate_Position_clientID_Value,
														String Validate_Position_boothID_Value,
														String Validate_Position_symbolWithoutSfx_Value,
														String Validate_Position_optionSymbol_Value,
														String Validate_Position_optionsFields_Value,
														String Validate_Position_OptionDesc,
														String Validate_Position_maturityDay_Value,
														String Validate_Position_maturityMonthYear_Value,
														String Validate_Position_maturityMonthYearDesc_Value,
														String Validate_Position_maturityDate_Value,
														String Validate_Position_strikePrice_Value,
														String Validate_Position_putOrCallInt_Value,
														String Validate_Position_putOrCall_Value,
														String Validate_Position_coveredOrUncoveredInt_Value,
														String Validate_Position_coveredOrUncovered_Value,
														String Validate_Position_customerOrFirmInt_Value,
														String Validate_Position_customerOrFirm_Value,
														String Validate_Position_openCloseBoxed_Value,
														String Validate_Position_openClose_Value,
														String Validate_Position_cmta_Value,
														String Validate_Position_expiryDate_Value,
														String Validate_Position_symbol_Value,
														String Validate_Position_positionString_Value,
														String Validate_Position_symbolSfx_Value,
														String Validate_Position_originatingUserDesc_Value,
														String Validate_Position_account_Value )
	 {
	 	try
		{

			 LoggingManager.logger.info("====================================================================");
			 LoggingManager.logger.info("TestCase : "+Option_Position_TestCases);
			 LoggingManager.logger.info("====================================================================");

			 DecimalFormat decimalFormat = new DecimalFormat("0.0000");
			 decimalFormat.getRoundingMode();
			 RestAssured.baseURI=Global.BaseURL;
			 //String position_ID=Validate_BoothID+"-"+Validate_Symbol_Value+" "+Validate_Mat_ExpDate+" "+Validate_StrikePrice_Value+" "+Validate_PutOrCall_Value+"-"+Validate_Account_Value;
			 //String position_OptionDesc=Validate_Symbol_Value+" "+Validate_Mat_ExpDate+" "+Validate_StrikePrice_Value+" "+Validate_PutOrCall_Value;
			 APIHelperClass.Flat_Option_Positions(	Endpoint_Version,
					 								Subscribe_Option_Positions_BasePath,
													Content_Type,
													Subscribe_Option_Positions_StatusCode,
					 								Position_ID,
													Validate_Position_FLAT,
													Order_Creation_BasePath,
													Order_OrdType,
													Order_TimeInForce,
													Order_Destination,
													Order_Price,
													Order_StopPx,
													Order_PutOrCall,
													Order_StrikePrice,
													Order_CoveredOrUncovered,
													Order_CustomerOrFirm,
													Order_Cmta,
													Order_OpenClose,
													Order_ExpiryDate,
													Order_Creation_StatusCode );

			 LoggingManager.logger.info("API-Get Option realizedPnL After Flat : ["+ Global.getOptionLONGrealizedPnL+"]");
			 //LoggingManager.logger.info("API-Get Option SHORTrealizedPnL After Flat : ["+ Global.getOptionSHORTrealizedPnL+"]");
			 //=======================================================Order Creation================================================================

			 Response response=

							 given()
							 .header("Content-Type",Content_Type)
							 .header("Authorization", "Bearer " + Global.getAccToken)
							 .body(OptionOrder_Creation_Body)

							 .when()
							 .post(Order_Creation_BasePath)

							 .then()
							 .extract()
							 .response();

			 LoggingManager.logger.info("API-OptionOrder_Filled_BUY_BasePath : ["+Order_Creation_BasePath+"]");
			 LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			 LoggingManager.logger.info("API-OptionOrder_Filled_BUY_Body : ["+OptionOrder_Creation_Body+"]");
			 LoggingManager.logger.info("API-OptionOrder_Filled_BUY_StatusCode : ["+response.getStatusCode()+"]");
			 LoggingManager.logger.info("API-OptionOrder_Filled_BUY_Response_Body : ["+response.getBody().asString()+"]");
			 Assert.assertEquals(response.getStatusCode(), Integer.parseInt(OptionOrder_Creation_StatusCode),"Verify_OptionOrder_Creation_StatusCode");
			 Assert.assertEquals(response.getBody().asString(), OptionOrder_Creation_Response,"Verify_OptionOrder_Creation_Response");
			 APIHelperClass.GetOrderValues(  Subscribe_OptionOrder_BasePath,
											 Global.getAccToken,
											 Content_Type,
											 Integer.parseInt(StatusCode),
											 Endpoint_Version,
											 Subscribe_OptionOrder_UserID,
											 Subscribe_OptionOrder_ExpectedStatus,
											 Subscribe_OptionOrder_Account,
											 Subscribe_OptionOrder_Symbol,
											 Subscribe_OptionOrder_Destination,
											 Subscribe_OptionOrder_Price,
											 Subscribe_OptionOrder_Side,
											 Subscribe_OptionOrder_OrderQty,
											 Subscribe_OptionOrder_OrderType,"option");

			 Global.getOptionBuyFilledOrderID=Global.getOptionOrderID;
			 Global.getOptionBuyFilled_qOrderID=Global.getOptionqOrderID;
			 Global.getOptioncompleteDayBuyOrderQty+=Double.parseDouble(Subscribe_OptionOrder_OrderQty);
			 LoggingManager.logger.info("API- BUY OrderID Before Execution : ["+Global.getOptionBuyFilledOrderID+"]");
			 LoggingManager.logger.info("API- BUY qOrderID Before Execution : ["+Global.getOptionBuyFilled_qOrderID+"]");
			 if(Global.getOptionBuyFilledOrderID == null || Global.getOptionBuyFilledOrderID=="" )
			 {Assert.fail("Logs : Buy Order Not Found with status :["+Subscribe_OptionOrder_ExpectedStatus+"]");}

			 //----------------------------------------------------------Executions ---------------------------------------------------------------
			 Response get_execution_response=

							 given()
							 .header("Content-Type",Content_Type)
							 .header("Authorization", "Bearer " + Global.getAccToken)
							 .when()
							 .get(Subscribe_Executions_BasePath)

							 .then()
							 //.statusCode(Integer.parseInt(Subscribe_Executions_StatusCode))
							 //.statusLine("HTTP/1.1 200 OK")
							 .extract().response();

			 LoggingManager.logger.info("API-Subscribe_Executions_BasePath : ["+Subscribe_Executions_BasePath+"]");
			 LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			 LoggingManager.logger.info("API-Subscribe_Executions_StatusCode : ["+get_execution_response.getStatusCode()+"]");
			 Assert.assertEquals(get_execution_response.getStatusCode(), Integer.parseInt(Subscribe_Executions_StatusCode),"Verify_SubscribeBUY_Executions");
			 APIHelperClass.Validate_Executions( get_execution_response,
												 Endpoint_Version,
												 Global.getOptionBoothID,
												 Executions_Case,
												 Executions_SideDesc,
												 Executions_Side,
												 Global.getOptionBuyFilledOrderID,
												 Global.getOptionBuyFilled_qOrderID,
												 Fetch_Executions_Symbol,
												 Fetch_Executions_Account,
												 Fetch_Executions_OrderQty,
												 Fetch_Executions_Price,
												 Fetch_Executions_OrderType,
												 Fetch_Executions_Destination,
												 Fetch_Executions_SymbolSfx,
												 Fetch_Executions_Status,
												 Fetch_Executions_Text,
												 Fetch_Executions_OrdStatus,
												 Fetch_Last_Executions_OrdStatus,
												 Fetch_Executions_OriginatingUserDesc,
												 Fetch_Executions_ExecBroker,
												 Fetch_Executions_TimeInForce,
												 Fetch_Executions_ExecRefID,
												 Fetch_Executions_ExecTransType,
												 Fetch_Executions_ExecTransTypeDesc,"","","",0,"","","","","","","","","","","","","","","","","","position");

			 //=======================================================Position Subcription================================================================

			 LoggingManager.logger.info("API-Subscribe_position_ID : ["+Position_ID+"]");

			 Response get_position_response=
											 given()
											 .header("Content-Type",Content_Type)
											 .header("Authorization", "Bearer " + Global.getAccToken)
											 .when()
											 .get(Subscribe_Option_Positions_BasePath)
											 .then().extract().response();

			 Assert.assertEquals(get_position_response.getStatusCode(), Integer.parseInt(Subscribe_Option_Positions_StatusCode),"Verify_Subscribe_Option_Positions_StatusCode");
			 APIHelperClass.Validate_Option_Positions(get_position_response,
														 Endpoint_Version,
					 									 Position_ID,
														 Global.getOptionAvgPrice,
														 Global.getOptionLONGrealizedPnL,
														 Validate_Position_comment_Value,
														 Validate_Position_currency_Value,
														 Validate_Position_exchangeName_Value,
														 Validate_Position_exchangeID_Value,
														 Validate_Position_rule80A_Value,
														 Integer.parseInt(Validate_Position_positionOperation_Value),
														 Integer.parseInt(Validate_Position_positionSide_Value),
														 Double.parseDouble(Validate_Position_parentOrdPrice_Value),
														 Double.parseDouble(Validate_Position_parentOrdQty_Value),
														 Validate_Position_clientID_Value,
														 Validate_Position_boothID_Value,
														 Validate_Position_symbolWithoutSfx_Value,
														 Validate_Position_optionSymbol_Value,
														 Validate_Position_optionsFields_Value,
					 									 Validate_Position_OptionDesc,
														 Integer.parseInt(Validate_Position_maturityDay_Value),
														 Validate_Position_maturityMonthYear_Value,
														 Validate_Position_maturityMonthYearDesc_Value,
														 Validate_Position_maturityDate_Value,
														 Double.parseDouble(Validate_Position_strikePrice_Value),
														 Integer.parseInt(Validate_Position_putOrCallInt_Value),
														 Validate_Position_putOrCall_Value,
														 Integer.parseInt(Validate_Position_coveredOrUncoveredInt_Value),
														 Validate_Position_coveredOrUncovered_Value,
														 Integer.parseInt(Validate_Position_customerOrFirmInt_Value),
														 Validate_Position_customerOrFirm_Value,
														 Validate_Position_openCloseBoxed_Value,
														 Validate_Position_openClose_Value,
														 Validate_Position_cmta_Value,
														 Validate_Position_expiryDate_Value,
														 Validate_Position_symbol_Value,
														 Validate_Position_positionString_Value,
														 Validate_Position_symbolSfx_Value,
														 Validate_Position_originatingUserDesc_Value,
														 Validate_Position_account_Value );


		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	 }
	 
	 
	 @Test (dataProvider="SubscribeSELLOption_Positions", dataProviderClass=ExcelDataProvider.class , groups={"SubscribeSELLOption_Positions"}, dependsOnGroups={"UserLoginAuthentications"})//UserLoginAuthentications  Flat_Option_Position
	 public void Verify_SubscribeSELL_Option_Positions(	String Order_Position_TestCases,
														String Endpoint_Version,
														String Subscribe_Option_Positions_BasePath,
														String Content_Type,
														String Subscribe_Option_Positions_StatusCode,
														String Position_ID,
													    String Validate_Position_FLAT,
													    String Order_Creation_BasePath,
													    String Order_OrdType,
													    String Order_TimeInForce,
													    String Order_Destination,
													    String Order_Price,
													    String Order_StopPx,
													    String Order_PutOrCall,
													    String Order_StrikePrice,
													    String Order_CoveredOrUncovered,
													    String Order_CustomerOrFirm,
													    String Order_Cmta,
													    String Order_OpenClose,
													    String Order_ExpiryDate,
													    String Order_Creation_StatusCode,
														String BUY_OptionOrder_Creation_Body,
														String SELL_OptionOrder_Creation_Body,
														String OptionOrder_Creation_StatusCode,
														String OptionOrder_Creation_Response,
														String Subscribe_OptionOrder_BasePath,
														String StatusCode,
														String Subscribe_OptionOrder_UserID,
														String Subscribe_OptionOrder_OrderType,
														String Subscribe_OptionOrder_Side,
														String Subscribe_OptionOrder_Symbol,
														String Subscribe_OptionOrder_Account,
														String Subscribe_OptionOrder_Destination,
														String Subscribe_OptionOrder_Price,
														String Subscribe_OptionOrder_OrderQty,
														String Subscribe_OptionOrder_boothID,
														String Subscribe_OptionOrder_ExpectedStatus,
														String SELL_Subscribe_OptionOrder_UserID,
														String SELL_Subscribe_OptionOrder_OrderType,
														String SELL_Subscribe_OptionOrder_Side,
														String SELL_Subscribe_OptionOrder_Symbol,
														String SELL_Subscribe_OptionOrder_Account,
														String SELL_Subscribe_OptionOrder_Destination,
														String SELL_Subscribe_OptionOrder_Price,
														String SELL_Subscribe_OptionOrder_OrderQty,
														String SELL_Subscribe_OptionOrder_boothID,
														String SELL_Subscribe_OptionOrder_ExpectedStatus,
														String Subscribe_Executions_BasePath,
														String Subscribe_Executions_StatusCode,
														String Executions_Case,
														String Executions_Buy_SideDesc,
														String Executions_Buy_Side,
														String Fetch_Executions_Buy_Symbol,
														String Fetch_Executions_Buy_Account,
														String Fetch_Executions_Buy_OrderQty,
														String Fetch_Executions_Buy_Price,
														String Fetch_Executions_Buy_OrderType,
														String Fetch_Executions_Buy_Destination,
														String Fetch_Executions_Buy_SymbolSfx,
														String Fetch_Executions_Buy_Status,
														String Fetch_Executions_Buy_Text,
														String Fetch_Executions_Buy_OrdStatus,
														String Fetch_Last_Executions_Buy_OrdStatus,
														String Fetch_Executions_Buy_OriginatingUserDesc,
														String Fetch_Executions_Buy_ExecBroker,
														String Fetch_Executions_Buy_TimeInForce,
														String Fetch_Executions_Buy_ExecRefID,
														String Fetch_Executions_Buy_ExecTransType,
														String Fetch_Executions_Buy_ExecTransTypeDesc,
														String Executions_Sell_SideDesc,
														String Executions_Sell_Side,
														String Fetch_Executions_Sell_Symbol,
														String Fetch_Executions_Sell_Account,
														String Fetch_Executions_Sell_OrderQty,
														String Fetch_Executions_Sell_Price,
														String Fetch_Executions_Sell_OrderType,
														String Fetch_Executions_Sell_Destination,
														String Fetch_Executions_Sell_SymbolSfx,
														String Fetch_Executions_Sell_Status,
														String Fetch_Executions_Sell_Text,
														String Fetch_Executions_Sell_OrdStatus,
														String Fetch_Last_Executions_Sell_OrdStatus,
														String Fetch_Executions_Sell_OriginatingUserDesc,
														String Fetch_Executions_Sell_ExecBroker,
														String Fetch_Executions_Sell_TimeInForce,
														String Fetch_Executions_Sell_ExecRefID,
														String Fetch_Executions_Sell_ExecTransType,
														String Fetch_Executions_Sell_ExecTransTypeDesc,
														String Validate_Position_comment_Value,
														String Validate_Position_currency_Value,
														String Validate_Position_exchangeName_Value,
														String Validate_Position_exchangeID_Value,
														String Validate_Position_rule80A_Value,
														String Validate_Position_positionOperation_Value,
														String Validate_Position_positionSide_Value,
														String Validate_Position_parentOrdPrice_Value,
														String Validate_Position_parentOrdQty_Value,
														String Validate_Position_clientID_Value,
														String Validate_Position_boothID_Value,
														String Validate_Position_symbolWithoutSfx_Value,
														String Validate_Position_optionSymbol_Value,
														String Validate_Position_optionsFields_Value,
														String Validate_Position_OptionDesc,
														String Validate_Position_maturityDay_Value,
														String Validate_Position_maturityMonthYear_Value,
														String Validate_Position_maturityMonthYearDesc_Value,
														String Validate_Position_maturityDate_Value,
														String Validate_Position_strikePrice_Value,
														String Validate_Position_putOrCallInt_Value,
														String Validate_Position_putOrCall_Value,
														String Validate_Position_coveredOrUncoveredInt_Value,
														String Validate_Position_coveredOrUncovered_Value,
														String Validate_Position_customerOrFirmInt_Value,
														String Validate_Position_customerOrFirm_Value,
														String Validate_Position_openCloseBoxed_Value,
														String Validate_Position_openClose_Value,
														String Validate_Position_cmta_Value,
														String Validate_Position_expiryDate_Value,
														String Validate_Position_symbol_Value,
														String Validate_Position_positionString_Value,
														String Validate_Position_symbolSfx_Value,
														String Validate_Position_originatingUserDesc_Value,
														String Validate_Position_account_Value)
	 {
	 	try
		{
			 LoggingManager.logger.info("====================================================================");
			 LoggingManager.logger.info("TestCase : "+Order_Position_TestCases);
			 LoggingManager.logger.info("====================================================================");

			 DecimalFormat decimalFormat = new DecimalFormat("0.0000");
			 decimalFormat.getRoundingMode();
			 RestAssured.baseURI=Global.BaseURL;
			// String position_ID=Validate_BoothID+"-"+Validate_Symbol_Value+" "+Validate_Mat_ExpDate+" "+Validate_StrikePrice_Value+" "+Validate_PutOrCall_Value+"-"+Validate_Account_Value;
			 //String position_OptionDesc=Validate_Symbol_Value+" "+Validate_Mat_ExpDate+" "+Validate_StrikePrice_Value+" "+Validate_PutOrCall_Value;
			 APIHelperClass.Flat_Option_Positions(	Endpoint_Version,
													Subscribe_Option_Positions_BasePath,
													Content_Type,
													Subscribe_Option_Positions_StatusCode,
													Position_ID,
													Validate_Position_FLAT,
													Order_Creation_BasePath,
													Order_OrdType,
													Order_TimeInForce,
													Order_Destination,
													Order_Price,
													Order_StopPx,
													Order_PutOrCall,
													Order_StrikePrice,
													Order_CoveredOrUncovered,
													Order_CustomerOrFirm,
													Order_Cmta,
													Order_OpenClose,
													Order_ExpiryDate,
													Order_Creation_StatusCode );

			LoggingManager.logger.info("API-Get Option realizedPnL After Flat : ["+ Global.getOptionLONGrealizedPnL+"]");
			//LoggingManager.logger.info("API-Get Option SHORTrealizedPnL After Flat : ["+ Global.getOptionSHORTrealizedPnL+"]");

			 //====================================================BUY Order=============================================================================

			 Response BUY_response=
					 		 given()
							 .header("Content-Type",Content_Type)
							 .header("Authorization", "Bearer " + Global.getAccToken)
							 .body(BUY_OptionOrder_Creation_Body)

							 .when()
							 .post(Order_Creation_BasePath)

							 .then()
							 .extract()
							 .response();


			 LoggingManager.logger.info("API-OptionOrder_Filled_BUY_BasePath : ["+Order_Creation_BasePath+"]");
			 LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			 LoggingManager.logger.info("API-OptionOrder_Filled_BUY_Body : ["+BUY_OptionOrder_Creation_Body+"]");
			 LoggingManager.logger.info("API-OptionOrder_Filled_BUY_StatusCode : ["+BUY_response.getStatusCode()+"]");
			 LoggingManager.logger.info("API-OptionOrder_Filled_BUY_Response_Body : ["+BUY_response.getBody().asString()+"]");
			 Assert.assertEquals(BUY_response.getStatusCode(), Integer.parseInt(OptionOrder_Creation_StatusCode),"Verify_Option_Order_BUY_Creation");
			 Assert.assertEquals(BUY_response.getBody().asString(), OptionOrder_Creation_Response,"Verify_OptionOrder_BUY_Creation_Response");

			 //====================================================SELL Order=============================================================================

			 Response SELL_response=

							 given()
							 .header("Content-Type",Content_Type)
							 .header("Authorization", "Bearer " + Global.getAccToken)
							 .body(SELL_OptionOrder_Creation_Body)

							 .when()
							 .post(Order_Creation_BasePath)

							 .then()
							 .extract()
							 .response();


			 LoggingManager.logger.info("API-OptionOrder_Filled_SELL_BasePath : ["+Order_Creation_BasePath+"]");
			 LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			 LoggingManager.logger.info("API-OptionOrder_Filled_SELL_Body : ["+SELL_OptionOrder_Creation_Body+"]");
			 LoggingManager.logger.info("API-OptionOrder_Filled_SELL_StatusCode : ["+SELL_response.getStatusCode()+"]");
			 LoggingManager.logger.info("API-OptionOrder_Filled_SELL_Response_Body : ["+SELL_response.getBody().asString()+"]");
			 Assert.assertEquals(SELL_response.getStatusCode(), Integer.parseInt(OptionOrder_Creation_StatusCode),"Verify_Option_Order_Creation");
			 Assert.assertEquals(SELL_response.getBody().asString(), OptionOrder_Creation_Response,"Verify_OptionOrder_SELL_Creation_Response");


//=====================================================================Buy Order ID Fetching=============================================

			 APIHelperClass.GetOrderValues(	 Subscribe_OptionOrder_BasePath,
											 Global.getAccToken,
											 Content_Type,
											 Integer.parseInt(StatusCode),
											 Endpoint_Version,
											 Subscribe_OptionOrder_UserID,
											 Subscribe_OptionOrder_ExpectedStatus,
											 Subscribe_OptionOrder_Account,
											 Subscribe_OptionOrder_Symbol,
											 Subscribe_OptionOrder_Destination,
											 Subscribe_OptionOrder_Price,
											 Subscribe_OptionOrder_Side,
											 Subscribe_OptionOrder_OrderQty,
											 Subscribe_OptionOrder_OrderType,"option");

			 Global.getOptionBuyFilledOrderID=Global.getOptionOrderID;
			 Global.getOptionBuyFilled_qOrderID=Global.getOptionqOrderID;
			 Global.getOptioncompleteDayBuyOrderQty+=Double.parseDouble(Subscribe_OptionOrder_OrderQty);
			 LoggingManager.logger.info("API- BUY OrderID Before Execution : ["+Global.getOptionBuyFilledOrderID+"]");
			 LoggingManager.logger.info("API- BUY qOrderID Before Execution : ["+Global.getOptionBuyFilled_qOrderID+"]");
			 if(Global.getOptionBuyFilledOrderID == null || Global.getOptionBuyFilledOrderID=="" )
			 {Assert.fail("Logs : Buy Order Not Found with status :["+Subscribe_OptionOrder_ExpectedStatus+"]");}

//=====================================================================Sell Order ID Fetching=============================================	

			 APIHelperClass.GetOrderValues(	Subscribe_OptionOrder_BasePath,
											 Global.getAccToken,
											 Content_Type,
											 Integer.parseInt(StatusCode),
											 Endpoint_Version,
											 SELL_Subscribe_OptionOrder_UserID,
											 SELL_Subscribe_OptionOrder_ExpectedStatus,
											 SELL_Subscribe_OptionOrder_Account,
											 SELL_Subscribe_OptionOrder_Symbol,
											 SELL_Subscribe_OptionOrder_Destination,
											 SELL_Subscribe_OptionOrder_Price,
											 SELL_Subscribe_OptionOrder_Side,
											 SELL_Subscribe_OptionOrder_OrderQty,
											 SELL_Subscribe_OptionOrder_OrderType,"option");

			 Global.getOptionSellFilledOrderID=Global.getOptionOrderID;
			 Global.getOptionSellFilled_qOrderID=Global.getOptionqOrderID;
			 Global.getOptioncompleteDaySellLongOrderQty+=Double.parseDouble(SELL_Subscribe_OptionOrder_OrderQty);
			 LoggingManager.logger.info("API- SELL OrderID Before Execution : ["+Global.getOptionSellFilledOrderID+"]");
			 LoggingManager.logger.info("API- SELL qOrderID Before Execution : ["+Global.getOptionSellFilled_qOrderID+"]");

			 //=====================================================================Execution Validations=============================================

			 Response get_execution_response=

											  given()
											 .header("Content-Type",Content_Type)
											 .header("Authorization", "Bearer " + Global.getAccToken)
											 .when()
											 .get(Subscribe_Executions_BasePath)

											 .then()
											 .statusCode(Integer.parseInt(Subscribe_Executions_StatusCode))
											 .extract().response();

			 LoggingManager.logger.info("Verify_SubscribeSELLExecutions Buy Order ID : "+Global.getOptionBuyFilledOrderID);
			 LoggingManager.logger.info("Verify_SubscribeSELLExecutions Sell Order ID  : "+Global.getOptionSellFilledOrderID);

			 APIHelperClass.Validate_Executions(get_execution_response,
												 Endpoint_Version,
												 Global.getOptionBoothID,
												 Executions_Case,
												 Executions_Buy_SideDesc,
												 Executions_Buy_Side,
												 Global.getOptionBuyFilledOrderID,
												 Global.getOptionBuyFilled_qOrderID,
												 Fetch_Executions_Buy_Symbol,
												 Fetch_Executions_Buy_Account,
												 Fetch_Executions_Buy_OrderQty,
												 Fetch_Executions_Buy_Price,
												 Fetch_Executions_Buy_OrderType,
												 Fetch_Executions_Buy_Destination,
												 Fetch_Executions_Buy_SymbolSfx,
												 Fetch_Executions_Buy_Status,
												 Fetch_Executions_Buy_Text,
												 Fetch_Executions_Buy_OrdStatus,
												 Fetch_Last_Executions_Buy_OrdStatus,
												 Fetch_Executions_Buy_OriginatingUserDesc,
												 Fetch_Executions_Buy_ExecBroker,
												 Fetch_Executions_Buy_TimeInForce,
												 Fetch_Executions_Buy_ExecRefID,
												 Fetch_Executions_Buy_ExecTransType,
												 Fetch_Executions_Buy_ExecTransTypeDesc,
												 Executions_Sell_SideDesc,
												 Executions_Sell_Side,
												 Global.getOptionSellFilledOrderID,
												 Global.getOptionSellFilled_qOrderID,
												 Fetch_Executions_Sell_Symbol,
												 Fetch_Executions_Sell_Account,
												 Fetch_Executions_Sell_OrderQty,
												 Fetch_Executions_Sell_Price,
												 Fetch_Executions_Sell_OrderType,
												 Fetch_Executions_Sell_Destination,
												 Fetch_Executions_Sell_SymbolSfx,
												 Fetch_Executions_Sell_Status,
												 Fetch_Executions_Sell_Text,
												 Fetch_Executions_Sell_OrdStatus,
												 Fetch_Last_Executions_Sell_OrdStatus,
												 Fetch_Executions_Sell_OriginatingUserDesc,
												 Fetch_Executions_Sell_ExecBroker,
												 Fetch_Executions_Sell_TimeInForce,
												 Fetch_Executions_Sell_ExecRefID,
												 Fetch_Executions_Sell_ExecTransType,
												 Fetch_Executions_Sell_ExecTransTypeDesc,"position");

			 Global.getOptionAvgPrice=Global.getAvgPrice;
			 // Global.getOptionLONGrealizedPnL=Global.getLONGrealizedPnL;
			 LoggingManager.logger.info("API - Calculated AvgPrice After Executions : "+Global.getOptionAvgPrice);
			 LoggingManager.logger.info("API - Calculated RealizePNL After Executions : "+Global.getOptionLONGrealizedPnL);
			 //=======================================================Position Subcription================================================================

			 LoggingManager.logger.info("API-Subscribe_position_ID : ["+Position_ID+"]");
			 Response get_position_response=

											  given()
											 .header("Content-Type",Content_Type)
											 .header("Authorization", "Bearer " + Global.getAccToken)
											 .when()
											 .get(Subscribe_Option_Positions_BasePath)
											 .then().extract().response();

			 Assert.assertEquals(get_position_response.getStatusCode(), Integer.parseInt(Subscribe_Option_Positions_StatusCode),"Verify_Subscribe_Option_Positions_StatusCode");
			 APIHelperClass.Validate_Option_Positions(get_position_response,
														 Endpoint_Version,
														 Position_ID,
														 Global.getOptionAvgPrice,
														 Global.getOptionLONGrealizedPnL,
														 Validate_Position_comment_Value,
														 Validate_Position_currency_Value,
														 Validate_Position_exchangeName_Value,
														 Validate_Position_exchangeID_Value,
														 Validate_Position_rule80A_Value,
														 Integer.parseInt(Validate_Position_positionOperation_Value),
														 Integer.parseInt(Validate_Position_positionSide_Value),
														 Double.parseDouble(Validate_Position_parentOrdPrice_Value),
														 Double.parseDouble(Validate_Position_parentOrdQty_Value),
														 Validate_Position_clientID_Value,
														 Validate_Position_boothID_Value,
														 Validate_Position_symbolWithoutSfx_Value,
														 Validate_Position_optionSymbol_Value,
														 Validate_Position_optionsFields_Value,
														 Validate_Position_OptionDesc,
														 Integer.parseInt(Validate_Position_maturityDay_Value),
														 Validate_Position_maturityMonthYear_Value,
														 Validate_Position_maturityMonthYearDesc_Value,
														 Validate_Position_maturityDate_Value,
														 Double.parseDouble(Validate_Position_strikePrice_Value),
														 Integer.parseInt(Validate_Position_putOrCallInt_Value),
														 Validate_Position_putOrCall_Value,
														 Integer.parseInt(Validate_Position_coveredOrUncoveredInt_Value),
														 Validate_Position_coveredOrUncovered_Value,
														 Integer.parseInt(Validate_Position_customerOrFirmInt_Value),
														 Validate_Position_customerOrFirm_Value,
														 Validate_Position_openCloseBoxed_Value,
														 Validate_Position_openClose_Value,
														 Validate_Position_cmta_Value,
														 Validate_Position_expiryDate_Value,
														 Validate_Position_symbol_Value,
														 Validate_Position_positionString_Value,
														 Validate_Position_symbolSfx_Value,
														 Validate_Position_originatingUserDesc_Value,
														 Validate_Position_account_Value );

		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	 }
	 
	 
	 @Test (dataProvider="SubscribeSHORTOption_Position", dataProviderClass=ExcelDataProvider.class , groups={"SubscribeSHORTOption_Position"}, dependsOnGroups={"UserLoginAuthentications"})//UserLoginAuthentications  Flat_Option_Position
	 public void Verify_Subscribe_SHORTOption_Position(	String Option_Position_TestCases,
														String Endpoint_Version,
														String Subscribe_Option_Positions_BasePath,
														String Content_Type,
														String Subscribe_Option_Positions_StatusCode,
													    String Position_ID,
													    String Validate_Position_FLAT,
													    String Order_Creation_BasePath,
													    String Order_OrdType,
													    String Order_TimeInForce,
													    String Order_Destination,
													    String Order_Price,
													    String Order_StopPx,
													    String Order_PutOrCall,
													    String Order_StrikePrice,
													    String Order_CoveredOrUncovered,
													    String Order_CustomerOrFirm,
													    String Order_Cmta,
													    String Order_OpenClose,
													    String Order_ExpiryDate,
													    String Order_Creation_StatusCode,
														String OptionOrder_Creation_Body,
														String OptionOrder_Creation_StatusCode,
														String OptionOrder_Creation_Response,
														String Subscribe_OptionOrder_BasePath,
														String StatusCode,
														String Subscribe_OptionOrder_UserID,
														String Subscribe_OptionOrder_OrderType,
														String Subscribe_OptionOrder_Side,
														String Subscribe_OptionOrder_Symbol,
														String Subscribe_OptionOrder_Account,
														String Subscribe_OptionOrder_Destination,
														String Subscribe_OptionOrder_Price,
														String Subscribe_OptionOrder_OrderQty,
													    String Subscribe_OptionOrder_ExpectedStatus,
										 			    String Subscribe_Executions_BasePath,
														String Subscribe_Executions_StatusCode,
														String Executions_Case,
														String Executions_Sell_SideDesc,
														String Executions_Sell_Side,
														String Fetch_Executions_Sell_Symbol,
														String Fetch_Executions_Sell_Account,
														String Fetch_Executions_Sell_OrderQty,
														String Fetch_Executions_Sell_Price,
														String Fetch_Executions_Sell_OrderType,
														String Fetch_Executions_Sell_Destination,
														String Fetch_Executions_Sell_SymbolSfx,
														String Fetch_Executions_Sell_Status,
														String Fetch_Executions_Sell_Text,
														String Fetch_Executions_Sell_OrdStatus,
														String Fetch_Last_Executions_Sell_OrdStatus,
														String Fetch_Executions_Sell_OriginatingUserDesc,
														String Fetch_Executions_Sell_ExecBroker,
														String Fetch_Executions_Sell_TimeInForce,
														String Fetch_Executions_Sell_ExecRefID,
														String Fetch_Executions_Sell_ExecTransType,
														String Fetch_Executions_Sell_ExecTransTypeDesc,
														String Validate_Position_comment_Value,
														String Validate_Position_currency_Value,
														String Validate_Position_exchangeName_Value,
														String Validate_Position_exchangeID_Value,
														String Validate_Position_rule80A_Value,
														String Validate_Position_positionOperation_Value,
														String Validate_Position_positionSide_Value,
														String Validate_Position_parentOrdPrice_Value,
														String Validate_Position_parentOrdQty_Value,
														String Validate_Position_clientID_Value,
														String Validate_Position_boothID_Value,
														String Validate_Position_symbolWithoutSfx_Value,
														String Validate_Position_optionSymbol_Value,
														String Validate_Position_optionsFields_Value,
														String Validate_Position_OptionDesc,
														String Validate_Position_maturityDay_Value,
														String Validate_Position_maturityMonthYear_Value,
														String Validate_Position_maturityMonthYearDesc_Value,
														String Validate_Position_maturityDate_Value,
														String Validate_Position_strikePrice_Value,
														String Validate_Position_putOrCallInt_Value,
														String Validate_Position_putOrCall_Value,
														String Validate_Position_coveredOrUncoveredInt_Value,
														String Validate_Position_coveredOrUncovered_Value,
														String Validate_Position_customerOrFirmInt_Value,
														String Validate_Position_customerOrFirm_Value,
														String Validate_Position_openCloseBoxed_Value,
														String Validate_Position_openClose_Value,
														String Validate_Position_cmta_Value,
														String Validate_Position_expiryDate_Value,
														String Validate_Position_symbol_Value,
														String Validate_Position_positionString_Value,
														String Validate_Position_symbolSfx_Value,
														String Validate_Position_originatingUserDesc_Value,
														String Validate_Position_account_Value)
																							  
	 {
	 	try
		{
			 LoggingManager.logger.info("====================================================================");
			 LoggingManager.logger.info("TestCase : "+Option_Position_TestCases);
			 LoggingManager.logger.info("====================================================================");

			 DecimalFormat decimalFormat = new DecimalFormat("0.0000");
			 decimalFormat.getRoundingMode();
			 RestAssured.baseURI=Global.BaseURL;
			 //String position_ID=Validate_BoothID+"-"+Validate_Symbol_Value+" "+Validate_Mat_ExpDate+" "+Validate_StrikePrice_Value+" "+Validate_PutOrCall_Value+"-"+Validate_Account_Value;
			 //String position_OptionDesc=Validate_Symbol_Value+" "+Validate_Mat_ExpDate+" "+Validate_StrikePrice_Value+" "+Validate_PutOrCall_Value;
			 APIHelperClass.Flat_Option_Positions(	Endpoint_Version,
													Subscribe_Option_Positions_BasePath,
													Content_Type,
													Subscribe_Option_Positions_StatusCode,
													Position_ID,
													Validate_Position_FLAT,
													Order_Creation_BasePath,
													Order_OrdType,
													Order_TimeInForce,
													Order_Destination,
													Order_Price,
													Order_StopPx,
													Order_PutOrCall,
													Order_StrikePrice,
													Order_CoveredOrUncovered,
													Order_CustomerOrFirm,
													Order_Cmta,
													Order_OpenClose,
													Order_ExpiryDate,
													Order_Creation_StatusCode );

			//LoggingManager.logger.info("API-Get Option realizedPnL After Flat : ["+ Global.getOptionLONGrealizedPnL+"]");
			LoggingManager.logger.info("API-Get Option realizedPnL After Flat : ["+ Global.getOptionSHORTrealizedPnL+"]");
			//=======================================================Order Creation================================================================

			 Response response=

					 		  given()
							 .header("Content-Type",Content_Type)
							 .header("Authorization", "Bearer " + Global.getAccToken)
							 .body(OptionOrder_Creation_Body)

							 .when()
							 .post(Order_Creation_BasePath)

							 .then()
							 .extract()
							 .response();

			 LoggingManager.logger.info("API-OptionOrder_Filled_SELL_BasePath : ["+Order_Creation_BasePath+"]");
			 LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			 LoggingManager.logger.info("API-OptionOrder_Filled_SELL_Body : ["+OptionOrder_Creation_Body+"]");
			 LoggingManager.logger.info("API-OptionOrder_Filled_SELL_StatusCode : ["+response.getStatusCode()+"]");
			 LoggingManager.logger.info("API-OptionOrder_Filled_SELL_Response_Body : ["+response.getBody().asString()+"]");
			 Assert.assertEquals(response.getStatusCode(), Integer.parseInt(OptionOrder_Creation_StatusCode),"Verify_OptionOrder_Creation_StatusCode");
			 Assert.assertEquals(response.getBody().asString(), OptionOrder_Creation_Response,"Verify_OptionOrder_Creation_Response");
			 APIHelperClass.GetOrderValues(Subscribe_OptionOrder_BasePath,
											 Global.getAccToken,
											 Content_Type,
											 Integer.parseInt(StatusCode),
											 Endpoint_Version,
											 Subscribe_OptionOrder_UserID,
											 Subscribe_OptionOrder_ExpectedStatus,
											 Subscribe_OptionOrder_Account,
											 Subscribe_OptionOrder_Symbol,
											 Subscribe_OptionOrder_Destination,
											 Subscribe_OptionOrder_Price,
											 Subscribe_OptionOrder_Side,
											 Subscribe_OptionOrder_OrderQty,
											 Subscribe_OptionOrder_OrderType,"option");

			 Global.getOptionSellFilledOrderID=Global.getOptionOrderID;
			 Global.getOptionSellFilled_qOrderID=Global.getOptionqOrderID;
			 Global.getOptioncompleteDaySellLongOrderQty+=Double.parseDouble(Subscribe_OptionOrder_OrderQty);
			 LoggingManager.logger.info("API- SELL OrderID Before Execution : ["+Global.getOptionSellFilledOrderID+"]");
			 LoggingManager.logger.info("API- SELL qOrderID Before Execution : ["+Global.getOptionSellFilled_qOrderID+"]");
			 if(Global.getOptionSellFilledOrderID == null || Global.getOptionSellFilledOrderID=="" )
			 {Assert.fail("Logs : Sell Order Not Found with status :["+Subscribe_OptionOrder_ExpectedStatus+"]");}

			 //----------------------------------------------------------Executions ---------------------------------------------------------------
			 Response get_execution_response=

					 		  given()
							 .header("Content-Type",Content_Type)
							 .header("Authorization", "Bearer " + Global.getAccToken)
							 .when()
							 .get(Subscribe_Executions_BasePath)

							 .then()
							 //.statusCode(Integer.parseInt(Subscribe_Executions_StatusCode))
							 //.statusLine("HTTP/1.1 200 OK")
							 .extract().response();

			 LoggingManager.logger.info("API-Subscribe_Executions_BasePath : ["+Subscribe_Executions_BasePath+"]");
			 LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			 LoggingManager.logger.info("API-Subscribe_Executions_StatusCode : ["+get_execution_response.getStatusCode()+"]");
			 Assert.assertEquals(get_execution_response.getStatusCode(), Integer.parseInt(Subscribe_Executions_StatusCode),"Verify_Subscribe_Sell_Executions");
			 APIHelperClass.Validate_Executions( get_execution_response,
												 Endpoint_Version,
												 Global.getOptionBoothID,
												 Executions_Case,
												 Executions_Sell_SideDesc,
												 Executions_Sell_Side,
												 Global.getOptionSellFilledOrderID,
												 Global.getOptionSellFilled_qOrderID,
												 Fetch_Executions_Sell_Symbol,
												 Fetch_Executions_Sell_Account,
												 Fetch_Executions_Sell_OrderQty,
												 Fetch_Executions_Sell_Price,
												 Fetch_Executions_Sell_OrderType,
												 Fetch_Executions_Sell_Destination,
												 Fetch_Executions_Sell_SymbolSfx,
												 Fetch_Executions_Sell_Status,
												 Fetch_Executions_Sell_Text,
												 Fetch_Executions_Sell_OrdStatus,
												 Fetch_Last_Executions_Sell_OrdStatus,
												 Fetch_Executions_Sell_OriginatingUserDesc,
												 Fetch_Executions_Sell_ExecBroker,
												 Fetch_Executions_Sell_TimeInForce,
												 Fetch_Executions_Sell_ExecRefID,
												 Fetch_Executions_Sell_ExecTransType,
												 Fetch_Executions_Sell_ExecTransTypeDesc,"","","",0,"","","","","","","","","","","","","","","","","","position");

			 //=======================================================Position Subcription================================================================

			 LoggingManager.logger.info("API-Subscribe_position_ID : ["+Position_ID+"]");
			 Response get_position_response=

							  given()
							 .header("Content-Type",Content_Type)
							 .header("Authorization", "Bearer " + Global.getAccToken)
							 .when()
							 .get(Subscribe_Option_Positions_BasePath)
							 .then().extract().response();

			 Assert.assertEquals(get_position_response.getStatusCode(), Integer.parseInt(Subscribe_Option_Positions_StatusCode),"Verify_Subscribe_Option_Positions_StatusCode");
			 APIHelperClass.Validate_Option_Positions(get_position_response,
														 Endpoint_Version,
														 Position_ID,
														 Global.getOptionAvgPrice,
														 Global.getOptionSHORTrealizedPnL,
														 Validate_Position_comment_Value,
														 Validate_Position_currency_Value,
														 Validate_Position_exchangeName_Value,
														 Validate_Position_exchangeID_Value,
														 Validate_Position_rule80A_Value,
														 Integer.parseInt(Validate_Position_positionOperation_Value),
														 Integer.parseInt(Validate_Position_positionSide_Value),
														 Double.parseDouble(Validate_Position_parentOrdPrice_Value),
														 Double.parseDouble(Validate_Position_parentOrdQty_Value),
														 Validate_Position_clientID_Value,
														 Validate_Position_boothID_Value,
														 Validate_Position_symbolWithoutSfx_Value,
														 Validate_Position_optionSymbol_Value,
														 Validate_Position_optionsFields_Value,
														 Validate_Position_OptionDesc,
														 Integer.parseInt(Validate_Position_maturityDay_Value),
														 Validate_Position_maturityMonthYear_Value,
														 Validate_Position_maturityMonthYearDesc_Value,
														 Validate_Position_maturityDate_Value,
														 Double.parseDouble(Validate_Position_strikePrice_Value),
														 Integer.parseInt(Validate_Position_putOrCallInt_Value),
														 Validate_Position_putOrCall_Value,
														 Integer.parseInt(Validate_Position_coveredOrUncoveredInt_Value),
														 Validate_Position_coveredOrUncovered_Value,
														 Integer.parseInt(Validate_Position_customerOrFirmInt_Value),
														 Validate_Position_customerOrFirm_Value,
														 Validate_Position_openCloseBoxed_Value,
														 Validate_Position_openClose_Value,
														 Validate_Position_cmta_Value,
														 Validate_Position_expiryDate_Value,
														 Validate_Position_symbol_Value,
														 Validate_Position_positionString_Value,
														 Validate_Position_symbolSfx_Value,
														 Validate_Position_originatingUserDesc_Value,
														 Validate_Position_account_Value );

		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	 }
}
