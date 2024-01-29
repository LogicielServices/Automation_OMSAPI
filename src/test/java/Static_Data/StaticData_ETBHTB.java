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


public class StaticData_ETBHTB {


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
	
	@Test (dataProvider="StaticData_ETBHTB", dataProviderClass=ExcelDataProvider.class,groups={"StaticData_ETBHTB"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_StaticData_ETBHTB( String StaticData_ETBHTB_TestCases,
			  							  String StaticData_ETBHTB_BasePath,
										  String Content_Type,
										  String StaticData_ETBHTB_StatusCode,
										  String Validate_ETBHTB_ID ,
										  String Validate_ETBHTB_Symbol,
										  String Validate_ETBHTB_LocationID,
										  String Validate_ETBHTB_ContactName,
										  String Validate_ETBHTB_BoothID)
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+StaticData_ETBHTB_TestCases);
			LoggingManager.logger.info("====================================================================");
			RestAssured.baseURI=Global.BaseURL;
			Response response=
					given()
							.header("Content-Type",Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)

							.when()
							.get(StaticData_ETBHTB_BasePath)

							.then()
							.statusCode(Integer.parseInt(StaticData_ETBHTB_StatusCode))
							//.statusLine("HTTP/1.1 200 OK")
							.extract().response();


			String ETBHTB_ID=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.data.eventData[?(@.id =='"+Validate_ETBHTB_ID+"' )].id").toString();
			String ETBHTB_Symbol=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.data.eventData[?(@.id =='"+Validate_ETBHTB_ID+"' )].symbol").toString();
			String ETBHTB_LocationID=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.data.eventData[?(@.id =='"+Validate_ETBHTB_ID+"' )].locationID").toString();
			String ETBHTB_ContactName=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.data.eventData[?(@.id =='"+Validate_ETBHTB_ID+"' )].contactName").toString();
			String ETBHTB_BoothID=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.data.eventData[?(@.id =='"+Validate_ETBHTB_ID+"' )].boothID").toString();
			LoggingManager.logger.info("API-StaticData_ETBHTB_BasePath : ["+StaticData_ETBHTB_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-StaticData_ETBHTB_StatusCode : ["+response.getStatusCode()+"]");
			LoggingManager.logger.info("API-Validate_ETBHTB_ID : ["+Validate_ETBHTB_ID +"] - Response ETBHTB_ID : "+ETBHTB_ID);
			LoggingManager.logger.info("API-Validate_ETBHTB_Symbol : ["+Validate_ETBHTB_Symbol +"] - Response ETBHTB_Symbol : "+ETBHTB_Symbol);
			LoggingManager.logger.info("API-Validate_ETBHTB_LocationID : ["+Validate_ETBHTB_LocationID +"] - Response ETBHTB_LocationID : "+ETBHTB_LocationID);
			LoggingManager.logger.info("API-Validate_ETBHTB_ContactName : ["+Validate_ETBHTB_ContactName +"] - Response ETBHTB_ContactName : "+ETBHTB_ContactName);
			LoggingManager.logger.info("API-Validate_ETBHTB_BoothID : ["+Validate_ETBHTB_BoothID +"] - Response ETBHTB_BoothID : "+ETBHTB_BoothID);
			Assert.assertEquals(ETBHTB_ID,"[\""+Validate_ETBHTB_ID+"\"]", "Validate_ETBHTB_ID");
			Assert.assertEquals(ETBHTB_Symbol,"[\""+Validate_ETBHTB_Symbol+"\"]", "Validate_ETBHTB_Symbol");
			Assert.assertEquals(ETBHTB_LocationID,"[\""+Validate_ETBHTB_LocationID+"\"]", "Validate_ETBHTB_LocationID");
			Assert.assertEquals(ETBHTB_ContactName,"[\""+Validate_ETBHTB_ContactName+"\"]", "Validate_ETBHTB_ContactName");
			Assert.assertEquals(ETBHTB_BoothID,"[\""+Validate_ETBHTB_BoothID+"\"]", "Validate_ETBHTB_BoothID");

		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}
}
