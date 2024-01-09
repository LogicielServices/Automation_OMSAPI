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


public class UserProfile_ChangePassword {

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
	
	@Test (dataProvider="UserProfile_ChangePassword", dataProviderClass=ExcelDataProvider.class,groups={"UserProfile_ChangePassword"})
	public void Verify_UserProfile_ChangePassword(  String UserProfile_ChangePassword_TestCase,
													String Forgot_Password_BasePath,
													String Content_Type,
													String Forgot_Password_Body,
													String Forgot_Password_StatusCode,
													String Validate_Forgot_Password_Response,
													String Forgot_Password_Confirm_BasePath,
													String Forgot_Password_Confirm_TFA,
													String Forgot_NewPassword,
													String Forgot_Confirm_NewPassword,
													String Forgot_Password_Confirm_StatusCode,
													String Validate_Forgot_Password_Confirm_Response,
													String Base_Path_Login_Step1,
													String Email,
													String Status_Code_Step1,
													String Base_Path_Login_Step2,
													String TFACode,
													String Status_Code_Step2,
													String UserProfile_ChangePassword_BasePath,
													String Change_NewPassword,
													String Change_Confirm_NewPassword,
													String UserProfile_ChangePassword_StatusCode,
													String UserProfile_ChangePassword_StatusLine,
													String Validate_UserProfile_ChangePassword_Response)
	{
		LoggingManager.logger.info("====================================================================");
		LoggingManager.logger.info("TestCase : "+UserProfile_ChangePassword_TestCase);
		LoggingManager.logger.info("====================================================================");
		
				
		RestAssured.baseURI=Global.BaseURL;
		APIHelperClass userlogin=new APIHelperClass();
		
		
//==========================================Forgot Password=========================================================================
		Response ForgotPassword_response=
				given()	
						//.header("Authorization", "Bearer " + Global.getAccToken)
						.header("Content-Type",Content_Type) 
						.body(Forgot_Password_Body)
					
				.when()
						.post(Forgot_Password_BasePath)
					
				.then()
						.extract().response();

		
		
		LoggingManager.logger.info("API-Forgot_Password_BasePath : ["+Forgot_Password_BasePath+"]");
		LoggingManager.logger.info("API-Forgot_Password_Body : ["+Forgot_Password_Body.toString()+"]");
		LoggingManager.logger.info("API-Forgot_Password_StatusLine : ["+ForgotPassword_response.getStatusCode()+"]");
		LoggingManager.logger.info("API-Forgot_Password_Response_Body : ["+ForgotPassword_response.getBody().asString()+"]");
		Assert.assertEquals(ForgotPassword_response.statusCode(), Integer.parseInt(Forgot_Password_StatusCode),"Validate_Forgot_Password_StatusCode");
		Assert.assertEquals(ForgotPassword_response.jsonPath().get("message"), Validate_Forgot_Password_Response,"Validate_Forgot_Password_Response_Message");
		Global.getCodeID=ForgotPassword_response.jsonPath().get("codeId");
		LoggingManager.logger.info("API-Forgot_Password_getCodeID : ["+ Global.getCodeID+"]");
		
		
//==========================================Forgot Password Confirm=========================================================================
		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMHHmmss");
        String UniqueID = localDateTime.format(formatter);
        
		HashMap<String, Object> Forgot_Password_Confirm_Body=new HashMap<String , Object>();
		Forgot_Password_Confirm_Body.put("code",Forgot_Password_Confirm_TFA);
		Forgot_Password_Confirm_Body.put("codeId", Global.getCodeID);
		Forgot_Password_Confirm_Body.put("newPassword",Forgot_NewPassword.concat(UniqueID));
		Forgot_Password_Confirm_Body.put("confirmNewPassword",Forgot_Confirm_NewPassword.concat(UniqueID));
		Response ForgotPassword_Confirm_response=
							given()	
									//.header("Authorization", "Bearer " + Global.getAccToken)
									.header("Content-Type",Content_Type) 
									.body(Forgot_Password_Confirm_Body)
								
							.when()
									.post(Forgot_Password_Confirm_BasePath)
								
							.then()
									//.statusCode(Integer.parseInt(Forgot_Password_Confirm_StatusCode))
									.extract().response();
			
		 Global.getPassword=Forgot_NewPassword.concat(UniqueID);	
		 
		 LoggingManager.logger.info("API-Forgot_Password_Confirm_BasePath : ["+Forgot_Password_Confirm_BasePath+"]");
		 LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
		 LoggingManager.logger.info("API-Forgot_Password_Confirm_Password : ["+Global.getPassword+"]");
		 LoggingManager.logger.info("API-Forgot_Password_Confirm_Body : ["+Forgot_Password_Confirm_Body.toString()+"]");
		 LoggingManager.logger.info("API-Forgot_Password_Confirm_BasePath : ["+ForgotPassword_Confirm_response.getStatusCode()+"]");
		 LoggingManager.logger.info("API-Forgot_Password_Confirm_Response_Body : ["+ForgotPassword_Confirm_response.getBody().asString()+"]");
		 
		 Assert.assertEquals(ForgotPassword_Confirm_response.getBody().asString(),Validate_Forgot_Password_Confirm_Response,"Validate_Forgot_Password_Confirm_Response");
				
//==========================================Login For Change Password=========================================================================		
		 
		
		 Global.getAccToken=userlogin.UserLoginAuthentications( Email,
																Global.getPassword,
																Base_Path_Login_Step1,
																Base_Path_Login_Step2,
																TFACode,
																Content_Type,
																Status_Code_Step1,
																Status_Code_Step2);
	
		 LoggingManager.logger.info("API-UserProfile_UserLogin Email : ["+Email+"]");
		 LoggingManager.logger.info("API-UserProfile_UserLogin loginPassword : ["+Global.getPassword+"]");
		 LoggingManager.logger.info("API-UserProfile_UserLogin_AccToken : ["+Global.getAccToken+"]");
		
		 if(Global.getAccToken == null || Global.getAccToken=="" ) 
		 {
			 Assert.fail("Logs : AccToken is not created"); 
		 }
//==========================================Change Password=========================================================================		
		
		LocalDateTime localDateTime_ChangePassword = LocalDateTime.now();
		DateTimeFormatter formatter_ChangePassword = DateTimeFormatter.ofPattern("yyMMHHmmss");
	    String UniqueID_ChangePassword = localDateTime_ChangePassword.format(formatter_ChangePassword);
	    
		HashMap<String, Object> UserProfile_ChangePassword_Body=new HashMap<String , Object>();
		UserProfile_ChangePassword_Body.put("oldPassword",Global.getPassword);
		UserProfile_ChangePassword_Body.put("newPassword",Change_NewPassword.concat(UniqueID_ChangePassword));
		UserProfile_ChangePassword_Body.put("confirmNewPassword",Change_Confirm_NewPassword.concat(UniqueID_ChangePassword));
		
		//String UserProfile_ChangePassword_Body="{\r\n"
												//+ "    \"confirmNewPassword\": \""+Change_Confirm_NewPassword.concat(UniqueID_ChangePassword)+"\",\r\n"
												//+ "    \"newPassword\": \""+Change_NewPassword.concat(UniqueID_ChangePassword)+"\",\r\n"
												//+ "    \"oldPassword\": \""+Global.getPassword+"\"\r\n"
												//+ "}";
		
		
		Response ChangePassword_response=
							given()	
									.header("Authorization", "Bearer " + Global.getAccToken)
									.header("Content-Type",Content_Type) 
									.body(UserProfile_ChangePassword_Body)
								
							.when()
									.put(UserProfile_ChangePassword_BasePath)
								
							.then()
									//.statusCode(Integer.parseInt(UserProfile_ChangePassword_StatusCode))
								//	.statusLine(UserProfile_ChangePassword_StatusLine)
									.extract().response();
		
		Global.getPassword=Change_NewPassword.concat(UniqueID_ChangePassword);
		LoggingManager.logger.info("API-UserProfile_ChangePassword_BasePath : ["+UserProfile_ChangePassword_BasePath+"]");
		LoggingManager.logger.info("API-UserProfile_ChangePassword_Body : ["+UserProfile_ChangePassword_Body.toString()+"]");
		LoggingManager.logger.info("API-UserProfile_ChangePassword_StatusCode : ["+ChangePassword_response.getStatusCode()+"]");
		LoggingManager.logger.info("API-UserProfile_ChangePassword_Response_Body : ["+ChangePassword_response.getBody().asString()+"]");
		//LoggingManager.Login_logger.info("API-Password After Changed : ["+Global.getPassword+"]");
		
		Assert.assertEquals(ChangePassword_response.getBody().asString(),Validate_UserProfile_ChangePassword_Response,"Validate_UserProfile_ChangePassword_Response");
		
		Global.getAccToken=userlogin.UserLoginAuthentications(  Email,
																Global.getPassword,
																Base_Path_Login_Step1,
																Base_Path_Login_Step2,
																TFACode,
																Content_Type,
																Status_Code_Step1,
																Status_Code_Step2);

		
		LoggingManager.logger.info("API-User_Login_with_AccToken : ["+Global.getAccToken+"]");
		
		if(Global.getAccToken == null || Global.getAccToken=="" ) 
		{
		Assert.fail("Logs : AccToken is not created"); 
		}			
		
		
	}	
}
