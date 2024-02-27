package LocatesAPI;

import APIHelper.APIHelperClass;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import org.apache.batik.css.engine.value.StringValue;
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


public class Subscribe_Locates {
	
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
	 
	@Test (dataProvider="Subscribe_Locates", dataProviderClass=ExcelDataProvider.class,groups={"Subscribe_Locates"}, dependsOnGroups={"Post_Locates"})
	public void Verify_Subscribe_Locates(String Subscribe_Locates_TestCase,
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
											String Validate_Flag)
	{
		try
		{
			LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+Subscribe_Locates_TestCase);
			LoggingManager.logger.info("====================================================================");

			RestAssured.baseURI=Global.BaseURL;
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

		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}
}
