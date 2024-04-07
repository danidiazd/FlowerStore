package Products;

public class Tree<T> extends Product <T>{
    private final T height;

    public Tree(String name, int quantity, double price, T height) {
        super(name, quantity, price, ProductType.TREE, height);
        this.height = height;
    }

    public T getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return super.toString() + ", Height: " + getHeight();
    }
}
