package Subscriptions;

import static io.restassured.RestAssured.given;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import APIHelper.APIHelperClass;
import APIHelper.Global;
import APIHelper.LoggingManager;
import XLDataProvider.ExcelDataProvider;
import io.restassured.RestAssured;
import io.restassured.response.Response;

//Test(groups = {"Create"},dependsOnGroups={"UserLoginAuthentications"})
public class Option_Executions_Subscribe {

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
	 @Test (dataProvider="SubscribeBUY_OptionExecutions", dataProviderClass=ExcelDataProvider.class , groups={"SubscribeBUY_OptionExecutions"}, dependsOnGroups={"UserLoginAuthentications"})//CreateBUYFilledOptionOrder UserLoginAuthentications
	 public void Verify_SubscribeBUY_OptionExecutions(	 String Subscribe_Executions_TestCase,
			 											 String Endpoint_Version,
														 String Fetch_OptionOrder_BasePath,
														 String Content_Type,
														 String StatusCode,
														 String Fetch_OptionOrder_UserID,
														 String Fetch_OptionOrder_OrderType,
														 String Fetch_OptionOrder_Side,
														 String Fetch_OptionOrder_Symbol,
														 String Fetch_OptionOrder_Account,
														 String Fetch_OptionOrder_Destination,
														 String Fetch_OptionOrder_Price,
														 String Fetch_OptionOrder_OrderQty,
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
																	String Fetch_Executions_ExecTransTypeDesc)
	 
		{
		 	LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+Subscribe_Executions_TestCase);
			LoggingManager.logger.info("====================================================================");
			RestAssured.baseURI=Global.BaseURL;		
			APIHelperClass.GetOrderValues(	Fetch_OptionOrder_BasePath,
														Global.getAccToken,
														Content_Type,
														Integer.parseInt(StatusCode),
														Endpoint_Version,
														Fetch_OptionOrder_UserID,
														Subscribe_OptionOrder_ExpectedStatus,
														Fetch_OptionOrder_Account,
														Fetch_OptionOrder_Symbol,
														Fetch_OptionOrder_Destination,
														Fetch_OptionOrder_Price,
														Fetch_OptionOrder_Side,
														Fetch_OptionOrder_OrderQty,
														Fetch_OptionOrder_OrderType,"option");
			
