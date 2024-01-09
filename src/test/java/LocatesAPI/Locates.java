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


public class Locates {

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
	
	@Test (dataProvider="Post_Locates", dataProviderClass=ExcelDataProvider.class,groups={"Post_Locates"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_Post_Locates(String Locates_TestCase,
									String Locates_BasePath,
									String Content_Type,
									String Locates_Body,
									String Locates_StatusCode )
	{
		LoggingManager.logger.info("====================================================================");
		LoggingManager.logger.info("TestCase : "+Locates_TestCase);
		LoggingManager.logger.info("====================================================================");
		
		
		RestAssured.baseURI=Global.BaseURL;
		
		Response response=	given()	
								.header("Content-Type",Content_Type) 
								.header("Authorization", "Bearer " + Global.getAccToken)
								.body(Locates_Body)
								
							.when()
								.post(Locates_BasePath)
								
							.then()
								//.statusCode(Integer.parseInt(Locates_StatusCode))
								//.statusLine("HTTP/1.1 200 OK")
								.extract().response();
		
		LoggingManager.logger.info("API-StaticData_Account_BasePath : ["+Locates_BasePath+"]");
		LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
		LoggingManager.logger.info("API-Locates_Body : ["+Locates_Body+"]");
		LoggingManager.logger.info("API-Locates_StatusCode : ["+response.getStatusCode()+"]");
		Assert.assertEquals(response.statusCode(),Integer.parseInt(Locates_StatusCode), "Verify_Post_Locates_StatusCode");
		LoggingManager.logger.info("====================================================================");
		
		
		
	}	
}
