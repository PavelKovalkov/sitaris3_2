package course.project.service.impl;

import course.project.entity.Bus;
import course.project.entity.BusTrip;
import course.project.repo.BusRepo;
import course.project.repo.BusTripRepo;
import course.project.service.BusTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultBusTripService implements BusTripService {
    private final BusTripRepo busTripRepo;
    private final BusRepo busRepo;

    @Autowired
    public DefaultBusTripService(BusTripRepo busTripRepo, BusRepo busRepo) {
        this.busTripRepo = busTripRepo;
        this.busRepo = busRepo;
    }

    @Override
    public BusTrip getBusTripInfo(String busTripId) {
        return busTripRepo.findById(busTripId).orElseThrow(RuntimeException::new);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveBusTrip(BusTrip busTrip) {
        if (busTripRepo.existsById(busTrip.getId())) {
            throw new RuntimeException("Уже существует");
        }

        if (!busRepo.existsById(busTrip.getBus().getId())) {
            throw new RuntimeException("Автобуса с данным номером не существует");
        }
        busTripRepo.save(busTrip);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateBusTrip(BusTrip busTrip) {
        if (!busTripRepo.existsById(busTrip.getId())) {
            throw new RuntimeException("Не существует");
        }

        Bus bus = busRepo.findById(busTrip.getBus().getId()).orElseThrow(RuntimeException::new);
        busTrip.setBus(bus);
        busTripRepo.save(busTrip);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteBusTrip(String id) {
        if (!busTripRepo.existsById(id)) {
            throw new RuntimeException("Не найдено");
        }
        busTripRepo.deleteById(id);
    }
}
