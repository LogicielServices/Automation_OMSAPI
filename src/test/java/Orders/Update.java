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
import java.util.HashMap;


public class Update {

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
	@Test (dataProvider="EquityOrderUpdation", dataProviderClass=ExcelDataProvider.class,groups={"UpdateEquityOrder"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_Update_Equity_Order(	String EquityOrder_TestCase,
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
										    String EquityOrder_Update_BasePath,
										    String EquityOrder_Update_OrdType,
										    String EquityOrder_Update_OrderQty,
										    String EquityOrder_Update_Price,
										    String EquityOrder_Update_StatusCode,
										    String EquityOrder_Update_Response,
										    String UpdateFlag)
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
		
		LoggingManager.logger.info("API-Fetch_Order_ID : ["+Global.getOrderID+"]");
		
		
		if(Global.getOrderID == null || Global.getOrderID=="" ) 
		{
			Assert.fail("Logs : Order ID Match Not Found"); 
		}
	
		
		String Update_equityorders_basePath=EquityOrder_Update_BasePath;
		HashMap<Object, Object> Update_equityOrder_Body=new HashMap<Object, Object>(); 
		
		Update_equityOrder_Body.put("ordType",EquityOrder_Update_OrdType); 
		Update_equityOrder_Body.put("orderID", Global.getOrderID);
		Update_equityOrder_Body.put("orderQty",Double.parseDouble(EquityOrder_Update_OrderQty));   
	//	Update_equityOrder_Body.put("price",Double.parseDouble(EquityOrder_Update_Price));
		
		Response update_response=
		
			given()	
					.header("Content-Type",Content_Type) 
					.header("Authorization", "Bearer " + Global.getAccToken)
					.body(Update_equityOrder_Body)
						
			.when()
					.put(Update_equityorders_basePath)	
			.then()
					.extract().response();
		
		LoggingManager.logger.info("API-Update_equityorders_basePath : ["+Update_equityorders_basePath+"]");
		LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
		LoggingManager.logger.info("API-Update_EquityOrder_Body : ["+Update_equityOrder_Body+"]");
		LoggingManager.logger.info("API-Update_EquityOrder_Creation_StatusCode : ["+update_response.getStatusCode()+"]");
		LoggingManager.logger.info("API-Update_EquityOrder_Response_Body : ["+update_response.getBody().asString()+"]");
		Assert.assertEquals(update_response.getStatusCode(), Integer.parseInt(EquityOrder_Update_StatusCode),"Verify_Update_Equity_Order");
		if(Endpoint_Version.equalsIgnoreCase("V1")) {Assert.assertEquals(update_response.getBody().asString(), EquityOrder_Update_Response,"Verify_EquityOrder_Update_Response");}
		else{Assert.assertEquals(update_response.jsonPath().get("message"), EquityOrder_Update_Response,"Verify_EquityOrder_Update_Response");}
		
		Global.ValidationFlag=APIHelperClass.UpdateOrderValidate(Fetch_EquityOrder_BasePath,
															Global.getAccToken,
															Content_Type,
															Integer.parseInt(EquityOrder_Update_StatusCode),
															Endpoint_Version,
															Global.getOrderID,
															EquityOrder_Update_OrdType,
															EquityOrder_Update_OrderQty,
															EquityOrder_Update_Price);
		
		LoggingManager.logger.info("API-Updated_EquityOrde_Order_ID : ["+Global.getOrderID+"]");
		LoggingManager.logger.info("API-Updated_EquityOrde_ValidationFlag : ["+Global.ValidationFlag+"]");
		Assert.assertEquals(Global.ValidationFlag,Boolean.parseBoolean(UpdateFlag),"Order Update Validation");
		
	}	
}
