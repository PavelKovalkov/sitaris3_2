package course.project.config;

import course.project.resource.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class WebSecurityConfig {
    @Configuration
    @Order(1)
    public static class AdminSecurityConfig extends WebSecurityConfigurerAdapter {
        private final AuthenticationProvider authenticationProvider;

        @Autowired
        public AdminSecurityConfig(@Qualifier("admin_provider") AuthenticationProvider authenticationProvider) {
            this.authenticationProvider = authenticationProvider;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
            authenticationManagerBuilder.authenticationProvider(authenticationProvider);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/admin/**").authorizeRequests()
                    .antMatchers("/admin/**").hasAuthority(Authority.ADMIN.getAuthority())
                    .anyRequest().permitAll()
                .and()
                    .formLogin()
                    .loginPage("/admin/login")
                    .successHandler((request, response, authentication) -> response.sendRedirect("/admin/home"))
                    .permitAll()
                .and()
                    .logout()
                    .logoutUrl("/admin/logout")
                    .permitAll();
        }
    }

    @Configuration
    @Order(2)
    public static class UserSecurityConfig extends WebSecurityConfigurerAdapter {
        private final AuthenticationProvider authenticationProvider;
        private final AuthenticationSuccessHandler authenticationSuccessHandler;

        @Autowired
        public UserSecurityConfig(@Qualifier("user_provider") AuthenticationProvider authenticationProvider,
                                  AuthenticationSuccessHandler authenticationSuccessHandler) {
            this.authenticationProvider = authenticationProvider;
            this.authenticationSuccessHandler = authenticationSuccessHandler;
        }


        @Override
        protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
            authenticationManagerBuilder.authenticationProvider(authenticationProvider);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/user/**").authorizeRequests()
                    .antMatchers("/user/**").hasAuthority(Authority.USER.getAuthority())
                    .anyRequest().permitAll()
                .and()
                    .formLogin()
                    .loginPage("/user/login")
                    .successHandler(authenticationSuccessHandler)
                    .permitAll()
                .and()
                    .logout()
                    .logoutUrl("/user/logout")
                    .permitAll();
        }
    }
}
