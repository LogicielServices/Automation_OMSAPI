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
import io.restassured.response.Response;

public class Opt_Topic {
   

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
	
	@Test (dataProvider="Topic_MarketData", dataProviderClass=ExcelDataProvider.class, groups={"Get_Topic"},dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_Get_Topic (String Topic_TestCases,String EndpointVersion,String Topic_Base_Path,String Content_Type ,String Topic_Symbol,String Get_Topic_StatusCode)
	{
		try
		{
			 LoggingManager.logger.info("====================================================================");
			 LoggingManager.logger.info("TestCase : "+Topic_TestCases);
			 LoggingManager.logger.info("====================================================================");	
			 RestAssured.baseURI=Global.BaseURL;
			 Response response= 
								 given()	
										.header("Content-Type",Content_Type) 
										.header("Authorization", "Bearer " + Global.getAccToken)
									
								 .when()
										.get(Topic_Base_Path+Topic_Symbol)
								 .then()
										 .extract()
										 .response();
			 
			//Global.getTopicResponse=response;
			LoggingManager.logger.info("API-Topic_MarketData_Base_Path : ["+Topic_Base_Path+Topic_Symbol+"]");	
			LoggingManager.logger.info("API-Topic_MarketData_StatusCode : ["+response.statusCode()+"]");
			//LoggingManager.logger.info("API-Topic_MarketData_Response: ["+response.getBody().asPrettyString()+"]");
			Assert.assertEquals(response.statusCode(),Integer.parseInt(Get_Topic_StatusCode),"Get Topic Response");
			LoggingManager.logger.info("====================================================================");	
			//Global.getTopicValue=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.OptionSymbol.*").toString();
			//LoggingManager.logger.info("API-Topic_MarketData_Response : ["+Global.getTopicValue+"]");
		
		}	
		catch (Exception e) 
		{
			LoggingManager.logger.error(e);
		}	
		
	}
}
