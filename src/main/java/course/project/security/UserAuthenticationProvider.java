package course.project.security;

import course.project.dao.User;
import course.project.repo.UserRepo;
import course.project.resource.Authority;
import course.project.resource.UserPublicInfo;
import course.project.service.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserAuthenticationProvider implements AuthenticationProvider {
    private UserRepo repo;
    private PasswordEncoder encoder;

    public UserAuthenticationProvider(UserRepo repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getPrincipal().toString();
        String password = encoder.encodePassword(authentication.getCredentials().toString());
        Optional<User> user = repo.findByEmailAndPassword(email, password);

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
