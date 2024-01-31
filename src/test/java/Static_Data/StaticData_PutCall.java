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


public class StaticData_PutCall {


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
	 
	@Test (dataProvider="StaticData_PutCall", dataProviderClass=ExcelDataProvider.class,groups={"StaticData_PutCall"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_StaticData_PutCall(String StaticData_PutCall_TestCases,
										  String EndpointVersion,
										  String StaticData_PutCall_BasePath,
										  String Content_Type,
										  String StaticData_PutCall_StatusCode,
										  String Validate_PutCall_Name ,
										  String Validate_PutCall_Value,
										  String Validate_Booth )
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+StaticData_PutCall_TestCases);
			LoggingManager.logger.info("====================================================================");
			Global.getResponseArray=APIHelperClass.apiRespVersion(EndpointVersion);
			RestAssured.baseURI=Global.BaseURL;
			Response response=
					given()
							.header("Content-Type",Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)

							.when()
							.get(StaticData_PutCall_BasePath)

							.then()
							//.statusCode(Integer.parseInt(StaticData_PutCall_StatusCode))
							//.statusLine("HTTP/1.1 200 OK")
							.extract().response();

			LoggingManager.logger.info("API-Endpoint Version : [" + EndpointVersion + "]");
			LoggingManager.logger.info("API-StaticData_PutCall_BasePath : ["+StaticData_PutCall_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-StaticData_PutCall_StatusCode : ["+response.getStatusCode()+"]");
			Assert.assertEquals(response.getStatusCode(),Integer.parseInt(StaticData_PutCall_StatusCode), "Validate_StaticData_PutCall_StatusCode");
			String PutCallName=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =='"+Validate_PutCall_Value+"')].name").toString();
			String PutCallValue=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =='"+Validate_PutCall_Value+"')].value").toString();
			String BoothID=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =='"+Validate_PutCall_Value+"' )].booth").toString();
			LoggingManager.logger.info("API-Validate_PutCall_Name : "+ APIHelperClass.ValidationNullValue(Validate_PutCall_Name)+" - Response PutCallName : "+PutCallName);
			LoggingManager.logger.info("API-Validate_PutCall_Value : "+ APIHelperClass.ValidationNullValue(Validate_PutCall_Value)+" - Response PutCallValue : "+PutCallValue);
			LoggingManager.logger.info("API-Validate_Booth : "+APIHelperClass.ValidationNullValue(Validate_Booth)+" - Response BoothID : "+BoothID);
			Assert.assertEquals(PutCallValue,APIHelperClass.ValidationNullValue(Validate_PutCall_Value),"Validate_PutCall_Value");
			Assert.assertEquals(PutCallName,APIHelperClass.ValidationNullValue(Validate_PutCall_Name), "Validate_PutCall_Name");
			Assert.assertEquals(BoothID,APIHelperClass.ValidationNullValue(Validate_Booth),"Validate_Booth");
		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}
}
