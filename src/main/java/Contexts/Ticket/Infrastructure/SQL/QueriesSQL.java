package Contexts.Ticket.Infrastructure.SQL;

public class QueriesSQL {

    public static final String SQL_SELECT_TICKET = "SELECT t.idticket, t.date, p.idproduct, p.name, p.quantity, p.price, p.type, pt.amount " +
            "FROM ticket t " +
            "INNER JOIN product_ticket pt ON t.idticket = pt.ticket_idticket " +
            "INNER JOIN product p ON pt.product_idproduct = p.idproduct " +
            "LEFT JOIN flower ON p.idproduct = flower.product_idproduct " +
            "LEFT JOIN decoration ON p.idproduct = decoration.product_idproduct " +
            "LEFT JOIN tree ON p.idproduct = tree.product_idproduct ";

    public static final String SQL_INSERT_TICKET = "INSERT INTO ticket(date) VALUES(?)";

    public static final String SQL_INSERT_PRODUCT_TICKET = "INSERT INTO product_ticket(ticket_idticket, product_idproduct, amount) VALUES (?, ?, ?)";

}
