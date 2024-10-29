package com.silstechnologie.customerservice.entities;

import org.springframework.data.rest.core.config.Projection;

//permet de personnaliser les retours d'api
// pour lui faire appel il faut http://localhost:8082/customers?projection=all
@Projection(name= "all", types=Customer.class)
public interface CustomerProjection {
    String getName();
    String getEmail();
}
