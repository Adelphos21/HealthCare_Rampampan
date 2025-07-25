package com.healthcare.healthcare.config;


import com.healthcare.healthcare.exception.NotFoundException;
import com.healthcare.healthcare.usuario.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class CustomUserDetailsService {

    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByDni(username)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado: " + username));
    }
}
