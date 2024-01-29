package APIHelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/*
import AuditTrail.Subscribe_OrderID;
import AuditTrail.Subscribe_qOrderID;
import AuditTrail.UnSubscribe_OrderID;
import AuditTrail.UnSubscribe_qOrderID;
import IdentityServer_API_Admin.AdminLogin;
import IdentityServer_API_Admin.UpdateUserInfo;
import IdentityServer_API_Admin.UpdateUserStatus;
import IdentityServer_API_Admin.UserAccountsAssociate;
import IdentityServer_API_Admin.UserAccountsRegister;
import IdentityServer_API_Admin.UserRegister;
import IdentityServer_API_User.Forgot_Password;
import IdentityServer_API_User.Forgot_Password_Confirm;
import IdentityServer_API_User.Forgot_Password_Resend;
import IdentityServer_API_User.Forgot_Password_Verify;
import IdentityServer_API_User.Login;
import IdentityServer_API_User.UserProfile_ChangePassword;
import IdentityServer_API_User.UserProfile_Detail;
import LocatesAPI.Locates;
import LocatesAPI.Locates_Acquire;
import LocatesAPI.Subscribe_Locates;
import LocatesAPI.Summary_Available_Subscribe;
import LocatesAPI.Summary_Available_UnSubscribe;
import LocatesAPI.Summary_Locate_Subscribe;
import LocatesAPI.Summary_Locate_UnSubscribe;
import LocatesAPI.UnSubscribe_Locates;
import MarketData_L1.CompanyInformation;
import MarketData_L1.Historicaldata_Snapshot;
import MarketData_L1.Subscribe_Bulk;
import MarketData_L1.Subscribe_MarketData;
import MarketData_L1.UnSubscribe;
import MarketData_L1.UnSubscribe_Bulk;
import MarketData_OPT_L1.Opt_Subscribe;
import MarketData_OPT_L1.Opt_Subscribe_Bulk;
import MarketData_OPT_L1.Opt_Topic;
import MarketData_OPT_L1.Opt_UnSubscribe;
import MarketData_OPT_L1.Opt_UnSubscribe_Bulk;
import MarketData_OPT_L1.Opt_UnSubscribe_Bulk_NoSnapshot;
import MarketData_OPT_L1.Opt_UnSubscribe_Bulk_Symbols;
import OptionOrders.Cancel_OptionOrder;
import OptionOrders.Create_OptionOrder;
import OptionOrders.Delete_OptionOrder;
import OptionOrders.Update_OptionOrder;
import Orders.Cancel;
import Orders.Create;
import Orders.Delete;
import Orders.Filled_Orders;
import Orders.Update;
import Static_Data.StaticData_Account;
import Static_Data.StaticData_BlotterPackage_Permissions;
import Static_Data.StaticData_Blotter_Permissions;
import Static_Data.StaticData_CommType;
import Static_Data.StaticData_Destination;
import Static_Data.StaticData_Destination_TraderOnly;
import Static_Data.StaticData_ETBHTB;
import Static_Data.StaticData_LocateTIF;
import Static_Data.StaticData_MktTopPerfCateg;
import Static_Data.StaticData_MktTopPerfExchange;
import Static_Data.StaticData_Option_Destination;
import Static_Data.StaticData_OrdType;
import Static_Data.StaticData_Side;
import Static_Data.StaticData_TIF;
import Subscriptions.Equity_Order_Subscribe;
import Subscriptions.Flat_Positions;
import Subscriptions.Option_Order_Subscribe;
import Subscriptions.Order_Executions_Subscribe;
import Subscriptions.Order_Positions_Subscribe;
*/

public class LoggingManager {

