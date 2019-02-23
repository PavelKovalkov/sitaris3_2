package course.project.repo;

import course.project.dao.Admin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepo extends CrudRepository<Admin, Long> {
    Optional<Admin> findByLoginAndPassword(String login, String password);
}
