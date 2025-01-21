package MarketData_OPT_L1;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.testng.Tag;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import APIHelper.Global;
import APIHelper.LoggingManager;

import static io.restassured.RestAssured.given;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import XLDataProvider.ExcelDataProvider;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Opt_Subscribe {

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
	@Description("This is Subscribe MarketData TestCase")
	@Tag("Option MarketData")
	@Test (dataProvider="Subscribe_MarketData", dataProviderClass=ExcelDataProvider.class,groups={"Subscribe_Individual"} ,dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_Subscribe_Individual (String Subscribe_TestCases,
											 String EndpointVersion,
											 String Topic_Base_Path,
											 String Content_Type,
											 String Topic_Symbol,
											 String Get_Topic_StatusCode,
											 String Subscribe_Base_Path,
											 String Topic_Response_Index,
											 String Subscribe_StatusCode,
											 String Validate_Response_Fields)
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+Subscribe_TestCases);
			LoggingManager.logger.info("====================================================================");	
			RestAssured.baseURI=Global.BaseURL;
			HashMap<Object, Object>  subscribeBody=new HashMap<Object, Object>();
			Response topicResponse= 
					 given()	
							.header("Content-Type",Content_Type) 
							.header("Authorization", "Bearer " + Global.getAccToken)
						
					 .when()
							.get(Topic_Base_Path+Topic_Symbol)
					 .then()
							 .extract()
							 .response();
			Global.getTopicResponse=topicResponse;	
			LoggingManager.logger.info("API-Topic_MarketData_Base_Path : ["+Topic_Base_Path+Topic_Symbol+"]");	
			LoggingManager.logger.info("API-Topic_MarketData_StatusCode : ["+topicResponse.statusCode()+"]");
			Assert.assertEquals(topicResponse.statusCode(),Integer.parseInt(Get_Topic_StatusCode),"Get Topic Response");
			Global.getTopicValue=topicResponse.jsonPath().get("OptionSymbol["+Topic_Response_Index+"]").toString();
			subscribeBody.put("optionSymbol",Global.getTopicValue);
			Response response=  				 
								 given()	
										.header("Content-Type",Content_Type) 
										.header("Authorization", "Bearer " + Global.getAccToken)
										.body(subscribeBody)
								 .when()
										.post(Subscribe_Base_Path)
								 .then()
								 		.extract()
										.response();
			
			
			JSONObject jsonObject=(JSONObject) JSONValue.parse(response.getBody().asString());//convert Object into JSONObject 
			LoggingManager.logger.info("API-Subscribe_MarketData_Base_Path : ["+Subscribe_Base_Path+"]");
			LoggingManager.logger.info("API-Subscribe_MarketData_Body : ["+subscribeBody.toString()+"]");
			LoggingManager.logger.info("API-Subscribe_MarketData_StatusCode : ["+response.statusCode()+"]");
			LoggingManager.logger.info("API-Subscribe_MarketData_TopicValue Expected : ["+Global.getTopicValue+"] and Found : ["+response.jsonPath().get("LocalCode")+"]"); 
			Assert.assertEquals(response.jsonPath().get("LocalCode"),Global.getTopicValue,"Verify_Subscribe_Individual_LocalCode");
			Assert.assertEquals((jsonObject.keySet()).toString(),Validate_Response_Fields,"Verify_Subscribe_Individual_Fields");
		}	
		catch (Exception e) 
		{
			LoggingManager.logger.error(e);
		}	
	}
}
