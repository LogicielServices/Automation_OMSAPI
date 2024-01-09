package IdentityServer_API_Admin;

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


public class UserAccountsRegister 
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

	// @Test (dataProvider="UserAccountsRegister", dataProviderClass=ExcelDataProvider.class , groups={"AccountsRegister"}, dependsOnGroups={"AdminLoginAuthentications"})
	 @Test (dataProvider="UserAccountsRegister", dataProviderClass=ExcelDataProvider.class , groups={"AccountsRegister"})
	 public void Verify_User_Account_Register(String Account_Register_TestCases,String Account_Register_Base_Path,
												String Content_Type ,
												String User_Account_Register_Body,
												String User_Account_Register_code,
												String Account_Register_Response_Message )
												
	{	
		 	LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+Account_Register_TestCases);
			LoggingManager.logger.info("====================================================================");
			RestAssured.baseURI=Global.BaseURL;
			Response response=
				
								given()	
										.header("Content-Type",Content_Type) 
										.header("Authorization", "Bearer " + Global.getAccToken)
										.body(User_Account_Register_Body)
									
								.when()
										.post(Account_Register_Base_Path)
									
								.then()
										.extract().response();
			

			LoggingManager.logger.info("API-User_Account_Register_Base_Path : ["+Account_Register_Base_Path+"]");
			LoggingManager.logger.info("API-User_Account_Register_Body : ["+User_Account_Register_Body+"]");
			LoggingManager.logger.info("API-User_Account_Register_code : ["+response.statusCode()+"]");
			LoggingManager.logger.info("API-User_Account_Register_Response_Message : ["+response.jsonPath().get("message")+"]");				
			LoggingManager.logger.info("API-User_Account_Register_Response_Body : ["+response.getBody().asString()+"]");
			
			Assert.assertEquals(response.jsonPath().get("message"), Account_Register_Response_Message,"Verify_Update_Account_Register");
			
	 }
}
	

