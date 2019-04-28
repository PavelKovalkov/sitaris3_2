package course.project.repo;

import course.project.entity.Admin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepo extends CrudRepository<Admin, String> {

    Optional<Admin> findByLoginAndPassword(String login, String password);
}
