package com.silstechnologie.customerservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

// TODO 6- deuxième méthode de fichier de configuration
//@ConfigurationProperties(prefix = "customer.params")
public record CustomerConfigParams(int x, int y) {
}
