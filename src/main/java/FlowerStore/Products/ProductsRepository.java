package FlowerStore.Products;

import java.util.List;

public interface ProductsRepository {
    List<Product> getAllProducts();
    void addPrimaryStock();
    void updateProduct(Product product);
    void newTicket();
}
