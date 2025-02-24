package OptionOrders;

import APIHelper.APIHelperClass;
import APIHelper.Global;
import APIHelper.LoggingManager;
import XLDataProvider.ExcelDataProvider;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static io.restassured.RestAssured.given;

//Test(groups = {"Create"},dependsOnGroups={"UserLoginAuthentications"})
public class OptionOrder_Executions {

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
	 @Test (dataProvider="Subscribe_OptionExecutions", dataProviderClass=ExcelDataProvider.class , groups={"Subscribe_OptionExecutions"}, dependsOnGroups={"UserLoginAuthentications"})//CreateBUYFilledOptionOrder UserLoginAuthentications
	 public void Verify_Subscribe_OptionExecutions(	String OptionOrder_Executions_TestCase,
													   String Endpoint_Version,
													   String OptionOrder_Creation_BasePath,
													   String Content_Type ,
													   String OptionOrder_Creation_Body,
													   String OptionOrder_Creation_StatusCode,
													   String OptionOrder_Creation_Response,
													   String Subscribe_OptionOrder_BasePath,
													   String Subscribe_OptionOrder_StatusCode,
													   String Subscribe_OptionOrder_originatingUserDesc,
													   String Subscribe_OptionOrder_boothID,
													   String Subscribe_OptionOrder_ExpectedStatus,
													   String Subscribe_OptionOrder_text,
													   String Subscribe_Executions_BasePath,
													   String Subscribe_Executions_StatusCode,
													   String Executions_SideDesc,
													   String Executions_Side,
													   String Fetch_Executions_Symbol,
													   String Fetch_Executions_Account,
													   String Fetch_Executions_OrderQty,
													   String Fetch_Executions_Price,
													   String Fetch_Executions_OrderType,
													   String Fetch_Executions_Destination,
													   String Fetch_Executions_SymbolSfx,
													   String Fetch_Executions_Status,
													   String Fetch_Executions_Text,
													   String Fetch_Executions_OrdStatus,
													   String Fetch_Last_Executions_OrdStatus,
													   String Fetch_Executions_OriginatingUserDesc,
													   String Fetch_Executions_ExecBroker,
													   String Fetch_Executions_TimeInForce,
													   String Fetch_Executions_ExecRefID,
													   String Fetch_Executions_ExecTransType,
													   String Fetch_Executions_ExecTransTypeDesc)

	 {
	 	try
		{
			 LoggingManager.logger.info("====================================================================");
			 LoggingManager.logger.info("TestCase : "+OptionOrder_Executions_TestCase);
			 LoggingManager.logger.info("====================================================================");
			 RestAssured.baseURI=Global.BaseURL;
			 Response response=
					given()
							.header("Content-Type",Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)
							.body(OptionOrder_Creation_Body)
							.when()
							.post(OptionOrder_Creation_BasePath)
							.then()
							//.statusCode(Integer.parseInt(OptionOrder_Creation_StatusCode))
							//.statusLine("HTTP/1.1 200 OK")
							.extract().response();

			LoggingManager.logger.info("API-OptionOrder_Creation_BasePath : ["+OptionOrder_Creation_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-OptionOrder_Creation_Body : ["+OptionOrder_Creation_Body+"]");
			LoggingManager.logger.info("API-OptionOrder_Creation_StatusCode : ["+response.getStatusCode()+"]");
			LoggingManager.logger.info("API-OptionOrder_Response_Body : ["+response.getBody().asPrettyString()+"]");
			Assert.assertEquals(response.getStatusCode(),Integer.parseInt(OptionOrder_Creation_StatusCode),"Option_Order_Creation");
			if(Endpoint_Version.equalsIgnoreCase("V1")) {Assert.assertEquals(response.getBody().asString(), OptionOrder_Creation_Response,"Verify_OptionOrder_Response");}
			else{Assert.assertEquals(response.jsonPath().get("message"), OptionOrder_Creation_Response,"Verify_OptionOrder_Response");}


			//---------------------------------------------Subscriptions------------------------------------------------------


			APIHelperClass.GetOptionOrder(	Subscribe_OptionOrder_BasePath,
											Global.getAccToken,
											Content_Type,
											Integer.parseInt(Subscribe_OptionOrder_StatusCode),
											Endpoint_Version,
											Subscribe_OptionOrder_originatingUserDesc,
											Subscribe_OptionOrder_text);

			LoggingManager.logger.info("API-Found Order_ID :  ["+Global.getOptionOrderID+"]");
			LoggingManager.logger.info("API-Found q Order_ID :  ["+Global.getOptionqOrderID+"]");
			LoggingManager.logger.info("API-Found Order_Status :  ["+Global.getOptionStatus+"]");
			LoggingManager.logger.info("API-Found Side Desc. :  ["+Global.getOptionSideDesc+"]");
			if(Global.getOptionOrderID == null || Global.getOptionOrderID=="" )
			{Assert.fail("Logs : Option Order Not Found with status :["+Subscribe_OptionOrder_ExpectedStatus+"]");}

			//---------------------------------------Executions---------------------------------------------------
			Response get_execution_response=

					given()
							.header("Content-Type",Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)
							.when()
							.get(Subscribe_Executions_BasePath)

							.then()
							//.statusCode(Integer.parseInt(Subscribe_Executions_StatusCode))
							//.statusLine("HTTP/1.1 200 OK")
							.extract().response();

			LoggingManager.logger.info("API-Subscribe_Executions_BasePath : ["+Subscribe_Executions_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-Subscribe_Executions_StatusCode : ["+get_execution_response.getStatusCode()+"]");
			Assert.assertEquals(get_execution_response.getStatusCode(), Integer.parseInt(Subscribe_Executions_StatusCode),"Verify_Subscribe_Executions");
			APIHelperClass.Validate_Get_Executions( get_execution_response,
													Endpoint_Version,
													Subscribe_OptionOrder_boothID,
													Executions_SideDesc,
													Executions_Side,
													Global.getOptionOrderID,
													Global.getOptionqOrderID,
													Fetch_Executions_Symbol,
													Fetch_Executions_Account,
													Fetch_Executions_OrderQty,
													Fetch_Executions_Price,
													Fetch_Executions_OrderType,
													Fetch_Executions_Destination,
													Fetch_Executions_SymbolSfx,
													Fetch_Executions_Status,
													Fetch_Executions_Text,
													Fetch_Executions_OrdStatus,
													Fetch_Last_Executions_OrdStatus,
													Fetch_Executions_OriginatingUserDesc,
													Fetch_Executions_ExecBroker,
													Fetch_Executions_TimeInForce,
													Fetch_Executions_ExecRefID,
													Fetch_Executions_ExecTransType,
													Fetch_Executions_ExecTransTypeDesc);
		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	 }
}
