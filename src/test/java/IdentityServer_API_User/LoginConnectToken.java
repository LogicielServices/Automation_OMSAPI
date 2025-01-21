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
import java.util.Map;

import static APIHelper.APIHelperClass.NVL;
import static io.restassured.RestAssured.given;

public class LoginConnectToken {

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
	@Description("This is a ConnectTokenEndpoint Positive test case")
	@Tag("ConnectTokenEndpoint")
	@Test(dataProvider = "ConnectTokenEndpoint",dataProviderClass=ExcelDataProvider.class,  groups = {"ConnectTokenAuthentication"})
	public void ConnectTokenEndpoint_Cases(	String ConnectToken_TestCases,
											String basePath,
											String grantType,
											String clientId,
											String username,
											String password,
											String contentType,
											String expectedStatusCode,
											String expectedResponse) {

		// Log test case details
		LoggingManager.logger.info("====================================================================");
		LoggingManager.logger.info("Test Case: " + ConnectToken_TestCases);
		LoggingManager.logger.info("====================================================================");

		// Set base URI
		RestAssured.baseURI = Global.BaseURL;

		// Make the POST request
		Response response =
				given()
						.header("Content-Type", contentType)
						.formParam("grant_type", grantType)
						.formParam("client_id", clientId)
						.formParam("username", username)
						.formParam("password", password)
						.when()
						.post(basePath)
						.then()
						.extract().response();

		// Log request and response details
		Map<String, Object> jsonResponse = response.jsonPath().get();
		LoggingManager.logger.info("API Base Path: [" + basePath + "]");
		LoggingManager.logger.info("API Content-Type: [" + contentType + "]");
		LoggingManager.logger.info("API Request Body: [grant_type=" + grantType + ", client_id=" + clientId + ", username=" + username + ", password=" + password + "]");
		LoggingManager.logger.info("API Status Code: [" + response.getStatusCode() + "]");
		LoggingManager.logger.info("API Response Body: [" + response.getBody().asString() + "]");
		LoggingManager.logger.info("API-Validate_Response_Fields : "+(jsonResponse.keySet()).toString());

		// Assertions
		String accessToken = response.jsonPath().getString("access_token");
		int expiresIn = response.jsonPath().getInt("expires_in");
		String tokenType = response.jsonPath().getString("token_type");
		String refreshToken = response.jsonPath().getString("refresh_token");
		String scope = response.jsonPath().getString("scope");

		Assert.assertNotNull(accessToken, "access_token value is null");
		Assert.assertTrue(expiresIn > 0, "expires_in value is not greater than 0");
		Assert.assertEquals(tokenType, "Bearer", "token_type value is not 'Bearer'");
		Assert.assertNotNull(refreshToken, "refresh_token value is null");
		Assert.assertNotNull(scope, "scope value is null");


		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode), "Status code mismatch");
		Assert.assertEquals((jsonResponse.keySet()).toString(), expectedResponse,"Verify_Response_Fields");
	}


	@Severity(SeverityLevel.CRITICAL)
	@Owner("api.automation@mailinator.com")
	@Description("This is a ConnectTokenEndpoint_Negative test case")
	@Tag("ConnectTokenEndpoint")
	@Test(dataProvider = "ConnectTokenEndpointNegative",dataProviderClass=ExcelDataProvider.class,  groups = {"ConnectTokenAuthenticationNegative"})
	public void ConnectTokenEndpoint_NegativeCases(	String ConnectToken_TestCases,
											   String basePath,
											   String grantType,
											   String clientId,
											   String username,
											   String password,
											   String contentType,
											   String expectedStatusCode,
											   String Error_Message,
											   String Error_Description,
											   String expectedResponse) {

		// Log test case details
		LoggingManager.logger.info("====================================================================");
		LoggingManager.logger.info("Test Case: " + ConnectToken_TestCases);
		LoggingManager.logger.info("====================================================================");

		// Set base URI
		RestAssured.baseURI = Global.BaseURL;

		// Make the POST request
		Response response =
				given()
						.header("Content-Type", contentType)
						.formParam("grant_type", grantType)
						.formParam("client_id", clientId)
						.formParam("username", username)
						.formParam("password", password)
						.when()
						.post(basePath)
						.then()
						.extract().response();

		// Log request and response details
		Map<String, Object> jsonResponse = response.jsonPath().get();
		LoggingManager.logger.info("API Base Path: [" + basePath + "]");
		LoggingManager.logger.info("API Content-Type: [" + contentType + "]");
		LoggingManager.logger.info("API Request Body: [grant_type=" + grantType + ", client_id=" + clientId + ", username=" + username + ", password=" + password + "]");
		LoggingManager.logger.info("API Status Code: [" + response.getStatusCode() + "]");
		LoggingManager.logger.info("API Response Body: [" + response.getBody().asString() + "]");

		// Assertions
		LoggingManager.logger.info("API-Validate_Response_Fields : "+(jsonResponse.keySet()).toString());
		LoggingManager.logger.info("API-Validate_Response_Error_Message : "+response.jsonPath().getString("error"));
		LoggingManager.logger.info("API-Validate_Response_Error_Description : "+response.jsonPath().getString("error_description"));
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(expectedStatusCode), "Status code mismatch");
		Assert.assertEquals(NVL(response.jsonPath().getString("error"),"null"),Error_Message,"Verify_Response_Error_Message");
		Assert.assertEquals(NVL((response.jsonPath().getString("error_description")),"null"),Error_Description,"Verify_Response_Error_Description");
		//Assert.assertEquals((jsonResponse.keySet()).toString(), expectedResponse,"Verify_Response_Fields");
		Assert.assertEquals(response.getBody().asString(), expectedResponse, "Response body mismatch");
	}

}
