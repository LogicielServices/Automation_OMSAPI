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

import static APIHelper.APIHelperClass.getserializedJsonObj;
import static io.restassured.RestAssured.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;


public class Cancel_OptionOrder {

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
	
	@Test (dataProvider="OptionOrderCancellation", dataProviderClass=ExcelDataProvider.class, groups={"OptionOrderCancellation"},dependsOnGroups={"UserLoginAuthentications"}) //CreateOptionOrderUserLoginAuthentications
	public void Verify_Cancel_Option_Order(String OptionOrder_Cancel_TestCase,
			 							   String Endpoint_Version,
			 							   String OptionOrder_Creation_BasePath,
										   String Content_Type ,
										   String OptionOrder_Creation_Body,
										   String OptionOrder_Creation_StatusCode,
										   String OptionOrder_Creation_Response,
										   String Subscribe_OptionOrder_BasePath,
										   String Subscribe_OptionOrder_UserID,
										   String Subscribe_OptionOrder_Text,
										   String Subscribe_OptionOrder_StatusCode,
										   String Subscribe_OptionOrder_ExpectedStatus,
										   String OptionOrder_Cancel_BasePath,
										   String OptionOrder_Cancel_StatusCode,
										   String Validation_Fieldname,
										   String Order_Cancel_Response,
										   String Status_Validation,
										   String StatusDesc_Validation)
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+OptionOrder_Cancel_TestCase);
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

			APIHelperClass.GetOptionOrder(	Subscribe_OptionOrder_BasePath,
					Global.getAccToken,
					Content_Type,
					Integer.parseInt(Subscribe_OptionOrder_StatusCode),
					Endpoint_Version,
					Subscribe_OptionOrder_UserID,
					Subscribe_OptionOrder_Text);

			LoggingManager.logger.info("API-Fetch_OptionOrderID : ["+Global.getOptionOrderID+"]");
			if(Global.getOptionOrderID == null || Global.getOptionOrderID=="" )
			{
				Global.getOptionOrderID="Invalid OrderID";
				//Assert.fail("Logs :Option Order ID Match Not Found");
			}

			//String cancel_OrderOption_Body="{\r\n\"orderId\": \""+getOrderID+"\"\r\n}";
			HashMap<Object,Object> cancel_OrderOption_Body=new HashMap<Object,Object>();
			cancel_OrderOption_Body.put("orderId", Global.getOptionOrderID);
			Response cancel_response=
					given()
							.header("Content-Type",Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)
							.body(cancel_OrderOption_Body)

							.when()
							.patch(OptionOrder_Cancel_BasePath)

							.then()
							//.statusCode(Integer.parseInt(OptionOrder_Cancel_StatusCode))
							//.statusLine("HTTP/1.1 200 OK")
							.extract().response();

			LoggingManager.logger.info("API-OptionOrder_Cancel_BasePath : ["+OptionOrder_Cancel_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-Cancel_OptionOrder_Body : ["+cancel_OrderOption_Body+"]");
			LoggingManager.logger.info("API-Cancel_OptionOrder_StatusCode : ["+cancel_response.getStatusCode()+"]");
			LoggingManager.logger.info("API-Cancel_OptionOrder_Response_Body : ["+cancel_response.getBody().asPrettyString()+"]");
			LoggingManager.logger.info("API-Cancel_OptionOrder_Response : ["+getserializedJsonObj(cancel_response,Validation_Fieldname)+"]");
			Assert.assertEquals(cancel_response.getStatusCode(), Integer.parseInt(OptionOrder_Cancel_StatusCode),"Verify_Cancel_OptionOrder_ResponseCode");
			Assert.assertEquals(getserializedJsonObj(cancel_response, Validation_Fieldname), Order_Cancel_Response,"Verify_OptionOrder_Cancel_Response");
/*
			APIHelperClass.GetCancelledOrder(Subscribe_OptionOrder_BasePath,
												Global.getAccToken,
												Content_Type,
												Integer.parseInt(Subscribe_OptionOrder_StatusCode),
												Endpoint_Version,
												Global.getOptionOrderID,
												Status_Validation,
												StatusDesc_Validation);

			LoggingManager.logger.info("API-Cancelled_OrderID : ["+Global.getOptionOrderID+"]");
			LoggingManager.logger.info("API-After Cancel Option OrderID_Status : ["+Global.getStatus+"]");
 */
		/*	Global.ValidationFlag=APIHelperClass.GetOptionOrderValidate(   Fetch_OptionOrder_BasePath,
																			Global.getAccToken,
																			Content_Type,
																			Integer.parseInt(Fetch_OptionOrder_StatusCode),
																			Endpoint_Version,
																			Global.getOptionOrderID,
																			Status_Validation);

			LoggingManager.logger.info("API-Cancel_OptionOrder_OrderID : ["+Global.getOptionOrderID+"]");
			LoggingManager.logger.info("API-Cancel_OptionOrder_ValidationFlag : ["+Global.ValidationFlag+"]");
			Assert.assertEquals(Global.ValidationFlag ,Boolean.parseBoolean(Status_Validation_Flag),"Order Cancel Validation");
*/

		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}
}
	
	

