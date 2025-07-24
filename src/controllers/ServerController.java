/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static Dialog.AddGRNItemPBDialog.ItemId;
import static Dialog.AddGRNItemPBDialog.grnId;
import static Dialog.InvoiceProductDialog.productnumber;
import static Dialog.InvoiceViewDialog.invoiceId;
import static Dialog.ProductDetailsDialog.productId;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Oshan Premachandra
 */
public class ServerController {

    //Script Request area

    //Get All Customers
    public static HashMap<String, String> getClientListParams(String value){
           HashMap<String,String> map = new HashMap<>();
           map.put("v", value);

           return map;
    }

    //Get stat to dashboard
    public static HashMap<String, String> getDashboardStat(String keyword){
        HashMap<String,String> map = new HashMap<>();
        map.put("reqType", keyword);
        return map;
    }



    //Get All Customers
    public static HashMap<String, String> getClientDetailsParams(String value){
           HashMap<String,String> map = new HashMap<>();
           map.put("client_id", value);

           return map;
    }


    //Send Client data to DB
    public static HashMap<String, String> sendClientDetailsParams(String ClientType, String FirstName, String LastName, String CompanyName, String BirthdayView, String Nic, String Email, String ContactNumber, String ClientCat, String HowtoKnow, String Address, String UserId){
           HashMap<String,String> map = new HashMap<>();
           map.put("client_type", ClientType);
           map.put("f_name", FirstName);
           map.put("l_name", LastName);
           map.put("comp_name", CompanyName);
           map.put("birth_day", BirthdayView);
           map.put("nic_passport", Nic);
           map.put("email", Email);
           map.put("contact_number", ContactNumber);
           map.put("client_cat", ClientCat);
           map.put("how_to_know", HowtoKnow);
           map.put("address", Address);
           map.put("user_id", UserId);

           return map;
    }

    //Get All Stock
    public static HashMap<String, String> getStockParams(String value){
           HashMap<String,String> map = new HashMap<>();
           map.put("s", value);

           return map;
    }

    //Get All Products
    public static HashMap<String, String> getProductsParams(String value){
           HashMap<String,String> map = new HashMap<>();
           map.put("s", value);

           return map;
    }


    //Get Product Details
    public static HashMap<String, String> getProductDetailsParams(int productId){
           HashMap<String,String> map = new HashMap<>();
           map.put("product_id", productId+"");

           return map;
    }


    //Update Product data to DB
    public static HashMap<String, String> sendUpdateProductDetailsParams(String ProductName, String ProductNumber, String ProductLocation, String ProductDetails, int productId){
           HashMap<String,String> map = new HashMap<>();
           map.put("product_name", ProductName);
           map.put("product_number", ProductNumber);
           map.put("product_location", ProductLocation);
           map.put("product_details", ProductDetails);
           map.put("product_id", productId+"");

           return map;
    }


    //Add Product data to DB
    public static HashMap<String, String> sendProductDataParams(String ProductName, String ProductNumber, String ProductLocation, String ProductDetails){
           HashMap<String,String> map = new HashMap<>();
           map.put("product_name", ProductName);
           map.put("product_number", ProductNumber);
           map.put("product_location", ProductLocation);
           map.put("product_details", ProductDetails);

           return map;
    }


    //Add Product data to DB
    public static HashMap<String, String> getSearchProductsParams(String SearchName){
           HashMap<String,String> map = new HashMap<>();
           map.put("search_value", SearchName);
           map.put("search_cat_name", "All");

           return map;
    }

     //GET latest price
    public static HashMap<String, String> getLatestPrice(String productId){
           HashMap<String,String> map = new HashMap<>();
           map.put("product_id", productId);

           return map;
    }


    //Get Suppliers
    public static HashMap<String, String> getSupplierListParams(String value){
           HashMap<String,String> map = new HashMap<>();
           map.put("s", value);

           return map;
    }


