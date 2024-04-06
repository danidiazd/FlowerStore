package Products;

import java.util.List;

public interface ProductsRepository {
    List<Product> getAllProducts();
    List<Product> getFlowers();
    List<Product> getTrees();
    List<Product> getDecorations();
    void addPrimaryStock();
    void updateProduct(Product product);
    void deleteProduct(Product product);
    void newTicket();

    void addProduct(Product product);
}
