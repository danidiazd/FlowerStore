package FlowerStore.Products;

public class Flower<T> extends Product{
    private T color;
    private int productId;
    private static int productIdNext = 1;
    public Flower(String name, int quantity, double price, T color) {
        super(name, quantity, price, ProductType.FLOWER, color);
        this.color = color;
        this.productId = productIdNext;
    }

    public T getColor() {
        return color;
    }

    @Override
    public String toString() {
        return super.toString() + ", Color: " + getColor();
    }
}
