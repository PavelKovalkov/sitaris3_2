package course.project.security;

import course.project.entity.User;
import course.project.resource.Authority;
import course.project.resource.UserPublicInfo;
import course.project.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Qualifier("user_provider")
public class UserAuthenticationProvider implements AuthenticationProvider {
    private final UserManagementService userManagementService;

    @Autowired
    public UserAuthenticationProvider(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        Optional<User> user = userManagementService.findUserByEmailAndPassword(email, password);

        if (user.isPresent()) {
            List<Authority> authorities = new ArrayList<>();
            authorities.add(Authority.USER);
            User currentUser = user.get();
            UserPublicInfo userPublicInfo = new UserPublicInfo(currentUser.getEmail(), currentUser.getUsername());
            return new UsernamePasswordAuthenticationToken(userPublicInfo, password, authorities);
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
