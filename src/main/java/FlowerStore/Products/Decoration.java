package FlowerStore.Products;

public class Decoration<T> extends Product{
    private T material;
    private int productId;
    private static int productIdNext = 1;
    public Decoration(String name, int quantity, double price, T material) {
        super(name, quantity, price, ProductType.DECORATION, material);
        this.material = material;
        this.productId = productIdNext;
    }

    public T getMaterial() {
        return material;
    }


    @Override
    public String toString() {
        return super.toString() + ", Material: " + getMaterial();
    }
}
