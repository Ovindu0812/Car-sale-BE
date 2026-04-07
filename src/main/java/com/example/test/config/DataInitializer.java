package com.example.test.config;

import com.example.test.entity.Role;
import com.example.test.entity.User;
import com.example.test.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String ADMIN_EMAIL = "admin@carsale.com";
    private static final String ADMIN_PASSWORD = "password123";

    @Override
    public void run(String... args) throws Exception {
        Optional<User> existingAdmin = userRepository.findByEmail(ADMIN_EMAIL);

        if (existingAdmin.isEmpty()) {
            User admin = User.builder()
                .firstName("System")
                .lastName("Admin")
                .email(ADMIN_EMAIL)
                .username("admin")
                .password(passwordEncoder.encode(ADMIN_PASSWORD))
                .role(Role.ADMIN)
                .build();
            userRepository.save(admin);
            System.out.println("Default admin user created: admin@carsale.com / password123");
            return;
        }

        User admin = existingAdmin.get();
        boolean changed = false;

        if (admin.getRole() == null) {
            admin.setRole(Role.ADMIN);
            changed = true;
        }

        String password = admin.getPassword();
        boolean isBcrypt = password != null && (password.startsWith("$2a$") || password.startsWith("$2b$") || password.startsWith("$2y$"));
        if (!isBcrypt) {
            admin.setPassword(passwordEncoder.encode(ADMIN_PASSWORD));
            changed = true;
        }

        if (changed) {
            userRepository.save(admin);
            System.out.println("Default admin user repaired: admin@carsale.com / password123");
        }
    }
}