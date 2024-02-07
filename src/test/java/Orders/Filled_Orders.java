package Orders;

import static io.restassured.RestAssured.given;

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
import io.restassured.response.Response;

public class Filled_Orders {

	
	
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
		
	@Test (dataProvider="CreateBUYFilledEquityOrder", dataProviderClass=ExcelDataProvider.class , groups={"CreateBUYFilledEquityOrder"}, dependsOnGroups={"UserLoginAuthentications"})//UserLoginAuthenticationsFlat_OrderSymbol_Position
	public void Verify_BUY_Equity_Order_Filled_Creation( String EquityOrder_TestCase,
														 String EndpointVersion,
														 String EquityOrder_Creation_BasePath,
														 String Content_Type,
														 String EquityOrder_Creation_Body,
														 String EquityOrder_Creation_StatusCode,
														 String EquityOrder_Creation_Response)
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+EquityOrder_TestCase);
			LoggingManager.logger.info("====================================================================");
			RestAssured.baseURI=Global.BaseURL;
			Response response=

					given()
							.header("Content-Type",Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)
							.body(EquityOrder_Creation_Body)

							.when()
							.post(EquityOrder_Creation_BasePath)

							.then()
							.extract()
							.response();

			LoggingManager.logger.info("API-EquityOrder_Filled_BUY_BasePath : ["+EquityOrder_Creation_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-EquityOrder_Filled_BUY_Body : ["+EquityOrder_Creation_Body+"]");
			LoggingManager.logger.info("API-EquityOrder_Filled_BUY_StatusCode : ["+response.getStatusCode()+"]");
			LoggingManager.logger.info("API-EquityOrder_Filled_BUY_Response_Body : ["+response.getBody().asString()+"]");

			Assert.assertEquals(response.getStatusCode(), Integer.parseInt(EquityOrder_Creation_StatusCode),"Verify_EquityOrder_Creation_StatusCode");
			if(EndpointVersion.equalsIgnoreCase("V1")) {Assert.assertEquals(response.getBody().asString(), EquityOrder_Creation_Response,"Verify_EquityOrder_Creation_Response");}
			else{Assert.assertEquals(response.jsonPath().get("message"), EquityOrder_Creation_Response,"Verify_EquityOrder_Creation_Response");}

		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}
			
	
	@Test (dataProvider="CreateSELLFilledEquityOrder", dataProviderClass=ExcelDataProvider.class , groups={"CreateSELLFilledEquityOrder"}, dependsOnGroups={"UserLoginAuthentications"})//Flat_Position_Acc_BoxvsShort UserLoginAuthenticationsCreateSELLFilledEquityOrder
	public void Verify_SELL_Equity_Order_Filled_Creation( String EquityOrder_TestCase,
			 											  String EndpointVersion,
														  String EquityOrder_Creation_BasePath,
														  String Content_Type,
														  String BUY_EquityOrder_Creation_Body,
														  String SELL_EquityOrder_Creation_Body,
														  String EquityOrder_Creation_StatusCode,
														  String EquityOrder_Creation_Response)
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+EquityOrder_TestCase);
			LoggingManager.logger.info("====================================================================");
			RestAssured.baseURI=Global.BaseURL;

			//====================================================BUY Order=============================================================================

			Response response=

							 given()
							.header("Content-Type",Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)
							.body(BUY_EquityOrder_Creation_Body)

							.when()
							.post(EquityOrder_Creation_BasePath)

							.then()
							.extract()
							.response();


			LoggingManager.logger.info("API-EquityOrder_Filled_BUY_BasePath : ["+EquityOrder_Creation_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-EquityOrder_Filled_BUY_Body : ["+BUY_EquityOrder_Creation_Body+"]");
			LoggingManager.logger.info("API-EquityOrder_Filled_BUY_StatusCode : ["+response.getStatusCode()+"]");
			LoggingManager.logger.info("API-EquityOrder_Filled_BUY_Response_Body : ["+response.getBody().asString()+"]");
			Assert.assertEquals(response.getStatusCode(), Integer.parseInt(EquityOrder_Creation_StatusCode),"Verify_Equity_Order_BUY_Creation");
			if(EndpointVersion.equalsIgnoreCase("V1")) {Assert.assertEquals(response.getBody().asString(), EquityOrder_Creation_Response,"Verify_EquityOrder_BUY_Creation_Response");}
			else{Assert.assertEquals(response.jsonPath().get("message"), EquityOrder_Creation_Response,"Verify_EquityOrder_BUY_Creation_Response");}

			//====================================================SELL Order=============================================================================

			Response SELL_response=

							given()
							.header("Content-Type",Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)
							.body(SELL_EquityOrder_Creation_Body)

							.when()
							.post(EquityOrder_Creation_BasePath)

							.then()
							.extract()
							.response();


			LoggingManager.logger.info("API-EquityOrder_Filled_SELL_BasePath : ["+EquityOrder_Creation_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-EquityOrder_Filled_SELL_Body : ["+SELL_EquityOrder_Creation_Body+"]");
			LoggingManager.logger.info("API-EquityOrder_Filled_SELL_StatusCode : ["+SELL_response.getStatusCode()+"]");
			LoggingManager.logger.info("API-EquityOrder_Filled_SELL_Response_Body : ["+SELL_response.getBody().asString()+"]");
			Assert.assertEquals(SELL_response.getStatusCode(), Integer.parseInt(EquityOrder_Creation_StatusCode),"Verify_Equity_Order_Creation");
			if(EndpointVersion.equalsIgnoreCase("V1")) {Assert.assertEquals(SELL_response.getBody().asString(), EquityOrder_Creation_Response,"Verify_EquityOrder_SELL_Creation_Response");}
			else{Assert.assertEquals(SELL_response.jsonPath().get("message"), EquityOrder_Creation_Response,"Verify_EquityOrder_SELL_Creation_Response");}

		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}
	
	
	@Test (dataProvider="CreateSHORTSELLFilledOrder", dataProviderClass=ExcelDataProvider.class , groups={"CreateSHORTSELLFilledOrder"}, dependsOnGroups={"UserLoginAuthentications"}) //UserLoginAuthentications  Flat_Position
	public void Verify_SHORTSELL_EquityOrder_Filled_Creation(    String EquityOrder_TestCase,
																 String EndpointVersion,
																 String EquityOrder_Creation_BasePath,
																 String Content_Type ,
																 String EquityOrder_Creation_Body,
																 String EquityOrder_Creation_StatusCode,
																 String EquityOrder_Creation_Response)
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+EquityOrder_TestCase);
			LoggingManager.logger.info("====================================================================");
			RestAssured.baseURI=Global.BaseURL;
			Response response=

							given()
							.header("Content-Type",Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)
							.body(EquityOrder_Creation_Body)

							.when()
							.post(EquityOrder_Creation_BasePath)

							.then()
							.extract()
							.response();

			LoggingManager.logger.info("API-EquityOrder_Filled_SHORTSELL_BasePath : ["+EquityOrder_Creation_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-EquityOrder_Filled_SHORTSELL_Body : ["+EquityOrder_Creation_Body+"]");
			LoggingManager.logger.info("API-EquityOrder_Filled_SHORTSELL_StatusCode : ["+response.getStatusCode()+"]");
			LoggingManager.logger.info("API-EquityOrder_Filled_SHORTSELL_Response_Body : ["+response.getBody().asString()+"]");
			Assert.assertEquals(response.getStatusCode(), Integer.parseInt(EquityOrder_Creation_StatusCode),"Verify_Equity_Order_Creation");
			if(EndpointVersion.equalsIgnoreCase("V1")) {Assert.assertEquals(response.getBody().asString(), EquityOrder_Creation_Response,"Verify_EquityOrder_SHORTSELL_Creation_Response");}
			else{Assert.assertEquals(response.jsonPath().get("message"), EquityOrder_Creation_Response,"Verify_EquityOrder_SHORTSELL_Creation_Response");}

		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}
}
