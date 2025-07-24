/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Dialog;

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
public class GrnViewDialog extends javax.swing.JDialog {

    /**
     * Creates new form GrnViewDialog
     */
    private DefaultTableModel dtm;
    public static int grnId;
    DecimalFormat df2 = new DecimalFormat( "#,###,###,##0.00" );

    private Thread GrnDataThread;

    public GrnViewDialog(java.awt.Frame parent, boolean modal, int grnId) {
        super(parent, modal);
        initComponents();

        GrnViewDialog.grnId =grnId;

        int GRNNumberView = grnId + 10000;

        GrnNumberLbl.setText("GRN Number: "+GRNNumberView);

        dtm = (DefaultTableModel) GrnItemTable.getModel();
        LoadGrnData();
    }

    private void LoadGrnData(){

        System.out.println("downloading...");


        GrnDataThread = new Thread(new Runnable() {
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
                            Double GrandTotal = pd.getDouble("GrandTotal");




                            InvoiceNumberLbl.setText("Invoice Number: "+InvoiceNumber);
                            GRDateLbl.setText("Goods Receved Date: "+GodsRecevedDate);
                            SupplierNameLbl.setText("Supplier Name: "+SupplierName);
                            GrnRaiseDateLbl.setText("GRN Raise Date: "+GRNDateTime);
                            SubTotalLbl.setText(df2.format(GrandTotal));



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
                            Double ItemCostTotal = sd.getDouble("ItemCostTotal");

                            String DisplayName = ProductName+" ("+ProductNumber+")";

                            Vector v = new Vector();
                            v.add(ProductCount);
                            v.add(DisplayName);
                            v.add(Quantity);
                            v.add(df2.format(ProductCost));
                            v.add(df2.format(ItemCostTotal));

                            dtm.addRow(v);
                    }

                    DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
                    rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
                    GrnItemTable.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
                    GrnItemTable.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
                    GrnItemTable.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);








                }else{
                    System.out.println("error");
                }







            }



            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        });


        GrnDataThread.start();

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
        jLabel2 = new javax.swing.JLabel();
        GrnNumberLbl = new javax.swing.JLabel();
        InvoiceNumberLbl = new javax.swing.JLabel();
        SupplierNameLbl = new javax.swing.JLabel();
        GRDateLbl = new javax.swing.JLabel();
        GrnRaiseDateLbl = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        Grn_item_area = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        GrnItemTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        SubTotalLbl = new javax.swing.JLabel();
        PrintBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        Logo_etc_area.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(0, 51, 153));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("GRN Invoice");

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

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/a_logo.png"))); // NOI18N

        GrnNumberLbl.setText("GRN Number: ");

        InvoiceNumberLbl.setText("Invoice Number: ");

        SupplierNameLbl.setText("Supplier Name:");

        GRDateLbl.setText("Goods Receved Date:");

        GrnRaiseDateLbl.setText("GRN Raise Date:");

        javax.swing.GroupLayout Logo_etc_areaLayout = new javax.swing.GroupLayout(Logo_etc_area);
        Logo_etc_area.setLayout(Logo_etc_areaLayout);
        Logo_etc_areaLayout.setHorizontalGroup(
            Logo_etc_areaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Logo_etc_areaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Logo_etc_areaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1)
                    .addComponent(GrnNumberLbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(InvoiceNumberLbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SupplierNameLbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(GRDateLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
                    .addComponent(GrnRaiseDateLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE))
                .addContainerGap())
        );
        Logo_etc_areaLayout.setVerticalGroup(
            Logo_etc_areaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Logo_etc_areaLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(GrnNumberLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(InvoiceNumberLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SupplierNameLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GRDateLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GrnRaiseDateLbl)
                .addContainerGap())
        );

        Grn_item_area.setBackground(new java.awt.Color(255, 255, 255));

        GrnItemTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "#", "Item Name", "Qty", "Cost", "Total"
            }
        ) {
            final boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(GrnItemTable);
        if (GrnItemTable.getColumnModel().getColumnCount() > 0) {
            GrnItemTable.getColumnModel().getColumn(0).setMinWidth(50);
            GrnItemTable.getColumnModel().getColumn(0).setPreferredWidth(50);
            GrnItemTable.getColumnModel().getColumn(0).setMaxWidth(50);
            GrnItemTable.getColumnModel().getColumn(2).setMinWidth(50);
            GrnItemTable.getColumnModel().getColumn(2).setPreferredWidth(50);
            GrnItemTable.getColumnModel().getColumn(2).setMaxWidth(50);
            GrnItemTable.getColumnModel().getColumn(3).setMinWidth(100);
            GrnItemTable.getColumnModel().getColumn(3).setPreferredWidth(100);
            GrnItemTable.getColumnModel().getColumn(3).setMaxWidth(100);
            GrnItemTable.getColumnModel().getColumn(4).setMinWidth(100);
            GrnItemTable.getColumnModel().getColumn(4).setPreferredWidth(100);
            GrnItemTable.getColumnModel().getColumn(4).setMaxWidth(100);
        }

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Helvetica Neue", 1, 36)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Total Price:");

        SubTotalLbl.setFont(new java.awt.Font("Helvetica Neue", 1, 36)); // NOI18N
        SubTotalLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        SubTotalLbl.setText("0.00");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SubTotalLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                    .addComponent(SubTotalLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        PrintBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/printer.png"))); // NOI18N
        PrintBtn.setText("Print");

        javax.swing.GroupLayout Grn_item_areaLayout = new javax.swing.GroupLayout(Grn_item_area);
        Grn_item_area.setLayout(Grn_item_areaLayout);
        Grn_item_areaLayout.setHorizontalGroup(
            Grn_item_areaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Grn_item_areaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Grn_item_areaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Grn_item_areaLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(PrintBtn)))
                .addContainerGap())
        );
        Grn_item_areaLayout.setVerticalGroup(
            Grn_item_areaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Grn_item_areaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PrintBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(Grn_item_area, javax.swing.GroupLayout.PREFERRED_SIZE, 597, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(GrnViewDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GrnViewDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GrnViewDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GrnViewDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                GrnViewDialog dialog = new GrnViewDialog(new javax.swing.JFrame(), true, 0);
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
    private javax.swing.JLabel GRDateLbl;
    private javax.swing.JTable GrnItemTable;
    private javax.swing.JLabel GrnNumberLbl;
    private javax.swing.JLabel GrnRaiseDateLbl;
    private javax.swing.JPanel Grn_item_area;
    private javax.swing.JLabel InvoiceNumberLbl;
    private javax.swing.JPanel Logo_etc_area;
    private javax.swing.JButton PrintBtn;
    private javax.swing.JLabel SubTotalLbl;
    private javax.swing.JLabel SupplierNameLbl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
