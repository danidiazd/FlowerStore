package Contexts.Ticket.Infrastructure.SQL;

import Contexts.Product.Domain.Product;
import Contexts.Ticket.Domain.Ticket;
import Contexts.Ticket.Domain.TicketRepository;
import Infrastructure.Connections.MySQLConnection;

import java.util.List;
import java.util.Map;

public class TicketRepositorySQL implements TicketRepository {

    private MySQLConnection mySQLConnection;

    public TicketRepositorySQL(MySQLConnection mySQLConnection) {
        this.mySQLConnection = mySQLConnection;
    }
    @Override
    public void newTicket(Map<Product, Integer> ticket) {

    }

    @Override
    public List<Ticket> getAllTickets() {
        return null;
    }

    @Override
    public void getAllSales() {

    }
}
