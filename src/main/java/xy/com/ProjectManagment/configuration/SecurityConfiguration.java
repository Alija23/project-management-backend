package xy.com.ProjectManagment.configuration;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import xy.com.ProjectManagment.configuration.login.AuthenticationFailureHandlerImpl;
import xy.com.ProjectManagment.configuration.login.AuthenticationSuccessHandlerImpl;
import xy.com.ProjectManagment.module.project.service.UserDataServiceImpl;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration {
    private UserDataServiceImpl userDataService;
    private AuthenticationFailureHandlerImpl authenticationFailureHandler;

    private AuthenticationSuccessHandlerImpl authenticationSuccessHandler;
    private BCryptPasswordEncoder encoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/api/user-data-admin/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form ->
                        form
                                .loginProcessingUrl("/api/login")
                                .failureHandler(authenticationFailureHandler)
                                .successHandler(authenticationSuccessHandler)
                        )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }


    /*@Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDataService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    } */

    @Autowired
    public void configureGlobal(
            AuthenticationManagerBuilder builder,
            UserDataServiceImpl userDetailsService
    ) throws Exception {
        builder
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder);
    }

}
