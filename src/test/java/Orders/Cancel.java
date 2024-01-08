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
import groovy.util.logging.Log4j;

import static io.restassured.RestAssured.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;


public class Cancel {

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
	@Test (dataProvider="EquityOrderCancellation", dataProviderClass=ExcelDataProvider.class,groups={"CancelEquityOrder"}, dependsOnGroups={"UserLoginAuthentications"})//CreateEquityOrder
	public void Verify_Cancel_Equity_Order(String EquityOrder_TestCase,
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
										   String Fetch_EquityOrder_Symbol,
										   String Fetch_EquityOrder_Account,										   
										   String Fetch_EquityOrder_Destination,
										   String Fetch_EquityOrder_Price,
										   String Fetch_EquityOrder_OrderQty,
										   String Fetch_EquityOrder_ExpectedStatus,
										   String Fetch_EquityOrder_StatusCode,
										   String EquityOrder_Cancel_BasePath,
										   String EquityOrder_Cancel_StatusCode,
										   String EquityOrder_Cancel_Response,
										   String Status_Validation,
										   String Status_Validation_Flag)
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
													Fetch_EquityOrder_Account,
													Fetch_EquityOrder_Symbol,
													Fetch_EquityOrder_Destination,
													Fetch_EquityOrder_Price,
													Fetch_EquityOrder_Side,
													Fetch_EquityOrder_OrderQty,
													Fetch_EquityOrder_OrderType,"equity");
				
		
		System.out.println("Verify_Cancel_Equity_order = [ "+Global.getOrderID+" ]");
		LoggingManager.logger.info("API-Cancel_EquityOrder_OrderID : ["+Global.getOrderID+"]");
		HashMap<Object, Object> cancel_EquityOrder_Body=new HashMap<Object, Object>(); 
		cancel_EquityOrder_Body.put("orderId", Global.getOrderID);
		Response Cacncel_response=
							given()	
								.header("Content-Type",Content_Type) 
								.header("Authorization", "Bearer " + Global.getAccToken)
								.body(cancel_EquityOrder_Body)
								
							.when()
								.patch(EquityOrder_Cancel_BasePath)
								
							.then()
								//.statusCode(Integer.parseInt(EquityOrder_Cancel_StatusCode))
								//.statusLine("HTTP/1.1 200 OK")
								.extract().response();
		
		LoggingManager.logger.info("API-EquityOrder_Cancel_BasePath : ["+EquityOrder_Cancel_BasePath+"]");
		LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
		LoggingManager.logger.info("API-Cancel_EquityOrder_Body : ["+cancel_EquityOrder_Body+"]");
		LoggingManager.logger.info("API-Cancel_EquityOrder_StatusCode : ["+Cacncel_response.getStatusCode()+"]");
		LoggingManager.logger.info("API-Cancel_EquityOrder_Response_Body : ["+Cacncel_response.getBody().asString()+"]");
		
		Assert.assertEquals(Cacncel_response.statusCode(),Integer.parseInt(EquityOrder_Cancel_StatusCode), "Order Cancel Response");
		if(Endpoint_Version.equalsIgnoreCase("V1")) {Assert.assertEquals(Cacncel_response.getBody().asString(), EquityOrder_Cancel_Response,"Verify_EquityOrder_Cancel_Response");}
		else{Assert.assertEquals(Cacncel_response.jsonPath().get("message"), EquityOrder_Cancel_Response,"Verify_EquityOrder_Cancel_Response");}
		
						
		Global.ValidationFlag=APIHelperClass.GetOrderValidate(Fetch_EquityOrder_BasePath,
														 Global.getAccToken,
		    										     Content_Type,
													     Integer.parseInt(EquityOrder_Cancel_StatusCode),
													     Endpoint_Version,
													     Global.getOrderID,
													     Status_Validation);
		  
		
		LoggingManager.logger.info("API-Cancel_EquityOrder_ValidationFlag : ["+Global.ValidationFlag+"]");
		LoggingManager.logger.info("API-Cancelled_OrderID : ["+Global.getOrderID+"]");
		LoggingManager.logger.info("API-AFter Cancel OrderID_Status : ["+Global.getStatus+"]");
		Assert.assertEquals(Global.ValidationFlag, Boolean.parseBoolean(Status_Validation_Flag), "Order Cancel Validation");
		
	}	
}
