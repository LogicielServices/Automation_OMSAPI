package XLDataProvider;


import Excel_Utilities.XLUtils;
import org.testng.annotations.DataProvider;

import java.io.IOException;


public class ExcelDataProvider {

	/**
	 * @param File Name
	 * @param Sheet Name
	 * @return
	 */
	
	String OptionOrders_FileDirectory="/src/test/java/OptionOrders/Data/OptionOrderData.xlsx";
	String EquityOrders_FileDirectory="/src/test/java/Orders/Data/OrderData.xlsx";
	String OpenOrders_FileDirectory="/src/test/java/OpenOrders/Data/OpenOrderData.xlsx";
	String IdentityServer_API_Admin_FileDirectory="/src/test/java/IdentityServer_API_Admin/Data/IdentityServer_API_Admin.xlsx";
	String IdentityServer_API_User_FileDirectory="/src/test/java/IdentityServer_API_User/Data/IdentityServer_API_User.xlsx";
	String MarketDataAPI_Option_FileDirectory="/src/test/java/MarketData_OPT_L1/Data/MarketData_OPT_L1.xlsx";
	String MarketDataAPI_Equity_FileDirectory="/src/test/java/MarketData_L1/Data/MarketData_L1.xlsx";
	String StaticData_FileDirectory="/src/test/java/Static_Data/Data/Static_Data.xlsx";
	String AuditTrail_FileDirectory="/src/test/java/AuditTrail/Data/AuditTrail.xlsx";
	String AccountBalance_FileDirectory="/src/test/java/Account_Balances/Data/Account_Balance.xlsx";
	String Subscriptions_EquityOrders_FileDirectory="/src/test/java/Subscriptions/Data/Equity_Order_Subscription.xlsx";
	String Subscriptions_OpenOrders_FileDirectory="/src/test/java/Subscriptions/Data/Open_Order_Subscription.xlsx";
	String Subscriptions_OptionOrders_FileDirectory="/src/test/java/Subscriptions/Data/Option_Order_Subscription.xlsx";
	String Subscriptions_Executions_FileDirectory="/src/test/java/Subscriptions/Data/Executions_Subscription.xlsx";
	String Subscriptions_Order_Positions_FileDirectory="/src/test/java/Subscriptions/Data/Order_Positions_Subscription.xlsx";
	String Flat_Positions_FileDirectory="/src/test/java/Subscriptions/Data/Flat_Symbol_Positions.xlsx";
	String Subscriptions_Option_Positions_FileDirectory="/src/test/java/Subscriptions/Data/Option_Positions_Subscription.xlsx";
	String Locates_FileDirectory="/src/test/java/LocatesAPI/Data/Locates.xlsx";
	String MarketDataAPI_FileDirectory="/src/test/java/MarketDataAPI/Data/Market_Symbols.xlsx";
	
	String[][] GetExcelData(String Filename,String Sheetname) throws IOException {
	String path = System.getProperty("user.dir") +Filename;
	int rownum = XLUtils.getRowCount(path, Sheetname);
	int colnum = XLUtils.getCellCount(path, Sheetname, 1);
	String xldata[][] = new String[rownum][colnum];
	for (int i = 1; i <= rownum; i++) 
	{
		for (int j = 0; j < colnum; j++) 
		{
			xldata[i-1][j] = XLUtils.getCellData(path, Sheetname, i, j);
		}
	}
	return xldata;
	}
	
	
	
	//===================================================Identity Server User==================================================
	
	@DataProvider(name="UserLogin")
	public Object[][] loginData() throws IOException 
	{
	    Object[][] DataObject = GetExcelData(IdentityServer_API_User_FileDirectory,"UserLogin");
	    return DataObject;
	}
	
	@DataProvider(name="Forgot_Password")
	public Object[][] Forgot_Password() throws IOException 
	{
	    Object[][] DataObject = GetExcelData(IdentityServer_API_User_FileDirectory,"Forgot_Password");
	    return DataObject;
	}
	
	@DataProvider(name="Forgot_Password_Resend")
	public Object[][] Forgot_Password_Resend() throws IOException 
	{
	    Object[][] DataObject = GetExcelData(IdentityServer_API_User_FileDirectory,"Forgot_Password_Resend");
	    return DataObject;
	}
	
