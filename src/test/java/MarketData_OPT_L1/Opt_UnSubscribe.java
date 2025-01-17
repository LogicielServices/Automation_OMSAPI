package MarketData_OPT_L1;

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
import java.util.HashMap;
import XLDataProvider.ExcelDataProvider;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Opt_UnSubscribe {

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
	@Description("This is UnSubscribe MarketData TestCase")
	@Tag("Option MarketData")
	@Test (dataProvider="UnSubscribe_MarketData", dataProviderClass=ExcelDataProvider.class,groups={"UnSubscribe_Individual"} ,dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_UnSubscribe_Individual (String UnSubscribe_TestCases,
											   String EndpointVersion,
											   String UnSubscribe_Base_Path,
											   String Content_Type,
											   String Unsubscribe_Body,
											   String UnSubscribe_StatusCode,
											   String UnSubscribe_Message)
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+UnSubscribe_TestCases);
			LoggingManager.logger.info("====================================================================");	
			RestAssured.baseURI=Global.BaseURL;
			HashMap<Object, Object>  UnsubscribeBody=new HashMap<Object, Object>();

			UnsubscribeBody.put("optionSymbol",Unsubscribe_Body);
			Response unsubscirbe_response=
								 given()	
										.header("Content-Type",Content_Type) 
										.header("Authorization", "Bearer " + Global.getAccToken)
										.body(UnsubscribeBody)
								 .when()
										.post(UnSubscribe_Base_Path)
								 .then()
								 		//.statusCode(Integer.parseInt(UnSubscribe_StatusCode))
								 		.extract()
										.response();
			
			LoggingManager.logger.info("API-UnSubscribe_Individual_MarketData_Base_Path : ["+UnSubscribe_Base_Path+"]");
			LoggingManager.logger.info("API-UnSubscribe_Individual_MarketData_Body : ["+UnsubscribeBody+"]");
			LoggingManager.logger.info("API-UnSubscribe_Individual_MarketData_StatusCode : ["+unsubscirbe_response.statusCode()+"]");
			LoggingManager.logger.info("API-UnSubscribe_Individual_MarketData_ResponseBody : ["+unsubscirbe_response.getBody().asString()+"]");
			Assert.assertEquals(unsubscirbe_response.statusCode(),Integer.parseInt(UnSubscribe_StatusCode),"Verify_UnSubscribe_Individual_StatusCode");
			Assert.assertEquals(unsubscirbe_response.getBody().asString(),UnSubscribe_Message,"Verify_Subscribe_Individual");
		}	
		catch (Exception e) 
		{
			LoggingManager.logger.error(e);
		}
	}
}
