package Static_Data;

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


public class StaticData_Destination {


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
	 
	@Test (dataProvider="StaticData_Destination", dataProviderClass=ExcelDataProvider.class,groups={"StaticData_Destination"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_StaticData_Destination(String StaticData_Destination_TestCases,
			  								  String StaticData_Destination_BasePath,
											  String Content_Type,
											  String StaticData_Destination_StatusCode,
											  String Validate_Destination_Name ,
											  String Validate_Destination_Value,
											  String Validate_Destination_Account_Value,
											  String Validate_Booth )
	{
		LoggingManager.logger.info("====================================================================");
		LoggingManager.logger.info("TestCase : "+StaticData_Destination_TestCases);
		LoggingManager.logger.info("====================================================================");
	try {
		RestAssured.baseURI=Global.BaseURL;
		Response response=
							given()	
								.header("Content-Type",Content_Type) 
								.header("Authorization", "Bearer " + Global.getAccToken)
								
							.when()
								.get(StaticData_Destination_BasePath)
								
							.then()
								//.statusCode(Integer.parseInt(StaticData_Destination_StatusCode))
								//.statusLine("HTTP/1.1 200 OK")
								.extract().response();
		
		LoggingManager.logger.info("API-StaticData_Destination_BasePath : ["+StaticData_Destination_BasePath+"]");
		LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
		LoggingManager.logger.info("API-StaticData_Destination_StatusCode : ["+response.getStatusCode()+"]");
		Assert.assertEquals(response.getStatusCode(),Integer.parseInt(StaticData_Destination_StatusCode), "Validate_StaticData_Destination_StatusCode");
		String DestinationName=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.data.eventData[?(@.value =='"+Validate_Destination_Value.substring(1, Validate_Destination_Value.length() - 1)+"' )].name").toString();
		String DestinationValue=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.data.eventData[?(@.value =='"+Validate_Destination_Value.substring(1, Validate_Destination_Value.length() - 1)+"' )].value").toString();
		String DestinationAccountValue=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.data.eventData[?(@.value =='"+Validate_Destination_Value.substring(1, Validate_Destination_Value.length() - 1)+"' )].account").toString();
		//String BoothID=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.data.eventData[?(@.value =='"+Validate_Destination_Value+"' )].booth").toString();
		
		LoggingManager.logger.info("API-Validate_Destination_Name : ["+Validate_Destination_Name +"] - Response DestinationName : "+DestinationName);
		LoggingManager.logger.info("API-Validate_Destination_Value : ["+Validate_Destination_Value +"] - Response DestinationValue : "+DestinationValue);
		LoggingManager.logger.info("API-Validate_Destination_Account_Value : ["+Validate_Destination_Account_Value +"] - Response DestinationAccountValue : "+DestinationAccountValue);
		Assert.assertEquals(DestinationValue,"["+Validate_Destination_Value+"]", "Validate_Destination_Value");
		Assert.assertEquals(DestinationName,"[\""+Validate_Destination_Name+"\"]", "Validate_Destination_Name");
		//Assert.assertEquals(BoothID,"[\""+Validate_Booth+"\"]", "Validate_Booth");
		if (Validate_Destination_Account_Value.equalsIgnoreCase("null")) 
		{
		Assert.assertEquals(DestinationAccountValue,"["+Validate_Destination_Account_Value+"]", "Validate_Destination_Account_Value");
		}
		else 
		{
		Assert.assertEquals(DestinationAccountValue,"[\""+Validate_Destination_Account_Value+"\"]", "Validate_Destination_Account_Value");
		}
		
		LoggingManager.logger.info("====================================================================");
	}
	catch (Exception e) 
	{
		LoggingManager.logger.error(e);
	}
	}	
}
