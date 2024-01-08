package MarketData_OPT_L1;

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

import XLDataProvider.ExcelDataProvider;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Opt_Subscribe_Bulk_NoSnapshot {

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
	
	@Test (dataProvider="Subscribe_Bulk_NoSnapshot_MarketData", dataProviderClass=ExcelDataProvider.class,groups={"Subscribe_Bulk_NoSnapshot"} ,dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_Subscribe_Bulk_NoSnapshot (String Subscribe_Bulk_NoSnapshot_TestCases,
													String EndpointVersion,
													String Topic_Base_Path,
													String Content_Type,
													String Topic_Symbol,
													String Get_Topic_StatusCode,
													String Subscribe_Base_Path,
													String Topic_Response_ObjectName,
													String Topic_Response_ToIndex,
													String Subscribe_StatusCode,
													String Validate_Response_Fields,
													String UnSubscribe_Bulk_NoSnapshot_Base_Path,
													String UnSubscribe_StatusCode,
													String UnSubscribe_Message)
	{
		try
		{
			 LoggingManager.logger.info("====================================================================");
			 LoggingManager.logger.info("TestCase : "+Subscribe_Bulk_NoSnapshot_TestCases);
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
			 LoggingManager.logger.info("API-Topic_MarketData_Base_Path : ["+Topic_Base_Path+Topic_Symbol+"]");	
			 LoggingManager.logger.info("API-Topic_MarketData_StatusCode : ["+topicResponse.statusCode()+"]");
			 Assert.assertEquals(topicResponse.statusCode(),Integer.parseInt(Get_Topic_StatusCode),"Get Topic Response");
			 JsonPath topicJsonResponse = new JsonPath(topicResponse.getBody().asString());
			 int ResponseArraySize = topicJsonResponse.getInt(Topic_Response_ObjectName+".size()");
			 LoggingManager.logger.info("API-ResponseArraySize : ["+ResponseArraySize+"]");
			 if(!(Topic_Response_ToIndex.equalsIgnoreCase("*")) && Integer.parseInt(Topic_Response_ToIndex)>=0 && Integer.parseInt(Topic_Response_ToIndex)<ResponseArraySize)  
	         {
			     getTopics=com.jayway.jsonpath.JsonPath.read(topicResponse.getBody().asString(),"$."+Topic_Response_ObjectName+"[0:"+Topic_Response_ToIndex+"]").toString();
			     LoggingManager.logger.info("API - Get "+Topic_Response_ToIndex+" Topic Data From Response :"+getTopics);
		     }
	         else
	         {
	        	 getTopics=com.jayway.jsonpath.JsonPath.read(topicResponse.getBody().asString(),"$."+Topic_Response_ObjectName+"[*]").toString();
	        	 LoggingManager.logger.info("API - Get ALL Topic Data From Response :"+getTopics);
	         }
	        
	         String subscribeBody="{\n\"OptionSymbols\": "+getTopics+"\n}";
			 Response subscribeResponse=  				 
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
			
		
			 JsonPath subscribeJsonResponse = new JsonPath(subscribeResponse.getBody().asString());
			 JsonPath jsonPath = new JsonPath(subscribeResponse.getBody().asString());
			 LoggingManager.logger.info("API-Subscribe_Bulk_MarketData_Base_Path : ["+Subscribe_Base_Path+"]");
			 LoggingManager.logger.info("API-Subscribe_Bulk_MarketData_Body : ["+subscribeBody.toString()+"]");
			 LoggingManager.logger.info("API-Subscribe_Bulk_MarketData_StatusCode : ["+subscribeResponse.statusCode()+"]"); 
			 String ValidateLocalCodes=com.jayway.jsonpath.JsonPath.read(subscribeResponse.getBody().asString(),"$.[*].Data.LocalCode").toString();
			 LoggingManager.logger.info("API-Subscribe_Bulk_MarketData_Value Expected : ["+getTopics+"] and Found : ["+ValidateLocalCodes+"]"); 
			 Assert.assertEquals(ValidateLocalCodes,getTopics,"Verify_Subscribe_Bulk_LocalCode");
			 Assert.assertEquals(ValidateLocalCodes,getTopics,"Verify_Subscribe_Bulk_LocalCode");
			 for (int index = 0; index < subscribeJsonResponse.getInt("size()"); index++) 
	         {Assert.assertEquals((jsonPath.getMap("["+index+"].Data").keySet()).toString(),Validate_Response_Fields,"Verify_Subscribe_Bulk_Fields");}
			 String UnSubscribeBody="{\n\"OptionSymbols\": "+getTopics+"\n}";
			 Response response=  				 
								 given()	
										.header("Content-Type",Content_Type) 
										.header("Authorization", "Bearer " + Global.getAccToken)
										.body(UnSubscribeBody)
								 .when()
										.post(UnSubscribe_Bulk_NoSnapshot_Base_Path)
								 .then()
								 		//.statusCode(Integer.parseInt(UnSubscribe_StatusCode))
								 		.extract()
										.response();
			
			 LoggingManager.logger.info("API-UnSubscribe_Bulk_NoSnapshot_MarketData_Base_Path : ["+UnSubscribe_Bulk_NoSnapshot_Base_Path+"]");
			 LoggingManager.logger.info("API-UnSubscribe_Bulk_NoSnapshot_MarketData_Body : ["+UnSubscribeBody.toString()+"]");
			 LoggingManager.logger.info("API-UnSubscribe_Bulk_NoSnapshot_MarketData_StatusCode : ["+response.statusCode()+"]"); 
			 LoggingManager.logger.info("API-UnSubscribe_Bulk_NoSnapshot_MarketData_ResponseBody : ["+response.getBody().asString()+"]");
			 Assert.assertEquals(response.statusCode(),Integer.parseInt(UnSubscribe_StatusCode),"Verify_UnSubscribe_Bulk_StatusCode");
			 Assert.assertEquals(response.getBody().asString(),UnSubscribe_Message,"Verify_UnSubscribe_Bulk_NoSnapshot");
			 LoggingManager.logger.info("====================================================================");	
		}	
		catch (Exception e) 
		{
			LoggingManager.logger.error(e);
		}
	}
}
