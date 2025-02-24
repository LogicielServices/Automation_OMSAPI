package OptionOrders;

import APIHelper.Global;
import APIHelper.LoggingManager;
import XLDataProvider.ExcelDataProvider;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static io.restassured.RestAssured.given;

//Test(groups = {"Create"},dependsOnGroups={"UserLoginAuthentications"})
public class Option_Order_UnSubscribe {

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
	 @Test (dataProvider="UnSubscribeOptionOrder", dataProviderClass=ExcelDataProvider.class , groups={"UnSubscribeOptionOrder"}, dependsOnGroups={"UserLoginAuthentications"})
	 public void Verify_UnSubscribe_OptionOrder( String TestCases,String EndpointVersion,String UnSubscribe_OptionOrder_BasePath,
												 String Content_Type,
											     String UnSubscribe_OptionOrder_StatusCode,													 
											     String UnSubscribe_Validation_Message)
	 {
	 	try
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
							 .get(UnSubscribe_OptionOrder_BasePath)

							 .then()
							 .statusCode(Integer.parseInt(UnSubscribe_OptionOrder_StatusCode))
							 .statusLine("HTTP/1.1 200 OK")
							 .extract().response();

			 LoggingManager.logger.info("API - UnSubscribe OptionOrder BasePath : ["+UnSubscribe_OptionOrder_BasePath+"]");
			 LoggingManager.logger.info("API - UnSubscribe OptionOrder Content_Type : ["+Content_Type+"]");
			 LoggingManager.logger.info("API - UnSubscribe OptionOrder StatusCode : ["+UnSubscribe_OptionOrder_StatusCode+"]");
			 LoggingManager.logger.info("API - UnSubscribe OptionOrder Validation Message : ["+response.getBody().asString()+"]");
			 Assert.assertEquals(response.getBody().asString(), UnSubscribe_Validation_Message,"Validate_UnSubscribeOptionOrder");
			 //LoggingManager.logger.info("API - UnSubscribe OptionOrder Validation Message : ["+response.jsonPath().get("message")+"]");
			 //if(EndpointVersion.equalsIgnoreCase("V1")) {Assert.assertEquals(response.getBody().asString(), UnSubscribe_Validation_Message,"Validate_UnSubscribeOptionOrder");}
			 //else{Assert.assertEquals(response.jsonPath().get("message"),UnSubscribe_Validation_Message, "Validate_UnSubscribeOptionOrder");}


		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	 }

}
