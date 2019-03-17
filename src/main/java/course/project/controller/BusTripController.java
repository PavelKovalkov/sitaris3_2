package course.project.controller;

import course.project.entity.BusTrip;
import course.project.repo.BusTripRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.Collection;

@RestController
@RequestMapping(value = "/bus-trip")
public class BusTripController {

    private final BusTripRepo repo;

    @Autowired
    public BusTripController(BusTripRepo repo) {
        this.repo = repo;
    }

    @GetMapping("/get")
    public ResponseEntity getBusTripsByDepartureDateAndDepartureStationAndArrivalStation(@RequestParam(name = "date") String date,
                                                                                         @RequestParam(name = "departure_station") String departureStation,
                                                                                         @RequestParam(name = "arrival_station") String arrivalStation) {
        Collection<BusTrip> result = repo.findAllByDepartureDateAndDepartureStationAndArrivalStation(Date.valueOf(date), departureStation, arrivalStation);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