	@DataProvider(name="Forgot_Password_Confirm")
	public Object[][] Forgot_Password_Confirm() throws IOException 
	{
	    Object[][] DataObject = GetExcelData(IdentityServer_API_User_FileDirectory,"Forgot_Password_Confirm");
	    return DataObject;
	}
	
	@DataProvider(name="Forgot_Password_Verify")
	public Object[][] Forgot_Password_Verify() throws IOException 
	{
	    Object[][] DataObject = GetExcelData(IdentityServer_API_User_FileDirectory,"Forgot_Password_Verify");
	    return DataObject;
	}
	
	@DataProvider(name="UserProfile_UserLogin")
	public Object[][] UserProfile_UserLogin() throws IOException 
	{
	    Object[][] DataObject = GetExcelData(IdentityServer_API_User_FileDirectory,"UserProfile_UserLogin");
	    return DataObject;
	}
	
	@DataProvider(name="UserProfile_ChangePassword")
	public Object[][] UserProfile_ChangePassword() throws IOException 
	{
	    Object[][] DataObject = GetExcelData(IdentityServer_API_User_FileDirectory,"UserProfile_ChangePassword");
	    return DataObject;
	}
	
	@DataProvider(name="UserProfile_Detail")
	public Object[][] UserProfile_Detail() throws IOException 
	{
	    Object[][] DataObject = GetExcelData(IdentityServer_API_User_FileDirectory,"UserProfile_Detail");
	    return DataObject;
	}
	
	
	
	
	//===================================================Identity Admin User==================================================
	
	@DataProvider(name="AdminUserLogin")
	public Object[][] AdminloginData() throws IOException 
	{
	    Object[][] DataObject = GetExcelData(IdentityServer_API_Admin_FileDirectory,"AdminLogin");
	    return DataObject;
	}
		
	@DataProvider(name="RegisterUser")
	public Object[][] Register_User_Data() throws IOException 
	{
	    Object[][] DataObject = GetExcelData(IdentityServer_API_Admin_FileDirectory,"UserRegister");//UserRegister
	    return DataObject;
	}
	
	
	@DataProvider(name="UpdateUserInfomation")
	public Object[][] Update_User_Info() throws IOException 
	{
	    Object[][] DataObject = GetExcelData(IdentityServer_API_Admin_FileDirectory,"UpdateUserInfo");
	    return DataObject;
	}
	
	
	@DataProvider(name="UpdateUserStatus")
	public Object[][] Update_User_Status() throws IOException 
	{
	    Object[][] DataObject = GetExcelData(IdentityServer_API_Admin_FileDirectory,"UpdateUserStatus");
	    return DataObject;
	}
	
	
	@DataProvider(name="UserAccountsRegister")
	public Object[][] User_Accounts_Register() throws IOException 
	{
	    Object[][] DataObject = GetExcelData(IdentityServer_API_Admin_FileDirectory,"AccountsRegister");
	    return DataObject;
	}
	
	@DataProvider(name="UserAccountsAssociate")
	public Object[][] User_Accounts_Associate() throws IOException 
	{
	    Object[][] DataObject = GetExcelData(IdentityServer_API_Admin_FileDirectory,"AccountsAssociate");
	    return DataObject;
	}
	
	
	//==================================================Market Data Option API===================================================
	
	@DataProvider(name="NYSE_Halt_Symbols")
	public Object[][] NYSE_Halt_Symbols() throws IOException 
	{
	    Object[][] DataObject = GetExcelData(MarketDataAPI_FileDirectory,"NYSE");
	    return DataObject;
	}
	
	
	@DataProvider(name="Topic_MarketData")
	public Object[][] Topic_Market_Data() throws IOException 
	{
	    Object[][] DataObject = GetExcelData(MarketDataAPI_Option_FileDirectory,"Topic");
	    return DataObject;
	}
	
	@DataProvider(name="Subscribe_MarketData")
	public Object[][] Subscribe_Market_Data() throws IOException 
	{
	    Object[][] DataObject = GetExcelData(MarketDataAPI_Option_FileDirectory,"Subscribe");
	    return DataObject;
	}
	
	@DataProvider(name="UnSubscribe_MarketData")
	public Object[][] UnSubscribe_Market_Data() throws IOException 
	{
	    Object[][] DataObject = GetExcelData(MarketDataAPI_Option_FileDirectory,"UnSubscribe");
	    return DataObject;
	}
	
