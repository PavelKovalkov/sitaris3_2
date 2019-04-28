package course.project.service.impl;

import course.project.entity.Bus;
import course.project.entity.BusTrip;
import course.project.exception.BusDoesNotExistException;
import course.project.exception.BusTripAlreadyExistException;
import course.project.exception.BusTripDoesNotExistException;
import course.project.repo.BusRepo;
import course.project.repo.BusTripRepo;
import course.project.service.BusTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Collection;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

@Service
public class DefaultBusTripService implements BusTripService {
    private final BusTripRepo busTripRepo;
    private final BusRepo busRepo;
    private final Lock readLock;
    private final Lock writeLock;

    @Autowired
    public DefaultBusTripService(BusTripRepo busTripRepo, BusRepo busRepo) {
        this.busTripRepo = busTripRepo;
        this.busRepo = busRepo;
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        readLock = reentrantReadWriteLock.readLock();
        writeLock = reentrantReadWriteLock.writeLock();
    }

    @Override
    public BusTrip getBusTripInfo(String busTripId) {
        try {
            readLock.lock();
            return busTripRepo.findById(busTripId).orElseThrow(BusTripDoesNotExistException::new);

        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveBusTrip(BusTrip busTrip) {
        try {
            writeLock.lock();
            if (busTripRepo.existsById(busTrip.getId())) {
                throw new BusTripAlreadyExistException();
            }

            Bus bus = busRepo.findById(busTrip.getBus().getId()).orElseThrow(BusDoesNotExistException::new);
            busTrip.setBus(bus);
            busTripRepo.save(busTrip);

        } finally {
            writeLock.unlock();
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateBusTrip(BusTrip busTrip) {
        try {
            writeLock.lock();

            if (!busTripRepo.existsById(busTrip.getId())) {
                throw new BusTripDoesNotExistException();
            }

            Bus bus = busRepo.findById(busTrip.getBus().getId()).orElseThrow(BusDoesNotExistException::new);
            busTrip.setBus(bus);
            busTripRepo.save(busTrip);

        } finally {
            writeLock.unlock();
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteBusTrip(String id) {
        try {
            writeLock.lock();

            if (!busTripRepo.existsById(id)) {
                throw new BusTripDoesNotExistException();
            }
            busTripRepo.deleteById(id);

        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public Collection<BusTrip> getBusTripsByDepartureDateAndDepartureStationAndArrivalStation(String date, String departureStation, String arrivalStation) {
        try {
            readLock.lock();

            Collection<BusTrip> result = busTripRepo.findAllByDepartureDateAndDepartureStationAndArrivalStation(Date.valueOf(date), departureStation, arrivalStation);
            return result
                .stream()
                .filter(trip -> trip.getAvailableTicketCount() != 0)
                .collect(Collectors.toList());

        } finally {
            readLock.unlock();
        }
    }
}
