/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ax01.gym.config;

import com.ax01.gym.model.Account;
import com.ax01.gym.model.CatRole;
import com.ax01.gym.model.User;
import com.ax01.gym.repository.ICatRoleJpaRepository;
import com.ax01.gym.service.IAdminService;
import com.ax01.gym.utils.Constants.GenreEnum;
import com.ax01.gym.utils.Constants.RoleEnum;
import java.time.Instant;
import java.util.Optional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author bill
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final ICatRoleJpaRepository catRoleRepository;
    private final IAdminService adminService;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(ICatRoleJpaRepository catRoleRepository, IAdminService adminService, PasswordEncoder passwordEncoder) {
        this.catRoleRepository = catRoleRepository;
        this.adminService = adminService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        initializeRoles();

        initializeAdminUser();
    }

    private void initializeRoles() {
        System.out.println("--- Inicializando Roles ---");

        Optional<CatRole> adminRole = catRoleRepository.findByName(RoleEnum.ADMINISTRADOR.getName());
        if (adminRole.isEmpty()) {
            CatRole role = new CatRole();
            role.setName(RoleEnum.ADMINISTRADOR.getName());
            catRoleRepository.save(role);
            System.out.println("Rol creado: ADMINISTRADOR");
        } else {
            System.out.println("Rol ADMINISTRADOR ya existe.");
        }

        Optional<CatRole> clientRole = catRoleRepository.findByName(RoleEnum.CLIENTE.getName());
        if (clientRole.isEmpty()) {
            CatRole role = new CatRole();
            role.setName(RoleEnum.CLIENTE.getName());
            catRoleRepository.save(role);
            System.out.println("Rol creado: CLIENTE");
        } else {
            System.out.println("Rol CLIENTE ya existe.");
        }

    }

    private void initializeAdminUser() {
        try {
            if (adminService.findAllUsers().stream().noneMatch(u -> u.getName().equals("Billy"))) {
                System.out.println("--- Creando Admin Inicial ---");

                User adminUser = new User();
                adminUser.setName("Billy");
                adminUser.setLastname1("Rivera");
                adminUser.setLastname2("Salinas");
                adminUser.setGenre(GenreEnum.MALE.getCode());
                adminUser.setCreatedAt(Instant.now());

                Account adminAccount = new Account();
                adminAccount.setEmail("billy@gmail.com");
                String rawPassword = "passwordseguro";
                String hashedPassword = passwordEncoder.encode(rawPassword);
                adminAccount.setPassword(hashedPassword);

                adminService.registerNewAdmin(adminUser, adminAccount);
                System.out.println("Admin 'billy@gmail.com' creado con éxito. Contraseña: passwordseguro");

            } else {
                System.out.println("Admin inicial ya existe.");
            }
        } catch (Exception e) {
            System.err.println("Error al crear Admin inicial: " + e.getMessage());
            System.err.println("Asegúrese de que el rol ADMINISTRADOR exista.");
        }
    }
}
