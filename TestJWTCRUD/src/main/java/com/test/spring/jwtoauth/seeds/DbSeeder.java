package com.test.spring.jwtoauth.seeds;

import com.github.javafaker.Faker;
import com.test.spring.jwtoauth.entities.Client;
import com.test.spring.jwtoauth.repositories.ClientRepository;
import com.test.spring.jwtoauth.service.UserService;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Random;
import java.util.stream.LongStream;

@Service
public class DbSeeder implements CommandLineRunner {

    @Autowired
    private ClientRepository clientsRepository;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserService usersService;

    @Autowired
    private Environment environment;

    @Override
    public void run(String... args) {
        // Print the database connection string for verification
        System.out.printf("[+] We are using the following database connection string : %s\n" +
                "Go ahead into http://localhost:" + environment.getProperty("server.port") + "/api/h2-console and paste that connection string,\nusername=user,password=password, to access" +
                "the h2 database console ;)", ((HikariDataSource) dataSource).getJdbcUrl());

        // Count existing clients
        long clientsCount = this.clientsRepository.count();
        Faker faker = new Faker(new Random(System.currentTimeMillis()));
        long clientsToSeed = 23;
        clientsToSeed -= clientsCount;

        // Seed new clients
        LongStream.range(0, clientsToSeed).forEach(i -> {
            Client client = new Client(
                    faker.name().fullName(),
                    faker.internet().emailAddress(),
                    faker.phoneNumber().phoneNumber(),
                    faker.address().fullAddress()
            );
            clientsRepository.save(client);
        });

        // Seed users
        long usersCount = this.usersService.count();
        long usersToSeed = 3;
        usersToSeed -= usersCount;
        LongStream.range(0, usersToSeed).forEach(i -> {
            usersService.createUser(faker.name().username(), "password");
        });

        // Create an admin if needed
        if (usersToSeed > 0) {
            usersService.createUser("admin", "password");
        }
    }
}
