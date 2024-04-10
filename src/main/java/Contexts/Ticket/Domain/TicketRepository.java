package Contexts.Ticket.Domain;

import Contexts.Ticket.Infrastructure.Exceptions.NoTicketsFoundException;

import java.util.List;

public interface TicketRepository {
    void newTicket(Ticket ticket);

    List<Ticket> getAllTickets() throws NoTicketsFoundException;

    Ticket getLastTicket();

    int nextTicketID();

    void getAllSales(List<Ticket> tickets);
}
