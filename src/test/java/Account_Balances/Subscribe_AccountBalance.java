package Account_Balances;

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


public class Subscribe_AccountBalance {
	
	
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
	 
	@Test (dataProvider="Subscribe_AccountBalance", dataProviderClass=ExcelDataProvider.class,groups={"Subscribe_AccountBalance"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_Subscribe_AccountBalance(String TestCase,
												String EndpointVersion,
												String Subscribe_AccountBalance_BasePath,
												String Content_Type,
												String Subscribe_AccountBalance_StatusCode )
															{
		LoggingManager.logger.info("====================================================================");
		LoggingManager.logger.info("TestCase : "+TestCase);
		LoggingManager.logger.info("====================================================================");
		RestAssured.baseURI=Global.BaseURL;
		Response response=
							given()	
								.header("Content-Type",Content_Type) 
								.header("Authorization", "Bearer " + Global.getAccToken)
								
							.when()
								.get(Subscribe_AccountBalance_BasePath)
								
							.then()
								.statusCode(Integer.parseInt(Subscribe_AccountBalance_StatusCode))
								.statusLine("HTTP/1.1 200 OK")
								.extract().response();
		
		LoggingManager.logger.info("API-Subscribe_AccountBalance_BasePath : ["+Subscribe_AccountBalance_BasePath+"]");
		LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
		LoggingManager.logger.info("API-Subscribe_AccountBalance_StatusCode : ["+response.getStatusCode()+"]");
		
		//Assert.assertEquals(response.statusCode(),Integer.parseInt(Subscribe_AccountBalance_StatusCode), "Verify_AccountBalance_StatusCode");
		//String Response_Subscribe_OrderID=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.eventData[?(@.qOrderID =='"+Validate_Subscribe_OrderID+"' )].qOrderID").toString();
		//System.out.println("Subscribe_qOrderID "+Subscribe_OrderID);
		//Assert.assertEquals(Subscribe_qOrderID,"[\""+Validate_Subscribe_qOrderID+"\"]", "Validate_Subscribe_qOrderID");
		
		
		
	}	
}
