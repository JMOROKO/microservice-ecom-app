package com.silstechnologie.billingservice;

import com.silstechnologie.billingservice.entities.Bill;
import com.silstechnologie.billingservice.entities.ProductItem;
import com.silstechnologie.billingservice.feign.CustomerRestClient;
import com.silstechnologie.billingservice.feign.ProductRestClient;
import com.silstechnologie.billingservice.model.Customer;
import com.silstechnologie.billingservice.model.Product;
import com.silstechnologie.billingservice.repository.BillRepository;
import com.silstechnologie.billingservice.repository.ProductItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;

import java.util.*;

@SpringBootApplication
@EnableFeignClients //activé pour pouvoir utiliser le rest client
public class BillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BillRepository billRepository
            , ProductItemRepository productItemRepository
            , CustomerRestClient customerRestClient
            , ProductRestClient productRestClient){
        return args -> {
            Collection<Customer> customers = customerRestClient.getAllCustomers().getContent();
            Collection<Product> products = productRestClient.getAllProducts().getContent();


            customers.forEach(c -> {
                Bill bill = Bill.builder()
                        .billingDate(new Date())
                        .customerId(c.getId())
                        .build();
                billRepository.save(bill);

                products.forEach(p -> {
                    ProductItem productItem = ProductItem.builder()
                            .bill(bill)
                            .productId(p.getId())
                            .quantity(1 + new Random().nextInt(10))
                            .build();
                    productItemRepository.save(productItem);
                });
            });
        };
    }
}
