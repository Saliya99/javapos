package Pages;

import controllers.ServerController;
import controllers.WebServiceController;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class Dashboard extends javax.swing.JPanel {
    public String invoiceCount;
    public String customerCount;
    public String soldItemCount;

    public Dashboard() {
        dataLoader(); // Fetch and load data before initializing components
        initComponents(); // Initialize components with loaded data
    }

    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanelSquare1 = new javax.swing.JPanel();
        jPanelSquare2 = new javax.swing.JPanel();
        jPanelSquare3 = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(1162, 810));

        jPanel1.setLayout(new BorderLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", Font.BOLD, 50));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText("WELCOME SMART RETAILER");
        jPanel1.add(jLabel1, BorderLayout.NORTH);

        // Panel for No. Invoices

        jPanelSquare1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));  // Black border
        jPanelSquare1.setBackground(new Color(240, 240, 240));  // Light gray background
        jPanelSquare1.setMaximumSize(new Dimension(500, 400));
        jPanelSquare1.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
        jPanelSquare1.setLayout(new BoxLayout(jPanelSquare1, BoxLayout.Y_AXIS));

        JLabel label1 = new JLabel("No. Invoices");
        JLabel label11 = new JLabel(invoiceCount); // Use formatted invoice count

        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/Images/inImage.png"));
        Image img = imageIcon.getImage();
        Image newImg = img.getScaledInstance(110, 100, java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newImg);
        JLabel imageLabel = new JLabel(imageIcon);

        label1.setFont(new java.awt.Font("Tahoma", Font.BOLD, 30));
        label11.setFont(new java.awt.Font("Tahoma", Font.BOLD, 40));

        label1.setAlignmentX(Component.CENTER_ALIGNMENT);
        label11.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        jPanelSquare1.add(label1);
        jPanelSquare1.add(imageLabel);
        jPanelSquare1.add(label11);

        // Panel for Customers
        jPanelSquare2.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
        jPanelSquare2.setLayout(new BoxLayout(jPanelSquare2, BoxLayout.Y_AXIS));

        JLabel label2 = new JLabel("Customers");
        JLabel label22 = new JLabel(customerCount); // Use formatted customer count

        ImageIcon imageIcon2 = new ImageIcon(getClass().getResource("/Images/cusImageOne.png"));
        Image img2 = imageIcon2.getImage();
        Image newImg2 = img2.getScaledInstance(120, 100, java.awt.Image.SCALE_SMOOTH);
        imageIcon2 = new ImageIcon(newImg2);
        JLabel imageLabel2 = new JLabel(imageIcon2);

        label2.setFont(new java.awt.Font("Tahoma", Font.BOLD, 30));
        label22.setFont(new java.awt.Font("Tahoma", Font.BOLD, 40));

        label2.setAlignmentX(Component.CENTER_ALIGNMENT);
        label22.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);

        jPanelSquare2.add(label2);
        jPanelSquare2.add(imageLabel2);
        jPanelSquare2.add(label22);

        // Panel for Item Sold
        jPanelSquare3.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));  // Black border
        jPanelSquare3.setBackground(new Color(240, 240, 240));  // Light gray background
        jPanelSquare3.setMaximumSize(new Dimension(500, 400));
        jPanelSquare3.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
        jPanelSquare3.setLayout(new BoxLayout(jPanelSquare3, BoxLayout.Y_AXIS));

        JLabel label3 = new JLabel("Item Sold");
        JLabel label33 = new JLabel(soldItemCount); // Use formatted sold item count

        ImageIcon imageIcon3 = new ImageIcon(getClass().getResource("/Images/solItem.png"));
        Image img3 = imageIcon3.getImage();
        Image newImg3 = img3.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
        imageIcon3 = new ImageIcon(newImg3);
        JLabel imageLabel3 = new JLabel(imageIcon3);

        label3.setFont(new java.awt.Font("Tahoma", Font.BOLD, 30));
        label33.setFont(new java.awt.Font("Tahoma", Font.BOLD, 40));

        label3.setAlignmentX(Component.CENTER_ALIGNMENT);
        label33.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageLabel3.setAlignmentX(Component.CENTER_ALIGNMENT);

        jPanelSquare3.add(label3);
        jPanelSquare3.add(imageLabel3);
        jPanelSquare3.add(label33);

        // Add panels to a simple grid layout
        JPanel squaresPanel = new JPanel();
        squaresPanel.setLayout(new GridLayout(1, 3, 10, 10));
        squaresPanel.add(jPanelSquare1);
        squaresPanel.add(jPanelSquare2);
        squaresPanel.add(jPanelSquare3);

        jPanel1.add(squaresPanel, BorderLayout.CENTER);

        // Set layout for the outer panel
        setLayout(new BorderLayout());
        add(jPanel1, BorderLayout.CENTER);
    }

    public void dataLoader() {
        System.out.println("Data loading method");

        try {
            JSONObject jo = ServerController.getJsonObject(WebServiceController.GET_CLIENT_COUNT, ServerController.getDashboardStat("invoiceCount"));

            if (jo != null) {
                if (jo.getBoolean("result")) {
                    String count = jo.getString("count");

                    double number = Double.parseDouble(count);
                    long roundedNumber = Math.round(number); // Round to the nearest whole number

                    // Format the number with commas
                    String formattedNumber = formatNumberWithCommas(roundedNumber);
                    invoiceCount = formattedNumber;
                } else {
                    System.out.println("Error: The result is not true.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            JSONObject jo = ServerController.getJsonObject(WebServiceController.GET_CLIENT_COUNT, ServerController.getDashboardStat("customerCount"));

            if (jo != null) {
                if (jo.getBoolean("result")) {
                    String count = jo.getString("count");

                    double number = Double.parseDouble(count);
                    long roundedNumber = Math.round(number); // Round to the nearest whole number

                    // Format the number with commas
                    String formattedNumber = formatNumberWithCommas(roundedNumber);
                    customerCount = formattedNumber;
                } else {
                    System.out.println("Error: The result is not true.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            JSONObject jo = ServerController.getJsonObject(WebServiceController.GET_CLIENT_COUNT, ServerController.getDashboardStat("soldItemCount"));

            if (jo != null) {
                if (jo.getBoolean("result")) {
                    String count = jo.getString("count");

                    double number = Double.parseDouble(count);
                    long roundedNumber = Math.round(number); // Round to the nearest whole number

                    // Format the number with commas
                    String formattedNumber = formatNumberWithCommas(roundedNumber);

                    soldItemCount = formattedNumber;
                } else {
                    System.out.println("Error: The result is not true.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String formatNumberWithCommas(long number) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(number);
    }

    public static void main(String[] args) {
        // Test the panel in a JFrame
        JFrame frame = new JFrame("Dashboard Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Dashboard());
        frame.pack();
        frame.setVisible(true);
    }

    // Variables declaration
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelSquare1;
    private javax.swing.JPanel jPanelSquare2;
    private javax.swing.JPanel jPanelSquare3;
}
