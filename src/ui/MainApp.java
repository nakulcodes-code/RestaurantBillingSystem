package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainApp extends JFrame {
    
    private JButton btnOpenOrderManagement, btnPrintReceipt, btnSaveInvoice;

    public MainApp() {
        setTitle("Restaurant Billing System");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        // Buttons to interact with the application
        btnOpenOrderManagement = new JButton("Open Order Management");
        btnPrintReceipt = new JButton("Print Receipt");
        btnSaveInvoice = new JButton("Save Invoice");

        // Add buttons to the frame
        add(btnOpenOrderManagement);
        add(btnPrintReceipt);
        add(btnSaveInvoice);

        // Button Listeners
        btnOpenOrderManagement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the Order Management Frame
                new OrderFrame().setVisible(true);
            }
        });

        btnPrintReceipt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // This should invoke print receipt functionality
                String receiptContent = generateReceiptContent(); // Generate receipt content dynamically
                PrintReceipt.printReceipt(receiptContent); // Print receipt
            }
        });

        btnSaveInvoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // This should invoke save invoice functionality
                String invoiceContent = generateInvoiceContent(); // Generate invoice content dynamically
                String filePath = "Invoice_" + System.currentTimeMillis() + ".txt"; // Generate a unique file name
                SaveInvoice.saveInvoice(invoiceContent, filePath); // Save invoice
            }
        });
    }

    // Method to generate receipt content (this can be customized based on your project data)
    private String generateReceiptContent() {
        // You can fetch real data or generate it dynamically
        return "Receipt:\nItem: Pizza\nQuantity: 2\nPrice: $20\nTotal: $40";
    }

    // Method to generate invoice content (this can be customized based on your project data)
    private String generateInvoiceContent() {
        // You can fetch real data or generate it dynamically
        return "Invoice:\nItem: Pizza\nQuantity: 2\nPrice: $20\nTotal: $40\nTax: $4\nTotal Due: $44";
    }

    public static void main(String[] args) {
        // Create and display the main application window
        SwingUtilities.invokeLater(() -> new MainApp().setVisible(true));
    }
}