    //Add Product data to DB
    public static HashMap<String, String> sendGRNDataParams(String SupplierName, String InvoiceNumber, String GRNNumber, String GRNNote, String GRNDateView){
           HashMap<String,String> map = new HashMap<>();
           map.put("supplier_name", SupplierName);
           map.put("invoice_number", InvoiceNumber);
           map.put("grn_number", GRNNumber);
           map.put("grn_note", GRNNote);
           map.put("grn_date", GRNDateView);

           return map;
    }


    //Check GRN
    public static HashMap<String, String> getGrnCheckParams(String value){
           HashMap<String,String> map = new HashMap<>();
           map.put("s", value);

           return map;
    }



    //Get GRN Details
    public static HashMap<String, String> getGRNDetailsParams(int grnId){
           HashMap<String,String> map = new HashMap<>();
           map.put("GrnId", grnId+"");

           return map;
    }

    //Get PB Details
    public static HashMap<String, String> getPriceBatchListParams(int ItemId){
           HashMap<String,String> map = new HashMap<>();
           map.put("ItemId", ItemId+"");

           return map;
    }

    //Add Price Batch data to DB
    public static HashMap<String, String> sendNewPriceBatchParams(String CostPrice, String SellingPrice,int ItemId,int grnId){
           HashMap<String,String> map = new HashMap<>();
           map.put("cost_price", CostPrice);
           map.put("selling_price", SellingPrice);
           map.put("item_id", ItemId+"");
           map.put("grn_id", grnId+"");

           return map;
    }

    //Add New GRN Item data to DB
    public static HashMap<String, String> sendNewItemToGRNParams(String CostPrice, String SellingPrice, String Quantity,int ItemId, int ItemPBId,int grnId){
           HashMap<String,String> map = new HashMap<>();
           map.put("cost_price", CostPrice);
           map.put("selling_price", SellingPrice);
           map.put("qty", Quantity);
           map.put("item_id", ItemId+"");
           map.put("item_pb_id", ItemPBId+"");
           map.put("grn_id", grnId+"");

           return map;
    }


    //Add New GRN Item data to DB
    public static HashMap<String, String> deleteItemGRNParams(Double itemqty, int pbid, int grnitemid){
           HashMap<String,String> map = new HashMap<>();
           map.put("item_qty", itemqty+"");
           map.put("pb_id", pbid+"");
           map.put("grn_item_id", grnitemid+"");

           return map;
    }


    //Add New GRN Item data to DB
    public static HashMap<String, String> finalizeGRNParams(int grnId){
           HashMap<String,String> map = new HashMap<>();
           map.put("grn_id", grnId+"");

           return map;
    }

    //GET GRN list
    public static HashMap<String, String> getGrnListParams(String value){
           HashMap<String,String> map = new HashMap<>();
           map.put("g", value);

           return map;
    }


    //GET INVOICE list
    public static HashMap<String, String> getInvoiceListParams(String value){
           HashMap<String,String> map = new HashMap<>();
           map.put("I", value);

           return map;
    }

    //GET INVOICE list
    public static HashMap<String, String> getInvoiceAllDetailsParams(int invoiceId){
           HashMap<String,String> map = new HashMap<>();
           map.put("invoice_id", invoiceId+"");

           return map;
    }


    //GET INVOICE list
    public static HashMap<String, String> getSubProductsDetailsParams(String productnumber){
           HashMap<String,String> map = new HashMap<>();
           map.put("product_number", productnumber);

           return map;
    }


    //Check Invoice
    public static HashMap<String, String> getInvoiceCheckParams(String UserId){
           HashMap<String,String> map = new HashMap<>();
           map.put("user_id", UserId);

           return map;
    }

    //Check discount
    public static HashMap<String, String> getItemDiscount(String itemName){
           HashMap<String,String> map = new HashMap<>();
           map.put("inItemName", itemName);

           return map;
    }

    //Add New Item to invoice cart
    public static HashMap<String, String> sendItemAddToCartDataParams(int invoiceId, String UserId, int productid,String DataValue, String itemDiscount, String sellingPrice, String reqType){
           HashMap<String,String> map = new HashMap<>();
           map.put("invoice_id", invoiceId+"");
           map.put("user_id", UserId);
           map.put("product_id", productid+"");
           map.put("qty", DataValue);
           map.put("discount", itemDiscount);
           map.put("sellPrice", sellingPrice);
           map.put("reqType", reqType );
           return map;
    }

