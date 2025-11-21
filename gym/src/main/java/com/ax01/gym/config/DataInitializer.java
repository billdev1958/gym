/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ax01.gym.config;

import com.ax01.gym.model.Account;
import com.ax01.gym.model.CatRole;
import com.ax01.gym.model.MembershipType;
import com.ax01.gym.model.User;
import com.ax01.gym.repository.IAccountJpaRepository;
import com.ax01.gym.repository.ICatRoleJpaRepository;
import com.ax01.gym.repository.IMembershipTypeJpaRepository;
import com.ax01.gym.service.IAdminService;
import com.ax01.gym.utils.Constants.GenreEnum;
import com.ax01.gym.utils.Constants.RoleEnum;
import java.math.BigDecimal;
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
    private final IAccountJpaRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final IMembershipTypeJpaRepository membershipTypeRepository;

    public DataInitializer(ICatRoleJpaRepository catRoleRepository, 
                           IAdminService adminService, 
                           IAccountJpaRepository accountRepository,
                           PasswordEncoder passwordEncoder,
                           IMembershipTypeJpaRepository membershipTypeRepository) {
        this.catRoleRepository = catRoleRepository;
        this.adminService = adminService;
        this.accountRepository = accountRepository;
        this.membershipTypeRepository = membershipTypeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        initializeRoles();
        initializeAdminUser();
        initializeMembershipTypes();
    }

    private void initializeRoles() {
        System.out.println("--- Inicializando Roles ---");

        Optional<CatRole> adminRole = catRoleRepository.findByName(RoleEnum.ADMINISTRADOR.getName());
        if (adminRole.isEmpty()) {
            CatRole role = new CatRole();
            role.setName(RoleEnum.ADMINISTRADOR.getName());
            catRoleRepository.save(role);
            System.out.println("Rol creado: ADMINISTRADOR");
        }

        Optional<CatRole> clientRole = catRoleRepository.findByName(RoleEnum.CLIENTE.getName());
        if (clientRole.isEmpty()) {
            CatRole role = new CatRole();
            role.setName(RoleEnum.CLIENTE.getName());
            catRoleRepository.save(role);
            System.out.println("Rol creado: CLIENTE");
        }
    }
    
    private void initializeMembershipTypes() {
        System.out.println("--- Inicializando Catálogo de Membresías ---");

        createMembershipIfNotExists(
            "Membresía Básica", 
            "Uso libre del gimnasio (mancuernas, cardio, máquinas), regaderas. Horario: 10:00am - 7:00pm.", 
            30, 
            new BigDecimal("400.00")
        );

        createMembershipIfNotExists(
            "Membresía Premium", 
            "Uso libre total, regaderas, horario extendido (6:00am - 10:00pm) + asesoría personalizada.", 
            30, 
            new BigDecimal("700.00")
        );

        createMembershipIfNotExists(
            "Membresía Semanal", 
            "Uso libre del gimnasio por 7 días (mancuernas, cardio, máquinas).", 
            7,
            new BigDecimal("450.00")
        );

        createMembershipIfNotExists(
            "Anual Básica", 
            "Beneficios de mensualidad básica con 30% de descuento anual.", 
            365, 
            new BigDecimal("4000.00") 
        );

        createMembershipIfNotExists(
            "Anual Premium", 
            "Beneficios de mensualidad premium con 30% de descuento anual.", 
            365, 
            new BigDecimal("7500.00")
        );
    }

    private void createMembershipIfNotExists(String name, String description, Integer days, BigDecimal cost) {
        if (membershipTypeRepository.findByName(name).isEmpty()) {
            MembershipType mt = new MembershipType();
            mt.setName(name);
            mt.setDescription(description);
            mt.setDurationDays(days);
            mt.setCost(cost);
            mt.setCreatedAt(Instant.now());
            
            membershipTypeRepository.save(mt);
            System.out.println("Membresía creada: " + name);
        } else {
            System.out.println("Membresía ya existe: " + name);
        }
    }

    private void initializeAdminUser() {
        String email = "billy@gmail.com";
        String rawPassword = "passwordseguro";
        
        try {
            Optional<Account> existingAccount = accountRepository.findByEmail(email);
            
            if (existingAccount.isPresent()) {
                System.out.println("--- Admin existente encontrado (" + email + ") ---");
                System.out.println("--- ACTUALIZANDO CONTRASEÑA ---");
                
                Account account = existingAccount.get();
                account.setPassword(passwordEncoder.encode(rawPassword));
                accountRepository.save(account);
                
                System.out.println("Contraseña actualizada a: " + rawPassword);
                
            } else {
                System.out.println("--- Creando Admin Inicial ---");

                User adminUser = new User();
                adminUser.setName("Billy");
                adminUser.setLastname1("Rivera");
                adminUser.setLastname2("Salinas");
                adminUser.setGenre(GenreEnum.MALE.getCode());
                adminUser.setCreatedAt(Instant.now());

                Account adminAccount = new Account();
                adminAccount.setEmail(email);
                adminAccount.setPassword(rawPassword); 

                adminService.registerNewAdmin(adminUser, adminAccount);
                System.out.println("Admin creado con éxito. Login: " + email + " / " + rawPassword);
            }
        } catch (Exception e) {
            System.err.println("❌ Error en DataInitializer: " + e.getMessage());
        }
    }
}