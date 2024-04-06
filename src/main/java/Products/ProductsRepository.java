package Products;

import FlowerStore.Ticket.Ticket;

import java.util.List;
import java.util.Map;

public interface ProductsRepository {
    List<Product> getAllProducts();
    List<Product> getFlowers();
    List<Product> getTrees();
    List<Product> getDecorations();
    void addPrimaryStock();
    void updateProduct(Product product);
    void deleteProduct(Product product);
    void newTicket(Map<Product, Integer> ticket);
    List<Ticket> getAllTickets();
    void addProduct(Product product);
}
