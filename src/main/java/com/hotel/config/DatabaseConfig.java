package com.hotel.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * DatabaseConfig
 * Verifies the MySQL connection on startup and prints a clear
 * success / failure message so you know immediately if the DB
 * is reachable.
 */
@Configuration
public class DatabaseConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    CommandLineRunner checkDatabaseConnection() {
        return args -> {
            try (Connection connection = dataSource.getConnection()) {
                System.out.println("============================================");
                System.out.println("  ✅ Database connected successfully!");
                System.out.println("  URL : " + connection.getMetaData().getURL());
                System.out.println("  DB  : " + connection.getCatalog());
                System.out.println("============================================");
            } catch (Exception e) {
                System.err.println("============================================");
                System.err.println("  ❌ Database connection FAILED!");
                System.err.println("  Reason : " + e.getMessage());
                System.err.println("  Fix    : Check application.properties");
                System.err.println("           - spring.datasource.password");
                System.err.println("           - Make sure MySQL is running");
                System.err.println("============================================");
            }
        };
    }
}
