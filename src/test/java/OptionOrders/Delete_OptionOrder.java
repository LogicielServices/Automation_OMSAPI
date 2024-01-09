package OptionOrders;

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


public class Delete_OptionOrder {

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
	
	@Test (dataProvider="DeleteOptionOrders", dataProviderClass=ExcelDataProvider.class, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_Delete_Option_Order(String OptionOrder_Delete_TestCase,
			 							   String Endpoint_Version,
			 							   String OptionOrder_Creation_BasePath,
										   String Content_Type,
										   String OptionOrder_Creation_Body,
										   String OptionOrder_Creation_StatusCode,
										   String OptionOrder_Creation_Response,
			 							   String Fetch_OptionOrder_BasePath,
										   String Fetch_OptionOrder_UserID,
										   String Fetch_OptionOrder_OrderType,
										   String Fetch_OptionOrder_Side,
										   String Fetch_OptionOrder_Symbol,
										   String Fetch_OptionOrder_Account,
										   String Fetch_OptionOrder_Destination,
										   String Fetch_OptionOrder_Price,
										   String Fetch_OptionOrder_OrderQty,
										   String Fetch_OptionOrder_ExpectedStatus,
										   String Fetch_OptionOrder_StatusCode,
										   String OptionOrder_Delete_BasePath,
										   String OptionOrder_Delete_StatusCode,
										   String Status_Validation,
										   String Status_Validation_Flag ) throws InterruptedException
	{
		LoggingManager.logger.info("====================================================================");
		LoggingManager.logger.info("TestCase : "+OptionOrder_Delete_TestCase);
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
		Assert.assertEquals(response.getStatusCode(),Integer.parseInt(OptionOrder_Creation_StatusCode),"Order_Creation_Active_Open");
		if(Endpoint_Version.equalsIgnoreCase("V1")) {Assert.assertEquals(response.getBody().asString(), OptionOrder_Creation_Response,"Verify_OptionOrder_Response");}
		else{Assert.assertEquals(response.jsonPath().get("message"), OptionOrder_Creation_Response,"Verify_OptionOrder_Response");}
		APIHelperClass apihelper=new APIHelperClass();
		apihelper.GetOrderValues(Fetch_OptionOrder_BasePath,
													Global.getAccToken,
													Content_Type,
													Integer.parseInt(Fetch_OptionOrder_StatusCode),
													Endpoint_Version,
													Fetch_OptionOrder_UserID,
													Fetch_OptionOrder_ExpectedStatus,
													Fetch_OptionOrder_Account,
													Fetch_OptionOrder_Symbol,
													Fetch_OptionOrder_Destination,
													Fetch_OptionOrder_Price,
													Fetch_OptionOrder_Side,
													Fetch_OptionOrder_OrderQty,
													Fetch_OptionOrder_OrderType,"option");
		
		LoggingManager.logger.info("API-Delete_OptionOrder_OrderID : ["+Global.getOptionOrderID+"]");
		
		if(Global.getOptionOrderID == null || Global.getOptionOrderID=="" ) 
		{
			Assert.fail("Logs : Order ID Match Not Found"); 
		}
		
		apihelper.GetOrderValues (Fetch_OptionOrder_BasePath,
													Global.getAccToken,
													Content_Type,
													Integer.parseInt(Fetch_OptionOrder_StatusCode),
													Endpoint_Version,
													Fetch_OptionOrder_UserID,
													Fetch_OptionOrder_ExpectedStatus,
													Fetch_OptionOrder_Account,
													Fetch_OptionOrder_Symbol,
													Fetch_OptionOrder_Destination,
													Fetch_OptionOrder_Price,
													Fetch_OptionOrder_Side,
													Fetch_OptionOrder_OrderQty,
													Fetch_OptionOrder_OrderType,"option");
		LoggingManager.logger.info("API-Delete_OptionOrder_qOrderID : ["+Global.getOptionqOrderID+"]");
		if(Global.getOptionqOrderID == null || Global.getOptionqOrderID==0 ) 
		{
			Assert.fail("Logs : qOrderID is not Found against OrderID ["+Global.getOptionqOrderID+"]"); 
		}
		
		Response delete_response=
							given()	
								  .header("Content-Type",Content_Type) 
								  .header("Authorization", "Bearer " + Global.getAccToken)
							.when()
								  .delete(OptionOrder_Delete_BasePath+Global.getOptionqOrderID)
								
							.then()
								   .extract().response();
		
		LoggingManager.logger.info("API-OptionOrder_Delete_BasePath : ["+OptionOrder_Delete_BasePath+Global.getOptionqOrderID+"]");
		LoggingManager.logger.info("API-OptionOrder_Delete_StatusCode : ["+delete_response.statusCode()+"]");
		LoggingManager.logger.info("API-OptionOrder_Delete_Response_Body : ["+delete_response.getBody().asPrettyString()+"]");
		Assert.assertEquals(delete_response.statusCode(),Integer.parseInt(OptionOrder_Delete_StatusCode), "Order Delete Response: ");
		
	
		Global.ValidationFlag=APIHelperClass.GetOptionOrderValidate(  Fetch_OptionOrder_BasePath,
																	  Global.getAccToken,
																	  Content_Type,
																	  Integer.parseInt(OptionOrder_Delete_StatusCode),
																	  Endpoint_Version,
																	  Global.getOptionOrderID,
																	  Status_Validation);
					
	   
		LoggingManager.logger.info("API-OptionOrder_Deleted_Order_ID : ["+Global.getOptionOrderID+"]");
		LoggingManager.logger.info("API-OptionOrder_Deleted_ValidationFlag : ["+Global.ValidationFlag+"]");
		Assert.assertEquals(Global.ValidationFlag,Boolean.parseBoolean(Status_Validation_Flag), "Order Delete Validation");
		
	}		
}
