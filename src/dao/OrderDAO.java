package dao;

import model.Order;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant_db", "root", "");
    }

    public static boolean insertOrder(Order order) {
        String sql = "INSERT INTO orders (item_name, quantity, price, total) VALUES (?, ?, ?, ?)";
        try (Connection con = getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, order.getItemName());
            stmt.setInt(2, order.getQuantity());
            stmt.setDouble(3, order.getPrice());
            stmt.setDouble(4, order.getTotal());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        try (Connection con = getConnection(); PreparedStatement stmt = con.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                orders.add(new Order(rs.getInt("id"), rs.getString("item_name"), rs.getInt("quantity"), rs.getDouble("price")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public static boolean updateOrder(Order order) {
        String sql = "UPDATE orders SET item_name=?, quantity=?, price=?, total=? WHERE id=?";
        try (Connection con = getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, order.getItemName());
            stmt.setInt(2, order.getQuantity());
            stmt.setDouble(3, order.getPrice());
            stmt.setDouble(4, order.getTotal());
            stmt.setInt(5, order.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteOrder(int id) {
        String sql = "DELETE FROM orders WHERE id=?";
        try (Connection con = getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

