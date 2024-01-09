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
public class Option_Order_Subscribe {

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

	 
	 @Test (dataProvider="SubscribeActiveOptionOrder", dataProviderClass=ExcelDataProvider.class , groups={"SubscribeActiveOptionOrder"}, dependsOnGroups={"UserLoginAuthentications"})//UserLoginAuthentications CreateActive_OpenOptionOrder
	 public void Verify_SubscribeActiveOptionOrder(	 String OptionOrder_Subscribe_TestCases,
			 										 String Endpoint_Version,
			 										 String Subscribe_OptionOrder_BasePath,
													 String Content_Type,
												     String Subscribe_OptionOrder_StatusCode,
													 String Subscribe_OptionOrder_originatingUserDesc,
													 String Subscribe_OptionOrder_orderType,
													 String Subscribe_OptionOrder_side,
													 String Subscribe_OptionOrder_sideDesc,
													 String Subscribe_OptionOrder_symbol,
													 String Subscribe_OptionOrder_account,
													 String Subscribe_OptionOrder_destination,
													 String Subscribe_OptionOrder_price,
													 String Subscribe_OptionOrder_orderQty,	
													 String Subscribe_OptionOrder_optionSymbol,
													 String Subscribe_OptionOrder_strikePrice,
													 
													 String Subscribe_OptionOrder_maturityDay,
													 String Subscribe_OptionOrder_maturityMonthYear,
													 String Subscribe_OptionOrder_maturityMonthYearDesc,
													 String Subscribe_OptionOrder_maturityDate,
													 String Subscribe_OptionOrder_optionDateDesc,
													 
													 String Subscribe_OptionOrder_cmta,
													 String Subscribe_OptionOrder_execBroker,
													 String Subscribe_OptionOrder_putOrCallInt,
													 String Subscribe_OptionOrder_putOrCall,
													 String Subscribe_OptionOrder_coveredOrUncoveredInt,
													 String Subscribe_OptionOrder_coveredOrUncovered,
													 String Subscribe_OptionOrder_customerOrFirmInt,
													 String Subscribe_OptionOrder_customerOrFirm,
													 String Subscribe_OptionOrder_openCloseBoxed,
													 String Subscribe_OptionOrder_openClose,
													 String Subscribe_OptionOrder_text,
													 String Subscribe_OptionOrder_complianceID,
													 String Subscribe_OptionOrder_stopPx,
													 String Subscribe_OptionOrder_timeInForce,
													 String Subscribe_OptionOrder_tifDesc,
													 String Subscribe_OptionOrder_symbolSfx,
													 String Subscribe_OptionOrder_symbolWithoutSfx,
													 String Subscribe_OptionOrder_orderTypeDesc,
													 String Subscribe_OptionOrder_avgPx,
													 String Subscribe_OptionOrder_cumQty,
													 String Subscribe_OptionOrder_workableQty,
													 String Subscribe_OptionOrder_leavesQty,
													 String Subscribe_OptionOrder_locateID,
													 String Subscribe_OptionOrder_contactName,
													 String Subscribe_OptionOrder_locateRequired,
													 String Subscribe_OptionOrder_locateRate,
													 String Subscribe_OptionOrder_boothID,
												     String Subscribe_OptionOrder_ExpectedStatus )
		{
		 	 
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+OptionOrder_Subscribe_TestCases);
			LoggingManager.logger.info("====================================================================");	
			APIHelperClass.GetOrderValues( Subscribe_OptionOrder_BasePath,
																Global.getAccToken,
																Content_Type,
																Integer.parseInt(Subscribe_OptionOrder_StatusCode),
																Endpoint_Version,
																Subscribe_OptionOrder_originatingUserDesc,
																Subscribe_OptionOrder_ExpectedStatus,
																Subscribe_OptionOrder_account,
																Subscribe_OptionOrder_symbol,
																Subscribe_OptionOrder_destination,
																Subscribe_OptionOrder_price,
																Subscribe_OptionOrder_side,
																Subscribe_OptionOrder_orderQty,
																Subscribe_OptionOrder_orderType,"option");

				

