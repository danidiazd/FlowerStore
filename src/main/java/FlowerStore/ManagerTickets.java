package FlowerStore;

import Contexts.Product.Domain.Product;
import Contexts.Product.Domain.ProductsRepository;
import Contexts.Ticket.Domain.Ticket;
import Contexts.Ticket.Domain.TicketRepository;
import Utils.InputControl.InputControl;
import org.bson.Document;

import java.util.*;

public class ManagerTickets {

    private static ManagerTickets instance;
    private TicketRepository ticketRepository;
    private ProductsRepository productsRepository;

    private ManagerTickets(TicketRepository ticketRepository, ProductsRepository productsRepository) {
        this.ticketRepository = ticketRepository;
        this.productsRepository = productsRepository;

    }

    public static ManagerTickets getInstance(TicketRepository ticketRepository, ProductsRepository productsRepository) {
        if (instance == null) {
            instance = new ManagerTickets(ticketRepository, productsRepository);
        }
        return instance;
    }

    public Map<Product, Integer> addProductsToTicket() {
        ManagerProducts managerProducts = ManagerProducts.getInstance(productsRepository);
        Map<Product, Integer> ticketWithProducts = new HashMap<>();
        boolean addMore;

        do {
            Product productToTicket = managerProducts.getProduct();
            int quantity = InputControl.readInt("Type quantity to add.");
            // CHECK STOCK
            if (productToTicket.getQuantity() < quantity) throw new RuntimeException(""); //falta excepcion personalizada
            ticketWithProducts.put(productToTicket, quantity);
            productToTicket = Ticket.updateStockStore(productToTicket, quantity);
            productsRepository.updateProduct(productToTicket);
            System.out.println(productToTicket.getName() + " added to buy.");
            addMore = InputControl.readBoolean("Want add more? (yes or not) ");
        } while (addMore);


        return ticketWithProducts;
    }
    public void createNewTicket() {
        Date date = new Date();
        Map<Product, Integer> mapProduct = addProductsToTicket();
        double total = getTotalTicket(mapProduct);
        Ticket newTicket = new Ticket(date, mapProduct, total);
        ticketRepository.newTicket(newTicket);
    }

    public void showAllTickets() {
        List<Ticket> alltickets = ticketRepository.getAllTickets();
        for (Ticket ticket : alltickets) {
            ticket.showTicket();
        }
    }
    public void shopBenefits() {
        ticketRepository.getAllSales(ticketRepository.getAllTickets());
    }

    private double getTotalTicket(Map<Product, Integer> ticket) {
        double total = 0;
        for (Map.Entry<Product, Integer> entry : ticket.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            total += product.getPrice() * quantity;
        }
        return total;
    }


}
