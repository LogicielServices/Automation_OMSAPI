package Orders;

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


public class Order_Executions {

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
	

	 @Test (dataProvider="OrderExecutions", dataProviderClass=ExcelDataProvider.class , groups={"Order_Executions"}, dependsOnGroups={"UserLoginAuthentications"})
	 public void Verify_Order_Executions(String OrderExecutions_TestCase,
										 String EndpointVersion,
										 String Order_Creation_BasePath,
										 String Content_Type ,
										 String Order_Creation_Body,
										 String Order_Creation_StatusCode,
										 String Order_Creation_Response,
										 String Subscribe_Order_BasePath,
										 String Subscribe_Order_StatusCode,
										 String Subscribe_Order_UserID,
										 String Order_boothID,
										 String Subscribe_Order_ExpectedStatus,
										 String Subscribe_Order_Text,
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
				LoggingManager.logger.info("TestCase : "+OrderExecutions_TestCase);
				LoggingManager.logger.info("====================================================================");

				RestAssured.baseURI=Global.BaseURL;
				Response response=

								 given()
								.header("Content-Type",Content_Type)
								.header("Authorization", "Bearer " + Global.getAccToken)
								.body(Order_Creation_Body)

								.when()
								.post(Order_Creation_BasePath)

								.then()
								.extract()
								.response();

				LoggingManager.logger.info("API-Order_Creation_BasePath : ["+Order_Creation_BasePath+"]");
				LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
				LoggingManager.logger.info("API-Order_Creation_Body : ["+Order_Creation_Body+"]");
				LoggingManager.logger.info("API-Order_Creation_StatusCode : ["+response.getStatusCode()+"]");
				LoggingManager.logger.info("API-Order_Creation_Response : ["+response.getBody().asString()+"]");
				Assert.assertEquals(response.getStatusCode(), Integer.parseInt(Order_Creation_StatusCode),"Verify_Equity_Order_Active_Open_Creation");
				if(EndpointVersion.equalsIgnoreCase("V1")) {Assert.assertEquals(response.getBody().asString(), Order_Creation_Response,"Verify_Order_Creation_Response");}
				else{Assert.assertEquals(response.jsonPath().get("message"), Order_Creation_Response,"Verify_Order_Creation_Response");}

				//------------------------------------------Get OrderID-------------------------

				APIHelperClass.GetOrder(Subscribe_Order_BasePath,
												Global.getAccToken,
												Content_Type,
												Integer.parseInt(Subscribe_Order_StatusCode),
												EndpointVersion,
												Subscribe_Order_UserID,
												Subscribe_Order_Text, "equity");

				LoggingManager.logger.info("API-Found Order_ID :  [" + Global.getOrderID + "]");
				LoggingManager.logger.info("API-Found Order_Status :  [" + Global.getStatus + "]");
				LoggingManager.logger.info("API-Found Side Desc. :  [" + Global.getSideDesc + "]");
				if (Global.getOrderID == null || Global.getOrderID == "") {
					Assert.fail("Logs : Order Not Found with status :[" + Subscribe_Order_ExpectedStatus + "]");
				}

				//------------------------------------------Validate Execution Against OrderID-------------------------

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
				Assert.assertEquals(get_execution_response.getStatusCode(), Integer.parseInt(Subscribe_Executions_StatusCode),"Verify_SubscribeBUY_Executions");
				APIHelperClass.Validate_Get_Executions( get_execution_response,
														EndpointVersion,
														Order_boothID,
														Executions_SideDesc,
														Executions_Side,
														Global.getOrderID,
														Global.qOrderID,
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
