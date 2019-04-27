package course.project.service;

import course.project.entity.User;

import java.util.Optional;

public interface UserManagementService {

    void registerUser(User user);

    void changePassword(String email, String oldPassword, String newPassword);

    Optional<User> findUserByEmailAndPassword(String email, String password);
}
