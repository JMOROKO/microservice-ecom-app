package com.silstechnologie.billingservice.web;

import com.silstechnologie.billingservice.entities.Bill;
import com.silstechnologie.billingservice.feign.CustomerRestClient;
import com.silstechnologie.billingservice.feign.ProductRestClient;
import com.silstechnologie.billingservice.repository.BillRepository;
import com.silstechnologie.billingservice.repository.ProductItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("bills")
@RequiredArgsConstructor
public class BillRestController {
    private final BillRepository billRepository;
    private final ProductItemRepository productItemRepository;
    private final CustomerRestClient customerRestClient;
    private final ProductRestClient productRestClient;

    @GetMapping("/bills/{id}")
    public Bill getBill(@PathVariable Long id){
        Bill bill = billRepository.findById(id).get();
        bill.setCustomer(customerRestClient.findCustomerById(bill.getCustomerId()));

        bill.getProductItems().forEach(p -> {
            p.setProduct(productRestClient.getProductById(p.getProductId()));
        });
        return bill;
    }
}
