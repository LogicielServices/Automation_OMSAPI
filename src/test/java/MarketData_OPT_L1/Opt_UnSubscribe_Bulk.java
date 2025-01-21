package MarketData_OPT_L1;

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

import static APIHelper.APIHelperClass.getserializedJsonObj;
import static io.restassured.RestAssured.given;

import java.io.PrintWriter;
import java.io.StringWriter;

import XLDataProvider.ExcelDataProvider;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import javax.swing.*;

public class Opt_UnSubscribe_Bulk {

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
	@Description("This is UnSubscribe Bulk MarketData TestCase")
	@Tag("Option MarketData")
	@Test (dataProvider="UnSubscribe_Bulk_MarketData", dataProviderClass=ExcelDataProvider.class,groups={"UnSubscribe_Bulk"} ,dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_UnSubscribe_Bulk (String UnSubscribe_Bulk_TestCases,
										 String EndpointVersion,
										 String UnSubscribe_Bulk_Base_Path,
										 String Content_Type,
										 String Unsubscribe_Bulk_Body,
										 String UnSubscribe_StatusCode,
										 String UnSubscribe_Message)
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+UnSubscribe_Bulk_TestCases);
			LoggingManager.logger.info("====================================================================");	
			RestAssured.baseURI=Global.BaseURL;

			String  UnsubscribeBody="{\n\"OptionSymbols\": "+Unsubscribe_Bulk_Body+"\n}";
			Response unsubscribeResponse=
						given()
								.header("Content-Type",Content_Type)
								.header("Authorization", "Bearer " + Global.getAccToken)
								.body(UnsubscribeBody)
								.when()
								.post(UnSubscribe_Bulk_Base_Path)
								.then()
								//.statusCode(Integer.parseInt(UnSubscribe_StatusCode))
								.extract()
								.response();

				LoggingManager.logger.info("API-UnSubscribe_Bulk_MarketData_Base_Path : ["+UnSubscribe_Bulk_Base_Path+"]");
				LoggingManager.logger.info("API-UnSubscribe_Bulk_MarketData_Body : ["+UnsubscribeBody+"]");
				LoggingManager.logger.info("API-UnSubscribe_Bulk_MarketData_StatusCode : ["+unsubscribeResponse.statusCode()+"]");
				LoggingManager.logger.info("API-UnSubscribe_Bulk_MarketData_ResponseBody : ["+unsubscribeResponse.getBody().asString()+"]");
				Assert.assertEquals(unsubscribeResponse.statusCode(),Integer.parseInt(UnSubscribe_StatusCode),"Verify_UnSubscribe_Bulk_StatusCode");
				Assert.assertEquals(unsubscribeResponse.getBody().asString(),UnSubscribe_Message,"Verify_UnSubscribe_Bulk");

		}	
		catch (Exception e) 
		{
			LoggingManager.logger.error(e);
		}
	}
}
