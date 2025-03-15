package model;

public class Order {
    private int id;
    private String itemName;
    private int quantity;
    private double price;
    private double total;

    public Order(int id, String itemName, int quantity, double price) {
        this.id = id;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
        this.total = quantity * price;
    }

    public int getId() { return id; }
    public String getItemName() { return itemName; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public double getTotal() { return total; }

    public void setId(int id) { this.id = id; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public void setQuantity(int quantity) { this.quantity = quantity; this.total = quantity * price; }
    public void setPrice(double price) { this.price = price; this.total = quantity * price; }
}

