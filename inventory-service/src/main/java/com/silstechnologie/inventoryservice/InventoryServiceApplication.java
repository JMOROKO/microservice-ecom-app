package com.silstechnologie.inventoryservice;

import com.silstechnologie.inventoryservice.entities.Product;
import com.silstechnologie.inventoryservice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ProductRepository productRepository){
        return args -> {
            productRepository.save(
                    Product.builder()
                            .id(UUID.randomUUID().toString())
                            .name("Printer")
                            .price(500)
                            .quantity(2)
                            .build()
            );
            productRepository.save(
                    Product.builder()
                            .id(UUID.randomUUID().toString())
                            .name("Computer")
                            .price(1500)
                            .quantity(2)
                            .build()
            );
            productRepository.save(
                    Product.builder()
                            .id(UUID.randomUUID().toString())
                            .name("Smart phone")
                            .price(800)
                            .quantity(2)
                            .build()
            );
            productRepository.findAll().forEach(p -> {
                System.out.println(p.toString());
            });
        };
    }
}
