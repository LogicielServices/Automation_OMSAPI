package Subscriptions;

import static io.restassured.RestAssured.given;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.testng.Assert;
import org.testng.annotations.Test;
import APIHelper.Global;
import XLDataProvider.ExcelDataProvider;
import io.restassured.RestAssured;
import io.restassured.response.Response;

//Test(groups = {"Create"},dependsOnGroups={"UserLoginAuthentications"})
public class Order_Positions_Subscribe_CopY {

	 
	 @Test (dataProvider="SubscribeBUYOrder_Positions", dataProviderClass=ExcelDataProvider.class , groups={"SubscribeBUYOrder_Positions"}, dependsOnGroups={"SubscribeBUYExecutions"})//CreateFilledEquityOrder
	 public void Verify_Subscribe_BUY_Order_Positions(String Subscribe_Order_Positions_BasePath,
													  String Content_Type,
													  String Subscribe_Order_Positions_StatusCode,
													  String Validate_Symbol_Value,
													  String Validate_positionString_Value,
													  String Validate_Account_Value )
		{
		 	 DecimalFormat decimalFormat = new DecimalFormat("0.0000");
		     decimalFormat.getRoundingMode();
			 RestAssured.baseURI=Global.BaseURL;		
			 Response get_position_response=
					
					given()	
						.header("Content-Type",Content_Type) 
						.header("Authorization", "Bearer " + Global.getAccToken)
					.when()
						.get(Subscribe_Order_Positions_BasePath)
						
					.then()
						//.statusCode(statuscode)
						//.statusLine("HTTP/1.1 200 OK")
						.extract().response();
			 
			 
			 String response_avgPrice=com.jayway.jsonpath.JsonPath.read(get_position_response.getBody().asString(), "$.data.eventData[?((@.symbol =='"+Validate_Symbol_Value+"') && (@.positionString =='"+Validate_positionString_Value+"')&& (@.account =='"+Validate_Account_Value+"'))].avgPrice").toString();
			 String response_realizedPnL=com.jayway.jsonpath.JsonPath.read(get_position_response.getBody().asString(), "$.data.eventData[?((@.symbol =='"+Validate_Symbol_Value+"') && (@.positionString =='"+Validate_positionString_Value+"')&& (@.account =='"+Validate_Account_Value+"'))].realizedPnL").toString();
			 
			 System.out.println("Verify_Subscribe_Order_Positions Global.getAvgPrice :"+decimalFormat.format(Global.getAvgPrice));
			 System.out.println("Verify_Subscribe_Order_Positions Global.getrealizedPnL :"+decimalFormat.format(Global.getLONGrealizedPnL));
				
			 
			 System.out.println("Verify_response_avgPrice :"+response_avgPrice);
			 System.out.println("Verify_response_realizedPnL :"+response_realizedPnL);
			// Assert.assertEquals(response_avgPrice,"["+decimalFormat.format(Global.getAvgPrice)+"]", "Validate_AvgPrice_Subscribe_Order_Positions");
			// Assert.assertEquals(response_realizedPnL,"["+decimalFormat.format(Global.getrealizedPnL)+"]", "Validate_realizedPnL_Subscribe_Order_Positions");
			 	
		}	
	 
	 
	 @Test (dataProvider="SubscribeSELLOrder_Positions", dataProviderClass=ExcelDataProvider.class , groups={"SubscribeSELLOrder_Positions"}, dependsOnGroups={"SubscribeSELLExecutions"})//CreateFilledEquityOrder
	 public void Verify_Subscribe_SELL_Order_Positions(	String Subscribe_Order_Positions_BasePath,
														String Content_Type,
														String Subscribe_Order_Positions_StatusCode,
														String Validate_Symbol_Value,
														String Validate_positionString_Value,
														String Validate_Account_Value)
		{
		 	 DecimalFormat decimalFormat = new DecimalFormat("0.00");
		     decimalFormat.setRoundingMode(RoundingMode.UP);
			 RestAssured.baseURI=Global.BaseURL;		
			 Response get_position_response=
					
					given()	
						.header("Content-Type",Content_Type) 
						.header("Authorization", "Bearer " + Global.getAccToken)
					.when()
						.get(Subscribe_Order_Positions_BasePath)
						
					.then()
						//.statusCode(statuscode)
						//.statusLine("HTTP/1.1 200 OK")
						.extract().response();
			 
			 
			 String response_avgPrice=com.jayway.jsonpath.JsonPath.read(get_position_response.getBody().asString(), "$.data.eventData[?((@.symbol =='"+Validate_Symbol_Value+"') && (@.positionString =='"+Validate_positionString_Value+"')&& (@.account =='"+Validate_Account_Value+"'))].avgPrice").toString();
			 String response_realizedPnL=com.jayway.jsonpath.JsonPath.read(get_position_response.getBody().asString(), "$.data.eventData[?((@.symbol =='"+Validate_Symbol_Value+"') && (@.positionString =='"+Validate_positionString_Value+"')&& (@.account =='"+Validate_Account_Value+"'))].realizedPnL").toString();
			 
			 System.out.println("Verify_Subscribe_Order_Positions Global.getAvgPrice :"+Global.getAvgPrice);
			 System.out.println("Verify_Subscribe_Order_Positions Global.getrealizedPnL :"+decimalFormat.format(Global.getLONGrealizedPnL));
				
			 
			 System.out.println("Verify_response_avgPrice :"+response_avgPrice);
			 System.out.println("Verify_response_realizedPnL :"+response_realizedPnL);
			// Assert.assertEquals(response_avgPrice,"["+decimalFormat.format(Global.getAvgPrice)+"]", "Validate_AvgPrice_Subscribe_Order_Positions");
			// Assert.assertEquals(response_realizedPnL,"["+decimalFormat.format(Global.getrealizedPnL)+"]", "Validate_realizedPnL_Subscribe_Order_Positions");
			 	
		}
	 
