package course.project.controller.admin;

import course.project.entity.Bus;
import course.project.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminBusCrudController {
    private final BusService busService;

    @Autowired
    public AdminBusCrudController(BusService busService) {
        this.busService = busService;
    }

    @GetMapping("/bus/get-info/{busId}")
    public ResponseEntity getBusInfo(@PathVariable String busId) {
        return new ResponseEntity<>(busService.getBusInfo(busId), HttpStatus.OK);
    }

    @PostMapping("/bus/save")
    public ResponseEntity saveBus(@RequestBody Bus bus) {
        busService.saveBus(bus);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/bus/update")
    public ResponseEntity updateBus(@RequestBody Bus bus) {
        busService.updateBus(bus);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/bus/delete")
    public ResponseEntity deleteBus(@RequestParam(name = "id") String id) {
        busService.deleteBus(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
