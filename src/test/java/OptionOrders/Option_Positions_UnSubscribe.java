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
public class Option_Positions_UnSubscribe {
	
	
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
	 
	 @Test (dataProvider="UnSubscribe_Option_Positions", dataProviderClass=ExcelDataProvider.class , groups={"UnSubscribe_Option_Positions"}, dependsOnGroups={"UserLoginAuthentications"})
	 public void Verify_UnSubscribe_Option_Positions(String TestCases,String EndpointVersion,String UnSubscribe_Option_Positions_BasePath,
													 String Content_Type,
												     String UnSubscribe_Option_Positions_StatusCode,													 
												     String UnSubscribe_Validation_Message )
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
							 .get(UnSubscribe_Option_Positions_BasePath)

							 .then()
							 .statusCode(Integer.parseInt(UnSubscribe_Option_Positions_StatusCode))
							 //.statusLine("HTTP/1.1 200 OK")
							 .extract().response();

			 LoggingManager.logger.info("API - UnSubscribe Option Position BasePath : ["+UnSubscribe_Option_Positions_BasePath+"]");
			 LoggingManager.logger.info("API - UnSubscribe Option Position Content_Type : ["+Content_Type+"]");
			 LoggingManager.logger.info("API - UnSubscribe Option Position StatusCode : ["+UnSubscribe_Option_Positions_StatusCode+"]");
			 LoggingManager.logger.info("API - UnSubscribe Option Position Validation Message : ["+response.jsonPath().get("message")+"]");
			 if(EndpointVersion.equalsIgnoreCase("V1")) {Assert.assertEquals(response.getBody().asString(), UnSubscribe_Validation_Message,"Validate_UnSubscribe_Option_Positions");}
			 else{Assert.assertEquals(response.jsonPath().get("message"),UnSubscribe_Validation_Message, "Validate_UnSubscribe_Option_Positions");}


		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	 }

}