	@DataProvider(name="Subscribe_Bulk_MarketData")
	public Object[][] Subscribe_Bulk_MarketData() throws IOException 
	{
	    Object[][] DataObject = GetExcelData(MarketDataAPI_Option_FileDirectory,"Subscribe_Bulk");
	    return DataObject;
	}
	
	@DataProvider(name="UnSubscribe_Bulk_MarketData")
	public Object[][] UnSubscribe_Bulk_MarketData() throws IOException 
	{
	    Object[][] DataObject = GetExcelData(MarketDataAPI_Option_FileDirectory,"UnSubscribe_Bulk");
	    return DataObject;
	}
	
	@DataProvider(name="Subscribe_Bulk_NoSnapshot_MarketData")
	public Object[][] Subscribe_Bulk_NoSnapshot_MarketData() throws IOException 
	{
	    Object[][] DataObject = GetExcelData(MarketDataAPI_Option_FileDirectory,"Subscribe_Bulk_NoSnapshot");
	    return DataObject;
	}
	
	@DataProvider(name="UnSubscribe_Bulk_CompanySymbol_MarketData")
	public Object[][] UnSubscribe_Bulk_Symbol_MarketData() throws IOException 
	{
	    Object[][] DataObject = GetExcelData(MarketDataAPI_Option_FileDirectory,"UnSubscribe_Bulk_CompanySymbol");
	    return DataObject;
	}
	
	//==================================================Market Data Equity API==================================================
	
	
				
		@DataProvider(name="Subscribe_Equity_MarketData")
		public Object[][] Subscribe_Equity_MarketData() throws IOException 
		{
		    Object[][] DataObject = GetExcelData(MarketDataAPI_Equity_FileDirectory,"Subscribe");
		    return DataObject;
		}
		
		@DataProvider(name="UnSubscribe_Equity_MarketData")
		public Object[][] UnSubscribe_Equity_MarketData() throws IOException 
		{
		    Object[][] DataObject = GetExcelData(MarketDataAPI_Equity_FileDirectory,"UnSubscribe");
		    return DataObject;
		}
		
		
		@DataProvider(name="Subscribe_Bulk_Equity_MarketData")
		public Object[][] Subscribe_Bulk_Equity_MarketData() throws IOException 
		{
		    Object[][] DataObject = GetExcelData(MarketDataAPI_Equity_FileDirectory,"Subscribe_Bulk");
		    return DataObject;
		}
		
		@DataProvider(name="UnSubscribe_Bulk_Equity_MarketData")
		public Object[][] UnSubscribe_Bulk_Equity_MarketData() throws IOException 
		{
		    Object[][] DataObject = GetExcelData(MarketDataAPI_Equity_FileDirectory,"UnSubscribe_Bulk");
		    return DataObject;
		}
		
		@DataProvider(name="CompanyInformation_MarketData")
		public Object[][] CompanyInformation_MarketData() throws IOException 
		{
		    Object[][] DataObject = GetExcelData(MarketDataAPI_Equity_FileDirectory,"CompanyInformation");
		    return DataObject;
		}
		
		@DataProvider(name="Historicaldata_Snapshot_MarketData")
		public Object[][] Historicaldata_Snapshot_MarketData() throws IOException 
		{
		    Object[][] DataObject = GetExcelData(MarketDataAPI_Equity_FileDirectory,"Historicaldata");
		    return DataObject;
		}
	
		
		
	//==================================================Static Data V3==================================================
		
		@DataProvider(name="StaticData_Account")
		public Object[][] StaticData_Account() throws IOException 
		{
			Object[][] DataObject = GetExcelData(StaticData_FileDirectory,"Account");
		    return DataObject;
		}
		
		@DataProvider(name="StaticData_Destination")
		public Object[][] StaticData_Destination() throws IOException 
		{
			Object[][] DataObject = GetExcelData(StaticData_FileDirectory,"Destination");
		    return DataObject;
		}

		@DataProvider(name="StaticData_Option_Equity_Destination")
		public Object[][] StaticData_Option_Equity_Destination() throws IOException
		{
		Object[][] DataObject = GetExcelData(StaticData_FileDirectory,"Option_Equity_Destination");
		return DataObject;
		}
		
		@DataProvider(name="StaticData_Option_Destination")
		public Object[][] StaticData_Option_Destination() throws IOException 
		{
			Object[][] DataObject = GetExcelData(StaticData_FileDirectory,"OptionDestination");
		    return DataObject;
		}
		
