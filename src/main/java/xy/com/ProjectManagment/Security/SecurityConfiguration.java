package xy.com.ProjectManagment.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import xy.com.ProjectManagment.Security.Login.AuthenticationFailureHandlerImpl;
import xy.com.ProjectManagment.Security.Login.AuthenticationSuccessHandlerImpl;
import xy.com.ProjectManagment.User.Entity.UserData;
import xy.com.ProjectManagment.User.Service.UserDataServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private UserDataServiceImpl userDataService;
    private AuthenticationFailureHandlerImpl authenticationFailureHandler;

    private AuthenticationSuccessHandlerImpl authenticationSuccessHandler;
    private BCryptPasswordEncoder encoder;
    public SecurityConfiguration(
            UserDataServiceImpl userDataService,
            AuthenticationSuccessHandlerImpl authenticationSuccessHandler,
            AuthenticationFailureHandlerImpl authenticationFailureHandler,
            BCryptPasswordEncoder encoder
    ) {
        this.userDataService = userDataService;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.encoder = encoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/user-data/**").permitAll()
                        .requestMatchers("api/guest").permitAll()
                        .requestMatchers("/api/admin").hasRole("ADMIN")
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
            UserDetailsService userDetailsService
    ) throws Exception {
        builder
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder);
    }

}
