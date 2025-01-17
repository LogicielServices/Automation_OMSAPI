package Account_Balances;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.testng.Tag;
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


public class UnSubscribe_AccountBalance {

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

	@Owner("api.automation@mailinator.com")
	@Description("This is UnSubscribe_AccountBalance TestCase")
	@Tag("AccountBalance")
	@Test (dataProvider="UnSubscribe_AccountBalance", dataProviderClass=ExcelDataProvider.class,groups={"UnSubscribe_AccountBalance"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_UnSubscribe_AccountBalance(String TestCase,
												  String EndpointVersion,
												  String UnSubscribe_AccountBalance_BasePath,
												  String Content_Type,
												  String UnSubscribe_AccountBalance_StatusCode,
												  String Validate_UnSubscribe_Response)
	{
		LoggingManager.logger.info("====================================================================");
		LoggingManager.logger.info("TestCase : "+TestCase);
		LoggingManager.logger.info("====================================================================");
		RestAssured.baseURI=Global.BaseURL;
		Response response=
							given()	
								.header("Content-Type",Content_Type) 
								.header("Authorization", "Bearer " + Global.getAccToken)
								
							.when()
								.get(UnSubscribe_AccountBalance_BasePath)
								
							.then()
								.statusCode(Integer.parseInt(UnSubscribe_AccountBalance_StatusCode))
								.statusLine("HTTP/1.1 200 OK")
								.extract().response();
		
		LoggingManager.logger.info("API-UnSubscribe_AccountBalance_BasePath : ["+UnSubscribe_AccountBalance_BasePath+"]");
		LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
		LoggingManager.logger.info("API-UnSubscribe_AccountBalance_StatusCode : ["+response.getStatusCode()+"]");
		LoggingManager.logger.info("API-UnSubscribe_AccountBalance_StatusCode : ["+response.body().asString()+"]");
		//if(EndpointVersion.equalsIgnoreCase("V1")) {Assert.assertEquals(response.getBody().asString(), Validate_UnSubscribe_Response,"Validate_UnSubscribe_Response");}
		//else{Assert.assertEquals(response.jsonPath().get("message"), Validate_UnSubscribe_Response,"Validate_UnSubscribe_Response");}
			
	}	
}
