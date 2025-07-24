/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Pages;

import Dialog.AddGRNItemPBDialog;
import static Pages.StockNav.StockPageChangeArea;
import smartretailer.Home;
import controllers.ServerController;
import controllers.WebServiceController;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import modals.PriceBatch;
import modals.Product;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Oshan
 */
public class StockGRNInvoiceCreate extends javax.swing.JPanel {


    /**
     * Creates new form StockGRNInvoiceCreate
     */
    private int grnId;

    private static Thread grndetailsThread;
    private static Thread allstockThread;
    private static Thread getPBThread;

    private DefaultTableModel dtm;

    boolean pressed = false;

    DecimalFormat df2 = new DecimalFormat( "#,###,###,##0.00" );
    private static int ItemId;
    private static int ItemPBId;

    public StockGRNInvoiceCreate() {
        initComponents();

        AutoCompleteDecorator.decorate(PartNumberField);

        CostField.setEditable(false);
        SellingField.setEditable(false);
    }

    public void setGrnId(int grnId){
        this.grnId = grnId;


        System.out.println("received "+grnId);

        GrnNumberLbl.setText("GRN Number - "+grnId);

        dtm = (DefaultTableModel) GrnItemTable.getModel();
        LoadGrnData(grnId);

        LoadAllStockData();
    }

    public static void showPanel(JPanel panel){

        StockPageChangeArea.removeAll();
        StockPageChangeArea.add(panel);
        panel.setVisible(true);
        StockPageChangeArea.repaint();

    }

