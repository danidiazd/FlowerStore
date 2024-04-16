package Contexts.Ticket.Infrastructure.SQL;

public class QueriesSQL {

    public static final String SQL_SELECT_DISTINCT_TICKETS = "SELECT DISTINCT t.idticket, t.date, t.totalPrice " +
            "FROM ticket t " +
            "INNER JOIN product_ticket pt ON t.idticket = pt.ticket_idticket " +
            "INNER JOIN product p ON pt.product_idproduct = p.idproduct;";
    public static final String SQL_INSERT_TICKET = "INSERT INTO ticket(date, totalPrice) VALUES(?, ?)";

    public static final String SQL_INSERT_PRODUCT_TICKET = "INSERT INTO product_ticket(ticket_idticket, product_idproduct, amount) VALUES (?, ?, ?)";

    public static final String SQL_SELECT_LAST_TICKET = "SELECT t.idticket, t.date, t.totalPrice, p.idproduct, p.name, p.quantity, p.price, p.type, " +
            "COALESCE(f.color, tr.height, d.material) AS attribute, pt.amount " +
            "FROM ticket t " +
            "INNER JOIN product_ticket pt ON t.idticket = pt.ticket_idticket " +
            "INNER JOIN product p ON pt.product_idproduct = p.idproduct " +
            "LEFT JOIN flower f ON p.idproduct = f.product_idproduct " +
            "LEFT JOIN decoration d ON p.idproduct = d.product_idproduct " +
            "LEFT JOIN tree tr ON p.idproduct = tr.product_idproduct " +
            "ORDER BY idticket DESC LIMIT 1";

    public static final String SQL_SELECT_PRODUCT_TICKET = "SELECT pt.idproduct_ticket, pt.amount, p.idproduct, p.name, p.quantity, p.price, p.type, pt.ticket_idticket, " +
            "COALESCE(f.color, tr.height, d.material) AS attribute " +
            "FROM product_ticket pt " +
            "INNER JOIN ticket t ON pt.ticket_idticket = t.idticket " +
            "INNER JOIN product p ON pt.product_idproduct = p.idproduct " +
            "LEFT JOIN flower f ON p.idproduct = f.product_idproduct " +
            "LEFT JOIN decoration d ON p.idproduct = d.product_idproduct " +
            "LEFT JOIN tree tr ON p.idproduct = tr.product_idproduct " +
            "WHERE t.idticket = ?";
}
