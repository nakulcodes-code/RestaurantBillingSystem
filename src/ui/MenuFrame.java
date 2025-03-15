package ui;

import dao.MenuDAO;
import model.MenuItem;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class MenuFrame extends JFrame {
    private JTextField txtName, txtPrice;
    private JButton btnAdd, btnUpdate, btnDelete;
    private JTable table;
    private DefaultTableModel tableModel;
    private int selectedItemId = -1; // To track selected item for update/delete

    public MenuFrame() {
        setTitle("Menu Management");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for Form Inputs
        JPanel panelForm = new JPanel(new GridLayout(3, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Menu Details"));
        
        panelForm.add(new JLabel("Item Name:"));
        txtName = new JTextField();
        panelForm.add(txtName);
        
        panelForm.add(new JLabel("Price:"));
        txtPrice = new JTextField();
        panelForm.add(txtPrice);
        
        btnAdd = new JButton("Add Item");
        btnUpdate = new JButton("Update Item");
        btnDelete = new JButton("Delete Item");

        panelForm.add(btnAdd);
        panelForm.add(btnUpdate);
        panelForm.add(btnDelete);

        // Table for Menu Items
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Price"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Load menu items into table
        loadMenuItems();

        // Add Action Listeners
        btnAdd.addActionListener(e -> addMenuItem());
        btnUpdate.addActionListener(e -> updateMenuItem());
        btnDelete.addActionListener(e -> deleteMenuItem());

        // Select item from table
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    selectedItemId = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                    txtName.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    txtPrice.setText(tableModel.getValueAt(selectedRow, 2).toString());
                }
            }
        });

        // Add components to frame
        add(panelForm, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadMenuItems() {
        tableModel.setRowCount(0); // Clear table
        List<MenuItem> menuList = MenuDAO.getAllMenuItems();
        for (MenuItem item : menuList) {
            tableModel.addRow(new Object[]{item.getId(), item.getName(), item.getPrice()});
        }
    }

    private void addMenuItem() {
        String name = txtName.getText().trim();
        String priceText = txtPrice.getText().trim();

        if (name.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter item name and price.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double price = Double.parseDouble(priceText);
            MenuItem item = new MenuItem(0, name, price);
            if (MenuDAO.insertMenuItem(item)) {
                JOptionPane.showMessageDialog(this, "Item added successfully.");
                loadMenuItems();
                txtName.setText("");
                txtPrice.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add item.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid price format.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateMenuItem() {
        if (selectedItemId == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item to update.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String name = txtName.getText().trim();
        String priceText = txtPrice.getText().trim();

        if (name.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter item name and price.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double price = Double.parseDouble(priceText);
            MenuItem item = new MenuItem(selectedItemId, name, price);
            if (MenuDAO.updateMenuItem(item)) {
                JOptionPane.showMessageDialog(this, "Item updated successfully.");
                loadMenuItems();
                txtName.setText("");
                txtPrice.setText("");
                selectedItemId = -1;
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update item.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid price format.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteMenuItem() {
        if (selectedItemId == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item to delete.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this item?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (MenuDAO.deleteMenuItem(selectedItemId)) {
                JOptionPane.showMessageDialog(this, "Item deleted successfully.");
                loadMenuItems();
                txtName.setText("");
                txtPrice.setText("");
                selectedItemId = -1;
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete item.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuFrame().setVisible(true));
    }
}
