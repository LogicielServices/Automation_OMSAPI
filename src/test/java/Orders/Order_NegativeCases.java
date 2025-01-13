package Orders;

import APIHelper.Global;
import APIHelper.LoggingManager;
import XLDataProvider.ExcelDataProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import static APIHelper.APIHelperClass.getserializedJsonObj;
import static io.restassured.RestAssured.given;


public class Order_NegativeCases {

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
	

	 @Test (dataProvider="Order_Creation_NegativeCases", dataProviderClass=ExcelDataProvider.class , groups={"Order_Creation_NegativeCases"}, dependsOnGroups={"UserLoginAuthentications"})
	 public void Verify_Order_Creation_NegativeCases(String OrderCreation_Negative_TestCase,
													 String EndpointVersion,
													 String Order_Creation_BasePath,
													 String Content_Type ,
													 String Order_Creation_Body,
													 String Order_Creation_StatusCode,
													 String Validation_fieldname,
													 String Order_Creation_Response)

		{
			try
			{
				LoggingManager.logger.info("====================================================================");
				LoggingManager.logger.info("TestCase : "+OrderCreation_Negative_TestCase);
				LoggingManager.logger.info("====================================================================");

				RestAssured.baseURI=Global.BaseURL;
				Response response=

								 given()
								.header("Content-Type",Content_Type)
								.header("Authorization", "Bearer " + Global.getAccToken)
								.body(Order_Creation_Body)

								.when()
								.post(Order_Creation_BasePath)

								.then()
								.extract()
								.response();

				LoggingManager.logger.info("API-Order_Creation_BasePath : ["+Order_Creation_BasePath+"]");
				LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
				LoggingManager.logger.info("API-Order_Creation_Body : ["+Order_Creation_Body+"]");
				LoggingManager.logger.info("API-Order_Creation_StatusCode : ["+response.getStatusCode()+"]");
				LoggingManager.logger.info("API-Order_Creation_Response : ["+response.getBody().asString()+"]");
				LoggingManager.logger.info("API-Order_Creation_Error_Response : ["+getserializedJsonObj(response, Validation_fieldname)+"]");
				Assert.assertEquals(response.getStatusCode(), Integer.parseInt(Order_Creation_StatusCode),"Verify_Equity_Order_ResponseCode");
				Assert.assertEquals(getserializedJsonObj(response, Validation_fieldname), Order_Creation_Response,"Verify_Order_Creation_Error_Response");


			}
			catch (Exception e)
			{
				LoggingManager.logger.error(e);
			}
		}
}
