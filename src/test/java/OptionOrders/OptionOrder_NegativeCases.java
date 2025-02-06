package OptionOrders;

import APIHelper.APIHelperClass;
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

import static APIHelper.APIHelperClass.getserializedJsonObj;
import static io.restassured.RestAssured.given;

//@Test(groups = {"Create_Option"},dependsOnGroups={"UserLoginAuthentications"})
public class OptionOrder_NegativeCases
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
	
	@Test (dataProvider="OptionOrder_NegativeCases", dataProviderClass=ExcelDataProvider.class, groups={"OptionOrder_NegativeCases"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_OptionOrder_NegativeCases( String OptionOrder_Subscription_NegativeTestCase,
														   String Endpoint_Version,
														   String OptionOrder_Creation_BasePath,
														   String Content_Type ,
														   String OptionOrder_Creation_Body,
														   String OptionOrder_Creation_StatusCode,
														   String Validation_Fieldname,
														   String OptionOrder_Error_Response)
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+OptionOrder_Subscription_NegativeTestCase);
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
			Assert.assertEquals(response.getStatusCode(),Integer.parseInt(OptionOrder_Creation_StatusCode),"Option_Order_Creation_NegativeCase_ResponseCode");
			LoggingManager.logger.info("API-OptionOrder_Creation_Error_Response : ["+getserializedJsonObj(response, Validation_Fieldname)+"]");
			if(Endpoint_Version.equalsIgnoreCase("V1")) {Assert.assertEquals(response.getBody().asString(), OptionOrder_Error_Response,"Verify_OptionOrder_Response");}
			else{Assert.assertEquals(getserializedJsonObj(response, Validation_Fieldname), OptionOrder_Error_Response,"Verify_OptionOrder_Creation_Error_Response");}


		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
}
	

}
	
	
	

