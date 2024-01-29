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


public class StaticData_Side {


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
	
	@Test (dataProvider="StaticData_Side", dataProviderClass=ExcelDataProvider.class,groups={"StaticData_Side"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_StaticData_Side(String StaticData_Side_TestCases,
			  						   String StaticData_Side_BasePath,
											  String Content_Type,
											  String StaticData_Side_StatusCode,
											  String Validate_Side_Name ,
											  String Validate_Side_Value,
											  String Validate_Booth )
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+StaticData_Side_TestCases);
			LoggingManager.logger.info("====================================================================");
			RestAssured.baseURI=Global.BaseURL;
			Response response=
							 given()
							.header("Content-Type",Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)

							.when()
							.get(StaticData_Side_BasePath)

							.then()
							.statusCode(Integer.parseInt(StaticData_Side_StatusCode))
							//.statusLine("HTTP/1.1 200 OK")
							.extract().response();


			String SideName=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.data.eventData[?(@.value =='"+Validate_Side_Value+"' )].name").toString();
			String SideValue=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.data.eventData[?(@.value =='"+Validate_Side_Value+"' )].value").toString();
			//String BoothID=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.data.eventData[?(@.value =='"+Validate_Destination_Value+"' )].booth").toString();
			LoggingManager.logger.info("API-StaticData_Side_BasePath : ["+StaticData_Side_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-StaticData_Side_StatusCode : ["+response.getStatusCode()+"]");
			LoggingManager.logger.info("API-Validate_Side_Name : ["+Validate_Side_Name +"] - Response SideName : "+SideName);
			LoggingManager.logger.info("API-Validate_Side_Value : ["+Validate_Side_Value +"] - Response SideValue : "+SideValue);
			//LoggingManager.logger.info("API-Validate_Booth : ["+Validate_Booth +"] - Response BoothID : "+BoothID);
			Assert.assertEquals(SideValue,"[\""+Validate_Side_Value+"\"]", "Validate_Side_Value");
			Assert.assertEquals(SideName,"[\""+Validate_Side_Name+"\"]", "Validate_Side_Name");
			//Assert.assertEquals(BoothID,"[\""+Validate_Booth+"\"]", "Validate_Booth");

		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}

}