			LoggingManager.logger.info("API-Found Order_ID :  ["+Global.getOptionOrderID+"]");
			LoggingManager.logger.info("API-Found Order_Status :  ["+Global.getOptionStatus+"]");
			LoggingManager.logger.info("API-Found Side Desc. :  ["+Global.getOptionSideDesc+"]");
			if(Global.getOptionOrderID == null || Global.getOptionOrderID=="" ) 
			{Assert.fail("Logs : Option Order Not Found with status :["+Subscribe_OptionOrder_ExpectedStatus+"]");}
			
			
			APIHelperClass.Option_OrdersSubscriptionValidation(  Subscribe_OptionOrder_ExpectedStatus,
															Subscribe_OptionOrder_originatingUserDesc,
															Subscribe_OptionOrder_orderType,
															Subscribe_OptionOrder_side,
															Subscribe_OptionOrder_sideDesc,
															Subscribe_OptionOrder_symbol,
															Subscribe_OptionOrder_account,
															Subscribe_OptionOrder_destination,
															Subscribe_OptionOrder_price,
															Subscribe_OptionOrder_orderQty,
															Subscribe_OptionOrder_optionSymbol,
															Subscribe_OptionOrder_strikePrice,
															Subscribe_OptionOrder_maturityDay,
															Subscribe_OptionOrder_maturityMonthYear,
															Subscribe_OptionOrder_maturityMonthYearDesc,
															Subscribe_OptionOrder_maturityDate,
															Subscribe_OptionOrder_optionDateDesc,
															Subscribe_OptionOrder_cmta,
															Subscribe_OptionOrder_execBroker,
															Subscribe_OptionOrder_putOrCallInt,
															Subscribe_OptionOrder_putOrCall,
															Subscribe_OptionOrder_coveredOrUncoveredInt,
															Subscribe_OptionOrder_coveredOrUncovered,
															Subscribe_OptionOrder_customerOrFirmInt,
															Subscribe_OptionOrder_customerOrFirm,
															Subscribe_OptionOrder_openCloseBoxed,
															Subscribe_OptionOrder_openClose,
															Subscribe_OptionOrder_text,
															Subscribe_OptionOrder_complianceID,
															Subscribe_OptionOrder_stopPx,
															Subscribe_OptionOrder_timeInForce,
															Subscribe_OptionOrder_tifDesc,
															Subscribe_OptionOrder_symbolSfx,
															Subscribe_OptionOrder_symbolWithoutSfx,
															Subscribe_OptionOrder_orderTypeDesc,
															Subscribe_OptionOrder_avgPx,
															Subscribe_OptionOrder_cumQty,
															Subscribe_OptionOrder_workableQty,
															Subscribe_OptionOrder_leavesQty,
															Subscribe_OptionOrder_locateID,
															Subscribe_OptionOrder_contactName,
															Subscribe_OptionOrder_locateRequired,
															Subscribe_OptionOrder_locateRate,
															Subscribe_OptionOrder_boothID,
															Subscribe_OptionOrder_ExpectedStatus,
															Endpoint_Version,"","","");
												
		}
	 
	 @Test (dataProvider="SubscribeBUYFilledOptionOrder", dataProviderClass=ExcelDataProvider.class , groups={"SubscribeBUYFilledOptionOrder"}, dependsOnGroups={"UserLoginAuthentications"})//UserLoginAuthentications CreateBUYFilledOptionOrder
	 public void Verify_Subscribe_BUYFilledOptionOrder(String OptionOrder_Subscribe_TestCases,
			 											 String Endpoint_Version,
			 											 String Subscribe_OptionOrder_BasePath,
			 											 String Subscribe_Executions_BasePath,
														 String Content_Type,
													     String Subscribe_StatusCode,
														 String Subscribe_OptionOrder_originatingUserDesc,
														 String Subscribe_OptionOrder_orderType,
														 String Subscribe_OptionOrder_side,
														 String Subscribe_OptionOrder_SideDesc,
														 String Subscribe_OptionOrder_symbol,
														 String Subscribe_OptionOrder_account,
														 String Subscribe_OptionOrder_destination,
														 String Subscribe_OptionOrder_price,
														 String Subscribe_OptionOrder_orderQty,
															 String Subscribe_OptionOrder_optionSymbol,
															 String Subscribe_OptionOrder_strikePrice,
															 
															 String Subscribe_OptionOrder_maturityDay,
															 String Subscribe_OptionOrder_maturityMonthYear,
															 String Subscribe_OptionOrder_maturityMonthYearDesc,
															 String Subscribe_OptionOrder_maturityDate,
															 String Subscribe_OptionOrder_optionDateDesc,
															 
															 String Subscribe_OptionOrder_cmta,
															 String Subscribe_OptionOrder_execBroker,
															 String Subscribe_OptionOrder_putOrCallInt,
															 String Subscribe_OptionOrder_putOrCall,
															 String Subscribe_OptionOrder_coveredOrUncoveredInt,
															 String Subscribe_OptionOrder_coveredOrUncovered,
															 String Subscribe_OptionOrder_customerOrFirmInt,
															 String Subscribe_OptionOrder_customerOrFirm,
															 String Subscribe_OptionOrder_openCloseBoxed,
															 String Subscribe_OptionOrder_openClose,
															 
														 
														 		 String Subscribe_OptionOrder_text,
																 String Subscribe_OptionOrder_complianceID,
																 String Subscribe_OptionOrder_stopPx,
																 String Subscribe_OptionOrder_timeInForce,
																 String Subscribe_OptionOrder_tifDesc,
																 String Subscribe_OptionOrder_symbolSfx,
																 String Subscribe_OptionOrder_symbolWithoutSfx,
																 String Subscribe_OptionOrder_orderTypeDesc,
																 String Subscribe_OptionOrder_avgPx,
																 String Subscribe_OptionOrder_cumQty,
																 String Subscribe_OptionOrder_workableQty,
																 String Subscribe_OptionOrder_leavesQty,
																 String Subscribe_OptionOrder_locateID,
																 String Subscribe_OptionOrder_contactName,
																 String Subscribe_OptionOrder_locateRequired,
																 String Subscribe_OptionOrder_locateRate,
																 String Subscribe_OptionOrder_boothID,
															     String Subscribe_OptionOrder_ExpectedStatus)
		{
		 	 
		 	LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+OptionOrder_Subscribe_TestCases);
			LoggingManager.logger.info("====================================================================");
			APIHelperClass.GetOrderValues( Subscribe_OptionOrder_BasePath,
																Global.getAccToken,
																Content_Type,
																Integer.parseInt(Subscribe_StatusCode),
																Endpoint_Version,
																Subscribe_OptionOrder_originatingUserDesc,
																Subscribe_OptionOrder_ExpectedStatus,
																Subscribe_OptionOrder_account,
																Subscribe_OptionOrder_symbol,
																Subscribe_OptionOrder_destination,
																Subscribe_OptionOrder_price,
																Subscribe_OptionOrder_side,
																Subscribe_OptionOrder_orderQty,
																Subscribe_OptionOrder_orderType,
																"option");
				
				Global.getOptionBuyFilledOrderID=Global.getOptionOrderID;	
				LoggingManager.logger.info("API-Found BUY Order_ID :  ["+Global.getOptionBuyFilledOrderID+"]");
				LoggingManager.logger.info("API-Found BUY Side Desc. :  ["+Global.getOptionSideDesc+"]");
				LoggingManager.logger.info("API-Found BUY Order_Status :  ["+Global.getOptionStatus+"]");
				if(Global.getOptionBuyFilledOrderID == null || Global.getOptionBuyFilledOrderID=="" ) 
				{Assert.fail("Logs : Buy Order Not Found with status :["+Subscribe_OptionOrder_ExpectedStatus+"]");}
							
				APIHelperClass.Option_OrdersSubscriptionValidation(  Subscribe_OptionOrder_ExpectedStatus,
																Subscribe_OptionOrder_originatingUserDesc,
																Subscribe_OptionOrder_orderType,
																Subscribe_OptionOrder_side,
																Subscribe_OptionOrder_SideDesc,
																Subscribe_OptionOrder_symbol,
																Subscribe_OptionOrder_account,
																Subscribe_OptionOrder_destination,
																Subscribe_OptionOrder_price,
																Subscribe_OptionOrder_orderQty,
																Subscribe_OptionOrder_optionSymbol,
																Subscribe_OptionOrder_strikePrice,
																Subscribe_OptionOrder_maturityDay,
																Subscribe_OptionOrder_maturityMonthYear,
																Subscribe_OptionOrder_maturityMonthYearDesc,
																Subscribe_OptionOrder_maturityDate,
																Subscribe_OptionOrder_optionDateDesc,
																Subscribe_OptionOrder_cmta,
																Subscribe_OptionOrder_execBroker,
																Subscribe_OptionOrder_putOrCallInt,
																Subscribe_OptionOrder_putOrCall,
																Subscribe_OptionOrder_coveredOrUncoveredInt,
																Subscribe_OptionOrder_coveredOrUncovered,
																Subscribe_OptionOrder_customerOrFirmInt,
																Subscribe_OptionOrder_customerOrFirm,
																Subscribe_OptionOrder_openCloseBoxed,
																Subscribe_OptionOrder_openClose,
																Subscribe_OptionOrder_text,
																Subscribe_OptionOrder_complianceID,
																Subscribe_OptionOrder_stopPx,
																Subscribe_OptionOrder_timeInForce,
																Subscribe_OptionOrder_tifDesc,
																Subscribe_OptionOrder_symbolSfx,
																Subscribe_OptionOrder_symbolWithoutSfx,
																Subscribe_OptionOrder_orderTypeDesc,
																Subscribe_OptionOrder_avgPx,
																Subscribe_OptionOrder_cumQty,
																Subscribe_OptionOrder_workableQty,
																Subscribe_OptionOrder_leavesQty,
																Subscribe_OptionOrder_locateID,
																Subscribe_OptionOrder_contactName,
																Subscribe_OptionOrder_locateRequired,
																Subscribe_OptionOrder_locateRate,
																Subscribe_OptionOrder_boothID,
																Subscribe_OptionOrder_ExpectedStatus,
																Endpoint_Version,
																Subscribe_Executions_BasePath,
																Content_Type,
																Subscribe_StatusCode);
					
		}
	
	 @Test (dataProvider="SubscribeSELLFilledOptionOrder", dataProviderClass=ExcelDataProvider.class , groups={"SubscribeSELLFilledOptionOrder"}, dependsOnGroups={"UserLoginAuthentications"}) //UserLoginAuthentications  CreateSELLFilledOptionOrder
	 public void Verify_Subscribe_SELLFilledOptionOrder(String OptionOrder_Subscribe_TestCases,
			 											 String Endpoint_Version,
			 											 String Subscribe_OptionOrder_BasePath,
			 											 String Subscribe_Executions_BasePath,
														 String Content_Type,
													     String Subscribe_StatusCode,
														 String Subscribe_OptionOrder_originatingUserDesc,
														 String Subscribe_OptionOrder_orderType,
														 String Subscribe_OptionOrder_side,
														 String Subscribe_OptionOrder_SideDesc,
														 String Subscribe_OptionOrder_symbol,
														 String Subscribe_OptionOrder_account,
														 String Subscribe_OptionOrder_destination,
														 String Subscribe_OptionOrder_price,
														 String Subscribe_OptionOrder_orderQty,
															 String Subscribe_OptionOrder_optionSymbol,
															 String Subscribe_OptionOrder_strikePrice,
															 
															 String Subscribe_OptionOrder_maturityDay,
															 String Subscribe_OptionOrder_maturityMonthYear,
															 String Subscribe_OptionOrder_maturityMonthYearDesc,
															 String Subscribe_OptionOrder_maturityDate,
															 String Subscribe_OptionOrder_optionDateDesc,
															 
															 
															 String Subscribe_OptionOrder_cmta,
															 String Subscribe_OptionOrder_execBroker,
															 String Subscribe_OptionOrder_putOrCallInt,
															 String Subscribe_OptionOrder_putOrCall,
															 String Subscribe_OptionOrder_coveredOrUncoveredInt,
															 String Subscribe_OptionOrder_coveredOrUncovered,
															 String Subscribe_OptionOrder_customerOrFirmInt,
															 String Subscribe_OptionOrder_customerOrFirm,
															 String Subscribe_OptionOrder_openCloseBoxed,
															 String Subscribe_OptionOrder_openClose,
															 
														 
														 		 String Subscribe_OptionOrder_text,
																 String Subscribe_OptionOrder_complianceID,
																 String Subscribe_OptionOrder_stopPx,
																 String Subscribe_OptionOrder_timeInForce,
																 String Subscribe_OptionOrder_tifDesc,
																 String Subscribe_OptionOrder_symbolSfx,
																 String Subscribe_OptionOrder_symbolWithoutSfx,
																 String Subscribe_OptionOrder_orderTypeDesc,
																 String Subscribe_OptionOrder_avgPx,
																 String Subscribe_OptionOrder_cumQty,
																 String Subscribe_OptionOrder_workableQty,
																 String Subscribe_OptionOrder_leavesQty,
																 String Subscribe_OptionOrder_locateID,
																 String Subscribe_OptionOrder_contactName,
																 String Subscribe_OptionOrder_locateRequired,
																 String Subscribe_OptionOrder_locateRate,
																 String Subscribe_OptionOrder_boothID,
															     String Subscribe_OptionOrder_ExpectedStatus,
															     
																	     String SELL_Subscribe_OptionOrder_originatingUserDesc,
																		 String SELL_Subscribe_OptionOrder_orderType,
																		 String SELL_Subscribe_OptionOrder_side,
																		 String SELL_Subscribe_OptionOrder_SideDesc,
																		 String SELL_Subscribe_OptionOrder_symbol,
																		 String SELL_Subscribe_OptionOrder_account,
																		 String SELL_Subscribe_OptionOrder_destination,
																		 String SELL_Subscribe_OptionOrder_price,
																		 String SELL_Subscribe_OptionOrder_orderQty,
																		 String SELL_Subscribe_OptionOrder_optionSymbol,
																		 String SELL_Subscribe_OptionOrder_strikePrice,
																		 
																		 String SELL_Subscribe_OptionOrder_maturityDay,
																		 String SELL_Subscribe_OptionOrder_maturityMonthYear,
																		 String SELL_Subscribe_OptionOrder_maturityMonthYearDesc,
																		 String SELL_Subscribe_OptionOrder_maturityDate,
																		 String SELL_Subscribe_OptionOrder_optionDateDesc,
																		 
																		 
																		 String SELL_Subscribe_OptionOrder_cmta,
																		 String SELL_Subscribe_OptionOrder_execBroker,
																		 String SELL_Subscribe_OptionOrder_putOrCallInt,
																		 String SELL_Subscribe_OptionOrder_putOrCall,
																		 String SELL_Subscribe_OptionOrder_coveredOrUncoveredInt,
																		 String SELL_Subscribe_OptionOrder_coveredOrUncovered,
																		 String SELL_Subscribe_OptionOrder_customerOrFirmInt,
																		 String SELL_Subscribe_OptionOrder_customerOrFirm,
																		 String SELL_Subscribe_OptionOrder_openCloseBoxed,
																		 String SELL_Subscribe_OptionOrder_openClose,
																				
																		 String SELL_Subscribe_OptionOrder_text,
																		 String SELL_Subscribe_OptionOrder_complianceID,
																		 String SELL_Subscribe_OptionOrder_stopPx,
																		 String SELL_Subscribe_OptionOrder_timeInForce,
																		 String SELL_Subscribe_OptionOrder_tifDesc,
																		 String SELL_Subscribe_OptionOrder_symbolSfx,
																		 String SELL_Subscribe_OptionOrder_symbolWithoutSfx,
																		 String SELL_Subscribe_OptionOrder_orderTypeDesc,
																		 String SELL_Subscribe_OptionOrder_avgPx,
																		 String SELL_Subscribe_OptionOrder_cumQty,
																		 String SELL_Subscribe_OptionOrder_workableQty,
																		 String SELL_Subscribe_OptionOrder_leavesQty,
																		 String SELL_Subscribe_OptionOrder_locateID,
																		 String SELL_Subscribe_OptionOrder_contactName,
																		 String SELL_Subscribe_OptionOrder_locateRequired,
																		 String SELL_Subscribe_OptionOrder_locateRate,
																		 String SELL_Subscribe_OptionOrder_boothID,
																		 String SELL_Subscribe_OptionOrder_ExpectedStatus)
			{
		 	 
			 	LoggingManager.logger.info("====================================================================");
				LoggingManager.logger.info("TestCase : "+OptionOrder_Subscribe_TestCases);
				LoggingManager.logger.info("====================================================================");
		
				//========================================================Get Buy Order ID===================================================================
				APIHelperClass.GetOrderValues( Subscribe_OptionOrder_BasePath,
																Global.getAccToken,
																Content_Type,
																Integer.parseInt(Subscribe_StatusCode),
																Endpoint_Version,
																Subscribe_OptionOrder_originatingUserDesc,
																Subscribe_OptionOrder_ExpectedStatus,
																Subscribe_OptionOrder_account,
																Subscribe_OptionOrder_symbol,
																Subscribe_OptionOrder_destination,
																Subscribe_OptionOrder_price,
																Subscribe_OptionOrder_side,
																Subscribe_OptionOrder_orderQty,
																Subscribe_OptionOrder_orderType,
																"option");
				
				Global.getOptionBuyFilledOrderID=Global.getOptionOrderID;	
				LoggingManager.logger.info("API-Found BUY Order_ID :  ["+Global.getOptionBuyFilledOrderID+"]");
				LoggingManager.logger.info("API-Found BUY Side Desc. :  ["+Global.getOptionSideDesc+"]");
				LoggingManager.logger.info("API-Found BUY Order_Status :  ["+Global.getOptionStatus+"]");
				if(Global.getOptionBuyFilledOrderID == null || Global.getOptionBuyFilledOrderID=="" ) 
				{Assert.fail("Logs : Buy Order Not Found with status :["+Subscribe_OptionOrder_ExpectedStatus+"]");}
				
				APIHelperClass.Option_OrdersSubscriptionValidation(  Subscribe_OptionOrder_ExpectedStatus,
																Subscribe_OptionOrder_originatingUserDesc,
																Subscribe_OptionOrder_orderType,
																Subscribe_OptionOrder_side,
																Subscribe_OptionOrder_SideDesc,
																Subscribe_OptionOrder_symbol,
																Subscribe_OptionOrder_account,
																Subscribe_OptionOrder_destination,
																Subscribe_OptionOrder_price,
																Subscribe_OptionOrder_orderQty,
																Subscribe_OptionOrder_optionSymbol,
																Subscribe_OptionOrder_strikePrice,
																Subscribe_OptionOrder_maturityDay,
																Subscribe_OptionOrder_maturityMonthYear,
																Subscribe_OptionOrder_maturityMonthYearDesc,
																Subscribe_OptionOrder_maturityDate,
																Subscribe_OptionOrder_optionDateDesc,
																Subscribe_OptionOrder_cmta,
																Subscribe_OptionOrder_execBroker,
																Subscribe_OptionOrder_putOrCallInt,
																Subscribe_OptionOrder_putOrCall,
																Subscribe_OptionOrder_coveredOrUncoveredInt,
																Subscribe_OptionOrder_coveredOrUncovered,
																Subscribe_OptionOrder_customerOrFirmInt,
																Subscribe_OptionOrder_customerOrFirm,
																Subscribe_OptionOrder_openCloseBoxed,
																Subscribe_OptionOrder_openClose,
																Subscribe_OptionOrder_text,
																Subscribe_OptionOrder_complianceID,
																Subscribe_OptionOrder_stopPx,
																Subscribe_OptionOrder_timeInForce,
																Subscribe_OptionOrder_tifDesc,
																Subscribe_OptionOrder_symbolSfx,
																Subscribe_OptionOrder_symbolWithoutSfx,
																Subscribe_OptionOrder_orderTypeDesc,
																Subscribe_OptionOrder_avgPx,
																Subscribe_OptionOrder_cumQty,
																Subscribe_OptionOrder_workableQty,
																Subscribe_OptionOrder_leavesQty,
																Subscribe_OptionOrder_locateID,
																Subscribe_OptionOrder_contactName,
																Subscribe_OptionOrder_locateRequired,
																Subscribe_OptionOrder_locateRate,
																Subscribe_OptionOrder_boothID,
																Subscribe_OptionOrder_ExpectedStatus,
																Endpoint_Version,
																Subscribe_Executions_BasePath,
																Content_Type,
																Subscribe_StatusCode);
				
				
				
				
		//========================================================Get Sell Order ID===================================================================
				
				APIHelperClass.GetOrderValues( Subscribe_OptionOrder_BasePath,
											Global.getAccToken,
											Content_Type,
											Integer.parseInt(Subscribe_StatusCode),
											Endpoint_Version,
											SELL_Subscribe_OptionOrder_originatingUserDesc,
											SELL_Subscribe_OptionOrder_ExpectedStatus,
											SELL_Subscribe_OptionOrder_account,
											SELL_Subscribe_OptionOrder_symbol,
											SELL_Subscribe_OptionOrder_destination,
											SELL_Subscribe_OptionOrder_price,
											SELL_Subscribe_OptionOrder_side,
											SELL_Subscribe_OptionOrder_orderQty,
											SELL_Subscribe_OptionOrder_orderType,
											"option");

				
				Global.getOptionSellFilledOrderID=Global.getOptionOrderID;	
				LoggingManager.logger.info("API-Found SELL Order_ID :  ["+Global.getOptionSellFilledOrderID+"]");
				LoggingManager.logger.info("API-Found SELL Side Desc. :  ["+Global.getOptionSideDesc+"]");
				LoggingManager.logger.info("API-Found SELL Order_Status :  ["+Global.getOptionStatus+"]");
				if(Global.getOptionSellFilledOrderID == null || Global.getOptionSellFilledOrderID=="" ) 
				{Assert.fail("Logs : Sell Order Not Found with status :["+SELL_Subscribe_OptionOrder_ExpectedStatus+"]");}
				
				APIHelperClass.Option_OrdersSubscriptionValidation(  SELL_Subscribe_OptionOrder_ExpectedStatus,
																SELL_Subscribe_OptionOrder_originatingUserDesc,
																SELL_Subscribe_OptionOrder_orderType,
																SELL_Subscribe_OptionOrder_side,
																SELL_Subscribe_OptionOrder_SideDesc,
																SELL_Subscribe_OptionOrder_symbol,
																SELL_Subscribe_OptionOrder_account,
																SELL_Subscribe_OptionOrder_destination,
																SELL_Subscribe_OptionOrder_price,
																SELL_Subscribe_OptionOrder_orderQty,
																SELL_Subscribe_OptionOrder_optionSymbol,
																SELL_Subscribe_OptionOrder_strikePrice,
																SELL_Subscribe_OptionOrder_maturityDay,
																SELL_Subscribe_OptionOrder_maturityMonthYear,
																SELL_Subscribe_OptionOrder_maturityMonthYearDesc,
																SELL_Subscribe_OptionOrder_maturityDate,
																SELL_Subscribe_OptionOrder_optionDateDesc,
																SELL_Subscribe_OptionOrder_cmta,
																SELL_Subscribe_OptionOrder_execBroker,
																SELL_Subscribe_OptionOrder_putOrCallInt,
																SELL_Subscribe_OptionOrder_putOrCall,
																SELL_Subscribe_OptionOrder_coveredOrUncoveredInt,
																SELL_Subscribe_OptionOrder_coveredOrUncovered,
																SELL_Subscribe_OptionOrder_customerOrFirmInt,
																SELL_Subscribe_OptionOrder_customerOrFirm,
																SELL_Subscribe_OptionOrder_openCloseBoxed,
																SELL_Subscribe_OptionOrder_openClose,
																SELL_Subscribe_OptionOrder_text,
																SELL_Subscribe_OptionOrder_complianceID,
																SELL_Subscribe_OptionOrder_stopPx,
																SELL_Subscribe_OptionOrder_timeInForce,
																SELL_Subscribe_OptionOrder_tifDesc,
																SELL_Subscribe_OptionOrder_symbolSfx,
																SELL_Subscribe_OptionOrder_symbolWithoutSfx,
																SELL_Subscribe_OptionOrder_orderTypeDesc,
																SELL_Subscribe_OptionOrder_avgPx,
																SELL_Subscribe_OptionOrder_cumQty,
																SELL_Subscribe_OptionOrder_workableQty,
																SELL_Subscribe_OptionOrder_leavesQty,
																SELL_Subscribe_OptionOrder_locateID,
																SELL_Subscribe_OptionOrder_contactName,
																SELL_Subscribe_OptionOrder_locateRequired,
																SELL_Subscribe_OptionOrder_locateRate,
																SELL_Subscribe_OptionOrder_boothID,
																SELL_Subscribe_OptionOrder_ExpectedStatus,
																Endpoint_Version,
																Subscribe_Executions_BasePath,
																Content_Type,
																Subscribe_StatusCode);
			
		}
				
	
	 
	 @Test (dataProvider="SubscribeRejectedOptionOrder", dataProviderClass=ExcelDataProvider.class , groups={"SubscribeRejectedOptionOrder"}, dependsOnGroups={"UserLoginAuthentications"})//CreateRejectedOptionOrder  UserLoginAuthentications
	 public void Verify_SubscribeRejectedOptionOrder(	 String OptionOrder_Subscribe_TestCases,
			 											 String Endpoint_Version,
			 											 String Subscribe_OptionOrder_BasePath,
														 String Content_Type,
													     String Subscribe_OptionOrder_StatusCode,
														 String Subscribe_OptionOrder_originatingUserDesc,
														 String Subscribe_OptionOrder_orderType,
														 String Subscribe_OptionOrder_side,
														 String Subscribe_OptionOrder_sideDesc,
														 String Subscribe_OptionOrder_symbol,
														 String Subscribe_OptionOrder_account,
														 String Subscribe_OptionOrder_destination,
														 String Subscribe_OptionOrder_price,
														 String Subscribe_OptionOrder_orderQty,	
														 
																 
														 String Subscribe_OptionOrder_optionSymbol,
														 String Subscribe_OptionOrder_strikePrice,
														 
														 String Subscribe_OptionOrder_maturityDay,
														 String Subscribe_OptionOrder_maturityMonthYear,
														 String Subscribe_OptionOrder_maturityMonthYearDesc,
														 String Subscribe_OptionOrder_maturityDate,
														 String Subscribe_OptionOrder_optionDateDesc,
														 
														 
														 String Subscribe_OptionOrder_cmta,
														 String Subscribe_OptionOrder_execBroker,
														 String Subscribe_OptionOrder_putOrCallInt,
														 String Subscribe_OptionOrder_putOrCall,
														 String Subscribe_OptionOrder_coveredOrUncoveredInt,
														 String Subscribe_OptionOrder_coveredOrUncovered,
														 String Subscribe_OptionOrder_customerOrFirmInt,
														 String Subscribe_OptionOrder_customerOrFirm,
														 String Subscribe_OptionOrder_openCloseBoxed,
														 String Subscribe_OptionOrder_openClose,
														 
														 
														 		 String Subscribe_OptionOrder_text,
																 String Subscribe_OptionOrder_complianceID,
																 String Subscribe_OptionOrder_stopPx,
																 String Subscribe_OptionOrder_timeInForce,
																 String Subscribe_OptionOrder_tifDesc,
																 String Subscribe_OptionOrder_symbolSfx,
																 String Subscribe_OptionOrder_symbolWithoutSfx,
																 String Subscribe_OptionOrder_orderTypeDesc,
																 String Subscribe_OptionOrder_avgPx,
																 String Subscribe_OptionOrder_cumQty,
																 String Subscribe_OptionOrder_workableQty,
																 String Subscribe_OptionOrder_leavesQty,
																 String Subscribe_OptionOrder_locateID,
																 String Subscribe_OptionOrder_contactName,
																 String Subscribe_OptionOrder_locateRequired,
																 String Subscribe_OptionOrder_locateRate,
																 String Subscribe_OptionOrder_boothID,
																 String Subscribe_OptionOrder_ExpectedStatus )
	 {
				LoggingManager.logger.info("====================================================================");
				LoggingManager.logger.info("TestCase : "+OptionOrder_Subscribe_TestCases);
				LoggingManager.logger.info("====================================================================");
				APIHelperClass.GetOrderValues(  Subscribe_OptionOrder_BasePath,
											Global.getAccToken,
											Content_Type,
											Integer.parseInt(Subscribe_OptionOrder_StatusCode),
											Endpoint_Version,
											Subscribe_OptionOrder_originatingUserDesc,
											Subscribe_OptionOrder_ExpectedStatus,
											Subscribe_OptionOrder_account,
											Subscribe_OptionOrder_symbol,
											Subscribe_OptionOrder_destination,
											Subscribe_OptionOrder_price,
											Subscribe_OptionOrder_side,
											Subscribe_OptionOrder_orderQty,
											Subscribe_OptionOrder_orderType,"option");
			
			
			
				LoggingManager.logger.info("API-Found Order_ID :  ["+Global.getOptionOrderID+"]");
				LoggingManager.logger.info("API-Found Order_Status :  ["+Global.getOptionStatus+"]");
				LoggingManager.logger.info("API-Found Side Desc. :  ["+Global.getOptionSideDesc+"]");
				if(Global.getOptionOrderID == null || Global.getOptionOrderID=="" ) 
				{Assert.fail("Logs : Option Order Not Found with status :["+Subscribe_OptionOrder_ExpectedStatus+"]");}
				
				
				APIHelperClass.Option_OrdersSubscriptionValidation(  Subscribe_OptionOrder_ExpectedStatus,
																Subscribe_OptionOrder_originatingUserDesc,
																Subscribe_OptionOrder_orderType,
																Subscribe_OptionOrder_side,
																Subscribe_OptionOrder_sideDesc,
																Subscribe_OptionOrder_symbol,
																Subscribe_OptionOrder_account,
																Subscribe_OptionOrder_destination,
																Subscribe_OptionOrder_price,
																Subscribe_OptionOrder_orderQty,
																Subscribe_OptionOrder_optionSymbol,
																Subscribe_OptionOrder_strikePrice,
																Subscribe_OptionOrder_maturityDay,
																Subscribe_OptionOrder_maturityMonthYear,
																Subscribe_OptionOrder_maturityMonthYearDesc,
																Subscribe_OptionOrder_maturityDate,
																Subscribe_OptionOrder_optionDateDesc,
																Subscribe_OptionOrder_cmta,
																Subscribe_OptionOrder_execBroker,
																Subscribe_OptionOrder_putOrCallInt,
																Subscribe_OptionOrder_putOrCall,
																Subscribe_OptionOrder_coveredOrUncoveredInt,
																Subscribe_OptionOrder_coveredOrUncovered,
																Subscribe_OptionOrder_customerOrFirmInt,
																Subscribe_OptionOrder_customerOrFirm,
																Subscribe_OptionOrder_openCloseBoxed,
																Subscribe_OptionOrder_openClose,
																Subscribe_OptionOrder_text,
																Subscribe_OptionOrder_complianceID,
																Subscribe_OptionOrder_stopPx,
																Subscribe_OptionOrder_timeInForce,
																Subscribe_OptionOrder_tifDesc,
																Subscribe_OptionOrder_symbolSfx,
																Subscribe_OptionOrder_symbolWithoutSfx,
																Subscribe_OptionOrder_orderTypeDesc,
																Subscribe_OptionOrder_avgPx,
																Subscribe_OptionOrder_cumQty,
																Subscribe_OptionOrder_workableQty,
																Subscribe_OptionOrder_leavesQty,
																Subscribe_OptionOrder_locateID,
																Subscribe_OptionOrder_contactName,
																Subscribe_OptionOrder_locateRequired,
																Subscribe_OptionOrder_locateRate,
																Subscribe_OptionOrder_boothID,
																Subscribe_OptionOrder_ExpectedStatus,Endpoint_Version,"","","");
													
		}
	 
	}

	 
