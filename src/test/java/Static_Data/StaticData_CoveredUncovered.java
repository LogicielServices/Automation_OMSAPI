package Static_Data;

import APIHelper.APIHelperClass;
import APIHelper.Global;
import APIHelper.LoggingManager;
import XLDataProvider.ExcelDataProvider;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static io.restassured.RestAssured.given;


public class StaticData_CoveredUncovered {


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
	 
	@Test (dataProvider="StaticData_CoveredUncovered", dataProviderClass=ExcelDataProvider.class,groups={"StaticData_CoveredUncovered"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_StaticData_CoveredUncovered(String StaticData_CoveredUncovered_TestCases,
												   String EndpointVersion,
												   String StaticData_CoveredUncovered_BasePath,
												   String Content_Type,
												   String StaticData_CoveredUncovered_StatusCode,
												   String Validate_CoveredUncovered_Name ,
												   String Validate_CoveredUncovered_Value,
												   String Validate_Booth )
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+StaticData_CoveredUncovered_TestCases);
			LoggingManager.logger.info("====================================================================");
			Global.getResponseArray=APIHelperClass.apiRespVersion(EndpointVersion);
			RestAssured.baseURI=Global.BaseURL;
			Response response=
					given()
							.header("Content-Type",Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)

							.when()
							.get(StaticData_CoveredUncovered_BasePath)

							.then()
							//.statusCode(Integer.parseInt(StaticData_CoveredUncovered_StatusCode))
							//.statusLine("HTTP/1.1 200 OK")
							.extract().response();

			LoggingManager.logger.info("API-Endpoint Version : [" + EndpointVersion + "]");
			LoggingManager.logger.info("API-StaticData_CoveredUncovered_BasePath : ["+StaticData_CoveredUncovered_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-StaticData_CoveredUncovered_StatusCode : ["+response.getStatusCode()+"]");
			Assert.assertEquals(response.getStatusCode(),Integer.parseInt(StaticData_CoveredUncovered_StatusCode), "Validate_StaticData_CoveredUncovered_StatusCode");
			String CoveredUncoveredName=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =='"+Validate_CoveredUncovered_Value+"' )].name").toString();
			String CoveredUncoveredValue=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =='"+Validate_CoveredUncovered_Value+"' )].value").toString();
			String BoothID=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =='"+Validate_CoveredUncovered_Value+"' )].booth").toString();
			LoggingManager.logger.info("API-Validate_CoveredUncovered_Name : "+ APIHelperClass.ValidationNullValue(Validate_CoveredUncovered_Name) +" - Response CoveredUncoveredName : "+CoveredUncoveredName);
			LoggingManager.logger.info("API-Validate_CoveredUncovered_Value : "+ APIHelperClass.ValidationNullValue(Validate_CoveredUncovered_Value) +" - Response CoveredUncoveredValue : "+CoveredUncoveredValue);
			LoggingManager.logger.info("API-Validate_Booth : "+APIHelperClass.ValidationNullValue(Validate_Booth)+" - Response BoothID : "+BoothID);
			Assert.assertEquals(CoveredUncoveredValue,APIHelperClass.ValidationNullValue(Validate_CoveredUncovered_Value),"Validate_CoveredUncovered_Value");
			Assert.assertEquals(CoveredUncoveredName,APIHelperClass.ValidationNullValue(Validate_CoveredUncovered_Name),"Validate_CoveredUncovered_Name");
			Assert.assertEquals(BoothID,APIHelperClass.ValidationNullValue(Validate_Booth),"Validate_Booth");
		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}
}
