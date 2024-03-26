package FlowerStore;

import FlowerStore.Products.Product;

import java.util.ArrayList;

public class FlowerStore {
    private int idStore;
    private static int idStoreNext = 1;
    private String nameStore;
    private static ArrayList<Product> stockStore = new ArrayList<>();


    public FlowerStore(String nameStore) {
        this.nameStore = nameStore;
        this.idStore = idStoreNext;
    }

    public String getNameStore() {
        return nameStore;
    }

    public int getIdStore() {
        return idStore;
    }

    public static ArrayList<Product> getStockStore() {
        return stockStore;
    }

    public static void addToStock(Product product) {
        stockStore.add(product);
    }

    @Override
    public String toString() {
        return getNameStore() + " flowerStore with ID: " + getIdStore();
    }
}
