package MarketData_OPT_L1;

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

public class Opt_UnSubscribe_Bulk_Symbols {

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
	
	@Test (dataProvider="UnSubscribe_Bulk_CompanySymbol_MarketData", dataProviderClass=ExcelDataProvider.class,groups={"UnSubscribe_Bulk_Symbol"} ,dependsOnGroups={"Subscribe_Bulk"})
	public void Verify_UnSubscribe_Bulk_CompanySymbol (String UnSubscribe_Bulk_Symbol_TestCases,String EndpointVersion,String UnSubscribe_Bulk_Symbol_Base_Path,String Content_Type, String Symbol,String UnSubscribe_StatusCode ,String UnSubscribe_Message)
	{
		try
		{
			
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+UnSubscribe_Bulk_Symbol_TestCases);
			LoggingManager.logger.info("====================================================================");	
			RestAssured.baseURI=Global.BaseURL;
			Response response=  				 
								 given()	
										.header("Content-Type",Content_Type) 
										.header("Authorization", "Bearer " + Global.getAccToken)
										
								 .when()
										.get(UnSubscribe_Bulk_Symbol_Base_Path+Symbol)
								 .then()
								 		//.statusCode(Integer.parseInt(UnSubscribe_StatusCode))
								 		.extract()
										.response();
			
			
			LoggingManager.logger.info("API-UnSubscribe_Bulk_CompanySymbol_MarketData_Base_Path : ["+UnSubscribe_Bulk_Symbol_Base_Path+Symbol+"]");
			LoggingManager.logger.info("API-UnSubscribe_Bulk_CompanySymbol : ["+Symbol+"]");
			LoggingManager.logger.info("API-UnSubscribe_Bulk_CompanySymbol_MarketData_StatusCode : ["+response.statusCode()+"]"); 
			LoggingManager.logger.info("API-UnSubscribe_Bulk_CompanySymbol_MarketData_ResponseBody : ["+response.getBody().asString()+"]");
			Assert.assertEquals(response.statusCode(),Integer.parseInt(UnSubscribe_StatusCode),"Verify_UnSubscribe_Bulk_CompanySymbol_StatusCode");
			Assert.assertEquals(response.getBody().asString(),UnSubscribe_Message,"Verify_UnSubscribe_Bulk_CompanySymbol");
			//Assert.assertTrue(response.getBody().asString().equalsIgnoreCase(UnSubscribe_Enqueued_Message) || response.getBody().asString().equalsIgnoreCase(UnSubscribe_Success_Message) ,"Verify_UnSubscribe_Bulk_CompanySymbol");
		}	
		catch (Exception e) 
		{
			LoggingManager.logger.error(e);
		}
	}
}
