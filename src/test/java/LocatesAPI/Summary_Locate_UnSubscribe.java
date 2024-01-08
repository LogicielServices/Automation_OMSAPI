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


public class Summary_Locate_UnSubscribe {

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
	
	@Test (dataProvider="Summary_Locate_UnSubscribe", dataProviderClass=ExcelDataProvider.class,groups={"Summary_Locate_UnSubscribe"}, dependsOnGroups={"Summary_Locate_Subscribe"})
	public void Verify_Summary_Locate_UnSubscribe(String Summary_Locate_UnSubscribe_TestCase,
																											  String Summary_Locate_UnSubscribe_BasePath,
																											  String Content_Type,
																											  String Summary_Locate_UnSubscribe_StatusCode,
																											  String Validate_UnSubscribe_Response)
	{
		LoggingManager.logger.info("====================================================================");
		LoggingManager.logger.info("TestCase : "+Summary_Locate_UnSubscribe_TestCase);
		LoggingManager.logger.info("====================================================================");
		RestAssured.baseURI=Global.BaseURL;
		Response response=
							given()	
								.header("Content-Type",Content_Type) 
								.header("Authorization", "Bearer " + Global.getAccToken)
								
							.when()
								.get(Summary_Locate_UnSubscribe_BasePath)
								
							.then()
								//.statusCode(Integer.parseInt(Summary_Locate_UnSubscribe_StatusCode))
								//.statusLine("HTTP/1.1 200 OK")
								.extract().response();
		
		LoggingManager.logger.info("API-Summary_Locate_UnSubscribe_BasePath : ["+Summary_Locate_UnSubscribe_BasePath+"]");
		LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
		LoggingManager.logger.info("API-Summary_Locate_UnSubscribe_StatusCode : ["+response.getStatusCode()+"]");
		LoggingManager.logger.info("API-SSummary_Locate_UnSubscribe_StatusLine : ["+response.getStatusLine()+"]");
		LoggingManager.logger.info("API-Response Body : ["+response.getBody().asString() +"]");
		Assert.assertEquals(response.statusCode(),Integer.parseInt(Summary_Locate_UnSubscribe_StatusCode), "Verify_Summary_Locate_UnSubscribe_StatusCode");
		Assert.assertEquals(response.getBody().asString(),Validate_UnSubscribe_Response, "Verify_Summary_Locate_UnSubscribe");
		LoggingManager.logger.info("====================================================================");
		
	}	
}