		@DataProvider(name="StaticData_Destination_TraderOnly")
		public Object[][] StaticData_Destination_TraderOnly() throws IOException 
		{
			Object[][] DataObject = GetExcelData(StaticData_FileDirectory,"Destination_TraderOnly");
		    return DataObject;
		}
		
		@DataProvider(name="StaticData_Side")
		public Object[][] StaticData_Side() throws IOException 
		{
			Object[][] DataObject = GetExcelData(StaticData_FileDirectory,"Side");
		    return DataObject;
		}
		
		@DataProvider(name="StaticData_TIF")
		public Object[][] StaticData_TIF() throws IOException 
		{
			Object[][] DataObject = GetExcelData(StaticData_FileDirectory,"TIF");
		    return DataObject;
		}
		
		@DataProvider(name="StaticData_CommType")
		public Object[][] StaticData_CommType() throws IOException 
		{
			Object[][] DataObject = GetExcelData(StaticData_FileDirectory,"CommType");
		    return DataObject;
		}
		
		@DataProvider(name="StaticData_OrdType")
		public Object[][] StaticData_OrdType() throws IOException 
		{
			Object[][] DataObject = GetExcelData(StaticData_FileDirectory,"OrdType");
		    return DataObject;
		}
		
		@DataProvider(name="StaticData_LocateTIF")
		public Object[][] StaticData_LocateTIF() throws IOException 
		{
			Object[][] DataObject = GetExcelData(StaticData_FileDirectory,"LocateTIF");
		    return DataObject;
		}
		
		@DataProvider(name="StaticData_MktTopPerfCateg")
		public Object[][] StaticData_MktTopPerfCateg() throws IOException 
		{
			Object[][] DataObject = GetExcelData(StaticData_FileDirectory,"MktTopPerfCateg");
		    return DataObject;
		}
		
		@DataProvider(name="StaticData_MktTopPerfExchange")
		public Object[][] StaticData_MktTopPerfExchange() throws IOException 
		{
			Object[][] DataObject = GetExcelData(StaticData_FileDirectory,"MktTopPerfExchange");
		    return DataObject;
		}
		
		@DataProvider(name="StaticData_BlotterPackage_Permissions")
		public Object[][] StaticData_BlotterPackage_Permissions() throws IOException 
		{
			Object[][] DataObject = GetExcelData(StaticData_FileDirectory,"BlotterPackagePermissions");
		    return DataObject;
		}
		
		@DataProvider(name="StaticData_BlotterPermissions")
		public Object[][] StaticData_BlotterPermissions() throws IOException 
		{
			Object[][] DataObject = GetExcelData(StaticData_FileDirectory,"BlotterPermissions");
		    return DataObject;
		}
		
		@DataProvider(name="StaticData_ETBHTB")
		public Object[][] StaticData_ETBHTB() throws IOException 
		{
			Object[][] DataObject = GetExcelData(StaticData_FileDirectory,"ETBHTB");
		    return DataObject;
		}

		@DataProvider(name="StaticData_PutCall")
		public Object[][] StaticData_PutCall() throws IOException
		{
			Object[][] DataObject = GetExcelData(StaticData_FileDirectory,"PutCall");
			return DataObject;
		}

		@DataProvider(name="StaticData_CoveredUncovered")
		public Object[][] StaticData_CoveredUncovered() throws IOException
		{
			Object[][] DataObject = GetExcelData(StaticData_FileDirectory,"CoveredUncovered");
			return DataObject;
		}

		@DataProvider(name="StaticData_CustomerFirm")
		public Object[][] StaticData_CustomerFirm() throws IOException
		{
			Object[][] DataObject = GetExcelData(StaticData_FileDirectory,"CustomerFirm");
			return DataObject;
		}

		@DataProvider(name="StaticData_OptionOrderType")
		public Object[][] StaticData_OptionOrderType() throws IOException
		{
			Object[][] DataObject = GetExcelData(StaticData_FileDirectory,"OptionOrderType");
			return DataObject;
		}

		@DataProvider(name="StaticData_OptionTIF")
		public Object[][] StaticData_OptionTIF() throws IOException
		{
			Object[][] DataObject = GetExcelData(StaticData_FileDirectory,"OptionTIF");
			return DataObject;
		}
		
	//==================================================Account Balance==================================================
		
