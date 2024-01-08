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
import java.util.HashMap;


public class Update_OptionOrder {

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
	
	@Test (dataProvider="OptionOrderUpdation", dataProviderClass=ExcelDataProvider.class, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_Update_Option_Order(	String OptionOrder_Update_TestCase,
			 								String Endpoint_Version,
			 								String OptionOrder_Creation_BasePath,
										    String Content_Type ,
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
										    String OptionOrder_Update_BasePath,
										    String OptionOrder_Update_OrdType,
										    String OptionOrder_Update_OrderQty,
										    String OptionOrder_Update_Price,
										    String OptionOrder_Update_ComplianceID,
										    String OptionOrder_Update_StatusCode,
										    String UpdateFlag
										    
										   )
	{
		LoggingManager.logger.info("====================================================================");
		LoggingManager.logger.info("TestCase : "+OptionOrder_Update_TestCase);
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
	
		LoggingManager.logger.info("API-Fetch_Order_ID : ["+Global.getOptionOrderID+"]");
		if(Global.getOptionOrderID == null || Global.getOptionOrderID=="" ) 
		{
			Assert.fail("Logs :Option Order ID Match Not Found"); 
		}
		//String Update_optionorders_basePath=OptionOrder_Update_BasePath;
		HashMap<Object, Object> Update_OrderOption_Body=new HashMap<Object, Object>(); 
		Update_OrderOption_Body.put("orderId", Global.getOptionOrderID);
		Update_OrderOption_Body.put("OrdType",OptionOrder_Update_OrdType); 
		Update_OrderOption_Body.put("OrderQty", Integer.parseInt(OptionOrder_Update_OrderQty));
		Update_OrderOption_Body.put("Price",Integer.parseInt(OptionOrder_Update_Price)); 
		Update_OrderOption_Body.put("ComplianceID", OptionOrder_Update_ComplianceID);
									
		Response update_response=
		
								given()	
										.header("Content-Type",Content_Type) 
										.header("Authorization", "Bearer " + Global.getAccToken)
										.body(Update_OrderOption_Body)
										
								.when()
										.put(OptionOrder_Update_BasePath)
									
								.then()
										.extract().response();
		
		LoggingManager.logger.info("API-Update_OptionOrder_basePath : ["+OptionOrder_Update_BasePath+"]");
		LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
		LoggingManager.logger.info("API-Update_OptionOrder_Body : ["+Update_OrderOption_Body+"]");
		LoggingManager.logger.info("API-Update_OptionOrder_Creation_StatusCode : ["+update_response.getStatusCode()+"]");
		LoggingManager.logger.info("API-Update_OptionOrder_Response_Body : ["+update_response.getBody().asPrettyString()+"]");
		Assert.assertEquals(update_response.getStatusCode(), Integer.parseInt(OptionOrder_Update_StatusCode),"Verify_Update_Option_Order");
		Global.ValidationFlag=APIHelperClass.UpdateOptionOrderValidate( Fetch_OptionOrder_BasePath,
																		Global.getAccToken,
																		Content_Type,
																		Integer.parseInt(Fetch_OptionOrder_StatusCode),
																		Endpoint_Version,
																		Global.getOptionOrderID,
																		OptionOrder_Update_OrdType,
																	    OptionOrder_Update_OrderQty,
																	    OptionOrder_Update_Price);
		
		LoggingManager.logger.info("API-Updated_OptionOrder_Order_ID : ["+Global.getOptionOrderID+"]");
		LoggingManager.logger.info("API-Updated_OptionOrder_ValidationFlag : ["+Global.ValidationFlag+"]");
		Assert.assertEquals(Global.ValidationFlag,Boolean.parseBoolean(UpdateFlag), "Order Update Validation");
			
	}
	
}
