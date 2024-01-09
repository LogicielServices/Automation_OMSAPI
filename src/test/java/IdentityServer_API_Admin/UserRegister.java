package IdentityServer_API_Admin;

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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class UserRegister {

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
	
	 // @Test (dataProvider="RegisterUser", dataProviderClass=ExcelDataProvider.class , groups={"CreateRegisterUser"}, dependsOnGroups={"AdminLoginAuthentications"})
	 @Test (dataProvider="RegisterUser", dataProviderClass=ExcelDataProvider.class , groups={"CreateRegisterUser"})
	 public void Verify_User_Registeration(	String User_Creation_TestCases,String User_Creation_BasePath,
											String Content_Type ,
											String User_Name,
											String User_Firstname,
											String User_Lastname,
											String User_Email,
											String User_Password,
											String User_ClientId,
											String User_ServerUsername,
											String User_ServerPassword,
											String User_Accounts,
											String User_Permissions,
											String User_RegistrationPlatforms,
											String User_Enabled,
											String User_Creation_Status_Code,
											String User_Creation_success_Message,
											String Fetch_User_BasePath,
											String Fetch_User_StatusCode,
											String Status_Validation)
												
		{	
		 	LoggingManager.logger.info("====================================================================");
			LoggingManager.logger.info("TestCase : "+User_Creation_TestCases);
			LoggingManager.logger.info("====================================================================");
			
			LocalDateTime localDateTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");
	        String UserID = localDateTime.format(formatter);
	        System.out.println("formattedDateTime :"+UserID);
	        RestAssured.baseURI=Global.BaseURL;
	        String RegisterUser_Body =	"{\r\n"
						        		+ "    \"name\": \""+User_Name+"\",\r\n"
						        		+ "    \"firstname\":\""+User_Firstname.concat(UserID)+"\",\r\n"
						        		+ "    \"lastname\":\""+User_Lastname+"\",\r\n"
						        		+ "    \"email\": \""+UserID.concat(User_Email)+"\",\r\n"
						        		+ "    \"password\": \""+User_Password+"\",\r\n"
						        		+ "    \"clientId\": \""+User_ClientId+"\",\r\n"
						        		+ "    \"serverUsername\": \""+User_ServerUsername.concat(UserID)+"\",\r\n"
						        		+ "    \"serverPassword\": \""+User_ServerPassword+"\",\r\n"
						        		+ "    \"accounts\": "+User_Accounts+",\r\n"
						        		+ "    \"permissions\":"+User_Permissions+",\r\n"
						        		+ "    \"registrationPlatforms\": "+User_RegistrationPlatforms+",\r\n"
						        		+ "    \"enabled\": "+User_Enabled+"\r\n"
						        		+ "}";
	        
				System.out.println("Body is here : "+RegisterUser_Body);
				Response response=
				
								given()	
										.header("Content-Type",Content_Type) 
										.header("Authorization", "Bearer " + Global.getAccToken)
										.body(RegisterUser_Body)
									
								.when()
										.post(User_Creation_BasePath)
									
								.then()
										.extract().response();
			
				LoggingManager.logger.info("API-RegisterUser_BasePath : ["+User_Creation_BasePath+"]");
				LoggingManager.logger.info("API-RegisterUser_StatusCode : ["+response.statusCode()+"]");
				LoggingManager.logger.info("API-RegisterUser_success_Message : ["+response.jsonPath().get("message")+"]");				
				LoggingManager.logger.info("API-RegisterUser_Response_Body : ["+response.getBody().asString()+"]");
				
				Assert.assertEquals(response.statusCode(), Integer.parseInt(User_Creation_Status_Code),"Verify_User_Creation_Status_Code");
				Assert.assertEquals(response.jsonPath().get("message"), User_Creation_success_Message,"Verify_User_Registeration_Message");
				
				APIHelperClass apihelper=new APIHelperClass();
				Global.ValidationFlag=apihelper.GetUserValidate(Fetch_User_BasePath,
																Global.getAccToken, 
																Content_Type,
																Fetch_User_StatusCode,
																User_ServerUsername.concat(UserID),
																UserID.concat(User_Email),
																Status_Validation );
				
				//Global.getUserName=User_ServerUsername.concat(UserID);
				//Global.getUserEmail=UserID.concat(User_Email);
				//System.out.println(Global.getUserName+" ::: "+Global.getUserEmail);
				LoggingManager.logger.info("API-RegisterUser_Found_Flag : ["+Global.ValidationFlag+"]");
				Assert.assertEquals(Global.ValidationFlag,Boolean.parseBoolean(Status_Validation),"Validate_User_Registeration");
		}
		
}
	

