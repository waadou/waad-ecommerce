package com.waadsoft.ecommerce.common.entities;

import java.util.Objects;
import javax.persistence.Table;
import javax.persistence.Index;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.XmlTransient;
import static java.util.Objects.requireNonNull;

import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Collections.unmodifiableSet;

import com.waadsoft.ecommerce.common.core.BaseEntity;

/**
 *
 * @author Alassani ABODJI <abodjialassani[at]gmail.com>
 */
@Entity
@Table(name = "WAAD_USER", uniqueConstraints = {
    @UniqueConstraint(name = "UK_WAAD_USER_EMAIL", columnNames = {"EMAIL"})
}, indexes = {
    @Index(name = "IDX_WAAD_USER_EMAIL", columnList = "EMAIL")
})
public class User extends BaseEntity<Long> implements Comparable<User> {

    private static final long serialVersionUID = 1L;

    private static final String ROLE_NOT_NULL_MSG = "Role not specified";
    private static final String ROLES_NOT_NULL_MSG = "Roles not specified";

    private static final Comparator<User> COMPARATOR = Comparator.comparing(User::getEmail);

    @NotBlank(message = "{waad.validation.user.firstname.notBlank}")
    @Size(min = 2, max = 63, message = "{waad.validation.user.firstname.size}")
    @Column(name = "FIRSTNAME", length = 63, nullable = false)
    private String firstname;

    @NotBlank(message = "{waad.validation.user.lastname.notBlank}")
    @Size(min = 2, max = 63, message = "{waad.validation.user.lastname.size}")
    @Column(name = "LASTNAME", length = 63, nullable = false)
    private String lastname;

    @NotBlank(message = "{waad.validation.user.email.notBlank}")
    @Size(min = 6, max = 127, message = "{waad.validation.user.email.size}")
    @Column(name = "EMAIL", length = 127, nullable = false)
    private String email;

    @Size(max = 255, message = "{waad.validation.user.photo.size}")
    @Column(name = "PHOTO", length = 255)
    private String photo;

    @XmlTransient
    @Column(name = "PASSWORD", nullable = false, length = 1023)
    @NotBlank(message = "{waad.validation.user.password.notBlank}")
    @Size(min = 5, max = 1023, message = "{waad.validation.user.password.size}")
    private String password;

    @Column(name = "STATUS", length = 1, nullable = false)
    @NotNull(message = "{waad.validation.account.user.notNull}")
    private UserStatus status = UserStatus.NEW;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<@NotNull UserRole> roles = new HashSet<>();

    /*---------------------------------------------------------
    |      C   O   N   S   T   R   U   C   T   O   R   S      |
    ==========================================================*/
    protected User() {
    }

    /*---------------------------------------------------------
    |         F A C T O R Y      M E T H O D S                |
    ==========================================================*/
    public static Builder builder() {
        return new Builder();
    }

    /*---------------------------------------------------------
    |       A C C E S S O R S    /    M O D I F I E R S       |
    ==========================================================*/
    @XmlTransient
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        requireNonNull(password, "The specified password is empty or blank !");
        this.password = password;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        requireNonNull(status, "No account status specified !");
        this.status = status;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public User activate() {
        this.status = UserStatus.ACTIVE;
        return this;
    }

    public User enable() {
        this.status = UserStatus.ACTIVE;
        return this;
    }

    public User block() {
        this.status = UserStatus.BLOCKED;
        return this;
    }

    public void disable() {
        this.status = UserStatus.BANNED;
    }

    public boolean isEnabled() {
        return nonNull(this.status) && this.status.isEnabled();
    }

    public boolean isActive() {
        return nonNull(this.status) && this.status.isActive();
    }

    public boolean isNotActive() {
        return !isActive();
    }

    public boolean isNewAccount() {
        return nonNull(this.status) && this.status.isNew();
    }

    public boolean isBlocked() {
        return nonNull(this.status) && this.status.isBlocked();
    }

    public boolean isDisabled() {
        return !isEnabled();
    }

    public Set<UserRole> getRoles() {
        return unmodifiableSet(toRoles());
    }

    public Set<Role> getRolesOnly() {
        return toRoles().stream().map(UserRole::getRole).collect(Collectors.toSet());
    }

    Set<UserRole> toRoles() {
        if (isNull(this.roles)) {
            this.roles = new HashSet<>();
        }
        return this.roles;
    }

