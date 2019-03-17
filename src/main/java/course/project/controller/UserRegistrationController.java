package course.project.controller;

import course.project.entity.User;
import course.project.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class UserRegistrationController {
    private final UserRegistrationService userRegistrationService;

    @Autowired
    public UserRegistrationController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @PostMapping("/registration")
    public String registerUser(@RequestBody @Valid User user) {
        userRegistrationService.registerUser(user);
        return "user/login";
    }
}
