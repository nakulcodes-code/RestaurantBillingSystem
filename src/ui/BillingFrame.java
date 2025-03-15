package ui;

import dao.OrderDAO;
import model.Billing;
import model.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.util.List;

public class BillingFrame extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnGenerateReceipt, btnClear, btnPrintBill; // Add Print Bill button
    private JTextArea receiptArea;

    public BillingFrame() {
        setTitle("Billing System");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table for Orders
        tableModel = new DefaultTableModel(new String[]{"ID", "Item Name", "Quantity", "Price", "Total"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Buttons for actions
        JPanel panelButtons = new JPanel();
        btnGenerateReceipt = new JButton("Generate Receipt");
        btnClear = new JButton("Clear");
        btnPrintBill = new JButton("Print Bill"); // Create Print Bill button
        panelButtons.add(btnGenerateReceipt);
        panelButtons.add(btnClear);
        panelButtons.add(btnPrintBill); // Add Print Bill button to panel

        // TextArea to display the receipt
        receiptArea = new JTextArea(10, 30);
        receiptArea.setEditable(false);
        JScrollPane receiptScrollPane = new JScrollPane(receiptArea);

        // Load orders into table
        loadOrders();

        // Add Action Listeners
        btnGenerateReceipt.addActionListener(e -> generateReceipt());
        btnClear.addActionListener(e -> clearReceipt());
        btnPrintBill.addActionListener(e -> printBill()); // Print Bill on button click

        // Add components to frame
        add(scrollPane, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);
        add(receiptScrollPane, BorderLayout.EAST);
    }

    private void loadOrders() {
        tableModel.setRowCount(0);
        List<Order> orderList = OrderDAO.getAllOrders();
        for (Order order : orderList) {
            tableModel.addRow(new Object[]{order.getId(), order.getItemName(), order.getQuantity(), order.getPrice(), order.getTotal()});
        }
    }

    private void generateReceipt() {
        int[] selectedRows = table.getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "Please select orders to generate the receipt.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get selected orders
        List<Order> selectedOrders = new java.util.ArrayList<>();
        for (int row : selectedRows) {
            int orderId = (int) table.getValueAt(row, 0);
            String itemName = (String) table.getValueAt(row, 1);
            int quantity = (int) table.getValueAt(row, 2);
            double price = (double) table.getValueAt(row, 3);
            selectedOrders.add(new Order(orderId, itemName, quantity, price));
        }

        // Generate billing object and receipt
        Billing billing = new Billing(selectedOrders);
        receiptArea.setText(billing.generateReceipt());
    }

    private void clearReceipt() {
        receiptArea.setText("");
    }

    private void printBill() {
        String billContent = receiptArea.getText(); // Get the receipt content from the text area
        if (billContent.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please generate a receipt before printing.", "Print Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Set up the printing process
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            printerJob.setJobName("Print Bill");

            // Create a simple print document with the receipt content
            printerJob.setPrintable((graphics, pageFormat, pageIndex) -> {
                if (pageIndex > 0) {
                    return Printable.NO_SUCH_PAGE;
                }

                // Set font and print the receipt content
                graphics.setFont(new Font("Monospaced", Font.PLAIN, 12));
                graphics.drawString(billContent, 100, 100); // Print starting at (100, 100) coordinates
                return Printable.PAGE_EXISTS;
            });

            // Show print dialog and start printing if user clicks OK
            if (printerJob.printDialog()) {
                printerJob.print();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred while printing the bill.", "Print Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BillingFrame().setVisible(true));
    }
}
