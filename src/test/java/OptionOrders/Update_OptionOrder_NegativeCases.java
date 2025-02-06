package OptionOrders;

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

import static APIHelper.APIHelperClass.getserializedJsonObj;
import static io.restassured.RestAssured.given;


public class Update_OptionOrder_NegativeCases {

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
	
	@Test (dataProvider="OptionOrderUpdationNegative", dataProviderClass=ExcelDataProvider.class, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_Update_Option_Order_Negative(	String OptionOrder_Update_TestCase,
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
														String Option_qOrderid_flag,
														String OptionOrder_Update_BasePath,
														String OptionOrder_Update_Body,
														String OptionOrder_Update_StatusCode,
														String Validation_fieldname,
														String OptionOrder_Update_ErrorResponse)
		{
		try
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
			APIHelperClass.GetOptionOrder(	Subscribe_OptionOrder_BasePath,
											Global.getAccToken,
											Content_Type,
											Integer.parseInt(Subscribe_OptionOrder_StatusCode),
											Endpoint_Version,
											Subscribe_OptionOrder_UserID,
											Subscribe_OptionOrder_Text);

			LoggingManager.logger.info("API-Fetch_Order_ID : ["+Global.getOptionOrderID+"]");
			if(Global.getOptionOrderID == null || Global.getOptionOrderID=="" )
			{
				Assert.fail("Logs :Option Order ID Match Not Found");
			}
			String UpdateOptionOrderBody="";
		/*	HashMap<Object, Object> Update_OrderOption_Body=new HashMap<Object, Object>();
			Update_OrderOption_Body.put("orderId", Global.getOptionOrderID);
			Update_OrderOption_Body.put("OrdType",OptionOrder_Update_OrdType);
			Update_OrderOption_Body.put("OrderQty", Integer.parseInt(OptionOrder_Update_OrderQty));
			Update_OrderOption_Body.put("Price",Integer.parseInt(OptionOrder_Update_Price));
			Update_OrderOption_Body.put("ComplianceID", OptionOrder_Update_ComplianceID);
*/
			if (Boolean.parseBoolean(Option_qOrderid_flag)==true)
			{UpdateOptionOrderBody="{\"qOrderID\": " + Global.getOptionqOrderID +","+OptionOrder_Update_Body;}
			else
			{UpdateOptionOrderBody="{\"orderId\":\"" + Global.getOptionOrderID + "\", \"qOrderID\": " + Global.getOptionqOrderID +","+OptionOrder_Update_Body;}

			Response update_response=

					given()
							.header("Content-Type",Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)
							.body(UpdateOptionOrderBody)

							.when()
							.put(OptionOrder_Update_BasePath)

							.then()
							.extract().response();

			LoggingManager.logger.info("API-Update_OptionOrder_basePath : ["+OptionOrder_Update_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-Update_OptionOrder_Body : ["+OptionOrder_Update_Body+"]");
			LoggingManager.logger.info("API-Update_OptionOrder_Creation_StatusCode : ["+update_response.getStatusCode()+"]");
			LoggingManager.logger.info("API-Update_OptionOrder_Response_Body : ["+update_response.getBody().asPrettyString()+"]");
			Assert.assertEquals(update_response.getStatusCode(), Integer.parseInt(OptionOrder_Update_StatusCode),"Verify_Update_Option_Order");

			LoggingManager.logger.info("API-Update_OptionOrder_Error_Response : ["+getserializedJsonObj(update_response, Validation_fieldname)+"]");
			Assert.assertEquals(update_response.getStatusCode(), Integer.parseInt(OptionOrder_Update_StatusCode),"Verify_Update_OptionOrder_ResponseCode");
			Assert.assertEquals(getserializedJsonObj(update_response, Validation_fieldname), OptionOrder_Update_ErrorResponse,"Verify_OptionOrder_Update_ErrorResponse");


		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}
}
