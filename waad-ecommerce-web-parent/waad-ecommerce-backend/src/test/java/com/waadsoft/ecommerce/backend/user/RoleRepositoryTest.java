package com.waadsoft.ecommerce.backend.user;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import static java.util.stream.Collectors.toList;

import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import com.waadsoft.ecommerce.common.entities.Role;

/**
 *
 * @author Alassani ABODJI <abodjialassani[at]gmail.com>
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(true) // Set Rollback to false tell Spring Boot persist data definitely into the data store, otherwise it will rollback after the test. The default value is true.
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void givenCreateRoleWhenLoadTheRoleThenExpectSameRole() {
        Role role = Role.of("Admin", "Manage everything");
        Role savedRole = roleRepository.save(role);

        assertThat(savedRole.getId()).isGreaterThan(0);
        assertThat(roleRepository.findById(savedRole.getId()).get()).isEqualTo(role);
        assertThat(roleRepository.findByName(role.getName()).get()).isEqualTo(role);
    }

    @Test
    public void givenCreateRoleWhenLoadTheRolesThenExpectSameSize() {
        List<Role> roles = createRoles();

        Iterable<Role> savedRoles = roleRepository.saveAll(roles);
        assertThat(size(roles)).isEqualTo(size(savedRoles));

        List<String> rolesNames = roles.stream().map(Role::getName).collect(toList());
        List<Role> foundRoles = roleRepository.findByNameIn(rolesNames);
        assertThat(roles.size()).isEqualTo(foundRoles.size());
    }

    private List<Role> createRoles() {
        List<Role> roles = new ArrayList<>();
        roles.add(Role.of("SalesPerson", "Manage product prices, customers, shipping, orders and sales report"));
        roles.add(Role.of("Editor", "Manage product categories, brands, products, articles and menus"));
        roles.add(Role.of("Shipper", "View products, view orders and update order status"));
        roles.add(Role.of("Assistant", "Manage questions and reviews"));

        return roles;
    }

    private static int size(Iterable data) {

        if (data instanceof Collection) {
            return ((Collection<?>) data).size();
        }
        int counter = 0;
        for (Object i : data) {
            counter++;
        }
        return counter;
    }
}

/*=============================================================================
 * Copyright 2023 Waad Soft<https://www.waadsoft.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 =============================================================================*/
