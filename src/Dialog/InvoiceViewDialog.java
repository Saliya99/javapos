/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Dialog;


import controllers.ServerController;
import controllers.WebServiceController;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Oshan
 */
public class InvoiceViewDialog extends javax.swing.JDialog implements Printable{

    /**
     * Creates new form InvoiceViewDialog
     */
    Double bHeight=0.0;

    private DefaultTableModel dtm;
    public static int invoiceId;

    DecimalFormat df2 = new DecimalFormat( "#,###,###,##0.00" );
    private Thread InvoiceDataThread;
     private Thread ShopDetailsThread;

    public static String ShopName;
    public static String ShopEmail;
    public static String ShopTel;
    public static String ShopAddress;
    public static String ShopRoadName;
    public static String ShopCityName;
    public static String ShopCountryName;
    public double[] disArray;
    public double totalDouble = 0;
    public double totalPayment = 0;
    String totalPaymentFormatted;

    boolean pressed = false;

    public static InvoiceViewDialog invoiceViewDialog;

    public InvoiceViewDialog(java.awt.Frame parent, boolean modal, int invoiceId) {
        super(parent, modal);
        initComponents();
        invoiceViewDialog = this;
        InvoiceViewDialog.invoiceId =invoiceId;

        int InvoiceNumberTitle = invoiceId + 10000;

        setTitle("Client Management System | Invoice "+InvoiceNumberTitle+" | AMAZOFT");

        dtm = (DefaultTableModel) InvoiceItemTable.getModel();
        LoadInvoiceData();

        LoadShopData();

    }

    private void LoadShopData(){
        ShopDetailsThread = new Thread(new Runnable() {
        @Override
        public void run() {

//            try {
//                 JSONObject jo = ServerController.getJsonObject(WebServiceController.DOWNLOAD_SHOP_DETAILS, ServerController.getShopDetailsParams("1"));
//            if(jo != null){
//
//                if(jo.getBoolean("result")){
//
//
//                    JSONArray array = jo.getJSONArray("data");
//
//                    for(int i = 0; i<array.length() ;i++){
//
//                            JSONObject pd = array.getJSONObject(i);
//
//
//                            int ShopId = pd.getInt("ShopId");
//                            String ShopName = pd.getString("ShopName");
//                            String ShopEmail = pd.getString("ShopEmail");
//                            String ShopTel = pd.getString("ShopTel");
//                            String ShopAddress = pd.getString("ShopAddress");
//                            String ShopRoadName = pd.getString("ShopRoadName");
//                            String ShopCityName = pd.getString("ShopCityName");
//                            String ShopCountryName = pd.getString("ShopCountryName");
//
//                            InvoiceViewDialog.ShopName=ShopName;
//                            InvoiceViewDialog.ShopEmail=ShopEmail;
//                            InvoiceViewDialog.ShopTel=ShopTel;
//                            InvoiceViewDialog.ShopAddress=ShopAddress;
//                            InvoiceViewDialog.ShopRoadName=ShopRoadName;
//                            InvoiceViewDialog.ShopCityName=ShopCityName;
//                            InvoiceViewDialog.ShopCountryName=ShopCountryName;
//
//                    }

//                }else{
//                    System.out.println("error");
//                }
//
//            }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }


        }

        });

        ShopDetailsThread.start();

    }

