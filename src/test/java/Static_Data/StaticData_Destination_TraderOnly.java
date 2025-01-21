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

public class StaticData_Destination_TraderOnly {


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
	@Description("This is StaticData_Destination_TraderOnly TestCase")
	@Tag("StaticData")
	@Test (dataProvider="StaticData_Destination_TraderOnly", dataProviderClass=ExcelDataProvider.class,groups={"StaticData_Destination_TraderOnly"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_StaticData_Destination_TraderOnly( String StaticData_Destination_TraderOnly_TestCases,
														  String EndpointVersion,
														  String StaticData_Destination_TraderOnly_BasePath,
														  String Content_Type,
														  String StaticData_Destination_TraderOnly_StatusCode,
														  String Validate_Destination_TraderOnly_Name ,
														  String Validate_Destination_TraderOnly_Value,
														  String Validate_Booth )
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+StaticData_Destination_TraderOnly_TestCases);
			LoggingManager.logger.info("====================================================================");
			Global.getResponseArray=APIHelperClass.apiRespVersion(EndpointVersion);
			RestAssured.baseURI=Global.BaseURL;
			Response response=
					given()
							.header("Content-Type",Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)

							.when()
							.get(StaticData_Destination_TraderOnly_BasePath)

							.then()
							//.statusCode(Integer.parseInt(StaticData_Destination_TraderOnly_StatusCode))
							//.statusLine("HTTP/1.1 200 OK")
							.extract().response();

			LoggingManager.logger.info("API-Endpoint Version : [" + EndpointVersion + "]");
			LoggingManager.logger.info("API-StaticData_Destination_TraderOnly_BasePath : ["+StaticData_Destination_TraderOnly_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-StaticData_Destination_TraderOnly_StatusCode : ["+response.getStatusCode()+"]");
			Assert.assertEquals(response.getStatusCode(),Integer.parseInt(StaticData_Destination_TraderOnly_StatusCode), "Validate_StaticData_Destination_TraderOnly_StatusCode");
			String DestinationTraderOnlyName=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =='"+Validate_Destination_TraderOnly_Value.substring(1, Validate_Destination_TraderOnly_Value.length() - 1)+"' )].name").toString();
			String DestinationTraderOnlyValue=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =='"+Validate_Destination_TraderOnly_Value.substring(1, Validate_Destination_TraderOnly_Value.length() - 1)+"' )].value").toString();
			String BoothID=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =='"+Validate_Destination_TraderOnly_Value.substring(1, Validate_Destination_TraderOnly_Value.length() - 1)+"' )].booth").toString();
			LoggingManager.logger.info("API-Validate_Destination_TraderOnly_Name : "+ APIHelperClass.ValidationNullValue(Validate_Destination_TraderOnly_Name)+" - Response DestinationTraderOnlyName : "+DestinationTraderOnlyName);
			LoggingManager.logger.info("API-Validate_Destination_TraderOnly_Value : ["+Validate_Destination_TraderOnly_Value+"] - Response DestinationTraderOnlyValue : "+DestinationTraderOnlyValue);
			LoggingManager.logger.info("API-Validate_Booth : "+APIHelperClass.ValidationNullValue(Validate_Booth)+" - Response BoothID : "+BoothID);
			Assert.assertEquals(DestinationTraderOnlyValue,"["+Validate_Destination_TraderOnly_Value+"]", "Validate_Destination_TraderOnly_Value");
			Assert.assertEquals(DestinationTraderOnlyName,APIHelperClass.ValidationNullValue(Validate_Destination_TraderOnly_Name), "Validate_Destination_TraderOnly_Name");
			Assert.assertEquals(BoothID, APIHelperClass.ValidationNullValue(Validate_Booth),"Validate_Booth");

		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}
}
