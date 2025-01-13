package Orders;

import APIHelper.APIHelperClass;
import APIHelper.Global;
import APIHelper.LoggingManager;
import XLDataProvider.ExcelDataProvider;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static io.restassured.RestAssured.given;


public class Order_Subscriptions {

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
	

	 @Test (dataProvider="OrderSubscriptions", dataProviderClass=ExcelDataProvider.class , groups={"Order_Subscriptions"}, dependsOnGroups={"UserLoginAuthentications"})
	 public void Verify_Order_Subscriptions(String OrderSubscription_TestCase,
																String EndpointVersion,
																String Order_Creation_BasePath,
																String Content_Type ,
																String Order_Creation_Body,
																String Order_Creation_StatusCode,
																String Order_Creation_Response,
																String Subscribe_Order_BasePath,
																String Subscribe_Order_StatusCode,
																String Subscribe_Order_UserID,
																String Subscribe_Order_OrderType,
																String Subscribe_Order_Side,
																String Subscribe_Order_SideDesc,
																String Subscribe_Order_Symbol,
																String Subscribe_Order_Account,
																String Subscribe_Order_Destination,
																String Subscribe_Order_Price,
																String Subscribe_Order_OrderQty,
																String Subscribe_Order_MaxFloor,
																String Subscribe_Order_complianceID,
																String Subscribe_Order_stopPx,
																String Subscribe_Order_timeInForce,
																String Subscribe_Order_tifDesc,
																String Subscribe_Order_symbolSfx,
																String Subscribe_Order_symbolWithoutSfx,
																String Subscribe_Order_orderTypeDesc,
																String Subscribe_Order_avgPx,
																String Subscribe_Order_cumQty,
																String Subscribe_Order_workableQty,
																String Subscribe_Order_leavesQty,
																String Subscribe_Order_locateID,
																String Subscribe_Order_contactName,
																String Subscribe_Order_locateRequired,
																String Subscribe_Order_locateRate,
																String Subscribe_Order_boothID,
																String Subscribe_Order_ExpectedStatus,
																String Subscribe_Order_Text)
		{
			try
			{
				LoggingManager.logger.info("====================================================================");
				LoggingManager.logger.info("TestCase : "+OrderSubscription_TestCase);
				LoggingManager.logger.info("====================================================================");

				RestAssured.baseURI=Global.BaseURL;
				Response response=

								 given()
								.header("Content-Type",Content_Type)
								.header("Authorization", "Bearer " + Global.getAccToken)
								.body(Order_Creation_Body)

								.when()
								.post(Order_Creation_BasePath)

								.then()
								.extract()
								.response();

				LoggingManager.logger.info("API-Order_Creation_BasePath : ["+Order_Creation_BasePath+"]");
				LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
				LoggingManager.logger.info("API-Order_Creation_Body : ["+Order_Creation_Body+"]");
				LoggingManager.logger.info("API-Order_Creation_StatusCode : ["+response.getStatusCode()+"]");
				LoggingManager.logger.info("API-Order_Creation_Response : ["+response.getBody().asString()+"]");
				Assert.assertEquals(response.getStatusCode(), Integer.parseInt(Order_Creation_StatusCode),"Verify_Equity_Order_Active_Open_Creation");
				if(EndpointVersion.equalsIgnoreCase("V1")) {Assert.assertEquals(response.getBody().asString(), Order_Creation_Response,"Verify_Order_Creation_Response");}
				else{Assert.assertEquals(response.jsonPath().get("message"), Order_Creation_Response,"Verify_Order_Creation_Response");}

//------------------------------------------Subscribe Logic-------------------------

				APIHelperClass.GetOrder(Subscribe_Order_BasePath,
												Global.getAccToken,
												Content_Type,
												Integer.parseInt(Subscribe_Order_StatusCode),
												EndpointVersion,
												Subscribe_Order_UserID,
												Subscribe_Order_Text, "equity");

				LoggingManager.logger.info("API-Found Order_ID :  [" + Global.getOrderID + "]");
				LoggingManager.logger.info("API-Found Order_Status :  [" + Global.getStatus + "]");
				LoggingManager.logger.info("API-Found Side Desc. :  [" + Global.getSideDesc + "]");
				if (Global.getOrderID == null || Global.getOrderID == "") {
					Assert.fail("Logs : Order Not Found with status :[" + Subscribe_Order_ExpectedStatus + "]");
				}
				Thread.sleep(3000);
				APIHelperClass.ValidateOrdersSubscription(	Subscribe_Order_UserID,
															Subscribe_Order_OrderType,
															Subscribe_Order_Side,
															Subscribe_Order_SideDesc,
															Subscribe_Order_Symbol,
															Subscribe_Order_Account,
															Subscribe_Order_Destination,
															Subscribe_Order_Price,
															Subscribe_Order_OrderQty,
															Subscribe_Order_MaxFloor,
															Subscribe_Order_Text,
															Subscribe_Order_complianceID,
															Subscribe_Order_stopPx,
															Subscribe_Order_timeInForce,
															Subscribe_Order_tifDesc,
															Subscribe_Order_symbolSfx,
															Subscribe_Order_symbolWithoutSfx,
															Subscribe_Order_orderTypeDesc,
															Subscribe_Order_avgPx,
															Subscribe_Order_cumQty,
															Subscribe_Order_workableQty,
															Subscribe_Order_leavesQty,
															Subscribe_Order_locateID,
															Subscribe_Order_contactName,
															Subscribe_Order_locateRequired,
															Subscribe_Order_locateRate,
															Subscribe_Order_boothID,
															Subscribe_Order_ExpectedStatus);


			}
			catch (Exception e)
			{
				LoggingManager.logger.error(e);
			}
		}
}
