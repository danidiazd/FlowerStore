package Contexts.Ticket.Domain;

import Contexts.Product.Domain.Product;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Ticket {
    private int id;
    private Date date;
    private Map<Product, Integer> products;

    public Ticket(Date date) {
        this.date = new Date();
        products = new HashMap<>();
    }

    public Ticket(int id, Date date) {
        this.id = id;
        this.date = date;
        products = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
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
        System.out.println("\n\t TICKET  #" + getId());
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
