package IdentityServer_API_User;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import APIHelper.APIHelperClass;
import APIHelper.Global;
import APIHelper.LoggingManager;
import XLDataProvider.ExcelDataProvider;
import static io.restassured.RestAssured.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;


public class Forgot_Password_Confirm_BKP {
	
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
	
	@Test (dataProvider="Forgot_Password_Confirm", dataProviderClass=ExcelDataProvider.class,groups={"Forgot_Password_Confirm"}, dependsOnGroups={"Forgot_Password_Verify"})
	public void Verify_Forgot_Password_Confirm(  String Forgot_Password_Confirm_TestCase,
												 String Forgot_Password_Confirm_BasePath,
												 String Content_Type,
												 String Forgot_Password_Confirm_TFA,
												 String NewPassword,
												 String Confirm_NewPassword,
												 String Forgot_Password_Confirm_StatusCode,
												 String Forgot_Password_Confirm_StatusLine,
												 String Validate_Forgot_Password_Confirm_Response,
												 String Base_Path_Login_Step1,
												 String Email,
												 String Status_Code_Step1,
												 String Base_Path_Login_Step2,
												 String TFACode,
												 String Status_Code_Step2)
	{
		LoggingManager.logger.info("====================================================================");
		LoggingManager.logger.info("TestCase : "+Forgot_Password_Confirm_TestCase);
		LoggingManager.logger.info("====================================================================");
		
		RestAssured.baseURI=Global.BaseURL;
		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMHHmmss");
        String UniqueID = localDateTime.format(formatter);
        APIHelperClass userlogin=new APIHelperClass();
        
		HashMap<String, Object> Forgot_Password_Confirm_Body=new HashMap<String , Object>();
		Forgot_Password_Confirm_Body.put("code",Forgot_Password_Confirm_TFA);
		Forgot_Password_Confirm_Body.put("codeId", Global.getCodeID);
		Forgot_Password_Confirm_Body.put("newPassword",NewPassword.concat(UniqueID));
		Forgot_Password_Confirm_Body.put("confirmNewPassword",Confirm_NewPassword.concat(UniqueID));
		Response response=
							given()	
									//.header("Authorization", "Bearer " + Global.getAccToken)
									.header("Content-Type",Content_Type) 
									.body(Forgot_Password_Confirm_Body)
								
							.when()
									.post(Forgot_Password_Confirm_BasePath)
								
							.then()
									//.statusCode(Integer.parseInt(Forgot_Password_Confirm_StatusCode))
									.extract().response();
			
	 Global.getPassword=NewPassword.concat(UniqueID);	
	 
	 LoggingManager.logger.info("API-Forgot_Password_Confirm_BasePath : ["+Forgot_Password_Confirm_BasePath+"]");
	 LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
	 LoggingManager.logger.info("API-Forgot_Password_Confirm_Password : ["+Global.getPassword+"]");
	 LoggingManager.logger.info("API-Forgot_Password_Confirm_Body : ["+Forgot_Password_Confirm_Body.toString()+"]");
	 LoggingManager.logger.info("API-Forgot_Password_Confirm_BasePath : ["+response.getStatusCode()+"]");
	 LoggingManager.logger.info("API-Forgot_Password_Confirm_Response_Body : ["+response.getBody().asString()+"]");
	 
	 Assert.assertEquals(response.getBody().asString(),Validate_Forgot_Password_Confirm_Response,"Validate_Forgot_Password_Confirm_Response");
	 
	 Global.getAccToken=userlogin.UserLoginAuthentications(   Email,
																Global.getPassword,
																Base_Path_Login_Step1,
																Base_Path_Login_Step2,
																TFACode,
																Content_Type,
																Status_Code_Step1,
																"",
																Status_Code_Step2,
																 "");

	LoggingManager.logger.info("API-Validate_UserLogin Email : ["+Email+"]");
	LoggingManager.logger.info("API-Validate loginPassword : ["+Global.getPassword+"]");
	LoggingManager.logger.info("API-Validate_UserLogin_AccToken : ["+Global.getAccToken+"]");
	
	if(Global.getAccToken == null || Global.getAccToken=="" ) 
	{
		Assert.fail("Logs : AccToken is not created"); 
	}
	 
  }	
}