    public boolean isRoleAssignedTo(Role role) {
        return contains(role);
    }

    public boolean isRoleAssignedTo(UserRole role) {
        return contains(role.getRole());
    }

    public void addRole(Role role) {
        requireNonNull(role, ROLE_NOT_NULL_MSG);
        toRoles().add(UserRole.of(this, role));
    }

    public void addRole(UserRole role) {
        requireNonNull(role, ROLE_NOT_NULL_MSG);
        addRole(role.getRole());
    }

    public void addRoles(Iterable<@NotNull Role> roles) {
        requireNonNull(roles, ROLES_NOT_NULL_MSG);
        roles.forEach(this::addRole);
    }

    public void addRoles(Collection<@NotNull UserRole> roles) {
        requireNonNull(roles, ROLES_NOT_NULL_MSG);
        roles.forEach(this::addRole);
    }

    public void addRoles(Role... roles) {
        requireNonNull(roles, ROLES_NOT_NULL_MSG);
        addRoles(Arrays.asList(roles));
    }

    public void removeRole(Role role) {
        if (nonNull(role)) {
            Optional<UserRole> found = toRoles().stream()
                    .filter(elt -> role.equals(elt.getRole()))
                    .findAny();

            if (found.isPresent()) {
                UserRole ar = found.get();
                toRoles().remove(ar);
                ar.setUser(null);
                ar.setRole(null);
            }
        }
    }

    public void removeRole(UserRole role) {
        if (nonNull(role)) {
            removeRole(role.getRole());
        }
    }

    public void removeRoles(Iterable<Role> roles) {
        requireNonNull(roles, ROLES_NOT_NULL_MSG);
        roles.forEach(this::removeRole);
    }

    public void removeRoles(Collection<UserRole> roles) {
        requireNonNull(roles, ROLES_NOT_NULL_MSG);
        roles.forEach(this::removeRole);
    }

    public void removeRoles(Role... roles) {
        requireNonNull(roles, ROLES_NOT_NULL_MSG);
        this.removeRoles(Arrays.asList(roles));
    }

    private boolean contains(Role role) {
        return toRoles().stream()
                .anyMatch(elt -> elt.getRole().equals(role));
    }

    /*---------------------------------------------------------
    |   H A S H C O D E  /  E Q U A L S  /  T O S T R I N G   |
    ==========================================================*/
    @Override
    public int compareTo(User other) {
        return COMPARATOR.compare(this, other);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.email);
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
        final User other = (User) obj;
        return Objects.equals(this.email, other.email);
    }

    @Override
    public String toString() {
        return "User{" + "firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", password=" + password + ", status=" + status + '}';
    }

    /*---------------------------------------------------------
    |          B   U   I   L   D   E   R                      |
    ==========================================================*/
    public static class Builder {

        private final User user;

        private Builder() {
            this.user = new User();
        }

        public User build() {
            return user;
        }

        public Builder firstname(String firstname) {
            user.firstname = firstname;
            return this;
        }

        public Builder lastname(String lastname) {
            user.lastname = lastname;
            return this;
        }

        public Builder email(String email) {
            user.email = email;
            return this;
        }

        public Builder photo(String photo) {
            user.photo = photo;
            return this;
        }

        public Builder password(String password) {
            user.password = password;
            return this;
        }

        public Builder status(UserStatus status) {
            user.status = status;
            return this;
        }

        public Builder enable() {
            return activate();
        }

        public Builder activate() {
            user.activate();
            return this;
        }

        public Builder role(UserRole role) {
            if (isNull(role)) {
                return this;
            }
            user.addRole(role);
            return this;
        }

        public Builder role(Role role) {
            if (isNull(role)) {
                return this;
            }
            user.addRole(role);
            return this;
        }

        public Builder roles(Iterable<@NotNull Role> roles) {
            if (isNull(roles) || !roles.iterator().hasNext()) {
                return this;
            }
            user.addRoles(roles);
            return this;
        }

        public Builder roles(Collection<@NotNull UserRole> roles) {
            if (isNull(roles) || roles.isEmpty()) {
                return this;
            }
            user.addRoles(roles);
            return this;
        }

        public Builder roles(Role... roles) {
            if (isNull(roles) || roles.length == 0) {
                return this;
            }
            user.addRoles(roles);
            return this;
        }
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