    //Add New product for temp
    public static HashMap<String, String> saveTempProductData(String productName, String sellPrice){
           HashMap<String,String> map = new HashMap<>();
           map.put("product_name", productName);
           map.put("sell_price", sellPrice );
           return map;
    }

    //Add Update Item to invoice cart
    public static HashMap<String, String> sendUpdateItemCartDataParams(int invoiceId, int PriceBatchId, int InvoiceProductId,Double InvoiceProductQty, Double newDiscount, String DataValue, String UserId){
           HashMap<String,String> map = new HashMap<>();
           map.put("invoice_id", invoiceId+"");
           map.put("price_batch_id", PriceBatchId+"");
           map.put("invoice_product_id", InvoiceProductId+"");
           map.put("product_qty", InvoiceProductQty+"");
           map.put("product_discount", newDiscount+"");
           map.put("qty", DataValue);
           map.put("user_id", UserId);

           return map;
    }

    //Remove Item From invoice cart
    public static HashMap<String, String> sendRemoveItemCartDataParams(int invoiceId, int PriceBatchId, int InvoiceProductId,Double InvoiceProductQty){
           HashMap<String,String> map = new HashMap<>();
           map.put("invoice_id", invoiceId+"");
           map.put("price_batch_id", PriceBatchId+"");
           map.put("invoice_product_id", InvoiceProductId+"");
           map.put("product_qty", InvoiceProductQty+"");

           return map;
    }

        //cancel invoice
    public static HashMap<String, String> deleteInvoice(int invoiceId){
           HashMap<String,String> map = new HashMap<>();
           map.put("invoice_id", invoiceId+"");

           return map;
    }

    //Update Discount
    public static HashMap<String, String> sendDiscountInvoiceParams(int invoiceId, String DataValue){
           HashMap<String,String> map = new HashMap<>();
           map.put("invoice_id", invoiceId+"");
           map.put("discount_value", DataValue);

           return map;
    }

    //Vat Discount
    public static HashMap<String, String> sendVatInvoiceParams(int invoiceId, String DataValue){
           HashMap<String,String> map = new HashMap<>();
           map.put("invoice_id", invoiceId+"");
           map.put("vat_value", DataValue);

           return map;
    }

    //Save Invoice
    public static HashMap<String, String> sendSaveInvoiceParams(int invoiceId, String UserId, String ReceptType){
           HashMap<String,String> map = new HashMap<>();
           map.put("invoice_id", invoiceId+"");
           map.put("user_id", UserId);
           map.put("recept_type", ReceptType);

           return map;
    }

    //Re-Open Invoice
    public static HashMap<String, String> sendReOpenInvoiceParams(int invoiceId, String UserId){
           HashMap<String,String> map = new HashMap<>();
           map.put("invoice_id", invoiceId+"");
           map.put("user_id", UserId);

           return map;
    }

    //Create Invoice
    public static HashMap<String, String> sendNewInvoiceParams(String InvoiceType, String ClientName, String ClientEmail, String ClientTel, int ClientId, String UserId){
           HashMap<String,String> map = new HashMap<>();
           map.put("invoice_type", InvoiceType);
           map.put("client_name", ClientName);
           map.put("client_email", ClientEmail);
           map.put("client_tel", ClientTel);
           map.put("client_id", ClientId+"");
           map.put("user_id", UserId);

           return map;
    }

    //Pay Invoice
    public static HashMap<String, String> sendpayInvoiceParams(int invoiceId){
           HashMap<String,String> map = new HashMap<>();
           map.put("invoice_id", invoiceId+"");

           return map;
    }


    //Download Client Invoice
    public static HashMap<String, String> getClientInvoiceListParams(String ClientId){
           HashMap<String,String> map = new HashMap<>();
           map.put("client_id", ClientId);

           return map;
    }


