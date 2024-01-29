package OptionOrders;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import APIHelper.APIHelperClass;
import APIHelper.Global;
import APIHelper.LoggingManager;
import XLDataProvider.ExcelDataProvider;
import static io.restassured.RestAssured.*;

import java.io.PrintWriter;
import java.io.StringWriter;

//@Test(groups = {"Create_Option"},dependsOnGroups={"UserLoginAuthentications"})
public class Create_OptionOrder  
{
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
	
	@Test (dataProvider="OptionOrderCreation_ActiveOpen", dataProviderClass=ExcelDataProvider.class, groups={"CreateActive_OpenOptionOrder"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_Order_Creation_Active_Status ( String OptionOrder_Creation_TestCase,
														   String Endpoint_Version,
														   String OptionOrder_Creation_BasePath,
														   String Content_Type ,
														   String OptionOrder_Creation_Body,
														   String OptionOrder_Creation_StatusCode,
														   String OptionOrder_Creation_Response)
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+OptionOrder_Creation_TestCase);
			LoggingManager.logger.info("====================================================================");
			RestAssured.baseURI=Global.BaseURL;
			Response response=
					given()
							.header("Content-Type",Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)
							.body(OptionOrder_Creation_Body)
							.when()
							.post(OptionOrder_Creation_BasePath)
							.then()
							//.statusCode(Integer.parseInt(OptionOrder_Creation_StatusCode))
							//.statusLine("HTTP/1.1 200 OK")
							.extract().response();

			LoggingManager.logger.info("API-OptionOrder_Creation_BasePath : ["+OptionOrder_Creation_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-OptionOrder_Creation_Body : ["+OptionOrder_Creation_Body+"]");
			LoggingManager.logger.info("API-OptionOrder_Creation_StatusCode : ["+response.getStatusCode()+"]");
			LoggingManager.logger.info("API-OptionOrder_Response_Body : ["+response.getBody().asPrettyString()+"]");
			Assert.assertEquals(response.getStatusCode(),Integer.parseInt(OptionOrder_Creation_StatusCode),"Order_Creation_Active_Open");
			if(Endpoint_Version.equalsIgnoreCase("V1")) {Assert.assertEquals(response.getBody().asString(), OptionOrder_Creation_Response,"Verify_OptionOrder_Active_Open_Response");}
			else{Assert.assertEquals(response.jsonPath().get("message"), OptionOrder_Creation_Response,"Verify_OptionOrder_Active_Open_Response");}

		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
}
	
	
	
	@Test (dataProvider="OptionOrderCreation_Rejected", dataProviderClass=ExcelDataProvider.class, groups={"CreateRejectedOptionOrder"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_Order_Creation_Rejected_Status(String OptionOrder_Creation_TestCase,
			   										  String Endpoint_Version,
													  String OptionOrder_Creation_BasePath,
													  String Content_Type ,
													  String OptionOrder_Creation_Body,
													  String OptionOrder_Creation_StatusCode,
													  String OptionOrder_Creation_Response)
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+OptionOrder_Creation_TestCase);
			LoggingManager.logger.info("====================================================================");
			RestAssured.baseURI=Global.BaseURL;
			Response response=
					given()
							.header("Content-Type",Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)
							.body(OptionOrder_Creation_Body)
							.when()
							.post(OptionOrder_Creation_BasePath)
							.then()
							.extract().response();

			LoggingManager.logger.info("API-OptionOrder_Creation_BasePath : ["+OptionOrder_Creation_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-OptionOrder_Creation_Body : ["+OptionOrder_Creation_Body+"]");
			LoggingManager.logger.info("API-OptionOrder_Creation_StatusCode : ["+response.getStatusCode()+"]");
			LoggingManager.logger.info("API-OptionOrder_Response_Body : ["+response.getBody().asPrettyString()+"]");
			Assert.assertEquals(response.getStatusCode(),Integer.parseInt(OptionOrder_Creation_StatusCode),"Verify_Order_Creation_Rejected_Status");
			if(Endpoint_Version.equalsIgnoreCase("V1")) {Assert.assertEquals(response.getBody().asString(), OptionOrder_Creation_Response,"Verify_Order_Creation_Rejected_Response");}
			else{Assert.assertEquals(response.jsonPath().get("message"), OptionOrder_Creation_Response,"Verify_Order_Creation_Rejected_Response");}
		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}
}
	
	
	

