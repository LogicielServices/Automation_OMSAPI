package Static_Data;

import APIHelper.APIHelperClass;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.testng.Tag;
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


public class StaticData_CommType {


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
	@Owner("api.automation@mailinator.com")
	@Description("This is StaticData_CommType TestCase")
	@Tag("StaticData")
	@Test (dataProvider="StaticData_CommType", dataProviderClass=ExcelDataProvider.class,groups={"StaticData_CommType"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_StaticData_CommType(String StaticData_CommType_TestCases,
										   String EndpointVersion,
			  							   String StaticData_CommType_BasePath,
										   String Content_Type,
										   String StaticData_CommType_StatusCode,
										   String Validate_CommType_Name ,
										   String Validate_CommType_Value,
										   String Validate_Booth )
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+StaticData_CommType_TestCases);
			LoggingManager.logger.info("====================================================================");
			Global.getResponseArray=APIHelperClass.apiRespVersion(EndpointVersion);
			RestAssured.baseURI=Global.BaseURL;
			Response response=
							 given()
							.header("Content-Type",Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)

							.when()
							.get(StaticData_CommType_BasePath)

							.then()
							//.statusCode(Integer.parseInt(StaticData_CommType_StatusCode))
							//.statusLine("HTTP/1.1 200 OK")
							.extract().response();

			LoggingManager.logger.info("API-Endpoint Version : [" + EndpointVersion + "]");
			LoggingManager.logger.info("API-StaticData_CommType_BasePath : ["+StaticData_CommType_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-StaticData_CommType_StatusCode : ["+response.getStatusCode()+"]");
			Assert.assertEquals(response.getStatusCode(),Integer.parseInt(StaticData_CommType_StatusCode), "Validate_StaticData_CommType_StatusCode");
			String CommTypeName=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =='"+Validate_CommType_Value+"' )].name").toString();
			String CommTypeValue=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =='"+Validate_CommType_Value+"' )].value").toString();
			String BoothID=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =='"+Validate_CommType_Value+"' )].booth").toString();
			LoggingManager.logger.info("API-Validate_CommType_Name : "+APIHelperClass.ValidationNullValue(Validate_CommType_Name) +" - Response CommTypeName : "+CommTypeName);
			LoggingManager.logger.info("API-Validate_CommType_Value : "+APIHelperClass.ValidationNullValue(Validate_CommType_Value)+" - Response CommTypeValue : "+CommTypeValue);
			LoggingManager.logger.info("API-Validate_Booth : "+APIHelperClass.ValidationNullValue(Validate_Booth)+" - Response BoothID : "+BoothID);
			Assert.assertEquals(CommTypeValue,APIHelperClass.ValidationNullValue(Validate_CommType_Value), "Validate_CommType_Value");
			Assert.assertEquals(CommTypeName,APIHelperClass.ValidationNullValue(Validate_CommType_Name), "Validate_CommType_Name");
			Assert.assertEquals(BoothID, APIHelperClass.ValidationNullValue(Validate_Booth), "Validate_Booth");

		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}
}
