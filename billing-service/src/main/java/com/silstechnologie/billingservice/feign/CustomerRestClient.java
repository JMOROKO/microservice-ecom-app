package com.silstechnologie.billingservice.feign;

import com.silstechnologie.billingservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//pour pouvoir utiliser ça il faut l'activer dans le point de demarrage de l'application
@FeignClient(name = "customer-service")  //utilisation de openfeign pour pouvoir faire la communication entre les microservices
public interface CustomerRestClient {
    @GetMapping("/api/customers/{id}")
    Customer findCustomerById(@PathVariable("id") Long id);

    @GetMapping("/api/customers")
    PagedModel<Customer> getAllCustomers();
}
