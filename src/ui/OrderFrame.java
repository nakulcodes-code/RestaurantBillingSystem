package ui;

import dao.OrderDAO;
import model.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;

public class OrderFrame extends JFrame {
    private JTextField txtItemName, txtQuantity, txtPrice;
    private JButton btnAdd, btnUpdate, btnDelete, btnPrintReceipt, btnSaveInvoice, btnOpenMenu;
    private JTable table;
    private DefaultTableModel tableModel;
    private int selectedOrderId = -1;

    public OrderFrame() {
        setTitle("Order Management");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel panelForm = new JPanel(new GridLayout(6, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Order Details"));

        panelForm.add(new JLabel("Item Name:"));
        txtItemName = new JTextField();
        panelForm.add(txtItemName);

        panelForm.add(new JLabel("Quantity:"));
        txtQuantity = new JTextField();
        panelForm.add(txtQuantity);

        panelForm.add(new JLabel("Price:"));
        txtPrice = new JTextField();
        panelForm.add(txtPrice);

        btnAdd = new JButton("Add Order");
        btnUpdate = new JButton("Update Order");
        btnDelete = new JButton("Delete Order");

        // Add Print and Save buttons
        btnPrintReceipt = new JButton("Print Receipt");
        btnSaveInvoice = new JButton("Save Invoice");

        // Add Menu Frame button
        btnOpenMenu = new JButton("Open Menu");

        panelForm.add(btnAdd);
        panelForm.add(btnUpdate);
        panelForm.add(btnDelete);
        panelForm.add(btnPrintReceipt); // Add print button to the form
        panelForm.add(btnSaveInvoice);  // Add save invoice button to the form
        panelForm.add(btnOpenMenu);     // Add Open Menu button to the form

        // Table for Orders
        tableModel = new DefaultTableModel(new String[]{"ID", "Item Name", "Quantity", "Price", "Total"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Load orders into table
        loadOrders();

        // Add Action Listeners
        btnAdd.addActionListener(e -> addOrder());
        btnUpdate.addActionListener(e -> updateOrder());
        btnDelete.addActionListener(e -> deleteOrder());
        btnPrintReceipt.addActionListener(e -> printReceipt());
        btnSaveInvoice.addActionListener(e -> saveInvoice());
        btnOpenMenu.addActionListener(e -> openMenu());  // Open Menu on button click

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    selectedOrderId = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                    txtItemName.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    txtQuantity.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    txtPrice.setText(tableModel.getValueAt(selectedRow, 3).toString());
                }
            }
        });

        add(panelForm, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadOrders() {
        tableModel.setRowCount(0);
        List<Order> orderList = OrderDAO.getAllOrders();
        for (Order order : orderList) {
            tableModel.addRow(new Object[]{order.getId(), order.getItemName(), order.getQuantity(), order.getPrice(), order.getTotal()});
        }
    }

    private void addOrder() {
        String itemName = txtItemName.getText().trim();
        int quantity = Integer.parseInt(txtQuantity.getText().trim());
        double price = Double.parseDouble(txtPrice.getText().trim());
        Order order = new Order(0, itemName, quantity, price);
        if (OrderDAO.insertOrder(order)) {
            JOptionPane.showMessageDialog(this, "Order added successfully.");
            loadOrders();
        }
    }

    private void updateOrder() {
        if (selectedOrderId == -1) return;
        Order order = new Order(selectedOrderId, txtItemName.getText().trim(), Integer.parseInt(txtQuantity.getText().trim()), Double.parseDouble(txtPrice.getText().trim()));
        if (OrderDAO.updateOrder(order)) loadOrders();
    }

    private void deleteOrder() {
        if (selectedOrderId == -1) return;
        if (OrderDAO.deleteOrder(selectedOrderId)) loadOrders();
    }

    private void printReceipt() {
        String receiptContent = generateReceiptContent(); // Generate receipt content dynamically
        PrintReceipt.printReceipt(receiptContent);  // This should now work
    }

    private void saveInvoice() {
        String invoiceContent = generateInvoiceContent(); // Generate invoice content dynamically
        String filePath = "Invoice_" + System.currentTimeMillis() + ".txt"; // Unique file name
        SaveInvoice.saveInvoice(invoiceContent, filePath); // This should now work
    }

    private void openMenu() {
        new MenuFrame().setVisible(true); // Open the MenuFrame
    }

    private String generateReceiptContent() {
        // You can customize this content based on your order details
        String itemName = txtItemName.getText().trim();
        int quantity = Integer.parseInt(txtQuantity.getText().trim());
        double price = Double.parseDouble(txtPrice.getText().trim());
        double total = quantity * price;
        return "Receipt:\nItem: " + itemName + "\nQuantity: " + quantity + "\nPrice: $" + price + "\nTotal: $" + total;
    }

    private String generateInvoiceContent() {
        // Customize your invoice content here
        String itemName = txtItemName.getText().trim();
        int quantity = Integer.parseInt(txtQuantity.getText().trim());
        double price = Double.parseDouble(txtPrice.getText().trim());
        double total = quantity * price;
        return "Invoice:\nItem: " + itemName + "\nQuantity: " + quantity + "\nPrice: $" + price + "\nTotal: $" + total;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new OrderFrame().setVisible(true));
    }
}
