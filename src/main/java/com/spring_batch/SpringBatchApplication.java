package com.spring_batch;

import com.spring_batch.enums.UserRole;
import com.spring_batch.model.User;
import com.spring_batch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class SpringBatchApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchApplication.class, args);
    }

    @Value("${app.security.initial-username}")
    private String username;
    @Value("${app.security.initial-password}")
    private String password;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public SpringBatchApplication(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            String newUserPassword = passwordEncoder.encode(password);
            User defaultUser = new User();
            defaultUser.setUsername(username);
            defaultUser.setPassword(newUserPassword);
            defaultUser.setRoles(Set.of(UserRole.ADMIN));
            userRepository.save(defaultUser);
        }
    }
}
