package Contexts.Product.Infrastructure.MongoDB;

import Contexts.Product.Domain.*;
import Contexts.Ticket.Domain.Ticket;
import FlowerStore.FlowerStore;
import Infrastructure.Connections.MongoDBConnection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductRepositoryMongoDB<T> implements ProductsRepository {

    private MongoCollection<Document> collection;

    public ProductRepositoryMongoDB(MongoDBConnection mongoDBConnection) {
        this.collection = mongoDBConnection.mongoDatabase.getCollection("products");
    }


    private int nextProductId() {
        Product lastProduct = getLastProduct();
        if (lastProduct == null) return 1;
        return lastProduct.getProductId() + 1;
    }

    @Override
    public Product getProduct(int productId) {

        Document query = new Document("productId", productId);
        Document document = collection.find(query).first();
        return documentToProduct(document);
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
    public Product getLastProduct() {
        Document document = collection.find().sort(new Document("productId", -1)).first();

        if (document == null) return null;

        return documentToProduct(document);
    }

    public List<Product> getFlowers() {
        List<Product> allProducts = getAllProducts();
        return allProducts.stream()
                .filter(product -> product.getType() == ProductType.FLOWER)
                .collect(Collectors.toList());
    }

    public List<Product> getTrees() {
        List<Product> allProducts = getAllProducts();
        return allProducts.stream()
                .filter(product -> product.getType() == ProductType.TREE)
                .collect(Collectors.toList());
    }

    public List<Product> getDecorations() {
        List<Product> allProducts = getAllProducts();
        return allProducts.stream()
                .filter(product -> product.getType() == ProductType.DECORATION)
                .collect(Collectors.toList());
    }

    @Override
    public void addPrimaryStock() {

        List<Document> stock = new ArrayList<>();

        stock.add(new Document("type", ProductType.TREE.toString())
                .append("productId", 1)
                .append("name", "Manzano")
                .append("quantity", 0)
                .append("price", 0.0)
                .append("attribute", 1.5));

        stock.add(new Document("type", ProductType.TREE.toString())
                .append("productId", 2)
                .append("name", "Olivo")
                .append("quantity", 0)
                .append("price", 0.0)
                .append("attribute", 2.0));

        stock.add(new Document("type", ProductType.TREE.toString())
                .append("productId", 3)
                .append("name", "Pino")
                .append("quantity", 0)
                .append("price", 0.0)
                .append("attribute", 3.0));

        stock.add(new Document("type", ProductType.TREE.toString())
                .append("productId", 4)
                .append("name", "Rosal")
                .append("quantity", 0)
                .append("price", 0.0)
                .append("attribute", 0.5));

        stock.add(new Document("type", ProductType.FLOWER.toString())
                .append("productId", 5)
                .append("name", "Rosa")
                .append("quantity", 0)
                .append("price", 0.0)
                .append("attribute", "Roja"));

        stock.add(new Document("type", ProductType.FLOWER.toString())
                .append("productId", 6)
                .append("name", "Girasol")
                .append("quantity", 0)
                .append("price", 0.0)
                .append("attribute", "Blanca"));

        stock.add(new Document("type", ProductType.FLOWER.toString())
                .append("productId", 7)
                .append("name", "Amapola")
                .append("quantity", 0)
                .append("price", 0.0)
                .append("attribute", "Roja"));

        stock.add(new Document("type", ProductType.FLOWER.toString())
                .append("productId", 8)
                .append("name", "Lirio")
                .append("quantity", 0)
                .append("price", 0.0)
                .append("attribute", "Naranja"));

        stock.add(new Document("type", ProductType.FLOWER.toString())
                .append("productId", 9)
                .append("name", "Clavel")
                .append("quantity", 0)
                .append("price", 0.0)
                .append("attribute", "Amarillo"));

        stock.add(new Document("type", ProductType.DECORATION.toString())
                .append("productId", 10)
                .append("name", "Jarron")
                .append("quantity", 0)
                .append("price", 0.0)
                .append("attribute", "Madera"));

        stock.add(new Document("type", ProductType.DECORATION.toString())
                .append("productId", 11)
                .append("name", "Tiesto")
                .append("quantity", 0)
                .append("price", 0.0)
                .append("attribute", "Plastico"));

        stock.add(new Document("type", ProductType.DECORATION.toString())
                .append("productId", 12)
                .append("name", "Jarron")
                .append("quantity", 0)
                .append("price", 0.0)
                .append("attribute", "Plastico"));

        stock.add(new Document("type", ProductType.DECORATION.toString())
                .append("productId", 13)
                .append("name", "Tiesto")
                .append("quantity", 0)
                .append("price", 0.0)
                .append("attribute", "Madera"));


        collection.insertMany(stock);

    }


    @Override
    public void addProduct(Product product) {
        Document productDocument = new Document("name", product.getName())
                .append("productId", nextProductId())
                .append("quantity", product.getQuantity())
                .append("price", product.getPrice())
                .append("type", product.getType().toString())
                .append("attribute", product.getAttributes());
        collection.insertOne(productDocument);
    }

    @Override
    public void updateProduct(Product product) {
        Document query = new Document("name", product.getName()).append("attribute", product.getAttributes());
        Document update = new Document("$set", new Document("quantity", product.getQuantity())
                .append("price", product.getPrice()));

        collection.updateOne(query, update);
        System.out.println("Stock updated from " + product.getName());
    }

    @Override
    public void deleteProduct(Product product) {
        Document query = new Document("name", product.getName())
                .append("attribute", product.getAttributes());
        collection.deleteOne(query);
        System.out.println("Product: " + product.getName() + " deleted from DataBase");
    }


    public Product documentToProduct(Document document) {
        int productId = document.getInteger("productId");
        String name = document.getString("name");
        int quantity = document.getInteger("quantity");
        double price = document.getDouble("price");
        ProductType type = ProductType.valueOf(document.getString("type").toUpperCase());

        Object attribute = document.get("attribute");


        Product product;

        if (type == ProductType.FLOWER) {
            product = new Flower<>(productId, name, quantity, price, attribute);
        } else if (type == ProductType.DECORATION) {
            product = new Decoration<>(productId, name, quantity, price, attribute);
        } else if (type == ProductType.TREE) {
            product = new Tree<>(productId, name, quantity, price, (Double) attribute);
        } else {
            throw new IllegalArgumentException("Tipo de atributo no válido para un producto tipo TREE");
        }
        return product;
    }
}