    private void LoadInvoiceData(){

        System.out.println("downloading...");


        InvoiceDataThread = new Thread(new Runnable() {
        @Override
        public void run() {

            try {
                 JSONObject jo = ServerController.getJsonObject(WebServiceController.DOWNLOAD_INVOICE_Details, ServerController.getInvoiceAllDetailsParams(invoiceId));
            if(jo != null){

                if(jo.getBoolean("result")){

                    JSONArray array = jo.getJSONArray("data");
                    System.out.println(array);

                    for(int i = 0; i<array.length() ;i++){

                            JSONObject pd = array.getJSONObject(i);

                            String ClientsName = pd.getString("ClientName");
                            String ClientsAddress = pd.getString("ClientAddress");
                            totalDouble = pd.getDouble("DiscountPrice");
                            int InvoiceStat = pd.getInt("InvoiceStat");
                            String InvoiceDateTime = pd.getString("InvoiceDateTime");
                            int PayStat = pd.getInt("PayStat");

                            Double VatPrice = pd.getDouble("VatPrice");
                            Double DiscountPrice = pd.getDouble("DiscountPrice");
                            Double GrandTotal = pd.getDouble("GrandTotal");
                            DecimalFormat df = new DecimalFormat("#.00");
                            totalPaymentFormatted = df.format(GrandTotal - DiscountPrice);


                            int InvoiceNumber = invoiceId + 10000;

                            if(InvoiceStat == 0){
                                PaidBtn.setVisible(false);
                                PrintBtn.setVisible(false);
                                ReOpenBtn.setVisible(false);
                            }else{
                                PrintBtn.setVisible(true);
                                ReOpenBtn.setVisible(true);

								PaidBtn.setVisible(PayStat == 9);

                            }


                            InvoiceNumberLbl.setText("Invoice Number: "+InvoiceNumber);
                            ClientNameLbl.setText("Client Name: "+ClientsName);
                            ClientAddress.setText("Client Address: "+ClientsAddress);
                            InvoiceDateLbl.setText("Invoice Date: "+InvoiceDateTime);

                            VatTotalLbl.setText(df2.format(VatPrice));
                            DiscountTotalLbl.setText("(-"+df2.format(DiscountPrice)+")");
                            GrandTotalLbl.setText(df2.format(GrandTotal - DiscountPrice));
                             totalLbl.setText(df2.format(GrandTotal));


                    }



                    dtm = (DefaultTableModel) InvoiceItemTable.getModel();
                    dtm.setRowCount(0);

                    JSONArray arrayD = jo.getJSONArray("invoiceitemdataArray");
                    disArray = new double[arrayD.length()];


                    for(int p = 0; p<arrayD.length() ;p++){

                            JSONObject sd = arrayD.getJSONObject(p);

                            Double SellingPrice = sd.getDouble("SellingPrice");
                            int ProductCount = sd.getInt("CountView");
                            String ProductName = sd.getString("ProductName");
                            String ProductNumber = sd.getString("ProductNumber");
                            Double ItemSubTotal = sd.getDouble("ItemSubTotal");
                            disArray[p] = sd.getDouble("ItemDiscount");
                            String newQt = sd.getDouble("ProductQty")+"("+sd.getString("ProductType")+")";

                            String DisplayName = ProductName+"("+ProductNumber+")";

                            Vector v = new Vector();
                            v.add(ProductCount);
                            v.add(DisplayName);
                            v.add(newQt);
                            v.add(df2.format(SellingPrice));
                            v.add(df2.format(ItemSubTotal));

                            dtm.addRow(v);
                    }

                    DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
                    rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
                    InvoiceItemTable.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
                    InvoiceItemTable.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
                    InvoiceItemTable.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);




                }else{
                    System.out.println("error");
                }







            }



            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        });


        InvoiceDataThread.start();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Logo_etc_area = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        InvoiceNumberLbl = new javax.swing.JLabel();
        ClientNameLbl = new javax.swing.JLabel();
        ClientAddress = new javax.swing.JLabel();
        InvoiceDateLbl = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        Grn_item_area = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        InvoiceItemTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        GrandTotalLbl = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        DiscountTotalLbl = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        VatTotalLbl = new javax.swing.JLabel();
        totalLbl = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        PrintBtn = new javax.swing.JButton();
        ReOpenBtn = new javax.swing.JButton();
        PaidBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setMaximumSize(new java.awt.Dimension(32667, 32667));
        jPanel1.setPreferredSize(new java.awt.Dimension(479, 516));

        Logo_etc_area.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(0, 51, 153));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Invoice");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1)
        );

        InvoiceNumberLbl.setText("Invoice Number: ");

        ClientNameLbl.setText("Client Name:");

        ClientAddress.setText("Client Address");

