package course.project.repo;

import course.project.dao.Bus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRepo extends CrudRepository<Bus, Long> {
}
