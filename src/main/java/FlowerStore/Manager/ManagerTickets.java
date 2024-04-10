package FlowerStore.Manager;

import Contexts.Product.Domain.Product;
import Contexts.Product.Domain.ProductsRepository;
import Contexts.Ticket.Domain.Ticket;
import Contexts.Ticket.Domain.TicketRepository;
import Contexts.Ticket.Infrastructure.Exceptions.NoTicketsFoundException;
import FlowerStore.Manager.Exceptions.InsufficientStockException;
import FlowerStore.Manager.ManagerProducts;
import Utils.InputControl.InputControl;

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
        boolean addMore = false;

        do {
            try {
                Product productToTicket = managerProducts.getProduct();
                int quantity = InputControl.readInt("Type quantity to add.");
                if (productToTicket.getQuantity() < quantity) {
                    throw new InsufficientStockException("Insufficient stock for product: " + productToTicket.getName());
                }
                ticketWithProducts.put(productToTicket, quantity);
                productToTicket = Ticket.updateStockStore(productToTicket, quantity);
                productsRepository.updateProduct(productToTicket);
                System.out.println(productToTicket.getName() + " added to buy.");
            } catch (InsufficientStockException e) {
                System.out.println("Error: " + e.getMessage());
                continue;
            }
            addMore = InputControl.readBoolean("Want add more? (yes or not) ");
        } while (addMore);

        return ticketWithProducts;
    }

    public void createNewTicket() throws InsufficientStockException {
        Date date = new Date();
        Map<Product, Integer> mapProduct = addProductsToTicket();
        double total = getTotalTicket(mapProduct);
        Ticket newTicket = new Ticket(date, mapProduct, total);
        ticketRepository.newTicket(newTicket);
        newTicket = ticketRepository.getLastTicket();
        newTicket.showTicket();

    }

    public void showAllTickets() throws NoTicketsFoundException {
        List<Ticket> alltickets = ticketRepository.getAllTickets();
        for (Ticket ticket : alltickets) {
            ticket.showTicket();
        }
    }
    public void shopBenefits() throws NoTicketsFoundException {
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
