package dao;

import model.MenuItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO {
    private static final String INSERT_QUERY = "INSERT INTO menu_items (name, price) VALUES (?, ?)";
    private static final String UPDATE_QUERY = "UPDATE menu_items SET name = ?, price = ? WHERE item_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM menu_items WHERE item_id = ?";
    private static final String SELECT_QUERY = "SELECT * FROM menu_items";

    // Method to insert a new menu item
    public static boolean insertMenuItem(MenuItem item) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_QUERY)) {

            ps.setString(1, item.getName());
            ps.setDouble(2, item.getPrice());

            return ps.executeUpdate() > 0; // Return true if insert is successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to update an existing menu item
    public static boolean updateMenuItem(MenuItem item) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_QUERY)) {

            ps.setString(1, item.getName());
            ps.setDouble(2, item.getPrice());
            ps.setInt(3, item.getId());

            return ps.executeUpdate() > 0; // Return true if update is successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to delete a menu item by ID
    public static boolean deleteMenuItem(int id) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_QUERY)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0; // Return true if delete is successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to retrieve all menu items
    public static List<MenuItem> getAllMenuItems() {
        List<MenuItem> menuList = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_QUERY);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                menuList.add(new MenuItem(rs.getInt("item_id"), rs.getString("name"), rs.getDouble("price")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menuList;
    }
}