		@DataProvider(name="Subscribe_AccountBalance")
		public Object[][] Subscribe_AccountBalance() throws IOException 
		{
			Object[][] DataObject = GetExcelData(AccountBalance_FileDirectory,"Subscribe_AccountBalance");
		    return DataObject;
		}
		
		@DataProvider(name="UnSubscribe_AccountBalance")
		public Object[][] UnSubscribe_AccountBalance() throws IOException 
		{
			Object[][] DataObject = GetExcelData(AccountBalance_FileDirectory,"UnSubscribe_AccountBalance");
		    return DataObject;
		}

	//==================================================Audit Trail==================================================
		
		@DataProvider(name="Subscribe_qOrderID")
		public Object[][] Subscribe_qOrderID() throws IOException 
		{
			Object[][] DataObject = GetExcelData(AuditTrail_FileDirectory,"Subscribe_qOrderID");
		    return DataObject;
		}
		
		@DataProvider(name="UnSubscribe_qOrderID")
		public Object[][] UnSubscribe_qOrderID() throws IOException 
		{
			Object[][] DataObject = GetExcelData(AuditTrail_FileDirectory,"UnSubscribe_qOrderID");
		    return DataObject;
		}
		
		@DataProvider(name="Subscribe_OrderID")
		public Object[][] Subscribe_OrderID() throws IOException 
		{
			Object[][] DataObject = GetExcelData(AuditTrail_FileDirectory,"Subscribe_OrderID");
		    return DataObject;
		}
		
		@DataProvider(name="UnSubscribe_OrderID")
		public Object[][] UnSubscribe_OrderID() throws IOException 
		{
			Object[][] DataObject = GetExcelData(AuditTrail_FileDirectory,"UnSubscribe_OrderID");
		    return DataObject;
		}
		
	//==================================================Option Orders==================================================
	
		@DataProvider(name="OptionOrderCreation_ActiveOpen")
		public Object[][] Option_ActiveOpen_Order_Create_Data() throws IOException 
		{
			Object[][] DataObject = GetExcelData(OptionOrders_FileDirectory,"CreateActiveOpenOrder");
		    return DataObject;
		}
		
		@DataProvider(name="OptionOrder_BUYCreation_Filled")
		public Object[][] Option_BUY_Filled_Order_Create_Data() throws IOException 
		{
			Object[][] DataObject = GetExcelData(OptionOrders_FileDirectory,"CreateBUYFilledOrder");
		    return DataObject;
		}
		
		@DataProvider(name="OptionOrder_SELLCreation_Filled")
		public Object[][] Option_SELL_Filled_Order_Create_Data() throws IOException 
		{
			Object[][] DataObject = GetExcelData(OptionOrders_FileDirectory,"CreateSELLFilledOrder");
		    return DataObject;
		}
		
				
		@DataProvider(name="OptionOrderCreation_Rejected")
		public Object[][] Option_Rejected_Order_Create_Data() throws IOException 
		{
			Object[][] DataObject = GetExcelData(OptionOrders_FileDirectory,"CreateRejectedOrder");
		    return DataObject;
		}
		
		@DataProvider(name="OptionOrderCancellation")
		public Object[][] Order_Cancel_Data() throws IOException 
		{
			Object[][] DataObject = GetExcelData(OptionOrders_FileDirectory,"CancelOrders");
		    return DataObject;
		}
		
		@DataProvider(name="DeleteOptionOrders")
		public Object[][] Order_Delete_Data() throws IOException 
		{
			Object[][] DataObject = GetExcelData(OptionOrders_FileDirectory,"DeleteOrders");
		    return DataObject;
		}
		
		@DataProvider(name="OptionOrderUpdation")
		public Object[][] Order_Update_Data() throws IOException 
		{
			Object[][] DataObject = GetExcelData(OptionOrders_FileDirectory,"UpdateOrders");
		    return DataObject;
		}
	
	
	
	//==================================================Equity Orders==================================================
	
	
		@DataProvider(name="CreateActiveEquityOrder")
		public Object[][] Create_Active_Equity_Order() throws IOException 
		{
			Object[][] DataObject = GetExcelData(EquityOrders_FileDirectory,"CreateActiveEquityOrder");
		    return DataObject;
		}
		
		@DataProvider(name="CreateOpenOrder")
		public Object[][] Create_Open_Order() throws IOException 
		{
			Object[][] DataObject = GetExcelData(OpenOrders_FileDirectory,"CreateOpenOrder");
		    return DataObject;
		}
		
