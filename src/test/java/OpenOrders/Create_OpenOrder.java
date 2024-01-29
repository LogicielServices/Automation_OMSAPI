package OpenOrders;

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


public class Create_OpenOrder {

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
	
	 @Test (dataProvider="CreateOpenOrder", dataProviderClass=ExcelDataProvider.class , groups={"CreateOpenOrder"}, dependsOnGroups={"UserLoginAuthentications"})
	 public void Verify_Open_Order_Creation(  String OpenOrder_TestCase,
											  String EndpointVersion,
											  String OpenOrder_Creation_BasePath,
											  String Content_Type ,
											  String OpenOrder_Creation_Body,
											  String OpenOrder_Creation_StatusCode,
											  String OpenOrder_Creation_Response)
		{
			try
			{
				LoggingManager.logger.info("====================================================================");
				LoggingManager.logger.info("TestCase : "+OpenOrder_TestCase);
				LoggingManager.logger.info("====================================================================");

				RestAssured.baseURI=Global.BaseURL;
				Response response=

						given()
								.header("Content-Type",Content_Type)
								.header("Authorization", "Bearer " + Global.getAccToken)
								.body(OpenOrder_Creation_Body)

								.when()
								.post(OpenOrder_Creation_BasePath)

								.then()
								.extract()
								.response();

				LoggingManager.logger.info("API-OpenOrder_Creation_BasePath : ["+OpenOrder_Creation_BasePath+"]");
				LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
				LoggingManager.logger.info("API-OpenOrder_Creation_Body : ["+OpenOrder_Creation_Body+"]");
				LoggingManager.logger.info("API-OpenOrder_Creation_StatusCode : ["+response.getStatusCode()+"]");
				LoggingManager.logger.info("API-OpenOrder_Response_Body : ["+response.getBody().asString()+"]");
				Assert.assertEquals(response.getStatusCode(), Integer.parseInt(OpenOrder_Creation_StatusCode),"Verify_Open_Order_Creation");
				if(EndpointVersion.equalsIgnoreCase("V1")) {Assert.assertEquals(response.getBody().asString(), OpenOrder_Creation_Response,"Verify_OpenOrder_Response");}
				else{Assert.assertEquals(response.jsonPath().get("message"), OpenOrder_Creation_Response,"Verify_Open_Order_Creation_Response");}

			}
			catch (Exception e)
			{
				LoggingManager.logger.error(e);
			}
		}
}