/*	 
	 @Test (dataProvider="SubscribeCancelOptionOrder", dataProviderClass=ExcelDataProvider.class , groups={"SubscribeCancelOptionOrder"}, dependsOnGroups={"OptionOrderCancellation"})
	 public void Verify_SubscribeCancelOptionOrder(	 String Subscribe_OptionOrder_BasePath,
													 String Content_Type,
												     String Subscribe_OptionOrder_StatusCode,
													 String Subscribe_OptionOrder_UserID,
													 String Subscribe_OptionOrder_OrderType,
													 String Subscribe_OptionOrder_Side,
													 String Subscribe_OptionOrder_Symbol,
													 String Subscribe_OptionOrder_Account,
													 String Subscribe_OptionOrder_Destination,
													 String Subscribe_OptionOrder_Price,
													 String Subscribe_OptionOrder_OrderQty,												 
													 String Subscribe_OptionOrder_ExpectedStatus,
												     String Order_Validation_Value
												  )
		{
		 	 
/*				APIHelperClassClass APIHelperClass=new APIHelperClassClass();
				Global.getOrderID=APIHelperClass.GetOrderValues( Subscribe_OptionOrder_BasePath,
																Global.getAccToken,
																Content_Type,
																Integer.parseInt(Subscribe_OptionOrder_StatusCode),
																Subscribe_OptionOrder_UserID,
																Subscribe_OptionOrder_ExpectedStatus_Cancelled,
																Subscribe_OptionOrder_ExpectedStatus_Pending_Cancelled,
																Subscribe_OptionOrder_Account,
																Subscribe_OptionOrder_Symbol,
																Subscribe_OptionOrder_Destination,
																Subscribe_OptionOrder_Price,
																Subscribe_OptionOrder_Side,
																Subscribe_OptionOrder_OrderQty,
																Subscribe_OptionOrder_OrderType,
																Order_Validation_Value );

				System.out.println("Verify_SubscribeCancelOptionOrder = [ "+Global.getOrderID+" ]");		
				if(Global.getOrderID == null || Global.getOrderID=="" ) 
				{
					Assert.fail("Logs : Cancel Option Order Not Found"); 
				}
	*/			
				

	 