    //Update Client Details
    public static HashMap<String, String> updateClientDetailsParams(String ClientId, String FirstName, String LastName, String Nic, String Email, String ContactNumber, String ClientCat, String Address, String JobDesignation, String WorkPlace, String WorkPlaceAddress, String UserId){
           HashMap<String,String> map = new HashMap<>();
           map.put("client_id", ClientId);
           map.put("f_name", FirstName);
           map.put("l_name", LastName);
           map.put("nic", Nic);
           map.put("email", Email);
           map.put("con_number", ContactNumber);
           map.put("client_cat", ClientCat);
           map.put("address", Address);
           map.put("job_description", JobDesignation);
           map.put("work_place", WorkPlace);
           map.put("work_place_address", WorkPlaceAddress);
           map.put("user_id", UserId);

           return map;
    }

    //Download Client Records
    public static HashMap<String, String> getClientRecordsParams(String ClientId){
           HashMap<String,String> map = new HashMap<>();
           map.put("client_id", ClientId);

           return map;
    }


    //Download Client Status
    public static HashMap<String, String> sendSaveClientRecordsParams(String ClientId, String Status, String StatusViewColor){
           HashMap<String,String> map = new HashMap<>();
           map.put("client_id", ClientId);
           map.put("status", Status);
           map.put("status_color", StatusViewColor);

           return map;
    }

    //Download Client Records
    public static HashMap<String, String> addRecordsParams(String ClientId, String ClientRecord){
           HashMap<String,String> map = new HashMap<>();
           map.put("client_id", ClientId);
           map.put("client_record", ClientRecord);

           return map;
    }

    //Send Reminder
    public static HashMap<String, String> addReminderParams(String UserId, String Subject, String Reminder){
           HashMap<String,String> map = new HashMap<>();
           map.put("user_id", UserId);
           map.put("subject", Subject);
           map.put("reminder", Reminder);

           return map;
    }


    //Downloadn User Reminder
    public static HashMap<String, String> getdownloadUserRemindersParams(String UserId){
           HashMap<String,String> map = new HashMap<>();
           map.put("user_id", UserId);

           return map;
    }

    //Update User Name
    public static HashMap<String, String> updateUserNameParams(String UserId, String UserName){
           HashMap<String,String> map = new HashMap<>();
           map.put("user_id", UserId);
           map.put("user_name", UserName);

           return map;
    }

    //Update User Contact Number
    public static HashMap<String, String> updateUserContactNumberParams(String UserId, String UserContactNumber){
           HashMap<String,String> map = new HashMap<>();
           map.put("user_id", UserId);
           map.put("user_contact_number", UserContactNumber);

           return map;
    }

    //Update User Password
    public static HashMap<String, String> updateUserPasswordParams(String UserId, String NewPassword){
           HashMap<String,String> map = new HashMap<>();
           map.put("user_id", UserId);
           map.put("password", NewPassword);

           return map;
    }

    //Update User Password
    public static HashMap<String, String> getUserAccountListParams(String UserId){
           HashMap<String,String> map = new HashMap<>();
           map.put("user_id", UserId);

           return map;
    }


    //Create User
    public static HashMap<String, String> createUserAccountParams(String UserName, String UserEmail, String UserTel, String UserPassword, String UserRole){
           HashMap<String,String> map = new HashMap<>();
           map.put("user_name", UserName);
           map.put("user_email", UserEmail);
           map.put("user_tel", UserTel);
           map.put("user_password", UserPassword);
           map.put("user_role", UserRole);

           return map;
    }

    //Create User
    public static HashMap<String, String> sendSupplierDetailsParams(String SupplierName, String ContactNumber, String Address){
           HashMap<String,String> map = new HashMap<>();
           map.put("supplier_name", SupplierName);
           map.put("contact_number", ContactNumber);
           map.put("address", Address);

           return map;
    }

    //Today Report
    public static HashMap<String, String> getTodayReportListParams(String value){
           HashMap<String,String> map = new HashMap<>();
           map.put("r", value);

           return map;
    }

    //All Report
    public static HashMap<String, String> getAllReportListParams(String StartDateN, String EndDateN){
           HashMap<String,String> map = new HashMap<>();
           map.put("start_date", StartDateN);
           map.put("end_date", EndDateN);

           return map;
    }

