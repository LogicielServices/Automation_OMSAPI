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

import static io.restassured.RestAssured.given;


public class Order_Positions {

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
	

	 @Test (dataProvider="OrderPositions", dataProviderClass=ExcelDataProvider.class , groups={"Order_Positions"}, dependsOnGroups={"UserLoginAuthentications"})
	 public void Verify_Order_Positions(String OrderPositions_TestCase,
			 												 String EndpointVersion,
															 String Order_Creation_BasePath,
															 String Content_Type ,
															 String Order_Creation_Body,
															 String Order_Creation_StatusCode,
															 String Order_Creation_Response,
															 String Subscribe_Order_BasePath,
															 String Subscribe_Order_StatusCode,
															 String Subscribe_Order_UserID,
															 String Subscribe_Order_ExpectedStatus,
															 String Subscribe_Order_Text,
															 String Subscribe_Order_Positions_BasePath,
															 String Subscribe_Order_Positions_StatusCode,
															 String PositionID,
															 String Validate_Position_CompleteDayBuyOrderQty,
															 String Validate_Position_CompleteDaySellLongOrderQty,
															 String Validate_Position_CompleteDaySellShortOrderQty,
															 String Validate_Position_ExecQty,
															 String Validate_Position_Symbol_Value,
															 String Validate_Position_symbolSfx_Value,
															 String Validate_Position_originatingUserDesc_Value,
															 String Validate_Position_positionString_Value,
															 String Validate_Position_Account_Value,
															 String Validate_Position_isOptionTrade)

		{
			try
			{
				LoggingManager.logger.info("====================================================================");
				LoggingManager.logger.info("TestCase : "+OrderPositions_TestCase);
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

				LoggingManager.logger.info("API-Order_Creation_BasePath : ["+Order_Creation_BasePath+"]");
				LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
				LoggingManager.logger.info("API-Order_Creation_Body : ["+Order_Creation_Body+"]");
				LoggingManager.logger.info("API-Order_Creation_StatusCode : ["+response.getStatusCode()+"]");
				LoggingManager.logger.info("API-Order_Creation_Response : ["+response.getBody().asString()+"]");
				Assert.assertEquals(response.getStatusCode(), Integer.parseInt(Order_Creation_StatusCode),"Verify_Equity_Order_Creation");
				if(EndpointVersion.equalsIgnoreCase("V1")) {Assert.assertEquals(response.getBody().asString(), Order_Creation_Response,"Verify_Order_Creation_Response");}
				else{Assert.assertEquals(response.jsonPath().get("message"), Order_Creation_Response,"Verify_Order_Creation_Response");}

				//------------------------------------------Get OrderID-------------------------

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

				//------------------------------------------Validate Positions Against Accounts-------------------------

				Response get_position_response=
						given()
								.header("Content-Type",Content_Type)
								.header("Authorization", "Bearer " + Global.getAccToken)
								.when()
								.get(Subscribe_Order_Positions_BasePath)
								.then().extract().response();

				LoggingManager.logger.info("API-Order_Position_BasePath : ["+Subscribe_Order_Positions_BasePath+"]");
				LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
				LoggingManager.logger.info("API-Order_Position_StatusCode : ["+get_position_response.getStatusCode()+"]");
				LoggingManager.logger.info("API-Order_Position_Response : ["+response.getBody().asString()+"]");
				Assert.assertEquals(get_position_response.getStatusCode(), Integer.parseInt(Subscribe_Order_Positions_StatusCode),"Verify_Equity_Position");

				APIHelperClass.Validate_Get_Positions(get_position_response,
														EndpointVersion,
														PositionID,
														Validate_Position_CompleteDayBuyOrderQty,
														Validate_Position_CompleteDaySellLongOrderQty,
														Validate_Position_CompleteDaySellShortOrderQty,
														Validate_Position_ExecQty,
														Validate_Position_Symbol_Value,
														Validate_Position_symbolSfx_Value,
														Validate_Position_originatingUserDesc_Value,
														Validate_Position_positionString_Value,
														Validate_Position_Account_Value,
														Validate_Position_isOptionTrade);


			}
			catch (Exception e)
			{
				LoggingManager.logger.error(e);
			}
		}
}
