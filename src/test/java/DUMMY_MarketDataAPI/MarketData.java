package DUMMY_MarketDataAPI;

import org.testng.annotations.Test;

import APIHelper.Global;

import static io.restassured.RestAssured.given;
import XLDataProvider.ExcelDataProvider;
import io.restassured.RestAssured;
import io.restassured.response.Response;

@Test(groups = {"MarketData"},dependsOnGroups={"UserLoginAuthentications"})
public class MarketData {
   
	 
	@Test (dataProvider="NYSE_Halt_Symbols", dataProviderClass=ExcelDataProvider.class, dependsOnGroups={"UserLoginAuthentications"})
	public void NYSE_Halt_Symbols (String Symbol,String Reason_Code)
		{
			 RestAssured.baseURI=Global.BaseURL;
			 String OptionOrder_Creation_BasePath="/int/mkt/api/marketData/L1/subscribe?symbol=";
			
			
			 Response response= 
								 given()	
										.header("Content-Type","application/json") 
										.header("Authorization", "Bearer " + Global.getAccToken)
									
								 .when()
										.get(OptionOrder_Creation_BasePath+Symbol)
								 .then()
										.extract()
										.response();
				 
			if(response.statusCode()==200){ 
			System.out.println("==========================================================================");
			System.out.println("["+response.statusCode()+"] -- ["+Symbol+"] -- ["+Reason_Code+"]");
			System.out.println("\"localCode\" : "+response.jsonPath().get("localCode"));
			System.out.println("\"tradingStatusReason\" : -------------------------------------------------------------------------------------"+response.jsonPath().get("tradingStatusReason"));
			System.out.println("\"tradingStatusReasonDetails\" -------------------------------------------------------------------------------: "+response.jsonPath().get("tradingStatusReasonDetails"));
			System.out.println("\"tradingStatusDetails\" : "+response.jsonPath().get("tradingStatusDetails"));
			System.out.println("==========================================================================");	
			}
			else {
				System.out.println("["+response.statusCode()+"] -- ["+Symbol+"] -- ["+Reason_Code+"]");
			}
		}
}
