package com.waadsoft.ecommerce.common.entities;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author Alassani ABODJI <abodjialassani[at]gmail.com>
 */
public final class SecurityUser implements UserDetails {

    private static final long serialVersionUID = 1L;

    private final User user;

    public SecurityUser(User user) {
        this.user = requireNonNull(user, User.class.getSimpleName() + " not specified!");
    }

    public static SecurityUser of(User user) {
        return new SecurityUser(user);
    }

    public static Collection<SecurityUser> from(Collection<User> users) {
        if (users == null || users.isEmpty()) {
            return Collections.emptyList();
        }
        return users.stream().map(SecurityUser::of).collect(toList());
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<CharSequence> getAuthorities() {
        return Arrays.asList("");
    }

    @Override
    public boolean isAccountNonExpired() {
        return !user.isDisabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.isBlocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
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
