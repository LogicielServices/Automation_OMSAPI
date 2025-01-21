package ArchiveAPI;

import APIHelper.APIHelperClass;
import APIHelper.Global;
import APIHelper.LoggingManager;
import XLDataProvider.ExcelDataProvider;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.testng.Tag;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

import static APIHelper.APIHelperClass.ArchiveUserLoginAuthentications;

public class ArchiveLogin {

	@Owner("api.automation@mailinator.com")
	@Description("This is  Archive Login Positive TestCase")
	@Tag("Archive Endpoints")
	@Test (dataProvider="ArchiveUserLogin", dataProviderClass=ExcelDataProvider.class, groups={"ArchiveLogin"})
	public void ArchiveAuthorization(String ArchiveLogin_TestCases,
										  String ArchiveLogin_API_BasePath,
										  String Content_Type,
										  String Client_id,
										  String Client_secret,
									 	  String Username,
										  String Booth_id,
										  String Status_Code,
										  String Validate_Response_Fields)
	{
		LoggingManager.logger.info("====================================================================");
		LoggingManager.logger.info("TestCase : "+ArchiveLogin_TestCases);
		LoggingManager.logger.info("====================================================================");
		
		RestAssured.baseURI=Global.BaseURL;
		//APIHelperClass Adminuserlogin=new APIHelperClass();
		Global.getArchive_AccToken=ArchiveUserLoginAuthentications(ArchiveLogin_API_BasePath,
																		Content_Type,
																	    Client_id,
																	    Client_secret,
																		Username,
																		Booth_id,
																	    Status_Code,
																		null,
																		Validate_Response_Fields);
																 
		
		LoggingManager.logger.info("API-Response_ArchiveLogin_AccToken : ["+Global.getArchive_AccToken+"]");
		if(Global.getArchive_AccToken == null || Global.getArchive_AccToken=="" )
		{
			Assert.fail("Logs : Archive_AccToken is not created");
		}	
	}

	@Owner("api.automation@mailinator.com")
	@Description("This is  Archive Login Negative TestCase")
	@Tag("Archive Endpoints")
	@Test (dataProvider="ArchiveUserLoginNegative", dataProviderClass=ExcelDataProvider.class, groups={"ArchiveLoginNegative"})
	public void ArchiveAuthorizationNegativeCase(String ArchiveLogin_Negative_TestCases,
												 String ArchiveLogin_API_BasePath,
												 String Content_Type,
												 String Client_id,
												 String Client_secret,
												 String Username,
												 String Booth_id,
												 String Status_Code,
												 String Error_Message,
												 String Validate_Response_Fields)
	{
		LoggingManager.logger.info("====================================================================");
		LoggingManager.logger.info("TestCase : "+ArchiveLogin_Negative_TestCases);
		LoggingManager.logger.info("====================================================================");

		RestAssured.baseURI=Global.BaseURL;
		Global.getArchive_AccToken=ArchiveUserLoginAuthentications(ArchiveLogin_API_BasePath,
											Content_Type,
											Client_id,
											Client_secret,
											Username,
											Booth_id,
											Status_Code,
											Error_Message,
											Validate_Response_Fields);

	}
		
}
