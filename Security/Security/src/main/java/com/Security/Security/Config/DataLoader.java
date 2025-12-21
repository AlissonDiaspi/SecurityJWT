package com.Security.Security.Config;

import com.Security.Security.Entity.User;
import com.Security.Security.Entity.Role;
import com.Security.Security.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            if (userRepository.count() == 0) {
                User user = new User();
                user.setEmail("admin@email.com");
                user.setPassword(passwordEncoder.encode("123456"));
                user.setRole(Set.of(Role.ADMIN));
                user.setEnabled(true);


                userRepository.save(user);
            }
        };
    }
}
