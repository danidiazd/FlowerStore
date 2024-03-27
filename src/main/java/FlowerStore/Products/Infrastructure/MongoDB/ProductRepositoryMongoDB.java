package FlowerStore.Products.Infrastructure.MongoDB;

import Connections.MongoDBConnection;
import FlowerStore.Products.Product;
import FlowerStore.Products.ProductType;
import FlowerStore.Products.ProductsRepository;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductRepositoryMongoDB implements ProductsRepository {

    private MongoCollection<Document> collection;

    public ProductRepositoryMongoDB(MongoDBConnection mongoDBConnection) {
        this.collection = mongoDBConnection.mongoDatabase.getCollection("products");
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        FindIterable<Document> cursor = collection.find();
        for (Document document : cursor) {
            Product product = documentToProduct(document);
            products.add(product);
        }
        return products;
    }

    @Override
    public void addProduct(Product product) {
        //TODO: ME SIGUE CREANDO 2 DOCUMENTOS CON MISMO NOMBRE Y ATRIBUTO
        Document query = new Document("name", product.getName())
                .append("attribute", product.getAttributes().toString())
                .append("type", product.getType().toString());

        Document existingProduct = collection.find(query).first();
        if (existingProduct != null) {
            int actualQuantity = existingProduct.getInteger("quantity");
            int newQuantity = actualQuantity + product.getQuantity();
            double newPrice = product.getPrice();

            Document updateObject = new Document();
            updateObject.append("quantity", newQuantity);
            updateObject.append("price", newPrice);

            collection.updateOne(query, new Document("$set", updateObject));
        } else {
            Map<String, Object> productMap = Map.of(
                    "id", product.getProductId(),
                    "name", product.getName(),
                    "price", product.getPrice(),
                    "quantity", product.getQuantity(),
                    "type", product.getType().toString(),
                    "attribute", product.getAttributes().toString());

            Document productDocument = new Document(productMap);
            collection.insertOne(productDocument);
        }
    }

    @Override
    public void updateProduct(Product product) {
        // Implementar la actualización del producto si es necesario
    }

    private Product documentToProduct(Document document) {
        int id = document.getInteger("id");
        String name = document.getString("name");
        int quantity = document.getInteger("quantity");
        double price = document.getDouble("price");
        ProductType type = ProductType.valueOf(document.getString("type"));
        String attribute = document.getString("attribute");

        Product product = new Product(name, quantity, price, type, attribute);
        product.setProductId(id);
        return product;
    }
}
