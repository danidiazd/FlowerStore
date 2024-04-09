package Contexts.Ticket.Domain;

import java.util.List;

public interface TicketRepository {
    void newTicket(Ticket ticket);
    List<Ticket> getAllTickets();
    Ticket getLastTicket();
    int nextTicketID();
    void getAllSales(List<Ticket> tickets);
}
