package ArchiveAPI;

import APIHelper.APIHelperClass;
import APIHelper.Global;
import APIHelper.LoggingManager;
import XLDataProvider.ExcelDataProvider;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;

import static APIHelper.APIHelperClass.ArchiveUserLoginAuthentications;

public class ArchiveLogin {

		
	@Test (dataProvider="ArchiveUserLogin", dataProviderClass=ExcelDataProvider.class, groups={"ArchiveLogin"})
	public void ArchiveAuthorization(String ArchiveLogin_TestCases,
										  String ArchiveLogin_API_BasePath,
										  String Content_Type,
										  String Client_id,
										  String Client_secret,
									 	  String Username,
										  String Booth_id,
										  String Status_Code )
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
																	    Status_Code);
																 
		
		LoggingManager.logger.info("API-Response_ArchiveLogin_AccToken : ["+Global.getArchive_AccToken+"]");
		if(Global.getArchive_AccToken == null || Global.getArchive_AccToken=="" )
		{
			Assert.fail("Logs : Archive_AccToken is not created");
		}	
	}
	
		
}
