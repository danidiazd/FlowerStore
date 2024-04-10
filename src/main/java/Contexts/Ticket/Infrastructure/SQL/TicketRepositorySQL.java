package Contexts.Ticket.Infrastructure.SQL;

import Contexts.Product.Domain.Product;
import Contexts.Product.Domain.ProductType;
import Contexts.Ticket.Domain.Ticket;
import Contexts.Ticket.Domain.TicketRepository;
import Infrastructure.Connections.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Infrastructure.Connections.MySQLConnection.getMySQLDatabase;

public class TicketRepositorySQL implements TicketRepository {

    private MySQLConnection mySQLConnection;;

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

                PreparedStatement stmtProductTicket = conn.prepareStatement(QueriesSQL.SQL_INSERT_PRODUCT_TICKET);
                Map<Product, Integer> ticket = newTicket.getProducts();
                for (Map.Entry<Product, Integer> entry : ticket.entrySet()) {
                    Product product = entry.getKey();
                    Integer quantity = entry.getValue();

                    stmtProductTicket.setInt(1, ticketId);
                    stmtProductTicket.setInt(2, product.getProductId());
                    stmtProductTicket.setInt(3, quantity);
                    stmtProductTicket.executeUpdate();
                }
                newTicket.showTicket();
            } else {
                throw new SQLException("The creation of the ticket failed, failed to obtain the generated ID.");
            }
        } catch(SQLException e) {
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
                double productPrice = rs.getDouble("price");
                double amount = rs.getInt("amount");

                double totalPrice = productPrice * amount; // A falta de saber como implementarlo

                Ticket ticket = ticketMap.getOrDefault(ticketId, new Ticket(date));
                ticket.setTicketID(ticketId);
                if(!ticketMap.containsKey(ticketId)) {
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
            }
            tickets.addAll(ticketMap.values());
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return tickets;
    }

    @Override
    public Ticket getLastTicket() {
        return null;
    }

    @Override
    public int nextTicketID() {
        return 0;
    }

    @Override
    public void getAllSales(List<Ticket> tickets) {

    }

}

