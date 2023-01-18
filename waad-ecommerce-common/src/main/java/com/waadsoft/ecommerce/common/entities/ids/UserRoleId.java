package com.waadsoft.ecommerce.common.entities.ids;

import java.util.Objects;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import com.waadsoft.ecommerce.common.entities.Role;
import com.waadsoft.ecommerce.common.entities.User;

/**
 *
 * @author Alassani ABODJI <abodjialassani[at]gmail.com>
 */
@Embeddable
public class UserRoleId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "USERS")
    private Long userId;

    @Column(name = "ROLES")
    private Long roleId;

    /*---------------------------------------------------------
    |              C O N S T R U C T O R S                    |
    ==========================================================*/
    protected UserRoleId() {
    }

    private UserRoleId(Long userId, Long roleId) {
        this.roleId = roleId;
        this.userId = userId;
    }

    /*---------------------------------------------------------
    |            F A C T O R Y   M E T H O D S                |
    ==========================================================*/
    public static UserRoleId of() {
        return new UserRoleId();
    }

    public static UserRoleId of(Long userId, Long roleId) {
        return new UserRoleId(userId, roleId);
    }

    public static UserRoleId of(User user, Role role) {
        requireNonNull(role, "Role not specified !");
        requireNonNull(user, "User not specified !");

        return new UserRoleId(user.getId(), role.getId());
    }

    public static UserRoleId of(String id) {
        requireNonNull(id, "ID must not be blank !");
        String[] parts = id.split(",");
        if(parts.length != 2) {
            throw new IllegalArgumentException("Invalid ID of type 'AccountRoleId' !");
        }
        return new UserRoleId(Long.valueOf(parts[0]), Long.valueOf(parts[1]));
    }

    /*-------------------------------------------
    |  A C C E S S O R S / M O D I F I E R S    |
    ============================================*/
    public Long getUserId() {
        return userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    /*---------------------------------------------------------
    |   H A S H C O D E  /  E Q U A L S  /  T O S T R I N G   |
    ==========================================================*/
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (isNull(obj) || !(obj instanceof UserRoleId)) {
            return false;
        }
        final UserRoleId other = (UserRoleId) obj;
        return Objects.equals(this.userId, other.userId)
                && Objects.equals(this.roleId, other.roleId);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.userId);
        hash = 29 * hash + Objects.hashCode(this.roleId);
        return hash;
    }

    @Override
    public String toString() {
        return String.format("%d,%d", userId, roleId);
    }
}

/*=============================================================================
 * Copyright 2022 Waad Soft<https://www.waadsoft.com>.
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