		@DataProvider(name="CreateBUYFilledEquityOrder")
		public Object[][] Create_BUY_Filled_Equity_Order() throws IOException 
		{
			Object[][] DataObject = GetExcelData(EquityOrders_FileDirectory,"CreateBUYFilledEquityOrder");
		    return DataObject;
		}
		
		@DataProvider(name="CreateSELLFilledEquityOrder")
		public Object[][] Create_SELL_Filled_Equity_Order() throws IOException 
		{
			Object[][] DataObject = GetExcelData(EquityOrders_FileDirectory,"CreateSELLFilledEquityOrder");
		    return DataObject;
		}
		
		@DataProvider(name="CreateSHORTSELLFilledOrder")
		public Object[][] Create_SHORTSELL_Filled_Equity_Order() throws IOException 
		{
			Object[][] DataObject = GetExcelData(EquityOrders_FileDirectory,"CreateSHORTSELLFilledOrder");
		    return DataObject;
		}
		
		@DataProvider(name="CreateRejectedEquityOrder")
		public Object[][] Create_Rejected_Equity_Order() throws IOException 
		{
			Object[][] DataObject = GetExcelData(EquityOrders_FileDirectory,"CreateRejectedEquityOrder");
		    return DataObject;
		}
		
		@DataProvider(name="EquityOrderUpdation")
		public Object[][] EquityOrder_Update_Data() throws IOException 
		{
			Object[][] DataObject = GetExcelData(EquityOrders_FileDirectory,"UpdateEquityOrder");
		    return DataObject;
		}
		
		
		@DataProvider(name="EquityOrderCancellation")
		public Object[][] EquityOrder_Cancel_Data() throws IOException 
		{
			Object[][] DataObject = GetExcelData(EquityOrders_FileDirectory,"CancelEquityOrders");
		    return DataObject;
		}
		
		@DataProvider(name="EquityOrderDelete")
		public Object[][] EquityOrder_Delete_Data() throws IOException 
		{
			Object[][] DataObject = GetExcelData(EquityOrders_FileDirectory,"DeleteEquityOrders");
		    return DataObject;
		}
		
	
	
	

	//==================================================Subscriptions V3==================================================
	
