package Contexts.Ticket.Infrastructure.SQL;

import Contexts.Product.Domain.Product;
import Contexts.Ticket.Domain.Ticket;
import Contexts.Ticket.Domain.TicketRepository;

import java.util.List;
import java.util.Map;

public class TicketRepositorySQL implements TicketRepository {
    @Override
    public void newTicket(Map<Product, Integer> ticket) {

    }

    @Override
    public List<Ticket> getAllTickets() {
        return null;
    }
}
