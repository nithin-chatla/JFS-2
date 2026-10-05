package com.campusconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CampusConnectApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampusConnectApplication.class, args);
        System.out.println("\n=======================================================");
        System.out.println("🚀 Campus Connect Platform is RUNNING successfully!");
        System.out.println("🌐 Access Application: http://localhost:8080");
        System.out.println("🗄️ H2 Database Console: http://localhost:8080/h2-console");
        System.out.println("=======================================================\n");
    }
}
