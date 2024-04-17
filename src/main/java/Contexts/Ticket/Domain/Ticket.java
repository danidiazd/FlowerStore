package Contexts.Ticket.Domain;

import Contexts.Product.Domain.Product;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Ticket {


    private int ticketID;
    private Date date;
    private Map<Product, Integer> products;
    private double total;


    public Ticket(Date date, Map<Product, Integer> products, double total) {
        this.date = date;
        this.products = products;
        this.total = total;
    }

    public Ticket(int ticketID, Date date, Map<Product, Integer> products, double total) {
        this.ticketID = ticketID;
        this.date = date;
        this.products = products;
        this.total = total;
    }

    public int getTicketID() {
        return ticketID;
    }

    public Date getDate() {
        return date;
    }

    public Map<Product, Integer> getProducts() {
        return this.products;
    }

    public double getTotal() {
        return total;
    }

    public static Product updateStockStore(Product product, int quantity) {
        product.setQuantity(product.getQuantity() - quantity);
        return product;
    }

    public void showTicket() {

        int nameWidth = 20, quantityWidth = 10;
        System.out.println("\n\t     TICKET  #" + getTicketID());
        SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        String formattedDate = dateFormat.format(getDate());
        System.out.println(formattedDate + "\n");        System.out.printf("%-" + nameWidth + "s %-" + quantityWidth + "s%n",
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
        }
        System.out.printf("%-" + nameWidth + "s %-" + quantityWidth + ".2f€%n",
                "TOTAL:", getTotal());
    }
}
