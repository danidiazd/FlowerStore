package Contexts.Product.Domain;

import Contexts.Ticket.Domain.Ticket;

import java.util.List;
import java.util.Map;

public interface ProductsRepository {

    Product getProduct(int id);
    Product getLastProduct();
    List<Product> getAllProducts();
    List<Product> getFlowers();
    List<Product> getTrees();
    List<Product> getDecorations();
    void addPrimaryStock();
    void updateProduct(Product product);
    void deleteProduct(Product product);
    void addProduct(Product product);

}
