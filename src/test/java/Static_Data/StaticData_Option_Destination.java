package Static_Data;

import APIHelper.APIHelperClass;
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


public class StaticData_Option_Destination {


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
	 
	@Test (dataProvider="StaticData_Option_Destination", dataProviderClass=ExcelDataProvider.class,groups={"StaticData_Option_Destination"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_StaticData_Option_Destination(String StaticData_OptionDestination_TestCases,
													 String EndpointVersion,
													 String StaticData_OptionDestination_BasePath, 
													 String Content_Type, 
													 String StaticData_OptionDestination_StatusCode, 
													 String Validate_OptionDestination_Name , 
													 String Validate_OptionDestination_Value,
													 String Validate_OptionDestination_Account_Value,
													 String Validate_Booth )
	{
		try
		{

			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+StaticData_OptionDestination_TestCases);
			LoggingManager.logger.info("====================================================================");
			Global.getResponseArray=APIHelperClass.apiRespVersion(EndpointVersion);
			RestAssured.baseURI=Global.BaseURL;
			Response response=
							given()
							.header("Content-Type",Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)

							.when()
							.get(StaticData_OptionDestination_BasePath)

							.then()
							.statusCode(Integer.parseInt(StaticData_OptionDestination_StatusCode))
							//.statusLine("HTTP/1.1 200 OK")
							.extract().response();


			String DestinationName=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =='"+Validate_OptionDestination_Value.substring(1, Validate_OptionDestination_Value.length() - 1)+"' )].name").toString();
			String DestinationValue=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =='"+Validate_OptionDestination_Value.substring(1, Validate_OptionDestination_Value.length() - 1)+"' )].value").toString();
			//String DestinationAccountValue=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =='"+Validate_OptionDestination_Value.substring(1, Validate_OptionDestination_Value.length() - 1)+"' )].account").toString();
			String BoothID=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.value =='"+Validate_OptionDestination_Value.substring(1, Validate_OptionDestination_Value.length() - 1)+"' )].booth").toString();
			LoggingManager.logger.info("API-Endpoint Version : [" + EndpointVersion + "]");
			LoggingManager.logger.info("API-StaticData_OptionDestination_BasePath : ["+StaticData_OptionDestination_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-StaticData_OptionDestination_StatusCode : ["+response.getStatusCode()+"]");
			LoggingManager.logger.info("API-Validate_OptionDestination_Name : "+APIHelperClass.ValidationNullValue(Validate_OptionDestination_Name)+" - Response DestinationName : "+DestinationName);
			LoggingManager.logger.info("API-Validate_OptionDestination_Value : ["+Validate_OptionDestination_Value+"] - Response DestinationValue : "+DestinationValue);
			//LoggingManager.logger.info("API-Validate_OptionDestination_Account_Value : ["+Validate_OptionDestination_Account_Value +"] - Response DestinationAccountValue : "+DestinationAccountValue);
			LoggingManager.logger.info("API-Validate_Booth :"+APIHelperClass.ValidationNullValue(Validate_Booth)+" - Response Booth : " + BoothID);
			Assert.assertEquals(DestinationName,APIHelperClass.ValidationNullValue(Validate_OptionDestination_Name), "Validate_OptionDestination_Name");
			Assert.assertEquals(DestinationValue,"["+Validate_OptionDestination_Value+"]", "Validate_OptionDestination_Value");
			//Assert.assertEquals(DestinationAccountValue,APIHelperClass.ValidationNullValue(Validate_OptionDestination_Account_Value), "Validate_OptionDestination_Account_Value");
			Assert.assertEquals(BoothID, APIHelperClass.ValidationNullValue(Validate_Booth), "Validate_Booth");
		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}

	}

}
