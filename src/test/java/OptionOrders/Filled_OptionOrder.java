package OptionOrders;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import APIHelper.APIHelperClass;
import APIHelper.Global;
import APIHelper.LoggingManager;
import XLDataProvider.ExcelDataProvider;
import static io.restassured.RestAssured.*;

import java.io.PrintWriter;
import java.io.StringWriter;

//@Test(groups = {"Create_Option"},dependsOnGroups={"UserLoginAuthentications"})
public class Filled_OptionOrder  
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
	 
	@Test (dataProvider="OptionOrder_BUYCreation_Filled", dataProviderClass=ExcelDataProvider.class, groups={"CreateBUYFilledOptionOrder"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_BUY_Order_Creation_Filled(String OptionOrder_Creation_TestCase,
			 									 String Endpoint_Version,
												 String OptionOrder_Creation_BasePath,
												 String Content_Type,
												 String OptionOrder_Creation_Body,
												 String OptionOrder_Creation_StatusCode,
												 String OptionOrder_Creation_Response )
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
			LoggingManager.logger.info("API-OptionOrder_Response_Body : ["+response.getBody().asString()+"]");
			Assert.assertEquals(response.getStatusCode(),Integer.parseInt(OptionOrder_Creation_StatusCode),"Verify_Order_Creation_Filled_Status");
			if(Endpoint_Version.equalsIgnoreCase("V1")) {Assert.assertEquals(response.getBody().asString(), OptionOrder_Creation_Response,"Order_Creation_Filled");}
			else{Assert.assertEquals(response.jsonPath().get("message"), OptionOrder_Creation_Response,"Order_Creation_Filled");}
			
	}
	
	
	@Test (dataProvider="OptionOrder_SELLCreation_Filled", dataProviderClass=ExcelDataProvider.class, groups={"CreateSELLFilledOptionOrder"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_SELL_Order_Creation_Filled(   String OptionOrder_Creation_TestCase,
													 String Endpoint_Version,
													 String OptionOrder_Creation_BasePath,
													 String Content_Type ,
													 String BUY_OptionOrder_Creation_Body,
													 String SELL_OptionOrder_Creation_Body,
													 String OptionOrder_Creation_StatusCode,
													 String OptionOrder_Creation_Response )

	{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+OptionOrder_Creation_TestCase);
			LoggingManager.logger.info("====================================================================");
			RestAssured.baseURI=Global.BaseURL;
			Response buy_response= 
								given()	
										.header("Content-Type",Content_Type) 
										.header("Authorization", "Bearer " + Global.getAccToken)
										.body(BUY_OptionOrder_Creation_Body)
								.when()
										.post(OptionOrder_Creation_BasePath)
								.then()
										.extract().response();
			
			LoggingManager.logger.info("API-BUY_OptionOrder_Creation_BasePath : ["+OptionOrder_Creation_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-BUY_OptionOrder_Creation_Body : ["+BUY_OptionOrder_Creation_Body+"]");
			LoggingManager.logger.info("API-BUY_OptionOrder_Creation_StatusCode : ["+buy_response.getStatusCode()+"]");
			LoggingManager.logger.info("API-BUY_OptionOrder_Response_Body : ["+buy_response.getBody().asString()+"]");
			
			Assert.assertEquals(buy_response.getStatusCode(),Integer.parseInt(OptionOrder_Creation_StatusCode),"Verify_BUY_Order_Creation_Filled_Status");
			if(Endpoint_Version.equalsIgnoreCase("V1")) {Assert.assertEquals(buy_response.getBody().asString(), OptionOrder_Creation_Response,"Verify_BUY_Order_Creation_Filled_Status");}
			else{Assert.assertEquals(buy_response.jsonPath().get("message"), OptionOrder_Creation_Response,"Verify_BUY_Order_Creation_Filled_Status");}

			
//====================================================SELL Order=============================================================================			
			
			Response sell_response= 
					given()	
							.header("Content-Type",Content_Type) 
							.header("Authorization", "Bearer " + Global.getAccToken)
							.body(SELL_OptionOrder_Creation_Body)
					.when()
							.post(OptionOrder_Creation_BasePath)
					.then()
							.extract().response();

			LoggingManager.logger.info("API-SELL_OptionOrder_Creation_BasePath : ["+OptionOrder_Creation_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-SELL_OptionOrder_Creation_Body : ["+SELL_OptionOrder_Creation_Body+"]");
			LoggingManager.logger.info("API-SELL_OptionOrder_Creation_StatusCode : ["+sell_response.getStatusCode()+"]");
			LoggingManager.logger.info("API-SELL_OptionOrder_Response_Body : ["+sell_response.getBody().asString()+"]");
			Assert.assertEquals(sell_response.getStatusCode(),Integer.parseInt(OptionOrder_Creation_StatusCode),"Verify_SELL_Order_Creation_Filled_Status");
			if(Endpoint_Version.equalsIgnoreCase("V1")) {Assert.assertEquals(sell_response.getBody().asString(), OptionOrder_Creation_Response,"Verify_SELL_OptionOrder_Creation_Response");}
			else{Assert.assertEquals(sell_response.jsonPath().get("message"), OptionOrder_Creation_Response,"Verify_SELL_OptionOrder_Creation_Response");}
			

	}
	
}	
	
	
	

