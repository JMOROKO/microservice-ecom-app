package com.silstechnologie.customerservice.repository;

import com.silstechnologie.customerservice.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource //de spring data rest permet de creer les api
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
