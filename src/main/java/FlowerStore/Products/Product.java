package FlowerStore.Products;

import FlowerStore.FlowerStore;

import java.util.Objects;

public class Product<T> {

    private int productId;
    private static int productIdNext = 1;
    private String name;
    private int quantity;
    private double price;
    private ProductType type;
    private T attributes;

    public Product(String name, int quantity, double price, ProductType type, T attributes) {
        this.productId = productIdNext++;
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

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return getName() + " with ID : " + getProductId() + " with a price " + getPrice();
    }
}
