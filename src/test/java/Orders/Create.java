package Orders;

import static io.restassured.RestAssured.given;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.graph.EndpointPair;

import APIHelper.APIHelperClass;
import APIHelper.Global;
import APIHelper.LoggingManager;
import XLDataProvider.ExcelDataProvider;
import io.restassured.RestAssured;
import io.restassured.response.Response;


public class Create {

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
	

	 @Test (dataProvider="CreateActiveEquityOrder", dataProviderClass=ExcelDataProvider.class , groups={"CreateActiveEquityOrder"}, dependsOnGroups={"UserLoginAuthentications"})
	 public void Verify_Equity_Order_Active_Creation(   String EquityOrder_TestCase,
			 												 String EndpointVersion,
															 String EquityOrder_Creation_BasePath,
															 String Content_Type ,
															 String EquityOrder_Creation_Body,
															 String EquityOrder_Creation_StatusCode,
															 String EquityOrder_Creation_Response)
		{
			
		 	LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+EquityOrder_TestCase);
			LoggingManager.logger.info("====================================================================");
			
		 	RestAssured.baseURI=Global.BaseURL;
			Response response=
				
					given()	
						.header("Content-Type",Content_Type) 
						.header("Authorization", "Bearer " + Global.getAccToken)
						.body(EquityOrder_Creation_Body)
						
					.when()
						.post(EquityOrder_Creation_BasePath)
						
					.then()
						.extract()
						.response();
			
			LoggingManager.logger.info("API-EquityOrder_Creation_BasePath : ["+EquityOrder_Creation_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-EquityOrder_Creation_Body : ["+EquityOrder_Creation_Body+"]");
			LoggingManager.logger.info("API-EquityOrder_Creation_StatusCode : ["+response.getStatusCode()+"]");
			LoggingManager.logger.info("API-EquityOrder_Response_Body : ["+response.getBody().asString()+"]");
			Assert.assertEquals(response.getStatusCode(), Integer.parseInt(EquityOrder_Creation_StatusCode),"Verify_Equity_Order_Active_Open_Creation");
			if(EndpointVersion.equalsIgnoreCase("V1")) {Assert.assertEquals(response.getBody().asString(), EquityOrder_Creation_Response,"Verify_Equity_Order_Active_Open_Response");}
			else{Assert.assertEquals(response.jsonPath().get("message"), EquityOrder_Creation_Response,"Verify_Equity_Order_Active_Open_Response");}
			
		}
	 
	
	 
	 
	
	@Test (dataProvider="CreateRejectedEquityOrder", dataProviderClass=ExcelDataProvider.class , groups={"CreateRejectedEquityOrder"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_Equity_Order_Rejected_Creation(   String EquityOrder_TestCase,
														 String EndpointVersion,
														 String EquityOrder_Creation_BasePath,
														 String Content_Type ,
														 String EquityOrder_Creation_Body,
														 String EquityOrder_Creation_StatusCode,
														 String EquityOrder_Creation_Response)
	{
		LoggingManager.logger.info("====================================================================");
		LoggingManager.logger.info("TestCase : "+EquityOrder_TestCase);
		LoggingManager.logger.info("====================================================================");
		RestAssured.baseURI=Global.BaseURL;
		Response response=
			
							given()	
								.header("Content-Type",Content_Type) 
								.header("Authorization", "Bearer " + Global.getAccToken)
								.body(EquityOrder_Creation_Body)
								
							.when()
								.post(EquityOrder_Creation_BasePath)
								
							.then()
								.extract()
								.response();
		
		LoggingManager.logger.info("API-EquityOrder_Creation_BasePath : ["+EquityOrder_Creation_BasePath+"]");
		LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
		LoggingManager.logger.info("API-EquityOrder_Creation_Body : ["+EquityOrder_Creation_Body+"]");
		LoggingManager.logger.info("API-EquityOrder_Creation_StatusCode : ["+response.getStatusCode()+"]");
		LoggingManager.logger.info("API-EquityOrder_Response_Body : ["+response.getBody().asPrettyString()+"]");
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(EquityOrder_Creation_StatusCode),"Verify_Equity_Order_Creation");
		if(EndpointVersion.equalsIgnoreCase("V1")) {Assert.assertEquals(response.getBody().asString(), EquityOrder_Creation_Response,"Verify_Equity_Order_Rejected_Response");}
		else{Assert.assertEquals(response.jsonPath().get("message"), EquityOrder_Creation_Response,"Verify_Equity_Order_Rejected_Response");}
		
			
	}
	 
}
