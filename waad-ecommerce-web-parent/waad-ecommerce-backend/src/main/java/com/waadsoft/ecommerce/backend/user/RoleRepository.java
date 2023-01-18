package com.waadsoft.ecommerce.backend.user;

import java.util.List;
import java.util.Optional;
import java.util.Collection;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import com.waadsoft.ecommerce.common.entities.Role;

/**
 *
 * @author Alassani ABODJI <abodjialassani[at]gmail.com>
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(String name);

    List<Role> findByNameIn(Collection<String> names);

    boolean existsByName(String name);
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
