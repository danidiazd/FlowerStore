package FlowerStore.Products;

public class Tree extends Product{
    private double height;
    public Tree(String name, int quantity, double price, double height) {
        super(name, quantity, price);
        this.height = height;
    }

    public double getHeight() {
        return height;
    }


    @Override
    public String toString() {
        return super.toString() + ", Height: " + getHeight();
    }
}
