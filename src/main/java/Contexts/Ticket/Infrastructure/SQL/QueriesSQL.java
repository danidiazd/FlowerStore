package Contexts.Ticket.Infrastructure.SQL;

public class QueriesSQL {

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

}
