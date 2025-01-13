package MarketData_L1;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import APIHelper.Global;
import APIHelper.LoggingManager;

import static APIHelper.APIHelperClass.getserializedJsonObj;
import static io.restassured.RestAssured.given;
import java.io.PrintWriter;
import java.io.StringWriter;
import XLDataProvider.ExcelDataProvider;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Historicaldata_Snapshot {
	
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
	
	@Test (dataProvider="Historicaldata_Snapshot_MarketData", dataProviderClass=ExcelDataProvider.class,groups={"UnSubscribe_Equity"} ,dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_Historicaldata_Snapshot_MarketData (String Historicaldata_Snapshot_TestCases,
														   String EndpointVersion,
														   String Historicaldata_Snapshot_Base_Path,
														   String Content_Type,
														   String Historicaldata_Snapshot_Body,
														   String Historicaldata_Snapshot_StatusCode,
														   String Symbol_Validation_Historicaldata_Snapshot,
														   String Validate_Response_Fields,
														   String Empty_Invalid_Response)
	{
		try
		{
				LoggingManager.logger.info("====================================================================");
				LoggingManager.logger.info("TestCase : "+Historicaldata_Snapshot_TestCases);
				LoggingManager.logger.info("====================================================================");	
				RestAssured.baseURI=Global.BaseURL;
				Response response=  				 
									 given()	
											.header("Content-Type",Content_Type) 
											.header("Authorization", "Bearer " + Global.getAccToken)
											.body(Historicaldata_Snapshot_Body)
									 .when()
											.post(Historicaldata_Snapshot_Base_Path)
									 .then()
									 		//.statusCode(Integer.parseInt(Historicaldata_Snapshot_StatusCode))
									 		.extract()
											.response();
				
				JsonPath jsonPath = new JsonPath(response.getBody().asString());
				int respSize=jsonPath.getInt("size()");
				LoggingManager.logger.info("API-Historicaldata_Snapshot_Base_Path : ["+Historicaldata_Snapshot_Base_Path+"]");
				LoggingManager.logger.info("API-Historicaldata_Snapshot_Body : ["+Historicaldata_Snapshot_Body+"]");	
				LoggingManager.logger.info("API-Historicaldata_Snapshot_StatusCode : ["+response.statusCode()+"]");	
				Assert.assertEquals(response.statusCode(),Integer.parseInt(Historicaldata_Snapshot_StatusCode),"Verify_Historicaldata_Snapshot_StatusCode");
				LoggingManager.logger.info("API-Response Data Size : ["+respSize+"]");
				LoggingManager.logger.info("API-Response Data: ["+response.getBody().asString()+"]");
				if(Integer.parseInt(Historicaldata_Snapshot_StatusCode)==400)
				{
					Assert.assertEquals(getserializedJsonObj(response, "errors"),Empty_Invalid_Response,"Validation_CompanyInformation_Error_Response");
				}
				else {
					if (respSize > 0) {
						LoggingManager.logger.info("API-Symbol_Validation_Historicaldata_Snapshot index[0]: " + Symbol_Validation_Historicaldata_Snapshot + " - Found : " + com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.[0].symbol").toString());
						LoggingManager.logger.info("API-Response Keys Validation_Historicaldata_Snapshot index[0]: " + Validate_Response_Fields + " - Found : " + (jsonPath.getMap("[0]").keySet()).toString());
						for (int index = 0; index <= respSize - 1; index++) {
							String ValidateLocalCodes = com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.[" + index + "].symbol").toString();
							Assert.assertEquals(ValidateLocalCodes, Symbol_Validation_Historicaldata_Snapshot, "Verify_Symbol_Historicaldata_Snapshot_MarketData");
							Assert.assertEquals((jsonPath.getMap("[" + index + "]").keySet()).toString(), Validate_Response_Fields, "ValidateResponseFields_Historicaldata_Snapshot_MarketData");
						}
					} else {
						Assert.assertEquals(response.getBody().asString(), Empty_Invalid_Response, "Validate_ResponseFields_Historicaldata_Snapshot_MarketData");
					}
				}
	 
		}
		catch (Exception e) 
		{
			LoggingManager.logger.error(e);
		}
	}
}
