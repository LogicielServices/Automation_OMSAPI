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
public class Open_Order_UnSubscribe {

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
	 @Test (dataProvider="UnSubscribeOpenOrder", dataProviderClass=ExcelDataProvider.class , groups={"UnSubscribeOpenOrder"}, dependsOnGroups={"UserLoginAuthentications"})
	 public void Verify_UnSubscribe_OpenOrder( String TestCases,String EndpointVersion,String UnSubscribe_OpenOrder_BasePath,
												 String Content_Type,
											     String UnSubscribe_OpenOrder_StatusCode,													 
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
									.get(UnSubscribe_OpenOrder_BasePath)
									
								.then()
									.statusCode(Integer.parseInt(UnSubscribe_OpenOrder_StatusCode))
									.statusLine("HTTP/1.1 200 OK")
									.extract().response();
			
			LoggingManager.logger.info("API - UnSubscribe OpenOrder BasePath : ["+UnSubscribe_OpenOrder_BasePath+"]");
			LoggingManager.logger.info("API - UnSubscribe OpenOrder Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API - UnSubscribe OpenOrder StatusCode : ["+UnSubscribe_OpenOrder_StatusCode+"]");
			LoggingManager.logger.info("API - UnSubscribe OpenOrder Validation Message : ["+response.jsonPath().get("message")+"]");
			if(EndpointVersion.equalsIgnoreCase("V1")) {Assert.assertEquals(response.getBody().asString(), UnSubscribe_Validation_Message,"Validate_UnSubscribeOpenOrder");}
			else{Assert.assertEquals(response.jsonPath().get("message"),UnSubscribe_Validation_Message, "Validate_UnSubscribeOpenOrder");}

				
		}

}
