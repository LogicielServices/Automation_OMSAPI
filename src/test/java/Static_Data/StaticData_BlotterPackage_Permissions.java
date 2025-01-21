package Static_Data;

import APIHelper.APIHelperClass;
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
import APIHelper.Global;
import APIHelper.LoggingManager;
import XLDataProvider.ExcelDataProvider;
import static io.restassured.RestAssured.*;

import java.io.PrintWriter;
import java.io.StringWriter;


public class StaticData_BlotterPackage_Permissions {


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
	@Description("This is StaticData_BlotterPackage_Permissions TestCase")
	@Tag("StaticData")
	@Test (dataProvider="StaticData_BlotterPackage_Permissions", dataProviderClass=ExcelDataProvider.class,groups={"StaticData_BlotterPackage_Permissions"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_StaticData_BlotterPackage_Permissions( String StaticData_BlotterPackage_Permissions_TestCases,
															  String EndpointVersion,
			  												  String StaticData_BlotterPackage_Permissions_BasePath,
															  String Content_Type,
															  String StaticData_BlotterPackage_Permissions_StatusCode,
															  String Validate_BlotterPermissions_Package ,
															  String Validate_isWindowAllowed,
															  String Validate_NumberOfWindowsAllowed,
															  String Validate_WindowPermissions_Name,
															  String Validate_WindowPermissions_isVisible,
															  String Validate_WindowPermissions_isEnable)
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+StaticData_BlotterPackage_Permissions_TestCases);
			LoggingManager.logger.info("====================================================================");
			RestAssured.baseURI=Global.BaseURL;
			Response response=
							given()
							.header("Content-Type",Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)

							.when()
							.get(StaticData_BlotterPackage_Permissions_BasePath)

							.then()
							//.statusCode(Integer.parseInt(StaticData_BlotterPackage_Permissions_StatusCode))
							//.statusLine("HTTP/1.1 200 OK")
							.extract().response();

			LoggingManager.logger.info("API-Endpoint Version : [" + EndpointVersion + "]");
			LoggingManager.logger.info("API-StaticData_BlotterPackage_Permissions_BasePath : ["+StaticData_BlotterPackage_Permissions_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-StaticData_BlotterPackage_Permissions_StatusCode : ["+response.getStatusCode()+"]");
			Assert.assertEquals(response.getStatusCode(),Integer.parseInt(StaticData_BlotterPackage_Permissions_StatusCode), "Validate_StaticData_BlotterPackage_Permissions_StatusCode");
			//String BlotterPermissionsPackage=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.package."+Validate_BlotterPermissions_Package).toString();
			String BlotterPackage_isWindowAllowed=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.package."+Validate_BlotterPermissions_Package+".isWindowAllowed").toString();
			String BlotterPackage_NumberOfWindowsAllowed=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.package."+Validate_BlotterPermissions_Package+".numberOfWindowsAllowed").toString();
			String BlotterPackage_WindowPermissions_Name=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.package."+Validate_BlotterPermissions_Package+".windowPermissions[?(@.name =='"+Validate_WindowPermissions_Name+"')].name").toString();
			String BlotterPackage_WindowPermissions_isVisible=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.package."+Validate_BlotterPermissions_Package+".windowPermissions[?(@.name =='"+Validate_WindowPermissions_Name+"')].isVisible").toString();
			String BlotterPackage_WindowPermissions_isEnable=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.package."+Validate_BlotterPermissions_Package+".windowPermissions[?(@.name =='"+Validate_WindowPermissions_Name+"')].isEnable").toString();
			LoggingManager.logger.info("API-Validate_isWindowAllowed : ["+Validate_isWindowAllowed +"] - Response BlotterPackage_isWindowAllowed : "+BlotterPackage_isWindowAllowed);
			LoggingManager.logger.info("API-Validate_NumberOfWindowsAllowed : ["+Validate_NumberOfWindowsAllowed +"] - Response BlotterPackage_NumberOfWindowsAllowed : "+BlotterPackage_NumberOfWindowsAllowed);
			LoggingManager.logger.info("API-Validate_WindowPermissions_Name : ["+Validate_WindowPermissions_Name +"] - Response BlotterPackage_WindowPermissions_Name : "+BlotterPackage_WindowPermissions_Name);
			LoggingManager.logger.info("API-Validate_WindowPermissions_isVisible : ["+Validate_WindowPermissions_isVisible +"] - Response BlotterPackage_WindowPermissions_isVisible : "+BlotterPackage_WindowPermissions_isVisible);
			LoggingManager.logger.info("API-Validate_WindowPermissions_isEnable : ["+Validate_WindowPermissions_isEnable +"] - Response BlotterPackage_WindowPermissions_isEnable : "+BlotterPackage_WindowPermissions_isEnable);
			if (Validate_WindowPermissions_Name.equalsIgnoreCase("null"))
			{
				Assert.assertEquals(BlotterPackage_isWindowAllowed,Validate_isWindowAllowed, "Validate_BlotterPackage_isWindowAllowed");
				Assert.assertEquals(BlotterPackage_NumberOfWindowsAllowed,Validate_NumberOfWindowsAllowed, "Validate_BlotterPackage_NumberOfWindowsAllowed");
			}
			else
			{
				Assert.assertEquals(BlotterPackage_isWindowAllowed,Validate_isWindowAllowed, "Validate_BlotterPackage_isWindowAllowed");
				Assert.assertEquals(BlotterPackage_NumberOfWindowsAllowed,Validate_NumberOfWindowsAllowed, "Validate_BlotterPackage_NumberOfWindowsAllowed");
				Assert.assertEquals(BlotterPackage_WindowPermissions_Name,APIHelperClass.ValidationNullValue(Validate_WindowPermissions_Name), "Validate_BlotterPackage__WindowPermissions_Name");
				Assert.assertEquals(BlotterPackage_WindowPermissions_isVisible,"["+Validate_WindowPermissions_isVisible+"]", "Validate_BlotterPackage_WindowPermissions_isVisible");
				Assert.assertEquals(BlotterPackage_WindowPermissions_isEnable,"["+Validate_WindowPermissions_isEnable+"]", "Validate_BlotterPackage_WindowPermissions_isEnable");
			}

		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}
}
