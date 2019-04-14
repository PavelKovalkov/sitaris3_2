package course.project.controller.user;

import course.project.entity.BusTrip;
import course.project.service.BusTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collection;

@Controller
@RequestMapping(value = "/user/bus-trip")
public class UserBusTripController {
    private final BusTripService busTripService;

    @Autowired
    public UserBusTripController(BusTripService busTripService) {
        this.busTripService = busTripService;
    }

    @GetMapping("/get")
    public @ResponseBody
    ResponseEntity getBusTripsByDepartureDateAndDepartureStationAndArrivalStation(@RequestParam(name = "date") String date,
                                                                                  @RequestParam(name = "departure_station") String departureStation,
                                                                                  @RequestParam(name = "arrival_station") String arrivalStation) {
        Collection<BusTrip> result = busTripService.getBusTripsByDepartureDateAndDepartureStationAndArrivalStation(date, departureStation, arrivalStation);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/get-info/{busTripId}")
    public String getBusTripInfo(@PathVariable String busTripId,
                                 Model model,
                                 HttpSession session) {
        BusTrip busTrip = busTripService.getBusTripInfo(busTripId);

        model.addAttribute("user", session.getAttribute("userInfo"));
        model.addAttribute("info", busTrip);

        return "user/tripinfo";
    }
}
