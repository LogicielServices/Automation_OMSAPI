package Subscriptions;

import static io.restassured.RestAssured.given;

import java.io.PrintWriter;
import java.io.StringWriter;
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
@Log4j
public class Order_Positions_Subscribe {

	
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
	 
	 @Test (dataProvider="SubscribeBUYOrder_Positions", dataProviderClass=ExcelDataProvider.class , groups={"SubscribeBUYOrder_Positions"}, dependsOnGroups={"UserLoginAuthentications"})//Flat_Equity_Position UserLoginAuthentications
	 public void Verify_Subscribe_BUY_Order_Positions(	String Order_Position_TestCases,
			 											String Endpoint_Version,
			 											String Subscribe_Order_Positions_BasePath,
			 											String Content_Type,
														String Subscribe_Order_Positions_StatusCode,
														String Flat_Account_Type_BoxvsShort,
														String PositionID,
														String Validate_Position_FLAT,
														String Order_Creation_BasePath,
														String Order_OrdType,
														String Order_TimeInForce,
														String Order_Destination,
														String Order_Price,
														String Order_StopPx,
														String EquityOrder_Creation_Body,
														String EquityOrder_Creation_StatusCode,
														String EquityOrder_Creation_Response,
														String Subscribe_EquityOrder_BasePath,
														String StatusCode,
														String Subscribe_EquityOrder_UserID,
														String Subscribe_EquityOrder_OrderType,
														String Subscribe_EquityOrder_Side,
														String Subscribe_EquityOrder_Symbol,
														String Subscribe_EquityOrder_Account,
														String Subscribe_EquityOrder_Destination,
														String Subscribe_EquityOrder_Price,
														String Subscribe_EquityOrder_OrderQty,
													    String Subscribe_EquityOrder_ExpectedStatus,
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
														String Validate_Position_Symbol_Value,
														String Validate_Position_symbolSfx_Value,
														String Validate_Position_originatingUserDesc_Value,
														String Validate_Position_positionString_Value,
														String Validate_Position_Account_Value,
														String Validate_Position_BoothID)
	 {
	 	try
		{
			 LoggingManager.logger.info("====================================================================");
			 LoggingManager.logger.info("TestCase : "+Order_Position_TestCases);
			 LoggingManager.logger.info("====================================================================");
			 DecimalFormat decimalFormat = new DecimalFormat("0.0000");
			 decimalFormat.getRoundingMode();
			 RestAssured.baseURI=Global.BaseURL;
			 APIHelperClass.Flat_Equity_Positions(	Endpoint_Version,
													Subscribe_Order_Positions_BasePath,
													Content_Type,
													Subscribe_Order_Positions_StatusCode,
													PositionID,
													Validate_Position_FLAT,
													Order_Creation_BasePath,
													Order_OrdType,
													Order_TimeInForce,
													Order_Destination,
													Order_Price,
													Order_StopPx,
													StatusCode);


			 LoggingManager.logger.info("API-Get realizedPnL After Flat : ["+ Global.getLONGrealizedPnL+"]");
			 // LoggingManager.logger.info("API-Get SHORTrealizedPnL After Flat : ["+ Global.getSHORTrealizedPnL+"]");

			 //=======================================================Order Creation================================================================

			 Response response=

							 given()
							 .header("Content-Type",Content_Type)
							 .header("Authorization", "Bearer " + Global.getAccToken)
							 .body(EquityOrder_Creation_Body)

							 .when()
							 .post(Order_Creation_BasePath)

							 .then()
							 .extract()
							 .response();

			 LoggingManager.logger.info("API-EquityOrder_Filled_BUY_BasePath : ["+Order_Creation_BasePath+"]");
			 LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			 LoggingManager.logger.info("API-EquityOrder_Filled_BUY_Body : ["+EquityOrder_Creation_Body+"]");
			 LoggingManager.logger.info("API-EquityOrder_Filled_BUY_StatusCode : ["+response.getStatusCode()+"]");
			 LoggingManager.logger.info("API-EquityOrder_Filled_BUY_Response_Body : ["+response.getBody().asString()+"]");
			 Assert.assertEquals(response.getStatusCode(), Integer.parseInt(EquityOrder_Creation_StatusCode),"Verify_EquityOrder_Creation_StatusCode");
			 Assert.assertEquals(response.getBody().asString(), EquityOrder_Creation_Response,"Verify_EquityOrder_Creation_Response");
			 APIHelperClass.GetOrderValues(Subscribe_EquityOrder_BasePath,
										 Global.getAccToken,
										 Content_Type,
										 Integer.parseInt(StatusCode),
										 Endpoint_Version,
										 Subscribe_EquityOrder_UserID,
										 Subscribe_EquityOrder_ExpectedStatus,
										 Subscribe_EquityOrder_Account,
										 Subscribe_EquityOrder_Symbol,
										 Subscribe_EquityOrder_Destination,
										 Subscribe_EquityOrder_Price,
										 Subscribe_EquityOrder_Side,
										 Subscribe_EquityOrder_OrderQty,
										 Subscribe_EquityOrder_OrderType,"equity");

			 //LoggingManager.logger.info("API- BUY OrderID Before Execution : ["+Global.getOrderID+"]");
			 Global.getBuyFilledOrderID=Global.getOrderID;
			 Global.getBuyFilled_qOrderID=Global.qOrderID;
			 Global.getcompleteDayBuyOrderQty+=Double.parseDouble(Subscribe_EquityOrder_OrderQty);
			 LoggingManager.logger.info("API- BUY OrderID Before Execution : ["+Global.getBuyFilledOrderID+"]");
			 LoggingManager.logger.info("API- BUY qOrderID Before Execution : ["+Global.getBuyFilled_qOrderID+"]");
			 if(Global.getBuyFilledOrderID==null || Global.getBuyFilledOrderID=="" )
			 {Assert.fail("Logs : Order Not Found with status :["+Subscribe_EquityOrder_ExpectedStatus+"]");}
			 //=======================================================Execution Subcription================================================================

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
			 //APIHelperClass.Validate_Executions(get_execution_response,Executions_Case,"","","","",Global.getBuyFilledOrderID,Global.getBuyFilled_qOrderID,"","","","","","","","","","","","","","","","","","","","",0,"","","","","","","","","","","","","","","","","");

			 APIHelperClass.Validate_Executions( get_execution_response,
												 Endpoint_Version,
												 Global.getboothID,
												 Executions_Case,
												 Executions_SideDesc,
												 Executions_Side,
												 Global.getBuyFilledOrderID,
												 Global.getBuyFilled_qOrderID,
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

			 // LoggingManager.logger.info("API- Before Execution : ["+Global.getOrderID+"]");
			 //=======================================================Execution Subcription================================================================

			 Response get_position_response=
											 given()
											 .header("Content-Type",Content_Type)
											 .header("Authorization", "Bearer " + Global.getAccToken)
											 .when()
											 .get(Subscribe_Order_Positions_BasePath)
											 .then().extract().response();

			 APIHelperClass.Validate_Positions(get_position_response,Endpoint_Version,Flat_Account_Type_BoxvsShort,Global.getAvgPrice,Global.getLONGrealizedPnL,Validate_Position_Symbol_Value,Validate_Position_symbolSfx_Value,Validate_Position_originatingUserDesc_Value,Validate_Position_positionString_Value,Validate_Position_Account_Value,Validate_Position_BoothID);
		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	 }
	 
	 
	 @Test (dataProvider="SubscribeSELLOrder_Positions", dataProviderClass=ExcelDataProvider.class , groups={"SubscribeSELLOrder_Positions"}, dependsOnGroups={"UserLoginAuthentications"})//UserLoginAuthentications Flat_Equity_Position
	 public void Verify_Subscribe_SELL_Order_Positions(	String Order_Position_TestCases,
														String Endpoint_Version,
														String Subscribe_Order_Positions_BasePath,
														String Content_Type,
														String Subscribe_Order_Positions_StatusCode,
														String Flat_Account_Type_BoxvsShort,
														String PositionID,
													    String Validate_Position_FLAT,
													    String Order_Creation_BasePath,
													    String Order_OrdType,
													    String Order_TimeInForce,
													    String Order_Destination,
													    String Order_Price,
													    String Order_StopPx,
														String EquityOrder_Creation_Body,
														String EquityOrder_Creation_StatusCode,
														String EquityOrder_Creation_Response,
														String SELL_EquityOrder_Creation_Body,
														String SELL_EquityOrder_Creation_StatusCode,
														String SELL_EquityOrder_Creation_Response,
														String Subscribe_EquityOrder_BasePath,
														String StatusCode,
														String Subscribe_EquityOrder_UserID,
														String Subscribe_EquityOrder_OrderType,
														String Subscribe_EquityOrder_Side,
														String Subscribe_EquityOrder_Symbol,
														String Subscribe_EquityOrder_Account,
														String Subscribe_EquityOrder_Destination,
														String Subscribe_EquityOrder_Price,
														String Subscribe_EquityOrder_OrderQty,
														String Subscribe_EquityOrder_boothID,
													    String Subscribe_EquityOrder_ExpectedStatus,
														String SELL_Subscribe_EquityOrder_UserID,
														String SELL_Subscribe_EquityOrder_OrderType,
														String SELL_Subscribe_EquityOrder_Side,
														String SELL_Subscribe_EquityOrder_Symbol,
														String SELL_Subscribe_EquityOrder_Account,
														String SELL_Subscribe_EquityOrder_Destination,
														String SELL_Subscribe_EquityOrder_Price,
														String SELL_Subscribe_EquityOrder_OrderQty,
														String SELL_Subscribe_EquityOrder_boothID,
														String SELL_Subscribe_EquityOrder_ExpectedStatus,
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
														String Validate_Position_Symbol_Value,
														String Validate_Position_symbolSfx_Value,
														String Validate_Position_originatingUserDesc_Value,
														String Validate_Position_positionString_Value,
														String Validate_Position_Account_Value,
														String Validate_Position_BoothID )
	 {
	 	try
		{
			 LoggingManager.logger.info("====================================================================");
			 LoggingManager.logger.info("TestCase : "+Order_Position_TestCases);
			 LoggingManager.logger.info("====================================================================");

			 DecimalFormat decimalFormat = new DecimalFormat("0.0000");
			 decimalFormat.getRoundingMode();
			 RestAssured.baseURI=Global.BaseURL;
			 APIHelperClass.Flat_Equity_Positions(	Endpoint_Version,
													Subscribe_Order_Positions_BasePath,
													Content_Type,
													Subscribe_Order_Positions_StatusCode,
													PositionID,
													Validate_Position_FLAT,
													Order_Creation_BasePath,
													Order_OrdType,
													Order_TimeInForce,
													Order_Destination,
													Order_Price,
													Order_StopPx,
													StatusCode);

			 LoggingManager.logger.info("API-Get realizedPnL After Flat : ["+ Global.getLONGrealizedPnL+"]");
			 //LoggingManager.logger.info("API-Get SHORTrealizedPnL After Flat : ["+ Global.getSHORTrealizedPnL+"]");

			//====================================================BUY Order=============================================================================

			Response response=
					          given()
							 .header("Content-Type",Content_Type)
							 .header("Authorization", "Bearer " + Global.getAccToken)
							 .body(EquityOrder_Creation_Body)

							 .when()
							 .post(Order_Creation_BasePath)

							 .then()
							 .extract()
							 .response();

			 LoggingManager.logger.info("API-EquityOrder_Filled_BUY_BasePath : ["+Order_Creation_BasePath+"]");
			 LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			 LoggingManager.logger.info("API-EquityOrder_Filled_BUY_Body : ["+EquityOrder_Creation_Body+"]");
			 LoggingManager.logger.info("API-EquityOrder_Filled_BUY_StatusCode : ["+response.getStatusCode()+"]");
			 LoggingManager.logger.info("API-EquityOrder_Filled_BUY_Response_Body : ["+response.getBody().asString()+"]");
			 Assert.assertEquals(response.getStatusCode(), Integer.parseInt(EquityOrder_Creation_StatusCode),"Verify_Equity_Order_BUY_Creation");
			 Assert.assertEquals(response.getBody().asString(), EquityOrder_Creation_Response,"Verify_EquityOrder_BUY_Creation_Response");

			 //====================================================SELL Order=============================================================================

			 Response SELL_response=

					 		  given()
							 .header("Content-Type",Content_Type)
							 .header("Authorization", "Bearer " + Global.getAccToken)
							 .body(SELL_EquityOrder_Creation_Body)

							 .when()
							 .post(Order_Creation_BasePath)

							 .then()
							 .extract()
							 .response();


			 LoggingManager.logger.info("API-EquityOrder_Filled_SELL_BasePath : ["+Order_Creation_BasePath+"]");
			 LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			 LoggingManager.logger.info("API-EquityOrder_Filled_SELL_Body : ["+SELL_EquityOrder_Creation_Body+"]");
			 LoggingManager.logger.info("API-EquityOrder_Filled_SELL_StatusCode : ["+SELL_response.getStatusCode()+"]");
			 LoggingManager.logger.info("API-EquityOrder_Filled_SELL_Response_Body : ["+SELL_response.getBody().asString()+"]");
			 Assert.assertEquals(SELL_response.getStatusCode(), Integer.parseInt(SELL_EquityOrder_Creation_StatusCode),"Verify_Equity_Order_Creation");
			 Assert.assertEquals(response.getBody().asString(), SELL_EquityOrder_Creation_Response,"Verify_EquityOrder_SELL_Creation_Response");


//=====================================================================Buy Order ID Fetching=============================================

			APIHelperClass.GetOrderValues(	Subscribe_EquityOrder_BasePath,
											 Global.getAccToken,
											 Content_Type,
											 Integer.parseInt(StatusCode),
											 Endpoint_Version,
											 Subscribe_EquityOrder_UserID,
											 Subscribe_EquityOrder_ExpectedStatus,
											 Subscribe_EquityOrder_Account,
											 Subscribe_EquityOrder_Symbol,
											 Subscribe_EquityOrder_Destination,
											 Subscribe_EquityOrder_Price,
											 Subscribe_EquityOrder_Side,
											 Subscribe_EquityOrder_OrderQty,
											 Subscribe_EquityOrder_OrderType,"equity");

			 Global.getBuyFilledOrderID=Global.getOrderID;
			 Global.getBuyFilled_qOrderID=Global.qOrderID;
			 Global.getcompleteDayBuyOrderQty+=Double.parseDouble(Subscribe_EquityOrder_OrderQty);
			 LoggingManager.logger.info("API- BUY OrderID Before Execution : ["+Global.getBuyFilledOrderID+"]");
			 LoggingManager.logger.info("API- BUY qOrderID Before Execution : ["+Global.getBuyFilled_qOrderID+"]");

//=====================================================================Sell Order ID Fetching=============================================	

			APIHelperClass.GetOrderValues(	Subscribe_EquityOrder_BasePath,
											 Global.getAccToken,
											 Content_Type,
											 Integer.parseInt(StatusCode),
											 Endpoint_Version,
											 SELL_Subscribe_EquityOrder_UserID,
											 SELL_Subscribe_EquityOrder_ExpectedStatus,
											 SELL_Subscribe_EquityOrder_Account,
											 SELL_Subscribe_EquityOrder_Symbol,
											 SELL_Subscribe_EquityOrder_Destination,
											 SELL_Subscribe_EquityOrder_Price,
											 SELL_Subscribe_EquityOrder_Side,
											 SELL_Subscribe_EquityOrder_OrderQty,
											 SELL_Subscribe_EquityOrder_OrderType,"equity");

			 Global.getSellFilledOrderID=Global.getOrderID;
			 Global.getSellFilled_qOrderID=Global.qOrderID;
			 Global.getcompleteDaySellLongOrderQty+=Double.parseDouble(SELL_Subscribe_EquityOrder_OrderQty);
			 LoggingManager.logger.info("API- SELL OrderID Before Execution : ["+Global.getSellFilledOrderID+"]");
			 LoggingManager.logger.info("API- SELL qOrderID Before Execution : ["+Global.getSellFilled_qOrderID+"]");

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

			 LoggingManager.logger.info("Verify_SubscribeSELLExecutions Buy Order ID : "+Global.getBuyFilledOrderID);
			 LoggingManager.logger.info("Verify_SubscribeSELLExecutions Sell Order ID  : "+Global.getSellFilledOrderID);
			 APIHelperClass.Validate_Executions(get_execution_response,
												 Endpoint_Version,
												 Global.getboothID,
												 Executions_Case,
												 Executions_Buy_SideDesc,
												 Executions_Buy_Side,
												 Global.getBuyFilledOrderID,
												 Global.getBuyFilled_qOrderID,
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
												 Global.getSellFilledOrderID,
												 Global.getSellFilled_qOrderID,
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

			 LoggingManager.logger.info("API - Calculated  AvgPrice After Executions : "+Global.getAvgPrice);
			 LoggingManager.logger.info("API - Calculated RealizePNL After Executions : "+Global.getLONGrealizedPnL);
			 //=======================================================Position Subcription================================================================

			 Response get_position_response=
											  given()
											 .header("Content-Type",Content_Type)
											 .header("Authorization", "Bearer " + Global.getAccToken)
											 .when()
											 .get(Subscribe_Order_Positions_BasePath)
											 .then().extract().response();

			 APIHelperClass.Validate_Positions(get_position_response,Endpoint_Version,Flat_Account_Type_BoxvsShort,Global.getAvgPrice,Global.getLONGrealizedPnL,Validate_Position_Symbol_Value,Validate_Position_symbolSfx_Value,Validate_Position_originatingUserDesc_Value,Validate_Position_positionString_Value,Validate_Position_Account_Value,Validate_Position_BoothID);
			 //LoggingManager.logger.info("API - get_position_response : "+get_position_response.body().asString());
		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}

	 }
	 
	 
	  

	 @Test (dataProvider="SubscribeSHORTSELL_Position", dataProviderClass=ExcelDataProvider.class , groups={"SubscribeSHORTSELL_Position"}, dependsOnGroups={"UserLoginAuthentications"})//Flat_Equity_Position  UserLoginAuthentications
	 public void Verify_Subscribe_SHORTSELL_Order_Positions(	String Order_Position_TestCases,
					 											String Endpoint_Version,
					 											String Subscribe_Order_Positions_BasePath,
					 											String Content_Type,
																String Subscribe_Order_Positions_StatusCode,
																String Flat_Account_Type_BoxvsShort,
																String PositionID,
																String Validate_Position_FLAT,
																String Order_Creation_BasePath,
																String Order_OrdType,
																String Order_TimeInForce,
																String Order_Destination,
																String Order_Price,
																String Order_StopPx,
																String EquityOrder_Creation_Body,
																String EquityOrder_Creation_StatusCode,
																String EquityOrder_Creation_Response,
																String Subscribe_EquityOrder_BasePath,
																String StatusCode,
																String Subscribe_EquityOrder_UserID,
																String Subscribe_EquityOrder_OrderType,
																String Subscribe_EquityOrder_Side,
																String Subscribe_EquityOrder_Symbol,
																String Subscribe_EquityOrder_Account,
																String Subscribe_EquityOrder_Destination,
																String Subscribe_EquityOrder_Price,
																String Subscribe_EquityOrder_OrderQty,
															    String Subscribe_EquityOrder_ExpectedStatus,
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
																String Validate_Position_Symbol_Value,
																String Validate_Position_symbolSfx_Value,
																String Validate_Position_originatingUserDesc_Value,
																String Validate_Position_positionString_Value,
																String Validate_Position_Account_Value,
																String Validate_Position_BoothID)
	 {
	 	try
		{
			 LoggingManager.logger.info("====================================================================");
			 LoggingManager.logger.info("TestCase : "+Order_Position_TestCases);
			 LoggingManager.logger.info("====================================================================");

			 DecimalFormat decimalFormat = new DecimalFormat("0.0000");
			 decimalFormat.getRoundingMode();
			 RestAssured.baseURI=Global.BaseURL;
			 APIHelperClass.Flat_Equity_Positions(	Endpoint_Version,
													Subscribe_Order_Positions_BasePath,
													Content_Type,
													Subscribe_Order_Positions_StatusCode,
													PositionID,
													Validate_Position_FLAT,
													Order_Creation_BasePath,
													Order_OrdType,
													Order_TimeInForce,
													Order_Destination,
													Order_Price,
													Order_StopPx,
													StatusCode);

	 		 //LoggingManager.logger.info("API-Get LONGrealizedPnL After Flat : ["+ Global.getLONGrealizedPnL+"]");
			 LoggingManager.logger.info("API-Get realizedPnL After Flat : ["+ Global.getSHORTrealizedPnL+"]");

			 //=======================================================Order Creation================================================================

			 Response response=

							 given()
							 .header("Content-Type",Content_Type)
							 .header("Authorization", "Bearer " + Global.getAccToken)
							 .body(EquityOrder_Creation_Body)

							 .when()
							 .post(Order_Creation_BasePath)

							 .then()
							 .extract()
							 .response();

			 LoggingManager.logger.info("API-EquityOrder_Filled_BUY_BasePath : ["+Order_Creation_BasePath+"]");
			 LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			 LoggingManager.logger.info("API-EquityOrder_Filled_BUY_Body : ["+EquityOrder_Creation_Body+"]");
			 LoggingManager.logger.info("API-EquityOrder_Filled_BUY_StatusCode : ["+response.getStatusCode()+"]");
			 LoggingManager.logger.info("API-EquityOrder_Filled_BUY_Response_Body : ["+response.getBody().asString()+"]");
			 Assert.assertEquals(response.getStatusCode(), Integer.parseInt(EquityOrder_Creation_StatusCode),"Verify_EquityOrder_Creation_StatusCode");
			 Assert.assertEquals(response.getBody().asString(), EquityOrder_Creation_Response,"Verify_EquityOrder_Creation_Response");
			 APIHelperClass.GetOrderValues(Subscribe_EquityOrder_BasePath,
											 Global.getAccToken,
											 Content_Type,
											 Integer.parseInt(StatusCode),
											 Endpoint_Version,
											 Subscribe_EquityOrder_UserID,
											 Subscribe_EquityOrder_ExpectedStatus,
											 Subscribe_EquityOrder_Account,
											 Subscribe_EquityOrder_Symbol,
											 Subscribe_EquityOrder_Destination,
											 Subscribe_EquityOrder_Price,
											 Subscribe_EquityOrder_Side,
											 Subscribe_EquityOrder_OrderQty,
											 Subscribe_EquityOrder_OrderType,"equity");


			 Global.getShortSellFilledOrderID=Global.getOrderID;
			 Global.getShortSellFilled_qOrderID=Global.qOrderID;
			 Global.getcompleteDaySellShortOrderQty+=Double.parseDouble(Subscribe_EquityOrder_OrderQty);
			 LoggingManager.logger.info("API- ShortSell OrderID Before Execution : ["+Global.getShortSellFilledOrderID+"]");
			 LoggingManager.logger.info("API- ShortSell qOrderID Before Execution : ["+Global.getShortSellFilled_qOrderID+"]");
			 LoggingManager.logger.info("API- ShortSell completeDaySellShortOrderQty Before Execution : ["+Global.getcompleteDaySellShortOrderQty+"]");
			 if(Global.getShortSellFilledOrderID == null || Global.getShortSellFilledOrderID=="" )
			 {Assert.fail("Logs : Order Not Found with status :["+Subscribe_EquityOrder_ExpectedStatus+"]");}

			 //=======================================================Execution Subcription================================================================

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
			 Assert.assertEquals(get_execution_response.getStatusCode(), Integer.parseInt(Subscribe_Executions_StatusCode),"Verify_Subscribe_ShortSell_Executions");
			 //APIHelperClass.Validate_Executions(get_execution_response,Executions_Case,"","","","",Global.getBuyFilledOrderID,Global.getBuyFilled_qOrderID,"","","","","","","","","","","","","","","","","","","","",0,"","","","","","","","","","","","","","","","","");
			 APIHelperClass.Validate_Executions( get_execution_response,
												 Endpoint_Version,
												 Global.getboothID,
												 Executions_Case,
												 Executions_SideDesc,
												 Executions_Side,
												 Global.getShortSellFilledOrderID,
												 Global.getShortSellFilled_qOrderID,
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
			 // LoggingManager.logger.info("API- Before Execution : ["+Global.getOrderID+"]");

			 //=======================================================Execution Subcription================================================================
			 Response get_position_response=
											 given()
											 .header("Content-Type",Content_Type)
											 .header("Authorization", "Bearer " + Global.getAccToken)
											 .when()
											 .get(Subscribe_Order_Positions_BasePath)
											 .then().extract().response();

			 APIHelperClass.Validate_Positions(get_position_response,Endpoint_Version,Flat_Account_Type_BoxvsShort,Global.getAvgPrice,Global.getSHORTrealizedPnL,Validate_Position_Symbol_Value,Validate_Position_symbolSfx_Value,Validate_Position_originatingUserDesc_Value,Validate_Position_positionString_Value,Validate_Position_Account_Value,Validate_Position_BoothID);
		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	 }

}
