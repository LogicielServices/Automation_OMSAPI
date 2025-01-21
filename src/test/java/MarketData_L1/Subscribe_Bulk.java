package MarketData_L1;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.testng.Tag;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import APIHelper.Global;
import APIHelper.LoggingManager;

import static io.restassured.RestAssured.given;

import java.io.PrintWriter;
import java.io.StringWriter;

import XLDataProvider.ExcelDataProvider;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Subscribe_Bulk {

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
	@Description("This is Subscribe Bulk MarketData TestCase")
	@Tag("Equity MarketData")
	@Test (dataProvider="Subscribe_Bulk_Equity_MarketData", dataProviderClass=ExcelDataProvider.class,groups={"Subscribe_Bulk_Equity"} ,dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_Subscribe_Bulk_Equity_MarketData (String Subscribe_Bulk_TestCases,
														 String EndpointVersion,
														 String Subscribe_Bulk_Equity_Base_Path,
														 String Content_Type,
														 String Subscribe_Bulk_Equity_Body,
														 String Subscribe_StatusCode,
														 String Response_LocalCode_Validation,
														 String Response_Symbols_Validation,
														 String Validate_Response_Fields)
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+Subscribe_Bulk_TestCases);
			LoggingManager.logger.info("====================================================================");
			RestAssured.baseURI=Global.BaseURL;
			String ValidateLocalCodes="",ValidateSymbol="";
			Response response=  				 
								 given()	
										.header("Content-Type",Content_Type) 
										.header("Authorization", "Bearer " + Global.getAccToken)
										.body(Subscribe_Bulk_Equity_Body)
								 .when()
										.post(Subscribe_Bulk_Equity_Base_Path)
								 .then()
								 		//.statusCode(Integer.parseInt(Subscribe_StatusCode))
								 		.extract()
										.response();
			JsonPath jsonPath = new JsonPath(response.getBody().asString());
			LoggingManager.logger.info("API-Subscribe_Bulk_Equity_Base_Path : ["+Subscribe_Bulk_Equity_Base_Path+"]");
			LoggingManager.logger.info("API-Subscribe_Bulk_Equity_Body : ["+Subscribe_Bulk_Equity_Body+"]");	
			LoggingManager.logger.info("API-Subscribe_Bulk_StatusCode : ["+response.statusCode()+"]");
			switch (EndpointVersion) 
			{
				case "V3":
					ValidateLocalCodes=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(),"$.data[*].localCode").toString();
					ValidateSymbol=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(),"$.data[*].symbol").toString();
					break;
				default:
					Assert.fail("API- Kindly Provide Valid Endpoint Version");
					break;
			}
			LoggingManager.logger.info("API-Response_LocalCode_Validation : Expected "+Response_LocalCode_Validation+" - Found : "+ValidateLocalCodes);	
			LoggingManager.logger.info("API-Response_Symbols_Validation : Expected "+Response_Symbols_Validation+" - Found : "+ValidateSymbol);
			Assert.assertEquals(response.statusCode(),Integer.parseInt(Subscribe_StatusCode),"Verify_Subscribe_Bulk_StatusCode");
			Assert.assertEquals(ValidateLocalCodes,Response_LocalCode_Validation,"Verify_Response_LocalCode_Equity_MarketData");	
			Assert.assertEquals(ValidateSymbol,Response_Symbols_Validation,"Verify_Response_Symbols_Equity_MarketData");
			LoggingManager.logger.info("API-jsonPath.getInt data.size()) : ["+jsonPath.getInt("data.size()")+"]");
			for (int index = 0; index < jsonPath.getInt("data.size()"); index++) 
			{Assert.assertEquals((jsonPath.getMap("data["+index+"]").keySet()).toString(),Validate_Response_Fields,"ValidateResponseFields_Subscribe_Equity_MarketData");}
		}	
		catch (Exception e) 
		{
			LoggingManager.logger.error(e);
		}	
	}
}
