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


public class Delete_Orders {

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
	@Test (dataProvider="DeleteEquityOrder", dataProviderClass=ExcelDataProvider.class,groups={"DeleteEquityOrder"}, dependsOnGroups={"UserLoginAuthentications"})//CreateEquityOrder
	public void Verify_Delete_Equity_Order(String Delete_EquityOrder_TestCase,
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
										   String Order_Delete_BasePath,
										   String Order_Delete_StatusCode,
										   String Validation_fieldname,
										   String Order_Delete_Response,
										   String Status_Validation,
										   String StatusDesc_Validation)
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+Delete_EquityOrder_TestCase);
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


			LoggingManager.logger.info("API-Delete_EquityOrder_Order_ID : ["+Global.getOrderID+"]");
			LoggingManager.logger.info("API-Delete_EquityOrder_qOrderID : ["+Global.qOrderID+"]");

			Response delete_response=
					given()
							.header("Content-Type",Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)
							.pathParam("qOrderID", Global.qOrderID)

							.when()
							.delete(Order_Delete_BasePath.concat("{qOrderID}"))

							.then()
							.extract().response();

			LoggingManager.logger.info("API-EquityOrder_Delete_BasePath : ["+Order_Delete_BasePath.concat("{qOrderID}")+"]");
			LoggingManager.logger.info("API-EquityOrder_Delete_StatusCode : ["+delete_response.statusCode()+"]");
			LoggingManager.logger.info("API-EquityOrder_Delete_Response_Body : ["+delete_response.getBody().asString()+"]");
			Assert.assertEquals(delete_response.statusCode(),Integer.parseInt(Order_Delete_StatusCode), "Order Delete Response: ");
			//if(EndpointVersion.equalsIgnoreCase("V1")) {Assert.assertEquals(delete_response.getBody().asString(), Order_Delete_Response,"Verify_EquityOrder_Delete_Response");}
			//else{Assert.assertEquals(delete_response.jsonPath().get("message"),Order_Delete_Response,"Verify_EquityOrder_Delete_Response");}

			LoggingManager.logger.info("API-Delete_Order_Response : ["+getserializedJsonObj(delete_response, Validation_fieldname)+"]");
			Assert.assertEquals(delete_response.getStatusCode(), Integer.parseInt(Order_Delete_StatusCode),"Verify_Delete_Order_ResponseCode");
			Assert.assertEquals(getserializedJsonObj(delete_response, Validation_fieldname), Order_Delete_Response,"Verify_Order_Delete_Response");

			APIHelperClass.GetCancelledOrder(Subscribe_Order_BasePath,
											Global.getAccToken,
											Content_Type,
											Integer.parseInt(Subscribe_Order_StatusCode),
											EndpointVersion,
											Global.getOrderID,
											Status_Validation,
											StatusDesc_Validation);

			LoggingManager.logger.info("API-Deleted_OrderID : ["+Global.getOrderID+"]");
			LoggingManager.logger.info("API-After Delete OrderID_Status : ["+Global.getStatus+"]");

		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}
}
