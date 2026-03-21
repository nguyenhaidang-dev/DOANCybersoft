package com.datn.drugstore.config;

import com.datn.drugstore.entity.User;
import com.datn.drugstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {

            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("admin@gmail.com");
            admin.setPhone("0123456789");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setIsAdmin(true);

            userRepository.save(admin);

            System.out.println("Admin account created!");
        }
    }
}