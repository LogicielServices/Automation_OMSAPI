package ArchiveAPI;

import static io.restassured.RestAssured.given;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.testng.Tag;
import io.restassured.path.json.JsonPath;
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


public class OrdersArchiveAPI {

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

	@Owner("api.automation@mailinator.com")
	@Description("This is  Archive Order TestCase")
	@Tag("Archive Endpoints")
	@Test (dataProvider="GetArchiveOrder", dataProviderClass=ExcelDataProvider.class , groups={"ArchiveOrder"}, dependsOnGroups={"ArchiveLogin"})
	 public void Verify_Get_ArchiveOrder(   String ArchiveOrder_TestCase,
														String EndpointVersion,
														String ArchiveOrder_Path,
														String Content_Type ,
														String ArchiveOrder_Body,
														String ArchiveOrder_StatusCode,
														String Validate_Response_Fields)
		{
			try
			{
				LoggingManager.logger.info("====================================================================");
				LoggingManager.logger.info("TestCase : "+ArchiveOrder_TestCase);
				LoggingManager.logger.info("====================================================================");

				RestAssured.baseURI=Global.BaseURL;
				Response response=

								 given()
								.header("Content-Type",Content_Type)
								.header("Authorization", "Bearer " + Global.getArchive_AccToken)
								.body(ArchiveOrder_Body)

								.when()
								.post(ArchiveOrder_Path)

								.then()
								.extract()
								.response();

				//JsonPath jsonPath = new JsonPath(response.getBody().asString());
				Map<String, Object> jsonResponse = response.jsonPath().get();
				LoggingManager.logger.info("API-ArchiveOrder_Path : ["+ArchiveOrder_Path+"]");
				LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
				LoggingManager.logger.info("API-ArchiveOrder_Body : ["+ArchiveOrder_Body+"]");
				LoggingManager.logger.info("API-ArchiveOrder_StatusCode : ["+response.getStatusCode()+"]");
				LoggingManager.logger.info("API-Validate_Response_Fields : "+(jsonResponse.keySet()).toString());
				Assert.assertEquals(response.getStatusCode(), Integer.parseInt(ArchiveOrder_StatusCode),"Verify_Archive_GetOrders_StatusCode");
				if(EndpointVersion.equalsIgnoreCase("V1")) {Assert.assertEquals((jsonResponse.keySet()).toString(), Validate_Response_Fields,"Verify_Response_Fields");}
				//else{Assert.assertEquals(response.jsonPath().get("message"), Validate_Response_Fields,"Verify_Response_Fields");}
				//Assert.assertEquals((jsonPath.getMap("data").keySet()).toString(),Validate_Response_Fields,"Verify_Archive_GetOrders_ResponseFields");

			}
			catch (Exception e)
			{
				LoggingManager.logger.error(e);
			}
		}
}
