package course.project.repo;

import course.project.entity.Bus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusRepo extends CrudRepository<Bus, Long> {

    boolean existsById(String id);

    void deleteById(String id);

    Optional<Bus> findById(String id);
}
