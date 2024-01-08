package MarketData_OPT_L1;

import org.json.simple.JSONArray;
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
import static org.junit.Assert.assertThat;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import XLDataProvider.ExcelDataProvider;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Opt_Subscribe_Bulk {

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
	
	@Test (dataProvider="Subscribe_Bulk_MarketData", dataProviderClass=ExcelDataProvider.class,groups={"Subscribe_Bulk"} ,dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_Subscribe_Bulk (String Subscribe_Bulk_TestCases,
									   String EndpointVersion,
									   String Topic_Base_Path,
									   String Content_Type,
									   String Topic_Symbol,
									   String Get_Topic_StatusCode,
									   String Subscribe_Base_Path,
									   String Topic_Response_ObjectName,
									   String Topic_Response_ToIndex,
									   String Subscribe_StatusCode,
									   String Validate_Response_Fields)
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+Subscribe_Bulk_TestCases);
			LoggingManager.logger.info("====================================================================");	
			RestAssured.baseURI=Global.BaseURL;
			String getTopics="";
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
			JsonPath topicJsonResponse = new JsonPath(topicResponse.getBody().asString());
			int ResponseArraySize = topicJsonResponse.getInt(Topic_Response_ObjectName+".size()");
			LoggingManager.logger.info("API-Topic_MarketData ResponseArraySize : ["+ResponseArraySize+"]");	
			if(!(Topic_Response_ToIndex.equalsIgnoreCase("*")) && Integer.parseInt(Topic_Response_ToIndex)>=0 && Integer.parseInt(Topic_Response_ToIndex)<ResponseArraySize) 
	        {
			     getTopics=com.jayway.jsonpath.JsonPath.read(topicResponse.getBody().asString(),"$."+Topic_Response_ObjectName+"[0:"+Topic_Response_ToIndex+"]").toString();
			     LoggingManager.logger.info("API - Get "+Topic_Response_ToIndex+" Topic Data From Response :"+getTopics);
		    }
	        else
	        {
	        	 getTopics=com.jayway.jsonpath.JsonPath.read(topicResponse.getBody().asString(),"$."+Topic_Response_ObjectName+"["+Topic_Response_ToIndex+"]").toString();
	        	 LoggingManager.logger.info("API - Get ALL Topic Data From Response :"+getTopics);
	        }
	        Global.getTopicValue=getTopics;
	        String subscribeBody="{\n\"OptionSymbols\": "+getTopics+"\n}";
			Response response=  				 
								 given()	
										.header("Content-Type",Content_Type) 
										.header("Authorization", "Bearer " + Global.getAccToken)
										.body(subscribeBody)
								 .when()
										.post(Subscribe_Base_Path)
								 .then()
								 		.statusCode(Integer.parseInt(Subscribe_StatusCode))
								 		.extract()
										.response();
			
		
			JsonPath jsonPath = new JsonPath(response.getBody().asString());
			LoggingManager.logger.info("API-Subscribe_Bulk_MarketData_Base_Path : ["+Subscribe_Base_Path+"]");
			LoggingManager.logger.info("API-Subscribe_Bulk_MarketData_Body : ["+subscribeBody.toString()+"]");
			LoggingManager.logger.info("API-Subscribe_Bulk_MarketData_StatusCode : ["+response.statusCode()+"]"); 
			String ValidateLocalCodes=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(),"$.[*].Data.LocalCode").toString();
			LoggingManager.logger.info("API-Subscribe_Bulk_MarketData_Value Expected : ["+getTopics+"] and Found : ["+ValidateLocalCodes+"]"); 
			Assert.assertEquals(ValidateLocalCodes,getTopics,"Verify_Subscribe_Bulk_LocalCode");
			for (int index = 0; index < jsonPath.getInt("size()"); index++) 
	        {Assert.assertEquals((jsonPath.getMap("["+index+"].Data").keySet()).toString(),Validate_Response_Fields,"Verify_Subscribe_Bulk_Fields");}

		}	
		catch (Exception e) 
		{
			LoggingManager.logger.error(e);
		}	
		
		//LoggingManager.logger.info("API-LocalCode : ["+jsonresponse.getJsonObject("[*].Data.LocalCode")+"]");
		//LoggingManager.logger.info("API-Response : ["+jsonresponse.getInt("size()")+"]");
		//LoggingManager.logger.info("API-LocalCode : ["+jsonresponse.getJsonObject("[*].Data.LocalCode")+"]");
		//LoggingManager.logger.info("API-DataObj : ["+jsonresponse.getJsonObject("[1].Data")+"]");
		//LoggingManager.logger.info("API-keySet : ["+jsonPath.getString("[1].Data.findAll {true}.keySet()")+"]");
		// jsonPath.getMap("[1].Data").keySet().forEach(System.out::println);
		//LoggingManager.logger.info("API-KeySet : ["+jsonPath.getMap("[1].Data").keySet()+"]"); 
	
	}
}
