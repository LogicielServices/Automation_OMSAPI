package Orders;

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
public class Equity_Order_UnSubscribe {

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
	@Test (dataProvider="UnSubscribeEquityOrder", dataProviderClass=ExcelDataProvider.class , groups={"UnSubscribeEquityOrder"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_UnSubscribe_EquityOrder( String TestCases,String EndpointVersion,String UnSubscribe_EquityOrder_BasePath,
												 String Content_Type,
											     String UnSubscribe_EquityOrder_StatusCode,													 
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
							.get(UnSubscribe_EquityOrder_BasePath)

							.then()
							.statusCode(Integer.parseInt(UnSubscribe_EquityOrder_StatusCode))
							.statusLine("HTTP/1.1 200 OK")
							.extract().response();

			LoggingManager.logger.info("API - UnSubscribe EquityOrder BasePath : ["+UnSubscribe_EquityOrder_BasePath+"]");
			LoggingManager.logger.info("API - UnSubscribe EquityOrder Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API - UnSubscribe EquityOrder StatusCode : ["+UnSubscribe_EquityOrder_StatusCode+"]");
			LoggingManager.logger.info("API - UnSubscribe EquityOrder Validation Message : ["+response.jsonPath().get("message")+"]");
			if(EndpointVersion.equalsIgnoreCase("V1")) {Assert.assertEquals(response.getBody().asString(), UnSubscribe_Validation_Message,"Validate_UnSubscribeEquityOrder");}
			else{Assert.assertEquals(response.jsonPath().get("message"),UnSubscribe_Validation_Message, "Validate_UnSubscribeEquityOrder");}

		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}

}
