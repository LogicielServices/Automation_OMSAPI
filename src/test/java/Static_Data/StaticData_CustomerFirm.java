package Static_Data;

import APIHelper.APIHelperClass;
import APIHelper.Global;
import APIHelper.LoggingManager;
import XLDataProvider.ExcelDataProvider;
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

import java.io.PrintWriter;
import java.io.StringWriter;

import static io.restassured.RestAssured.given;


public class StaticData_CustomerFirm {


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
	@Description("This is StaticData_CustomerFirm TestCase")
	@Tag("StaticData")
	@Test (dataProvider="StaticData_CustomerFirm", dataProviderClass=ExcelDataProvider.class,groups={"StaticData_CustomerFirm"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_StaticData_CustomerFirm(String StaticData_CustomerFirm_TestCases,
											   String EndpointVersion,
			  								   String StaticData_CustomerFirm_BasePath,
											   String Content_Type,
											   String StaticData_CustomerFirm_StatusCode,
											   String Validate_CustomerFirm_Name ,
											   String Validate_CustomerFirm_Value,
											   String Validate_Booth )
	{
		try {
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : " + StaticData_CustomerFirm_TestCases);
			LoggingManager.logger.info("====================================================================");
			Global.getResponseArray=APIHelperClass.apiRespVersion(EndpointVersion);
			RestAssured.baseURI = Global.BaseURL;
			Response response =
					given()
							.header("Content-Type", Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)

							.when()
							.get(StaticData_CustomerFirm_BasePath)

							.then()
							//.statusCode(Integer.parseInt(StaticData_CustomerFirm_StatusCode))
							//.statusLine("HTTP/1.1 200 OK")
							.extract().response();

			LoggingManager.logger.info("API-Endpoint Version : [" + EndpointVersion + "]");
			LoggingManager.logger.info("API-StaticData_CustomerFirm_BasePath : [" + StaticData_CustomerFirm_BasePath + "]");
			LoggingManager.logger.info("API-Content_Type : [" + Content_Type + "]");
			LoggingManager.logger.info("API-StaticData_CustomerFirm_StatusCode : [" + response.getStatusCode() + "]");
			Assert.assertEquals(response.getStatusCode(), Integer.parseInt(StaticData_CustomerFirm_StatusCode), "Validate_StaticData_CustomerFirm_StatusCode");
			String CustomerFirmName = com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =='" + Validate_CustomerFirm_Value + "' )].name").toString();
			String CustomerFirmValue = com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =='" + Validate_CustomerFirm_Value + "' )].value").toString();
			String BoothID = com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =='" + Validate_CustomerFirm_Value + "' )].booth").toString();
			LoggingManager.logger.info("API-Validate_CustomerFirm_Name : " +APIHelperClass.ValidationNullValue(Validate_CustomerFirm_Name) +" - Response CustomerFirmName : " + CustomerFirmName);
			LoggingManager.logger.info("API-Validate_CustomerFirm_Value : " +APIHelperClass.ValidationNullValue(Validate_CustomerFirm_Value)+" - Response CustomerFirmValue : " + CustomerFirmValue);
			LoggingManager.logger.info("API-Validate_Booth : "+APIHelperClass.ValidationNullValue(Validate_Booth)+" - Response BoothID : "+BoothID);
			Assert.assertEquals(CustomerFirmValue, APIHelperClass.ValidationNullValue(Validate_CustomerFirm_Value), "Validate_CustomerFirm_Value");
			Assert.assertEquals(CustomerFirmName, APIHelperClass.ValidationNullValue(Validate_CustomerFirm_Name), "Validate_CustomerFirm_Name");
			Assert.assertEquals(BoothID, APIHelperClass.ValidationNullValue(Validate_Booth),"Validate_Booth");
		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}
}
