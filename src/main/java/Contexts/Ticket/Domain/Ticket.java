package Contexts.Ticket.Domain;

import Contexts.Product.Domain.Product;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Ticket {
    private String id;
    private Date date;
    private Map<Product, Integer> products;

    public Ticket(Date date) {
        this.id = id;
        this.date = new Date();
        products = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        System.out.println("\t TICKET \t");
        double price = 0;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            System.out.println(product.getName() + "....." + quantity);
            price += product.getPrice() * quantity;
        }
        System.out.println("TOTAL: " + price);
    }


}
