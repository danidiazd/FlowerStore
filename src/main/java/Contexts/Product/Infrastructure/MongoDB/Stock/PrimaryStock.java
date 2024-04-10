package Contexts.Product.Infrastructure.MongoDB.Stock;

import Contexts.Product.Domain.*;
import Infrastructure.Connections.MongoDBConnection;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class PrimaryStock {

    private final MongoCollection<Document> collection;


    public PrimaryStock(MongoDBConnection mongoDBConnection) {
        this.collection = mongoDBConnection.mongoDatabase.getCollection("products");
    }

    public void loadInitialStock() {
        if (collection.countDocuments() == 0) {
            List<Document> stock = new ArrayList<>();
            addTreeStock(stock);
            addFlowerStock(stock);
            addDecorationStock(stock);
            collection.insertMany(stock);
        }
    }

    private void addTreeStock(List<Document> stock) {
        stock.add(createProductDocument(ProductType.TREE, 1, "manzano", 50, 20.5, 1.5));
        stock.add(createProductDocument(ProductType.TREE, 2, "olivo", 50, 11.99, 2.0));
        stock.add(createProductDocument(ProductType.TREE, 3, "pino", 50, 8.50, 3.0));
        stock.add(createProductDocument(ProductType.TREE, 4, "rosal", 50, 6.50, 0.5));
    }

    private void addFlowerStock(List<Document> stock) {
        stock.add(createProductDocument(ProductType.FLOWER, 5, "rosa", 50, 4.0, "roja"));
        stock.add(createProductDocument(ProductType.FLOWER, 6, "girasol", 50, 3.50, "blanca"));
        stock.add(createProductDocument(ProductType.FLOWER, 7, "amapola", 50, 2.75, "roja"));
        stock.add(createProductDocument(ProductType.FLOWER, 8, "lirio", 50, 1.5, "naranja"));
        stock.add(createProductDocument(ProductType.FLOWER, 9, "clavel", 50, 9.50, "amarillo"));
    }

    private void addDecorationStock(List<Document> stock) {
        stock.add(createProductDocument(ProductType.DECORATION, 10, "jarron", 50, 20.50, "madera"));
        stock.add(createProductDocument(ProductType.DECORATION, 11, "tiesto", 50, 13.50, "plastico"));
        stock.add(createProductDocument(ProductType.DECORATION, 12, "jarron", 50, 9.99, "plastico"));
        stock.add(createProductDocument(ProductType.DECORATION, 13, "tiesto", 50, 10.0, "madera"));
    }

    private Document createProductDocument(ProductType type, int productId, String name, int quantity, double price, Object attribute) {
        return new Document("type", type.toString())
                .append("productId", productId)
                .append("name", name)
                .append("quantity", quantity)
                .append("price", price)
                .append("attribute", attribute);
    }
}