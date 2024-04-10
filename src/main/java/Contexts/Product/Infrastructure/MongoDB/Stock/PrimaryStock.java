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
        stock.add(createProductDocument(ProductType.TREE, 1, "Manzano", 50, 20.5, 1.5));
        stock.add(createProductDocument(ProductType.TREE, 2, "Olivo", 50, 11.99, 2.0));
        stock.add(createProductDocument(ProductType.TREE, 3, "Pino", 50, 8.50, 3.0));
        stock.add(createProductDocument(ProductType.TREE, 4, "Rosal", 50, 6.50, 0.5));
    }

    private void addFlowerStock(List<Document> stock) {
        stock.add(createProductDocument(ProductType.FLOWER, 5, "Rosa", 50, 4.0, "Roja"));
        stock.add(createProductDocument(ProductType.FLOWER, 6, "Girasol", 50, 3.50, "Blanca"));
        stock.add(createProductDocument(ProductType.FLOWER, 7, "Amapola", 50, 2.75, "Roja"));
        stock.add(createProductDocument(ProductType.FLOWER, 8, "Lirio", 50, 1.5, "Naranja"));
        stock.add(createProductDocument(ProductType.FLOWER, 9, "Clavel", 50, 9.50, "Amarillo"));
    }

    private void addDecorationStock(List<Document> stock) {
        stock.add(createProductDocument(ProductType.DECORATION, 10, "Jarron", 50, 20.50, "Madera"));
        stock.add(createProductDocument(ProductType.DECORATION, 11, "Tiesto", 50, 13.50, "Plastico"));
        stock.add(createProductDocument(ProductType.DECORATION, 12, "Jarron", 50, 9.99, "Plastico"));
        stock.add(createProductDocument(ProductType.DECORATION, 13, "Tiesto", 50, 10.0, "Madera"));
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