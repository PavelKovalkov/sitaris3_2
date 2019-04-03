package course.project.controller;

import course.project.entity.Bus;
import course.project.entity.BusTrip;
import course.project.entity.Ticket;
import course.project.entity.User;
import course.project.repo.BusRepo;
import course.project.repo.BusTripRepo;
import course.project.repo.TicketRepo;
import course.project.repo.UserRepo;
import course.project.resource.UserPublicInfo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/user/bus-trip")
public class BusTripController {
    private final UserRepo userRepo;
    private final TicketRepo ticketRepo;
    private final BusTripRepo busTripRepo;
    private final BusRepo busRepo;
    private Lock readLock;
    private Lock writeLock;

    @Autowired
    public BusTripController(UserRepo userRepo, TicketRepo ticketRepo, BusTripRepo repo, BusRepo busRepo) {
        this.userRepo = userRepo;
        this.ticketRepo = ticketRepo;
        this.busTripRepo = repo;
        this.busRepo = busRepo;
        ReadWriteLock locker = new ReentrantReadWriteLock();
        readLock = locker.readLock();
        writeLock = locker.writeLock();
    }

    @GetMapping("/get")
    public @ResponseBody
    ResponseEntity getBusTripsByDepartureDateAndDepartureStationAndArrivalStation(@RequestParam(name = "date") String date,
                                                                                  @RequestParam(name = "departure_station") String departureStation,
                                                                                  @RequestParam(name = "arrival_station") String arrivalStation) {
        readLock.lock();
        Collection<BusTrip> result = busTripRepo.findAllByDepartureDateAndDepartureStationAndArrivalStation(Date.valueOf(date), departureStation, arrivalStation);
        result = result
            .stream()
            .filter(trip -> trip.getAvailableTicketCount() < trip.getTotalTicketCount())
            .collect(Collectors.toList());
        readLock.unlock();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/get-info/{busTripId}")
    public String getBusTripInfo(@PathVariable String busTripId,
                                 Model model,
                                 HttpSession session) throws Exception {
        readLock.lock();
        Optional<BusTrip> busTrip = busTripRepo.findById(busTripId);
        readLock.unlock();

        if (!busTrip.isPresent()) {
            throw new Exception();
        }

        model.addAttribute("user", session.getAttribute("userInfo"));
        model.addAttribute("info", busTrip.get());

        return "user/tripinfo";
    }

    @GetMapping("/bus-info/{busId}")
    public String getBusInfo(@PathVariable String busId,
                             Model model) throws Exception {
        Optional<Bus> searchResult = busRepo.findById(busId);
        Bus bus = searchResult.orElseThrow(Exception::new);
        model.addAttribute("bus", bus);

        return "user/businfo";
    }

    @PostMapping("/buy")
    public @ResponseBody
    ResponseEntity purchaseTicket(@RequestBody String body) throws Exception {
        JSONObject object = new JSONObject(body);
        BusTrip busTrip;
        try {
            writeLock.lock();
            busTrip = busTripRepo.findById(object.getString("id")).orElseThrow(Exception::new);
            if (busTrip.getAvailableTicketCount() == 0) throw new Exception();
            busTrip.setAvailableTicketCount(busTrip.getAvailableTicketCount() - 1);
            busTripRepo.save(busTrip);
        } finally {
            writeLock.unlock();
        }
        User user = userRepo.findByEmail(object.getString("email")).orElseThrow(Exception::new);
        Ticket ticket = new Ticket();
        ticket.setBusTrip(busTrip);
        ticket.setUser(user);
        ticket.setUserName(object.getString("name"));
        ticket.setUserSurname(object.getString("surname"));
        ticket.setUserPatronymic(object.getString("patronymic"));
        ticket.setPassportId(object.getString("passport"));
        ticketRepo.save(ticket);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/get-not-used-tickets")
    public @ResponseBody
    ResponseEntity getNotUsedTickets(HttpSession session) {
        UserPublicInfo publicInfo = (UserPublicInfo) session.getAttribute("userInfo");
        User user = userRepo.findByEmail(publicInfo.getEmail()).orElseThrow(RuntimeException::new);

        List<Ticket> result = user
            .getTickets()
            .stream()
            .filter(ticket -> ticket.getBusTrip().getDepartureDate().after(new Date(System.currentTimeMillis())))
            .collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
