package course.project.service.impl;

import course.project.entity.BusTrip;
import course.project.entity.Ticket;
import course.project.entity.User;
import course.project.exception.NoAvailableTicketsException;
import course.project.exception.UserDoesNotExistException;
import course.project.repo.TicketRepo;
import course.project.repo.UserRepo;
import course.project.service.BusTripService;
import course.project.service.TicketService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Collection;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

@Service
public class DefaultTicketService implements TicketService {
    private final UserRepo userRepo;
    private final TicketRepo ticketRepo;
    private final BusTripService busTripService;
    private final Lock readLock;
    private final Lock writeLock;

    @Autowired
    public DefaultTicketService(UserRepo userRepo, TicketRepo ticketRepo, BusTripService busTripService) {
        this.userRepo = userRepo;
        this.ticketRepo = ticketRepo;
        this.busTripService = busTripService;
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        readLock = reentrantReadWriteLock.readLock();
        writeLock = reentrantReadWriteLock.writeLock();
    }

    @Override
    public Collection<Ticket> getNotUsedTickets(String userEmail) {
        try {
            readLock.lock();
            User user = userRepo.findByEmail(userEmail).orElseThrow(UserDoesNotExistException::new);

            return user
                .getTickets()
                .stream()
                .filter(ticket -> ticket.getBusTrip().getDepartureDate().after(new Date(System.currentTimeMillis())))
                .collect(Collectors.toList());

        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void addNewTicket(String jsonTicketInfo) {
        JSONObject object = new JSONObject(jsonTicketInfo);

        BusTrip busTrip = busTripService.getBusTripInfo(object.getString("id"));
        if (busTrip.getAvailableTicketCount() == 0) throw new NoAvailableTicketsException();
        User user = userRepo.findByEmail(object.getString("email")).orElseThrow(UserDoesNotExistException::new);

        Ticket ticket = new Ticket();
        ticket.setBusTrip(busTrip);
        ticket.setUser(user);
        ticket.setUserName(object.getString("name"));
        ticket.setUserSurname(object.getString("surname"));
        ticket.setUserPatronymic(object.getString("patronymic"));
        ticket.setPassportId(object.getString("passport"));

        busTrip.setAvailableTicketCount(busTrip.getAvailableTicketCount() - 1);
        busTripService.updateBusTrip(busTrip);

        writeLock.lock();
        ticketRepo.save(ticket);
        writeLock.unlock();
    }
}
