package Contexts.Ticket.Infrastructure.SQL;

import Contexts.Product.Domain.Product;
import Contexts.Product.Domain.ProductType;
import Contexts.Ticket.Domain.Ticket;
import Contexts.Ticket.Domain.TicketRepository;
import Contexts.Ticket.Infrastructure.Exceptions.NoTicketsFoundException;
import FlowerStore.FlowerStore;
import Infrastructure.Connections.MySQLConnection;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import static Infrastructure.Connections.MySQLConnection.getMySQLDatabase;

public class TicketRepositorySQL implements TicketRepository {

    private MySQLConnection mySQLConnection;

    public TicketRepositorySQL(MySQLConnection mySQLConnection) {
        this.mySQLConnection = mySQLConnection;
    }

    @Override
    public void newTicket(Ticket newTicket) {
        try {
            Connection connection = getMySQLDatabase();
            PreparedStatement statement = connection.prepareStatement(QueriesSQL.SQL_INSERT_TICKET, Statement.RETURN_GENERATED_KEYS);
            java.util.Date utilDate = newTicket.getDate();

            statement.setTimestamp(1, new java.sql.Timestamp(utilDate.getTime()));
            statement.setDouble(2, newTicket.getTotal());
            statement.executeUpdate();
            ResultSet generateKey = statement.getGeneratedKeys();

            if (generateKey.next()) {
                int ticketId = generateKey.getInt(1);
                newTicket.setTicketID(ticketId);

                Map<Product, Integer> products = newTicket.getProducts();
                for (Map.Entry<Product, Integer> entry : products.entrySet()) {
                    Product product = entry.getKey();
                    Integer quantity = entry.getValue();

                    PreparedStatement statementProductTicket = connection.prepareStatement(QueriesSQL.SQL_INSERT_PRODUCT_TICKET);
                    statementProductTicket.setInt(1, ticketId);
                    statementProductTicket.setInt(2, product.getProductId());
                    statementProductTicket.setInt(3, quantity);
                    statementProductTicket.executeUpdate();
                }
            } else {
                throw new SQLException("The creation of the ticket failed, failed to obtain the generated ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Ticket> getAllTickets() throws NoTicketsFoundException {
        List<Ticket> tickets = new ArrayList<>();
        try {
            Connection connection = getMySQLDatabase();
            PreparedStatement statement = connection.prepareStatement(QueriesSQL.SQL_SELECT_DISTINCT_TICKETS);
            ResultSet resultSet = statement.executeQuery();
            boolean flagTickets = false;

            while (resultSet.next()) {
                Ticket ticket = resultSetToTicket(resultSet);
                tickets.add(ticket);
                flagTickets = true;
            }
            if (!flagTickets) {
                throw new NoTicketsFoundException("No tickets found in the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return tickets;
    }

    @Override
    public Ticket getLastTicket() {
        Ticket lastTicket = null;
        try {
            Connection connection = getMySQLDatabase();
            PreparedStatement statement = connection.prepareStatement(QueriesSQL.SQL_SELECT_LAST_TICKET);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                lastTicket = resultSetToTicket(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return lastTicket;
    }

    private Ticket resultSetToTicket(ResultSet resultSet) {
        Ticket ticket = null;
        try {
            ticket = createTicketFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return ticket;
    }

    private Ticket createTicketFromResultSet(ResultSet resultSet) throws SQLException {
        int ticketID = resultSet.getInt("idticket");
        Timestamp timestamp = resultSet.getTimestamp("date");
        Date date = new Date(timestamp.getTime());
        double total = resultSet.getDouble("totalPrice");
        Map<Product, Integer> products = getProductsFromResultSet(ticketID);
        return new Ticket(ticketID, date, products, total);
    }

    private Map<Product, Integer> getProductsFromResultSet(int ticketID) throws SQLException {
        Map<Product, Integer> products = new HashMap<>();
        try (Connection connection = getMySQLDatabase();
             PreparedStatement productStatement = connection.prepareStatement(QueriesSQL.SQL_SELECT_PRODUCT_TICKET)) {
            productStatement.setInt(1, ticketID);
            try (ResultSet productRs = productStatement.executeQuery()) {
                while (productRs.next()) {
                    Product product = createProductFromResultSet(productRs);
                    int quantity = productRs.getInt("amount");
                    products.put(product, quantity);
                }
            }
        }
        return products;
    }

    private Product createProductFromResultSet(ResultSet productRs) throws SQLException {
        return new Product(
                productRs.getInt("idproduct"),
                productRs.getString("name"),
                productRs.getInt("quantity"),
                productRs.getDouble("price"),
                ProductType.valueOf(productRs.getString("type")),
                productRs.getString("attribute"));
    }


    @Override
    public int nextTicketID() {
        return 0;
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

