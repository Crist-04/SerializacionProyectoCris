package com.digis01.CAlvarezProgramacionNCapasOctubre2025.Configuration;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO.IUsuarioRepositoryDAO;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.UsuarioJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.Service.UserDetailsJPAService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

    private final UserDetailsJPAService userDetailsJPAService;
    private IUsuarioRepositoryDAO usuarioRepositoryDAO;

    public SpringSecurityConfiguration(UserDetailsJPAService userDetailsJPAService, IUsuarioRepositoryDAO usuarioRepositoryDAO) {
        this.userDetailsJPAService = userDetailsJPAService;
        this.usuarioRepositoryDAO = usuarioRepositoryDAO;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/api/login").permitAll()
                .requestMatchers("/api/**").permitAll()
                .requestMatchers("/usuario/**").hasAnyRole("ADMINISTRADOR", "USUARIO", "CLIENTE", "ROL5")
                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                .loginPage("/login")
                .successHandler(customAuthenticationSuccessHandler())
                .defaultSuccessUrl("/usuario", true)
                .permitAll()
                )
                .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
                )
                .userDetailsService(userDetailsJPAService);

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request,
                    HttpServletResponse response,
                    Authentication authentication) throws IOException, ServletException {

                String username = authentication.getName();
                UsuarioJPA usuario = usuarioRepositoryDAO.findByUsername(username);

                if (usuario != null && usuario.getRol() != null) {
                    String rolNombre = usuario.getRol().getNombre().toUpperCase();

                    if ("ADMINISTRADOR".equals(rolNombre)) {
                        response.sendRedirect("/usuario");
                    } else if ("USUARIO".equals(rolNombre)
                            || "CLIENTE".equals(rolNombre)
                            || "ROL5".equals(rolNombre)) {
                        response.sendRedirect("/usuario/detalle/" + usuario.IdUsuario);
                    } else {
                        response.sendRedirect("/usuario");
                    }
                } else {
                    response.sendRedirect("/usuario");
                }
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
