package IdentityServer_API_User;

import APIHelper.APIHelperClass;
import APIHelper.Global;
import APIHelper.LoggingManager;
import XLDataProvider.ExcelDataProvider;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.testng.Tag;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static APIHelper.APIHelperClass.NVL;
import static io.restassured.RestAssured.given;

public class Biometric {
	
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
	 	
	@Severity(SeverityLevel.CRITICAL)
	@Owner("api.automation@mailinator.com")
	@Description("This is User Biometric Login TestCase")
	@Tag("UserBiometricLogin")
	@Test (dataProvider="LoginWithBiometric", dataProviderClass=ExcelDataProvider.class, groups={"LoginWithBiometric"})
	public void UserLoginWithBiometric(String LoginWithBiometric_TestCase,
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
									 String Base_Path_Biometric,
									 String Status_Code_Biometric,
									 String Biometric_Response_message,
									 String expectedResponse_Biometric)
	{
		LoggingManager.logger.info("====================================================================");
		LoggingManager.logger.info("TestCase : "+LoginWithBiometric_TestCase);
		LoggingManager.logger.info("====================================================================");
		String accessToken,tokenType,refreshToken,scope="",biometric_token="";
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

		//Map<String, Object> jsonResponse = Global.getResponse.jsonPath().get();
		if (Step2_Response_message.equalsIgnoreCase("null"))
		{
			if (Endpoint_Version.equalsIgnoreCase("v1")) { biometric_token=Global.getResponse.jsonPath().getString("biometric_token");	}
			else { biometric_token=Global.getResponse.jsonPath().getString("data.biometric_token"); }
			Assert.assertNotNull(biometric_token, "biometric_token value is null");
			Assert.assertEquals(Global.getResponse.getStatusCode(),Integer.parseInt(Status_Code_Step2), "Status code mismatch");
		}
		else
		{
			biometric_token="invalid";
		}
		//---------------------------------------Biometric API---------------------------------------------------------

		HashMap<String, String> mapBiometricLoginCredential=new HashMap<String, String>();
		mapBiometricLoginCredential.put("biometricToken", biometric_token);
		Response response_BiometricLogin=

				given()
						.header("Content-Type",Content_Type)
						.header("X-Internal-User","True")
						.body(mapBiometricLoginCredential)

						.when()
						.post(Base_Path_Biometric)

						.then()
						.extract().response();


		//Map<String, Object> jsonResponse_Biometric = response_BiometricLogin.jsonPath().get();

		LoggingManager.logger.info("API-Login_Base_Path_Biometric : ["+Base_Path_Biometric+"]");
		LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
		LoggingManager.logger.info("API-Biometric_Body : ["+mapBiometricLoginCredential+"]");
		LoggingManager.logger.info("API-BiometricLogin_StatusCode : ["+response_BiometricLogin.statusCode()+"]");
		LoggingManager.logger.info("API-BiometricLogin_Response_Body : ["+response_BiometricLogin.getBody().asString()+"]");


		if (Biometric_Response_message.equalsIgnoreCase("null"))
		{
			Map<String, Object> jsonResponse_Biometric = response_BiometricLogin.jsonPath().getMap("data");
			accessToken = response_BiometricLogin.jsonPath().getString("data.access_token");
			expiresIn = response_BiometricLogin.jsonPath().getInt("data.expires_in");
			tokenType = response_BiometricLogin.jsonPath().getString("data.token_type");
			refreshToken = response_BiometricLogin.jsonPath().getString("data.refresh_token");
			scope = response_BiometricLogin.jsonPath().getString("data.scope");
			biometric_token=response_BiometricLogin.jsonPath().getString("data.biometric_token");

			Assert.assertNotNull(accessToken, "biometric access_token value is null");
			Assert.assertTrue(expiresIn > 0, "biometric expires_in value is not greater than 0");
			Assert.assertEquals(tokenType, "Bearer", "biometric token_type value is not 'Bearer'");
			Assert.assertNotNull(refreshToken, "biometric refresh_token value is null");
			Assert.assertNotNull(biometric_token, "biometric_token value is null");
			Assert.assertNotNull(scope, "biometric scope value is null");
			Assert.assertEquals(response_BiometricLogin.getStatusCode(),Integer.parseInt(Status_Code_Biometric),"Biometric Status code mismatch");
			Assert.assertEquals(jsonResponse_Biometric.keySet().toString(), expectedResponse_Biometric, "Verify_Biometric_Response_Fields");
		}
		else
		{
			LoggingManager.logger.info("Validate_BiometricLogin_Response_message - Found :"+NVL(response_BiometricLogin.jsonPath().get("message"),"null")+" - Expected :"+Biometric_Response_message);
			Assert.assertEquals(NVL(response_BiometricLogin.jsonPath().get("message"),"null"),Biometric_Response_message,"Validate_BiometricLogin_Response_message");
		}

		if((Global.getAccToken == null || Global.getAccToken=="") && Biometric_Response_message.equalsIgnoreCase("null"))
		{Assert.fail("Logs : AccToken is not created Against User : ["+Email+"]");}
		
	}
}
