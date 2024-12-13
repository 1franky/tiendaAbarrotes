package mx.unam.dgtic.security;

import mx.unam.dgtic.security.jwt.JWTAuthenticationFilter;
import mx.unam.dgtic.security.jwt.JWTTokenProvider;
import mx.unam.dgtic.security.logout.CustomLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.SecureRandom;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private UserDetailsService uds;
    @Autowired
    private JWTTokenProvider tokenProvider;
    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/api/**").permitAll()
                        .requestMatchers("/inicio", "/home", "/", "/login").permitAll()
                        .requestMatchers("/css/**", "/favicon.ico", "/templates/**", "/token", "/index").permitAll()
                        .requestMatchers("/bootstrap/**", "/iconos/**", "/image/**", "/js/**").permitAll()
                        .requestMatchers("/users/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/reporte/**", "/product/**", "/category/**", "/proveedor/**").hasAnyAuthority("ADMIN", "GERENTE")
                        .requestMatchers("/ticket/**").hasAnyAuthority("ADMIN", "GERENTE", "USER")
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .successForwardUrl("/login_success_handler")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/doLogout")
                        .logoutSuccessUrl("/index")
                        .deleteCookies("JSESSIONID") //NEW Cookies to clear
                        .logoutSuccessHandler(customLogoutSuccessHandler)
                        .clearAuthentication(true)
                        .invalidateHttpSession(true))
                .addFilterAfter(new JWTAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        ;
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11, new SecureRandom());
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(uds);
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
