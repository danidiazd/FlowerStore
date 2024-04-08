package FlowerStore;

import Contexts.Product.Domain.ProductsRepository;

public class FlowerStore {
     private String nameStore;
    private static FlowerStore instance;
    private static ProductsRepository productsRepository;


    private FlowerStore() {
        this.nameStore = nameStore;
    }

    public static FlowerStore getInstance() {
        if (instance == null) {
            instance = new FlowerStore();
        }
        return instance;
    }

    public String getNameStore() {
        return nameStore;
    }

    public void setNameStore(String nameStore) {
        this.nameStore = nameStore;
    }


    @Override
    public String toString() {
        return "FlowerStore with name: " + getNameStore();
    }


}
