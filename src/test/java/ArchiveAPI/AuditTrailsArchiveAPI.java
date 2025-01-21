package ArchiveAPI;

import APIHelper.Global;
import APIHelper.LoggingManager;
import XLDataProvider.ExcelDataProvider;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.testng.Tag;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class AuditTrailsArchiveAPI {

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
	 @Description("This is  Archive Audittrail TestCase")
	 @Tag("Archive Endpoints")
	 @Test (dataProvider="GetArchiveAudittrail", dataProviderClass=ExcelDataProvider.class , groups={"ArchiveAudittrail"}, dependsOnGroups={"ArchiveLogin"})
	 public void Verify_Get_ArchiveAudittrail(   String ArchiveAudittrail_TestCase,
														String EndpointVersion,
														String ArchiveAudittrail_Path,
														String Content_Type ,
														String ArchiveAudittrail_Body,
														String ArchiveAudittrail_StatusCode,
														String Validate_Response_Fields)
		{
			try
			{
				LoggingManager.logger.info("====================================================================");
				LoggingManager.logger.info("TestCase : "+ArchiveAudittrail_TestCase);
				LoggingManager.logger.info("====================================================================");

				RestAssured.baseURI=Global.BaseURL;
				Response response=

								 given()
								.header("Content-Type",Content_Type)
								.header("Authorization", "Bearer " + Global.getArchive_AccToken)
								.body(ArchiveAudittrail_Body)

								.when()
								.post(ArchiveAudittrail_Path)

								.then()
								.extract()
								.response();

				Map<String, Object> jsonResponse = response.jsonPath().get();
				LoggingManager.logger.info("API-ArchiveAudittrail_Path : ["+ArchiveAudittrail_Path+"]");
				LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
				LoggingManager.logger.info("API-ArchiveAudittrail_Body : ["+ArchiveAudittrail_Body+"]");
				LoggingManager.logger.info("API-ArchiveAudittrail_StatusCode : ["+response.getStatusCode()+"]");
				LoggingManager.logger.info("API-Validate_Response_Fields : "+(jsonResponse.keySet()).toString());
				Assert.assertEquals(response.getStatusCode(), Integer.parseInt(ArchiveAudittrail_StatusCode),"Verify_Archive_GetAudittrails_StatusCode");
				if(EndpointVersion.equalsIgnoreCase("V1")) {Assert.assertEquals((jsonResponse.keySet()).toString(), Validate_Response_Fields,"Verify_Response_Fields");}
				//else{Assert.assertEquals(response.jsonPath().get("message"), Validate_Response_Fields,"Verify_Response_Fields");}
			}
			catch (Exception e)
			{
				LoggingManager.logger.error(e);
			}
		}
}
