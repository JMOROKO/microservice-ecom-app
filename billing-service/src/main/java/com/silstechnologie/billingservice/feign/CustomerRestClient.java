package com.silstechnologie.billingservice.feign;

import com.silstechnologie.billingservice.model.Customer;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.List;

//pour pouvoir utiliser ça il faut l'activer dans le point de demarrage de l'application
@FeignClient(name = "customer-service")  //utilisation de openfeign pour pouvoir faire la communication entre les microservices
public interface CustomerRestClient {

    @GetMapping("/api/customers/{id}")
    @CircuitBreaker(name="customerServiceCB", fallbackMethod = "getDefaultCustomer")
    Customer findCustomerById(@PathVariable("id") Long id);

    @GetMapping("/api/customers")
    @CircuitBreaker(name="customerServiceCB", fallbackMethod = "getDefaultCustomers")
    PagedModel<Customer> getAllCustomers();

    default Customer getDefaultCustomer(Long id, Exception ex){
        return Customer.builder()
                .id(id)
                .name("Default name")
                .email("defaultEmail")
                .build();
    }

    default PagedModel<Customer> getDefaultCustomers(Exception ex){
        Customer customer = Customer.builder()
                .id(1L)
                .name("Default name")
                .email("defaultEmail")
                .build();

        List<Customer> customers = Collections.singletonList(customer);

        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(1, 0, 1, 1);
        return PagedModel.of(customers, metadata);
    }
    /*default PagedModel<Customer> getDefaultCustomers(Long id, Exception ex){
        return Customer.builder()
                .id(id)
                .name("Default name")
                .email("defaultEmail")
                .build();
    }*/
}
