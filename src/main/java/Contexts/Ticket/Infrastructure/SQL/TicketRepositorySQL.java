package Contexts.Ticket.Infrastructure.SQL;

import Contexts.Product.Domain.Product;
import Contexts.Product.Domain.ProductType;
import Contexts.Product.Infrastructure.SQL.QueriesSQL;
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

    private MySQLConnection mySQLConnection;

    private static final String SQL_CREATE =
            "CREATE TABLE IF NOT EXISTS ticket (" +
                    "idticket INT PRIMARY KEY AUTO_INCREMENT, " +
                    "date DATE NOT NULL, " +
                    "totalPrice FLOAT NOT NULL, " +
                    ")";

    private static final String SQL_SELECT = "SELECT * t.idticket, t.date, p.idproduct, p.name, p.quantity, p.price, p.type, p.attribute, pt.amount" +
            "FROM ticket t" +
            "INNER JOIN product_ticket pt ON t.idticket = pt.ticket_idticket" +
            "INNER JOIN product p ON pt.product_idproduct = p.idproduct";

    private static final String SQL_INSERT = "INSERT INTO ticket(date) VALUES(?)";

    private static final String SQL_INSERT_PRODUCT_TICKET = "INSERT INTO product_ticket(ticket_idticket, product_idproduct, amount) VALUES (?, ?, ?)";

    public TicketRepositorySQL(MySQLConnection mySQLConnection) {
        this.mySQLConnection = mySQLConnection;
    }

    @Override
    public void newTicket(Map<Product, Integer> ticket) {
        try(Connection conn = getMySQLDatabase();
            PreparedStatement stmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            conn.setAutoCommit(false);
            stmt.setDate(1, java.sql.Date.valueOf(java.time.LocalDate.now()));
            stmt.executeUpdate();
            ResultSet generateKey = stmt.getGeneratedKeys();

            if (generateKey.next()) {
                int ticketId = generateKey.getInt(1);
                PreparedStatement stmtProductTicket = conn.prepareStatement(SQL_INSERT_PRODUCT_TICKET);

                for (Map.Entry<Product, Integer> entry : ticket.entrySet()) {
                    Product product = entry.getKey();
                    Integer quantity = entry.getValue();

                    stmtProductTicket.setLong(1, ticketId);
                    stmtProductTicket.setInt(2, product.getProductId());
                    stmtProductTicket.setInt(3, quantity);
                    stmtProductTicket.executeUpdate();
                }
                conn.commit();
            } else {
                conn.rollback();
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
            PreparedStatement stmt = conn.prepareStatement(QueriesSQL.SQL_SELECT);
            ResultSet rs = stmt.executeQuery();
            Map<Integer, Ticket> ticketMap = new HashMap<>();

            while (rs.next()) {
                int ticketId = rs.getInt("ticketid");
                Date date = rs.getDate("date");
                double productPrice = rs.getDouble("price");
                double amount = rs.getInt("amount");

                double totalPrice = productPrice * amount; // A falta de saber como implementarlo

                Ticket ticket = ticketMap.getOrDefault(ticketId, new Ticket(date));
                ticket.setId(ticketId);
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
    public void getAllSales() {

    }
}

