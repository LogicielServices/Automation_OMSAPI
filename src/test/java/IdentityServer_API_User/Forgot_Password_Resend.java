package IdentityServer_API_User;

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
	
	@Test (dataProvider="Forgot_Password_Resend", dataProviderClass=ExcelDataProvider.class,groups={"Forgot_Password_Resend"}, dependsOnGroups={"Forgot_Password"})
	public void Verify_Forgot_Password_Resend(  String Forgot_Password_Resend_TestCase,String Forgot_Password_Resend_BasePath,
												String Content_Type,
												String Forgot_Password_Resend_StatusCode,
												String Forgot_Password_Resend_StatusLine,
												String Validate_Forgot_Password_Resend_Response)
	{
		LoggingManager.logger.info("====================================================================");
		LoggingManager.logger.info("TestCase : "+Forgot_Password_Resend_TestCase);
		LoggingManager.logger.info("====================================================================");
		
		RestAssured.baseURI=Global.BaseURL;
		Response response=
							given()	
									//.header("Authorization", "Bearer " + Global.getAccToken)
									.header("Content-Type",Content_Type) 
									.pathParam("codeId",Global.getCodeID)
									
								
							.when()
									.post(Forgot_Password_Resend_BasePath.concat("{codeId}"))
								
							.then()
									//.statusCode(Integer.parseInt(Forgot_Password_Resend_StatusCode))
									//.statusLine(Forgot_Password_Resend_StatusLine)
									.extract().response();
			
	 Global.getCodeID=response.jsonPath().get("codeId");
	 		
	 LoggingManager.logger.info("API-Forgot_Password_Resend_BasePath : ["+Forgot_Password_Resend_BasePath.concat("{codeId}")+"]");
	 LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
	 LoggingManager.logger.info("API-Forgot_Password_Resend_StatusCode : ["+response.getStatusCode()+"]");
	 LoggingManager.logger.info("API-Forgot_Password_Resend_Response_Body : ["+response.getBody().asString()+"]");
	 LoggingManager.logger.info("API-Forgot_Password_Resend_CodID : ["+Global.getCodeID+"]");
	 
	 Assert.assertEquals(response.statusCode(),Integer.parseInt(Forgot_Password_Resend_StatusCode),"Validate_Forgot_Password_Resend_StatusCode");
	 Assert.assertEquals(response.statusLine(), Forgot_Password_Resend_StatusLine,"Validate_Forgot_Password_Resend_StatusLine");
	 Assert.assertEquals(response.jsonPath().get("message"),Validate_Forgot_Password_Resend_Response,"Validate_Forgot_Password_Resend_Response");
					
		
		
	}	
}
