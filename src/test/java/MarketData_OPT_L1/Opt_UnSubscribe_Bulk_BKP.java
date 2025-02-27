package MarketData_OPT_L1;

import APIHelper.Global;
import APIHelper.LoggingManager;
import XLDataProvider.ExcelDataProvider;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static io.restassured.RestAssured.given;

public class Opt_UnSubscribe_Bulk_BKP {

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
	
	@Test (dataProvider="UnSubscribe_Bulk_MarketData", dataProviderClass=ExcelDataProvider.class,groups={"UnSubscribe_Bulk"} ,dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_UnSubscribe_Bulk (String UnSubscribe_Bulk_TestCases,
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
										 String UnSubscribe_Bulk_Base_Path,
										 String Unsubscribe_Bulk_Body,
										 String UnSubscribe_StatusCode,
										 String UnSubscribe_Message)
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+UnSubscribe_Bulk_TestCases);
			LoggingManager.logger.info("====================================================================");	
			RestAssured.baseURI=Global.BaseURL;
			String getTopics="", UnsubscribeBody="";
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

				for (int index = 0; index < subscribeJsonResponse.getInt("size()"); index++)
				{Assert.assertEquals((jsonPath.getMap("["+index+"].Data").keySet()).toString(),Validate_Response_Fields,"Verify_Subscribe_Bulk_Fields");}

				if(Unsubscribe_Bulk_Body.equalsIgnoreCase("0"))
				{
					 UnsubscribeBody="{\n\"OptionSymbols\": "+getTopics+"\n}";
				}
				else {
					 UnsubscribeBody="{\n\"OptionSymbols\": "+Unsubscribe_Bulk_Body+"\n}";
				}

				Response unsubscribeResponse=
						given()
								.header("Content-Type",Content_Type)
								.header("Authorization", "Bearer " + Global.getAccToken)
								.body(UnsubscribeBody)
								.when()
								.post(UnSubscribe_Bulk_Base_Path)
								.then()
								//.statusCode(Integer.parseInt(UnSubscribe_StatusCode))
								.extract()
								.response();

				LoggingManager.logger.info("API-UnSubscribe_Bulk_MarketData_Base_Path : ["+UnSubscribe_Bulk_Base_Path+"]");
				LoggingManager.logger.info("API-UnSubscribe_Bulk_MarketData_Body : ["+UnsubscribeBody+"]");
				LoggingManager.logger.info("API-UnSubscribe_Bulk_MarketData_StatusCode : ["+unsubscribeResponse.statusCode()+"]");
				LoggingManager.logger.info("API-UnSubscribe_Bulk_MarketData_ResponseBody : ["+unsubscribeResponse.getBody().asString()+"]");
				Assert.assertEquals(unsubscribeResponse.statusCode(),Integer.parseInt(UnSubscribe_StatusCode),"Verify_UnSubscribe_Bulk_StatusCode");
				Assert.assertEquals(unsubscribeResponse.getBody().asString(),UnSubscribe_Message,"Verify_UnSubscribe_Bulk");

		}	
		catch (Exception e) 
		{
			LoggingManager.logger.error(e);
		}
	}
}
