package MarketData_L1;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import APIHelper.Global;
import APIHelper.LoggingManager;

import static APIHelper.APIHelperClass.getserializedJsonObj;
import static io.restassured.RestAssured.given;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

import XLDataProvider.ExcelDataProvider;
import io.restassured.RestAssured;
import io.restassured.response.Response;


public class CompanyInformation {
   
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
	
	@Test (dataProvider="CompanyInformation_MarketData", dataProviderClass=ExcelDataProvider.class,groups={"UnSubscribe_Equity"} ,dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_CompanyInformation_MarketData (String CompanyInformation_TestCases,
													  String EndpointVersion,
													  String CompanyInformation_Base_Path,
													  String Content_Type,
													  String CompanyInformation_Symbol,
													  String CompanyInformation_StatusCode,
													  String Symbol_Validation_CompanyInformation,
													  String CountryCode_Validation_CompanyInformation,
													  String Currency_Validation_CompanyInformation,
													  String PrimaryTicker_Validation_CompanyInformation,
													  String Validate_Response_Fields,
													  String Error_Response)
	{
		 try
		 {
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+CompanyInformation_TestCases);
			LoggingManager.logger.info("====================================================================");
			
			RestAssured.baseURI=Global.BaseURL;
			Response response=  				 
								 given()	
										.header("Content-Type",Content_Type) 
										.header("Authorization", "Bearer " + Global.getAccToken)
										//.pathParam("codeId",Global.getCodeID)
										.pathParam("symbol",CompanyInformation_Symbol)
																			
								 .when()
										.get(CompanyInformation_Base_Path.concat("{symbol}"))
								 .then()
								 		//.body("ttmSharesOutstanding", isA(Long.class))
								 		.extract()
										.response();
			
			//JsonPath jsonPath = new JsonPath(response.getBody().asString());
			//assertThat(jsonPath.get("peRatio") instanceof Float).isTrue();
			JSONObject jsonObject=(JSONObject) JSONValue.parse(response.getBody().asString());//convert Object into JSONObject 
			LoggingManager.logger.info("API-CompanyInformation_Base_Path : ["+CompanyInformation_Base_Path+CompanyInformation_Symbol+"]");
			LoggingManager.logger.info("API-CompanyInformation_StatusCode : ["+response.statusCode()+"]");	
			LoggingManager.logger.info("API-CompanyInformation_StatusCode : Expected ["+response.jsonPath().get("symbol")+"] - Found ["+Symbol_Validation_CompanyInformation+"]");
			Assert.assertEquals(response.statusCode(),Integer.parseInt(CompanyInformation_StatusCode),"Verify_CompanyInformation_StatusCode");

			 if ((CompanyInformation_Symbol.toString()).equalsIgnoreCase("null") || CompanyInformation_Symbol.isEmpty()|| CompanyInformation_Symbol.isBlank())
			 {
				 Assert.assertEquals(getserializedJsonObj(response, "errors"),Error_Response,"Validation_CompanyInformation_Error_Response");
			 }
			 else
			 {
				 Assert.assertEquals(response.jsonPath().get("symbol"),Symbol_Validation_CompanyInformation,"Symbol_Validation_CompanyInformation");
				 Assert.assertEquals(getserializedJsonObj(response, "countryCode"),CountryCode_Validation_CompanyInformation,"CountryCode_Validation_CompanyInformation");
				 Assert.assertEquals(getserializedJsonObj(response, "currency"),Currency_Validation_CompanyInformation,"Currency_Validation_CompanyInformation");
				 Assert.assertEquals(getserializedJsonObj(response, "primaryTicker"),PrimaryTicker_Validation_CompanyInformation,"PrimaryTicker_Validation_CompanyInformation");
				 Assert.assertEquals((jsonObject.keySet()).toString(),Validate_Response_Fields,"Verify_CompanyInformation_MarketData_Response_Fields");

			 }

		}	
		catch (Exception e) 
		{
			LoggingManager.logger.error(e);
		}	
	}
}
