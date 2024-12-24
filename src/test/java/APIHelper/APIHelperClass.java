package APIHelper;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static io.restassured.RestAssured.given;

public class APIHelperClass {

public static String AdminUserLoginAuthentications(String admin_api_path,
												String Content_Type,
												String grant_type,
												String client_id,
												String client_secret,
												String status_code)
{
	try
	{
		RestAssured.baseURI=Global.BaseURL;
		Response response_AdminLogin=
				given()
						.config(RestAssured.config()
						.encoderConfig(EncoderConfig.encoderConfig()
						.encodeContentTypeAs(Content_Type, ContentType.URLENC)))
						.contentType(ContentType.URLENC.withCharset("UTF-8"))
						.formParam("grant_type", grant_type)
						.formParam("client_id", client_id)
						.formParam("client_secret",client_secret)
						//.header("Content-Type",content_type)
						//.header("X-Internal-User","True")
						//.body(mapLoginCredential)

						.when()
						.post(admin_api_path)

						.then()
						//.statusCode(Integer.parseInt(status_code))
						.extract().response();

		LoggingManager.logger.info("API-AdminLogin_BasePath : ["+admin_api_path+"]");
		LoggingManager.logger.info("API-AdminLogin_grant_type : ["+grant_type+"]");
		LoggingManager.logger.info("API-AdminLogin_client_id : ["+client_id+"]");
		LoggingManager.logger.info("API-AdminLogin_client_secret : ["+client_secret+"]");
		LoggingManager.logger.info("API-AdminLogin_StatusCode : ["+response_AdminLogin.statusCode()+"]");
		LoggingManager.logger.info("API-AdminLogin_Response_Body : ["+response_AdminLogin.getBody().asString()+"]");
		Assert.assertEquals(response_AdminLogin.getStatusCode(),Integer.parseInt(status_code),"AdminUser Login Authentications");
		Global.getAccToken=response_AdminLogin.jsonPath().get("access_token");
		return Global.getAccToken;

	}
	catch (Exception e)
	{
		LoggingManager.logger.error(e);
		return "";
	}
}

public static String ArchiveUserLoginAuthentications(String archive_Api_Path,
													   String content_Type,
													   String client_Id,
													   String client_Secret,
													   String username,
													   String booth_Id,
													   String status_code,
													   String Error_Message,
													   String Validate_Response_Fields )
	{
		try
		{
			RestAssured.baseURI=Global.BaseURL;
			Response response_ArchiveLogin=
					given()
							.config(RestAssured.config()
									.encoderConfig(EncoderConfig.encoderConfig()
											.encodeContentTypeAs(content_Type, ContentType.URLENC)))
							.contentType(ContentType.URLENC.withCharset("UTF-8"))
							.formParam("client_id", client_Id)
							.formParam("client_secret",client_Secret)
							.formParam("username", username)
							.formParam("booth_id", booth_Id)
							//.header("Content-Type",content_type)
							//.header("X-Internal-User","True")
							//.body(mapLoginCredential)

							.when()
							.post(archive_Api_Path)

							.then()
							//.statusCode(Integer.parseInt(status_code))
							.extract().response();
			Map<String, Object> jsonResponse = response_ArchiveLogin.jsonPath().get();
			LoggingManager.logger.info("API-ArchiveLogin_BasePath : ["+archive_Api_Path+"]");
			LoggingManager.logger.info("API-ArchiveLogin_client_id : ["+client_Id+"]");
			LoggingManager.logger.info("API-ArchiveLogin_client_secret : ["+client_Secret+"]");
			LoggingManager.logger.info("API-ArchiveLogin_username : ["+username+"]");
			LoggingManager.logger.info("API-ArchiveLogin_booth_Id : ["+booth_Id+"]");
			LoggingManager.logger.info("API-ArchiveLogin_StatusCode : ["+response_ArchiveLogin.statusCode()+"]");
			LoggingManager.logger.info("API-ArchiveLogin_Response_Body : ["+response_ArchiveLogin.getBody().asString()+"]");
			LoggingManager.logger.info("API-Validate_Response_Fields : "+(jsonResponse.keySet()).toString());
			LoggingManager.logger.info("API-Validate_Response_Error_Message : "+response_ArchiveLogin.jsonPath().getString("errorText"));
			Assert.assertEquals(response_ArchiveLogin.getStatusCode(),Integer.parseInt(status_code),"Verify_Response_Status_Code");
			Assert.assertEquals(response_ArchiveLogin.jsonPath().getString("errorText"),Error_Message,"Verify_Response_Error_Message");
			Assert.assertEquals((jsonResponse.keySet()).toString(), Validate_Response_Fields,"Verify_Response_Fields");
			Global.getArchive_AccToken=response_ArchiveLogin.jsonPath().get("accessToken");
			return Global.getArchive_AccToken;

		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
			return "";
		}
	}
	
	
public Boolean GetUserValidate( String Get_User_basePath,
									String AccToken, 
									String contentType,
									String getUserStatusCode,
									String userid,
									String useremail,
									String Status_Validation) 
{
	try
	{

		RestAssured.baseURI=Global.BaseURL;
		Response get_User_response=

				given()
						.header("Content-Type",contentType)
						.header("Authorization", "Bearer " + AccToken)
						.when()
						.get(Get_User_basePath)
						.then()
						//.statusCode(Integer.parseInt(getUserStatusCode))
						.extract()
						.response();

		LoggingManager.logger.info("API-Get_User_basePath : ["+Get_User_basePath+"]");
		LoggingManager.logger.info("API-Get_User_StatusCode : ["+get_User_response.statusCode()+"]");
		Assert.assertEquals(get_User_response.getStatusCode(),Integer.parseInt(getUserStatusCode),"Get User Validation");
		Global.getUserName  = com.jayway.jsonpath.JsonPath.read(get_User_response.getBody().asString(), "$.data[?(@.username =='"+userid+"' )].username").toString();
		Global.getUserEmail = com.jayway.jsonpath.JsonPath.read(get_User_response.getBody().asString(), "$.data[?(@.email =='"+useremail+"' )].email").toString();
		LoggingManager.logger.info("API-Expected UserName : ["+userid+"] and Found : "+Global.getUserName);
		LoggingManager.logger.info("API-Expected UserEmail : ["+useremail+"] and Found : "+Global.getUserEmail);

		if(Global.getUserName.equalsIgnoreCase("[\""+userid+"\"]") && Global.getUserEmail.equalsIgnoreCase("[\""+useremail+"\"]"))
		{
			Global.ValidationFlag=true;
		}
		return Global.ValidationFlag;
	}
	catch (Exception e)
	{
		LoggingManager.logger.error("ValidationFlag is : "+Global.ValidationFlag+" : "+e);
		return false;
	}
}
	
	
public String UserLoginAuthentications(String UserEmail,
										   String UserPassword,
										   String login_Step1_basePath,
										   String basePathStep2,
										   String TFACode,
										   String contentType,
										   String Status_Code_Step1,
										   String Status_Code_Step2)
{
	try
	{
		RestAssured.baseURI=Global.BaseURL;
		HashMap<String, String> mapLoginCredential=new HashMap<String, String>();
		mapLoginCredential.put("email", UserEmail);
		mapLoginCredential.put( "password",UserPassword);
		Response response_Login_Step1=

						 given()
						.header("Content-Type",contentType)
						.header("X-Internal-User","True")
						.body(mapLoginCredential)

						.when()
						.post(login_Step1_basePath)

						.then()
						//.statusCode(Integer.parseInt(Status_Code_Step1))
						//.statusLine("HTTP/1.1 202 Accepted")
						.extract().response();

		LoggingManager.logger.info("API-Login_BasePath_Login_Step1 : ["+login_Step1_basePath+"]");
		LoggingManager.logger.info("API-Content_Type : ["+contentType+"]");
		LoggingManager.logger.info("API-Login_Step1_Body : ["+mapLoginCredential+"]");
		LoggingManager.logger.info("API-Status_Code_Step1_StatusCode : ["+response_Login_Step1.statusCode()+"]");
		LoggingManager.logger.info("API-response_Login_Step1_Response_Body : ["+response_Login_Step1.getBody().asString()+"]");

		Assert.assertEquals(response_Login_Step1.getStatusCode(),Integer.parseInt(Status_Code_Step1),"User Login Authentications Step 1");
		Global.getCodeID=response_Login_Step1.jsonPath().get("codeId");
		LoggingManager.logger.info("API-getCodeID : ["+Global.getCodeID+"]");
		LoggingManager.logger.info("API-TFACode : ["+TFACode+"]");

		String login_Step2_basePath=basePathStep2+Global.getCodeID+"/"+TFACode;
		Response response_Login_Step2=

						given()

						.when()
						.post(login_Step2_basePath)

						.then()
						//.statusCode(Integer.parseInt(Status_Code_Step2))
						//.statusLine("HTTP/1.1 200 OK")
						.extract().response();

		LoggingManager.logger.info("API-Login_BasePath_Login_Step2 : ["+login_Step2_basePath+"]");
		LoggingManager.logger.info("API-response_Login_Step2_StatusCode : ["+response_Login_Step2.getStatusCode()+"]");
		LoggingManager.logger.info("API-response_Login_Step2_Response_Body : ["+response_Login_Step2.getBody().asString()+"]");
		Assert.assertEquals(response_Login_Step2.getStatusCode(),Integer.parseInt(Status_Code_Step2),"User Login Authentications Step 2");
		Global.getAccToken=response_Login_Step2.jsonPath().get("access_token");
		//RefToken=response_Login_Step2.jsonPath().get("refresh_token");
		return Global.getAccToken;

	}
	catch (Exception e)
	{
		LoggingManager.logger.error("AccToken is : "+Global.getAccToken+" : "+e);
		return "";
	}
}

public static String ValidationNullValue(String value)
{
	try
	{
		if (value.equalsIgnoreCase("null") || value.equalsIgnoreCase("true") ||value.equalsIgnoreCase("false"))
		{
			return "["+value+"]";
		}
		else
		{
			return "[\""+value+"\"]";
		}
	}
	catch (Exception e)
	{
		LoggingManager.logger.error(e);
		return "";
	}
}
	
public static String NVL(String ParamA,String ParamB)
{
	try
	{
		if (ParamA==null)
		{
			return ParamB;
		}
		else
		{
			return ParamA;
		}
	}
	catch (Exception e)
	{
		LoggingManager.logger.error(e);
		return "";
	}
}


public static String apiRespVersion(String endpoint_version)
{
	String getResp="";
	try
	{
		switch (endpoint_version) {

			case "V1":
				getResp="eventData";
				break;
			case "V3":
				getResp="data.eventData";
				break;
			default:
				getResp="";
				LoggingManager.logger.error("Endpoint Version ["+endpoint_version+"] Not Found");
				break;
		}
		return getResp;
		///if (endpoint_version=="V1"){getResponseArray="eventData";}else{getResponseArray="data.eventData";}
	}
	catch (Exception e)
	{
		LoggingManager.logger.error("EndpointVersion : "+endpoint_version +" - Reponse : "+getResp +" - "+e);
		return "";
	}
}
	
	
public static void GetOrderValues(	 String Get_orders_basePath,
									 String AccToken,
									 String contentType,
									 int statuscode,
									 String endpoint_version,
									 String orderUserid,
									 String expected_orderStatus,
									 String expected_order_account,
									 String expected_order_symbol,
									 String expected_order_destination,
									 String expected_order_price,
									 String expected_order_side,
									 String expected_order_orderQty,
									 String expected_order_ordType,
									 String orderNature)
{
	try
	{
			 String getResponseArray="";
			 RestAssured.baseURI=Global.BaseURL;		
			 Response get_orders_response=
					
					given()	
						.header("Content-Type",contentType) 
						.header("Authorization", "Bearer " + AccToken)
					.when()
						.get(Get_orders_basePath)
						
					.then()
						.extract().response();
			
			LoggingManager.logger.info("API-Get_orders_basePath : ["+Get_orders_basePath+"]");
			LoggingManager.logger.info("API-Get_orders_statusCode : ["+get_orders_response.statusCode()+"]");
			LoggingManager.logger.info("API-Get_orders_API Version : ["+endpoint_version+"]");
			Assert.assertEquals(get_orders_response.getStatusCode(),statuscode,"Fetch Orders Record Failed");
			JsonPath jsonresponse = new JsonPath(get_orders_response.getBody().asString());
			if (orderNature.equalsIgnoreCase("option")) {getResponseArray="eventData";}
			else {getResponseArray=apiRespVersion(endpoint_version);}
			//getResponseArray=apiRespVersion(endpoint_version);
			int ResponseArraySize = jsonresponse.getInt(getResponseArray+".size()");
			for(int position = ResponseArraySize-1; position >=0; position--) 
				{
					
				     String response_UserID = jsonresponse.getString(getResponseArray+"["+position+"].originatingUserDesc");
			         String response_destination = jsonresponse.getString(getResponseArray+"["+position+"].destination");
			         String response_account = jsonresponse.getString(getResponseArray+"["+position+"].account");
			         String response_symbol = jsonresponse.getString(getResponseArray+"["+position+"].symbol");
			         String response_status = jsonresponse.getString(getResponseArray+"["+position+"].status");
			         Double response_price = jsonresponse.getDouble(getResponseArray+"["+position+"].price");
			         String response_side = jsonresponse.getString(getResponseArray+"["+position+"].side");
			         String response_sideDesc = jsonresponse.getString(getResponseArray+"["+position+"].sideDesc");
			         Double response_orderQty = jsonresponse.getDouble(getResponseArray+"["+position+"].orderQty");
			         String response_ordType = jsonresponse.getString(getResponseArray+"["+position+"].ordType");
			         String response_orderId= jsonresponse.getString(getResponseArray+"["+position+"].orderId");
			         Integer response_qOrderID =jsonresponse.getInt(getResponseArray+"["+position+"].qOrderID");
			         String response_ID = jsonresponse.getString(getResponseArray+"["+position+"].id");
			         String response_time = jsonresponse.getString(getResponseArray+"["+position+"].time");
			         String response_clOrdID = jsonresponse.getString(getResponseArray+"["+position+"].clOrdID");
			         String response_origClOrdID = jsonresponse.getString(getResponseArray+"["+position+"].origClOrdID");
			         String response_text = jsonresponse.getString(getResponseArray+"["+position+"].text");
			         String response_complianceID = jsonresponse.getString(getResponseArray+"["+position+"].complianceID");
			         Double response_stopPx= jsonresponse.getDouble(getResponseArray+"["+position+"].stopPx");
			         String response_timeInForce = jsonresponse.getString(getResponseArray+"["+position+"].timeInForce");
			         String response_transactTime = jsonresponse.getString(getResponseArray+"["+position+"].transactTime");
			         String response_symbolSfx = jsonresponse.getString(getResponseArray+"["+position+"].symbolSfx");
			         String response_symbolWithoutSfx= jsonresponse.getString(getResponseArray+"["+position+"].symbolWithoutSfx");
			         String response_tifDesc =jsonresponse.getString(getResponseArray+"["+position+"].tifDesc");
			         String response_orderTypeDesc = jsonresponse.getString(getResponseArray+"["+position+"].orderTypeDesc");
			         String response_statusDesc = jsonresponse.getString(getResponseArray+"["+position+"].statusDesc");
			         Double response_avgPx = jsonresponse.getDouble(getResponseArray+"["+position+"].avgPx");
			         Double response_cumQty = jsonresponse.getDouble(getResponseArray+"["+position+"].cumQty");
			         Double response_workableQty = jsonresponse.getDouble(getResponseArray+"["+position+"].workableQty");
			         Double response_leavesQty= jsonresponse.getDouble(getResponseArray+"["+position+"].leavesQty");
			         String response_locateID= jsonresponse.getString(getResponseArray+"["+position+"].locateID");
			         String response_contactName = jsonresponse.getString(getResponseArray+"["+position+"].contactName");
			         String response_locateRequired = jsonresponse.getString(getResponseArray+"["+position+"].locateRequired");
			         Double response_locateRate = jsonresponse.getDouble(getResponseArray+"["+position+"].locateRate");
			         String response_boothID= jsonresponse.getString(getResponseArray+"["+position+"].boothID");
			         
					 //check if condition meets

		/*	      	 System.out.println(response_UserID +" --- "+orderUserid);
			         System.out.println(response_status +" --- "+expected_orderStatus);
			         System.out.println(response_account +" --- "+expected_order_account);
			         System.out.println(response_symbol +" --- "+expected_order_symbol);
			         System.out.println(response_destination +" --- "+expected_order_destination);
			         System.out.println(response_price.toString() +" --- "+expected_order_price);
			         System.out.println(response_side +" --- "+expected_order_side);
			         System.out.println(response_orderQty.toString() +" --- "+expected_order_orderQty);
			         System.out.println(response_ordType +" --- "+expected_order_ordType);
		*/
			         if (response_UserID.equalsIgnoreCase(orderUserid)
				        			&& response_status.equalsIgnoreCase(expected_orderStatus) 
				        			&& response_account.equalsIgnoreCase(expected_order_account)
				        			&& response_symbol.equalsIgnoreCase(expected_order_symbol)
				        			&& response_destination.equalsIgnoreCase(expected_order_destination)
				        			&& (response_price.toString()).equalsIgnoreCase(expected_order_price)
				        			&& response_side.equalsIgnoreCase(expected_order_side)
				        			&& (response_orderQty.toString()).equalsIgnoreCase(expected_order_orderQty)
				        			&& response_ordType.equalsIgnoreCase(expected_order_ordType))
			        		 
				        			 
				        	{
			        	 	
			        	 		switch (orderNature) {
									case "equity":
										
										Get_OrderResponse_values(response_orderId,response_sideDesc,response_status,response_ID,response_qOrderID,response_time,response_account,response_clOrdID,response_origClOrdID,response_price,response_symbol,response_text,response_side,response_complianceID,response_UserID,response_ordType,response_stopPx,response_timeInForce,response_transactTime,response_symbolSfx,response_symbolWithoutSfx,response_tifDesc,response_orderTypeDesc,response_statusDesc,response_avgPx,response_cumQty,response_workableQty,response_leavesQty,response_orderQty,response_locateID,response_contactName,response_locateRequired,response_locateRate,response_destination,response_boothID);
					        	 		break;
										
									case "option":
										
										 String response_optionSymbol = jsonresponse.getString(getResponseArray+"["+position+"].optionSymbol");
					       		         Double response_strikePrice = jsonresponse.getDouble(getResponseArray+"["+position+"].strikePrice");
					       		         String response_maturityDay = jsonresponse.getString(getResponseArray+"["+position+"].maturityDay");
					       		         String response_maturityMonthYear = jsonresponse.getString(getResponseArray+"["+position+"].maturityMonthYear");
					       		         String response_maturityMonthYearDesc= jsonresponse.getString(getResponseArray+"["+position+"].maturityMonthYearDesc");
					       		         String response_maturityDate = jsonresponse.getString(getResponseArray+"["+position+"].maturityDate");
					       		         String response_optionDesc = jsonresponse.getString(getResponseArray+"["+position+"].optionDesc");
					       		         String response_cmta = jsonresponse.getString(getResponseArray+"["+position+"].cmta");
					       		         String response_execBroker = jsonresponse.getString(getResponseArray+"["+position+"].execBroker");
					       		         Integer response_putOrCallInt= jsonresponse.getInt(getResponseArray+"["+position+"].putOrCallInt");
					       		         String response_putOrCall = jsonresponse.getString(getResponseArray+"["+position+"].putOrCall");
					       		         Integer response_coveredOrUncoveredInt = jsonresponse.getInt(getResponseArray+"["+position+"].coveredOrUncoveredInt");
					       		         String response_coveredOrUncovered = jsonresponse.getString(getResponseArray+"["+position+"].coveredOrUncovered");
					       		         Integer response_customerOrFirmInt = jsonresponse.getInt(getResponseArray+"["+position+"].customerOrFirmInt");
					       		         String response_customerOrFirm= jsonresponse.getString(getResponseArray+"["+position+"].customerOrFirm");
					       		         Integer response_openCloseBoxed= jsonresponse.getInt(getResponseArray+"["+position+"].openCloseBoxed");
					       		         String response_openClose= jsonresponse.getString(getResponseArray+"["+position+"].openClose");
					       		         Get_OptionOrderResponse_values(response_orderId,response_sideDesc,response_status,response_ID,response_qOrderID,response_time,response_account,response_clOrdID,response_origClOrdID,response_price,response_symbol,response_text,response_side,response_complianceID,response_UserID,response_ordType,response_stopPx,response_timeInForce,response_transactTime,response_symbolSfx,response_symbolWithoutSfx,response_tifDesc,response_orderTypeDesc,response_statusDesc,response_avgPx,response_cumQty,response_workableQty,response_leavesQty,response_orderQty,response_locateID,response_contactName,response_locateRequired,response_locateRate,response_destination,response_boothID,response_optionSymbol,response_strikePrice,response_maturityDay,response_maturityMonthYear,response_maturityMonthYearDesc,response_maturityDate,response_optionDesc,response_cmta,response_execBroker,response_putOrCallInt,response_putOrCall,response_coveredOrUncoveredInt,response_coveredOrUncovered,response_customerOrFirmInt,response_customerOrFirm,response_openCloseBoxed,response_openClose);
					       		         break;
	
									default:
										break;
									}
				        	break;
			        	 			
				        	}
				        	else
				        	{
				        		Global.getOrderID=null;
								Global.qOrderID=null;
				        		Global.getOptionOrderID=null;
								Global.getSideDesc=null;
				        		Global.getStatus=null;
				        		Global.getOptionStatus=null;
				        	}
				        } 
		        	
		}
		catch(NullPointerException e)
        {
			LoggingManager.logger.error(" API- Null Pointer Exception Caught : "+e);
        }
	}
		
	
public static void Get_OrderResponse_values(String getOrderID,String getSideDesc,String getStatus,String getID,Integer qOrderID,String gettime,String getaccount,String getclOrdID,String getOrigClOrdID,Double getprice,String getsymbol,String gettext,String getside,String getcomplianceID,String getoriginatingUserDesc,String getordType,Double getstopPx,String gettimeInForce,String gettransactTime,String getsymbolSfx,String getsymbolWithoutSfx,String gettifDesc,String getorderTypeDesc,String getstatusDesc,Double getavgPx,Double getcumQty,Double getworkableQty,Double getleavesQty,Double getorderQty,String getlocateID,String getcontactName,String getlocateRequired,Double getlocateRate,String getdestination,String getboothID)
{
	try
	{
		//Global.getOrderValue=getOrderValue;
		Global.getOrderID=getOrderID;
		Global.getSideDesc=getSideDesc;
		Global.getStatus=getStatus;
		Global.getID=getID;
		Global.qOrderID=qOrderID;
		Global.gettime=gettime;
		Global.getaccount=getaccount;
		Global.getclOrdID=getclOrdID;
		Global.getOrigClOrdID=getOrigClOrdID;
		Global.getprice=getprice;
		Global.getsymbol=getsymbol;
		Global.gettext=gettext;
		Global.getside=getside;
		Global.getcomplianceID=getcomplianceID;
		Global.getoriginatingUserDesc=getoriginatingUserDesc;
		Global.getordType=getordType;
		Global.getstopPx=getstopPx;
		Global.gettimeInForce=gettimeInForce;
		Global.gettransactTime=gettransactTime;
		Global.getsymbolSfx=getsymbolSfx;
		Global.getsymbolWithoutSfx=getsymbolWithoutSfx;
		Global.gettifDesc=gettifDesc;
		Global.getorderTypeDesc=getorderTypeDesc;
		Global.getstatusDesc=getstatusDesc;
		Global.getavgPx=getavgPx;
		Global.getcumQty=getcumQty;
		Global.getworkableQty=getworkableQty;
		Global.getleavesQty=getleavesQty;
		Global.getorderQty=getorderQty;
		Global.getlocateID=getlocateID;
		Global.getcontactName=getcontactName;
		Global.getlocateRequired=getlocateRequired;
		Global.getlocateRate=getlocateRate;
		Global.getdestination=getdestination;
		Global.getboothID=getboothID;

	}
	catch (Exception e)
	{
		LoggingManager.logger.error(e);
	}
}


public  static void Get_OptionOrderResponse_values(String getOrderID,String getSideDesc,String getStatus,String getID,Integer qOrderID,String gettime,String getaccount,String getclOrdID,String getOrigClOrdID,Double getprice,String getsymbol,String gettext,String getside,String getcomplianceID,String getoriginatingUserDesc,String getordType,Double getstopPx,String gettimeInForce,String gettransactTime,String getsymbolSfx,String getsymbolWithoutSfx,String gettifDesc,String getorderTypeDesc,String getstatusDesc,Double getavgPx,Double getcumQty,Double getworkableQty,Double getleavesQty,Double getorderQty,String getlocateID,String getcontactName,String getlocateRequired,Double getlocateRate,String getdestination,String getboothID,String getoptionSymbol,Double getstrikePrice,String getmaturityDay,String getmaturityMonthYear,String getmaturityMonthYearDesc,String getmaturityDate,String getoptionDesc,String getcmta,String getexecBroker,Integer getputOrCallInt,String getputOrCall,Integer getcoveredOrUncoveredInt,String getcoveredOrUncovered,Integer getcustomerOrFirmInt,String getcustomerOrFirm,Integer getopenCloseBoxed,String getopenClose)
{
	try
	{
		//Global.getOrderValue=getOrderValue;
		Global.getOptionOrderID=getOrderID;
		Global.getOptionSideDesc=getSideDesc;
		Global.getOptionStatus=getStatus;
		Global.getOptionID=getID;
		Global.getOptionqOrderID=qOrderID;
		Global.getOptionTime=gettime;
		Global.getOptionAccount=getaccount;
		Global.getOptionclOrdID=getclOrdID;
		Global.getOptionOrigClOrdID=getOrigClOrdID;
		Global.getOptionPrice=getprice;
		Global.getOptSymbol=getsymbol;
		Global.getOptionText=gettext;
		Global.getOptionSide=getside;
		Global.getOptionComplianceID=getcomplianceID;
		Global.getOptionOriginatingUserDesc=getoriginatingUserDesc;
		Global.getOptionOrdType=getordType;
		Global.getOptionStopPx=getstopPx;
		Global.getOptionTimeInForce=gettimeInForce;
		Global.getOptionTransactTime=gettransactTime;
		Global.getOptionSymbolSfx=getsymbolSfx;
		Global.getOptionSymbolWithoutSfx=getsymbolWithoutSfx;
		Global.getOptionTifDesc=gettifDesc;
		Global.getOptionOrderTypeDesc=getorderTypeDesc;
		Global.getOptionStatusDesc=getstatusDesc;
		Global.getOptionAvgPx=getavgPx;
		Global.getOptionCumQty=getcumQty;
		Global.getOptionWorkableQty=getworkableQty;
		Global.getOptionLeavesQty=getleavesQty;
		Global.getOptionOrderQty=getorderQty;
		Global.getOptionLocateID=getlocateID;
		Global.getOptionContactName=getcontactName;
		Global.getOptionLocateRequired=getlocateRequired;
		Global.getOptionLocateRate=getlocateRate;
		Global.getOptionDestination=getdestination;
		Global.getOptionBoothID=getboothID;
		Global.getOptionSymbol=getoptionSymbol;
		Global.getOptionStrikePrice=getstrikePrice;
		Global.getOptionMaturityDay=getmaturityDay;
		Global.getOptionMaturityMonthYear=getmaturityMonthYear;
		Global.getOptionMaturityMonthYearDesc=getmaturityMonthYearDesc;
		Global.getOptionMaturityDate=getmaturityDate;
		Global.getOptionDesc=getoptionDesc;
		Global.getOptionCmta=getcmta;
		Global.getOptionExecBroker=getexecBroker;
		Global.getOptionPutOrCallInt=getputOrCallInt;
		Global.getOptionPutOrCall=getputOrCall;
		Global.getOptionCoveredOrUncoveredInt=getcoveredOrUncoveredInt;
		Global.getOptionCoveredOrUncovered=getcoveredOrUncovered;
		Global.getOptionCustomerOrFirmInt=getcustomerOrFirmInt;
		Global.getOptionCustomerOrFirm=getcustomerOrFirm;
		Global.getOptionOpenCloseBoxed=getopenCloseBoxed;
		Global.getOptionOpenClose=getopenClose;

	}
	catch (Exception e)
	{
		LoggingManager.logger.error(e);
	}
}

	
public static void OrdersSubscriptionValidation(String Order_Status,
												 String Subscribe_Order_UserID,
												 String Subscribe_Order_OrderType,
												 String Subscribe_Order_Side,
												 String Subscribe_Order_SideDesc,
												 String Subscribe_Order_Symbol,
												 String Subscribe_Order_Account,
												 String Subscribe_Order_Destination,
												 String Subscribe_Order_Price,
												 String Subscribe_Order_OrderQty,
												 String Subscribe_Order_Text,
												 String Subscribe_Order_complianceID,
												 String Subscribe_Order_stopPx,
												 String Subscribe_Order_timeInForce,
												 String Subscribe_Order_tifDesc,
												 String Subscribe_Order_symbolSfx,
												 String Subscribe_Order_symbolWithoutSfx,
												 String Subscribe_Order_orderTypeDesc,
												 String Subscribe_Order_avgPx,
												 String Subscribe_Order_cumQty,
												 String Subscribe_Order_workableQty,
												 String Subscribe_Order_leavesQty,
												 String Subscribe_Order_locateID,
												 String Subscribe_Order_contactName,
												 String Subscribe_Order_locateRequired,
												 String Subscribe_Order_locateRate,
												 String Subscribe_Order_boothID,
												 String Subscribe_Order_ExpectedStatus,
												 String endpoint_version,
												 String Subscribe_Executions_BasePath,
												 String Content_Type,
												 String Status_Code)
{
	try
	{
		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter time_formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		DateTimeFormatter transactTime_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DecimalFormat decimalFormat = new DecimalFormat("0.000");
		decimalFormat.getRoundingMode();


		switch(Order_Status)
		{

			case "Active":
				Assert.assertNotEquals(Global.getID,null,"Validate_ID");
				Assert.assertNotEquals(Global.getOrderID,null,"Validate_OrderID");
				Assert.assertNotEquals(Global.qOrderID,null,"Validate_qOrderID");
				Assert.assertNotEquals(Global.getclOrdID,null,"Validate_clOrdID");
				Assert.assertEquals(Global.getOrigClOrdID,null,"Validate_OrigClOrdID");
				Assert.assertNotEquals(Global.gettime,null,"Validate_time");
				Assert.assertEquals(NVL(Global.getoriginatingUserDesc,"null"),Subscribe_Order_UserID,"Validate_Subscribe_Order_UserID ");
				Assert.assertEquals(NVL(Global.getSideDesc,"null"),Subscribe_Order_SideDesc,"Validate_Subscribe_Order_SideDesc ");
				Assert.assertEquals(NVL(Global.getStatus,"null"),Subscribe_Order_ExpectedStatus,"Validate_Subscribe_Order_ExpectedStatus ");
				Assert.assertEquals(NVL(Global.getaccount,"null"),Subscribe_Order_Account,"Validate_Subscribe_Order_Account ");
				Assert.assertEquals(Global.getprice,Double.parseDouble(Subscribe_Order_Price),"Validate_Subscribe_Order_Price");
				Assert.assertEquals(NVL(Global.getsymbol,"null"),Subscribe_Order_Symbol,"Validate_Subscribe_Order_Symbol");
				Assert.assertEquals(NVL(Global.gettext,"null"),Subscribe_Order_Text,"Validate_Subscribe_Order_Text");
				Assert.assertEquals(NVL(Global.getside,"null"),Subscribe_Order_Side,"Validate_Subscribe_Order_Side");
				Assert.assertEquals(NVL(Global.getcomplianceID,"null"),Subscribe_Order_complianceID,"Validate_Subscribe_Order_complianceID");
				Assert.assertEquals(NVL(Global.getordType,"null"),Subscribe_Order_OrderType,"Validate_Subscribe_Order_OrderType");
				Assert.assertEquals(Global.getstopPx,Double.parseDouble(Subscribe_Order_stopPx),"Validate_Subscribe_Order_stopPx");
				Assert.assertEquals(NVL(Global.gettimeInForce,"null"),Subscribe_Order_timeInForce,"Validate_Subscribe_Order_timeInForce");
				Assert.assertEquals(NVL(Global.getsymbolSfx,"null") ,Subscribe_Order_symbolSfx,"Validate_Subscribe_Order_symbolSfx");
				Assert.assertEquals(NVL(Global.getsymbolWithoutSfx,"null") ,Subscribe_Order_symbolWithoutSfx,"Validate_Subscribe_Order_symbolWithoutSfx");
				Assert.assertEquals(NVL(Global.gettifDesc,"null"),Subscribe_Order_tifDesc,"Validate_Subscribe_Order_tifDesc");
				Assert.assertEquals(NVL(Global.getorderTypeDesc,"null"),Subscribe_Order_orderTypeDesc,"Validate_Subscribe_Order_orderTypeDesc");
				Assert.assertEquals(NVL(Global.getstatusDesc,"null"),Subscribe_Order_ExpectedStatus,"Validate_Subscribe_Order_ExpectedStatus");
				Assert.assertEquals(Global.getavgPx,Double.parseDouble(Subscribe_Order_avgPx),"Validate_Subscribe_Order_avgPx");
				Assert.assertEquals(Global.getcumQty,Double.parseDouble(Subscribe_Order_cumQty),"Validate_Subscribe_Order_cumQty");
				Assert.assertEquals(Global.getworkableQty,Double.parseDouble(Subscribe_Order_workableQty),"Validate_Subscribe_Order_workableQty");
				Assert.assertEquals(Global.getleavesQty,Double.parseDouble(Subscribe_Order_leavesQty),"Validate_Subscribe_Order_leavesQty");
				Assert.assertEquals(Global.getorderQty,Double.parseDouble(Subscribe_Order_OrderQty),"Validate_Subscribe_Order_OrderQty");
				Assert.assertEquals(NVL(Global.getlocateID,"null"),Subscribe_Order_locateID,"Validate_Subscribe_Order_locateID");
				Assert.assertEquals(NVL(Global.getcontactName,"null"),Subscribe_Order_contactName,"Validate_Subscribe_Order_contactName");
				Assert.assertEquals(NVL(Global.getlocateRequired,"null"),Subscribe_Order_locateRequired,"Validate_Subscribe_Order_locateRequired");
				Assert.assertEquals(Global.getlocateRate,Double.parseDouble(Subscribe_Order_locateRate),"Validate_Subscribe_Order_locateRate");
				Assert.assertEquals(NVL(Global.getdestination,"null"),Subscribe_Order_Destination,"Validate_Subscribe_Order_Destination");
				Assert.assertEquals(NVL(Global.getboothID,"null"),Subscribe_Order_boothID,"Validate_Subscribe_Order_boothID");
				Assert.assertEquals(Global.gettransactTime.substring(0,10),localDateTime.format(transactTime_formatter),"Validate_transactTime");
				//Assert.assertEquals(Global.gettime.substring(0,10),localDateTime.format(time_formatter),"Validate_time");


				break;

			case "Open":
				Assert.assertNotEquals(Global.getID,null,"Validate_ID");
				Assert.assertNotEquals(Global.getOrderID,null,"Validate_OrderID");
				Assert.assertNotEquals(Global.qOrderID,null,"Validate_qOrderID");
				Assert.assertNotEquals(Global.getclOrdID,null,"Validate_clOrdID");
				Assert.assertEquals(Global.getOrigClOrdID,null,"Validate_OrigClOrdID");
				Assert.assertNotEquals(Global.gettime,null,"Validate_time");
				Assert.assertEquals(NVL(Global.getoriginatingUserDesc,"null"),Subscribe_Order_UserID,"Validate_Subscribe_Order_UserID ");
				Assert.assertEquals(NVL(Global.getSideDesc,"null"),Subscribe_Order_SideDesc,"Validate_Subscribe_Order_SideDesc ");
				Assert.assertEquals(NVL(Global.getStatus,"null"),Subscribe_Order_ExpectedStatus,"Validate_Subscribe_Order_ExpectedStatus ");
				Assert.assertEquals(NVL(Global.getaccount,"null"),Subscribe_Order_Account,"Validate_Subscribe_Order_Account ");
				Assert.assertEquals(Global.getprice,Double.parseDouble(Subscribe_Order_Price),"Validate_Subscribe_Order_Price");
				Assert.assertEquals(NVL(Global.getsymbol,"null"),Subscribe_Order_Symbol,"Validate_Subscribe_Order_Symbol");
				Assert.assertEquals(NVL(Global.gettext,"null"),Subscribe_Order_Text,"Validate_Subscribe_Order_Text");
				Assert.assertEquals(NVL(Global.getside,"null"),Subscribe_Order_Side,"Validate_Subscribe_Order_Side");
				Assert.assertEquals(NVL(Global.getcomplianceID,"null"),Subscribe_Order_complianceID,"Validate_Subscribe_Order_complianceID");
				Assert.assertEquals(NVL(Global.getordType,"null"),Subscribe_Order_OrderType,"Validate_Subscribe_Order_OrderType");
				Assert.assertEquals(Global.getstopPx,Double.parseDouble(Subscribe_Order_stopPx),"Validate_Subscribe_Order_stopPx");
				Assert.assertEquals(NVL(Global.gettimeInForce,"null"),Subscribe_Order_timeInForce,"Validate_Subscribe_Order_timeInForce");
				Assert.assertEquals(NVL(Global.getsymbolSfx,"null") ,Subscribe_Order_symbolSfx,"Validate_Subscribe_Order_symbolSfx");
				Assert.assertEquals(NVL(Global.getsymbolWithoutSfx,"null") ,Subscribe_Order_symbolWithoutSfx,"Validate_Subscribe_Order_symbolWithoutSfx");
				Assert.assertEquals(NVL(Global.gettifDesc,"null"),Subscribe_Order_tifDesc,"Validate_Subscribe_Order_tifDesc");
				Assert.assertEquals(NVL(Global.getorderTypeDesc,"null"),Subscribe_Order_orderTypeDesc,"Validate_Subscribe_Order_orderTypeDesc");
				Assert.assertEquals(NVL(Global.getstatusDesc,"null"),Subscribe_Order_ExpectedStatus,"Validate_Subscribe_Order_ExpectedStatus");
				Assert.assertEquals(Global.getavgPx,Double.parseDouble(Subscribe_Order_avgPx),"Validate_Subscribe_Order_avgPx");
				Assert.assertEquals(Global.getcumQty,Double.parseDouble(Subscribe_Order_cumQty),"Validate_Subscribe_Order_cumQty");
				Assert.assertEquals(Global.getworkableQty,Double.parseDouble(Subscribe_Order_workableQty),"Validate_Subscribe_Order_workableQty");
				Assert.assertEquals(Global.getleavesQty,Double.parseDouble(Subscribe_Order_leavesQty),"Validate_Subscribe_Order_leavesQty");
				Assert.assertEquals(Global.getorderQty,Double.parseDouble(Subscribe_Order_OrderQty),"Validate_Subscribe_Order_OrderQty");
				Assert.assertEquals(NVL(Global.getlocateID,"null"),Subscribe_Order_locateID,"Validate_Subscribe_Order_locateID");
				Assert.assertEquals(NVL(Global.getcontactName,"null"),Subscribe_Order_contactName,"Validate_Subscribe_Order_contactName");
				Assert.assertEquals(NVL(Global.getlocateRequired,"null"),Subscribe_Order_locateRequired,"Validate_Subscribe_Order_locateRequired");
				Assert.assertEquals(Global.getlocateRate,Double.parseDouble(Subscribe_Order_locateRate),"Validate_Subscribe_Order_locateRate");
				Assert.assertEquals(NVL(Global.getdestination,"null"),Subscribe_Order_Destination,"Validate_Subscribe_Order_Destination");
				Assert.assertEquals(NVL(Global.getboothID,"null"),Subscribe_Order_boothID,"Validate_Subscribe_Order_boothID");
				Assert.assertEquals(Global.gettransactTime.substring(0,10),localDateTime.format(transactTime_formatter),"Validate_transactTime");
				//Assert.assertEquals(Global.gettime.substring(0,10),localDateTime.format(time_formatter),"Validate_time");

				break;

			case "Rejected":
				Assert.assertNotEquals(Global.getID,null,"Validate_ID");
				Assert.assertNotEquals(Global.getOrderID,null,"Validate_OrderID");
				Assert.assertNotEquals(Global.qOrderID,null,"Validate_qOrderID");
				Assert.assertNotEquals(Global.getclOrdID,null,"Validate_clOrdID");
				Assert.assertEquals(Global.getOrigClOrdID,null,"Validate_OrigClOrdID");
				Assert.assertNotEquals(Global.gettime,null,"Validate_time");
				Assert.assertEquals(NVL(Global.getoriginatingUserDesc,"null"),Subscribe_Order_UserID,"Validate_Subscribe_Order_UserID ");
				Assert.assertEquals(NVL(Global.getSideDesc,"null"),Subscribe_Order_SideDesc,"Validate_Subscribe_Order_SideDesc ");
				Assert.assertEquals(NVL(Global.getStatus,"null"),Subscribe_Order_ExpectedStatus,"Validate_Subscribe_Order_ExpectedStatus ");
				Assert.assertEquals(NVL(Global.getaccount,"null"),Subscribe_Order_Account,"Validate_Subscribe_Order_Account ");
				Assert.assertEquals(Global.getprice,Double.parseDouble(Subscribe_Order_Price),"Validate_Subscribe_Order_Price");
				Assert.assertEquals(NVL(Global.getsymbol,"null"),Subscribe_Order_Symbol,"Validate_Subscribe_Order_Symbol");
				Assert.assertEquals(NVL(Global.gettext,"null"),Subscribe_Order_Text,"Validate_Subscribe_Order_Text");
				Assert.assertEquals(NVL(Global.getside,"null"),Subscribe_Order_Side,"Validate_Subscribe_Order_Side");
				Assert.assertEquals(NVL(Global.getcomplianceID,"null"),Subscribe_Order_complianceID,"Validate_Subscribe_Order_complianceID");
				Assert.assertEquals(NVL(Global.getordType,"null"),Subscribe_Order_OrderType,"Validate_Subscribe_Order_OrderType");
				Assert.assertEquals(Global.getstopPx,Double.parseDouble(Subscribe_Order_stopPx),"Validate_Subscribe_Order_stopPx");
				Assert.assertEquals(NVL(Global.gettimeInForce,"null"),Subscribe_Order_timeInForce,"Validate_Subscribe_Order_timeInForce");
				Assert.assertEquals(NVL(Global.getsymbolSfx,"null") ,Subscribe_Order_symbolSfx,"Validate_Subscribe_Order_symbolSfx");
				Assert.assertEquals(NVL(Global.getsymbolWithoutSfx,"null") ,Subscribe_Order_symbolWithoutSfx,"Validate_Subscribe_Order_symbolWithoutSfx");
				Assert.assertEquals(NVL(Global.gettifDesc,"null"),Subscribe_Order_tifDesc,"Validate_Subscribe_Order_tifDesc");
				Assert.assertEquals(NVL(Global.getorderTypeDesc,"null"),Subscribe_Order_orderTypeDesc,"Validate_Subscribe_Order_orderTypeDesc");
				Assert.assertEquals(NVL(Global.getstatusDesc,"null"),Subscribe_Order_ExpectedStatus,"Validate_Subscribe_EquityOrder_StatusDesc");
				Assert.assertEquals(Global.getavgPx,Double.parseDouble(Subscribe_Order_avgPx),"Validate_Subscribe_Order_avgPx");
				Assert.assertEquals(Global.getcumQty,Double.parseDouble(Subscribe_Order_cumQty),"Validate_Subscribe_Order_cumQty");
				Assert.assertEquals(Global.getworkableQty,Double.parseDouble(Subscribe_Order_workableQty),"Validate_Subscribe_Order_workableQty");
				Assert.assertEquals(Global.getleavesQty,Double.parseDouble(Subscribe_Order_leavesQty),"Validate_Subscribe_Order_leavesQty");
				Assert.assertEquals(Global.getorderQty,Double.parseDouble(Subscribe_Order_OrderQty),"Validate_Subscribe_Order_OrderQty");
				Assert.assertEquals(NVL(Global.getlocateID,"null"),Subscribe_Order_locateID,"Validate_Subscribe_Order_locateID");
				Assert.assertEquals(NVL(Global.getcontactName,"null"),Subscribe_Order_contactName,"Validate_Subscribe_Order_contactName");
				Assert.assertEquals(NVL(Global.getlocateRequired,"null"),Subscribe_Order_locateRequired,"Validate_Subscribe_Order_locateRequired");
				Assert.assertEquals(Global.getlocateRate,Double.parseDouble(Subscribe_Order_locateRate),"Validate_Subscribe_Order_locateRate");
				Assert.assertEquals(NVL(Global.getdestination,"null"),Subscribe_Order_Destination,"Validate_Subscribe_Order_Destination");
				Assert.assertEquals(NVL(Global.getboothID,"null"),Subscribe_Order_boothID,"Validate_Subscribe_Order_boothID");
				//Assert.assertEquals(Global.gettime.substring(0,10),localDateTime.format(time_formatter),"Validate_time");
				Assert.assertEquals(Global.gettransactTime.substring(0,10),localDateTime.format(transactTime_formatter),"Validate_transactTime");
				break;

			case "Filled":

				Global.getAvgPrice=GetOrderAvgPx(endpoint_version,Subscribe_Executions_BasePath,Content_Type,Global.getOrderID,Status_Code);
				Assert.assertNotEquals(Global.getID,null,"Validate_ID");
				Assert.assertNotEquals(Global.getOrderID,null,"Validate_OrderID");
				Assert.assertNotEquals(Global.qOrderID,null,"Validate_qOrderID");
				Assert.assertNotEquals(Global.getclOrdID,null,"Validate_clOrdID");
				Assert.assertEquals(Global.getOrigClOrdID,null,"Validate_OrigClOrdID");
				Assert.assertNotEquals(Global.gettime,null,"Validate_time");
				Assert.assertEquals(NVL(Global.getoriginatingUserDesc,"null"),Subscribe_Order_UserID,"Validate_Subscribe_Order_UserID ");
				Assert.assertEquals(NVL(Global.getSideDesc,"null"),Subscribe_Order_SideDesc,"Validate_Subscribe_Order_SideDesc ");
				Assert.assertEquals(NVL(Global.getStatus,"null"),Subscribe_Order_ExpectedStatus,"Validate_Subscribe_Order_ExpectedStatus ");
				Assert.assertEquals(NVL(Global.getaccount,"null"),Subscribe_Order_Account,"Validate_Subscribe_Order_Account ");
				Assert.assertEquals(Global.getprice,Double.parseDouble(Subscribe_Order_Price),"Validate_Subscribe_Order_Price");
				Assert.assertEquals(NVL(Global.getsymbol,"null"),Subscribe_Order_Symbol,"Validate_Subscribe_Order_Symbol");
				Assert.assertEquals(NVL(Global.gettext,"null"),Subscribe_Order_Text,"Validate_Subscribe_Order_Text");
				Assert.assertEquals(NVL(Global.getside,"null"),Subscribe_Order_Side,"Validate_Subscribe_Order_Side");
				Assert.assertEquals(NVL(Global.getcomplianceID,"null"),Subscribe_Order_complianceID,"Validate_Subscribe_Order_complianceID");
				Assert.assertEquals(NVL(Global.getordType,"null"),Subscribe_Order_OrderType,"Validate_Subscribe_Order_OrderType");
				Assert.assertEquals(Global.getstopPx,Double.parseDouble(Subscribe_Order_stopPx),"Validate_Subscribe_Order_stopPx");
				Assert.assertEquals(NVL(Global.gettimeInForce,"null"),Subscribe_Order_timeInForce,"Validate_Subscribe_Order_timeInForce");
				Assert.assertEquals(NVL(Global.getsymbolSfx,"null") ,Subscribe_Order_symbolSfx,"Validate_Subscribe_Order_symbolSfx");
				Assert.assertEquals(NVL(Global.getsymbolWithoutSfx,"null") ,Subscribe_Order_symbolWithoutSfx,"Validate_Subscribe_Order_symbolWithoutSfx");
				Assert.assertEquals(NVL(Global.gettifDesc,"null"),Subscribe_Order_tifDesc,"Validate_Subscribe_Order_tifDesc");
				Assert.assertEquals(NVL(Global.getorderTypeDesc,"null"),Subscribe_Order_orderTypeDesc,"Validate_Subscribe_Order_orderTypeDesc");
				Assert.assertEquals(NVL(Global.getstatusDesc,"null"),Subscribe_Order_ExpectedStatus,"Validate_Subscribe_EquityOrder_StatusDesc");
				Assert.assertEquals(Double.parseDouble(decimalFormat.format(Global.getavgPx)),Global.getAvgPrice,"Validate_Subscribe_Order_avgPx");
				//Assert.assertEquals((Global.getavgPx.toString()).substring(0,6),(Global.getAvgPrice.toString()).substring(0,6),"Validate_Subscribe_Order_avgPx");
				Assert.assertEquals(Global.getcumQty,Double.parseDouble(Subscribe_Order_cumQty),"Validate_Subscribe_Order_cumQty");
				Assert.assertEquals(Global.getworkableQty,Double.parseDouble(Subscribe_Order_workableQty),"Validate_Subscribe_Order_workableQty");
				Assert.assertEquals(Global.getleavesQty,Double.parseDouble(Subscribe_Order_leavesQty),"Validate_Subscribe_Order_leavesQty");
				Assert.assertEquals(Global.getorderQty,Double.parseDouble(Subscribe_Order_OrderQty),"Validate_Subscribe_Order_OrderQty");
				Assert.assertEquals(NVL(Global.getlocateID,"null"),Subscribe_Order_locateID,"Validate_Subscribe_Order_locateID");
				Assert.assertEquals(NVL(Global.getcontactName,"null"),Subscribe_Order_contactName,"Validate_Subscribe_Order_contactName");
				Assert.assertEquals(NVL(Global.getlocateRequired,"null"),Subscribe_Order_locateRequired,"Validate_Subscribe_Order_locateRequired");
				Assert.assertEquals(Global.getlocateRate,Double.parseDouble(Subscribe_Order_locateRate),"Validate_Subscribe_Order_locateRate");
				Assert.assertEquals(NVL(Global.getdestination,"null"),Subscribe_Order_Destination,"Validate_Subscribe_Order_Destination");
				Assert.assertEquals(NVL(Global.getboothID,"null"),Subscribe_Order_boothID,"Validate_Subscribe_Order_boothID");
				Assert.assertEquals(Global.gettransactTime.substring(0,10),localDateTime.format(transactTime_formatter),"Validate_transactTime");
				//Assert.assertEquals(Global.gettime.substring(0,10),localDateTime.format(time_formatter),"Validate_time");
				LoggingManager.logger.info("API - Actual AvgPrice: ["+decimalFormat.format(Global.getavgPx)+"] - Expected AvgPrice: ["+Global.getAvgPrice+"]");
				break;

			case "Partially filled":
				break;

			default:
				break;
		}
	}
	catch (Exception e)
	{
		LoggingManager.logger.error(e);
	}
}


public static void Option_OrdersSubscriptionValidation(String Order_Status,
														 String Subscribe_Order_UserID,
														 String Subscribe_Order_OrderType,
														 String Subscribe_Order_Side,
														 String Subscribe_Order_SideDesc,
														 String Subscribe_Order_Symbol,
														 String Subscribe_Order_Account,
														 String Subscribe_Order_Destination,
														 String Subscribe_Order_Price,
														 String Subscribe_Order_OrderQty,
														 String Subscribe_Order_optionSymbol,
														 String Subscribe_Order_strikePrice,
														 String Subscribe_Order_maturityDay,
														 String Subscribe_Order_maturityMonthYear,
														 String Subscribe_Order_maturityMonthYearDesc,
														 String Subscribe_Order_maturityDate,
														 String Subscribe_Order_optionDateDesc,
														 String Subscribe_Order_cmta,
														 String Subscribe_Order_execBroker,
														 String Subscribe_Order_putOrCallInt,
														 String Subscribe_Order_putOrCall,
														 String Subscribe_Order_coveredOrUncoveredInt,
														 String Subscribe_Order_coveredOrUncovered,
														 String Subscribe_Order_customerOrFirmInt,
														 String Subscribe_Order_customerOrFirm,
														 String Subscribe_Order_openCloseBoxed,
														 String Subscribe_Order_openClose,
														 String Subscribe_Order_Text,
														 String Subscribe_Order_complianceID,
														 String Subscribe_Order_stopPx,
														 String Subscribe_Order_timeInForce,
														 String Subscribe_Order_tifDesc,
														 String Subscribe_Order_symbolSfx,
														 String Subscribe_Order_symbolWithoutSfx,
														 String Subscribe_Order_orderTypeDesc,
														 String Subscribe_Order_avgPx,
														 String Subscribe_Order_cumQty,
														 String Subscribe_Order_workableQty,
														 String Subscribe_Order_leavesQty,
														 String Subscribe_Order_locateID,
														 String Subscribe_Order_contactName,
														 String Subscribe_Order_locateRequired,
														 String Subscribe_Order_locateRate,
														 String Subscribe_Order_boothID,
														 String Subscribe_Order_ExpectedStatus,
														 String endpoint_version,
														 String Subscribe_Executions_BasePath,
														 String Content_Type,
														 String Status_Code) 
{
	try
	{
		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter time_formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		DateTimeFormatter transactTime_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DecimalFormat decimalFormat = new DecimalFormat("0.000");
		decimalFormat.getRoundingMode();
		switch(Order_Status)
		{

			case "Active":
				Assert.assertNotEquals(Global.getOptionID,null,"Validate_ID");
				Assert.assertNotEquals(Global.getOptionOrderID,null,"Validate_OrderID");
				Assert.assertNotEquals(Global.getOptionqOrderID,null,"Validate_qOrderID");
				Assert.assertNotEquals(Global.getOptionclOrdID,null,"Validate_clOrdID");
				Assert.assertEquals	(Global.getOptionOrigClOrdID,null,"Validate_OrigClOrdID");
				Assert.assertNotEquals(Global.getOptionTime,null,"Validate_time");
				Assert.assertEquals(NVL(Global.getOptionOriginatingUserDesc,"null"),Subscribe_Order_UserID,"Validate_Subscribe_Order_UserID ");
				Assert.assertEquals(NVL(Global.getOptionSideDesc,"null"),Subscribe_Order_SideDesc,"Validate_Subscribe_Order_SideDesc ");
				Assert.assertEquals(NVL(Global.getOptionStatus,"null"),Subscribe_Order_ExpectedStatus,"Validate_Subscribe_Order_ExpectedStatus ");
				Assert.assertEquals(NVL(Global.getOptionAccount,"null"),Subscribe_Order_Account,"Validate_Subscribe_Order_Account ");
				Assert.assertEquals(Global.getOptionPrice,Double.parseDouble(Subscribe_Order_Price),"Validate_Subscribe_Order_Price");
				Assert.assertEquals(NVL(Global.getOptionSymbol,"null"),Subscribe_Order_Symbol,"Validate_Subscribe_Order_Symbol");
				Assert.assertEquals(NVL(Global.getOptionText,"null"),Subscribe_Order_Text,"Validate_Subscribe_Order_Text");
				Assert.assertEquals(NVL(Global.getOptionSide,"null"),Subscribe_Order_Side,"Validate_Subscribe_Order_Side");
				Assert.assertEquals(NVL(Global.getOptionComplianceID,"null"),Subscribe_Order_complianceID,"Validate_Subscribe_Order_complianceID");
				Assert.assertEquals(NVL(Global.getOptionOrdType,"null"),Subscribe_Order_OrderType,"Validate_Subscribe_Order_OrderType");
				Assert.assertEquals(Global.getOptionStopPx,Double.parseDouble(Subscribe_Order_stopPx),"Validate_Subscribe_Order_stopPx");
				Assert.assertEquals(NVL(Global.getOptionTimeInForce,"null"),Subscribe_Order_timeInForce,"Validate_Subscribe_Order_timeInForce");
				Assert.assertEquals(NVL(Global.getOptionSymbolSfx,"null") ,Subscribe_Order_symbolSfx,"Validate_Subscribe_Order_symbolSfx");
				Assert.assertEquals(NVL(Global.getOptionSymbolWithoutSfx,"null") ,Subscribe_Order_symbolWithoutSfx,"Validate_Subscribe_Order_symbolWithoutSfx");
				Assert.assertEquals(NVL(Global.getOptionTifDesc,"null"),Subscribe_Order_tifDesc,"Validate_Subscribe_Order_tifDesc");
				Assert.assertEquals(NVL(Global.getOptionOrderTypeDesc,"null"),Subscribe_Order_orderTypeDesc,"Validate_Subscribe_Order_orderTypeDesc");
				Assert.assertEquals(NVL(Global.getOptionStatusDesc,"null"),Subscribe_Order_ExpectedStatus,"Validate_Subscribe_Order_StatusDesc");
				Assert.assertEquals(Global.getOptionAvgPx,Double.parseDouble(Subscribe_Order_avgPx),"Validate_Subscribe_Order_avgPx");
				Assert.assertEquals(Global.getOptionCumQty,Double.parseDouble(Subscribe_Order_cumQty),"Validate_Subscribe_Order_cumQty");
				Assert.assertEquals(Global.getOptionWorkableQty,Double.parseDouble(Subscribe_Order_workableQty),"Validate_Subscribe_Order_workableQty");
				Assert.assertEquals(Global.getOptionLeavesQty,Double.parseDouble(Subscribe_Order_leavesQty),"Validate_Subscribe_Order_leavesQty");
				Assert.assertEquals(Global.getOptionOrderQty,Double.parseDouble(Subscribe_Order_OrderQty),"Validate_Subscribe_Order_OrderQty");
				Assert.assertEquals(NVL(Global.getOptionLocateID,"null"),Subscribe_Order_locateID,"Validate_Subscribe_Order_locateID");
				Assert.assertEquals(NVL(Global.getOptionContactName,"null"),Subscribe_Order_contactName,"Validate_Subscribe_Order_contactName");
				Assert.assertEquals(NVL(Global.getOptionLocateRequired,"null"),Subscribe_Order_locateRequired,"Validate_Subscribe_Order_locateRequired");
				Assert.assertEquals(Global.getOptionLocateRate,Double.parseDouble(Subscribe_Order_locateRate),"Validate_Subscribe_Order_locateRate");
				Assert.assertEquals(NVL(Global.getOptionDestination,"null"),Subscribe_Order_Destination,"Validate_Subscribe_Order_Destination");
				Assert.assertEquals(NVL(Global.getOptionBoothID,"null"),Subscribe_Order_boothID,"Validate_Subscribe_Order_boothID");
				//Assert.assertEquals(Global.getOptionTime.substring(0,10),localDateTime.format(time_formatter),"Validate_time");  
				Assert.assertEquals(Global.getOptionTransactTime.substring(0,10),localDateTime.format(transactTime_formatter),"Validate_transactTime");
				Assert.assertEquals(Global.getOptionMaturityDay,Subscribe_Order_maturityDay,"Validate_Subscribe_Order_maturityDay");
				Assert.assertEquals(Global.getOptionMaturityMonthYear,Subscribe_Order_maturityMonthYear,"Validate_Subscribe_Order_maturityMonthYear");
				Assert.assertEquals(Global.getOptionMaturityMonthYearDesc,Subscribe_Order_maturityMonthYearDesc,"Validate_Subscribe_Order_maturityMonthYearDesc");
				Assert.assertEquals(Global.getOptionMaturityDate,Subscribe_Order_maturityDate,"Validate_Subscribe_Order_maturityDate");
				Assert.assertEquals(Global.getOptionStrikePrice,Double.parseDouble(Subscribe_Order_strikePrice),"Validate_Subscribe_Order_strikePrice ");
				Assert.assertEquals(NVL(Global.getOptionSymbol,"null"),Subscribe_Order_optionSymbol,"Validate_Subscribe_Order_optionSymbol ");
				Assert.assertEquals(NVL(Global.getOptionDesc,"null"),(Subscribe_Order_Symbol+" "+Subscribe_Order_optionDateDesc+" "+Subscribe_Order_strikePrice+0+" "+Subscribe_Order_putOrCall),"Validate_Subscribe_Order_OptionDesc ");
				Assert.assertEquals(NVL(Global.getOptionCmta,"null"),Subscribe_Order_cmta,"Validate_Subscribe_Order_cmta ");
				Assert.assertEquals(NVL(Global.getOptionExecBroker,"null"),Subscribe_Order_execBroker,"Validate_Subscribe_Order_execBroker ");
				Assert.assertEquals(Global.getOptionPutOrCallInt,Integer.parseInt(Subscribe_Order_putOrCallInt),"Validate_Subscribe_Order_putOrCallInt ");
				Assert.assertEquals(NVL(Global.getOptionPutOrCall,"null"),Subscribe_Order_putOrCall,"Validate_Subscribe_Order_putOrCall ");
				Assert.assertEquals(Global.getOptionCoveredOrUncoveredInt,Integer.parseInt(Subscribe_Order_coveredOrUncoveredInt),"Validate_Subscribe_Order_coveredOrUncoveredInt ");
				Assert.assertEquals(NVL(Global.getOptionCoveredOrUncovered,"null"),Subscribe_Order_coveredOrUncovered,"Validate_Subscribe_Order_coveredOrUncovered ");
				Assert.assertEquals(Global.getOptionCustomerOrFirmInt,Integer.parseInt(Subscribe_Order_customerOrFirmInt),"Validate_Subscribe_Order_customerOrFirmInt");
				Assert.assertEquals(NVL(Global.getOptionCustomerOrFirm,"null"),Subscribe_Order_customerOrFirm,"Validate_Subscribe_Order_customerOrFirm");
				Assert.assertEquals(Global.getOptionOpenCloseBoxed,Integer.parseInt(Subscribe_Order_openCloseBoxed),"Validate_Subscribe_Order_openCloseBoxed");
				Assert.assertEquals(NVL(Global.getOptionOpenClose,"null"),Subscribe_Order_openClose,"Validate_Subscribe_Order_openClose");
				break;

			case "Open":
				Assert.assertNotEquals(Global.getOptionID,null,"Validate_ID");
				Assert.assertNotEquals(Global.getOptionOrderID,null,"Validate_OrderID");
				Assert.assertNotEquals(Global.getOptionqOrderID,null,"Validate_qOrderID");
				Assert.assertNotEquals(Global.getOptionclOrdID,null,"Validate_clOrdID");
				Assert.assertEquals	(Global.getOptionOrigClOrdID,null,"Validate_OrigClOrdID");
				Assert.assertNotEquals(Global.getOptionTime,null,"Validate_time");
				Assert.assertEquals(NVL(Global.getOptionOriginatingUserDesc,"null"),Subscribe_Order_UserID,"Validate_Subscribe_Order_UserID ");
				Assert.assertEquals(NVL(Global.getOptionSideDesc,"null"),Subscribe_Order_SideDesc,"Validate_Subscribe_Order_SideDesc ");
				Assert.assertEquals(NVL(Global.getOptionStatus,"null"),Subscribe_Order_ExpectedStatus,"Validate_Subscribe_Order_ExpectedStatus ");
				Assert.assertEquals(NVL(Global.getOptionAccount,"null"),Subscribe_Order_Account,"Validate_Subscribe_Order_Account ");
				Assert.assertEquals(Global.getOptionPrice,Double.parseDouble(Subscribe_Order_Price),"Validate_Subscribe_Order_Price");
				Assert.assertEquals(NVL(Global.getOptionSymbol,"null"),Subscribe_Order_Symbol,"Validate_Subscribe_Order_Symbol");
				Assert.assertEquals(NVL(Global.getOptionText,"null"),Subscribe_Order_Text,"Validate_Subscribe_Order_Text");
				Assert.assertEquals(NVL(Global.getOptionSide,"null"),Subscribe_Order_Side,"Validate_Subscribe_Order_Side");
				Assert.assertEquals(NVL(Global.getOptionComplianceID,"null"),Subscribe_Order_complianceID,"Validate_Subscribe_Order_complianceID");
				Assert.assertEquals(NVL(Global.getOptionOrdType,"null"),Subscribe_Order_OrderType,"Validate_Subscribe_Order_OrderType");
				Assert.assertEquals(Global.getOptionStopPx,Double.parseDouble(Subscribe_Order_stopPx),"Validate_Subscribe_Order_stopPx");
				Assert.assertEquals(NVL(Global.getOptionTimeInForce,"null"),Subscribe_Order_timeInForce,"Validate_Subscribe_Order_timeInForce");
				Assert.assertEquals(NVL(Global.getOptionSymbolSfx,"null") ,Subscribe_Order_symbolSfx,"Validate_Subscribe_Order_symbolSfx");
				Assert.assertEquals(NVL(Global.getOptionSymbolWithoutSfx,"null") ,Subscribe_Order_symbolWithoutSfx,"Validate_Subscribe_Order_symbolWithoutSfx");
				Assert.assertEquals(NVL(Global.getOptionTifDesc,"null"),Subscribe_Order_tifDesc,"Validate_Subscribe_Order_tifDesc");
				Assert.assertEquals(NVL(Global.getOptionOrderTypeDesc,"null"),Subscribe_Order_orderTypeDesc,"Validate_Subscribe_Order_orderTypeDesc");
				Assert.assertEquals(NVL(Global.getOptionStatusDesc,"null"),Subscribe_Order_ExpectedStatus,"Validate_Subscribe_Order_StatusDesc");
				Assert.assertEquals(Global.getOptionAvgPx,Double.parseDouble(Subscribe_Order_avgPx),"Validate_Subscribe_Order_avgPx");
				Assert.assertEquals(Global.getOptionCumQty,Double.parseDouble(Subscribe_Order_cumQty),"Validate_Subscribe_Order_cumQty");
				Assert.assertEquals(Global.getOptionWorkableQty,Double.parseDouble(Subscribe_Order_workableQty),"Validate_Subscribe_Order_workableQty");
				Assert.assertEquals(Global.getOptionLeavesQty,Double.parseDouble(Subscribe_Order_leavesQty),"Validate_Subscribe_Order_leavesQty");
				Assert.assertEquals(Global.getOptionOrderQty,Double.parseDouble(Subscribe_Order_OrderQty),"Validate_Subscribe_Order_OrderQty");
				Assert.assertEquals(NVL(Global.getOptionLocateID,"null"),Subscribe_Order_locateID,"Validate_Subscribe_Order_locateID");
				Assert.assertEquals(NVL(Global.getOptionContactName,"null"),Subscribe_Order_contactName,"Validate_Subscribe_Order_contactName");
				Assert.assertEquals(NVL(Global.getOptionLocateRequired,"null"),Subscribe_Order_locateRequired,"Validate_Subscribe_Order_locateRequired");
				Assert.assertEquals(Global.getOptionLocateRate,Double.parseDouble(Subscribe_Order_locateRate),"Validate_Subscribe_Order_locateRate");
				Assert.assertEquals(NVL(Global.getOptionDestination,"null"),Subscribe_Order_Destination,"Validate_Subscribe_Order_Destination");
				Assert.assertEquals(NVL(Global.getOptionBoothID,"null"),Subscribe_Order_boothID,"Validate_Subscribe_Order_boothID");
				//Assert.assertEquals(Global.getOptionTime.substring(0,10),localDateTime.format(time_formatter),"Validate_time");  
				Assert.assertEquals(Global.getOptionTransactTime.substring(0,10),localDateTime.format(transactTime_formatter),"Validate_transactTime");
				Assert.assertEquals(Global.getOptionMaturityDay,Subscribe_Order_maturityDay,"Validate_Subscribe_Order_maturityDay");
				Assert.assertEquals(Global.getOptionMaturityMonthYear,Subscribe_Order_maturityMonthYear,"Validate_Subscribe_Order_maturityMonthYear");
				Assert.assertEquals(Global.getOptionMaturityMonthYearDesc,Subscribe_Order_maturityMonthYearDesc,"Validate_Subscribe_Order_maturityMonthYearDesc");
				Assert.assertEquals(Global.getOptionMaturityDate,Subscribe_Order_maturityDate,"Validate_Subscribe_Order_maturityDate");
				Assert.assertEquals(Global.getOptionStrikePrice,Double.parseDouble(Subscribe_Order_strikePrice),"Validate_Subscribe_Order_strikePrice ");
				Assert.assertEquals(NVL(Global.getOptionSymbol,"null"),Subscribe_Order_optionSymbol,"Validate_Subscribe_Order_optionSymbol ");
				Assert.assertEquals(NVL(Global.getOptionDesc,"null"),(Subscribe_Order_Symbol+" "+Subscribe_Order_optionDateDesc+" "+Subscribe_Order_strikePrice+0+" "+Subscribe_Order_putOrCall),"Validate_Subscribe_Order_OptionDesc ");
				Assert.assertEquals(NVL(Global.getOptionCmta,"null"),Subscribe_Order_cmta,"Validate_Subscribe_Order_cmta ");
				Assert.assertEquals(NVL(Global.getOptionExecBroker,"null"),Subscribe_Order_execBroker,"Validate_Subscribe_Order_execBroker ");
				Assert.assertEquals(Global.getOptionPutOrCallInt,Integer.parseInt(Subscribe_Order_putOrCallInt),"Validate_Subscribe_Order_putOrCallInt ");
				Assert.assertEquals(NVL(Global.getOptionPutOrCall,"null"),Subscribe_Order_putOrCall,"Validate_Subscribe_Order_putOrCall ");
				Assert.assertEquals(Global.getOptionCoveredOrUncoveredInt,Integer.parseInt(Subscribe_Order_coveredOrUncoveredInt),"Validate_Subscribe_Order_coveredOrUncoveredInt ");
				Assert.assertEquals(NVL(Global.getOptionCoveredOrUncovered,"null"),Subscribe_Order_coveredOrUncovered,"Validate_Subscribe_Order_coveredOrUncovered ");
				Assert.assertEquals(Global.getOptionCustomerOrFirmInt,Integer.parseInt(Subscribe_Order_customerOrFirmInt),"Validate_Subscribe_Order_customerOrFirmInt");
				Assert.assertEquals(NVL(Global.getOptionCustomerOrFirm,"null"),Subscribe_Order_customerOrFirm,"Validate_Subscribe_Order_customerOrFirm");
				Assert.assertEquals(Global.getOptionOpenCloseBoxed,Integer.parseInt(Subscribe_Order_openCloseBoxed),"Validate_Subscribe_Order_openCloseBoxed");
				Assert.assertEquals(NVL(Global.getOptionOpenClose,"null"),Subscribe_Order_openClose,"Validate_Subscribe_Order_openClose");
				break;

			case "Rejected":
				Assert.assertNotEquals(Global.getOptionID,null,"Validate_ID");
				Assert.assertNotEquals(Global.getOptionOrderID,null,"Validate_OrderID");
				Assert.assertNotEquals(Global.getOptionqOrderID,null,"Validate_qOrderID");
				Assert.assertNotEquals(Global.getOptionclOrdID,null,"Validate_clOrdID");
				Assert.assertEquals	(Global.getOptionOrigClOrdID,null,"Validate_OrigClOrdID");
				Assert.assertNotEquals(Global.getOptionTime,null,"Validate_time");
				Assert.assertEquals(NVL(Global.getOptionOriginatingUserDesc,"null"),Subscribe_Order_UserID,"Validate_Subscribe_Order_UserID ");
				Assert.assertEquals(NVL(Global.getOptionSideDesc,"null"),Subscribe_Order_SideDesc,"Validate_Subscribe_Order_SideDesc ");
				Assert.assertEquals(NVL(Global.getOptionStatus,"null"),Subscribe_Order_ExpectedStatus,"Validate_Subscribe_Order_ExpectedStatus ");
				Assert.assertEquals(NVL(Global.getOptionAccount,"null"),Subscribe_Order_Account,"Validate_Subscribe_Order_Account ");
				Assert.assertEquals(Global.getOptionPrice,Double.parseDouble(Subscribe_Order_Price),"Validate_Subscribe_Order_Price");
				Assert.assertEquals(NVL(Global.getOptionSymbol,"null"),Subscribe_Order_Symbol,"Validate_Subscribe_Order_Symbol");
				Assert.assertEquals(NVL(Global.getOptionText,"null"),Subscribe_Order_Text,"Validate_Subscribe_Order_Text");
				Assert.assertEquals(NVL(Global.getOptionSide,"null"),Subscribe_Order_Side,"Validate_Subscribe_Order_Side");
				Assert.assertEquals(NVL(Global.getOptionComplianceID,"null"),Subscribe_Order_complianceID,"Validate_Subscribe_Order_complianceID");
				Assert.assertEquals(NVL(Global.getOptionOrdType,"null"),Subscribe_Order_OrderType,"Validate_Subscribe_Order_OrderType");
				Assert.assertEquals(Global.getOptionStopPx,Double.parseDouble(Subscribe_Order_stopPx),"Validate_Subscribe_Order_stopPx");
				Assert.assertEquals(NVL(Global.getOptionTimeInForce,"null"),Subscribe_Order_timeInForce,"Validate_Subscribe_Order_timeInForce");
				Assert.assertEquals(NVL(Global.getOptionSymbolSfx,"null") ,Subscribe_Order_symbolSfx,"Validate_Subscribe_Order_symbolSfx");
				Assert.assertEquals(NVL(Global.getOptionSymbolWithoutSfx,"null") ,Subscribe_Order_symbolWithoutSfx,"Validate_Subscribe_Order_symbolWithoutSfx");
				Assert.assertEquals(NVL(Global.getOptionTifDesc,"null"),Subscribe_Order_tifDesc,"Validate_Subscribe_Order_tifDesc");
				Assert.assertEquals(NVL(Global.getOptionOrderTypeDesc,"null"),Subscribe_Order_orderTypeDesc,"Validate_Subscribe_Order_orderTypeDesc");
				Assert.assertEquals(NVL(Global.getOptionStatusDesc,"null"),Subscribe_Order_ExpectedStatus,"Validate_Subscribe_Order_StatusDesc");
				Assert.assertEquals(Global.getOptionAvgPx,Double.parseDouble(Subscribe_Order_avgPx),"Validate_Subscribe_Order_avgPx");
				Assert.assertEquals(Global.getOptionCumQty,Double.parseDouble(Subscribe_Order_cumQty),"Validate_Subscribe_Order_cumQty");
				Assert.assertEquals(Global.getOptionWorkableQty,Double.parseDouble(Subscribe_Order_workableQty),"Validate_Subscribe_Order_workableQty");
				Assert.assertEquals(Global.getOptionLeavesQty,Double.parseDouble(Subscribe_Order_leavesQty),"Validate_Subscribe_Order_leavesQty");
				Assert.assertEquals(Global.getOptionOrderQty,Double.parseDouble(Subscribe_Order_OrderQty),"Validate_Subscribe_Order_OrderQty");
				Assert.assertEquals(NVL(Global.getOptionLocateID,"null"),Subscribe_Order_locateID,"Validate_Subscribe_Order_locateID");
				Assert.assertEquals(NVL(Global.getOptionContactName,"null"),Subscribe_Order_contactName,"Validate_Subscribe_Order_contactName");
				Assert.assertEquals(NVL(Global.getOptionLocateRequired,"null"),Subscribe_Order_locateRequired,"Validate_Subscribe_Order_locateRequired");
				Assert.assertEquals(Global.getOptionLocateRate,Double.parseDouble(Subscribe_Order_locateRate),"Validate_Subscribe_Order_locateRate");
				Assert.assertEquals(NVL(Global.getOptionDestination,"null"),Subscribe_Order_Destination,"Validate_Subscribe_Order_Destination");
				Assert.assertEquals(NVL(Global.getOptionBoothID,"null"),Subscribe_Order_boothID,"Validate_Subscribe_Order_boothID");
				//Assert.assertEquals(Global.getOptionTime.substring(0,10),localDateTime.format(time_formatter),"Validate_time");  
				Assert.assertEquals(Global.getOptionTransactTime.substring(0,10),localDateTime.format(transactTime_formatter),"Validate_transactTime");
				Assert.assertEquals(Global.getOptionMaturityDay,Subscribe_Order_maturityDay,"Validate_Subscribe_Order_maturityDay");
				Assert.assertEquals(Global.getOptionMaturityMonthYear,Subscribe_Order_maturityMonthYear,"Validate_Subscribe_Order_maturityMonthYear");
				Assert.assertEquals(Global.getOptionMaturityMonthYearDesc,Subscribe_Order_maturityMonthYearDesc,"Validate_Subscribe_Order_maturityMonthYearDesc");
				Assert.assertEquals(Global.getOptionMaturityDate,Subscribe_Order_maturityDate,"Validate_Subscribe_Order_maturityDate");
				Assert.assertEquals(Global.getOptionStrikePrice,Double.parseDouble(Subscribe_Order_strikePrice),"Validate_Subscribe_Order_strikePrice ");
				Assert.assertEquals(NVL(Global.getOptionSymbol,"null"),Subscribe_Order_optionSymbol,"Validate_Subscribe_Order_optionSymbol ");
				Assert.assertEquals(NVL(Global.getOptionDesc,"null"),(Subscribe_Order_Symbol+" "+Subscribe_Order_optionDateDesc+" "+Subscribe_Order_strikePrice+0+" "+Subscribe_Order_putOrCall),"Validate_Subscribe_Order_OptionDesc ");
				Assert.assertEquals(NVL(Global.getOptionCmta,"null"),Subscribe_Order_cmta,"Validate_Subscribe_Order_cmta ");
				Assert.assertEquals(NVL(Global.getOptionExecBroker,"null"),Subscribe_Order_execBroker,"Validate_Subscribe_Order_execBroker ");
				Assert.assertEquals(Global.getOptionPutOrCallInt,Integer.parseInt(Subscribe_Order_putOrCallInt),"Validate_Subscribe_Order_putOrCallInt ");
				Assert.assertEquals(NVL(Global.getOptionPutOrCall,"null"),Subscribe_Order_putOrCall,"Validate_Subscribe_Order_putOrCall ");
				Assert.assertEquals(Global.getOptionCoveredOrUncoveredInt,Integer.parseInt(Subscribe_Order_coveredOrUncoveredInt),"Validate_Subscribe_Order_coveredOrUncoveredInt ");
				Assert.assertEquals(NVL(Global.getOptionCoveredOrUncovered,"null"),Subscribe_Order_coveredOrUncovered,"Validate_Subscribe_Order_coveredOrUncovered ");
				Assert.assertEquals(Global.getOptionCustomerOrFirmInt,Integer.parseInt(Subscribe_Order_customerOrFirmInt),"Validate_Subscribe_Order_customerOrFirmInt");
				Assert.assertEquals(NVL(Global.getOptionCustomerOrFirm,"null"),Subscribe_Order_customerOrFirm,"Validate_Subscribe_Order_customerOrFirm");
				Assert.assertEquals(Global.getOptionOpenCloseBoxed,Integer.parseInt(Subscribe_Order_openCloseBoxed),"Validate_Subscribe_Order_openCloseBoxed");
				Assert.assertEquals(NVL(Global.getOptionOpenClose,"null"),Subscribe_Order_openClose,"Validate_Subscribe_Order_openClose");
				break;

			case "Filled":

				Global.getAvgPrice=GetOrderAvgPx(endpoint_version,Subscribe_Executions_BasePath,Content_Type,Global.getOptionOrderID,Status_Code);
				Assert.assertNotEquals(Global.getOptionID,null,"Validate_ID");
				Assert.assertNotEquals(Global.getOptionOrderID,null,"Validate_OrderID");
				Assert.assertNotEquals(Global.getOptionqOrderID,null,"Validate_qOrderID");
				Assert.assertNotEquals(Global.getOptionclOrdID,null,"Validate_clOrdID");
				Assert.assertEquals	(Global.getOptionOrigClOrdID,null,"Validate_OrigClOrdID");
				Assert.assertNotEquals(Global.getOptionTime,null,"Validate_time");
				Assert.assertEquals(NVL(Global.getOptionOriginatingUserDesc,"null"),Subscribe_Order_UserID,"Validate_Subscribe_Order_UserID ");
				Assert.assertEquals(NVL(Global.getOptionSideDesc,"null"),Subscribe_Order_SideDesc,"Validate_Subscribe_Order_SideDesc ");
				Assert.assertEquals(NVL(Global.getOptionStatus,"null"),Subscribe_Order_ExpectedStatus,"Validate_Subscribe_Order_ExpectedStatus ");
				Assert.assertEquals(NVL(Global.getOptionAccount,"null"),Subscribe_Order_Account,"Validate_Subscribe_Order_Account ");
				Assert.assertEquals(Global.getOptionPrice,Double.parseDouble(Subscribe_Order_Price),"Validate_Subscribe_Order_Price");
				Assert.assertEquals(NVL(Global.getOptionSymbol,"null"),Subscribe_Order_Symbol,"Validate_Subscribe_Order_Symbol");
				Assert.assertEquals(NVL(Global.getOptionText,"null"),Subscribe_Order_Text,"Validate_Subscribe_Order_Text");
				Assert.assertEquals(NVL(Global.getOptionSide,"null"),Subscribe_Order_Side,"Validate_Subscribe_Order_Side");
				Assert.assertEquals(NVL(Global.getOptionComplianceID,"null"),Subscribe_Order_complianceID,"Validate_Subscribe_Order_complianceID");
				Assert.assertEquals(NVL(Global.getOptionOrdType,"null"),Subscribe_Order_OrderType,"Validate_Subscribe_Order_OrderType");
				Assert.assertEquals(Global.getOptionStopPx,Double.parseDouble(Subscribe_Order_stopPx),"Validate_Subscribe_Order_stopPx");
				Assert.assertEquals(NVL(Global.getOptionTimeInForce,"null"),Subscribe_Order_timeInForce,"Validate_Subscribe_Order_timeInForce");
				Assert.assertEquals(NVL(Global.getOptionSymbolSfx,"null") ,Subscribe_Order_symbolSfx,"Validate_Subscribe_Order_symbolSfx");
				Assert.assertEquals(NVL(Global.getOptionSymbolWithoutSfx,"null") ,Subscribe_Order_symbolWithoutSfx,"Validate_Subscribe_Order_symbolWithoutSfx");
				Assert.assertEquals(NVL(Global.getOptionTifDesc,"null"),Subscribe_Order_tifDesc,"Validate_Subscribe_Order_tifDesc");
				Assert.assertEquals(NVL(Global.getOptionOrderTypeDesc,"null"),Subscribe_Order_orderTypeDesc,"Validate_Subscribe_Order_orderTypeDesc");
				Assert.assertEquals(NVL(Global.getOptionStatusDesc,"null"),Subscribe_Order_ExpectedStatus,"Validate_Subscribe_Order_StatusDesc");
				Assert.assertEquals(Double.parseDouble(decimalFormat.format(Global.getOptionAvgPx)),Global.getAvgPrice,"Validate_Subscribe_Order_avgPx");
				//Assert.assertEquals((Global.getavgPx.toString()).substring(0,6),(Global.getAvgPrice.toString()).substring(0,6),"Validate_Subscribe_Order_avgPx");              
				Assert.assertEquals(Global.getOptionCumQty,Double.parseDouble(Subscribe_Order_cumQty),"Validate_Subscribe_Order_cumQty");
				Assert.assertEquals(Global.getOptionWorkableQty,Double.parseDouble(Subscribe_Order_workableQty),"Validate_Subscribe_Order_workableQty");
				Assert.assertEquals(Global.getOptionLeavesQty,Double.parseDouble(Subscribe_Order_leavesQty),"Validate_Subscribe_Order_leavesQty");
				Assert.assertEquals(Global.getOptionOrderQty,Double.parseDouble(Subscribe_Order_OrderQty),"Validate_Subscribe_Order_OrderQty");
				Assert.assertEquals(NVL(Global.getOptionLocateID,"null"),Subscribe_Order_locateID,"Validate_Subscribe_Order_locateID");
				Assert.assertEquals(NVL(Global.getOptionContactName,"null"),Subscribe_Order_contactName,"Validate_Subscribe_Order_contactName");
				Assert.assertEquals(NVL(Global.getOptionLocateRequired,"null"),Subscribe_Order_locateRequired,"Validate_Subscribe_Order_locateRequired");
				Assert.assertEquals(Global.getOptionLocateRate,Double.parseDouble(Subscribe_Order_locateRate),"Validate_Subscribe_Order_locateRate");
				Assert.assertEquals(NVL(Global.getOptionDestination,"null"),Subscribe_Order_Destination,"Validate_Subscribe_Order_Destination");
				Assert.assertEquals(NVL(Global.getOptionBoothID,"null"),Subscribe_Order_boothID,"Validate_Subscribe_Order_boothID");
				//Assert.assertEquals(Global.getOptionTime.substring(0,10),localDateTime.format(time_formatter),"Validate_time");  
				Assert.assertEquals(Global.getOptionTransactTime.substring(0,10),localDateTime.format(transactTime_formatter),"Validate_transactTime");
				Assert.assertEquals(Global.getOptionMaturityDay,Subscribe_Order_maturityDay,"Validate_Subscribe_Order_maturityDay");
				Assert.assertEquals(Global.getOptionMaturityMonthYear,Subscribe_Order_maturityMonthYear,"Validate_Subscribe_Order_maturityMonthYear");
				Assert.assertEquals(Global.getOptionMaturityMonthYearDesc,Subscribe_Order_maturityMonthYearDesc,"Validate_Subscribe_Order_maturityMonthYearDesc");
				Assert.assertEquals(Global.getOptionMaturityDate,Subscribe_Order_maturityDate,"Validate_Subscribe_Order_maturityDate");
				Assert.assertEquals(Global.getOptionStrikePrice,Double.parseDouble(Subscribe_Order_strikePrice),"Validate_Subscribe_Order_strikePrice ");
				Assert.assertEquals(NVL(Global.getOptionSymbol,"null"),Subscribe_Order_optionSymbol,"Validate_Subscribe_Order_optionSymbol ");
				Assert.assertEquals(NVL(Global.getOptionDesc,"null"),(Subscribe_Order_Symbol+" "+Subscribe_Order_optionDateDesc+" "+Subscribe_Order_strikePrice+0+" "+Subscribe_Order_putOrCall),"Validate_Subscribe_Order_OptionDesc ");
				Assert.assertEquals(NVL(Global.getOptionCmta,"null"),Subscribe_Order_cmta,"Validate_Subscribe_Order_cmta ");
				Assert.assertEquals(NVL(Global.getOptionExecBroker,"null"),Subscribe_Order_execBroker,"Validate_Subscribe_Order_execBroker ");
				Assert.assertEquals(Global.getOptionPutOrCallInt,Integer.parseInt(Subscribe_Order_putOrCallInt),"Validate_Subscribe_Order_putOrCallInt ");
				Assert.assertEquals(NVL(Global.getOptionPutOrCall,"null"),Subscribe_Order_putOrCall,"Validate_Subscribe_Order_putOrCall ");
				Assert.assertEquals(Global.getOptionCoveredOrUncoveredInt,Integer.parseInt(Subscribe_Order_coveredOrUncoveredInt),"Validate_Subscribe_Order_coveredOrUncoveredInt ");
				Assert.assertEquals(NVL(Global.getOptionCoveredOrUncovered,"null"),Subscribe_Order_coveredOrUncovered,"Validate_Subscribe_Order_coveredOrUncovered ");
				Assert.assertEquals(Global.getOptionCustomerOrFirmInt,Integer.parseInt(Subscribe_Order_customerOrFirmInt),"Validate_Subscribe_Order_customerOrFirmInt");
				Assert.assertEquals(NVL(Global.getOptionCustomerOrFirm,"null"),Subscribe_Order_customerOrFirm,"Validate_Subscribe_Order_customerOrFirm");
				Assert.assertEquals(Global.getOptionOpenCloseBoxed,Integer.parseInt(Subscribe_Order_openCloseBoxed),"Validate_Subscribe_Order_openCloseBoxed");
				Assert.assertEquals(NVL(Global.getOptionOpenClose,"null"),Subscribe_Order_openClose,"Validate_Subscribe_Order_openClose");
				LoggingManager.logger.info("API - Actual AvgPrice: ["+decimalFormat.format(Global.getOptionAvgPx)+"] - Expected AvgPrice: ["+Global.getAvgPrice+"]");
				break;

			case "Partially filled":
				break;

			default:
				break;
		}

	}
	catch (Exception e)
	{
		LoggingManager.logger.error(e);
	}
}


public static Double GetOrderAvgPx(String endpoint_version,String Subscribe_Executions_BasePath,String Content_Type,String OrderID,String StatusCode) {
	String getResponseArray = "";
	int count = 0;
	Double orderAvgPx = 0.0000;
	LocalDateTime localDateTime = LocalDateTime.now();
	DateTimeFormatter transactTime_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	DecimalFormat decimalFormat = new DecimalFormat("0.000");
	decimalFormat.getRoundingMode();
	try {
		RestAssured.baseURI = Global.BaseURL;
		Response get_execution_response =

				given()
						.header("Content-Type", Content_Type)
						.header("Authorization", "Bearer " + Global.getAccToken)
						.when()
						.get(Subscribe_Executions_BasePath)

						.then()
						.extract().response();

		LoggingManager.logger.info("API-Get AvgPx Subscribe_Executions_BasePath : [" + Subscribe_Executions_BasePath + "]");
		LoggingManager.logger.info("API-Get AvgPx Content_Type : [" + Content_Type + "]");
		LoggingManager.logger.info("API-Get AvgPx Subscribe_Executions_StatusCode : [" + get_execution_response.getStatusCode() + "]");
		Assert.assertEquals(get_execution_response.getStatusCode(), Integer.parseInt(StatusCode), "Verify_Get_OrderAvgPx_Executions_Response");
		JsonPath jsonresponse = new JsonPath(get_execution_response.getBody().asString());
		getResponseArray = apiRespVersion(endpoint_version);
		int ResponseArraySize = jsonresponse.getInt(getResponseArray + ".size()");
		AvgPxLoop:
		for (int position = ResponseArraySize - 1; position >= 0; position--) {
			String response_OrderID = jsonresponse.getString("data.eventData[" + position + "].orderId");
			Double response_avgPx = jsonresponse.getDouble("data.eventData[" + position + "].avgPx");
			Double response_lastPx = jsonresponse.getDouble("data.eventData[" + position + "].lastPx");
			String response_transactTime = jsonresponse.getString("data.eventData[" + position + "].transactTime");

			if (response_OrderID.equalsIgnoreCase(OrderID) && (response_transactTime.substring(0, 10)).equalsIgnoreCase(localDateTime.format(transactTime_formatter))) {
				count += 1;
				orderAvgPx += response_lastPx;

			} else {
				continue AvgPxLoop;
			}
		}
		orderAvgPx /= count;

		return Double.parseDouble(decimalFormat.format(orderAvgPx));
	}
	catch (Exception e)
	{
		LoggingManager.logger.error("Order Average Price : "+Double.parseDouble(decimalFormat.format(orderAvgPx)) +"Exeception :"+e);
		return 0.0;
	}
}


public static Boolean GetOrderValidate(String Get_orders_basePath,
									String AccToken,
									String contentType,
									int statuscode,
									String endpoint_version,
									String orderid,
									String cancelled_Validation_Check)
{
	try
	{
		String getResponseArray="";
		RestAssured.baseURI=Global.BaseURL;
		Response get_orders_response=

				given()
						.header("Content-Type",contentType)
						.header("Authorization", "Bearer " + AccToken)
						.when()
						.get(Get_orders_basePath)
						.then()
						.statusCode(statuscode)
						//.statusLine("HTTP/1.1 200 OK")
						.extract().response();
		getResponseArray=apiRespVersion(endpoint_version);
		JsonPath jsonresponse = new JsonPath(get_orders_response.asString());
		int ResponseArraySize = jsonresponse.getInt(getResponseArray+".size()");
		String OrderID="";
		String status="";
		for(int position = ResponseArraySize-1; position >=0; position--)
		{

			OrderID = jsonresponse.getString(getResponseArray+"["+position+"].orderId");
			status = jsonresponse.getString(getResponseArray+"["+position+"].status");
			//check if condition meets
			if((OrderID.equalsIgnoreCase(orderid)) && (status.equalsIgnoreCase(cancelled_Validation_Check)))
			{
				Global.ValidationFlag=true;
				Global.getStatus=status;
				break;
			}
		}

		LoggingManager.logger.info("API- Order Status Found : ["+status+"]");
		return Global.ValidationFlag;
	}
	catch (Exception e)
	{
		LoggingManager.logger.error(e);
		return false;
	}
}
	
	
public static Boolean UpdateOrderValidate( String Get_orders_basePath,
										String AccToken,
										String contentType,
										int statuscode,
										String endpoint_version,
										String orderid,
										String Order_Update_OrdType,
									    String Order_Update_OrderQty,
									    String Order_Update_Price
									    ) 
{
	try
	{
		String getResponseArray="";
		RestAssured.baseURI=Global.BaseURL;
		Response get_orders_response=	given()
				.header("Content-Type",contentType)
				.header("Authorization", "Bearer " + AccToken)
				.when()
				.get(Get_orders_basePath)
				.then()
				.extract().response();

		Assert.assertEquals(get_orders_response.getStatusCode(),statuscode,"Update Orders Record Failed");
		JsonPath jsonresponse = new JsonPath(get_orders_response.getBody().asString());
		getResponseArray=apiRespVersion(endpoint_version);
		int ResponseArraySize = jsonresponse.getInt(getResponseArray+".size()");
		String OrderID ="",response_ordType="",response_orderQty="",response_price="";
		for(int position = ResponseArraySize-1; position >=0; position--)
		{
			OrderID = jsonresponse.getString(getResponseArray+"["+position+"].orderId");
			response_ordType = jsonresponse.getString(getResponseArray+"["+position+"].ordType");
			response_orderQty = jsonresponse.getString(getResponseArray+"["+position+"].orderQty");
			response_price = jsonresponse.getString("eventData["+position+"].price");
			// String response_complianceID = jsonresponse.getString("eventData["+position+"].complianceID");
		   /*    LoggingManager.logger.info("API- Checking Update OrderID : ["+orderid+"] and Found OrderID : ["+OrderID+"]");
		         LoggingManager.logger.info("API- Checking Update response_ordType : ["+Order_Update_OrdType+"] and Found response_ordType : ["+response_ordType+"]");
		         LoggingManager.logger.info("API- Checking Update response_orderQty : ["+Order_Update_OrderQty+"] and Found response_orderQty : ["+response_orderQty+"]");
		         LoggingManager.logger.info("API- Checking Update response_price : ["+Order_Update_Price+"] and Found response_price : ["+response_price+"]");
		         LoggingManager.logger.info("-----------------------------------------------------------------------------------------------------------------------");
			*/
				if(OrderID.equalsIgnoreCase(orderid)
				&& response_ordType.equalsIgnoreCase(Order_Update_OrdType)
				&& response_orderQty.equalsIgnoreCase(Order_Update_OrderQty)
				&& response_price.equalsIgnoreCase(Order_Update_Price))
				{
					Global.ValidationFlag=true;
					break;
				}
				else
				{ continue; }
		}
		return Global.ValidationFlag;
	}
	catch (Exception e)
	{
		LoggingManager.logger.error(e);
		return false;
	}
}
	
public static Boolean GetOptionOrderValidate(String Get_orders_basePath,
											String AccToken,
											String contentType,
											int statuscode,
											String endpoint_version,
											String orderid,
											String cancelled_Validaion_Check)
{
	try
	{
		String getResponseArray="";
		RestAssured.baseURI=Global.BaseURL;
		Response get_orders_response=
										given()
										.header("Content-Type",contentType)
										.header("Authorization", "Bearer " + AccToken)
										.when()
										.get(Get_orders_basePath)
										.then()
										.statusCode(statuscode)
										//.statusLine("HTTP/1.1 200 OK")
										.extract().response();
		getResponseArray="eventData";
		JsonPath jsonresponse = new JsonPath(get_orders_response.asString());
		int ResponseArraySize = jsonresponse.getInt(getResponseArray+".size()");
		String OrderID="";
		String status="";
		for(int position = ResponseArraySize-1; position >=0; position--)
		{

			OrderID = jsonresponse.getString(getResponseArray+"["+position+"].orderId");
			status = jsonresponse.getString(getResponseArray+"["+position+"].status");
			//check if condition meets
			if((OrderID.equalsIgnoreCase(orderid)) && (status.equalsIgnoreCase(cancelled_Validaion_Check)))
			{
				Global.ValidationFlag=true;
				Global.getStatus=status;
				break;
			}
		}

		LoggingManager.logger.info("API- Order Status Found : ["+status+"]");
		return Global.ValidationFlag;
	}
	catch (Exception e)
	{
		LoggingManager.logger.error(e);
		return false;
	}
}


public static Boolean UpdateOptionOrderValidate( String Get_orders_basePath,
													String AccToken,
													String contentType,
													int statuscode,
													String endpoint_version,
													String orderid,
													String Order_Update_OrdType,
													String Order_Update_OrderQty,
													String Order_Update_Price)
{
	try
	{
		String getResponseArray="";
		RestAssured.baseURI=Global.BaseURL;
		Response get_orders_response=
									given()
									.header("Content-Type",contentType)
									.header("Authorization", "Bearer " + AccToken)
									.when()
									.get(Get_orders_basePath)
									.then()
									.extract().response();

		Assert.assertEquals(get_orders_response.getStatusCode(),statuscode,"Update Orders Record Failed");
		JsonPath jsonresponse = new JsonPath(get_orders_response.getBody().asString());
		getResponseArray="eventData";
		int ResponseArraySize = jsonresponse.getInt(getResponseArray+".size()");
		String OrderID ="",response_ordType="",response_orderQty="",response_price="";
		for(int position = ResponseArraySize-1; position >=0; position--)
		{
			OrderID = jsonresponse.getString(getResponseArray+"["+position+"].orderId");
			response_ordType = jsonresponse.getString(getResponseArray+"["+position+"].ordType");
			response_orderQty = jsonresponse.getString(getResponseArray+"["+position+"].orderQty");
			response_price = jsonresponse.getString("eventData["+position+"].price");
			// String response_complianceID = jsonresponse.getString("eventData["+position+"].complianceID");
				
				/*    LoggingManager.logger.info("API- Checking Update OrderID : ["+orderid+"] and Found OrderID : ["+OrderID+"]");
				LoggingManager.logger.info("API- Checking Update response_ordType : ["+Order_Update_OrdType+"] and Found response_ordType : ["+response_ordType+"]");
				LoggingManager.logger.info("API- Checking Update response_orderQty : ["+Order_Update_OrderQty+"] and Found response_orderQty : ["+response_orderQty+"]");
				LoggingManager.logger.info("API- Checking Update response_price : ["+Order_Update_Price+"] and Found response_price : ["+response_price+"]");
				LoggingManager.logger.info("-----------------------------------------------------------------------------------------------------------------------");
				*/
			if(OrderID.equalsIgnoreCase(orderid)
					&& response_ordType.equalsIgnoreCase(Order_Update_OrdType)
					&& response_orderQty.equalsIgnoreCase(Order_Update_OrderQty)
					&& response_price.equalsIgnoreCase(Order_Update_Price))
			{
				Global.ValidationFlag=true;
				break;
			}
			else
			{ continue; }
		}
		return Global.ValidationFlag;
	}
	catch (Exception e)
	{
		LoggingManager.logger.error(e);
		return false;
	}
}

public static void Flat_Equity_Positions(String endpoint_version,
											 String Subscribe_Order_Positions_BasePath,
											 String Content_Type,
											 String Subscribe_Order_Positions_StatusCode,
											 String PositionID,
											 String Validate_Position_FLAT,
											 String Order_Creation_BasePath,
											 String Order_OrdType,
											 String Order_TimeInForce,
											 String Order_Destination,
											 String Order_Price,
											 String Order_StopPx,
											 String Order_Creation_StatusCode)
	{
		try {
			Global.getorderType = "Equity";
			String getResponseArray = "", getexecQty = "",Flat_Order_Position_Side="";
			int ResponseArraySize = 0, position = 0;
			Global.getAvgPrice=0.0;
			Global.getSHORTrealizedPnL=0.0;
			Global.getLONGrealizedPnL=0.0;
			Global.getcompleteDayBuyOrderQty=0.0;
			Global.getcompleteDaySellLongOrderQty=0.0;
			Global.getcompleteDayBuyOrderQty=0.0;
			Global.getcompleteDaySellShortOrderQty=0.0;
			getResponseArray = apiRespVersion(endpoint_version);
			Response get_position_response =

					given()
							.header("Content-Type", Content_Type)
							.header("Authorization", "Bearer " + Global.getAccToken)
							.when()
							.get(Subscribe_Order_Positions_BasePath)

							.then()
							.extract().response();

			LoggingManager.logger.info("API-Subscribe_Order_Positions_BasePath : [" + Subscribe_Order_Positions_BasePath + "]");
			LoggingManager.logger.info("API-Subscribe_Order_Positions_StatusCode : [" + get_position_response.statusCode() + "]");
			Assert.assertEquals(get_position_response.getStatusCode(), Integer.parseInt(Subscribe_Order_Positions_StatusCode), "Verify_OrderSymbol_Positions");
			JsonPath jsonresponse = new JsonPath(get_position_response.getBody().asString());
			ResponseArraySize = jsonresponse.getInt(getResponseArray + ".size()");
			getresponseloop:
			for (position = ResponseArraySize - 1; position >= 0; position--)
			{
				String response_ID = jsonresponse.getString(getResponseArray + "[" + position + "].id");
				String response_account = jsonresponse.getString(getResponseArray + "[" + position + "].account");
				String response_symbol = jsonresponse.getString(getResponseArray + "[" + position + "].symbol");
				String response_execQty = jsonresponse.getString(getResponseArray + "[" + position + "].execQty");
				String response_positionString = jsonresponse.getString(getResponseArray + "[" + position + "].positionString");
				String response_realizedPnL = jsonresponse.getString(getResponseArray + "[" + position + "].realizedPnL");

				if (response_ID.equalsIgnoreCase(PositionID) && Double.parseDouble(response_execQty) != 0)
				{
					LoggingManager.logger.info("API-Before Flat Position response_ID : [" + response_ID + "]");
					LoggingManager.logger.info("API-Before Flat Position response_positionString : [" + response_positionString + "]");
					LoggingManager.logger.info("API-Before Flat Position response_realizedPnL : [" + response_realizedPnL + "]");
					LoggingManager.logger.info("API-Before Flat Position response_account : [" + response_account + "]");
					LoggingManager.logger.info("API-Before Flat Position response_execQty : [" + response_execQty + "]");
					LoggingManager.logger.info("API-Before Flat Position response_symbol : [" + response_symbol + "]");
					if (Double.parseDouble(response_execQty) < 0)
					{
						getexecQty = response_execQty.substring(1);
						Flat_Order_Position_Side="1";
					}
					else
					{
						getexecQty = response_execQty;
						Flat_Order_Position_Side="2";
					}

					HashMap<String, Object> fill_order_body = new HashMap<String, Object>();
					fill_order_body.put("ordType", Order_OrdType);
					fill_order_body.put("side", Flat_Order_Position_Side);
					fill_order_body.put("timeInForce", Order_TimeInForce);
					fill_order_body.put("destination", Order_Destination);
					fill_order_body.put("account", response_account);
					fill_order_body.put("orderQty", Double.parseDouble(getexecQty));
					fill_order_body.put("symbol", response_symbol);
					fill_order_body.put("Price", Double.parseDouble(Order_Price));
					fill_order_body.put("StopPx", Double.parseDouble(Order_StopPx));
					Response response =
							given()
									.header("Content-Type", Content_Type)
									.header("Authorization", "Bearer " + Global.getAccToken)
									.body(fill_order_body)

									.when()
									.post(Order_Creation_BasePath)

									.then()
									.extract()
									.response();
					Assert.assertEquals(response.getStatusCode(), Integer.parseInt(Order_Creation_StatusCode), "Verify_Order_Creation");
					Response Validate_position_response =
							given()
									.header("Content-Type", Content_Type)
									.header("Authorization", "Bearer " + Global.getAccToken)
									.when()
									.get(Subscribe_Order_Positions_BasePath)

									.then()
									.extract().response();

					Assert.assertEquals(Validate_position_response.getStatusCode(), 200, "Validate_position_response");
					JsonPath JSON_position_response = new JsonPath(Validate_position_response.getBody().asString());
					ResponseArraySize = JSON_position_response.getInt(getResponseArray + ".size()");
					for (position = ResponseArraySize - 1; position >= 0; position--)
					{
						Global.getPosition_id = JSON_position_response.getString(getResponseArray + "[" + position + "].id");
						if (Global.getPosition_id.equalsIgnoreCase(PositionID))
						{
							Global.getPosition_completeDayBuyOrderQty = JSON_position_response.getDouble(getResponseArray + "[" + position + "].completeDayBuyOrderQty");
							Global.getPosition_completeDaySellLongOrderQty = JSON_position_response.getDouble(getResponseArray + "[" + position + "].completeDaySellLongOrderQty");
							Global.getPosition_completeDaySellShortOrderQty = JSON_position_response.getDouble(getResponseArray + "[" + position + "].completeDaySellShortOrderQty");
							Global.getPosition_symbol = JSON_position_response.getString(getResponseArray + "[" + position + "].symbol");
							Global.getPosition_positionString = JSON_position_response.getString(getResponseArray + "[" + position + "].positionString");
							Global.getPosition_avgPrice = JSON_position_response.getDouble(getResponseArray + "[" + position + "].avgPrice");
							Global.getPosition_totDollarOfTrade = JSON_position_response.getDouble(getResponseArray + "[" + position + "].totDollarOfTrade");
							Global.getPosition_execQty = JSON_position_response.getDouble(getResponseArray + "[" + position + "].execQty");
							Global.getPosition_realizedPnL = JSON_position_response.getDouble(getResponseArray + "[" + position + "].realizedPnL");
							Global.getPosition_symbolSfx = JSON_position_response.getString(getResponseArray + "[" + position + "].symbolSfx");
							Global.getPosition_originatingUserDesc = JSON_position_response.getString(getResponseArray + "[" + position + "].originatingUserDesc");
							Global.getPosition_account = JSON_position_response.getString(getResponseArray + "[" + position + "].account");
							Global.getLONGrealizedPnL=Global.getPosition_realizedPnL;
							Global.getSHORTrealizedPnL=Global.getPosition_realizedPnL;
							Global.getcompleteDayBuyOrderQty=Global.getPosition_completeDayBuyOrderQty;
							Global.getcompleteDaySellShortOrderQty=Global.getPosition_completeDaySellShortOrderQty;
							Global.getcompleteDaySellLongOrderQty=Global.getPosition_completeDaySellLongOrderQty;

							LoggingManager.logger.info("API-After Flat Position ID : [" + Global.getPosition_id + "]");
							LoggingManager.logger.info("API-After Flat Position positionString : [" + Global.getPosition_positionString + "]");
							LoggingManager.logger.info("API-After Flat Position realizedPnL : [" + Global.getPosition_realizedPnL + "]");
							LoggingManager.logger.info("API-After Flat Position account : [" + Global.getPosition_account + "]");
							LoggingManager.logger.info("API-After Flat Position execQty : [" + Global.getPosition_execQty + "]");
							LoggingManager.logger.info("API-After Flat Position symbol : [" + Global.getPosition_symbol + "]");
							LoggingManager.logger.info("API-After Flat Position completeDayBuyOrderQty : [" + Global.getPosition_completeDayBuyOrderQty + "]");
							LoggingManager.logger.info("API-After Flat Position completeDaySellLongOrderQty : [" + Global.getPosition_completeDaySellLongOrderQty + "]");
							LoggingManager.logger.info("API-After Flat Position completeDaySellShortOrderQty : [" + Global.getPosition_completeDaySellShortOrderQty + "]");
							LoggingManager.logger.info("API-After Flat Position totDollarOfTrade : [" + Global.getPosition_totDollarOfTrade + "]");
							LoggingManager.logger.info("API-After Flat Position avgPrice : [" + Global.getPosition_avgPrice + "]");
							LoggingManager.logger.info("API-After Flat Position symbolSfx : [" + Global.getPosition_symbolSfx + "]");
							LoggingManager.logger.info("API-After Flat Position originatingUserDesc : [" + Global.getPosition_originatingUserDesc + "]");

							Assert.assertEquals(Global.getPosition_execQty, 0.0, "Validate_ExecQty_Flat_Response_[" + PositionID + "]");
							Assert.assertEquals(Global.getPosition_positionString, Validate_Position_FLAT, "Validate_Position: [" + PositionID + "]");
							break getresponseloop;
						}
					}
				}
				else if (response_ID.equalsIgnoreCase(PositionID)
							&& Double.parseDouble(response_execQty) == 0
							&& response_positionString.equalsIgnoreCase(Validate_Position_FLAT))
				{
					Global.getPosition_id = jsonresponse.getString(getResponseArray + "[" + position + "].id");
					Global.getPosition_completeDayBuyOrderQty = jsonresponse.getDouble(getResponseArray + "[" + position + "].completeDayBuyOrderQty");
					Global.getPosition_completeDaySellLongOrderQty = jsonresponse.getDouble(getResponseArray + "[" + position + "].completeDaySellLongOrderQty");
					Global.getPosition_completeDaySellShortOrderQty = jsonresponse.getDouble(getResponseArray + "[" + position + "].completeDaySellShortOrderQty");
					Global.getPosition_symbol = jsonresponse.getString(getResponseArray + "[" + position + "].symbol");
					Global.getPosition_positionString = jsonresponse.getString(getResponseArray + "[" + position + "].positionString");
					Global.getPosition_avgPrice = jsonresponse.getDouble(getResponseArray + "[" + position + "].avgPrice");
					Global.getPosition_totDollarOfTrade = jsonresponse.getDouble(getResponseArray + "[" + position + "].totDollarOfTrade");
					Global.getPosition_execQty = jsonresponse.getDouble(getResponseArray + "[" + position + "].execQty");
					Global.getPosition_realizedPnL = jsonresponse.getDouble(getResponseArray + "[" + position + "].realizedPnL");
					Global.getPosition_symbolSfx = jsonresponse.getString(getResponseArray + "[" + position + "].symbolSfx");
					Global.getPosition_originatingUserDesc = jsonresponse.getString(getResponseArray + "[" + position + "].originatingUserDesc");
					Global.getPosition_account = jsonresponse.getString(getResponseArray + "[" + position + "].account");
					Global.getLONGrealizedPnL=Global.getPosition_realizedPnL;
					Global.getSHORTrealizedPnL=Global.getPosition_realizedPnL;
					Global.getcompleteDayBuyOrderQty=Global.getPosition_completeDayBuyOrderQty;
					Global.getcompleteDaySellShortOrderQty=Global.getPosition_completeDaySellShortOrderQty;
					Global.getcompleteDaySellLongOrderQty=Global.getPosition_completeDaySellLongOrderQty;

					LoggingManager.logger.info("API-Already Flat Position ID : [" + Global.getPosition_id + "]");
					LoggingManager.logger.info("API-Already Flat Position positionString : [" + Global.getPosition_positionString + "]");
					LoggingManager.logger.info("API-Already Flat Position realizedPnL : [" + Global.getPosition_realizedPnL + "]");
					LoggingManager.logger.info("API-Already Flat Position account : [" + Global.getPosition_account + "]");
					LoggingManager.logger.info("API-Already Flat Position execQty : [" + Global.getPosition_execQty + "]");
					LoggingManager.logger.info("API-Already Flat Position symbol : [" + Global.getPosition_symbol + "]");
					LoggingManager.logger.info("API-Already Flat Position completeDayBuyOrderQty : [" + Global.getPosition_completeDayBuyOrderQty + "]");
					LoggingManager.logger.info("API-Already Flat Position completeDaySellLongOrderQty : [" + Global.getPosition_completeDaySellLongOrderQty + "]");
					LoggingManager.logger.info("API-Already Flat Position completeDaySellShortOrderQty : [" + Global.getPosition_completeDaySellShortOrderQty + "]");
					LoggingManager.logger.info("API-Already Flat Position totDollarOfTrade : [" + Global.getPosition_totDollarOfTrade + "]");
					LoggingManager.logger.info("API-Already Flat Position avgPrice : [" + Global.getPosition_avgPrice + "]");
					LoggingManager.logger.info("API-Already Flat Position symbolSfx : [" + Global.getPosition_symbolSfx + "]");
					LoggingManager.logger.info("API-Already Flat Position originatingUserDesc : [" + Global.getPosition_originatingUserDesc + "]");

					Assert.assertEquals(Global.getPosition_execQty, 0.0, "Validate_ExecQty_Flat_Response_[" + PositionID + "]");
					Assert.assertEquals(Global.getPosition_positionString, Validate_Position_FLAT, "Validate_Position: [" + PositionID + "]");
					break getresponseloop;
				}
				else
				{
					if (position == 0) { LoggingManager.logger.warn("API-Position not found with ID : [" + PositionID + "]"); }
				}
			}
		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}


public static void Flat_Option_Positions(String endpoint_version,
										String Subscribe_Order_Positions_BasePath,
										String Content_Type,
										String Subscribe_Order_Positions_StatusCode,
										String PositionID,
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
										String Order_Creation_StatusCode  )

	{
		try
		{
			Global.getorderType="Option";
			Global.getOptionAvgPrice=0.0;
			Global.getOptionSHORTrealizedPnL=0.0;
			Global.getOptionLONGrealizedPnL=0.0;
			Global.getOptioncompleteDayBuyOrderQty=0.0;
			Global.getOptioncompleteDaySellLongOrderQty=0.0;
			Global.getOptioncompleteDayBuyOrderQty=0.0;
			Global.getOptioncompleteDaySellShortOrderQty=0.0;
			String getResponseArray="",getexecQty="",Flat_Order_Position_Side="";
			int ResponseArraySize=0,position=0,innerposition=0;
			getResponseArray=apiRespVersion(endpoint_version);
			Response get_position_response =
											given()
											.header("Content-Type", Content_Type)
											.header("Authorization", "Bearer " + Global.getAccToken)
											.when()
											.get(Subscribe_Order_Positions_BasePath)

											.then()
											.extract().response();

			LoggingManager.logger.info("API-Subscribe_Order_Positions_BasePath : [" + Subscribe_Order_Positions_BasePath + "]");
			LoggingManager.logger.info("API-Subscribe_Order_Positions_StatusCode : [" + get_position_response.statusCode() + "]");
			Assert.assertEquals(get_position_response.getStatusCode(), Integer.parseInt(Subscribe_Order_Positions_StatusCode), "Verify_OrderSymbol_Positions");
			JsonPath jsonresponse = new JsonPath(get_position_response.getBody().asString());
			ResponseArraySize = jsonresponse.getInt(getResponseArray + ".size()");
			outerloop: for(position = ResponseArraySize-1; position >=0; position--)
			{
				String response_ID = jsonresponse.getString(getResponseArray+"["+position+"].id");
				String response_account = jsonresponse.getString(getResponseArray+"["+position+"].account");
				String response_symbol = jsonresponse.getString(getResponseArray+"["+position+"].symbol");
				String response_execQty = jsonresponse.getString(getResponseArray+"["+position+"].execQty");
				String response_positionString = jsonresponse.getString(getResponseArray+"["+position+"].positionString");
				String response_realizedPnL = jsonresponse.getString(getResponseArray+"["+position+"].realizedPnL");
				//LoggingManager.logger.info("API : ["+position+"] - ["+position_ID+"]");
				//LoggingManager.logger.info("API-Fetched Option Validate_Position_LONG_SHORT : ["+response_positionString+"] - ["+Validate_Position_LONG_SHORT+"]");
				//Assert.assertEquals(response_ID,position_ID,"Validate_response_ID");
				//Assert.assertEquals(response_positionString,Validate_Position_LONG_SHORT,"Validate_Position_LONG_SHORT");
				if (response_ID.equalsIgnoreCase(PositionID) && Double.parseDouble(response_execQty)!=0)
				{
					LoggingManager.logger.info("API-Before Flat Option Position response_ID : ["+response_ID+"]");
					LoggingManager.logger.info("API-Before Flat Option Position response_positionString : ["+response_positionString+"]");
					LoggingManager.logger.info("API-Before Flat Option Position response_realizedPnL : ["+response_realizedPnL+"]");
					LoggingManager.logger.info("API-Before Flat Option Position response_account : ["+response_account+"]");
					LoggingManager.logger.info("API-Before Flat Option Position response_execQty : ["+response_execQty+"]");
					LoggingManager.logger.info("API-Before Flat Option Position response_symbol : ["+response_symbol+"]");
					if (Double.parseDouble(response_execQty) < 0)
					{
						getexecQty = response_execQty.substring(1);
						Flat_Order_Position_Side="1";
					}
					else
					{
						getexecQty = response_execQty;
						Flat_Order_Position_Side="2";
					}
					HashMap<String, Object> fill_optionOrder_body=new   HashMap<String, Object>();
					fill_optionOrder_body.put("OrdType",Order_OrdType);
					fill_optionOrder_body.put("Side", Flat_Order_Position_Side);
					fill_optionOrder_body.put("TimeInForce",Order_TimeInForce );
					fill_optionOrder_body.put("Destination",Order_Destination );
					fill_optionOrder_body.put("Account", response_account);
					fill_optionOrder_body.put("OrderQty", Double.parseDouble(getexecQty));
					fill_optionOrder_body.put("Symbol", response_symbol);
					fill_optionOrder_body.put("Price", Double.parseDouble(Order_Price));
					fill_optionOrder_body.put("StopPx", Double.parseDouble(Order_StopPx));
					fill_optionOrder_body.put("StrikePrice",Double.parseDouble(Order_StrikePrice));
					fill_optionOrder_body.put("PutOrCall", Order_PutOrCall);
					fill_optionOrder_body.put("CoveredOrUncovered", Order_CoveredOrUncovered);
					fill_optionOrder_body.put("CustomerOrFirm", Order_CustomerOrFirm);
					fill_optionOrder_body.put("ExpiryDate", Order_ExpiryDate);
					fill_optionOrder_body.put("Cmta", Order_Cmta);
					fill_optionOrder_body.put("OpenClose", Order_OpenClose);
					Response response=
							given()
									.header("Content-Type",Content_Type)
									.header("Authorization", "Bearer " + Global.getAccToken)
									.body(fill_optionOrder_body)

									.when()
									.post(Order_Creation_BasePath)

									.then()
									.extract().response();
					LoggingManager.logger.info("API-Response : ["+response.getBody().asPrettyString()+"]");
					Assert.assertEquals(response.getStatusCode(), Integer.parseInt(Order_Creation_StatusCode),"Verify_Order_Creation");
					Response Validate_position_response=

							given()
									.header("Content-Type",Content_Type)
									.header("Authorization", "Bearer " + Global.getAccToken)
									.when()
									.get(Subscribe_Order_Positions_BasePath)

									.then()
									.extract().response();

					Assert.assertEquals(Validate_position_response.getStatusCode(),Integer.parseInt(Order_Creation_StatusCode),"Validate_response_LONG_SHORT");
					JsonPath JSON_position_response = new JsonPath(Validate_position_response.getBody().asString());
					ResponseArraySize = JSON_position_response.getInt(getResponseArray+".size()");
					innerloop: for(innerposition = ResponseArraySize-1; innerposition >=0; innerposition--)
					{
						Global.getOptionPosition_id = JSON_position_response.getString(getResponseArray+"["+innerposition+"].id");
						Global.getOptionPosition_positionString = JSON_position_response.getString(getResponseArray+"["+innerposition+"].positionString");
						if (Global.getOptionPosition_id.equalsIgnoreCase(PositionID) && Global.getOptionPosition_positionString.equalsIgnoreCase(Validate_Position_FLAT))
						{
							Global.getOptionPosition_completeDayBuyOrderQty = JSON_position_response.getDouble(getResponseArray+"["+innerposition+"].completeDayBuyOrderQty");
							Global.getOptionPosition_completeDaySellLongOrderQty = JSON_position_response.getDouble(getResponseArray+"["+innerposition+"].completeDaySellLongOrderQty");
							Global.getOptionPosition_completeDaySellShortOrderQty= JSON_position_response.getDouble(getResponseArray+"["+innerposition+"].completeDaySellShortOrderQty");
							Global.getOptionPosition_symbol = JSON_position_response.getString(getResponseArray+"["+innerposition+"].symbol");
							Global.getOptionPosition_avgPrice = JSON_position_response.getDouble(getResponseArray+"["+innerposition+"].avgPrice");
							Global.getOptionPosition_totDollarOfTrade = JSON_position_response.getDouble(getResponseArray+"["+innerposition+"].totDollarOfTrade");
							Global.getOptionPosition_execQty = JSON_position_response.getDouble(getResponseArray+"["+innerposition+"].execQty");
							Global.getOptionPosition_realizedPnL = JSON_position_response.getDouble(getResponseArray+"["+innerposition+"].realizedPnL");
							Global.getOptionPosition_symbolSfx = JSON_position_response.getString(getResponseArray+"["+innerposition+"].symbolSfx");
							Global.getOptionPosition_originatingUserDesc = JSON_position_response.getString(getResponseArray+"["+innerposition+"].originatingUserDesc");
							Global.getOptionPosition_account = JSON_position_response.getString(getResponseArray+"["+innerposition+"].account");
							Global.getOptionLONGrealizedPnL=Global.getOptionPosition_realizedPnL;
							Global.getOptionSHORTrealizedPnL=Global.getOptionPosition_realizedPnL;
							Global.getOptioncompleteDayBuyOrderQty=Global.getOptionPosition_completeDayBuyOrderQty;
							Global.getOptioncompleteDaySellShortOrderQty=Global.getOptionPosition_completeDaySellShortOrderQty;
							Global.getOptioncompleteDaySellLongOrderQty=Global.getOptionPosition_completeDaySellLongOrderQty;

							LoggingManager.logger.info("API-After Flat Position ID : ["+Global.getOptionPosition_id+"]");
							LoggingManager.logger.info("API-After Flat Position positionString : ["+Global.getOptionPosition_positionString+"]");
							LoggingManager.logger.info("API-After Flat Position realizedPnL : ["+Global.getOptionPosition_realizedPnL+"]");
							LoggingManager.logger.info("API-After Flat Position account : ["+Global.getOptionPosition_account+"]");
							LoggingManager.logger.info("API-After Flat Position execQty : ["+Global.getOptionPosition_execQty+"]");
							LoggingManager.logger.info("API-After Flat Position symbol : ["+Global.getOptionPosition_symbol+"]");
							LoggingManager.logger.info("API-After Flat Position completeDayBuyOrderQty : ["+Global.getOptionPosition_completeDayBuyOrderQty+"]");
							LoggingManager.logger.info("API-After Flat Position completeDaySellLongOrderQty : ["+Global.getOptionPosition_completeDaySellLongOrderQty+"]");
							LoggingManager.logger.info("API-After Flat Position completeDaySellShortOrderQty : ["+Global.getOptionPosition_completeDaySellShortOrderQty+"]");
							LoggingManager.logger.info("API-After Flat Position totDollarOfTrade : ["+Global.getOptionPosition_totDollarOfTrade+"]");
							LoggingManager.logger.info("API-After Flat Position avgPrice : ["+Global.getOptionPosition_avgPrice+"]");
							LoggingManager.logger.info("API-After Flat Position symbolSfx : ["+Global.getOptionPosition_symbolSfx+"]");
							LoggingManager.logger.info("API-After Flat Position originatingUserDesc : ["+Global.getOptionPosition_originatingUserDesc+"]");
							Assert.assertEquals(Global.getOptionPosition_execQty,0.0, "Validate_ExecQty_Flat_Response_["+PositionID+"]");
							Assert.assertEquals(Global.getOptionPosition_positionString,Validate_Position_FLAT, "Validate_Position: ["+PositionID+"]");
							break outerloop;
						}
						else
						{
							if (innerposition==0 && Global.getOptionPosition_positionString!=Validate_Position_FLAT)
							{
								LoggingManager.logger.warn("API-Position ["+PositionID+"] : Found ["+Global.getOptionPosition_positionString+"]- Expected ["+Validate_Position_FLAT+"]");
								Assert.assertEquals(Global.getOptionPosition_positionString,Validate_Position_FLAT, "Validate_Position: ["+PositionID+"]");
								break outerloop;
							}
							else
							{
								continue innerloop;
							}
						}
					}
				}

				else if(response_ID.equalsIgnoreCase(PositionID) && response_positionString.equalsIgnoreCase(Validate_Position_FLAT) && Double.parseDouble(response_execQty)==0)
				{
					Global.getOptionPosition_id = jsonresponse.getString(getResponseArray+"["+position+"].id");
					Global.getOptionPosition_completeDayBuyOrderQty = jsonresponse.getDouble(getResponseArray+"["+position+"].completeDayBuyOrderQty");
					Global.getOptionPosition_completeDaySellLongOrderQty = jsonresponse.getDouble(getResponseArray+"["+position+"].completeDaySellLongOrderQty");
					Global.getOptionPosition_completeDaySellShortOrderQty= jsonresponse.getDouble(getResponseArray+"["+position+"].completeDaySellShortOrderQty");
					Global.getOptionPosition_symbol = jsonresponse.getString(getResponseArray+"["+position+"].symbol");
					Global.getOptionPosition_positionString = jsonresponse.getString(getResponseArray+"["+position+"].positionString");
					Global.getOptionPosition_avgPrice = jsonresponse.getDouble(getResponseArray+"["+position+"].avgPrice");
					Global.getOptionPosition_totDollarOfTrade = jsonresponse.getDouble(getResponseArray+"["+position+"].totDollarOfTrade");
					Global.getOptionPosition_execQty = jsonresponse.getDouble(getResponseArray+"["+position+"].execQty");
					Global.getOptionPosition_realizedPnL = jsonresponse.getDouble(getResponseArray+"["+position+"].realizedPnL");
					Global.getOptionPosition_symbolSfx = jsonresponse.getString(getResponseArray+"["+position+"].symbolSfx");
					Global.getOptionPosition_originatingUserDesc = jsonresponse.getString(getResponseArray+"["+position+"].originatingUserDesc");
					Global.getOptionPosition_account = jsonresponse.getString(getResponseArray+"["+position+"].account");
					Global.getOptionLONGrealizedPnL=Global.getOptionPosition_realizedPnL;
					Global.getOptionSHORTrealizedPnL=Global.getOptionPosition_realizedPnL;
					Global.getOptioncompleteDayBuyOrderQty=Global.getOptionPosition_completeDayBuyOrderQty;
					Global.getOptioncompleteDaySellShortOrderQty=Global.getOptionPosition_completeDaySellShortOrderQty;
					Global.getOptioncompleteDaySellLongOrderQty=Global.getOptionPosition_completeDaySellLongOrderQty;

					LoggingManager.logger.info("API-Already Flat Position ID : ["+Global.getOptionPosition_id+"]");
					LoggingManager.logger.info("API-Already Flat Position positionString : ["+Global.getOptionPosition_positionString+"]");
					LoggingManager.logger.info("API-Already Flat Position realizedPnL : ["+Global.getOptionPosition_realizedPnL+"]");
					LoggingManager.logger.info("API-Already Flat Position account : ["+Global.getOptionPosition_account+"]");
					LoggingManager.logger.info("API-Already Flat Position execQty : ["+Global.getOptionPosition_execQty+"]");
					LoggingManager.logger.info("API-Already Flat Position symbol : ["+Global.getOptionPosition_symbol+"]");
					LoggingManager.logger.info("API-Already Flat Position completeDayBuyOrderQty : ["+Global.getOptionPosition_completeDayBuyOrderQty+"]");
					LoggingManager.logger.info("API-Already Flat Position completeDaySellLongOrderQty : ["+Global.getOptionPosition_completeDaySellLongOrderQty+"]");
					LoggingManager.logger.info("API-Already Flat Position completeDaySellShortOrderQty : ["+Global.getOptionPosition_completeDaySellShortOrderQty+"]");
					LoggingManager.logger.info("API-Already Flat Position totDollarOfTrade : ["+Global.getOptionPosition_totDollarOfTrade+"]");
					LoggingManager.logger.info("API-Already Flat Position avgPrice : ["+Global.getOptionPosition_avgPrice+"]");
					LoggingManager.logger.info("API-Already Flat Position symbolSfx : ["+Global.getOptionPosition_symbolSfx+"]");
					LoggingManager.logger.info("API-Already Flat Position originatingUserDesc : ["+Global.getOptionPosition_originatingUserDesc+"]");
					Assert.assertEquals(Global.getOptionPosition_execQty,0.0, "Validate_ExecQty_Flat_Response ["+PositionID+"]");
					Assert.assertEquals(Global.getOptionPosition_positionString,Validate_Position_FLAT, "Validate_Position_FLAT: ["+PositionID+"]");
					break outerloop;
				}
				else
				{		if (position==0)
						{LoggingManager.logger.warn("API-Position Data Not Exist Against : ["+PositionID+"]"); }
						else
						{continue outerloop;}
				}
			}
		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}
				
	
public static void Validate_Positions(Response getresponse,String endpoint_version, String Account_Type_BoxvsShort,Double AvgPrx,Double RealizePNL,String Validate_Position_Symbol_Value,String Validate_Position_symbolSfx_Value,String Validate_Position_originatingUserDesc_Value ,String Validate_Position_positionString_Value,String Validate_Position_Account_Value,String Validate_Position_BoothID)
{
	try
	{
		String getResponseArray="" ,PositionID;
		DecimalFormat decimalFormat = new DecimalFormat("0.0000");
		decimalFormat.getRoundingMode();
		JsonPath jsonresponse = new JsonPath(getresponse.getBody().asString());
		getResponseArray=apiRespVersion(endpoint_version);
		if (Account_Type_BoxvsShort.equalsIgnoreCase("1")){PositionID=Validate_Position_BoothID+"-"+Validate_Position_Symbol_Value+"-"+Validate_Position_Account_Value+"-"+Validate_Position_positionString_Value;}
		else{PositionID=Validate_Position_BoothID+"-"+Validate_Position_Symbol_Value+"-"+Validate_Position_Account_Value;	}
		int ResponseArraySize = jsonresponse.getInt(getResponseArray+".size()");
		for(int position = ResponseArraySize-1; position >=0; position--)
		{
			Global.getPosition_id = jsonresponse.getString(getResponseArray+"["+position+"].id");
			Global.getPosition_completeDayBuyOrderQty = jsonresponse.getDouble(getResponseArray+"["+position+"].completeDayBuyOrderQty");
			Global.getPosition_completeDaySellLongOrderQty = jsonresponse.getDouble(getResponseArray+"["+position+"].completeDaySellLongOrderQty");
			Global.getPosition_completeDaySellShortOrderQty= jsonresponse.getDouble(getResponseArray+"["+position+"].completeDaySellShortOrderQty");
			Global.getPosition_symbol = jsonresponse.getString(getResponseArray+"["+position+"].symbol");
			Global.getPosition_positionString = jsonresponse.getString(getResponseArray+"["+position+"].positionString");
			Global.getPosition_avgPrice = jsonresponse.getDouble(getResponseArray+"["+position+"].avgPrice");
			Global.getPosition_totDollarOfTrade = jsonresponse.getDouble(getResponseArray+"["+position+"].totDollarOfTrade");
			Global.getPosition_execQty = jsonresponse.getDouble(getResponseArray+"["+position+"].execQty");
			Global.getPosition_realizedPnL = jsonresponse.getDouble(getResponseArray+"["+position+"].realizedPnL");
			Global.getPosition_symbolSfx = jsonresponse.getString(getResponseArray+"["+position+"].symbolSfx");
			Global.getPosition_originatingUserDesc = jsonresponse.getString(getResponseArray+"["+position+"].originatingUserDesc");
			Global.getPosition_account = jsonresponse.getString(getResponseArray+"["+position+"].account");
			//LoggingManager.logger.info("API-Position ["+Global.getPosition_id+"] - ["+PositionID+"]");
			//LoggingManager.logger.info("API-Position ["+Global.getPosition_symbol+"] - ["+Validate_Position_Symbol_Value+"]");
			//LoggingManager.logger.info("API-Position ["+Global.getPosition_account+"] - ["+Validate_Position_Account_Value+"]");
			if (Global.getPosition_id.equalsIgnoreCase(PositionID)
					&& Global.getPosition_symbol.equalsIgnoreCase(Validate_Position_Symbol_Value)
					&& Global.getPosition_account.equalsIgnoreCase(Validate_Position_Account_Value))

			{
				//LoggingManager.logger.info("API-Position getPosition_avgPrice : ["+decimalFormat.format(AvgPrx)+"]");
				//LoggingManager.logger.info("API-Position getPosition_realizedPnL : ["+decimalFormat.format(RealizePNL)+"]");
				//LoggingManager.logger.info("API-Position Validate_execQty : ["+(Global.getPosition_completeDayBuyOrderQty-Global.getPosition_completeDaySellLongOrderQty)+"]");
				LoggingManager.logger.info("API-Position : Found Average Prx ["+decimalFormat.format(Global.getPosition_avgPrice)+"] - Expected Average Prx ["+decimalFormat.format(AvgPrx)+"]");
				LoggingManager.logger.info("API-Position : Found RealizePNL ["+decimalFormat.format(Global.getPosition_realizedPnL)+"] - Expected RealizePNL ["+decimalFormat.format(RealizePNL)+"]");
				LoggingManager.logger.info("API-Position : Found Position ["+Global.getPosition_positionString+"] - Expected Position ["+Validate_Position_positionString_Value+"]");
				LoggingManager.logger.info("API-Position : Found PositionID ["+Global.getPosition_id+"] - Expected PositionID  ["+PositionID+"]");
				LoggingManager.logger.info("API-Position : Found completeDayBuyOrderQty ["+Global.getPosition_completeDayBuyOrderQty+"] - Expected completeDayBuyOrderQty  ["+Global.getcompleteDayBuyOrderQty+"]");
				LoggingManager.logger.info("API-Position : Found completeDaySellLongOrderQty ["+Global.getPosition_completeDaySellLongOrderQty+"] - Expected completeDaySellLongOrderQty  ["+Global.getcompleteDaySellLongOrderQty+"]");
				LoggingManager.logger.info("API-Position : Found completeDaySellShortOrderQty ["+Global.getPosition_completeDaySellShortOrderQty+"] - Expected completeDaySellShortOrderQty  ["+Global.getcompleteDaySellShortOrderQty+"]");
				LoggingManager.logger.info("API-Position : Found Symbol ["+Global.getPosition_symbol+"] - Expected Symbol  ["+Validate_Position_Symbol_Value+"]");
				LoggingManager.logger.info("API-Position : Found TotDollarOfTrade ["+String.format("%.4f",Global.getPosition_totDollarOfTrade)+"] - Expected TotDollarOfTrade  ["+String.format("%.4f",Global.totalTrade)+"]");
				//LoggingManager.logger.info("API-Position : Found ExecQty ["+Global.getPosition_execQty+"] - Expected ExecQty  ["+(Global.getPosition_completeDayBuyOrderQty-Global.getPosition_completeDaySellLongOrderQty)+"]");
				LoggingManager.logger.info("API-Position : Found SymbolSfx ["+Global.getPosition_symbolSfx+"] - Expected SymbolSfx  ["+Validate_Position_symbolSfx_Value+"]");
				LoggingManager.logger.info("API-Position : Found OriginatingUserDesc ["+Global.getPosition_originatingUserDesc+"] - Expected OriginatingUserDesc  ["+Validate_Position_originatingUserDesc_Value+"]");
				LoggingManager.logger.info("API-Position : Found Account ["+Global.getPosition_account+"] - Expected PositionID  ["+Validate_Position_Account_Value+"]");
				Assert.assertEquals(Global.getPosition_id,PositionID,"Validate_PositionID");
				Assert.assertEquals(Global.getPosition_completeDayBuyOrderQty,Global.getcompleteDayBuyOrderQty,"Validate_completeDayBuyOrderQty");
				Assert.assertEquals(Global.getPosition_completeDaySellLongOrderQty,Global.getcompleteDaySellLongOrderQty,"Validate_completeDaySellLongOrderQty");
				Assert.assertEquals(Global.getPosition_completeDaySellShortOrderQty,Global.getcompleteDaySellShortOrderQty,"Validate_completeDaySellShortOrderQty");
				Assert.assertEquals(Global.getPosition_symbol,Validate_Position_Symbol_Value,"Validate_Position_Symbol_Value");
				Assert.assertEquals(String.format("%.4f",Global.getPosition_totDollarOfTrade),String.format("%.4f",Global.totalTrade),"Validate_totDollarOfTrade");

				if (Validate_Position_positionString_Value.equalsIgnoreCase("SHORT"))
					{LoggingManager.logger.info("API-Position : Found SHORT ExecQty ["+Global.getPosition_execQty+"] - Expected SHORT ExecQty  ["+(Global.getPosition_completeDayBuyOrderQty-(Global.getPosition_completeDaySellShortOrderQty+Global.getPosition_completeDaySellLongOrderQty))+"]");
					Assert.assertEquals(decimalFormat.format(Global.getPosition_execQty),decimalFormat.format(Global.getPosition_completeDayBuyOrderQty-(Global.getPosition_completeDaySellShortOrderQty+Global.getPosition_completeDaySellLongOrderQty)),"Validate_SHORT_execQty");}
				else if (Validate_Position_positionString_Value.equalsIgnoreCase("FLAT"))
					{LoggingManager.logger.info("API-Position : Found FLAT ExecQty ["+Global.getPosition_execQty+"] - Expected FLAT ExecQty  ["+(0.0)+"]");
					Assert.assertEquals(decimalFormat.format(Global.getPosition_execQty),decimalFormat.format(0.0),"Validate_FLAT_execQty");}
				else
					{LoggingManager.logger.info("API-Position : Found LONG ExecQty ["+Global.getPosition_execQty+"] - Expected LONG ExecQty  ["+(Global.getPosition_completeDayBuyOrderQty-(Global.getPosition_completeDaySellShortOrderQty+Global.getPosition_completeDaySellLongOrderQty))+"]");
					Assert.assertEquals(decimalFormat.format(Global.getPosition_execQty),decimalFormat.format(Global.getPosition_completeDayBuyOrderQty-(Global.getPosition_completeDaySellShortOrderQty+Global.getPosition_completeDaySellLongOrderQty)),"Validate_LONG_execQty");}

				Assert.assertEquals(NVL(Global.getPosition_symbolSfx,"null"),Validate_Position_symbolSfx_Value,"Validate_Position_symbolSfx_Value");
				Assert.assertEquals(NVL(Global.getPosition_originatingUserDesc,"null"),Validate_Position_originatingUserDesc_Value,"Validate_Position_originatingUserDesc_Value");
				Assert.assertEquals(Global.getPosition_account,Validate_Position_Account_Value,"Validate_Position_Account_Value");
				Assert.assertEquals(decimalFormat.format(Global.getPosition_avgPrice),decimalFormat.format(AvgPrx),"Validate_AvgPrx");
				Assert.assertEquals(decimalFormat.format(Global.getPosition_realizedPnL),decimalFormat.format(RealizePNL),"Validate_RealizePNL");
				Assert.assertEquals(Global.getPosition_positionString,Validate_Position_positionString_Value,"Validate_Position_positionString_Value");
				break;
			}
			else
			{
				continue;
			}
		}
	}
	catch (Exception e)
	{
		LoggingManager.logger.error(e);
	}
}


public static void Validate_Option_Positions(Response getresponse,
												String endpoint_version,
												String PositionID,
												Double AvgPrx,
												Double RealizePNL,
												String Validate_Position_comment_Value,
												String Validate_Position_currency_Value,
												String Validate_Position_exchangeName_Value,
												String Validate_Position_exchangeID_Value,
												String Validate_Position_rule80A_Value,
												Integer Validate_Position_positionOperation_Value,
												Integer Validate_Position_positionSide_Value,
												Double Validate_Position_parentOrdPrice_Value,
												Double Validate_Position_parentOrdQty_Value,
												String Validate_Position_clientID_Value,
												String Validate_Position_boothID_Value,
												String Validate_Position_symbolWithoutSfx_Value,
												String Validate_Position_optionSymbol_Value,
												String Validate_Position_optionsFields_Value,
												String Validate_Position_optionDesc_Value,
												Integer Validate_Position_maturityDay_Value,
												String Validate_Position_maturityMonthYear_Value,
												String Validate_Position_maturityMonthYearDesc_Value,
												String Validate_Position_maturityDate_Value,
												Double Validate_Position_strikePrice_Value,
												Integer Validate_Position_putOrCallInt_Value,
												String Validate_Position_putOrCall_Value,
												Integer Validate_Position_coveredOrUncoveredInt_Value,
												String Validate_Position_coveredOrUncovered_Value,
												Integer Validate_Position_customerOrFirmInt_Value,
												String Validate_Position_customerOrFirm_Value,
												String Validate_Position_openCloseBoxed_Value,
												String Validate_Position_openClose_Value,
												String Validate_Position_cmta_Value,
												String Validate_Position_expiryDate_Value,
												String Validate_Position_symbol_Value,
												String Validate_Position_positionString_Value,
												String Validate_Position_symbolSfx_Value,
												String Validate_Position_originatingUserDesc_Value,
												String Validate_Position_account_Value )
{
	try
	{
		String getResponseArray="";
		DecimalFormat decimalFormat = new DecimalFormat("0.000");
		decimalFormat.getRoundingMode();
		JsonPath jsonresponse = new JsonPath(getresponse.getBody().asString());
		getResponseArray=apiRespVersion(endpoint_version);
		//if (Account_Type_BoxvsShort.equalsIgnoreCase("1")){PositionID=Validate_Position_BoothID+"-"+Validate_Position_Symbol_Value+"-"+Validate_Position_Account_Value+"-"+Validate_Position_positionString_Value;}
		//else{PositionID=Validate_Position_BoothID+"-"+Validate_Position_Symbol_Value+"-"+Validate_Position_Account_Value;	}
		int ResponseArraySize = jsonresponse.getInt(getResponseArray+".size()");
		for(int position = ResponseArraySize-1; position >=0; position--)
		{
			Global.getOptionPosition_id = jsonresponse.getString(getResponseArray+"["+position+"].id");
			Global.getOptionPosition_comment = jsonresponse.getString(getResponseArray+"["+position+"].comment");
			Global.getOptionPosition_currency = jsonresponse.getString(getResponseArray+"["+position+"].currency");
			Global.getOptionPosition_exchangeName = jsonresponse.getString(getResponseArray+"["+position+"].exchangeName");
			Global.getOptionPosition_exchangeID= jsonresponse.getString(getResponseArray+"["+position+"].exchangeID");
			Global.getOptionPosition_rule80A = jsonresponse.getString(getResponseArray+"["+position+"].rule80A");
			Global.getOptionPosition_positionOperation = jsonresponse.getInt(getResponseArray+"["+position+"].positionOperation");
			Global.getOptionPosition_positionSide = jsonresponse.getInt(getResponseArray+"["+position+"].positionSide");
			Global.getOptionPosition_parentOrdPrice = jsonresponse.getDouble(getResponseArray+"["+position+"].parentOrdPrice");
			Global.getOptionPosition_parentOrdQty = jsonresponse.getDouble(getResponseArray+"["+position+"].parentOrdQty");
			Global.getOptionPosition_clientID = jsonresponse.getString(getResponseArray+"["+position+"].clientID");
			Global.getOptionPosition_execQtyFrac = jsonresponse.getDouble(getResponseArray+"["+position+"].execQtyFrac");
			Global.getOptionPosition_unRealizedPnL = jsonresponse.getDouble(getResponseArray+"["+position+"].unRealizedPnL");
			Global.getOptionPosition_openQty = jsonresponse.getDouble(getResponseArray+"["+position+"].openQty");
			Global.getOptionPosition_totalBuyOrderQty = jsonresponse.getDouble(getResponseArray+"["+position+"].totalBuyOrderQty");
			Global.getOptionPosition_totalSellOrderQty = jsonresponse.getDouble(getResponseArray+"["+position+"].totalSellOrderQty");
			Global.getOptionPosition_buyAvgPx= jsonresponse.getDouble(getResponseArray+"["+position+"].buyAvgPx");
			Global.getOptionPosition_sellAvgPx = jsonresponse.getDouble(getResponseArray+"["+position+"].sellAvgPx");
			Global.getOptionPosition_boothID = jsonresponse.getString(getResponseArray+"["+position+"].boothID");
			Global.getOptionPosition_symbolWithoutSfx = jsonresponse.getString(getResponseArray+"["+position+"].symbolWithoutSfx");
			Global.getOptionPosition_completeDayBuyOrderQty = jsonresponse.getDouble(getResponseArray+"["+position+"].completeDayBuyOrderQty");
			Global.getOptionPosition_completeDaySellLongOrderQty = jsonresponse.getDouble(getResponseArray+"["+position+"].completeDaySellLongOrderQty");
			Global.getOptionPosition_completeDaySellShortOrderQty= jsonresponse.getDouble(getResponseArray+"["+position+"].completeDaySellShortOrderQty");
			Global.getOptionPosition_optionSymbol = jsonresponse.getString(getResponseArray+"["+position+"].optionSymbol");
			Global.getOptionPosition_optionsFields = jsonresponse.getString(getResponseArray+"["+position+"].optionsFields");
			Global.getOptionPosition_optionDesc = jsonresponse.getString(getResponseArray+"["+position+"].optionDesc");
			Global.getOptionPosition_maturityDay= jsonresponse.getInt(getResponseArray+"["+position+"].maturityDay");
			Global.getOptionPosition_maturityMonthYear = jsonresponse.getString(getResponseArray+"["+position+"].maturityMonthYear");
			Global.getOptionPosition_maturityMonthYearDesc = jsonresponse.getString(getResponseArray+"["+position+"].maturityMonthYearDesc");
			Global.getOptionPosition_maturityDate = jsonresponse.getString(getResponseArray+"["+position+"].maturityDate");
			Global.getOptionPosition_strikePrice = jsonresponse.getDouble(getResponseArray+"["+position+"].strikePrice");
			Global.getOptionPosition_putOrCallInt = jsonresponse.getInt(getResponseArray+"["+position+"].putOrCallInt");
			Global.getOptionPosition_putOrCall = jsonresponse.getString(getResponseArray+"["+position+"].putOrCall");
			Global.getOptionPosition_coveredOrUncoveredInt = jsonresponse.getInt(getResponseArray+"["+position+"].coveredOrUncoveredInt");
			Global.getOptionPosition_coveredOrUncovered = jsonresponse.getString(getResponseArray+"["+position+"].coveredOrUncovered");
			Global.getOptionPosition_customerOrFirmInt = jsonresponse.getInt(getResponseArray+"["+position+"].customerOrFirmInt");
			Global.getOptionPosition_customerOrFirm= jsonresponse.getString(getResponseArray+"["+position+"].customerOrFirm");
			Global.getOptionPosition_openCloseBoxed = jsonresponse.getString(getResponseArray+"["+position+"].openCloseBoxed");
			Global.getOptionPosition_openClose = jsonresponse.getString(getResponseArray+"["+position+"].openClose");
			Global.getOptionPosition_cmta = jsonresponse.getString(getResponseArray+"["+position+"].cmta");
			Global.getOptionPosition_expiryDate = jsonresponse.getString(getResponseArray+"["+position+"].expiryDate");
			Global.getOptionPosition_symbol = jsonresponse.getString(getResponseArray+"["+position+"].symbol");
			Global.getOptionPosition_positionString = jsonresponse.getString(getResponseArray+"["+position+"].positionString");
			Global.getOptionPosition_avgPrice = jsonresponse.getDouble(getResponseArray+"["+position+"].avgPrice");
			Global.getOptionPosition_totDollarOfTrade = jsonresponse.getDouble(getResponseArray+"["+position+"].totDollarOfTrade");
			Global.getOptionPosition_execQty = jsonresponse.getDouble(getResponseArray+"["+position+"].execQty");
			Global.getOptionPosition_realizedPnL = jsonresponse.getDouble(getResponseArray+"["+position+"].realizedPnL");
			Global.getOptionPosition_symbolSfx = jsonresponse.getString(getResponseArray+"["+position+"].symbolSfx");
			Global.getOptionPosition_originatingUserDesc = jsonresponse.getString(getResponseArray+"["+position+"].originatingUserDesc");
			Global.getOptionPosition_account = jsonresponse.getString(getResponseArray+"["+position+"].account");
			/*	LoggingManager.logger.info("API-Global.getOptionPosition_id ["+Global.getOptionPosition_id+"] - Expected PositionID["+PositionID+"]");
				LoggingManager.logger.info("API-Global.getOptionPosition_symbol ["+Global.getOptionPosition_symbol+"] - Expected Validate_Position_symbol_Value ["+Validate_Position_symbol_Value+"]");
				LoggingManager.logger.info("API-Global.getOptionPosition_account ["+Global.getOptionPosition_account+"] - Expected Validate_Position_account_Value ["+Validate_Position_account_Value+"]");
			*/
			if (Global.getOptionPosition_id.equalsIgnoreCase(PositionID)
					&& Global.getOptionPosition_symbol.equalsIgnoreCase(Validate_Position_symbol_Value)
					&& Global.getOptionPosition_account.equalsIgnoreCase(Validate_Position_account_Value))

			{
				LoggingManager.logger.info("API-OptionPosition : Found PositionID ["+Global.getOptionPosition_id+"] - Expected PositionID  ["+PositionID+"]");
				LoggingManager.logger.info("API-OptionPosition : Found Average Prx ["+decimalFormat.format(Global.getOptionPosition_avgPrice)+"] - Expected Average Prx ["+decimalFormat.format(AvgPrx)+"]");
				LoggingManager.logger.info("API-OptionPosition : Found RealizePNL ["+decimalFormat.format(Global.getOptionPosition_realizedPnL)+"] - Expected RealizePNL ["+decimalFormat.format(RealizePNL)+"]");
				LoggingManager.logger.info("API-OptionPosition : Found Position ["+Global.getOptionPosition_positionString+"] - Expected Position ["+Validate_Position_positionString_Value+"]");
				LoggingManager.logger.info("API-OptionPosition : Found completeDayBuyOrderQty ["+Global.getOptionPosition_completeDayBuyOrderQty+"] - Expected completeDayBuyOrderQty  ["+Global.getOptioncompleteDayBuyOrderQty+"]");
				LoggingManager.logger.info("API-OptionPosition : Found completeDaySellLongOrderQty ["+Global.getOptionPosition_completeDaySellLongOrderQty+"] - Expected completeDaySellLongOrderQty  ["+Global.getOptioncompleteDaySellLongOrderQty+"]");
				LoggingManager.logger.info("API-OptionPosition : Found completeDaySellShortOrderQty ["+Global.getOptionPosition_completeDaySellShortOrderQty+"] - Expected completeDaySellShortOrderQty  ["+Global.getOptioncompleteDaySellShortOrderQty+"]");
				LoggingManager.logger.info("API-OptionPosition : Found comment ["+Global.getOptionPosition_comment+"] - Expected comment  ["+Validate_Position_comment_Value+"]");
				LoggingManager.logger.info("API-OptionPosition : Found currency ["+Global.getOptionPosition_currency+"] - Expected currency  ["+Validate_Position_currency_Value+"]");
				LoggingManager.logger.info("API-OptionPosition : Found exchangeName ["+Global.getOptionPosition_exchangeName+"] - Expected exchangeName  ["+Validate_Position_exchangeName_Value+"]");
				LoggingManager.logger.info("API-OptionPosition : Found exchangeID ["+Global.getOptionPosition_exchangeID+"] - Expected exchangeID  ["+Validate_Position_exchangeID_Value+"]");
				LoggingManager.logger.info("API-OptionPosition : Found rule80A ["+Global.getOptionPosition_rule80A+"] - Expected rule80A  ["+Validate_Position_rule80A_Value+"]");
				LoggingManager.logger.info("API-OptionPosition : Found positionOperation ["+Global.getOptionPosition_positionOperation+"] - Expected positionOperation  ["+Validate_Position_positionOperation_Value+"]");
				LoggingManager.logger.info("API-OptionPosition : Found positionSide ["+Global.getOptionPosition_positionSide+"] - Expected positionSide  ["+Validate_Position_positionSide_Value+"]");
				LoggingManager.logger.info("API-OptionPosition : Found PositionID ["+Global.getOptionPosition_parentOrdPrice+"] - Expected parentOrdPrice  ["+Validate_Position_parentOrdPrice_Value+"]");
				LoggingManager.logger.info("API-OptionPosition : Found parentOrdQty ["+Global.getOptionPosition_parentOrdQty+"] - Expected parentOrdQty  ["+Validate_Position_parentOrdQty_Value+"]");
				LoggingManager.logger.info("API-OptionPosition : Found clientID ["+Global.getOptionPosition_clientID+"] - Expected clientID  ["+Validate_Position_clientID_Value+"]");
				LoggingManager.logger.info("API-OptionPosition : Found boothID ["+Global.getOptionPosition_boothID+"] - Expected boothID  ["+Validate_Position_boothID_Value+"]");
				LoggingManager.logger.info("API-OptionPosition : Found totalBuyOrderQty ["+Global.getOptionPosition_totalBuyOrderQty+"] - Expected totalBuyOrderQty  ["+Global.getOptionPosition_completeDayBuyOrderQty+"]");
				LoggingManager.logger.info("API-OptionPosition : Found totalSellOrderQty ["+Global.getOptionPosition_totalSellOrderQty+"] - Expected totalSellOrderQty  ["+Global.getOptionPosition_completeDaySellLongOrderQty+"]");
				LoggingManager.logger.info("API-OptionPosition : Found symbolWithoutSfx ["+Global.getOptionPosition_symbolWithoutSfx+"] - Expected symbolWithoutSfx  ["+Validate_Position_symbolWithoutSfx_Value+"]");
				LoggingManager.logger.info("API-OptionPosition : Found optionSymbol ["+Global.getOptionPosition_optionSymbol+"] - Expected optionSymbol  ["+Validate_Position_optionSymbol_Value+"]");
				LoggingManager.logger.info("API-OptionPosition : Found optionsFields ["+Global.getOptionPosition_optionsFields+"] - Expected optionsFields  ["+Validate_Position_optionsFields_Value+"]");
				LoggingManager.logger.info("API-OptionPosition : Found optionDesc ["+Global.getOptionPosition_optionDesc+"] - Expected optionDesc  ["+Validate_Position_optionDesc_Value+"]");
				LoggingManager.logger.info("API-OptionPosition : Found maturityDay ["+Global.getOptionPosition_maturityDay+"] - Expected maturityDay  ["+Validate_Position_maturityDay_Value+"]");
				LoggingManager.logger.info("API-OptionPosition : Found maturityMonthYear ["+Global.getOptionPosition_maturityMonthYear+"] - Expected maturityMonthYear  ["+Validate_Position_maturityMonthYear_Value+"]");
				LoggingManager.logger.info("API-OptionPosition : Found maturityMonthYearDesc ["+Global.getOptionPosition_maturityMonthYearDesc+"] - Expected maturityMonthYearDesc  ["+Validate_Position_maturityMonthYearDesc_Value+"]");
				LoggingManager.logger.info("API-OptionPosition : Found maturityDate ["+Global.getOptionPosition_maturityDate+"] - Expected maturityDate  ["+Validate_Position_maturityDate_Value+"]");
				LoggingManager.logger.info("API-OptionPosition : Found strikePrice ["+Global.getOptionPosition_strikePrice+"] - Expected strikePrice  ["+Validate_Position_strikePrice_Value+"]");
				LoggingManager.logger.info("API-OptionPosition : Found putOrCallInt ["+Global.getOptionPosition_putOrCallInt+"] - Expected putOrCallInt  ["+Validate_Position_putOrCallInt_Value+"]");
				LoggingManager.logger.info("API-OptionPosition : Found putOrCall ["+Global.getOptionPosition_putOrCall+"] - Expected putOrCall  ["+Validate_Position_putOrCall_Value+"]");
				LoggingManager.logger.info("API-OptionPosition : Found coveredOrUncoveredInt ["+Global.getOptionPosition_coveredOrUncoveredInt+"] - Expected coveredOrUncoveredInt  ["+Validate_Position_coveredOrUncoveredInt_Value+"]");
				LoggingManager.logger.info("API-OptionPosition : Found coveredOrUncovered ["+Global.getOptionPosition_coveredOrUncovered+"] - Expected coveredOrUncovered  ["+Validate_Position_coveredOrUncovered_Value+"]");
				LoggingManager.logger.info("API-OptionPosition : Found customerOrFirmInt ["+Global.getOptionPosition_customerOrFirmInt+"] - Expected customerOrFirmInt  ["+Validate_Position_customerOrFirmInt_Value+"]");
				LoggingManager.logger.info("API-OptionPosition : Found customerOrFirm ["+Global.getOptionPosition_customerOrFirm+"] - Expected customerOrFirm  ["+Validate_Position_customerOrFirm_Value+"]");
				LoggingManager.logger.info("API-OptionPosition : Found openCloseBoxed ["+Global.getOptionPosition_openCloseBoxed+"] - Expected openCloseBoxed  ["+Validate_Position_openCloseBoxed_Value+"]");
				LoggingManager.logger.info("API-OptionPosition : Found openClose ["+Global.getOptionPosition_openClose+"] - Expected openClose  ["+Validate_Position_openClose_Value+"]");
				LoggingManager.logger.info("API-OptionPosition : Found cmta ["+Global.getOptionPosition_cmta+"] - Expected cmta  ["+Validate_Position_cmta_Value+"]");
				LoggingManager.logger.info("API-OptionPosition : Found expiryDate ["+Global.getOptionPosition_expiryDate+"] - Expected expiryDate  ["+Validate_Position_expiryDate_Value+"]");
				LoggingManager.logger.info("API-OptionPosition : Found positionString ["+Global.getOptionPosition_positionString+"] - Expected positionString  ["+Validate_Position_positionString_Value+"]");
				LoggingManager.logger.info("API-OptionPosition : Found execQtyFrac ["+Global.getOptionPosition_execQtyFrac+"] - Expected execQtyFrac  ["+0.0+"]");
				LoggingManager.logger.info("API-OptionPosition : Found unRealizedPnL ["+Global.getOptionPosition_unRealizedPnL+"] - Expected unRealizedPnL  ["+0.0+"]");
				LoggingManager.logger.info("API-OptionPosition : Found openQty ["+Global.getOptionPosition_openQty+"] - Expected openQty  ["+0.0+"]");
				LoggingManager.logger.info("API-OptionPosition : Found buyAvgPx ["+Global.getOptionPosition_buyAvgPx+"] - Expected buyAvgPx  ["+0.0+"]");
				LoggingManager.logger.info("API-OptionPosition : Found sellAvgPx ["+Global.getOptionPosition_sellAvgPx+"] - Expected sellAvgPx  ["+0.0+"]");
				LoggingManager.logger.info("API-OptionPosition : Found Symbol ["+Global.getOptionPosition_symbol+"] - Expected Symbol  ["+Validate_Position_symbol_Value+"]");
				LoggingManager.logger.info("API-OptionPosition : Found TotDollarOfTrade ["+String.format("%.4f",Global.getOptionPosition_totDollarOfTrade)+"] - Expected TotDollarOfTrade  ["+String.format("%.4f",Global.totalTrade)+"]");
				LoggingManager.logger.info("API-OptionPosition : Found SymbolSfx ["+Global.getOptionPosition_symbolSfx+"] - Expected SymbolSfx  ["+Validate_Position_symbolSfx_Value+"]");
				LoggingManager.logger.info("API-OptionPosition : Found OriginatingUserDesc ["+Global.getOptionPosition_originatingUserDesc+"] - Expected OriginatingUserDesc  ["+Validate_Position_originatingUserDesc_Value+"]");
				LoggingManager.logger.info("API-OptionPosition : Found Account ["+Global.getOptionPosition_account+"] - Expected PositionID  ["+Validate_Position_account_Value+"]");


				Assert.assertEquals(Global.getOptionPosition_id,PositionID,"Validate_OptionPositionID");
				Assert.assertEquals(NVL(Global.getOptionPosition_comment,"null"),Validate_Position_comment_Value,"Validate_Position_comment_Value");
				Assert.assertEquals(NVL(Global.getOptionPosition_currency,"null"),Validate_Position_currency_Value,"Validate_Position_currency_Value");
				Assert.assertEquals(NVL(Global.getOptionPosition_exchangeName,"null"),Validate_Position_exchangeName_Value,"Validate_Position_exchangeName_Value");
				Assert.assertEquals(NVL(Global.getOptionPosition_exchangeID,"null"),Validate_Position_exchangeID_Value,"Validate_Position_exchangeID_Value");
				Assert.assertEquals(NVL(Global.getOptionPosition_rule80A,"null"),Validate_Position_rule80A_Value,"Validate_Position_rule80A_Value");
				Assert.assertEquals(Global.getOptionPosition_positionOperation,Validate_Position_positionOperation_Value,"Validate_Position_positionOperation_Value");
				Assert.assertEquals(Global.getOptionPosition_positionSide,Validate_Position_positionSide_Value,"Validate_Position_positionSide_Value");
				Assert.assertEquals(Global.getOptionPosition_parentOrdPrice,Validate_Position_parentOrdPrice_Value,"Validate_Position_parentOrdPrice_Value");
				Assert.assertEquals(Global.getOptionPosition_parentOrdQty,Validate_Position_parentOrdQty_Value,"Validate_Position_parentOrdQty_Value");
				Assert.assertEquals(Global.getOptionPosition_clientID,Validate_Position_clientID_Value,"Validate_Position_clientID_Value");
				Assert.assertEquals(Global.getOptionPosition_boothID,Validate_Position_boothID_Value,"Validate_Position_boothID_Value");
				Assert.assertEquals(Global.getOptionPosition_totalBuyOrderQty,Global.getOptionPosition_completeDayBuyOrderQty,"Validate_Position_totalBuyOrderQty_Value");
				Assert.assertEquals(Global.getOptionPosition_totalSellOrderQty,Global.getOptionPosition_completeDaySellLongOrderQty,"Validate_Position_totalSellOrderQty_Value");
				Assert.assertEquals(Global.getOptionPosition_symbolWithoutSfx,Validate_Position_symbolWithoutSfx_Value,"Validate_Position_symbolWithoutSfx_Value");
				Assert.assertEquals(Global.getOptionPosition_optionSymbol,Validate_Position_optionSymbol_Value,"Validate_Position_optionSymbol_Value");
				Assert.assertEquals(NVL(Global.getOptionPosition_optionsFields,"null"),Validate_Position_optionsFields_Value,"Validate_Position_optionsFields_Value");
				Assert.assertEquals(Global.getOptionPosition_optionDesc,Validate_Position_optionDesc_Value,"Validate_Position_optionDesc_Value");
				Assert.assertEquals(Global.getOptionPosition_maturityDay,Validate_Position_maturityDay_Value,"Validate_Position_maturityDay_Value");
				Assert.assertEquals(Global.getOptionPosition_maturityMonthYear,Validate_Position_maturityMonthYear_Value,"Validate_Position_maturityMonthYear_Value");
				Assert.assertEquals(Global.getOptionPosition_maturityMonthYearDesc,Validate_Position_maturityMonthYearDesc_Value,"Validate_Position_maturityMonthYearDesc_Value");
				Assert.assertEquals(Global.getOptionPosition_maturityDate,Validate_Position_maturityDate_Value,"Validate_Position_maturityDate_Value");
				Assert.assertEquals(Global.getOptionPosition_strikePrice,Validate_Position_strikePrice_Value,"Validate_Position_strikePrice_Value");
				Assert.assertEquals(Global.getOptionPosition_putOrCallInt,Validate_Position_putOrCallInt_Value,"Validate_Position_putOrCallInt_Value");
				Assert.assertEquals(Global.getOptionPosition_putOrCall,Validate_Position_putOrCall_Value,"Validate_Position_putOrCall_Value");
				Assert.assertEquals(Global.getOptionPosition_coveredOrUncoveredInt,Validate_Position_coveredOrUncoveredInt_Value,"Validate_Position_coveredOrUncoveredInt_Value");
				Assert.assertEquals(Global.getOptionPosition_coveredOrUncovered,Validate_Position_coveredOrUncovered_Value,"Validate_Position_coveredOrUncovered_Value");
				Assert.assertEquals(Global.getOptionPosition_customerOrFirmInt,Validate_Position_customerOrFirmInt_Value,"Validate_Position_customerOrFirmInt_Value");
				Assert.assertEquals(Global.getOptionPosition_customerOrFirm,Validate_Position_customerOrFirm_Value,"Validate_Position_customerOrFirm_Value");
				Assert.assertEquals(NVL(Global.getOptionPosition_openCloseBoxed,"null"),Validate_Position_openCloseBoxed_Value,"Validate_Position_openCloseBoxed_Value");
				Assert.assertEquals(Global.getOptionPosition_openClose,Validate_Position_openClose_Value,"Validate_Position_openClose_Value");
				Assert.assertEquals(NVL(Global.getOptionPosition_cmta,"null"),Validate_Position_cmta_Value,"Validate_Position_cmta_Value");
				Assert.assertEquals(NVL(Global.getOptionPosition_expiryDate,"null"),Validate_Position_expiryDate_Value,"Validate_Position_expiryDate_Value");
				Assert.assertEquals(Global.getOptionPosition_symbol,Validate_Position_symbol_Value,"Validate_OptionPosition_Symbol_Value");
				Assert.assertEquals(Global.getOptionPosition_positionString,Validate_Position_positionString_Value,"Validate_Position_positionString_Value");
				Assert.assertEquals(Global.getOptionPosition_symbolSfx,Validate_Position_symbolSfx_Value,"Validate_Position_symbolSfx_Value");
				Assert.assertEquals(NVL(Global.getOptionPosition_originatingUserDesc,"null"),Validate_Position_originatingUserDesc_Value,"Validate_Position_originatingUserDesc_Value");
				Assert.assertEquals(Global.getOptionPosition_account,Validate_Position_account_Value,"Validate_Position_account_Value");
				Assert.assertEquals(Global.getOptionPosition_completeDayBuyOrderQty,Global.getOptioncompleteDayBuyOrderQty,"Validate_Option_completeDayBuyOrderQty");
				Assert.assertEquals(Global.getOptionPosition_completeDaySellLongOrderQty,Global.getOptioncompleteDaySellLongOrderQty,"Validate_Option_completeDaySellLongOrderQty");
				Assert.assertEquals(Global.getOptionPosition_completeDaySellShortOrderQty,Global.getOptioncompleteDaySellShortOrderQty,"Validate_Option_completeDaySellShortOrderQty");
				Assert.assertEquals(Global.getOptionPosition_execQtyFrac,0.0,"Validate_Position_execQtyFrac_Value");
				Assert.assertEquals(Global.getOptionPosition_unRealizedPnL,0.0,"Validate_Position_unRealizedPnL_Value");
				Assert.assertEquals(Global.getOptionPosition_openQty,0.0,"Validate_Position_openQty_Value");
				Assert.assertEquals(Global.getOptionPosition_buyAvgPx,0.0,"Validate_Position_buyAvgPx_Value");
				Assert.assertEquals(Global.getOptionPosition_sellAvgPx,0.0,"Validate_Position_sellAvgPx_Value");
				Assert.assertEquals(Global.getOptionPosition_execQty,(Global.getOptionPosition_completeDayBuyOrderQty-Global.getOptionPosition_completeDaySellLongOrderQty),"Validate_Position_execQuantity_Value");
				Assert.assertEquals(String.format("%.4f",Global.getOptionPosition_totDollarOfTrade),String.format("%.4f",Global.totalTrade),"Validate_totDollarOfTrade");
				Assert.assertEquals(decimalFormat.format(Global.getOptionPosition_avgPrice),decimalFormat.format(AvgPrx),"Validate_AvgPrx");
				Assert.assertEquals(decimalFormat.format(Global.getOptionPosition_realizedPnL),decimalFormat.format(RealizePNL),"Validate_RealizePNL");
				break;
			}
			else
			{
				continue;
			}
		}
	}
	catch (Exception e)
	{
		LoggingManager.logger.error(e);
	}
}
		

public static void Validate_Executions(	Response getresponse,
										String Endpointversion,
										String BoothID,
										String Executions_Case,
										String Executions_SideDesc,
										String Executions_Side,
										String OrderId,
										Integer qOrderId,
										String Fetch_Executions_Symbol,
										String Fetch_Executions_Account,
										String Fetch_Executions_OrderQty,
										String Fetch_Executions_Price,
										String Fetch_Executions_OrderType,
										String Fetch_Executions_Destination,
										String Fetch_Executions_SymbolSfx,
										String Fetch_Executions_Status,
										String Fetch_Executions_Text,
										String Fetch_Executions_OrdStatus,
										String Fetch_Last_Executions_OrdStatus,
										String Fetch_Executions_OriginatingUserDesc,
										String Fetch_Executions_ExecBroker,
										String Fetch_Executions_TimeInForce,
										String Fetch_ExecutionS_ExecRefID,
										String Fetch_Executions_ExecTransType,
										String Fetch_Executions_ExecTransTypeDesc,
										String Executions_Sell_SideDesc,
										String Executions_Sell_Side,
										String Sell_OrderId,
										Integer Sell_qOrderId,
										String Fetch_Executions_Sell_Symbol,
										String Fetch_Executions_Sell_Account,
										String Fetch_Executions_Sell_OrderQty,
										String Fetch_Executions_Sell_Price,
										String Fetch_Executions_Sell_OrderType,
										String Fetch_Executions_Sell_Destination,
										String Fetch_Executions_Sell_SymbolSfx,
										String Fetch_Executions_Sell_Status,
										String Fetch_Executions_Sell_Text,
										String Fetch_Executions_Sell_OrdStatus,
										String Fetch_Last_Executions_Sell_OrdStatus,
										String Fetch_Executions_Sell_OriginatingUserDesc,
										String Fetch_Executions_Sell_ExecBroker,
										String Fetch_Executions_Sell_TimeInForce,
										String Fetch_Executions_Sell_ExecRefID,
										String Fetch_Executions_Sell_ExecTransType,
										String Fetch_Executions_Sell_ExecTransTypeDesc,
										String CaseType)	
{
	try
	{
		JsonPath jsonresponse = new JsonPath(getresponse.getBody().asString());
		String getResponseArray=apiRespVersion(Endpointversion);
		int ResponseArraySize = jsonresponse.getInt(getResponseArray+".size()");
		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DecimalFormat decimalFormat = new DecimalFormat("0.0000");
		decimalFormat.getRoundingMode();
		Global.getAvgPrice=0.0;
		ArrayList<Integer> BuyExecID = new ArrayList<Integer>();
		ArrayList<Integer> SellExecID = new ArrayList<Integer>();
		ArrayList<Double> getBuyExecPrx = new ArrayList<Double>();
		ArrayList<Double> getSellExecPrx = new ArrayList<Double>();
		Map<Object,Integer> getBuyExecutionsTime = new HashMap<Object,Integer>();
		Map<Integer,Double> getBuyExecutionsAvgPrice = new HashMap<Integer,Double>();
		Map<Integer,Double> getBuyExecutionsLastPrice = new HashMap<Integer,Double>();
		Map<Integer,Double> getBuyExecutionsLastshares = new HashMap<Integer,Double>();
		Map<Object,Integer> getSellExecutionsTime = new HashMap<Object,Integer>();
		Map<Integer,Double> getSellExecutionsAvgPrice = new HashMap<Integer,Double>();
		Map<Integer,Double> getSellExecutionsLastPrice = new HashMap<Integer,Double>();
		Map<Integer,Double> getSellExecutionsLastshares = new HashMap<Integer,Double>();
		switch (Executions_Case)
		{

			//-------------------------------------------------BUY Case-------------------------------------------------------------------------------
			case "BUY":

				LoggingManager.logger.info("API-Executions_Case : ["+Executions_Case+"]");
				LoggingManager.logger.info("----------------------[BUY Execution DATA]---------------------");
				LoggingManager.logger.info("API-BUY Executions_OrderId : ["+OrderId+"]");
				LoggingManager.logger.info("API-BUY Executions_SideDesc : ["+Executions_SideDesc+"]");
				LoggingManager.logger.info("API-BUY Executions_Side : ["+Executions_Side+"]");
				LoggingManager.logger.info("API-BUY Fetch_Executions_Symbol : ["+Fetch_Executions_Symbol+"]");
				LoggingManager.logger.info("API-BUY Fetch_Executions_Account : ["+Fetch_Executions_Account+"]");
				LoggingManager.logger.info("API-BUY Fetch_Executions_OrderQty : ["+Fetch_Executions_OrderQty+"]");
				LoggingManager.logger.info("API-BUY Fetch_Executions_Price : ["+Fetch_Executions_Price+"]");
				LoggingManager.logger.info("API-BUY Fetch_Executions_OrderType : ["+Fetch_Executions_OrderType+"]");
				LoggingManager.logger.info("API-BUY Fetch_Executions_Destination : ["+Fetch_Executions_Destination+"]");
				LoggingManager.logger.info("API-BUY Fetch_Executions_SymbolSfx : ["+Fetch_Executions_SymbolSfx+"]");
				LoggingManager.logger.info("API-BUY Fetch_Executions_Status : ["+Fetch_Executions_Status+"]");
				LoggingManager.logger.info("API-BUY Fetch_Executions_OrdStatus : ["+Fetch_Executions_OrdStatus+"]");
				LoggingManager.logger.info("API-BUY Fetch_Executions_Text : ["+Fetch_Executions_Text+"]");
				LoggingManager.logger.info("API-BUY Fetch_Last_Executions_OrdStatus : ["+Fetch_Last_Executions_OrdStatus+"]");
				LoggingManager.logger.info("API-BUY Fetch_Executions_OriginatingUserDesc : ["+Fetch_Executions_OriginatingUserDesc+"]");
				LoggingManager.logger.info("API-BUY Fetch_Executions_ExecBroker : ["+Fetch_Executions_ExecBroker+"]");
				LoggingManager.logger.info("API-BUY Fetch_Executions_TimeInForce : ["+Fetch_Executions_TimeInForce+"]");
				LoggingManager.logger.info("API-BUY Fetch_Executions_ExecRefID : ["+Fetch_ExecutionS_ExecRefID+"]");
				LoggingManager.logger.info("API-BUY Fetch_Executions_ExecTransType : ["+Fetch_Executions_ExecTransType+"]");
				LoggingManager.logger.info("API-BUY Fetch_Executions_ExecTransTypeDesc : ["+Fetch_Executions_ExecTransTypeDesc+"]");

				loop: for(int position = ResponseArraySize-1; position >=0; position--)
				{
					String response_symbolSfx = jsonresponse.getString(getResponseArray+"["+position+"].symbolSfx");
					String response_symbol = jsonresponse.getString(getResponseArray+"["+position+"].symbol");
					String response_account = jsonresponse.getString(getResponseArray+"["+position+"].account");
					Double response_orderQty = jsonresponse.getDouble(getResponseArray+"["+position+"].orderQty");
					Double response_leavesQty = jsonresponse.getDouble(getResponseArray+"["+position+"].leavesQty");
					Double response_lastShares = jsonresponse.getDouble(getResponseArray+"["+position+"].lastShares");
					Double response_cumQty = jsonresponse.getDouble(getResponseArray+"["+position+"].cumQty");
					Double response_price = jsonresponse.getDouble(getResponseArray+"["+position+"].price");
					String response_ordType = jsonresponse.getString(getResponseArray+"["+position+"].ordType");
					String response_side = jsonresponse.getString(getResponseArray+"["+position+"].side");
					String response_destination = jsonresponse.getString(getResponseArray+"["+position+"].destination");
					Integer response_qOrderID = jsonresponse.getInt(getResponseArray+"["+position+"].qOrderID");
					String response_OrderID = jsonresponse.getString(getResponseArray+"["+position+"].orderId");
					Double response_lastPx = jsonresponse.getDouble(getResponseArray+"["+position+"].lastPx");
					String response_sideDesc = jsonresponse.getString(getResponseArray+"["+position+"].sideDesc");
					Double response_avgPx = jsonresponse.getDouble(getResponseArray+"["+position+"].avgPx");
					String response_transactTime = jsonresponse.getString(getResponseArray+"["+position+"].transactTime");
					String response_tradeDate = jsonresponse.getString(getResponseArray+"["+position+"].tradeDate");
					String response_transactTimeUtc = jsonresponse.getString(getResponseArray+"["+position+"].transactTimeUtc");
					String response_tradeDateUtc = jsonresponse.getString(getResponseArray+"["+position+"].tradeDateUtc");
					String response_status = jsonresponse.getString(getResponseArray+"["+position+"].status");
					String response_text = jsonresponse.getString(getResponseArray+"["+position+"].text");
					String response_ordStatus = jsonresponse.getString(getResponseArray+"["+position+"].ordStatus");
					String response_originatingUserDesc = jsonresponse.getString(getResponseArray+"["+position+"].originatingUserDesc");
					String response_clOrdID = jsonresponse.getString(getResponseArray+"["+position+"].clOrdID");
					String response_origClOrdID = jsonresponse.getString(getResponseArray+"["+position+"].origClOrdID");
					String response_execRefID = jsonresponse.getString(getResponseArray+"["+position+"].execRefID");
					String response_execID = jsonresponse.getString(getResponseArray+"["+position+"].execID");
					String response_execType = jsonresponse.getString(getResponseArray+"["+position+"].execType");
					String response_execTransType = jsonresponse.getString(getResponseArray+"["+position+"].execTransType");
					String response_execTransTypeDesc = jsonresponse.getString(getResponseArray+"["+position+"].execTransTypeDesc");
					String response_timeInForce = jsonresponse.getString(getResponseArray+"["+position+"].timeInForce");
					String response_execBroker = jsonresponse.getString(getResponseArray+"["+position+"].execBroker");

					if (response_OrderID.equalsIgnoreCase(OrderId)
							&& (response_transactTime.substring(0,10)).equalsIgnoreCase(localDateTime.format(formatter)))
					{
						getBuyExecPrx.add(response_lastPx);
						getBuyExecutionsAvgPrice.put(Integer.parseInt(response_execID), response_avgPx);
						getBuyExecutionsLastPrice.put(Integer.parseInt(response_execID),response_lastPx);
						Assert.assertNotEquals(response_avgPx,null,"Validate_response_avgPx");
						Assert.assertNotEquals(response_lastPx,null,"Validate_response_lastPx");
						Assert.assertNotEquals(response_execID,null,"Validate_Executions_Buy_execID");
						Assert.assertNotEquals(response_execType,null,"Validate_Executions_Buy_execType");
						Assert.assertNotEquals(response_lastShares,null,"Validate_Executions_Buy_lastShares");
						//Assert.assertNotEquals(response_origClOrdID,null,"Validate_Executions_Buy_origClOrdID");
						Assert.assertEquals(response_origClOrdID,null,"Validate_Executions_Buy_origClOrdID");
						Assert.assertEquals(response_OrderID,OrderId,"Validate_Buy_OrderId");
						Assert.assertEquals(response_qOrderID,qOrderId,"Validate_Buy_qOrderId");
						Assert.assertEquals((response_transactTime.substring(0,10)),localDateTime.format(formatter),"Validate_response_transactTime");
						Assert.assertEquals((response_tradeDate.substring(0,10)),localDateTime.format(formatter),"Validate_response_tradeDate");
						Assert.assertEquals((response_transactTimeUtc.substring(0,10)),localDateTime.format(formatter),"Validate_response_transactTimeUTC");
						Assert.assertEquals((response_tradeDateUtc.substring(0,10)),localDateTime.format(formatter),"Validate_response_tradeDateUTC");
						Assert.assertEquals(response_symbol,Fetch_Executions_Symbol,"Validate_Executions_Buy_Symbol");
						Assert.assertEquals(NVL(response_symbolSfx,"null"),Fetch_Executions_SymbolSfx,"Validate_Executions_Buy_SymbolSfx");
						Assert.assertEquals(response_account,Fetch_Executions_Account,"Validate_Executions_Buy_Account");
						Assert.assertEquals(response_orderQty,Double.parseDouble(Fetch_Executions_OrderQty),"Validate_Executions_Buy_OrderQty");
						Assert.assertEquals(response_leavesQty,(response_orderQty-response_cumQty),"Validate_Executions_Buy_leavesQty");
						Assert.assertEquals(response_cumQty,(response_orderQty-response_leavesQty),"Validate_Executions_Buy_cumQty");
						Assert.assertEquals(response_price,Double.parseDouble(Fetch_Executions_Price),"Validate_Executions_Buy_Price");
						Assert.assertEquals(response_ordType,Fetch_Executions_OrderType,"Validate_Executions_Buy_OrderType");
						Assert.assertEquals(response_side,Executions_Side,"Validate_Executions_Buy_Side");
						Assert.assertEquals(response_destination,Fetch_Executions_Destination,"Validate_Executions_Buy_Destination");
						Assert.assertEquals(response_sideDesc,Executions_SideDesc,"Validate_Executions_Buy_sideDesc");
						Assert.assertEquals(NVL(response_status,"null"),Fetch_Executions_Status,"Validate_Executions_Buy_Status");
						Assert.assertEquals(NVL(response_text,"null"),Fetch_Executions_Text,"Validate_Executions_Buy_Text");

						if ((response_leavesQty.toString()).equalsIgnoreCase("0.0") && (response_orderQty.toString()).equalsIgnoreCase(response_cumQty.toString()))
						{Assert.assertEquals(response_ordStatus,Fetch_Last_Executions_OrdStatus,"Validate_Last_Executions_Buy_OrdStatus");}
						else {Assert.assertEquals(response_ordStatus,Fetch_Executions_OrdStatus,"Validate_Executions_Buy_OrdStatus");}
						Assert.assertEquals(response_originatingUserDesc,Fetch_Executions_OriginatingUserDesc,"Validate_Executions_Buy_OriginatingUserDesc");
						Assert.assertEquals(response_clOrdID,BoothID+"-"+(qOrderId-1)+"-"+1,"Validate_Executions_Buy_clOrdID");
						Assert.assertEquals(response_execRefID,Fetch_ExecutionS_ExecRefID,"Validate_Executions_Buy_ExecRefID");
						Assert.assertEquals(response_execTransType,Fetch_Executions_ExecTransType,"Validate_Executions_Buy_ExecTransType");
						Assert.assertEquals(response_execTransTypeDesc,Fetch_Executions_ExecTransTypeDesc,"Validate_Executions_Buy_ExecTransTypeDesc");
						Assert.assertEquals(response_timeInForce,Fetch_Executions_TimeInForce,"Validate_Executions_Buy_TimeInForce");
						Assert.assertEquals(NVL(response_execBroker,"null"),Fetch_Executions_ExecBroker,"Validate_Executions_Buy_ExecBroker");
					}
					else
					{
						continue loop;
					}
				}

				if (CaseType.equalsIgnoreCase("position"))
				{
					if(getBuyExecPrx.isEmpty()==false)
					{
						LoggingManager.logger.info("Price List for LastPrx Calculations:"+getBuyExecPrx);
						for (int lastPrice=0;lastPrice<=getBuyExecPrx.size()-1; lastPrice++)
						{
							LoggingManager.logger.info("Add Execution Prx = "+Global.getAvgPrice +" + "+getBuyExecPrx.get(lastPrice));
							Global.getAvgPrice+= getBuyExecPrx.get(lastPrice);
							LoggingManager.logger.info("= "+Global.getAvgPrice);

						}
						LoggingManager.logger.info("Total Prx counts :"+getBuyExecPrx.size());
						LoggingManager.logger.info("Total Executions Prx Added :"+Global.getAvgPrice);
						Global.totalTrade=Global.getAvgPrice;
						Global.getAvgPrice/=getBuyExecPrx.size();
						if(Global.getorderType=="Equity") {LoggingManager.logger.info("Total Order Avg. Price :"+decimalFormat.format(Global.getAvgPrice));}
						Global.getOptionAvgPrice=Global.getAvgPrice;
						if(Global.getorderType=="Option") {LoggingManager.logger.info("Total OptionOrder Avg. Price :"+decimalFormat.format(Global.getOptionAvgPrice));}
					}
					else
					{
						LoggingManager.logger.info("API-No Executions Found Against OrderID : ["+OrderId+"]");
						if(Global.getorderType=="Equity") {LoggingManager.logger.info("Total Order Avg. Price :"+decimalFormat.format(Global.getAvgPrice));}
						Global.getOptionAvgPrice=Global.getAvgPrice;
						if(Global.getorderType=="Option") {LoggingManager.logger.info("Total OptionOrder Avg. Price :"+decimalFormat.format(Global.getOptionAvgPrice));}
					}
				}
				else
				{
					LoggingManager.logger.info("API-Executions Buy  ExecutionsLastPrice   : ["+getBuyExecutionsLastPrice+"]");
					LoggingManager.logger.info("API-Executions Buy  ExecutionsAvgPrice    : ["+getBuyExecutionsAvgPrice+"]");
				}
				break;
			//-------------------------------------------------SELL Case-------------------------------------------------------------------------------

			case "SELL":

				LoggingManager.logger.info("API-Executions_Case : ["+Executions_Case+"]");
				LoggingManager.logger.info("----------------------[BUY Execution DATA]---------------------");
				LoggingManager.logger.info("API-BUY Executions_OrderId : ["+OrderId+"]");
				LoggingManager.logger.info("API-BUY Executions_SideDesc : ["+Executions_SideDesc+"]");
				LoggingManager.logger.info("API-BUY Executions_Side : ["+Executions_Side+"]");
				LoggingManager.logger.info("API-BUY Fetch_Executions_Symbol : ["+Fetch_Executions_Symbol+"]");
				LoggingManager.logger.info("API-BUY Fetch_Executions_Account : ["+Fetch_Executions_Account+"]");
				LoggingManager.logger.info("API-BUY Fetch_Executions_OrderQty : ["+Fetch_Executions_OrderQty+"]");
				LoggingManager.logger.info("API-BUY Fetch_Executions_Price : ["+Fetch_Executions_Price+"]");
				LoggingManager.logger.info("API-BUY Fetch_Executions_OrderType : ["+Fetch_Executions_OrderType+"]");
				LoggingManager.logger.info("API-BUY Fetch_Executions_Destination : ["+Fetch_Executions_Destination+"]");
				LoggingManager.logger.info("API-BUY Fetch_Executions_SymbolSfx : ["+Fetch_Executions_SymbolSfx+"]");
				LoggingManager.logger.info("API-BUY Fetch_Executions_Status : ["+Fetch_Executions_Status+"]");
				LoggingManager.logger.info("API-BUY Fetch_Executions_OrdStatus : ["+Fetch_Executions_OrdStatus+"]");
				LoggingManager.logger.info("API-BUY Fetch_Executions_Text : ["+Fetch_Executions_Text+"]");
				LoggingManager.logger.info("API-BUY Fetch_Last_Executions_OrdStatus : ["+Fetch_Last_Executions_OrdStatus+"]");
				LoggingManager.logger.info("API-BUY Fetch_Executions_OriginatingUserDesc : ["+Fetch_Executions_OriginatingUserDesc+"]");
				LoggingManager.logger.info("API-BUY Fetch_Executions_ExecBroker : ["+Fetch_Executions_ExecBroker+"]");
				LoggingManager.logger.info("API-BUY Fetch_Executions_TimeInForce : ["+Fetch_Executions_TimeInForce+"]");
				LoggingManager.logger.info("API-BUY Fetch_Executions_ExecRefID : ["+Fetch_ExecutionS_ExecRefID+"]");
				LoggingManager.logger.info("API-BUY Fetch_Executions_ExecTransType : ["+Fetch_Executions_ExecTransType+"]");
				LoggingManager.logger.info("API-BUY Fetch_Executions_ExecTransTypeDesc : ["+Fetch_Executions_ExecTransTypeDesc+"]");
				LoggingManager.logger.info("----------------------[SELL Execution DATA]---------------------");
				LoggingManager.logger.info("API-SELL Executions_OrderId : ["+Sell_OrderId+"]");
				LoggingManager.logger.info("API-SELL Executions_SideDesc : ["+Executions_Sell_SideDesc+"]");
				LoggingManager.logger.info("API-SELL Executions_Side : ["+Executions_Sell_Side+"]");
				LoggingManager.logger.info("API-SELL Fetch_Executions_Symbol : ["+Fetch_Executions_Sell_Symbol+"]");
				LoggingManager.logger.info("API-SELL Fetch_Executions_Account : ["+Fetch_Executions_Sell_Account+"]");
				LoggingManager.logger.info("API-SELL Fetch_Executions_OrderQty : ["+Fetch_Executions_Sell_OrderQty+"]");
				LoggingManager.logger.info("API-SELL Fetch_Executions_Price : ["+Fetch_Executions_Sell_Price+"]");
				LoggingManager.logger.info("API-SELL Fetch_Executions_OrderType : ["+Fetch_Executions_Sell_OrderType+"]");
				LoggingManager.logger.info("API-SELL Fetch_Executions_Destination : ["+Fetch_Executions_Sell_Destination+"]");
				LoggingManager.logger.info("API-SELL Fetch_Executions_SymbolSfx : ["+Fetch_Executions_Sell_SymbolSfx+"]");
				LoggingManager.logger.info("API-SELL Fetch_Executions_Status : ["+Fetch_Executions_Sell_Status+"]");
				LoggingManager.logger.info("API-SELL Fetch_Executions_OrdStatus : ["+Fetch_Executions_Sell_OrdStatus+"]");
				LoggingManager.logger.info("API-SELL Fetch_Executions_Text : ["+Fetch_Executions_Sell_Text+"]");
				LoggingManager.logger.info("API-SELL Fetch_Last_Executions_OrdStatus : ["+Fetch_Last_Executions_Sell_OrdStatus+"]");
				LoggingManager.logger.info("API-SELL Fetch_Executions_OriginatingUserDesc : ["+Fetch_Executions_Sell_OriginatingUserDesc+"]");
				LoggingManager.logger.info("API-SELL Fetch_Executions_ExecBroker : ["+Fetch_Executions_Sell_ExecBroker+"]");
				LoggingManager.logger.info("API-SELL Fetch_Executions_TimeInForce : ["+Fetch_Executions_Sell_TimeInForce+"]");
				LoggingManager.logger.info("API-SELL Fetch_Executions_ExecRefID : ["+Fetch_Executions_Sell_ExecRefID+"]");
				LoggingManager.logger.info("API-SELL Fetch_Executions_ExecTransType : ["+Fetch_Executions_Sell_ExecTransType+"]");
				LoggingManager.logger.info("API-SELL Fetch_Executions_ExecTransTypeDesc : ["+Fetch_Executions_Sell_ExecTransTypeDesc+"]");

				for(int position = ResponseArraySize-1; position >=0; position--)
				{
					String response_symbol = jsonresponse.getString(getResponseArray+"["+position+"].symbol");
					String response_account = jsonresponse.getString(getResponseArray+"["+position+"].account");
					Double response_orderQty = jsonresponse.getDouble(getResponseArray+"["+position+"].orderQty");
					Double response_price = jsonresponse.getDouble(getResponseArray+"["+position+"].price");
					String response_ordType = jsonresponse.getString(getResponseArray+"["+position+"].ordType");
					String response_side = jsonresponse.getString(getResponseArray+"["+position+"].side");
					String response_destination = jsonresponse.getString(getResponseArray+"["+position+"].destination");
					Integer response_qOrderID = jsonresponse.getInt(getResponseArray+"["+position+"].qOrderID");
					String response_OrderID = jsonresponse.getString(getResponseArray+"["+position+"].orderId");
					Double response_lastPx = jsonresponse.getDouble(getResponseArray+"["+position+"].lastPx");
					Double response_lastShares = jsonresponse.getDouble(getResponseArray+"["+position+"].lastShares");
					Double response_avgPx = jsonresponse.getDouble(getResponseArray+"["+position+"].avgPx");
					String response_sideDesc = jsonresponse.getString(getResponseArray+"["+position+"].sideDesc");
					String response_transactTime = jsonresponse.getString(getResponseArray+"["+position+"].transactTime");
					String response_execID = jsonresponse.getString(getResponseArray+"["+position+"].execID");
					String response_symbolSfx = jsonresponse.getString(getResponseArray+"["+position+"].symbolSfx");
					Double response_leavesQty = jsonresponse.getDouble(getResponseArray+"["+position+"].leavesQty");
					Double response_cumQty = jsonresponse.getDouble(getResponseArray+"["+position+"].cumQty");
					String response_tradeDate = jsonresponse.getString(getResponseArray+"["+position+"].tradeDate");
					String response_transactTimeUtc = jsonresponse.getString(getResponseArray+"["+position+"].transactTimeUtc");
					String response_tradeDateUtc = jsonresponse.getString(getResponseArray+"["+position+"].tradeDateUtc");
					String response_status = jsonresponse.getString(getResponseArray+"["+position+"].status");
					String response_text = jsonresponse.getString(getResponseArray+"["+position+"].text");
					String response_ordStatus = jsonresponse.getString(getResponseArray+"["+position+"].ordStatus");
					String response_originatingUserDesc = jsonresponse.getString(getResponseArray+"["+position+"].originatingUserDesc");
					String response_clOrdID = jsonresponse.getString(getResponseArray+"["+position+"].clOrdID");
					String response_origClOrdID = jsonresponse.getString(getResponseArray+"["+position+"].origClOrdID");
					String response_execRefID = jsonresponse.getString(getResponseArray+"["+position+"].execRefID");
					String response_execType = jsonresponse.getString(getResponseArray+"["+position+"].execType");
					String response_execTransType = jsonresponse.getString(getResponseArray+"["+position+"].execTransType");
					String response_execTransTypeDesc = jsonresponse.getString(getResponseArray+"["+position+"].execTransTypeDesc");
					String response_timeInForce = jsonresponse.getString(getResponseArray+"["+position+"].timeInForce");
					String response_execBroker = jsonresponse.getString(getResponseArray+"["+position+"].execBroker");

					if (response_OrderID.equalsIgnoreCase(OrderId)
							&& (response_transactTime.substring(0,10)).equalsIgnoreCase(localDateTime.format(formatter)))
					{
						//getBuyOrderid.add(response_OrderID);
						BuyExecID.add(Integer.parseInt(response_execID));
						getBuyExecutionsTime.put(response_transactTime,response_qOrderID);
						getBuyExecutionsAvgPrice.put(Integer.parseInt(response_execID), response_avgPx);
						getBuyExecutionsLastPrice.put(Integer.parseInt(response_execID),response_lastPx);
						getBuyExecutionsLastshares.put(Integer.parseInt(response_execID), response_lastShares);

						Assert.assertNotEquals(response_avgPx,null,"Validate_response_avgPx");
						Assert.assertNotEquals(response_lastPx,null,"Validate_response_lastPx");
						Assert.assertNotEquals(response_execID,null,"Validate_Executions_Buy_execID");
						Assert.assertNotEquals(response_execType,null,"Validate_Executions_Buy_execType");
						Assert.assertNotEquals(response_lastShares,null,"Validate_Executions_Buy_lastShares");
						Assert.assertEquals(response_origClOrdID,null,"Validate_Executions_Buy_origClOrdID");
						Assert.assertEquals(response_OrderID,OrderId,"Validate_Buy_OrderId");
						Assert.assertEquals(response_qOrderID,qOrderId,"Validate_Buy_qOrderId");
						Assert.assertEquals((response_transactTime.substring(0,10)),localDateTime.format(formatter),"Validate_response_transactTime");
						Assert.assertEquals((response_tradeDate.substring(0,10)),localDateTime.format(formatter),"Validate_response_tradeDate");
						Assert.assertEquals((response_transactTimeUtc.substring(0,10)),localDateTime.format(formatter),"Validate_response_transactTimeUTC");
						Assert.assertEquals((response_tradeDateUtc.substring(0,10)),localDateTime.format(formatter),"Validate_response_tradeDateUTC");
						Assert.assertEquals(response_symbol,Fetch_Executions_Symbol,"Validate_Executions_Buy_Symbol");
						Assert.assertEquals(NVL(response_symbolSfx,"null"),Fetch_Executions_SymbolSfx,"Validate_Executions_Buy_SymbolSfx");
						Assert.assertEquals(response_account,Fetch_Executions_Account,"Validate_Executions_Buy_Account");
						Assert.assertEquals(response_orderQty,Double.parseDouble(Fetch_Executions_OrderQty),"Validate_Executions_Buy_OrderQty");
						Assert.assertEquals(response_leavesQty,(response_orderQty-response_cumQty),"Validate_Executions_Buy_leavesQty");
						Assert.assertEquals(response_cumQty,(response_orderQty-response_leavesQty),"Validate_Executions_Buy_OrderQty");
						Assert.assertEquals(response_price,Double.parseDouble(Fetch_Executions_Price),"Validate_Executions_Buy_Price");
						Assert.assertEquals(response_ordType,Fetch_Executions_OrderType,"Validate_Executions_Buy_OrderType");
						Assert.assertEquals(response_side,Executions_Side,"Validate_Executions_Buy_Side");
						Assert.assertEquals(response_destination,Fetch_Executions_Destination,"Validate_Executions_Buy_Destination");
						Assert.assertEquals(response_sideDesc,Executions_SideDesc,"Validate_Executions_Buy_sideDesc");
						Assert.assertEquals(NVL(response_status,"null"),Fetch_Executions_Status,"Validate_Executions_Buy_Status");
						Assert.assertEquals(NVL(response_text,"null"),Fetch_Executions_Text,"Validate_Executions_Buy_Text");
						if ((response_leavesQty.toString()).equalsIgnoreCase("0.0") && (response_orderQty.toString()).equalsIgnoreCase(response_cumQty.toString()))
						{Assert.assertEquals(response_ordStatus,Fetch_Last_Executions_OrdStatus,"Validate_Last_Executions_Buy_OrdStatus");}
						else {Assert.assertEquals(response_ordStatus,Fetch_Executions_OrdStatus,"Validate_Executions_Buy_OrdStatus");}
						Assert.assertEquals(response_originatingUserDesc,Fetch_Executions_OriginatingUserDesc,"Validate_Executions_Buy_OriginatingUserDesc");
						Assert.assertEquals(response_clOrdID,BoothID+"-"+(qOrderId-1)+"-"+1,"Validate_Executions_Buy_clOrdID");
						Assert.assertEquals(response_execRefID,Fetch_ExecutionS_ExecRefID,"Validate_Executions_Buy_ExecRefID");
						Assert.assertEquals(response_execTransType,Fetch_Executions_ExecTransType,"Validate_Executions_Buy_ExecTransType");
						Assert.assertEquals(response_execTransTypeDesc,Fetch_Executions_ExecTransTypeDesc,"Validate_Executions_Buy_ExecTransTypeDesc");
						Assert.assertEquals(response_timeInForce,Fetch_Executions_TimeInForce,"Validate_Executions_Buy_TimeInForce");
						Assert.assertEquals(NVL(response_execBroker,"null"),Fetch_Executions_ExecBroker,"Validate_Executions_Buy_ExecBroker");
					}

					else if (response_OrderID.equalsIgnoreCase(Sell_OrderId)
							&& (response_transactTime.substring(0,10)).equalsIgnoreCase(localDateTime.format(formatter)))
					{
						//getSellOrderid.add(response_OrderID);
						SellExecID.add(Integer.parseInt(response_execID));
						getSellExecutionsTime.put(response_transactTime,response_qOrderID);
						getSellExecutionsAvgPrice.put(Integer.parseInt(response_execID), response_avgPx);
						getSellExecutionsLastPrice.put(Integer.parseInt(response_execID),response_lastPx);
						getSellExecutionsLastshares.put(Integer.parseInt(response_execID),response_lastShares);

						Assert.assertNotEquals(response_avgPx,null,"Validate_response_avgPx");
						Assert.assertNotEquals(response_lastPx,null,"Validate_response_lastPx");
						Assert.assertNotEquals(response_execID,null,"Validate_Executions_Sell_execID");
						Assert.assertNotEquals(response_execType,null,"Validate_Executions_Sell_execType");
						Assert.assertNotEquals(response_lastShares,null,"Validate_Executions_Sell_lastShares");
						Assert.assertEquals(response_origClOrdID,null,"Validate_Executions_Sell_origClOrdID");
						Assert.assertEquals(response_OrderID,Sell_OrderId,"Validate_Sell_OrderId");
						Assert.assertEquals(response_qOrderID,Sell_qOrderId,"Validate_Sell_qOrderId");
						Assert.assertEquals((response_transactTime.substring(0,10)),localDateTime.format(formatter),"Validate_response_transactTime");
						Assert.assertEquals((response_tradeDate.substring(0,10)),localDateTime.format(formatter),"Validate_response_tradeDate");
						Assert.assertEquals((response_transactTimeUtc.substring(0,10)),localDateTime.format(formatter),"Validate_response_transactTimeUTC");
						Assert.assertEquals((response_tradeDateUtc.substring(0,10)),localDateTime.format(formatter),"Validate_response_tradeDateUTC");
						Assert.assertEquals(response_symbol,Fetch_Executions_Sell_Symbol,"Validate_Executions_Sell_Symbol");
						Assert.assertEquals(NVL(response_symbolSfx,"null"),Fetch_Executions_Sell_SymbolSfx,"Validate_Executions_Sell_SymbolSfx");
						Assert.assertEquals(response_account,Fetch_Executions_Sell_Account,"Validate_Executions_Sell_Account");
						Assert.assertEquals(response_orderQty,Double.parseDouble(Fetch_Executions_Sell_OrderQty),"Validate_Executions_Sell_OrderQty");
						Assert.assertEquals(response_leavesQty,(response_orderQty-response_cumQty),"Validate_Executions_Sell_leavesQty");
						Assert.assertEquals(response_cumQty,(response_orderQty-response_leavesQty),"Validate_Executions_Sell_OrderQty");
						Assert.assertEquals(response_price,Double.parseDouble(Fetch_Executions_Sell_Price),"Validate_Executions_Sell_Price");
						Assert.assertEquals(response_ordType,Fetch_Executions_Sell_OrderType,"Validate_Executions_Sell_OrderType");
						Assert.assertEquals(response_side,Executions_Sell_Side,"Validate_Executions_Sell_Side");
						Assert.assertEquals(response_destination,Fetch_Executions_Sell_Destination,"Validate_Executions_Sell_Destination");
						Assert.assertEquals(response_sideDesc,Executions_Sell_SideDesc,"Validate_Executions_Sell_sideDesc");
						Assert.assertEquals(NVL(response_status,"null"),Fetch_Executions_Sell_Status,"Validate_Executions_Sell_Status");
						Assert.assertEquals(NVL(response_text,"null"),Fetch_Executions_Sell_Text,"Validate_Executions_Sell_Text");
						if ((response_leavesQty.toString()).equalsIgnoreCase("0.0") && (response_orderQty.toString()).equalsIgnoreCase(response_cumQty.toString()))
						{Assert.assertEquals(response_ordStatus,Fetch_Last_Executions_Sell_OrdStatus,"Validate_Last_Executions_Sell_OrdStatus");}
						else {Assert.assertEquals(response_ordStatus,Fetch_Executions_Sell_OrdStatus,"Validate_Executions_Sell_OrdStatus");}
						Assert.assertEquals(response_originatingUserDesc,Fetch_Executions_Sell_OriginatingUserDesc,"Validate_Executions_Sell_OriginatingUserDesc");
						Assert.assertEquals(response_clOrdID,BoothID+"-"+(Sell_qOrderId-1)+"-"+1,"Validate_Executions_Sell_clOrdID");
						Assert.assertEquals(response_execRefID,Fetch_Executions_Sell_ExecRefID,"Validate_Executions_Sell_ExecRefID");
						Assert.assertEquals(response_execTransType,Fetch_Executions_Sell_ExecTransType,"Validate_Executions_Sell_ExecTransType");
						Assert.assertEquals(response_execTransTypeDesc,Fetch_Executions_Sell_ExecTransTypeDesc,"Validate_Executions_Sell_ExecTransTypeDesc");
						Assert.assertEquals(response_timeInForce,Fetch_Executions_Sell_TimeInForce,"Validate_Executions_Sell_TimeInForce");
						Assert.assertEquals(NVL(response_execBroker,"null"),Fetch_Executions_Sell_ExecBroker,"Validate_Executions_Sell_ExecBroker");
					}
				}
				if (CaseType.equalsIgnoreCase("position"))
				{
					LoggingManager.logger.info("API-Executions BuyExecutionsLastPrice   : ["+getBuyExecutionsLastPrice+"]");
					LoggingManager.logger.info("API-Executions SellExecutionsLastPrice  : ["+getSellExecutionsLastPrice+"]");
					//LoggingManager.logger.info("API-Executions BuyExecutionsLastshares  : ["+getBuyExecutionsLastshares+"]");
					//LoggingManager.logger.info("API-Executions SellExecutionsLastshares : ["+getSellExecutionsLastshares+"]");
					Collections.sort(BuyExecID);
					Collections.sort(SellExecID);
					LoggingManager.logger.info("API-Executions Buy ExecIDs  : ["+BuyExecID+"]");
					LoggingManager.logger.info("API-Executions Sell ExecIDs   : ["+SellExecID+"]");
					Double getBuyValue=0.0;
					Double getSellValue=0.0;
					int checkquantity=0;
					Boolean flag=true;

					if (getBuyExecutionsLastPrice.isEmpty()==false && getSellExecutionsLastPrice.isEmpty()==false)
					{
						int listsize=SellExecID.size();
						for(int position=0;position<listsize;position++)
						{
							//System.out.println(getBuyExecutionsLastshares.get(BuyExecID.get(0))+" > "+ getSellExecutionsLastshares.get(SellExecID.get(0)) );
							if(getBuyExecutionsLastshares.get(BuyExecID.get(0)) > getSellExecutionsLastshares.get(SellExecID.get(0))  )
							{
								// System.out.println("SellExecID = "+SellExecID.get(0));
								getSellValue+=Double.parseDouble(decimalFormat.format((getSellExecutionsLastshares.get(SellExecID.get(0)) * getSellExecutionsLastPrice.get(SellExecID.get(0)))));
								checkquantity+=getSellExecutionsLastshares.get(SellExecID.get(0));
								SellExecID.remove(0);
								LoggingManager.logger.info("API-SELL Execution Data : [("+getSellExecutionsLastshares.get(SellExecID.get(0))+" * "+getSellExecutionsLastPrice.get(SellExecID.get(0))+")] = "+getSellValue);
								LoggingManager.logger.info("API-SELL Order Quantity :"+checkquantity);
								LoggingManager.logger.info("API-Remaining SellExecIDs :"+SellExecID);

								if(getBuyExecutionsLastshares.get(BuyExecID.get(0)) == checkquantity)
								{
									//System.out.println("BuyExecID = "+BuyExecID.get(0));
									getBuyValue+=Double.parseDouble(decimalFormat.format((getBuyExecutionsLastshares.get(BuyExecID.get(0)) * getBuyExecutionsLastPrice.get(BuyExecID.get(0)))));
									BuyExecID.remove(0);
									LoggingManager.logger.info("API-BUY Execution Data : [("+getBuyExecutionsLastshares.get(BuyExecID.get(0)) +" * "+getBuyExecutionsLastPrice.get(BuyExecID.get(0))+")] = "+getBuyValue);
									LoggingManager.logger.info("API-BUY Order Quantity :"+getBuyExecutionsLastshares.get(BuyExecID.get(0)));
									LoggingManager.logger.info("API-Remaining BuyExecID :"+BuyExecID);

								}
							}
							else
							{
								// System.out.println("SellExecID = "+SellExecID.get(0));
								getSellValue+=Double.parseDouble(decimalFormat.format((getSellExecutionsLastshares.get(SellExecID.get(0)) * getSellExecutionsLastPrice.get(SellExecID.get(0)))));
								// System.out.println("BuyExecID = "+BuyExecID.get(0));
								getBuyValue+=Double.parseDouble(decimalFormat.format((getBuyExecutionsLastshares.get(BuyExecID.get(0)) * getBuyExecutionsLastPrice.get(BuyExecID.get(0)))));
								LoggingManager.logger.info("API-SELL Execution Data : [("+getSellExecutionsLastshares.get(SellExecID.get(0))+" * "+getSellExecutionsLastPrice.get(SellExecID.get(0))+")] = "+getSellValue);
								LoggingManager.logger.info("API-BUY Execution Data : [("+getBuyExecutionsLastshares.get(BuyExecID.get(0)) +" * "+getBuyExecutionsLastPrice.get(BuyExecID.get(0))+")] = "+getBuyValue);
								SellExecID.remove(0);
								BuyExecID.remove(0);
							}
						}
						LoggingManager.logger.info("API-Remaining SellExecIDs :"+SellExecID);
						LoggingManager.logger.info("API-Remaining BuyExecID :"+BuyExecID);
						LoggingManager.logger.info("API-Total Buy Prx Value : "+getBuyValue);
						LoggingManager.logger.info("API-Total Sell Prx Value :"+getSellValue);

						if(Global.getorderType=="Equity")
						{
							LoggingManager.logger.info("API-Calculating Realize PNL : [Previous PnL: ("+Global.getLONGrealizedPnL+")] + Current PnL:("+getSellValue+"-"+getBuyValue+")]");
							Global.getLONGrealizedPnL=Global.getLONGrealizedPnL+(getSellValue-getBuyValue);
							LoggingManager.logger.info("API-Total Equity Realize PNL  = "+Global.getLONGrealizedPnL);
							// Global.getSHORTrealizedPnL=Global.getSHORTrealizedPnL+(getBuyValue-getSellValue);
						}

						if(Global.getorderType=="Option")
						{
							LoggingManager.logger.info("API-Calculating Option Realize PNL : [Previous PnL: ("+Global.getOptionLONGrealizedPnL+")] + Current PnL:("+getSellValue+"-"+getBuyValue+")]");
							Global.getOptionLONGrealizedPnL=Global.getOptionLONGrealizedPnL+(getSellValue-getBuyValue);
							LoggingManager.logger.info("API-Total Option Realize PNL  = "+Global.getOptionLONGrealizedPnL);
						}

						if(BuyExecID.isEmpty()==false)
						{
							for (int lastPrice=0;lastPrice<=BuyExecID.size()-1; lastPrice++)
							{
								LoggingManager.logger.info("Add Execution Prx = "+Global.getAvgPrice +" + "+getBuyExecutionsLastPrice.get(BuyExecID.get(lastPrice)));
								Global.getAvgPrice+= getBuyExecutionsLastPrice.get(BuyExecID.get(lastPrice));
								//LoggingManager.logger.info("= "+Global.getAvgPrice);
							}

							LoggingManager.logger.info("Total Prx counts :"+BuyExecID.size());
							LoggingManager.logger.info("Total Executions Prx Added :"+Global.getAvgPrice);
							Global.totalTrade=Global.getAvgPrice;
							Global.getAvgPrice/=BuyExecID.size();
							if(Global.getorderType=="Equity") {LoggingManager.logger.info("Total Order Avg. Price :"+decimalFormat.format(Global.getAvgPrice));}
							Global.getOptionAvgPrice=Global.getAvgPrice;
							if(Global.getorderType=="Option") {LoggingManager.logger.info("Total OptionOrder Avg. Price :"+decimalFormat.format(Global.getOptionAvgPrice));}
						}
						else
						{
							LoggingManager.logger.info("API-No Executions Found Against OrderID : ["+OrderId+"]");
							if(Global.getorderType=="Equity") {LoggingManager.logger.info("Total Order Avg. Price :"+decimalFormat.format(Global.getAvgPrice));}
							Global.getOptionAvgPrice=Global.getAvgPrice;
							if(Global.getorderType=="Option") {LoggingManager.logger.info("Total OptionOrder Avg. Price :"+decimalFormat.format(Global.getOptionAvgPrice));}
						}
					}
				}

				else
				{
					LoggingManager.logger.info("API-Executions Buy  ExecutionsLastPrice   : ["+getBuyExecutionsLastPrice+"]");
					LoggingManager.logger.info("API-Executions Sell ExecutionsLastPrice   : ["+getSellExecutionsLastPrice+"]");
					LoggingManager.logger.info("API-Executions Buy  ExecutionsAvgPrice    : ["+getBuyExecutionsAvgPrice+"]");
					LoggingManager.logger.info("API-Executions Sell ExecutionsAvgPrice    : ["+getSellExecutionsAvgPrice+"]");
					LoggingManager.logger.info("API-Executions Buy  ExecutionsLastshares  : ["+getBuyExecutionsLastshares+"]");
					LoggingManager.logger.info("API-Executions Sell ExecutionsLastshares  : ["+getSellExecutionsLastshares+"]");
				}
				break;

			case "SHORTSELL":

				LoggingManager.logger.info("API-Executions_Case : ["+Executions_Case+"]");
				LoggingManager.logger.info("---------------------[SHORTSELL Execution DATA]--------------------");
				LoggingManager.logger.info("API-SHORTSELL Executions_OrderId : ["+OrderId+"]");
				LoggingManager.logger.info("API-SHORTSELL Executions_SideDesc : ["+Executions_SideDesc+"]");
				LoggingManager.logger.info("API-SHORTSELL Executions_Side : ["+Executions_Side+"]");
				LoggingManager.logger.info("API-SHORTSELL Fetch_Executions_Symbol : ["+Fetch_Executions_Symbol+"]");
				LoggingManager.logger.info("API-SHORTSELL Fetch_Executions_Account : ["+Fetch_Executions_Account+"]");
				LoggingManager.logger.info("API-SHORTSELL Fetch_Executions_OrderQty : ["+Fetch_Executions_OrderQty+"]");
				LoggingManager.logger.info("API-SHORTSELL Fetch_Executions_Price : ["+Fetch_Executions_Price+"]");
				LoggingManager.logger.info("API-SHORTSELL Fetch_Executions_OrderType : ["+Fetch_Executions_OrderType+"]");
				LoggingManager.logger.info("API-SHORTSELL Fetch_Executions_Destination : ["+Fetch_Executions_Destination+"]");
				LoggingManager.logger.info("API-SHORTSELL Fetch_Executions_SymbolSfx : ["+Fetch_Executions_SymbolSfx+"]");
				LoggingManager.logger.info("API-SHORTSELL Fetch_Executions_Status : ["+Fetch_Executions_Status+"]");
				LoggingManager.logger.info("API-SHORTSELL Fetch_Executions_OrdStatus : ["+Fetch_Executions_OrdStatus+"]");
				LoggingManager.logger.info("API-SHORTSELL Fetch_Executions_Text : ["+Fetch_Executions_Text+"]");
				LoggingManager.logger.info("API-SHORTSELL Fetch_Last_Executions_OrdStatus : ["+Fetch_Last_Executions_OrdStatus+"]");
				LoggingManager.logger.info("API-SHORTSELL Fetch_Executions_OriginatingUserDesc : ["+Fetch_Executions_OriginatingUserDesc+"]");
				LoggingManager.logger.info("API-SHORTSELL Fetch_Executions_ExecBroker : ["+Fetch_Executions_ExecBroker+"]");
				LoggingManager.logger.info("API-SHORTSELL Fetch_Executions_TimeInForce : ["+Fetch_Executions_TimeInForce+"]");
				LoggingManager.logger.info("API-SHORTSELL Fetch_Executions_ExecRefID : ["+Fetch_ExecutionS_ExecRefID+"]");
				LoggingManager.logger.info("API-SHORTSELL Fetch_Executions_ExecTransType : ["+Fetch_Executions_ExecTransType+"]");
				LoggingManager.logger.info("API-SHORTSELL Fetch_Executions_ExecTransTypeDesc : ["+Fetch_Executions_ExecTransTypeDesc+"]");


				loop: for(int position = ResponseArraySize-1; position >=0; position--)
				{
					String  response_symbolSfx = jsonresponse.getString(getResponseArray+"["+position+"].symbolSfx");
					String  response_symbol = jsonresponse.getString(getResponseArray+"["+position+"].symbol");
					String  response_account = jsonresponse.getString(getResponseArray+"["+position+"].account");
					Double  response_orderQty = jsonresponse.getDouble(getResponseArray+"["+position+"].orderQty");
					Double  response_leavesQty = jsonresponse.getDouble(getResponseArray+"["+position+"].leavesQty");
					Double response_lastShares = jsonresponse.getDouble(getResponseArray+"["+position+"].lastShares");
					Double  response_cumQty = jsonresponse.getDouble(getResponseArray+"["+position+"].cumQty");
					Double  response_price = jsonresponse.getDouble(getResponseArray+"["+position+"].price");
					String  response_ordType = jsonresponse.getString(getResponseArray+"["+position+"].ordType");
					String  response_side = jsonresponse.getString(getResponseArray+"["+position+"].side");
					String  response_destination = jsonresponse.getString(getResponseArray+"["+position+"].destination");
					Integer response_qOrderID = jsonresponse.getInt(getResponseArray+"["+position+"].qOrderID");
					String response_OrderID = jsonresponse.getString(getResponseArray+"["+position+"].orderId");
					Double response_lastPx = jsonresponse.getDouble(getResponseArray+"["+position+"].lastPx");
					String response_sideDesc = jsonresponse.getString(getResponseArray+"["+position+"].sideDesc");
					Double response_avgPx = jsonresponse.getDouble(getResponseArray+"["+position+"].avgPx");
					String response_transactTime = jsonresponse.getString(getResponseArray+"["+position+"].transactTime");
					String response_tradeDate = jsonresponse.getString(getResponseArray+"["+position+"].tradeDate");
					String response_transactTimeUtc = jsonresponse.getString(getResponseArray+"["+position+"].transactTimeUtc");
					String response_tradeDateUtc = jsonresponse.getString(getResponseArray+"["+position+"].tradeDateUtc");
					String response_status = jsonresponse.getString(getResponseArray+"["+position+"].status");
					String response_text = jsonresponse.getString(getResponseArray+"["+position+"].text");
					String response_ordStatus = jsonresponse.getString(getResponseArray+"["+position+"].ordStatus");
					String response_originatingUserDesc = jsonresponse.getString(getResponseArray+"["+position+"].originatingUserDesc");
					String response_clOrdID = jsonresponse.getString(getResponseArray+"["+position+"].clOrdID");
					String response_origClOrdID = jsonresponse.getString(getResponseArray+"["+position+"].origClOrdID");
					String response_execRefID = jsonresponse.getString(getResponseArray+"["+position+"].execRefID");
					String response_execID = jsonresponse.getString(getResponseArray+"["+position+"].execID");
					String response_execType = jsonresponse.getString(getResponseArray+"["+position+"].execType");
					String response_execTransType = jsonresponse.getString(getResponseArray+"["+position+"].execTransType");
					String response_execTransTypeDesc = jsonresponse.getString(getResponseArray+"["+position+"].execTransTypeDesc");
					String response_timeInForce = jsonresponse.getString(getResponseArray+"["+position+"].timeInForce");
					String response_execBroker = jsonresponse.getString(getResponseArray+"["+position+"].execBroker");
					if (response_OrderID.equalsIgnoreCase(OrderId)
							&& (response_transactTime.substring(0,10)).equalsIgnoreCase(localDateTime.format(formatter)))
					{
						getSellExecPrx.add(response_lastPx);
						getSellExecutionsAvgPrice.put(Integer.parseInt(response_execID), response_avgPx);
						getSellExecutionsLastPrice.put(Integer.parseInt(response_execID), response_lastPx);

						Assert.assertNotEquals(response_avgPx,null,"Validate_response_avgPx");
						Assert.assertNotEquals(response_lastPx,null,"Validate_response_lastPx");
						Assert.assertNotEquals(response_execID,null,"Validate_Executions_ShortSell_execID");
						Assert.assertNotEquals(response_execType,null,"Validate_Executions_ShortSell_execType");
						Assert.assertNotEquals(response_lastShares,null,"Validate_Executions_ShortSell_lastShares");
						//Assert.assertNotEquals(response_origClOrdID,null,"Validate_Executions_ShortSell_origClOrdID");
						Assert.assertEquals(response_origClOrdID,null,"Validate_Executions_ShortSell_origClOrdID");
						Assert.assertEquals(response_OrderID,OrderId,"Validate_ShortSell_OrderId");
						Assert.assertEquals(response_qOrderID,qOrderId,"Validate_ShortSell_qOrderId");
						Assert.assertEquals((response_transactTime.substring(0,10)),localDateTime.format(formatter),"Validate_response_transactTime");
						Assert.assertEquals((response_tradeDate.substring(0,10)),localDateTime.format(formatter),"Validate_response_tradeDate");
						Assert.assertEquals((response_transactTimeUtc.substring(0,10)),localDateTime.format(formatter),"Validate_response_transactTimeUTC");
						Assert.assertEquals((response_tradeDateUtc.substring(0,10)),localDateTime.format(formatter),"Validate_response_tradeDateUTC");
						Assert.assertEquals(response_symbol,Fetch_Executions_Symbol,"Validate_Executions_ShortSell_Symbol");
						Assert.assertEquals(NVL(response_symbolSfx,"null"),Fetch_Executions_SymbolSfx,"Validate_Executions_ShortSell_SymbolSfx");
						Assert.assertEquals(response_account,Fetch_Executions_Account,"Validate_Executions_ShortSell_Account");
						Assert.assertEquals(response_orderQty,Double.parseDouble(Fetch_Executions_OrderQty),"Validate_Executions_ShortSell_OrderQty");
						Assert.assertEquals(response_leavesQty,(response_orderQty-response_cumQty),"Validate_Executions_ShortSell_leavesQty");
						Assert.assertEquals(response_cumQty,(response_orderQty-response_leavesQty),"Validate_Executions_ShortSell_cumQty");
						Assert.assertEquals(response_price,Double.parseDouble(Fetch_Executions_Price),"Validate_Executions_ShortSell_Price");
						Assert.assertEquals(response_ordType,Fetch_Executions_OrderType,"Validate_Executions_ShortSell_OrderType");
						Assert.assertEquals(response_side,Executions_Side,"Validate_Executions_ShortSell_Side");
						Assert.assertEquals(response_destination,Fetch_Executions_Destination,"Validate_Executions_ShortSell_Destination");
						Assert.assertEquals(response_sideDesc,Executions_SideDesc,"Validate_Executions_ShortSell_sideDesc");
						Assert.assertEquals(NVL(response_status,"null"),Fetch_Executions_Status,"Validate_Executions_ShortSell_Status");
						Assert.assertEquals(NVL(response_text,"null"),Fetch_Executions_Text,"Validate_Executions_ShortSell_Text");

						if ((response_leavesQty.toString()).equalsIgnoreCase("0.0") && (response_orderQty.toString()).equalsIgnoreCase(response_cumQty.toString()))
						{Assert.assertEquals(response_ordStatus,Fetch_Last_Executions_OrdStatus,"Validate_Last_Executions_ShortSell_OrdStatus");}
						else {Assert.assertEquals(response_ordStatus,Fetch_Executions_OrdStatus,"Validate_Executions_ShortSell_OrdStatus");}
						Assert.assertEquals(response_originatingUserDesc,Fetch_Executions_OriginatingUserDesc,"Validate_Executions_ShortSell_OriginatingUserDesc");
						Assert.assertEquals(response_clOrdID,BoothID+"-"+(qOrderId-1)+"-"+1,"Validate_Executions_ShortSell_clOrdID");
						Assert.assertEquals(response_execRefID,Fetch_ExecutionS_ExecRefID,"Validate_Executions_ShortSell_ExecRefID");
						Assert.assertEquals(response_execTransType,Fetch_Executions_ExecTransType,"Validate_Executions_ShortSell_ExecTransType");
						Assert.assertEquals(response_execTransTypeDesc,Fetch_Executions_ExecTransTypeDesc,"Validate_Executions_ShortSell_ExecTransTypeDesc");
						Assert.assertEquals(response_timeInForce,Fetch_Executions_TimeInForce,"Validate_Executions_ShortSell_TimeInForce");
						Assert.assertEquals(NVL(response_execBroker,"null"),Fetch_Executions_ExecBroker,"Validate_Executions_ShortSell_ExecBroker");
					}
					else
					{
						continue loop;
					}
				}

				if (CaseType.equalsIgnoreCase("position"))
				{
					LoggingManager.logger.info("API-Total SHORT Realize PNL : ["+Global.getSHORTrealizedPnL+"]");

					if(getSellExecPrx.isEmpty()==false)
					{
						LoggingManager.logger.info("Price List for LastPrx Calculations:"+getSellExecPrx);
						for (int lastPrice=0;lastPrice<=getSellExecPrx.size()-1; lastPrice++)
						{
							LoggingManager.logger.info("Add Execution Prx = "+Global.getAvgPrice +" + "+getSellExecPrx.get(lastPrice));
							Global.getAvgPrice+= getSellExecPrx.get(lastPrice);
							LoggingManager.logger.info("= "+Global.getAvgPrice);

						}
						LoggingManager.logger.info("Total Prx counts :"+getSellExecPrx.size());
						LoggingManager.logger.info("Total Executions Prx Added :"+Global.getAvgPrice);
						Global.totalTrade=Global.getAvgPrice;
						Global.getAvgPrice/=getSellExecPrx.size();
						if(Global.getorderType=="Equity") {LoggingManager.logger.info("Total Order Avg. Price :"+decimalFormat.format(Global.getAvgPrice));}
						Global.getOptionAvgPrice=Global.getAvgPrice;
						if(Global.getorderType=="Option") {LoggingManager.logger.info("Total OptionOrder Avg. Price :"+decimalFormat.format(Global.getOptionAvgPrice));}
					}
					else
					{
						LoggingManager.logger.info("API-No Executions Found Against OrderID : ["+OrderId+"]");
						if(Global.getorderType=="Equity") {LoggingManager.logger.info("Total Order Avg. Price :"+decimalFormat.format(Global.getAvgPrice));}
						Global.getOptionAvgPrice=Global.getAvgPrice;
						if(Global.getorderType=="Option") {LoggingManager.logger.info("Total OptionOrder Avg. Price :"+decimalFormat.format(Global.getOptionAvgPrice));}
					}
				}
				else
				{
					LoggingManager.logger.info("API-Executions Sell ExecutionsLastPrice   : ["+getSellExecutionsLastPrice+"]");
					LoggingManager.logger.info("API-Executions Sell ExecutionsAvgPrice    : ["+getSellExecutionsAvgPrice+"]");
				}
				break;

			default:
				break;
		}
	}
	catch (Exception e)
	{
		LoggingManager.logger.error(e);
	}
  }

public static void Validate_Subscribe_Locates( Response response,
											   String Summary_Locate_Subscribe_BasePath,
											   String Content_Type,
											   String Summary_Locate_Subscribe_StatusCode,
											   String Validate_SummaryID,
											   String Validate_SummaryBooth,
											   String Validate_OrdType,
											   String Validate_OrdStatus,
											   String Validate_OrderQty,
											   String Validate_OfferPx,
											   String Validate_OfferSize,
											   String Validate_CumQty,
											   String Validate_AvgPx,
											   String Validate_StatusDesc,
											   String Validate_Status,
											   String Validate_OrdRejReason,
											   String Validate_TransactionStatusString,
											   String Validate_TransactionStatus,
											   String Validate_TimeInForce,
											   String Validate_Text,
											   String Validate_Id,
											   String Validate_Symbol,
											   String Validate_SymbolSfx,
											   String Validate_ClientID,
											   String Validate_LocateType,
											   String Validate_Booth,
											   String Validate_Account,
											   String Validate_OriginatingUserDesc,
											   String Validate_Flag)
	{
		try
		{
			ArrayList<String> LocateQuoteReqID = new ArrayList<String>();
			String  getSummaryID="", getSummaryBooth="",getSummaryEtbQty="",expectedEtbQty="";
			JsonPath jsonresponse = new JsonPath(response.getBody().asString());
			int ResponseArraySize = jsonresponse.getInt("eventData.size()");
			for(int position = ResponseArraySize-1; position >=0; position--)
			{

				Global.getLocateQuoteReqID = jsonresponse.getString("eventData["+position+"].quoteReqID");
				Global.getLocateOriginatingUserDesc = jsonresponse.getString("eventData["+position+"].originatingUserDesc");
				Global.getLocateStatus = jsonresponse.getString("eventData["+position+"].status");
				Global.getLocateSymbol = jsonresponse.getString("eventData["+position+"].symbol");
				Global.getLocateAccount = jsonresponse.getString("eventData["+position+"].account");
				Global.getLocateBoothID = jsonresponse.getString("eventData["+position+"].boothID");
				if(	Global.getLocateOriginatingUserDesc.equalsIgnoreCase(Validate_OriginatingUserDesc)
						&& Global.getLocateStatus.equalsIgnoreCase(Validate_Status)
						&& Global.getLocateSymbol.equalsIgnoreCase(Validate_Symbol)
						&& Global.getLocateAccount.equalsIgnoreCase(Validate_Account)
						&& Global.getLocateBoothID.equalsIgnoreCase(Validate_Booth))
				{
					LocateQuoteReqID.add(Global.getLocateQuoteReqID);
				}
				else
				{ }
			}
			//Collections.sort(LocateQuoteReqID);
			LocateQuoteReqID.sort(Comparator.<String, Character>comparing(s -> s.charAt(0)).thenComparingInt(s -> Integer.parseInt(s.substring(1))));
			LoggingManager.logger.info("API-QuotesReID After Sort : ["+LocateQuoteReqID+"]");
			if (LocateQuoteReqID.isEmpty()==false)
			{
				Global.getLocateQuoteReqID=LocateQuoteReqID.get(LocateQuoteReqID.size() - 1);
				LoggingManager.logger.info("API- Picked latest QuotesReID : [" + Global.getLocateQuoteReqID+ "]");
			}
			else
			{
				LoggingManager.logger.info("API- QuotesReID Not Found : [" + LocateQuoteReqID+ "]");
			}

			for(int position = ResponseArraySize-1; position >=0; position--)
			{
				Global.getLocateId = jsonresponse.getString("eventData["+position+"].id");
				if((Validate_Id+Global.getLocateQuoteReqID).equalsIgnoreCase(Global.getLocateId))
				{
					Global.ValidationFlag=true;
					Global.getLocateOriginatingUserDesc = jsonresponse.getString("eventData["+position+"].originatingUserDesc");
					Global.getLocateOrderQty = jsonresponse.getInt("eventData["+position+"].orderQty");
					Global.getLocateOfferSize = jsonresponse.getInt("eventData["+position+"].offerSize");
					Global.getLocateCumQty = jsonresponse.getInt("eventData["+position+"].cumQty");
					Global.getLocateStatus = jsonresponse.getString("eventData["+position+"].status");
					Global.getLocateStatusDesc = jsonresponse.getString("eventData["+position+"].statusDesc");
					Global.getLocateSymbol = jsonresponse.getString("eventData["+position+"].symbol");
					Global.getLocateSymbolSfx = jsonresponse.getString("eventData["+position+"].symbolSfx");
					Global.getLocateClientID = jsonresponse.getString("eventData["+position+"].clientID");
					Global.getLocateAccount = jsonresponse.getString("eventData["+position+"].account");
					Global.getLocateBoothID = jsonresponse.getString("eventData["+position+"].boothID");
					Global.getLocateOrdType = jsonresponse.getString("eventData["+position+"].ordType");
					Global.getLocateOrdStatus = jsonresponse.getString("eventData["+position+"].ordStatus");
					Global.getLocateOfferPx = jsonresponse.getDouble("eventData["+position+"].offerPx");
					Global.getLocateAvgPx = jsonresponse.getDouble("eventData["+position+"].avgPx");
					Global.getLocateOrdRejReason = jsonresponse.getString("eventData["+position+"].ordRejReason");
					Global.getLocateTransactionStatusString = jsonresponse.getString("eventData["+position+"].transactionStatusString");
					Global.getLocateTransactionStatus = jsonresponse.getString("eventData["+position+"].transactionStatus");
					Global.getLocateTimeInForce = jsonresponse.getString("eventData["+position+"].timeInForce");
					Global.getLocateText = jsonresponse.getString("eventData["+position+"].text");
					Global.getLocateTransactTimeUtc = jsonresponse.getString("eventData["+position+"].transactTimeUtc");
					Global.getLocateTransactTime = jsonresponse.getString("eventData["+position+"].transactTime");
					Global.getLocateLocateType = jsonresponse.getString("eventData["+position+"].locateType");
					Global.getLocateEtbQty = jsonresponse.getInt("eventData["+position+"].etbQty");
					break;
				}
				else
				{
					Global.ValidationFlag=false;
					continue;
				}
			}

			Response Summary_response=
						given()
								.header("Content-Type",Content_Type)
								.header("Authorization", "Bearer " + Global.getAccToken)

								.when()
								.get(Summary_Locate_Subscribe_BasePath)

								.then()
								.extract().response();

			LoggingManager.logger.info("API-BasePath : ["+Summary_Locate_Subscribe_BasePath+"]");
			LoggingManager.logger.info("API-Content_Type : ["+Content_Type+"]");
			LoggingManager.logger.info("API-Summary_Locates_Subscribe_StatusCode : ["+Summary_response.getStatusCode()+"]");
			Assert.assertEquals(Summary_response.statusCode(),Integer.parseInt(Summary_Locate_Subscribe_StatusCode), "Verify_Summary_Locate_Subscribe_StatusCode");
			getSummaryID=com.jayway.jsonpath.JsonPath.read(Summary_response.getBody().asString(), "$.eventData[?(@.id =='"+Validate_SummaryID+"' )].id").toString();
			getSummaryBooth=com.jayway.jsonpath.JsonPath.read(Summary_response.getBody().asString(), "$.eventData[?(@.id =='"+Validate_SummaryID+"' )].boothID").toString();
			getSummaryEtbQty=com.jayway.jsonpath.JsonPath.read(Summary_response.getBody().asString(), "$.eventData[?(@.id =='"+Validate_SummaryID+"' )].etbQty").toString();
			LoggingManager.logger.info("API-Validate_SummaryID : [\""+Validate_SummaryID +"\"] - Response Validate_SummaryID : "+getSummaryID);
			LoggingManager.logger.info("API-Validate_SummaryBooth : [\""+Validate_SummaryBooth +"\"] - Response Validate_SummaryBooth : "+getSummaryBooth);
			Assert.assertEquals(getSummaryID,APIHelperClass.ValidationNullValue(Validate_SummaryID),"Validate_Summary_Locate_Subscribe_ID");
			Assert.assertEquals(getSummaryBooth,APIHelperClass.ValidationNullValue(Validate_SummaryBooth), "Validate_Summary_Locate_Subscribe_Booth");
			Global.getLocateEtbQty = Integer.parseInt(getSummaryEtbQty.substring(1, getSummaryEtbQty.length() - 1));
			//expectedEtbQty= getSummaryEtbQty;
			LoggingManager.logger.info("API-Validate_Summary EtbQty: ["+Global.getLocateEtbQty+"]");
			//LoggingManager.logger.info("API-Validate_SummaryEtbQty: ["+Global.getLocateEtbQty +"] - Response Validate_SummaryEtbQty : "+getSummaryEtbQty);
			//Assert.assertEquals(getSummaryEtbQty,"["+Global.getLocateEtbQty +"]","Validate_Summary_Locate_EtbQty");

			LoggingManager.logger.info("API-Validate_LocateFlag : ["+Boolean.parseBoolean(Validate_Flag) +"] - Response Validate_LocateFlag : "+Global.ValidationFlag);
			Assert.assertEquals(Global.ValidationFlag,Boolean.parseBoolean(Validate_Flag),"Validate_Flag");
			LoggingManager.logger.info("API-Validate_LocateId : ["+(Validate_Id+Global.getLocateQuoteReqID)+"] - Response_Locate_Id : "+Global.getLocateId);
			LoggingManager.logger.info("API-Validate_LocateOriginatingUserDesc : ["+Validate_OriginatingUserDesc +"] - Response_LocateOriginatingUserDesc : "+Global.getLocateOriginatingUserDesc);
			LoggingManager.logger.info("API-Validate_LocateStatus : ["+Validate_Status +"] - Response Validate_LocateStatus : "+Global.getLocateStatus);
			LoggingManager.logger.info("API-Validate_LocateStatusDesc : ["+Validate_StatusDesc +"] - Response Validate_LocateStatusDesc : "+Global.getLocateStatusDesc);
			LoggingManager.logger.info("API-Validate_LocateOrderQty : ["+Validate_OrderQty +"] - Response Validate_LocateOrderQty : "+Global.getLocateOrderQty);
			LoggingManager.logger.info("API-Validate_LocateCumQty : ["+Validate_CumQty +"] - Response Validate_CumQty : "+Global.getLocateCumQty);
			LoggingManager.logger.info("API-Validate_LocateOfferSize : ["+Validate_OfferSize +"] - Response Validate_LocateOfferSize : "+Global.getLocateOfferSize);
			LoggingManager.logger.info("API-Validate_LocateSymbol : ["+Validate_Symbol +"] - Response Validate_LocateSymbol : "+Global.getLocateSymbol);
			LoggingManager.logger.info("API-Validate_LocateSymbolSfx : ["+Validate_SymbolSfx +"] - Response Validate_LocateSymbolSfx : "+Global.getLocateSymbolSfx);
			LoggingManager.logger.info("API-Validate_LocateClientID : ["+Validate_ClientID +"] - Response Validate_LocateClientID : "+Global.getLocateClientID);
			LoggingManager.logger.info("API-Validate_LocateAccount : ["+Validate_Account +"] - Response Validate_LocateAccount : "+Global.getLocateAccount);
			LoggingManager.logger.info("API-Validate_LocateBooth : ["+Validate_Booth +"] - Response Validate_LocateBooth : "+Global.getLocateBoothID);
			LoggingManager.logger.info("API-Validate_LocateOrdType : ["+Validate_OrdType +"] - Response Validate_LocateOrdType : "+Global.getLocateOrdType);
			LoggingManager.logger.info("API-Validate_LocateOrdStatus : ["+Validate_OrdStatus +"] - Response Validate_LocateOrdStatus : "+Global.getLocateOrdStatus);
			LoggingManager.logger.info("API-Validate_LocateOfferPx : ["+Double.parseDouble(Validate_OfferPx) +"] - Response Validate_LocateOfferPx : "+Global.getLocateOfferPx);
			LoggingManager.logger.info("API-Validate_LocateAvgPx : ["+Double.parseDouble(Validate_AvgPx) +"] - Response Validate_LocateAvgPx : "+Global.getLocateAvgPx);
			LoggingManager.logger.info("API-Validate_LocateOrdRejReason : ["+Validate_OrdRejReason +"] - Response Validate_LocateOrdRejReason : "+Global.getLocateOrdRejReason);
			LoggingManager.logger.info("API-Validate_LocateTransactionStatusString : ["+Validate_TransactionStatusString +"] - Response Validate_LocateTransactionStatusString : "+Global.getLocateTransactionStatusString);
			LoggingManager.logger.info("API-Validate_LocateTransactionStatus : ["+Validate_TransactionStatus +"] - Response Validate_LocateTransactionStatus : "+Global.getLocateTransactionStatus);
			LoggingManager.logger.info("API-Validate_LocateTimeInForce : ["+Validate_TimeInForce +"] - Response Validate_LocateTimeInForce : "+Global.getLocateTimeInForce);
			//LoggingManager.logger.info("API-Validate_LocateLocateEtbQty : ["+Integer.parseInt(expectedEtbQty)+"] - Response Validate_LocateEtbQty : "+Global.getLocateEtbQty);

			Assert.assertEquals(Global.getLocateId,(Validate_Id+Global.getLocateQuoteReqID),"Validate_Id");
			Assert.assertEquals(Global.getLocateOriginatingUserDesc,Validate_OriginatingUserDesc,"Validate_OriginatingUserDesc");
			Assert.assertEquals(Global.getLocateStatus,Validate_Status,"Validate_Status");
			Assert.assertEquals(Global.getLocateStatusDesc,Validate_StatusDesc,"Validate_StatusDesc");
			Assert.assertEquals(Global.getLocateCumQty,Integer.parseInt(Validate_CumQty),"Validate_CumQty");
			Assert.assertEquals(Global.getLocateOfferSize,Integer.parseInt(Validate_OfferSize),"Validate_OfferSize");
			Assert.assertEquals(Global.getLocateOrderQty,Integer.parseInt(Validate_OrderQty),"Validate_OrderQty");
			Assert.assertEquals(Global.getLocateSymbol,Validate_Symbol,"Validate_Symbol");
			Assert.assertEquals(APIHelperClass.NVL(Global.getLocateSymbolSfx,"null"),Validate_SymbolSfx,"Validate_SymbolSfx");
			Assert.assertEquals(Global.getLocateClientID,Validate_ClientID,"Validate_clientID");
			Assert.assertEquals(Global.getLocateAccount,Validate_Account,"Validate_Account");
			Assert.assertEquals(Global.getLocateBoothID,Validate_Booth,"Validate_Booth");
			Assert.assertEquals(APIHelperClass.NVL(Global.getLocateOrdType,"null"),Validate_OrdType,"Validate_LocateOrdType");
			Assert.assertEquals(APIHelperClass.NVL(Global.getLocateOrdStatus,"null"),Validate_OrdStatus,"Validate_LocateOrdStatus");
			Assert.assertEquals(Global.getLocateOfferPx,Double.parseDouble(Validate_OfferPx),"Validate_LocateOfferPx");
			Assert.assertEquals(Global.getLocateAvgPx,Double.parseDouble(Validate_AvgPx),"Validate_LocateAvgPx");
			Assert.assertEquals(Global.getLocateOrdRejReason,Validate_OrdRejReason,"Validate_LocateOrdRejReason");
			Assert.assertEquals(Global.getLocateTransactionStatusString,Validate_TransactionStatusString,"Validate_LocateTransactionStatusString");
			Assert.assertEquals(Global.getLocateTransactionStatus,Validate_TransactionStatus,"Validate_LocateTransactionStatus");
			Assert.assertEquals(APIHelperClass.NVL(Global.getLocateTimeInForce,"null"),Validate_TimeInForce,"Validate_LocateTimeInForce");
			Assert.assertEquals(APIHelperClass.NVL(Global.getLocateText,"null"),Validate_Text,"Validate_Text");
			Assert.assertEquals(APIHelperClass.NVL(Global.getLocateLocateType,"null"),Validate_LocateType,"Validate_LocateLocateType");
		//	Assert.assertEquals(Global.getLocateEtbQty,Integer.parseInt(expectedEtbQty),"Validate_LocateETBQty");

		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}


	public static void Validate_Acquired_Subscribe_Locates(Response response,
														   String Validate_Acquired_quoteReqID,
														   String Validate_Acquired_OrdType,
														   String Validate_Acquired_OrdStatus,
														   String Validate_Acquired_OrderQty,
														   String Validate_Acquired_OfferPx,
														   String Validate_Acquired_OfferSize,
														   String Validate_Acquired_CumQty,
														   String Validate_Acquired_AvgPx,
														   String Validate_Acquired_StatusDesc,
														   String Validate_Acquired_Status,
														   String Validate_Acquired_OrdRejReason,
														   String Validate_Acquired_TransactionStatusString,
														   String Validate_Acquired_TransactionStatus,
														   String Validate_Acquired_TimeInForce,
														   String Validate_Acquired_Text,
														   String Validate_Acquired_Id,
														   String Validate_Acquired_Symbol,
														   String Validate_Acquired_SymbolSfx,
														   String Validate_Acquired_ClientID,
														   String Validate_Acquired_LocateType,
														   String Validate_Acquired_Booth,
														   String Validate_Acquired_Account,
														   String Validate_Acquired_OriginatingUserDesc,
														   Integer Validate_Acquired_EtbQty)
	{
		try
		{
			JsonPath jsonresponse = new JsonPath(response.getBody().asString());
			int ResponseArraySize = jsonresponse.getInt("eventData.size()");
			for(int position = ResponseArraySize-1; position >=0; position--)
			{
				Global.getLocateQuoteReqID = jsonresponse.getString("eventData["+position+"].quoteReqID");
				Global.getLocateId = jsonresponse.getString("eventData["+position+"].id");
				Global.getLocateOriginatingUserDesc = jsonresponse.getString("eventData["+position+"].originatingUserDesc");
				Global.getLocateOrderQty = jsonresponse.getInt("eventData["+position+"].orderQty");
				Global.getLocateOfferSize = jsonresponse.getInt("eventData["+position+"].offerSize");
				Global.getLocateCumQty = jsonresponse.getInt("eventData["+position+"].cumQty");
				Global.getLocateStatus = jsonresponse.getString("eventData["+position+"].status");
				Global.getLocateStatusDesc = jsonresponse.getString("eventData["+position+"].statusDesc");
				Global.getLocateSymbol = jsonresponse.getString("eventData["+position+"].symbol");
				Global.getLocateSymbolSfx = jsonresponse.getString("eventData["+position+"].symbolSfx");
				Global.getLocateClientID = jsonresponse.getString("eventData["+position+"].clientID");
				Global.getLocateAccount = jsonresponse.getString("eventData["+position+"].account");
				Global.getLocateBoothID = jsonresponse.getString("eventData["+position+"].boothID");
				Global.getLocateOrdType = jsonresponse.getString("eventData["+position+"].ordType");
				Global.getLocateOrdStatus = jsonresponse.getString("eventData["+position+"].ordStatus");
				Global.getLocateOfferPx = jsonresponse.getDouble("eventData["+position+"].offerPx");
				Global.getLocateAvgPx = jsonresponse.getDouble("eventData["+position+"].avgPx");
				Global.getLocateOrdRejReason = jsonresponse.getString("eventData["+position+"].ordRejReason");
				Global.getLocateTransactionStatusString = jsonresponse.getString("eventData["+position+"].transactionStatusString");
				Global.getLocateTransactionStatus = jsonresponse.getString("eventData["+position+"].transactionStatus");
				Global.getLocateTimeInForce = jsonresponse.getString("eventData["+position+"].timeInForce");
				Global.getLocateText = jsonresponse.getString("eventData["+position+"].text");
				Global.getLocateTransactTimeUtc = jsonresponse.getString("eventData["+position+"].transactTimeUtc");
				Global.getLocateTransactTime = jsonresponse.getString("eventData["+position+"].transactTime");
				Global.getLocateLocateType = jsonresponse.getString("eventData["+position+"].locateType");
				Global.getLocateEtbQty = jsonresponse.getInt("eventData["+position+"].etbQty");

				if(	Global.getLocateQuoteReqID.equalsIgnoreCase(Validate_Acquired_quoteReqID))
				{
					LoggingManager.logger.info("API-Validate_Acquired_LocateId : ["+(Validate_Acquired_Id+Validate_Acquired_quoteReqID)+"] - Response_Locate_Id : "+Global.getLocateId);
					LoggingManager.logger.info("API-Validate_Acquired_LocateOriginatingUserDesc : ["+Validate_Acquired_OriginatingUserDesc +"] - Response_LocateOriginatingUserDesc : "+Global.getLocateOriginatingUserDesc);
					LoggingManager.logger.info("API-Validate_Acquired_LocateStatus : ["+Validate_Acquired_Status +"] - Response Validate_Acquired_LocateStatus : "+Global.getLocateStatus);
					LoggingManager.logger.info("API-Validate_Acquired_LocateStatusDesc : ["+Validate_Acquired_StatusDesc +"] - Response Validate_Acquired_LocateStatusDesc : "+Global.getLocateStatusDesc);
					LoggingManager.logger.info("API-Validate_Acquired_LocateOrderQty : ["+Validate_Acquired_OrderQty +"] - Response Validate_Acquired_LocateOrderQty : "+Global.getLocateOrderQty);
					LoggingManager.logger.info("API-Validate_Acquired_LocateCumQty : ["+Validate_Acquired_CumQty +"] - Response Validate_Acquired_CumQty : "+Global.getLocateCumQty);
					LoggingManager.logger.info("API-Validate_Acquired_LocateOfferSize : ["+Validate_Acquired_OfferSize +"] - Response Validate_Acquired_LocateOfferSize : "+Global.getLocateOfferSize);
					LoggingManager.logger.info("API-Validate_Acquired_LocateSymbol : ["+Validate_Acquired_Symbol +"] - Response Validate_Acquired_LocateSymbol : "+Global.getLocateSymbol);
					LoggingManager.logger.info("API-Validate_Acquired_LocateSymbolSfx : ["+Validate_Acquired_SymbolSfx +"] - Response Validate_Acquired_LocateSymbolSfx : "+Global.getLocateSymbolSfx);
					LoggingManager.logger.info("API-Validate_Acquired_LocateClientID : ["+Validate_Acquired_ClientID +"] - Response Validate_Acquired_LocateClientID : "+Global.getLocateClientID);
					LoggingManager.logger.info("API-Validate_Acquired_LocateAccount : ["+Validate_Acquired_Account +"] - Response Validate_Acquired_LocateAccount : "+Global.getLocateAccount);
					LoggingManager.logger.info("API-Validate_Acquired_LocateBooth : ["+Validate_Acquired_Booth +"] - Response Validate_Acquired_LocateBooth : "+Global.getLocateBoothID);
					LoggingManager.logger.info("API-Validate_Acquired_LocateOrdType : ["+Validate_Acquired_OrdType +"] - Response Validate_Acquired_LocateOrdType : "+Global.getLocateOrdType);
					LoggingManager.logger.info("API-Validate_Acquired_LocateOrdStatus : ["+Validate_Acquired_OrdStatus +"] - Response Validate_Acquired_LocateOrdStatus : "+Global.getLocateOrdStatus);
					LoggingManager.logger.info("API-Validate_Acquired_LocateOfferPx : ["+Double.parseDouble(Validate_Acquired_OfferPx) +"] - Response Validate_Acquired_LocateOfferPx : "+Global.getLocateOfferPx);
					LoggingManager.logger.info("API-Validate_Acquired_LocateAvgPx : ["+Double.parseDouble(Validate_Acquired_AvgPx) +"] - Response Validate_Acquired_LocateAvgPx : "+Global.getLocateAvgPx);
					LoggingManager.logger.info("API-Validate_Acquired_LocateOrdRejReason : ["+Validate_Acquired_OrdRejReason +"] - Response Validate_Acquired_LocateOrdRejReason : "+Global.getLocateOrdRejReason);
					LoggingManager.logger.info("API-Validate_Acquired_LocateTransactionStatusString : ["+Validate_Acquired_TransactionStatusString +"] - Response Validate_Acquired_LocateTransactionStatusString : "+Global.getLocateTransactionStatusString);
					LoggingManager.logger.info("API-Validate_Acquired_LocateTransactionStatus : ["+Validate_Acquired_TransactionStatus +"] - Response Validate_Acquired_LocateTransactionStatus : "+Global.getLocateTransactionStatus);
					LoggingManager.logger.info("API-Validate_Acquired_LocateTimeInForce : ["+Validate_Acquired_TimeInForce +"] - Response Validate_Acquired_LocateTimeInForce : "+Global.getLocateTimeInForce);
					LoggingManager.logger.info("API-Validate_Acquired_Text : ["+Validate_Acquired_Text +"] - Response Validate_Acquired_Text : "+Global.getLocateText);
					LoggingManager.logger.info("API-Validate_Acquired_LocateLocateEtbQty : ["+Validate_Acquired_EtbQty+"] - Response Validate_Acquired_LocateEtbQty : "+Global.getLocateEtbQty);

					Assert.assertEquals(Global.getLocateId,(Validate_Acquired_Id+Global.getLocateQuoteReqID),"Validate_Acquired_Id");
					Assert.assertEquals(Global.getLocateOriginatingUserDesc,Validate_Acquired_OriginatingUserDesc,"Validate_Acquired_OriginatingUserDesc");
					Assert.assertEquals(Global.getLocateStatus,Validate_Acquired_Status,"Validate_Acquired_Status");
					Assert.assertEquals(Global.getLocateStatusDesc,Validate_Acquired_StatusDesc,"Validate_Acquired_StatusDesc");
					Assert.assertEquals(Global.getLocateCumQty,Integer.parseInt(Validate_Acquired_CumQty),"Validate_Acquired_CumQty");
					Assert.assertEquals(Global.getLocateOfferSize,Integer.parseInt(Validate_Acquired_OfferSize),"Validate_Acquired_OfferSize");
					Assert.assertEquals(Global.getLocateOrderQty,Integer.parseInt(Validate_Acquired_OrderQty),"Validate_Acquired_OrderQty");
					Assert.assertEquals(Global.getLocateSymbol,Validate_Acquired_Symbol,"Validate_Acquired_Symbol");
					Assert.assertEquals(APIHelperClass.NVL(Global.getLocateSymbolSfx,"null"),Validate_Acquired_SymbolSfx,"Validate_Acquired_SymbolSfx");
					Assert.assertEquals(Global.getLocateClientID,Validate_Acquired_ClientID,"Validate_Acquired_clientID");
					Assert.assertEquals(Global.getLocateAccount,Validate_Acquired_Account,"Validate_Acquired_Account");
					Assert.assertEquals(Global.getLocateBoothID,Validate_Acquired_Booth,"Validate_Acquired_Booth");
					Assert.assertEquals(APIHelperClass.NVL(Global.getLocateOrdType,"null"),Validate_Acquired_OrdType,"Validate_Acquired_LocateOrdType");
					Assert.assertEquals(APIHelperClass.NVL(Global.getLocateOrdStatus,"null"),Validate_Acquired_OrdStatus,"Validate_Acquired_LocateOrdStatus");
					Assert.assertEquals(Global.getLocateOfferPx,Double.parseDouble(Validate_Acquired_OfferPx),"Validate_Acquired_LocateOfferPx");
					Assert.assertEquals(Global.getLocateAvgPx,Double.parseDouble(Validate_Acquired_AvgPx),"Validate_Acquired_LocateAvgPx");
					Assert.assertEquals(Global.getLocateOrdRejReason,Validate_Acquired_OrdRejReason,"Validate_Acquired_LocateOrdRejReason");
					Assert.assertEquals(Global.getLocateTransactionStatusString,Validate_Acquired_TransactionStatusString,"Validate_Acquired_LocateTransactionStatusString");
					Assert.assertEquals(Global.getLocateTransactionStatus,Validate_Acquired_TransactionStatus,"Validate_Acquired_LocateTransactionStatus");
					Assert.assertEquals(APIHelperClass.NVL(Global.getLocateTimeInForce,"null"),Validate_Acquired_TimeInForce,"Validate_Acquired_LocateTimeInForce");
					Assert.assertEquals(APIHelperClass.NVL(Global.getLocateText,"null"),Validate_Acquired_Text,"Validate_Acquired_Text");
					Assert.assertEquals(APIHelperClass.NVL(Global.getLocateLocateType,"null"),Validate_Acquired_LocateType,"Validate_Acquired_LocateLocateType");
					Assert.assertEquals(Global.getLocateEtbQty,Validate_Acquired_EtbQty,"Validate_Acquired_LocateETBQty");
					break;
				}
				else
				{
					continue;
				}
			}
		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}


public static void Validate_Summary_Subscribe_Locates(Response Summary_response,
													 String Validate_SummaryID,
													 String Validate_SummaryOriginatingUserDesc,
													 String Validate_SummaryClientID,
													 String Validate_SummaryLocateType,
													 String Validate_SummarySymbol,
													 String Validate_SummarySymbolSfx,
													 String Validate_SummaryAccount,
													 Integer Validate_SummaryAcqEtbQty,
													 String Validate_SummaryBooth)
{
	try
	{
		String getSummaryID=com.jayway.jsonpath.JsonPath.read(Summary_response.getBody().asString(), "$.eventData[?(@.id =='"+Validate_SummaryID+"' )].id").toString();
		String getSummarySymbol=com.jayway.jsonpath.JsonPath.read(Summary_response.getBody().asString(), "$.eventData[?(@.id =='"+Validate_SummaryID+"' )].symbol").toString();
		String getSummaryAccount=com.jayway.jsonpath.JsonPath.read(Summary_response.getBody().asString(), "$.eventData[?(@.id =='"+Validate_SummaryID+"' )].account").toString();
		String getSummarySymbolSfx=com.jayway.jsonpath.JsonPath.read(Summary_response.getBody().asString(), "$.eventData[?(@.id =='"+Validate_SummaryID+"' )].symbolSfx").toString();
		String getSummaryClientID=com.jayway.jsonpath.JsonPath.read(Summary_response.getBody().asString(), "$.eventData[?(@.id =='"+Validate_SummaryID+"' )].clientID").toString();
		String getSummaryLocateType=com.jayway.jsonpath.JsonPath.read(Summary_response.getBody().asString(), "$.eventData[?(@.id =='"+Validate_SummaryID+"' )].locateType").toString();
		String getSummaryBooth=com.jayway.jsonpath.JsonPath.read(Summary_response.getBody().asString(), "$.eventData[?(@.id =='"+Validate_SummaryID+"' )].boothID").toString();
		String getSummaryOriginatingUserDesc=com.jayway.jsonpath.JsonPath.read(Summary_response.getBody().asString(), "$.eventData[?(@.id =='"+Validate_SummaryID+"' )].originatingUserDesc").toString();
		String getSummaryEtbQty=com.jayway.jsonpath.JsonPath.read(Summary_response.getBody().asString(), "$.eventData[?(@.id =='"+Validate_SummaryID+"' )].etbQty").toString();

		LoggingManager.logger.info("API-Validate_SummaryID : [\""+Validate_SummaryID +"\"] - Response Validate_SummaryID : "+getSummaryID);
		LoggingManager.logger.info("API-Validate_SummarySymbol : [\""+Validate_SummarySymbol +"\"] - Response Validate_SummarySymbol : "+getSummarySymbol);
		LoggingManager.logger.info("API-Validate_SummaryAccount : [\""+Validate_SummaryAccount +"\"] - Response Validate_SummaryAccount : "+getSummaryAccount);
		LoggingManager.logger.info("API-Validate_SummarySymbolSfx : [\""+Validate_SummarySymbolSfx +"\"] - Response Validate_SummarySymbolSfx : "+getSummarySymbolSfx);
		LoggingManager.logger.info("API-Validate_SummaryClientID : [\""+Validate_SummaryClientID +"\"] - Response Validate_SummaryClientID : "+getSummaryClientID);
		LoggingManager.logger.info("API-Validate_SummaryLocateType : [\""+Validate_SummaryLocateType +"\"] - Response Validate_SummaryLocateType : "+getSummaryLocateType);
		LoggingManager.logger.info("API-Validate_SummaryBooth : [\""+Validate_SummaryBooth +"\"] - Response Validate_SummaryBooth : "+getSummaryBooth);
		LoggingManager.logger.info("API-Response Validate_Summary_OriginatingUserDesc : "+getSummaryOriginatingUserDesc);
		LoggingManager.logger.info("API-Validate_SummaryEtbQty : ["+Validate_SummaryAcqEtbQty+"] - Response Validate_SummaryEtbQty : "+getSummaryEtbQty);

		Assert.assertEquals(getSummaryID,APIHelperClass.ValidationNullValue(Validate_SummaryID),"Validate_Summary_Locate_Subscribe_ID");
		Assert.assertEquals(getSummarySymbol,APIHelperClass.ValidationNullValue(Validate_SummarySymbol),"Validate_Summary_Locate_Subscribe_Symbol");
		Assert.assertEquals(getSummaryAccount,APIHelperClass.ValidationNullValue(Validate_SummaryAccount),"Validate_Summary_Locate_Subscribe_Account");
		Assert.assertEquals(getSummarySymbolSfx,APIHelperClass.ValidationNullValue(Validate_SummarySymbolSfx),"Validate_Summary_Locate_Subscribe_SymbolSfx");
		Assert.assertEquals(getSummaryClientID,APIHelperClass.ValidationNullValue(Validate_SummaryClientID),"Validate_Summary_Locate_Subscribe_ClientID");
		Assert.assertEquals(getSummaryLocateType,APIHelperClass.ValidationNullValue(Validate_SummaryLocateType),"Validate_Summary_Locate_Subscribe_LocateType");
		Assert.assertNotEquals(getSummaryOriginatingUserDesc,APIHelperClass.ValidationNullValue("null"),"Validate_Summary_Locate_Subscribe_OriginatingUserDesc");
		Assert.assertEquals(Integer.parseInt(getSummaryEtbQty.substring(1, getSummaryEtbQty.length() - 1)),Validate_SummaryAcqEtbQty,"Validate_Summary_Locate_Subscribe_EtbQty");
		Assert.assertEquals(getSummaryBooth,APIHelperClass.ValidationNullValue(Validate_SummaryBooth), "Validate_Summary_Locate_Subscribe_Booth");

	}
	catch (Exception e)
	{
		LoggingManager.logger.error(e);
	}
}

public static void Validate_Available_Summary_Subscribe_Locates(Response Available_Summary_response,
																	  String Validate_Available_SummaryID,
																	  String Validate_Available_SummaryOriginatingUserDesc,
																	  String Validate_Available_SummaryClientID,
																	  String Validate_Available_SummaryLocateType,
																	  String Validate_Available_SummarySymbol,
																	  String Validate_Available_SummarySymbolSfx,
																	  String Validate_Available_SummaryAccount,
																	  Integer Validate_Available_SummaryAcqEtbQty,
																	  String Validate_Available_SummaryBooth)
{
		try
		{
			String getSummaryID=com.jayway.jsonpath.JsonPath.read(Available_Summary_response.getBody().asString(), "$.eventData[?(@.id =='"+Validate_Available_SummaryID+"' )].id").toString();
			String getSummarySymbol=com.jayway.jsonpath.JsonPath.read(Available_Summary_response.getBody().asString(), "$.eventData[?(@.id =='"+Validate_Available_SummaryID+"' )].symbol").toString();
			String getSummaryAccount=com.jayway.jsonpath.JsonPath.read(Available_Summary_response.getBody().asString(), "$.eventData[?(@.id =='"+Validate_Available_SummaryID+"' )].account").toString();
			String getSummarySymbolSfx=com.jayway.jsonpath.JsonPath.read(Available_Summary_response.getBody().asString(), "$.eventData[?(@.id =='"+Validate_Available_SummaryID+"' )].symbolSfx").toString();
			String getSummaryClientID=com.jayway.jsonpath.JsonPath.read(Available_Summary_response.getBody().asString(), "$.eventData[?(@.id =='"+Validate_Available_SummaryID+"' )].clientID").toString();
			String getSummaryLocateType=com.jayway.jsonpath.JsonPath.read(Available_Summary_response.getBody().asString(), "$.eventData[?(@.id =='"+Validate_Available_SummaryID+"' )].locateType").toString();
			String getSummaryBooth=com.jayway.jsonpath.JsonPath.read(Available_Summary_response.getBody().asString(), "$.eventData[?(@.id =='"+Validate_Available_SummaryID+"' )].boothID").toString();
			String getSummaryOriginatingUserDesc=com.jayway.jsonpath.JsonPath.read(Available_Summary_response.getBody().asString(), "$.eventData[?(@.id =='"+Validate_Available_SummaryID+"' )].originatingUserDesc").toString();
			String getSummaryEtbQty=com.jayway.jsonpath.JsonPath.read(Available_Summary_response.getBody().asString(), "$.eventData[?(@.id =='"+Validate_Available_SummaryID+"' )].etbQty").toString();

			LoggingManager.logger.info("API-Validate_Available_SummaryID : [\""+Validate_Available_SummaryID +"\"] - Response Validate_Available_SummaryID : "+getSummaryID);
			LoggingManager.logger.info("API-Validate_Available_SummarySymbol : [\""+Validate_Available_SummarySymbol +"\"] - Response Validate_Available_SummarySymbol : "+getSummarySymbol);
			LoggingManager.logger.info("API-Validate_Available_SummaryAccount : [\""+Validate_Available_SummaryAccount +"\"] - Response Validate_Available_SummaryAccount : "+getSummaryAccount);
			LoggingManager.logger.info("API-Validate_Available_SummarySymbolSfx : [\""+Validate_Available_SummarySymbolSfx +"\"] - Response Validate_Available_SummarySymbolSfx : "+getSummarySymbolSfx);
			LoggingManager.logger.info("API-Validate_Available_SummaryClientID : [\""+Validate_Available_SummaryClientID +"\"] - Response Validate_Available_SummaryClientID : "+getSummaryClientID);
			LoggingManager.logger.info("API-Validate_Available_SummaryLocateType : [\""+Validate_Available_SummaryLocateType +"\"] - Response Validate_Available_SummaryLocateType : "+getSummaryLocateType);
			LoggingManager.logger.info("API-Validate_Available_SummaryBooth : [\""+Validate_Available_SummaryBooth +"\"] - Response Validate_Available_SummaryBooth : "+getSummaryBooth);
			LoggingManager.logger.info("API-Response Validate_Available_Summary_OriginatingUserDesc : "+getSummaryOriginatingUserDesc);
			LoggingManager.logger.info("API-Validate_Available_SummaryEtbQty : ["+Validate_Available_SummaryAcqEtbQty+"] - Response Validate_Available_SummaryEtbQty : "+getSummaryEtbQty);

			Assert.assertEquals(getSummaryID,APIHelperClass.ValidationNullValue(Validate_Available_SummaryID),"Validate_Available_Summary_Locate_Subscribe_ID");
			Assert.assertEquals(getSummarySymbol,APIHelperClass.ValidationNullValue(Validate_Available_SummarySymbol),"Validate_Available_Summary_Locate_Subscribe_Symbol");
			Assert.assertEquals(getSummaryAccount,APIHelperClass.ValidationNullValue(Validate_Available_SummaryAccount),"Validate_Available_Summary_Locate_Subscribe_Account");
			Assert.assertEquals(getSummarySymbolSfx,APIHelperClass.ValidationNullValue(Validate_Available_SummarySymbolSfx),"Validate_Available_Summary_Locate_Subscribe_SymbolSfx");
			Assert.assertEquals(getSummaryClientID,APIHelperClass.ValidationNullValue(Validate_Available_SummaryClientID),"Validate_Available_Summary_Locate_Subscribe_ClientID");
			Assert.assertEquals(getSummaryLocateType,APIHelperClass.ValidationNullValue(Validate_Available_SummaryLocateType),"Validate_Available_Summary_Locate_Subscribe_LocateType");
			Assert.assertNotEquals(getSummaryOriginatingUserDesc,APIHelperClass.ValidationNullValue("null"),"Validate_Available_Summary_Locate_Subscribe_OriginatingUserDesc");
			Assert.assertEquals(Integer.parseInt(getSummaryEtbQty.substring(1, getSummaryEtbQty.length() - 1)),Validate_Available_SummaryAcqEtbQty,"Validate_Available_Summary_Locate_Subscribe_EtbQty");
			Assert.assertEquals(getSummaryBooth,APIHelperClass.ValidationNullValue(Validate_Available_SummaryBooth), "Validate_Available_Summary_Locate_Subscribe_Booth");

		}
		catch (Exception e)
		{
			LoggingManager.logger.error(e);
		}
	}

}

/*
public static void GetOrder_PositionsData(  String endpoint_version,
											String Subscribe_Order_Positions_BasePath,
											String Content_Type,
											String Subscribe_Order_Positions_StatusCode,
											String Account_BoxVsShort,
											String PositionID,
											String PositionString) 
{
	try
	{
		Global.getAvgPrice=0.0;
		Global.getSHORTrealizedPnL=0.0;
		Global.getLONGrealizedPnL=0.0;
		Global.getcompleteDayBuyOrderQty=0.0;
		Global.getcompleteDaySellLongOrderQty=0.0;
		Global.getcompleteDayBuyOrderQty=0.0;
		Global.getcompleteDaySellShortOrderQty=0.0;
		Response Validate_position_response=

				given()
						.header("Content-Type",Content_Type)
						.header("Authorization", "Bearer " + Global.getAccToken)
						.when()
						.get(Subscribe_Order_Positions_BasePath)

						.then()
						.extract().response();

		Assert.assertEquals(Validate_position_response.getStatusCode(),200,"Validate_response_LONG_BoxvsShort");
		JsonPath JSON_position_response = new JsonPath(Validate_position_response.getBody().asString());
		String getResponseArray=apiRespVersion(endpoint_version);
		int ResponseArraySize = JSON_position_response.getInt(getResponseArray+".size()");
		loop: for(int position = ResponseArraySize-1; position >=0; position--)
		{
			Global.getPosition_id = JSON_position_response.getString(getResponseArray+"["+position+"].id");
			Global.getPosition_positionString = JSON_position_response.getString(getResponseArray+"["+position+"].positionString");
			if(Account_BoxVsShort.equalsIgnoreCase("1"))
			{
				if (Global.getPosition_id.equalsIgnoreCase(PositionID))
				{
					Global.getPosition_completeDayBuyOrderQty = JSON_position_response.getDouble(getResponseArray+"["+position+"].completeDayBuyOrderQty");
					Global.getPosition_completeDaySellLongOrderQty = JSON_position_response.getDouble(getResponseArray+"["+position+"].completeDaySellLongOrderQty");
					Global.getPosition_completeDaySellShortOrderQty= JSON_position_response.getDouble(getResponseArray+"["+position+"].completeDaySellShortOrderQty");
					Global.getPosition_symbol = JSON_position_response.getString(getResponseArray+"["+position+"].symbol");
					Global.getPosition_avgPrice = JSON_position_response.getDouble(getResponseArray+"["+position+"].avgPrice");
					Global.getPosition_totDollarOfTrade = JSON_position_response.getDouble(getResponseArray+"["+position+"].totDollarOfTrade");
					Global.getPosition_execQty = JSON_position_response.getDouble(getResponseArray+"["+position+"].execQty");
					Global.getPosition_realizedPnL = JSON_position_response.getDouble(getResponseArray+"["+position+"].realizedPnL");
					Global.getPosition_symbolSfx = JSON_position_response.getString(getResponseArray+"["+position+"].symbolSfx");
					Global.getPosition_originatingUserDesc = JSON_position_response.getString(getResponseArray+"["+position+"].originatingUserDesc");
					Global.getPosition_account = JSON_position_response.getString(getResponseArray+"["+position+"].account");
					Global.getLONGrealizedPnL=Global.getPosition_realizedPnL;
					Global.getSHORTrealizedPnL=Global.getPosition_realizedPnL;
					Global.getcompleteDayBuyOrderQty=Global.getPosition_completeDayBuyOrderQty;
					Global.getcompleteDaySellShortOrderQty=Global.getPosition_completeDaySellShortOrderQty;
					Global.getcompleteDaySellLongOrderQty=Global.getPosition_completeDaySellLongOrderQty;

					LoggingManager.logger.info("API-Flat Position ID : ["+Global.getPosition_id+"]");
					LoggingManager.logger.info("API-Flat Position positionString : ["+Global.getPosition_positionString+"]");
					LoggingManager.logger.info("API-Flat Position realizedPnL : ["+Global.getPosition_realizedPnL+"]");
					LoggingManager.logger.info("API-Flat Position account : ["+Global.getPosition_account+"]");
					LoggingManager.logger.info("API-Flat Position execQty : ["+Global.getPosition_execQty+"]");
					LoggingManager.logger.info("API-Flat Position symbol : ["+Global.getPosition_symbol+"]");
					LoggingManager.logger.info("API-Flat Position completeDayBuyOrderQty : ["+Global.getPosition_completeDayBuyOrderQty+"]");
					LoggingManager.logger.info("API-Flat Position completeDaySellLongOrderQty : ["+Global.getPosition_completeDaySellLongOrderQty+"]");
					LoggingManager.logger.info("API-Flat Position completeDaySellShortOrderQty : ["+Global.getPosition_completeDaySellShortOrderQty+"]");
					LoggingManager.logger.info("API-Flat Position getcompleteDayBuyOrderQty : ["+Global.getcompleteDayBuyOrderQty+"]");
					LoggingManager.logger.info("API-Flat Position getcompleteDaySellShortOrderQty : ["+Global.getcompleteDaySellShortOrderQty+"]");
					LoggingManager.logger.info("API-Flat Position getcompleteDaySellLongOrderQty : ["+Global.getcompleteDaySellLongOrderQty+"]");
					LoggingManager.logger.info("API-Flat Position totDollarOfTrade : ["+Global.getPosition_totDollarOfTrade+"]");
					LoggingManager.logger.info("API-Flat Position avgPrice : ["+Global.getPosition_avgPrice+"]");
					LoggingManager.logger.info("API-Flat Position symbolSfx : ["+Global.getPosition_symbolSfx+"]");
					LoggingManager.logger.info("API-Flat Position originatingUserDesc : ["+Global.getPosition_originatingUserDesc+"]");


				}
				else
				{
					continue loop;
				}
			}

			else if(Account_BoxVsShort.equalsIgnoreCase("0"))
			{
				if (Global.getPosition_id.equalsIgnoreCase(PositionID) && Global.getPosition_positionString.equalsIgnoreCase(PositionString))
				{
					Global.getPosition_completeDayBuyOrderQty = JSON_position_response.getDouble(getResponseArray+"["+position+"].completeDayBuyOrderQty");
					Global.getPosition_completeDaySellLongOrderQty = JSON_position_response.getDouble(getResponseArray+"["+position+"].completeDaySellLongOrderQty");
					Global.getPosition_completeDaySellShortOrderQty= JSON_position_response.getDouble(getResponseArray+"["+position+"].completeDaySellShortOrderQty");
					Global.getPosition_symbol = JSON_position_response.getString(getResponseArray+"["+position+"].symbol");
					Global.getPosition_avgPrice = JSON_position_response.getDouble(getResponseArray+"["+position+"].avgPrice");
					Global.getPosition_totDollarOfTrade = JSON_position_response.getDouble(getResponseArray+"["+position+"].totDollarOfTrade");
					Global.getPosition_execQty = JSON_position_response.getDouble(getResponseArray+"["+position+"].execQty");
					Global.getPosition_realizedPnL = JSON_position_response.getDouble(getResponseArray+"["+position+"].realizedPnL");
					Global.getPosition_symbolSfx = JSON_position_response.getString(getResponseArray+"["+position+"].symbolSfx");
					Global.getPosition_originatingUserDesc = JSON_position_response.getString(getResponseArray+"["+position+"].originatingUserDesc");
					Global.getPosition_account = JSON_position_response.getString(getResponseArray+"["+position+"].account");
					Global.getLONGrealizedPnL=Global.getPosition_realizedPnL;
					Global.getSHORTrealizedPnL=Global.getPosition_realizedPnL;
					Global.getcompleteDayBuyOrderQty=Global.getPosition_completeDayBuyOrderQty;
					Global.getcompleteDaySellShortOrderQty=Global.getPosition_completeDaySellShortOrderQty;
					Global.getcompleteDaySellLongOrderQty=Global.getPosition_completeDaySellLongOrderQty;

					LoggingManager.logger.info("API-Flat Position ID : ["+Global.getPosition_id+"]");
					LoggingManager.logger.info("API-Flat Position positionString : ["+Global.getPosition_positionString+"]");
					LoggingManager.logger.info("API-Flat Position realizedPnL : ["+Global.getPosition_realizedPnL+"]");
					LoggingManager.logger.info("API-Flat Position account : ["+Global.getPosition_account+"]");
					LoggingManager.logger.info("API-Flat Position execQty : ["+Global.getPosition_execQty+"]");
					LoggingManager.logger.info("API-Flat Position symbol : ["+Global.getPosition_symbol+"]");
					LoggingManager.logger.info("API-Flat Position completeDayBuyOrderQty : ["+Global.getPosition_completeDayBuyOrderQty+"]");
					LoggingManager.logger.info("API-Flat Position completeDaySellLongOrderQty : ["+Global.getPosition_completeDaySellLongOrderQty+"]");
					LoggingManager.logger.info("API-Flat Position completeDaySellShortOrderQty : ["+Global.getPosition_completeDaySellShortOrderQty+"]");
					LoggingManager.logger.info("API-Flat Position getcompleteDayBuyOrderQty : ["+Global.getcompleteDayBuyOrderQty+"]");
					LoggingManager.logger.info("API-Flat Position getcompleteDaySellShortOrderQty : ["+Global.getcompleteDaySellShortOrderQty+"]");
					LoggingManager.logger.info("API-Flat Position getcompleteDaySellLongOrderQty : ["+Global.getcompleteDaySellLongOrderQty+"]");
					LoggingManager.logger.info("API-Flat Position totDollarOfTrade : ["+Global.getPosition_totDollarOfTrade+"]");
					LoggingManager.logger.info("API-Flat Position avgPrice : ["+Global.getPosition_avgPrice+"]");
					LoggingManager.logger.info("API-Flat Position symbolSfx : ["+Global.getPosition_symbolSfx+"]");
					LoggingManager.logger.info("API-Flat Position originatingUserDesc : ["+Global.getPosition_originatingUserDesc+"]");


				}
				else
				{
					continue loop;
				}
			}
			else
			{
				Assert.fail("API-Incorrect Account_BoxVsShort check : ["+Account_BoxVsShort+"]");
			}
		}
	}
	catch (Exception e)
	{
		LoggingManager.logger.error(e);
	}
}



public static void GetOption_PositionsData( String endpoint_version,
											String Subscribe_Option_Positions_BasePath,
											String Content_Type,
											String Subscribe_Option_Positions_StatusCode,
											String PositionID) 
{
	try
	{

		Global.getOptionAvgPrice=0.0;
		Global.getOptionSHORTrealizedPnL=0.0;
		Global.getOptionLONGrealizedPnL=0.0;
		Global.getOptioncompleteDayBuyOrderQty=0.0;
		Global.getOptioncompleteDaySellLongOrderQty=0.0;
		Global.getOptioncompleteDayBuyOrderQty=0.0;
		Global.getOptioncompleteDaySellShortOrderQty=0.0;
		Response Validate_position_response=

				given()
						.header("Content-Type",Content_Type)
						.header("Authorization", "Bearer " + Global.getAccToken)
						.when()
						.get(Subscribe_Option_Positions_BasePath)

						.then()
						.extract().response();

		Assert.assertEquals(Validate_position_response.getStatusCode(),Integer.parseInt(Subscribe_Option_Positions_StatusCode),"Validate_Subscribe_Option_Positions_StatusCode");
		JsonPath JSON_position_response = new JsonPath(Validate_position_response.getBody().asString());
		String getResponseArray=apiRespVersion(endpoint_version);
		int ResponseArraySize = JSON_position_response.getInt(getResponseArray+".size()");
		loop: for(int innerposition = ResponseArraySize-1; innerposition >=0; innerposition--)
		{
			Global.getOptionPosition_id = JSON_position_response.getString(getResponseArray+"["+innerposition+"].id");

			if (Global.getOptionPosition_id.equalsIgnoreCase(PositionID))
			{
				Global.getOptionPosition_completeDayBuyOrderQty = JSON_position_response.getDouble(getResponseArray+"["+innerposition+"].completeDayBuyOrderQty");
				Global.getOptionPosition_completeDaySellLongOrderQty = JSON_position_response.getDouble(getResponseArray+"["+innerposition+"].completeDaySellLongOrderQty");
				Global.getOptionPosition_completeDaySellShortOrderQty= JSON_position_response.getDouble(getResponseArray+"["+innerposition+"].completeDaySellShortOrderQty");
				Global.getOptionPosition_symbol = JSON_position_response.getString(getResponseArray+"["+innerposition+"].symbol");
				Global.getOptionPosition_avgPrice = JSON_position_response.getDouble(getResponseArray+"["+innerposition+"].avgPrice");
				Global.getOptionPosition_totDollarOfTrade = JSON_position_response.getDouble(getResponseArray+"["+innerposition+"].totDollarOfTrade");
				Global.getOptionPosition_execQty = JSON_position_response.getDouble(getResponseArray+"["+innerposition+"].execQty");
				Global.getOptionPosition_realizedPnL = JSON_position_response.getDouble(getResponseArray+"["+innerposition+"].realizedPnL");
				Global.getOptionPosition_symbolSfx = JSON_position_response.getString(getResponseArray+"["+innerposition+"].symbolSfx");
				Global.getOptionPosition_originatingUserDesc = JSON_position_response.getString(getResponseArray+"["+innerposition+"].originatingUserDesc");
				Global.getOptionPosition_account = JSON_position_response.getString(getResponseArray+"["+innerposition+"].account");
				Global.getOptionPosition_positionString = JSON_position_response.getString(getResponseArray+"["+innerposition+"].positionString");
				Global.getOptionLONGrealizedPnL=Global.getOptionPosition_realizedPnL;
				Global.getOptionSHORTrealizedPnL=Global.getOptionPosition_realizedPnL;
				Global.getOptioncompleteDayBuyOrderQty=Global.getOptionPosition_completeDayBuyOrderQty;
				Global.getOptioncompleteDaySellShortOrderQty=Global.getOptionPosition_completeDaySellShortOrderQty;
				Global.getOptioncompleteDaySellLongOrderQty=Global.getOptionPosition_completeDaySellLongOrderQty;
			}
			else
			{
				continue loop;
			}
		}
	}
	catch (Exception e)
	{
		LoggingManager.logger.error(e);
	}
}
*/
/*
	public Boolean DeleteOrderValidate( String BaseURL,
										String basePath,
										String AccToken,
										String contentType,
										int statuscode,
										String orderid,
										String qorderid) 
	{

			RestAssured.baseURI=BaseURL;
			Boolean Delete_Validation_Flag=true;
			String Get_orders_basePath=basePath;
			
			Response get_orders_response=
			
							given()	
									.header("Content-Type",contentType) 
									.header("Authorization", "Bearer " + AccToken)
							.when()
									.get(Get_orders_basePath)
							.then()
									//.statusCode(statuscode)
									//.statusLine("HTTP/1.1 200 OK")
									.extract().response();
			
			System.out.println(" this is get response for delete response "+get_orders_response.statusCode());
			
			Assert.assertEquals(get_orders_response.getStatusCode(),statuscode,"Get Order ID Response Data For Delete Orders data check");
			JsonPath jsonresponse = new JsonPath(get_orders_response.asString());
			int ResponseArraySize = jsonresponse.getInt("eventData.size()");
			
			for(int position = ResponseArraySize-1; position >=0; position--) 
			{
			
				String OrderID = jsonresponse.getString("eventData["+position+"].orderId");
				String qOrderID = jsonresponse.getString("eventData["+position+"].qOrderID");	
				System.out.println(" this is ID from Response :"+qOrderID+" ::: "+OrderID);
				System.out.println(" this is ID we are looking for :"+qorderid+" ::: "+orderid);
				if(OrderID.equalsIgnoreCase(orderid) && qOrderID.equalsIgnoreCase(qorderid)) 
				{
					Delete_Validation_Flag=false;
					//Assert.fail("Logs - Order is not deleted with OrderID - ["+orderid+"]");
					break;
				}  
								
			}	
			
		return Delete_Validation_Flag;	
	}
	
*/
/*
	
	public String GetqOrderID(String BaseURL,
							 String basePath,
							 String AccToken,
							 String contentType,
							 int statuscode,
							 String ExpectedOrderID) 
	{
	
			String getOrderID="";
			String qOrderID="";
			RestAssured.baseURI=BaseURL;
			String Get_orders_basePath=basePath;
			
			Response get_orders_response=
			
					 given()	
							.header("Content-Type",contentType) 
							.header("Authorization", "Bearer " + AccToken)
					 .when()
							.get(Get_orders_basePath)
					 .then()
							.statusCode(statuscode)
							.statusLine("HTTP/1.1 200 OK")
							.extract().response();
			
			JsonPath jsonresponse = new JsonPath(get_orders_response.asString());
			int ResponseArraySize = jsonresponse.getInt("eventData.size()");
			for(int position = ResponseArraySize-1; position >=0; position--) 
			{
				getOrderID = jsonresponse.getString("eventData["+position+"].orderId");
				if(getOrderID.equalsIgnoreCase(ExpectedOrderID))   
				{ 
					qOrderID=jsonresponse.getString("eventData["+position+"].qOrderID");  
					break;	
				}
				
				else 
				{	
					continue; 	
				}  
			}  
			
			
	  return qOrderID;
	}
	
	*/


