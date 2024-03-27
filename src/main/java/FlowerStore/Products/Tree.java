package FlowerStore.Products;

public class Tree<T> extends Product{
    private T height;
    private int productId;
    private static int productIdNext = 1;
    public Tree(String name, int quantity, double price, T height) {
        super(name, quantity, price, ProductType.TREE, height);
        this.height = height;
        this.productId = productIdNext;
    }

    public T getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return super.toString() + ", Height: " + getHeight();
    }
}
