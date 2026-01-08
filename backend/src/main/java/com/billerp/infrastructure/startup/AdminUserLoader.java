package com.billerp.infrastructure.startup;

import java.util.EnumSet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.billerp.domain.enums.Role;
import com.billerp.domain.model.User;
import com.billerp.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AdminUserLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        var userAdmin = userRepository.findByUsername("admin");

        userAdmin.ifPresentOrElse(
                user -> {
                    System.out.println("admin ja existe");
                },
                () -> {
                    var user = new User();
                    user.setUsername("admin");
                    user.setEmail("admin@admin.com");
                    user.setPassword(passwordEncoder.encode("123456"));
                    user.setRoles(EnumSet.of(Role.ADMIN, Role.OPERATOR));
                    userRepository.save(user);
                });
    }
}
