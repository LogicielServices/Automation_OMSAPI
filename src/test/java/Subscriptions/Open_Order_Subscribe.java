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
public class Open_Order_Subscribe {

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

	 @Test (dataProvider="SubscribeOpenOrder", dataProviderClass=ExcelDataProvider.class , groups={"SubscribeOpenOrder"}, dependsOnGroups={"UserLoginAuthentications"}) //UserLoginAuthentications CreateActiveOpenEquityOrder
	 public void Verify_SubscribeOpenOrder(	 String TestCases,String Endpoint_Version,
	 										 String Subscribe_OpenOrder_BasePath,
											 String Content_Type,
										     String Subscribe_OpenOrder_StatusCode,
											 String Subscribe_OpenOrder_UserID,
											 String Subscribe_OpenOrder_OrderType,
											 String Subscribe_OpenOrder_Side,
											 String Subscribe_OpenOrder_SideDesc,
											 String Subscribe_OpenOrder_Symbol,
											 String Subscribe_OpenOrder_Account,
											 String Subscribe_OpenOrder_Destination,
											 String Subscribe_OpenOrder_Price,
											 String Subscribe_OpenOrder_OrderQty,	
											 String Subscribe_OpenOrder_Text,
											 String Subscribe_OpenOrder_complianceID,
											 String Subscribe_OpenOrder_stopPx,
											 String Subscribe_OpenOrder_timeInForce,
											 String Subscribe_OpenOrder_tifDesc,
											 String Subscribe_OpenOrder_symbolSfx,
											 String Subscribe_OpenOrder_symbolWithoutSfx,
											 String Subscribe_OpenOrder_orderTypeDesc,
											 String Subscribe_OpenOrder_avgPx,
											 String Subscribe_OpenOrder_cumQty,
											 String Subscribe_OpenOrder_workableQty,
											 String Subscribe_OpenOrder_leavesQty,
											 String Subscribe_OpenOrder_locateID,
											 String Subscribe_OpenOrder_contactName,
											 String Subscribe_OpenOrder_locateRequired,
											 String Subscribe_OpenOrder_locateRate,
											 String Subscribe_OpenOrder_boothID,
										     String Subscribe_OpenOrder_ExpectedStatus)
		{
			try
			{
				LoggingManager.logger.info("====================================================================");
				LoggingManager.logger.info("TestCase : "+TestCases);
				LoggingManager.logger.info("====================================================================");
				APIHelperClass.GetOrderValues(Subscribe_OpenOrder_BasePath,
												Global.getAccToken,
												Content_Type,
												Integer.parseInt(Subscribe_OpenOrder_StatusCode),
												Endpoint_Version,
												Subscribe_OpenOrder_UserID,
												Subscribe_OpenOrder_ExpectedStatus,
												Subscribe_OpenOrder_Account,
												Subscribe_OpenOrder_Symbol,
												Subscribe_OpenOrder_Destination,
												Subscribe_OpenOrder_Price,
												Subscribe_OpenOrder_Side,
												Subscribe_OpenOrder_OrderQty,
												Subscribe_OpenOrder_OrderType,"equity");

				LoggingManager.logger.info("API-Found Order_ID :  ["+Global.getOrderID+"]");
				LoggingManager.logger.info("API-Found Order_Status :  ["+Global.getStatus+"]");
				LoggingManager.logger.info("API-Found Side Desc. :  ["+Global.getSideDesc+"]");
				if(Global.getOrderID == null || Global.getOrderID=="" )
				{Assert.fail("Logs : Order Not Found with status :["+Subscribe_OpenOrder_ExpectedStatus+"]");}
				APIHelperClass.OrdersSubscriptionValidation(Subscribe_OpenOrder_ExpectedStatus,
															Subscribe_OpenOrder_UserID,
															Subscribe_OpenOrder_OrderType,
															Subscribe_OpenOrder_Side,
															Subscribe_OpenOrder_SideDesc,
															Subscribe_OpenOrder_Symbol,
															Subscribe_OpenOrder_Account,
															Subscribe_OpenOrder_Destination,
															Subscribe_OpenOrder_Price,
															Subscribe_OpenOrder_OrderQty,
															Subscribe_OpenOrder_Text,
															Subscribe_OpenOrder_complianceID,
															Subscribe_OpenOrder_stopPx,
															Subscribe_OpenOrder_timeInForce,
															Subscribe_OpenOrder_tifDesc,
															Subscribe_OpenOrder_symbolSfx,
															Subscribe_OpenOrder_symbolWithoutSfx,
															Subscribe_OpenOrder_orderTypeDesc,
															Subscribe_OpenOrder_avgPx,
															Subscribe_OpenOrder_cumQty,
															Subscribe_OpenOrder_workableQty,
															Subscribe_OpenOrder_leavesQty,
															Subscribe_OpenOrder_locateID,
															Subscribe_OpenOrder_contactName,
															Subscribe_OpenOrder_locateRequired,
															Subscribe_OpenOrder_locateRate,
															Subscribe_OpenOrder_boothID,
															Subscribe_OpenOrder_ExpectedStatus,"","","","");

			}
			catch (Exception e)
			{
				LoggingManager.logger.error(e);
			}
		}

}
