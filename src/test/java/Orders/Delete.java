package Orders;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import APIHelper.APIHelperClass;
import APIHelper.Global;
import APIHelper.LoggingManager;
import XLDataProvider.ExcelDataProvider;
import static io.restassured.RestAssured.*;

import java.io.PrintWriter;
import java.io.StringWriter;


public class Delete {
	
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
	@Test (dataProvider="EquityOrderDelete", dataProviderClass=ExcelDataProvider.class,groups={"DeleteEquityOrder"}, dependsOnGroups={"UserLoginAuthentications"})//CreateEquityOrder--UserLoginAuthentications
	public void Verify_Delete_Equity_Order(String EquityOrder_TestCase,
										   String Endpoint_Version,
										   String EquityOrder_Creation_BasePath,
			 							   String Content_Type,
			 							   String EquityOrder_Creation_Body,
			 							   String EquityOrder_Creation_StatusCode,
			 							   String EquityOrder_Creation_Response,
										   String Fetch_EquityOrder_BasePath,
										   String Fetch_EquityOrder_UserID,
										   String Fetch_EquityOrder_OrderType,
										   String Fetch_EquityOrder_Side,
										   String Fetch_EquityOrder_Account,
										   String Fetch_EquityOrder_Symbol,
										   String Fetch_EquityOrder_Destination,
										   String Fetch_EquityOrder_Price,
										   String Fetch_EquityOrder_OrderQty,
										   String Fetch_EquityOrder_ExpectedStatus,
										   String Fetch_EquityOrder_StatusCode,
										   String EquityOrder_Delete_BasePath,
										   String EquityOrder_Delete_StatusCode,
										   String EquityOrder_Delete_Response,
										   String Status_Validation,
										   String Status_Validation_Flag ) throws InterruptedException
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+EquityOrder_TestCase);
			LoggingManager.logger.info("====================================================================");
			RestAssured.baseURI=Global.BaseURL;

			Response response=

							given()
							.header("Content-Type",Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)
							.body(EquityOrder_Creation_Body)

							.when()
							.post(EquityOrder_Creation_BasePath)

							.then()
							.extract()
							.response();

			LoggingManager.logger.info("API-EquityOrder_Creation_BasePath : ["+EquityOrder_Creation_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-EquityOrder_Creation_Body : ["+EquityOrder_Creation_Body+"]");
			LoggingManager.logger.info("API-EquityOrder_Creation_StatusCode : ["+response.getStatusCode()+"]");
			LoggingManager.logger.info("API-EquityOrder_Response_Body : ["+response.getBody().asString()+"]");

			Assert.assertEquals(response.getStatusCode(), Integer.parseInt(EquityOrder_Creation_StatusCode),"Verify_Equity_Order_Active_Open_Creation");
			if(Endpoint_Version.equalsIgnoreCase("V1")) {Assert.assertEquals(response.getBody().asString(), EquityOrder_Creation_Response,"Verify_Equity_Order_Active_Open_Response");}
			else{Assert.assertEquals(response.jsonPath().get("message"), EquityOrder_Creation_Response,"Verify_Equity_Order_Active_Open_Response");}
			APIHelperClass apihelper=new APIHelperClass();
			apihelper.GetOrderValues(Fetch_EquityOrder_BasePath,
									Global.getAccToken,
									Content_Type,
									Integer.parseInt(Fetch_EquityOrder_StatusCode),
									Endpoint_Version,
									Fetch_EquityOrder_UserID,
									Fetch_EquityOrder_ExpectedStatus,
									Fetch_EquityOrder_Symbol,
									Fetch_EquityOrder_Account,
									Fetch_EquityOrder_Destination,
									Fetch_EquityOrder_Price,
									Fetch_EquityOrder_Side,
									Fetch_EquityOrder_OrderQty,
									Fetch_EquityOrder_OrderType,"equity");

			LoggingManager.logger.info("API-Delete_EquityOrder_Order_ID : ["+Global.getOrderID+"]");
			LoggingManager.logger.info("API-Delete_EquityOrder_qOrderID : ["+Global.qOrderID+"]");
			if(Global.getOrderID == null || Global.getOrderID=="" )
			{
				Assert.fail("Logs : Order ID Match Not Found");
			}

			if(Global.qOrderID == null || Global.qOrderID==0 )
			{
				Assert.fail("Logs : qOrderID is not Found against OrderID ["+Global.getOrderID+"]");
			}
			Response delete_response=
							given()
							.header("Content-Type",Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)
							.pathParam("qOrderID", Global.qOrderID)

							.when()
							.delete(EquityOrder_Delete_BasePath.concat("{qOrderID}"))

							.then()
							.extract().response();

			LoggingManager.logger.info("API-EquityOrder_Delete_BasePath : ["+EquityOrder_Delete_BasePath.concat("{qOrderID}")+"]");
			LoggingManager.logger.info("API-EquityOrder_Delete_StatusCode : ["+delete_response.statusCode()+"]");
			LoggingManager.logger.info("API-EquityOrder_Delete_Response_Body : ["+delete_response.getBody().asString()+"]");
			Assert.assertEquals(delete_response.statusCode(),Integer.parseInt(EquityOrder_Delete_StatusCode), "Order Delete Response: ");
			if(Endpoint_Version.equalsIgnoreCase("V1")) {Assert.assertEquals(delete_response.getBody().asString(), EquityOrder_Delete_Response,"Verify_EquityOrder_Delete_Response");}
			else{Assert.assertEquals(delete_response.jsonPath().get("message"), EquityOrder_Delete_Response,"Verify_EquityOrder_Delete_Response");}
			Global.ValidationFlag=APIHelperClass.GetOrderValidate(Fetch_EquityOrder_BasePath,
																	Global.getAccToken,
																	Content_Type,
																	Integer.parseInt(EquityOrder_Delete_StatusCode),
																	Endpoint_Version,
																	Global.getOrderID,
																	Status_Validation);


			LoggingManager.logger.info("API-EquityOrder_Deleted_Order_ID : ["+Global.getOrderID+"]");
			LoggingManager.logger.info("API-EquityOrder_Deleted_ValidationFlag : ["+Global.ValidationFlag+"]");
			Assert.assertEquals(Global.ValidationFlag,Boolean.parseBoolean(Status_Validation_Flag), "Order Delete Validation");

		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}
}
