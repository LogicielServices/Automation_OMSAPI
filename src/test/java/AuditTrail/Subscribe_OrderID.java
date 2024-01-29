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
import java.util.HashMap;


public class Subscribe_OrderID {
	
	
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
	 
	@Test (dataProvider="Subscribe_OrderID", dataProviderClass=ExcelDataProvider.class,groups={"Subscribe_OrderID"}, dependsOnGroups={"CreateFilledEquityOrder"})
	public void Verify_Subscribe_OrderID(String Subscribe_OrderID_TestCases,
										 String Subscribe_OrderID_BasePath,
										 String Content_Type,
										 String Subscribe_OrderID_StatusCode )
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+Subscribe_OrderID_TestCases);
			LoggingManager.logger.info("====================================================================");
			RestAssured.baseURI=Global.BaseURL;
			HashMap<Object, Object>  Subscribe_OrderID_Body=new HashMap<Object,Object>();
			Subscribe_OrderID_Body.put("orderId",Global.getOrderID);
			Response response=
							given()
							.header("Content-Type",Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)
							.body(Subscribe_OrderID_Body)

							.when()
							.post(Subscribe_OrderID_BasePath)

							.then()
							//.statusCode(Integer.parseInt(Subscribe_OrderID_StatusCode))
							//.statusLine("HTTP/1.1 200 OK")
							.extract().response();


			LoggingManager.logger.info("API-Subscribe_OrderID_BasePath : ["+Subscribe_OrderID_BasePath+"]");
			LoggingManager.logger.info("API-Subscribe_OrderID_Body : ["+Subscribe_OrderID_Body.toString()+"]");
			LoggingManager.logger.info("API-Subscribe_OrderID_StatusCode : ["+response.statusCode()+"]");
			String Response_Subscribe_OrderID=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.eventData[?(@.orderId =='"+Global.getOrderID+"' )].orderId").toString();
			Assert.assertEquals(response.statusCode(),Integer.parseInt(Subscribe_OrderID_StatusCode), "Validate_Subscribe_OrderID_StatusCode");
			//String Response_Subscribe_OrderID=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.eventData[?(@.qOrderID =='"+Validate_Subscribe_OrderID+"' )].qOrderID").toString();
			//System.out.println("Subscribe_qOrderID "+Subscribe_OrderID);
			//Assert.assertEquals(Subscribe_qOrderID,"[\""+Validate_Subscribe_qOrderID+"\"]", "Validate_Subscribe_qOrderID");
		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}
}
