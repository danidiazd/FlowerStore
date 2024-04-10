package FlowerStore;

import Contexts.Product.Domain.Product;
import Contexts.Product.Domain.ProductsRepository;
import Contexts.Ticket.Domain.TicketRepository;
import Contexts.Ticket.Infrastructure.Exceptions.NoTicketsFoundException;
import FlowerStore.Manager.Exceptions.InsufficientStockException;
import FlowerStore.Manager.ManagerProducts;
import FlowerStore.Manager.ManagerTickets;

import java.util.List;

public class FlowerStore {
    private static String nameStore;
    private static FlowerStore instance;
    private ManagerProducts managerProducts;
    private ManagerTickets managerTickets;


    private FlowerStore(ProductsRepository productsRepository, TicketRepository ticketRepository, String nameStore) {
        this.nameStore = nameStore;
        this.managerProducts = ManagerProducts.getInstance(productsRepository);
        this.managerTickets = ManagerTickets.getInstance(ticketRepository, productsRepository);
    }

    public static FlowerStore getInstance(ProductsRepository productsRepository, TicketRepository ticketRepository, String nameStore) {
        if (instance == null) {
            instance = new FlowerStore(productsRepository, ticketRepository, nameStore);
        }
        return instance;
    }

    public static String getNameStore() {
        return nameStore;
    }

    public void setNameStore(String nameStore) {
        this.nameStore = nameStore;
    }

    public void addProductsToTicket()     {

        try {
            managerTickets.createNewTicket();
        } catch (InsufficientStockException e) {
            System.out.println(e);
        }

    }

    public void showAllTickets() {

        try {
            managerTickets.showAllTickets();
        } catch (NoTicketsFoundException e) {
            System.err.println(e);
        }
    }

    public void shopBenefits() {
        try {
            managerTickets.shopBenefits();
        } catch (NoTicketsFoundException e) {
            System.out.println(e);
        }
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
        managerProducts.showProducts(products);
    }

    public void showAllProducts() {
        managerProducts.showAllProducts();
    }

    @Override
    public String toString() {
        return "FlowerStore with name: " + getNameStore();
    }


}
