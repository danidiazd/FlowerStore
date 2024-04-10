package Contexts.Ticket.Infrastructure.SQL;

import Contexts.Product.Domain.Product;
import Contexts.Product.Domain.ProductType;
import Contexts.Ticket.Domain.Ticket;
import Contexts.Ticket.Domain.TicketRepository;
import FlowerStore.FlowerStore;
import Infrastructure.Connections.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Infrastructure.Connections.MySQLConnection.getMySQLDatabase;

public class TicketRepositorySQL implements TicketRepository {

    private MySQLConnection mySQLConnection;
    ;

    public TicketRepositorySQL(MySQLConnection mySQLConnection) {
        this.mySQLConnection = mySQLConnection;
    }

    @Override
    public void newTicket(Ticket newTicket) {
        try {
            Connection conn = getMySQLDatabase();
            PreparedStatement stmt = conn.prepareStatement(QueriesSQL.SQL_INSERT_TICKET, Statement.RETURN_GENERATED_KEYS);
            stmt.setDate(1, java.sql.Date.valueOf(java.time.LocalDate.now()));
            stmt.executeUpdate();
            ResultSet generateKey = stmt.getGeneratedKeys();

            if (generateKey.next()) {
                int ticketId = generateKey.getInt(1);
                newTicket.setTicketID(ticketId);

                Map<Product, Integer> products = newTicket.getProducts();
                for (Map.Entry<Product, Integer> entry : products.entrySet()) {
                    Product product = entry.getKey();
                    Integer quantity = entry.getValue();

                    PreparedStatement stmtProductTicket = conn.prepareStatement(QueriesSQL.SQL_INSERT_PRODUCT_TICKET);
                    stmtProductTicket.setInt(1, ticketId);
                    stmtProductTicket.setInt(2, product.getProductId());
                    stmtProductTicket.setInt(3, quantity);
                    stmtProductTicket.executeUpdate();
                }
            } else {
                throw new SQLException("The creation of the ticket failed, failed to obtain the generated ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Ticket> getAllTickets() {
        List<Ticket> tickets = new ArrayList<>();

        try {
            Connection conn = getMySQLDatabase();
            PreparedStatement stmt = conn.prepareStatement(QueriesSQL.SQL_SELECT_TICKET);
            ResultSet rs = stmt.executeQuery();
            Map<Integer, Ticket> ticketMap = new HashMap<>();

            while (rs.next()) {
                int ticketId = rs.getInt("idticket");
                Date date = rs.getDate("date");

                Ticket ticket = ticketMap.getOrDefault(ticketId, new Ticket(date));
                ticket.setTicketID(ticketId);
                if (!ticketMap.containsKey(ticketId)) {
                    ticketMap.put(ticketId, ticket);
                }
                Product product = new Product(
                        rs.getInt("idproduct"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        ProductType.valueOf(rs.getString("type")),
                        rs.getString("attribute"));
                ticket.addProductToTicket(product, rs.getInt("amount"));
                ticket.setTotal(ticket.getTotal() + (product.getPrice() * rs.getInt("amount")));
            }
            tickets.addAll(ticketMap.values());
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return tickets;
    }

    @Override
    public Ticket getLastTicket() {
        Ticket lastTicket = null;
        try {
            Connection conn = getMySQLDatabase();
            PreparedStatement stmt = conn.prepareStatement(QueriesSQL.SQL_SELECT_PRODUCT_TICKET);
            ResultSet rs = stmt.executeQuery();

            Map<Product, Integer> ticketProductsMap = new HashMap<>();
            int lastTicketId = -1;
            Date lastTicketDate = null;

            while (rs.next()) {
                int ticketId = rs.getInt("ticket_idticket");
                if (lastTicketId != -1 && lastTicketId != ticketId) {
                    lastTicketDate = null;
                    PreparedStatement lastTicketStmt = conn.prepareStatement(QueriesSQL.SQL_SELECT_LAST_TICKET);
                    ResultSet lastTicketRs = lastTicketStmt.executeQuery();
                    if (lastTicketRs.next()) {
                        lastTicketDate = lastTicketRs.getDate("date");
                    }
                    lastTicket = new Ticket(lastTicketId, lastTicketDate, new HashMap<>(ticketProductsMap), 0);
                    ticketProductsMap.clear();
                }
                lastTicketId = ticketId;

                Product product = new Product(
                        rs.getInt("idproduct"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        ProductType.valueOf(rs.getString("type")),
                        rs.getString("attribute"));
                int quantity = rs.getInt("amount");
                ticketProductsMap.put(product, quantity);
            }

            if (lastTicketId != -1) {
                lastTicketDate = null;
                PreparedStatement lastTicketStmt = conn.prepareStatement(QueriesSQL.SQL_SELECT_LAST_TICKET);
                ResultSet lastTicketRs = lastTicketStmt.executeQuery();
                if (lastTicketRs.next()) {
                    lastTicketDate = lastTicketRs.getDate("date");
                }
                lastTicket = new Ticket(lastTicketId, lastTicketDate, new HashMap<>(ticketProductsMap), 0);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return lastTicket;
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
                + FlowerStore.getNameStore() + " is the: " + totalSales + "€.");
    }

}

