package model;

import java.util.List;

public class Billing {
    private List<Order> orders;
    private double totalAmount;

    public Billing(List<Order> orders) {
        this.orders = orders;
        this.totalAmount = calculateTotal();
    }

    private double calculateTotal() {
        double total = 0;
        for (Order order : orders) {
            total += order.getTotal();
        }
        return total;
    }

    public List<Order> getOrders() { return orders; }
    public double getTotalAmount() { return totalAmount; }

    public String generateReceipt() {
        StringBuilder receipt = new StringBuilder();
        receipt.append("=========== RECEIPT ===========\n");
        receipt.append("ID   Item Name       Quantity   Price   Total\n");
        receipt.append("-----------------------------------------------\n");

        for (Order order : orders) {
            receipt.append(String.format("%-5d %-15s %-10d %-7.2f %.2f\n", 
                    order.getId(), order.getItemName(), order.getQuantity(), order.getPrice(), order.getTotal()));
        }

        receipt.append("-----------------------------------------------\n");
        receipt.append(String.format("Total Amount: %.2f\n", totalAmount));
        receipt.append("=========== THANK YOU ===========\n");
        return receipt.toString();
    }
}

