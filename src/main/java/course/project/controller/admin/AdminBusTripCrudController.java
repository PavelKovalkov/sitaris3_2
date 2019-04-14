package course.project.controller.admin;

import course.project.entity.BusTrip;
import course.project.service.BusTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminBusTripCrudController {
    private final BusTripService busTripService;

    @Autowired
    public AdminBusTripCrudController(BusTripService busTripService) {
        this.busTripService = busTripService;
    }

    @GetMapping("/bus-trip/get-info/{busTripId}")
    public ResponseEntity getBusTripInfo(@PathVariable String busTripId) {
        return new ResponseEntity<>(busTripService.getBusTripInfo(busTripId), HttpStatus.OK);
    }

    @PostMapping("/bus-trip/save")
    public ResponseEntity saveBusTrip(@RequestBody BusTrip busTrip) {
        busTripService.saveBusTrip(busTrip);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/bus-trip/update")
    public ResponseEntity updateBusTrip(@RequestBody BusTrip busTrip) {
        busTripService.updateBusTrip(busTrip);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @DeleteMapping("/bus-trip/delete")
    public ResponseEntity deleteBusTrip(@RequestParam(name = "id") String id) {
        busTripService.deleteBusTrip(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
