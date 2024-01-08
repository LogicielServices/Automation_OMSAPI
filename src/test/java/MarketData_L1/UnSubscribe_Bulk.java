package MarketData_L1;

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

public class UnSubscribe_Bulk {

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
	
	@Test (dataProvider="UnSubscribe_Bulk_Equity_MarketData", dataProviderClass=ExcelDataProvider.class,groups={"UnSubscribe_Bulk_Equity"} ,dependsOnGroups={"Subscribe_Bulk_Equity"})
	public void Verify_UnSubscribe_Bulk_Equity_MarketData (String UnSubscribe_Bulk_TestCases,
														   String EndpointVersion,
														   String UnSubscribe_Bulk_Equity_Base_Path,
														   String Content_Type,
														   String UnSubscribe_Bulk_Equity_Body,
														   String UnSubscribe_StatusCode,
														   String Response_Success_Flag_Validation,
														   String Response_Message_Validation,
														   String Response_DataValue_Validation )
	{
		try
		{
				LoggingManager.logger.info("====================================================================");
				LoggingManager.logger.info("TestCase : "+UnSubscribe_Bulk_TestCases);
				LoggingManager.logger.info("====================================================================");	
				RestAssured.baseURI=Global.BaseURL;
				String ValidateLocalCodes="",ValidateMessage="";
				Boolean ValidateSuccessMsg=false;
				Response response=  				 
									 given()	
											.header("Content-Type",Content_Type) 
											.header("Authorization", "Bearer " + Global.getAccToken)
											.body(UnSubscribe_Bulk_Equity_Body)
									 .when()
											.post(UnSubscribe_Bulk_Equity_Base_Path)
									 .then()
									 		//.statusCode(Integer.parseInt(UnSubscribe_StatusCode))
									 		.extract()
											.response();
				
				LoggingManager.logger.info("API-UnSubscribe_Bulk_Equity_Base_Path : ["+UnSubscribe_Bulk_Equity_Base_Path+"]");	
				LoggingManager.logger.info("API-UnSubscribe_Bulk_Equity_Body : ["+UnSubscribe_Bulk_Equity_Body+"]");	
				LoggingManager.logger.info("API-UnSubscribe_Bulk_StatusCode : ["+response.statusCode()+"]");
				switch (EndpointVersion) 
				{
					case "V3":
						LoggingManager.logger.info("API-UnSubscribe_Bulk_StatusCode : ["+com.jayway.jsonpath.JsonPath.read(response.getBody().asString(),"$.data.*").toString()+"]");
						ValidateLocalCodes=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(),"$.data.*").toString();
						ValidateSuccessMsg=response.jsonPath().get("success");
						ValidateMessage=response.jsonPath().get("message");
						break;
					default:
						Assert.fail("API- Kindly Provide Valid Endpoint Version");
						break;
				}
				LoggingManager.logger.info("API-Response_Success_Flag_Validation : "+Response_Success_Flag_Validation+" - Found : "+ValidateSuccessMsg.toString());	
				LoggingManager.logger.info("API-Response_Message_Validation : "+Response_Message_Validation+" - Found : "+ValidateMessage);
				LoggingManager.logger.info("API-Response_DataValue_Validation : "+Response_DataValue_Validation+" - Found: "+ValidateLocalCodes);
				Assert.assertEquals(response.statusCode(),Integer.parseInt(UnSubscribe_StatusCode),"Verify_UnSubscribe_Bulk_StatusCode");
				Assert.assertEquals(ValidateSuccessMsg.toString(),Response_Success_Flag_Validation,"Verify_UnSubscribe_Bulk_Response_Success_Flag");
				Assert.assertEquals(ValidateMessage,Response_Message_Validation,"Verify_UnSubscribe_Bulk_Response_Message");
				Assert.assertEquals(ValidateLocalCodes,Response_DataValue_Validation,"Verify_UnSubscribe_Bulk_Response_DataValue");	
		}	
		catch (Exception e) 
		{
			LoggingManager.logger.error(e);
		}
	}
}
