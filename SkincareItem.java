public class SkincareItem {
    private String name;
    private int quantity;
    private double price;
    private String brand;
    private String expiry;

    public SkincareItem(String name, int quantity, double price, String brand, String expiry) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.brand = brand;
        this.expiry = expiry;
    }

    public double getCost() {
        return quantity * price;
    }

    
    public String display() {
        return "Name: " + name +
               ",quantity: " + quantity +
               ",\n Price: " + price +
               ",brand: " + brand +
               ",\n Expiry: " + expiry;
    }
}