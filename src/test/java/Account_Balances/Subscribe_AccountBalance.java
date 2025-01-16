package Account_Balances;

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
import java.util.Map;


public class Subscribe_AccountBalance {
	
	
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
	 
	@Test (dataProvider="Subscribe_AccountBalance", dataProviderClass=ExcelDataProvider.class,groups={"Subscribe_AccountBalance"}, dependsOnGroups={"UserLoginAuthentications"})
	public void Verify_Subscribe_AccountBalance(String TestCase,
												String EndpointVersion,
												String Subscribe_AccountBalance_BasePath,
												String Content_Type,
												String Subscribe_AccountBalance_StatusCode )
															{
		LoggingManager.logger.info("====================================================================");
		LoggingManager.logger.info("TestCase : "+TestCase);
		LoggingManager.logger.info("====================================================================");
		RestAssured.baseURI=Global.BaseURL;
		Response response=
							given()	
								.header("Content-Type",Content_Type) 
								.header("Authorization", "Bearer " + Global.getAccToken)
								
							.when()
								.get(Subscribe_AccountBalance_BasePath)
								
							.then()
								.statusCode(Integer.parseInt(Subscribe_AccountBalance_StatusCode))
								.statusLine("HTTP/1.1 200 OK")
								.extract().response();
		
		LoggingManager.logger.info("API-Subscribe_AccountBalance_BasePath : ["+Subscribe_AccountBalance_BasePath+"]");
		LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
		LoggingManager.logger.info("API-Subscribe_AccountBalance_StatusCode : ["+response.getStatusCode()+"]");
		JsonPath jsonPath = response.jsonPath();
/*
		// Step 4: Extract top-level fields
		String firmId = jsonPath.getString("firm_id"); // Extract "firm_id" as String
		String groupId = jsonPath.getString("group_id"); // Extract "group_id" as String
		String traderId = jsonPath.getString("trader_id"); // Extract "trader_id" as String

		// Step 5: Extract fields from the nested "metrics" object
		double sodEquity = jsonPath.getDouble("metrics.sod_equity"); // Extract "sod_equity" as double
		double currentEquity = jsonPath.getDouble("metrics.current_equity"); // Extract "current_equity" as double
		double sodExcess = jsonPath.getDouble("metrics.sod_excess"); // Extract "sod_excess" as double
		double sodOvnBp = jsonPath.getDouble("metrics.sod_ovn_bp"); // Extract "sod_ovn_bp" as double
		double sodDayBp = jsonPath.getDouble("metrics.sod_day_bp"); // Extract "sod_day_bp" as double
		double currentOvnExcess = jsonPath.getDouble("metrics.current_ovn_excess"); // Extract "current_ovn_excess" as double
		double currentDayExcess = jsonPath.getDouble("metrics.current_day_excess"); // Extract "current_day_excess" as double
		double currentOvnBp = jsonPath.getDouble("metrics.current_ovn_bp"); // Extract "current_ovn_bp" as double
		double currentDayBp = jsonPath.getDouble("metrics.current_day_bp"); // Extract "current_day_bp" as double

		// Step 6: Extract nested objects within "metrics"
		Map<String, Double> unrealizedPnl = jsonPath.getMap("metrics.unrealized_pnl"); // Extract "unrealized_pnl" as Map<String, Double>
		Map<String, Double> posExp = jsonPath.getMap("metrics.pos_exp"); // Extract "pos_exp" as Map<String, Double>
		Map<String, Double> ordExp = jsonPath.getMap("metrics.ord_exp"); // Extract "ord_exp" as Map<String, Double>
		Map<String, Double> ordMreq = jsonPath.getMap("metrics.ord_mreq"); // Extract "ord_mreq" as Map<String, Double>
		Map<String, Double> portfolioMreq = jsonPath.getMap("metrics.portfolio_mreq"); // Extract "portfolio_mreq" as Map<String, Double>
		Map<String, Map<String, Map<String, Object>>> openOrdersStats = jsonPath.getMap("metrics.open_orders_stats"); // Extract "open_orders_stats" as nested Map
		Map<String, Double> sodUnrealizedPnl = jsonPath.getMap("metrics.sod_unrealized_pnl"); // Extract "sod_unrealized_pnl" as Map<String, Double>
		Map<String, Double> sodPosExp = jsonPath.getMap("metrics.sod_pos_exp"); // Extract "sod_pos_exp" as Map<String, Double>

*/
		//Assert.assertEquals(response.statusCode(),Integer.parseInt(Subscribe_AccountBalance_StatusCode), "Verify_AccountBalance_StatusCode");
		//String Response_Subscribe_OrderID=com.jayway.jsonpath.JsonPath.read(response.getBody().asString(), "$.eventData[?(@.qOrderID =='"+Validate_Subscribe_OrderID+"' )].qOrderID").toString();
		//System.out.println("Subscribe_qOrderID "+Subscribe_OrderID);
		//Assert.assertEquals(Subscribe_qOrderID,"[\""+Validate_Subscribe_qOrderID+"\"]", "Validate_Subscribe_qOrderID");
		
		
		
	}	
}
