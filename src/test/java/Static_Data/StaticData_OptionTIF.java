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


public class StaticData_OptionTIF {


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
	 
	@Test (dataProvider="StaticData_OptionTIF", dataProviderClass=ExcelDataProvider.class,groups={"StaticData_OptionTIF"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_StaticData_OptionTIF(String StaticData_OptionTIF_TestCases,
											String EndpointVersion,
											String StaticData_OptionTIF_BasePath,
											String Content_Type,
											String StaticData_OptionTIF_StatusCode,
											String Validate_OptionTIF_Name ,
											String Validate_OptionTIF_Value,
											String Validate_Booth )
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+StaticData_OptionTIF_TestCases);
			LoggingManager.logger.info("====================================================================");
			Global.getResponseArray=APIHelperClass.apiRespVersion(EndpointVersion);
			RestAssured.baseURI=Global.BaseURL;
			Response response=
					given()
							.header("Content-Type",Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)

							.when()
							.get(StaticData_OptionTIF_BasePath)

							.then()
							//.statusCode(Integer.parseInt(StaticData_OptionTIF_StatusCode))
							//.statusLine("HTTP/1.1 200 OK")
							.extract().response();

			LoggingManager.logger.info("API-Endpoint Version : [" + EndpointVersion + "]");
			LoggingManager.logger.info("API-StaticData_OptionTIF_BasePath : ["+StaticData_OptionTIF_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-StaticData_OptionTIF_StatusCode : ["+response.getStatusCode()+"]");
			Assert.assertEquals(response.getStatusCode(),Integer.parseInt(StaticData_OptionTIF_StatusCode), "Validate_StaticData_OptionTIF_StatusCode");
			String OptionTIFName=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =='"+Validate_OptionTIF_Value+"' )].name").toString();
			String OptionTIFValue=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =='"+Validate_OptionTIF_Value+"' )].value").toString();
			String BoothID=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =='"+Validate_OptionTIF_Value+"' )].booth").toString();
			LoggingManager.logger.info("API-Validate_OptionTIF_Name : "+ APIHelperClass.ValidationNullValue(Validate_OptionTIF_Name)+" - Response OptionTIFName : "+OptionTIFName);
			LoggingManager.logger.info("API-Validate_OptionTIF_Value : "+ APIHelperClass.ValidationNullValue(Validate_OptionTIF_Value)+" - Response OptionTIFValue : "+OptionTIFValue);
			LoggingManager.logger.info("API-Validate_Booth : "+APIHelperClass.ValidationNullValue(Validate_Booth)+" - Response BoothID : "+BoothID);
			Assert.assertEquals(OptionTIFValue,APIHelperClass.ValidationNullValue(Validate_OptionTIF_Value), "Validate_OptionTIF_Value");
			Assert.assertEquals(OptionTIFName,APIHelperClass.ValidationNullValue(Validate_OptionTIF_Name), "Validate_OptionTIF_Name");
			Assert.assertEquals(BoothID, APIHelperClass.ValidationNullValue(Validate_Booth),"Validate_Booth");
		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}
}
