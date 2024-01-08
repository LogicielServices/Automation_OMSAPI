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


public class Subscribe_qOrderID {
	
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
	 
	@Test (dataProvider="Subscribe_qOrderID", dataProviderClass=ExcelDataProvider.class,groups={"Subscribe_qOrderID"}, dependsOnGroups={"CreateFilledEquityOrder"})//UserLoginAuthentications
	public void Verify_Subscribe_qOrderID(String Subscribe_qOrderID_TestCases,
										  String Subscribe_qOrderID_BasePath,
										  String Content_Type,
										  String Subscribe_qOrderID_StatusCode)
	{
		LoggingManager.logger.info("====================================================================");
		LoggingManager.logger.info("TestCase : "+Subscribe_qOrderID_TestCases);
		LoggingManager.logger.info("====================================================================");
		RestAssured.baseURI=Global.BaseURL;
		Response response=
							given()	
								.header("Content-Type",Content_Type) 
								.header("Authorization", "Bearer " + Global.getAccToken)
								
							.when()
								.get(Subscribe_qOrderID_BasePath+Global.qOrderID)
								
							.then()
								//.statusCode(Integer.parseInt(Subscribe_qOrderID_StatusCode))
								//.statusLine("HTTP/1.1 200 OK")
								.extract().response();
		
		
		LoggingManager.logger.info("API-Subscribe_qOrderID_BasePath : ["+Subscribe_qOrderID_BasePath+Global.qOrderID+"]");
		LoggingManager.logger.info("API-Subscribe_qOrderID_StatusCode : ["+response.statusCode()+"]");	
		String Response_Subscribe_OrderID=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.eventData[?(@.orderId =='"+Global.getOrderID+"' )].orderId").toString();
		
		Assert.assertEquals(response.statusCode(),Integer.parseInt(Subscribe_qOrderID_StatusCode), "Validate_Subscribe_qOrderID_StatusCode");
		Assert.assertEquals(Response_Subscribe_OrderID,Global.getOrderID, "Validate_Response_Subscribe_OrderID");
		
	}	
}
