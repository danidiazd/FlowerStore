package Contexts.Products.Domain;

public class Flower<T> extends Product<T> {
    private final T color;

    public Flower(String name, int quantity, double price, T color) {
        super(name, quantity, price, ProductType.FLOWER, color);
        this.color = color;
    }

    public Flower(int productId, String name, int quantity, double price, T color) {
        super(productId, name, quantity, price, ProductType.FLOWER, color);
        this.color = color;
    }

    public T getColor() {
        return color;
    }

    @Override
    public String toString() {
        return super.toString() + ", Color: " + getColor();
    }
}
