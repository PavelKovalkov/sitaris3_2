package course.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/user/home").setViewName("user/home");
        registry.addViewController("/user/login").setViewName("user/login");
        registry.addViewController("/user/userinfo").setViewName("user/userinfo");
        registry.addViewController("/admin/home").setViewName("admin/home");
        registry.addViewController("/admin/login").setViewName("admin/login");
    }
}
