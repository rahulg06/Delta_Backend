package com.deltaclause.academy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import jakarta.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.net.Socket;

@SpringBootApplication
public class Application {


    @PostConstruct
    public void testGmailConnection() {
        try (Socket socket = new Socket()) {
            System.out.println("Testing smtp.gmail.com:587...");
            socket.connect(new InetSocketAddress("smtp.gmail.com", 587), 10000);
            System.out.println("SUCCESS: Gmail SMTP reachable");
        } catch (Exception e) {
            System.out.println("FAILED: Gmail SMTP NOT reachable");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
