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


public class StaticData_LocateTIF {


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
	
	@Test (dataProvider="StaticData_LocateTIF", dataProviderClass=ExcelDataProvider.class,groups={"StaticData_LocateTIF"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_StaticData_LocateTIF(String StaticData_LocateTIF_TestCases,
			  								String StaticData_LocateTIF_BasePath,
											String Content_Type,
											String StaticData_LocateTIF_StatusCode,
											String Validate_LocateTIF_Name ,
											String Validate_LocateTIF_Value,
											String Validate_Booth )
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+StaticData_LocateTIF_TestCases);
			LoggingManager.logger.info("====================================================================");
			RestAssured.baseURI=Global.BaseURL;
			Response response=
					given()
							.header("Content-Type",Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)

							.when()
							.get(StaticData_LocateTIF_BasePath)

							.then()
							.statusCode(Integer.parseInt(StaticData_LocateTIF_StatusCode))
							//.statusLine("HTTP/1.1 200 OK")
							.extract().response();

			String LocateTIFName=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.data.eventData[?(@.value =='"+Validate_LocateTIF_Value+"' )].name").toString();
			String LocateTIFValue=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.data.eventData[?(@.value =='"+Validate_LocateTIF_Value+"' )].value").toString();
			//String BoothID=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.data.eventData[?(@.value =='"+Validate_Destination_Value+"' )].booth").toString();
			LoggingManager.logger.info("API-StaticData_LocateTIF_BasePath : ["+StaticData_LocateTIF_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-StaticData_LocateTIF_StatusCode : ["+response.getStatusCode()+"]");
			LoggingManager.logger.info("API-Validate_LocateTIF_Name : ["+Validate_LocateTIF_Name +"] - Response BlotterPermissions_Name : "+LocateTIFName);
			LoggingManager.logger.info("API-Validate_LocateTIF_Value : ["+Validate_LocateTIF_Value +"] - Response BlotterPermissions_isVisible : "+LocateTIFValue);
			Assert.assertEquals(LocateTIFValue,"[\""+Validate_LocateTIF_Value+"\"]", "Validate_LocateTIF_Value");
			Assert.assertEquals(LocateTIFName,"[\""+Validate_LocateTIF_Name+"\"]", "Validate_LocateTIF_Name");
			//Assert.assertEquals(BoothID,"[\""+Validate_Booth+"\"]", "Validate_Booth");

		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}
}
