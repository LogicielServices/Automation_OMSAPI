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


public class StaticData_OrdType {


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
	
	@Test (dataProvider="StaticData_OrdType", dataProviderClass=ExcelDataProvider.class,groups={"StaticData_OrdType"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_StaticData_OrdType(String StaticData_OrdType_TestCases,
			  							  String StaticData_OrdType_BasePath,
											  String Content_Type,
											  String StaticData_OrdType_StatusCode,
											  String Validate_OrdType_Name ,
											  String Validate_OrdType_Value,
											  String Validate_Booth )
	{
		
		LoggingManager.logger.info("====================================================================");
		LoggingManager.logger.info("TestCase : "+StaticData_OrdType_TestCases);
		LoggingManager.logger.info("====================================================================");
	try {
		RestAssured.baseURI=Global.BaseURL;
		Response response=
							given()	
								.header("Content-Type",Content_Type) 
								.header("Authorization", "Bearer " + Global.getAccToken)
								
							.when()
								.get(StaticData_OrdType_BasePath)
								
							.then()
								.statusCode(Integer.parseInt(StaticData_OrdType_StatusCode))
								//.statusLine("HTTP/1.1 200 OK")
								.extract().response();
		
		
		String OrdTypeName=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.data.eventData[?(@.value =='"+Validate_OrdType_Value+"' )].name").toString();
		String OrdTypeValue=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.data.eventData[?(@.value =='"+Validate_OrdType_Value+"' )].value").toString();
		//String BoothID=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.data.eventData[?(@.value =='"+Validate_Destination_Value+"' )].booth").toString();
		LoggingManager.logger.info("API-StaticData_BlotterPermissions_BasePath : ["+StaticData_OrdType_BasePath+"]");
		LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
		LoggingManager.logger.info("API-StaticData_OrdType_StatusCode : ["+response.getStatusCode()+"]");
		LoggingManager.logger.info("API-Validate_OrdType_Name : ["+Validate_OrdType_Name +"] - Response OrdTypeName : "+OrdTypeName);
		LoggingManager.logger.info("API-Validate_OrdType_Value : ["+Validate_OrdType_Value +"] - Response OrdTypeValue : "+OrdTypeValue);
		//LoggingManager.logger.info("API-Validate_Booth : ["+Validate_Booth +"] - Response BoothID : "+BoothID);
		Assert.assertEquals(OrdTypeValue,"[\""+Validate_OrdType_Value+"\"]", "Validate_OrdType_Value");
		Assert.assertEquals(OrdTypeName,"[\""+Validate_OrdType_Name+"\"]", "Validate_OrdType_Name");
		//Assert.assertEquals(BoothID,"[\""+Validate_Booth+"\"]", "Validate_Booth");
		
		LoggingManager.logger.info("====================================================================");
	}
	catch (Exception e) 
	{
		LoggingManager.logger.error(e);
	}
	}	
}