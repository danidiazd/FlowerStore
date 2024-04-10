package Contexts.Ticket.Infrastructure.Exceptions;

public class NoTicketsFoundException extends Exception {
    public NoTicketsFoundException() {
        super("No tickets found in the database.");
    }

}