    private void LoadGrnData(int grnId){

        System.out.println("downloading...");


        grndetailsThread = new Thread(new Runnable() {
        @Override
        public void run() {

            try {
                 JSONObject jo = ServerController.getJsonObject(WebServiceController.DOWNLOAD_GRN_Details, ServerController.getGRNDetailsParams(grnId));
            if(jo != null){

                if(jo.getBoolean("result")){

                    JSONArray array = jo.getJSONArray("data");

                    for(int i = 0; i<array.length() ;i++){

                            JSONObject pd = array.getJSONObject(i);

                            String InvoiceNumber = pd.getString("InvoiceNumber");
                            String GrnNumber = pd.getString("GrnNumber");
                            String GodsRecevedDate = pd.getString("GodsRecevedDate");
                            String GRNNote = pd.getString("GRNNote");
                            String GRNStat = pd.getString("GRNStat");
                            String GRNDateTime = pd.getString("GRNDateTime");
                            String SupplierName = pd.getString("SupplierName");
                            String SupplierAddress = pd.getString("SupplierAddress");
                            String SupplierContactNumber = pd.getString("SupplierContactNumber");



                            InvoiceNumberLbl.setText("Invoice Number - "+InvoiceNumber);
                            GRDateLbl.setText("GR Date - "+GRNDateTime);
                            SupplierNameLbl.setText(SupplierName);
                            SupplierContactNumberLbl.setText(SupplierContactNumber);
                            SupplierAddressLbl.setText(SupplierAddress);


                    }



                    dtm = (DefaultTableModel) GrnItemTable.getModel();
                    dtm.setRowCount(0);

                    JSONArray arrayD = jo.getJSONArray("GRNItemdata");


                    for(int p = 0; p<arrayD.length() ;p++){

                            JSONObject sd = arrayD.getJSONObject(p);

                            int GRNItemId = sd.getInt("GRNItemId");
                            int ItemId = sd.getInt("ItemId");
                            int PriceBatchId = sd.getInt("PBId");
                            Double Quantity = sd.getDouble("Quantity");
                            Double ProductCost = sd.getDouble("ProductCost");
                            Double ProductSelling = sd.getDouble("ProductSelling");
                            int ProductCount = sd.getInt("CountView");
                            String ProductName = sd.getString("ProductName");
                            String ProductNumber = sd.getString("ProductNumber");

                            String DisplayName = ProductName+" ("+ProductNumber+")";

                            Vector v = new Vector();
                            v.add(ProductCount);
                            v.add(DisplayName);
                            v.add(Quantity);
                            v.add(df2.format(ProductCost));
                            v.add(ItemId);
                            v.add(PriceBatchId);
                            v.add(GRNItemId);

                            dtm.addRow(v);
                    }

//                    DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
//                    rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
//                    GrnItemTable.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);








                }else{
                    System.out.println("error");
                }







            }

            ///Get Track data




            ///





            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        });


        grndetailsThread.start();

    }

    private void LoadAllStockData(){
        dtm.setRowCount(0);
        //SupplierSelectField.removeAllItems();

        PartNumberField.addItem(new Product(0, "PLEASE SELECT A PART",""));


        allstockThread = new Thread(new Runnable() {
        @Override
        public void run() {

            try {
                 JSONObject jo = ServerController.getJsonObject(WebServiceController.DOWNLOAD_ALL_STOCK, ServerController.getStockParams("1"));
            if(jo != null){

                if(jo.getBoolean("result")){


                    JSONArray array = jo.getJSONArray("data");

                    for(int i = 0; i<array.length() ;i++){

                            JSONObject pd = array.getJSONObject(i);

                            int ProductId = pd.getInt("ProductId");
                            String ProductName = pd.getString("ProductName");
                            String ProductLocation = pd.getString("ProductLocation");
                            String ProductNumber = pd.getString("ProductNumber");
                            String ProductDetails = pd.getString("ProductDetails");
                            Double ProductQuantity = pd.getDouble("ProductQuantity");
                            Double ProductCost = pd.getDouble("ProductCost");
                            Double ProductSelling = pd.getDouble("ProductSelling");



                            Product product = new Product();
                            product.setProductId(ProductId);
                            product.setProductCost();
                            product.setProductDetails();
                            product.setProductLocation();
                            product.setProductName(ProductName);
                            product.setProductNumber(ProductNumber);
                            product.setProductQty();
                            product.setProductSelling();




                            PartNumberField.addItem(product);

                            //PartNumberField.addItem(ProductNumber);

                    }



                }else{
                    System.out.println("error");
                }

            }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        });


        allstockThread.start();

    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        page_change_here = new javax.swing.JPanel();
        GRN_invoice_create_area = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        GrnNumberLbl = new javax.swing.JLabel();
        InvoiceNumberLbl = new javax.swing.JLabel();
        GRDateLbl = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        SupplierNameLbl = new javax.swing.JLabel();
        SupplierContactNumberLbl = new javax.swing.JLabel();
        SupplierAddressLbl = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        GrnItemTable = new javax.swing.JTable();
        FinalizeGrnBtn = new javax.swing.JButton();
        RemoveGrnItemBtn = new javax.swing.JButton();
        LocalJpanel = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        PartNumberField = new javax.swing.JComboBox<>();
        jLabel29 = new javax.swing.JLabel();
        PriceBatchField = new javax.swing.JComboBox<>();
        AddLocalPBBtn = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        CostField = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        SellingField = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        QtyField = new javax.swing.JTextField();
        AddGrnItemLocalBtn = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        stockGRNList1 = new Pages.StockGRNList();

        setPreferredSize(new java.awt.Dimension(1162, 810));

        page_change_here.setPreferredSize(new java.awt.Dimension(1162, 810));
        page_change_here.setLayout(new java.awt.CardLayout());

        jPanel2.setPreferredSize(new java.awt.Dimension(1162, 810));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        GrnNumberLbl.setFont(new java.awt.Font("Helvetica Neue", 0, 15)); // NOI18N
        GrnNumberLbl.setText("GRN Number - BAE/GRN/2022/10174");

        InvoiceNumberLbl.setFont(new java.awt.Font("Helvetica Neue", 0, 15)); // NOI18N
        InvoiceNumberLbl.setText("Invoice Number - 321");

        GRDateLbl.setFont(new java.awt.Font("Helvetica Neue", 0, 15)); // NOI18N
        GRDateLbl.setText("GR Date - Fri Oct 07 23:01:08 IST 2022");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GrnNumberLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(InvoiceNumberLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(GRDateLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(GrnNumberLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(InvoiceNumberLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GRDateLbl)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel37.setFont(new java.awt.Font("Helvetica Neue", 0, 15)); // NOI18N
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel37.setText("Supplier Details");

        SupplierNameLbl.setFont(new java.awt.Font("Helvetica Neue", 0, 15)); // NOI18N
        SupplierNameLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        SupplierNameLbl.setText("Test Suppliers");

        SupplierContactNumberLbl.setFont(new java.awt.Font("Helvetica Neue", 0, 15)); // NOI18N
        SupplierContactNumberLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        SupplierContactNumberLbl.setText("Test");

        SupplierAddressLbl.setFont(new java.awt.Font("Helvetica Neue", 0, 15)); // NOI18N
        SupplierAddressLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        SupplierAddressLbl.setText("Test Address");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel37, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SupplierNameLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SupplierContactNumberLbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SupplierAddressLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SupplierNameLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SupplierContactNumberLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SupplierAddressLbl)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        GrnItemTable.setFont(new java.awt.Font("Helvetica Neue", 0, 15)); // NOI18N
        GrnItemTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Item", "Qty", "Cost", "item_id", "price_batch_id", "grn_item_id"
            }
        ) {
            final boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        GrnItemTable.setRowHeight(25);
        jScrollPane5.setViewportView(GrnItemTable);
        if (GrnItemTable.getColumnModel().getColumnCount() > 0) {
            GrnItemTable.getColumnModel().getColumn(0).setMinWidth(50);
            GrnItemTable.getColumnModel().getColumn(0).setPreferredWidth(50);
            GrnItemTable.getColumnModel().getColumn(0).setMaxWidth(50);
            GrnItemTable.getColumnModel().getColumn(2).setMinWidth(100);
            GrnItemTable.getColumnModel().getColumn(2).setPreferredWidth(100);
            GrnItemTable.getColumnModel().getColumn(2).setMaxWidth(100);
            GrnItemTable.getColumnModel().getColumn(3).setMinWidth(100);
            GrnItemTable.getColumnModel().getColumn(3).setPreferredWidth(100);
            GrnItemTable.getColumnModel().getColumn(3).setMaxWidth(100);
            GrnItemTable.getColumnModel().getColumn(4).setMinWidth(0);
            GrnItemTable.getColumnModel().getColumn(4).setPreferredWidth(0);
            GrnItemTable.getColumnModel().getColumn(4).setMaxWidth(0);
            GrnItemTable.getColumnModel().getColumn(5).setMinWidth(0);
            GrnItemTable.getColumnModel().getColumn(5).setPreferredWidth(0);
            GrnItemTable.getColumnModel().getColumn(5).setMaxWidth(0);
            GrnItemTable.getColumnModel().getColumn(6).setMinWidth(0);
            GrnItemTable.getColumnModel().getColumn(6).setPreferredWidth(0);
            GrnItemTable.getColumnModel().getColumn(6).setMaxWidth(0);
        }

        FinalizeGrnBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/done.png"))); // NOI18N
        FinalizeGrnBtn.setText("Finalize GRN");
        FinalizeGrnBtn.setToolTipText("");
        FinalizeGrnBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FinalizeGrnBtnActionPerformed(evt);
            }
        });

        RemoveGrnItemBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/delete.png"))); // NOI18N
        RemoveGrnItemBtn.setText("Remove");
        RemoveGrnItemBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveGrnItemBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 693, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(RemoveGrnItemBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(FinalizeGrnBtn)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FinalizeGrnBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RemoveGrnItemBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel28.setFont(new java.awt.Font("Helvetica Neue", 0, 15)); // NOI18N
        jLabel28.setText("Select Part");

        PartNumberField.setFont(new java.awt.Font("Helvetica Neue", 0, 15)); // NOI18N
        PartNumberField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PartNumberFieldActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Helvetica Neue", 0, 15)); // NOI18N
        jLabel29.setText("Select Price Batch");

        PriceBatchField.setFont(new java.awt.Font("Helvetica Neue", 0, 15)); // NOI18N
        PriceBatchField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PriceBatchFieldActionPerformed(evt);
            }
        });

        AddLocalPBBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/add.png"))); // NOI18N
        AddLocalPBBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddLocalPBBtnActionPerformed(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Helvetica Neue", 0, 15)); // NOI18N
        jLabel30.setText("Cost Price (.Rs)");

        CostField.setFont(new java.awt.Font("Helvetica Neue", 0, 15)); // NOI18N
        CostField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel31.setFont(new java.awt.Font("Helvetica Neue", 0, 15)); // NOI18N
        jLabel31.setText("Selling Price (.Rs)");

        SellingField.setFont(new java.awt.Font("Helvetica Neue", 0, 15)); // NOI18N
        SellingField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel32.setFont(new java.awt.Font("Helvetica Neue", 0, 15)); // NOI18N
        jLabel32.setText("Quantity");

        QtyField.setFont(new java.awt.Font("Helvetica Neue", 0, 15)); // NOI18N
        QtyField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        AddGrnItemLocalBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/add.png"))); // NOI18N
        AddGrnItemLocalBtn.setText("Add");
        AddGrnItemLocalBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddGrnItemLocalBtnActionPerformed(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Helvetica Neue", 0, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 0, 0));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel33.setText("Don't put Unit Type");

        javax.swing.GroupLayout LocalJpanelLayout = new javax.swing.GroupLayout(LocalJpanel);
        LocalJpanel.setLayout(LocalJpanelLayout);
        LocalJpanelLayout.setHorizontalGroup(
            LocalJpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LocalJpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(LocalJpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(LocalJpanelLayout.createSequentialGroup()
                        .addGroup(LocalJpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(PartNumberField, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
                            .addGroup(LocalJpanelLayout.createSequentialGroup()
                                .addComponent(PriceBatchField, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(AddLocalPBBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(CostField)
                            .addComponent(jLabel31, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(SellingField)
                            .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(QtyField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LocalJpanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(AddGrnItemLocalBtn)))
                        .addContainerGap())))
        );
        LocalJpanelLayout.setVerticalGroup(
            LocalJpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LocalJpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PartNumberField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(LocalJpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PriceBatchField)
                    .addComponent(AddLocalPBBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CostField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SellingField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(QtyField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AddGrnItemLocalBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        jLabel1.setText("Goods Receving Form");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(LocalJpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 970, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LocalJpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel2);

        javax.swing.GroupLayout GRN_invoice_create_areaLayout = new javax.swing.GroupLayout(GRN_invoice_create_area);
        GRN_invoice_create_area.setLayout(GRN_invoice_create_areaLayout);
        GRN_invoice_create_areaLayout.setHorizontalGroup(
            GRN_invoice_create_areaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1162, Short.MAX_VALUE)
        );
        GRN_invoice_create_areaLayout.setVerticalGroup(
            GRN_invoice_create_areaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 810, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        page_change_here.add(GRN_invoice_create_area, "card2");
        page_change_here.add(stockGRNList1, "card3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(page_change_here, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(page_change_here, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void PartNumberFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PartNumberFieldActionPerformed
      Product p = (Product) PartNumberField.getSelectedItem();
      System.out.println(p.getProductId()+" "+p.getProductName());

      int ItemId = p.getProductId();
      StockGRNInvoiceCreate.ItemId=ItemId;

      if(PartNumberField.getSelectedItem() != null){
            PriceBatchField.removeAllItems();
            CostField.setText("");
            SellingField.setText("");

            PriceBatchField.addItem(new PriceBatch(0, "PLEASE SELECT A PRICE BATCH",0));

            getPBThread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                     JSONObject jo = ServerController.getJsonObject(WebServiceController.DOWNLOAD_PB_Details, ServerController.getPriceBatchListParams(ItemId));
                if(jo != null){

                    if(jo.getBoolean("result")){


                        JSONArray array = jo.getJSONArray("data");

                        for(int x = 0; x<array.length() ;x++){

                                JSONObject prb = array.getJSONObject(x);

                                int ItemPBID = prb.getInt("ItemPBID");
                                String ItemName = prb.getString("ItemName");
                                Double ItemCost = prb.getDouble("ItemCost");
                                Double ItemSelling = prb.getDouble("ItemSelling");

                                System.out.println("id = "+ItemPBID);


                                PriceBatch pricebatch = new PriceBatch();
                                pricebatch.setProductItemPBID(ItemPBID);

                                pricebatch.setProductItemName(ItemName);
                                pricebatch.setProductItemCost(ItemCost);
                                pricebatch.setProductItemSelling(ItemSelling);

                                PriceBatchField.addItem(pricebatch);



                        }



                    }else{
                        System.out.println("error");
                    }

                }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            });

            getPBThread.start();

      }




    }//GEN-LAST:event_PartNumberFieldActionPerformed

    private void PriceBatchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PriceBatchFieldActionPerformed

        if(PriceBatchField.getSelectedItem() != null){
            PriceBatch pb = (PriceBatch) PriceBatchField.getSelectedItem();
            CostField.setText(pb.getProductItemCost()+"");
            SellingField.setText(pb.getProductItemSelling()+"");

            int ItemPBId = pb.getProductItemPBID();
            StockGRNInvoiceCreate.ItemPBId=ItemPBId;
        }

//        String pbid = null;
//        String batchlabel = null;
//
//        if(PriceBatchField.getSelectedItem() != null){
//
//            String getpricebatch = PriceBatchField.getSelectedItem().toString();
//
//            String arr = getpricebatch;
//            String[] split = arr.split("__");
//
//            pbid = split[0];
//            batchlabel = split[1];
//
//            ResultSet pbdetails = Database.getData("SELECT * FROM tbl_item_price_batch WHERE price_batch_id = "+pbid+" ");
//            try {
//                while(pbdetails.next()){
//                    String CostPrice = pbdetails.getString(4);
//                    String SellingPrice = pbdetails.getString(5);
//
//                    CostField.setText(CostPrice);
//                    SellingField.setText(SellingPrice);
//                }
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//
//        }

    }//GEN-LAST:event_PriceBatchFieldActionPerformed

    private void AddLocalPBBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddLocalPBBtnActionPerformed

        new Thread(new Runnable() {
            @Override
            public void run() {
                if(ItemId == 0){
                    JOptionPane.showMessageDialog(null, "Please select part number first.");
                }else{
                    new AddGRNItemPBDialog(null, pressed, ItemId, grnId).setVisible(true);
                }
            }

        }).start();

    }//GEN-LAST:event_AddLocalPBBtnActionPerformed

    private void AddGrnItemLocalBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddGrnItemLocalBtnActionPerformed

        new Thread(new Runnable() {
            @Override
            public void run() {

                if(PriceBatchField.getSelectedItem().toString().isEmpty() || CostField.getText().isEmpty() || SellingField.getText().isEmpty() || QtyField.getText().isEmpty() ){
                    System.out.println("Please fill the form.");
                    JOptionPane.showMessageDialog(null, "Please fill the form.");
                }else{

                    String CostPrice = CostField.getText();
                    String SellingPrice = SellingField.getText();
                    String Quantity = QtyField.getText();

                    try {
                         JSONObject jo = ServerController.getJsonObject(WebServiceController.UPLOAD_GRN_ITEM, ServerController.sendNewItemToGRNParams(CostPrice,SellingPrice,Quantity,ItemId,ItemPBId,grnId));
                    if(jo != null){

                        if(jo.getBoolean("result")){

                            //JSONArray array = jo.getJSONArray("msg");


                            //JOptionPane.showMessageDialog(null, "Record added successfully.");
                            //CostPriceValue.setText("");
                            //SellingPriceValue.setText("");
                            System.out.println("done");
                            PartNumberField.addItem(new Product(0, "PLEASE SELECT A PART",""));
                            PriceBatchField.removeAllItems();
                            PriceBatchField.addItem(new PriceBatch(0, "PLEASE SELECT A PRICE BATCH",0));
                            CostField.setText("");
                            SellingField.setText("");
                            QtyField.setText("");


                            LoadGrnData(grnId);



                        }else{
                            System.out.println("error");
                            JOptionPane.showMessageDialog(null, "Error adding stock.");
                        }

                    }



                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }


            }
        }).start();


    }//GEN-LAST:event_AddGrnItemLocalBtnActionPerformed

    private void FinalizeGrnBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FinalizeGrnBtnActionPerformed

        new Thread(new Runnable() {
            @Override
            public void run() {



                    try {
                        JSONObject jo = ServerController.getJsonObject(WebServiceController.FINALIZE_GRN, ServerController.finalizeGRNParams(grnId));
                    if(jo != null){

                        if(jo.getBoolean("result")){

                            //LoadGrnData(grnId);
                            stockGRNList1.loadData();
                            Home.showPanel(stockGRNList1);
                            JOptionPane.showMessageDialog(null, "Successfully saved.");


                        }else{
                            System.out.println("error");
                            JOptionPane.showMessageDialog(null, "Error adding stock.");
                        }

                    }



                    } catch (Exception e) {
                        e.printStackTrace();
                    }



            }

        }).start();

    }//GEN-LAST:event_FinalizeGrnBtnActionPerformed

    private void RemoveGrnItemBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveGrnItemBtnActionPerformed

        new Thread(new Runnable() {
            @Override
            public void run() {

                if(GrnItemTable.getSelectedRowCount() <= 0){
                    JOptionPane.showMessageDialog(null, "Please select item first");
                }else{

                    Double itemqty = (Double) GrnItemTable.getValueAt(GrnItemTable.getSelectedRow(), 2);
                    int pbid = (int) GrnItemTable.getValueAt(GrnItemTable.getSelectedRow(), 5);
                    int grnitemid = (int) GrnItemTable.getValueAt(GrnItemTable.getSelectedRow(), 6);


                    try {
                        JSONObject jo = ServerController.getJsonObject(WebServiceController.DELETE_GRN_ITEM, ServerController.deleteItemGRNParams(itemqty,pbid,grnitemid));
                    if(jo != null){

                        if(jo.getBoolean("result")){

                            LoadGrnData(grnId);



                        }else{
                            System.out.println("error");
                            JOptionPane.showMessageDialog(null, "Error adding stock.");
                        }

                    }



                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

        }).start();

    }//GEN-LAST:event_RemoveGrnItemBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddGrnItemLocalBtn;
    private javax.swing.JButton AddLocalPBBtn;
    private javax.swing.JTextField CostField;
    private javax.swing.JButton FinalizeGrnBtn;
    private javax.swing.JLabel GRDateLbl;
    private javax.swing.JPanel GRN_invoice_create_area;
    private javax.swing.JTable GrnItemTable;
    private javax.swing.JLabel GrnNumberLbl;
    private javax.swing.JLabel InvoiceNumberLbl;
    private javax.swing.JPanel LocalJpanel;
    public static javax.swing.JComboBox<Product> PartNumberField;
    private javax.swing.JComboBox<PriceBatch> PriceBatchField;
    private javax.swing.JTextField QtyField;
    private javax.swing.JButton RemoveGrnItemBtn;
    private javax.swing.JTextField SellingField;
    private javax.swing.JLabel SupplierAddressLbl;
    private javax.swing.JLabel SupplierContactNumberLbl;
    private javax.swing.JLabel SupplierNameLbl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel page_change_here;
    private Pages.StockGRNList stockGRNList1;
    // End of variables declaration//GEN-END:variables
}
