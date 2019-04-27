package course.project.controller.user;

import course.project.entity.User;
import course.project.resource.UserPublicInfo;
import course.project.service.UserManagmentService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UserProfileController {
    private final UserManagmentService userManagmentService;

    @Autowired
    public UserProfileController(UserManagmentService userManagmentService) {
        this.userManagmentService = userManagmentService;
    }

    @PostMapping("/registration")
    public String registerUser(@RequestBody @Valid User user) {
        userManagmentService.registerUser(user);
        return "user/login";
    }

    @PostMapping("/user/change-password")
    public @ResponseBody
    ResponseEntity changePassword(@RequestBody String body, HttpSession session) {
        UserPublicInfo userInfo = (UserPublicInfo) session.getAttribute("userInfo");
        JSONObject object = new JSONObject(body);

        userManagmentService.changePassword(userInfo.getEmail(), object.getString("old_password"), object.getString("new_password"));
        return new ResponseEntity(HttpStatus.OK);
    }
}
