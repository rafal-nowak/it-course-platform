package pl.sages.javadevpro.projecttwo.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    // TODO final + required args constructor - done
    private final UserPrincipalDetailsService userPrincipalDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
            .csrf().disable()
            .authorizeRequests()
//            .antMatchers("/api/v1/test").anonymous()
//            .antMatchers("/quiz/**").anonymous()
            .antMatchers(HttpMethod.GET, "/api/v1/users/me").hasAnyRole("ADMIN", "STUDENT")
            .antMatchers("/api/v1/users/**").hasRole("ADMIN")
            .antMatchers(HttpMethod.DELETE, "/api/v1/task-blueprints").hasRole("ADMIN")
            .mvcMatchers(HttpMethod.POST, "/api/v1/task-blueprints").hasRole("ADMIN")
            .mvcMatchers(HttpMethod.PUT, "/api/v1/task-blueprints").hasRole("ADMIN")
            .mvcMatchers("/api/v1/task-blueprints/**").hasAnyRole("ADMIN", "STUDENT")
            .antMatchers(HttpMethod.DELETE, "/api/v1/grading-tables/**").hasRole("ADMIN")
            .mvcMatchers(HttpMethod.POST, "/api/v1/grading-tables").hasRole("ADMIN")
            .mvcMatchers(HttpMethod.PUT, "/api/v1/grading-tables").hasRole("ADMIN")
            .mvcMatchers("/api/v1/grading-tables/**").hasAnyRole("ADMIN", "STUDENT")
            .antMatchers(HttpMethod.DELETE, "/api/v1/quizzes/**").hasRole("ADMIN")
            .mvcMatchers(HttpMethod.POST, "/api/v1/quizzes").hasRole("ADMIN")
            .mvcMatchers("/api/v1/quizzes/**").hasAnyRole("ADMIN", "STUDENT")
            .mvcMatchers(HttpMethod.GET, "/api/v1/quiz-templates/**").hasAnyRole("ADMIN", "STUDENT")
            .antMatchers("/api/v1/quiz-templates/**").hasRole("ADMIN")
            .antMatchers("/api/v1/quiz-solution-templates/**").hasRole("ADMIN")
            .antMatchers("/api/v1/quiz-solution-templates/**").hasRole("ADMIN")
            .antMatchers("/api/v1/quiz-assign/**").hasRole("ADMIN")
            .antMatchers("/api/v1/assign").hasRole("ADMIN")
            .mvcMatchers("/api/v1/tasks/{taskId}/**").hasAnyRole("ADMIN", "STUDENT")
            .and()
            .httpBasic();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // TODO zdefiniować PasswordEncoder - done
        auth.userDetailsService(userPrincipalDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
//        return source;
//    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.applyPermitDefaultValues();
        corsConfiguration.addAllowedMethod(HttpMethod.GET);
        corsConfiguration.addAllowedMethod(HttpMethod.POST);
        corsConfiguration.addAllowedMethod(HttpMethod.PUT);
        corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
        corsConfiguration.addAllowedMethod(HttpMethod.PATCH);
//        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedOrigin("http://localhost:3000");

        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

}
