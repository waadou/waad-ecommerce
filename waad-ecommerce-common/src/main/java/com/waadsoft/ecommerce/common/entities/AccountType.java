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
 * Represents the type of an {@link Account account}. An acount can be:
 * <ul>
 * <li>{@link #USER}: It is a type for regular a account, for example an account
 * for a person.</li>
 * <li>{@link #SYSTEM}: This type of account suits an entity like a system, an
 * external application that needs to login in, or access some endpoints of our
 * a current application.</li>
 * </ul>
 *
 * @author Alassani ABODJI <abodjialassani[at]gmail.com>
 */
public enum AccountType implements PersistableEnum<String> {
    USER("U"),
    SYSTEM("S");

    private static final Map<String, AccountType> VALUES_MAP = Stream.of(values())
            .collect(Collectors.toConcurrentMap(at -> at.value, at -> at));

    private static final Set<AccountType> ACCOUNT_TYPES = Collections.unmodifiableSet(new HashSet<>(VALUES_MAP.values()));

    private final String value;

    private AccountType(String value) {
        this.value = value;
    }

    /*----------------------------------------------------------
     |            F A C T O R Y   M E T H O D S                |
     ==========================================================*/
    public static Set<AccountType> getTypes() {
        return ACCOUNT_TYPES;
    }

    public static Optional<AccountType> from(String value) {
        return Optional.ofNullable(VALUES_MAP.get(value));
    }

    /*--------------------------------------------
     |  A C C E S S O R S / M O D I F I E R S    |
     ============================================*/
    @Override
    public String getValue() {
        return value;
    }

    public boolean isSystem() {
        return this == SYSTEM;
    }

    public boolean isUser() {
        return this == USER;
    }

    @javax.persistence.Converter(autoApply = true)
    public static class Converter extends PersistableEnumConverter<AccountType, String> {

        public Converter() {
            super(AccountType.class);
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
