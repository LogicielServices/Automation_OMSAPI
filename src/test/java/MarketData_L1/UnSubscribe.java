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
import io.restassured.response.Response;

public class UnSubscribe {

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
	@Tag("Equity MarketData")
	@Test (dataProvider="UnSubscribe_Equity_MarketData", dataProviderClass=ExcelDataProvider.class,groups={"UnSubscribe_Equity"} ,dependsOnGroups={"Subscribe_Equity"})
	public void Verify_UnSubscribe_Equity_MarketData (String UnSubscribe_TestCases,
													  String EndpointVersion,
													  String UnSubscribe_Equity_Base_Path,
													  String Content_Type,
													  String Symbol,
													  String UnSubscribe_StatusCode,
													  String UnSubscribe_Message)
	{
		try
		{
				LoggingManager.logger.info("====================================================================");
				LoggingManager.logger.info("TestCase : "+UnSubscribe_TestCases);
				LoggingManager.logger.info("====================================================================");	
				RestAssured.baseURI=Global.BaseURL;
				Response response=  				 
									 given()	
											.header("Content-Type",Content_Type) 
											.header("Authorization", "Bearer " + Global.getAccToken)
											
									 .when()
											.get(UnSubscribe_Equity_Base_Path+Symbol)
									 .then()
									 		//.statusCode(Integer.parseInt(UnSubscribe_StatusCode))
									 		.extract()
											.response();
				
		
				LoggingManager.logger.info("API-UnSubscribe_Equity_Base_Path : ["+UnSubscribe_Equity_Base_Path+Symbol+"]");	
				LoggingManager.logger.info("API-UnSubscribe_StatusCode : ["+response.statusCode()+"]");	
				LoggingManager.logger.info("API-UnSubscribe_Message : ["+response.jsonPath().get("message")+"]");	
				Assert.assertEquals(response.statusCode(),Integer.parseInt(UnSubscribe_StatusCode),"Verify_UnSubscribe_StatusCode");
				if(EndpointVersion.equalsIgnoreCase("V1")) {Assert.assertEquals(response.getBody().asString(), UnSubscribe_Message,"Verify_OpenOrder_Response");}
				else{Assert.assertEquals(response.jsonPath().get("message"),UnSubscribe_Message,"Verify_UnSubscribe_Equity_MarketData");}
		}	
		catch (Exception e) 
		{
			LoggingManager.logger.error(e);
		}
	}
}
