package Contexts.Ticket.Infrastructure.MongoDB;

import Contexts.Product.Domain.*;
import Contexts.Ticket.Domain.Ticket;
import Contexts.Ticket.Domain.TicketRepository;
import FlowerStore.FlowerStore;
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
    private FlowerStore flowerStore;

    public TicketRepositoryMongoDB(MongoDBConnection mongoDBConnection, FlowerStore flowerStore) {
        this.ticketCollection = mongoDBConnection.mongoDatabase.getCollection("tickets");
        this.flowerStore = flowerStore;
    }

    private Ticket documentToTicket(Document document) {

        int id = document.getInteger("id");
        Date date = document.getDate("date");
        Ticket ticket = new Ticket(id, date);

        List<Document> productsInfo = (List<Document>) document.get("products");
        for (Document productInfo : productsInfo) {
            String name = productInfo.getString("Name");
            String type = productInfo.getString("Type");
            String features = productInfo.getString("Features");
            int quantity = productInfo.getInteger("Quantity");
            double price = productInfo.getDouble("Price");

            Product product;
            if (type.equals(ProductType.FLOWER.toString())) {
                product = new Flower<>(name, quantity, price, features);
            } else if (type.equals(ProductType.DECORATION.toString())) {
                product = new Decoration<>(name, quantity, price, features);
            } else if (type.equals(ProductType.TREE.toString())) {
                product = new Tree<>(name, quantity, price, Double.parseDouble(features));
            } else {
                throw new IllegalArgumentException("Invalid product type : " + type);
            }

            ticket.addProductToTicket(product, quantity);
        }
        return ticket;
    }
    public Ticket getLastTicketId() {
        Document document = ticketCollection.find().sort(new Document("id", -1)).first();

        if (document == null) return null;
        Ticket ticket = documentToTicket(document);
        return ticket;
    }
    private int nextTicketID() {
        Ticket lastTicket = getLastTicketId();
        if (lastTicket == null) return 1;
        return lastTicket.getId();
    }

    @Override
    public void newTicket(Map<Product, Integer> ticketInfo) {
        List<Document> newTicketInfo = new ArrayList<>();


        double totalPrice = 0.0;

        Date date = new Date();

        for (Map.Entry<Product, Integer> entry : ticketInfo.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            newTicketInfo.add(new Document("id", nextTicketID())
                    .append("Date", date)
                    .append("Name", product.getName())
                    .append("Type", product.getType().toString())
                    .append("Features", product.getAttributes().toString())
                    .append("Quantity", quantity)
                    .append("Price", product.getPrice()));
            totalPrice += product.getPrice() * quantity;
        }

        Document newTicket = new Document("id", nextTicketID())
                .append("date", date)
                .append("products", newTicketInfo)
                .append("totalPrice", totalPrice);

        ticketCollection.insertOne(newTicket);

    }

    @Override
    public List<Ticket> getAllTickets() {
        List<Ticket> tickets = new ArrayList<>();
        FindIterable<Document> cursor = ticketCollection.find();
        double totalSales = 0;

        for (Document document : cursor) {
            Ticket ticket = documentToTicket(document);
            tickets.add(ticket);
            double totalTicket = document.getDouble("totalPrice");
            totalSales += totalTicket;
        }
        return tickets;
    }

    @Override
    public void getAllSales() {
        List<Ticket> tickets = new ArrayList<>();
        FindIterable<Document> cursor = ticketCollection.find();
        double totalSales = 0;

        for (Document document : cursor) {
            Ticket ticket = documentToTicket(document);
            tickets.add(ticket);
            double totalTicket = document.getDouble("totalPrice");
            totalSales += totalTicket;
        }

        System.out.println("The total sales of the FlowerShop "
                + FlowerStore.getNameStore() + " is the: " + totalSales + "€.");

    }
}
