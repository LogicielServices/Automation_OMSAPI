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
import java.util.HashMap;

import static APIHelper.APIHelperClass.getserializedJsonObj;
import static io.restassured.RestAssured.given;


public class Cancel_Orders {

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
	@Test (dataProvider="CancelEquityOrder", dataProviderClass=ExcelDataProvider.class,groups={"CancelEquityOrder"}, dependsOnGroups={"UserLoginAuthentications"})//CreateEquityOrder
	public void Verify_Cancel_Equity_Order(String Cancel_EquityOrder_TestCase,
										   String EndpointVersion,
										   String Order_Creation_BasePath,
										   String Content_Type,
										   String Order_Creation_Body,
										   String Order_Creation_StatusCode,
										   String Order_Creation_Response,
										   String Subscribe_Order_BasePath,
										   String Subscribe_Order_StatusCode,
										   String Subscribe_Order_UserID,
										   String Subscribe_Order_Text,
										   String Order_Cancel_BasePath,
										   String Order_Cancel_StatusCode,
										   String Validation_fieldname,
										   String Order_Cancel_Response,
										   String Status_Validation,
										   String StatusDesc_Validation)
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+Cancel_EquityOrder_TestCase);
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

			LoggingManager.logger.info("API-EquityOrder_Creation_BasePath : ["+Order_Creation_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-EquityOrder_Creation_Body : ["+Order_Creation_Body+"]");
			LoggingManager.logger.info("API-EquityOrder_Creation_StatusCode : ["+response.getStatusCode()+"]");
			LoggingManager.logger.info("API-EquityOrder_Response_Body : ["+response.getBody().asString()+"]");
			Assert.assertEquals(response.getStatusCode(), Integer.parseInt(Order_Creation_StatusCode),"Verify_Equity_Order_Creation");
			if(EndpointVersion.equalsIgnoreCase("V1")) {Assert.assertEquals(response.getBody().asString(),Order_Creation_Response,"Verify_Equity_Order_Response");}
			else{Assert.assertEquals(response.jsonPath().get("message"), Order_Creation_Response,"Verify_Equity_Order_Response");}

			APIHelperClass.GetOrder(Subscribe_Order_BasePath,
											Global.getAccToken,
											Content_Type,
											Integer.parseInt(Subscribe_Order_StatusCode),
											EndpointVersion,
											Subscribe_Order_UserID,
											Subscribe_Order_Text,"equity");

			LoggingManager.logger.info("API-Cancel_EquityOrder_OrderID : ["+Global.getOrderID+"]");
			HashMap<Object, Object> cancel_EquityOrder_Body=new HashMap<Object, Object>();
			cancel_EquityOrder_Body.put("orderId", Global.getOrderID);
			Response cancel_response=
							given()
							.header("Content-Type",Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)
							.body(cancel_EquityOrder_Body)

							.when()
							.patch(Order_Cancel_BasePath)

							.then()
							//.statusCode(Integer.parseInt(EquityOrder_Cancel_StatusCode))
							//.statusLine("HTTP/1.1 200 OK")
							.extract().response();

			LoggingManager.logger.info("API-EquityOrder_Cancel_BasePath : ["+Order_Cancel_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-Cancel_EquityOrder_Body : ["+cancel_EquityOrder_Body+"]");
			LoggingManager.logger.info("API-Cancel_EquityOrder_StatusCode : ["+cancel_response.getStatusCode()+"]");
			LoggingManager.logger.info("API-Cancel_EquityOrder_Response_Body : ["+cancel_response.getBody().asString()+"]");

			Assert.assertEquals(cancel_response.statusCode(),Integer.parseInt(Order_Cancel_StatusCode), "Order Cancel Response");
			//if(EndpointVersion.equalsIgnoreCase("V1")) {Assert.assertEquals(cancel_response.getBody().asString(), Order_Cancel_Response,"Verify_EquityOrder_Cancel_Response");}
			//else{Assert.assertEquals(cancel_response.jsonPath().get("message"), Order_Cancel_Response,"Verify_EquityOrder_Cancel_Response");}
			LoggingManager.logger.info("API-Cancel_Order_Response : ["+getserializedJsonObj(cancel_response, Validation_fieldname)+"]");
			Assert.assertEquals(cancel_response.getStatusCode(), Integer.parseInt(Order_Cancel_StatusCode),"Verify_Cancel_Order_ResponseCode");
			Assert.assertEquals(getserializedJsonObj(cancel_response, Validation_fieldname), Order_Cancel_Response,"Verify_Order_Cancel_Response");


			APIHelperClass.GetCancelledOrder(Subscribe_Order_BasePath,
											Global.getAccToken,
											Content_Type,
											Integer.parseInt(Subscribe_Order_StatusCode),
											EndpointVersion,
											Global.getOrderID,
											Status_Validation,
											StatusDesc_Validation);

			LoggingManager.logger.info("API-Cancelled_OrderID : ["+Global.getOrderID+"]");
			LoggingManager.logger.info("API-After Cancel OrderID_Status : ["+Global.getStatus+"]");

		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}
}
