package Products;

public class Flower<T> extends Product{
    private T color;

    public Flower(String name, int quantity, double price, T color) {
        super(name, quantity, price, ProductType.FLOWER, color);
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
