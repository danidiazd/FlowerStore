package FlowerStore.Products;

public class Decoration extends Product{
    private String material;
    public Decoration(String name, int quantity, double price, String material) {
        super(name, quantity, price);
        this.material = material;
    }

    public String getMaterial() {
        return material;
    }


    @Override
    public String toString() {
        return super.toString() + ", Material: " + getMaterial();
    }
}
