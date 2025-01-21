package IdentityServer_API_User;

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

public class Dummy_UserProfile_Login {

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
	
	@Test (dataProvider="UserProfile_UserLogin", dataProviderClass=ExcelDataProvider.class, groups={"UserProfile_UserLogin"})
	public void UserProfile_UserLogin(   String UserProfile_UserLogin_TestCase,
										 String Base_Path_Login_Step1,
										 String Email,
										 String Content_Type,
										 String Status_Code_Step1,
										 String Step1_Response_message,
										 String Base_Path_Login_Step2,
										 String TFACode,
										 String Status_Code_Step2,
										 String Step2_Response_message)
		{
		
		LoggingManager.logger.info("====================================================================");
		LoggingManager.logger.info("TestCase : "+UserProfile_UserLogin_TestCase);
		LoggingManager.logger.info("====================================================================");
		
		
		RestAssured.baseURI=Global.BaseURL;
		APIHelperClass userlogin=new APIHelperClass();
		Global.getAccToken=userlogin.UserLoginAuthentications(  Email,
																Global.getPassword,
																Base_Path_Login_Step1,
																Base_Path_Login_Step2,
																TFACode,
																Content_Type,
																Status_Code_Step1,
																Step1_Response_message,
																Status_Code_Step2,
																Step2_Response_message);
	
		LoggingManager.logger.info("API-UserProfile_UserLogin Email : ["+Email+"]");
		LoggingManager.logger.info("API-UserProfile_UserLogin loginPassword : ["+Global.getPassword+"]");
		LoggingManager.logger.info("API-UserProfile_UserLogin_AccToken : ["+Global.getAccToken+"]");
		
		if(Global.getAccToken == null || Global.getAccToken=="" ) 
		{
		Assert.fail("Logs : AccToken is not created"); 
		}
	}
	
			
}
