package ru.kata.spring.boot_security.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

@Component
public class ForTestDataLoader implements CommandLineRunner {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    @Autowired
    public ForTestDataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder,
                             RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            Role adminRole = new Role("ROLE_ADMIN");
            Role userRole = new Role("ROLE_USER");
            roleRepository.saveAll(List.of(adminRole, userRole));

            User admin = new User("admin", "admin@admin.ru", passwordEncoder.encode("admin"));
            admin.setRoles(Set.of(adminRole, userRole));
            userRepository.save(admin);

            User user = new User("user", "user@user.ru", passwordEncoder.encode("user"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);

            System.out.println("✔ Админ и юзер созданы в БД!");
        }

    }
}
