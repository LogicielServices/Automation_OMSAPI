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


public class StaticData_OptionOrderType {


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
	 
	@Test (dataProvider="StaticData_OptionOrderType", dataProviderClass=ExcelDataProvider.class,groups={"StaticData_OptionOrderType"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_StaticData_OptionOrderType(String StaticData_OptionOrderType_TestCases,
												  String EndpointVersion,
												  String StaticData_OptionOrderType_BasePath,
												  String Content_Type,
												  String StaticData_OptionOrderType_StatusCode,
												  String Validate_OptionOrderType_Name ,
												  String Validate_OptionOrderType_Value,
												  String Validate_Booth )
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+StaticData_OptionOrderType_TestCases);
			LoggingManager.logger.info("====================================================================");
			Global.getResponseArray=APIHelperClass.apiRespVersion(EndpointVersion);
			RestAssured.baseURI=Global.BaseURL;
			Response response=
					given()
							.header("Content-Type",Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)

							.when()
							.get(StaticData_OptionOrderType_BasePath)

							.then()
							//.statusCode(Integer.parseInt(StaticData_OptionOrderType_StatusCode))
							//.statusLine("HTTP/1.1 200 OK")
							.extract().response();

			LoggingManager.logger.info("API-Endpoint Version : [" + EndpointVersion + "]");
			LoggingManager.logger.info("API-StaticData_OptionOrderType_BasePath : ["+StaticData_OptionOrderType_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-StaticData_OptionOrderType_StatusCode : ["+response.getStatusCode()+"]");
			Assert.assertEquals(response.getStatusCode(),Integer.parseInt(StaticData_OptionOrderType_StatusCode), "Validate_StaticData_OptionOrderType_StatusCode");
			String OptionOrderTypeName=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =='"+Validate_OptionOrderType_Value+"')].name").toString();
			String OptionOrderTypeValue=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =='"+Validate_OptionOrderType_Value+"')].value").toString();
			String BoothID=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =='"+Validate_OptionOrderType_Value+"')].booth").toString();
			LoggingManager.logger.info("API-Validate_OptionOrderType_Name : "+APIHelperClass.ValidationNullValue(Validate_OptionOrderType_Name)+" - Response OptionOrderTypeName : "+OptionOrderTypeName);
			LoggingManager.logger.info("API-Validate_OptionOrderType_Value : "+APIHelperClass.ValidationNullValue(Validate_OptionOrderType_Value)+" - Response OptionOrderTypeValue : "+OptionOrderTypeValue);
			LoggingManager.logger.info("API-Validate_Booth : "+APIHelperClass.ValidationNullValue(Validate_Booth)+" - Response BoothID : "+BoothID);
			Assert.assertEquals(OptionOrderTypeValue,APIHelperClass.ValidationNullValue(Validate_OptionOrderType_Value), "Validate_OptionOrderType_Value");
			Assert.assertEquals(OptionOrderTypeName,APIHelperClass.ValidationNullValue(Validate_OptionOrderType_Name), "Validate_OptionOrderType_Name");
			Assert.assertEquals(BoothID, APIHelperClass.ValidationNullValue(Validate_Booth),"Validate_Booth");
		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}
}
