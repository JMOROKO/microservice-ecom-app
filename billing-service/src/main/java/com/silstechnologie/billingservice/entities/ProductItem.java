package com.silstechnologie.billingservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.silstechnologie.billingservice.model.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class ProductItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productId;
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Bill bill;
    private int quantity;
    private double unitPrice;
    @Transient
    private Product product; //cette classe est représenté dans un autre microsercice
}
