package Subscriptions;

import static io.restassured.RestAssured.given;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import APIHelper.Global;
import APIHelper.LoggingManager;
import XLDataProvider.ExcelDataProvider;
import io.restassured.RestAssured;
import io.restassured.response.Response;

//Test(groups = {"Create"},dependsOnGroups={"UserLoginAuthentications"})
public class Order_Positions_UnSubscribe {
	
		
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
	 
	 @Test (dataProvider="UnSubscribeOrder_Positions", dataProviderClass=ExcelDataProvider.class , groups={"UnSubscribeOrder_Positions"}, dependsOnGroups={"UserLoginAuthentications"})
	 public void Verify_UnSubscribe_Order_Positions( String TestCases,String EndpointVersion,
			 										 String UnSubscribe_Order_Positions_BasePath,
													 String Content_Type,
												     String UnSubscribe_Order_Positions_StatusCode,													 
												     String UnSubscribe_Validation_Message)
		{

		 	LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+TestCases);
			LoggingManager.logger.info("====================================================================");
		    RestAssured.baseURI=Global.BaseURL;
			Response response=
								given()	
									.header("Content-Type",Content_Type) 
									.header("Authorization", "Bearer " + Global.getAccToken)
									
								.when()
									.get(UnSubscribe_Order_Positions_BasePath)
									
								.then()
									.statusCode(Integer.parseInt(UnSubscribe_Order_Positions_StatusCode))
									//.statusLine("HTTP/1.1 200 OK")
									.extract().response();
			LoggingManager.logger.info("API - UnSubscribe Order Position BasePath : ["+UnSubscribe_Order_Positions_BasePath+"]");
			LoggingManager.logger.info("API - UnSubscribe Order Position Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API - UnSubscribe Order Position StatusCode : ["+UnSubscribe_Order_Positions_StatusCode+"]");
			LoggingManager.logger.info("API - UnSubscribe Order Position Validation Message : ["+response.jsonPath().get("message")+"]");
			if(EndpointVersion.equalsIgnoreCase("V1")) {Assert.assertEquals(response.getBody().asString(), UnSubscribe_Validation_Message,"Validate_UnSubscribeOrder_Positions");}
			else{Assert.assertEquals(response.jsonPath().get("message"),UnSubscribe_Validation_Message, "Validate_UnSubscribeOrder_Positions");}
			
			
   
				
		}
	 
	 
	
}
