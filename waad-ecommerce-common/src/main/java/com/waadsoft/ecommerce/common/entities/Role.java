package com.waadsoft.ecommerce.common.entities;

import java.util.Objects;
import java.util.Comparator;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import static java.util.Objects.requireNonNull;

import com.waadsoft.ecommerce.common.core.BaseEntity;

/**
 *
 * @author Alassani ABODJI <abodjialassani[at]gmail.com>
 */
@Entity
@Table(name = "WAAD_ROLE", uniqueConstraints = {
    @UniqueConstraint(name = "UK_WAAD_ROLE_NAME", columnNames = {"NAME"})
}, indexes = {
    @Index(name = "IDX_WAAD_ROLE_NAME", columnList = "NAME")
})
public class Role extends BaseEntity<Long> implements Comparable<Role> {

    private static final long serialVersionUID = 1L;

    private static final Comparator<Role> COMPARATOR = Comparator.comparing(Role::getName);

    @Column(name = "NAME", length = 63, nullable = false)
    @NotBlank(message = "{waad.validation.role.name.notBlank}")
    @Size(min = 2, max = 63, message = "{waad.validation.role.name.size}")
    private String name;

    @NotBlank(message = "{waad.validation.role.description.notBlank}")
    @Size(min = 3, max = 127, message = "{waad.validation.role.description.size}")
    @Column(name = "DESCRIPTION", length = 127, nullable = false)
    private String description;

    /*---------------------------------------------------------
    |      C   O   N   S   T   R   U   C   T   O   R   S      |
    ==========================================================*/
    protected Role() {
    }

    public Role(String name, String description) {
        this.name = requireNonNull(name, "Role name not specified!");
        this.description = requireNonNull(description, "Role description not specified!");;
    }

    /*---------------------------------------------------------
    |       A C C E S S O R S    /    M O D I F I E R S       |
    ==========================================================*/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /*---------------------------------------------------------
    |   H A S H C O D E  /  E Q U A L S  /  T O S T R I N G   |
    ==========================================================*/
    @Override
    public int compareTo(Role other) {
        return COMPARATOR.compare(this, other);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Role other = (Role) obj;
        return Objects.equals(this.name, other.name);
    }

    @Override
    public String toString() {
        return "Role{" + "id=" + getId() + ", name=" + name + ", description=" + description + '}';
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
