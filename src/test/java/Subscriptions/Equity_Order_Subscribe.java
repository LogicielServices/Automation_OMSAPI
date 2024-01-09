package Subscriptions;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import APIHelper.APIHelperClass;
import APIHelper.Global;
import APIHelper.LoggingManager;
import XLDataProvider.ExcelDataProvider;
import io.restassured.RestAssured;

//Test(groups = {"Create"},dependsOnGroups={"UserLoginAuthentications"})
public class Equity_Order_Subscribe {

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

	 @Test (dataProvider="SubscribeActiveEquityOrder", dataProviderClass=ExcelDataProvider.class , groups={"SubscribeActiveEquityOrder"}, dependsOnGroups={"UserLoginAuthentications"}) //UserLoginAuthentications CreateActiveOpenEquityOrder
	 public void Verify_SubscribeActiveEquityOrder(	 String TestCases,String Endpoint_Version,
			 										 String Subscribe_EquityOrder_BasePath,
													 String Content_Type,
												     String Subscribe_EquityOrder_StatusCode,
													 String Subscribe_EquityOrder_UserID,
													 String Subscribe_EquityOrder_OrderType,
													 String Subscribe_EquityOrder_Side,
													 String Subscribe_EquityOrder_SideDesc,
													 String Subscribe_EquityOrder_Symbol,
													 String Subscribe_EquityOrder_Account,
													 String Subscribe_EquityOrder_Destination,
													 String Subscribe_EquityOrder_Price,
													 String Subscribe_EquityOrder_OrderQty,	
													 String Subscribe_EquityOrder_Text,
													 String Subscribe_EquityOrder_complianceID,
													 String Subscribe_EquityOrder_stopPx,
													 String Subscribe_EquityOrder_timeInForce,
													 String Subscribe_EquityOrder_tifDesc,
													 String Subscribe_EquityOrder_symbolSfx,
													 String Subscribe_EquityOrder_symbolWithoutSfx,
													 String Subscribe_EquityOrder_orderTypeDesc,
													 String Subscribe_EquityOrder_avgPx,
													 String Subscribe_EquityOrder_cumQty,
													 String Subscribe_EquityOrder_workableQty,
													 String Subscribe_EquityOrder_leavesQty,
													 String Subscribe_EquityOrder_locateID,
													 String Subscribe_EquityOrder_contactName,
													 String Subscribe_EquityOrder_locateRequired,
													 String Subscribe_EquityOrder_locateRate,
													 String Subscribe_EquityOrder_boothID,
												     String Subscribe_EquityOrder_ExpectedStatus)
		{
			 	LoggingManager.logger.info("====================================================================");
				LoggingManager.logger.info("TestCase : "+TestCases);
				LoggingManager.logger.info("====================================================================");
				APIHelperClass.GetOrderValues(Subscribe_EquityOrder_BasePath,
														    Global.getAccToken,
														    Content_Type,
														    Integer.parseInt(Subscribe_EquityOrder_StatusCode),
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

				LoggingManager.logger.info("API-Found Order_ID :  ["+Global.getOrderID+"]");
				LoggingManager.logger.info("API-Found Order_Status :  ["+Global.getStatus+"]");
				LoggingManager.logger.info("API-Found Side Desc. :  ["+Global.getSideDesc+"]");
				if(Global.getOrderID == null || Global.getOrderID=="" ) 
				{Assert.fail("Logs : Order Not Found with status :["+Subscribe_EquityOrder_ExpectedStatus+"]");}
				APIHelperClass.OrdersSubscriptionValidation( Subscribe_EquityOrder_ExpectedStatus,
														Subscribe_EquityOrder_UserID,
														Subscribe_EquityOrder_OrderType,
														Subscribe_EquityOrder_Side,
														Subscribe_EquityOrder_SideDesc,
														Subscribe_EquityOrder_Symbol,
														Subscribe_EquityOrder_Account,
														Subscribe_EquityOrder_Destination,
														Subscribe_EquityOrder_Price,
														Subscribe_EquityOrder_OrderQty,
														Subscribe_EquityOrder_Text,
														Subscribe_EquityOrder_complianceID,
														Subscribe_EquityOrder_stopPx,
														Subscribe_EquityOrder_timeInForce,
														Subscribe_EquityOrder_tifDesc,
														Subscribe_EquityOrder_symbolSfx,
														Subscribe_EquityOrder_symbolWithoutSfx,
														Subscribe_EquityOrder_orderTypeDesc,
														Subscribe_EquityOrder_avgPx,
														Subscribe_EquityOrder_cumQty,
														Subscribe_EquityOrder_workableQty,
														Subscribe_EquityOrder_leavesQty,
														Subscribe_EquityOrder_locateID,
														Subscribe_EquityOrder_contactName,
														Subscribe_EquityOrder_locateRequired,
														Subscribe_EquityOrder_locateRate,
														Subscribe_EquityOrder_boothID,
														Subscribe_EquityOrder_ExpectedStatus,"","","","");
												
				
				
		}
	

	 @Test (dataProvider="SubscribeBUYFilledEquityOrder", dataProviderClass=ExcelDataProvider.class , groups={"SubscribeBUYFilledEquityOrder"}, dependsOnGroups={"UserLoginAuthentications"})//UserLoginAuthentications CreateBUYFilledEquityOrder
	 public void Verify_SubscribeBUYFilledEquityOrder(	 String TestCases,String Endpoint_Version,
			 											 String Subscribe_EquityOrder_BasePath,
			 											 String Subscribe_Executions_BasePath,
														 String Content_Type,
														 String Subscribe_StatusCode,
														 String Subscribe_EquityOrder_UserID,
														 String Subscribe_EquityOrder_OrderType,
														 String Subscribe_EquityOrder_Side,
														 String Subscribe_EquityOrder_SideDesc,
														 String Subscribe_EquityOrder_Symbol,
														 String Subscribe_EquityOrder_Account,
														 String Subscribe_EquityOrder_Destination,
														 String Subscribe_EquityOrder_Price,
														 String Subscribe_EquityOrder_OrderQty,
														 
														 String Subscribe_EquityOrder_Text,
														 String Subscribe_EquityOrder_complianceID,
														 String Subscribe_EquityOrder_stopPx,
														 String Subscribe_EquityOrder_timeInForce,
														 String Subscribe_EquityOrder_tifDesc,
														 String Subscribe_EquityOrder_symbolSfx,
														 String Subscribe_EquityOrder_symbolWithoutSfx,
														 String Subscribe_EquityOrder_orderTypeDesc,
														 String Subscribe_EquityOrder_avgPx,
														 String Subscribe_EquityOrder_cumQty,
														 String Subscribe_EquityOrder_workableQty,
														 String Subscribe_EquityOrder_leavesQty,
														 String Subscribe_EquityOrder_locateID,
														 String Subscribe_EquityOrder_contactName,
														 String Subscribe_EquityOrder_locateRequired,
														 String Subscribe_EquityOrder_locateRate,
														 String Subscribe_EquityOrder_boothID,
														 String Subscribe_EquityOrder_ExpectedStatus)
		{
		 	
			 	LoggingManager.logger.info("====================================================================");
				LoggingManager.logger.info("TestCase : "+TestCases);
				LoggingManager.logger.info("====================================================================");
		 		APIHelperClass.GetOrderValues( Subscribe_EquityOrder_BasePath,
															Global.getAccToken,
															Content_Type,
															Integer.parseInt(Subscribe_StatusCode),
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
				
			
				LoggingManager.logger.info("API-Found BUY Order_ID :  ["+Global.getOrderID+"]");
				LoggingManager.logger.info("API-Found BUY Side Desc. :  ["+Global.getSideDesc+"]");
				LoggingManager.logger.info("API-Found BUY Order_Status :  ["+Global.getStatus+"]");
				if(Global.getOrderID == null || Global.getOrderID=="" ) 
				{Assert.fail("Logs : Buy Order Not Found with status :["+Subscribe_EquityOrder_ExpectedStatus+"]");}
				APIHelperClass.OrdersSubscriptionValidation( Subscribe_EquityOrder_ExpectedStatus,
														Subscribe_EquityOrder_UserID,
														Subscribe_EquityOrder_OrderType,
														Subscribe_EquityOrder_Side,
														Subscribe_EquityOrder_SideDesc,
														Subscribe_EquityOrder_Symbol,
														Subscribe_EquityOrder_Account,
														Subscribe_EquityOrder_Destination,
														Subscribe_EquityOrder_Price,
														Subscribe_EquityOrder_OrderQty,
														Subscribe_EquityOrder_Text,
														Subscribe_EquityOrder_complianceID,
														Subscribe_EquityOrder_stopPx,
														Subscribe_EquityOrder_timeInForce,
														Subscribe_EquityOrder_tifDesc,
														Subscribe_EquityOrder_symbolSfx,
														Subscribe_EquityOrder_symbolWithoutSfx,
														Subscribe_EquityOrder_orderTypeDesc,
														Subscribe_EquityOrder_avgPx,
														Subscribe_EquityOrder_cumQty,
														Subscribe_EquityOrder_workableQty,
														Subscribe_EquityOrder_leavesQty,
														Subscribe_EquityOrder_locateID,
														Subscribe_EquityOrder_contactName,
														Subscribe_EquityOrder_locateRequired,
														Subscribe_EquityOrder_locateRate,
														Subscribe_EquityOrder_boothID,
														Subscribe_EquityOrder_ExpectedStatus,
														Endpoint_Version,
														Subscribe_Executions_BasePath,
														Content_Type,
														Subscribe_StatusCode);
			
		}
	 
	 
	 
	 @Test (dataProvider="SubscribeSELLFilledEquityOrder", dataProviderClass=ExcelDataProvider.class , groups={"SubscribeSELLFilledEquityOrder"}, dependsOnGroups={"UserLoginAuthentications"})//UserLoginAuthentications CreateSELLFilledEquityOrder
	 public void Verify_SubscribeSELLFilledEquityOrder(	 String TestCases,String Endpoint_Version,
			 											 String Subscribe_EquityOrder_BasePath,
			 											 String Subscribe_Executions_BasePath,
														 String Content_Type,
													     String Subscribe_StatusCode,
														 String Subscribe_EquityOrder_UserID,
														 String Subscribe_EquityOrder_OrderType,
														 String Subscribe_EquityOrder_Side,
														 String Subscribe_EquityOrder_SideDesc,
														 String Subscribe_EquityOrder_Symbol,
														 String Subscribe_EquityOrder_Account,
														 String Subscribe_EquityOrder_Destination,
														 String Subscribe_EquityOrder_Price,
														 String Subscribe_EquityOrder_OrderQty,
														 
														 String Subscribe_EquityOrder_Text,
														 String Subscribe_EquityOrder_complianceID,
														 String Subscribe_EquityOrder_stopPx,
														 String Subscribe_EquityOrder_timeInForce,
														 String Subscribe_EquityOrder_tifDesc,
														 String Subscribe_EquityOrder_symbolSfx,
														 String Subscribe_EquityOrder_symbolWithoutSfx,
														 String Subscribe_EquityOrder_orderTypeDesc,
														 String Subscribe_EquityOrder_avgPx,
														 String Subscribe_EquityOrder_cumQty,
														 String Subscribe_EquityOrder_workableQty,
														 String Subscribe_EquityOrder_leavesQty,
														 String Subscribe_EquityOrder_locateID,
														 String Subscribe_EquityOrder_contactName,
														 String Subscribe_EquityOrder_locateRequired,
														 String Subscribe_EquityOrder_locateRate,
														 String Subscribe_EquityOrder_boothID,
														 String Subscribe_EquityOrder_ExpectedStatus,
													     
													     String SELL_Subscribe_EquityOrder_UserID,
														 String SELL_Subscribe_EquityOrder_OrderType,
														 String SELL_Subscribe_EquityOrder_Side,
														 String SELL_Subscribe_EquityOrder_SideDesc,
														 String SELL_Subscribe_EquityOrder_Symbol,
														 String SELL_Subscribe_EquityOrder_Account,
														 String SELL_Subscribe_EquityOrder_Destination,
														 String SELL_Subscribe_EquityOrder_Price,
														 String SELL_Subscribe_EquityOrder_OrderQty,
														 String SELL_Subscribe_EquityOrder_Text,
														 String SELL_Subscribe_EquityOrder_complianceID,
														 String SELL_Subscribe_EquityOrder_stopPx,
														 String SELL_Subscribe_EquityOrder_timeInForce,
														 String SELL_Subscribe_EquityOrder_tifDesc,
														 String SELL_Subscribe_EquityOrder_symbolSfx,
														 String SELL_Subscribe_EquityOrder_symbolWithoutSfx,
														 String SELL_Subscribe_EquityOrder_orderTypeDesc,
														 String SELL_Subscribe_EquityOrder_avgPx,
														 String SELL_Subscribe_EquityOrder_cumQty,
														 String SELL_Subscribe_EquityOrder_workableQty,
														 String SELL_Subscribe_EquityOrder_leavesQty,
														 String SELL_Subscribe_EquityOrder_locateID,
														 String SELL_Subscribe_EquityOrder_contactName,
														 String SELL_Subscribe_EquityOrder_locateRequired,
														 String SELL_Subscribe_EquityOrder_locateRate,
														 String SELL_Subscribe_EquityOrder_boothID,
														 String SELL_Subscribe_EquityOrder_ExpectedStatus)
		{
			 	LoggingManager.logger.info("====================================================================");
				LoggingManager.logger.info("TestCase : "+TestCases);
				LoggingManager.logger.info("====================================================================");
			//========================================================Get Buy Order ID===================================================================
				APIHelperClass.GetOrderValues( Subscribe_EquityOrder_BasePath,
																Global.getAccToken,
																Content_Type,
																Integer.parseInt(Subscribe_StatusCode),
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
				LoggingManager.logger.info("API-Found BUY Order_ID :  ["+Global.getBuyFilledOrderID+"]");
				LoggingManager.logger.info("API-Found BUY Side Desc. :  ["+Global.getSideDesc+"]");
				LoggingManager.logger.info("API-Found BUY Order_Status :  ["+Global.getStatus+"]");
				if(Global.getBuyFilledOrderID == null || Global.getBuyFilledOrderID=="" ) 
				{Assert.fail("Logs : Buy Order Not Found with status :["+Subscribe_EquityOrder_ExpectedStatus+"]");}
				APIHelperClass.OrdersSubscriptionValidation( Subscribe_EquityOrder_ExpectedStatus,
														Subscribe_EquityOrder_UserID,
														Subscribe_EquityOrder_OrderType,
														Subscribe_EquityOrder_Side,
														Subscribe_EquityOrder_SideDesc,
														Subscribe_EquityOrder_Symbol,
														Subscribe_EquityOrder_Account,
														Subscribe_EquityOrder_Destination,
														Subscribe_EquityOrder_Price,
														Subscribe_EquityOrder_OrderQty,
														Subscribe_EquityOrder_Text,
														Subscribe_EquityOrder_complianceID,
														Subscribe_EquityOrder_stopPx,
														Subscribe_EquityOrder_timeInForce,
														Subscribe_EquityOrder_tifDesc,
														Subscribe_EquityOrder_symbolSfx,
														Subscribe_EquityOrder_symbolWithoutSfx,
														Subscribe_EquityOrder_orderTypeDesc,
														Subscribe_EquityOrder_avgPx,
														Subscribe_EquityOrder_cumQty,
														Subscribe_EquityOrder_workableQty,
														Subscribe_EquityOrder_leavesQty,
														Subscribe_EquityOrder_locateID,
														Subscribe_EquityOrder_contactName,
														Subscribe_EquityOrder_locateRequired,
														Subscribe_EquityOrder_locateRate,
														Subscribe_EquityOrder_boothID,
														Subscribe_EquityOrder_ExpectedStatus,
														Endpoint_Version,
														Subscribe_Executions_BasePath,
														Content_Type,
														Subscribe_StatusCode);
				
		//========================================================Get Sell Order ID===================================================================
				
				APIHelperClass.GetOrderValues(  Subscribe_EquityOrder_BasePath,
															Global.getAccToken,
															Content_Type,
															Integer.parseInt(Subscribe_StatusCode),
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
				LoggingManager.logger.info("API-Found SELL Order_ID :  ["+Global.getSellFilledOrderID+"]");
				LoggingManager.logger.info("API-Found SELL Side Desc. :  ["+Global.getSideDesc+"]");
				LoggingManager.logger.info("API-Found SELL Order_Status :  ["+Global.getStatus+"]");
				if(Global.getSellFilledOrderID == null || Global.getSellFilledOrderID=="" ) 
				{Assert.fail("Logs : Sell Order Not Found with status :["+SELL_Subscribe_EquityOrder_ExpectedStatus+"]");}
				APIHelperClass.OrdersSubscriptionValidation( SELL_Subscribe_EquityOrder_ExpectedStatus,
														SELL_Subscribe_EquityOrder_UserID,
														SELL_Subscribe_EquityOrder_OrderType,
														SELL_Subscribe_EquityOrder_Side,
														SELL_Subscribe_EquityOrder_SideDesc,
														SELL_Subscribe_EquityOrder_Symbol,
														SELL_Subscribe_EquityOrder_Account,
														SELL_Subscribe_EquityOrder_Destination,
														SELL_Subscribe_EquityOrder_Price,
														SELL_Subscribe_EquityOrder_OrderQty,
														SELL_Subscribe_EquityOrder_Text,
														SELL_Subscribe_EquityOrder_complianceID,
														SELL_Subscribe_EquityOrder_stopPx,
														SELL_Subscribe_EquityOrder_timeInForce,
														SELL_Subscribe_EquityOrder_tifDesc,
														SELL_Subscribe_EquityOrder_symbolSfx,
														SELL_Subscribe_EquityOrder_symbolWithoutSfx,
														SELL_Subscribe_EquityOrder_orderTypeDesc,
														SELL_Subscribe_EquityOrder_avgPx,
														SELL_Subscribe_EquityOrder_cumQty,
														SELL_Subscribe_EquityOrder_workableQty,
														SELL_Subscribe_EquityOrder_leavesQty,
														SELL_Subscribe_EquityOrder_locateID,
														SELL_Subscribe_EquityOrder_contactName,
														SELL_Subscribe_EquityOrder_locateRequired,
														SELL_Subscribe_EquityOrder_locateRate,
														SELL_Subscribe_EquityOrder_boothID,
														SELL_Subscribe_EquityOrder_ExpectedStatus,
														Endpoint_Version,
														Subscribe_Executions_BasePath,
														Content_Type,
														Subscribe_StatusCode);
		}
	 
	 
	 
	 @Test (dataProvider="Subscribe_SS_FilledEquityOrder", dataProviderClass=ExcelDataProvider.class , groups={"Subscribe_SS_FilledEquityOrder"}, dependsOnGroups={"UserLoginAuthentications"})//UserLoginAuthentications CreateBUYFilledEquityOrder
	 public void Verify_SubscribeSHORTSELLFilledEquityOrder( String TestCases,String Endpoint_Version,
				 											 String Subscribe_EquityOrder_BasePath,
				 											 String Subscribe_Executions_BasePath,
															 String Content_Type,
														     String Subscribe_StatusCode,
														     String Subscribe_EquityOrder_UserID,
															 String Subscribe_EquityOrder_OrderType,
															 String Subscribe_EquityOrder_Side,
															 String Subscribe_EquityOrder_SideDesc,
															 String Subscribe_EquityOrder_Symbol,
															 String Subscribe_EquityOrder_Account,
															 String Subscribe_EquityOrder_Destination,
															 String Subscribe_EquityOrder_Price,
															 String Subscribe_EquityOrder_OrderQty,
															 
															 String Subscribe_EquityOrder_Text,
															 String Subscribe_EquityOrder_complianceID,
															 String Subscribe_EquityOrder_stopPx,
															 String Subscribe_EquityOrder_timeInForce,
															 String Subscribe_EquityOrder_tifDesc,
															 String Subscribe_EquityOrder_symbolSfx,
															 String Subscribe_EquityOrder_symbolWithoutSfx,
															 String Subscribe_EquityOrder_orderTypeDesc,
															 String Subscribe_EquityOrder_avgPx,
															 String Subscribe_EquityOrder_cumQty,
															 String Subscribe_EquityOrder_workableQty,
															 String Subscribe_EquityOrder_leavesQty,
															 String Subscribe_EquityOrder_locateID,
															 String Subscribe_EquityOrder_contactName,
															 String Subscribe_EquityOrder_locateRequired,
															 String Subscribe_EquityOrder_locateRate,
															 String Subscribe_EquityOrder_boothID,
															 String Subscribe_EquityOrder_ExpectedStatus)
		{
			 	LoggingManager.logger.info("====================================================================");
				LoggingManager.logger.info("TestCase : "+TestCases);
				LoggingManager.logger.info("====================================================================");
				APIHelperClass.GetOrderValues( Subscribe_EquityOrder_BasePath,
															Global.getAccToken,
															Content_Type,
															Integer.parseInt(Subscribe_StatusCode),
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
				LoggingManager.logger.info("API-Found ShortSell Order_ID :  ["+Global.getShortSellFilledOrderID+"]");
				LoggingManager.logger.info("API-Found ShortSell Side Desc. :  ["+Global.getSideDesc+"]");
				LoggingManager.logger.info("API-Found ShortSell Order_Status :  ["+Global.getStatus+"]");
				if(Global.getShortSellFilledOrderID == null || Global.getShortSellFilledOrderID=="" ) 
				{Assert.fail("Logs : Order Not Found with status :["+Subscribe_EquityOrder_ExpectedStatus+"]");}
				APIHelperClass.OrdersSubscriptionValidation( Subscribe_EquityOrder_ExpectedStatus,
														Subscribe_EquityOrder_UserID,
														Subscribe_EquityOrder_OrderType,
														Subscribe_EquityOrder_Side,
														Subscribe_EquityOrder_SideDesc,
														Subscribe_EquityOrder_Symbol,
														Subscribe_EquityOrder_Account,
														Subscribe_EquityOrder_Destination,
														Subscribe_EquityOrder_Price,
														Subscribe_EquityOrder_OrderQty,
														Subscribe_EquityOrder_Text,
														Subscribe_EquityOrder_complianceID,
														Subscribe_EquityOrder_stopPx,
														Subscribe_EquityOrder_timeInForce,
														Subscribe_EquityOrder_tifDesc,
														Subscribe_EquityOrder_symbolSfx,
														Subscribe_EquityOrder_symbolWithoutSfx,
														Subscribe_EquityOrder_orderTypeDesc,
														Subscribe_EquityOrder_avgPx,
														Subscribe_EquityOrder_cumQty,
														Subscribe_EquityOrder_workableQty,
														Subscribe_EquityOrder_leavesQty,
														Subscribe_EquityOrder_locateID,
														Subscribe_EquityOrder_contactName,
														Subscribe_EquityOrder_locateRequired,
														Subscribe_EquityOrder_locateRate,
														Subscribe_EquityOrder_boothID,
														Subscribe_EquityOrder_ExpectedStatus,
														Endpoint_Version,
														Subscribe_Executions_BasePath,
														Content_Type,
														Subscribe_StatusCode);
				
		}
	 
	 
	 
	 @Test (dataProvider="SubscribeRejectedEquityOrder", dataProviderClass=ExcelDataProvider.class , groups={"SubscribeRejectedEquityOrder"}, dependsOnGroups={"UserLoginAuthentications"})//UserLoginAuthentications   CreateRejectedEquityOrder
	 public void Verify_SubscribeRejectedEquityOrder(	 String TestCases,String Endpoint_Version, 
			 											 String Subscribe_EquityOrder_BasePath,
														 String Content_Type,
													     String Subscribe_EquityOrder_StatusCode,
														 String Subscribe_EquityOrder_UserID,
														 String Subscribe_EquityOrder_OrderType,
														 String Subscribe_EquityOrder_Side,
														 String Subscribe_EquityOrder_SideDesc,
														 String Subscribe_EquityOrder_Symbol,
														 String Subscribe_EquityOrder_Account,
														 String Subscribe_EquityOrder_Destination,
														 String Subscribe_EquityOrder_Price,
														 String Subscribe_EquityOrder_OrderQty,
														 String Subscribe_EquityOrder_Text,
														 String Subscribe_EquityOrder_complianceID,
														 String Subscribe_EquityOrder_stopPx,
														 String Subscribe_EquityOrder_timeInForce,
														 String Subscribe_EquityOrder_tifDesc,
														 String Subscribe_EquityOrder_symbolSfx,
														 String Subscribe_EquityOrder_symbolWithoutSfx,
														 String Subscribe_EquityOrder_orderTypeDesc,
														 String Subscribe_EquityOrder_avgPx,
														 String Subscribe_EquityOrder_cumQty,
														 String Subscribe_EquityOrder_workableQty,
														 String Subscribe_EquityOrder_leavesQty,
														 String Subscribe_EquityOrder_locateID,
														 String Subscribe_EquityOrder_contactName,
														 String Subscribe_EquityOrder_locateRequired,
														 String Subscribe_EquityOrder_locateRate,
														 String Subscribe_EquityOrder_boothID,
														 String Subscribe_EquityOrder_ExpectedStatus)
	 {
			 	LoggingManager.logger.info("====================================================================");
				LoggingManager.logger.info("TestCase : "+TestCases);
				LoggingManager.logger.info("====================================================================");
				APIHelperClass.GetOrderValues( Subscribe_EquityOrder_BasePath,
																Global.getAccToken,
																Content_Type,
																Integer.parseInt(Subscribe_EquityOrder_StatusCode),
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

				
				LoggingManager.logger.info("API-Found Rejected Order_ID :  ["+Global.getOrderID+"]");
				LoggingManager.logger.info("API-Found Rejected Side Desc. :  ["+Global.getSideDesc+"]");
				LoggingManager.logger.info("API-Found Rejected Order_Status :  ["+Global.getStatus+"]");
				if(Global.getOrderID == null || Global.getOrderID=="" ) 
				{Assert.fail("Logs : Order Not Found with status :["+Subscribe_EquityOrder_ExpectedStatus+"]");}
				APIHelperClass.OrdersSubscriptionValidation( Subscribe_EquityOrder_ExpectedStatus,
														Subscribe_EquityOrder_UserID,
														Subscribe_EquityOrder_OrderType,
														Subscribe_EquityOrder_Side,
														Subscribe_EquityOrder_SideDesc,
														Subscribe_EquityOrder_Symbol,
														Subscribe_EquityOrder_Account,
														Subscribe_EquityOrder_Destination,
														Subscribe_EquityOrder_Price,
														Subscribe_EquityOrder_OrderQty,
														Subscribe_EquityOrder_Text,
														Subscribe_EquityOrder_complianceID,
														Subscribe_EquityOrder_stopPx,
														Subscribe_EquityOrder_timeInForce,
														Subscribe_EquityOrder_tifDesc,
														Subscribe_EquityOrder_symbolSfx,
														Subscribe_EquityOrder_symbolWithoutSfx,
														Subscribe_EquityOrder_orderTypeDesc,
														Subscribe_EquityOrder_avgPx,
														Subscribe_EquityOrder_cumQty,
														Subscribe_EquityOrder_workableQty,
														Subscribe_EquityOrder_leavesQty,
														Subscribe_EquityOrder_locateID,
														Subscribe_EquityOrder_contactName,
														Subscribe_EquityOrder_locateRequired,
														Subscribe_EquityOrder_locateRate,
														Subscribe_EquityOrder_boothID,
														Subscribe_EquityOrder_ExpectedStatus,"","","","");
				
		}
	 
	 
	
}
