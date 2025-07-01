package com.healthcare.healthcare.config;


import com.healthcare.healthcare.usuario.entity.Role;
import com.healthcare.healthcare.usuario.entity.User;
import com.healthcare.healthcare.usuario.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner initUsers(UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                userRepository.save(User.builder()
                        .username("admin")
                        .dni("admin")
                        .password(encoder.encode("admin123"))
                        .role(Role.ADMIN)
                        .enabled(true)
                        .build());
                System.out.println("✅ Usuario ADMIN creado: admin/admin123");
            }
        };
    }
}
