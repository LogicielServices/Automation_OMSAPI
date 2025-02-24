package OptionOrders;

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

//@Test(groups = {"Create_Option"},dependsOnGroups={"UserLoginAuthentications"})
public class OptionOrder_Subscription
{
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
	
	@Test (dataProvider="OptionOrder_Subscription", dataProviderClass=ExcelDataProvider.class, groups={"OptionOrder_Subscription"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_OptionOrder_Subscription( String OptionOrder_Subscription_TestCase,
														     String Endpoint_Version,
														     String OptionOrder_Creation_BasePath,
														     String Content_Type ,
														     String OptionOrder_Creation_Body,
														     String OptionOrder_Creation_StatusCode,
														     String OptionOrder_Creation_Response,
															 String Subscribe_OptionOrder_BasePath,
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
															 String Subscribe_OptionOrder_ExpectedStatus)
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+OptionOrder_Subscription_TestCase);
			LoggingManager.logger.info("====================================================================");
			RestAssured.baseURI=Global.BaseURL;
			Response response=
					given()
							.header("Content-Type",Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)
							.body(OptionOrder_Creation_Body)
							.when()
							.post(OptionOrder_Creation_BasePath)
							.then()
							//.statusCode(Integer.parseInt(OptionOrder_Creation_StatusCode))
							//.statusLine("HTTP/1.1 200 OK")
							.extract().response();

			LoggingManager.logger.info("API-OptionOrder_Creation_BasePath : ["+OptionOrder_Creation_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-OptionOrder_Creation_Body : ["+OptionOrder_Creation_Body+"]");
			LoggingManager.logger.info("API-OptionOrder_Creation_StatusCode : ["+response.getStatusCode()+"]");
			LoggingManager.logger.info("API-OptionOrder_Response_Body : ["+response.getBody().asPrettyString()+"]");
			Assert.assertEquals(response.getStatusCode(),Integer.parseInt(OptionOrder_Creation_StatusCode),"Option_Order_Creation");
			if(Endpoint_Version.equalsIgnoreCase("V1")) {Assert.assertEquals(response.getBody().asString(), OptionOrder_Creation_Response,"Verify_OptionOrder_Response");}
			else{Assert.assertEquals(response.jsonPath().get("message"), OptionOrder_Creation_Response,"Verify_OptionOrder_Response");}


		//---------------------------------------------subscriptions------------------------------------------------------


			APIHelperClass.GetOptionOrder(	Subscribe_OptionOrder_BasePath,
											Global.getAccToken,
											Content_Type,
											Integer.parseInt(Subscribe_OptionOrder_StatusCode),
											Endpoint_Version,
											Subscribe_OptionOrder_originatingUserDesc,
											Subscribe_OptionOrder_text);



			LoggingManager.logger.info("API-Found Order_ID :  ["+Global.getOptionOrderID+"]");
			LoggingManager.logger.info("API-Found Order_Status :  ["+Global.getOptionStatus+"]");
			LoggingManager.logger.info("API-Found Side Desc. :  ["+Global.getOptionSideDesc+"]");
			if(Global.getOptionOrderID == null || Global.getOptionOrderID=="" )
			{Assert.fail("Logs : Option Order Not Found with status :["+Subscribe_OptionOrder_ExpectedStatus+"]");}
			APIHelperClass.Validate_OptionOrdersSubscription( Subscribe_OptionOrder_originatingUserDesc,
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
																Subscribe_OptionOrder_ExpectedStatus);

		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
}
	
	
	
	@Test (dataProvider="OptionOrderCreation_Rejected", dataProviderClass=ExcelDataProvider.class, groups={"CreateRejectedOptionOrder"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_Order_Creation_Rejected_Status(String OptionOrder_Creation_TestCase,
			   										  String Endpoint_Version,
													  String OptionOrder_Creation_BasePath,
													  String Content_Type ,
													  String OptionOrder_Creation_Body,
													  String OptionOrder_Creation_StatusCode,
													  String OptionOrder_Creation_Response)
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+OptionOrder_Creation_TestCase);
			LoggingManager.logger.info("====================================================================");
			RestAssured.baseURI=Global.BaseURL;
			Response response=
					given()
							.header("Content-Type",Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)
							.body(OptionOrder_Creation_Body)
							.when()
							.post(OptionOrder_Creation_BasePath)
							.then()
							.extract().response();

			LoggingManager.logger.info("API-OptionOrder_Creation_BasePath : ["+OptionOrder_Creation_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-OptionOrder_Creation_Body : ["+OptionOrder_Creation_Body+"]");
			LoggingManager.logger.info("API-OptionOrder_Creation_StatusCode : ["+response.getStatusCode()+"]");
			LoggingManager.logger.info("API-OptionOrder_Response_Body : ["+response.getBody().asPrettyString()+"]");
			Assert.assertEquals(response.getStatusCode(),Integer.parseInt(OptionOrder_Creation_StatusCode),"Verify_Order_Creation_Rejected_Status");
			if(Endpoint_Version.equalsIgnoreCase("V1")) {Assert.assertEquals(response.getBody().asString(), OptionOrder_Creation_Response,"Verify_Order_Creation_Rejected_Response");}
			else{Assert.assertEquals(response.jsonPath().get("message"), OptionOrder_Creation_Response,"Verify_Order_Creation_Rejected_Response");}
		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}
}
	
	
	

