package com.waadsoft.ecommerce.backend.user;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaBuilder;

import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import com.waadsoft.ecommerce.common.entities.Role;
import com.waadsoft.ecommerce.common.entities.User;

/**
 *
 * @author Alassani ABODJI <abodjialassani[at]gmail.com>
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(true) // Set Rollback to false tell Spring Boot persist data definitely into the data store, otherwise it will rollback after the test. The default value is true.
class UserRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void givenCreateUserWhenLoadTheUserThenExpectSameUser() {
        Optional<Role> optRole = findRoleByName("Admin");
        User user = User.builder()
                .firstname("Alassani")
                .lastname("ABODJI")
                .email("abodjialassani@gmail.com")
                .password("ala2i")
                .activate()
                .build();

        optRole.ifPresent(user::addRole);

        User savedUser = userRepository.save(user);

        assertThat(savedUser.getId()).isGreaterThan(0);
        assertThat(userRepository.findById(savedUser.getId()).get()).isEqualTo(user);
        assertThat(userRepository.findByEmail(savedUser.getEmail()).get()).isEqualTo(user);
    }

    @Test
    public void givenCreateUserWhenLoadUserThenExpectSameSize() {
        List<User> users = createUsers();
        Iterable<User> savedUsers = userRepository.saveAll(createUsers());

        assertThat(size(users)).isEqualTo(size(savedUsers));
        assertThat(userRepository.findByEmail("abodjiaminou@gmail.com").get()).isNotNull();
        assertThat(userRepository.existsByEmail("abodjidazassoh@gmail.com")).isFalse();
    }

    @Test
    public void givenUpdateUserWhenLoadTheUserThenExpectUpdatedUser() {
        Role adminRole = findRoleByName("Admin")
                .orElse(Role.of("Admin", "Manage everything"));
        Role editor = findRoleByName("SalesPerson")
                .orElse(Role.of("Editor", "Manage product categories, brands, products, articles and menus"));


        User user = User.builder()
                .firstname("Melany")
                .lastname("DORLAND")
                .email("melanydorland@goldenkids.com")
                .password("melany")
                .role(editor)
                .role(adminRole)
                .build();

        userRepository.save(user);

        // Make some changes
        user.enable();
        user.setPhoto("beautiful-melany-dorland.png");
        user.removeRole(adminRole);

        User savedUser = userRepository.save(user);

        assertThat(userRepository.findById(savedUser.getId()).get().getRoles().size()).isEqualTo(1);
        assertThat(userRepository.findById(savedUser.getId()).get().isEnabled()).isTrue();
        assertThat(userRepository.findById(savedUser.getId()).get().getPhoto()).isEqualTo("beautiful-melany-dorland.png");
    }

    @Test
    public void givenDeleteUserWhenLoadTheUserThenExpectNoUser() {
        Role adminRole = findRoleByName("Admin")
                .orElse(Role.of("Admin", "Manage everything"));

        User user = User.builder()
                .firstname("Natalie")
                .lastname("PARKS")
                .email("natalieparks@parksfondation.org")
                .password("natalie")
                .role(adminRole)
                .build();

        User savedUser = userRepository.save(user);

        assertThat(userRepository.findById(savedUser.getId()).get()).isEqualTo(user);

        userRepository.delete(user);

        assertThat(userRepository.findById(savedUser.getId()).isPresent()).isFalse();
    }

    private Optional<Role> findRoleByName(String name) {
        try {
            EntityManager em = testEntityManager.getEntityManager();
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<Role> query = builder.createQuery(Role.class);
            Root<Role> role = query.from(Role.class);

            query.where(builder.equal(role.get("name"), name));

            return Optional.ofNullable(em.createQuery(query).getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private List<User> createUsers() {
        List<User> users = new ArrayList<>();

        Role salesPerson = findRoleByName("SalesPerson")
                .orElse(Role.of("SalesPerson", "Manage product prices, customers, shipping, orders and sales report"));

        Role editor = findRoleByName("SalesPerson")
                .orElse(Role.of("Editor", "Manage product categories, brands, products, articles and menus"));

        Role shipper = findRoleByName("SalesPerson")
                .orElse(Role.of("Shipper", "View products, view orders and update order status"));

        Role assistant = findRoleByName("SalesPerson")
                .orElse(Role.of("Assistant", "Manage questions and reviews"));

        User user = User.builder()
                .firstname("Aminou")
                .lastname("ABODJI")
                .email("abodjiaminou@gmail.com")
                .password("aminou")
                .role(editor)
                .role(salesPerson)
                .build();

        users.add(user);

        user = User.builder()
                .firstname("Asmiou")
                .lastname("ABODJI")
                .email("abodjiasmiou@gmail.com")
                .password("asmiou")
                .role(editor)
                .role(shipper)
                .role(salesPerson)
                .build();

        users.add(user);

        user = User.builder()
                .firstname("Meheza")
                .lastname("BIAO")
                .email("biaomeheza@gmail.com")
                .password("meheza")
                .role(assistant)
                .build();

        users.add(user);

        return users;
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
