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


public class StaticData_Latest_Destination {


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
	 
	@Test (dataProvider="StaticData_Option_Equity_Destination", dataProviderClass=ExcelDataProvider.class,groups={"StaticData_Option_Equity_Destination"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_StaticData_Option_Equity_Destination(String StaticData_Equity_Option_Destination_TestCases,
															  String EndpointVersion,
															  String StaticData_Equity_Option_Destination_BasePath,
															  String Content_Type,
															  String StaticData_Equity_Option_Destination_StatusCode,
															  String Validate_Equity_Option_Destination_Name ,
															  String Validate_Equity_Option_Destination_Value,
															  String Validate_Equity_Option_Destination_Accounts,
															  String Validate_Equity_Option_Destination_isEquity,
															  String Validate_Equity_Option_Destination_isOptions,
															  String Validate_Equity_Option_Destination_isComplex,
															  String Validate_Equity_Option_Destination_isFractional,
															  String Validate_Booth )
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+StaticData_Equity_Option_Destination_TestCases);
			LoggingManager.logger.info("====================================================================");
			Global.getResponseArray=APIHelperClass.apiRespVersion(EndpointVersion);
			RestAssured.baseURI=Global.BaseURL;
			Response response=
					given()
							.header("Content-Type",Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)

							.when()
							.get(StaticData_Equity_Option_Destination_BasePath)

							.then()
							//.statusCode(Integer.parseInt(StaticData_Equity_Option_Destination_StatusCode))
							//.statusLine("HTTP/1.1 200 OK")
							.extract().response();

			LoggingManager.logger.info("API-Endpoint Version : [" + EndpointVersion + "]");
			LoggingManager.logger.info("API-StaticData_Equity_Option_Destination_BasePath : ["+StaticData_Equity_Option_Destination_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-StaticData_Equity_Option_Destination_StatusCode : ["+response.getStatusCode()+"]");
			Assert.assertEquals(response.getStatusCode(),Integer.parseInt(StaticData_Equity_Option_Destination_StatusCode), "Validate_StaticData_Equity_Option_Destination_StatusCode");
			String Equity_Option_DestinationName=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =='"+Validate_Equity_Option_Destination_Value.substring(1, Validate_Equity_Option_Destination_Value.length() - 1)+"' )].name").toString();
			String Equity_Option_DestinationValue=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =='"+Validate_Equity_Option_Destination_Value.substring(1, Validate_Equity_Option_Destination_Value.length() - 1)+"' )].value").toString();
			String Equity_Option_DestinationAccountValue=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =='"+Validate_Equity_Option_Destination_Value.substring(1, Validate_Equity_Option_Destination_Value.length() - 1)+"' )].accounts").toString();

			String Equity_Option_Destination_isEquity=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =='"+Validate_Equity_Option_Destination_Value.substring(1, Validate_Equity_Option_Destination_Value.length() - 1)+"' )].isEquity").toString();
			String Equity_Option_Destination_isOptions=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =='"+Validate_Equity_Option_Destination_Value.substring(1, Validate_Equity_Option_Destination_Value.length() - 1)+"' )].isOptions").toString();
			String Equity_Option_Destination_isComplex=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =='"+Validate_Equity_Option_Destination_Value.substring(1, Validate_Equity_Option_Destination_Value.length() - 1)+"' )].isComplex").toString();
			String Equity_Option_Destination_isFractional=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =='"+Validate_Equity_Option_Destination_Value.substring(1, Validate_Equity_Option_Destination_Value.length() - 1)+"' )].isFractional").toString();


			String BoothID=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =='"+Validate_Equity_Option_Destination_Value.substring(1, Validate_Equity_Option_Destination_Value.length() - 1)+"' )].booth").toString();
			LoggingManager.logger.info("API-Validate_Equity_Option_Destination_Name : "+APIHelperClass.ValidationNullValue(Validate_Equity_Option_Destination_Name) +" - Response Equity_Option_DestinationName : "+Equity_Option_DestinationName);
			LoggingManager.logger.info("API-Validate_Equity_Option_Destination_Value : ["+Validate_Equity_Option_Destination_Value+"] - Response Equity_Option_DestinationValue : "+Equity_Option_DestinationValue);
			LoggingManager.logger.info("API-Validate_Equity_Option_Destination_Account_Value : "+Validate_Equity_Option_Destination_Accounts+" - Response Equity_Option_DestinationAccountValue : "+Equity_Option_DestinationAccountValue);
			LoggingManager.logger.info("API-Validate_Equity_Option_Destination_isEquity : "+APIHelperClass.ValidationNullValue(Validate_Equity_Option_Destination_isEquity)+" - Response Equity_Option_Destination_isEquity : "+Equity_Option_Destination_isEquity);
			LoggingManager.logger.info("API-Validate_Equity_Option_Destination_isOptions : "+APIHelperClass.ValidationNullValue(Validate_Equity_Option_Destination_isOptions)+" - Response Equity_Option_Destination_isOptions : "+Equity_Option_Destination_isOptions);
			LoggingManager.logger.info("API-Validate_Equity_Option_Destination_isComplex : "+APIHelperClass.ValidationNullValue(Validate_Equity_Option_Destination_isComplex)+" - Response Equity_Option_Destination_isComplex : "+Equity_Option_Destination_isComplex);
			LoggingManager.logger.info("API-Validate_Equity_Option_Destination_isFractional : "+APIHelperClass.ValidationNullValue(Validate_Equity_Option_Destination_isFractional)+" - Response Equity_Option_Destination_isFractional : "+Equity_Option_Destination_isFractional);

			LoggingManager.logger.info("API-Validate_Booth :"+Validate_Booth+" - Response Booth : " + BoothID);

			Assert.assertEquals(Equity_Option_DestinationName,APIHelperClass.ValidationNullValue(Validate_Equity_Option_Destination_Name), "Validate_Equity_Option_Destination_Name");
			Assert.assertEquals(Equity_Option_DestinationValue,"["+Validate_Equity_Option_Destination_Value+"]", "Validate_Equity_Option_Destination_Value");
			Assert.assertEquals(Equity_Option_DestinationAccountValue,"["+Validate_Equity_Option_Destination_Accounts+"]","Validate_Equity_Option_Destination_Account_Value");

			Assert.assertEquals(Equity_Option_Destination_isEquity,APIHelperClass.ValidationNullValue(Validate_Equity_Option_Destination_isEquity), "Validate_Equity_Option_Destination_isEquity");
			Assert.assertEquals(Equity_Option_Destination_isOptions,APIHelperClass.ValidationNullValue(Validate_Equity_Option_Destination_isOptions), "Validate_Equity_Option_Destination_isOptions");
			Assert.assertEquals(Equity_Option_Destination_isComplex,APIHelperClass.ValidationNullValue(Validate_Equity_Option_Destination_isComplex), "Validate_Equity_Option_Destination_isComplex");
			Assert.assertEquals(Equity_Option_Destination_isFractional,APIHelperClass.ValidationNullValue(Validate_Equity_Option_Destination_isFractional), "Validate_Equity_Option_Destination_isFractional");

			Assert.assertEquals(BoothID,Validate_Booth, "Validate_Booth");
		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}
}
