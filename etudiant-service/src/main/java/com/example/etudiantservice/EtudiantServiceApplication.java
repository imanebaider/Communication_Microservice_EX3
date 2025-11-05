package com.example.etudiantservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.etudiantservice.configuration.RsaKeys;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties(RsaKeys.class)

public class EtudiantServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EtudiantServiceApplication.class, args);
    }

}
