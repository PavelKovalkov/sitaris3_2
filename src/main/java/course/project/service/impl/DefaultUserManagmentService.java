package course.project.service.impl;

import course.project.entity.User;
import course.project.exception.UserAlreadyExistException;
import course.project.repo.UserRepo;
import course.project.service.PasswordEncoder;
import course.project.service.UserManagmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DefaultUserManagmentService implements UserManagmentService {
    private final PasswordEncoder encoder;
    private final UserRepo repo;


    @Autowired
    public DefaultUserManagmentService(PasswordEncoder encoder, UserRepo repo) {
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

    @Override
    public void changePassword(String email, String oldPassword, String newPassword) {
        User user = repo.findByEmail(email).orElseThrow(RuntimeException::new);

        String encodedOldPassword = encoder.encodePassword(oldPassword);

        if (!user.getPassword().equals(encodedOldPassword)) {
            throw new RuntimeException();
        }

        String encodedNewPassword = encoder.encodePassword(newPassword);
        user.setPassword(encodedNewPassword);
        repo.save(user);
    }
}
