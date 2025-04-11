package com.silstechnologie.customerservice;

import com.silstechnologie.customerservice.config.CustomerConfigParams;
import com.silstechnologie.customerservice.entities.Customer;
import com.silstechnologie.customerservice.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
//TODO 7- demander à spring d'aller charger la configuration au démarrage
@EnableConfigurationProperties(CustomerConfigParams.class)
@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository){
        return args -> {
            customerRepository.save(
                    Customer.builder()
                            .name("Moroko")
                            .email("morokojeanr@gmail.com")
                            .build());
            customerRepository.save(
                    Customer.builder()
                            .name("Koffi")
                            .email("koffi@gmail.com")
                            .build());
            customerRepository.findAll().forEach(c -> {
                System.out.println("=========================");
                System.out.println("id :"+c.getId());
                System.out.println("nom :"+c.getName());
                System.out.println("email :"+c.getEmail());
            });
        };
    }
}
