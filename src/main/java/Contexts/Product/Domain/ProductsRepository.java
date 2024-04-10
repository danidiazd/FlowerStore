package Contexts.Product.Domain;

import java.util.List;

public interface ProductsRepository {
    void initialize();

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
