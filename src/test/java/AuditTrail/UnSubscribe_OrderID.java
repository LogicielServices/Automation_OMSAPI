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


public class UnSubscribe_OrderID {
	
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
	 
	@Test (dataProvider="UnSubscribe_OrderID", dataProviderClass=ExcelDataProvider.class,groups={"UnSubscribe_OrderID"}, dependsOnGroups={"Subscribe_OrderID"})
	public void Verify_UnSubscribe_OrderID(String UnSubscribe_OrderID_TestCases,
										   String UnSubscribe_OrderID_BasePath,
										   String Content_Type,
										   String UnSubscribe_OrderID_StatusCode,
										   String Validate_UnSubscribe_Response)
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+UnSubscribe_OrderID_TestCases);
			LoggingManager.logger.info("====================================================================");
			RestAssured.baseURI=Global.BaseURL;
			HashMap<Object, Object>  UnSubscribe_OrderID_Body=new HashMap<Object,Object>();
			UnSubscribe_OrderID_Body.put("orderId",Global.getOrderID);
			Response response=
					given()
							.header("Content-Type",Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)
							.body(UnSubscribe_OrderID_Body)

							.when()
							.patch(UnSubscribe_OrderID_BasePath)

							.then()
							//.statusCode(Integer.parseInt(UnSubscribe_OrderID_StatusCode))
							//.statusLine("HTTP/1.1 200 OK")
							.extract().response();

			LoggingManager.logger.info("API-UnSubscribe_OrderID_BasePath : ["+UnSubscribe_OrderID_BasePath+"]");
			LoggingManager.logger.info("API-UnSubscribe_OrderID_Body : ["+UnSubscribe_OrderID_Body.toString()+"]");
			LoggingManager.logger.info("API-UnSubscribe_OrderID_StatusCode : ["+response.statusCode()+"]");
			LoggingManager.logger.info("API-UnSubscribe_OrderID_Respons_Body : ["+response.getBody().asString()+"]");
			Assert.assertEquals(response.statusCode(),Integer.parseInt(UnSubscribe_OrderID_StatusCode), "Validate_UnSubscribe_OrderID_StatusCode");
			Assert.assertEquals(response.getBody().asString(),Validate_UnSubscribe_Response, "Validate_UnSubscribe_Response");
			//String Response_UnSubscribe_OrderID=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.eventData[?(@.qOrderID =='"+Validate_UnSubscribe_OrderID+"' )].qOrderID").toString();
			//System.out.println("UnSubscribe_qOrderID "+UnSubscribe_OrderID);
			//Assert.assertEquals(UnSubscribe_qOrderID,"[\""+Validate_UnSubscribe_qOrderID+"\"]", "Validate_UnSubscribe_qOrderID");
		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}
}