        InvoiceDateLbl.setText("Invoice Date:");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Shop Contact Number");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Shop Address");

        javax.swing.GroupLayout Logo_etc_areaLayout = new javax.swing.GroupLayout(Logo_etc_area);
        Logo_etc_area.setLayout(Logo_etc_areaLayout);
        Logo_etc_areaLayout.setHorizontalGroup(
            Logo_etc_areaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(Logo_etc_areaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Logo_etc_areaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(InvoiceNumberLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ClientNameLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ClientAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(InvoiceDateLbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        Logo_etc_areaLayout.setVerticalGroup(
            Logo_etc_areaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Logo_etc_areaLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(InvoiceNumberLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ClientNameLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ClientAddress)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(InvoiceDateLbl)
                .addContainerGap())
        );

        Grn_item_area.setBackground(new java.awt.Color(255, 255, 255));

        InvoiceItemTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "#", "Item Name", "Qty", "Price", "Total"
            }
        ) {
            final boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(InvoiceItemTable);
        if (InvoiceItemTable.getColumnModel().getColumnCount() > 0) {
            InvoiceItemTable.getColumnModel().getColumn(0).setMinWidth(50);
            InvoiceItemTable.getColumnModel().getColumn(0).setPreferredWidth(50);
            InvoiceItemTable.getColumnModel().getColumn(0).setMaxWidth(50);
            InvoiceItemTable.getColumnModel().getColumn(1).setMinWidth(200);
            InvoiceItemTable.getColumnModel().getColumn(1).setPreferredWidth(200);
            InvoiceItemTable.getColumnModel().getColumn(1).setMaxWidth(200);
            InvoiceItemTable.getColumnModel().getColumn(2).setMinWidth(80);
            InvoiceItemTable.getColumnModel().getColumn(2).setPreferredWidth(80);
            InvoiceItemTable.getColumnModel().getColumn(2).setMaxWidth(80);
            InvoiceItemTable.getColumnModel().getColumn(3).setMinWidth(120);
            InvoiceItemTable.getColumnModel().getColumn(3).setPreferredWidth(120);
            InvoiceItemTable.getColumnModel().getColumn(3).setMaxWidth(120);
            InvoiceItemTable.getColumnModel().getColumn(4).setMinWidth(120);
            InvoiceItemTable.getColumnModel().getColumn(4).setPreferredWidth(120);
            InvoiceItemTable.getColumnModel().getColumn(4).setMaxWidth(120);
        }

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Helvetica Neue", 1, 36)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Total Net:");

        GrandTotalLbl.setFont(new java.awt.Font("Helvetica Neue", 1, 36)); // NOI18N
        GrandTotalLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        GrandTotalLbl.setText("0.00");

        jLabel6.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Discount:");

        DiscountTotalLbl.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        DiscountTotalLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        DiscountTotalLbl.setText("-(0.00)");

        jLabel7.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("VAT(0%):");

        VatTotalLbl.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        VatTotalLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        VatTotalLbl.setText("0.00");

        totalLbl.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        totalLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalLbl.setText("0.00");

        jLabel8.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Total:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(DiscountTotalLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(VatTotalLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(GrandTotalLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(totalLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(VatTotalLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(DiscountTotalLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(GrandTotalLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        PrintBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/printer.png"))); // NOI18N
        PrintBtn.setText("Print");
        PrintBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintBtnActionPerformed(evt);
            }
        });

        ReOpenBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/update.png"))); // NOI18N
        ReOpenBtn.setText("Re-Open");
        ReOpenBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReOpenBtnActionPerformed(evt);
            }
        });

        PaidBtn.setBackground(new java.awt.Color(204, 0, 0));
        PaidBtn.setForeground(new java.awt.Color(255, 255, 255));
        PaidBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/done.png"))); // NOI18N
        PaidBtn.setText("Pay");
        PaidBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PaidBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Grn_item_areaLayout = new javax.swing.GroupLayout(Grn_item_area);
        Grn_item_area.setLayout(Grn_item_areaLayout);
        Grn_item_areaLayout.setHorizontalGroup(
            Grn_item_areaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Grn_item_areaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Grn_item_areaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                    .addGroup(Grn_item_areaLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(ReOpenBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PrintBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(PaidBtn)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        Grn_item_areaLayout.setVerticalGroup(
            Grn_item_areaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Grn_item_areaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(Grn_item_areaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ReOpenBtn)
                    .addComponent(PrintBtn)
                    .addComponent(PaidBtn))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Logo_etc_area, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Grn_item_area, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(Logo_etc_area, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Grn_item_area, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 717, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 75, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

@Override
public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
    int r = InvoiceItemTable.getRowCount();
    Paper paper = pageFormat.getPaper();

    System.out.println("resdata");
    System.out.println(disArray[0]);

    // Set the page width to 80mm and height to a reasonable default (e.g., A4 height 297mm)
    double paperWidth = 80 * 2.83465; // 80mm to points conversion
    double paperHeight = 260 * 2.83465; // 297mm (A4) to points conversion
    double margin = 0.1; // Adjust margin if needed

    // Set the paper size and the printable area
    paper.setSize(paperWidth, paperHeight);
    paper.setImageableArea(margin, margin, paperWidth - (2 * margin), paperHeight - (2 * margin));
    pageFormat.setPaper(paper);

    // Dynamically adjust the content height based on pageIndex and total content length
    int result = NO_SUCH_PAGE;

    if (pageIndex == 0) {
        Graphics2D g2d = (Graphics2D) graphics;
        double x = pageFormat.getImageableX();
        double y = pageFormat.getImageableY();

        g2d.translate(x, y);

        try {
            int yPosition = 5; // Start position
            int yPosTwo = 50;
            int yShift = 10;
            int yShiftTwo = 12; // Shift per line
            int headerRectHeight = 15;

            //g2d.setFont(new Font("Monospaced", Font.PLAIN, 8));
            InputStream fontFile = getClass().getResourceAsStream("/Assets/Samantha Sinhala Font.TTF");
            try {
                Font sinhalaFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(10f);
                g2d.setFont(sinhalaFont);
            } catch (FontFormatException | IOException e) {
                e.printStackTrace();
            }


            // Print shop details (default strings)
            g2d.drawString("-----------------------------------------------", 5, yPosition); yPosition += yShift;

            fontFile = getClass().getResourceAsStream("/Assets/Samantha Sinhala Font.TTF");
            try {
                Font sinhalaFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(11f);
                g2d.setFont(sinhalaFont);
            } catch (FontFormatException | IOException e) {
                e.printStackTrace();
            }
            g2d.drawString("                  Wodrd fudag¾ia          ", 10, yPosition); yPosition += yShift;

            fontFile = getClass().getResourceAsStream("/Assets/Samantha Sinhala Font.TTF");
            try {
                Font sinhalaFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(10f);
                g2d.setFont(sinhalaFont);
            } catch (FontFormatException | IOException e) {
                e.printStackTrace();
            }
            g2d.drawString("                         wxl 257     ", 5, yPosition); yPosition += yShift;
            g2d.drawString("                      wUr¨j mdr      ", 5, yPosition); yPosition += yShift;
            g2d.drawString("                        je,sfõßh           ", 5, yPosition); yPosition += yShiftTwo;
            g2d.drawString("      033 225 9334 $ 077 787 0445           ", 5, yPosition); yPosition += yShift;

            g2d.setFont(new Font("Monospaced", Font.PLAIN, 8));
            g2d.drawString("-----------------------------------------------", 5, yPosition); yPosition += headerRectHeight;


            g2d.setFont(new Font("Poppins", Font.BOLD, 8));
            g2d.drawString(InvoiceViewDialog.invoiceViewDialog.InvoiceNumberLbl.getText(), 5, yPosition); yPosition += headerRectHeight;
            g2d.drawString(InvoiceViewDialog.invoiceViewDialog.InvoiceDateLbl.getText(), 5, yPosition); yPosition += headerRectHeight;
            g2d.setFont(new Font("Monospaced", Font.PLAIN, 8));
            g2d.drawString("------------------------------------------", 5, yPosition); yPosition += headerRectHeight;
            g2d.setFont(new Font("Arial", Font.BOLD, 8));
            g2d.drawString("Item    Qty               Price          Disc            Total   ", 5, yPosition);
            yPosition += yShift;

            g2d.setFont(new Font("Monospaced", Font.PLAIN, 8));
            g2d.drawString("------------------------------------------", 5, yPosition);
            yPosition += headerRectHeight;


            // Print each item in the receipt
             DecimalFormat formatter = new DecimalFormat("#,###.00");
            for (int s = 0; s < r; s++) {
            g2d.setFont(new Font("Poppins", Font.PLAIN, 8));

            String productName = InvoiceItemTable.getValueAt(s, 1).toString().substring(0, InvoiceItemTable.getValueAt(s, 1).toString().indexOf("-"));
            String itemCode = InvoiceItemTable.getValueAt(s, 1).toString().substring(
            InvoiceItemTable.getValueAt(s, 1).toString().indexOf("("),
            InvoiceItemTable.getValueAt(s, 1).toString().indexOf(")") + 1).trim();

            // Draw both in one line with space in between
            g2d.drawString(productName + " " + itemCode, 3, yPosition);



                yPosition += yShift;

                String itemV = InvoiceItemTable.getValueAt(s, 4).toString().replace(",", "");
                double itemTotal = Double.parseDouble(itemV); // Total column

                // Process the discount and net total
                double discount = disArray[s];
                double netTotal = itemTotal - discount;

                // Format the net total

                    String netTotalFormatted = formatter.format(netTotal);

                    // Draw on the receipt
                    g2d.drawString("" + InvoiceItemTable.getValueAt(s, 2), 20, yPosition); // Quantity
                    g2d.drawString("" + InvoiceItemTable.getValueAt(s, 3), 75, yPosition); // Unit Price
                    g2d.drawString("" + disArray[s], 120, yPosition); // Discount
                    g2d.drawString(netTotalFormatted, 160, yPosition); // Net Total

                yPosition += yShift + 2;
                g2d.setFont(new Font("Monospaced", Font.PLAIN, 8));
            g2d.drawString("-------------------------------------", 5, yPosition); yPosition += yShift;
            }

//            DecimalFormat formatter = new DecimalFormat("#,###.00");
            String discountFormatted = totalDouble == 0 ? "0.00" : formatter.format(totalDouble);
            String netTotalFormatted = formatter.format(Double.parseDouble(totalPaymentFormatted));

            g2d.setFont(new Font("Poppins", Font.BOLD, 8));
            g2d.drawString("Gross Total:                                         " + totalLbl.getText() + "   ", 5, yPosition); yPosition += yShift;
            g2d.drawString("Total Discount:                                      " + discountFormatted + "   ", 5, yPosition); yPosition += yShift;
            g2d.drawString("Net Total:                                              " + netTotalFormatted + "   ", 5, yPosition); yPosition += yShift;

            yPosition += yShift;
            g2d.setFont(new Font("Monospaced", Font.PLAIN, 8));
            g2d.drawString("-------------------------------------", 5, yPosition); yPosition += yShift;

            fontFile = getClass().getResourceAsStream("/Assets/Samantha Sinhala Font.TTF");
            try {
                Font sinhalaFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(8f);
                g2d.setFont(sinhalaFont);
            } catch (FontFormatException | IOException e) {
                e.printStackTrace();
            }

            g2d.drawString("            úl=Kq NdKav yqjudre l,yelafla", 5, yPosition); yPosition += yShift;
            g2d.drawString("    weiqreu fkdlvd ì,am; iu. Èk 2 la ;=< muKs", 5, yPosition); yPosition += yShift;
            g2d.drawString("            úÿ,suh NdKav yqjudre l, fkdyel", 5, yPosition); yPosition += yShift;


            g2d.setFont(new Font("Monospaced", Font.BOLD, 8));
            g2d.drawString("*************************************", 5, yPosition); yPosition += yShift;
            g2d.drawString("         THANK YOU COME AGAIN          ", 5, yPosition); yPosition += yShift;
            g2d.drawString("*************************************", 5, yPosition); yPosition += yShift;
            g2d.setFont(new Font("Arial", Font.BOLD, 8));
            g2d.drawString("                SOFTWARE BY: AMAZOFT           ", 5, yPosition); yPosition += yShift;
            g2d.drawString("                            076 441 5555           ", 5, yPosition); yPosition += yShift;
            g2d.drawString("                        www.amazoft.com          ", 5, yPosition); yPosition += yShift;

            // Adjust the paper height to fit the content
            if (yPosition > paperHeight - margin) {
                paper.setSize(paperWidth, yPosition + margin);
                pageFormat.setPaper(paper);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        result = PAGE_EXISTS;
    }
    return result;
}













    private void PrintBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrintBtnActionPerformed
         PrinterJob job = PrinterJob.getPrinterJob();
    PageFormat pf = job.defaultPage();
    Paper paper = pf.getPaper();

    // Set the paper size to 80mm wide and 297mm tall
    paper.setSize(80 * 2.8346, 297 * 2.8346); // Conversion from mm to points (1 mm = 2.8346 points)

    // Set the imageable area for the content (allowing some margins)
    paper.setImageableArea(0, 0, paper.getWidth(), paper.getHeight()); // Set margins

    pf.setPaper(paper); // Set the new paper settings to the PageFormat

    job.setPrintable(this, pf); // Set the Printable object with the new PageFormat

    try {
        job.print(); // Print directly without showing the dialog
    } catch (PrinterException ex) {
        System.out.println("PRINT_ERR"); // Error logging
        ex.printStackTrace(); // Print the stack trace for debugging
    }
    }//GEN-LAST:event_PrintBtnActionPerformed

    private void ReOpenBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReOpenBtnActionPerformed

        int confirmRemoveLabour = JOptionPane.showConfirmDialog(rootPane, "Are you sure to Re-Open this invoice?", "Confirm",JOptionPane.YES_NO_OPTION);
        if(confirmRemoveLabour == 0){

            new ReOpenInvoiceDialog(null, pressed, invoiceId).setVisible(true);

        }

    }//GEN-LAST:event_ReOpenBtnActionPerformed

    private void PaidBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PaidBtnActionPerformed

        int confirmRemoveLabour = JOptionPane.showConfirmDialog(rootPane, "Are you sure to payment complete this invoice?", "Confirm",JOptionPane.YES_NO_OPTION);
        if(confirmRemoveLabour == 0){

            try {
                JSONObject jo = ServerController.getJsonObject(WebServiceController.INVOICE_PAYED_DETAILS, ServerController.sendpayInvoiceParams(invoiceId));
                if(jo != null){

                    if(jo.getBoolean("result")){

                        JOptionPane.showMessageDialog(this, "Invoice pay successfully.");
                        LoadInvoiceData();

                    }else{
                        System.out.println("error");
                        JOptionPane.showMessageDialog(this, "Error paying invoice.");
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }//GEN-LAST:event_PaidBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InvoiceViewDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InvoiceViewDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InvoiceViewDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InvoiceViewDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                InvoiceViewDialog dialog = new InvoiceViewDialog(new javax.swing.JFrame(), true, 0);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ClientAddress;
    private javax.swing.JLabel ClientNameLbl;
    private javax.swing.JLabel DiscountTotalLbl;
    private javax.swing.JLabel GrandTotalLbl;
    private javax.swing.JPanel Grn_item_area;
    private javax.swing.JLabel InvoiceDateLbl;
    private javax.swing.JTable InvoiceItemTable;
    private javax.swing.JLabel InvoiceNumberLbl;
    private javax.swing.JPanel Logo_etc_area;
    private javax.swing.JButton PaidBtn;
    private javax.swing.JButton PrintBtn;
    private javax.swing.JButton ReOpenBtn;
    private javax.swing.JLabel VatTotalLbl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel totalLbl;
    // End of variables declaration//GEN-END:variables
}
