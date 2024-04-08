package Contexts.Ticket.Infrastructure.MongoDB;

import Contexts.Product.Domain.*;
import Contexts.Ticket.Domain.Ticket;
import Contexts.Ticket.Domain.TicketRepository;
import Infrastructure.Connections.MongoDBConnection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TicketRepositoryMongoDB implements TicketRepository {
    private MongoCollection<Document> ticketCollection;


    public TicketRepositoryMongoDB(MongoDBConnection mongoDBConnection) {
        this.ticketCollection = mongoDBConnection.mongoDatabase.getCollection("tickets");
    }

    private Ticket documentToTicket(Document document) {
        Ticket ticket = new Ticket(document.getDate("date"));
        List<Document> productsInfo = (List<Document>) document.get("products");
        for (Document productInfo : productsInfo) {
            String type = productInfo.getString("Type");
            String features = productInfo.getString("Features");
            int quantity = productInfo.getInteger("Quantity");
            double price = productInfo.getDouble("Price");

            Product product;
            if (type.equals(ProductType.FLOWER.toString())) {
                product = new Flower<>(features, 0, price, features);
            } else if (type.equals(ProductType.DECORATION.toString())) {
                product = new Decoration<>(features, 0, price, features);
            } else if (type.equals(ProductType.TREE.toString())) {
                product = new Tree<>(features, 0, price, Double.parseDouble(features));
            } else {
                throw new IllegalArgumentException("Tipo de producto no válido: " + type);
            }

            ticket.addProductToTicket(product, quantity);
        }
        return ticket;
    }

    @Override
    public void newTicket(Map<Product, Integer> ticketInfo) {
        List<Document> newTicketInfo = new ArrayList<>();

        double totalPrice = 0.0;
        for (Map.Entry<Product, Integer> entry : ticketInfo.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            newTicketInfo.add(new Document("Type", product.getType().toString())
                    .append("Features", product.getAttributes().toString())
                    .append("Quantity", quantity)
                    .append("Price", product.getPrice()));
            totalPrice += product.getPrice() * quantity;
        }

        Document newTicket = new Document("date", new Date())
                .append("products", newTicketInfo)
                .append("totalPrice", totalPrice);

        ticketCollection.insertOne(newTicket);
    }

    @Override
    public List<Ticket> getAllTickets() {
        List<Ticket> tickets = new ArrayList<>();
        FindIterable<Document> cursor = ticketCollection.find();
        for (Document document : cursor) {
            Ticket ticket = documentToTicket(document);
            tickets.add(ticket);
        }
        return tickets;
    }
}
