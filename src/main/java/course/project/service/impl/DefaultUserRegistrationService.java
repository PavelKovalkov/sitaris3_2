package course.project.service.impl;

import course.project.entity.User;
import course.project.exception.UserAlreadyExistException;
import course.project.repo.UserRepo;
import course.project.service.PasswordEncoder;
import course.project.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DefaultUserRegistrationService implements UserRegistrationService {
    private final PasswordEncoder encoder;
    private final UserRepo repo;


    @Autowired
    public DefaultUserRegistrationService(PasswordEncoder encoder, UserRepo repo) {
        this.encoder = encoder;
        this.repo = repo;
    }

    @Override
    public void registerUser(User user) {
        Optional<User> dbUser = repo.findByEmail(user.getEmail());

        if (dbUser.isPresent()) {
            throw new UserAlreadyExistException();
        }

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(encoder.encodePassword(user.getPassword()));
        newUser.setUsername(user.getUsername());

        repo.save(newUser);
    }
}