			Global.getOptionBuyFilledOrderID=Global.getOptionOrderID;
			Global.getOptionBuyFilled_qOrderID=Global.getOptionqOrderID;
			LoggingManager.logger.info("API- BUY OrderID Before Execution : ["+Global.getOptionBuyFilledOrderID+"]");
			LoggingManager.logger.info("API- BUY qOrderID Before Execution : ["+Global.getOptionBuyFilled_qOrderID+"]");	
			if(Global.getOptionBuyFilledOrderID == null || Global.getOptionBuyFilledOrderID=="" ) 
			{Assert.fail("Logs : Buy Order Not Found with status :["+Subscribe_OptionOrder_ExpectedStatus+"]");}
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
												Fetch_Executions_ExecTransTypeDesc,"","","",0,"","","","","","","","","","","","","","","","","","executions");
		
		
		}
	 
	 
	
	 @Test (dataProvider="SubscribeSELL_OptionExecutions", dataProviderClass=ExcelDataProvider.class , groups={"SubscribeSELL_OptionExecutions"}, dependsOnGroups={"UserLoginAuthentications"})//CreateSELLFilledOptionOrder UserLoginAuthentications
	 public void Verify_SubscribeSELL_OptionExecutions(String Subscribe_Executions_TestCase,String Endpoint_Version,
														String Fetch_OptionOrder_BasePath,
														String Content_Type,
														String StatusCode,
														String Fetch_OptionOrder_UserID,
														String Fetch_OptionOrder_OrderType,
														String Fetch_OptionOrder_Side,
														String Fetch_OptionOrder_Symbol,
														String Fetch_OptionOrder_Account,
														String Fetch_OptionOrder_Destination,
														String Fetch_OptionOrder_Price,
														String Fetch_OptionOrder_OrderQty,
													    String Subscribe_OptionOrder_ExpectedStatus,
													    String Fetch_Sell_OptionOrder_UserID,
													    String Fetch_Sell_OptionOrder_OrderType,
													    String Fetch_Sell_OptionOrder_Side,
													    String Fetch_Sell_OptionOrder_Symbol,
													    String Fetch_Sell_OptionOrder_Account,
													    String Fetch_Sell_OptionOrder_Destination,
													    String Fetch_Sell_OptionOrder_Price,
													    String Fetch_Sell_OptionOrder_OrderQty,
													    String Subscribe_Sell_OptionOrder_ExpectedStatus,
													    
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
																String Fetch_Executions_Sell_ExecTransTypeDesc)
		{
		 	LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+Subscribe_Executions_TestCase);
			LoggingManager.logger.info("====================================================================");
			RestAssured.baseURI=Global.BaseURL;		
			//=====================================================================Buy Order ID Fetching=============================================
			
			APIHelperClass.GetOrderValues(	Fetch_OptionOrder_BasePath,
										Global.getAccToken,
										Content_Type,
										Integer.parseInt(StatusCode),
										Endpoint_Version,
										Fetch_OptionOrder_UserID,
										Subscribe_OptionOrder_ExpectedStatus,
										Fetch_OptionOrder_Account,
										Fetch_OptionOrder_Symbol,
										Fetch_OptionOrder_Destination,
										Fetch_OptionOrder_Price,
										Fetch_OptionOrder_Side,
										Fetch_OptionOrder_OrderQty,
										Fetch_OptionOrder_OrderType,"option");
			
			Global.getOptionBuyFilledOrderID=Global.getOptionOrderID;
			Global.getOptionBuyFilled_qOrderID=Global.getOptionqOrderID;
			LoggingManager.logger.info("API- BUY OrderID Before Execution : ["+Global.getOptionBuyFilledOrderID+"]");
			LoggingManager.logger.info("API- BUY qOrderID Before Execution : ["+Global.getOptionBuyFilled_qOrderID+"]");	
			if(Global.getOptionBuyFilledOrderID == null || Global.getOptionBuyFilledOrderID=="" ) 
			{Assert.fail("Logs : Buy Order Not Found with status :["+Subscribe_OptionOrder_ExpectedStatus+"]");}
			
//=====================================================================Sell Order ID Fetching=============================================	
			
			APIHelperClass.GetOrderValues(	Fetch_OptionOrder_BasePath,
										Global.getAccToken,
										Content_Type,
										Integer.parseInt(StatusCode),
										Endpoint_Version,
										Fetch_Sell_OptionOrder_UserID,
										Subscribe_Sell_OptionOrder_ExpectedStatus,
										Fetch_Sell_OptionOrder_Account,
										Fetch_Sell_OptionOrder_Symbol,
										Fetch_Sell_OptionOrder_Destination,
										Fetch_Sell_OptionOrder_Price,
										Fetch_Sell_OptionOrder_Side,
										Fetch_Sell_OptionOrder_OrderQty,
										Fetch_Sell_OptionOrder_OrderType,"option");

			Global.getOptionSellFilledOrderID=Global.getOptionOrderID;
			Global.getOptionSellFilled_qOrderID=Global.getOptionqOrderID;
			LoggingManager.logger.info("API- SELL OrderID Before Execution : ["+Global.getOptionSellFilledOrderID+"]");
			LoggingManager.logger.info("API- SELL qOrderID Before Execution : ["+Global.getOptionSellFilled_qOrderID+"]");
			if(Global.getOptionSellFilledOrderID == null || Global.getOptionSellFilledOrderID=="" ) 
			{Assert.fail("Logs : Sell Order Not Found with status :["+Subscribe_Sell_OptionOrder_ExpectedStatus+"]");}

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
												Fetch_Executions_Sell_ExecTransTypeDesc,"executions");
			
			     
		}			     
				     
	
	
}
