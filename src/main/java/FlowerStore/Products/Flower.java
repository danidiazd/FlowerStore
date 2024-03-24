package FlowerStore.Products;

public class Flower extends Product{
    private String color;
    public Flower(String name, int quantity, double price, String color) {
        super(name, quantity, price);
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return super.toString() + ", Color: " + getColor();
    }
}
