package IdentityServer_API_User;

import APIHelper.APIHelperClass;
import io.restassured.RestAssured;
import io.restassured.path.json.exception.JsonPathException;
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


public class Forgot_Password_Resend {

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
	
	@Test (dataProvider="Forgot_Password_Resend", dataProviderClass=ExcelDataProvider.class,groups={"Forgot_Password_Resend"})
	public void Verify_Forgot_Password_Resend(  String Forgot_Password_Resend_TestCase,
												String Forgot_Password_BasePath,
												String Content_Type,
												String Forgot_Password_Body,
												String Forgot_Password_StatusCode,
												String Forgot_Password_StatusLine,
												String Response_codeID_field,
												String Validate_Forgot_Password_Response,
												String Forgot_Password_Resend_BasePath,
												String Forgot_Password_Resend_StatusCode,
												String Forgot_Password_Resend_StatusLine,
												String Validate_Forgot_Password_Resend_Response,
												String Forgot_Password_Confirm_BasePath,
												String Forgot_Password_Confirm_TFA,
												String NewPassword,
												String Confirm_NewPassword,
												String Forgot_Password_Confirm_StatusCode,
												String Forgot_Password_Confirm_StatusLine,
												String Validate_Forgot_Password_Confirm_Response)
	{
		LoggingManager.logger.info("====================================================================");
		LoggingManager.logger.info("TestCase : "+Forgot_Password_Resend_TestCase);
		LoggingManager.logger.info("====================================================================");
		
		RestAssured.baseURI=Global.BaseURL;

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

		try {
			Global.getCodeID=response.jsonPath().get(Response_codeID_field);
		} catch (JsonPathException e) {
			LoggingManager.logger.info("API-Failed to parse JSON:: " + e.getMessage());
		}

		LoggingManager.logger.info("API-Forgot_Password_BasePath : ["+Forgot_Password_BasePath+"]");
		LoggingManager.logger.info("API-Forgot_Password_Request_Body : ["+Forgot_Password_Body.toString()+"]");
		LoggingManager.logger.info("API-Forgot_Password_StatusCode : ["+response.getStatusCode()+"]");
		LoggingManager.logger.info("API-Forgot_Password_StatusLine : ["+response.getStatusLine()+"]");
		LoggingManager.logger.info("API-Forgot_Password_Response_Body : ["+response.asString()+"]");
		LoggingManager.logger.info("API-Forgot_Password_getCodeID : ["+ Global.getCodeID+"]");
		Assert.assertEquals(response.statusCode(),Integer.parseInt(Forgot_Password_StatusCode),"Validate_Forgot_Password_StatusCode");
		Assert.assertEquals(response.statusLine(), Forgot_Password_StatusLine,"Validate_Forgot_Password_StatusLine");
		Assert.assertEquals(NVL(response.jsonPath().get("message"),"null"), Validate_Forgot_Password_Response,"Validate_Forgot_Password_Response");

//--------------------------Forgot Password Resend--------------------------------------------

		HashMap<String, String> Forgot_Password_Resend_Body=new HashMap<String , String>();
		Forgot_Password_Resend_Body.put("codeId", NVL(Global.getCodeID,"null"));

		Response forgot_resend_response=
							given()
									.header("Content-Type",Content_Type)
									.body(Forgot_Password_Resend_Body)
									//.pathParam("codeId",NVL(Global.getCodeID,"null"))
							.when()
									.post(Forgot_Password_Resend_BasePath)
								
							.then()
									//.statusCode(Integer.parseInt(Forgot_Password_Resend_StatusCode))
									//.statusLine(Forgot_Password_Resend_StatusLine)
									.extract().response();

		try {
			Global.getCodeID=forgot_resend_response.jsonPath().get(Response_codeID_field);
		} catch (JsonPathException e) {
			LoggingManager.logger.info("API-Failed to parse JSON:: " + e.getMessage());
		}

	 LoggingManager.logger.info("API-Forgot_Password_Resend_Response_StatusCode : ["+forgot_resend_response.getStatusCode()+"]");
	 LoggingManager.logger.info("API-Forgot_Password_Resend_Response_StatusLine : ["+forgot_resend_response.getStatusLine()+"]");
	 LoggingManager.logger.info("API-Forgot_Password_Resend_Response_Body : ["+forgot_resend_response.asString()+"]");

	 Assert.assertEquals(forgot_resend_response.statusCode(),Integer.parseInt(Forgot_Password_Resend_StatusCode),"Validate_Forgot_Password_Resend_StatusCode");
	 Assert.assertEquals(forgot_resend_response.statusLine(), Forgot_Password_Resend_StatusLine,"Validate_Forgot_Password_Resend_StatusLine");
	 Assert.assertEquals(forgot_resend_response.jsonPath().get("message"),Validate_Forgot_Password_Resend_Response,"Validate_Forgot_Password_Resend_Response");

	 //--------------------------Forgot Password Confirm--------------------------------------------

		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMHHmmss");
		String UniqueID = localDateTime.format(formatter);

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
		
		
	}	
}
