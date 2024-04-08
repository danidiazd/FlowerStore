package FlowerStore;

import Contexts.Product.Domain.Product;
import Contexts.Product.Domain.ProductsRepository;
import Contexts.Ticket.Domain.TicketRepository;

import java.util.List;

public class FlowerStore {
    private static String nameStore;
    private static FlowerStore instance;
    private ManagerProducts managerProducts;
    private ManagerTickets managerTickets;



    private FlowerStore(ProductsRepository productsRepository,TicketRepository ticketRepository, String nameStore) {
        this.nameStore = nameStore;
        this.managerProducts = ManagerProducts.getInstance(productsRepository);
        this.managerTickets = ManagerTickets.getInstance(ticketRepository, productsRepository);
    }

    public static FlowerStore getInstance(ProductsRepository productsRepository, TicketRepository ticketRepository,String nameStore) {
        if (instance == null) {
            instance = new FlowerStore(productsRepository , ticketRepository, nameStore);
        }
        return instance;
    }

    public static String getNameStore() {
        return nameStore;
    }

    public void setNameStore(String nameStore) {
        this.nameStore = nameStore;
    }

    public void addProductsToTicket() {
        managerTickets.addProductsToTicket();
    }

    public void showAllTickets() {
        managerTickets.showAllTickets();
    }
    public void shopBenefits() {
        managerTickets.shopBenefits();
    }
    public void updateStock() {
        managerProducts.updateStock();
    }

    public void deleteProduct() {
        managerProducts.deleteProduct();
    }

    public void addProduct() {
        managerProducts.addProduct();
    }

    public void getProduct() {
        managerProducts.getProduct();
    }

    public void totalValue() {
        managerProducts.totalValue();
    }

    public List<Product> getType() {
        return managerProducts.getType();
    }

    public void showTypeProducts(List<Product> products) {
        managerProducts.showAllProducts();
    }
    public void showAllProducts() {
        managerProducts.showAllProducts();
    }

    @Override
    public String toString() {
        return "FlowerStore with name: " + getNameStore();
    }


}
