package course.project.service.impl;

import course.project.entity.Bus;
import course.project.repo.BusRepo;
import course.project.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class DefaultBusService implements BusService {
    private final BusRepo busRepo;
    private final Lock readLock;
    private final Lock writeLock;

    @Autowired
    public DefaultBusService(BusRepo busRepo) {
        this.busRepo = busRepo;
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        readLock = reentrantReadWriteLock.readLock();
        writeLock = reentrantReadWriteLock.writeLock();
    }

    @Override
    public Bus getBusInfo(String busId) {
        try {
            readLock.lock();
            return busRepo.findById(busId).orElseThrow(RuntimeException::new);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveBus(Bus bus) {
        try {
            writeLock.lock();
            if (busRepo.existsById(bus.getId())) {
                throw new RuntimeException("Уже существует");
            }
            busRepo.save(bus);

        } finally {
            writeLock.unlock();
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateBus(Bus bus) {
        try {
            writeLock.lock();

            if (!busRepo.existsById(bus.getId())) {
                throw new RuntimeException("Не существует");
            }
            busRepo.save(bus);

        } finally {
            writeLock.unlock();
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteBus(String id) {
        try {
            writeLock.lock();

            if (!busRepo.existsById(id)) {
                throw new RuntimeException("Не найдено");
            }
            busRepo.deleteById(id);

        } finally {
            writeLock.unlock();
        }
    }
}
