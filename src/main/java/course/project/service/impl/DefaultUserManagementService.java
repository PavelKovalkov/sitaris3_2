package course.project.service.impl;

import course.project.entity.User;
import course.project.exception.UnsuitablePasswordException;
import course.project.exception.UserAlreadyExistException;
import course.project.exception.UserDoesNotExistException;
import course.project.repo.UserRepo;
import course.project.service.PasswordEncoder;
import course.project.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class DefaultUserManagementService implements UserManagementService {
    private final PasswordEncoder encoder;
    private final UserRepo repo;
    private final Lock readLock;
    private final Lock writeLock;

    @Autowired
    public DefaultUserManagementService(PasswordEncoder encoder, UserRepo repo) {
        this.encoder = encoder;
        this.repo = repo;
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        readLock = reentrantReadWriteLock.readLock();
        writeLock = reentrantReadWriteLock.writeLock();
    }

    @Override
    public void registerUser(User user) {
        try {
            writeLock.lock();
            Optional<User> dbUser = repo.findByEmail(user.getEmail());

            if (dbUser.isPresent()) {
                throw new UserAlreadyExistException();
            }

            User newUser = new User();
            newUser.setEmail(user.getEmail());
            newUser.setPassword(encoder.encodePassword(user.getPassword()));
            newUser.setUsername(user.getUsername());

            repo.save(newUser);

        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void changePassword(String email, String oldPassword, String newPassword) {
        try {
            writeLock.lock();

            User user = repo.findByEmail(email).orElseThrow(UserDoesNotExistException::new);

            String encodedOldPassword = encoder.encodePassword(oldPassword);

            if (!user.getPassword().equals(encodedOldPassword)) {
                throw new UnsuitablePasswordException();
            }

            String encodedNewPassword = encoder.encodePassword(newPassword);
            user.setPassword(encodedNewPassword);
            repo.save(user);

        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        try {
            readLock.lock();

            String encodedPassword = encoder.encodePassword(password);
            return repo.findByEmailAndPassword(email, encodedPassword);

        } finally {
            readLock.unlock();
        }
    }
}
