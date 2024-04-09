package Contexts.Ticket.Domain;

import Contexts.Product.Domain.Product;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Ticket {
<<<<<<< Updated upstream
    private int ticketID;
=======
    private int id;
>>>>>>> Stashed changes
    private Date date;
    private Map<Product, Integer> products;

    public Ticket(Date date) {
        this.date = new Date();
        products = new HashMap<>();
    }

<<<<<<< Updated upstream
    public Ticket(int ticketID, Date date) {
        this.ticketID = ticketID;
        this.date = date;
        products = new HashMap<>();
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public Date getDate() {
        return date;
=======
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
>>>>>>> Stashed changes
    }

    public void addProductToTicket(Product product, int quantity) {
        products.put(product, quantity);
    }

    public Product updateStockStore(Product product, int quantity) {
        product.setQuantity(product.getQuantity() - quantity);
        return product;
    }

    public Map<Product, Integer> getProducts() {
        return this.products;
    }

    public void showTicket() {

        int nameWidth = 20, quantityWidth = 10;
        System.out.println("\n\t TICKET  #" + getTicketID()); //NO SE INCREMENTA
        double price = 0;
        System.out.println(getDate() + "\n");
        System.out.printf("%-" + nameWidth + "s %-" + quantityWidth + "s%n",
                "PRODUCT", "QUANTITY");
        for (int i = 0; i < nameWidth + quantityWidth + 4; i++) {
            System.out.print("-");
        }
        System.out.println();

        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            System.out.printf("%-" + nameWidth + "s %-" + quantityWidth + "d%n",
                    product.getName(), quantity);
            price += product.getPrice() * quantity;
        }
        System.out.printf("%-" + nameWidth + "s %-" + quantityWidth + ".2f€%n",
                "TOTAL:", price);
    }
}
