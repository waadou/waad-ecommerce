package com.waadsoft.ecommerce.common.entities;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Optional;
import java.util.Collections;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import com.waadsoft.ecommerce.common.core.PersistableEnum;
import com.waadsoft.ecommerce.common.core.PersistableEnumConverter;

/**
 *
 * @author Alassani ABODJI <abodjialassani[at]gmail.com>
 */
public enum UserStatus implements PersistableEnum<String> {
    NEW("N"),
    ACTIVE("A"),
    BLOCKED("B"),
    DELETED("W"),
    BANNED("D");

    private static final Map<String, UserStatus> VALUES_MAP = Stream.of(values())
            .collect(Collectors.toConcurrentMap(as -> as.value, as -> as));

    private static final Set<UserStatus> STATUS = Collections.unmodifiableSet(new HashSet<>(VALUES_MAP.values()));

    private final String value;

    private UserStatus(String value) {
        this.value = value;
    }

    /*----------------------------------------------------------
     |            F A C T O R Y   M E T H O D S                |
     ==========================================================*/
    public static Set<UserStatus> getStatus() {
        return STATUS;
    }

    public static Optional<UserStatus> from(String value) {
        return Optional.ofNullable(VALUES_MAP.get(value));
    }

    /*--------------------------------------------
     |  A C C E S S O R S / M O D I F I E R S    |
     ============================================*/
    @Override
    public String getValue() {
        return value;
    }

    public boolean isNew() {
        return this == NEW;
    }

    public boolean isActive() {
        return this == ACTIVE;
    }

    public boolean isEnabled() {
        return isActive();
    }

    public boolean isBlocked() {
        return this == BLOCKED;
    }

    public boolean isBanned() {
        return this == BANNED;
    }

    public boolean isDeleted() {
        return this == DELETED;
    }

    @javax.persistence.Converter(autoApply = true)
    public static class Converter extends PersistableEnumConverter<UserStatus, String> {

        public Converter() {
            super(UserStatus.class);
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
