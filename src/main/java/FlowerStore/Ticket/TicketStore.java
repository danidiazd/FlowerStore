package FlowerStore.Ticket;

import FlowerStore.Products.Product;

import java.util.HashMap;
import java.util.Map;

public class TicketStore {

    private static TicketStore instance;
    private Map<Product, Integer> products;

    private TicketStore() {
        products = new HashMap<>();
    }

    // singleton
    public static TicketStore getInstance() {
        if (instance == null) {
            instance = new TicketStore();
        }
        return instance;
    }

    public void addProductToTicket(Product product, int quantity) {
        product.setQuantity(product.getQuantity() - quantity);
        products.put(product, quantity);
    }

    public void showTicket() {
        System.out.println("\t TICKET \t");
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            System.out.println(product.getName() + "---" + quantity);
        }
    }
}
