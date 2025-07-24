/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Pages;

import Dialog.ProductDetailsDialog;
import smartretailer.Home;
import controllers.ServerController;
import controllers.WebServiceController;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Oshan
 */
public class StockProducts extends javax.swing.JPanel {

    /**
     * Creates new form StockProducts
     */

    private static DefaultTableModel dtm;
    private static Thread allProductThread;

    boolean pressed = false;

    static DecimalFormat df2 = new DecimalFormat( "#,###,###,##0.00" );

    public static void loadData(){
      LoadAllProductData();
    }

    public StockProducts() {
        initComponents();


        dtm = (DefaultTableModel) ProductTbl.getModel();
        //LoadAllProductData();
    }

    private static void LoadAllProductData(){
        dtm.setRowCount(0);

        allProductThread = new Thread(new Runnable() {
        @Override
        public void run() {


            try {
                 JSONObject jo = ServerController.getJsonObject(WebServiceController.DOWNLOAD_ALL_PRODUCTS, ServerController.getProductsParams("1"));
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
                            Double ProductCost = pd.getDouble("ProductCost");
                            Double ProductSelling = pd.getDouble("ProductSelling");
                            int CountView = pd.getInt("CountView");


                            Vector v = new Vector();
                            v.add(CountView);
                            v.add(ProductName);
                            v.add(ProductNumber);
                            v.add(ProductLocation);
                            v.add(df2.format(ProductCost));
                            v.add(df2.format(ProductSelling));
                            v.add(ProductId);

                            dtm.addRow(v);

                    }

                    DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
                    rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
                    ProductTbl.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
                    ProductTbl.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);



                }else{
                    System.out.println("error");
                }

            }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        });


        allProductThread.start();

    }

    private void searchProductByKeyword(){
        String keyword = SearchField.getText();

        try {

            dtm.setRowCount(0);
            JSONArray array = new JSONArray();
            JSONObject jo = ServerController.getJsonObject(WebServiceController.DOWNLOAD_ALL_PRODUCTS, ServerController.getProductsParams(keyword));
            if(jo != null){

                if(jo.getBoolean("result")){

                    array = jo.getJSONArray("data");
                    for(int i = 0; i<array.length() ;i++){

                            JSONObject pd = array.getJSONObject(i);

                            int ProductId = pd.getInt("ProductId");
                            String ProductName = pd.getString("ProductName");
                            String ProductLocation = pd.getString("ProductLocation");
                            String ProductNumber = pd.getString("ProductNumber");
                            String ProductDetails = pd.getString("ProductDetails");
                            Double ProductCost = pd.getDouble("ProductCost");
                            Double ProductSelling = pd.getDouble("ProductSelling");
                            int CountView = pd.getInt("CountView");

                            Vector v = new Vector();
                            v.add(CountView);
                            v.add(ProductName);
                            v.add(ProductNumber);
                            v.add(ProductLocation);
                            v.add(df2.format(ProductCost));
                            v.add(df2.format(ProductSelling));
                            v.add(ProductId);

                            dtm.addRow(v);

                    }

                    DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
                    rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
                    ProductTbl.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
                    ProductTbl.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);



                }else{
                    System.out.println("error");
                }

            }

            } catch (Exception e) {
                e.printStackTrace();
            }
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        BackBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        SearchField = new javax.swing.JTextField();
        SearchBtn = new javax.swing.JButton();
        ClearBtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        ProductTbl = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(1162, 810));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        BackBtn.setFont(new java.awt.Font("Helvetica Neue", 1, 15)); // NOI18N
        BackBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/back.png"))); // NOI18N
        BackBtn.setText("Back");
        BackBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackBtnActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        jLabel1.setText("Products");

        SearchBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/search.png"))); // NOI18N
        SearchBtn.setText("Search");
        SearchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchProduct(evt);
            }
        });

        ClearBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/clear.png"))); // NOI18N

        ProductTbl.setFont(new java.awt.Font("Helvetica Neue", 0, 15)); // NOI18N
        ProductTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Product Name", "Product Number", "Product Location", "Cost Price", "Selling Price", "ProductId"
            }
        ) {
            final boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ProductTbl.setRowHeight(25);
        ProductTbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ProductTblMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(ProductTbl);
        if (ProductTbl.getColumnModel().getColumnCount() > 0) {
            ProductTbl.getColumnModel().getColumn(0).setMinWidth(50);
            ProductTbl.getColumnModel().getColumn(0).setPreferredWidth(50);
            ProductTbl.getColumnModel().getColumn(0).setMaxWidth(50);
            ProductTbl.getColumnModel().getColumn(6).setMinWidth(0);
            ProductTbl.getColumnModel().getColumn(6).setPreferredWidth(0);
            ProductTbl.getColumnModel().getColumn(6).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(BackBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 677, Short.MAX_VALUE)
                                .addComponent(SearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(SearchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ClearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(14, 14, 14))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BackBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ClearBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(SearchBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(SearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 627, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(104, 104, 104))
        );

        jScrollPane1.setViewportView(jPanel2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 810, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void BackBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackBtnActionPerformed
        Home.showPanel(Home.stockNavView);
    }//GEN-LAST:event_BackBtnActionPerformed

    private void ProductTblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProductTblMouseClicked

        int productId = (int) ProductTbl.getValueAt(ProductTbl.getSelectedRow(), 6);
        new ProductDetailsDialog(null, pressed, productId).setVisible(true);
    }//GEN-LAST:event_ProductTblMouseClicked

    private void searchProduct(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchProduct
        searchProductByKeyword();
    }//GEN-LAST:event_searchProduct


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackBtn;
    private javax.swing.JButton ClearBtn;
    private static javax.swing.JTable ProductTbl;
    private javax.swing.JButton SearchBtn;
    private javax.swing.JTextField SearchField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
