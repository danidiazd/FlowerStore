package Contexts.Product.Domain;

public abstract class Product<T> {

    private int productId;
    private String name;
    private int quantity;
    private double price;
    private ProductType type;
    private T attributes;

    public Product(int productId, String name, int quantity, double price, ProductType type, T attributes) {
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
        this.attributes = attributes;
    }

    public Product(String name, int quantity, double price, ProductType type, T attributes) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
        this.attributes = attributes;
    }

    public T getAttributes() {
        return attributes;
    }

    public ProductType getType() {
        return type;
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return getName() + " with stock : " + getQuantity() + " with a price " + getPrice();
    }
}
