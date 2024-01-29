package Static_Data;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import APIHelper.Global;
import APIHelper.LoggingManager;
import XLDataProvider.ExcelDataProvider;
import static io.restassured.RestAssured.*;

import java.io.PrintWriter;
import java.io.StringWriter;


public class StaticData_Blotter_Permissions {

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
	
	 
	@Test (dataProvider="StaticData_BlotterPermissions", dataProviderClass=ExcelDataProvider.class,groups={"StaticData_BlotterPermissions"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_StaticData_BlotterPermissions( String StaticData_BlotterPermissions_TestCases,
			  										  String StaticData_BlotterPermissions_BasePath,
													  String Content_Type,
													  String StaticData_BlotterPermissions_StatusCode,
													  String Validate_BlotterPermissions_Name,
													  String Validate_BlotterPermissions_isVisible,
													  String Validate_BlotterPermissions_isEnable)
	{
		try
			{
				LoggingManager.logger.info("====================================================================");
				LoggingManager.logger.info("TestCase : "+StaticData_BlotterPermissions_TestCases);
				LoggingManager.logger.info("====================================================================");

				RestAssured.baseURI=Global.BaseURL;
				Response response=
									given()
										.header("Content-Type",Content_Type)
										.header("Authorization", "Bearer " + Global.getAccToken)

									.when()
										.get(StaticData_BlotterPermissions_BasePath)

									.then()
										//.statusCode(Integer.parseInt(StaticData_BlotterPermissions_StatusCode))
										//.statusLine("HTTP/1.1 200 OK")
										.extract().response();

				LoggingManager.logger.info("API-StaticData_BlotterPermissions_BasePath : ["+StaticData_BlotterPermissions_BasePath+"]");
				LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
				LoggingManager.logger.info("API-StaticData_BlotterPermissions_StatusCode : ["+response.getStatusCode()+"]");
				Assert.assertEquals(response.getStatusCode(),Integer.parseInt(StaticData_BlotterPermissions_StatusCode), "Validate_StaticData_BlotterPermissions_StatusCode");
				String BlotterPermissions_Name=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.permissions[?(@.name =='"+Validate_BlotterPermissions_Name+"')].name").toString();
				String BlotterPermissions_isVisible=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.permissions[?(@.name =='"+Validate_BlotterPermissions_Name+"')].isVisible").toString();
				String BlotterPermissions_isEnable=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.permissions[?(@.name =='"+Validate_BlotterPermissions_Name+"')].isEnable").toString();
				LoggingManager.logger.info("API-Validate_BlotterPermissions_Name : ["+Validate_BlotterPermissions_Name +"] - Response BlotterPermissions_Name : "+BlotterPermissions_Name);
				LoggingManager.logger.info("API-Validate_BlotterPermissions_isVisible : ["+Validate_BlotterPermissions_isVisible +"] - Response BlotterPermissions_isVisible : "+BlotterPermissions_isVisible);
				LoggingManager.logger.info("API-Validate_BlotterPermissions_isEnable : ["+Validate_BlotterPermissions_isEnable +"] - Response BlotterPermissions_isEnable : "+BlotterPermissions_isEnable);
				Assert.assertEquals(BlotterPermissions_Name,"[\""+Validate_BlotterPermissions_Name+"\"]", "Validate_BlotterPermissions_Name");
				Assert.assertEquals(BlotterPermissions_isVisible,"["+Validate_BlotterPermissions_isVisible+"]", "Validate_BlotterPermissions_isVisible");
				Assert.assertEquals(BlotterPermissions_isEnable,"["+Validate_BlotterPermissions_isEnable+"]", "Validate_BlotterPermissions_isEnable");

			}
			catch (Exception e)
			{
				LoggingManager.logger.error(e);
			}
	   }
	}
