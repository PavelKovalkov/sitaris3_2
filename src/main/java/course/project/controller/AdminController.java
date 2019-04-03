package course.project.controller;

import course.project.entity.Bus;
import course.project.entity.BusTrip;
import course.project.repo.BusRepo;
import course.project.repo.BusTripRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final BusTripRepo busTripRepo;
    private final BusRepo busRepo;

    @Autowired
    public AdminController(BusTripRepo busTripRepo, BusRepo busRepo) {
        this.busTripRepo = busTripRepo;
        this.busRepo = busRepo;
    }

    @GetMapping("/bus/get-info/{busId}")
    public ResponseEntity getBusInfo(@PathVariable String busId) {
        Optional<Bus> searchResult = busRepo.findById(busId);
        Bus bus = searchResult.orElseThrow(RuntimeException::new);

        return new ResponseEntity<>(bus, HttpStatus.OK);
    }

    @GetMapping("/bus-trip/get-info/{busTripId}")
    public ResponseEntity getBusTripInfo(@PathVariable String busTripId) {
        Optional<BusTrip> searchResult = busTripRepo.findById(busTripId);
        BusTrip busTrip = searchResult.orElseThrow(RuntimeException::new);

        return new ResponseEntity<>(busTrip, HttpStatus.OK);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @PostMapping("/bus-trip/save")
    public ResponseEntity saveBusTrip(@RequestBody BusTrip busTrip) {
        if (busTripRepo.existsById(busTrip.getId())) {
            return new ResponseEntity<>("Уже существует", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (!busRepo.existsById(busTrip.getBus().getId())) {
            return new ResponseEntity<>("Автобуса с данным номером не существует", HttpStatus.NOT_FOUND);
        }
        busTripRepo.save(busTrip);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @PostMapping("/bus-trip/update")
    public ResponseEntity updateBusTrip(@RequestBody BusTrip busTrip) {
        if (!busTripRepo.existsById(busTrip.getId())) {
            return new ResponseEntity<>("Не существует", HttpStatus.NOT_FOUND);
        }

        Bus bus = busRepo.findById(busTrip.getBus().getId()).orElseThrow(RuntimeException::new);
        busTrip.setBus(bus);
        busTripRepo.save(busTrip);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @DeleteMapping("/bus-trip/delete")
    public ResponseEntity deleteBusTrip(@RequestParam(name = "id") String id) {
        if (!busTripRepo.existsById(id)) {
            return new ResponseEntity<>("Не найдено", HttpStatus.NOT_FOUND);
        }
        busTripRepo.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @PostMapping("/bus/save")
    public ResponseEntity saveBus(@RequestBody Bus bus) {
        if (busRepo.existsById(bus.getId())) {
            return new ResponseEntity<>("Уже существует", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        busRepo.save(bus);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @PutMapping("/bus/update")
    public ResponseEntity updateBus(@RequestBody Bus bus) {
        if (!busRepo.existsById(bus.getId())) {
            return new ResponseEntity<>("Не существует", HttpStatus.NOT_FOUND);
        }
        busRepo.save(bus);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @DeleteMapping("/bus/delete")
    public ResponseEntity deleteBus(@RequestParam(name = "id") String id) {
        if (!busRepo.existsById(id)) {
            return new ResponseEntity<>("Не найдено", HttpStatus.NOT_FOUND);
        }
        busRepo.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
