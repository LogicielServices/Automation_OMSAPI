package Static_Data;

import APIHelper.APIHelperClass;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import APIHelper.Global;
import APIHelper.LoggingManager;
import XLDataProvider.ExcelDataProvider;
import static io.restassured.RestAssured.*;
import java.io.PrintWriter;
import java.io.StringWriter;



public class StaticData_Account {

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
	
	@Test (dataProvider="StaticData_Account", dataProviderClass=ExcelDataProvider.class,groups={"StaticData_Account"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_StaticData_Account(String StaticData_Account_TestCases,
										  String EndpointVersion,
										  String StaticData_Account_BasePath,
										  String Content_Type,
										  String StaticData_Account_StatusCode,
										  String Validate_isBoxVsShort,
										  String Validate_Account_Name ,
										  String Validate_Account_Value,
										  String Validate_Booth )
	{
		try {
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : " + StaticData_Account_TestCases);
			LoggingManager.logger.info("====================================================================");
			Global.getResponseArray=APIHelperClass.apiRespVersion(EndpointVersion);
			RestAssured.baseURI = Global.BaseURL;
			Response response =
					given()
							.header("Content-Type", Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)

							.when()
							.get(StaticData_Account_BasePath)

							.then()
							//.statusCode(Integer.parseInt(StaticData_Account_StatusCode))
							//.statusLine("HTTP/1.1 200 OK")
							.extract().response();

			LoggingManager.logger.info("API-Endpoint Version : [" + EndpointVersion + "]");
			LoggingManager.logger.info("API-StaticData_Account_BasePath : [" + StaticData_Account_BasePath + "]");
			LoggingManager.logger.info("API-Content_Type : [" + Content_Type + "]");
			LoggingManager.logger.info("API-StaticData_Account_StatusCode : [" + response.getStatusCode() + "]");
			Assert.assertEquals(response.getStatusCode(), Integer.parseInt(StaticData_Account_StatusCode), "Validate_StaticData_Account_StatusCode");
			//String isBoxVsShort = com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =="+ Validate_isBoxVsShort + ")].isBoxVsShort").toString();
			//String isBoxVsShort = com.jayway.jsonpath.JsonPath.read(response.getBody().asString(),"$.data.eventData[0].isBoxVsShort").toString();

			String isBoxVsShort = com.jayway.jsonpath.JsonPath.read(response.getBody().asString(),"$.data.eventData[?(@.value =='" + Validate_Account_Value +"' )].isBoxVsShort").toString();

			//$.data.eventData[?(@.isBoxVsShort == " + Validate_isBoxVsShort + ")].isBoxVsShort[0]"

			String AccountName = com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =='" + Validate_Account_Value + "' )].name").toString();
			String AccountValue = com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =='" + Validate_Account_Value + "' )].value").toString();
			String BoothID = com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =='" + Validate_Account_Value + "' )].booth").toString();
			LoggingManager.logger.info("API-Validate_isBoxVsShort : "+APIHelperClass.ValidationNullValue(Validate_isBoxVsShort)+" - Response isBoxVsShort Value : " + isBoxVsShort);
			LoggingManager.logger.info("API-Validate_Account_Value : "+APIHelperClass.ValidationNullValue(Validate_Account_Value)+" - Response Account Value : " + AccountValue);
			LoggingManager.logger.info("API-Validate_Account_Name : "+APIHelperClass.ValidationNullValue(Validate_Account_Name)+" - Response Account Name : " + AccountName);
			LoggingManager.logger.info("API-Validate_Booth :"+APIHelperClass.ValidationNullValue(Validate_Booth)+" - Response Booth : " + BoothID);
			Assert.assertEquals(isBoxVsShort,APIHelperClass.ValidationNullValue(Validate_isBoxVsShort), "Validate_isBoxVsShort");
			Assert.assertEquals(AccountValue,APIHelperClass.ValidationNullValue(Validate_Account_Value), "Validate_Account_Value");
			Assert.assertEquals(AccountName,APIHelperClass.ValidationNullValue(Validate_Account_Name), "Validate_Account_Name");
			Assert.assertEquals(BoothID,APIHelperClass.ValidationNullValue(Validate_Booth), "Validate_Booth");
		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}
}
