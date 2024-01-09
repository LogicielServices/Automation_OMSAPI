package AuditTrail;

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


public class UnSubscribe_qOrderID {
	
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
	 
	@Test (dataProvider="UnSubscribe_qOrderID", dataProviderClass=ExcelDataProvider.class,groups={"UnSubscribe_qOrderID"}, dependsOnGroups={"Subscribe_qOrderID"})
	public void Verify_UnSubscribe_qOrderID(String UnSubscribe_qOrderID_TestCases,
											String UnSubscribe_qOrderID_BasePath,
											String Content_Type,
											String UnSubscribe_qOrderID_StatusCode,
										  	String Validate_UnSubscribe_Response )
	{
		LoggingManager.logger.info("====================================================================");
		LoggingManager.logger.info("TestCase : "+UnSubscribe_qOrderID_TestCases);
		LoggingManager.logger.info("====================================================================");
		RestAssured.baseURI=Global.BaseURL;
		Response response=
							given()	
								.header("Content-Type",Content_Type) 
								.header("Authorization", "Bearer " + Global.getAccToken)
								
							.when()
								.get(UnSubscribe_qOrderID_BasePath+Global.qOrderID)
								
							.then()
								//.statusCode(Integer.parseInt(UnSubscribe_qOrderID_StatusCode))
								//.statusLine("HTTP/1.1 200 OK")
								.extract().response();
		
		LoggingManager.logger.info("API-UnSubscribe_qOrderID_BasePath : ["+UnSubscribe_qOrderID_BasePath+Global.qOrderID+"]");
		LoggingManager.logger.info("API-UnSubscribe_qOrderID_StatusCode : ["+response.statusCode()+"]");	
		LoggingManager.logger.info("API-UnSubscribe_qOrderID_Responses_Body : ["+response.getBody().asString()+"]");
		Assert.assertEquals(response.statusCode(),Integer.parseInt(UnSubscribe_qOrderID_StatusCode), "Validate_UnSubscribe_qOrderID_StatusCode");
		Assert.assertEquals(response.getBody().asString(),Validate_UnSubscribe_Response, "Validate_UnSubscribe_Audit_Response_Body");
		
		
		
	}	
}
