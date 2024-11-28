package mx.unam.dgtic.security;

import lombok.RequiredArgsConstructor;
import mx.unam.dgtic.security.jwt.JWTAuthenticationFilter;
import mx.unam.dgtic.security.jwt.JWTTokenProvider;
import mx.unam.dgtic.security.logout.CustomLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.SecureRandom;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 22/11/24
 * @project tiendaAbarrotes
 * Descripción: [...]
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserDetailsService userDetailsService;
    private final JWTTokenProvider tokenProvider;
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

//    @Value(value = "${spring.security.user.name}")
//    private String USERNAME;
//
//    @Value(value = "${spring.security.user.password}")
//    private String PASSWORD;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/api/**").permitAll()
                        .requestMatchers("/inicio", "/home", "/", "/login", "/index").permitAll()
                        .requestMatchers("/css/**", "/favicon.ico", "/templates/**").permitAll()
                        .requestMatchers("/bootstrap/**", "/iconos/**", "/image/**", "/js/**").permitAll()
//                        .requestMatchers("/category/**").hasAnyRole("ADMIN")
//                        .requestMatchers("/product/**").hasAnyRole("ADMIN")
//                        .requestMatchers("/proveedor/**").hasAnyRole("ADMIN")
//                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .successForwardUrl("/login_success_handler")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/doLogout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("JSESSIONID") //NEW Cookies to clear
//                        .logoutSuccessHandler(customLogoutSuccessHandler)
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                )
                .addFilterAfter(new JWTAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        ;
        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails admin = User.builder()
//                .username(USERNAME)
//                .password("{noop}" + PASSWORD)
//                .roles("ADMIN", "USER")
//                .build();
//
//        UserDetails user = User.builder()
//                .username("user")
//                .password("{noop}user")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, user);
//    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11, new SecureRandom());
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        //your AuthenticationProvider must return UserDetails object
        return new ProviderManager(authenticationProvider());
    }

}