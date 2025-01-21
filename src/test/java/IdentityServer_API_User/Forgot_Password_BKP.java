package IdentityServer_API_User;

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


public class Forgot_Password_BKP {

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
	
	@Test (dataProvider="Forgot_Password", dataProviderClass=ExcelDataProvider.class,groups={"Forgot_Password"})
	public void Verify_Forgot_Password( String Forgot_Password_TestCase,
										String Forgot_Password_BasePath,
										String Content_Type,
										String Forgot_Password_Body,
										String Forgot_Password_StatusCode,
										String Forgot_Password_StatusLine,
										String Validate_Forgot_Password_Response)
	{
		LoggingManager.logger.info("====================================================================");
		LoggingManager.logger.info("TestCase : "+Forgot_Password_TestCase);
		LoggingManager.logger.info("====================================================================");
		RestAssured.baseURI=Global.BaseURL;
		
		Response response=
							given()	
									//.header("Authorization", "Bearer " + Global.getAccToken)
									.header("Content-Type",Content_Type) 
									.body(Forgot_Password_Body)
								
							.when()
									.post(Forgot_Password_BasePath)
								
							.then()
									//.statusLine(Forgot_Password_StatusLine)
									.extract().response();
			
	 Global.getCodeID=response.jsonPath().get("codeId");
	 
	LoggingManager.logger.info("API-Forgot_Password_BasePath : ["+Forgot_Password_BasePath+"]");
	LoggingManager.logger.info("API-Forgot_Password_Body : ["+Forgot_Password_Body.toString()+"]");
	LoggingManager.logger.info("API-Forgot_Password_StatusLine : ["+response.getStatusCode()+"]");
	LoggingManager.logger.info("API-Forgot_Password_Response_Body : ["+response.getBody().asString()+"]");
	LoggingManager.logger.info("API-Forgot_Password_getCodeID : ["+ Global.getCodeID+"]");
	Assert.assertEquals(response.statusCode(),Integer.parseInt(Forgot_Password_StatusCode),"Validate_Forgot_Password_StatusCode");
	Assert.assertEquals(response.statusLine(), Forgot_Password_StatusLine,"Validate_Forgot_Password_StatusLine");
	Assert.assertEquals(response.jsonPath().get("message"), Validate_Forgot_Password_Response,"Validate_Forgot_Password_Response");
	
		
	}	
}
