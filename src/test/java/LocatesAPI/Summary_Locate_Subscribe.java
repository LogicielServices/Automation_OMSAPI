package LocatesAPI;

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


public class Summary_Locate_Subscribe {
	
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
	 
	@Test (dataProvider="Summary_Locate_Subscribe", dataProviderClass=ExcelDataProvider.class,groups={"Summary_Locate_Subscribe"}, dependsOnGroups={"Post_Locates"})
	public void Verify_Summary_Locate_Subscribe(String Summary_Locate_Subscribe_TestCase,
												String Summary_Locate_Subscribe_BasePath,
												String Content_Type,
												String Summary_Locate_Subscribe_StatusCode,
												String Validate_ID,
												String Validate_OriginatingUserDesc,
												String Validate_ClientID,
												String Validate_Symbol,
												String Validate_Account,
												String Validate_Booth)
	{
		LoggingManager.logger.info("====================================================================");
		LoggingManager.logger.info("TestCase : "+Summary_Locate_Subscribe_TestCase);
		LoggingManager.logger.info("====================================================================");
		RestAssured.baseURI=Global.BaseURL;
		Response response=
							given()	
								.header("Content-Type",Content_Type) 
								.header("Authorization", "Bearer " + Global.getAccToken)
								
							.when()
								.get(Summary_Locate_Subscribe_BasePath)
								
							.then()
								//.statusCode(Integer.parseInt(Summary_Locate_Subscribe_StatusCode))
								//.statusLine("HTTP/1.1 200 OK")
								.extract().response();
		
		LoggingManager.logger.info("API-BasePath : ["+Summary_Locate_Subscribe_BasePath+"]");
		LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
		LoggingManager.logger.info("API-Summary_Available_Subscribe_StatusCode : ["+response.getStatusCode()+"]");
		Assert.assertEquals(response.statusCode(),Integer.parseInt(Summary_Locate_Subscribe_StatusCode), "Verify_Summary_Locate_Subscribe_StatusCode");
		
		
		String getID=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.eventData[?(@.id =='"+Validate_ID+"' )].id").toString();
		String getOriginatingUserDesc=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.eventData[?(@.id =='"+Validate_ID+"' )].originatingUserDesc").toString();
		String getClientID=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.eventData[?(@.id =='"+Validate_ID+"' )].clientID").toString();
		String getSymbol=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.eventData[?(@.id =='"+Validate_ID+"' )].symbol").toString();
		String getAccount=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.eventData[?(@.id =='"+Validate_ID+"' )].account").toString();
		String getBoothID=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.eventData[?(@.id =='"+Validate_ID+"' )].boothID").toString();
		//System.out.println(AcountValue+AccountName+BoothID);
		
		LoggingManager.logger.info("API-Validate_ID : ["+Validate_ID +"] - Response getID : "+getID);
		LoggingManager.logger.info("API-Validate_OriginatingUserDesc : ["+Validate_OriginatingUserDesc +"] - Response getOriginatingUserDesc : "+getOriginatingUserDesc);
		LoggingManager.logger.info("API-Validate_ClientID : ["+Validate_ClientID +"] - Response getClientID : "+getClientID);
		LoggingManager.logger.info("API-Validate_Symbol : ["+Validate_Symbol +"] - Response getSymbol : "+getSymbol);
		LoggingManager.logger.info("API-Validate_Account : ["+Validate_Account +"] - Response getAccount : "+getAccount);
		LoggingManager.logger.info("API-Validate_Booth : ["+Validate_Booth +"] - Response getBoothID : "+getBoothID);
		
		
		Assert.assertEquals(getID,"[\""+Validate_ID+"\"]", "Validate_Summary_Locate_Subscribe_ID");
		Assert.assertEquals(getOriginatingUserDesc,"[\""+Validate_OriginatingUserDesc+"\"]", "Validate_Summary_Locate_Subscribe_OriginatingUserDesc");
		Assert.assertEquals(getClientID,"[\""+Validate_ClientID+"\"]", "Validate_Summary_Locate_Subscribe_ClientID");
		Assert.assertEquals(getSymbol,"[\""+Validate_Symbol+"\"]", "Validate_Summary_Locate_Subscribe_Symbol");
		Assert.assertEquals(getAccount,"[\""+Validate_Account+"\"]", "Validate_Summary_Locate_Subscribe_Account");
		Assert.assertEquals(getBoothID,"[\""+Validate_Booth+"\"]", "Validate_Summary_Locate_Subscribe_Booth");
		
		LoggingManager.logger.info("====================================================================");
	}	
}
