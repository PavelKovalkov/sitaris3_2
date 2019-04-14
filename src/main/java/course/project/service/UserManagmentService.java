package course.project.service;

import course.project.entity.User;

public interface UserManagmentService {

    void registerUser(User user);

    void changePassword(String email, String oldPassword, String newPassword);
}
