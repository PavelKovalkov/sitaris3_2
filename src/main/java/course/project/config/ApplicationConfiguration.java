package course.project.config;

import course.project.repo.AdminRepo;
import course.project.repo.UserRepo;
import course.project.security.AdminAuthenticationProvider;
import course.project.security.UserAuthenticationProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;

@Configuration
public class ApplicationConfiguration {
    @Bean
    @Qualifier("admin_provider")
    public AuthenticationProvider adminProvider(AdminRepo adminRepo) {
        return new AdminAuthenticationProvider(adminRepo);
    }

    @Bean
    @Qualifier("user_provider")
    public AuthenticationProvider userProvider(UserRepo userRepo) {
        return new UserAuthenticationProvider(userRepo);
    }
}
