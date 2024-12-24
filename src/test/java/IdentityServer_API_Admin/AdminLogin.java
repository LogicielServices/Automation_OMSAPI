package IdentityServer_API_Admin;

import org.testng.Assert;
import org.testng.annotations.Test;

import APIHelper.APIHelperClass;
import APIHelper.Global;
import APIHelper.LoggingManager;
import XLDataProvider.ExcelDataProvider;
import io.restassured.RestAssured;

import static APIHelper.APIHelperClass.AdminUserLoginAuthentications;

public class AdminLogin {

		
	@Test (dataProvider="AdminUserLogin", dataProviderClass=ExcelDataProvider.class, groups={"AdminLoginAuthentications"})
	public void AdminLoginAuthentications(String AdminLogin_TestCases,
										  String Admin_API_Base_Path,
										  String Content_Type,
										  String Grant_type,
										  String Client_id,
										  String Client_secret,
										  String Status_Code )
	{
		LoggingManager.logger.info("====================================================================");
		LoggingManager.logger.info("TestCase : "+AdminLogin_TestCases);
		LoggingManager.logger.info("====================================================================");
		
		RestAssured.baseURI=Global.BaseURL;
		//APIHelperClass Adminuserlogin=new APIHelperClass();
		Global.getAccToken=AdminUserLoginAuthentications(Admin_API_Base_Path,
																		Content_Type,
																	    Grant_type,
																	    Client_id,
																	    Client_secret,
																	    Status_Code);
																 
		
		LoggingManager.logger.info("API-Response_AdminLogin_AccToken : ["+Global.getAccToken+"]");
		if(Global.getAccToken == null || Global.getAccToken=="" ) 
		{
			Assert.fail("Logs : AccToken is not created"); 
		}	
	}
	
		
}
