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


public class StaticData_ETBHTB {


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
	@Description("This is StaticData_ETBHTB TestCase")
	@Tag("StaticData")
	@Test (dataProvider="StaticData_ETBHTB", dataProviderClass=ExcelDataProvider.class,groups={"StaticData_ETBHTB"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_StaticData_ETBHTB( String StaticData_ETBHTB_TestCases,
										  String EndpointVersion,
			  							  String StaticData_ETBHTB_BasePath,
										  String Content_Type,
										  String StaticData_ETBHTB_StatusCode,
										  String Empty_Data_Validation,
										  String Validate_ETBHTB_BoothID,
										  String Validate_ETBHTB_Symbol,
										  String Validate_ETBHTB_SymbolDesc,
										  String Validate_ETBHTB_LocationID,
										  String Validate_ETBHTB_ContactName,
										  String Validate_ETBHTB_OrigQuantity,
										  String Validate_ETBHTB_Quantity,
										  String Validate_ETBHTB_EtbListType,
										  String Validate_ETBHTB_ID )
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+StaticData_ETBHTB_TestCases);
			LoggingManager.logger.info("====================================================================");
			Global.getResponseArray=APIHelperClass.apiRespVersion(EndpointVersion);
			RestAssured.baseURI=Global.BaseURL;
			Response response=
					given()
							.header("Content-Type",Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)

							.when()
							.get(StaticData_ETBHTB_BasePath)

							.then()
							.statusCode(Integer.parseInt(StaticData_ETBHTB_StatusCode))
							//.statusLine("HTTP/1.1 200 OK")
							.extract().response();

			String ETBHTB_Data=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(),"$.data.eventData").toString();
			LoggingManager.logger.info("API-Endpoint Version : [" + EndpointVersion + "]");
			LoggingManager.logger.info("API-StaticData_ETBHTB_BasePath : ["+StaticData_ETBHTB_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-StaticData_ETBHTB_StatusCode : ["+response.getStatusCode()+"]");
			LoggingManager.logger.info("API-StaticData_ETBHTB_Response: [" + com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.data.eventData") + "]");

			if(ETBHTB_Data.equalsIgnoreCase("[]"))
			{
				Assert.assertEquals(ETBHTB_Data,Empty_Data_Validation, "Validate_Empty_Data_Response");
			}
			else
			{

				String ETBHTB_BoothID=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.id =='"+Validate_ETBHTB_ID+"' )].boothID").toString();
				String ETBHTB_Symbol=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.id =='"+Validate_ETBHTB_ID+"' )].symbol").toString();
				String ETBHTB_SymbolDesc=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.id =='"+Validate_ETBHTB_ID+"' )].symbolDesc").toString();
				String ETBHTB_LocationID=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.id =='"+Validate_ETBHTB_ID+"' )].locationID").toString();
				String ETBHTB_ContactName=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.id =='"+Validate_ETBHTB_ID+"' )].contactName").toString();
				String ETBHTB_OrigQuantity=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.id =='"+Validate_ETBHTB_ID+"' )].origQuantity").toString();
				String ETBHTB_Quantity=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.id =='"+Validate_ETBHTB_ID+"' )].quantity").toString();
				String ETBHTB_EtbListType=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.id =='"+Validate_ETBHTB_ID+"' )].etbListType").toString();
				String ETBHTB_ID=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$."+Global.getResponseArray+"[?(@.id =='"+Validate_ETBHTB_ID+"' )].id").toString();

				LoggingManager.logger.info("API-Validate_ETBHTB_BoothID : "+APIHelperClass.ValidationNullValue(Validate_ETBHTB_BoothID)+" - Response ETBHTB_BoothID : "+ETBHTB_BoothID);
				LoggingManager.logger.info("API-Validate_ETBHTB_Symbol : ["+Validate_ETBHTB_Symbol +"] - Response ETBHTB_Symbol : "+ETBHTB_Symbol);
				LoggingManager.logger.info("API-Validate_ETBHTB_SymbolDesc : ["+Validate_ETBHTB_SymbolDesc +"] - Response ETBHTB_SymbolDesc : "+ETBHTB_SymbolDesc);
				LoggingManager.logger.info("API-Validate_ETBHTB_LocationID : ["+Validate_ETBHTB_LocationID +"] - Response ETBHTB_LocationID : "+ETBHTB_LocationID);
				LoggingManager.logger.info("API-Validate_ETBHTB_ContactName : ["+Validate_ETBHTB_ContactName +"] - Response ETBHTB_ContactName : "+ETBHTB_ContactName);
				LoggingManager.logger.info("API-Validate_ETBHTB_OrigQuantity : ["+Validate_ETBHTB_OrigQuantity +"] - Response ETBHTB_OrigQuantity : "+ETBHTB_OrigQuantity);
				LoggingManager.logger.info("API-Validate_ETBHTB_Quantity : ["+Validate_ETBHTB_Quantity +"] - Response ETBHTB_Quantity : "+ETBHTB_Quantity);
				LoggingManager.logger.info("API-Validate_ETBHTB_EtbListType : ["+Validate_ETBHTB_EtbListType +"] - Response ETBHTB_EtbListType : "+ETBHTB_EtbListType);
				LoggingManager.logger.info("API-Validate_ETBHTB_ID : ["+Validate_ETBHTB_ID +"] - Response ETBHTB_ID : "+ETBHTB_ID);

				Assert.assertEquals(ETBHTB_BoothID, APIHelperClass.ValidationNullValue(Validate_ETBHTB_BoothID),"Validate_ETBHTB_BoothID");
				Assert.assertEquals(ETBHTB_Symbol,APIHelperClass.ValidationNullValue(Validate_ETBHTB_Symbol), "Validate_ETBHTB_Symbol");
				Assert.assertEquals(ETBHTB_SymbolDesc,APIHelperClass.ValidationNullValue(Validate_ETBHTB_SymbolDesc), "Validate_ETBHTB_SymbolDesc");
				Assert.assertEquals(ETBHTB_LocationID,APIHelperClass.ValidationNullValue(Validate_ETBHTB_LocationID), "Validate_ETBHTB_LocationID");
				Assert.assertEquals(ETBHTB_ContactName,APIHelperClass.ValidationNullValue(Validate_ETBHTB_ContactName), "Validate_ETBHTB_ContactName");
				Assert.assertEquals(ETBHTB_OrigQuantity,"["+Validate_ETBHTB_OrigQuantity+"]", "Validate_ETBHTB_OrigQuantity");
				Assert.assertEquals(ETBHTB_Quantity,"["+Validate_ETBHTB_Quantity+"]", "Validate_ETBHTB_Quantity");
				Assert.assertEquals(ETBHTB_EtbListType,"["+Validate_ETBHTB_EtbListType+"]", "Validate_ETBHTB_EtbListType");
				Assert.assertEquals(ETBHTB_ID,APIHelperClass.ValidationNullValue(Validate_ETBHTB_ID), "Validate_ETBHTB_ID");

			}
		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}
}
