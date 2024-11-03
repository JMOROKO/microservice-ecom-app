package com.silstechnologie.billingservice.repository;

import com.silstechnologie.billingservice.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long> {
}
