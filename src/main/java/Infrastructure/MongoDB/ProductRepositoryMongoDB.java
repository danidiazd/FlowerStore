package Infrastructure.MongoDB;

import Connections.MongoDBConnection;
import FlowerStore.FlowerStore;
import Products.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductRepositoryMongoDB<T> implements ProductsRepository {

    private MongoCollection<Document> collection;
    private MongoCollection<Document> ticketCollection;
    private FlowerStore flowerStore;

    public ProductRepositoryMongoDB(MongoDBConnection mongoDBConnection) {
        this.collection = mongoDBConnection.mongoDatabase.getCollection("products");
        this.ticketCollection = mongoDBConnection.mongoDatabase.getCollection("tickets");

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
                .append("name", "Manzano")
                .append("quantity", 0)
                .append("price", 0.0)
                .append("attribute", 1.5));

        stock.add(new Document("type", ProductType.TREE.toString())
                .append("name", "Olivo")
                .append("quantity", 0)
                .append("price", 0.0)
                .append("attribute", 2.0));

        stock.add(new Document("type", ProductType.TREE.toString())
                .append("name", "Pino")
                .append("quantity", 0)
                .append("price", 0.0)
                .append("attribute", 3.0));

        stock.add(new Document("type", ProductType.TREE.toString())
                .append("name", "Rosal")
                .append("quantity", 0)
                .append("price", 0.0)
                .append("attribute", 0.5));

        stock.add(new Document("type", ProductType.FLOWER.toString())
                .append("name", "Rosa")
                .append("quantity", 0)
                .append("price", 0.0)
                .append("attribute", "Roja"));

        stock.add(new Document("type", ProductType.FLOWER.toString())
                .append("name", "Girasol")
                .append("quantity", 0)
                .append("price", 0.0)
                .append("attribute", "Blanca"));

        stock.add(new Document("type", ProductType.FLOWER.toString())
                .append("name", "Amapola")
                .append("quantity", 0)
                .append("price", 0.0)
                .append("attribute", "Roja"));

        stock.add(new Document("type", ProductType.FLOWER.toString())
                .append("name", "Lirio")
                .append("quantity", 0)
                .append("price", 0.0)
                .append("attribute", "Naranja"));

        stock.add(new Document("type", ProductType.FLOWER.toString())
                .append("name", "Clavel")
                .append("quantity", 0)
                .append("price", 0.0)
                .append("attribute", "Amarillo"));

        stock.add(new Document("type", ProductType.DECORATION.toString())
                .append("name", "Jarron")
                .append("quantity", 0)
                .append("price", 0.0)
                .append("attribute", "Madera"));

        stock.add(new Document("type", ProductType.DECORATION.toString())
                .append("name", "Tiesto")
                .append("quantity", 0)
                .append("price", 0.0)
                .append("attribute", "Plastico"));

        stock.add(new Document("type", ProductType.DECORATION.toString())
                .append("name", "Jarron")
                .append("quantity", 0)
                .append("price", 0.0)
                .append("attribute", "Plastico"));

        stock.add(new Document("type", ProductType.DECORATION.toString())
                .append("name", "Tiesto")
                .append("quantity", 0)
                .append("price", 0.0)
                .append("attribute", "Madera"));


        collection.insertMany(stock);

    }
    public void addProduct(Product product) {
        Document productDocument = new Document("name", product.getName())
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

    @Override
    public void newTicket() {

    }

    public Document findProduct(Document query) {
        return collection.find(query).first();
    }


    public Product documentToProduct(Document document) {
        String name = document.getString("name");
        int quantity = document.getInteger("quantity");
        double price = document.getDouble("price");
        ProductType type = ProductType.valueOf(document.getString("type").toUpperCase());

        Object attribute = document.get("attribute");



        Product product;

        if (type == ProductType.FLOWER) {
            product = new Flower<>(name, quantity, price, attribute);
        } else if (type == ProductType.DECORATION) {
            product = new Decoration<>(name, quantity, price, attribute);
        } else if (type == ProductType.TREE) {
<<<<<<< HEAD:src/main/java/FlowerStore/Products/Infrastructure/MongoDB/ProductRepositoryMongoDB.java
            if (attribute instanceof Integer) {
                product = new Tree<>(name, quantity, price, ((Integer) attribute).doubleValue());
            } else if (attribute instanceof Double) {
                product = new Tree<>(name, quantity, price, (Double) attribute);
        } else {
            throw new IllegalArgumentException("Product type invalid");
=======
            product = new Tree<>(name, quantity, price, (Double) attribute);
        } else {
            throw new IllegalArgumentException("Tipo de atributo no válido para un producto tipo TREE");
>>>>>>> conexionMongo:src/main/java/Infrastructure/MongoDB/ProductRepositoryMongoDB.java
        }
        return product;
}

}