		@DataProvider(name="SubscribeActiveEquityOrder")
		public Object[][] Subscribe_Active_Equity_Order() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Subscriptions_EquityOrders_FileDirectory,"SubscribeActiveEquityOrder");
		    return DataObject;
		}
		
		@DataProvider(name="SubscribeOpenOrder")
		public Object[][] Subscribe_Open_Order() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Subscriptions_OpenOrders_FileDirectory,"SubscribeOpenOrder");
		    return DataObject;
		}
		
		
		@DataProvider(name="SubscribeBUYFilledEquityOrder")
		public Object[][] Subscribe_Buy_Filled_Equity_Order() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Subscriptions_EquityOrders_FileDirectory,"SubscribeBUYFilledEquityOrder");
		    return DataObject;
		}
		
		@DataProvider(name="SubscribeSELLFilledEquityOrder")
		public Object[][] Subscribe_Sell_Filled_Equity_Order() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Subscriptions_EquityOrders_FileDirectory,"SubscribeSELLFilledEquityOrder");
		    return DataObject;
		}
		
		@DataProvider(name="Subscribe_SS_FilledEquityOrder")
		public Object[][] Subscribe_ShortSell_Filled_Equity_Order() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Subscriptions_EquityOrders_FileDirectory,"Subscribe_SS_FilledEquityOrder");
		    return DataObject;
		}
		
		@DataProvider(name="SubscribeRejectedEquityOrder")
		public Object[][] Subscribe_Rejected_Equity_Order() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Subscriptions_EquityOrders_FileDirectory,"SubscribeRejectedEquityOrder");
		    return DataObject;
		}
		
		@DataProvider(name="SubscribeCancelEquityOrder")
		public Object[][] Subscribe_Cancel_Equity_Order() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Subscriptions_EquityOrders_FileDirectory,"SubscribeCancelEquityOrder");
		    return DataObject;
		}
		
		@DataProvider(name="UnSubscribeEquityOrder")
		public Object[][] UnSubscribe_Equity_Order() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Subscriptions_EquityOrders_FileDirectory,"UnSubscribeEquityOrder");
		    return DataObject;
		}
		
		@DataProvider(name="UnSubscribeOpenOrder")
		public Object[][] UnSubscribe_Open_Order() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Subscriptions_OpenOrders_FileDirectory,"UnSubscribeOpenOrder");
		    return DataObject;
		}
		
		@DataProvider(name="SubscribeActiveOptionOrder")
		public Object[][] Subscribe_Active_Option_Order() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Subscriptions_OptionOrders_FileDirectory,"SubscribeActiveOptionOrder");
		    return DataObject;
		}
		
		@DataProvider(name="SubscribeBUYFilledOptionOrder")
		public Object[][] Subscribe_BUYFilled_Option_Order() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Subscriptions_OptionOrders_FileDirectory,"SubscribeBUYFilledOptionOrder");
		    return DataObject;
		}
		
		@DataProvider(name="SubscribeSELLFilledOptionOrder")
		public Object[][] Subscribe_SELLFilled_Option_Order() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Subscriptions_OptionOrders_FileDirectory,"SubscribeSELLFilledOptionOrder");
		    return DataObject;
		}
		
		@DataProvider(name="SubscribeRejectedOptionOrder")
		public Object[][] Subscribe_Rejected_Option_Order() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Subscriptions_OptionOrders_FileDirectory,"SubscribeRejectedOptionOrder");
		    return DataObject;
		}
		
		@DataProvider(name="SubscribeCancelOptionOrder")
		public Object[][] Subscribe_Cancel_Option_Order() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Subscriptions_OptionOrders_FileDirectory,"SubscribeCancelOptionOrder");
		    return DataObject;
		}
		
		@DataProvider(name="UnSubscribeOptionOrder")
		public Object[][] UnSubscribe_Option_Order() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Subscriptions_OptionOrders_FileDirectory,"UnSubscribeOptionOrder");
		    return DataObject;
		}
		
		
		@DataProvider(name="SubscribeBUYExecutions")
		public Object[][] Subscribe_BUY_Executions() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Subscriptions_Executions_FileDirectory,"SubscribeBUYExecutions");
		    return DataObject;
		}
		
		@DataProvider(name="SubscribeSELLExecutions")
		public Object[][] Subscribe_SELL_Executions() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Subscriptions_Executions_FileDirectory,"SubscribeSELLExecutions");
		    return DataObject;
		}
		
		@DataProvider(name="SubscribeSHORTSELLExecutions")
		public Object[][] Subscribe_SHORTSELL_Executions() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Subscriptions_Executions_FileDirectory,"SubscribeSHORTSELLExecutions");
		    return DataObject;
		}
		
		@DataProvider(name="SubscribeBUY_OptionExecutions")
		public Object[][] Subscribe_BUY_OptionExecutions() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Subscriptions_Executions_FileDirectory,"SubscribeBUY_OptionExecutions");
		    return DataObject;
		}
		
		@DataProvider(name="SubscribeSELL_OptionExecutions")
		public Object[][] Subscribe_SELL_OptionExecutions() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Subscriptions_Executions_FileDirectory,"SubscribeSELL_OptionExecutions");
		    return DataObject;
		}
		
		
		@DataProvider(name="SubscribeBUYOptionExecutions")
		public Object[][] Subscribe_BUYOptionExecutions() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Subscriptions_Executions_FileDirectory,"SubscribeBUYOptionExecutions");
		    return DataObject;
		}
		
		@DataProvider(name="SubscribeSELLOptionExecutions")
		public Object[][] Subscribe_SELLOptionExecutions() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Subscriptions_Executions_FileDirectory,"SubscribeSELLOptionExecutions");
		    return DataObject;
		}
		
		@DataProvider(name="UnSubscribeExecutions")
		public Object[][] UnSubscribe_Executions() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Subscriptions_Executions_FileDirectory,"UnSubscribeExecutions");
		    return DataObject;
		}
		
		@DataProvider(name="Flat_Equity_Position")
		public Object[][] Flat_Equity_Position() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Flat_Positions_FileDirectory,"Flat_Equity_Position");
		    return DataObject;
		}
		
		@DataProvider(name="Flat_Option_Position")
		public Object[][] Flat_Option_Position() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Flat_Positions_FileDirectory,"Flat_Option_Position");
		    return DataObject;
		}
		
	//	@DataProvider(name="Flat_Position_Acc_BoxvsShort")
	//	public Object[][] Flat_Position_Acc_BoxvsShort() throws IOException 
	//	{
	//		Object[][] DataObject = GetExcelData(Flat_Positions_FileDirectory,"Flat_Position_Acc_BoxvsShort");
	//	    return DataObject;
	//	}
		
		@DataProvider(name="SubscribeBUYOrder_Positions")
		public Object[][] SubscribeBUYOrder_Positions() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Subscriptions_Order_Positions_FileDirectory,"SubscribeBUYOrder_Positions");
		    return DataObject;
		}
		
		@DataProvider(name="SubscribeSELLOrder_Positions")
		public Object[][] Subscribe_SELLOrder_Positions() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Subscriptions_Order_Positions_FileDirectory,"SubscribeSELLOrder_Positions");
		    return DataObject;
		}
		
		@DataProvider(name="SubscribeSHORTSELL_Position")
		public Object[][] Subscribe_SHORTSELL_Order_Positions() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Subscriptions_Order_Positions_FileDirectory,"SubscribeSHORTSELL_Position");
		    return DataObject;
		}
		
		@DataProvider(name="UnSubscribeOrder_Positions")
		public Object[][] UnSubscribeOrder_Positions() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Subscriptions_Order_Positions_FileDirectory,"UnSubscribeOrder_Positions");
		    return DataObject;
		}
		
		@DataProvider(name="SubscribeBUYOption_Positions")
		public Object[][] Subscribe_BUY_Option_Positions() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Subscriptions_Option_Positions_FileDirectory,"SubscribeBUYOption_Positions");
		    return DataObject;
		}
		@DataProvider(name="SubscribeSELLOption_Positions")
		public Object[][] Subscribe_SELL_Option_Positions() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Subscriptions_Option_Positions_FileDirectory,"SubscribeSELLOption_Positions");
		    return DataObject;
		}
		
		@DataProvider(name="SubscribeSHORTOption_Position")
		public Object[][] SubscribeSHORTOption_Position() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Subscriptions_Option_Positions_FileDirectory,"SubscribeSHORTOption_Position");
		    return DataObject;
		}
		
		@DataProvider(name="UnSubscribe_Option_Positions")
		public Object[][] UnSubscribe_Option_Positions() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Subscriptions_Option_Positions_FileDirectory,"UnSubscribe_Option_Positions");
		    return DataObject;
		}
		
		

	//==================================================Locates V1==================================================
		
		@DataProvider(name="Post_Locates")
		public Object[][] Post_Locates() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Locates_FileDirectory,"Locates");
		    return DataObject;
		}

		@DataProvider(name="Locates_ETBHTP")
		public Object[][] Locates_ETBHTP() throws IOException
		{
			Object[][] DataObject = GetExcelData(Locates_FileDirectory,"Locates_ETBHTP");
			return DataObject;
		}

		@DataProvider(name="Locates_Acquire")
		public Object[][] Locates_Acquire() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Locates_FileDirectory,"Locates_Acquire");
		    return DataObject;
		}
		
		@DataProvider(name="Subscribe_Locates")
		public Object[][] Subscribe_Locates() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Locates_FileDirectory,"Subscribe_Locates");
		    return DataObject;
		}
		
		@DataProvider(name="UnSubscribe_Locates")
		public Object[][] UnSubscribe_Locates() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Locates_FileDirectory,"UnSubscribe_Locates");
		    return DataObject;
		}
		
		@DataProvider(name="Summary_Locate_Subscribe")
		public Object[][] Summary_Locate_Subscribe() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Locates_FileDirectory,"Summary_Locate_Subscribe");
		    return DataObject;
		}
		
		@DataProvider(name="Summary_Locate_UnSubscribe")
		public Object[][] Summary_Locate_UnSubscribe() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Locates_FileDirectory,"Summary_Locate_UnSubscribe");
		    return DataObject;
		}
		
		@DataProvider(name="Summary_Available_Subscribe")
		public Object[][] Summary_Available_Subscribe() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Locates_FileDirectory,"Summary_Available_Subscribe");
		    return DataObject;
		}
		
		@DataProvider(name="Summary_Available_UnSubscribe")
		public Object[][] Summary_Available_UnSubscribe() throws IOException 
		{
			Object[][] DataObject = GetExcelData(Locates_FileDirectory,"Summary_Available_UnSubscribe");
		    return DataObject;
		}
		
}
