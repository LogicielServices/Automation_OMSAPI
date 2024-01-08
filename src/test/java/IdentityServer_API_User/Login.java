package IdentityServer_API_User;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import APIHelper.APIHelperClass;
import APIHelper.Global;
import APIHelper.LoggingManager;
import XLDataProvider.ExcelDataProvider;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import io.qameta.allure.testng.Tag;
import io.restassured.RestAssured;

public class Login {
	
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
	 	
	
	//@Epic("User Login")
	//@Feature("User Login Feature")
	//@Story("Login User Story")
	//@TmsLink("https://nyf.qatouch.com/v2#/case/list/p/3b2X/nLa47")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("QATest27")
	@Description("This is User Login TestCase")
	@Tag("UserLogin")
	@Test (dataProvider="UserLogin", dataProviderClass=ExcelDataProvider.class, groups={"UserLoginAuthentications"})
	public void UserLogin(String Login_TestCase,
									 String Base_Path_Login_Step1,
									 String Email,
									 String Password,
									 String Content_Type,
									 String Status_Code_Step1,
									 String Base_Path_Login_Step2,
									 String TFACode,
									 String Status_Code_Step2)
	{
		LoggingManager.logger.info("====================================================================");
		LoggingManager.logger.info("TestCase : "+Login_TestCase);
		LoggingManager.logger.info("====================================================================");
		Reporter.log("This is Login verbose logging :",2);
		RestAssured.baseURI=Global.BaseURL;
		APIHelperClass userlogin=new APIHelperClass();
		Global.getAccToken=userlogin.UserLoginAuthentications(  Email,
																Password,
																Base_Path_Login_Step1,
																Base_Path_Login_Step2,
																TFACode,
																Content_Type,
																Status_Code_Step1,
																Status_Code_Step2);
		
		
		LoggingManager.logger.info("API-Response_Login_AccToken : ["+Global.getAccToken+"]");
		
		
		if(Global.getAccToken == null || Global.getAccToken=="" ) 
		{Assert.fail("Logs : AccToken is not created Against User : ["+Email+"]"); }
		
	}
	
			
}
