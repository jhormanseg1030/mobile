package com.app.backend.config;

import com.app.backend.models.User;
import com.app.backend.models.Category;
import com.app.backend.repository.UserRepository;
import com.app.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Ejecutando DataInitializer");

        if(userRepository.existsByUsername("admin")){
            User existingAdmin = userRepository.findByUsername("admin").orElse(null);

            if(existingAdmin != null){
                userRepository.delete(existingAdmin);
                System.out.println("Usuario admin borrado");
            }
        }
        if(userRepository.existsByUsername("coor")){
            User existingCoord = userRepository.findByUsername("coor").orElse(null);

            if(existingCoord != null){
                userRepository.delete(existingCoord);
                System.out.println("Usuario coor borrado");
            }
        }

        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@app.com");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRole(User.Role.ADMIN);
        admin.setActive(true);
        userRepository.save(admin);
        System.out.println("Usuario admin creado");

        User coor = new User();
        coor.setUsername("coor");
        coor.setEmail("coordinador@app.com");
        coor.setPassword(passwordEncoder.encode("coor"));
        coor.setRole(User.Role.COORDINADOR);
        coor.setActive(true);
        userRepository.save(coor);
        System.out.println("Usuario coor creado");
        System.out.println("DataInitializer completado exitosamente");
    }
}