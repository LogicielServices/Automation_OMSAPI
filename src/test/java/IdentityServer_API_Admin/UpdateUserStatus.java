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


public class UpdateUserStatus {
	
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
	// @Test (dataProvider="UpdateUserStatus", dataProviderClass=ExcelDataProvider.class , groups={"UpdateUserStatus"}, dependsOnGroups={"AdminLoginAuthentications"})
	 @Test (dataProvider="UpdateUserStatus", dataProviderClass=ExcelDataProvider.class , groups={"UpdateUserStatus"})
	 public void Verify_Update_User_Status(	String Update_Status_TestCases,String Update_Status_Base_Path,
											String Content_Type ,
											String User_Status_Update_Body,
											String User_Update_Status_code,
											String User_Update_Response_Message,
											String Base_Path_Login_Step1,
											String Login_Email_Body,
											String Validate_Message											
											)
												
		{
		 
		 	LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+Update_Status_TestCases);
			LoggingManager.logger.info("====================================================================");
			RestAssured.baseURI=Global.BaseURL;
			Response Status_Update_response=
				
								given()	
										.header("Content-Type",Content_Type) 
										.header("Authorization", "Bearer " + Global.getAccToken)
										.body(User_Status_Update_Body)
									
								.when()
										.put(Update_Status_Base_Path)
									
								.then()
										.extract().response();
			LoggingManager.logger.info("API-Update_Status_Base_Path : ["+Update_Status_Base_Path+"]");
			LoggingManager.logger.info("API-User_Status_Update_Body : ["+User_Status_Update_Body+"]");
			LoggingManager.logger.info("API-User_Update_Status_code : ["+Status_Update_response.statusCode()+"]");			
			LoggingManager.logger.info("API-Account_Associate_Response_Body : ["+Status_Update_response.body().asString()+"]");
			
			Assert.assertEquals(Status_Update_response.body().asString(), User_Update_Response_Message,"Verify_Update_User_Status");
			
			Response Status_Validation_response=
					
					given()	
							.header("Content-Type",Content_Type) 
							.body(Login_Email_Body)
						
					.when()
							.post(Base_Path_Login_Step1)
						
					.then()
							.extract().response();
			
			LoggingManager.logger.info("API-Validate_Login_Email_Base : ["+Base_Path_Login_Step1+"]");
			LoggingManager.logger.info("API-Validate_Login_Email_Body : ["+Login_Email_Body+"]");
			LoggingManager.logger.info("API-Validate_response_StatusCode : ["+Status_Validation_response.statusCode()+"]");	
			LoggingManager.logger.info("API-Validate_Status_Response_Message : ["+Status_Validation_response.jsonPath().get("message")+"]");
			LoggingManager.logger.info("API-Validate_Status_Response_Body : ["+Status_Validation_response.getBody().asString()+"]");
			
			Assert.assertEquals(Status_Validation_response.jsonPath().get("message"), Validate_Message,"Verify_Update_Status_Validation");
			
			
		}
		
}
	

