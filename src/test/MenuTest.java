package test;

import dao.MenuDAO;
import model.MenuItem;
import java.util.List;

public class MenuTest {
    public static void main(String[] args) {
        // Insert new menu item
        MenuItem newItem = new MenuItem(0, "Grilled Chicken", 350.00);
        if (MenuDAO.insertMenuItem(newItem)) {
            System.out.println("New menu item added successfully!");
        }

        // Retrieve all menu items
        System.out.println("Menu Items List:");
        List<MenuItem> menuList = MenuDAO.getAllMenuItems();
        for (MenuItem item : menuList) {
            System.out.println(item.getId() + " | " + item.getName() + " | ₹" + item.getPrice());
        }

        // Update a menu item (Example: Update item with ID 1)
        MenuItem updateItem = new MenuItem(1, "Grilled Chicken Deluxe", 380.00);
        if (MenuDAO.updateMenuItem(updateItem)) {
            System.out.println("Menu item updated successfully!");
        }

        // Delete a menu item (Example: Delete item with ID 2)
        if (MenuDAO.deleteMenuItem(2)) {
            System.out.println("Menu item deleted successfully!");
        }
    }
}

