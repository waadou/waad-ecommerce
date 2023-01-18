package com.waadsoft.ecommerce.common.entities;

import java.util.Objects;
import javax.persistence.Table;
import javax.persistence.Index;
import javax.persistence.Entity;
import javax.persistence.MapsId;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.EmbeddedId;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import static java.util.Objects.isNull;
import javax.persistence.UniqueConstraint;
import static java.util.Objects.requireNonNull;

import com.waadsoft.ecommerce.common.core.BaseEntity;
import com.waadsoft.ecommerce.common.entities.ids.UserRoleId;

/**
 *
 * @author Alassani ABODJI <abodjialassani[at]gmail.com>
 */
@Entity
@Table(name = "WAAD_USER_ROLE", uniqueConstraints = {
    @UniqueConstraint(name = "WAAD_USER_ROLE_USERS_ROLES", columnNames = {"USERS", "ROLES"})
}, indexes = {
    @Index(name = "IDX_WAAD_USER_ROLE_ROLES", columnList = "ROLES"),
    @Index(name = "IDX_WAAD_USER_ROLE_USERS", columnList = "USERS"),
    @Index(name = "IDX_WAAD_USER_ROLE_USERS_ROLES", columnList = "USERS,ROLES"),
    @Index(name = "IDX_WAAD_USER_ROLE_ROLES_USERS", columnList = "ROLES,USERS")
})
public class UserRole extends BaseEntity<UserRoleId> {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private UserRoleId id;

    @MapsId("roleId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLES", nullable = false, foreignKey = @ForeignKey(name = "FK_WAAD_USER_ROLE_ROLES"))
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "USERS", nullable = false, foreignKey = @ForeignKey(name = "FK_WAAD_USER_ROLE_USERS"))
    private User user;

    /*---------------------------------------------------------
    |              C O N S T R U C T O R S                    |
    ==========================================================*/
    protected UserRole() {
    }

    private UserRole(User user, Role role) {
        requireNonNull(role, "Role not specified !");
        requireNonNull(user, "User not specified !");

        this.role = role;
        this.user = user;
        this.id = UserRoleId.of(user, role);
    }

    /*---------------------------------------------------------
    |            F A C T O R Y   M E T H O D S                |
    ==========================================================*/
    public static UserRole of() {
        return new UserRole();
    }

    public static UserRole of(User user, Role role) {
        return new UserRole(user, role);
    }

    /*-------------------------------------------
    |  A C C E S S O R S / M O D I F I E R S    |
    ============================================*/
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /*---------------------------------------------------------
    |                O T H E R   M E T H O D S                |
    ==========================================================*/
    @Override
    public UserRoleId getId() {
        return id;
    }

    /*---------------------------------------------------------
    |   H A S H C O D E  /  E Q U A L S  /  T O S T R I N G   |
    ==========================================================*/
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (isNull(obj) || !(obj instanceof UserRole)) {
            return false;
        }
        final UserRole other = (UserRole) obj;
        return Objects.equals(this.user, other.user)
                && Objects.equals(this.role, other.role);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.user);
        hash = 67 * hash + Objects.hashCode(this.role);
        return hash;
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
 * See the License for the specific language governing accounts and
 * limitations under the License.
 =============================================================================*/
