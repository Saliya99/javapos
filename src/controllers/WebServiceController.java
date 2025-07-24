/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

/**
 *
 * @author Oshan
 */
public class WebServiceController {


//     private static final String DOMAIN = "http://amazofttestcloud.com/clients/development/udara_development/udara_motors/java/client_management/script/";
     //private static final String DOMAIN = "http://amazofttestcloud.com/clients/prod/udara_motors/java/client_management/script/";
    //private static final String DOMAIN = "http://localhost/udara_motors/java/client_management/script/";
    private static final String DOMAIN = "http://localhost/smart_retailer_web/java/client_management/script/";
     //private static final String DOMAIN = "http://localhost/udara_motors-tm/udara_motors/java/client_management/script/";

    ////GET Customers//
    public static String DOWNLOAD_CLIENT_Details = DOMAIN+"download_client_details.php";
    public static String DOWNLOAD_CLIENT_LIST = DOMAIN+"download_all_client_list.php";
    public static String UPLOAD_CLIENT_DETAILS = DOMAIN+"send_client_details.php";
    public static String DOWNLOAD_CLIENT_INVOICE_LIST = DOMAIN+"download_client_invoice.php";
    public static String UPDATE_CLIENT_DETAILS = DOMAIN+"update_client_details.php";
    public static String DOWNLOAD_CLIENT_RECORDES = DOMAIN+"download_clients_record.php";
    public static String SAVE_CLIENT_STATUS = DOMAIN+"send_client_status.php";
    public static String ADD_CLIENT_RECORD = DOMAIN+"add_client_record.php";
    public static String GET_CLIENT_COUNT = DOMAIN+"stat_to_dashboard.php";


    ////Add Price Batch////
    public static String UPLOAD_NEW_PB_Details = DOMAIN+"add_item_price_batch.php";


    /////Product Details Dialog///
    public static String DOWNLOAD_PRODUCT_Details = DOMAIN+"download_product_details.php";
    public static String UPDATE_PRODUCT_Details = DOMAIN+"update_product_details.php";

    ///////GRN Invoice//////
    public static String UPLOAD_GRN_ITEM = DOMAIN+"add_grn_item.php";
    public static String DOWNLOAD_PB_Details = DOMAIN+"download_PB_this_item.php";
    public static String DOWNLOAD_GRN_Details = DOMAIN+"download_grn_details.php";
    public static String DOWNLOAD_ALL_STOCK = DOMAIN+"download_all_stock.php";



    ///ADD Proucts Details///
    public static String SEND_PRODUCT_DETAILS = DOMAIN+"send_product_details.php";
    public static String ADD_PRODUCT_DETAILS = DOMAIN+"add_temp_product.php";

    ///GET GRN///
    public static String DOWNLOAD_ALL_SUPPLIERS_LIST = DOMAIN+"download_all_supplier_list.php";
    public static String CREATE_GRN_DETAILS = DOMAIN+"create_grn_details.php";

    ///All Products///
    public static String DOWNLOAD_ALL_PRODUCTS = DOMAIN+"download_all_product.php";

    ///Seach Products///
    public static String DOWNLOAD_SEARCH_PRODUCTS = DOMAIN+"download_search_product.php";



    ///Stock View///
    public static String DOWNLOAD_STOCK_VIEW = DOMAIN+"download_all_stock.php";


    ///Check GRN Stat//
    public static String CHECK_GRN_STAT = DOMAIN+"check_grn.php";

    ///Delete GRN Item////
    public static String DELETE_GRN_ITEM = DOMAIN+"delete_grn_item.php";


    //Finalize GRN//
    public static String FINALIZE_GRN = DOMAIN+"finalize_grn.php";

    //Get GRN List//
    public static String DOWNLOAD_GRN_LIST = DOMAIN+"download_grn_list.php";


    //Get INVOICE List//
    public static String DOWNLOAD_INVOICE_LIST = DOMAIN+"download_invoice_list.php";

    //Get INVOICE Details//
    public static String DOWNLOAD_INVOICE_Details = DOMAIN+"download_invoice_details.php";
    public static String GET_INVOICE_Details = DOMAIN+"get_invoice_data.php";
    public static String DOWNLOAD_SUB_PRODUCTS_Details = DOMAIN+"download_sub_products_details.php";
    public static String INVOICE_REOPEN_DETAILS = DOMAIN+"invoice_reopen.php";
    public static String INVOICE_PAYED_DETAILS = DOMAIN+"invoice_pay_update.php";
    public static String GET_LATEST_PRICE = DOMAIN+"get_product_latest_price.php";

    //Check Invoice//
    public static String CHECK_INVOICE_STAT = DOMAIN+"check_invoice.php";

    //Delete Invoice data//
    public static String DELETE_INVOICE_DATA = DOMAIN+"delete_invoice.php";

    //Invoice Crate//
    public static String ADD_ITEM_TO_CART = DOMAIN+"add_item_to_cart.php";
    public static String UPDATE_ITEM_QTY = DOMAIN+"update_item_qty_cart.php";
    public static String REMOVE_ITEM_QTY = DOMAIN+"remove_item_qty_cart.php";
    public static String UPDATE_DISCOUNT_INVOICE = DOMAIN+"update_invoice_discount.php";
    public static String UPDATE_VAT_INVOICE = DOMAIN+"update_invoice_vat.php";
    public static String SAVE_INVOICE_DETAILS = DOMAIN+"save_invoice.php";
    public static String GET_INVOICE_DISCOUNT = DOMAIN+"get_invoice_discount.php";

    //Invoice Type Create
    public static String CREATE_INVOICE_TYPE_DETAILS = DOMAIN+"open_invoice.php";

    //Reminder Create
    public static String ADD_REMINDER_RECORD = DOMAIN+"add_reminder.php";
    public static String DOWNLOAD_USER_REMINDERS = DOMAIN+"download_reminder.php";


    //Settings Page
    public static String UPDATE_USER_NAME = DOMAIN+"update_user_account_name.php";
    public static String UPDATE_USER_CONTACT_NUMBER = DOMAIN+"update_user_account_contact_number.php";
    public static String UPDATE_USER_PASSWORD = DOMAIN+"update_user_account_password.php";
    public static String DOWNLOAD_USER_ACCOUNT_LIST = DOMAIN+"download_user_accounts.php";
    public static String CREATE_USER_ACCOUNT = DOMAIN+"create_user_accounts.php";
    public static String UPDATE_SHOP_DETAILS = DOMAIN+"update_shop_details.php";
    public static String DOWNLOAD_VAT_DETAILS = DOMAIN+"download_vat_details.php";
    public static String UPDATE_VAT_DETAILS = DOMAIN+"update_vat_details.php";

    //Supplier Page
    public static String SAVE_SUPPLIER_DETAILS = DOMAIN+"send_supplier.php";


    //Report Page
    public static String DOWNLOAD_TODAY_REPORT = DOMAIN+"download_today_report.php";
    public static String DOWNLOAD_ALL_REPORT = DOMAIN+"download_all_report.php";

    //Shop Details
    public static String DOWNLOAD_SHOP_DETAILS = DOMAIN+"download_shop_details.php";

    //All Projects
    public static String DOWNLOAD_ALL_PROJECTS = DOMAIN+"download_all_projects.php";

    //Started Projects
    public static String DOWNLOAD_STARTED_PROJECTS = DOMAIN+"download_started_projects.php";

    //Project Details
    public static String DOWNLOAD_PROJECT_DETAILS = DOMAIN+"download_project_details.php";

    //Project Register
    public static String SEND_PROJECT_DETAILS = DOMAIN+"send_project_details.php";


}
