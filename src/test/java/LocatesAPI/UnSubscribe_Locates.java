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


public class UnSubscribe_Locates {

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
	
	@Test (dataProvider="UnSubscribe_Locates", dataProviderClass=ExcelDataProvider.class,groups={"UnSubscribe_Locates"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_UnSubscribe_Locates(String UnSubscribe_Locates_TestCase,
												  String UnSubscribe_Locates_BasePath,
												  String Content_Type,
												  String UnSubscribe_Locates_StatusCode,
												  String Validate_UnSubscribe_Response)
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+UnSubscribe_Locates_TestCase);
			LoggingManager.logger.info("====================================================================");
			RestAssured.baseURI=Global.BaseURL;
			Response response=
							given()
							.header("Content-Type",Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)

							.when()
							.get(UnSubscribe_Locates_BasePath)

							.then()
							//.statusCode(Integer.parseInt(UnSubscribe_Locates_StatusCode))
							//.statusLine("HTTP/1.1 200 OK")
							.extract().response();

			LoggingManager.logger.info("API-UnSubscribe_Locates_BasePath : ["+UnSubscribe_Locates_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-UnSubscribe_Locates_StatusCode : ["+response.getStatusCode()+"]");
			LoggingManager.logger.info("API-UnSubscribe_Locates_StatusLine : ["+response.getStatusLine()+"]");
			LoggingManager.logger.info("API-Response Body : ["+response.getBody().asString() +"]");
			Assert.assertEquals(response.statusCode(),Integer.parseInt(UnSubscribe_Locates_StatusCode), "Verify_UnSubscribe_Locates_StatusCode");
			Assert.assertEquals(response.getBody().asString(),Validate_UnSubscribe_Response, "Validate_UnSubscribe_Response");
		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}
}
