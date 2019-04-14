package course.project.service;

import course.project.entity.Ticket;

import java.util.Collection;

public interface TicketService {

    Collection<Ticket> getNotUsedTickets(String userEmail);

    void addNewTicket(String jsonTicketInfo);
}
