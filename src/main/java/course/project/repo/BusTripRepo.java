package course.project.repo;

import course.project.entity.BusTrip;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface BusTripRepo extends CrudRepository<BusTrip, Long> {

    Optional<BusTrip> findById(String id);

    void deleteAllByDepartureDateBefore(Date date);

    Collection<BusTrip> findAllByDepartureDateAndDepartureStationAndArrivalStation(Date date,
                                                                                   String departureStation,
                                                                                   String arrivalStation);
}
