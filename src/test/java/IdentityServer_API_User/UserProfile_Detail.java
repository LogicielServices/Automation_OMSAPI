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

import static APIHelper.APIHelperClass.NVL;
import static io.restassured.RestAssured.*;

import java.io.PrintWriter;
import java.io.StringWriter;



public class UserProfile_Detail {

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
	
	@Test (dataProvider="UserProfile_Detail", dataProviderClass=ExcelDataProvider.class,groups={"UserProfile_Detail"})//dependsOnGroups={"Forgot_Password_Confirm"} UserProfile_ChangePassword
	public void Verify_UserProfile_Detail(	 String UserProfile_Detail_TestCase,
											 String Base_Path_Login_Step1,
											 String Content_Type,
											 String Email,
											 String Password,
											 String Status_Code_Step1,
											 String Step1_Response_message,
											 String Base_Path_Login_Step2,
											 String TFACode,
											 String Status_Code_Step2,
											 String Step2_Response_message,
											 String UserProfile_Detail_BasePath,
											 String UserProfile_Detail_Email,
											 String UserProfile_Detail_StatusCode,
											 String UserProfile_Detail_StatusLine,
											 String Validate_UserProfile_Email ,
											 String Validate_UserProfile_vTraderUsername,
											 String Validate_UserProfile_BoothID )
	{
		LoggingManager.logger.info("====================================================================");
		LoggingManager.logger.info("TestCase : "+UserProfile_Detail_TestCase);
		LoggingManager.logger.info("====================================================================");
		
		RestAssured.baseURI=Global.BaseURL;
		APIHelperClass userlogin=new APIHelperClass();
		Global.getAccToken=userlogin.UserLoginAuthentications(  Email,
																Password,
																Base_Path_Login_Step1,
																Base_Path_Login_Step2,
																TFACode,
																Content_Type,
																Status_Code_Step1,
																Step1_Response_message,
																Status_Code_Step2,
																Step2_Response_message);
		
		
		
		Response response=
							given()	
								.header("Content-Type",Content_Type) 
								.header("Authorization", "Bearer " + Global.getAccToken)
								//.queryParam("email", UserProfile_Detail_Email)
								
							.when()
								.get(UserProfile_Detail_BasePath.concat("?email="+UserProfile_Detail_Email))
								
							.then()
								.statusCode(Integer.parseInt(UserProfile_Detail_StatusCode))
								.statusLine(UserProfile_Detail_StatusLine)
								.extract().response();

		if (response.statusCode()==200) {

			LoggingManager.logger.info("API-UserProfile_Detail_BasePath : [" + UserProfile_Detail_BasePath.concat("?email=" + UserProfile_Detail_Email) + "]");
			LoggingManager.logger.info("API-UserProfile_Detail_StatusCode : [" + response.getStatusCode() + "]");
			LoggingManager.logger.info("API-Validate_UserProfile_Email : [" + Validate_UserProfile_Email + "] - Response Email : [" + response.jsonPath().get("email") + "]");
			LoggingManager.logger.info("API-Validate_UserProfile_vTraderUsername : [" + Validate_UserProfile_vTraderUsername + "] - Response vTraderUsername : [" + response.jsonPath().get("vTraderUsername") + "]");
			LoggingManager.logger.info("API-Validate_UserProfile_BoothID : [" + Validate_UserProfile_BoothID + "] - Response BoothID : [" + response.jsonPath().get("boothID") + "]");
			LoggingManager.logger.info("API-Validate_UserProfile_Response_Body : [" + response.getBody().asString() + "]");
			LoggingManager.logger.info("API-UserProfile_Detail_StatusCode : [" + response.getStatusCode() + "]");

			Assert.assertEquals(response.getStatusCode(), Integer.parseInt(UserProfile_Detail_StatusCode), "Validate_UserProfile_Detail_StatusCode");
			Assert.assertEquals(response.getStatusLine(), UserProfile_Detail_StatusLine, "Validate_UserProfile_Detail_StatusLine");
			Assert.assertEquals(response.jsonPath().get("email"), Validate_UserProfile_Email, "Validate_UserProfile_Email");
			Assert.assertEquals(response.jsonPath().get("vTraderUsername"), Validate_UserProfile_vTraderUsername, "Validate_UserProfile_vTraderUsername");
			Assert.assertEquals(response.jsonPath().get("boothID"), Validate_UserProfile_BoothID, "Validate_UserProfile_BoothID");
		}
		else
		{
			LoggingManager.logger.info("API-UserProfile_Detail_StatusCode : [" + response.getStatusCode() + "]");
			LoggingManager.logger.info("API-UserProfile_Detail_StatusLine : [" + response.getStatusLine() + "]");
			Assert.assertEquals(response.getStatusCode(), Integer.parseInt(UserProfile_Detail_StatusCode), "Validate_UserProfile_Detail_StatusCode");
			Assert.assertEquals(response.getStatusLine(), UserProfile_Detail_StatusLine, "Validate_UserProfile_Detail_StatusLine");
		}
	}	
}
