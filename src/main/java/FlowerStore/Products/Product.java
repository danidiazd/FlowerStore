package FlowerStore.Products;

public abstract class Product {

    private int productId;
    private static int productIdNext = 1;
    private String name;
    private int quantity;
    private double price;

    public Product(String name, int quantity, double price) {

        this.productId = productIdNext++;
        this.name = name;
        this.price = price;
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

    @Override
    public String toString() {
        return getName() + " with ID : " + getProductId() + " with a price " + getPrice();
    }
}
