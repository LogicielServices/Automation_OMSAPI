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


public class StaticData_MktTopPerfCateg {


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
	 
	@Test (dataProvider="StaticData_MktTopPerfCateg", dataProviderClass=ExcelDataProvider.class,groups={"StaticData_MktTopPerfCateg"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_StaticData_MktTopPerfCateg(String StaticData_MktTopPerfCateg_TestCases,
			  								  String StaticData_MktTopPerfCateg_BasePath,
											  String Content_Type,
											  String StaticData_MktTopPerfCateg_StatusCode,
											  String Validate_MktTopPerfCateg_Name ,
											  String Validate_MktTopPerfCateg_Value,
											  String Validate_Booth )
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+StaticData_MktTopPerfCateg_TestCases);
			LoggingManager.logger.info("====================================================================");
			RestAssured.baseURI=Global.BaseURL;
			Response response=
							given()
							.header("Content-Type",Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)

							.when()
							.get(StaticData_MktTopPerfCateg_BasePath)

							.then()
							.statusCode(Integer.parseInt(StaticData_MktTopPerfCateg_StatusCode))
							//.statusLine("HTTP/1.1 200 OK")
							.extract().response();

			String MktTopPerfCategName=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.data.eventData[?(@.value =='"+Validate_MktTopPerfCateg_Value+"' )].name").toString();
			String MktTopPerfCategValue=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.data.eventData[?(@.value =='"+Validate_MktTopPerfCateg_Value+"' )].value").toString();
			//String BoothID=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.data.eventData[?(@.value =='"+Validate_Destination_Value+"' )].booth").toString();
			LoggingManager.logger.info("API-StaticData_MktTopPerfCateg_BasePath : ["+StaticData_MktTopPerfCateg_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-StaticData_MktTopPerfCateg_StatusCode : ["+response.getStatusCode()+"]");
			LoggingManager.logger.info("API-Validate_MktTopPerfCateg_Name : ["+Validate_MktTopPerfCateg_Name +"] - Response MktTopPerfCategName : "+MktTopPerfCategName);
			LoggingManager.logger.info("API-Validate_MktTopPerfCateg_Value : ["+Validate_MktTopPerfCateg_Value +"] - Response MktTopPerfCategValue : "+MktTopPerfCategValue);
			Assert.assertEquals(MktTopPerfCategValue,"[\""+Validate_MktTopPerfCateg_Value+"\"]", "Validate_MktTopPerfCateg_Value");
			Assert.assertEquals(MktTopPerfCategName,"[\""+Validate_MktTopPerfCateg_Name+"\"]", "Validate_MktTopPerfCateg_Name");
			//Assert.assertEquals(BoothID,"[\""+Validate_Booth+"\"]", "Validate_Booth");

		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}
}
