package LocatesAPI;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
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


public class Subscribe_Locates {
	
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
	 
	@Test (dataProvider="Subscribe_Locates", dataProviderClass=ExcelDataProvider.class,groups={"Subscribe_Locates"}, dependsOnGroups={"Post_Locates"})
	public void Verify_Subscribe_Locates(String Subscribe_Locates_TestCase,
												String Subscribe_Locates_BasePath,
												String Content_Type,
												String Subscribe_Locates_StatusCode,
												String Validate_OriginatingUserDesc,
												String Validate_OrderQty,
												String Validate_Symbol,
												String Validate_Account,
												String Validate_Booth,
												String Validate_Flag)
	{
		LoggingManager.logger.info("====================================================================");
		LoggingManager.logger.info("TestCase : "+Subscribe_Locates_TestCase);
		LoggingManager.logger.info("====================================================================");
		
		
		RestAssured.baseURI=Global.BaseURL;
		Response response=
							given()	
								.header("Content-Type",Content_Type) 
								.header("Authorization", "Bearer " + Global.getAccToken)
								
							.when()
								.get(Subscribe_Locates_BasePath)
								
							.then()
								//.statusCode(Integer.parseInt(Subscribe_Locates_StatusCode))
								//.statusLine("HTTP/1.1 200 OK")
								.extract().response();
		
		LoggingManager.logger.info("API-Subscribe_Locates_BasePath : ["+Subscribe_Locates_BasePath+"]");
		LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
		LoggingManager.logger.info("API-Subscribe_Locates_StatusCode : ["+response.getStatusCode()+"]");
		Assert.assertEquals(response.statusCode(),Integer.parseInt(Subscribe_Locates_StatusCode), "Verify_Subscribe_Locates_StatusCode");
		
		JsonPath jsonresponse = new JsonPath(response.getBody().asString());
		int ResponseArraySize = jsonresponse.getInt("eventData.size()");
		String getOriginatingUserDesc="",getOrderQty="",getSymbol="",getAccount="",getBoothID="";
		
		for(int position = ResponseArraySize-1; position >=0; position--) 
		{
			getOriginatingUserDesc = jsonresponse.getString("eventData["+position+"].originatingUserDesc");
			getOrderQty = jsonresponse.getString("eventData["+position+"].orderQty");
			getSymbol = jsonresponse.getString("eventData["+position+"].symbol");
			getAccount = jsonresponse.getString("eventData["+position+"].account");
			getBoothID = jsonresponse.getString("eventData["+position+"].boothID");
		
		
			if(	getOriginatingUserDesc.equalsIgnoreCase(Validate_OriginatingUserDesc)
				&& getOrderQty.equalsIgnoreCase(Validate_OrderQty)
				&& getSymbol.equalsIgnoreCase(Validate_Symbol)
				&& getAccount.equalsIgnoreCase(Validate_Account)
				&& getBoothID.equalsIgnoreCase(Validate_Booth)) 
			{
				Global.getID = jsonresponse.getString("eventData["+position+"].quoteReqID");
				Global.ValidationFlag=true;
				break;
			}
			else
			{	Global.ValidationFlag=false;
				continue;
			}
		
		}	
		
		LoggingManager.logger.info("API-Validate_OriginatingUserDesc : ["+Validate_OriginatingUserDesc +"] - Response getOriginatingUserDesc : "+getOriginatingUserDesc);
		LoggingManager.logger.info("API-Validate_OrderQty : ["+Validate_OrderQty +"] - Response getOrderQty : "+getOrderQty);
		LoggingManager.logger.info("API-Validate_Symbol : ["+Validate_Symbol +"] - Response getSymbol : "+getSymbol);
		LoggingManager.logger.info("API-Validate_Account : ["+Validate_Account +"] - Response getOrderQty : "+getOrderQty);
		LoggingManager.logger.info("API-Validate_Booth : ["+Validate_Booth +"] - Response getBoothID : "+getBoothID);
		Assert.assertEquals(Global.ValidationFlag,Boolean.parseBoolean(Validate_Flag),"Validate_Flag");
		LoggingManager.logger.info("====================================================================");
		
		
		
	}	
}
