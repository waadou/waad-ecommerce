/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.waadsoft.ecommerce.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.waadsoft.ecommerce.common.entities.Role;

/**
 *
 * @author Alassani ABODJI <abodjialassani[at]gmail.com>
 */
@SpringBootApplication
@EntityScan(basePackageClasses = {Role.class})
public class WaadEcommerceBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(WaadEcommerceBackendApplication.class, args);
    }
}
