package LocatesAPI;

import APIHelper.APIHelperClass;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import APIHelper.Global;
import APIHelper.LoggingManager;
import XLDataProvider.ExcelDataProvider;
import static io.restassured.RestAssured.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;


public class Locates_Acquire {

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
	
	@Test (dataProvider="Locates_Acquire", dataProviderClass=ExcelDataProvider.class,groups={"Locates_Acquire"}, dependsOnGroups={"Post_Locates"})
	public void Verify_Locates_Acquire(String Locates_Acquire_TestCase,
									   String Subscribe_Locates_BasePath,
									   String Content_Type,
									   String Subscribe_Locates_StatusCode,
									   String Summary_Locate_Subscribe_BasePath,
									   String Summary_Locate_Subscribe_StatusCode,
									   String Validate_SummaryID,
									   String Validate_SummaryBooth,
									   String Validate_OrdType,
									   String Validate_OrdStatus,
									   String Validate_OrderQty,
									   String Validate_OfferPx,
									   String Validate_OfferSize,
									   String Validate_CumQty,
									   String Validate_AvgPx,
									   String Validate_StatusDesc,
									   String Validate_Status,
									   String Validate_OrdRejReason,
									   String Validate_TransactionStatusString,
									   String Validate_TransactionStatus,
									   String Validate_TimeInForce,
									   String Validate_Text,
									   String Validate_Id,
									   String Validate_Symbol,
									   String Validate_SymbolSfx,
									   String Validate_ClientID,
									   String Validate_LocateType,
									   String Validate_Booth,
									   String Validate_Account,
									   String Validate_OriginatingUserDesc,
									   String Validate_Flag,
									   String Locates_Acquire_BasePath,
									   String Locates_Acquire_Symbol,
									   String Locates_Acquire_TimeInForce,
									   String Locates_Acquire_OrderQty,
									   String Locates_Acquire_Synchronous,
									   String Locates_Acquire_Account,
									   String Locates_Acquire_StatusCode,
									   String Validate_Acquired_OrdType,
									   String Validate_Acquired_OrdStatus,
									   String Validate_Acquired_OrderQty,
									   String Validate_Acquired_OfferPx,
									   String Validate_Acquired_OfferSize,
									   String Validate_Acquired_CumQty,
									   String Validate_Acquired_AvgPx,
									   String Validate_Acquired_StatusDesc,
									   String Validate_Acquired_Status,
									   String Validate_Acquired_OrdRejReason,
									   String Validate_Acquired_TransactionStatusString,
									   String Validate_Acquired_TransactionStatus,
									   String Validate_Acquired_TimeInForce,
									   String Validate_Acquired_Text,
									   String Validate_Acquired_Id,
									   String Validate_Acquired_Symbol,
									   String Validate_Acquired_SymbolSfx,
									   String Validate_Acquired_ClientID,
									   String Validate_Acquired_LocateType,
									   String Validate_Acquired_Booth,
									   String Validate_Acquired_Account,
									   String Validate_Acquired_OriginatingUserDesc)
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+Locates_Acquire_TestCase);
			LoggingManager.logger.info("====================================================================");
			RestAssured.baseURI=Global.BaseURL;
			//-------------------------------------------Subscribe Request--------------------------------------------------------------
			Response response=
					given()
							.header("Content-Type",Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)

							.when()
							.get(Subscribe_Locates_BasePath)

							.then()
							//.statusCode(Integer.parseInt(Subscribe_Locates_StatusCode))
							//.statusLine("HTTP/1.1 200 OK")
							.extract().response();

			LoggingManager.logger.info("API-Subscribe_Locates_BasePath : ["+Subscribe_Locates_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-Subscribe_Locates_StatusCode : ["+response.getStatusCode()+"]");
			Assert.assertEquals(response.statusCode(),Integer.parseInt(Subscribe_Locates_StatusCode), "Verify_Subscribe_Locates_StatusCode");
			APIHelperClass.Validate_Subscribe_Locates(  response,
														Summary_Locate_Subscribe_BasePath,
														Content_Type,
														Summary_Locate_Subscribe_StatusCode,
														Validate_SummaryID,
														Validate_SummaryBooth,
														Validate_OrdType,
														Validate_OrdStatus,
														Validate_OrderQty,
														Validate_OfferPx,
														Validate_OfferSize,
														Validate_CumQty,
														Validate_AvgPx,
														Validate_StatusDesc,
														Validate_Status,
														Validate_OrdRejReason,
														Validate_TransactionStatusString,
														Validate_TransactionStatus,
														Validate_TimeInForce,
														Validate_Text,
														Validate_Id,
														Validate_Symbol,
														Validate_SymbolSfx,
														Validate_ClientID,
														Validate_LocateType,
														Validate_Booth,
														Validate_Account,
														Validate_OriginatingUserDesc,
														Validate_Flag);

			Integer acqEtbQty=Global.getLocateEtbQty;
			LoggingManager.logger.info("API-Before Acquire EtbQty : ["+acqEtbQty+"]");

			//-------------------------------------------Acquire Locates--------------------------------------------------------------
			HashMap<String, Object> Locates_Acquire_Body=new HashMap<String, Object>();
			Locates_Acquire_Body.put("quoteReqID",Global.getLocateQuoteReqID);
			Locates_Acquire_Body.put("symbol",Locates_Acquire_Symbol);
			Locates_Acquire_Body.put("timeInForce",Locates_Acquire_TimeInForce);
			Locates_Acquire_Body.put("orderQty",Integer.parseInt(Locates_Acquire_OrderQty));
			Locates_Acquire_Body.put("synchronous",Integer.parseInt(Locates_Acquire_Synchronous));
			Locates_Acquire_Body.put("account",Locates_Acquire_Account);

			Response acquire_Response=	given()
					.header("Content-Type",Content_Type)
					.header("Authorization", "Bearer " + Global.getAccToken)
					.body(Locates_Acquire_Body)

					.when()
					.post(Locates_Acquire_BasePath)

					.then()
					//.statusCode(Integer.parseInt(Locates_Acquire_StatusCode))
					//.statusLine("HTTP/1.1 200 OK")
					.extract().response();

			LoggingManager.logger.info("API-Locates_Acquire_BasePath : ["+Locates_Acquire_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-Request Body : ["+Locates_Acquire_Body.toString()+"]");
			LoggingManager.logger.info("API-Subscribe_Locates_StatusCode : ["+acquire_Response.getStatusCode()+"]");
			LoggingManager.logger.info("API-Subscribe_Locates_StatusLine : ["+acquire_Response.getStatusLine()+"]");
			Assert.assertEquals(acquire_Response.statusCode(),Integer.parseInt(Locates_Acquire_StatusCode), "Verify_Locates_Acquire_StatusCode");
			acqEtbQty+=Integer.parseInt(Locates_Acquire_OrderQty);

			//-------------------------------------------Subscribe Request--------------------------------------------------------------
			Response validate_Subscriberesponse=
					given()
							.header("Content-Type",Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)

							.when()
							.get(Subscribe_Locates_BasePath)

							.then()
							//.statusCode(Integer.parseInt(Subscribe_Locates_StatusCode))
							//.statusLine("HTTP/1.1 200 OK")
							.extract().response();

			LoggingManager.logger.info("API-Subscribe_Locates_BasePath : ["+Subscribe_Locates_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-Subscribe_Locates_StatusCode : ["+validate_Subscriberesponse.getStatusCode()+"]");
			Assert.assertEquals(validate_Subscriberesponse.statusCode(),Integer.parseInt(Subscribe_Locates_StatusCode), "Verify_Subscribe_Locates_StatusCode");
			APIHelperClass.Validate_Acquired_Subscribe_Locates( validate_Subscriberesponse,
																Global.getLocateQuoteReqID,
																Validate_Acquired_OrdType,
																Validate_Acquired_OrdStatus,
																Validate_Acquired_OrderQty,
																Validate_Acquired_OfferPx,
																Validate_Acquired_OfferSize,
																Validate_Acquired_CumQty,
																Validate_Acquired_AvgPx,
																Validate_Acquired_StatusDesc,
																Validate_Acquired_Status,
																Validate_Acquired_OrdRejReason,
																Validate_Acquired_TransactionStatusString,
																Validate_Acquired_TransactionStatus,
																Validate_Acquired_TimeInForce,
																Validate_Acquired_Text,
																Validate_Acquired_Id,
																Validate_Acquired_Symbol,
																Validate_Acquired_SymbolSfx,
																Validate_Acquired_ClientID,
																Validate_Acquired_LocateType,
																Validate_Acquired_Booth,
																Validate_Acquired_Account,
																Validate_Acquired_OriginatingUserDesc,
																acqEtbQty);
		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}
}
