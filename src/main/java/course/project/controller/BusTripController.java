package course.project.controller;

import course.project.entity.BusTrip;
import course.project.repo.BusTripRepo;
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
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/user/bus-trip")
public class BusTripController {

    private final BusTripRepo repo;
    private ReadWriteLock locker;
    private Lock readLock;
    private Lock writeLock;

    @Autowired
    public BusTripController(BusTripRepo repo) {
        this.repo = repo;
        locker = new ReentrantReadWriteLock();
        readLock = locker.readLock();
        writeLock = locker.writeLock();
    }

    @GetMapping("/get")
    public @ResponseBody
    ResponseEntity getBusTripsByDepartureDateAndDepartureStationAndArrivalStation(@RequestParam(name = "date") String date,
                                                                                  @RequestParam(name = "departure_station") String departureStation,
                                                                                  @RequestParam(name = "arrival_station") String arrivalStation) {
        readLock.lock();
        Collection<BusTrip> result = repo.findAllByDepartureDateAndDepartureStationAndArrivalStation(Date.valueOf(date), departureStation, arrivalStation);
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
        Optional<BusTrip> busTrip = repo.findById(busTripId);
        readLock.unlock();

        if (!busTrip.isPresent()) {
            throw new Exception();
        }

        model.addAttribute("user", session.getAttribute("userInfo"));
        model.addAttribute("info", busTrip.get());

        return "user/tripinfo";
    }

    @PostMapping("/buy")
    public @ResponseBody
    ResponseEntity purchaseTicket(@RequestBody String body) throws Exception {
        JSONObject object = new JSONObject(body);
        BusTrip busTrip;
        try {
            readLock.lock();
            busTrip = repo.findById(object.getString("id")).orElseThrow(Exception::new);
            if (busTrip.getAvailableTicketCount() == busTrip.getTotalTicketCount()) throw new Exception();
            busTrip.setAvailableTicketCount(busTrip.getAvailableTicketCount() + 1);
        } finally {
            readLock.unlock();
        }

        writeLock.lock();
        repo.save(busTrip);
        writeLock.unlock();

        return new ResponseEntity(HttpStatus.OK);
    }
}
