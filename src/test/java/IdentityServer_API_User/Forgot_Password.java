package IdentityServer_API_User;

import APIHelper.APIHelperClass;
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

import static APIHelper.APIHelperClass.NVL;
import static io.restassured.RestAssured.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;


public class Forgot_Password {

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
										String Response_codeID_field,
										String Validate_Forgot_Password_Response,
										String Forgot_Password_Verify_BasePath,
										String Forgot_Password_Verify_TFA,
										String Forgot_Password_Verify_StatusCode,
										String Forgot_Password_Verify_StatusLine,
										String Validate_Forgot_Password_Verify_Response,
										String Forgot_Password_Confirm_BasePath,
										String Forgot_Password_Confirm_TFA,
										String NewPassword,
										String Confirm_NewPassword,
										String Forgot_Password_Confirm_StatusCode,
										String Forgot_Password_Confirm_StatusLine,
										String Validate_Forgot_Password_Confirm_Response,
										String Base_Path_Login_Step1,
										String Email,
										String Status_Code_Step1,
										String Step1_Response_message,
										String Base_Path_Login_Step2,
										String TFACode,
										String Status_Code_Step2,
										String Step2_Response_message)
	{
		LoggingManager.logger.info("====================================================================");
		LoggingManager.logger.info("TestCase : "+Forgot_Password_TestCase);
		LoggingManager.logger.info("====================================================================");
		RestAssured.baseURI=Global.BaseURL;
		//Global.getCodeID=null;
		
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
			
	 Global.getCodeID=response.jsonPath().get(Response_codeID_field);
	 
	LoggingManager.logger.info("API-Forgot_Password_BasePath : ["+Forgot_Password_BasePath+"]");
	LoggingManager.logger.info("API-Forgot_Password_Body : ["+Forgot_Password_Body.toString()+"]");
	LoggingManager.logger.info("API-Forgot_Password_StatusLine : ["+response.getStatusCode()+"]");
	LoggingManager.logger.info("API-Forgot_Password_Response_Body : ["+response.getBody().asString()+"]");
	LoggingManager.logger.info("API-Forgot_Password_getCodeID : ["+ Global.getCodeID+"]");
	Assert.assertEquals(response.statusCode(),Integer.parseInt(Forgot_Password_StatusCode),"Validate_Forgot_Password_StatusCode");
	Assert.assertEquals(response.statusLine(), Forgot_Password_StatusLine,"Validate_Forgot_Password_StatusLine");
	Assert.assertEquals(response.jsonPath().get("message"), Validate_Forgot_Password_Response,"Validate_Forgot_Password_Response");

//--------------------------Forgot Password Verify--------------------------------------------

		HashMap<String, Object> Forgot_Password_Verify_Body=new HashMap<String , Object>();

		Forgot_Password_Verify_Body.put("codeId", NVL(Global.getCodeID,"null"));
		Forgot_Password_Verify_Body.put("code",Forgot_Password_Verify_TFA);


		Response Verify_response=
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
		LoggingManager.logger.info("API-Forgot_Password_Verify_StatusCode : ["+Verify_response.getStatusCode()+"]");
		LoggingManager.logger.info("API-Forgot_Password_Verify_Response_Body : ["+Verify_response.getBody().asPrettyString()+"]");

		Assert.assertEquals(Verify_response.statusCode(),Integer.parseInt(Forgot_Password_Verify_StatusCode),"Validate_Forgot_Password_Verify_StatusCode");
		Assert.assertEquals(Verify_response.jsonPath().get("message"),Validate_Forgot_Password_Verify_Response,"Validate_Forgot_Password_Response");

//--------------------------Forgot Password Confirm--------------------------------------------

		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMHHmmss");
		String UniqueID = localDateTime.format(formatter);
		APIHelperClass userlogin=new APIHelperClass();

		HashMap<String, Object> Forgot_Password_Confirm_Body=new HashMap<String , Object>();
		Forgot_Password_Confirm_Body.put("code",Forgot_Password_Confirm_TFA);
		Forgot_Password_Confirm_Body.put("codeId", NVL(Global.getCodeID,"null"));
		Forgot_Password_Confirm_Body.put("newPassword",NewPassword.concat(UniqueID));
		Forgot_Password_Confirm_Body.put("confirmNewPassword",Confirm_NewPassword.concat(UniqueID));
		Response Forgot_Password_Confirm_response=
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
		LoggingManager.logger.info("API-Forgot_Password_Confirm_BasePath : ["+Forgot_Password_Confirm_response.getStatusCode()+"]");
		LoggingManager.logger.info("API-Forgot_Password_Confirm_Response_Body : ["+Forgot_Password_Confirm_response.getBody().asString()+"]");

		Assert.assertEquals(Forgot_Password_Confirm_response.getBody().asString(),Validate_Forgot_Password_Confirm_Response,"Validate_Forgot_Password_Confirm_Response");

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

		LoggingManager.logger.info("API-Validate_UserLogin Email : ["+Email+"]");
		LoggingManager.logger.info("API-Validate loginPassword : ["+Global.getPassword+"]");
		LoggingManager.logger.info("API-Validate_UserLogin_AccToken : ["+Global.getAccToken+"]");

		if((Global.getAccToken == null || Global.getAccToken=="") && Step2_Response_message=="null" )
		{
			Assert.fail("Logs : AccToken is not created");
		}

	}	
}
