package IdentityServer_API_User;

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


public class Forgot_Password_Verify {
	
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
	 
	@Test (dataProvider="Forgot_Password_Verify", dataProviderClass=ExcelDataProvider.class,groups={"Forgot_Password_Verify"}, dependsOnGroups={"Forgot_Password"})
	public void Forgot_Password_Verification(String Forgot_Password_Verify_TestCase,
											 String Forgot_Password_Verify_BasePath,
											 String Content_Type,
											 String Forgot_Password_Verify_TFA,
											 String Forgot_Password_Verify_StatusCode,
											 String Forgot_Password_Verify_StatusLine,
											 String Validate_Forgot_Password_Verify_Response)
		{
		LoggingManager.logger.info("====================================================================");
		LoggingManager.logger.info("TestCase : "+Forgot_Password_Verify_TestCase);
		LoggingManager.logger.info("====================================================================");
		
		RestAssured.baseURI=Global.BaseURL;
		HashMap<String, Object> Forgot_Password_Verify_Body=new HashMap<String , Object>();
		
		Forgot_Password_Verify_Body.put("codeId", Global.getCodeID);
		Forgot_Password_Verify_Body.put("code",Forgot_Password_Verify_TFA);
		
		
		Response response=
							given()	
									.header("Content-Type",Content_Type) 
									.body(Forgot_Password_Verify_Body)
								
							.when()
									.post(Forgot_Password_Verify_BasePath)
								
							.then()
									//.statusCode(Integer.parseInt(Forgot_Password_Verify_StatusCode))
									.extract().response();
			
		LoggingManager.logger.info("API-Forgot_Password_Verify_BasePath : ["+Forgot_Password_Verify_BasePath+"]");
		LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
		LoggingManager.logger.info("API-Forgot_Password_Verify_Body : ["+Forgot_Password_Verify_Body+"]");
		LoggingManager.logger.info("API-Forgot_Password_Verify_StatusCode : ["+response.getStatusCode()+"]");
		LoggingManager.logger.info("API-Forgot_Password_Verify_Response_Body : ["+response.getBody().asPrettyString()+"]");
		
		Assert.assertEquals(response.statusCode(),Integer.parseInt(Forgot_Password_Verify_StatusCode),"Validate_Forgot_Password_Verify_StatusCode");
		Assert.assertEquals(response.jsonPath().get("message"),Validate_Forgot_Password_Verify_Response,"Validate_Forgot_Password_Response");
		
		
	}	
}
