/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Pages;

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
public class StockView extends javax.swing.JPanel {

    /**
     * Creates new form StockView
     */

    private final DefaultTableModel dtm;

    private Thread allstockThread;

    DecimalFormat df2 = new DecimalFormat( "#,###,###,##0.00" );

    public void loadData(){

       LoadAllStockData();
    }

    public StockView() {
        initComponents();

        dtm = (DefaultTableModel) StockTbl.getModel();
        //LoadAllStockData();
    }

    private void LoadAllStockData(){
        dtm.setRowCount(0);

        allstockThread = new Thread(new Runnable() {
        @Override
        public void run() {

            try {
                 JSONObject jo = ServerController.getJsonObject(WebServiceController.DOWNLOAD_STOCK_VIEW, ServerController.getStockParams("1"));
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


                            Vector v = new Vector();
                            v.add(ProductName);
                            v.add(ProductLocation);
                            v.add(ProductNumber);
                            v.add(ProductQuantity);
                            v.add(df2.format(ProductCost));
                            v.add(df2.format(ProductSelling));

                            dtm.addRow(v);

                    }

                    DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
                    rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
                    StockTbl.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
                    StockTbl.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);



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


   private void searchProductDetailsByKeyword() {
    String keyword = SearchField.getText();

    try {
        dtm.setRowCount(0);

        JSONArray array = new JSONArray();

        JSONObject jo = ServerController.getJsonObject(WebServiceController.DOWNLOAD_STOCK_VIEW, ServerController.getStockParams(keyword));
        if (jo != null) {
            if (jo.getBoolean("result")) {
                array = jo.getJSONArray("data");

                for (int i = 0; i < array.length(); i++) {
                    JSONObject pd = array.getJSONObject(i);

                    int ProductId = pd.getInt("ProductId");
                    String ProductName = pd.getString("ProductName");
                    String ProductLocation = pd.getString("ProductLocation");
                    String ProductNumber = pd.getString("ProductNumber");
                    String ProductDetails = pd.getString("ProductDetails");
                    Double ProductQuantity = pd.getDouble("ProductQuantity");
                    Double ProductCost = pd.getDouble("ProductCost");
                    Double ProductSelling = pd.getDouble("ProductSelling");

                    // Add data to vector
                    Vector v = new Vector();
                    v.add(ProductName);
                    v.add(ProductLocation);
                    v.add(ProductNumber);
                    v.add(ProductQuantity);
                    v.add(df2.format(ProductCost));
                    v.add(df2.format(ProductSelling));

                    dtm.addRow(v);  // Add the row to your DefaultTableModel
                }

                // Align columns 4 and 5 to the right
                DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
                rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
                StockTbl.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
                StockTbl.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
            } else {
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
        StockTbl = new javax.swing.JTable();

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
        jLabel1.setText("View Stock");

        SearchBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/search.png"))); // NOI18N
        SearchBtn.setText("Search");
        SearchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchProduct(evt);
            }
        });

        ClearBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/clear.png"))); // NOI18N

        StockTbl.setFont(new java.awt.Font("Helvetica Neue", 0, 15)); // NOI18N
        StockTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product Name", "Product Location", "Product Code", "Quantity", "Cost Price", "Selling Price"
            }
        ) {
            final boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        StockTbl.setRowHeight(25);
        jScrollPane2.setViewportView(StockTbl);
        if (StockTbl.getColumnModel().getColumnCount() > 0) {
            StockTbl.getColumnModel().getColumn(3).setMinWidth(100);
            StockTbl.getColumnModel().getColumn(3).setPreferredWidth(100);
            StockTbl.getColumnModel().getColumn(3).setMaxWidth(100);
            StockTbl.getColumnModel().getColumn(4).setMinWidth(200);
            StockTbl.getColumnModel().getColumn(4).setPreferredWidth(200);
            StockTbl.getColumnModel().getColumn(4).setMaxWidth(200);
            StockTbl.getColumnModel().getColumn(5).setMinWidth(200);
            StockTbl.getColumnModel().getColumn(5).setPreferredWidth(200);
            StockTbl.getColumnModel().getColumn(5).setMaxWidth(200);
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

    private void searchProduct(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchProduct
        searchProductDetailsByKeyword();
    }//GEN-LAST:event_searchProduct


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackBtn;
    private javax.swing.JButton ClearBtn;
    private javax.swing.JButton SearchBtn;
    private javax.swing.JTextField SearchField;
    private javax.swing.JTable StockTbl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
