package FlowerStore.Products;

import java.util.List;

public interface ProductsRepository {
    List<Product> getAllProducts();
    void addProduct(Product product);
    void updateProduct(Product product);
}
