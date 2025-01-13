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

import static io.restassured.RestAssured.given;


public class Update_Orders {

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
	@Test (dataProvider="UpdateEquityOrder", dataProviderClass=ExcelDataProvider.class,groups={"UpdateEquityOrder"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_Update_Equity_Order(	String Update_EquityOrder_TestCase,
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
											String Subscribe_Order_ExpectedStatus,
											String qOrderid_flag,
										    String Order_Update_BasePath,
											String Order_Update_Body,
										    String Order_Update_OrdType,
										    String Order_Update_OrderQty,
											String Order_Update_MaxFloor,
										    String Order_Update_Price,
											String Order_Update_StopPrice,
										    String Order_Update_StatusCode,
										    String Order_Update_Response)
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+Update_EquityOrder_TestCase);
			LoggingManager.logger.info("====================================================================");
			RestAssured.baseURI=Global.BaseURL;
			String UpdateOrderBody="";

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
			if(EndpointVersion.equalsIgnoreCase("V1")) {Assert.assertEquals(response.getBody().asString(), Order_Creation_Response,"Verify_Equity_Order_Response");}
			else{Assert.assertEquals(response.jsonPath().get("message"), Order_Creation_Response,"Verify_Equity_Order_Response");}

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

			HashMap<Object, Object> Update_equityOrder_Body=new HashMap<Object, Object>();
			Update_equityOrder_Body.put("ordType",Order_Update_OrdType);
			Update_equityOrder_Body.put("orderID", Global.getOrderID);
			Update_equityOrder_Body.put("orderQty",Double.parseDouble(Order_Update_OrderQty));
			Update_equityOrder_Body.put("maxFloor",Integer.parseInt(Order_Update_MaxFloor));
			Update_equityOrder_Body.put("price",Double.parseDouble(Order_Update_Price));
			Update_equityOrder_Body.put("stopPx",Double.parseDouble(Order_Update_StopPrice));

			if (Boolean.parseBoolean(qOrderid_flag)==true)
			{UpdateOrderBody="{\"qOrderID\": " + Global.qOrderID +","+Order_Update_Body;}
			else
			{UpdateOrderBody="{\"orderId\":\"" + Global.getOrderID + "\", \"qOrderID\": " + Global.qOrderID +","+Order_Update_Body;}
			Response update_response=

							given()
							.header("Content-Type",Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)
							.body(UpdateOrderBody)

							.when()
							.put(Order_Update_BasePath)
							.then()
							.extract().response();

			LoggingManager.logger.info("API-Update_EquityOrders_basePath : ["+Order_Update_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-Update_EquityOrder_Body : ["+Update_equityOrder_Body+"]");
			LoggingManager.logger.info("API-Update_EquityOrder_Creation_StatusCode : ["+update_response.getStatusCode()+"]");
			LoggingManager.logger.info("API-Update_EquityOrder_Response_Body : ["+update_response.getBody().asString()+"]");

			Assert.assertEquals(update_response.getStatusCode(), Integer.parseInt(Order_Update_StatusCode),"Verify_Update_Equity_Order");
			if(EndpointVersion.equalsIgnoreCase("V1")) {Assert.assertEquals(update_response.getBody().asString(), Order_Update_Response,"Verify_EquityOrder_Update_Response");}
			else{Assert.assertEquals(update_response.jsonPath().get("message"), Order_Update_Response,"Verify_EquityOrder_Update_Response");}
			APIHelperClass.GetUpdatedOrder(Subscribe_Order_BasePath,
											Global.getAccToken,
											Content_Type,
											Integer.parseInt(Subscribe_Order_StatusCode),
											EndpointVersion,
											Global.getOrderID,
											Order_Update_OrdType,
											Order_Update_OrderQty,
											Order_Update_MaxFloor,
											Order_Update_Price,
											Order_Update_StopPrice);
		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}
}
