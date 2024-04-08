package Contexts.Ticket.Domain;

import Contexts.Product.Domain.Product;

import java.util.List;
import java.util.Map;

public interface TicketRepository {
    void newTicket(Map<Product, Integer> ticket);
    List<Ticket> getAllTickets();
}