	public static final Logger logger = LogManager.getLogger(LoggingManager.class);
}
/*
 * 
///====================================================APIHelper==============================================================================================
	public static final Logger APIHelperClass_logger = LogManager.getLogger(APIHelperClass.class);
	
///====================================================IdentityServer_Admin APIs==============================================================================
	public static final Logger AdminLogin_logger = LogManager.getLogger(AdminLogin.class);
	public static final Logger Update_UserInfo_logger = LogManager.getLogger(UpdateUserInfo.class);
	public static final Logger Update_UserStatus_logger = LogManager.getLogger(UpdateUserStatus.class);
	public static final Logger User_AccountsAssociate_logger = LogManager.getLogger(UserAccountsAssociate.class);
	public static final Logger User_AccountsRegister_logger = LogManager.getLogger(UserAccountsRegister.class);
	public static final Logger User_Register_logger = LogManager.getLogger(UserRegister.class);
			
	
///====================================================IdentityServer_API_User APIs==============================================================================
	public static final Logger Login_logger = LogManager.getLogger(Login.class);
	public static final Logger Forgot_Password_logger = LogManager.getLogger(Forgot_Password.class);
	public static final Logger Forgot_Password_Verify_logger = LogManager.getLogger(Forgot_Password_Verify.class);
	public static final Logger Forgot_Password_Resend_logger = LogManager.getLogger(Forgot_Password_Resend.class);
	public static final Logger Forgot_Password_Confirm_logger = LogManager.getLogger(Forgot_Password_Confirm.class);
	public static final Logger UserProfile_ChangePassword_logger = LogManager.getLogger(UserProfile_ChangePassword.class);
	public static final Logger UserProfile_Detail_logger = LogManager.getLogger(UserProfile_Detail.class);
	//public static final Logger UserProfile_Login_logger = LogManager.getLogger(Dummy_UserProfile_Login.class);	
	
///====================================================AuditTrail APIs===========================================================================================
	public static final Logger Subscribe_OrderID_logger = LogManager.getLogger(Subscribe_OrderID.class);
	public static final Logger Subscribe_qOrderID_logger = LogManager.getLogger(Subscribe_qOrderID.class);
	public static final Logger UnSubscribe_OrderID_logger = LogManager.getLogger(UnSubscribe_OrderID.class);
	public static final Logger UnSubscribe_qOrderID_logger = LogManager.getLogger(UnSubscribe_qOrderID.class);

///====================================================Static Data APIs==========================================================================================
	public static final Logger StaticData_Account_logger = LogManager.getLogger(StaticData_Account.class);
	public static final Logger StaticData_Blotter_Permissions_logger = LogManager.getLogger(StaticData_Blotter_Permissions.class);
	public static final Logger StaticData_BlotterPackage_Permissions_logger = LogManager.getLogger(StaticData_BlotterPackage_Permissions.class);
	public static final Logger StaticData_CommType_logger = LogManager.getLogger(StaticData_CommType.class);
	public static final Logger StaticData_Destination_TraderOnly_logger = LogManager.getLogger(StaticData_Destination_TraderOnly.class);
	public static final Logger StaticData_Destination_logger = LogManager.getLogger(StaticData_Destination.class);
	public static final Logger StaticData_ETBHTB_logger = LogManager.getLogger(StaticData_ETBHTB.class);
	public static final Logger StaticData_LocateTIF_logger = LogManager.getLogger(StaticData_LocateTIF.class);
	public static final Logger StaticData_MktTopPerfCateg_logger = LogManager.getLogger(StaticData_MktTopPerfCateg.class);
	public static final Logger StaticData_MktTopPerfExchange_logger = LogManager.getLogger(StaticData_MktTopPerfExchange.class);
	public static final Logger StaticData_Option_Destination_logger = LogManager.getLogger(StaticData_Option_Destination.class);
	public static final Logger StaticData_OrdType_logger = LogManager.getLogger(StaticData_OrdType.class);
	public static final Logger StaticData_Side_logger = LogManager.getLogger(StaticData_Side.class);
	public static final Logger StaticData_TIF_logger = LogManager.getLogger(StaticData_TIF.class);

		
////====================================================Locates APIs============================================================================================
	public static final Logger Locates_logger = LogManager.getLogger(Locates.class);
	public static final Logger Locates_Acquire_logger = LogManager.getLogger(Locates_Acquire.class);
	public static final Logger Subscribe_Locates_logger = LogManager.getLogger(Subscribe_Locates.class);
	public static final Logger Summary_Available_Subscribe_logger = LogManager.getLogger(Summary_Available_Subscribe.class);
	public static final Logger Summary_Available_UnSubscribe_logger = LogManager.getLogger(Summary_Available_UnSubscribe.class);
	public static final Logger Summary_Locate_Subscribe_logger = LogManager.getLogger(Summary_Locate_Subscribe.class);
	public static final Logger Summary_Locate_UnSubscribe_logger = LogManager.getLogger(Summary_Locate_UnSubscribe.class);
	public static final Logger UnSubscribe_Locates_logger = LogManager.getLogger(UnSubscribe_Locates.class);
	
////====================================================EquityOrder APIs========================================================================================
	public static final Logger Create_EquityOrder_logger = LogManager.getLogger(Create.class);
	public static final Logger Filled_EquityOrder_logger = LogManager.getLogger(Filled_Orders.class);
	public static final Logger Cancel_EquityOrder_logger = LogManager.getLogger(Cancel.class);
	public static final Logger Delete_EquityOrder_logger = LogManager.getLogger(Delete.class);
	public static final Logger Update_EquityOrder_logger = LogManager.getLogger(Update.class);
	
////====================================================OptionOrder APIs========================================================================================
	public static final Logger Create_OptionOrder_logger = LogManager.getLogger(Create_OptionOrder.class);
	public static final Logger Cancel_OptionOrder_logger = LogManager.getLogger(Cancel_OptionOrder.class);
	public static final Logger Delete_OptionOrder_logger = LogManager.getLogger(Delete_OptionOrder.class);
	public static final Logger Update_OptionOrder_logger = LogManager.getLogger(Update_OptionOrder.class);
	
////====================================================Subsciption APIs========================================================================================
	public static final Logger Execution_Subscribe_logger = LogManager.getLogger(Order_Executions_Subscribe.class);
	public static final Logger OptionOrder_Subscribe_logger = LogManager.getLogger(Option_Order_Subscribe.class);
	public static final Logger EquityOrder_Subscribe_logger = LogManager.getLogger(Equity_Order_Subscribe.class);
	public static final Logger Equity_Position_Subscribe_logger = LogManager.getLogger(Order_Positions_Subscribe.class);
	public static final Logger Flat_Position_logger = LogManager.getLogger(Flat_Positions.class);

////====================================================Equity MarketData APIs========================================================================================
	public static final Logger CompanyInformation_logger = LogManager.getLogger(CompanyInformation.class);
	public static final Logger Historicaldata_Snapshot_logger = LogManager.getLogger(Historicaldata_Snapshot.class);
	public static final Logger Subscribe_MarketData_logger = LogManager.getLogger(Subscribe_MarketData.class);
	public static final Logger Subscribe_Bulk_MarketData_logger = LogManager.getLogger(Subscribe_Bulk.class);
	public static final Logger UnSubscribe_Bulk_MarketData_logger = LogManager.getLogger(UnSubscribe_Bulk.class);
	public static final Logger UnSubscribe_MarketData_logger = LogManager.getLogger(UnSubscribe.class);

	
////====================================================Option MarketData APIs========================================================================================
	public static final Logger Option_Topic_MarketData_logger = LogManager.getLogger(Opt_Topic.class);
	public static final Logger Option_Subscribe_MarketData_logger = LogManager.getLogger(Opt_Subscribe.class);
	public static final Logger Option_Subscribe_Bulk_MarketData_logger = LogManager.getLogger(Opt_Subscribe_Bulk.class);
	public static final Logger Option_UnSubscribe_MarketData_logger = LogManager.getLogger(Opt_UnSubscribe.class);
	public static final Logger Option_UnSubscribe_Bulk_MarketData_logger = LogManager.getLogger(Opt_UnSubscribe_Bulk.class);
	public static final Logger Option_UnSubscribe_Bulk_NoSnapshot_logger = LogManager.getLogger(Opt_UnSubscribe_Bulk_NoSnapshot.class);
	public static final Logger Option_UnSubscribe_Bulk_Symbols_logger = LogManager.getLogger(Opt_UnSubscribe_Bulk_Symbols.class);
	
*/

	
/*	logger.debug("This is debug message");
		logger.info("This is info message");
		logger.warn("This is warn message");
		logger.fatal("This is fatal message");
		logger.error("This is error message");
	
*/

