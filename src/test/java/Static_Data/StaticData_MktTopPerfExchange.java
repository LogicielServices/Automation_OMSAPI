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


public class StaticData_MktTopPerfExchange {


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
	
	@Test (dataProvider="StaticData_MktTopPerfExchange", dataProviderClass=ExcelDataProvider.class,groups={"StaticData_MktTopPerfExchange"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_StaticData_MktTopPerfExchange(String StaticData_MktTopPerfExchange_TestCases,
			  								  String StaticData_MktTopPerfExchange_BasePath,
											  String Content_Type,
											  String StaticData_MktTopPerfExchange_StatusCode,
											  String Validate_MktTopPerfExchange_Name ,
											  String Validate_MktTopPerfExchange_Value,
											  String Validate_Booth )
	{
		
		LoggingManager.logger.info("====================================================================");
		LoggingManager.logger.info("TestCase : "+StaticData_MktTopPerfExchange_TestCases);
		LoggingManager.logger.info("====================================================================");
	try {	
		RestAssured.baseURI=Global.BaseURL;
		Response response=
							given()	
								.header("Content-Type",Content_Type) 
								.header("Authorization", "Bearer " + Global.getAccToken)
								
							.when()
								.get(StaticData_MktTopPerfExchange_BasePath)
								
							.then()
								.statusCode(Integer.parseInt(StaticData_MktTopPerfExchange_StatusCode))
								//.statusLine("HTTP/1.1 200 OK")
								.extract().response();
		
		
		String MktTopPerfExchangeName=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.data.eventData[?(@.value =='"+Validate_MktTopPerfExchange_Value+"' )].name").toString();
		String MktTopPerfExchangeValue=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.data.eventData[?(@.value =='"+Validate_MktTopPerfExchange_Value+"' )].value").toString();
		//String BoothID=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.data.eventData[?(@.value =='"+Validate_Destination_Value+"' )].booth").toString();
		LoggingManager.logger.info("API-StaticData_MktTopPerfExchange_BasePath : ["+StaticData_MktTopPerfExchange_BasePath+"]");
		LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
		LoggingManager.logger.info("API-StaticData_MktTopPerfExchange_StatusCode : ["+response.getStatusCode()+"]");
		LoggingManager.logger.info("API-Validate_MktTopPerfExchange_Name : ["+Validate_MktTopPerfExchange_Name +"] - Response MktTopPerfExchangeName : "+MktTopPerfExchangeName);
		LoggingManager.logger.info("API-Validate_MktTopPerfExchange_Value : ["+Validate_MktTopPerfExchange_Value +"] - Response MktTopPerfExchangeValue : "+MktTopPerfExchangeValue);
		Assert.assertEquals(MktTopPerfExchangeValue,"[\""+Validate_MktTopPerfExchange_Value+"\"]", "Validate_MktTopPerfExchange_Value");
		Assert.assertEquals(MktTopPerfExchangeName,"[\""+Validate_MktTopPerfExchange_Name+"\"]", "Validate_MktTopPerfExchange_Name");
		//Assert.assertEquals(BoothID,"[\""+Validate_Booth+"\"]", "Validate_Booth");
		LoggingManager.logger.info("====================================================================");
	}
	catch (Exception e) 
	{
		LoggingManager.logger.error(e);
	}	
	}	
}
