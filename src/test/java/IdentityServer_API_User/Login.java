package IdentityServer_API_User;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import io.restassured.response.Response;
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

import javax.swing.*;

import static io.restassured.RestAssured.given;

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
	@Owner("api.automation@mailinator.com")
	@Description("This is User Login TestCase")
	@Tag("UserLogin")
	@Test (dataProvider="UserLogin", dataProviderClass=ExcelDataProvider.class, groups={"UserLoginAuthentications"})
	public void UserLogin(String Login_TestCase,
									 String Endpoint_Version,
									 String Base_Path_Login_Step1,
									 String Email,
									 String Password,
									 String Content_Type,
									 String Status_Code_Step1,
									 String Step1_Response_message,
									 String Base_Path_Login_Step2,
									 String TFACode,
									 String Status_Code_Step2,
						  			 String Step2_Response_message,
						  			 String expectedResponse)
	{
		LoggingManager.logger.info("====================================================================");
		LoggingManager.logger.info("TestCase : "+Login_TestCase);
		LoggingManager.logger.info("====================================================================");
		String accessToken,tokenType,refreshToken,scope="";
		int expiresIn=0;
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

		Map<String, Object> jsonResponse = Global.getResponse.jsonPath().get();
		LoggingManager.logger.info("API-Validate_Response_Fields : " + jsonResponse.keySet().toString());

		if (Endpoint_Version.equalsIgnoreCase("v1"))
		{
			 accessToken = Global.getResponse.jsonPath().getString("access_token");
			 expiresIn = Global.getResponse.jsonPath().getInt("expires_in");
			 tokenType = Global.getResponse.jsonPath().getString("token_type");
			 refreshToken = Global.getResponse.jsonPath().getString("refresh_token");
			 scope = Global.getResponse.jsonPath().getString("scope");
		}
		else
		{
			 accessToken = Global.getResponse.jsonPath().getString("data.access_token");
			 expiresIn = Global.getResponse.jsonPath().getInt("data.expires_in");
			 tokenType = Global.getResponse.jsonPath().getString("data.token_type");
			 refreshToken = Global.getResponse.jsonPath().getString("data.refresh_token");
			 scope = Global.getResponse.jsonPath().getString("data.scope");
		}

		Assert.assertNotNull(accessToken, "access_token value is null");
		Assert.assertTrue(expiresIn > 0, "expires_in value is not greater than 0");
		Assert.assertEquals(tokenType, "Bearer", "token_type value is not 'Bearer'");
		Assert.assertNotNull(refreshToken, "refresh_token value is null");
		Assert.assertNotNull(scope, "scope value is null");
		Assert.assertEquals(Global.getResponse.getStatusCode(), Integer.parseInt(Status_Code_Step2), "Status code mismatch");
		Assert.assertEquals(jsonResponse.keySet().toString(), expectedResponse, "Verify_Response_Fields");

		if(Global.getAccToken == null || Global.getAccToken=="" )
		{Assert.fail("Logs : AccToken is not created Against User : ["+Email+"]"); }
		
	}


	@Severity(SeverityLevel.CRITICAL)
	@Owner("api.automation@mailinator.com")
	@Description("This is Login Negation TestCase")
	@Tag("UserLogin")
	@Test (dataProvider="UserNegationLogin", dataProviderClass=ExcelDataProvider.class, groups={"UserLoginAuthenticationsNegation"})
	public void UserLoginNegative(String Login_Negation_TestCase,
						  String Base_Path_Login,
						  String Body,
						  String Content_Type,
						  String Status_Code,
						  String Error_Response)
	{
		LoggingManager.logger.info("====================================================================");
		LoggingManager.logger.info("TestCase : "+Login_Negation_TestCase);
		LoggingManager.logger.info("====================================================================");
		RestAssured.baseURI=Global.BaseURL;

		Response response_Negative_Login=

				given()
						.header("Content-Type",Content_Type)
						.header("X-Internal-User","True")
						.body(Body)

						.when()
						.post(Base_Path_Login)

						.then()
						.extract().response();

		LoggingManager.logger.info("API-Login_BasePath_Negative_Login : ["+Base_Path_Login+"]");
		LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
		LoggingManager.logger.info("API-Login_Body_NegativeCase : ["+Body+"]");
		LoggingManager.logger.info("API-Status_Code_Steps_StatusCode : ["+response_Negative_Login.statusCode()+"]");
		LoggingManager.logger.info("API-response_Login_Steps_Response_Body : ["+response_Negative_Login.getBody().asString()+"]");

		Assert.assertEquals(response_Negative_Login.getStatusCode(),Integer.parseInt(Status_Code),"User Negative Login response StatusCode");
		Assert.assertEquals(response_Negative_Login.getBody().asString(),Error_Response,"User Negative Login response Body");

	}



}
