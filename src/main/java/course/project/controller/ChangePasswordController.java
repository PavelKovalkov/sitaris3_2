package course.project.controller;

import course.project.entity.User;
import course.project.repo.UserRepo;
import course.project.resource.UserPublicInfo;
import course.project.service.PasswordEncoder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/user/change-password")
public class ChangePasswordController {
    private final UserRepo repo;
    private final PasswordEncoder encoder;

    @Autowired
    public ChangePasswordController(UserRepo repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @PostMapping
    public ResponseEntity changePassword(@RequestBody String body, HttpSession session) throws Exception {
        UserPublicInfo userInfo = (UserPublicInfo) session.getAttribute("userInfo");
        User user = repo.findByEmail(userInfo.getEmail()).orElseThrow(Exception::new);
        JSONObject object = new JSONObject(body);

        String oldPassword = encoder.encodePassword(object.getString("old_password"));
        if (!user.getPassword().equals(oldPassword)) {
            throw new Exception();
        }

        String newPassword = encoder.encodePassword(object.getString("new_password"));
        user.setPassword(newPassword);
        repo.save(user);
        return new ResponseEntity(HttpStatus.OK);
    }
}