    //Shop Details
    public static HashMap<String, String> getShopDetailsParams(String value){
           HashMap<String,String> map = new HashMap<>();
           map.put("s", value);

           return map;
    }

    //Update Shop Details
    public static HashMap<String, String> sendShopDetailsParams(String ShopName, String ShopEmail, String ShopTel, String ShopAddress, String ShopRoadName, String ShopCityName, String ShopCountryName){
           HashMap<String,String> map = new HashMap<>();
           map.put("shop_name", ShopName);
           map.put("shop_email", ShopEmail);
           map.put("shop_tel", ShopTel);
           map.put("shop_address", ShopAddress);
           map.put("shop_road_name", ShopRoadName);
           map.put("shop_city_name", ShopCityName);
           map.put("shop_country_name", ShopCountryName);

           return map;
    }

    //Download Vat Data
    public static HashMap<String, String> getVatDetailsParams(String value){
           HashMap<String,String> map = new HashMap<>();
           map.put("v", value);

           return map;
    }

    //Update Vat Details
    public static HashMap<String, String> sendVatDetailsParams(String NewVatRate){
           HashMap<String,String> map = new HashMap<>();
           map.put("vat_rate", NewVatRate);

           return map;
    }

    //Download All Projects
    public static HashMap<String, String> getProjectsListParams(String value){
           HashMap<String,String> map = new HashMap<>();
           map.put("v", value);

           return map;
    }

    //Download Started Projects
    public static HashMap<String, String> getProjectsStartedListParams(String UserId){
           HashMap<String,String> map = new HashMap<>();
           map.put("user_id", UserId);

           return map;
    }

    //Download Project Details
    public static HashMap<String, String> getProjectDetailsParams(String ProjectId){
           HashMap<String,String> map = new HashMap<>();
           map.put("project_id", ProjectId);

           return map;
    }

    //Send Project Details
    public static HashMap<String, String> sendProjectDetailsParams(String ClientId, String ProjectName, String ProjectNumber, String StartDateN, String EndDateN, String Note, String UserId){
           HashMap<String,String> map = new HashMap<>();
           map.put("client_id", ClientId);
           map.put("project_name", ProjectName);
           map.put("project_number", ProjectNumber);
           map.put("start_date", StartDateN);
           map.put("end_date", EndDateN);
           map.put("note", Note);
           map.put("user_id", UserId);

           return map;
    }


    //End Script Request area

    public static JSONObject getJsonObject(String url, HashMap<String, String> params) {

        StringBuilder sbParams = new StringBuilder();
        StringBuilder result = new StringBuilder();
        String charset = "UTF-8";
        HttpURLConnection conn = null;
        JSONObject jObj = null;
        URL urlObj = null;
        DataOutputStream wr = null;

        int i = 0;
        for (String key : params.keySet()) {
            try {
                if (i != 0) {
                    sbParams.append("&");
                }
                sbParams.append(key).append("=")
                        .append(URLEncoder.encode(params.get(key), charset));

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            i++;
        }

        System.out.println("params: " + sbParams);


        try {
            urlObj = new URL(url);

            conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept-Charset", charset);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setRequestProperty("User-Agent", "");








            conn.setReadTimeout(600000);
            conn.setConnectTimeout(600000);

            conn.connect();

            String paramsString = sbParams.toString();


            wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(paramsString);
            wr.flush();
            wr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            InputStream inn = null;
            try {
                inn = conn.getInputStream();
            } catch (Exception e) {
                System.out.println("server not found" + e);
            }
            if (inn != null) {
                InputStream in = new BufferedInputStream(inn);
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            } else {

                try {
                    String line = "{ \"response\" : -2 }";

                    result.append(line);


                } catch (Exception e) {

                }


            }

            System.out.println("result: " + result);

        } catch (IOException e) {
            e.printStackTrace();
        }

        conn.disconnect();

        // try parse the string to a JSON object
        try {



            jObj = new JSONObject(result.toString());
        } catch (JSONException e) {
            System.out.println("Error parsing data " + e);
            System.out.println(result);
        }

        // return JSON Object
        return jObj;
    }





}
