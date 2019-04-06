package course.project.service.impl;

import course.project.entity.Bus;
import course.project.repo.BusRepo;
import course.project.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultBusService implements BusService {
    private final BusRepo busRepo;

    @Autowired
    public DefaultBusService(BusRepo busRepo) {
        this.busRepo = busRepo;
    }

    @Override
    public Bus getBusInfo(String busId) {
        return busRepo.findById(busId).orElseThrow(RuntimeException::new);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveBus(Bus bus) {
        if (busRepo.existsById(bus.getId())) {
            throw new RuntimeException("Уже существует");
        }
        busRepo.save(bus);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateBus(Bus bus) {
        if (!busRepo.existsById(bus.getId())) {
            throw new RuntimeException("Не существует");
        }
        busRepo.save(bus);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteBus(String id) {
        if (!busRepo.existsById(id)) {
            throw new RuntimeException("Не найдено");
        }
        busRepo.deleteById(id);
    }
}
