package FlowerStore;

public class FlowerStore {
    private int idStore;
    private static int idStoreNext = 1;
    private String nameStore;

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


    @Override
    public String toString() {
        return getNameStore() + " flowerStore with ID: " + getIdStore();
    }
}
