package MarketData_L1;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.testng.Tag;
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

public class Subscribe_MarketData {

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
	@Description("This is Subscribe MarketData TestCase")
	@Tag("Equity MarketData")
	@Test (dataProvider="Subscribe_Equity_MarketData", dataProviderClass=ExcelDataProvider.class,groups={"Subscribe_Equity"} ,dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_Subscribe_Equity_MarketData (String Subscribe_TestCases,
													String EndpointVersion,
													String Subscribe_Equity_Base_Path,
													String Content_Type,
													String Symbol,
													String Subscribe_StatusCode,
													String Validate_LocalCode,
													String Validate_Symbol,
													String Validate_Response_Fields)
	{	
		try
		{
				LoggingManager.logger.info("====================================================================");
				LoggingManager.logger.info("TestCase : "+Subscribe_TestCases);
				LoggingManager.logger.info("====================================================================");	
				RestAssured.baseURI=Global.BaseURL;
				String ValidateLocalCodes="",ValidateSymbol="";
				Response response=  				 
									 given()	
											.header("Content-Type",Content_Type) 
											.header("Authorization", "Bearer " + Global.getAccToken)
											.pathParam("symbol",Symbol)
										
												
									 .when()
											.get(Subscribe_Equity_Base_Path.concat("{symbol}"))
									 .then()
									 		//.statusCode(Integer.parseInt(Subscribe_StatusCode))
									 		.extract()
											.response();
				
				JsonPath jsonPath = new JsonPath(response.getBody().asString());
				LoggingManager.logger.info("API-Subscribe_Equity_Base_Path : ["+Subscribe_Equity_Base_Path+Symbol+"]");	
				LoggingManager.logger.info("API-Subscribe_StatusCode : ["+response.statusCode()+"]");	
				LoggingManager.logger.info("API-Subscribe_ResponseBody : ["+response.getBody().asString()+"]");
			    switch (EndpointVersion) 
				{
					case "V1":
						ValidateLocalCodes=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(),"$.localCode").toString();
						ValidateSymbol=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(),"$.symbol").toString();
						break;
					case "V3":
						ValidateLocalCodes=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(),"$.data.localCode").toString();
						ValidateSymbol=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(),"$.data.symbol").toString();
						break;
					default:
						Assert.fail("API- Kindly Provide Valid Endpoint Version");
						break;
				}
				LoggingManager.logger.info("API-Validate_LocalCode_Subscribe : Expected ["+Validate_LocalCode+"] - Found : ["+ValidateLocalCodes+"]");	
				LoggingManager.logger.info("API-Validate_Symbol_Subscribe : Expected ["+Validate_Symbol+"] - Found : ["+ValidateSymbol+"]");
				LoggingManager.logger.info("API-Validate_Response_Fields : Expected ["+Validate_Response_Fields+"] - Found : ["+(jsonPath.getMap("data").keySet()).toString()+"]");
				Assert.assertEquals(response.statusCode(),Integer.parseInt(Subscribe_StatusCode),"Verify_Subscribe_StatusCode");
				Assert.assertEquals(ValidateLocalCodes,Validate_LocalCode,"ValidateLocalCodes_Subscribe_Equity_MarketData");	
				Assert.assertEquals(ValidateSymbol,Validate_Symbol,"ValidateSymbol_Subscribe_Equity_MarketData");
				Assert.assertEquals((jsonPath.getMap("data").keySet()).toString(),Validate_Response_Fields,"ValidateResponseFields_Subscribe_Equity_MarketData");
		}	
		catch (Exception e) 
		{
			LoggingManager.logger.error(e);
		}
	}
}
