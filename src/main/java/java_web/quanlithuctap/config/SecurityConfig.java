package java_web.quanlithuctap.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final CustomUserDetailsService customUserDetailsService;
    private final JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/auth/register").hasRole("ADMIN")

                        .requestMatchers("/api/users/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET,"/api/students").hasAnyRole("ADMIN", "MENTOR")
                        .requestMatchers(HttpMethod.GET,"/api/students/*").hasAnyRole("ADMIN", "MENTOR")
                        .requestMatchers(HttpMethod.POST,"/api/students").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/students/*").hasAnyRole("ADMIN", "STUDENT")

                        .requestMatchers(HttpMethod.GET,"/api/mentors").hasAnyRole("ADMIN", "STUDENT")
                        .requestMatchers(HttpMethod.GET,"/api/mentors/*").hasRole("MENTOR")
                        .requestMatchers(HttpMethod.POST,"/api/mentors").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/mentors/*").hasAnyRole("ADMIN", "MENTOR")

                        .requestMatchers(HttpMethod.GET, "/api/internship_phases/**").hasAnyRole("ADMIN", "MENTOR", "STUDENT")
                        .requestMatchers(HttpMethod.POST, "/api/internship_phases").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/internship_phases/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/internship_phases/**").hasRole("ADMIN")

                        .requestMatchers("/api/evaluation_criteria/**").hasAnyRole("ADMIN", "MENTOR", "STUDENT")

                        .requestMatchers("/api/assessment_rounds/**").hasAnyRole("ADMIN", "MENTOR", "STUDENT")

                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }



    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return customUserDetailsService;
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return jwtAuthenticationEntryPoint;
    }
}
