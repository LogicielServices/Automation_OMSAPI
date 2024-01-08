package IdentityServer_API_Admin;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
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


public class UpdateUserInfo {
 
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
	// @Test (dataProvider="UpdateUserInfomation", dataProviderClass=ExcelDataProvider.class , groups={"UpdateUser"}, dependsOnGroups={"AdminLoginAuthentications"})
	 @Test (dataProvider="UpdateUserInfomation", dataProviderClass=ExcelDataProvider.class , groups={"UpdateUser"})
	 public void Verify_Update_User_Info(	String Update_Info_TestCases,String Update_Info_Base_Path,
											String Content_Type,
											String Revert_Update_Info_Body,
											String Revert_Reponse_Message ,
											String User_Update_Info_Body,
											String User_Update_Status_code,
											String User_Update_Response_Message,
											String Fetch_User_BasePath,
											String Fetch_User_StatusCode,
											String Validate_User_UserID,
											String Validate_User_Email,
											String Status_Validation)
												
		{
		 
		 	LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+Update_Info_TestCases);
			LoggingManager.logger.info("====================================================================");
			RestAssured.baseURI=Global.BaseURL;
//========================================For Revert the updated information========================================		
			Response Info_Revert_response=
								
											given()	
													.header("Content-Type",Content_Type) 
													.header("Authorization", "Bearer " + Global.getAccToken)
													.body(Revert_Update_Info_Body)
												
											.when()
													.post(Update_Info_Base_Path)
												
											.then()
													.extract().response();
			
			LoggingManager.logger.info("API-Revert_Update_Info_Base_Path : ["+Update_Info_Base_Path+"]");
			LoggingManager.logger.info("API-Revert_Update_Info_Body : ["+Revert_Update_Info_Body+"]");
			LoggingManager.logger.info("API-Revert_Info_StatusCode : ["+Info_Revert_response.statusCode()+"]");	
			LoggingManager.logger.info("API-Revert_Info_Response_Body : ["+Info_Revert_response.body().asString()+"]");
			
			
			Assert.assertEquals(Info_Revert_response.body().asString(), Revert_Reponse_Message,"Verify_Status_Revert_response");
			
			Response Update_Info_response=
				
											given()	
													.header("Content-Type",Content_Type) 
													.header("Authorization", "Bearer " + Global.getAccToken)
													.body(User_Update_Info_Body)
												
											.when()
													.post(Update_Info_Base_Path)
												
											.then()
													.extract().response();
			
			LoggingManager.logger.info("API-Update_Info_Base_Path : ["+Update_Info_Base_Path+"]");
			LoggingManager.logger.info("API-Update_Info_Body : ["+User_Update_Info_Body+"]");
			LoggingManager.logger.info("API-Update_Info_StatusCode : ["+Update_Info_response.statusCode()+"]");	
			LoggingManager.logger.info("API-Update_Info_Response_Body : ["+Update_Info_response.body().asString()+"]");
			Assert.assertEquals(Update_Info_response.body().asString(), User_Update_Response_Message,"Verify_Update_User_Info");
						
			APIHelperClass apihelper=new APIHelperClass();
			Global.ValidationFlag=apihelper.GetUserValidate(Fetch_User_BasePath,
															Global.getAccToken, 
															Content_Type,
															Fetch_User_StatusCode,
															Validate_User_UserID,
															Validate_User_Email,
															Status_Validation );
	
			LoggingManager.logger.info("API-Update_Infoormation Done Flag : ["+Global.ValidationFlag+"]");
			Assert.assertEquals(Global.ValidationFlag,Boolean.parseBoolean(Status_Validation),"Validate_User_Updation");
		}
		
}
	

