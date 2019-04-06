package course.project.security;

import course.project.entity.Admin;
import course.project.repo.AdminRepo;
import course.project.resource.Authority;
import course.project.service.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Qualifier("admin_provider")
public class AdminAuthenticationProvider implements AuthenticationProvider {
    private AdminRepo repo;
    private PasswordEncoder encoder;

    @Autowired
    public AdminAuthenticationProvider(AdminRepo repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getPrincipal().toString();
        String password = encoder.encodePassword(authentication.getCredentials().toString());
        Optional<Admin> admin = repo.findByLoginAndPassword(login, password);

        if (admin.isPresent()) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(Authority.ADMIN);

            return new UsernamePasswordAuthenticationToken(login, password, authorities);
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
