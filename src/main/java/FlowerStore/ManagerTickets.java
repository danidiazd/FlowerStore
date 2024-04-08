package FlowerStore;

import Contexts.Product.Domain.Product;
import Contexts.Product.Domain.ProductsRepository;
import Contexts.Ticket.Domain.Ticket;
import Utils.InputControl.InputControl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ManagerTickets {

    private static ManagerTickets instance;
    private ProductsRepository productsRepository;

    private ManagerTickets(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public static ManagerTickets getInstance(ProductsRepository productsRepository) {
        if (instance == null) {
            instance = new ManagerTickets(productsRepository);
        }
        return instance;
    }

    public void addProductsToTicket() {
        ManagerProducts managerProducts = ManagerProducts.getInstance(productsRepository);
        boolean addMore;
        Date date = new Date();
        Ticket ticket = new Ticket(date);
        do {
            Product productToTicket = managerProducts.getProduct();
            int quantity = InputControl.readInt("Type quantity to add.");
            // CHECK STOCK
            if (productToTicket.getQuantity() < quantity) throw new RuntimeException(""); //falta excepcion personalizada
            ticket.addProductToTicket(productToTicket, quantity);
            productToTicket = ticket.updateStockStore(productToTicket, quantity);
            productsRepository.updateProduct(productToTicket);
            System.out.println(productToTicket.getName() + " added to buy.");
            addMore = InputControl.readBoolean("Want add more? (yes or not) ");
        } while (addMore);

        Map<Product, Integer> ticketWithProducts = ticket.getProducts();
        productsRepository.newTicket(ticketWithProducts);
        ticket.showTicket();
    }

    public void showAllTickets() {
        List<Ticket> alltickets = new ArrayList<>();
        alltickets = productsRepository.getAllTickets();
        for (Ticket ticket : alltickets) {
            ticket.showTicket();
        }
    }
}
