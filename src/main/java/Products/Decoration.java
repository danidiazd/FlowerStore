package Products;

public class Decoration<T> extends Product{
    private T material;

    public Decoration(String name, int quantity, double price, T material) {
        super(name, quantity, price, ProductType.DECORATION, material);
        this.material = material;
    }

    public T getMaterial() {
        return material;
    }


    @Override
    public String toString() {
        return super.toString() + ", Material: " + getMaterial();
    }
}
