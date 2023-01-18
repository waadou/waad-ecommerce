package com.waadsoft.ecommerce.backend.user;

import com.waadsoft.ecommerce.common.entities.Role;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.test.annotation.Rollback;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static java.util.stream.Collectors.toList;

/**
 *
 * @author Alassani ABODJI <abodjialassani[at]gmail.com>
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false) // Tell Spring Boot not to rollback after test(persist data definitely)
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    //@Test
    public void givenCreateRoleWhenLoadTheRoleThenExpectSameRole() {
        Role role = new Role("Admin", "Manage everything");
        Role savedRole = roleRepository.save(role);

        assertThat(savedRole.getId()).isGreaterThan(0);
        assertThat(roleRepository.findById(savedRole.getId()).get()).isEqualTo(role);
        assertThat(roleRepository.findByName(role.getName()).get()).isEqualTo(role);
    }

    //@Test
    public void givenCreateCollectionOfRolesWhenLoadRolesThenExpectSameSize() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("SalesPerson", "Manage product prices, customers, shipping, orders and sales report"));
        roles.add(new Role("Editor", "Manage product categories, brands, products, articles and menus"));
        roles.add(new Role("Shipper", "View products, view orders and update order status"));
        roles.add(new Role("Assistant", "Manage questions and reviews"));

        Iterable<Role> savedRoles = roleRepository.saveAll(roles);
        assertThat(size(roles)).isEqualTo(size(savedRoles));

        List<String> rolesNames = roles.stream().map(Role::getName).collect(toList());
        List<Role> foundRoles = roleRepository.findByNameIn(rolesNames);
        assertThat(roles.size()).isEqualTo(foundRoles.size());
    }

    public static int size(Iterable data) {

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
