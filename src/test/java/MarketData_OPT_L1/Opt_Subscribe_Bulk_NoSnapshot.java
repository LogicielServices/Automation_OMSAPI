package MarketData_OPT_L1;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import APIHelper.Global;
import APIHelper.LoggingManager;

import static APIHelper.APIHelperClass.getserializedJsonObj;
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
												    String Topic_Response_ObjectName,
													String Topic_Response_ToIndex,
													String Subscribe_Bulk_NoSnapshot_Base_Path,
													String Subscribe_StatusCode,
													String Subscribe_Message,
												  	String Error_Response)
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
				 LoggingManager.logger.info("API - Get ALL Topic Data From Response :");
	        	 //LoggingManager.logger.info("API - Get ALL Topic Data From Response :"+getTopics);
	         }

			String SubscribeNoSnapshotBody = "{\n\"OptionSymbols\": " + getTopics + "\n}";
				Response response =
						given()
								.header("Content-Type", Content_Type)
								.header("Authorization", "Bearer " + Global.getAccToken)
								.body(SubscribeNoSnapshotBody)
								.when()
								.post(Subscribe_Bulk_NoSnapshot_Base_Path)
								.then()
								//.statusCode(Integer.parseInt(UnSubscribe_StatusCode))
								.extract()
								.response();

				LoggingManager.logger.info("API-Subscribe_Bulk_NoSnapshot_MarketData_Base_Path : [" + Subscribe_Bulk_NoSnapshot_Base_Path + "]");
				LoggingManager.logger.info("API-Subscribe_Bulk_NoSnapshot_MarketData_Body : [" + SubscribeNoSnapshotBody.toString() + "]");
				LoggingManager.logger.info("API-Subscribe_Bulk_NoSnapshot_MarketData_StatusCode : [" + response.statusCode() + "]");
				LoggingManager.logger.info("API-Subscribe_Bulk_NoSnapshot_MarketData_ResponseBody : [" + response.getBody().asString() + "]");
				Assert.assertEquals(response.statusCode(), Integer.parseInt(Subscribe_StatusCode), "Verify_Subscribe_Bulk_StatusCode");

				if (ResponseArraySize==0)
				{
					LoggingManager.logger.info("API-Subscribe_Bulk_NoSnapshot_Error_Response Expected : ["+Error_Response+"] and Found : ["+getserializedJsonObj(response, "errors")+"]");
					Assert.assertEquals(getserializedJsonObj(response, "errors"), Error_Response, "Verify_Subscribe_Bulk_NoSnapshot_Error_Response");
				}
				else
				{
					Assert.assertEquals(response.getBody().asString(), Subscribe_Message, "Verify_Subscribe_Bulk_NoSnapshot");
				}
		}	
		catch (Exception e) 
		{
			LoggingManager.logger.error(e);
		}
	}
}
