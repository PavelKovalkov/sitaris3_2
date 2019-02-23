package course.project.repo;

import course.project.dao.BusTrip;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusTripRepo extends CrudRepository<BusTrip, Long> {
}
