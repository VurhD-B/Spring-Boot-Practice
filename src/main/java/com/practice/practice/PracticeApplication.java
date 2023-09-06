package com.practice.practice;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.practice.models.Role;
import com.practice.practice.models.User;
import com.practice.practice.repository.RoleRepository;
import com.practice.practice.repository.UserRepository;

@SpringBootApplication
@RestController
public class PracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PracticeApplication.class, args);
    }

    @Bean
    CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder){
        return args -> {
            // Check if the roles already exists:
            if(roleRepository.findByAuthority("ADMIN").isPresent()) return;

            // Otherwise we make a admin:
            Role adminRole = roleRepository.save(new Role("ADMIN"));
            roleRepository.save(new Role("USER"));

            Set<Role> roles = new HashSet<Role>();
            roles.add(adminRole);

            User admin = new User(1, "admin", passwordEncoder.encode("password"), roles);
            userRepository.save(admin);
        };
    }

    @GetMapping
    public String hello() {
        return "Hello World";
    }

}
