package Subscriptions;

import static io.restassured.RestAssured.given;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import APIHelper.APIHelperClass;
import APIHelper.Global;
import APIHelper.LoggingManager;
import XLDataProvider.ExcelDataProvider;
import io.restassured.RestAssured;
import io.restassured.response.Response;


public class Flat_Positions {
	
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

	@Test (dataProvider="Flat_Equity_Position", dataProviderClass=ExcelDataProvider.class , groups={"Flat_Equity_Position"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_Flat_Equity_Positions(String Flat_Position_TestCases,
												String EndpointVersion,
												String Subscribe_Order_Positions_BasePath,
												String Content_Type,
												String Subscribe_Order_Positions_StatusCode,
												String Account_Type_BoxvsShort,
												String PositionID,
												String Validate_Position_FLAT,
												String Order_Creation_BasePath,
												String Order_OrdType,
												String Order_TimeInForce,
												String Order_Destination,
												String Order_Price,
												String Order_StopPx,
												String Order_Creation_StatusCode )
	{
		try
		{
			LoggingManager.logger.info("====================================================================================");
			LoggingManager.logger.info("TestCase : "+Flat_Position_TestCases+" ["+PositionID+"]");
			LoggingManager.logger.info("====================================================================================");
			RestAssured.baseURI=Global.BaseURL;
			LoggingManager.logger.info("API- PositionID : "+PositionID);
			APIHelperClass.Flat_Equity_Positions(	EndpointVersion,
													Subscribe_Order_Positions_BasePath,
													Content_Type,
													Subscribe_Order_Positions_StatusCode,
													PositionID,
													Validate_Position_FLAT,
													Order_Creation_BasePath,
													Order_OrdType,
													Order_TimeInForce,
													Order_Destination,
													Order_Price,
													Order_StopPx,
													Order_Creation_StatusCode);

		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}
	
	
	
	
	@Test (dataProvider="Flat_Option_Position", dataProviderClass=ExcelDataProvider.class , groups={"Flat_Option_Position"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_Flat_Option_Positions(String Flat_Position_TestCases,
												String EndpointVersion,
												String Subscribe_Order_Positions_BasePath,
												String Content_Type,
												String Subscribe_Order_Positions_StatusCode,
												String Position_ID,
												String Validate_Position_FLAT,
												String Order_Creation_BasePath,
												String Order_OrdType,
												String Order_TimeInForce,
												String Order_Destination,
												String Order_Price,
												String Order_StopPx,
												String Order_PutOrCall,
												String Order_StrikePrice,
												String Order_CoveredOrUncovered,
												String Order_CustomerOrFirm,
												String Order_Cmta,
												String Order_OpenClose,
												String Order_ExpiryDate,
												String Order_Creation_StatusCode )
	{
		try
		{
			LoggingManager.logger.info("======================================================================================");
			LoggingManager.logger.info("TestCase : "+Flat_Position_TestCases+" ["+Position_ID+"]");
			LoggingManager.logger.info("======================================================================================");
			RestAssured.baseURI=Global.BaseURL;
			//String Position_ID=Validate_BoothID+"-"+Validate_Symbol_Value+" "+Validate_Mat_ExpDate+" "+Validate_StrikePrice_Value+" "+Validate_PutOrCall_Value+"-"+Validate_Account_Value;
			APIHelperClass.Flat_Option_Positions(	EndpointVersion,
													Subscribe_Order_Positions_BasePath,
													Content_Type,
													Subscribe_Order_Positions_StatusCode,
													Position_ID,
													Validate_Position_FLAT,
													Order_Creation_BasePath,
													Order_OrdType,
													Order_TimeInForce,
													Order_Destination,
													Order_Price,
													Order_StopPx,
													Order_PutOrCall,
													Order_StrikePrice,
													Order_CoveredOrUncovered,
													Order_CustomerOrFirm,
													Order_Cmta,
													Order_OpenClose,
													Order_ExpiryDate,
													Order_Creation_StatusCode );

		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}
}
