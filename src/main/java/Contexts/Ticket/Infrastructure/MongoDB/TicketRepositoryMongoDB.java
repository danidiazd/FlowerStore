package Contexts.Ticket.Infrastructure.MongoDB;

import Contexts.Product.Domain.*;
import Contexts.Ticket.Domain.Ticket;
import Contexts.Ticket.Domain.TicketRepository;
import Contexts.Ticket.Infrastructure.Exceptions.NoTicketsFoundException;
import FlowerStore.FlowerStore;
import Infrastructure.Connections.MongoDBConnection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.*;

public class TicketRepositoryMongoDB implements TicketRepository {
    private MongoCollection<Document> ticketCollection;
    private FlowerStore flowerStore;

    public TicketRepositoryMongoDB(MongoDBConnection mongoDBConnection, FlowerStore flowerStore) {
        this.ticketCollection = mongoDBConnection.mongoDatabase.getCollection("tickets");
        this.flowerStore = flowerStore;
    }

    @Override
    public Ticket getLastTicket() {
        Document document = ticketCollection.find().sort(new Document("ticketID", -1)).first();

        return documentToTicket(document);
    }

    public int nextTicketID() {
        Document document = ticketCollection.find().sort(new Document("ticketID", -1)).first();
        Ticket lastTicket = getLastTicket();
        if (document == null) {
            return 1;
        } else {
            return document.getInteger("ticketID") + 1;
        }
    }

    private Ticket documentToTicket(Document document) {
        if (document == null) {

            return new Ticket(0, new Date(), new HashMap<>(), 0.0);
        }
        Integer ticketID = document.getInteger("ticketID");
        if (ticketID == null) {
            ticketID = 1;
        }
        Date date = document.getDate("date");
        double totalPrice = document.getDouble("totalPrice");

        Map<Product, Integer> products = new HashMap<>();
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

            products.put(product, quantity);
        }
        return new Ticket(ticketID, date, products, totalPrice);
    }

    @Override
    public void newTicket(Ticket ticket) {

        List<Document> productList = new ArrayList<>();
        Map<Product, Integer> ticketInfo = ticket.getProducts();

        for (Map.Entry<Product, Integer> entry : ticketInfo.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            productList.add(new Document("Name", product.getName())
                    .append("Type", product.getType().toString())
                    .append("Features", product.getAttributes().toString())
                    .append("Quantity", quantity)
                    .append("Price", product.getPrice()));
        }

        Document newTicket = new Document("ticketID", nextTicketID())
                .append("date", ticket.getDate())
                .append("products", productList)
                .append("totalPrice", ticket.getTotal());

        ticketCollection.insertOne(newTicket);

    }

    @Override
    public List<Ticket> getAllTickets() throws NoTicketsFoundException {
        List<Ticket> tickets = new ArrayList<>();
        FindIterable<Document> cursor = ticketCollection.find();
        boolean flagTickets = false;

        for (Document document : cursor) {
            Ticket ticket = documentToTicket(document);
            tickets.add(ticket);
            flagTickets = true;
        }

        if (!flagTickets) {
            throw new NoTicketsFoundException("No tickets found in the database.");
        }
        return tickets;
    }

    @Override
    public void getAllSales(List<Ticket> tickets) {

        double totalSales = 0;
        for (Ticket ticket : tickets) {
            totalSales += ticket.getTotal();
        }

        System.out.println("The total sales of the FlowerShop "
                + FlowerStore.getNameStore() + " have a value of: " + totalSales + "€.");
    }
}