	 @Test (dataProvider="SubscribeSHORTSELL_Position", dataProviderClass=ExcelDataProvider.class , groups={"SubscribeSHORTSELL_Position"}, dependsOnGroups={"SubscribeSHORTSELLExecutions"})//CreateFilledEquityOrder
	 public void Verify_Subscribe_SHORTSELL_Order_Positions(	String Subscribe_Order_Positions_BasePath,
													String Content_Type,
													String Subscribe_Order_Positions_StatusCode,
													String Validate_Symbol_Value,
													String Validate_positionString_Value,
													String Validate_Account_Value)
		{
		 	 DecimalFormat decimalFormat = new DecimalFormat("0.00");
		     decimalFormat.setRoundingMode(RoundingMode.UP);
			 RestAssured.baseURI=Global.BaseURL;		
			 Response get_position_response=
					
					given()	
						.header("Content-Type",Content_Type) 
						.header("Authorization", "Bearer " + Global.getAccToken)
					.when()
						.get(Subscribe_Order_Positions_BasePath)
						
					.then()
						//.statusCode(statuscode)
						//.statusLine("HTTP/1.1 200 OK")
						.extract().response();
			 
			 
			 String response_avgPrice=com.jayway.jsonpath.JsonPath.read(get_position_response.getBody().asString(), "$.data.eventData[?((@.symbol =='"+Validate_Symbol_Value+"') && (@.positionString =='"+Validate_positionString_Value+"')&& (@.account =='"+Validate_Account_Value+"'))].avgPrice").toString();
			 String response_realizedPnL=com.jayway.jsonpath.JsonPath.read(get_position_response.getBody().asString(), "$.data.eventData[?((@.symbol =='"+Validate_Symbol_Value+"') && (@.positionString =='"+Validate_positionString_Value+"')&& (@.account =='"+Validate_Account_Value+"'))].realizedPnL").toString();
			 
			 System.out.println("Verify_Subscribe_Order_Positions Global.getAvgPrice :"+Global.getAvgPrice);
			 System.out.println("Verify_Subscribe_Order_Positions Global.getrealizedPnL :"+decimalFormat.format(Global.getSHORTrealizedPnL));
				
			 
			 System.out.println("Verify_response_avgPrice :"+response_avgPrice);
			 System.out.println("Verify_response_realizedPnL :"+response_realizedPnL);
			// Assert.assertEquals(response_avgPrice,"["+decimalFormat.format(Global.getAvgPrice)+"]", "Validate_AvgPrice_Subscribe_Order_Positions");
			// Assert.assertEquals(response_realizedPnL,"["+decimalFormat.format(Global.getrealizedPnL)+"]", "Validate_realizedPnL_Subscribe_Order_Positions");
			 	
		}	
}
