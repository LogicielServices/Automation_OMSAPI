package LocatesAPI;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import APIHelper.Global;
import APIHelper.LoggingManager;
import XLDataProvider.ExcelDataProvider;
import static io.restassured.RestAssured.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;


public class Locates_Acquire {

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
	
	@Test (dataProvider="Locates_Acquire", dataProviderClass=ExcelDataProvider.class,groups={"Locates_Acquire"}, dependsOnGroups={"Subscribe_Locates"})
	public void Verify_Locates_Acquire(String Locates_Acquire_TestCase,
									String Locates_Acquire_BasePath,
									String Content_Type,
									String Locates_Acquire_Symbol,
									String Locates_Acquire_TimeInForce,
									String Locates_Acquire_OrderQty,
									String Locates_Acquire_Synchronous,
									String Locates_Acquire_Account,
									String Locates_Acquire_StatusCode )
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+Locates_Acquire_TestCase);
			LoggingManager.logger.info("====================================================================");
			RestAssured.baseURI=Global.BaseURL;
			HashMap<String, Object> Locates_Acquire_Body=new HashMap<String, Object>();

			Locates_Acquire_Body.put("quoteReqID",Global.getID);
			Locates_Acquire_Body.put("symbol",Locates_Acquire_Symbol);
			Locates_Acquire_Body.put("timeInForce",Locates_Acquire_TimeInForce);
			Locates_Acquire_Body.put("orderQty",Integer.parseInt(Locates_Acquire_OrderQty));
			Locates_Acquire_Body.put("synchronous",Integer.parseInt(Locates_Acquire_Synchronous));
			Locates_Acquire_Body.put("account",Locates_Acquire_Account);

			Response response=	given()
								.header("Content-Type",Content_Type)
								.header("Authorization", "Bearer " + Global.getAccToken)
								.body(Locates_Acquire_Body)

								.when()
								.post(Locates_Acquire_BasePath)

								.then()
								//.statusCode(Integer.parseInt(Locates_Acquire_StatusCode))
								//.statusLine("HTTP/1.1 200 OK")
								.extract().response();

			LoggingManager.logger.info("API-Locates_Acquire_BasePath : ["+Locates_Acquire_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-Request Body : ["+Locates_Acquire_Body.toString()+"]");
			LoggingManager.logger.info("API-Subscribe_Locates_StatusCode : ["+response.getStatusCode()+"]");
			LoggingManager.logger.info("API-Subscribe_Locates_StatusLine : ["+response.getStatusLine()+"]");
			Assert.assertEquals(response.statusCode(),Integer.parseInt(Locates_Acquire_StatusCode), "Verify_Locates_Acquire_StatusCode");
			//Assert.assertEquals(Subscribe_qOrderID,"[\""+Validate_Subscribe_qOrderID+"\"]", "Validate_Subscribe_qOrderID");
		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}
}
