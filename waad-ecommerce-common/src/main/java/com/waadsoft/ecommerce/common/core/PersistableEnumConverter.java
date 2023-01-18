package com.waadsoft.ecommerce.common.core;

import java.io.Serializable;
import java.util.stream.Stream;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import javax.persistence.AttributeConverter;

/**
 * Generic enum converter for JPA.
 *
 * @author Alassani ABODJI <abodjialassani[at]gmail.com>
 *
 * @param <E> Type of the converted value for the enum constant to be stored in
 * the database column.
 * @param <T> Type of the enum constant.
 * @see
 * <a href="https://stackoverflow.com/questions/23564506/is-it-possible-to-write-a-generic-enum-converter-for-jpa">Is
 * it possible to write a generic enum converter for JPA?</a>
 */
public class PersistableEnumConverter<T extends Enum<T> & PersistableEnum<E>, E extends Serializable>
        implements AttributeConverter<T, E> {

    private final Class<T> clazz;

    protected PersistableEnumConverter(Class<T> clazz) {
        requireNonNull(clazz, "Class of the enum must not be null !");
        this.clazz = clazz;
    }

    @Override
    public E convertToDatabaseColumn(T enumConstant) {
        return isNull(enumConstant) ? null : enumConstant.getValue();
    }

    @Override
    public T convertToEntityAttribute(E dbData) {
        return Stream.of(clazz.getEnumConstants())
                .filter(e -> e.getValue().equals(dbData))
                .findFirst()
                .orElse(null);
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
